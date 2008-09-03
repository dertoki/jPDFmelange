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
import javax.swing.filechooser.FileFilter;


/** 
 *  Class to filter files with pdf extension, used for file dialog.
 */
public class PDFFilter extends FileFilter {

	/** 
	 *  Check if files have the extension ".pdf" or ".PDF".
	 *  
	 *	@param file file to check.
	 *	@return <code>true</code> when extension is supported, <code>false</code> if not supported.
	 */
	public boolean accept(File file) {
	       if (file.isDirectory()) {
	            return true;
	       }

	       if (file.getName().toLowerCase().endsWith( ".pdf" ))
	    	   return true;
	       else 
	    	   return false;
	}

	/** 
	 *  Returns a description of the filter.
	 *  
	 *	@return String describing the filter.
	 */
	public String getDescription() {
		return "Portable Document Format, PDF";
	}	   
}

///** 
// *  Class to filter properties files.
// */
//class PropertiesFilenameFilter implements FilenameFilter 
//{ 
//  public boolean accept( File f, String s ) 
//  { 
//	  boolean isLocalePropertyFileName = false;
//	  if (s.toLowerCase().endsWith( ".properties" ) || s.toLowerCase().startsWith("MelangeMessages"))
//		  isLocalePropertyFileName = true;
//	  else 
//		  isLocalePropertyFileName = false;
//    return isLocalePropertyFileName;
//  } 
//} 
 

