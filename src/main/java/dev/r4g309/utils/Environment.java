package dev.r4g309.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Environment {

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultValue) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/env.properties"));

            return defaultValue == null ? properties.getProperty(key) : properties.getProperty(key, defaultValue);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }

    }
}
