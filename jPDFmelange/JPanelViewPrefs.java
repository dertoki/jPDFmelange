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

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ResourceBundle;

public class JPanelViewPrefs extends JPanel {

	/**
	 * A panel with PDF preferences used in MelangeJFrame and MelangePreferencesDialog.
	 * 
	 * @author tobias tandetzki 12.07.2009
	 */
	private static final long serialVersionUID = 1L;
	private ResourceBundle messages = null;
	public JTextPane jTextPanePageLayout = null;
	public JTextPane jTextPanePageMode = null;
	public JComboBox jComboBoxPageLayout = null;
	public JComboBox jComboBoxPageMode = null;
	public JCheckBox jCheckBoxHideToolbar = null;
	public JCheckBox jCheckBoxHideMenubar = null;
	public JCheckBox jCheckBoxHideWindowUI = null;
	public JCheckBox jCheckBoxFitWindow = null;
	public JCheckBox jCheckBoxCenterWindow = null;
	public JCheckBox jCheckBoxDisplayDocTitle = null;

	/**
	 * This method initializes 
	 * 
	 */
	public JPanelViewPrefs() {
		super();
		initialize();
		set();
	}
	
	/**
	 * This method set the values of elements
	 * 
	 */
	public void set() {
		
		for (String element: MelangeJFrame.PageLayout.keySet()){
			jComboBoxPageLayout.addItem(getMessages().getString(element));
		}
		String pref = getMessages().getString(MelangeJFrame.prefPageLayout);
		jComboBoxPageLayout.setSelectedItem((Object)pref);
		
		for (String element: MelangeJFrame.PageMode.keySet()){
			jComboBoxPageMode.addItem(getMessages().getString(element));
		}
		pref = getMessages().getString(MelangeJFrame.prefPageMode);
		jComboBoxPageMode.setSelectedItem((Object)pref);		
	}

	/**
	 * This method returns the PageMode as non locale 
	 *  
	 * @return String
	 */
	public String getKeyPageMode(){
		String localkey = (String) jComboBoxPageMode.getSelectedItem();
		for (String key: MelangeJFrame.PageMode.keySet())
			if (getMessages().getString(key).equals(localkey)){
				return key;
		}		
		return null;
	}
	
	/**
	 * This method returns the PageLayout as non locale 
	 *  
	 * @return String
	 */
	public String getKeyPageLayout(){
		String localkey = (String) jComboBoxPageLayout.getSelectedItem();
		for (String key: MelangeJFrame.PageLayout.keySet())
			if (getMessages().getString(key).equals(localkey)){
				return key;
		}		
		return null;
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(null);
		this.setBounds(new Rectangle(10, 170, 361, 261));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.add(getJTextPanePageMode(), null);
		this.add(getJTextPanePageLayout(), null);
		this.add(getJComboBoxPageLayout(), null);
		this.add(getJComboBoxPageMode(), null);
		this.add(getJCheckBoxHideToolbar(), null);
		this.add(getJCheckBoxHideMenubar(), null);
		this.add(getJCheckBoxHideWindowUI(), null);
		this.add(getJCheckBoxFitWindow(), null);
		this.add(getJCheckBoxCenterWindow(), null);
		this.add(getJCheckBoxDisplayDocTitle(), null);
	}

	/**
	 * This method initializes jTextPanePageLayout	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPanePageLayout() {
		if (jTextPanePageLayout == null) {
			String string = getMessages().getString("PageLayout");
			jTextPanePageLayout = new JTextPane();
			jTextPanePageLayout.setBounds(new Rectangle(10, 40, 101, 21));
			jTextPanePageLayout.setForeground(Color.GRAY);
			jTextPanePageLayout.setPreferredSize(new Dimension(70, 21));
			jTextPanePageLayout.setEditable(false);
			jTextPanePageLayout.setBackground(new Color(238, 238, 238));
			jTextPanePageLayout.setText(string);
		}
		return jTextPanePageLayout;
	}

	/**
	 * This method initializes jTextPanePageMode	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPanePageMode() {
		if (jTextPanePageMode == null) {
			jTextPanePageMode = new JTextPane();
			jTextPanePageMode.setBounds(new Rectangle(10, 10, 101, 21));
			jTextPanePageMode.setForeground(Color.GRAY);
			jTextPanePageMode.setText(getMessages().getString("PageMode"));
			jTextPanePageMode.setBackground(new Color(238, 238, 238));
			jTextPanePageMode.setEditable(false);
		}
		return jTextPanePageMode;
	}
	/**
	 * This method initializes jComboBoxPageLayout	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxPageLayout() {
		if (jComboBoxPageLayout == null) {
			jComboBoxPageLayout = new JComboBox();
			jComboBoxPageLayout.setBounds(new Rectangle(120, 40, 231, 24));
			jComboBoxPageLayout.setBackground(Color.white);
			jComboBoxPageLayout.setEnabled(false);
		}
		return jComboBoxPageLayout;
	}

	/**
	 * This method initializes jComboBoxPageMode	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxPageMode() {
		if (jComboBoxPageMode == null) {
			jComboBoxPageMode = new JComboBox();
			jComboBoxPageMode.setBounds(new Rectangle(120, 10, 231, 24));
			jComboBoxPageMode.setBackground(Color.white);
			jComboBoxPageMode.setSelectedItem(getMessages().getString(MelangeJFrame.prefPageMode));
			jComboBoxPageMode.setEnabled(false);
		}
		return jComboBoxPageMode;
	}
	
	/**
	 * This method initializes jCheckBoxHideToolbar	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxHideToolbar() {
		if (jCheckBoxHideToolbar == null) {
			jCheckBoxHideToolbar = new JCheckBox();
			jCheckBoxHideToolbar.setBounds(new Rectangle(10, 80, 341, 21));
			jCheckBoxHideToolbar.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxHideToolbar.setText(getMessages().getString("Page_HIDETOOLBAR"));
			jCheckBoxHideToolbar.setEnabled(false);
			jCheckBoxHideToolbar.setSelected(MelangeJFrame.prefHideToolbar);
		}
		return jCheckBoxHideToolbar;
	}
	/**
	 * This method initializes jCheckBoxHideMenubar	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxHideMenubar() {
		if (jCheckBoxHideMenubar == null) {
			jCheckBoxHideMenubar = new JCheckBox();
			jCheckBoxHideMenubar.setBounds(new Rectangle(10, 110, 341, 21));
			jCheckBoxHideMenubar.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxHideMenubar.setText(getMessages().getString("Page_HIDEMENUBAR"));
			jCheckBoxHideMenubar.setEnabled(false);
			jCheckBoxHideMenubar.setSelected(MelangeJFrame.prefHideMenubar);
		}
		return jCheckBoxHideMenubar;
	}

	/**
	 * This method initializes jCheckBoxHideWindowUI	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxHideWindowUI() {
		if (jCheckBoxHideWindowUI == null) {
			jCheckBoxHideWindowUI = new JCheckBox();
			jCheckBoxHideWindowUI.setBounds(new Rectangle(10, 140, 341, 21));
			jCheckBoxHideWindowUI.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxHideWindowUI.setText(getMessages().getString("Page_HIDEWINDOWUI"));
			jCheckBoxHideWindowUI.setEnabled(false);
			jCheckBoxHideWindowUI.setSelected(MelangeJFrame.prefHideWindowUI);
		}
		return jCheckBoxHideWindowUI;
	}

	/**
	 * This method initializes jCheckBoxFitWindow	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxFitWindow() {
		if (jCheckBoxFitWindow == null) {
			jCheckBoxFitWindow = new JCheckBox();
			jCheckBoxFitWindow.setBounds(new Rectangle(10, 170, 341, 21));
			jCheckBoxFitWindow.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxFitWindow.setText(getMessages().getString("Page_FITWINDOW"));
			jCheckBoxFitWindow.setEnabled(false);
			jCheckBoxFitWindow.setSelected(MelangeJFrame.prefFitWindow);
		}
		return jCheckBoxFitWindow;
	}

	/**
	 * This method initializes jCheckBoxCenterWindow	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxCenterWindow() {
		if (jCheckBoxCenterWindow == null) {
			jCheckBoxCenterWindow = new JCheckBox();
			jCheckBoxCenterWindow.setBounds(new Rectangle(10, 200, 341, 21));
			jCheckBoxCenterWindow.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxCenterWindow.setText(getMessages().getString("Page_CENTERWINDOW"));
			jCheckBoxCenterWindow.setEnabled(false);
			jCheckBoxCenterWindow.setSelected(MelangeJFrame.prefCenterWindow);
		}
		return jCheckBoxCenterWindow;
	}

	/**
	 * This method initializes jCheckBoxDisplayDocTitle	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxDisplayDocTitle() {
		if (jCheckBoxDisplayDocTitle == null) {
			jCheckBoxDisplayDocTitle = new JCheckBox();
			jCheckBoxDisplayDocTitle.setBounds(new Rectangle(10, 230, 341, 21));
			jCheckBoxDisplayDocTitle.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBoxDisplayDocTitle.setEnabled(false);
			jCheckBoxDisplayDocTitle.setSelected(MelangeJFrame.prefDisplayDocTitle);
			jCheckBoxDisplayDocTitle.setText(getMessages().getString("Page_DISPLAYDOCTITLE"));
			jCheckBoxDisplayDocTitle.setVisible(false);
		}
		return jCheckBoxDisplayDocTitle;
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
	 * @return void	
	 */
	public void setEnablePDFViewerPrefs(boolean isenabled) {
		if (isenabled){
			for (int i=0; i<this.getComponentCount();i++){
				this.getComponent(i).setEnabled(true);
				this.getComponent(i).setForeground(Color.BLACK);
			};
		} else {
			for (int i=0; i<this.getComponentCount();i++){
				this.getComponent(i).setEnabled(false);
				this.getComponent(i).setForeground(Color.GRAY);
			}
		}
	}

	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
