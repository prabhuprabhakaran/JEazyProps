/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
package com.prabhu.jeazyprops.utils.validation;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.bean.FilePanel;
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.props.PropertyDisplayer;
import com.prabhu.jeazyprops.props.ReflectionProvider;
import java.awt.Color;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
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
import org.jdatepicker.impl.JDatePickerImpl;

/**
 * Class for Validation of Fields
 *
 * @author Prabhu Prabhakaran
 */
public final class Validator extends InputVerifier
{

    ReflectionProvider lReflectionProvider;
    PropertyDisplayer lDisplayer;

    public Validator(ReflectionProvider pReflectionProvider)
    {
        lReflectionProvider = pReflectionProvider;
        lDisplayer = lReflectionProvider.getDispalyer();
    }

    @Override
    public boolean shouldYieldFocus(JComponent input)
    {
        return super.shouldYieldFocus(input);
    }

    @Override
    public boolean verify(JComponent input)
    {
        boolean lReturn = false;
        if (input instanceof JTextField)
        {
            String lField = input.getAccessibleContext().getAccessibleName();
            Class<?> datatype = lReflectionProvider.getDatatype(lField);
            lReturn = CheckIsValid(((JTextField) input).getText(), datatype);
            ChangeIndicatior(lReturn, input);
        }
        else if (input instanceof JPasswordField)
        {
            String lField = input.getAccessibleContext().getAccessibleName();
            Class<?> datatype = lReflectionProvider.getDatatype(lField);
            lReturn = CheckIsValid(((JPasswordField) input).getPassword().toString(), datatype);
            ChangeIndicatior(lReturn, input);
        }
        else if (input instanceof FilePanel)
        {
            String lField = input.getAccessibleContext().getAccessibleName();
            Class<?> datatype = lReflectionProvider.getDatatype(lField);
            lReturn = CheckIsValid(((FilePanel) input).getFileName(), datatype);
            ChangeIndicatior(lReturn, input);
        }
        else
        {
            lReturn = true;
        }
        return lReturn;
    }

    public void ChangeIndicatior(boolean lReturn, JComponent input)
    {
        if (input instanceof FilePanel)
        {
            Highlighter highlighter = ((FilePanel) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn)
            {
                try
                {
                    highlighter.addHighlight(0, ((FilePanel) input).getFileName().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                }
                catch (BadLocationException ex)
                {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (input instanceof JPasswordField)
        {
            Highlighter highlighter = ((JPasswordField) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn)
            {
                try
                {
                    highlighter.addHighlight(0, ((JPasswordField) input).getPassword().length, new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                }
                catch (BadLocationException ex)
                {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (input instanceof JTextField)
        {
            Highlighter highlighter = ((JTextField) input).getHighlighter();
            highlighter.removeAllHighlights();
            if (!lReturn)
            {
                try
                {
                    highlighter.addHighlight(0, ((JTextField) input).getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                }
                catch (BadLocationException ex)
                {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean CheckIsValid(String text, Class<?> lType)
    {
        boolean lReturn = false;
        if (lType.equals(Integer.class) || lType.equals(int.class))
        {
            lReturn = Pattern.compile(Constants.IntegerPatternString).matcher(text).matches();
        }
        else if (lType.equals(Double.class) || lType.equals(double.class) || lType.equals(float.class) || lType.equals(Float.class))
        {
            lReturn = Pattern.compile(Constants.DoublePatternString).matcher(text).matches();
        }
        else if (lType.equals(String.class))
        {
            if (!text.isEmpty())
            {
                lReturn = true;//Pattern.compile(Constants.TextPatternString).matcher(text).matches();
            }
            else
            {
                lReturn = true;
            }
        }
        else if (lType.equals(File.class))
        {
            if (!text.isEmpty())
            {
                lReturn = new File(text).exists();
            }
            else
            {
                lReturn = true;
            }
        }
        return lReturn;
    }

    /**
     * Validates the text fields
     *
     * @return true if all validation matches
     */
    public boolean ValidateData()
    {
        boolean lReturn = true;
        try
        {
            for (Method lMethod : lReflectionProvider.getGetMethods())
            {
                KeyValue CurrentField = lReflectionProvider.getKeyValues().get(lMethod.getName());
                if (CurrentField != null && !CurrentField.lDisplayName.isEmpty())
                {
                    Class<?> parameterTypes = lMethod.getReturnType();
                    if (parameterTypes.equals(String.class) || parameterTypes.equals(int.class) || parameterTypes.equals(Integer.class) || parameterTypes.equals(double.class) || parameterTypes.equals(Double.class) || parameterTypes.equals(float.class) || parameterTypes.equals(Float.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            lReturn = lReturn && ((JPasswordField) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                        }
                        else
                        {
                            lReturn = lReturn && ((JTextField) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                        }
                    }
                    else if (parameterTypes.equals(File.class))
                    {
                        lReturn = lReturn && ((FilePanel) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                    }
                    else if (parameterTypes.equals(Date.class))
                    {
                        lReturn = lReturn && ((JDatePickerImpl) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            lReturn = false;
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lReturn;
    }

}
