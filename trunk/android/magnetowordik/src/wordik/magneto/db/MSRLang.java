package wordik.magneto.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MSRLang {
	
	/** Unique language identifier. */
    private int _id;

    /** Two (or more) letter language code, e.g. 'en', 'ru'. */
    private String code;
    
    /** Language name, e.g. 'English', 'Russian'. */
    private String name;

    /** Number of meanings (with semantic relations) of words of this language,
     * in the table mean_semrel_XX, where XX is a language code. */
    private int n_meaning;
    // SELECT COUNT(*) FROM mean_semrel_en;

    /** Number of semantic relations (for this language) in the table mean_semrel_XX,
     * where XX is a language code. */
    private int n_sem_rel;
    
    private final static MSRLang[] NULL_MSRLANG_ARRAY = new MSRLang[0];
    
    private MSRLang(int __id, String _name,String _code,int _n_meaning,int _n_sem_rel) {
    	_id = __id;
    	name = _name;
    	code = _code;
    	n_meaning = _n_meaning;
    	n_sem_rel = _n_sem_rel;
    }
    
    /** Gets language code. */
    public String getCode() {
        return code;
    }
    
    /** Gets language name. */
    public String getName() {
        return name;
    }
    
    /** Gets number of semantic relations. */
    public int getNumberOfSemanticRelations() {
        return n_sem_rel;
    }
    
    /** Gets number of semantic relations. */
    public int getNumberOfMeanings() {
        return n_meaning;
    }
    
    /** Reads all data from the table "lang". 
     * 
     * @param db
     * @return
     */
    public static MSRLang[] getAllLang(SQLiteDatabase db) {
    	
        //Cursor c = db.query("SELECT name,code,n_meaning,n_sem_rel FROM lang;", null);
        
    	// select * from lang order by n_sem_rel DESC limit 17;
    	// select * from lang order by name limit 17;
    	Cursor c = db.query("lang", new String[] { "_id", "name", "code", "n_meaning", "n_sem_rel"}, 
    				null, null, null, null, 
    				"name");
    				//"n_sem_rel DESC");
    	
        List<MSRLang> list = new ArrayList<MSRLang>();
        
        if (c.moveToFirst()) {
           do {
        	   int i_id = c.getColumnIndexOrThrow("_id");
        	   int i_name = c.getColumnIndexOrThrow("name");
        	   int i_code = c.getColumnIndexOrThrow("code");
        	   int i_meaning = c.getColumnIndexOrThrow("n_meaning");
        	   int i_sem_rel = c.getColumnIndexOrThrow("n_sem_rel");
        	    
        	   int _id = c.getInt(i_id);
        	   String name = c.getString(i_name);
        	   String code = c.getString(i_code);
        	   int n_meaning = c.getInt(i_meaning); 
        	   int n_sem_rel = c.getInt(i_sem_rel);
        	   
        	   list.add(new MSRLang(_id, name, code, n_meaning, n_sem_rel));
           } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
           c.close();
        }
    	
    	return( (MSRLang[])list.toArray(NULL_MSRLANG_ARRAY) );
     }
    
    /** Gets array of text lines in the form:
     * "Language name, code, number of semantic relations" (header).
     * The first line is a header.  
     */
    public static String[] getLangCodeStatistics(MSRLang[] langs) {
    	
    	String lines[] = new String[langs.length + 1];
    	lines[0] = "Language name, code, number of semantic relations";
    	
    	for(int i=0; i < langs.length; i++) {
    		MSRLang l = langs[i];
	        lines[i+1]= l.name + " " + l.code + " " + l.n_sem_rel; // + " " + l.n_meaning;
    	}
    	
    	return lines;
    }
    

}
