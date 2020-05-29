/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.interfaces;

import com.prabhu.jeazyprops.ui.gui.PropertyPanel;

/**
 *
 * @author Prabhu Pabhakaran
 */
public interface Display
{

    public void displaySettings(boolean isSave);

    public PropertyPanel getDisplayPanel(boolean isSave);
}
