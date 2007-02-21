/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package net.sourceforge.jeuclid.app.mathviewer;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import net.sourceforge.jeuclid.app.MathViewer;
import net.sourceforge.jeuclid.swing.JMathComponent;
import net.sourceforge.jeuclid.util.Converter;
import net.sourceforge.jeuclid.util.MathMLParserSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * Main frame for the MathViewer application.
 * 
 * @author Max Berger
 */
public class MainFrame extends JFrame {

    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(MathViewer.class);

    private static final long serialVersionUID = 1L;

    private static final float FONT_SIZE_MULTIPLICATOR = 1.20f;

    private JPanel jContentPane;

    private JMenuBar jJMenuBar;

    private JMenu fileMenu;

    private JMenu helpMenu;

    private JMenuItem exitMenuItem;

    private JMenuItem aboutMenuItem;

    private JMenuItem openMenuItem;

    private JDialog aboutDialog;

    private JPanel aboutContentPane;

    private JLabel aboutVersionLabel;

    private JScrollPane scrollPane;

    private JMathComponent mathComponent;

    private File lastPath;

    private JMenu viewMenu;

    private JMenuItem biggerMenuItem;

    private JMenuItem smallerMenuItem;

    private JMenuItem exportMenuItem;

    /**
     * This is the default constructor.
     */
    public MainFrame() {
        super();
        this.initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setJMenuBar(this.getJJMenuBar());
        this.setSize(300, 200);
        this.setContentPane(this.getJContentPane());
        this.setTitle(Messages.getString("MathViewer.windowTitle")); //$NON-NLS-1$
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (this.jContentPane == null) {
            this.jContentPane = new JPanel();
            this.jContentPane.setLayout(new BorderLayout());
            this.jContentPane.add(this.getScrollPane(), BorderLayout.CENTER);
        }
        return this.jContentPane;
    }

    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar() {
        if (this.jJMenuBar == null) {
            this.jJMenuBar = new JMenuBar();
            this.jJMenuBar.add(this.getFileMenu());
            this.jJMenuBar.add(this.getViewMenu());
            if (!MathViewer.OSX) {
                // This will need to be changed once the Help menu contains
                // more that just the About item.
                this.jJMenuBar.add(this.getHelpMenu());
            }
        }
        return this.jJMenuBar;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getFileMenu() {
        if (this.fileMenu == null) {
            this.fileMenu = new JMenu();
            this.fileMenu.setText(Messages.getString("MathViewer.FileMenu")); //$NON-NLS-1$
            this.fileMenu.add(this.getOpenMenuItem());
            this.fileMenu.add(this.getExportMenuItem());
            if (!MathViewer.OSX) {
                this.fileMenu.add(this.getExitMenuItem());
            }
        }
        return this.fileMenu;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getHelpMenu() {
        if (this.helpMenu == null) {
            this.helpMenu = new JMenu();
            this.helpMenu.setText(Messages.getString("MathViewer.helpMenu")); //$NON-NLS-1$
            // If there are more items, please modify getJJMenuBar to always
            // display the help menu and this function to not display about on
            // OS X
            this.helpMenu.add(this.getAboutMenuItem());
        }
        return this.helpMenu;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getExitMenuItem() {
        if (this.exitMenuItem == null) {
            this.exitMenuItem = new JMenuItem();
            this.exitMenuItem.setText(Messages.getString("MathViewer.exit")); //$NON-NLS-1$
            this.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_Q, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.exitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return this.exitMenuItem;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getAboutMenuItem() {
        if (this.aboutMenuItem == null) {
            this.aboutMenuItem = new JMenuItem();
            this.aboutMenuItem.setText(Messages
                    .getString("MathViewer.aboutMenuItem")); //$NON-NLS-1$
            this.aboutMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    final JDialog aDialog = MainFrame.this.getAboutDialog();
                    aDialog.pack();
                    final Point loc = MainFrame.this.getLocation();
                    loc.translate(20, 20);
                    aDialog.setLocation(loc);
                    aDialog.setVisible(true);
                }
            });
        }
        return this.aboutMenuItem;
    }

    /**
     * This method initializes aboutDialog
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getAboutDialog() {
        if (this.aboutDialog == null) {
            this.aboutDialog = new JDialog(this, true);
            this.aboutDialog.setTitle(Messages
                    .getString("MathViewer.aboutWindowTitle")); //$NON-NLS-1$
            this.aboutDialog.setContentPane(this.getAboutContentPane());
        }
        return this.aboutDialog;
    }

    /**
     * This method initializes aboutContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getAboutContentPane() {
        if (this.aboutContentPane == null) {
            this.aboutContentPane = new JPanel();
            this.aboutContentPane.setLayout(new BorderLayout());
            this.aboutContentPane.add(this.getAboutVersionLabel(),
                    BorderLayout.CENTER);
        }
        return this.aboutContentPane;
    }

    /**
     * This method initializes aboutVersionLabel
     * 
     * @return javax.swing.JLabel
     */
    private JLabel getAboutVersionLabel() {
        if (this.aboutVersionLabel == null) {
            // TODO: There should be much more information.
            this.aboutVersionLabel = new JLabel();
            this.aboutVersionLabel.setText(Messages
                    .getString("MathViewer.aboutContent")); //$NON-NLS-1$
            this.aboutVersionLabel
                    .setHorizontalAlignment(SwingConstants.CENTER);
        }
        return this.aboutVersionLabel;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getOpenMenuItem() {
        if (this.openMenuItem == null) {
            this.openMenuItem = new JMenuItem();
            this.openMenuItem.setText(Messages.getString("MathViewer.open")); //$NON-NLS-1$
            this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_O, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.openMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            MainFrame.this.openFile();
                        }
                    });
        }
        return this.openMenuItem;
    }

    /**
     * carries out the actual file-open procedure.
     */
    protected void openFile() {
        // Have to use AWT file chooser for Mac-friendlyness
        final FileDialog chooser = new FileDialog(this,
                "Please select a MathML file");
        if (this.lastPath != null) {
            chooser.setDirectory(this.lastPath.toString());
        }
        chooser.setVisible(true);
        final String fileName = chooser.getFile();
        if (fileName != null) {
            final File selectedFile = new File(chooser.getDirectory(),
                    fileName);
            this.lastPath = selectedFile.getParentFile();
            try {
                this.getMathComponent().setDocument(
                        MathMLParserSupport.parseFile(selectedFile));
            } catch (final SAXException e) {
                MainFrame.LOGGER.warn(e.getMessage(), e);
                JOptionPane
                        .showMessageDialog(
                                this,
                                e.getMessage(),
                                Messages.getString("MathViewer.errorParsing"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            } catch (final IOException e) {
                MainFrame.LOGGER.warn(e.getMessage(), e);
                JOptionPane
                        .showMessageDialog(
                                this,
                                e.getMessage(),
                                Messages
                                        .getString("MathViewer.errorAccessing"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }
        }
    }

    /**
     * This method initializes scrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getScrollPane() {
        if (this.scrollPane == null) {
            this.scrollPane = new JScrollPane();
            this.scrollPane.setViewportView(this.getMathComponent());

            if (MathViewer.OSX) {
                this.scrollPane
                        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                this.scrollPane
                        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            }
        }
        return this.scrollPane;
    }

    /**
     * This method initializes mathComponent
     * 
     * @return net.sourceforge.jeuclid.swing.JMathComponent
     */
    private JMathComponent getMathComponent() {
        if (this.mathComponent == null) {
            this.mathComponent = new JMathComponent();
            this.mathComponent
                    .setContent("<math><mtext>" //$NON-NLS-1$
                            + Messages.getString("MathViewer.noFileLoaded") + "</mtext></math>"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return this.mathComponent;
    }

    /**
     * This method initializes viewMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getViewMenu() {
        if (this.viewMenu == null) {
            this.viewMenu = new JMenu();
            this.viewMenu.setText(Messages.getString("MathViewer.viewMenu")); //$NON-NLS-1$
            this.viewMenu.add(this.getBiggerMenuItem());
            this.viewMenu.add(this.getSmallerMenuItem());
        }
        return this.viewMenu;
    }

    /**
     * This method initializes biggerMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getBiggerMenuItem() {
        if (this.biggerMenuItem == null) {
            this.biggerMenuItem = new JMenuItem();
            this.biggerMenuItem.setText(Messages
                    .getString("MathViewer.textBigger")); //$NON-NLS-1$
            this.biggerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_ADD, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.biggerMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            final JMathComponent jmc = MainFrame.this
                                    .getMathComponent();
                            jmc.setFontSize(jmc.getFontSize()
                                    * MainFrame.FONT_SIZE_MULTIPLICATOR);
                        }
                    });
        }
        return this.biggerMenuItem;
    }

    /**
     * This method initializes smallerMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getSmallerMenuItem() {
        if (this.smallerMenuItem == null) {
            this.smallerMenuItem = new JMenuItem();
            this.smallerMenuItem.setText(Messages
                    .getString("MathViewer.textSmaller")); //$NON-NLS-1$
            this.smallerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_SUBTRACT, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.smallerMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            final JMathComponent jmc = MainFrame.this
                                    .getMathComponent();
                            jmc.setFontSize(jmc.getFontSize()
                                    / MainFrame.FONT_SIZE_MULTIPLICATOR);
                        }
                    });
        }
        return this.smallerMenuItem;
    }

    /**
     * This method initializes exportMenuItem.
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getExportMenuItem() {
        if (this.exportMenuItem == null) {
            this.exportMenuItem = new JMenuItem();
            this.exportMenuItem.setText(Messages
                    .getString("MathViewer.export")); //$NON-NLS-1$
            this.exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.exportMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            MainFrame.this.exportFile();
                        }
                    });
        }
        return this.exportMenuItem;
    }

    /**
     * Carries out the actual export File operation.
     */
    protected void exportFile() {
        // Have to use AWT file chooser for Mac-friendlyness
        final FileDialog chooser = new FileDialog(this, "Export to...",
                FileDialog.SAVE);
        if (this.lastPath != null) {
            chooser.setDirectory(this.lastPath.toString());
        }
        chooser.setVisible(true);
        final String fileName = chooser.getFile();
        if (fileName != null) {
            final File selectedFile = new File(chooser.getDirectory(),
                    fileName);
            this.lastPath = selectedFile.getParentFile();

            MainFrame.LOGGER.info(selectedFile);

            int doIt = JOptionPane.YES_OPTION;

            if (selectedFile.exists()) {
                doIt = JOptionPane.showConfirmDialog(this, "File " + fileName
                        + " already exists. Overwrite?", "Confirm Overwrite",
                        JOptionPane.YES_NO_OPTION);
            }

            if (doIt == JOptionPane.YES_OPTION) {
                final String extension = fileName.substring(fileName
                        .lastIndexOf('.') + 1);
                final String mimetype = Converter
                        .getMimeTypeForSuffix(extension);
                try {
                    Converter.convert(this.getMathComponent().getDocument(),
                            selectedFile, mimetype);
                } catch (final IOException e) {
                    MainFrame.LOGGER.warn(e);
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Error during export", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
                }
            }
        }
    }

}
