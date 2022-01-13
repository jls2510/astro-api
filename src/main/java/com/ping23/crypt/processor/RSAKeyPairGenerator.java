package com.ping23.crypt.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAKeyPairGenerator {

    public static final int KEY_SIZE = 2048;
    public final static String RESOURCES_DIR = "src/main/resources";

    static {
        Security.addProvider(new BouncyCastleProvider());
        System.out.println("BouncyCastle provider added.");

    }

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static void main(String[] args)
            throws FileNotFoundException, IOException, NoSuchAlgorithmException,
            NoSuchProviderException {

        generateRSAKeyPairFiles("/encryption");

    }

    /**
     * 
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static Boolean generateRSAKeyPairFiles(String path) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair keyPair = generateRSAKeyPair();
        RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();

        PemFileWriter.writePemFile(priv, "RSA PRIVATE KEY",
                RESOURCES_DIR + path + "/rsa-private-key.pem");
        PemFileWriter.writePemFile(pub, "RSA PUBLIC KEY",
                RESOURCES_DIR + path + "/rsa-public-key.pem");

        return true;
    }

    /**
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static KeyPair generateRSAKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(KEY_SIZE);

        KeyPair keyPair = generator.generateKeyPair();
        System.out.println("RSA key pair generated.");
        return keyPair;
    }

}
