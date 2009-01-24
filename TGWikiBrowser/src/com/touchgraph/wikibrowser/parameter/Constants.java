/*
 * Constants.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.parameter;

import wikipedia.sql.Connect;
        
import java.util.*;

/**
 * 
 * @see wikipedia.sql.Connect in kleinberg subproject
 */
public interface Constants {

	// various constants for the WikiBrowser
    
    /** see comments in {@link com.touchgraph.wikibrowser.parameter.BrowserParameters} */
    public static final String[] LANG_CODE = {"en",      "ru",      "eo"};
    public static final String[] LANGUAGE  = {"English", "Russian", "Esperanto"};
    
    public static final String DB_HOST_EN  = "localhost";
    public static final String DB_HOST_RU  = "localhost";
    public static final String DB_HOST_EO  = "localhost";
    
    public static final String DB_NAME_EN       = Connect.WP_DB;
    public static final String DB_NAME_RU       = Connect.WP_RU_DB;
    public static final String DB_NAME_RUWIKT   = "ruwikt?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    public static final String DB_NAME_EO       = "eowiki?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    
    public static final String USER     = "javawiki";

    public static final String WIKI_URL_EN      = "http://localhost/enwikipedia/index.php/";
    public static final String WIKI_URL_RU      = "http://localhost/ruwikipedia/index.php/";
    public static final String WIKI_URL_RUWIKT  = "http://localhost/ruwiktionary/index.php/";
    public static final String WIKI_URL_EO      = "http://localhost/eowikipedia/index.php/";
    
    /** initial node name */
    public static final String NODE_EN      = "Robot";
    public static final String NODE_RU      = "Орбита";
    public static final String NODE_RUWIKT  = "самурай";
    public static final String NODE_EO      = "Roboto";
    
    public static final String ENC_UI   = "UTF8";
    public static final String ENC_JAVA = "UTF8";
    
    public static final int RADIUS = 1;
    
    public static final boolean SHOW_BACKLINKS = false;
    
    
    /** The categories in the blacklist
     * help to mark articles which have small possibilitiy to be synonyms
     * e.g. proper names, geo locations, e.g. such categories as: "Years", "Geography", etc.
     */
    
    /** The file /log_dir/article.params contains field BLACKLIST_PREFIX.
     * This field contains list of blacklist categories for the word article.
     */
    //public static final String BLACKLIST_PREFIX = "blacklist_category";
    
    /** The default list of categories' blacklists */
    public static final String[] BLACKLIST_CATEGORY_EN = {"Years", "Calendars", "Geography", "Scientists"};
    public static final String[] BLACKLIST_CATEGORY_RU = {"Страны", "Века",     "Календарь", "География_России", "Люди"};
    public static final String[] BLACKLIST_CATEGORY_EO = {"Landoj", "Jarcentoj","Kalendaro",                     "Famaj personoj"};
    
    /*public static final String[][] BLACKLIST_CATEGORY = {
        // en
        {"Years", "Calendars", "Geography", "Scientists"},
        // ru
        {"Страны", "Века", "Календарь", "География_России"}
    };*/
    
    
    
    /** see comments in {@link com.touchgraph.wikibrowser.parameter.ArticleParameters} */
    public static final int ROOT_SET_SIZE   = 10;
    public static final int INCREMENT       = 10;
    public static final int N_SYNONYMS      = 100;
    public static final float EPS_ERROR     = 0.001f;
    public static final int CATEGORIES_MAX_STEPS = 10;
}
