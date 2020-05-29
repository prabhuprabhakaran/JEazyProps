/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
package com.prabhu.jeazyprops.validation;

import com.prabhu.jeazyprops.bean.FilePanel;
import com.prabhu.jeazyprops.props.BaseProps;
import com.prabhu.jeazyprops.props.Constants;
import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * Class for Validation of Fields
 *
 * @author Prabhu Prabhakaran
 */
public class Validator extends InputVerifier {

    BaseProps lProps;

    public Validator(BaseProps pProps) {
        lProps = pProps;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        return super.shouldYieldFocus(input);
    }

    @Override
    public boolean verify(JComponent input) {
        boolean lReturn = false;
        if (input instanceof JTextField) {
            String lField = input.getAccessibleContext().getAccessibleName();
            String datatype = lProps.getDatatype(lField);
            lReturn = CheckIsValid(((JTextField) input).getText(), datatype);
            ChangeIndicatior(lReturn, input);
        } else if (input instanceof JPasswordField) {
            String lField = input.getAccessibleContext().getAccessibleName();
            String datatype = lProps.getDatatype(lField);
            lReturn = CheckIsValid(((JPasswordField) input).getPassword().toString(), datatype);
            ChangeIndicatior(lReturn, input);
        } else if (input instanceof FilePanel) {
            String lField = input.getAccessibleContext().getAccessibleName();
            String datatype = lProps.getDatatype(lField);
            lReturn = CheckIsValid(((FilePanel) input).getFileName(), datatype);
            ChangeIndicatior(lReturn, input);
        } else {
            lReturn = true;
        }
        return lReturn;
    }

    public void ChangeIndicatior(boolean lReturn, JComponent input) {
        if (input instanceof FilePanel) {
            Highlighter highlighter = ((FilePanel) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn) {
                try {
                    highlighter.addHighlight(0, ((FilePanel) input).getFileName().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                } catch (BadLocationException ex) {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (input instanceof JPasswordField) {
            Highlighter highlighter = ((JPasswordField) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn) {
                try {
                    highlighter.addHighlight(0, ((JPasswordField) input).getPassword().length, new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                } catch (BadLocationException ex) {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (input instanceof JTextField) {
            Highlighter highlighter = ((JTextField) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn) {
                try {
                    highlighter.addHighlight(0, ((JTextField) input).getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                } catch (BadLocationException ex) {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean CheckIsValid(String text, String lType) {
        boolean lReturn = false;
        if (lType.equals(Constants.IntegerString) || lType.equals(Constants.IntegerClassString)) {
            lReturn = Pattern.compile(Constants.IntegerPatternString).matcher(text).matches();
        } else if (lType.equals(Constants.DoubleString) || lType.equals(Constants.DoubleClassString) || lType.equals(Constants.FloatString) || lType.equals(Constants.FloatClassString)) {
            lReturn = Pattern.compile(Constants.DoublePatternString).matcher(text).matches();
        } else if (lType.equals(Constants.StringClassString)) {
            if (!text.isEmpty()) {
                lReturn = true;//Pattern.compile(Constants.TextPatternString).matcher(text).matches();
            } else {
                lReturn = true;
            }
        } else if (lType.equals(Constants.FileClassString)) {
            if (!text.isEmpty()) {
                lReturn = new File(text).exists();
            } else {
                lReturn = true;
            }
        }
        return lReturn;
    }
}
