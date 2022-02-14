package com.ping23.crypt.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;

public class PemFileWriter {

    public static void writePemFile(Key key, String description, String filename)
            throws FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(key, description);
        pemFile.write(filename);

        System.out.println(
                String.format("%s successfully writen in file %s.", description, filename));
    }

}
