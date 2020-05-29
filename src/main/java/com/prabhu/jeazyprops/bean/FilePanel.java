/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.bean;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.utils.FileUtils;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.Highlighter;

/**
 * File Chooser Component Class
 *
 * @author Prabhu Prabhakaran
 */
public final class FilePanel extends JPanel
{

    JTextField jTextField;
    JButton jButton;
    JFileChooser jFileChooser;
    boolean lEncrypt;
    boolean isrelativePath;

    /**
     * Creates File chooser Panel
     *
     * @param pEncrypt to specify the file name is encrypted or not
     */
    public FilePanel(boolean pEncrypt)
    {
        lEncrypt = pEncrypt;
        if (pEncrypt)
        {
            jTextField = new JPasswordField();
        }
        else
        {
            jTextField = new JTextField();
        }
        jTextField.setToolTipText(Constants.get(Constants.ToggleFilePathString));
        jTextField.setEditable(false);
        jButton = new JButton(Constants.get(Constants.FileBrowseButtonString));
        setLayout(new GridBagLayout());
        GridBagConstraints lBagConstraints = new GridBagConstraints();
        lBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        lBagConstraints.gridx = 0;
        lBagConstraints.gridy = 0;
        lBagConstraints.insets = new Insets(0, 0, 0, 0);
        lBagConstraints.weightx = 1.0;
        if (pEncrypt)
        {
            add((JPasswordField) jTextField, lBagConstraints);
        }
        else
        {
            add(jTextField, lBagConstraints);
        }
        lBagConstraints.insets = new Insets(0, 5, 0, 0);
        lBagConstraints.weightx = 0.01;
        lBagConstraints.gridx = 1;
        add(jButton, lBagConstraints);
        jTextField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                e.consume();
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    jTextField.setText(null);
                }
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                }
                else
                {
                    ShowDialog();
                }
            }

            public void keyReleased(KeyEvent e)
            {
                e.consume();
            }

            @Override
            public void keyTyped(KeyEvent e)
            {
                e.consume();
            }
        });
        jTextField.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    setRelativePath(!isRelativePath());
                    refreshFileName();
                }
            }
        });
        jButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ShowDialog();
            }
        });
    }

    private void ShowDialog()
    {
        if (jFileChooser == null)
        {
            if (jTextField.getText() != null && !jTextField.getText().isEmpty())
            {
                jFileChooser = new JFileChooser(jTextField.getText());
            }
            else
            {
                jFileChooser = new JFileChooser();
            }
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
        if (!jFileChooser.isShowing())
        {
            int returnVal = jFileChooser.showOpenDialog(jButton.getRootPane());
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                if (isRelativePath())
                {
                    String property = System.getProperty(Constants.UserDirectoryString);
                    String relativePath = FileUtils.relativePath(new File(property), jFileChooser.getSelectedFile());
                    jTextField.setText(relativePath);
                }
                else
                {
                    try
                    {
                        jTextField.setText(jFileChooser.getSelectedFile().getCanonicalPath());
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Sets File name to the TextBox
     *
     * @param pFile Sets the Selected File Name
     */
    public void setFileName(Object pFile)
    {
        if (pFile != null)
        {
            if (((File) pFile).getPath().startsWith(Constants.ParentDirectoryString))
            {
                jTextField.setText(((File) pFile).getPath());
                setRelativePath(true);
            }
            else
            {
                setRelativePath(false);
                try
                {
                    jTextField.setText(((File) pFile).getCanonicalPath());
                }
                catch (IOException ex)
                {
                    Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void refreshFileName()
    {
        if (isRelativePath())
        {
            String property = System.getProperty(Constants.UserDirectoryString);
            String relativePath = FileUtils.relativePath(new File(property), new File(getFileName()));
            jTextField.setText(relativePath);
        }
        else
        {
            try
            {
                jTextField.setText(new File(getFileName()).getCanonicalPath());
            }
            catch (IOException ex)
            {
                Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Gets File name from TextBox
     *
     * @return returns the File Name selected
     */
    public String getFileName()
    {
        return jTextField.getText();
    }

    public JComponent getActionComponent()
    {
        return jButton;
    }

    public Highlighter getHighlighter()
    {
        return jTextField.getHighlighter();
    }

    public boolean isRelativePath()
    {
        return isrelativePath;
    }

    public void setRelativePath(boolean isrelativePath)
    {
        this.isrelativePath = isrelativePath;
    }
}
