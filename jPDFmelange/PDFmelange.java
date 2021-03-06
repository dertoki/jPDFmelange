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

import java.io.File;

/**
 * A simple starter.
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class PDFmelange {

	/**
	 * @param argv file to open.
	 */
	public static void main(String argv[]) {
		
		MelangeJFrame mFrame;
		mFrame = new MelangeJFrame();
	    mFrame.setVisible(true);
	    
		if (argv.length == 1){
			File file = new File(argv[0]);
			if (file.exists()) mFrame.openFileMain(argv[0]);
		}
	}

}
