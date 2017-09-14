/* Image.java - picture in the Wiktionary entry related to some meaning of word, 
 * filename and picture caption are contained in [[File:...]] or {{илл|}}.
 * 
 * Copyright (c) 2017 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.constant;

/** Image (picture) related to some definition (meaning).
 * 
 * Filenames and captions of images could be presented in Wiktionary entry.
 */
public class Image {
    
    
    /** File name of image at Commons. One meaning has one (or zero) image. 
     */
    private String  filename;
    
    /** Text of image caption. */
    private String  caption;
    
    /** Number of meaning could be presented.
     * -1 by default
     */
    private int meaning_number;
    
    public Image(String filename, String caption) {
                             // -1, that is meaning number is absent
        this(filename, caption, -1);
    }
    
    public Image(String filename, String caption, int meaning_number) {
    
        if(filename.length() == 0)
            System.out.println("Error in constructor Image::Image(): filename is empty!");
        
        this.filename   = filename; 
        this.caption    = caption;
        this.meaning_number = meaning_number;
    }
    
    /** Gets name of file at Commons.
     */
    public String getFilename() {
        return filename;
    }
    
    /** Gets text caption of image.
     */
    public String getCaption() {
        return caption;
    }
    
    /** Gets number of meaning related to this image.
     */
    public int getMeaningNumber() {
        return meaning_number;
    }
    
    // if(null == params || params.length == 0)
            //return NULL_STRING_ARRAY;
    
}
