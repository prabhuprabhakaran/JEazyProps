/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.ui.cmd;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.interfaces.Display;
import com.prabhu.jeazyprops.props.PropertyDisplayer;
import com.prabhu.jeazyprops.props.ReflectionProvider;
import com.prabhu.jeazyprops.ui.gui.PropertyPanel;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prabhu Prabhakaran
 */
public final class CMDDisplayHandler extends CMDDisplayFormatter implements Display {

    ReflectionProvider lReflectionProvider;

    public CMDDisplayHandler(ReflectionProvider pReflectionProvider) {
        lReflectionProvider = pReflectionProvider;
    }

    @Override
    public PropertyPanel getDisplayPanel(boolean pisAbletoSave) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Print the Header for Properties
     */
    private void printPropertyHeader() {
        printHeader();
        printColumn(Constants.get(Constants.PrintTitleString));
        printHeader();
    }

    /**
     * Prints the Footer for Properties
     */
    private void printPropertyFooter() {
        printHeader();
        printColumn(Constants.get(Constants.PrintEndString));
        printHeader();
    }

    /**
     * Prints the Property
     */
    private void printPropertyColumn(String Key, String Value) {
        printColumn(Key, Value);
    }

    @Override
    public void displaySettings(boolean isSave) {
        try {
            printPropertyHeader();
            for (Method lMethod : lReflectionProvider.getGetMethods()) {
                Object lValue = lReflectionProvider.getValue(lMethod);
                String invoke = "";
                if (lValue instanceof Date) {
                    invoke = lReflectionProvider.getDispalyer().getDateFormat().format(lValue);
                } else {
                    invoke = String.valueOf(lValue);
                }
                KeyValue CurrentField = lReflectionProvider.getKeyValues().get(lMethod.getName());
                if (CurrentField != null && CurrentField.lKey != null && CurrentField.lDisplayName != null) {
                    printPropertyColumn(CurrentField.lDisplayName.replaceAll(Constants.MnenomicSymbolString, Constants.MnenomicReplaceSymbolString), invoke);
                }
            }
            printPropertyFooter();
        } catch (Exception ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
