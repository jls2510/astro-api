package com.ping23.crypt.processor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class PemFileReader {

    private PemObject pemObject;

    public PemFileReader(String filename) throws FileNotFoundException, IOException {

        // in web app we use this method
        InputStream inputStream = PemFileReader.class.getResourceAsStream(filename);

        // standalone java use this method
        if (inputStream == null) {
            inputStream = new FileInputStream(filename);
        }
        
        PemReader pemReader = new PemReader(new InputStreamReader(inputStream));

        try {
            this.pemObject = pemReader.readPemObject();
        } finally {
            pemReader.close();
        }
    }

    public PemObject getPemObject() {
        return pemObject;
    }
}


