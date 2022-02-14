package com.ping23.crypt.processor;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.DecoderException;

public class AESEncryptDecrypt {

    /**
     * 
     * @param plainText
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws DecoderException
     */
    public static byte[] encryptToByteArray(String plainText, SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {

        if (plainText == null || plainText.isEmpty()) {
            return null;
        }

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        return byteCipherText;
    }

    /**
     * 
     * @param plainText
     * @param aesKey
     * @return
     * @throws IOException
     * @throws DecoderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encryptToString(String plainText, String aesKey)
            throws IOException, DecoderException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        SecretKey secretKey = loadKeyFromString(aesKey);

        return encryptToString(plainText, secretKey);

    }


    /**
     * 
     * @param plainText
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws DecoderException
     */
    public static String encryptToString(String plainText, SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {

        if (plainText == null || plainText.isEmpty()) {
            return null;
        }

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        String encodedCipherText = DatatypeConverter.printBase64Binary(byteCipherText);

        return encodedCipherText;
    }

    /**
     * 
     * @param byteCipherText
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws DecoderException
     */
    public static String decryptFromByteArray(byte[] byteCipherText, SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {

        if (byteCipherText == null || byteCipherText.length < 1) {
            return null;
        }

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        String plainText = new String(bytePlainText);

        return plainText;
    }

    /**
     * 
     * @param encryptedText
     * @param aesKey
     * @return
     * @throws IOException
     * @throws DecoderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decryptFromString(String encryptedText, String aesKey)
            throws IOException, DecoderException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        SecretKey secretKey = loadKeyFromString(aesKey);

        return decryptFromString(encryptedText, secretKey);

    }

    /**
     * 
     * @param encryptedText
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws DecoderException
     */
    public static String decryptFromString(String encryptedText, SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {

        if (encryptedText == null || encryptedText.isEmpty()) {
            return null;
        }

        byte[] byteCipherText = DatatypeConverter.parseBase64Binary(encryptedText);

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        String plainText = new String(bytePlainText);

        return plainText;
    }

    /**
     * 
     * @param filename
     * @return
     * @throws IOException
     * @throws DecoderException
     */
    public static SecretKey loadKeyFromFile(String filename) throws IOException, DecoderException {

        File file;

        // have to try getting the file a couple of ways
        // url is for web app context
        URL url = AESEncryptDecrypt.class.getResource(filename);
        if (url != null) {
            try {
                file = new File(url.toURI());
            } catch (URISyntaxException e) {
                file = new File(url.getPath());
            }
        } else {
            // standalone java context
            file = new File(filename);
        }

        String data = new String(readFileToByteArray(file));
        byte[] encoded;
        encoded = decodeHex(data.toCharArray());
        return new SecretKeySpec(encoded, "AES");
    }

    /**
     * 
     * @param filename
     * @return
     * @throws IOException
     * @throws DecoderException
     */
    public static SecretKey loadKeyFromString(String aesKey) throws IOException, DecoderException {

        byte[] encoded;
        encoded = decodeHex(aesKey.toCharArray());
        return new SecretKeySpec(encoded, "AES");
    }

}
