package wordik.magneto.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import wordik.magneto.constant.Relation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MSRMeanSemrelXX {

	private final static MSRMeanSemrelXX[] NULL_MSRMEANSEMRELXX_ARRAY = new MSRMeanSemrelXX[0];
	private final static String[] NULL_STRING_ARRAY = new String[0];
    
    /** Unique identifier for this meaning. */
    private int id;
    
    /** Entry / headword in the language XX. */
    private String page_title;
    
    /** Text of the meaning (definition) of this entry (without wikification). */
    private String meaning;
    
    /** Symbol between words in the table fields synonyms, antonyms, etc. */
	private final static String delimiter = "&/";
    
    /** Names of semantic relations. */
    private static final String[] table_fields_relations = {
        "synonyms",     "antonyms",
        "hypernyms",    "hyponyms",
        "holonyms",     "meronyms",
        "troponyms",    "coordinate_terms"
    };
    
    /** Semantic relations. */
    private static final Relation[] ar_relations = {
        Relation.synonymy,  Relation.antonymy,
        Relation.hypernymy, Relation.hyponymy,
        Relation.holonymy,  Relation.meronymy,
        Relation.troponymy, Relation.coordinate_term
    };
    
    /** E.g. synonymy -> syn_word1, syn_word2; 
     * antonymy -> ant_word1, ...
     */
    private Map<Relation, String[]> m_relations;
    
    /** Number of semantic relations (for this meaning): synonyms + antonyms + ... */
    private int n_sem_rel;
    
    
    /** Number of correct answers with these semantic relations. */
    private int success;
    
    /** Number of wrong answers with these semantic relations. */
    private int failure;
    
    private static Random generator = new Random();
    
    public MSRMeanSemrelXX(int _id, String _page_title, String _meaning,
                    int _n_sem_rel, int _success, int _failure,
                    Map<Relation, String[]> _m_relations)
    {
        id          = _id;
        page_title  = _page_title;
        meaning     = _meaning;
        
        n_sem_rel   = _n_sem_rel;
        success     = _success;
        failure     = _failure;
        
        m_relations = _m_relations;
    }
    
    /** Gets semantic relations. */
    public Map<Relation, String[]> getRelations() {
        return m_relations;
    }
    
    /** Gets entry header (page_title). */
    public String getPageTitle() {
        return page_title;
    }
    
    /** Gets meaning (definition). */
    public String getMeaning() {
        return meaning;
    }
    
    /** Gets all synonym, antonym, etc. */
    public String[] getAllSynonyms ()
    {
    	List<String> words = new ArrayList<String>();
    	
    	for(String[] ww : m_relations.values())
    		for(String w : ww)
    			words.add(w);
    	
        return (String[])words.toArray(NULL_STRING_ARRAY);
    }
    
    /** Gets random synonym, or antonym, etc. */
    public String getRandomSynonym ()
    {
    	String[] all_words = getAllSynonyms();
    	
    	if(all_words.length <= 0)
    		return ""; // unreachable line
    	
    	return all_words[ generator.nextInt( all_words.length ) ];
    }
    
    /** Checks whether the candidate word is a synonym (or antonym, etc.) for the page_title word. */
    public boolean hasRelatedWord (String candidate)
    {
    	String[] all_words = getAllSynonyms();
    	
    	if(all_words.length <= 0)
    		return false; // unreachable line
    	
    	List<String> word_list = Arrays.asList(all_words);
    	return word_list.contains(candidate);
    }
    
    // = semrel_main_word.(candidate);
    /** Gets name of the relation between the page_title word and candidate word.
     * @return empty String "" if there is no relations 
     * */
    public String getRelation (String candidate)
    {
    	if(null == candidate || 0 == candidate.length())
    		return "";
    	
    	for(Relation r : m_relations.keySet()) {
    		
    		String [] ww = m_relations.get(r);
    		for(String w : ww) {
    			if(candidate.equalsIgnoreCase(w)) 
    				return r.toString();
    		}
    	}
    	return "";
    }
    
    /** Gets random rows (records) from the XX table.<br><br>
     * 
     * old: SELECT * FROM mean_semrel_en ORDER BY RANDOM() limit 2;
     * new: SELECT * FROM mean_semrel_en LIMIT random_id, 1
     * 
     * @param xx_lang   defines XX language code in mean_semrel_XX table
     * @param limit		number of random records
     * @return 
     */
    public static MSRMeanSemrelXX[] getRandom (SQLiteDatabase db, MSRLang xx_lang, int limit)
    {
    	if(limit < 1)
    		return NULL_MSRMEANSEMRELXX_ARRAY;
    	
    	String table = "mean_semrel_" + xx_lang.getCode();
    	
    	int max_id = xx_lang.getNumberOfMeanings();
    	int random_id = generator.nextInt(max_id);
    	
    	Cursor c = db.query(table, new String[] { 
    			"_id", "page_title", "meaning", "n_sem_rel",
    			"success", 	"failure",
    			"synonyms",     "antonyms",
    	        "hypernyms",    "hyponyms",
    	        "holonyms",     "meronyms",
    	        "troponyms",    "coordinate_terms"}, 
				null, null, null, null, 
				"", // "RANDOM()", // ORDER BY
				"" + random_id + ", " + limit);
    	
        //PageTableBase.
          //      convertToSafeStringEncodeToDBWunderscore(connect, page_title));
        
        List<MSRMeanSemrelXX> list_rel = new ArrayList<MSRMeanSemrelXX>();
        
        if (c.moveToFirst()) {
            do {
            	int i_id = c.getColumnIndexOrThrow("_id");
         	   	int i_page_title = c.getColumnIndexOrThrow("page_title");
         	   	int i_meaning = c.getColumnIndexOrThrow("meaning");
         	   	int i_sem_rel = c.getColumnIndexOrThrow("n_sem_rel");
         	   	int i_success = c.getColumnIndexOrThrow("success");
         	   	int i_failure = c.getColumnIndexOrThrow("failure");
         	   
				int _id = c.getInt(i_id);
				String page_title = c.getString(i_page_title);
				String meaning = c.getString(i_meaning);
				int n_sem_rel = c.getInt(i_sem_rel);
				int success = c.getInt(i_success);
				int failure = c.getInt(i_failure);
         	  
        	    Map<Relation, String[]> _m_relations = new HashMap<Relation, String[]>();
               
                for(Relation r : ar_relations) {
                    String relation_field = r.toString();   // relation name as table field
                    if(r == Relation.coordinate_term)
                        relation_field = "coordinate_terms";   // "coordinate terms" -> "coordinate_terms" (without space in the table field name)
                   
                    int i = c.getColumnIndexOrThrow(relation_field);
                    if(!c.isNull(i)) {
                	    String synset = c.getString(i);
                   
	                    //byte[] byte_synset = rs.getBytes(relation_field);
	                    //if(null != byte_synset) {
	                    //    String synset = Encodings.bytesToUTF8(byte_synset);
                	    
                	    String[] syn_words = synset.split(delimiter);
	                    _m_relations.put(r, syn_words);
	                    //}
                    }
                }
                list_rel.add(new MSRMeanSemrelXX(_id, page_title, meaning,
                			n_sem_rel, success, failure, _m_relations));
               
            } while (c.moveToNext());
         }
         if (c != null && !c.isClosed()) {
            c.close();
         }
                        
         return (MSRMeanSemrelXX[])list_rel.toArray(NULL_MSRMEANSEMRELXX_ARRAY);
    }
	
}
