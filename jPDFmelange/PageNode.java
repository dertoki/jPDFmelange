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

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class extends JLabel with some attributes of PDF pages.
 */
public class PageNode extends JLabel {

	private static final long serialVersionUID = 1624968555506269609L;
	String filename = ""; // canonicalfilename
	String password = null;
	int pagenumber; // pagenumber 1 = Page 1
	int rotation;   // 0, 90, 180, 270, 360
	//int format;     // A4, A3, A2, A1, A0, ...

	/** 
	 *  Store the password.
	 *   
	 *	@param password password.
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/** 
	 *  Store the canonical filename.
	 *   
	 *	@param name canonical filename.
	 */
	public void setFilename(String name){
		this.filename = name;
	}
	
	/** 
	 *  Store the pagenumber.
	 *   
	 *	@param i pagenumber
	 */
	public void setPagenumber(int i){
		this.pagenumber = i;
	}
	
	/** 
	 *  Store the page rotation.
	 *   
	 *	@param iRot page rotation. This should be a value of [0, 90, 180, 270, 360].
	 */
	public void setRotation(int iRot){
		this.rotation = iRot;
	}
	
//	/** 
//	 *  Store the page format (A0, A1, A2, A3, A4, ...). 
//	 *   
//	 *	@param iForm page format.
//	 */
//	public void setFormat(int iForm){
//		this.format = iForm;
//	}

	/** 
	 *  Execute a page rotation of the embedded image icon.
	 *   
	 *	@param CWorCCW clockwise or counterclockwise. 
	 *         This should be a value of [{@link DIRECTION#CW}, {@link DIRECTION#CCW}].
	 */
	public void rotate(int CWorCCW){

		// Labellist: rotate element
		ImageIcon imgIcon = (ImageIcon) this.getIcon();
		BufferedImage bimage = (BufferedImage) imgIcon.getImage();
        switch (CWorCCW){
	        case DIRECTION.CCW:
	    		//System.out.println("CCW rotation initiated.");
				imgIcon = new ImageIcon(rotate90CCW(bimage));
				if (rotation == 0) 
					rotation = 270;
				else 
					rotation = rotation - 90;
				break;
	        case DIRECTION.CW:
	    		//System.out.println("CW rotation initiated.");
				imgIcon = new ImageIcon(rotate90CW(bimage));
				rotation = (rotation + 90) % 360;
				break;
	        default: 
	            throw new IllegalArgumentException( "Unknown Operator!" ); 
        }
		
   		// Change the node.
   		this.setIcon(imgIcon);
   		this.setRotation(rotation);
	}
	
	/** 
	 *  Execute a counterclockwise page rotation on a {@link java.awt.image.BufferedImage}.
	 *   
	 *	@param bi input image.
	 *  @return output image.
	 */
	private BufferedImage rotate90CCW(BufferedImage bi)
	{
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		BufferedImage biRot = new BufferedImage(height, width, bi.getType());
		
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				biRot.setRGB(j, width-1-i, bi.getRGB(i, j));
		
		return biRot;
	}

	/** 
	 *  Execute a clockwise page rotation on a {@link java.awt.image.BufferedImage}.
	 *   
	 *	@param bi input image.
	 *  @return output image.
	 */
	private BufferedImage rotate90CW(BufferedImage bi)
	{
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		BufferedImage biRot = new BufferedImage(height, width, bi.getType());
		
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				biRot.setRGB(height-1-j, i, bi.getRGB(i, j));
		
		return biRot;
	}
}

/**
 * This class indicates the direction of rotating.
 *    This is used in a switch statement.
 */
class DIRECTION { 
	/** counterclockwise */
	public static final int CCW = -90;
	/** clockwise */
	public static final int CW 	= +90;
}