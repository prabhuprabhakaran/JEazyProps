/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.utils;

import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Prabhu Prabhakaran
 */
public class Utils {

    /**
     *
     * @param pEnum Class name of the Enum Collection
     * @param property Name to be find inside the Enum
     *
     * @return the index position of the property
     */
    public static Integer getIndexofEnum(Class<?> pEnum, String property) {
        Vector lVector = new Vector();
        for (Object object : pEnum.getEnumConstants()) {
            lVector.add("" + object);
        }
        return (lVector.indexOf(property) < 0) ? 0 : lVector.indexOf(property);
    }

    /**
     *
     * @param pEnum class name of the enum to be populated in ComboBox
     *
     * @return returns the Model to be populated in ComboBox
     */
    public static ComboBoxModel getComboValues(Class pEnum) {
        Vector lVector = new Vector();
        for (Object object : pEnum.getEnumConstants()) {
            lVector.add("" + object);
        }
        return new DefaultComboBoxModel(lVector);
    }
}
