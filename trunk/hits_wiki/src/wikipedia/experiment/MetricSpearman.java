/*
 * MetricSpearman.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.experiment;

import wikipedia.kleinberg.Article;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class WordPosition {
    /** Position of word in the first list */
    public int pos1;
    /** Position of word in the second list */
    public int pos2;
    //public int dist;
    public WordPosition() {
        pos1 = pos2 = -1;
    }
};


/** Spearman's rank correlation coefficient (Spearman's Footrule) is a metric.
 * It assesses how well an arbitrary monotonic function could describe 
 * the relationship between two variables, without making any assumptions 
 * about the frequency distribution of the variables.
 * See http://en.wikipedia.org/wiki/Spearman's_rank_correlation_coefficient
 * 
 */
public class MetricSpearman {
    
    public MetricSpearman() {
    }
    
    /** Returns true if a is b with precision eps. */
    public static boolean equals(double a,double b,double eps) {
        return Math.abs(a - b) < eps ? true : false;
    }
    
    
    /** Compares titles of two list of articles. List can have different length. 
     * It is supposed that list1 (list2) has no duplicate values.
     * @return -1 if List<Article> is null, bigger returned value means bigger difference. 
     */
    public static int compare(List<Article> list1, List<Article> list2) {
        int   i;
        if( null == list1 || 0 == list1.size() ||
            null == list2 || 0 == list2.size()) {
            return -1;
        }
        String[] s1 = new String[list1.size()];
        String[] s2 = new String[list2.size()];
        for(i=0; i<s1.length; i++) {
            s1[i] = list1.get(i).page_title;
        }
        for(i=0; i<s2.length; i++) {
            s2[i] = list2.get(i).page_title;
        }
        return compare(s1,s2);
    }
    
    /** Compares two list of words. List can have different length. 
     * It is supposed that list1 (list2) has no duplicate values.
     * 
     * Return -1 if String[] is null.
     * Bigger returned value means bigger difference. 
     * If element of short list is absent in long list, then 
     * dist += (length(long_list) // ? - word_position_in_short_list
     */
    public static int compare(String[] list1, String[] list2) {
        if( null == list1 || 0 == list1.length ||
            null == list2 || 0 == list2.length) {
            return -1;
        }
        
        String[] list_short;
        String[] list_long;
        if(list1.length < list2.length) {
            list_short = list1;
            list_long  = list2;
        } else {
            list_short = list2;
            list_long  = list1;
        }
       
        int dist = 0;
        
        // Position of word in the first list:
        // m<Word, Position_in_list1>
        Map<String, Integer> map_long = new HashMap<String, Integer>();
        
        for(int i=0; i<list_long.length; i++) {
            map_long.put(list_long[i], i);
        }
        
        //boolean b_once = false;
        for(int i=0; i<list_short.length; i++) {
            if(map_long.containsKey(list_short[i])) {
                dist += Math.abs(map_long.get(list_short[i]) - i);
                //b_once = true;
            } else {
                dist += list_long.length;
            }
        }
        
        return dist;
    }
    
    
    private static Map<String, WordPosition> createMap (String[] list1,String[] list2) {
        Map<String, WordPosition> map = new HashMap<String, WordPosition>();
        
        for(int i=0; i<list1.length; i++) {
            WordPosition wp = new WordPosition();
            wp.pos1 = i;
            map.put(list1[i], wp);
        }
        
        for(int i=0; i<list2.length; i++) {
            if(map.containsKey(list2[i])) {
                WordPosition wp = map.get(list2[i]);
                wp.pos2 = i;
            }
        }
        return map;
    }
    
    
    /** Calculates the distance between two list of words 
     * (1 - similar, 0 - unrelated lists), 
     * (S1, S2 - reranked lists with common elements) by the formula:
     * <pre>
     * <math>  Fr = 1- {{sum {S1 - S2}} over {MaxFr}</math>
     * MaxFr = (|S|^2)/2            if |S| - even; 
     * MaxFr = (|S|+1)*(|S|-1)/2    if |S| - odd.</pre>
     * 
     * List can have different length. 
     * It is supposed that list1 (list2) has no duplicate values.
     * 
     * Return -1 if String[] is null.
     */
    public static double calcSpearmanFootrule(String[] list1, String[] list2) {
        if( null == list1 || 0 == list1.length ||
            null == list2 || 0 == list2.length) {
            return -1;
        }
        
        if(1 == list1.length && 1 == list2.length)
        {
            if(list1[0].equalsIgnoreCase(list2[0]))
                return 1;
            return 0;
        }
        
        Map<String, WordPosition> map = createMap(list1, list2);
        List<String> s1 = new ArrayList<String>();
        List<String> s2 = new ArrayList<String>();
        
        for(String s:list1) {
            WordPosition wp = map.get(s);
            if(-1 != wp.pos2) {
                s1.add(s);
            }
        }
        
        for(String s:list2) {
            if(map.containsKey(s)) {
                WordPosition wp = map.get(s);
                if(-1 != wp.pos1) {
                    s2.add(s);
                }
            }
        }
        
        assert(s1.size() == s2.size());
        if(0 == s1.size() || 0 == s2.size())
            return 0;
        if(1 == s1.size() && 1 == s2.size())
        {
            if(s1.get(0).equalsIgnoreCase(s2.get(0)))
                return 1;
            return 0;
        }
        double dist = (double)compare((String[])s1.toArray(new String[0]),
                           (String[])s2.toArray(new String[0]));
        
        double max_fr;
        int b = s1.size();
        if(b == b >> 1 << 1) {  // even
            max_fr = b*b / 2; 
        } else {                // odd
            max_fr = (b+1)*(b-1) / 2; 
        }
        
        dist = 1 - dist / max_fr;
        return dist;
    }
    
    
    /** Finds elements of small array in big one. Concatenates these elements
     * in one string using 'token', add position of these elements in big array.
     */ 
    public static String findStringWithPosition (String[] big, String[] small, String token) {
        
        Map<String, WordPosition> map = createMap(big, small);
        List<String> s1 = new ArrayList<String>();
        List<String> s2 = new ArrayList<String>();
        
        String res = "";
        for(int i=0; i<big.length; i++) {
            WordPosition wp = map.get(big[i]);
            if(-1 != wp.pos2) {
                if(0 < res.length()) {
                    res += token;
                }
                res += big[i] + i;
            }
        }
        
        return res;
    }
}


//class WordPosition {
    // Position of word in the first list 
//    public int pos1;
    /** Position of word in the second list */
    //public int pos2;
    //public int dist;
//};












