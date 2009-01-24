/*
 * Hyponyms.java - Calculates distance between articles via categories.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.experiment;

import wikipedia.util.StringUtil;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;

/** Calculates distance between articles via categories. The table cat_count
 * is used. It contains number of hyponyms (sub-categories + articles) for each
 * category.
 *
 * Table cat_count could be built by MySQL stored procedure. See instructions 
 * in the file synarcher/sql_procedures/hyponyms/readme_ic.txt
 *
 * More information about IC distance metric see in
 *
 * 1) [Resnik95]. Resnik P. Disambiguating noun groupings with respect to 
 * WordNet senses. In Proceedings of the 3rd Workshop on Very Large Corpora. 
 * MIT, June, 1995. http://xxx.lanl.gov/abs/cmp-lg/9511006
 *
 * 2) [Resnik99]. Resnik P. Semantic similarity in a taxonomy: an 
 * information-based measure and its application to problems of ambiguity in 
 * natural language. – Journal of Artificial Intelligence Research (JAIR), 1999.
 * – Vol. 11, No. , pp. 95-130. 
 * http://www.cs.washington.edu/research/jair/abstracts/resnik99a.htm
 * 
 * and metric's adaptation to Wikipedia in (used here):
 *
 * 3) [Strube06] Strube M., Ponzetto S. WikiRelate! Computing semantic relatedness using 
 * Wikipedia. In Proceedings of the 21st National Conference on Artificial 
 * Intelligence (AAAI 06). Boston, Mass., July 16-20, 2006. [to appear] 
 * http://www.eml-research.de/english/research/nlp/publications.php
 */
public class Hyponyms {
    
    /** Gets information content (ic) by a title of category page. The field 
     * cat_count.ic is used.
     */
    /*public static float getIC(String category_title) {return 0; }*/
    
    /** Gets title of common category of two pages with the maximum value of 
     * information content (IC), i.e. the most specific category parent.
     * The field cat_count.ic is used.
     *
     *  @return CatCount    object with initialized fields, null if common 
     *                      categories are absent
     */
    public static CatCount getCommonCategoryWithMaxIC(String page_title1, String page_title2,
                                SessionHolder session) {
        
        int page_id1 = PageTable.getIDByTitle(session.connect, page_title1);
        int page_id2 = PageTable.getIDByTitle(session.connect, page_title2);
        
        // get list of parent categories for page 1 and 2
        String  categories1[] = session.category_black_list.getCategoryUpIteratively(page_id1, null);
        String  categories2[] = session.category_black_list.getCategoryUpIteratively(page_id2, null);
        
        // intersect lists
        String  cat12[] = StringUtil.intersect(categories1, categories2);
        
        // get category row with maximum IC (for each in intersection)
        return CatCount.getMaxIC(session.connect.conn, cat12); 
    }
    
    
    /** Calculates and prints IC for 353 pairs of words (may be synonyms).
     */
    public static void dumpICWordSim353(SessionHolder session) {
        System.out.println("getCommonCategoryWithMaxIC");
        int i = 0;
        CatCount cc;
        
        System.out.println ("\nThe 353 words pairs are processing.\n");
        System.out.println ("N, word1, word2, human, InformationContent, NearestCommonCategoryParent");
        WordSim353 wordsim353 = new WordSim353();
        
        int skip = 0;
        for(WordSim w:wordsim353.data) {
            i++;
            String word1 = StringUtil.UpperFirstLetter(w.word1);
            String word2 = StringUtil.UpperFirstLetter(w.word2);
            //System.out.println ("The word Latin1ToUTF8 '"+Encodings.Latin1ToUTF8(all_words[i])+"' is processing...");
            //word1 = "Stupid";
            //word2 = "Smart";
            
            cc = Hyponyms.getCommonCategoryWithMaxIC(word1, word2, session);
            if (null == cc || cc.getInformationContent() < -0.9) { // skip: ic = -1 for Disambiguation pages, since there is common sub-categories of Main_page
                skip ++;
                //System.out.println (i + ", " + word1 + ", " + word2 + ", " + w.sim);
                //System.out.println (i + ", " + word1 + ", " + word2 + ", " + w.sim + ", 0");
            } else {
                System.out.println (i + ", " + word1 + ", " + word2 + ", " + w.sim + ", " + cc.getInformationContent() + ", " + cc.getPageTitle());
            }
            
            //if( i > 7)
              //break;
        }
        
        System.out.println("Skipped "+ skip + " items.");
    }
    
}
