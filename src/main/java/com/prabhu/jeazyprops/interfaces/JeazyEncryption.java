/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.interfaces;

/**
 * Base for the Encryption Classes
 *
 * @author Prabhu Prabhakaran
 */
public interface JeazyEncryption
{

    /**
     * Decrypts the String
     *
     * @param pDataString String to be decrypt
     *
     * @return decrypted String
     */
    public abstract String decrypt(String pDataString);

    /**
     * Encrypts the String
     *
     * @param pDataString String to be Encrypt
     *
     * @return Encrypted String
     */
    public abstract String encrypt(String pDataString);
}
