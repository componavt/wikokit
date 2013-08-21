/* LabelsWikiText - data structure consists of context labels and wikified text (with these labels).
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.util;

import java.util.List;
import wikokit.base.wikt.constant.Label;

/** Data structure consists of an array of context labels 
 * and the corresponding text (e.g. a synonym, i.e. semantic relation).
 */
public class LabelsWikiText {
    
    /** Array of context labels. */
    private Label[]  labels;
    
    /** WikiText */
    private WikiText text;
    
    public LabelsWikiText(Label[] _labels, WikiText _text) {
        labels = _labels;
        text = _text;
    }
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    public LabelsWikiText(List<Label> _labels, WikiText _text) {
        if(null == _labels) {
            labels = null;
        } else {
            labels = (Label[])_labels.toArray(NULL_LABEL_ARRAY);
        }
        
        text = _text;
    }
    
    /** Gets context labels array. */
    public Label[] getLabels() {
        return labels;
    }
    
    /** Gets WikiText. */
    public WikiText getWikiText() {
        return text;
    }
    
    /** Frees memory recursively. */
    public void free ()
    {
        text.free();
    }
    
    /** @return true if object are equals (the same labels and the WikiText). */
    static public boolean equals (LabelsWikiText one, LabelsWikiText two) {
    
        if (null == one && null == two)
            return true;
        if (null == one || null == two)
            return false;
        
        if(!WikiText.equals(one.text, two.text))
            return false;
        
        // compare two arrays of labels
        if(one.labels.length != two.labels.length)
            return false;
        for(int i=0; i<one.labels.length; i++) {
            boolean b_identical = false;
            
            for(int j=0; j<two.labels.length; j++) {
                if(Label.equals( one.labels[i], two.labels[j] )) {
                    b_identical = true;
                    break;
                }
            }
            if(!b_identical)
                return false;
        }
        return true;
    };
    
}
