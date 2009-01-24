/*
 * ArticleParameters.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.parameter;

import wikipedia.util.*;
import wikipedia.kleinberg.SessionHolder;

import java.util.Properties;
import java.util.*;

/** Store search and result parameters for each article in the wikipedia 
 * to the separate file in the log directory */
public class ArticleParameters extends Parameters implements Constants {

    private SessionHolder session;
            
    /** Language selected for this article */
    private String  lang;
    
    /** Number of articles in the root set */
    private int     root_set_size;
    
    /** Number of articles which will be added to the base set (they refer to 
     * one of the pages in the root base) */
    private int     increment;
    
    /** Number of synonyms to search */
    private int     n_synonyms;
    
    /** The iterative calculations (search synonyms) will be stopped if the change of value is less than epsilon */
    private float   eps_error;
    
    /** The max depth the category's ansectors will be checked for belonging to the blacklist */
    private int     categories_max_steps;
    
    /** Map from language code, e.g. "en" to categories blacklist */
    private Map<String,String[]> blacklist_category;
    
    /** List of synonyms for this word rated by user */
    private List<String> rated_synonyms;
    
    
    /** The result path will be 
     *      dir."/".article.".params" 
     */
    public ArticleParameters(String dir, String article, SessionHolder s) {
        super(article+".params", "Article properties");
        this.setFolder(dir);

        blacklist_category  = new HashMap<String,String[]>();
        rated_synonyms      = new ArrayList<String>();
        
        System.out.print("Loading article parameters... ");
        session = s;
        getParameters();
        System.out.println("OK.");
    }
    
    protected void setDefaults(Properties defaults) {
        defaults.put("root_set_size", new Integer(ROOT_SET_SIZE).toString());
        defaults.put("increment",   new Integer(INCREMENT).toString());
        defaults.put("n_synonyms",  new Integer(N_SYNONYMS).toString());
        defaults.put("eps_error",   new Float(EPS_ERROR).toString());
        defaults.put("categories_max_steps", new Integer(CATEGORIES_MAX_STEPS).toString());
        defaults.put("lang", LANG_CODE[0]);
        
        String[] c = null;
        for(int i=0; i<LANG_CODE.length; i++) {
            if(LANG_CODE[i].equalsIgnoreCase("en")) {
                c =  BLACKLIST_CATEGORY_EN;
            } else {
                if (LANG_CODE[i].equalsIgnoreCase("eo")) {
                    c =  BLACKLIST_CATEGORY_EO;
                } else {
                    if (LANG_CODE[i].equalsIgnoreCase("ru")) {
                        //c =  BLACKLIST_CATEGORY_RU;
                        //Encodings.FromTo(NODE_RU, "UTF8", "Cp1251");
                        c = new String[BLACKLIST_CATEGORY_RU.length];
                        for(int j=0; j<BLACKLIST_CATEGORY_RU.length; j++) {
                            c[j] = session.connect.enc.EncodeFromJava(BLACKLIST_CATEGORY_RU[j]);
                        }
                    }
                }
            }
            defaults.put("blacklist_category_" + LANG_CODE[i], StringUtil.join("|", c));
        }
    }
    
    protected void updateSettingsFromProperties() {
        int i;
        try {
            root_set_size = Integer.parseInt(properties.getProperty("root_set_size"));
            increment   = Integer.parseInt(properties.getProperty("increment"));
            n_synonyms  = Integer.parseInt(properties.getProperty("n_synonyms"));
            eps_error   = Float.parseFloat(properties.getProperty("eps_error"));
            categories_max_steps = Integer.parseInt(properties.getProperty("categories_max_steps"));
            
            // get (update) blacklist_category for the language of the current word
            for(i=0; i<LANG_CODE.length; i++) {
                String s = properties.getProperty("blacklist_category_" + LANG_CODE[i]);
                blacklist_category.put(LANG_CODE[i], StringUtil.split("|", s));
            }
            
            rated_synonyms.clear();
            String s = properties.getProperty("rated_synonyms");
            if(null != s && 0 < s.length()) {
                rated_synonyms.addAll( Arrays.asList(StringUtil.split("|", s)) );
            }
            
        } catch (NumberFormatException e) {
            // we don't care if the property was of the wrong format,
            // they've all got default values. So catch the exception
            // and keep going.
        }
    }

    protected void updatePropertiesFromSettings() {
        properties.put("root_set_size", new Integer(root_set_size).toString());
        properties.put("increment",     new Integer(increment).toString());
        properties.put("n_synonyms",    new Integer(n_synonyms).toString());
        properties.put("eps_error",     new Float(eps_error).toString());
        properties.put("categories_max_steps", new Integer(categories_max_steps).toString());
        properties.put("rated_synonyms", getRatedSynonymsAsString());
        
        properties.put("lang", lang);
        String[] s = blacklist_category.get(lang);
        if(null != s) {
            properties.put("blacklist_category_" + lang, StringUtil.join("|", s));
        }
    }
    
    public void setLang(String s) {
        List<String> l = Arrays.asList(LANG_CODE);
        if(!l.contains(s)) {
            System.out.println("Error in ArticleParameters.setLang("+s+")");
        }
        lang = s;
    }
    public String getLang() {
        return lang;
    }

    public String[] getCategoryBlackList() {
        return blacklist_category.get(lang);
    }    
    public void setCategoryBlackList(String[] blacklist) {
        blacklist_category.put(lang, blacklist);
    }
    
    private String getRatedSynonymsAsString() {
        String s = "";
        if(null != rated_synonyms && 0 < rated_synonyms.size()) {
            s = StringUtil.join("|", (String[])rated_synonyms.toArray(new String[0]));
        }
        return s;
    }
    
    public List<String> getRatedSynonyms() {
        return rated_synonyms;
    }
    public void setRatedSynonyms(List<String> s){
        rated_synonyms = s;
    }
    
    public void setRootSetSize(int root_set_size) {
        this.root_set_size = root_set_size;
    }
    public int getRootSetSize() {
        return root_set_size;
    }
    
    public void setIncrement(int increment) {
        this.increment = increment;
    }
    public int getIncrement() {
        return increment;
    }
    
    public void setNSynonym(int n_synonyms) {
        this.n_synonyms = n_synonyms;
    }
    public int getNSynonyms() {
        return n_synonyms;
    }
    
    public void setEpsError(float eps_error) {
        this.eps_error = eps_error;
    }
    public float getEpsError() {
        return eps_error;
    }
    
    public void setCategoriesMaxSteps(int categories_max_steps) {
        this.categories_max_steps = categories_max_steps;
    }
    public int getCategoriesMaxSteps() {
        return categories_max_steps;
    }

}
