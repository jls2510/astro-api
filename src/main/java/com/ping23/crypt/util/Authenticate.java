package com.ping23.crypt.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Authenticate {

    private static final Properties properties;
    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try (InputStream input = classLoader.getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Boolean authenticate(String apiKey) {
        return BCrypt.checkpw(apiKey, properties.getProperty("API_KEY_HASHED"));
    }

}
