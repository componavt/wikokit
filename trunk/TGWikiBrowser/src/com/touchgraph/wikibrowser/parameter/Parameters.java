package com.touchgraph.wikibrowser.parameter;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public abstract class Parameters {

    private static final boolean DEBUG = true;
    private String propertiesFilename;
    private String propertiesDescription;
    private String folder;

    protected Properties properties = null;

    protected Parameters(String propertiesFilename, String propertiesDescription) {
        this.propertiesFilename = propertiesFilename;
        this.propertiesDescription = propertiesDescription;
        folder = System.getProperty("user.home");
    }

    abstract protected void setDefaults(Properties defaults) ;
    abstract protected void updatePropertiesFromSettings() ;
    abstract protected void updateSettingsFromProperties() ;

    /** Set folder for parameters read/write operations */
    protected void setFolder(String folder_new) {
        folder = folder_new;
    }
    
    protected void getParameters() {
        Properties defaults = new Properties();
        FileInputStream in = null;

        setDefaults(defaults);

        properties = new Properties(defaults);

        try {
            String fn = folder + System.getProperty("file.separator") + propertiesFilename;
            
            if (DEBUG) {
                System.out.println(" from file:" + fn);
            }
            
            in = new FileInputStream(fn);
            properties.load(in);
            
        } catch (java.io.FileNotFoundException e) {
            in = null;
            System.err.print("Can't find properties file. " +
                            "Using defaults. ");
        } catch (java.io.IOException e) {
            System.err.print("Can't read properties file. " +
                            "Using defaults. ");
        } finally {
            if (in != null) {
            try { in.close(); } catch (java.io.IOException e) { }
            in = null;
            }
        }

        updateSettingsFromProperties();

    }

    public void saveParameters() {

        updatePropertiesFromSettings();

        if (DEBUG) {
            System.out.print("Save properties: " + propertiesDescription + ". ");
            System.out.println(toString());
        }

            FileOutputStream out = null;

        try {
            out = new FileOutputStream(folder
                           + System.getProperty("file.separator")
                           + propertiesFilename);
            //properties.save(out, propertiesDescription);
            properties.store(out, propertiesDescription);
        } catch (java.io.IOException e) {
            System.err.print("Can't save properties. " +
                            "Oh well, it's not a big deal.");
        } finally {
            if (out != null) {
            try { out.close(); } catch (java.io.IOException e) { }
            out = null;
            }
        }
    }
}