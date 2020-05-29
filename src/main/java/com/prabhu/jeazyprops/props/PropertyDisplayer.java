/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.props;

import com.prabhu.cmdwindow.ui.Display;
import com.prabhu.cmdwindow.ui.PropertyPanel;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

/**
 * Class for Displaying Options
 *
 * @author prabhu.p
 */
public class PropertyDisplayer extends Display {

    JDialog lJDialog = null;
    PropertyPanel lJPanel;
    JScrollPane jMainScrollPane;
    /**
     * Displays the Properties in a GUI for Editing and Updating
     */
    GridBagConstraints gbc;

    /**
     * creates instance of PropertyDisplayer
     */
    public PropertyDisplayer() {
        lJDialog = new JDialog(lJDialog, Constants.get(Constants.ScreenTitleString));
        try {
            lJDialog.setIconImage(ImageIO.read(ClassLoader.getSystemResource(Constants.AppImageFileName)));
        } catch (IOException ex) {
        }
        lJDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lJDialog.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        lJDialog.setResizable(false);
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 100;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    /**
     * Setting Display to the window
     *
     * @param pJPanel
     */
    public void DisplaySettings(PropertyPanel pJPanel) {
        if (lJPanel != null) {
            jMainScrollPane.remove(lJPanel);
            lJDialog.remove(jMainScrollPane);
            lJDialog.setVisible(false);
        }
        lJPanel = pJPanel;
        lJDialog.add(getJMainScrollPane(), gbc);
        lJDialog.setPreferredSize(new Dimension(pJPanel.getPreferredSize().width + 45, pJPanel.getPreferredSize().height + 54));
        lJDialog.setBounds(new Rectangle(pJPanel.getPreferredSize().width + 45, pJPanel.getPreferredSize().height + 54));
        // lJDialog.setSize(pJPanel.getSize());
        setWindowParameters(lJDialog);
        lJDialog.setVisible(true);
    }

    /**
     * Adds scrollpane to the panel
     *
     * @return
     */
    private JScrollPane getJMainScrollPane() {
        jMainScrollPane = new JScrollPane(lJPanel);
        jMainScrollPane.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return jMainScrollPane;
    }

    /**
     * Sets the window Position
     *
     * @param aThis The Widow container
     */
    public void setWindowParameters(Container aThis) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenSize.getHeight() > aThis.getHeight()) {
            int x = (screenSize.width - aThis.getWidth()) / 2;
            int y = (screenSize.height - aThis.getHeight()) / 2;
            aThis.setBounds(x, y, aThis.getWidth(), aThis.getHeight());
        } else {
            int x = (screenSize.width - aThis.getWidth()) / 2;
            int y = 10;
            aThis.setBounds(x, y, aThis.getWidth(), screenSize.height - 40);
        }
    }

    /**
     * Print the Header for Properties
     */
    public void printPropertyHeader() {
        printHeader();
        printColumn(Constants.get(Constants.PrintTitleString));
        printHeader();
    }

    /**
     * Prints the Footer for Properties
     */
    public void printPropertyFooter() {
        printHeader();
        printColumn(Constants.get(Constants.PrintEndString));
        printHeader();
    }

    /**
     * Prints the Property
     */
    void printPropertyColumn(String Key, String Value) {
        printColumn(Key, Value);
    }
}
