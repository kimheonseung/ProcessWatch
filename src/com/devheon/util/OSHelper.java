package com.devheon.util;

public class OSHelper {

    private final String mOs;

    /* Singleton */
    private static OSHelper instance;
    public static OSHelper getInstance() {
        if(instance == null)
            instance = new OSHelper();
        return instance;
    }
    /* Singleton */

    /* Constructor */
    public OSHelper() {
        this.mOs = System.getProperty("os.name").toLowerCase();
    }
    /* Constructor */

    public boolean isWindows() {
        return this.mOs.contains("win");
    }

    public boolean isMac() {
        return this.mOs.contains("mac");
    }

    public boolean isUnix() {
        return this.mOs.contains("nix") || this.mOs.contains("nux") || this.mOs.contains("aix") || this.mOs.contains("hp-ux");
    }

    public boolean isSolaris() {
        return this.mOs.contains("sunos");
    }

}
