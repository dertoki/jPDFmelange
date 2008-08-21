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
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MelangePreferencesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991160732171375540L;
	
	private MelangeJFrame parent;

	private JPanel jContentPane = null;

	private JButton jButtonOK = null;

	private JButton jButtonCancel = null;

	private JTextField jTextFieldIconSize = null;

	private JTextPane jTextPaneIconSize = null;

	private JComboBox jComboBoxImageScale = null;

	private JTextPane jTextPaneImageScale = null;

	private JTextPane jTextPaneLanguage = null;

	private JComboBox jComboBoxLanguage = null;

	private JCheckBox jCheckBoxShowButtonsPanel = null;

	private JTextPane jTextPaneShowButtonsPanel = null;

	/**
	 * @param owner
	 */
	public MelangePreferencesDialog(Frame owner) {
		super(owner);
		this.parent = (MelangeJFrame) owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(395, 234);
		this.setTitle("jPDFmelange " + MelangeJFrame.messages.getString("options"));
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(this.getOwner());
		jTextFieldIconSize.setText(String.valueOf(parent.iconHeight));
		jComboBoxImageScale.addItem("SCALE_FAST");
		jComboBoxImageScale.addItem("SCALE_SMOOTH");
		jComboBoxImageScale.addItem("SCALE_AREA_AVERAGING");
		
		for (int i=0; i < MelangeJFrame.localeTable.size(); i++){
			jComboBoxLanguage.addItem(((Locale)MelangeJFrame.localeTable.get(i)).getDisplayName());
			if (MelangeJFrame.localeTable.get(i).equals(MelangeJFrame.locale)) 
				jComboBoxLanguage.setSelectedIndex(i);
		}
		
	    switch (parent.imageScalingAlgorithm) {
		case BufferedImage.SCALE_FAST:
			jComboBoxImageScale.setSelectedIndex(0);
			break;
		case BufferedImage.SCALE_SMOOTH:
			jComboBoxImageScale.setSelectedIndex(1);
			break;
		case BufferedImage.SCALE_AREA_AVERAGING:
			jComboBoxImageScale.setSelectedIndex(2);
			break;
		default:
			jComboBoxImageScale.setSelectedIndex(1);
			break;
		}
	    
	    jCheckBoxShowButtonsPanel.setSelected(parent.showButtonsPanel);
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
			jContentPane.setBorder(null);
			jContentPane.setBackground(SystemColor.window);
			jContentPane.add(getJButtonOK(), null);
			jContentPane.add(getJButtonCancel(), null);
			jContentPane.add(getJTextFieldIconSize(), null);
			jContentPane.add(getJTextPaneIconSize(), null);
			jContentPane.add(getJComboBoxImageScale(), null);
			jContentPane.add(getJTextPaneImageScale(), null);
			jContentPane.add(getJTextPaneLanguage(), null);
			jContentPane.add(getJComboBoxLanguage(), null);
			jContentPane.add(getJCheckBoxShowButtonsPanel(), null);
			jContentPane.add(getJTextPaneShowButtonsPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(290, 165, 90, 31));
			jButtonOK.setText(MelangeJFrame.messages.getString("buttonSave"));
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						parent.iconHeight = Integer.parseInt(jTextFieldIconSize.getText());
					} catch (NumberFormatException e1) {
						System.err.println("Incorrect formatting.");
					}		
					
					switch (jComboBoxImageScale.getSelectedIndex()) {
					case 0:
						parent.imageScalingAlgorithm = BufferedImage.SCALE_FAST;
						break;
					case 1:
						parent.imageScalingAlgorithm = BufferedImage.SCALE_SMOOTH;
						break;
					case 2:
						parent.imageScalingAlgorithm = BufferedImage.SCALE_AREA_AVERAGING;
						break;
					default:
						parent.imageScalingAlgorithm = BufferedImage.SCALE_SMOOTH;
						break;
					}
					
					MelangeJFrame.locale = (Locale) MelangeJFrame.localeTable.get(jComboBoxLanguage.getSelectedIndex());
					
					if (jCheckBoxShowButtonsPanel.isSelected()){
						parent.showButtonsPanel = true;
					} else {
						parent.showButtonsPanel = false;
					}
					parent.jCheckBoxMenuItemShowMoveButtons.setSelected(parent.showButtonsPanel);
					parent.jPanelButtons.setVisible(parent.showButtonsPanel);
					
					MelangePreferencesDialog.this.setVisible(false);
					MelangePreferencesDialog.this.dispose();
					//
					// Save to changed properties.
					//
					((MelangeJFrame)MelangePreferencesDialog.this.getOwner()).setProperties();
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
			jButtonCancel.setBounds(new Rectangle(190, 165, 90, 31));
			jButtonCancel.setText(MelangeJFrame.messages.getString("buttonCancel"));
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangePreferencesDialog.this.setVisible(false);
					MelangePreferencesDialog.this.dispose();
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jTextFieldIconSize	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldIconSize() {
		if (jTextFieldIconSize == null) {
			jTextFieldIconSize = new JTextField();
			jTextFieldIconSize.setBounds(new Rectangle(195, 15, 61, 25));
			jTextFieldIconSize.setHorizontalAlignment(JTextField.LEADING);
			jTextFieldIconSize.setBackground(SystemColor.text);
			jTextFieldIconSize.setEditable(true);
		}
		return jTextFieldIconSize;
	}

	/**
	 * This method initializes jTextPaneIconSize	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneIconSize() {
		if (jTextPaneIconSize == null) {
			jTextPaneIconSize = new JTextPane();
			jTextPaneIconSize.setEditable(false);
			jTextPaneIconSize.setBackground(SystemColor.window);
			jTextPaneIconSize.setPreferredSize(new Dimension(169, 25));
			jTextPaneIconSize.setLocation(new Point(15, 15));
			jTextPaneIconSize.setSize(new Dimension(169, 25));
			jTextPaneIconSize.setText(MelangeJFrame.messages.getString("iconSize"));
		}
		return jTextPaneIconSize;
	}

	/**
	 * This method initializes jComboBoxImageScale	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxImageScale() {
		if (jComboBoxImageScale == null) {
			jComboBoxImageScale = new JComboBox();
			jComboBoxImageScale.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
			jComboBoxImageScale.setBackground(SystemColor.text);
			jComboBoxImageScale.setPreferredSize(new Dimension(181, 25));
			jComboBoxImageScale.setLocation(new Point(195, 45));
			jComboBoxImageScale.setSize(new Dimension(181, 25));
			jComboBoxImageScale.setEditable(false);
		}
		return jComboBoxImageScale;
	}

	/**
	 * This method initializes jTextPaneImageScale	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneImageScale() {
		if (jTextPaneImageScale == null) {
			jTextPaneImageScale = new JTextPane();
			jTextPaneImageScale.setEditable(false);
			jTextPaneImageScale.setBackground(SystemColor.window);
			jTextPaneImageScale.setPreferredSize(new Dimension(169, 25));
			jTextPaneImageScale.setLocation(new Point(15, 45));
			jTextPaneImageScale.setSize(new Dimension(169, 25));
			jTextPaneImageScale.setText(MelangeJFrame.messages.getString("imageScalingAlgorithm"));
		}
		return jTextPaneImageScale;
	}

	/**
	 * This method initializes jTextPaneLanguage	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneLanguage() {
		if (jTextPaneLanguage == null) {
			jTextPaneLanguage = new JTextPane();
			jTextPaneLanguage.setText(MelangeJFrame.messages.getString("language"));
			jTextPaneLanguage.setEditable(false);
			jTextPaneLanguage.setEnabled(true);
			jTextPaneLanguage.setLocation(new Point(15, 75));
			jTextPaneLanguage.setSize(new Dimension(86, 25));
			jTextPaneLanguage.setPreferredSize(new Dimension(82, 25));
			jTextPaneLanguage.setBackground(null);
		}
		return jTextPaneLanguage;
	}

	/**
	 * This method initializes jComboBoxLanguage	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxLanguage() {
		if (jComboBoxLanguage == null) {
			jComboBoxLanguage = new JComboBox();
			jComboBoxLanguage.setBackground(SystemColor.text);
			jComboBoxLanguage.setEditable(false);
			jComboBoxLanguage.setPreferredSize(new Dimension(181, 25));
			jComboBoxLanguage.setLocation(new Point(195, 75));
			jComboBoxLanguage.setSize(new Dimension(181, 25));
			jComboBoxLanguage.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		}
		return jComboBoxLanguage;
	}

	/**
	 * This method initializes jCheckBoxShowButtonsPanel	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxShowButtonsPanel() {
		if (jCheckBoxShowButtonsPanel == null) {
			jCheckBoxShowButtonsPanel = new JCheckBox();
			jCheckBoxShowButtonsPanel.setLocation(new Point(195, 105));
			jCheckBoxShowButtonsPanel.setBackground(SystemColor.window);
			jCheckBoxShowButtonsPanel.setPreferredSize(new Dimension(200, 25));
			jCheckBoxShowButtonsPanel.setSize(new Dimension(30, 25));
		}
		return jCheckBoxShowButtonsPanel;
	}

	/**
	 * This method initializes jTextPaneShowButtonsPanel	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneShowButtonsPanel() {
		if (jTextPaneShowButtonsPanel == null) {
			jTextPaneShowButtonsPanel = new JTextPane();
			jTextPaneShowButtonsPanel.setEnabled(true);
			jTextPaneShowButtonsPanel.setPreferredSize(new Dimension(82, 25));
			jTextPaneShowButtonsPanel.setText(MelangeJFrame.messages.getString("ShowButtonsPanel"));
			jTextPaneShowButtonsPanel.setEditable(false);
			jTextPaneShowButtonsPanel.setLocation(new Point(15, 105));
			jTextPaneShowButtonsPanel.setSize(new Dimension(180, 25));
			jTextPaneShowButtonsPanel.setBackground(null);
		}
		return jTextPaneShowButtonsPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
