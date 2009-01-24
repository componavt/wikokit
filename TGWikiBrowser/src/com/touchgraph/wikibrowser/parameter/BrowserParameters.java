/*
 * BrowserParameters.java - keeps track of all the browser parameters.
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.parameter;

import wikipedia.util.*;
import wikipedia.language.Encodings;
import wikipedia.kleinberg.SessionHolder;

import java.util.Properties;
import java.util.*;

public class BrowserParameters extends Parameters implements Constants {
    
    private SessionHolder session;
    /** The selected Wikipedia (selected language) */
    private String  language;
    private String  lang_code;
    
    /** Map from language code to Wikipedia MySQL database host */
    private Map<String,String>  db_host;
    
    /** Map from language code to Wikipedia MySQL database name */
    private Map<String,String>  db_name;
    
    /** MySQL username */
    private Map<String,String>  user;
    
    /** MySQL passwords */
    private Map<String,String>  pass;
    
    /** Wiki site prefix */
    private Map<String,String>  wiki_url;
    
    /** Start node */
    private Map<String,String>  node;
    
    /** enabling of dump result of work to log directory */
    private boolean  b_log_dump2dir;
    
    /** log directory */
    private String  log_dir;
    
    /** encodings, see wikipedia.util.Encodings */
    private String  enc_java;
    private String  enc_ui;
    
    private int     radius;
    private boolean show_backlinks;
    
    
    public BrowserParameters() {
        super(".wikibrowser.server.props", "WikiBrowser Server Properties");
        
        db_host = new HashMap<String, String>();
        db_name = new HashMap<String, String>();
        user    = new HashMap<String, String>();
        pass    = new HashMap<String, String>();
        wiki_url= new HashMap<String, String>();
        node    = new HashMap<String, String>();
        
        System.out.print("Loading browser parameters... ");
        getParameters();
        // decode text parameters (categories, node, etc.) using parameter "java_enc"
        // setDefaultsEncoded();
        System.out.println("OK.");
    }
    
    /** Decode default text values using encoding value, 
     * extracted from .wikibrowser.server.props file.
     */
    public void setDefaultsEncoded() {
        String n;
        
        for(int i=0; i<LANG_CODE.length; i++) {
            if(LANG_CODE[i].equalsIgnoreCase("en")) {
                n =     NODE_EN;
            } else {
                if (LANG_CODE[i].equalsIgnoreCase("eo")) {
                    n =     NODE_EO;
                } else {
                    if (LANG_CODE[i].equalsIgnoreCase("ru")) {
                        if(null != session) {
                            n = session.connect.enc.EncodeFromJava(NODE_RU);
                        } else {
                            n = Encodings.FromTo(NODE_RU, enc_java, Encodings.enc_int_default);
                            //n = Encodings.FromTo(NODE_RU, "UTF8", "Cp1251");
                            //n = Encodings.FromTo(NODE_RU, "UTF8", "Cp1251");
                        }
                    } else {
                        n = "";
                    }
                }
            }
            node.    put(             LANG_CODE[i], n);
        }
    }
    
    protected void setDefaults(Properties defaults) {
        //defaults.put("#My land_code comment\nlang_code",LANG_CODE[0]);
        defaults.put("lang_code",LANG_CODE[0]);
        String h, d, w, n;
        
        defaults.put("enc_ui",  ENC_JAVA);
        defaults.put("enc_java",ENC_JAVA);
        enc_java = ENC_JAVA;
        
        for(int i=0; i<LANG_CODE.length; i++) {
            if(LANG_CODE[i].equalsIgnoreCase("en")) {
                    h =  DB_HOST_EN;
                    d =  DB_NAME_EN;
                    w = WIKI_URL_EN;
                    n =     NODE_EN;
                } else {
                if (LANG_CODE[i].equalsIgnoreCase("eo")) {
                        h =  DB_HOST_EO;
                        d =  DB_NAME_EO;
                        w = WIKI_URL_EO;
                        n =     NODE_EO;
                } else {
                    if (LANG_CODE[i].equalsIgnoreCase("ru")) {
                        h =  DB_HOST_RU;
                        d =  DB_NAME_RU;
                        w = WIKI_URL_RU;
                        if(null != session) {
                            n = session.connect.enc.EncodeFromJava(NODE_RU);
                        } else {
                            n = Encodings.FromTo(NODE_RU, enc_java, Encodings.enc_int_default);
                            //n = Encodings.FromTo(NODE_RU, "UTF8", "Cp1251");
                            //n = Encodings.FromTo(NODE_RU, "UTF8", "Cp1251");
                        }
                    } else {
                        h = "localhost";
                        d = "";
                        w = "";
                        n = "";
                    }}}
            
            defaults.put("db_host_" + LANG_CODE[i], h);
            db_host. put(             LANG_CODE[i], h);
            
            defaults.put("db_name_" + LANG_CODE[i], d);
            db_name. put(             LANG_CODE[i], d);
            
            defaults.put("wiki_url_"+ LANG_CODE[i], w);
            wiki_url.put(             LANG_CODE[i], w);
            
            defaults.put("node_" +    LANG_CODE[i], n);
            node.    put(             LANG_CODE[i], n);
            
            
            defaults.put("user_" + LANG_CODE[i], USER);
            user.    put(          LANG_CODE[i], USER);
            
            defaults.put("pass_" + LANG_CODE[i], "");
            pass.    put(          LANG_CODE[i], "");
        }

        defaults.put("b_log_dump2dir", "0");     // disable log by default
        defaults.put("log_dir", "");    // System.getProperty("user.home");
        
        defaults.put("radius", new Integer(RADIUS).toString());
        
        Integer k = SHOW_BACKLINKS ? 1 : 0;
        defaults.put("show_backlinks", k.toString());
    }
    
    protected void updateSettingsFromProperties() {
        int i;
        try {
            lang_code   = properties.getProperty("lang_code");
            db_host. put(lang_code, properties.getProperty("db_host_"+lang_code));
            db_name. put(lang_code, properties.getProperty("db_name_"+lang_code));
            user.    put(lang_code, properties.getProperty("user_" +  lang_code));
            pass.    put(lang_code, properties.getProperty("pass_" +  lang_code));
            wiki_url.put(lang_code, properties.getProperty("wiki_url_"+lang_code));
            node.    put(lang_code, properties.getProperty("node_" +  lang_code));
            
            enc_java    = properties.getProperty("enc_java");
            enc_ui      = properties.getProperty("enc_ui");
            if(null != session) {
                session.connect.enc.SetEncodingJavaSourceCode(enc_java);
                session.connect.enc.SetEncodings(
                        session.connect.enc.GetDBEnc(),
                        session.connect.enc.GetInternalEnc(),
                        enc_ui);
            }
            
            log_dir     = properties.getProperty("log_dir");
            radius      = Integer.parseInt(properties.getProperty("radius"));
            
            i           = Integer.parseInt(properties.getProperty("b_log_dump2dir"));
            b_log_dump2dir = (0 == i) ? false : true;
            
            i           = Integer.parseInt(properties.getProperty("show_backlinks"));
            show_backlinks = (0 == i) ? false : true;        
        } catch (NumberFormatException e) {
            // we don't care if the property was of the wrong format,
            // they've all got default values. So catch the exception
            // and keep going.
        }
    }

    protected void updatePropertiesFromSettings() {
        Integer i;
        
        properties.put("lang_code", lang_code);
        
        properties.put("db_host_"+lang_code, db_host.get(lang_code));
        properties.put("db_name_"+lang_code, db_name.get(lang_code));
        properties.put("user_"+lang_code, user.get(lang_code));
        properties.put("pass_"+lang_code, pass.get(lang_code));
        properties.put("node_"+lang_code, node.get(lang_code));
        properties.put("wiki_url_"+lang_code, wiki_url.get(lang_code));
        
        properties.put("enc_java",  enc_java);
        properties.put("enc_ui",    enc_ui);
        
        properties.put("log_dir",   log_dir);
        properties.put("radius", new Integer(radius).toString());
        
        i = b_log_dump2dir ? 1 : 0;
        properties.put("b_log_dump2dir", i.toString());
        
        i = show_backlinks ? 1 : 0;
        properties.put("show_backlinks", i.toString());
    }

    /*
    public String toString() {
        Integer i_log       = b_log_dump2dir ? 1 : 0;
        Integer i_backlinks = show_backlinks ? 1 : 0;
        return "["
               + "wiki_url="+ wiki_url + ","
               + "b_log_dump2dir="   + i_log + ","
               + "log_dir=" + log_dir + ","
               + "node="    + node + ","
               + "radius="  + radius + ","
               + "show_backlinks=" + i_backlinks
               + "]";
    }
     */

    public void setSessionHolder (SessionHolder s) {
        session = s;
    }
    
    public String getLangCode() {
        return lang_code;
    }
    public void setLanguage(String s) {
        boolean bfound = false;
        for(int i=0; i<LANGUAGE.length; i++) {
            if(s.equalsIgnoreCase(LANGUAGE[i])) {
                language  = s;
                lang_code = LANG_CODE[i];
                bfound = true;
                break;
            }
        }
        if(!bfound) {
            System.out.println("Error in BrowserParameters.setLanguage("+ s +")");
        }
        saveParameters();
    }
    public String getLanguage() {
        for(int i=0; i<LANG_CODE.length; i++) {
            if(lang_code.equalsIgnoreCase(LANG_CODE[i])) {
                return LANGUAGE[i];
            }
        }
        String err_msg = "Error in BrowserParameters.getLanguage()";
        System.out.println(err_msg);
        return err_msg;
    }
    
    /** Gets the selected language index in LANG_CODE[] */
    public int getSelectedLanguageIndex() {
        for(int i=0; i<LANG_CODE.length; i++) {
            if(lang_code.equalsIgnoreCase(LANG_CODE[i])) {
                return i;
            }
        }
        System.out.println("Error in BrowserParameters.getSelectedLanguageIndex()");
        return 0;
    }
    
    public String getDBHost() {return db_host.get(lang_code);   }
    public void   setDBHost(String s){db_host.put(lang_code, s); saveParameters();}
    
    public String getDBName() {return db_name.get(lang_code);   }
    public void   setDBName(String s){db_name.put(lang_code, s); saveParameters();}
    public String getUser  () {return user.   get(lang_code);   }
    public void   setUser  (String s){user.   put(lang_code, s); saveParameters();}
    public String getPass  () {return pass.   get(lang_code);   }
    public void   setPass  (String s){pass.   put(lang_code, s); saveParameters();}
    
    public String getNode  () {return node.   get(lang_code);   }
    public void   setNode  (String s){node.   put(lang_code, s); saveParameters();}
    
    public String getWikiURL() {return wiki_url.get(lang_code);    }
    public void   setWikiURL(String s){wiki_url.put(lang_code, s);saveParameters();}
    
    
    public void     setEnableLog(boolean enable) {
        b_log_dump2dir = enable; saveParameters();
    }
    public boolean  isLogEnabled()  { 
        return b_log_dump2dir;
    }
    
    public void setLogDir(String log_dir) {
        this.log_dir = log_dir;
        saveParameters();
    }
    public String getLogDir() {
        return log_dir;
    }
    
    
    public void updateEncodingsToSession() {
        if(null != session) {
            session.connect.enc.SetEncodingJavaSourceCode(enc_java);
            session.connect.enc.SetEncodings(
                        session.connect.enc.GetDBEnc(),
                        session.connect.enc.GetInternalEnc(),
                        enc_ui);
        }
    }
    public void setEncJava(String enc_java) {
        this.enc_java = enc_java;
        updateEncodingsToSession();
        saveParameters();
    }
    public void setEncUI(String enc_ui) {
        this.enc_ui = enc_ui;
        updateEncodingsToSession();
        saveParameters();
    }
    public String getEncJava() {    return enc_java;    }
    public String getEncUI  () {    return enc_ui;      }
    
    
    public void setRadius(int radius) {
        this.radius = radius;
        saveParameters();
    }
    public int getRadius() {
        return radius;
    }

    public void setShowBacklinks(boolean show_backlinks) {
        this.show_backlinks = show_backlinks;
        saveParameters();
    }
    public boolean getShowBacklinks() {
        return show_backlinks;
    }
}
