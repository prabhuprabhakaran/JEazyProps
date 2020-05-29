/*  
 * To change this template, choose Tools | Templates  
 * and open the template in the editor.  */
package com.prabhu.jeazyprops.props;

import com.prabhu.cmdwindow.ui.PropertyPanel;
import com.prabhu.jeazyprops.bean.FilePanel;
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.interfaces.PropsEncrypt;
import com.prabhu.jeazyprops.validation.Validator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This is the Base Class for the Property Bean Class <br> This class is to be
 * extended to the property Bean Class
 *
 * <pre>
 *      For Eg:
 *          public class PropsTestGui extends BaseProps {
 *               ...
 *               ...
 *               ...
 *          }
 *
 *          public static void main(String[] args) {
 *              lPropsTest = new PropsTestGui();
 *              lPropsTest.setEncryption(new AES());
 *              lPropsTest.loadProperties("test.properties");
 *              lPropsTest.DisplaySettings();
 *          }
 * </pre>
 *
 * @author Prabhu Prabhakaran
 */
public abstract class BaseProps implements ItemListener {

    private Properties lProperties;
    private File lPropsFile;
    private PropsEncrypt lPropsEncrypt;
    private String lPropsFilePath;
    /**
     * To Specify any Suffix is added for the KeyValue Object, Default is Empty
     */
    protected String lSufixProps = Constants.SuffixString;
    /**
     * To Specify any Prefix is added for the KeyValue Object, Default is Empty
     */
    protected String lPrefixProps = Constants.PrefixString;
    private PropsFiletype lPropsFiletype;
    private PropsFilelocation lPropsFilelocation;
    private int compscount;
    private PropertyReader lPropertyReader;
    private PropertyDisplayer lPropertyDisplayer;
    private PropertySaveListner lPropertySaveListner;
    private FocusListener lFocusListener;
    private boolean lisAbletoSave = true;
    private String pClassName = this.getClass().getName();
    private Validator lValidator = new Validator(this);
    JComboBox lComboBox;
    ArrayList<Method> ListGetMethods = new ArrayList<Method>();
    ArrayList<Method> ListSetMethods = new ArrayList<Method>();
    HashMap<String, KeyValue> ListKeyValue = new HashMap<String, KeyValue>();
    boolean displayingAsWindow = false;

    /**
     * Block To initialize UI LookNFeel
     */
    static {
        try {
//            LookAndFeel lookAndFeel = javax.swing.UIManager.getLookAndFeel();
//            if (lookAndFeel == null) {
            javax.swing.UIManager.setLookAndFeel(new MultiLookAndFeel());
//            }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Property File Types
     */
    public enum PropsFiletype {

        XML, PROPERTIES
    };

    /**
     * Property File Location Types
     */
    public enum PropsFilelocation {

        EXTERNAL, INTERNAL
    };

    /**
     * Default constructor for initialization
     */
    protected BaseProps() {
        lPropertyDisplayer = new PropertyDisplayer();
        lPropertySaveListner = new PropertySaveListner();
        lFocusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (e.getComponent() instanceof JTextField) {
                    ((JTextField) e.getComponent()).select(0, ((JTextField) e.getComponent()).getText().length());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (e.getComponent() instanceof JTextField) {
                    ((JTextField) e.getComponent()).select(0, 0);
                }
            }
        };
    }

    /**
     * Sets the Encryption algorithm for the properties
     *
     * @param pPropsEncrypt
     */
    public void setEncryption(PropsEncrypt pPropsEncrypt) {
        lPropsEncrypt = pPropsEncrypt;
    }

    /**
     * Loads the Properties from File to Bean
     *
     * @param PropsFilePath Relative or Absolute path of the Properties File
     */
    public void loadProperties(String PropsFilePath) {
        lPropsFilePath = PropsFilePath;

        if (lPropsFilePath.endsWith(Constants.XMLExtension)) {
            lPropsFiletype = PropsFiletype.XML;
        } else if (lPropsFilePath.endsWith(Constants.PropertiesExtension)) {
            lPropsFiletype = PropsFiletype.PROPERTIES;
        }
        InputStream pFileInputStream = null;
        lPropsFile = new File(PropsFilePath);

        try {
            if (lPropsFile.exists()) {
                pFileInputStream = new FileInputStream(lPropsFile);
                lPropsFilelocation = PropsFilelocation.EXTERNAL;
            } else {
                StackTraceElement[] s = new Exception().fillInStackTrace().getStackTrace();
                String callingClass = s[s.length - 2].getClassName();
                pFileInputStream = ClassLoader.getSystemClassLoader().loadClass(callingClass).
                        getResourceAsStream(Constants.JarSeperatorString + lPropsFilePath);
                lPropsFilelocation = PropsFilelocation.INTERNAL;
            }
            if (pFileInputStream == null) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    createNewXMLFile();
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    lPropsFile.createNewFile();
                }
                pFileInputStream = new FileInputStream(lPropsFile);
                lPropsFilelocation = PropsFilelocation.EXTERNAL;
            }
            loadProperties(pFileInputStream, lPropsFiletype);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileNotFoundString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCreationErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pFileInputStream != null) {
                    pFileInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCloseErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads the Properties from input stream to Bean
     *
     * @param pFileInputStream input stream of a file stream
     */
    private void loadProperties(InputStream pFileInputStream, PropsFiletype pPropsFiletype) {
        lPropsFiletype = pPropsFiletype;
        lProperties = new Properties();
        try {
            if (lPropsFiletype == PropsFiletype.XML) {
                lProperties.loadFromXML(pFileInputStream);
            } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                lProperties.load(pFileInputStream);
            }
            lPropertyReader = new PropertyReader(lProperties, lPropsEncrypt);
            getAllFields();
            readProperties();
        } catch (IOException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, Constants.get(Constants.FileReadErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pFileInputStream != null) {
                    pFileInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, Constants.get(Constants.FileCloseErrorString), Constants.get(Constants.AlertString), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates or saves the properties to the file
     *
     * @return
     */
    private boolean updateProperties() {
        try {
            for (Method lMethod : ListGetMethods) {
                Object lValue = lMethod.invoke(this);
                String invoke = "";
                if (lValue != null) {
                    invoke = String.valueOf(lValue);
                }
                KeyValue currentValue = ListKeyValue.get(lMethod.getName());
                if (currentValue.key != null) {
                    lPropertyReader.putStringProperty(currentValue.key, invoke, currentValue.isEncrypted);
                }
            }

            if (lPropsFilelocation == PropsFilelocation.EXTERNAL) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    lProperties.storeToXML(new FileOutputStream(lPropsFilePath), Constants.XMLCommentLine);
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    lProperties.store(new FileOutputStream(lPropsFilePath), Constants.PropsCommentLine);
                }
            } else if (lPropsFilelocation == PropsFilelocation.INTERNAL) {
                if (lPropsFiletype == PropsFiletype.XML) {
                    new File(lPropsFilePath.substring(0, lPropsFilePath.lastIndexOf(Constants.JarSeperatorString))).mkdirs();
                    createNewXMLFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(lPropsFilePath);
                    lProperties.storeToXML(fileOutputStream, Constants.XMLCommentLine);
                    fileOutputStream.close();
                } else if (lPropsFiletype == PropsFiletype.PROPERTIES) {
                    new File(lPropsFilePath.substring(0, lPropsFilePath.lastIndexOf(Constants.JarSeperatorString))).mkdirs();
                    new File(lPropsFilePath).createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(lPropsFilePath);
                    lProperties.store(fileOutputStream, Constants.PropsCommentLine);
                    fileOutputStream.close();
                }
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Displays the Properties in a GUI for Editing and Updating
     */
    public void displaySettings() {
        displaySettings(true);
    }

    /**
     * Displays the Properties in a GUI for Editing, Viewing and Updating
     *
     * @param isAbletoSave True for Save, False for View Only
     */
    public void displaySettings(boolean isAbletoSave) {
        displayingAsWindow = true;
        lisAbletoSave = isAbletoSave;
        lPropertyDisplayer.DisplaySettings(getDisplayPanel(isAbletoSave));
    }

    /**
     * Prints the Properties
     */
    public void printProperties() {

        try {
            lPropertyDisplayer.printPropertyHeader();
            for (Method lMethod : ListGetMethods) {
                String invoke = String.valueOf(lMethod.invoke(this));
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField.key != null && CurrentField.displayName != null) {
                    lPropertyDisplayer.printPropertyColumn(CurrentField.displayName.replaceAll(Constants.MnenomicSymbolString, Constants.MnenomicReplaceSymbolString), invoke);
                }
            }
            lPropertyDisplayer.printPropertyFooter();
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void getAllFields() {
        ListGetMethods.clear();
        ListSetMethods.clear();
        ListKeyValue.clear();
        try {
            Method[] methods = this.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getDeclaringClass().getName().equals(pClassName) && method.getName().startsWith(Constants.GetMethodStartString) || method.getName().startsWith(Constants.IsMethodStartString)) {
                    if (method.getParameterTypes().length == 0) {
                        Field field = null;
                        try {
                            if (method.getName().startsWith(Constants.GetMethodStartString)) {
                                field = this.getClass().getDeclaredField(lPrefixProps + method.getName().substring(3) + lSufixProps);
                            } else {
                                field = this.getClass().getDeclaredField(lPrefixProps + method.getName().substring(2) + lSufixProps);
                            }
                        } catch (NoSuchFieldException ex) {
                            // Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
                            continue;
                        }
                        if (field == null) {
                            continue;
                        }
                        KeyValue CurrentField = (KeyValue) field.get(this);
                        ListGetMethods.add(method);
                        ListKeyValue.put(method.getName(), CurrentField);
                    }
                }
                if (method.getDeclaringClass().getName().equals(pClassName) && method.getName().startsWith(Constants.SetMethodStartString)) {
                    Field field = null;
                    try {
                        field = this.getClass().getDeclaredField(lPrefixProps + method.getName().substring(3) + lSufixProps);
                    } catch (NoSuchFieldException ex) {
                        // Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
                        continue;
                    }
                    if (field == null) {
                        continue;
                    }
                    KeyValue CurrentField = (KeyValue) field.get(this);
                    ListSetMethods.add(method);
                    ListKeyValue.put(method.getName(), CurrentField);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the properties panel to Integrate with Application
     *
     * @return Properties JPanel
     */
    public PropertyPanel getDisplayPanel() {
        return getDisplayPanel(true);
    }

    /**
     * Gets the properties panel to Integrate with Application
     *
     * @param isAbleToSave True for Save, False for View Only
     * @return
     */
    public PropertyPanel getDisplayPanel(boolean isAbleToSave) {
        PropertyPanel lPanel = new PropertyPanel();
        compscount = 0;
        try {
            lPanel.setLayout(new GridBagLayout());
            if (KeyValue.listGroups().size() > 1) {
                CreateViewChangers(lPanel);
            }
            GridBagConstraints lBagConstraints = new GridBagConstraints();
            lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            lBagConstraints.weightx = 1.0;
            for (Method lMethod : ListGetMethods) {
                Object invoke = lMethod.invoke(this);
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                lBagConstraints.fill = GridBagConstraints.CENTER;
                lBagConstraints.gridx = 0;
                lBagConstraints.gridy = compscount;
                lBagConstraints.insets = new Insets(5, 10, 5, 5);
                lBagConstraints.anchor = GridBagConstraints.EAST;
                lBagConstraints.weightx = 0;
                if (CurrentField.displayName != null) {
                    int indexOf = CurrentField.displayName.indexOf(Constants.MnenomicSymbolString);
                    JLabel jLabel = null;
                    if (indexOf != -1) {
                        char menemonic = CurrentField.displayName.charAt(indexOf + 1);
                        jLabel = new JLabel(CurrentField.displayName.replaceAll(Constants.MnenomicSymbolString, Constants.MnenomicReplaceSymbolString) + Constants.DisplayNameAppendString);
                        jLabel.setDisplayedMnemonic(menemonic);
                    } else {
                        jLabel = new JLabel(CurrentField.displayName + Constants.DisplayNameAppendString);
                    }
                    lPanel.add(jLabel, lBagConstraints);
                    lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                    lBagConstraints.gridx = 1;
                    lBagConstraints.gridy = compscount;
                    lBagConstraints.insets = new Insets(5, 5, 5, 10);
                    lBagConstraints.weightx = 1.0;
                    if (lMethod.getReturnType().isEnum()) {
                        CurrentField.lComponent = new JComboBox(getComboValues(lMethod.getReturnType()));
                        CurrentField.lComponent.getAccessibleContext().setAccessibleName(lPrefixProps + lMethod.getName().substring(3) + lSufixProps);
                        if (invoke == null) {
                            invoke = lMethod.getReturnType().getEnumConstants()[0];
                        }
                        ((JComboBox) CurrentField.lComponent).setSelectedItem("" + invoke);
                    } else if (lMethod.getReturnType().getName().equals(Constants.StringClassString) || lMethod.getReturnType().getName().equals(Constants.IntegerString) || lMethod.getReturnType().getName().equals(Constants.IntegerClassString) || lMethod.getReturnType().getName().equals(Constants.DoubleString) || lMethod.getReturnType().getName().equals(Constants.DoubleClassString) || lMethod.getReturnType().getName().equals(Constants.FloatString)
                            || lMethod.getReturnType().getName().equals(Constants.FloatClassString)) {
                        if (CurrentField.isEncrypted) {
                            CurrentField.lComponent = new JPasswordField(String.valueOf(invoke));
                            CurrentField.lComponent.getAccessibleContext().setAccessibleName(lPrefixProps + lMethod.getName().substring(3) + lSufixProps);
                            CurrentField.lComponent.setInputVerifier(lValidator);
                            CurrentField.lComponent.addFocusListener(lFocusListener);
                        } else {
                            CurrentField.lComponent = new JTextField(String.valueOf(invoke));
                            CurrentField.lComponent.getAccessibleContext().setAccessibleName(lPrefixProps + lMethod.getName().substring(3) + lSufixProps);
                            CurrentField.lComponent.setInputVerifier(lValidator);
                            CurrentField.lComponent.addFocusListener(lFocusListener);
                        }
                    } else if (lMethod.getReturnType().getName().equals(Constants.BooleanClassString) || lMethod.getReturnType().getName().equals(Constants.BooleanString)) {
                        CurrentField.lComponent = new JCheckBox();
                        CurrentField.lComponent.getAccessibleContext().setAccessibleName(lPrefixProps + lMethod.getName().substring(3) + lSufixProps);
                        ((JCheckBox) CurrentField.lComponent).setSelected(Boolean.valueOf(String.valueOf(invoke)));
                    } else if (lMethod.getReturnType().getName().equals(Constants.FileClassString)) {
                        CurrentField.lComponent = new FilePanel(CurrentField.isEncrypted);
                        CurrentField.lComponent.setInputVerifier(lValidator);
                        CurrentField.lComponent.getAccessibleContext().setAccessibleName(lPrefixProps + lMethod.getName().substring(3) + lSufixProps);
                        ((FilePanel) CurrentField.lComponent).setFileName(invoke);
                    }
                    if (CurrentField.lComponent instanceof FilePanel) {
                        jLabel.setLabelFor(((FilePanel) CurrentField.lComponent).getActionComponent());
                    } else {
                        jLabel.setLabelFor(CurrentField.lComponent);
                    }
                    lPanel.add(CurrentField.lComponent, lBagConstraints);
                    compscount++;
                }
            }
            if (isAbleToSave) {
                lBagConstraints.gridx = 0;
                lBagConstraints.gridwidth = 2;
                lBagConstraints.gridy = compscount;
                lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                JPanel lJPanel = new JPanel();
                lJPanel.setLayout(new GridLayout(1, 2));
                JButton button = new JButton(Constants.get(Constants.SaveButtonString));
                button.setMnemonic(Constants.SaveMnenomicSymbolString);
                button.addActionListener(lPropertySaveListner);
                lJPanel.add(button);
                JButton button1 = new JButton(Constants.get(Constants.ResetButtonString));
                button1.setMnemonic(Constants.ResetMnenomicSymbolString);
                button1.addActionListener(lPropertySaveListner);
                lJPanel.add(button1);
                lPanel.add(lJPanel, lBagConstraints);
                compscount++;
            }
            EtchedBorder etchedBorder = new EtchedBorder(EtchedBorder.LOWERED);
            etchedBorder.getBorderInsets(lPanel);
            lPanel.setBorder(etchedBorder);
            lPanel.setPreferredSize(new Dimension((520), (compscount * 29) + 40));
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
        lPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    showAboutPanel(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        return lPanel;
    }

    /**
     * Shows the About Panel
     *
     * @param source Component to Show
     * @param x x Position to show
     * @param y y Position to show
     */
    private void showAboutPanel(Component source, int x, int y) {
        JPopupMenu lJPopupMenu = new JPopupMenu();
        lJPopupMenu.add(new AboutPanel(createDetailPanel()));
        lJPopupMenu.show(source, x, y);
    }

    private JPanel createDetailPanel() {
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lBagConstraints = new GridBagConstraints();
        lBagConstraints.fill = GridBagConstraints.BOTH;
        lBagConstraints.gridx = 0;
        lBagConstraints.gridy = 0;
        lBagConstraints.insets = new Insets(5, 10, 5, 5);
        lBagConstraints.anchor = GridBagConstraints.EAST;
        lBagConstraints.weightx = 1.0;
        jPanel.add(new JLabel(Constants.get(lPropsFilePath, Constants.AboutFileName)), lBagConstraints);
        lBagConstraints.gridy = 1;
        jPanel.add(new JLabel(Constants.get(lPropsFiletype.name(), Constants.AboutFileType)), lBagConstraints);
        lBagConstraints.gridy = 2;
        jPanel.add(new JLabel(Constants.get(lPropsFilelocation.name(), Constants.AboutFileLocation)), lBagConstraints);
        lBagConstraints.gridy = 3;
        String x1 = (lPropsEncrypt == null) ? "False" : "True";
        jPanel.add(new JLabel(Constants.get(x1, Constants.AboutIsEncrypt)), lBagConstraints);
        lBagConstraints.gridy = 4;
        x1 = (KeyValue.listGroups().size() > 1)
                ? ((lisAbletoSave) ? Constants.DefaultStringString + (compscount - 2) : Constants.DefaultStringString + (compscount - 1))
                : ((lisAbletoSave) ? Constants.DefaultStringString + (compscount - 1) : Constants.DefaultStringString + (compscount));
        jPanel.add(new JLabel(Constants.get(x1, Constants.AboutPropertiesCount)), lBagConstraints);
        return jPanel;
    }

    /**
     * Adds Group Field to Display
     *
     * @param lPanel
     */
    private void CreateViewChangers(PropertyPanel lPanel) {
        GridBagConstraints lBagConstraints = new GridBagConstraints();
        lBagConstraints.fill = GridBagConstraints.CENTER;
        lBagConstraints.gridx = 0;
        lBagConstraints.gridy = compscount;
        lBagConstraints.insets = new Insets(5, 10, 5, 5);
        lBagConstraints.anchor = GridBagConstraints.EAST;
        lBagConstraints.weightx = 0;
        JLabel jLabel = new JLabel(Constants.get(Constants.GroupTypeString));
        lPanel.add(jLabel, lBagConstraints);
        lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        lBagConstraints.gridx = 1;
        lBagConstraints.gridy = compscount;
        lBagConstraints.insets = new Insets(5, 5, 5, 10);
        lBagConstraints.weightx = 1.0;
        if (lComboBox == null) {
            lComboBox = new JComboBox(new Vector(KeyValue.listGroups()));
            lComboBox.addItemListener(this);
        }
        lPanel.add(lComboBox, lBagConstraints);
        jLabel.setLabelFor(lComboBox);
        compscount++;
    }

    /**
     * Validates the text fields
     *
     * @return true if all validation matches
     */
    private boolean ValidateData() {
        boolean lReturn = true;
        try {
            for (Method lMethod : ListGetMethods) {
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField.displayName != null) {
                    Class<?> parameterTypes = lMethod.getReturnType();
                    if (parameterTypes.getName().equals(Constants.StringClassString) || parameterTypes.getName().equals(Constants.IntegerString) || parameterTypes.getName().equals(Constants.IntegerClassString) || parameterTypes.getName().equals(Constants.DoubleString) || parameterTypes.getName().equals(Constants.DoubleClassString) || parameterTypes.getName().equals(Constants.FloatString) || parameterTypes.getName().equals(Constants.FloatClassString)) {
                        if (CurrentField.isEncrypted) {
                            lReturn = lReturn && ((JPasswordField) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                        } else {
                            lReturn = lReturn && ((JTextField) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                        }
                    } else if (parameterTypes.getName().equals(Constants.FileClassString)) {
                        lReturn = lReturn && ((FilePanel) CurrentField.lComponent).getInputVerifier().verify(CurrentField.lComponent);
                    }
                }
            }
        } catch (Exception ex) {
            lReturn = false;
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lReturn;
    }

    /**
     * Packs the Data from Components to Bean for Updating
     */
    private void packData() {
        try {
            for (Method lMethod : ListSetMethods) {
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField.displayName != null && CurrentField.lComponent != null) {
                    Class<?>[] parameterTypes = lMethod.getParameterTypes();
                    if (parameterTypes[0].getName().equals(Constants.StringClassString)) {
                        if (CurrentField.isEncrypted) {
                            String Property = String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword());
                            lMethod.invoke(this, Property);
                        } else {
                            String Property = ((JTextField) CurrentField.lComponent).getText();
                            lMethod.invoke(this, Property);
                        }
                    } else if (parameterTypes[0].getName().equals(Constants.IntegerString) || parameterTypes[0].getName().equals(Constants.IntegerClassString)) {
                        if (CurrentField.isEncrypted) {
                            Integer Property = Integer.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(this, Property);
                        } else {
                            Integer Property = Integer.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(this, Property);
                        }
                    } else if (parameterTypes[0].getName().equals(Constants.DoubleString) || parameterTypes[0].getName().equals(Constants.DoubleClassString)) {
                        if (CurrentField.isEncrypted) {
                            Double Property = Double.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(this, Property);
                        } else {
                            Double Property = Double.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(this, Property);
                        }
                    } else if (parameterTypes[0].getName().equals(Constants.FloatString) || parameterTypes[0].getName().equals(Constants.FloatClassString)) {
                        if (CurrentField.isEncrypted) {
                            Float Property = Float.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(this, Property);
                        } else {
                            Float Property = Float.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(this, Property);
                        }
                    } else if (parameterTypes[0].getName().equals(Constants.BooleanString) || parameterTypes[0].getName().equals(Constants.BooleanClassString)) {
                        Boolean Property = Boolean.valueOf(((JCheckBox) CurrentField.lComponent).isSelected());
                        lMethod.invoke(this, Property);
                    } else if (parameterTypes[0].getName().equals(Constants.FileClassString)) {
                        String Property = ((FilePanel) CurrentField.lComponent).getFileName();
                        lMethod.invoke(this, Property.trim().isEmpty() ? null : new File(Property));
                    } else if (parameterTypes[0].isEnum()) {
                        String Property = String.valueOf(((JComboBox) CurrentField.lComponent).getSelectedItem());
                        Integer Property1 = getIndexofEnum(parameterTypes[0], Property);
                        lMethod.invoke(this, parameterTypes[0].getEnumConstants()[Property1]);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param pEnum
     * @param property
     * @return
     */
    private Integer getIndexofEnum(Class<?> pEnum, String property) {
        Vector lVector = new Vector();
        for (Object object : pEnum.getEnumConstants()) {
            lVector.add("" + object);
        }
        return (lVector.indexOf(property) < 0) ? 0 : lVector.indexOf(property);
    }

    /**
     *
     * @param pEnum
     * @return
     */
    private ComboBoxModel getComboValues(Class pEnum) {
        Vector lVector = new Vector();
        for (Object object : pEnum.getEnumConstants()) {
            lVector.add("" + object);
        }
        return new DefaultComboBoxModel(lVector);
    }

    /**
     * Reads from properties to Bean
     *
     * @return
     */
    private boolean readProperties() {
        try {
            for (Method lMethod : ListSetMethods) {
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField.key != null) {
                    CurrentField.lComponent = null;
                    Class<?>[] parameterTypes = lMethod.getParameterTypes();
                    if (parameterTypes[0].getName().equals(Constants.StringClassString)) {
                        String stringProperty = lPropertyReader.getStringProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, stringProperty);
                    } else if (parameterTypes[0].getName().equals(Constants.IntegerString) || parameterTypes[0].getName().equals(Constants.IntegerClassString)) {
                        Integer cast1 = lPropertyReader.getIntegerProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, cast1);
                    } else if (parameterTypes[0].getName().equals(Constants.DoubleString) || parameterTypes[0].getName().equals(Constants.DoubleClassString)) {
                        Double cast1 = lPropertyReader.getDoubleProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, cast1);
                    } else if (parameterTypes[0].getName().equals(Constants.FloatString) || parameterTypes[0].getName().equals(Constants.FloatClassString)) {
                        Float cast1 = lPropertyReader.getFloatProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, cast1);
                    } else if (parameterTypes[0].getName().equals(Constants.BooleanString) || parameterTypes[0].getName().equals(Constants.BooleanClassString)) {
                        Boolean cast1 = lPropertyReader.getBooleanProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, cast1);
                    } else if (parameterTypes[0].getName().equals(Constants.FileClassString)) {
                        String stringProperty = lPropertyReader.getStringProperty(CurrentField.key, CurrentField.isEncrypted);
                        lMethod.invoke(this, stringProperty.trim().isEmpty() ? null : new File(stringProperty));
                    } else if (parameterTypes[0].isEnum()) {
                        String stringProperty = lPropertyReader.getStringProperty(CurrentField.key, CurrentField.isEncrypted);
                        Integer Property1 = getIndexofEnum(parameterTypes[0], stringProperty);
                        lMethod.invoke(this, parameterTypes[0].getEnumConstants()[Property1]);
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * * Creates a Empty XML Properties File
     */
    private void createNewXMLFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(Constants.PropertiesRootString);
            doc.appendChild(rootElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, Constants.PropertiesStandard);
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(lPropsFilePath));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the Data type of the field
     *
     * @param lField name to find the Data type
     * @return Data type name as String
     */
    public String getDatatype(String lField) {
        String lReturn = new String();
        try {
            String lFieldName = this.getClass().getDeclaredField(lField).getName();
            int lPrefixIndex, lSufixIndex;
            if (lPrefixProps.length() == 0) {
                lPrefixIndex = 0;
            } else {
                lPrefixIndex = lPrefixProps.length() - 1;
            }
            if (lSufixProps.length() == 0) {
                lSufixIndex = 0;
            } else {
                lSufixIndex = lSufixProps.length();
            }
            String lMethodName = Constants.GetMethodStartString + lFieldName.substring(lPrefixIndex, lFieldName.length() - lSufixIndex);
            Method lMethod = this.getClass().getMethod(lMethodName);
            lReturn = lMethod.getReturnType().getName();
        } catch (Exception ex) {
            Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lReturn;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            try {
                for (Method lMethod : ListGetMethods) {
                    KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                    String invoke = String.valueOf(lMethod.invoke(this));
                    if (CurrentField.isBelongToGroup((String) e.getItem())) {
                        CurrentField.SetDisplay(true);
                    } else {
                        CurrentField.SetDisplay(false);
                    }
                }
                packData();
                if (displayingAsWindow) {
                    displaySettings(lisAbletoSave);
                } else {
                    PropertyPanel displayPanel = getDisplayPanel(lisAbletoSave);
                    displayPanel.replace((JComponent) e.getSource());
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Internal Class to Save the Property
     *
     * @author prabhu.p
     */
    class PropertySaveListner implements ActionListener {

        /**
         * Action Handler For Save Button
         *
         * @param e Triggered Event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(Constants.get(Constants.SaveButtonString))) {
                doAction();
            } else if (e.getActionCommand().equals(Constants.get(Constants.ResetButtonString))) {
                resetWindow((JComponent) e.getSource());
            }
        }

        private void resetWindow(JComponent source) {
            loadProperties(lPropsFilePath);
            if (displayingAsWindow) {
                displaySettings(lisAbletoSave);
            } else {
                PropertyPanel displayPanel = getDisplayPanel(lisAbletoSave);
                displayPanel.replace(source);
            }
        }

        /**
         * Implementation For Save Button
         */
        private void doAction() {
            if (ValidateData()) {
                packData();
                if (updateProperties()) {
                    JOptionPane.showMessageDialog(null, Constants.get(lPropsFile.getAbsolutePath(), Constants.UpdatedSuccessfullyString), Constants.get(Constants.UpdateSuccessString), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, Constants.get(lPropsFile.getAbsolutePath(), Constants.UpdateFailedString), Constants.get(Constants.ErrorString), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, Constants.get(Constants.ValidationErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
