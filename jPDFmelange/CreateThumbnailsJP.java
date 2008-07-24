package jPDFmelange;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfSecurityException;

/**
 * This class creates the thumbnails from each page of a pdf and saves is to a JList.
 * It uses the jPedal Renderer org.jpedal.pdfDecoder
 */


public class CreateThumbnailsJP extends Thread {
	private PdfDecoder pdfDecoder = null;
	String canonicalfilename;
	JList jList; // the JList object
	Vector listContent; // the content of the JList object
	int nPages;
	JFrame parent = null;
	JProgressBar progressBar = null;

	CreateThumbnailsJP(JFrame parent, String filename, JList jList, Vector listContent) 
	{
		this.canonicalfilename = filename;
		this.jList = jList;
		this.listContent = listContent;
		this.parent = parent;
		this.progressBar = new JProgressBar();
		this.progressBar.setStringPainted(true);
		((MelangeJFrame)parent).jToolBar.add(this.progressBar);
		((MelangeJFrame)parent).jToolBar.validate();
	}

	public void run() {
		try {
			get();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PdfSecurityException e) {
			JOptionPane.showMessageDialog(parent,
					  MelangeJFrame.messages.getString(e.getLocalizedMessage()),
					  MelangeJFrame.messages.getString("warning"),
					  JOptionPane.WARNING_MESSAGE);
			((MelangeJFrame)parent).jToolBar.remove(this.progressBar);
			((MelangeJFrame)parent).jToolBar.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void get() throws Exception {
		pdfDecoder = new PdfDecoder();
		pdfDecoder.openPdfFile(canonicalfilename);
		pdfDecoder.setEnableLegacyJPEGConversion(true);
		if (pdfDecoder.isEncrypted())
			throw new PdfSecurityException("messageEncryptionNotSupported");
		nPages = pdfDecoder.getPageCount();

		File file = new File(canonicalfilename);
		MelangeJFrame.currentDirectoryPath = file.getParent();
		String filename = file.getName();

		// GUI stuff
		Cursor cursor = parent.getCursor();
		parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		progressBar.setMaximum(nPages);
		progressBar.setString(filename);
		progressBar.setValue(0);
		progressBar.setVisible(true);

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
				scaledImage = bufferedImage.getScaledInstance(MelangeJFrame.iconHeight, -1, MelangeJFrame.imageScalingAlgorithm);
			else
				scaledImage = bufferedImage.getScaledInstance(-1, MelangeJFrame.iconHeight, MelangeJFrame.imageScalingAlgorithm);
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

			listContent.add(pdfNode);

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
		jList.setListData(listContent);
		
		// GUI stuff, may be bad style - but works - 
		parent.setCursor(cursor);
		progressBar.setVisible(false);
		((MelangeJFrame)parent).jToolBar.remove(this.progressBar);
		((MelangeJFrame)parent).jToolBar.validate();
	}
}
