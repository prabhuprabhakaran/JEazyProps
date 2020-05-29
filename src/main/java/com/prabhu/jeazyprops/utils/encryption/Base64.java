/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.utils.encryption;

import com.prabhu.jeazyprops.interfaces.JeazyEncryption;

/**
 * Base64 Encryption
 *
 * @author Prabhu Prabhakaran
 */
public final class Base64 implements JeazyEncryption {

    /**
     * Decrypts the String
     *
     * @param str String to be decrypt
     *
     * @return decrypted String
     */
    @Override
    public String decrypt(String str) {
        String lReturn = new String();
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(str);
        lReturn = new String(decodedBytes);
        return lReturn;
    }

    /**
     * Encrypts the String
     *
     * @param str String to be Encrypt
     *
     * @return Encrypted String
     */
    @Override
    public String encrypt(String str) {
        String lReturn = new String();
        lReturn = java.util.Base64.getEncoder().encodeToString(str.getBytes());
        return lReturn;
    }
}
