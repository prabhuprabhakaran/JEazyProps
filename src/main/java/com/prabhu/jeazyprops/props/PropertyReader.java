/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.props;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.interfaces.JeazyEncryption;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 * Class to get the Formated Property
 *
 * @author Prabhu Prabhakaran
 */
public final class PropertyReader
{

    private final Properties lProperties;
    private final JeazyEncryption lPropsEncrypt;

    protected PropertyReader(Properties pProperties, JeazyEncryption pPropsEncrypt)
    {
        lProperties = pProperties;
        lPropsEncrypt = pPropsEncrypt;
    }

    /**
     * * Gets the double property
     *
     * @param Key       Property Name (key value)
     * @param isEncrypt Whether the value is encrypted
     *
     * @return The double Property
     */
    public double getDoubleProperty(String Key, boolean isEncrypt)
    {
        try
        {
            if (isEncrypt)
            {
                if (lProperties.getProperty(Key, Constants.DefaultFloatString).equals(Constants.DefaultFloatString))
                {
                    return Integer.valueOf(lProperties.getProperty(Key, Constants.DefaultFloatString));
                }
                else
                {
                    if (lPropsEncrypt == null)
                    {
                        JOptionPane.showMessageDialog(null, Constants.get(Constants.EncryptionErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
                    }
                    return Integer.valueOf(lPropsEncrypt.decrypt(lProperties.getProperty(Key)));
                }
            }
            else
            {
                return Integer.valueOf(lProperties.getProperty(Key, Constants.DefaultFloatString));
            }
        }
        catch (NumberFormatException ex)
        {
            return Integer.valueOf(Constants.DefaultFloatString);
        }
    }

    /**
     * * Gets the integer property
     *
     * @param Key       Property Name (key value)
     * @param isEncrypt Whether the value is encrypted
     *
     * @return The int Property
     */
    public int getIntegerProperty(String Key, boolean isEncrypt)
    {
        try
        {
            if (isEncrypt)
            {
                if (lProperties.getProperty(Key, Constants.DefaultIntegerString).equals(Constants.DefaultIntegerString))
                {
                    return Integer.valueOf(lProperties.getProperty(Key, Constants.DefaultIntegerString));
                }
                else
                {
                    if (lPropsEncrypt == null)
                    {
                        JOptionPane.showMessageDialog(null, Constants.get(Constants.EncryptionErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
                    }
                    return Integer.valueOf(lPropsEncrypt.decrypt(lProperties.getProperty(Key)));
                }
            }
            else
            {
                return Integer.valueOf(lProperties.getProperty(Key, Constants.DefaultIntegerString));
            }
        }
        catch (NumberFormatException ex)
        {
            return Integer.valueOf(Constants.DefaultIntegerString);
        }
    }

    /**
     * Gets the float property
     *
     * @param Key       Property Name (key value)
     * @param isEncrypt Whether the value is encrypted
     *
     * @return The double Property
     */
    public float getFloatProperty(String Key, boolean isEncrypt)
    {
        try
        {
            if (isEncrypt)
            {
                if (lProperties.getProperty(Key, Constants.DefaultFloatString).equals(Constants.DefaultFloatString))
                {
                    return Float.valueOf(lProperties.getProperty(Key, Constants.DefaultFloatString));
                }
                else
                {
                    if (lPropsEncrypt == null)
                    {
                        JOptionPane.showMessageDialog(null, Constants.get(Constants.EncryptionErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
                    }
                    return Float.valueOf(lPropsEncrypt.decrypt(lProperties.getProperty(Key)));
                }
            }
            else
            {
                return Float.valueOf(lProperties.getProperty(Key, Constants.DefaultFloatString));
            }
        }
        catch (NumberFormatException ex)
        {
            return Float.valueOf(Constants.DefaultFloatString);
        }
    }

    /**
     * Gets the String property
     *
     * @param Key       Property Name (key value)
     * @param isEncrypt Whether the value is encrypted
     *
     * @return The String Property
     */
    public String getStringProperty(String Key, boolean isEncrypt)
    {
        if (isEncrypt)
        {
            if (lProperties.getProperty(Key, Constants.DefaultStringString).equals(Constants.DefaultStringString))
            {
                return lProperties.getProperty(Key, Constants.DefaultStringString);
            }
            else
            {
                if (lPropsEncrypt == null)
                {
                    JOptionPane.showMessageDialog(null, Constants.get(Constants.EncryptionErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
                }
                return lPropsEncrypt.decrypt(lProperties.getProperty(Key));
            }
        }
        else
        {
            return lProperties.getProperty(Key, Constants.DefaultStringString);
        }
    }

    /**
     * Gets the Boolean property
     *
     * @param Key       Property Name (key value)
     * @param isEncrypt Whether the value is encrypted
     *
     * @return The Boolean Property
     */
    public boolean getBooleanProperty(String Key, boolean isEncrypt)
    {
        try
        {
            if (isEncrypt)
            {
                if (lProperties.getProperty(Key, Constants.DefaultBooleanString).equals(Constants.DefaultBooleanString))
                {
                    return Boolean.valueOf(lProperties.getProperty(Key, Constants.DefaultBooleanString));
                }
                else
                {
                    if (lPropsEncrypt == null)
                    {
                        JOptionPane.showMessageDialog(null, Constants.get(Constants.EncryptionErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
                    }
                    return Boolean.valueOf(lPropsEncrypt.decrypt(lProperties.getProperty(Key)));
                }
            }
            else
            {
                return Boolean.valueOf(lProperties.getProperty(Key, Constants.DefaultBooleanString));
            }
        }
        catch (NumberFormatException ex)
        {
            return Boolean.valueOf(Constants.DefaultBooleanString);
        }
    }

    /**
     * Sets the integer property
     *
     * @param Key   Property Name (key value)
     * @param Value Property Value (key value)
     */
    public void putStringProperty(String Key, String Value)
    {
        putStringProperty(Key, Value, false);
    }

    /**
     * Sets the String property
     *
     * @param Key       Property Name (key value)
     * @param Value     Property Value (key value)
     * @param isEncrypt Whether the value is encrypted
     */
    public void putStringProperty(String Key, String Value, boolean isEncrypt)
    {
        if (isEncrypt)
        {
            lProperties.put(Key, lPropsEncrypt.encrypt(Value));
        }
        else
        {
            lProperties.put(Key, Value);
        }
    }
}
