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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PDFjList extends JList {
	/**
	 *   Tan, 16. Feb 2008 
	 */
	private static final long serialVersionUID = 2267977915187909116L;


	/**
	 * Tan, 16. Feb 2008
	 */
	public PDFjList() {
		super();
		// TODO Automatisch erstellter Konstruktoren-Stub
		PDFListCellRenderer renderer = new PDFListCellRenderer();
		setCellRenderer(renderer);
	}

//	 Display an icon and a string for each object in the list.

	class PDFListCellRenderer extends JLabel implements ListCellRenderer {
	    
		/**
		 * Tan, 15. Feb 08
		 */
		private static final long serialVersionUID = 6632253181619407517L;

		public PDFListCellRenderer() {
	        setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		/*
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

			// Set the icon and text. If icon was null, say so.
		    // String pet = ((JLabel)list.getModel().getElementAt(index)).getText();
			// ups, is 'c' like casting really type save? Is this a good java style? 
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

