/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Prabhu Prabhakaran
 */
public final class Constants {

    static ResourceBundle rb;
    public static String CharacterSetString = "UTF-8";
    public static String DoublePatternString = "^(-)?(\\d){1,10}\\.(\\d){1,10}$";
    public static String EncryptionAESString = "AES";
    public static String EncryptionDESString = "DESede";
    public static String FileBrowseButtonString = "001";
    public static String GroupTypeString = "002";
    public static String IntegerPatternString = "^(-)?(\\d){1,9}$";
    public static String TextPatternString = "^(\\S)(.){1,75}(\\S)$";
    public static String UserDirectoryString = "user.dir";
    public static String ParentDirectoryString = "..";
    public static String ToggleFilePathString = "018";
    public static String AllString = "003";
    public static String AlertString = "007";
    public static String DefaultBooleanString = "false";
    public static String DefaultFloatString = "0.0";
    public static String DefaultIntegerString = "0";
    public static String DefaultStringString = "";
    public static String DisplayNameAppendString = " : ";
    public static String EncryptionErrorString = "020";
    public static String ErrorString = "016";
    public static String FileCloseErrorString = "009";
    public static String FileCreationErrorString = "008";
    public static String FileNotFoundString = "006";
    public static String FileReadErrorString = "010";
    public static String FileSeperatorString = "file.separator";
    public static String DatePattern = "yyyy-MM-dd";
    public static String GetMethodStartString = "get";
    public static String IsMethodStartString = "is";
    public static String JarSeperatorString = "/";
    public static String MnenomicReplaceSymbolString = "";
    public static String MnenomicSymbolString = "&";
    public static String PrefixString = "";
    public static String PrintEndString = "023";
    public static String PrintTitleString = "022";
    public static String PropertiesExtension = ".properties";
    public static String PropertiesRootString = "properties";
    public static String PropertiesStandard = "http://java.sun.com/dtd/properties.dtd";
    public static String SaveButtonString = "011";
    public static String ResetButtonString = "012";
    public static String ScreenTitleString = "021";
    public static String SetMethodStartString = "set";
    public static String SuffixString = "";
    public static String UpdateFailedString = "014";
    public static String UpdateSuccessString = "015";
    public static String UpdatedSuccessfullyString = "013";
    public static String AboutTagLine = "024";
    public static String AboutFileName = "025";
    public static String AboutFileType = "026";
    public static String AboutPropertiesCount = "027";
    public static String AboutIsEncrypt = "028";
    public static String AboutFileLocation = "029";
    public static String AboutContactString = "004";
    public static String ValidationErrorString = "017";
    public static String AppImageFileName = "JeazyProps.jpg";
    public static String XMLExtension = ".xml";
    public static char SaveMnenomicSymbolString = 'S';
    public static char ResetMnenomicSymbolString = 'R';
    public static String PropsCommentLine = "*******************************************************************************\n This Property file is generated by JeazyProps. Please do not edit it Manually.\n For More Info: http://prabhuprabhakaran.wordpress.com/2012/09/09/jeazyprops/\n                http://java.net/projects/jeazyprops\n*******************************************************************************";
    public static String XMLCommentLine = "\nThis Property file is generated by JeazyProps. Please do not edit it Manually.\n For More Info: http://prabhuprabhakaran.wordpress.com/2012/09/09/jeazyprops/\n                http://java.net/projects/jeazyprops\n";

    static {
        rb = ResourceBundle.getBundle("Bundle", Locale.getDefault());
    }

    /**
     * Gets the Constant Value
     *
     * @param pKeyValule String to be find in Resource Bundle
     *
     * @return returns the value String found for the Key.
     */
    public static String get(String pKeyValule) {
        return rb.getString(pKeyValule);
    }

    /**
     * Gets the Constant Value
     *
     * @param lPropsArg Replacement String to replace
     * @param pKeyValule Key String to be find
     *
     * @return returns the modified String
     */
    public static String get(String lPropsArg, String pKeyValule) {
        int indexOf = Constants.get(pKeyValule).indexOf("<");
        int indexOf1 = Constants.get(pKeyValule).indexOf(">");
        if (indexOf != -1 && indexOf1 != -1) {
            return Constants.get(pKeyValule).substring(0, indexOf) + lPropsArg + Constants.get(pKeyValule).substring(indexOf1 + 1);
        }
        return Constants.get(pKeyValule) + lPropsArg;
    }
}
