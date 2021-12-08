package com.epam.tc.api.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKeysInit {

    private static String get(String propertyName) {
        FileInputStream inputStream;
        Properties property = new Properties();
        try {
            inputStream = new FileInputStream("src/main/resources/properties/api.properties");
            property.load(inputStream);
            return property.getProperty(propertyName);
        } catch (IOException e) {
            System.err.println("Property file doesn't exist");
        }
        return "";
    }

    public static String getApiKey() {
        return get("key");
    }

    public static String getApiToken() {
        return get("token");
    }

    public static String getBaseURI() {
        return get("baseURI");
    }
}
