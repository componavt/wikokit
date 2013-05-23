/* LabelText.java - data structure consists of context labels and text (after this labels).
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.util;

import wikokit.base.wikt.constant.Label;

/** Data structure consists of an array of context labels and the corresponding text.
 */
public class LabelText {
    
    /** Array of context labels. */
    private Label[]  labels;
    
    /** Text */
    private String text;
    
    // public LabelText() {}
    
    public LabelText(Label[] _labels, String _text) {
        labels = _labels;
        text = _text;       //text = new StringBuffer();
    }

    /** Gets context labels array. */
    public Label[] getLabels() {
        return labels;
    }
    
    /** Gets context labels array. */
    public String getText() {
        return text;
    }
}
