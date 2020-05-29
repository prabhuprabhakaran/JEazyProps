/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.ui.gui;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.bean.FilePanel;
import com.prabhu.jeazyprops.bean.JeazyDateModel;
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.props.PropertyDisplayer;
import com.prabhu.jeazyprops.props.ReflectionProvider;
import com.prabhu.jeazyprops.utils.Utils;
import com.prabhu.jeazyprops.utils.validation.Validator;
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
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.border.EtchedBorder;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Prabhu Prabhakaran
 */
public class GUICreator implements ItemListener
{

    private int compscount;
    private int CurrentSelectedIndex = 0;
    ReflectionProvider lReflectionProvider;
    PropertyDisplayer lDisplayer;
    private final PropertySaveListner lPropertySaveListner;
    private final FocusListener lFocusListener;
    private final Validator lValidator;
    private boolean lisAbletoSave = true;

    public GUICreator(ReflectionProvider pReflectionProvider)
    {
        lReflectionProvider = pReflectionProvider;
        lDisplayer = pReflectionProvider.getDispalyer();
        lPropertySaveListner = new PropertySaveListner();
        lValidator = new Validator(pReflectionProvider);
        lFocusListener = new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (e.getComponent() instanceof JTextField)
                {
                    ((JTextField) e.getComponent()).select(0, ((JTextField) e.getComponent()).getText().length());
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (e.getComponent() instanceof JTextField)
                {
                    ((JTextField) e.getComponent()).select(0, 0);
                }
            }
        };
    }

    /**
     * Adds Group Field to Display
     *
     * @param lPanel
     */
    private void CreateViewChangers(PropertyPanel lPanel)
    {
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
        JComboBox lComboBox = new JComboBox(new Vector(KeyValue.listGroups()));
        lComboBox.setSelectedIndex(CurrentSelectedIndex);
        lComboBox.addItemListener(this);
        lPanel.add(lComboBox, lBagConstraints);
        jLabel.setLabelFor(lComboBox);
        compscount++;
    }

    /**
     * Gets the properties panel to Integrate with Application
     *
     * @param isAbleToSave True for enable save option, False for View Only
     *
     * @return the properties panel with the UI components
     */
    public final PropertyPanel getDisplayPanel(boolean isAbleToSave)
    {
        lisAbletoSave = isAbleToSave;
        PropertyPanel lPanel = new PropertyPanel();
        compscount = 0;
        try
        {
            lPanel.setLayout(new GridBagLayout());
            if (KeyValue.listGroups().size() > 1)
            {
                CreateViewChangers(lPanel);
            }
            GridBagConstraints lBagConstraints = new GridBagConstraints();
            lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            lBagConstraints.weightx = 1.0;
            for (Method lMethod : lReflectionProvider.getGetMethods())
            {
                Object invoke = lReflectionProvider.getValue(lMethod);
                KeyValue CurrentField = lReflectionProvider.getKeyValues().get(lMethod.getName());
                lBagConstraints.fill = GridBagConstraints.CENTER;
                lBagConstraints.gridx = 0;
                lBagConstraints.gridy = compscount;
                lBagConstraints.insets = new Insets(5, 10, 5, 5);
                lBagConstraints.anchor = GridBagConstraints.EAST;
                lBagConstraints.weightx = 0;
                if (CurrentField != null && !CurrentField.lDisplayName.isEmpty())
                {
                    int indexOf = CurrentField.lDisplayName.indexOf(Constants.MnenomicSymbolString);
                    JLabel jLabel = null;
                    if (indexOf != -1)
                    {
                        char menemonic = CurrentField.lDisplayName.charAt(indexOf + 1);
                        jLabel = new JLabel(CurrentField.lDisplayName.replaceAll(Constants.MnenomicSymbolString, Constants.MnenomicReplaceSymbolString) + Constants.DisplayNameAppendString);
                        jLabel.setDisplayedMnemonic(menemonic);
                    }
                    else
                    {
                        jLabel = new JLabel(CurrentField.lDisplayName + Constants.DisplayNameAppendString);
                    }
                    lPanel.add(jLabel, lBagConstraints);
                    lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                    lBagConstraints.gridx = 1;
                    lBagConstraints.gridy = compscount;
                    lBagConstraints.insets = new Insets(5, 5, 5, 10);
                    lBagConstraints.weightx = 1.0;
                    if (lMethod.getReturnType().isEnum())
                    {
                        CurrentField.lComponent = new JComboBox(Utils.getComboValues(lMethod.getReturnType()));
                        if (invoke == null)
                        {
                            invoke = lMethod.getReturnType().getEnumConstants()[0];
                        }
                        ((JComboBox) CurrentField.lComponent).setSelectedItem("" + invoke);

                    }
                    else if (lMethod.getReturnType().equals(String.class) || lMethod.getReturnType().equals(Integer.class) || lMethod.getReturnType().equals(int.class) || lMethod.getReturnType().equals(double.class) || lMethod.getReturnType().equals(Double.class) || lMethod.getReturnType().equals(float.class) || lMethod.getReturnType().equals(Float.class))
                    {
                        if (CurrentField.isEncrypted)
                        {
                            CurrentField.lComponent = new JPasswordField(String.valueOf(invoke));
                            CurrentField.lComponent.setInputVerifier(lValidator);
                            CurrentField.lComponent.addFocusListener(lFocusListener);
                        }

                        else
                        {
                            CurrentField.lComponent = new JTextField(String.valueOf(invoke));
                            CurrentField.lComponent.setInputVerifier(lValidator);
                            CurrentField.lComponent.addFocusListener(lFocusListener);
                        }
                    }

                    else if (lMethod.getReturnType().equals(Boolean.class) || lMethod.getReturnType().equals(boolean.class))
                    {
                        CurrentField.lComponent = new JCheckBox();

                        ((JCheckBox) CurrentField.lComponent).setSelected(Boolean.valueOf(String.valueOf(invoke)));
                    }
                    else if (lMethod.getReturnType().equals(File.class))
                    {
                        CurrentField.lComponent = new FilePanel(CurrentField.isEncrypted);

                        CurrentField.lComponent.setInputVerifier(lValidator);

                        ((FilePanel) CurrentField.lComponent).setFileName(invoke);
                    }
                    else if (lMethod.getReturnType().equals(Date.class))
                    {
                        CurrentField.lComponent = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), new JeazyDateModel(lDisplayer.getDateFormat()));

                        CurrentField.lComponent.setInputVerifier(lValidator);

                        ((UtilDateModel) ((JDatePickerImpl) CurrentField.lComponent).getModel()).setValue((Date) invoke);
                        ((UtilDateModel) ((JDatePickerImpl) CurrentField.lComponent).getModel()).setSelected(true);
                    }
                    CurrentField.lComponent.getAccessibleContext().setAccessibleName(lDisplayer.getPrefix() + lMethod.getName().substring(3) + lDisplayer.getSuffix());

                    if (CurrentField.lComponent instanceof FilePanel)
                    {
                        jLabel.setLabelFor(((FilePanel) CurrentField.lComponent).getActionComponent());
                    }
                    else if (CurrentField.lComponent instanceof FilePanel)
                    {
                        jLabel.setLabelFor(((JDatePickerImpl) CurrentField.lComponent).getJFormattedTextField());
                    }

                    else
                    {
                        jLabel.setLabelFor(CurrentField.lComponent);
                    }

                    lPanel.add(CurrentField.lComponent, lBagConstraints);
                    compscount++;
                }
            }
            if (isAbleToSave)
            {
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
        }
        catch (Exception ex)
        {
            Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        lPanel.addMouseListener(
                new MouseAdapter()
                {
                    @Override
                    public void mouseReleased(MouseEvent e
                    )
                    {
                        if (e.getButton() == MouseEvent.BUTTON3)
                        {
                            showAboutPanel(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
        );
        return lPanel;
    }

    /**
     * Shows the About Panel
     *
     * @param source Component to Show
     * @param x      x Position to show
     * @param y      y Position to show
     */
    private void showAboutPanel(Component source, int x, int y)
    {
        JPopupMenu lJPopupMenu = new JPopupMenu();
        lJPopupMenu.add(new AboutPanel(createDetailPanel()));
        lJPopupMenu.show(source, x, y);
    }

    /**
     * Creates the detail view of the about panel.
     *
     * @return the jpanel with the settings displayed
     */
    private JPanel createDetailPanel()
    {
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lBagConstraints = new GridBagConstraints();
        lBagConstraints.fill = GridBagConstraints.BOTH;
        lBagConstraints.gridx = 0;
        lBagConstraints.gridy = 0;
        lBagConstraints.insets = new Insets(5, 10, 5, 5);
        lBagConstraints.anchor = GridBagConstraints.EAST;
        lBagConstraints.weightx = 1.0;
        jPanel.add(new JLabel(Constants.get(lDisplayer.getPropsFilePath(), Constants.AboutFileName)), lBagConstraints);
        lBagConstraints.gridy = 1;
        jPanel.add(new JLabel(Constants.get(lDisplayer.getPropsFiletype().name(), Constants.AboutFileType)), lBagConstraints);
        lBagConstraints.gridy = 2;
        jPanel.add(new JLabel(Constants.get(lDisplayer.getPropsFilelocation().name(), Constants.AboutFileLocation)), lBagConstraints);
        lBagConstraints.gridy = 3;
        String x1 = (lDisplayer.getPropsEncrypt() == null) ? "False" : "True";
        jPanel.add(new JLabel(Constants.get(x1, Constants.AboutIsEncrypt)), lBagConstraints);
        lBagConstraints.gridy = 4;
        x1 = (KeyValue.listGroups().size() > 1)
             ? ((lisAbletoSave) ? Constants.DefaultStringString + (compscount - 2) : Constants.DefaultStringString + (compscount - 1))
             : ((lisAbletoSave) ? Constants.DefaultStringString + (compscount - 1) : Constants.DefaultStringString + (compscount));
        jPanel.add(new JLabel(Constants.get(x1, Constants.AboutPropertiesCount)), lBagConstraints);
        return jPanel;
    }

    @Override
    public final void itemStateChanged(ItemEvent e)
    {
        if (e.getStateChange() == ItemEvent.SELECTED)
        {
            try
            {
                for (Method lMethod : lReflectionProvider.getGetMethods())
                {
                    KeyValue CurrentField = lReflectionProvider.getKeyValues().get(lMethod.getName());
                    if (CurrentField.isBelongToGroup((String) e.getItem()))
                    {
                        CurrentField.SetDisplay(true);
                    }
                    else
                    {
                        CurrentField.SetDisplay(false);
                    }
                }
                CurrentSelectedIndex = ((JComboBox) e.getSource()).getSelectedIndex();
                lReflectionProvider.packData();
                if (lDisplayer.isDisplayingAsWindow())
                {
                    lDisplayer.displaySettings(lisAbletoSave);
                }
                else
                {
                    PropertyPanel displayPanel = getDisplayPanel(lisAbletoSave);
                    displayPanel.replace((JComponent) e.getSource());
                }
            }
            catch (Exception ex)
            {
                Logger.getLogger(PropertyDisplayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Internal Class to Save the Property
     *
     * @author Prabhu Prabhakaran
     */
    class PropertySaveListner implements ActionListener
    {

        /**
         * Action Handler For Save Button
         *
         * @param e Triggered Event
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals(Constants.get(Constants.SaveButtonString)))
            {
                doAction();
            }
            else if (e.getActionCommand().equals(Constants.get(Constants.ResetButtonString)))
            {
                resetWindow((JComponent) e.getSource());
            }
        }

        private void resetWindow(JComponent source)
        {
            CurrentSelectedIndex = 0;
            lDisplayer.loadProperties(lDisplayer.getPropsFilePath());
            if (lDisplayer.isDisplayingAsWindow())
            {
                lDisplayer.displaySettings(lisAbletoSave);
            }
            else
            {
                PropertyPanel displayPanel = getDisplayPanel(lisAbletoSave);
                displayPanel.replace(source);
            }
        }

        /**
         * Implementation For Save Button
         */
        private void doAction()
        {
            if (lValidator.ValidateData())
            {
                lReflectionProvider.packData();
                if (lReflectionProvider.saveProperties())
                {
                    JOptionPane.showMessageDialog(null, Constants.get(lDisplayer.getPropsFilePath(), Constants.UpdatedSuccessfullyString), Constants.get(Constants.UpdateSuccessString), JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, Constants.get(lDisplayer.getPropsFilePath(), Constants.UpdateFailedString), Constants.get(Constants.ErrorString), JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, Constants.get(Constants.ValidationErrorString), Constants.get(Constants.AlertString), JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
