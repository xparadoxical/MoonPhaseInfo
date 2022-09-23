package com.paradoxical.moonphaseinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {

    private static final File CONFIG = new File("./config", "moonphaseinfo.properties");
    public static int loc;

    public static boolean detailedMode;

    static {
        loc = 0;
        //0 = TOP_LEFT
        //1 = TOP_MIDDLE
        //2 = TOP_RIGHT
        //3 = BOTTOM_LEFT
        //4 = BOTTOM_RIGHT
        detailedMode = true;
        try {
            FileInputStream configInputStream = new FileInputStream(CONFIG);
            Properties properties = new Properties();
            properties.load(configInputStream);
            loc = Integer.parseInt(properties.getProperty("loc"));
            detailedMode = Boolean.parseBoolean(properties.getProperty(("detailedMode")));
        } catch (Exception e) {
            write();
            e.printStackTrace();
        }
    }

    private static void write() {
        try {
            FileOutputStream outputStream = new FileOutputStream(CONFIG);
            Properties properties = new Properties();
            properties.setProperty("loc", String.valueOf(loc));
            properties.setProperty("detailedMode", String.valueOf(detailedMode));
            properties.store(outputStream, "MoonPhaseInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
