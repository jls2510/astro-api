package com.ping23.crypt.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAEncodedKeyPairGenerator {

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    public final static String RESOURCES_DIR = "src/main/resources";

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException,
            NoSuchProviderException, IOException {

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

        RSAEncodedKeyPairGenerator generator;
        
        try {
            generator = new RSAEncodedKeyPairGenerator(4096);
            generator.createKeys();
            RSAEncodedKeyPairGenerator.writeToFile(RESOURCES_DIR + path + "/rsa-public-key.der",
                    generator.getPublicKey().getEncoded());
            RSAEncodedKeyPairGenerator.writeToFile(RESOURCES_DIR + path + "/rsa-private-key.der",
                    generator.getPrivateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        return true;
    }

    /**
     * 
     * @param keylength
     * @throws NoSuchAlgorithmException
     */
    public RSAEncodedKeyPairGenerator(int keylength) throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    /**
     * 
     */
    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    /**
     * 
     * @return
     */
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    /**
     * 
     * @return
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    /**
     * 
     * @param path
     * @param key
     * @throws IOException
     */
    public static void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(key);
            fos.flush();
            fos.close();
        }
    }

}
