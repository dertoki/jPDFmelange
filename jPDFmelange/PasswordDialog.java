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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * A simple input dialog to get the pdf  password. 
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class PasswordDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPasswordField jPasswordField = null;

	private JButton jButtonOK = null;

	private JButton jButtonCancel = null;

	private JTextArea jTextArea = null;

	public String password = "";	
	
	public boolean isCanceled = true;

	private String filename;
	
	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setLocation(new Point(15, 45));
			jPasswordField.setSize(new Dimension(331, 25));
		}
		return jPasswordField;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(255, 90, 91, 31));
			jButtonOK.setText(MelangeJFrame.messages.getString("buttonOK"));
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onOK();
				}
			});
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(150, 90, 91, 31));
			jButtonCancel.setText(MelangeJFrame.messages.getString("buttonCancel"));
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onCancel();
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBackground(SystemColor.window);
			jTextArea.setLocation(new Point(15, 15));
			jTextArea.setSize(new Dimension(331, 25));
			jTextArea.setEditable(false);
			jTextArea.setText(MelangeJFrame.messages.getString("enterPassword") + " <" + filename + ">");
		}
		return jTextArea;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PasswordDialog myPassDialog = new PasswordDialog(null, "noname.pdf");
		myPassDialog.setVisible(true);
		System.out.println("Password: " + myPassDialog.password);
	}

	/**
	 * @param owner
	 */
	public PasswordDialog(Frame owner, String filename) {
		super(owner);
		this.filename = filename;
		initialize();
		setLocationRelativeTo(this.getOwner());
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setModal(true);
		this.setSize(365, 159);
		this.setTitle("Password Dialog");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(SystemColor.window);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJButtonOK(), null);
			jContentPane.add(getJButtonCancel(), null);
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "OK");
			jContentPane.getActionMap().put("OK", new javax.swing.AbstractAction() {
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					onOK();
	            }
	        });
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "Cancel");
			jContentPane.getActionMap().put("Cancel", new javax.swing.AbstractAction() {
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					onCancel();
	            }
	        });
		}
		return jContentPane;
	}

	/** 
	 *  Wrapper for positive dialog closing.
	 */
	private void onOK(){
		isCanceled = false;
		password = String.valueOf(jPasswordField.getPassword());
		for (int i = 0; i<jPasswordField.getPassword().length; i++)
			jPasswordField.getPassword()[i] = 0;
		PasswordDialog.this.setVisible(false);
		PasswordDialog.this.dispose();						
	}
	
	/** 
	 *  Wrapper for negative dialog closing.
	 */
	private void onCancel(){
		isCanceled = true;
		password = "";
		for (int i = 0; i<jPasswordField.getPassword().length; i++)
			jPasswordField.getPassword()[i] = 0;
		PasswordDialog.this.setVisible(false);
		PasswordDialog.this.dispose();						
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
