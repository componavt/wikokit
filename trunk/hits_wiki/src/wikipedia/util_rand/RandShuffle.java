/*
 * RandShuffle.java Random access to data. Permutation and shuffle of source data.
 * @author Andrew Krizhanovsky /mail: aka at mail.iias.spb.su/
 * Created on 19 May 2005
 */

package wikipedia.util_rand;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandShuffle {
    
    /** Creates a new instance of RandShuffle */
    public RandShuffle() {
    }

     /*
    // return {true, true, ... true} size of n_total
    public static boolean[] GetTrueBoolArray (int n_total) {
        boolean[] b = new boolean[n_total];
        
        for(int i=0; i<n_total; i++) {
            b [i] = true;  
        }
        return b;
    }
     */
    
    /** Create and return boolean random array
     *  @param n_true     - number of elements with true value
     *  @param n_total    - total number of boolean elements
     */
    public static boolean[] getRandArray (int n_true, int n_total) {
        int     i;
        
        if(n_total < 1 || n_true > n_total) 
            return null;
        
        boolean[] b = new boolean[n_total];
        
        for(i=0; i<n_true; i++) {
            b [i] = true;  
        }
        
        // take as input the array b and rearrange elements in random order
        for (i=0; i<n_total; i++) {
            int r = (int) (Math.random() * (i+1));     // int between 0 and i
            boolean swap = b[r];
            b[r] = b[i];
            b[i] = swap;
        }
        return b;
    }
    
    
    /** Create and return boolean array with true elements
     *  @param n_size     - size of returned array
     */
    /*public static boolean[] getTrueArray (int n_size) {
        
        if(n_size < 1) 
            return null;
        
        boolean[] b = new boolean[n_size];
        
        for(int i=0; i<n_size; i++) {
            b [i] = true;  
        }
        return b;
    }*/
    

    /** Select randomly n_limit elements from source
     */
    /*public static int[] getRandIntArray(int[] source, int n_limit) {
        int     i, counter;
        if (null == source || 0 == n_limit)
            return null;
        if (n_limit < 0 || n_limit >= source.length)
            return source;
        
        boolean[] b_rand = RandShuffle.getRandBoolArray (n_limit, source.length);
        
        int[]  result = new int[n_limit];
        counter = 0;
        for (i=0; i<b_rand.length; i++) {
            if (b_rand[i]) {
                result[counter ++] = source[i];
            }
        }
        return result;
    }*/
    
    public static int[] permuteRandomly(int[] source) {
        int i;
        Integer[] objects = new Integer[source.length];
        for(i=0; i<source.length; i++) {
            objects[i] = source[i];
        }
                
        List<Integer> list = Arrays.asList(objects);
        Collections.shuffle(list);
        
        for(i=0; i<source.length; i++) {
            source[i] = list.get(i);
        }
        return source;
    }
     
    // todo rewrite, use shuffle from:
    // file:///mnt/win_e/all/docs/programming/java/tutorial_Nov_10_2006/collections/interfaces/list.html
    // file:///mnt/win_e/doc_huge/programming/java/tutorial_Nov_10_2006/collections/interfaces/list.html
    /*public static int[] permuteRandomly(int[] source) {
        int     i, counter;
        if (null == source)
            return null;

        int[] b = new int[source.length];
        for (i=0; i<source.length; i++) {
            b[i] = source[i];
        }
        
        // take as input the array b and rearrange elements in random order
        for (i=0; i<source.length; i++) {
            int r = (int) (Math.random() * (i+1));     // int between 0 and i
            int swap = b[r];
            b[r] = b[i];
            b[i] = swap;
        }
        return b;
    }*/
    
}
