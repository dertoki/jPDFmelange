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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jpedal.PdfDecoder;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNumber;
import com.lowagie.text.pdf.PdfReader;

public class MelangeJFrame extends JFrame {

	/**
	 *  Projekct Main Class
	 */
	private static final long serialVersionUID = 4042464615276354878L;
	
	public static final String projectName = "jPDFmelange";
	public static final String projectVersion = "0.1.11b";
	public static String propertiesFileName = System.getProperty("user.dir").concat(System.getProperty("file.separator")).concat("melange.rc");
	public static String canonicalBufferFileName = "";
	public static String canonicalMainFileName  = "";
	static String currentDirectoryPath = "";

	// Properties set in propertiesFile
    static int iconHeight = 70; // in pixels
    static int imageScalingAlgorithm = BufferedImage.SCALE_SMOOTH;
	public static Locale locale = Locale.getDefault();
	public static ArrayList localeTable = new ArrayList();
    /*
     * Declaration for Java 5 (Java SE 1.5 or higher).  
     * The Sun Renderer com.sun.pdfview needs Java 5 (Java SE 1.5 or higher).
     * 
    	private Vector<PageNode> listContent1 = new Vector<PageNode>();  //  @jve:decl-index=0:
    	private Vector<PageNode> listContent2 = new Vector<PageNode>();  //  @jve:decl-index=0:
    */
    private Vector listContent1 = new Vector();  //  @jve:decl-index=0:
    private Vector listContent2 = new Vector();  //  @jve:decl-index=0:
    int indexOfPreviewPane;
	public static ResourceBundle messages = null;  //  @jve:decl-index=0:

	// GUI Elemets
	protected Object frame;
	private JButton jButtonAdd = null;
	private JButton jButtonDel = null;
	private JButton jButtonDown = null;
	private JButton jButtonDownToolbar = null;
	private JButton jButtonFileAddBuffer = null;
	private JButton jButtonFileNew = null;
	private JButton jButtonFileOpen = null;
	private JButton jButtonFileSave = null;
	private JButton jButtonUp = null;
	private JButton jButtonUpToolbar = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;

	public  JToolBar jToolBar = null;
	private PDFjList jList1 = null;
    private PDFjList jList2 = null; 
	private JMenu jMenuEdit = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemFileNew = null;
	private JMenuItem jMenuItemFileOpen = null;
	private JMenuItem jMenuItemFileSave = null;
	private JMenuItem jMenuItemSaveAs = null;
	private JMenu jMenuPreferences = null;
	private JPanel jPanelBufferEditor = null;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JSplitPane jSplitPane = null;
	private JTabbedPane jTabbedPane = null;
	private JMenuItem jMenuItemInfo = null;
	private JMenuItem jMenuItemClearMain = null;
	private JMenuItem jMenuItemClearBuffer = null;
	private PdfDecoder jPanePreview = null;
	private JButton jButtonRotateRight = null;
	private JButton jButtonRotateLeft = null;
	private JMenuItem jMenuItemFileAddBuffer = null;
	private JMenuItem jMenuItemPreferences = null;

	/**
	 * This is the default constructor
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public MelangeJFrame() {
		super();
		initialize();
		this.setTitle(projectName + ": " + canonicalMainFileName);
		listContent1.clear();
		jList1.removeAll();
	}

	/* (Kein Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	//@Override
	public void dispose() {
		MelangeJFrame.this.setVisible(false);
		super.dispose();
		System.exit(0);
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setEnabled(false);
			jButtonAdd.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonAdd.setIcon(new ImageIcon(getClass().getResource("/icons/Back24.gif")));
			jButtonAdd.setPreferredSize(new Dimension(30, 30));
			jButtonAdd.setToolTipText(messages.getString("movePage2Source"));
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onAddPages();
				}
			});
		}
		return jButtonAdd;
	}

	/**
	 * This method moves selected pages from the "Buffer List" to "Main List" 	
	 * The "Buffer List" is represented with 
	 *                  jList2: PDFjList (extends JList)
	 *                  listcontent2: Vector<PageNode>
	 * The "Main List" is represented with 
	 *                  jList1: PDFjList (extends JList)
	 *                  listcontent1: Vector<PageNode>
	 * 
	 * @return void	
	 */
	private void onAddPages(){
		int index1 = 0;
		int[] idx;

		// fix the current index the "Main List"
		if (jList1.isSelectionEmpty() == true){
			index1 = jList1.getModel().getSize();
		} else {
			index1 = jList1.getSelectedIndex() + 1;
		}

		if (jList2.isSelectionEmpty() == false) {
			idx = jList2.getSelectedIndices();
		
			// move the selected item of "Buffer List" to "Main List"
			for (int i=(idx.length-1); i>=0; i--) {
				moveContent(listContent2, idx[i], 
				   	 		listContent1, index1);
			}
			
			jList1.setListData(listContent1); 
			jList2.setListData(listContent2);  
			// Update the graphical representation
			for (int i=0; i<idx.length; i++){
				idx[i] = index1 + i;
			}
			jList1.setSelectedIndices(idx);						
			jList1.ensureIndexIsVisible(idx[idx.length-1]);
		}
		jList1.repaint();
		jList2.repaint();
	}

	/**
	 * This method moves content of List A to List B  	
	 * The List A is represented with 
	 *                  C1: Vector<PageNode>
	 * The List B is represented with 
	 *                  C2: Vector<PageNode>
	 * 
	 * @return int: new index of List A
	 */
	private int moveContent(Vector C1, int i1,
						    Vector C2, int i2){
		// append or insert the selection to the content of list2 
		if (i2 == C2.size()) {
			C2.add(C1.get(i1));
		} else {
			C2.add(i2, C1.get(i1));
		}
		// remove selection of content in list1
		C1.remove(i1);
		if (i1 == C1.size()) i1--;
		return i1;
	}
	
	/**
	 * This method initializes jButtonDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDel() {
		if (jButtonDel == null) {
			jButtonDel = new JButton();
			jButtonDel.setEnabled(false);
			jButtonDel.setIcon(new ImageIcon(getClass().getResource("/icons/Forward24.gif")));
			jButtonDel.setPreferredSize(new Dimension(30, 30));
			jButtonDel.setToolTipText(messages.getString("movePage2Buffer"));
			jButtonDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onMovePages2Buffer();
				}
			});
		}
		return jButtonDel;
	}

	private void onMovePages2Buffer(){
		int idx[] = null;
		int index2 = 0;
		if (jList2.isSelectionEmpty() == true){
			index2 = jList2.getModel().getSize();
		} else {
			index2 = jList2.getSelectedIndex() + 1;
		}

		if (jList1.isSelectionEmpty() == false) {
			idx = jList1.getSelectedIndices();

			// move the selected item of "Main List" to "Buffer List"
			for (int i=(idx.length-1); i>=0; i--) {
				moveContent(listContent1, idx[i], 
				   	 		listContent2, index2);
			}

			jList1.setListData(listContent1); 
			jList2.setListData(listContent2);  

			// Update the graphical representation
			for (int i=0; i<idx.length; i++){
				idx[i] = index2 + i;
			}
			jList2.setSelectedIndices(idx);					
			jList2.ensureIndexIsVisible(idx[idx.length-1]);
		}
		jList1.repaint();
		jList2.repaint();
	}
	
	/**
	 * This method initializes jButtonDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton();
			jButtonDown.setEnabled(false);
			jButtonDown.setIcon(new ImageIcon(getClass().getResource("/icons/Down24.gif")));
			jButtonDown.setPreferredSize(new Dimension(30, 30));
			jButtonDown.setToolTipText(messages.getString("movePageDown"));
			jButtonDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Down();
				}
			});
		}
		return jButtonDown;
	}

	/**
	 * This method initializes jButtonDownToolbar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDownToolbar() {
		if (jButtonDownToolbar == null) {
			jButtonDownToolbar = new JButton();
			jButtonDownToolbar.setEnabled(false);
			jButtonDownToolbar.setIcon(new ImageIcon(getClass().getResource("/icons/Down24.gif")));
			jButtonDownToolbar.setToolTipText(messages.getString("movePageDown"));
			jButtonDownToolbar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Down();
				}
			});
		}
		return jButtonDownToolbar;
	}
	
	/**
	 * This method initializes jButtonFileAddBuffer	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileAddBuffer() {
		if (jButtonFileAddBuffer == null) {
			jButtonFileAddBuffer = new JButton();
			jButtonFileAddBuffer.setIcon(new ImageIcon(getClass().getResource("/icons/Add24.gif")));
			jButtonFileAddBuffer.setToolTipText(messages.getString("loadFileToBuffer"));
			jButtonFileAddBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenBuffer();
				}
			});
		}
		return jButtonFileAddBuffer;
	}

	/**
	 * This method initializes jButtonFileNew	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileNew() {
		if (jButtonFileNew == null) {
			jButtonFileNew = new JButton();
			jButtonFileNew.setIcon(new ImageIcon(getClass().getResource("/icons/New24.gif")));
			jButtonFileNew.setToolTipText(messages.getString("newFile"));
			jButtonFileNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onMainFileNew();
				}
			});
		}
		return jButtonFileNew;
	}

	/**
	 * This method initializes jButtonFileOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileOpen() {
		if (jButtonFileOpen == null) {
			jButtonFileOpen = new JButton();
			jButtonFileOpen.setIcon(new ImageIcon(getClass().getResource("/icons/Open24.gif")));
			jButtonFileOpen.setToolTipText(messages.getString("openFile"));
			jButtonFileOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenMain();
				}
			});
		}
		return jButtonFileOpen;
	}

	/**
	 * This method initializes jButtonFileSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileSave() {
		if (jButtonFileSave == null) {
			jButtonFileSave = new JButton();
			jButtonFileSave.setIcon(new ImageIcon(getClass().getResource("/icons/Save24.gif")));
			jButtonFileSave.setToolTipText(messages.getString("saveFile"));
			jButtonFileSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave(canonicalMainFileName);
				}
			});
		}
		return jButtonFileSave;
	}
	
	/**
	 * This method initializes jButtonUp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton();
			jButtonUp.setEnabled(false);
			jButtonUp.setIcon(new ImageIcon(getClass().getResource("/icons/Up24.gif")));
			jButtonUp.setToolTipText(messages.getString("movePageUp"));
			jButtonUp.setPreferredSize(new Dimension(30, 30));
			jButtonUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Up();
				}
			});
		}
		return jButtonUp;
	}

	/**
	 * This method initializes jButtonUpToolbar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonUpToolbar() {
		if (jButtonUpToolbar == null) {
			jButtonUpToolbar = new JButton();
			jButtonUpToolbar.setEnabled(false);
			jButtonUpToolbar.setIcon(new ImageIcon(getClass().getResource("/icons/Up24.gif")));
			jButtonUpToolbar.setToolTipText(messages.getString("movePageUp"));
			jButtonUpToolbar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Up();
				}
			});
		}
		return jButtonUpToolbar;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuEdit());
			jJMenuBar.add(getJMenuPreferences());
			jJMenuBar.add(getJMenuHelp());
			//jJMenuBar.setHelpMenu(jMenuHelp);
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJButtonFileOpen());
			jToolBar.add(getJButtonFileNew());
			jToolBar.add(getJButtonFileSave());
			jToolBar.add(getJButtonFileAddBuffer());
			jToolBar.add(getJButtonUpToolbar());
			jToolBar.add(getJButtonDownToolbar());
			jToolBar.add(getJButtonRotateRight());
			jToolBar.add(getJButtonRotateLeft());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jList1	
	 * 	
	 * @return jPDFpellmell.PDFjList	
	 */
	private PDFjList getJList1() {
		if (jList1 == null) {
			jList1 = new PDFjList();
			jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (jList1.isSelectionEmpty() == true) { 
						jButtonDel.setEnabled(false);
						jButtonUp.setEnabled(false);
						jButtonDown.setEnabled(false);
						jButtonUpToolbar.setEnabled(false);
						jButtonDownToolbar.setEnabled(false);
						jButtonRotateLeft.setEnabled(false);
						jButtonRotateRight.setEnabled(false);
					} else {
						int[] idx = jList1.getSelectedIndices();
						jButtonDel.setEnabled(true);
						jButtonRotateLeft.setEnabled(true);
						jButtonRotateRight.setEnabled(true);						
						if (idx[0] == 0) {
							jButtonUp.setEnabled(false);
							jButtonUpToolbar.setEnabled(false);
						} else {
							jButtonUp.setEnabled(true);
							jButtonUpToolbar.setEnabled(true);
						}
						if (idx[idx.length-1] == jList1.getModel().getSize()-1) {
							jButtonDown.setEnabled(false);
							jButtonDownToolbar.setEnabled(false);
						} else {
							jButtonDown.setEnabled(true);
							jButtonDownToolbar.setEnabled(true);
						}
						if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
							try {
								showPreviewJP((PageNode)listContent1.get(idx[0]));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
			jList1.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jList1.setSelectionBackground(new Color(184, 207, 229));
					jList2.setSelectionBackground(new Color(220, 220, 220));
				}
			});
		}
		return jList1;
	}

	/**
	 * This method initializes jList2	
	 * 	
	 * @return jPDFmelange.PDFjList	
	 */
	private PDFjList getJList2() {
		if (jList2 == null) {
			jList2 = new PDFjList();
			jList2.setBackground(new Color(230, 230, 230));
			jList2.setSelectionBackground(new Color(220, 220, 220));
			jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (jList2.isSelectionEmpty() == true) 
						jButtonAdd.setEnabled(false);
					else
						jButtonAdd.setEnabled(true);
				}
			});
			jList2.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jList1.setSelectionBackground(new Color(235, 235, 235));
					jList2.setSelectionBackground(new Color(184, 207, 229));
				}
			});
			
		}
		return jList2;
	}

	/**
	 * This method initializes jMenuEdit	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuEdit() {
		if (jMenuEdit == null) {
			jMenuEdit = new JMenu();
			jMenuEdit.setText(messages.getString("edit"));
			jMenuEdit.add(getJMenuItemClearMain());
			jMenuEdit.add(getJMenuItemClearBuffer());
			jMenuEdit.add(getJMenuItemFileAddBuffer());
		}
		return jMenuEdit;
	}

	/**
	 * This method initializes jMenuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText(messages.getString("file"));
			jMenuFile.add(getJMenuItemFileOpen());
			jMenuFile.add(getJMenuItemFileNew());
			jMenuFile.add(getJMenuItemFileSave());
			jMenuFile.add(getJMenuItemSaveAs());
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	/**
	 * This method initializes jMenuHelp	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText(messages.getString("help"));
			jMenuHelp.setHorizontalTextPosition(SwingConstants.LEADING);
			jMenuHelp.setHorizontalAlignment(SwingConstants.LEADING);
			jMenuHelp.add(getJMenuItemInfo());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText(messages.getString("quit"));
			jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangeJFrame.this.dispose();
				}
			});
			jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes jMenuItemFileNew	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileNew() {
		if (jMenuItemFileNew == null) {
			jMenuItemFileNew = new JMenuItem();
			jMenuItemFileNew.setText(messages.getString("new"));
			jMenuItemFileNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onMainFileNew();
				}
			});
			jMenuItemFileNew.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileNew;
	}
	
	/**
	 * This method initializes jMenuItemFileOpen	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileOpen() {
		if (jMenuItemFileOpen == null) {
			jMenuItemFileOpen = new JMenuItem();
	        jMenuItemFileOpen.setText(messages.getString("openFile"));
	        jMenuItemFileOpen.addActionListener(new java.awt.event.ActionListener() {
	        	public void actionPerformed(java.awt.event.ActionEvent e) {
	        		onFileOpenMain();
	        	}
	        });
			jMenuItemFileOpen.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileOpen;
	}

	/**
	 * This method initializes jMenuItemFileSave	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileSave() {
		if (jMenuItemFileSave == null) {
			jMenuItemFileSave = new JMenuItem();
			jMenuItemFileSave.setText(messages.getString("saveFile"));
			jMenuItemFileSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave(canonicalMainFileName);
				}
			});
			jMenuItemFileSave.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileSave;
	}

	/**
	 * This method initializes jMenuItemSaveAs	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemSaveAs() {
		if (jMenuItemSaveAs == null) {
			jMenuItemSaveAs = new JMenuItem();
			jMenuItemSaveAs.setText(messages.getString("saveFileAs"));
			jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave("");
				}
			});
			//jMenuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		}
		return jMenuItemSaveAs;
	}

	/**
	 * This method initializes jMenuPreferences	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuPreferences() {
		if (jMenuPreferences == null) {
			jMenuPreferences = new JMenu();
			jMenuPreferences.setText(messages.getString("preferences"));
			jMenuPreferences.add(getJMenuItemPreferences());
		}
		return jMenuPreferences;
	}

	/**
	 * This method initializes jPanelBufferEditor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBufferEditor() {
		if (jPanelBufferEditor == null) {
			jPanelBufferEditor = new JPanel();
			jPanelBufferEditor.setLayout(new BorderLayout());
			jPanelBufferEditor.add(getJPanelButtons(), BorderLayout.BEFORE_LINE_BEGINS);
			jPanelBufferEditor.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanelBufferEditor;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridheight = 1;
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new GridBagLayout());
			jPanelButtons.setName("jPanelButtons");
			jPanelButtons.add(getJButtonAdd(), gridBagConstraints1);
			jPanelButtons.add(getJButtonDel(), gridBagConstraints11);
			jPanelButtons.add(getJButtonDown(), gridBagConstraints2);
			jPanelButtons.add(getJButtonUp(), gridBagConstraints4);
		}
		return jPanelButtons;
	};

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setName("jScrollPane1");
			jScrollPane1.setBorder(null);
			jScrollPane1.setViewportView(getJList1());
			jScrollPane1.setPreferredSize(new Dimension(0, 0));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setPreferredSize(new Dimension(0, 0));
			jScrollPane2.setName("jScrollPane2");
			jScrollPane2.setViewportView(getJList2());
		}
		return jScrollPane2;
	}
	
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(310);
			jSplitPane.setLeftComponent(getJScrollPane1());
			jSplitPane.setRightComponent(getJTabbedPane());
		}
		return jSplitPane;
	}
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab(messages.getString("PufferEditor"), null, getJPanelBufferEditor(), null);
			jTabbedPane.addTab(messages.getString("Preview"), null, getJPanePreview(), null);
			jTabbedPane.addTab(messages.getString("Preview"), null, getJPanePreview(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
							int index1 = jList1.getSelectedIndex();
							if (index1>-1) {
								try {
									showPreviewJP((PageNode)listContent1.get(index1));
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
					}
				}
			});
			indexOfPreviewPane = jTabbedPane.indexOfComponent(jPanePreview);
		}
		return jTabbedPane;
	};
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		//
		//  Check what language properties files are available.
		//      Make a list of all locals that are supported with a language properties file.
		//      This list is used within the options menu dialoge.
		//
		/*
	    String url = getClass().getResource("/resources").getFile();		
		System.out.println(url);
		File resourcesDir = new File(url); 
	    String fileNames[] = resourcesDir.list(new PropertiesFilenameFilter());
	    for (int i = 0; i < fileNames.length; i++ ){
	    	  String language = "", country = "", variant = "";
		      int l = fileNames[i].indexOf('_');
		      if (l > -1){
		    	  language = fileNames[i].substring(l+1, l+3);
	    		  int c = fileNames[i].indexOf('_', l+3);
	    		  if (c > -1){
	    			  country = fileNames[i].substring(c+1, c+3);
	    			  int v = fileNames[i].indexOf('_', c+3);
	    			  if (v > -1){
	    				  int dot = fileNames[i].indexOf('.', v);
	    				  variant = fileNames[i].substring(v+1, dot);
	    			  }
	    		  }
	    		  //System.out.println(fileNames[i] + " " + language + " " + country + " " + variant);
	    		  localeTable.add(new Locale(language, country, variant));
	    	  }
	    }
	    */
		
	    localeTable.add(new Locale("de"));
	    localeTable.add(new Locale("en"));

	    System.out.println("-- supported locals --");
	    for (int i=0; i < localeTable.size(); i++){
  		  System.out.println(((Locale)localeTable.get(i)).getDisplayName()); 	    	  	    	
	    }
	    
		//
		// Load the properties File if is exists.
		//
		File pfile = new File(propertiesFileName);
		if (pfile.exists()) getProperties();
		
		//
	    //  Load the local properties.
	    //
		messages = ResourceBundle.getBundle("resources/MelangeMessages",MelangeJFrame.locale);
		System.out.println("-- using locale " + messages.getLocale().getDisplayName() + " --\n");
				
		//
		// Initailize the main window.
		//
		this.setJMenuBar(getJJMenuBar());
        this.setContentPane(getJContentPane());
		this.setSize(700, 485);
		this.setLocationRelativeTo(null);
		this.setTitle(projectName);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				MelangeJFrame.this.dispose();
			}
		});	
	}

	/**
	 * This method reads the properties file "melange.rc" located in "user.dir". 	
	 * 	
	 * @return void
	 */
	private void getProperties() {
		String propertyStr = null;
		int propertyInt;
		try {
			Properties melangeProperties = new Properties();
			// Java > java 1.4 we can use a FileReader
			//Reader propInFile = new FileReader(propertiesFileName);
			// for Java == java 1.4 we need a InputStream
			FileInputStream propInFile = new FileInputStream(propertiesFileName);
			melangeProperties.load(propInFile);
			melangeProperties.list(System.out);
			
			// Get the property IconHeight, check limits.
			propertyStr = melangeProperties.getProperty("IconHeight", String.valueOf(iconHeight));
			propertyInt = Integer.parseInt(propertyStr);
			if (propertyInt < 500 || propertyInt > 0) iconHeight = propertyInt;

			// Get the property ImageScalingAlgorithm, check limits.
			propertyStr = melangeProperties.getProperty("ImageScalingAlgorithm", String.valueOf(imageScalingAlgorithm));
			propertyInt = Integer.parseInt(propertyStr);
			if (propertyInt == BufferedImage.SCALE_FAST || 
				propertyInt == BufferedImage.SCALE_SMOOTH ||
				propertyInt == BufferedImage.SCALE_REPLICATE ||
				propertyInt == BufferedImage.SCALE_AREA_AVERAGING) imageScalingAlgorithm = propertyInt;
			
			// Get the property local, check limits.
			String languageStr =melangeProperties.getProperty("LocaleLanguage", locale.getLanguage());
			String countryStr = melangeProperties.getProperty("LocaleCountry", locale.getCountry());
			String variantStr = melangeProperties.getProperty("LocaleVariant", locale.getVariant());
			locale = new Locale(languageStr, countryStr, variantStr);
			
		} catch (NumberFormatException e){
			System.err.println("Incorrect formatting in " + propertiesFileName);
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertiesFileName);
		} catch (IOException e) {
			System.err.println("I/O failed.");
		}
		
	}

	/**
	 * This method writes the properties file "melange.rc" located in "user.dir". 	
	 * 	
	 * @return void
	 */
	public void setProperties() {
		try {
			Properties melangeProperties = new Properties();
			// Set the properties.
			melangeProperties.setProperty("IconHeight", String.valueOf(iconHeight));
			melangeProperties.setProperty("ImageScalingAlgorithm", String.valueOf(imageScalingAlgorithm));
			melangeProperties.setProperty("LocaleLanguage", locale.getLanguage());
			melangeProperties.setProperty("LocaleCountry", locale.getCountry());
			melangeProperties.setProperty("LocaleVariant", locale.getVariant());
			// write the properties to the rc file.
			// Java > java 1.4 we can use a FileWriter
			//Writer propOutFile = new FileWriter(propertiesFileName);
			// for Java == java 1.4 we need a OutputStream
			System.out.println("Saving properties to <" + propertiesFileName + ">");
			FileOutputStream propOutFile = new FileOutputStream(propertiesFileName);
			melangeProperties.store(propOutFile, "jPDFmelage properties file");
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertiesFileName);
		} catch (IOException e) {
			System.err.println("I/O failed.");
		}
		
	}

	private void onFileOpenBuffer(){
		JFileChooser chooser = new JFileChooser(currentDirectoryPath);
	    PDFFilter filter = new PDFFilter();
	    chooser.setFileFilter(filter);
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
	    //chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(MelangeJFrame.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	try {
					canonicalBufferFileName = chooser.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				CreateThumbnailsJP task = new CreateThumbnailsJP(this, canonicalBufferFileName, jList2, listContent2);
				task.start();
	    }
	}

	public void openFileMain(String filename){
		canonicalMainFileName = filename;
		this.setTitle(projectName + ": " + canonicalMainFileName);
		listContent1.clear();
		jList1.removeAll();
		CreateThumbnailsJP task = new CreateThumbnailsJP(this, canonicalMainFileName, jList1, listContent1);
		task.start();
	}

	private void onFileOpenMain(){
		JFileChooser chooser = new JFileChooser(currentDirectoryPath);
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
	    //chooser.setFileFilter(filter);
	    PDFFilter filter = new PDFFilter();
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(MelangeJFrame.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	try {
					canonicalMainFileName = chooser.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				openFileMain(canonicalMainFileName);
	    }
	}

	private void onList1Down(){
		
		//int index1;
		int idx[] = jList1.getSelectedIndices();
		int lastIdx = idx[idx.length-1];
		PageNode node = null;
		if (lastIdx < (listContent1.size()-1)) {
			// listContent: replace the selected element with next element
			for (int i=(idx.length-1); i>=0; i--){
				node = (PageNode) listContent1.get(idx[i]);
				listContent1.set(idx[i], listContent1.get(idx[i]+1));
				listContent1.set(idx[i]+1, node);
			}
			// Update the graphical representation
			for (int i=0; i<idx.length; i++){
				idx[i] = idx[i] + 1;
			}
			jList1.setSelectedIndices(idx);
			//jList1.setSelectedIndex(idx[0] + 1);						
			jList1.ensureIndexIsVisible(lastIdx + 1);
			jList1.updateUI();
		}
	}

	private void onList1Up(){		
		PageNode node = null;
		int idx[] = jList1.getSelectedIndices();
		if (idx[0] > 0) {
			// Labellist: replace each selected element with its preceding element
			for (int i=0; i<idx.length; i++){
				node = (PageNode) listContent1.get(idx[i]);
				listContent1.set(idx[i], listContent1.get(idx[i]-1));
				listContent1.set(idx[i]-1, node);
			}

			// Update the graphical representation
			for (int i=0; i<idx.length; i++){
				idx[i] = idx[i] - 1;
			}
			jList1.setSelectedIndices(idx);
			jList1.ensureIndexIsVisible(idx[0] - 1);
			jList1.updateUI();
		}
	}

	private void onMainFileNew(){
		
		listContent1.clear();
		jList1.removeAll();
		jList1.setListData(listContent1);
		canonicalMainFileName = "";
		this.setTitle(projectName + ": " + canonicalMainFileName);
		//System.out.println("Main list cleared,\nfile name cleared.");
	}

	private void onRotate(int CWorCCW){
		if (jList1.isSelectionEmpty() == false) {

			int[] index1 = jList1.getSelectedIndices();			

			// rotate the selected items of "Main List"
			for (int i=0; i<index1.length; i++) {
				((PageNode)listContent1.get(index1[i])).rotate(CWorCCW);
			}
     		
			// Update the graphical representation
			jList1.ensureIndexIsVisible(index1[0]);
			jList1.updateUI();
			jList1.repaint();

			// Update the preview, if the preview panel is selected in the tabbed pane.
			if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
				try {
					showPreviewJP((PageNode)listContent1.get(index1[0]));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void onFileSave(String fileName){
		boolean saveIt = false;
		
		if (listContent1.isEmpty()) {
			JOptionPane.showMessageDialog(MelangeJFrame.this,
				    					  messages.getString("messageNoContent"),
				    					  messages.getString("warning"),
				    					  JOptionPane.WARNING_MESSAGE);
			return;
		} else if (fileName.length() == 0) {
			JFileChooser chooser = new JFileChooser(currentDirectoryPath);
		    PDFFilter filter = new PDFFilter();
		    chooser.setFileFilter(filter);
		    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
		    //chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(MelangeJFrame.this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	try {
						fileName = chooser.getSelectedFile().getCanonicalPath();
						canonicalMainFileName = fileName;
						this.setTitle(projectName + ": " + fileName);		
					} catch (IOException e) {
						e.printStackTrace();
					}
		    } else
		    	return;
		}
		
		File file = new File(fileName);
		if (file.exists()) {
			int i = JOptionPane.showConfirmDialog(MelangeJFrame.this,
				    							  messages.getString("fileExists") + fileName,
				    							  messages.getString("warning"),
				    							  JOptionPane.YES_NO_OPTION);
			if (i==0) saveIt = true;
		}  else {
			saveIt = true;
		}
		
		try {
			if (saveIt) saveFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void saveFile(String fileName) throws IOException, DocumentException{
		File file =     new File(fileName);
		File tmpfile =  File.createTempFile("Mixer", null, file.getParentFile());
		String bakFileName = fileName.substring(0,fileName.lastIndexOf('.')).concat(".bak");
		File bakfile =  new File(bakFileName);

		// itext usage
		System.out.println("Writing new content to <" + tmpfile.getName() + ">");
		PdfReader reader = null;
		PdfDictionary dict = null;
		Document pdfDoc = new Document();
		PdfCopy writer = new PdfCopy(pdfDoc, new FileOutputStream(tmpfile.getCanonicalPath()));
		pdfDoc.open();
		PageNode node = null;
		for (int i = 0; i < listContent1.size(); i++){
			node = (PageNode)listContent1.get(i);
			reader = new PdfReader(node.filename);
			dict = reader.getPageN(node.pagenumber);
			dict.put(PdfName.ROTATE, new PdfNumber(node.rotation));
			writer.addPage(writer.getImportedPage(reader,node.pagenumber));
			reader.close(); // close input file
			System.out.println("Page " + node.pagenumber + "  File:" + node.filename + "  Rotation:" + node.rotation);
		}
		pdfDoc.close(); // close Helper Class
		writer.close(); // close output file
		
		// save old file to a XXX.bak file
		if (bakfile.exists()) bakfile.delete(); 
		if (file.renameTo(bakfile.getCanonicalFile())) {
			System.out.println("Orginal File is saved in <" + bakfile.getName() + ">");
		}
				
		// move new content to original file name
		file = new File(fileName);
		if (tmpfile.renameTo(file))
		  System.out.println("<" + tmpfile.getName() + "> is copied to <" + file.getName() + "> ");
	}

	/**
	 * This method creates a preview of a pdf page on a jLabel.
	 * It uses the jPedal Renderer org.jpedal.pdfDecoder
	 * @throws Exception 
	 */
	private void showPreviewJP(PageNode node) throws Exception{
		double zoom = 1;

		// GUI stuff
		Cursor cursor = getCursor();
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
		jPanePreview.closePdfFile();
	    jPanePreview.openPdfFile(node.filename);
        
        //get the width and height for the doc at the default zoom 
        Rectangle rectPDF = new Rectangle(0,0,
				                (int)jPanePreview.getPdfPageData().getMediaBoxWidth(node.pagenumber),
				                (int)jPanePreview.getPdfPageData().getMediaBoxHeight(node.pagenumber));
        Rectangle rectPanel = new Rectangle(0,0,
				                (int)jPanePreview.getWidth(),
				                (int)jPanePreview.getHeight());
        
        //System.out.println("PDF Page Width x Height, Rotation: "+ rectPDF.getWidth()+ " x "+ rectPDF.getHeight()+ ", "+ node.rotation);
    	
        // jPedal do not change Width and Hight when the page is rotated !? 
        // To calculate the page ratio we need to turn higth with width when the page is rotated.
        if (node.rotation == 90 || node.rotation == 270){
        	int dummy = rectPDF.width;
        	rectPDF.width = rectPDF.height;
        	rectPDF.height = dummy;
        }
        	
        // calculate the page ratio
    	double ratioPDF = rectPDF.getHeight()/rectPDF.getWidth();
        double ratioPanel = rectPanel.getHeight()/rectPanel.getWidth();
        
        // calculate the decoder zoom that fits with the panel dimensions.  
        if (ratioPDF > ratioPanel)
        	zoom =  rectPanel.getHeight() / rectPDF.getHeight();
        else
        	zoom = rectPanel.getWidth() / rectPDF.getWidth();
	    jPanePreview.setPageParameters((float)zoom, node.pagenumber , node.rotation); 
	    jPanePreview.decodePage(node.pagenumber);
        jPanePreview.invalidate();
	    jPanePreview.repaint();
        //System.out.println("Page " + node.pagenumber + " ratioPDF "+ ratioPDF + " ratioPanel " + ratioPanel + " zoom " + zoom);

	    // GUI stuff, may be bad style - but works - 
		setCursor(cursor);
}

	/**
	 * This method creates a preview of a pdf page on a jLabel.
	 * It uses the Sun Renderer com.sun.pdfview.
	private void showPreviewSR(PageNode node) throws IOException{
        //load a pdf from a byte buffer
        File file = new File(node.file);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);

        // draw the first page to an image
        PDFPage page = pdffile.getPage(node.pagenumber);
        
        //get the width and height for the doc at the default zoom 
        Rectangle rectClip = new Rectangle(0,0,
                (int)page.getBBox().getWidth(),
                (int)page.getBBox().getHeight());
        
        Rectangle rectLabel = new Rectangle(0,0,
                (int)jLabelPreview.getWidth(),
                (int)jLabelPreview.getHeight());
     
        // calculate the page ratio
        double ratioClip = rectClip.getWidth()/rectClip.getHeight();
        double ratioLabel = rectLabel.getWidth()/rectLabel.getHeight();
        if (ratioClip < ratioLabel)
        	rectLabel.width =  (int)(rectLabel.height * ratioClip);
        else
        	rectLabel.height = (int)(rectLabel.width / ratioClip);
        //generate the image
        Image img = page.getImage(
                rectLabel.width, rectLabel.height, //width & height
                rectClip, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true  // block until drawing is done
                );
        
        //show the image in the panel
        jLabelPreview.setIcon(new ImageIcon(img));
	}
	 */

	/**
	 * This method initializes jMenuItemInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemInfo() {
		if (jMenuItemInfo == null) {
			jMenuItemInfo = new JMenuItem();
			jMenuItemInfo.setText("Info");
			jMenuItemInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangeInfoDialog mInfo = new MelangeInfoDialog(MelangeJFrame.this);
					mInfo.setVisible(true);
				}
			});
		}
		return jMenuItemInfo;
	}

	/**
	 * This method initializes jMenuItemClearMain	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemClearMain() {
		if (jMenuItemClearMain == null) {
			jMenuItemClearMain = new JMenuItem();
			jMenuItemClearMain.setText(messages.getString("erasePages"));
			jMenuItemClearMain.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listContent1.clear();
					jList1.removeAll();
					jList1.setListData(listContent1);
					jList1.updateUI();
					//System.out.println("Main list cleared.");
				}
			});
		}
		return jMenuItemClearMain;
	}

	/**
	 * This method initializes jMenuItemClearBuffer	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemClearBuffer() {
		if (jMenuItemClearBuffer == null) {
			jMenuItemClearBuffer = new JMenuItem();
			jMenuItemClearBuffer.setText(messages.getString("clearBuffer"));
			jMenuItemClearBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listContent2.clear();
					jList2.removeAll();
					jList2.setListData(listContent2);
					jList2.updateUI();
					//System.out.println("Buffer list cleared.");
				}
			});
		}
		return jMenuItemClearBuffer;
	}

	/**
	 * This method initializes jPanePreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanePreview() {
		if (jPanePreview == null) {
			jPanePreview = new PdfDecoder();
		}
		return jPanePreview;
	}

	/**
	 * This method initializes jButtonRotateRight	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRotateRight() {
		if (jButtonRotateRight == null) {
			jButtonRotateRight = new JButton();
			jButtonRotateRight.setIcon(new ImageIcon(getClass().getResource("/icons/Redo24.gif")));
			jButtonRotateRight.setEnabled(false);
			jButtonRotateRight.setToolTipText(messages.getString("rotateRight"));
			jButtonRotateRight.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onRotate(DIRECTION.CW);
				}
			});
		}
		return jButtonRotateRight;
	}

	/**
	 * This method initializes jButtonRotateLeft	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRotateLeft() {
		if (jButtonRotateLeft == null) {
			jButtonRotateLeft = new JButton();
			jButtonRotateLeft.setIcon(new ImageIcon(getClass().getResource("/icons/Rotateleft24.gif")));
			jButtonRotateLeft.setEnabled(false);
			jButtonRotateLeft.setToolTipText(messages.getString("rotateLeft"));
			jButtonRotateLeft.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onRotate(DIRECTION.CCW);
				}
			});
		}
		return jButtonRotateLeft;
	}	

	/**
	 * This method initializes jMenuItemFileAddBuffer	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileAddBuffer() {
		if (jMenuItemFileAddBuffer == null) {
			jMenuItemFileAddBuffer = new JMenuItem();
			jMenuItemFileAddBuffer.setText(messages.getString("loadFileToBuffer"));
			jMenuItemFileAddBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenBuffer();
				}
			});
		}
		return jMenuItemFileAddBuffer;
	}

	/**
	 * This method initializes jMenuItemPreferences	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemPreferences() {
		if (jMenuItemPreferences == null) {
			jMenuItemPreferences = new JMenuItem();
			jMenuItemPreferences.setText(messages.getString("options"));
			jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangePreferencesDialog prefDialog = new MelangePreferencesDialog(MelangeJFrame.this);
					prefDialog.setVisible(true);
				}
			});
		}
		return jMenuItemPreferences;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
