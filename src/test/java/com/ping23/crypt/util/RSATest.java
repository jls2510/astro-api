package com.ping23.crypt.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ping23.crypt.processor.RSAEncryptDecrypt;

@SpringBootTest
public class RSATest {

    private final static String RESOURCES_DIR = "src/main/resources";

    private static final String token1 =
            "ycRpalT2fAInlgUQ3FwqWsWwWnZbCGne85YeHNDrPTi4BP7EPpd\\/LqrylF2LSDsE4mEjhSJRwGmYzjKb+xTHAgsQthNrkN\\/q7lMCko9CJ3ZKQnjdm1D6wBRnQ16j+RvpOrOaq72OYQ9Aw1YQMZjZdNrozgmHysyw5CSANf1GEz3ok10wCLBx9zLk2Z4Ded+JpTEeGX2FrOR9+AXHIKl32ANHLqEQAt107CnYCcYmAXtLZ9sjTRB9BpzzWBebwkyhu+RUHfqKkJX1O9cFNrCdjDC3eA+Dd8oC37feCGueNZ5a4QLtpG0ulzQFYVi\\/XkO6sZBA0RblIRrabyI+4VgwDw==";
    private static final String token2 =
            "oIU7WTWWgDAJ0Rtx2eOZ\\/C9wLWVqxaMqon\\/RrnczWoRUSUahcHFtyZ9nd2vlMMvLZF2JYB64kAjIk6mbA3Nq81Jxn6pAJlpaGVQ28jgrLnLZiR\\/vFQJWpt+Qp3hIH0rqge0xgozmoX0nxDN71p26Aq\\/6joZQJCP26NoQsfI39r1W+9cDw2R50+ubYZB9ZKn0q+60tTH8SvPS7ndXmC9YE4ZMaGzPXYDue0+0VKmUfqT2vRj3XXShbjw5XHK9dmAXAt7FMPJPFagNhxZTefYPXYP2bVO\\/RTLxGFQb74qVr8+3ImnzO5DPYMGCk26QxXeLDSeRckeD0OXz\\/3nYaylHEg==";
    private static final String token3 =
            "t8E/Vjzd0rs4tZdBeumrIcEp8TnOCZdnhRiaYicclS+kWx1hlsRjaLLzdPIWouX9p36xsN9UcZPAYe01v6J5+YVKYBJoKd4nlMdEa7m72N7ayMVl4LNyd6sxq20ZKEzp06AwSilPivOYTaJrd9pRUNFTa2mF9ukKhqtzqCme+38LCUewX5onOTarnSfKHmk9Xw90ESx/WUubRAYmOqn5bu19ky2V+Fxx4SFaBGiXEDtTKgHdU1BQQUgKoEUSWWLiCMAwQtjeqdmNu7s4Zy2o5JkQdCFUzk6l7jbJb2B5ZPHbtCn05TWicwytoni+PrKjggy+d8ibP08ng0b3th2jKA==";

    @BeforeEach
    void setUp() throws Exception {
        // empty
    }

    @AfterEach
    void tearDown() throws Exception {
        // empty
    }

    @Test
    void rsaTest() throws Exception {

//        String privateKeyFilename = RESOURCES_DIR + "/encryption/win_public_key.pem"; // note that WIN got it backwards!
//        String publicKeyFilename = RESOURCES_DIR + "/encryption/rsa-public-key.der";
//        String privateKeyFilename = RESOURCES_DIR + "/encryption/rsa-private-key.der";
        
        String publicKeyFilename = RESOURCES_DIR + "/encryption/rsa-public-key.pem";
        String privateKeyFilename = RESOURCES_DIR + "/encryption/rsa-private-key.pem";
        
        String encryptedMessage = null;
        String decryptedMessage = null;
        
        try {
            
            encryptedMessage = RSAEncryptDecrypt.encryptTextWithPemFile(SampleText.SHORT_SAMPLE_TEXT, publicKeyFilename);
            assertNotNull(encryptedMessage);
            System.out.println("Encrypted Message: " + encryptedMessage);
            
            //String decryptedMessage = RSAEncryptDecrypt.decryptText(token2, privateKeyFilename);
            decryptedMessage = RSAEncryptDecrypt.decryptTextWithPemFile(encryptedMessage, privateKeyFilename);
            assertNotNull(decryptedMessage);
            System.out.println("Decrypted Message: " + decryptedMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
