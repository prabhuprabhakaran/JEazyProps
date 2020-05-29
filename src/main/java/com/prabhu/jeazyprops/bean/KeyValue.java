/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.bean;

import com.prabhu.jeazyprops.props.Constants;
import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JComponent;

/**
 * The Basic Entity For Storing Key and value pair as Properties
 *
 * @author Prabhu Prabhakaran
 */
public class KeyValue {

    public String key;
    public String displayName;
    public String displayNameBackup;
    public boolean isEncrypted;
    public boolean isrelativePath;
    public JComponent lComponent;
    public SortedSet<String> lGroup;
    private static SortedSet<String> lAllGroups;

    static {
        lAllGroups = new TreeSet<String>();
        lAllGroups.add(Constants.get(Constants.AllString));
    }

    /**
     * Initializes a new Object with default string and no encryption
     *
     * @param pKey Property Key
     * @param pValue Property value
     */
    public KeyValue(String pKey, String pValue) {
        key = pKey;
        displayName = pValue;
        isEncrypted = false;
        lComponent = null;
        displayNameBackup = null;
        lGroup = new TreeSet<String>();
    }

    public KeyValue(String pKey, String pValue, String... Groups) {
        key = pKey;
        displayName = pValue;
        isEncrypted = false;
        lComponent = null;
        displayNameBackup = null;
        lGroup = new TreeSet<String>(Arrays.asList(Groups));
        lAllGroups.addAll(Arrays.asList(Groups));
    }

    /**
     * Initializes a new Object with default string
     *
     * @param pKey Property Key
     * @param pValue Property Value
     * @param pEncrypted is the Value Encrypted
     */
    public KeyValue(String pKey, String pValue, boolean pEncrypted) {
        key = pKey;
        displayName = pValue;
        isEncrypted = pEncrypted;
        lComponent = null;
        displayNameBackup = null;
        lGroup = new TreeSet<String>();

    }

    public KeyValue(String pKey, String pValue, boolean pEncrypted, String... Groups) {
        key = pKey;
        displayName = pValue;
        isEncrypted = pEncrypted;
        lComponent = null;
        displayNameBackup = null;
        lGroup = new TreeSet<String>(Arrays.asList(Groups));
        lAllGroups.addAll(Arrays.asList(Groups));
    }

    public void SetDisplay(Boolean pValue) {
        if (pValue && displayName == null) {
            displayName = displayNameBackup;
            displayNameBackup = null;
        } else if (!pValue && displayNameBackup == null) {
            displayNameBackup = displayName;
            displayName = null;
        }
    }

    public String getAnyDisplayName() {
        String lReturn = (displayName == null) ? ((displayNameBackup == null) ? null : displayNameBackup) : displayName;
        return lReturn;
    }

    public boolean isBelongToGroup(String pGroup) {
        if (pGroup.equals(Constants.get(Constants.AllString)) || lGroup.contains(pGroup)) {
            return true;
        } else {
            return false;
        }
    }

    public static Collection listGroups() {
        return lAllGroups;
    }

    @Override
    public String toString() {
        return getAnyDisplayName();
    }

    public boolean isRelativePath() {
        return isrelativePath;
    }

    public void setRelativePath(boolean isrelativePath) {
        this.isrelativePath = isrelativePath;
    }
}
