/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.ui.gui;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.interfaces.Display;
import com.prabhu.jeazyprops.props.ReflectionProvider;
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
 * @author Prabhu Prabhakaran
 */
public final class GUIDisplayHandler implements Display {

    JDialog lJDialog = null;
    PropertyPanel lJPanel;
    JScrollPane jMainScrollPane;
    GUICreator lGUICreator;
    /**
     * Displays the Properties in a GUI for Editing and Updating
     */
    GridBagConstraints gbc;

    /**
     * creates instance of GUIDisplayHandler
     *
     * @param pReflectionProvider the Displayer object which needs to be
     * displayed in GUI
     */
    public GUIDisplayHandler(ReflectionProvider pReflectionProvider) {
        lGUICreator = new GUICreator(pReflectionProvider);

    }

    @Override
    public PropertyPanel getDisplayPanel(boolean isAbletoSave) {
        return lGUICreator.getDisplayPanel(isAbletoSave);
    }

    private void createDisplayerDialog() {
        if (lJDialog == null) {
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
    }

    /**
     * Setting Display to the window
     *
     * @param pJPanel
     */
    private void displaySettings(PropertyPanel pJPanel) {
        createDisplayerDialog();
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
        jMainScrollPane = new JScrollPane(lJPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

    @Override
    public void displaySettings(boolean isSave) {
        displaySettings(lGUICreator.getDisplayPanel(isSave));
    }

}
