/* LabelText.java - data structure consists of context labels and text (with these labels).
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.util;

import java.util.List;
import wikokit.base.wikt.constant.Label;

/** Data structure consists of an array of context labels 
 * and the corresponding text (e.g. a definition).
 */
public class LabelsText {
    
    /** Array of context labels. */
    private Label[]  labels;
    
    /** to delete: New context labels from the template {{context|new label}} which are not listed in the LabelEn. */
    // private String[] new_labels; 
    
    /** Text */
    private String text;
    
    // public LabelText() {}
    
    public LabelsText(Label[] _labels, String _text) {
        labels = _labels;
        text = _text;       //text = new StringBuffer();
    }
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    public LabelsText(List<Label> _labels, String _text) {
        labels = (Label[])_labels.toArray(NULL_LABEL_ARRAY);
        text = _text;
    }
    
    /** Gets context labels array. */
    public Label[] getLabels() {
        return labels;
    }
    
    /** to delete Gets array of new context labels which are not listed in the LabelEn. */
    /*public String[] getNewLabels() {
        return new_labels;
    }*/
    
    /** Gets text. */
    public String getText() {
        return text;
    }
    
    /** @return true if object are equals (the same labels and the same text). */
    static public boolean equals (LabelsText one, LabelsText two) {
    
        if (null == one && null == two)
            return true;
        if (null == one || null == two)
            return false;
        
        if(!one.text.equals(two.text))
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
    }
}
