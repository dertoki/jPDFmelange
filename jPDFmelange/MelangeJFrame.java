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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfSecurityException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNumber;
import com.lowagie.text.pdf.PdfReader;

public class MelangeJFrame extends JFrame {

	/**
	 *  Projekct Main Class
	 */
	private static final long serialVersionUID = 4042464615276354878L;
	
	public static final String projectName = "jPDFmelange";
	public static final String projectVersion = "0.1.9.1";
	public static String propertiesFileName = System.getProperty("user.dir").concat(System.getProperty("file.separator")).concat("melange.rc");
	String bufferfile = "";
	String infileName  = "";
	String outfile = "";
	String currentDirectoryPath = "";
	public static Locale locale = Locale.getDefault();
	public static ArrayList localeTable = new ArrayList();

	// Properties set in propertiesFile
    static int iconHeight = 70; // in pixels
    static int imageScalingAlgorithm = BufferedImage.SCALE_SMOOTH;

	// GUI Elemets
	protected Object frame;
	private JButton jButtonAdd = null;
	private JButton jButtonDel = null;
	private JButton jButtonDown = null;
	private JButton jButtonDownToolbar = null;
	private JButton jButtonFileAddBuffer = null;
	private JButton jButtonFileNew = null;
	private JButton jButtonFileOpen = null;
	private JButton jButtonFileSave = null;
	private JButton jButtonUp = null;
	private JButton jButtonUpToolbar = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;

	private JToolBar jJToolBarBar = null;
	private PDFjList jList1 = null;
    private PDFjList jList2 = null; 
	private JMenu jMenuEdit = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemFileNew = null;
	private JMenuItem jMenuItemFileOpen = null;
	private JMenuItem jMenuItemFileSave = null;
	private JMenuItem jMenuItemSaveAs = null;
	private JMenu jMenuPreferences = null;
	private JPanel jPanelBufferEditor = null;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JSplitPane jSplitPane = null;
	private JTabbedPane jTabbedPane = null;
/*
 * Declaration for Java 5 (Java SE 1.5 or higher).  
 * The Sun Renderer com.sun.pdfview needs Java 5 (Java SE 1.5 or higher).
 * 
	private Vector<JLabel> listContent1 = new Vector<JLabel>();  //  @jve:decl-index=0:
	private Vector<JLabel> listContent2 = new Vector<JLabel>();  //  @jve:decl-index=0:
	private ArrayList<PageNode> pageList1 = new ArrayList<PageNode>();  //  @jve:decl-index=0:
	private ArrayList<PageNode> pageList2 = new ArrayList<PageNode>();  //  @jve:decl-index=0:
*/
	private Vector listContent1 = new Vector();  //  @jve:decl-index=0:
	private Vector listContent2 = new Vector();  //  @jve:decl-index=0:
	private ArrayList pageList1 = new ArrayList();  //  @jve:decl-index=0:
	private ArrayList pageList2 = new ArrayList();  //  @jve:decl-index=0:
	int indexOfPreviewPane;
	private JMenuItem jMenuItemInfo = null;
	private JMenuItem jMenuItemClearMain = null;
	private JMenuItem jMenuItemClearBuffer = null;
	public static ResourceBundle messages = null;  //  @jve:decl-index=0:
	//the JPanel/decoder object
	private PdfDecoder jPanePreview = null;
	private PdfDecoder pdfDecoder = null;
	private JButton jButtonRotateRight = null;
	private JButton jButtonRotateLeft = null;
	private JMenuItem jMenuItemFileAddBuffer = null;

	private JMenuItem jMenuItemPreferences = null;
	 
	/**
	 * This is the default constructor
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public MelangeJFrame() {
		super();
		initialize();
		this.setTitle(projectName + ": " + infileName);
		listContent1.clear();
		jList1.removeAll();
		pageList1.clear();
	}

	/* (Kein Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	//@Override
	public void dispose() {
		MelangeJFrame.this.setVisible(false);
		super.dispose();
		System.exit(0);
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setEnabled(false);
			jButtonAdd.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonAdd.setIcon(new ImageIcon(getClass().getResource("/icons/Back24.gif")));
			jButtonAdd.setPreferredSize(new Dimension(30, 30));
			jButtonAdd.setToolTipText(messages.getString("movePage2Source"));
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index1 = 0;
					int index2 = 0;
					if (jList1.isSelectionEmpty() == true){
						index1 = jList1.getModel().getSize();
					} else {
						index1 = jList1.getSelectedIndex() + 1;
					}
					//System.out.println("index1 " + index1);

					if (jList2.isSelectionEmpty() == false) {
						index2 = jList2.getSelectedIndex();
						// modify Labellist and Pagelist:
						if (index1 == listContent1.size()) {
							listContent1.add(listContent2.get(index2));
							pageList1.add(pageList2.get(index2));
						} else {
							listContent1.add(index1, listContent2.get(index2));
							pageList1.add(index1, pageList2.get(index2));
						}
						listContent2.remove(index2);
						pageList2.remove(index2);
						if (index2 == listContent2.size()) index2--;
						// I don't know why this is necessaray, 
						//   but jList1 and jList2 doesn't realize the changes without this ".setListData"
						jList1.setListData(listContent1); 
						jList2.setListData(listContent2);  
						// Update the graphical representation
						jList1.setSelectedIndex(index1);						
						jList1.ensureIndexIsVisible(index1);
						jList2.setSelectedIndex(index2);						
						jList2.ensureIndexIsVisible(index2);
					}
					jList1.repaint();
					jList2.repaint();
				}
			});
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDel() {
		if (jButtonDel == null) {
			jButtonDel = new JButton();
			jButtonDel.setEnabled(false);
			jButtonDel.setIcon(new ImageIcon(getClass().getResource("/icons/Forward24.gif")));
			jButtonDel.setPreferredSize(new Dimension(30, 30));
			jButtonDel.setToolTipText(messages.getString("movePage2Buffer"));
			jButtonDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index1 = 0;
					int index2 = 0;
					if (jList2.isSelectionEmpty() == true){
						index2 = jList2.getModel().getSize();
					} else {
						index2 = jList2.getSelectedIndex() + 1;
					}
					//System.out.println("index2 " + index2);

					if (jList1.isSelectionEmpty() == false) {
						index1 = jList1.getSelectedIndex();
						// modify Labellist and Pagelist:
						if (index2 == listContent2.size()) {
							listContent2.add(listContent1.get(index1));
							pageList2.add(pageList1.get(index1));
						} else {
							listContent2.add(index2, listContent1.get(index1));
							pageList2.add(index2, pageList1.get(index1));
						}
						listContent1.remove(index1);
						pageList1.remove(index1);
						if (index1 == listContent1.size()) index1--;
						// I don't know why this is necessaray, 
						//   but jList1 and jList2 doesn't realize the changes without this ".setListData"
						jList1.setListData(listContent1); 
						jList2.setListData(listContent2);  
						// Update the graphical representation
						jList1.setSelectedIndex(index1);						
						jList1.ensureIndexIsVisible(index1);
						jList2.setSelectedIndex(index2);						
						jList2.ensureIndexIsVisible(index2);
					}
					jList1.repaint();
					jList2.repaint();
				}
			});
		}
		return jButtonDel;
	}

	/**
	 * This method initializes jButtonDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton();
			jButtonDown.setEnabled(false);
			jButtonDown.setIcon(new ImageIcon(getClass().getResource("/icons/Down24.gif")));
			jButtonDown.setPreferredSize(new Dimension(30, 30));
			jButtonDown.setToolTipText(messages.getString("movePageDown"));
			jButtonDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Down();
				}
			});
		}
		return jButtonDown;
	}

	/**
	 * This method initializes jButtonDownToolbar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDownToolbar() {
		if (jButtonDownToolbar == null) {
			jButtonDownToolbar = new JButton();
			jButtonDownToolbar.setEnabled(false);
			jButtonDownToolbar.setIcon(new ImageIcon(getClass().getResource("/icons/Down24.gif")));
			jButtonDownToolbar.setToolTipText(messages.getString("movePageDown"));
			jButtonDownToolbar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Down();
				}
			});
		}
		return jButtonDownToolbar;
	}
	
	/**
	 * This method initializes jButtonFileAddBuffer	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileAddBuffer() {
		if (jButtonFileAddBuffer == null) {
			jButtonFileAddBuffer = new JButton();
			jButtonFileAddBuffer.setIcon(new ImageIcon(getClass().getResource("/icons/Add24.gif")));
			jButtonFileAddBuffer.setToolTipText(messages.getString("loadFileToBuffer"));
			jButtonFileAddBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenBuffer();
				}
			});
		}
		return jButtonFileAddBuffer;
	}

	/**
	 * This method initializes jButtonFileNew	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileNew() {
		if (jButtonFileNew == null) {
			jButtonFileNew = new JButton();
			jButtonFileNew.setIcon(new ImageIcon(getClass().getResource("/icons/New24.gif")));
			jButtonFileNew.setToolTipText(messages.getString("newFile"));
			jButtonFileNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onMainFileNew();
				}
			});
		}
		return jButtonFileNew;
	}

	/**
	 * This method initializes jButtonFileOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileOpen() {
		if (jButtonFileOpen == null) {
			jButtonFileOpen = new JButton();
			jButtonFileOpen.setIcon(new ImageIcon(getClass().getResource("/icons/Open24.gif")));
			jButtonFileOpen.setToolTipText(messages.getString("openFile"));
			jButtonFileOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenMain();
				}
			});
		}
		return jButtonFileOpen;
	}

	/**
	 * This method initializes jButtonFileSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFileSave() {
		if (jButtonFileSave == null) {
			jButtonFileSave = new JButton();
			jButtonFileSave.setIcon(new ImageIcon(getClass().getResource("/icons/Save24.gif")));
			jButtonFileSave.setToolTipText(messages.getString("saveFile"));
			jButtonFileSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave(infileName);
				}
			});
		}
		return jButtonFileSave;
	}
	
	/**
	 * This method initializes jButtonUp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton();
			jButtonUp.setEnabled(false);
			jButtonUp.setIcon(new ImageIcon(getClass().getResource("/icons/Up24.gif")));
			jButtonUp.setToolTipText(messages.getString("movePageUp"));
			jButtonUp.setPreferredSize(new Dimension(30, 30));
			jButtonUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Up();
				}
			});
		}
		return jButtonUp;
	}

	/**
	 * This method initializes jButtonUpToolbar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonUpToolbar() {
		if (jButtonUpToolbar == null) {
			jButtonUpToolbar = new JButton();
			jButtonUpToolbar.setEnabled(false);
			jButtonUpToolbar.setIcon(new ImageIcon(getClass().getResource("/icons/Up24.gif")));
			jButtonUpToolbar.setToolTipText(messages.getString("movePageUp"));
			jButtonUpToolbar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onList1Up();
				}
			});
		}
		return jButtonUpToolbar;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuEdit());
			jJMenuBar.add(getJMenuPreferences());
			jJMenuBar.add(getJMenuHelp());
			//jJMenuBar.setHelpMenu(jMenuHelp);
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJButtonFileOpen());
			jJToolBarBar.add(getJButtonFileNew());
			jJToolBarBar.add(getJButtonFileSave());
			jJToolBarBar.add(getJButtonFileAddBuffer());
			jJToolBarBar.add(getJButtonUpToolbar());
			jJToolBarBar.add(getJButtonDownToolbar());
			jJToolBarBar.add(getJButtonRotateRight());
			jJToolBarBar.add(getJButtonRotateLeft());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jList1	
	 * 	
	 * @return jPDFpellmell.PDFjList	
	 */
	private PDFjList getJList1() {
		if (jList1 == null) {
			jList1 = new PDFjList();
			jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (jList1.isSelectionEmpty() == true) { 
						jButtonDel.setEnabled(false);
						jButtonUp.setEnabled(false);
						jButtonDown.setEnabled(false);
						jButtonUpToolbar.setEnabled(false);
						jButtonDownToolbar.setEnabled(false);
						jButtonRotateLeft.setEnabled(false);
						jButtonRotateRight.setEnabled(false);
					} else {
						int index1 = jList1.getSelectedIndex();
						jButtonDel.setEnabled(true);
						jButtonRotateLeft.setEnabled(true);
						jButtonRotateRight.setEnabled(true);						
						if (index1 == 0) {
							jButtonUp.setEnabled(false);
							jButtonUpToolbar.setEnabled(false);
						} else {
							jButtonUp.setEnabled(true);
							jButtonUpToolbar.setEnabled(true);
						}
						if (index1 == jList1.getModel().getSize()-1) {
							jButtonDown.setEnabled(false);
							jButtonDownToolbar.setEnabled(false);
						} else {
							jButtonDown.setEnabled(true);
							jButtonDownToolbar.setEnabled(true);
						}
						if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
							try {
								showPreviewJP((PageNode)pageList1.get(index1));
							} catch (Exception e1) {
								// TODO Automatisch erstellter Catch-Block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			jList1.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jList1.setSelectionBackground(new Color(184, 207, 229));
					jList2.setSelectionBackground(new Color(220, 220, 220));
				}
			});
		}
		return jList1;
	}

	/**
	 * This method initializes jList2	
	 * 	
	 * @return jPDFmelange.PDFjList	
	 */
	private PDFjList getJList2() {
		if (jList2 == null) {
			jList2 = new PDFjList();
			jList2.setBackground(new Color(230, 230, 230));
			jList2.setSelectionBackground(new Color(220, 220, 220));
			jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (jList2.isSelectionEmpty() == true) 
						jButtonAdd.setEnabled(false);
					else
						jButtonAdd.setEnabled(true);
				}
			});
			jList2.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jList1.setSelectionBackground(new Color(235, 235, 235));
					jList2.setSelectionBackground(new Color(184, 207, 229));
				}
			});
			
		}
		return jList2;
	}

	/**
	 * This method initializes jMenuEdit	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuEdit() {
		if (jMenuEdit == null) {
			jMenuEdit = new JMenu();
			jMenuEdit.setText(messages.getString("edit"));
			jMenuEdit.add(getJMenuItemClearMain());
			jMenuEdit.add(getJMenuItemClearBuffer());
			jMenuEdit.add(getJMenuItemFileAddBuffer());
		}
		return jMenuEdit;
	}

	/**
	 * This method initializes jMenuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText(messages.getString("file"));
			jMenuFile.add(getJMenuItemFileOpen());
			jMenuFile.add(getJMenuItemFileNew());
			jMenuFile.add(getJMenuItemFileSave());
			jMenuFile.add(getJMenuItemSaveAs());
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	/**
	 * This method initializes jMenuHelp	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText(messages.getString("help"));
			jMenuHelp.setHorizontalTextPosition(SwingConstants.LEADING);
			jMenuHelp.setHorizontalAlignment(SwingConstants.LEADING);
			jMenuHelp.add(getJMenuItemInfo());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText(messages.getString("quit"));
			jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangeJFrame.this.dispose();
				}
			});
			jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes jMenuItemFileNew	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileNew() {
		if (jMenuItemFileNew == null) {
			jMenuItemFileNew = new JMenuItem();
			jMenuItemFileNew.setText(messages.getString("new"));
			jMenuItemFileNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onMainFileNew();
				}
			});
			jMenuItemFileNew.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileNew;
	}
	
	/**
	 * This method initializes jMenuItemFileOpen	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileOpen() {
		if (jMenuItemFileOpen == null) {
			jMenuItemFileOpen = new JMenuItem();
	        jMenuItemFileOpen.setText(messages.getString("openFile"));
	        jMenuItemFileOpen.addActionListener(new java.awt.event.ActionListener() {
	        	public void actionPerformed(java.awt.event.ActionEvent e) {
	        		onFileOpenMain();
	        	}
	        });
			jMenuItemFileOpen.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileOpen;
	}

	/**
	 * This method initializes jMenuItemFileSave	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileSave() {
		if (jMenuItemFileSave == null) {
			jMenuItemFileSave = new JMenuItem();
			jMenuItemFileSave.setText(messages.getString("saveFile"));
			jMenuItemFileSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave(infileName);
				}
			});
			jMenuItemFileSave.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		}
		return jMenuItemFileSave;
	}

	/**
	 * This method initializes jMenuItemSaveAs	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemSaveAs() {
		if (jMenuItemSaveAs == null) {
			jMenuItemSaveAs = new JMenuItem();
			jMenuItemSaveAs.setText(messages.getString("saveFileAs"));
			jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileSave("");
				}
			});
			//jMenuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		}
		return jMenuItemSaveAs;
	}

	/**
	 * This method initializes jMenuPreferences	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuPreferences() {
		if (jMenuPreferences == null) {
			jMenuPreferences = new JMenu();
			jMenuPreferences.setText(messages.getString("preferences"));
			jMenuPreferences.add(getJMenuItemPreferences());
		}
		return jMenuPreferences;
	}

	/**
	 * This method initializes jPanelBufferEditor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBufferEditor() {
		if (jPanelBufferEditor == null) {
			jPanelBufferEditor = new JPanel();
			jPanelBufferEditor.setLayout(new BorderLayout());
			jPanelBufferEditor.add(getJPanelButtons(), BorderLayout.BEFORE_LINE_BEGINS);
			jPanelBufferEditor.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanelBufferEditor;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridheight = 1;
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new GridBagLayout());
			jPanelButtons.setName("jPanelButtons");
			jPanelButtons.add(getJButtonAdd(), gridBagConstraints1);
			jPanelButtons.add(getJButtonDel(), gridBagConstraints11);
			jPanelButtons.add(getJButtonDown(), gridBagConstraints2);
			jPanelButtons.add(getJButtonUp(), gridBagConstraints4);
		}
		return jPanelButtons;
	};

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setName("jScrollPane1");
			jScrollPane1.setBorder(null);
			jScrollPane1.setViewportView(getJList1());
			jScrollPane1.setPreferredSize(new Dimension(0, 0));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setPreferredSize(new Dimension(0, 0));
			jScrollPane2.setName("jScrollPane2");
			jScrollPane2.setViewportView(getJList2());
		}
		return jScrollPane2;
	}
	
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(310);
			jSplitPane.setLeftComponent(getJScrollPane1());
			jSplitPane.setRightComponent(getJTabbedPane());
		}
		return jSplitPane;
	}
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab(messages.getString("PufferEditor"), null, getJPanelBufferEditor(), null);
			jTabbedPane.addTab(messages.getString("Preview"), null, getJPanePreview(), null);
			jTabbedPane.addTab(messages.getString("Preview"), null, getJPanePreview(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
							int index1 = jList1.getSelectedIndex();
							if (index1>-1) {
								try {
									showPreviewJP((PageNode)pageList1.get(index1));
								} catch (Exception e1) {
									// TODO Automatisch erstellter Catch-Block
									e1.printStackTrace();
								}
							}
					}
				}
			});
			indexOfPreviewPane = jTabbedPane.indexOfComponent(jPanePreview);
		}
		return jTabbedPane;
	};
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		//
		//  Check what language properties files are available.
		//      Make a list of all locals that are supported with a language properties file.
		//      This list is used within the options menu dialoge.
		//
		/*
	    String url = getClass().getResource("/resources").getFile();		
		System.out.println(url);
		File resourcesDir = new File(url); 
	    String fileNames[] = resourcesDir.list(new PropertiesFilenameFilter());
	    for (int i = 0; i < fileNames.length; i++ ){
	    	  String language = "", country = "", variant = "";
		      int l = fileNames[i].indexOf('_');
		      if (l > -1){
		    	  language = fileNames[i].substring(l+1, l+3);
	    		  int c = fileNames[i].indexOf('_', l+3);
	    		  if (c > -1){
	    			  country = fileNames[i].substring(c+1, c+3);
	    			  int v = fileNames[i].indexOf('_', c+3);
	    			  if (v > -1){
	    				  int dot = fileNames[i].indexOf('.', v);
	    				  variant = fileNames[i].substring(v+1, dot);
	    			  }
	    		  }
	    		  //System.out.println(fileNames[i] + " " + language + " " + country + " " + variant);
	    		  localeTable.add(new Locale(language, country, variant));
	    	  }
	    }
	    */
		
	    localeTable.add(new Locale("de"));
	    localeTable.add(new Locale("en"));

	    System.out.println("-- supported locals --");
	    for (int i=0; i < localeTable.size(); i++){
  		  System.out.println(((Locale)localeTable.get(i)).getDisplayName()); 	    	  	    	
	    }
	    
		//
		// Load the properties File if is exists.
		//
		File pfile = new File(propertiesFileName);
		if (pfile.exists()) getProperties();
		
		//
	    //  Load the local properties.
	    //
		messages = ResourceBundle.getBundle("resources/MelangeMessages",MelangeJFrame.locale);
		System.out.println("-- using locale " + messages.getLocale().getDisplayName() + " --\n");
				
		//
		// Initailize the main window.
		//
		this.setJMenuBar(getJJMenuBar());
        this.setContentPane(getJContentPane());
		this.setSize(700, 485);
		this.setLocationRelativeTo(null);
		this.setTitle(projectName);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				MelangeJFrame.this.dispose();
			}
		});	
	}

	/**
	 * This method reads the properties file "melange.rc" located in "user.dir". 	
	 * 	
	 * @return void
	 */
	private void getProperties() {
		String propertyStr = null;
		int propertyInt;
		try {
			Properties melangeProperties = new Properties();
			// Java > java 1.4 we can use a FileReader
			//Reader propInFile = new FileReader(propertiesFileName);
			// for Java == java 1.4 we need a InputStream
			FileInputStream propInFile = new FileInputStream(propertiesFileName);
			melangeProperties.load(propInFile);
			melangeProperties.list(System.out);
			
			// Get the property IconHeight, check limits.
			propertyStr = melangeProperties.getProperty("IconHeight", String.valueOf(iconHeight));
			propertyInt = Integer.parseInt(propertyStr);
			if (propertyInt < 500 || propertyInt > 0) iconHeight = propertyInt;

			// Get the property ImageScalingAlgorithm, check limits.
			propertyStr = melangeProperties.getProperty("ImageScalingAlgorithm", String.valueOf(imageScalingAlgorithm));
			propertyInt = Integer.parseInt(propertyStr);
			if (propertyInt == BufferedImage.SCALE_FAST || 
				propertyInt == BufferedImage.SCALE_SMOOTH ||
				propertyInt == BufferedImage.SCALE_REPLICATE ||
				propertyInt == BufferedImage.SCALE_AREA_AVERAGING) imageScalingAlgorithm = propertyInt;
			
			// Get the property local, check limits.
			String languageStr =melangeProperties.getProperty("LocaleLanguage", locale.getLanguage());
			String countryStr = melangeProperties.getProperty("LocaleCountry", locale.getCountry());
			String variantStr = melangeProperties.getProperty("LocaleVariant", locale.getVariant());
			locale = new Locale(languageStr, countryStr, variantStr);
			
		} catch (NumberFormatException e){
			System.err.println("Incorrect formatting in " + propertiesFileName);
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertiesFileName);
		} catch (IOException e) {
			System.err.println("I/O failed.");
		}
		
	}

	/**
	 * This method writes the properties file "melange.rc" located in "user.dir". 	
	 * 	
	 * @return void
	 */
	public void setProperties() {
		try {
			Properties melangeProperties = new Properties();
			// Set the properties.
			melangeProperties.setProperty("IconHeight", String.valueOf(iconHeight));
			melangeProperties.setProperty("ImageScalingAlgorithm", String.valueOf(imageScalingAlgorithm));
			melangeProperties.setProperty("LocaleLanguage", locale.getLanguage());
			melangeProperties.setProperty("LocaleCountry", locale.getCountry());
			melangeProperties.setProperty("LocaleVariant", locale.getVariant());
			// write the properties to the rc file.
			// Java > java 1.4 we can use a FileWriter
			//Writer propOutFile = new FileWriter(propertiesFileName);
			// for Java == java 1.4 we need a OutputStream
			System.out.println("Saving properties to <" + propertiesFileName + ">");
			FileOutputStream propOutFile = new FileOutputStream(propertiesFileName);
			melangeProperties.store(propOutFile, "jPDFmelage properties file");
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertiesFileName);
		} catch (IOException e) {
			System.err.println("I/O failed.");
		}
		
	}

	private void onFileOpenBuffer(){
		JFileChooser chooser = new JFileChooser(currentDirectoryPath);
	    PDFFilter filter = new PDFFilter();
	    chooser.setFileFilter(filter);
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
	    //chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(MelangeJFrame.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	try {
					bufferfile = chooser.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					// TODO Automatisch erstellter Catch-Block
					e.printStackTrace();
				} 
				try {
					getThumbnailsFromFileJP(bufferfile, jList2, listContent2, pageList2);
				} catch (IOException e) {
					// TODO Automatisch erstellter Catch-Block
					e.printStackTrace();
				} catch (PdfSecurityException e) {
					JOptionPane.showMessageDialog(MelangeJFrame.this,
							  messages.getString(e.getLocalizedMessage()),
							  messages.getString("warning"),
							  JOptionPane.WARNING_MESSAGE);
				} catch (Exception e) {
					// TODO Automatisch erstellter Catch-Block
					e.printStackTrace();
				}
	    }
	}

	public void openFileMain(String filename){
		infileName = filename;
		this.setTitle(projectName + ": " + infileName);
		listContent1.clear();
		jList1.removeAll();
		pageList1.clear();
		try {
			getThumbnailsFromFileJP(infileName, jList1, listContent1, pageList1);
		} catch (IOException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		} catch (PdfSecurityException e) {
			JOptionPane.showMessageDialog(MelangeJFrame.this,
					  messages.getString(e.getLocalizedMessage()),
					  messages.getString("warning"),
					  JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}

	private void onFileOpenMain(){
		JFileChooser chooser = new JFileChooser(currentDirectoryPath);
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
	    //chooser.setFileFilter(filter);
	    PDFFilter filter = new PDFFilter();
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(MelangeJFrame.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	try {
					infileName = chooser.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					// TODO Automatisch erstellter Catch-Block
					e.printStackTrace();
				} 
				openFileMain(infileName);
	    }
	}

	private void onList1Down(){
		
		int index1;
		JLabel label;
		PageNode pagenode;
		if (jList1.isSelectionEmpty() == false) {
			index1 = jList1.getSelectedIndex();
			// Labellist: replace the selected element with next element
			label = (JLabel)listContent1.get(index1);
			listContent1.set(index1, listContent1.get(index1 + 1));
			listContent1.set(index1 + 1, label);
			// Pagelist: replace the selected element with next element
			pagenode = (PageNode)pageList1.get(index1);
			pageList1.set(index1, pageList1.get(index1 + 1));
			pageList1.set(index1 + 1, pagenode);
			// Update the graphical representation
			jList1.setSelectedIndex(index1 + 1);						
			jList1.ensureIndexIsVisible(index1 + 1);
			jList1.updateUI();
			//jList1.repaint();
		}
	}

	private void onList1Up(){		
		int index1;
		JLabel label;
		PageNode pagenode;
		if (jList1.isSelectionEmpty() == false) {
			index1 = jList1.getSelectedIndex();
			// Labellist: replace the selected element with previous element
			label = (JLabel)listContent1.get(index1);
			listContent1.set(index1, listContent1.get(index1 - 1));
			listContent1.set(index1 - 1, label);
			// Pagelist: replace the selected element with previous element
			pagenode = (PageNode)pageList1.get(index1);
			pageList1.set(index1, pageList1.get(index1 - 1));
			pageList1.set(index1 - 1, pagenode);
			// Update the graphical representation
			jList1.setSelectedIndex(index1 - 1);						
			jList1.ensureIndexIsVisible(index1 - 1);
			jList1.updateUI();
			//jList1.repaint();
		}
	}

	private void onMainFileNew(){
		
		listContent1.clear();
		jList1.removeAll();
		jList1.setListData(listContent1);
		pageList1.clear();
		infileName = "";
		this.setTitle(projectName + ": " + infileName);
		//System.out.println("Main list cleared,\nfile name cleared.");
	}

	private void onRotate(int CWorCCW){
		int index1;
		if (jList1.isSelectionEmpty() == false) {
			index1 = jList1.getSelectedIndex();
			
			String filename = ((PageNode) pageList1.get(index1)).file;
			// Need the file to get the 'short' filename out of the canonical filename.
			//    Is there a better way to do this?
	   		File file = new File(filename);
	   		int pagenumber = ((PageNode) pageList1.get(index1)).pagenumber;
	   		int rotation = ((PageNode) pageList1.get(index1)).rotation;
	   		
			// Labellist: rotate element
			JLabel jlabel = (JLabel) listContent1.get(index1);
			ImageIcon imgIcon = (ImageIcon) jlabel.getIcon();
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
			
			jlabel = new JLabel("<" +file.getName() + "> " + messages.getString("page") + " " + pagenumber, 
					   			imgIcon, 
					   			SwingConstants.LEFT);
			listContent1.set(index1, jlabel);
			
			// Pagelist: rotate element
       		pageList1.set(index1, new PageNode(filename, pagenumber, rotation));
       		//System.out.println(filename + " Page " + pagenumber + " rotation " + rotation);
     		
			// Update the graphical representation
			jList1.setSelectedIndex(index1);						
			jList1.ensureIndexIsVisible(index1);
			jList1.updateUI();
			jList1.repaint();

			// Update the preview, if the preview panel is selected in the tabbed pane.
			if (indexOfPreviewPane == jTabbedPane.getSelectedIndex()){
				try {
					showPreviewJP((PageNode)pageList1.get(index1));
				} catch (Exception e1) {
					// TODO Automatisch erstellter Catch-Block
					e1.printStackTrace();
				}
			}

		}
	}

	private void onFileSave(String fileName){
		boolean saveIt = false;
		
		if (listContent1.isEmpty()) {
			JOptionPane.showMessageDialog(MelangeJFrame.this,
				    					  messages.getString("messageNoContent"),
				    					  messages.getString("warning"),
				    					  JOptionPane.WARNING_MESSAGE);
			return;
		} else if (fileName.length() == 0) {
			JFileChooser chooser = new JFileChooser(currentDirectoryPath);
		    PDFFilter filter = new PDFFilter();
		    chooser.setFileFilter(filter);
		    //FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
		    //chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(MelangeJFrame.this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	try {
						fileName = chooser.getSelectedFile().getCanonicalPath();
						this.infileName = fileName;
						this.setTitle(projectName + ": " + fileName);		
					} catch (IOException e) {
						// TODO Automatisch erstellter Catch-Block
						e.printStackTrace();
					}
		    } else
		    	return;
		}
		
		File file = new File(fileName);
		if (file.exists()) {
			int i = JOptionPane.showConfirmDialog(MelangeJFrame.this,
				    							  messages.getString("fileExists") + fileName,
				    							  messages.getString("warning"),
				    							  JOptionPane.YES_NO_OPTION);
			if (i==0) saveIt = true;
		}  else {
			saveIt = true;
		}
		
		try {
			if (saveIt) saveFile(fileName);
		} catch (IOException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}

	/**
	 * This method creates the thumbnails from each page of a pdf and saves is to a JList.
	 * It uses the jPedal Renderer org.jpedal.pdfDecoder
	 * @throws Exception 
	 */
	private void getThumbnailsFromFileJP(
						String filename, 
						JList jList,         // the JList object 
						Vector listContent,  // the content of the JList object
						ArrayList pageList)  // a list of pages that we'd like to display in our JList 
	throws Exception{
		
		int rotation;
		pdfDecoder = new PdfDecoder();
	    pdfDecoder.openPdfFile(filename);
	    if (pdfDecoder.isEncrypted()) 
	    	throw new PdfSecurityException("messageEncryptionNotSupported");
	    int nPages = pdfDecoder.getPageCount();
        // i need the file simply to find the last name in the pathname's name sequence.
   		File file = new File(filename);
		currentDirectoryPath = file.getParent();
		
        Cursor cursor = getCursor();
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        int pageWidth = 0;
        final int criticalPageWidth = 300; // in mm
        BufferedImage bufferedImage = null;
        Image scaledImage = null;
        ImageIcon imageIcon = null;
        
        //Runtime r = Runtime.getRuntime();

        for (int ipage = 1; ipage < (nPages+1); ipage++) {
        	//
        	//  getPageAsThubnail is much faster, but this method is depreciated
        	//     and the rendered image isn't transparent.
        	//
           	//bufferedImg = pdfDecoder.getPageAsThumbnail(ipage, iconheight);
        	//
        	
    	    // Get the page width in millimeter.
            pageWidth = Math.round(pdfDecoder.getPdfPageData().getMediaBoxWidth(ipage) * (25.4f/72f));
            // Reduce the page scale for larger page formats.
            //    This reduces the quality but avoid heap space problems.
            if (pageWidth > criticalPageWidth) {
            	pdfDecoder.setPageParameters(0.5f, ipage, -1);
            }
    	    bufferedImage = pdfDecoder.getPageAsTransparentImage(ipage);
    	    // Get a scaled image with defined height, 
    	    //     unfortunately this is not a buffered image. 
    	    if (bufferedImage.getHeight() > bufferedImage.getWidth())
    	    	scaledImage = bufferedImage.getScaledInstance(iconHeight, -1, imageScalingAlgorithm);
    	    else
    	    	scaledImage = bufferedImage.getScaledInstance(-1, iconHeight, imageScalingAlgorithm);
    	    // Create a buffered image with this scaled image.
    	    bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null) , BufferedImage.TYPE_INT_ARGB);
    	    Graphics2D g2 = bufferedImage.createGraphics();
    	    g2.drawImage(scaledImage, 0, 0,null);
    	    // Create a image icon with the created buffered image.
    	    //     (It's not a good idea to create the image icon with the scaled image directly,
    	    //      we will get a heap space error after about 30 thumbnails)
       		imageIcon = new ImageIcon(bufferedImage);
       		// Add a JLable to the list.
        	listContent.add(new JLabel("<" +file.getName() + "> " + messages.getString("page") + " " + ipage, 
       								   imageIcon, 
       								   SwingConstants.LEFT));
        	// Add a node to the page list.
       		rotation = pdfDecoder.getPdfPageData().getRotation(ipage);
       		pageList.add(new PageNode(filename, ipage, rotation));
       		// Print some info to the standard output.
    		System.out.println("Thumbnail of "
    				           + "<" +file.getName() + "> "
    				           + messages.getString("page") 
       				           + " " + ipage
       				           + ", rotation=" + rotation
       				           + ", pixel [" + bufferedImage.getWidth()  + "," + bufferedImage.getHeight() + "]" 
       				           //+ ", free memory=" + r.freeMemory()
       				           );
        }
        pdfDecoder.closePdfFile();
		jList.setListData(listContent);
		setCursor(cursor);
	}
	
	/**
	 * This method creates the thumbnails from each page of a pdf and saves is to a JList.
	 * It uses the Sun Renderer com.sun.pdfview.
	private void getThumbnailsFromFileSR(
						String filename, 
						JList jList, 
						Vector<JLabel> listContent, 
						ArrayList<PageNode> pageList) 
	throws IOException{

		//load a pdf from a byte buffer
        File file = new File(filename);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        int iPages = pdffile.getNumPages();
        ImageIcon image = null;
        Image img = null;
    	PDFPage page = null;

        Cursor cursor = getCursor();
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < iPages; i++) {
	        // draw the first page to an image
        	page = pdffile.getPage(i+1);
			//get the width and height for the doc at the default zoom 
			Rectangle rect = new Rectangle(0,0,
			        (int)page.getBBox().getWidth(),
			        (int)page.getBBox().getHeight());
			
			//generate the image
			img = page.getImage(
			        (int)(rect.width*zoom), (int)(rect.height*zoom), //width & height
			        rect, // clip rect
			        null, // null for the ImageObserver
			        false, // fill background with white
			        true  // block until drawing is done
			        );
	        	
	        //Load the pet images and create an array of indexes.
       		image = new ImageIcon(img);
       		listContent.add(new JLabel(messages.getString("page") + " " + ((Integer)(i+1)).toString(), image, SwingConstants.LEFT));
       		pageList.add(new PageNode(filename, i, 0));
        }
		jList.setListData(listContent);
		raf.close();		
		setCursor(cursor);
	}
	 */

	private void saveFile(String fileName) throws IOException, DocumentException{
		File file =     new File(fileName);
		File tmpfile =  File.createTempFile("Mixer", null, file.getParentFile());
		String bakfilePrefix = file.getName().substring(0, file.getName().lastIndexOf('.'));
		File bakfile =  new File(file.getParent().concat(System.getProperty("file.separator")).concat(bakfilePrefix).concat(".bak"));

		// itext usage
		System.out.println("Writing new content to <" + tmpfile.getName() + ">");
		PdfReader reader = null;
		PdfDictionary dict = null;
		Document pdfDoc = new Document();
		PdfCopy writer = new PdfCopy(pdfDoc, new FileOutputStream(tmpfile.getCanonicalPath()));
		pdfDoc.open();
		PageNode node = null;
		for (int i = 0; i < pageList1.size(); i++){
			node = (PageNode)pageList1.get(i);
			reader = new PdfReader(node.file);
			dict = reader.getPageN(node.pagenumber);
			dict.put(PdfName.ROTATE, new PdfNumber(node.rotation));
			writer.addPage(writer.getImportedPage(reader,node.pagenumber));
			reader.close(); // close input file
			System.out.println("Page " + node.pagenumber + "  File:" + node.file + "  Rotation:" + node.rotation);
		}
		pdfDoc.close(); // close Helper Class
		writer.close(); // close output file
		
		// save old file to a XXX.bak file
		if (bakfile.exists()) bakfile.delete(); 
		if (file.renameTo(bakfile.getCanonicalFile())) {
			System.out.println("Orginal File is saved in <" + bakfile.getName() + ">");
		}
				
		// move new content to original file name
		file = new File(fileName);
		if (tmpfile.renameTo(file))
		  System.out.println("<" + tmpfile.getName() + "> is copied to <" + file.getName() + "> ");
	}

	/**
	 * This method creates a preview of a pdf page on a jLabel.
	 * It uses the jPedal Renderer org.jpedal.pdfDecoder
	 * @throws Exception 
	 */
	private void showPreviewJP(PageNode node) throws Exception{
		double zoom = 1;
		jPanePreview.closePdfFile();
	    jPanePreview.openPdfFile(node.file);
        
        //get the width and height for the doc at the default zoom 
        Rectangle rectPDF = new Rectangle(0,0,
				                (int)jPanePreview.getPdfPageData().getMediaBoxWidth(node.pagenumber),
				                (int)jPanePreview.getPdfPageData().getMediaBoxHeight(node.pagenumber));
        Rectangle rectPanel = new Rectangle(0,0,
				                (int)jPanePreview.getWidth(),
				                (int)jPanePreview.getHeight());
        
        //System.out.println("PDF Page Width x Height, Rotation: "+ rectPDF.getWidth()+ " x "+ rectPDF.getHeight()+ ", "+ node.rotation);
    	
        // jPedal do not change Width and Hight when the page is rotated !? 
        // To calculate the page ratio we need to turn higth with width when the page is rotated.
        if (node.rotation == 90 || node.rotation == 270){
        	int dummy = rectPDF.width;
        	rectPDF.width = rectPDF.height;
        	rectPDF.height = dummy;
        }
        	
        // calculate the page ratio
    	double ratioPDF = rectPDF.getHeight()/rectPDF.getWidth();
        double ratioPanel = rectPanel.getHeight()/rectPanel.getWidth();
        
        // calculate the decoder zoom that fits with the panel dimensions.  
        if (ratioPDF > ratioPanel)
        	zoom =  rectPanel.getHeight() / rectPDF.getHeight();
        else
        	zoom = rectPanel.getWidth() / rectPDF.getWidth();
	    jPanePreview.setPageParameters((float)zoom, node.pagenumber , node.rotation); 
	    jPanePreview.decodePage(node.pagenumber);
        jPanePreview.invalidate();
	    jPanePreview.repaint();
        //System.out.println("Page " + node.pagenumber + " ratioPDF "+ ratioPDF + " ratioPanel " + ratioPanel + " zoom " + zoom);
	}

	/**
	 * This method creates a preview of a pdf page on a jLabel.
	 * It uses the Sun Renderer com.sun.pdfview.
	private void showPreviewSR(PageNode node) throws IOException{
        //load a pdf from a byte buffer
        File file = new File(node.file);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);

        // draw the first page to an image
        PDFPage page = pdffile.getPage(node.pagenumber);
        
        //get the width and height for the doc at the default zoom 
        Rectangle rectClip = new Rectangle(0,0,
                (int)page.getBBox().getWidth(),
                (int)page.getBBox().getHeight());
        
        Rectangle rectLabel = new Rectangle(0,0,
                (int)jLabelPreview.getWidth(),
                (int)jLabelPreview.getHeight());
     
        // calculate the page ratio
        double ratioClip = rectClip.getWidth()/rectClip.getHeight();
        double ratioLabel = rectLabel.getWidth()/rectLabel.getHeight();
        if (ratioClip < ratioLabel)
        	rectLabel.width =  (int)(rectLabel.height * ratioClip);
        else
        	rectLabel.height = (int)(rectLabel.width / ratioClip);
        //generate the image
        Image img = page.getImage(
                rectLabel.width, rectLabel.height, //width & height
                rectClip, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true  // block until drawing is done
                );
        
        //show the image in the panel
        jLabelPreview.setIcon(new ImageIcon(img));
	}
	 */

	/**
	 * This method initializes jMenuItemInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemInfo() {
		if (jMenuItemInfo == null) {
			jMenuItemInfo = new JMenuItem();
			jMenuItemInfo.setText("Info");
			jMenuItemInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangeInfoDialog mInfo = new MelangeInfoDialog(MelangeJFrame.this);
					mInfo.setVisible(true);
				}
			});
		}
		return jMenuItemInfo;
	}

	/**
	 * This method initializes jMenuItemClearMain	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemClearMain() {
		if (jMenuItemClearMain == null) {
			jMenuItemClearMain = new JMenuItem();
			jMenuItemClearMain.setText(messages.getString("erasePages"));
			jMenuItemClearMain.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listContent1.clear();
					jList1.removeAll();
					pageList1.clear();
					jList1.setListData(listContent1);
					jList1.updateUI();
					//System.out.println("Main list cleared.");
				}
			});
		}
		return jMenuItemClearMain;
	}

	/**
	 * This method initializes jMenuItemClearBuffer	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemClearBuffer() {
		if (jMenuItemClearBuffer == null) {
			jMenuItemClearBuffer = new JMenuItem();
			jMenuItemClearBuffer.setText(messages.getString("clearBuffer"));
			jMenuItemClearBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listContent2.clear();
					jList2.removeAll();
					pageList2.clear();
					jList2.setListData(listContent2);
					jList2.updateUI();
					//System.out.println("Buffer list cleared.");
				}
			});
		}
		return jMenuItemClearBuffer;
	}

	/**
	 * This method initializes jPanePreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanePreview() {
		if (jPanePreview == null) {
			jPanePreview = new PdfDecoder();
		}
		return jPanePreview;
	}

	/**
	 * This method initializes jButtonRotateRight	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRotateRight() {
		if (jButtonRotateRight == null) {
			jButtonRotateRight = new JButton();
			jButtonRotateRight.setIcon(new ImageIcon(getClass().getResource("/icons/Redo24.gif")));
			jButtonRotateRight.setEnabled(false);
			jButtonRotateRight.setToolTipText(messages.getString("rotateRight"));
			jButtonRotateRight.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onRotate(DIRECTION.CW);
				}
			});
		}
		return jButtonRotateRight;
	}

	/**
	 * This method initializes jButtonRotateLeft	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRotateLeft() {
		if (jButtonRotateLeft == null) {
			jButtonRotateLeft = new JButton();
			jButtonRotateLeft.setIcon(new ImageIcon(getClass().getResource("/icons/Rotateleft24.gif")));
			jButtonRotateLeft.setEnabled(false);
			jButtonRotateLeft.setToolTipText(messages.getString("rotateLeft"));
			jButtonRotateLeft.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onRotate(DIRECTION.CCW);
				}
			});
		}
		return jButtonRotateLeft;
	}
	
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

	/**
	 * This method initializes jMenuItemFileAddBuffer	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemFileAddBuffer() {
		if (jMenuItemFileAddBuffer == null) {
			jMenuItemFileAddBuffer = new JMenuItem();
			jMenuItemFileAddBuffer.setText(messages.getString("loadFileToBuffer"));
			jMenuItemFileAddBuffer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onFileOpenBuffer();
				}
			});
		}
		return jMenuItemFileAddBuffer;
	}

	/**
	 * This method initializes jMenuItemPreferences	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemPreferences() {
		if (jMenuItemPreferences == null) {
			jMenuItemPreferences = new JMenuItem();
			jMenuItemPreferences.setText(messages.getString("options"));
			jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MelangePreferencesDialog prefDialog = new MelangePreferencesDialog(MelangeJFrame.this);
					prefDialog.setVisible(true);
				}
			});
		}
		return jMenuItemPreferences;
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
