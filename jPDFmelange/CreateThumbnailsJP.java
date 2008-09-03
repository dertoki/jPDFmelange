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

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.exception.PdfSecurityException;

/**
 * This class creates the thumbnails from each page of a pdf and saves is to a JList.
 * It uses the jPedal Renderer org.jpedal.pdfDecoder.
 * <p>
 * To start a thread use <code> .start()</code>:
 * <pre>
 *    CreateThumbnailsJP thread = new CreateThumbnailsJP(parent, canonicalfilename, jList);
 *    thread.start();
 * </pre>
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class CreateThumbnailsJP extends Thread {
	private PdfDecoder pdfDecoder = null;
	private String canonicalfilename = null;
	private DefaultListModel listContent = null; // the content of the JList object
	private int nPages = -1;
	private int listOffset = -1;
	private MelangeJFrame parent = null;
	private JProgressBar progressBar = null;
	private String filename = null;
	private boolean insert = false;

	/** 
	 *  Creates a thread object for thumbnail creation.
	 *   
	 *	@param parent GUI element.
	 *	@param canonicalfilename name of the pdf file.
	 *  @param jList where the thumbnails are stored.
	 */
	CreateThumbnailsJP(MelangeJFrame parent, String canonicalfilename, JList jList) 
	{
		this.parent = parent;
		File file = new File(canonicalfilename);
		parent.currentDirectoryPath = file.getParent();
		this.filename = file.getName();

		this.canonicalfilename = canonicalfilename;
		this.listContent = (DefaultListModel) jList.getModel();
		this.listOffset = jList.getModel().getSize();


		progressBar = new JProgressBar();
		parent.jToolBar.add(progressBar);
		parent.jToolBar.validate();
		progressBar.setStringPainted(true);
   		progressBar.setString(filename);
   		progressBar.setVisible(true);
	}

	/**
	 * Set the offset position where pages are inserted in the JList resp. listContent.
	 * 
	 * @param listOffset index in {@link CreateThumbnailsJP#listContent content} of JList where input starts.
	 */
	public void setInsertOffset(int listOffset){
		this.listOffset = listOffset;
		this.insert = true;
	}
	
	/**
	 * Starts execution of the thread.
	 * <p>
	 * Don't use <code>.run()</code> to start the thread.<br>
	 * Use <code>.start()</code> to start the thread, this calls <code>.run()</code> indirect.
	 * <p>
	 * The thread is synchronized to {@link CreateThumbnailsJP#listContent content} of JList, so it is 
	 * guaranteed that only one thread is processing on the same content.
	 * 
	 * See {@linkplain CreateThumbnailsJP#getThumbnails() getThumbnails()} which does the main job.
	 */
	public void run() {
		try {
			synchronized (listContent) {
				getThumbnails();
			}
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (final PdfSecurityException e) {
			System.out.println(MelangeJFrame.messages.getString(e.getLocalizedMessage()));
			progressBar.setVisible(false);
			parent.jToolBar.remove(progressBar);
		} catch (PdfException e) {
			System.out.println("PdfException");
  			JOptionPane.showMessageDialog(parent,
					  "Cannot open PDF file.",
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			progressBar.setVisible(false);
			parent.jToolBar.remove(progressBar);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates the thumbnails from each page of a pdf and saves it to a JList.
	 * It uses the jPedal Renderer org.jpedal.pdfDecoder
	 */
	private void getThumbnails() throws Exception {
		pdfDecoder = new PdfDecoder();
		pdfDecoder.openPdfFile(canonicalfilename);
		pdfDecoder.setEnableLegacyJPEGConversion(true);
		String password = null;
		while (!pdfDecoder.isFileViewable()){
			PasswordDialog pdialog = new PasswordDialog(parent, filename);
			pdialog.setVisible(true);
			if (pdialog.isCanceled)
				throw new PdfSecurityException("messageEncryptedFile");
			pdfDecoder.setEncryptionPassword(pdialog.password);
			password = new String(pdialog.password);
		}
		nPages = pdfDecoder.getPageCount();

		// GUI stuff
		Cursor cursor = parent.getCursor();
		parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
   		progressBar.setMaximum(nPages);
   		progressBar.setValue(0);

		int pageWidth = 0;
		final int criticalPageWidth = 300; // in mm
		BufferedImage bufferedImage = null;
		Image scaledImage = null;
		ImageIcon imageIcon = null;
		PageNode pdfNode = null;

		// Runtime r = Runtime.getRuntime();

		for (int ipage = 1; ipage < (nPages + 1); ipage++) {
			//
			// getPageAsThubnail is much faster, but this method is depreciated
			// and the rendered image isn't transparent.
			//
			// bufferedImg = pdfDecoder.getPageAsThumbnail(ipage, iconheight);
			//

			// Get the page width in millimeter.
			pageWidth = Math.round(pdfDecoder.getPdfPageData().getMediaBoxWidth(ipage) * (25.4f / 72f));
			// Reduce the page scale for larger page formats.
			// This reduces the quality but avoid heap space problems.
			if (pageWidth > criticalPageWidth) {
				pdfDecoder.setPageParameters(0.5f, ipage, -1);
			}
			bufferedImage = pdfDecoder.getPageAsTransparentImage(ipage);
			pdfDecoder.invalidate();
			// Get a scaled image with defined height,
			// unfortunately this is not a buffered image.
			if (bufferedImage.getHeight() > bufferedImage.getWidth())
				scaledImage = bufferedImage.getScaledInstance(parent.iconHeight, -1, parent.imageScalingAlgorithm);
			else
				scaledImage = bufferedImage.getScaledInstance(-1, parent.iconHeight, parent.imageScalingAlgorithm);
			// Create a buffered image with this scaled image.
			bufferedImage = new BufferedImage(scaledImage.getWidth(null),
											  scaledImage.getHeight(null),
											  BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = bufferedImage.createGraphics();
			g2.drawImage(scaledImage, 0, 0, null);
			// Create a image icon with the created buffered image.
			// (It's not a good idea to create the image icon with the
			// scaled image directly,
			// we will get a heap space error after about 30 thumbnails)
			imageIcon = new ImageIcon(bufferedImage);

			// Add a node to the page list.
			pdfNode = new PageNode();
			pdfNode.setText("<" + filename + "> " + MelangeJFrame.messages.getString("page") + " " + ipage);
			pdfNode.setIcon(imageIcon);
			pdfNode.setHorizontalAlignment(SwingConstants.LEFT);
			pdfNode.setFilename(canonicalfilename);
			pdfNode.setPagenumber(ipage);
			pdfNode.setRotation(pdfDecoder.getPdfPageData().getRotation(ipage));
			pdfNode.setPassword(password);

			if (insert)
				listContent.add(listOffset + ipage-1, pdfNode);
			else
				listContent.addElement(pdfNode);

			progressBar.setValue(ipage);
			System.out.println("Thumbnail of " + "<" + filename
					+ "> " + MelangeJFrame.messages.getString("page") + " " + ipage
					+ ", rotation=" + pdfNode.rotation + ", pixel ["
					+ bufferedImage.getWidth() + ","
					+ bufferedImage.getHeight() + "]"
			        // + ", free memory=" + r.freeMemory()
					);
		}
		pdfDecoder.closePdfFile();
		
		// GUI stuff, may be bad style - but works - 
		parent.setCursor(cursor);
   		progressBar.setVisible(false);
		parent.jToolBar.remove(progressBar);
	}
}
