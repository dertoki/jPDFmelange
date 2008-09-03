/***************************************************************************
 *   Copyright (C) 2008 by Tobias Tandetzki                                *
 *   tandetzki.tobias@t-online.de                                          *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/
package jPDFmelange;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 * Transferhandler, used for DnD to and from internal lists and from OS filemanager.
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class ArrayListTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 8465952157592069923L;
	DataFlavor localArrayListFlavor, serialArrayListFlavor;
    String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType + ";class=java.util.ArrayList";
    JList source = null;
    JList target = null;
	MelangeJFrame parent = null;
    int sourceIdx[] = null;
    int targetIdx[] = null;

	/** 
	 *  Creates the Flavor for internal list transfer. 
	 */
    public ArrayListTransferHandler() {
        try {
            localArrayListFlavor = new DataFlavor(localArrayListType);
        } catch (ClassNotFoundException e) {
            System.out.println(
             "ArrayListTransferHandler: unable to create data flavor");
        }
        serialArrayListFlavor = new DataFlavor(ArrayList.class,"ArrayList");
    }

	/** 
	 *  Is called when a drop event occurs.<br>
	 *  Manages data flow depending on the transferables flavor.
	 *   
	 *	@param c The GUI element receiving the data.
	 *	@param t data element that is transfered.
	 *	@return <code>true</code> when flavor is supported, else <code>false</code>.
	 */
    public boolean importData(JComponent c, Transferable t) {
        DataFlavor flavors[] = t.getTransferDataFlavors();

        if (!canImport(c, t.getTransferDataFlavors())) {
            return false;
        }
        parent = (MelangeJFrame) c.getRootPane().getParent();
        target = (JList)c;
		if (hasFileListFlavor(flavors)){
			System.out.println("Received drop with FileListFlavor.");                	
			return onFileListFlavor(t);
		} else if (hasTextTypeFlavor(flavors)){
			System.out.println("Received drop with TextTypeFlavor.");                	
			return onTextTypeFlavor(t);
		} else if (hasLocalArrayListFlavor(flavors)) {
			System.out.println("Received drop with LocalArrayListFlavor.");                	
		    return onArrayListFlavor(t, localArrayListFlavor);
		} else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
			System.out.println("Received drop with SerialArrayListFlavor.");                	
		    return onArrayListFlavor(t, serialArrayListFlavor);
		} else {
		    return false;
		}
    }

	/** 
	 *  Handling a list of files when a JList receives a transfer.
	 *  <p> 
	 *  Starts file opening task for each file.<br>
	 *  This happens when the transferable has following flavors:
	 *  <ul> 
	 *  	<li>java.awt.datatransfer.DataFlavor.isFlavorJavaFileListType()
	 *  	<li>java.awt.datatransfer.DataFlavor.isFlavorTextType()
	 *  </ul>
	 *  <p>
	 *  If JList is our main list then the file is inserted to the drop position,<br>
	 *  if JList is our buffer list, the file is appended to the list.
	 *
	 *	@param files list of File elements that are casted as java.io.File.
	 *	@return <code>true</code> when tasks have been started, <code>false</code> when no files processed.
	 *  @throws IOException if file does not exist.
	 */
    private boolean onReceivedFileList(ArrayList files) throws IOException{

    	// Check if the dropped files are actually of type pdf.
    	PDFFilter filter = new PDFFilter();
        for (int i=0; i<files.size(); i++){
    		if (!filter.accept((File)files.get(i))){
    			File file = (File) files.remove(i);
    			System.out.println("Removing non PDF-File from list: " + file.getName());
        	}
        }
        if (files.isEmpty()) return false;
        
        // Insert the dropped files in the selected list Main or list Buffer.
        //
        // If the Main List is selected insert on selected position or append if List is empty.
        if (target.equals(parent.jListMain)){
        	if (parent.jListMain.getModel().getSize() == 0){
        		for (int i=0; i<files.size(); i++){
        			String fileName = ((File)files.get(i)).getCanonicalPath();
        			if (i==0) parent.openFileMain(fileName);
        			else {
                		CreateThumbnailsJP task = new CreateThumbnailsJP(parent, 
																		 fileName, 
																		 parent.jListMain);
                		task.start();
        			}
        		}        		
        	} else {
        		for (int i=0; i<files.size(); i++){
        			String fileName = ((File)files.get(i)).getCanonicalPath();
            		CreateThumbnailsJP task = new CreateThumbnailsJP(parent, 
							 fileName, 
							 parent.jListMain);
            		if (!target.isSelectionEmpty()) task.setInsertOffset(target.getSelectedIndex()+1);
            		task.start();
        		}        		
        	}
        // If Buffer List is selected, simply append the dropped files.
        } else if (target.equals(parent.jListBuffer)){
    		for (int i=0; i<files.size(); i++){
    			String fileName = ((File)files.get(i)).getCanonicalPath();
        		CreateThumbnailsJP task = new CreateThumbnailsJP(parent, 
        														 fileName, 
        														 parent.jListBuffer
        														);
        		task.start();
    		}        		
        }
        return true;
    }
    
	/** 
	 *  Manages Drops with <code>java.awt.datatransfer.DataFlavor.javaFileListFlavor</code>.
	 *  <p> 
	 *  This occurs only with windows (Tested with windows 98 and windows XP).
	 *  
	 *	@param t data element that is transfered.
	 *	@return <code>true</code> when flavor is supported, <code>false</code> on error.
	 */
    private boolean onFileListFlavor(Transferable t){
        List files;
		try {
			files = (List)t.getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
			return onReceivedFileList(new ArrayList(files));
		} catch (UnsupportedFlavorException e) {
            System.out.println("importData: unsupported data flavor");
            return false;
		} catch (IOException e) {
            System.out.println("importData: I/O exception");
            return false;
		}            	
    }
    
	/** 
	 *  Manages Drops with text type flavor.
	 *  <p> 
	 *  This occurs with gnome and kde.
	 *  
	 *	@param t data element that is transfered.
	 *	@return <code>true</code> when flavor is supported, <code>false</code> on error.
	 */
    private boolean onTextTypeFlavor(Transferable t){
    	ArrayList files = new ArrayList();
    	DataFlavor flavor = DataFlavor.selectBestTextFlavor(t.getTransferDataFlavors());
        BufferedReader reader;
		try {
			reader = new BufferedReader(flavor.getReaderForText(t));
	        for (String line = reader.readLine(); line!=null; line = reader.readLine()){
	        	if(Character.toString('\000').equals(line)) continue;
				URI uri = new URI(line);
				files.add(new File(uri));
	        }
	    	return onReceivedFileList(files);
		} catch (UnsupportedFlavorException e1) {
            System.out.println("importData: unsupported data flavor");
            return false;
		} catch (IOException e1) {
            System.out.println("importData: I/O exception");
            return false;
		} catch (URISyntaxException e1) {
            System.out.println("importData: URI-Syntax exception");
            return false;
		}
    }
    
	/** 
	 *  Handling DnD when a JList to JList transfer is initiated.
	 *  <p> 
	 *  Depending on source and target, the transfer is executed.
	 *  
	 *	@param t data element that is transfered.
	 *	@param flavor indicates how to interpret the transfered data.
	 *	@return <code>false</code> on error.
	 */
    private boolean onArrayListFlavor(Transferable t, DataFlavor flavor){
        try {
			ArrayList transferList =  (ArrayList)t.getTransferData(flavor);
	        DefaultListModel sourceContent = (DefaultListModel)source.getModel();
	        DefaultListModel targetContent = (DefaultListModel)target.getModel();
	        
	        int targetIndex = targetContent.getSize();
	        if (!target.isSelectionEmpty()) targetIndex = target.getSelectedIndex()+1;
	        int newTargetIndex = targetIndex;
	        targetIdx = new int[sourceIdx.length];

	        if (source.equals(target)) {
	        	// check if dropping on selected item
	    		for (int i=0; i<sourceIdx.length; i++){
					if (sourceIdx[i] == (targetIndex-1)) return false;
				}
	    		// if there are selected items before target index count down the target index 
	    		for (int i=0; i<sourceIdx.length; i++){
					if (sourceIdx[i] < (targetIndex-1)) newTargetIndex--;
				}
	    		// remove selected indexes, create target indexes 
				for (int i=0; i<sourceIdx.length; i++){				
					sourceContent.remove(sourceIdx[sourceIdx.length -1 - i]); // remove reverse
					targetIdx[i] = newTargetIndex + i;
				}
				// instert selected indexes on their new position 
				for (int i=0; i<sourceIdx.length; i++){				
					sourceContent.add(targetIdx[i], transferList.get(i));
				}			
				// Update the graphical representation
				source.setSelectedIndices(targetIdx);
	        	return true;
	        } else {
	        	for (int i=0; i < transferList.size(); i++) {
	                targetIdx[i] = targetIndex + i;
	                targetContent.add(targetIdx[i], transferList.get(i));
	            }
				// Update the graphical representation
				target.setSelectedIndices(targetIdx);
	            return true;
	        }
		} catch (UnsupportedFlavorException e) {
            System.out.println("importData: unsupported data flavor");
            return false;
		} catch (IOException e) {
            System.out.println("importData: I/O exception");
            return false;
		}
    }
    
	/** 
	 *  Handling DnD when a JList to JList transfer is done.
	 *  <p> 
	 *  If source and target is not equal the transfered data is removed from our source.
	 *  
	 *	@param c The GUI element receiving the data.
	 *	@param data data element that is transfered.
	 *	@param action Indicates if move or copy was initiated.
	 */
    protected void exportDone(JComponent c, Transferable data, int action) {
    	// We just need MOVE, so ignore possibilities given with action.
    	
    	// Ok, when "exportDone" is called "createTransferable" and "importData" are completed.
    	// The last thing to do is to remove the transfered data form our source:

    	if (source.equals(target)) return;
    	
        ArrayList transferList = null;
        try {
            if (hasLocalArrayListFlavor(data.getTransferDataFlavors())) {
                transferList = (ArrayList)data.getTransferData(localArrayListFlavor);
            } else if (hasSerialArrayListFlavor(data.getTransferDataFlavors())) {
                transferList = (ArrayList)data.getTransferData(serialArrayListFlavor);
            } else {
                return;
            }
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
            return;
        } catch (IOException ioe) {
            System.out.println("importData: I/O exception");
            return;
        }
        
        DefaultListModel sourceContent = (DefaultListModel)source.getModel();
        
        for (int i=(transferList.size()-1); i>=0; i--) {
        	sourceContent.remove(sourceIdx[i]);
        }
    }

	/** 
	 *  Test if the array of flavors contains <code>FlavorJavaFileListType</code>.
	 *   
	 *	@param flavors array of flavors.
	 *	@return <code>false</code> if flavor is not supported.
	 */
    private boolean hasFileListFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
        	if (flavors[i].isFlavorJavaFileListType()){
                return true;
        	}
        }
        return false;
    }

	/** 
	 *  Test if the array of flavors contains <code>FlavorTextType</code>.
	 *   
	 *	@param flavors array of flavors.
	 *	@return <code>false</code> if flavor is not supported.
	 */
    private boolean hasTextTypeFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
        	if (flavors[i].isFlavorTextType()){
                return true;
        	}
        }
        return false;
    }

	/** 
	 *  Test if the array of flavors contains <code>localArrayListFlavor</code>.
	 *   
	 *	@param flavors array of flavors.
	 *	@return <code>false</code> if flavor is not supported.
	 */
    private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
        if (localArrayListFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(localArrayListFlavor)) {
                return true;
            }
        }
        return false;
    }

	/** 
	 *  Test if the array of flavors contains <code>serialArrayListFlavor</code>.
	 *   
	 *	@param flavors array of flavors.
	 *	@return <code>false</code> if flavor is not supported.
	 */
    private boolean hasSerialArrayListFlavor(DataFlavor[] flavors) {
        if (serialArrayListFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(serialArrayListFlavor)) {
                return true;
            }
        }
        return false;
    }

	/** 
	 *  Test if the element, receiving the drop supports the its flavor.
	 *   
	 *	@param c The GUI element receiving the data.
	 *	@param flavors array of flavors.
	 *	@return <code>false</code> if flavor is not supported.
	 */
   public boolean canImport(JComponent c, DataFlavor[] flavors) {
        if (hasFileListFlavor(flavors))  { return true; }
        if (hasTextTypeFlavor(flavors))  { return true; }
        if (hasLocalArrayListFlavor(flavors))  { return true; }
        if (hasSerialArrayListFlavor(flavors)) { return true; }
        return false;
    }

	/** 
	 *  Creates data for transfer when Drag is initiated on a JList.
	 *   
	 *	@param c The GUI element receiving the data.
	 *	@return data element that is transfered.
	 */
    protected Transferable createTransferable(JComponent c) {
        
        if (c instanceof JList) {
            source = (JList)c;
            sourceIdx = source.getSelectedIndices();
            Object[] values = source.getSelectedValues();
            if (values == null || values.length == 0) {
                return null;
            }
            ArrayList transferList = new ArrayList(values.length);
            for (int i = 0; i < values.length; i++) {
                transferList.add(values[i]);
            }
            return new ArrayListTransferable(transferList);
        }
        return null;
    }

	/** 
	 *  Specify the supported actions.
	 *   
	 *	@param c The GUI element receiving the data.
	 *	@return int <code>MOVE</code>.
	 */
    public int getSourceActions(JComponent c) {
        //return COPY_OR_MOVE;
        return MOVE;
    }

    /**
     * Class definition for our Transferable based ArrayList.
     */
    public class ArrayListTransferable implements Transferable {
        ArrayList data;

        public ArrayListTransferable(ArrayList alist) {
            data = alist;
        }

        public Object getTransferData(DataFlavor flavor)
                                 throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return data;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { localArrayListFlavor,
                                      serialArrayListFlavor };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            if (localArrayListFlavor.equals(flavor)) {
                return true;
            }
            if (serialArrayListFlavor.equals(flavor)) {
                return true;
            }
            return false;
        }
    }
}
