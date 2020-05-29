/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.ui.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Prabhu Prabhakaran
 */
public class PropertyPanel extends JPanel {

    public void replace(JComponent source) {
        Container OldPanel = source.getParent();
        while (!(OldPanel instanceof PropertyPanel)) {
            OldPanel = OldPanel.getParent();
        }
        Container OldPanelParent = OldPanel.getParent();
        OldPanel.setVisible(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 100;
        gbc.insets = new Insets(10, 10, 10, 10);
        OldPanelParent.add(this, gbc);
        this.setVisible(true);
        OldPanel = null;
    }
}
