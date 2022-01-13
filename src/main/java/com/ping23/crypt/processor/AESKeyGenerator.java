package com.ping23.crypt.processor;

import static org.apache.commons.codec.binary.Hex.encodeHex;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESKeyGenerator {

    public static void main(String[] args) {
        final String AESKeyFilename = "src/main/resources/encryption/aes-key.hex";

        SecretKey secretKey = null;
        try {
            secretKey = generateSecretKey(AESKeyFilename);
        } catch (NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (secretKey != null) {
            System.out.println("secretKey: " + secretKey.toString());
        }
    }

    /**
     * 
     * @param filename
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static SecretKey generateSecretKey(String filename)
            throws NoSuchAlgorithmException, IOException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits; 128 bits is considered very safe.
        SecretKey secretKey = generator.generateKey();

        // save the key
        saveKey(secretKey, filename);

        return secretKey;
    }

    /**
     * 
     * @param key
     * @param file
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static void saveKey(SecretKey key, String filename) throws IOException {
        File file = new File(filename);
        char[] hex = encodeHex(key.getEncoded());
        writeStringToFile(file, String.valueOf(hex));
    }

}
