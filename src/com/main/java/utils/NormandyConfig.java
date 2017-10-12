package com.main.java.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author david.du
 *
 */
public class NormandyConfig {

    private static Properties p = null;
    static {
        try {
            InputStream resourceAsStream = 
            		NormandyConfig.class.getClassLoader().getResourceAsStream("backup.properties");
            p = new Properties();
            p.load(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return p.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(NormandyConfig.get("backupFilePath"));
    }

}
