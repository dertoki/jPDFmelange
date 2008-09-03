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

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * This class represents a list of Icons with some attributs of pdf pages.
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class PDFjList extends JList {

	private static final long serialVersionUID = 2267977915187909116L;


	/**
	 *  Creates a list object with a renderer for Icons.
	 */
	public PDFjList() {
		super();
		PDFListCellRenderer renderer = new PDFListCellRenderer();
		setCellRenderer(renderer);
	}
	
	/**
	 *  Removes all selected elements in the list.
	 */
	public void deleteSelected(){
		if (!isSelectionEmpty()) {
			DefaultListModel model = (DefaultListModel) getModel();
			int idx[] = getSelectedIndices();		

			for (int i=(idx.length-1); i>=0; i--) {
				model.remove(idx[i]);
			}
		}		
	}

	/**
	 * This method moves content of current List with a specified index 
	 *   to the target List at specified index.   	
	 * 
	 * @param sourceIdx index in source list
	 * @param targetList target list
	 * @param targetIdx index in target list
	 * @return int: new index of List A
	 */
	public int move(int sourceIdx, PDFjList targetList, int targetIdx){
		DefaultListModel source = (DefaultListModel) getModel();
		DefaultListModel target = (DefaultListModel) targetList.getModel();
		// append or insert the selection to the content of list2 
		if (targetIdx == target.size()) {
			target.addElement(source.get(sourceIdx));
		} else {
			target.add(targetIdx, source.get(sourceIdx));
		}
		// remove selection of content in list1
		source.remove(sourceIdx);
		if (sourceIdx == source.size()) sourceIdx--;
		return sourceIdx;
	}

	/**
	 * Execute a rotation on a list element.
	 * 
	 * @param idx index of element to be rotated.
	 * @param CWorCCW clockwise or counterclockwise. 
	 *         This should be a value of [{@link DIRECTION#CW}, {@link DIRECTION#CCW}].
	 */
	public void rotate(int idx, int CWorCCW){
		DefaultListModel model = (DefaultListModel) getModel();
		PageNode page = (PageNode) model.get(idx);
		//PageNode rotatedPage = (PageNode) page.clone();
		//rotatedPage.rotate(CWorCCW);
		model.remove(idx);
		page.rotate(CWorCCW);
		model.add(idx, page);
	}
	
	/**
	 * Display an icon and a string for each object in the list.
	 */
	class PDFListCellRenderer extends JLabel implements ListCellRenderer {
	    
		private static final long serialVersionUID = 6632253181619407517L;

		public PDFListCellRenderer() {
	        setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		/**
		 * This method finds the image and text corresponding to the selected value
		 * and returns the label, set up to display the text and image.
		 */
		public Component getListCellRendererComponent(JList list, 
													  Object value,
													  int index, 
													  boolean isSelected, 
													  boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			String sPage = ((JLabel)value).getText();
			setIcon(((JLabel)value).getIcon());
			if (value != null) {
				setText(sPage);
				setFont(list.getFont());
			} else {
				setText(sPage + " (no image available)");
			}
			return this;
		}
	}
}

