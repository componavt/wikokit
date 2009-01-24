/*
 * FreqLink.java -  to calculate the frequency of l_from and l_to for each article.
 *      It will be used to find the most frequently used words (stop-words), 
 *      which should be skipped while searching of synonyms.
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */
/*
package wikipedia;
import java.sql.*;

public class FreqLink {
    
  
    public FreqLink() {
    }
*/    
    /** Goal: calculate number of links_in, links_out for each article
     *
     *  Algorithm
     *   ?  1. Get next unique n_pack elements from the table link
     *          SQL:
     *      2. Take only links which are article, i.e. type=???
     *          SQL:
     *   ?  3. 
     *
     *  SELECT link_in, COUNT(link_in) FROM links WHERE links_in=749 GROUP BY article;
     *
     *  SELECT COUNT(*) FROM links;                     // total links
     *  SELECT COUNT(*) FROM links GROUP BY l_from;     // unique links l_from
     *  SELECT l_from, COUNT(l_from) AS size FROM links GROUP BY l_from ORDER BY size;  // l_from detail statistics
     *  SELECT l_to, COUNT(l_to) AS size FROM links GROUP BY l_to ORDER BY size;        // l_to detail statistics
     *  
     *  Analysis of links table (russian):
     *      total links: 306471; unique links l_from 24530; unique l_to 18446
     *  l_to field
     *      10 articles > 750 times; 
     *      145         > 200 times
     *      520         > 100 times
     *  l_from field
     *      33 articles > 200 times
     *      380         > 100 times
     *
     *  English links table
     *      total links: 18 380 035; unique links l_from 1 355 280 (8 min); unique l_to ???
     *  
     */
/*    
    public void CreateFreqLinkTable() {
        
    }
*/    
    /** Get article's frequency in the table links: count(l_from) and count(l_to)
     *  Example Russian 'domra' (id=749):
     *  SELECT COUNT(*) FROM links WHERE l_from=749;    6
     *  SELECT COUNT(*) FROM links WHERE l_to=749;      3
     */
    /*public void CountLFrom(int l_from) {
        
    }*/
/*    
}
*/