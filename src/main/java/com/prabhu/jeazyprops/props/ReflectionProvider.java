package com.prabhu.jeazyprops.props;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.bean.FilePanel;
import com.prabhu.jeazyprops.bean.JeazyProps;
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.bean.PropsElement;
import com.prabhu.jeazyprops.interfaces.PropertiesEntity;
import com.prabhu.jeazyprops.utils.Utils;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Prabhu Prabhakaran
 */
public final class ReflectionProvider
{

    PropertiesEntity lEntity;
    PropertyDisplayer lDisplayer;
    PropertyReader lReader;

    private final ArrayList<Method> ListGetMethods = new ArrayList<Method>();
    private final ArrayList<Method> ListSetMethods = new ArrayList<Method>();
    private final HashMap<String, KeyValue> ListKeyValue = new HashMap<String, KeyValue>();

    protected ReflectionProvider(PropertiesEntity pEntity, PropertyDisplayer pDisplayer)
    {
        lEntity = pEntity;
        lDisplayer = pDisplayer;
    }

    public PropertyDisplayer getDispalyer()
    {
        return lDisplayer;
    }

    public ArrayList<Method> getGetMethods()
    {
        return ListGetMethods;
    }

    public ArrayList<Method> getSetMethods()
    {
        return ListSetMethods;
    }

    public HashMap<String, KeyValue> getKeyValues()
    {
        return ListKeyValue;
    }

    public final boolean isHavingJAXBAnnotations()
    {
        boolean annotation = lEntity.getClass().isAnnotationPresent(XmlRootElement.class);
        return annotation;
    }

    public final boolean isHavingJPROPSAnnotations()
    {
        boolean annotation = lEntity.getClass().isAnnotationPresent(JeazyProps.class);
        return annotation;
    }

    /**
     * Populates all the KeyValue, Getter and Setter Methods to the corresponding Maps
     */
    public final void populateAllFields()
    {
        populateGetSetMethods();
        populateKeyValuePairs();
    }

    public boolean saveProperties()
    {
        return lDisplayer.updateProperties();
    }

    public void updateProperties() throws Exception
    {
        for (Method lMethod : ListGetMethods)
        {
            Object lValue = lMethod.invoke(lEntity);
            String invoke = "";
            if (lValue != null)
            {
                if (lValue instanceof Date)
                {
                    invoke = lDisplayer.getDateFormat().format(lValue);
                }
                else
                {
                    invoke = String.valueOf(lValue);
                }
            }
            KeyValue currentValue = ListKeyValue.get(lMethod.getName());
            if (currentValue != null && currentValue.lKey != null)
            {
                lReader.putStringProperty(currentValue.lKey, invoke, currentValue.isEncrypted);
            }
        }
    }

    public void readJAXBProperties()
    {
        try
        {
            JAXBContext jaxbCtx = JAXBContext.newInstance(new Class[]
            {
                lEntity.getClass()
            });
            Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            lEntity = (PropertiesEntity) unmarshaller.unmarshal(new java.io.File(lDisplayer.getPropsFilePath()));
        }
        catch (Exception ex)
        {
            System.out.println("Error in UnMarshall" + ex);
        }
    }

    public void updateJAXBProperties()
    {
        try
        {
            JAXBContext jaxbCtx = JAXBContext.newInstance(new Class[]
            {
                lEntity.getClass()
            });
            Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(lEntity, new FileWriter(lDisplayer.getPropsFilePath()));
        }
        catch (Exception ex)
        {
            System.out.println("Error in Marshall" + ex);
        }
    }

    /**
     * Reads from properties to Bean
     *
     * @return true on successful reading all properties.
     */
    public boolean readProperties()
    {
        try
        {
            for (Method lMethod : ListSetMethods)
            {
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField != null && CurrentField.lKey != null)
                {
                    CurrentField.lComponent = null;
                    Class<?>[] parameterTypes = lMethod.getParameterTypes();
                    if (parameterTypes[0].equals(String.class))
                    {
                        String stringProperty = lReader.getStringProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, stringProperty);
                    }
                    else if (parameterTypes[0].equals(int.class) || parameterTypes[0].equals(Integer.class))
                    {
                        Integer cast1 = lReader.getIntegerProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, cast1);
                    }
                    else if (parameterTypes[0].equals(double.class) || parameterTypes[0].equals(Double.class))
                    {
                        Double cast1 = lReader.getDoubleProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, cast1);
                    }
                    else if (parameterTypes[0].equals(float.class) || parameterTypes[0].equals(Float.class))
                    {
                        Float cast1 = lReader.getFloatProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, cast1);
                    }
                    else if (parameterTypes[0].equals(boolean.class) || parameterTypes[0].equals(Boolean.class))
                    {
                        Boolean cast1 = lReader.getBooleanProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, cast1);
                    }
                    else if (parameterTypes[0].equals(File.class))
                    {
                        String stringProperty = lReader.getStringProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, stringProperty.trim().isEmpty() ? null : new File(stringProperty));
                    }
                    else if (parameterTypes[0].equals(Date.class))
                    {
                        String stringProperty = lReader.getStringProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        lMethod.invoke(lEntity, stringProperty.trim().isEmpty() ? null : lDisplayer.getDateFormat().parse(stringProperty));
                    }
                    else if (parameterTypes[0].isEnum())
                    {
                        String stringProperty = lReader.getStringProperty(CurrentField.lKey, CurrentField.isEncrypted);
                        Integer Property1 = Utils.getIndexofEnum(parameterTypes[0], stringProperty);
                        lMethod.invoke(lEntity, parameterTypes[0].getEnumConstants()[Property1]);
                    }
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Packs the Data from Components to Bean for Updating
     */
    public void packData()
    {
        try
        {
            for (Method lMethod : ListSetMethods)
            {
                KeyValue CurrentField = ListKeyValue.get(lMethod.getName());
                if (CurrentField != null && CurrentField.lDisplayName != null && CurrentField.lComponent != null)
                {
                    Class<?>[] parameterTypes = lMethod.getParameterTypes();
                    if (parameterTypes[0].equals(String.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            String Property = String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword());
                            lMethod.invoke(lEntity, Property);
                        }
                        else
                        {
                            String Property = ((JTextField) CurrentField.lComponent).getText();
                            lMethod.invoke(lEntity, Property);
                        }
                    }
                    else if (parameterTypes[0].equals(int.class) || parameterTypes[0].equals(Integer.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            Integer Property = Integer.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(lEntity, Property);
                        }
                        else
                        {
                            Integer Property = Integer.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(lEntity, Property);
                        }
                    }
                    else if (parameterTypes[0].equals(double.class) || parameterTypes[0].equals(Double.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            Double Property = Double.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(lEntity, Property);
                        }
                        else
                        {
                            Double Property = Double.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(lEntity, Property);
                        }
                    }
                    else if (parameterTypes[0].equals(float.class) || parameterTypes[0].equals(Float.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            Float Property = Float.valueOf(String.valueOf(((JPasswordField) CurrentField.lComponent).getPassword()));
                            lMethod.invoke(lEntity, Property);
                        }
                        else
                        {
                            Float Property = Float.valueOf(((JTextField) CurrentField.lComponent).getText());
                            lMethod.invoke(lEntity, Property);
                        }
                    }
                    else if (parameterTypes[0].equals(boolean.class) || parameterTypes[0].equals(Boolean.class))
                    {
                        Boolean Property = Boolean.valueOf(((JCheckBox) CurrentField.lComponent).isSelected());
                        lMethod.invoke(lEntity, Property);
                    }
                    else if (parameterTypes[0].equals(File.class))
                    {
                        String Property = ((FilePanel) CurrentField.lComponent).getFileName();
                        lMethod.invoke(lEntity, Property.trim().isEmpty() ? null : new File(Property));
                    }
                    else if (parameterTypes[0].equals(Date.class))
                    {
                        Date Property = ((UtilDateModel) ((JDatePickerImpl) CurrentField.lComponent).getModel()).getValue();
                        lMethod.invoke(lEntity, Property);
                    }
                    else if (parameterTypes[0].isEnum())
                    {
                        String Property = String.valueOf(((JComboBox) CurrentField.lComponent).getSelectedItem());
                        Integer Property1 = Utils.getIndexofEnum(parameterTypes[0], Property);
                        lMethod.invoke(lEntity, parameterTypes[0].getEnumConstants()[Property1]);
                    }
                }
            }

            if (!isHavingJAXBAnnotations())
            {
                updateProperties();
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setReader(PropertyReader pPropertyReader)
    {
        lReader = pPropertyReader;
    }

    public Object getValue(Method pMethod) throws Exception
    {
        return pMethod.invoke(lEntity);
    }

    /**
     * Returns the Data type of the field
     *
     * @param lField name to find the Data type
     *
     * @return Data type name as String
     */
    public final Class<?> getDatatype(String lField)
    {
        Class<?> lReturn = null;
        try
        {
            String lFieldName = lField;//lEntity.getClass().getDeclaredField(lField).getName();
            int lPrefixIndex = (lDisplayer.getPrefix().length() == 0) ? 0 : (lDisplayer.getPrefix().length() - 1);
            int lSufixIndex = (lDisplayer.getSuffix().length() == 0) ? 0 : (lDisplayer.getSuffix().length());
            String lMethodName = Constants.GetMethodStartString + lFieldName.substring(lPrefixIndex, lFieldName.length() - lSufixIndex);
            Method lMethod = lEntity.getClass().getMethod(lMethodName);
            lReturn = lMethod.getReturnType();
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lReturn;
    }

    private void populateGetSetMethods()
    {
        ListGetMethods.clear();
        ListSetMethods.clear();
        try
        {
            Method[] methods = lEntity.getClass().getDeclaredMethods();
            for (Method method : methods)
            {
                if (method.getParameterTypes().length == 0 && (method.getName().startsWith(Constants.GetMethodStartString) || method.getName().startsWith(Constants.IsMethodStartString)))
                {
                    ListGetMethods.add(method);
                }
                if (method.getName().startsWith(Constants.SetMethodStartString))
                {
                    ListSetMethods.add(method);
                }
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateKeyValueFromEntity(List<Method> Methods)
    {
        try
        {
            for (Method method : Methods)
            {
                Field field;
                try
                {
                    if (method.getName().startsWith(Constants.IsMethodStartString))
                    {
                        field = lEntity.getClass().getDeclaredField(lDisplayer.getPrefix() + method.getName().substring(2) + lDisplayer.getSuffix());
                    }
                    else
                    {
                        field = lEntity.getClass().getDeclaredField(lDisplayer.getPrefix() + method.getName().substring(3) + lDisplayer.getSuffix());
                    }
                }
                catch (NoSuchFieldException ex)
                {
                    // Logger.getLogger(BaseProps.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
                if (field == null)
                {
                    continue;
                }
                KeyValue CurrentField = (KeyValue) field.get(lEntity);
                ListKeyValue.put(method.getName(), CurrentField);
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateKeyValuePairs()
    {
        ListKeyValue.clear();
        if (isHavingJPROPSAnnotations())
        {
            for (Method method : ListSetMethods)
            {
                PropsElement annotation = method.getAnnotation(PropsElement.class);
                if (annotation != null)
                {
                    KeyValue CurrentField = new KeyValue((annotation.keyName().equals("##default") ? method.getName().substring(3) : annotation.keyName()),
                                                         (annotation.displayName().equals("##default") ? method.getName().substring(3) : annotation.displayName()),
                                                         annotation.isEncrypted(),
                                                         annotation.groups());
                    ListKeyValue.put(method.getName(), CurrentField);

                    try
                    {
                        method = lEntity.getClass().getMethod(Constants.GetMethodStartString + method.getName().substring(3));
                    }
                    catch (Exception ex)
                    {
                        try
                        {
                            method = lEntity.getClass().getMethod(Constants.IsMethodStartString + method.getName().substring(3));
                        }
                        catch (Exception ex1)
                        {
                        }
                    }
                    ListKeyValue.put(method.getName(), CurrentField);
                }
            }
        }
        else
        {
            populateKeyValueFromEntity(ListGetMethods);
            populateKeyValueFromEntity(ListSetMethods);
        }
    }
}
