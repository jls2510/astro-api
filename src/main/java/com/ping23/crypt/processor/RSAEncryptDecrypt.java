package com.ping23.crypt.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAEncryptDecrypt {

    static {
        Security.addProvider(new BouncyCastleProvider());
        System.out.println("BouncyCastle provider added.");
    }

    /**
     * Decrypt the given encrypted text with the given Key file
     * 
     * @param encryptedText
     * @param privateKeyFilename
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws FileNotFoundException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static String decryptTextWithPemFile(String encryptedText, String privateKeyFilename)
            throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException,
            InvalidKeySpecException, IOException, InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {

        PrivateKey privateKey = getPrivateKeyFromPemFile(privateKeyFilename);
        return decryptTextWithPrivateKey(encryptedText, privateKey);

    }

    /**
     * Decrypt the given encrypted text with the given PrivateKey
     * 
     * @param encryptedText
     * @param privateKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String decryptTextWithPrivateKey(String encryptedText, PrivateKey privateKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)), "UTF-8");
    }

    /**
     * Encrypt the given text with the given Key File
     * 
     * @param clearText
     * @param publicKeyFilename
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws FileNotFoundException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static String encryptTextWithPemFile(String clearText, String publicKeyFilename)
            throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException,
            InvalidKeySpecException, IOException, InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {

        PublicKey publicKey = getPublicKeyFromPemFile(publicKeyFilename);
        return encryptTextWithPublicKey(clearText, publicKey);

    }

    /**
     * Encrypt the given text with the given PublicKey
     * 
     * @param clearText
     * @param key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encryptTextWithPublicKey(String clearText, PublicKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(clearText.getBytes("UTF-8")));
    }

    /**
     * Get Public Key from Pem File
     * 
     * @param publicKeyFilename
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InvalidKeySpecException
     */
    private static PublicKey getPublicKeyFromPemFile(String publicKeyFilename)
            throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException,
            IOException, InvalidKeySpecException {

        KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

        PemFileReader pemFileReader = new PemFileReader(publicKeyFilename);
        byte[] content = pemFileReader.getPemObject().getContent();
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(content);
        return factory.generatePublic(publicKeySpec);
    }

    /**
     * Get Private Key from Pem File
     * 
     * @param privateKeyFilename
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InvalidKeySpecException
     */
    private static PrivateKey getPrivateKeyFromPemFile(String privateKeyFilename)
            throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException,
            IOException, InvalidKeySpecException {

        KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

        PemFileReader pemFileReader = new PemFileReader(privateKeyFilename);
        byte[] content = pemFileReader.getPemObject().getContent();
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(privateKeySpec);
    }

    /**
     * Get Private Key from Encoded Key File
     * 
     * @param filename
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    // https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
    public static PrivateKey getPrivateKeyFromEncodedKeyFile(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * Get Public Key from Encoded Key File
     * 
     * @param filename
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    // https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
    public static PublicKey getPublicKeyFromEncodedKeyFile(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
