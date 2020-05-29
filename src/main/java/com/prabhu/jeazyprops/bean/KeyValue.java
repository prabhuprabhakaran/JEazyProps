/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.bean;

import com.prabhu.jeazyprops.Constants;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JComponent;

/**
 * The Basic Entity For Storing Key and value pair as Properties
 *
 * @author Prabhu Prabhakaran
 */
public final class KeyValue
{

    public final String lKey;
    public String lDisplayName;
    public String lDisplayNameBackup;
    public final boolean isEncrypted;
    public boolean isrelativePath;
    public JComponent lComponent;
    public final SortedSet<String> lGroup;
    private static final Set<String> lAllGroups;

    static
    {
        lAllGroups = new HashSet<String>();
        lAllGroups.add(Constants.get(Constants.AllString));
    }

    public KeyValue(String pKey)
    {
        lKey = pKey;
        lDisplayName = "";
        isEncrypted = false;
        lComponent = null;
        lDisplayNameBackup = "";
        lGroup = new TreeSet<String>();
    }

    /**
     * Initializes a new Object with default string and no encryption
     *
     * @param pKey         Property Key
     * @param pDisplayName Property value
     */
    public KeyValue(String pKey, String pDisplayName)
    {
        lKey = pKey;
        lDisplayName = pDisplayName;
        isEncrypted = false;
        lComponent = null;
        lDisplayNameBackup = "";
        lGroup = new TreeSet<String>();
    }

    /**
     *
     * @param pKey
     * @param pDisplayName
     * @param pGroups
     */
    public KeyValue(String pKey, String pDisplayName, String... pGroups)
    {
        lKey = pKey;
        lDisplayName = pDisplayName;
        isEncrypted = false;
        lComponent = null;
        lDisplayNameBackup = "";
        lGroup = new TreeSet<String>(Arrays.asList(pGroups));
        lAllGroups.addAll(Arrays.asList(pGroups));
    }

    /**
     * Initializes a new Object with default string
     *
     * @param pKey         Property Key
     * @param pDisplayName Property Value
     * @param pEncrypted   is the Value Encrypted
     */
    public KeyValue(String pKey, String pDisplayName, boolean pEncrypted)
    {
        lKey = pKey;
        lDisplayName = pDisplayName;
        isEncrypted = pEncrypted;
        lComponent = null;
        lDisplayNameBackup = "";
        lGroup = new TreeSet<String>();

    }

    /**
     *
     * @param pKey
     * @param pDisplayName
     * @param pEncrypted
     * @param pGroups
     */
    public KeyValue(String pKey, String pDisplayName, boolean pEncrypted, String... pGroups)
    {
        lKey = pKey;
        lDisplayName = pDisplayName;
        isEncrypted = pEncrypted;
        lComponent = null;
        lDisplayNameBackup = "";
        lGroup = new TreeSet<String>(Arrays.asList(pGroups));
        if (pGroups.length > 0 && !pGroups[0].isEmpty())
        {
            lAllGroups.addAll(Arrays.asList(pGroups));
        }
    }

    /**
     *
     * @param isDisplayable
     */
    public void SetDisplay(Boolean isDisplayable)
    {
        if (isDisplayable && lDisplayName.isEmpty())
        {
            lDisplayName = lDisplayNameBackup;
            lDisplayNameBackup = "";
        }
        else if (!isDisplayable && lDisplayNameBackup.isEmpty())
        {
            lDisplayNameBackup = lDisplayName;
            lDisplayName = "";
        }
    }

    /**
     *
     * @return
     */
    public String getAnyDisplayName()
    {
        String lReturn = (lDisplayName.isEmpty()) ? ((lDisplayNameBackup.isEmpty()) ? null : lDisplayNameBackup) : lDisplayName;
        return lReturn;
    }

    /**
     *
     * @param pGroup
     *
     * @return
     */
    public boolean isBelongToGroup(String pGroup)
    {
        if (pGroup.equals(Constants.get(Constants.AllString)) || lGroup.contains(pGroup))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static Collection listGroups()
    {
        return lAllGroups;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()
    {
        return getAnyDisplayName();
    }

    /**
     *
     * @return
     */
    public boolean isRelativePath()
    {
        return isrelativePath;
    }

    /**
     *
     * @param isrelativePath
     */
    public void setRelativePath(boolean isrelativePath)
    {
        this.isrelativePath = isrelativePath;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new KeyValue(lKey, lDisplayName, isEncrypted, (String[]) lGroup.toArray());
    }

    @Override
    public int hashCode()
    {
        return lKey.hashCode();
    }

    @Override
    public boolean equals(Object pObj)
    {
        if (pObj instanceof KeyValue)
        {
            return ((KeyValue) pObj).lKey.equals(this.lKey);
        }
        return false;
    }

}
