package com.ping23.crypt.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {

    private final static String RESOURCES_DIR = "src/main/resources";

    private final static String publicKeyFilename =
            RESOURCES_DIR + "/encryption/rsa-public-key.pem";
    private final static String privateKeyFilename =
            RESOURCES_DIR + "/encryption/rsa-private-key.pem";

    public static String encrypt(String plainText)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            FileNotFoundException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, IOException {

        if (plainText == null || plainText.isEmpty()) {
            return null;
        }

        String encryptedText = null;
        encryptedText =
                RSAEncryptDecrypt.encryptTextWithPemFile(plainText, publicKeyFilename);

        return encryptedText;
    }


    public static String decrypt(String encryptedText)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            FileNotFoundException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, IOException {

        if (encryptedText == null || encryptedText.isEmpty()) {
            return null;
        }

        String decryptedText = null;

        decryptedText =
                RSAEncryptDecrypt.decryptTextWithPemFile(encryptedText, privateKeyFilename);

        return decryptedText;
    }

}
