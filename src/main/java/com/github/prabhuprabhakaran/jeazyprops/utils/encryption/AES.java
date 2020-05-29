package com.prabhu.jeazyprops.utils.encryption;

import com.prabhu.jeazyprops.Constants;
import com.prabhu.jeazyprops.interfaces.JeazyEncryption;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES Encryption
 *
 * @author Prabhu Prabhakaran
 */
public final class AES implements JeazyEncryption {

    private static final String ALGORITHM = Constants.EncryptionAESString;
    Cipher ecipher;
    Cipher dcipher;
    public static char[] hexChar
            = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
            };
    private static final byte[] keyValue = new byte[]{
        '8', '/', 'M', 'N', '*', 'x', 'z', 'B', 'X', ',', '%', '@', '$', '~', ']', '|'
    };

    /**
     *
     */
    public AES() {
        try {
            Key key = new SecretKeySpec(keyValue, ALGORITHM);
            ecipher = Cipher.getInstance(ALGORITHM);
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher = Cipher.getInstance(ALGORITHM);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException pNoSuchAlgorithmException) {
        } catch (NoSuchPaddingException pNoSuchPaddingException) {
        } catch (InvalidKeyException pInvalidKeyException) {
        }
    }

    /**
     * try to encrypt the input string
     *
     * @return return encrypted string
     */
    @Override
    public String encrypt(String pValueToEnc) {
        try {
            byte[] lInputBytes = pValueToEnc.getBytes(Constants.CharacterSetString);
            byte[] lEncrpted = ecipher.doFinal(lInputBytes);
            StringBuffer lEncrytedData = new StringBuffer(lEncrpted.length * 2);
            for (int i = 0; i < lEncrpted.length; i++) {
                // look up high nibble char
                lEncrytedData.append(hexChar[(lEncrpted[i] & 0xf0) >>> 4]);
                // fill left with zero bits
                // look up low nibble char
                lEncrytedData.append(hexChar[lEncrpted[i] & 0x0f]);
            }
            return lEncrytedData.toString();
        } catch (BadPaddingException pBadPaddingException) {
        } catch (IllegalBlockSizeException pIllegalBlockSizeException) {
        } catch (UnsupportedEncodingException pUnsupportedEncodingException) {
        }
        return null;
    }

    /**
     * try to decrypt the input string
     *
     * @return return decrypted string
     */
    @Override
    public String decrypt(String pEncryptedValue) {
        try {
            byte lDecrypted[] = new byte[pEncryptedValue.length() / 2];
            for (int i = 0; i < (pEncryptedValue.length() / 2); i++) {
                byte lFirstNibble = Byte.parseByte(pEncryptedValue.substring(2 * i, 2 * i + 1), 16); // [x,y)
                byte lSecondNibble = Byte.parseByte(pEncryptedValue.substring(2 * i + 1, 2 * i + 2), 16);
                int lFinalByte = (lSecondNibble) | (lFirstNibble << 4);
                // bit-operations only with numbers,not bytes.
                lDecrypted[i] = (byte) lFinalByte;
            }
            byte[] lRecoveredBytes = dcipher.doFinal(lDecrypted);
            String lRecovered = new String(lRecoveredBytes);
            return lRecovered;
        } catch (BadPaddingException pBadPaddingException) {
        } catch (IllegalBlockSizeException pIllegalBlockSizeException) {
        }
        return null;
    }
}
