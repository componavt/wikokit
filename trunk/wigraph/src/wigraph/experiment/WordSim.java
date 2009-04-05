/*
 * WordSim.java - words similarity data object. calculator.
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph.experiment;

/** 
 * Words similarity object constructor used for 
 * "The WordSimilarity-353 Test Collection".
 */
public class WordSim {
    
    /** First word in the pair */
    String word1;
    
    /** Second word in the pair */
    String word2;
    
    /** Similarity (human) */
    float  sim;
    
    public WordSim(String word1, String word2, float sim) {
        this.word1  = word1;
        this.word2  = word2;
        this.sim    = sim;
    }   
}