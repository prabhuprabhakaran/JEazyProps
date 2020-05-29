/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author Prabhu Prabhakaran
 */
public final class JeazyDateModel extends AbstractFormatter {

    private DateFormat dateFormatter;

    public JeazyDateModel(DateFormat PDatePattern) {
        dateFormatter = PDatePattern;
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
