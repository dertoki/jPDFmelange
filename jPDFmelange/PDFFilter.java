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
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;


public class PDFFilter extends FileFilter {

	public boolean accept(File arg0) {
	       if (arg0.isDirectory()) {
	            return true;
	        }

	        String extension = getExtension(arg0);
	        if (extension != null) {
	            if (extension.equals("pdf") ||
	                extension.equals("PDF")) {
	                    return true;
	            } else {
	                return false;
	            }
	        }

		return false;
	}

	public String getDescription() {
		return "Portable Document Format, PDF";
	}
	   
	public static String getExtension(File arg0) {
	        String ext = null;
	        String s = arg0.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        
	        return ext;
    }

}

class PropertiesFilenameFilter implements FilenameFilter 
{ 
  public boolean accept( File f, String s ) 
  { 
	  boolean isLocalePropertyFileName = false;
	  if (s.toLowerCase().endsWith( ".properties" ) || s.toLowerCase().startsWith("MelangeMessages"))
		  isLocalePropertyFileName = true;
	  else 
		  isLocalePropertyFileName = false;
    return isLocalePropertyFileName;
  } 
} 
 

