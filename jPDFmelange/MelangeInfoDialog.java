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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MelangeInfoDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392801504056306623L;
	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelInfo = null;
	private JScrollPane jScrollPaneGPL = null;
	private JTextArea jTextAreaGPL = null;
	private JTextArea jTextAreaLibraryInfo = null;
	private JPanel jPanelAuthors = null;
	private JTextArea jTextArea1 = null;
	private JPanel jPanel = null;
	/**
	 * @param owner
	 */
	public MelangeInfoDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		ResourceBundle message = MelangeJFrame.messages;
		this.setTitle(message.getString("about") + " " + MelangeJFrame.projectName);
		this.setBounds(new Rectangle(0, 0, 537, 376));
		this.setLocationRelativeTo(this.getOwner());
		jTextAreaLibraryInfo.append(MelangeJFrame.projectName + " " +
				                    MelangeJFrame.projectVersion + "\n" +
				                    "http://jpdfmelange.berlios.de" + "\n\n");
		jTextAreaLibraryInfo.append(message.getString("MelageInfo")+ "\n\n");
		jTextAreaLibraryInfo.append("Using " +
				                    System.getProperty("os.name") + " " + 
				                    System.getProperty("os.version") + " " + 
				                    System.getProperty("os.arch") + "\n");
		jTextAreaLibraryInfo.append("Using Java " + 
				                    System.getProperty("java.version") + " " + 
				                    System.getProperty("java.vendor") + "\n");
		jTextAreaLibraryInfo.append("Using library jPedal " + org.jpedal.PdfDecoder.version + "\n");
		jTextAreaLibraryInfo.append("Using library " + com.lowagie.text.Document.getVersion() + "\n");
		jTextAreaLibraryInfo.append("\nUsing locale " + message.getLocale().getDisplayName() + "\n");
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(2);
			borderLayout.setVgap(2);
			jContentPane = new JPanel();
			jContentPane.setBackground(SystemColor.window);
			jContentPane.setLayout(borderLayout);
			jContentPane.setPreferredSize(new Dimension(400, 200));
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "OK");
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), "OK");
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "OK");
			jContentPane.getActionMap().put("OK", new javax.swing.AbstractAction() {
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					MelangeInfoDialog.this.setVisible(false);
					MelangeInfoDialog.this.dispose();
	            }
	        });
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("OK");
			jButton.setHorizontalAlignment(SwingConstants.CENTER);
			jButton.setPreferredSize(new Dimension(60, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangeInfoDialog.this.setVisible(false);
					MelangeInfoDialog.this.dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBackground(SystemColor.window);
			jTabbedPane.addTab("Info", null, getJPanelInfo(), null);
			jTabbedPane.addTab("Authors", null, getJPanelAuthors(), null);
			jTabbedPane.addTab("Licence", null, getJScrollPaneGPL(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanelInfo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelInfo() {
		if (jPanelInfo == null) {
			jPanelInfo = new JPanel();
			jPanelInfo.setLayout(new BorderLayout());
			jPanelInfo.setBackground(SystemColor.window);
			jPanelInfo.add(getJTextAreaLibraryInfo(), BorderLayout.CENTER);
		}
		return jPanelInfo;
	}

	/**
	 * This method initializes jScrollPaneGPL	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneGPL() {
		if (jScrollPaneGPL == null) {
			jScrollPaneGPL = new JScrollPane();
			jScrollPaneGPL.setBackground(SystemColor.window);
			jScrollPaneGPL.setViewportView(getJTextAreaGPL());
			InputStream in = getClass().getResourceAsStream("/resources/gpl-2.0.txt");
			InputStreamReader reader = new InputStreamReader(in);
			try {
				jTextAreaGPL.read(reader, null);
			} catch (IOException e) {
				// TODO Automatisch erstellter Catch-Block
				e.printStackTrace();
			}
		}
		return jScrollPaneGPL;
	}

	/**
	 * This method initializes jTextAreaGPL	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaGPL() {
		if (jTextAreaGPL == null) {
			jTextAreaGPL = new JTextArea();
			jTextAreaGPL.setBackground(SystemColor.window);
			jTextAreaGPL.setEditable(false);
		}
		return jTextAreaGPL;
	}

	/**
	 * This method initializes jTextAreaLibraryInfo	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaLibraryInfo() {
		if (jTextAreaLibraryInfo == null) {
			jTextAreaLibraryInfo = new JTextArea();
			jTextAreaLibraryInfo.setBackground(SystemColor.window);
			jTextAreaLibraryInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextAreaLibraryInfo.setEditable(false);
		}
		return jTextAreaLibraryInfo;
	}

	/**
	 * This method initializes jPanelAuthors	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelAuthors() {
		if (jPanelAuthors == null) {
			jPanelAuthors = new JPanel();
			jPanelAuthors.setLayout(null);
			jPanelAuthors.setBackground(SystemColor.window);
			jPanelAuthors.add(getJTextArea1(), null);
		}
		return jPanelAuthors;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setBackground(SystemColor.window);
			jTextArea1.setBounds(new Rectangle(15, 15, 400, 15));
			jTextArea1.setEditable(false);
			jTextArea1.setText("Author: Tobias Tandetzki <toki104400 at users.berlios.de>");
		}
		return jTextArea1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBackground(SystemColor.window);
			jPanel.add(getJButton(), BorderLayout.EAST);
		}
		return jPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
