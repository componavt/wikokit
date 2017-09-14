/* ImageParserRu.java - set of functions to extract pictures filenames 
 * and pictures captions {{илл|}}.
 * 
 * Copyright (c) 2017 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.constant.Image;

/** Set of functions to extract pictures filenames and pictures captions 
 * from the template {{илл|}} in Russian Wiktionary.
 * 
 * @see https://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%B8%D0%BB%D0%BB
 */
public final class ImageParserRu {
    
    
    //     public static LabelsText extractLabelsTrimText(String page_title, String line)

    /** Gets information about images: filename, caption, 
     * number of meaning in the caption.
     * 
     * Rules related to meaning_number:
     * 1) If there is no meaning_number in the caption text, 
     *      then this image belong to the first meaning.
     *    (Level-up logic: if there is only one meaning, then any image belong to this alone meaning.)
     * 2) If there are several images related to the same meaning, 
     *    then only one image (first or last?) will be stored to the result image array.
     * 
     * @param page_title  word described in this article 'text'
     * @param text Wiktionary entry text which may contain image text
     * @return 
     */
    public static Image[] getFilenameAndCaptionFromText(String page_title, String text) {
        
        
        
        return null;
    }
    
    
    
}
