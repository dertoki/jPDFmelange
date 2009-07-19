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
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.util.ResourceBundle;
import java.lang.String;

/**
 * A dialog to set some preferences.
 * 
 * @author tobias tandetzki 30.08.2008
 */
public class MelangePreferencesDialog extends JDialog {

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

	private JPanelViewPrefs jPanelViewPrefs = null;

	private ResourceBundle messages = null;

	private JCheckBox jCheckBoxEnablePDFViewerPrefs = null;

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
	 */
	private void initialize() {
		this.setSize(395, 507);
		this.setTitle("jPDFmelange " + getMessages().getString("options"));
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
	    jPanelViewPrefs.setEnablePDFViewerPrefs(MelangeJFrame.enablePDFPreferences);
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
			jContentPane.add(getJPanelViewPrefs(), null);
			jContentPane.add(getJCheckBoxEnablePDFViewerPrefs(), null);
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "OK");
			jContentPane.getActionMap().put("OK", new javax.swing.AbstractAction() {
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					onSave();
					MelangePreferencesDialog.this.setVisible(false);
					MelangePreferencesDialog.this.dispose();						
	            }
	        });
			jContentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "Cancel");
			jContentPane.getActionMap().put("Cancel", new javax.swing.AbstractAction() {
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					MelangePreferencesDialog.this.setVisible(false);
					MelangePreferencesDialog.this.dispose();						
	            }
	        });
//			jContentPane.addKeyListener(new java.awt.event.KeyAdapter() {
//				public void keyReleased(java.awt.event.KeyEvent e) {
//					if (e.getKeyCode() == KeyEvent.VK_ENTER){
//						onSave();
//						MelangePreferencesDialog.this.setVisible(false);
//						MelangePreferencesDialog.this.dispose();						
//					} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
//						MelangePreferencesDialog.this.setVisible(false);
//						MelangePreferencesDialog.this.dispose();
//					}
//				}
//			});
//			jContentPane.setFocusable(true);
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
			jButtonOK.setBounds(new Rectangle(290, 440, 90, 31));
			jButtonOK.setText(getMessages().getString("buttonSave"));
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onSave();
					MelangePreferencesDialog.this.setVisible(false);
					MelangePreferencesDialog.this.dispose();
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
			jButtonCancel.setBounds(new Rectangle(190, 440, 90, 31));
			jButtonCancel.setText(getMessages().getString("buttonCancel"));
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
			jTextPaneIconSize.setPreferredSize(new Dimension(169, 25));
			jTextPaneIconSize.setLocation(new Point(15, 15));
			jTextPaneIconSize.setSize(new Dimension(169, 25));
			jTextPaneIconSize.setBackground(new Color(238, 238, 238));
			jTextPaneIconSize.setText(getMessages().getString("iconSize"));
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
			jComboBoxImageScale.setPreferredSize(new Dimension(181, 25));
			jComboBoxImageScale.setLocation(new Point(195, 45));
			jComboBoxImageScale.setSize(new Dimension(181, 25));
			jComboBoxImageScale.setBackground(Color.white);
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
			jTextPaneImageScale.setPreferredSize(new Dimension(169, 25));
			jTextPaneImageScale.setLocation(new Point(15, 45));
			jTextPaneImageScale.setSize(new Dimension(169, 25));
			jTextPaneImageScale.setBackground(new Color(238, 238, 238));
			jTextPaneImageScale.setText(getMessages().getString("imageScalingAlgorithm"));
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
			jTextPaneLanguage.setText(getMessages().getString("language"));
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
			jComboBoxLanguage.setEditable(false);
			jComboBoxLanguage.setPreferredSize(new Dimension(181, 25));
			jComboBoxLanguage.setLocation(new Point(195, 75));
			jComboBoxLanguage.setSize(new Dimension(181, 25));
			jComboBoxLanguage.setBackground(Color.white);
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
			jTextPaneShowButtonsPanel.setText(getMessages().getString("ShowButtonsPanel"));
			jTextPaneShowButtonsPanel.setEditable(false);
			jTextPaneShowButtonsPanel.setLocation(new Point(15, 105));
			jTextPaneShowButtonsPanel.setSize(new Dimension(180, 25));
			jTextPaneShowButtonsPanel.setBackground(null);
		}
		return jTextPaneShowButtonsPanel;
	}
	
	/**
	 * Saves all settings. Use {@link MelangeJFrame#setProperties()} to write to properties file.	
	 */
	private void onSave(){
		
		// save iconHeight
		try {
			parent.iconHeight = Integer.parseInt(jTextFieldIconSize.getText());
		} catch (NumberFormatException e1) {
			System.err.println("Incorrect formatting.");
		}		
		
		// save imageScalingAlgorithm
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
		
		// save local
		MelangeJFrame.locale = (Locale) MelangeJFrame.localeTable.get(jComboBoxLanguage.getSelectedIndex());
		
		// save state of ButtonsPanel
		if (jCheckBoxShowButtonsPanel.isSelected()){
			parent.showButtonsPanel = true;
		} else {
			parent.showButtonsPanel = false;
		}
		parent.jCheckBoxMenuItemShowMoveButtons.setSelected(parent.showButtonsPanel);
		parent.jPanelButtons.setVisible(parent.showButtonsPanel);
		
		// save enablePDFPreferences
		if (jCheckBoxEnablePDFViewerPrefs.isSelected())
			MelangeJFrame.enablePDFPreferences = true;
		else
			MelangeJFrame.enablePDFPreferences = false;
		
		// save PageMode
		MelangeJFrame.prefPageMode = jPanelViewPrefs.getKeyPageMode();
		
		// save PageLayout
		MelangeJFrame.prefPageLayout = jPanelViewPrefs.getKeyPageLayout();
		
		// save prefHideToolbar
		if (jPanelViewPrefs.jCheckBoxHideToolbar.isSelected())
			MelangeJFrame.prefHideToolbar = true;
		else
			MelangeJFrame.prefHideToolbar = false;
		
		// save prefHideMenubar
		if (jPanelViewPrefs.jCheckBoxHideMenubar.isSelected())
			MelangeJFrame.prefHideMenubar = true;
		else
			MelangeJFrame.prefHideMenubar = false;
		
		// save prefHideWindowUI
		if (jPanelViewPrefs.jCheckBoxHideWindowUI.isSelected())
			MelangeJFrame.prefHideWindowUI = true;
		else
			MelangeJFrame.prefHideWindowUI = false;
		
		// save prefFitWindow
		if (jPanelViewPrefs.jCheckBoxFitWindow.isSelected())
			MelangeJFrame.prefFitWindow = true;
		else
			MelangeJFrame.prefFitWindow = false;
		
		// save prefCenterWindow
		if (jPanelViewPrefs.jCheckBoxCenterWindow.isSelected())
			MelangeJFrame.prefCenterWindow = true;
		else
			MelangeJFrame.prefCenterWindow = false;
		
		// save prefDisplayDocTitle
		if (jPanelViewPrefs.jCheckBoxDisplayDocTitle.isSelected())
			MelangeJFrame.prefDisplayDocTitle = true;
		else
			MelangeJFrame.prefDisplayDocTitle = false;
		
		//
		// Save the changed properties.
		//
		((MelangeJFrame)MelangePreferencesDialog.this.getOwner()).setProperties();
	}


	/**
	 * This method initializes jPanelViewPrefs	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelViewPrefs() {
		if (jPanelViewPrefs == null) {
			jPanelViewPrefs = new JPanelViewPrefs();
		}
		return jPanelViewPrefs;
	}

	/**
	 * This method initializes messages	
	 * 	
	 * @return java.util.PropertyResourceBundle	
	 */
	private ResourceBundle getMessages() {
		if (messages == null) {
			messages = ResourceBundle.getBundle("resources/MelangeMessages", MelangeJFrame.locale);
		}
		return messages;
	}

	/**
	 * This method initializes jCheckBoxEnablePDFViewerPrefs	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxEnablePDFViewerPrefs() {
		if (jCheckBoxEnablePDFViewerPrefs == null) {
			String string = getMessages().getString("enablePDFPreferences");
			jCheckBoxEnablePDFViewerPrefs = new JCheckBox();
			jCheckBoxEnablePDFViewerPrefs.setSelected(MelangeJFrame.enablePDFPreferences);
			jCheckBoxEnablePDFViewerPrefs.setBounds(new Rectangle(10, 140, 340, 23));
			jCheckBoxEnablePDFViewerPrefs.setText(string);
			jCheckBoxEnablePDFViewerPrefs.setPreferredSize(new Dimension(340, 23));
			jCheckBoxEnablePDFViewerPrefs.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED){
								jPanelViewPrefs.setEnablePDFViewerPrefs(true);
							} else {
								jPanelViewPrefs.setEnablePDFViewerPrefs(false);
							}
						}
					});
		}
		return jCheckBoxEnablePDFViewerPrefs;
	}



}  //  @jve:decl-index=0:visual-constraint="10,10"
