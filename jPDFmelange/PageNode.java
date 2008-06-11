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

public class PageNode {
	String file;
	int pagenumber; // pagenumber 1 = Page 1
	int rotation;   // 0, 90, 180, 270, 360
	int format;
	/**
	 * 
	 */
	public PageNode(String f, int p) {
		file = f;
		pagenumber = p;
		rotation = 0;
	}
	public PageNode(String f, int p, int r) {
		file = f;
		pagenumber = p;
		rotation = r;
	}
	public PageNode(String f, int p, int r, int a) {
		file = f;
		pagenumber = p;
		rotation = r;
		format = a;
	}
}

class DIRECTION { 
	public static final int CCW = -90;	//!< 'counter clockwise'
	public static final int CW 	= +90;	//!< 'clockwise'
}