package com.ping23.crypt.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ping23.crypt.processor.AESEncryptDecrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.apache.commons.codec.DecoderException;

@SpringBootTest
public class AESTest {

    private final static String AESKeyFilename = "src/main/resources/encryption/aes-key.hex";

    @BeforeEach
    void setUp() throws Exception {
        // empty
    }

    @AfterEach
    void tearDown() throws Exception {
        // empty
    }

    /**
     * 
     */
    @Test
    void testWithStrings() {

        String encryptedText = null;
        try {
            SecretKey secretKey = AESEncryptDecrypt.loadKeyFromFile(AESKeyFilename);
            encryptedText = AESEncryptDecrypt.encryptToString(SampleText.EXTRA_LONG_SAMPLE_TEXT,
            		secretKey);
            assertNotNull(encryptedText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException
                | DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("encryptedText: " + encryptedText.toString());

        String decryptedText = null;

        try {
            SecretKey secretKey = AESEncryptDecrypt.loadKeyFromFile(AESKeyFilename);
            decryptedText = AESEncryptDecrypt.decryptFromString(encryptedText, secretKey);
            assertNotNull(decryptedText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException
                | DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("decryptedText: " + decryptedText);

    }

    /**
     * 
     */
    @Test
    void testWithByteArray() {

        byte[] encryptedByteArray = null;
        try {
            SecretKey secretKey = AESEncryptDecrypt.loadKeyFromFile(AESKeyFilename);
            encryptedByteArray =
                    AESEncryptDecrypt.encryptToByteArray(SampleText.LONG_SAMPLE_TEXT, secretKey);
            assertNotNull(encryptedByteArray);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException
                | DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("encryptedByteArray: " + encryptedByteArray.toString());

        String decryptedText = null;

        try {
            SecretKey secretKey = AESEncryptDecrypt.loadKeyFromFile(AESKeyFilename);
            decryptedText = AESEncryptDecrypt.decryptFromByteArray(encryptedByteArray, secretKey);
            assertNotNull(decryptedText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException
                | DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("decryptedText: " + decryptedText);

    }

}
