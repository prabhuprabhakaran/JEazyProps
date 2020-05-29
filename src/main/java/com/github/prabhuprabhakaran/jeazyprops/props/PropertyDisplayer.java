/*  
 * To change this template, choose Tools | Templates  
 * and open the template in the editor.  */
package com.prabhu.jeazyprops.props;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.interfaces.Display;
import com.prabhu.jeazyprops.interfaces.JeazyEncryption;
import com.prabhu.jeazyprops.interfaces.PropertiesEntity;
import com.prabhu.jeazyprops.ui.cmd.CMDDisplayHandler;
import com.prabhu.jeazyprops.ui.gui.GUIDisplayHandler;
import com.prabhu.jeazyprops.ui.gui.PropertyPanel;
import com.prabhu.jeazyprops.utils.FileUtils;
import com.prabhu.jeazyprops.utils.JeazyDisplayType;
import com.prabhu.jeazyprops.utils.PropsFilelocation;
import com.prabhu.jeazyprops.utils.PropsFiletype;
import com.prabhu.jeazyprops.utils.encryption.AES;
import com.prabhu.jeazyprops.utils.encryption.Base64;
import com.prabhu.jeazyprops.utils.encryption.Tripledes;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This Class used to create GUI for the Property Bean Object. The Property Bean
 * class need to implement the marker interface
 * {@link PropertiesEntity PropertiesEntity}
 * <pre>
 *
 * For Eg:
 *      public class PropsTestGui implements PropertiesEntity
 *      {
 *          ...
 *          ...
 *          ...
 *
 *          public static void main(String[] args)
 *          {
 *              PropsTestGui lPropsTest = new PropsTestGui();
 *              PropertyDisplayer lDispalyer = new PropertyDisplayer(lPropsTest);
 *              lDispalyer.setEncryption(new AES());
 *              lDispalyer.loadProperties("test.properties");
 *              lDisplayer.setDisplayMode(Constants.JeazyDisplayType.GUI);
 *              lDispalyer.displaySettings();
 *          }
 *      }
 * </pre>
 *
 * @see PropertiesEntity
 * @author Prabhu Prabhakaran
 */
public final class PropertyDisplayer {

    private Properties lProperties;
    private File lPropsFile;
    private JeazyEncryption lPropsEncrypt;
    private String lPropsFilePath;
    private String lSufixProps = Constants.SuffixString;
    private String lPrefixProps = Constants.PrefixString;
    private String lDatePattern = Constants.DatePattern;
    private PropsFiletype lPropsFiletype;
    private DateFormat ldateFormat;
    private PropsFilelocation lPropsFilelocation;
    private Display lPropertyDisplayer;
    private final ReflectionProvider lReflectionProvider;
    private boolean dispalyingAsWindow;

    /**
     * Block To initialize UI LookNFeel
     */
    static {
        try {
            javax.swing.UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Default constructor for initialization of Displayer
     *
     * @param pEntity The Property Bean Object which needs to be displayed in UI
     *
     * @see PropertiesEntity
     */
    public PropertyDisplayer(PropertiesEntity pEntity) {
        ldateFormat = new SimpleDateFormat(lDatePattern);
        lReflectionProvider = new ReflectionProvider(pEntity, this);
        lPropertyDisplayer = new GUIDisplayHandler(lReflectionProvider);
    }

    public final JeazyEncryption getPropsEncrypt() {
        return lPropsEncrypt;
    }

    public final String getPropsFilePath() {
        return lPropsFilePath;
    }

    public final PropsFiletype getPropsFiletype() {
        return lPropsFiletype;
    }

    public final PropsFilelocation getPropsFilelocation() {
        return lPropsFilelocation;
    }

    /**
     * Sets the Encryption algorithm for the UI
     *
     * @param pEncryptModel Encryption Algorithm which implements
     * {@link JeazyEncryption}
     *
     * @see JeazyEncryption
     * @see AES
     * @see Base64
     * @see Tripledes
     */
    public final void setEncryption(JeazyEncryption pEncryptModel) {
        lPropsEncrypt = pEncryptModel;
    }

    /**
     * Sets the Display Mode for the Properties Bean
     *
     * @param pPropsDisplayType Either COMMAND Mode or GUI Mode based on
     * {@link JeazyDisplayType}
     *
     * @see JeazyDisplayType
     */
    public final void setDisplayMode(JeazyDisplayType pPropsDisplayType) {
        if (pPropsDisplayType == JeazyDisplayType.CMD) {
            lPropertyDisplayer = new CMDDisplayHandler(lReflectionProvider);
        } else if (pPropsDisplayType == JeazyDisplayType.GUI) {
            lPropertyDisplayer = new GUIDisplayHandler(lReflectionProvider);
        }
    }

    /**
     * To Specify any Prefix is added for the KeyValue Object, Default is Empty
     *
     * @param pPrefixProps Prefix String for all the properties
     */
    public final void setPrefix(String pPrefixProps) {
        lPrefixProps = pPrefixProps;
    }

    /**
     * Returns the Prefix String
     *
     * @return returns the Prefix String
     */
    public final String getPrefix() {
        return lPrefixProps;
    }

    /**
     * Returns the Suffix String
     *
     * @return returns the Suffix String
     */
    public final String getSuffix() {
        return lSufixProps;
    }

    /**
     * To Specify any Suffix is added for the KeyValue Object, Default is Empty
     *
     * @param pSufixProps Suffix String for all the properties
     */
    public final void setSuffix(String pSufixProps) {
        lSufixProps = pSufixProps;
    }

    /**
     * To Specify Date Format for Date Object, Default is "yyyy-MM-dd"
     *
     * @param pDatePattern Date pattern String for all the Date objects
     */
    public final void setDateFormat(String pDatePattern) {
        lDatePattern = pDatePattern;
        ldateFormat = new SimpleDateFormat(lDatePattern);
    }

    /**
     * Returns the Date Format for the Properties Bean
     *
     * @return returns the Date Format Model
     */
    public final DateFormat getDateFormat() {
        return ldateFormat;
    }

    public boolean isDisplayingAsWindow() {
        return dispalyingAsWindow;
    }

    /**
     * Gets the File InputStream of the properties file specified either
     * Internal or External
     *
     * @exception returns null
     * @return InputStream of properties file
     */
    private InputStream getPropsFileInputStream() {
        InputStream mFileInputStream = null;
        try {
            if (lPropsFile.exists()) {
                mFileInputStream = new FileInputStream(lPropsFile);
                lPropsFilelocation = PropsFilelocation.EXTERNAL;
            } else {
                StackTraceElement[] lStackTrace = new Exception().fillInStackTrace().getStackTrace();
                String lCallingClass = lStackTrace[lStackTrace.length - 2].getClassName();
                mFileInputStream = ClassLoader.getSystemClassLoader().loadClass(lCallingClass).getResourceAsStream(Constants.JarSeperatorString + lPropsFilePath);
                lPropsFilelocation = PropsFilelocation.INTERNAL;
            }
            if (mFileInputStream == null) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    FileUtils.createNewXMLFile(lPropsFilePath);
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    lPropsFile.createNewFile();
                }
                mFileInputStream = new FileInputStream(lPropsFile);
                lPropsFilelocation = PropsFilelocation.EXTERNAL;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileNotFoundString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCreationErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);

        }
        return mFileInputStream;
    }

    /**
     * Populate the properties listed in the file to the properties bean
     *
     * @param PropsFilePath Relative or Absolute path of the Properties File
     */
    public final void loadProperties(String PropsFilePath) {
        lPropsFilePath = PropsFilePath;

        if (lPropsFilePath.endsWith(Constants.XMLExtension)) {
            lPropsFiletype = PropsFiletype.XML;
        } else if (lPropsFilePath.endsWith(Constants.PropertiesExtension)) {
            lPropsFiletype = PropsFiletype.PROPERTIES;
        }

        if (lPropsFiletype == PropsFiletype.XML && lReflectionProvider.isHavingJAXBAnnotations()) {
            lPropsFiletype = PropsFiletype.JAXB;
        }
        InputStream mFileInputStream = null;
        try {
            lPropsFile = new File(PropsFilePath);
            mFileInputStream = getPropsFileInputStream();
            if (mFileInputStream != null) {
                loadProperties(mFileInputStream);
            } else {
                JOptionPane.showMessageDialog(null, Constants.get(Constants.FileNotFoundString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            try {
                if (mFileInputStream != null) {
                    mFileInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCloseErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads the Properties from input stream to Bean
     *
     * @param pFileInputStream input stream of a properties file
     */
    private void loadProperties(InputStream pFileInputStream) {
        lProperties = new Properties();
        try {
            if (lPropsFiletype != PropsFiletype.JAXB) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    pFileInputStream.close();
                    pFileInputStream = getPropsFileInputStream();
                    try {
                        lProperties.loadFromXML(pFileInputStream);
                    } catch (InvalidPropertiesFormatException ex) {
                        Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, Constants.get(Constants.FileReadErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
                    }
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    lProperties.load(pFileInputStream);
                }
                lReflectionProvider.populateAllFields();
                lReflectionProvider.setReader(new PropertyReader(lProperties, lPropsEncrypt));
                lReflectionProvider.readProperties();
            } else {
                lReflectionProvider.populateAllFields();
                lReflectionProvider.readJAXBProperties();
            }

        } catch (IOException ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileReadErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pFileInputStream != null) {
                    pFileInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCloseErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates or saves the properties to the file
     *
     * @return either true on successful save or false in case of fail
     */
    protected boolean updateProperties() {
        try {

            if (lPropsFilelocation == PropsFilelocation.EXTERNAL) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    lProperties.storeToXML(new FileOutputStream(lPropsFilePath), Constants.XMLCommentLine);
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    lProperties.store(new FileOutputStream(lPropsFilePath), Constants.PropsCommentLine);
                } else if (lPropsFiletype == PropsFiletype.JAXB) {
                    lReflectionProvider.updateJAXBProperties();
                }
            } else if (lPropsFilelocation == PropsFilelocation.INTERNAL) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    new File(lPropsFilePath.substring(0, lPropsFilePath.lastIndexOf(Constants.JarSeperatorString))).mkdirs();
                    FileUtils.createNewXMLFile(lPropsFilePath);
                    FileOutputStream fileOutputStream = new FileOutputStream(lPropsFilePath);
                    lProperties.storeToXML(fileOutputStream, Constants.XMLCommentLine);
                    fileOutputStream.close();
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    new File(lPropsFilePath.substring(0, lPropsFilePath.lastIndexOf(Constants.JarSeperatorString))).mkdirs();
                    new File(lPropsFilePath).createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(lPropsFilePath);
                    lProperties.store(fileOutputStream, Constants.PropsCommentLine);
                    fileOutputStream.close();
                } else if (lPropsFiletype == PropsFiletype.JAXB) {
                    lReflectionProvider.updateJAXBProperties();
                }
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Displays the Properties in a GUI for Editing and Updating
     */
    public final void displaySettings() {
        displaySettings(true);
    }

    /**
     * Displays the Properties in a GUI for Editing, Viewing and Updating
     *
     * @param isAbletoSave True for enable save option, False for View Only
     */
    public final void displaySettings(boolean isAbletoSave) {
        dispalyingAsWindow = true;
        lPropertyDisplayer.displaySettings(isAbletoSave);
    }

    /**
     * Gets the properties panel to Integrate with Application
     *
     * @return Properties JPanel
     */
    public final PropertyPanel getDisplayPanel() {
        return lPropertyDisplayer.getDisplayPanel(true);
    }

    /**
     *
     * @param isAbletoSave
     *
     * @return
     */
    public final PropertyPanel getDisplayPanel(boolean isAbletoSave) {
        dispalyingAsWindow = false;
        return lPropertyDisplayer.getDisplayPanel(isAbletoSave);
    }
}
