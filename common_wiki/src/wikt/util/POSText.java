/* POSText - data structure consists of a POS code and the corresponding text.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.util;

//import wikt.constant.POSType;
import wikt.constant.POS;

/** Data structure consists of a POS code and the corresponding text.  */
public class POSText {
    
    /** Part of speech code of the text. */
    private POS pos;
    
    /** POS name found in text, e.g. explicitly: "Verb", or implicitly "stitch I". */
    //private String pos_name;
    
    /** Text */
    private StringBuffer text;
    
    public POSText() {}
    
    /*public POSText(POSType _pos) { //, StringBuffer _text) {
        pos = _pos;
        text = new StringBuffer();
        //text = _text;
    }*/
    
    //public POSText(POSType _pos, StringBuffer _text) {
    public POSText(POS _pos, String _text) {
        pos = _pos;
        text = new StringBuffer(_text);
    }
    
    public POS getPOSType() {
        return pos;
    }

    public StringBuffer getText() {
        return text;
    }

}
