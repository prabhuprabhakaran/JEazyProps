/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.utils.encryption;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.interfaces.JeazyEncryption;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 3DES Encryption
 *
 * @author Prabhu Prabhakaran
 */
public final class Tripledes implements JeazyEncryption {

    Cipher decipher;
    Cipher encipher;

    /**
     * Initializes a new Object
     *
     * @param pFile Key File for TripleDes Encryption
     */
    public Tripledes(File pFile) {
        SecretKey key;
        try {
            key = Tripledes.readKey(pFile);
            decipher = Cipher.getInstance(Constants.EncryptionDESString);
            decipher.init(Cipher.DECRYPT_MODE, key);
            encipher = Cipher.getInstance(Constants.EncryptionDESString);
            encipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (IOException ex) {
        } catch (InvalidKeySpecException ex) {
        } catch (InvalidKeyException ex) {
        } catch (NoSuchAlgorithmException ex) {
        } catch (NoSuchPaddingException ex) {
        }
    }

    /**
     * Decrypts the String
     *
     * @param str String to be decrypt
     *
     * @return decrypted String
     */
    public String decrypt(String str) {
        try {
            byte[] decipher1 = java.util.Base64.getDecoder().decode(str);
            byte[] decryptedBytes = decipher.doFinal(decipher1);
            return new String(decryptedBytes, Constants.CharacterSetString);
        } catch (IllegalBlockSizeException ex) {
        } catch (BadPaddingException ex) {
        } catch (IOException ex) {
        }
        return null;
    }

    /**
     * Encrypts the String
     *
     * @param str String to be Encrypt
     *
     * @return Encrypted String
     */
    public String encrypt(String str) {
        try {
            byte[] encipher1 = str.getBytes(Constants.CharacterSetString);
            byte[] encrpyedBytes = encipher.doFinal(encipher1);
            return java.util.Base64.getEncoder().encodeToString(encrpyedBytes);
        } catch (UnsupportedEncodingException ex) {
        } catch (IllegalBlockSizeException ex) {
        } catch (BadPaddingException ex) {
        }
        return null;
    }

    /**
     * Generates a DES Key Pass Phrase to File
     *
     * @param pFile Filename to be stored
     */
    public static void generateKeytoFile(String pFile) {
        try {
            SecretKey key = generateKey();
            Tripledes.writeKey(key, new File(pFile));
        } catch (IOException ex) {
        } catch (InvalidKeySpecException ex) {
        } catch (NoSuchAlgorithmException ex) {
        }
    }

    /**
     * Generates a DES Key Pass Phrase
     *
     * @return
     * <p>
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey()
            throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(Constants.EncryptionDESString);
        return keygen.generateKey();
    }

    /**
     * Writes the key to File
     *
     * @param key Key to be write as file
     * @param f file to be stored
     */
    private static void writeKey(SecretKey key, File f)
            throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(Constants.EncryptionDESString);
        DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
                DESedeKeySpec.class);
        byte[] rawkey = keyspec.getKey();
        FileOutputStream out = new FileOutputStream(f);
        out.write(rawkey);
        out.close();
    }

    /**
     * Reads the Pass Phrase from File
     *
     * @param f file to be read
     *
     * @return pass phrase
     */
    private static SecretKey readKey(File f)
            throws IOException,
            NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException {
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] rawkey = new byte[(int) f.length()];
        in.readFully(rawkey);
        in.close();
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(Constants.EncryptionDESString);
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }
}
