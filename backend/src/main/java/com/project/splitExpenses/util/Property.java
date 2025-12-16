package com.project.splitExpenses.util;

import java.util.Properties;

public class Property {

    private static final Properties properties = new Properties();

    static {
        try {
        	properties.load(Property.class.getClassLoader().getResourceAsStream("sql.properties"));
        } catch (Exception ex) {
            throw new RuntimeException("Could not load sql.properties", ex);
        }
    }
    
    /*static {
        try {
        	properties.load(Property.class.getClassLoader().getResourceAsStream("system.properties"));
        } catch (Exception ex) {
            throw new RuntimeException("Could not load system.properties", ex);
        }
    }*/

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
