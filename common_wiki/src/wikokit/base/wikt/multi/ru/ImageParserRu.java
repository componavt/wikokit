/* ImageParserRu.java - set of functions to extract pictures filenames 
 * and pictures captions {{илл|}}.
 * 
 * Copyright (c) 2017 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wikokit.base.wikt.constant.Image;
import wikokit.base.wikipedia.util.template.TemplateExtractor;
import wikokit.base.wikt.word.WMeaning;

/** Set of functions to extract pictures filenames and pictures captions 
 * from the template {{илл|}} in Russian Wiktionary.
 * 
 * @see wikokit.base.wikipedia.text.ImageParser about [[Image:...]]
 * @see https://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%B8%D0%BB%D0%BB
 * 
 * Check MySQL results of parsing:
 * SELECT * FROM image, image_meaning WHERE image_meaning.image_id=image.id LIMIT 33;
 * SELECT filename,image_caption,image_id,meaning_id FROM image, image_meaning WHERE image_meaning.image_id=image.id LIMIT 33;
 */
public final class ImageParserRu {
    //private static final boolean DEBUG = true;
    
    private final static Image[] NULL_IMAGE_ARRAY = new Image[0];
    
    
    static class MeaningNumberInCaption {
        
        static public String caption_without_number;
        
        static public int meaning_number;
                                                          //       \[\d+\]
        private final static Pattern ptrn_digit = Pattern.compile("\\[(\\d+)\\]");
        

        /** Parse caption, extract [meaning number], write it to -> meaning_number; 
         * removes number with brackets from caption and write to -> caption_without_number.
         * 
         * @return true if [meaning number] was found in the caption
         */
        static public boolean parse(String caption) {
            
            // "3rd object's caption [3]" -> "3rd object's caption"
            
            Matcher m = ptrn_digit.matcher(caption);
            if(!m.find())
                return false;
            
            meaning_number = Integer.parseInt(m.group(1));
            caption_without_number = m.replaceAll("").trim().replaceAll(" +", " ");
                                                             // replace 2 or more spaces with single space
            return true;
        }
    }
    

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
        
        // 1. modern variant: template case {{илл|filename|caption [meaning number]}}
        String _filename, _caption;
                
        String template_name = "илл";
        TemplateExtractor[] te = TemplateExtractor.getAllTemplatesByName(page_title, template_name, text);
        
        if(0 == te.length)
            return NULL_IMAGE_ARRAY;
        
        Collection<Image> result = new ArrayList<>();
        for (TemplateExtractor t : te) {
            
            // 1. first required parameter (without name) is filename
            String[] params = t.getTemplateParameters();
            
            params = TemplateExtractor.excludeParameter (params, "lang");
            
            if(params.length == 0)
                continue;
            
            _filename = params[0].trim();
            if(_filename.length() == 0)
                continue;
            
            // 2. second optional parameter (without name) is title
            _caption = "";
            if(params.length > 1) {
                if(!params[1].contains("="))
                    _caption = params[1].trim();
            }
            
            // meaning number as fifth optional parameter (with name 'n')
            int _meaning_number = -1;
            String str_meaning_number = TemplateExtractor.getParameterValueByNameOrNumber (params, "n", 5);
            if(null == str_meaning_number)
                str_meaning_number = TemplateExtractor.getParameterValueByNameOrNumber (params, "№", 5);
            if(null != str_meaning_number)
                _meaning_number = Integer.parseInt(str_meaning_number);
            
            // meaning number in the caption string
            if(_caption.length() > 4 &&     // at least len("word [N]") > 4 &&
                   -1 == _meaning_number)   // there is no parameter 'meaning number'
            {
                if(ImageParserRu.MeaningNumberInCaption.parse(_caption)) {
                    _meaning_number = ImageParserRu.MeaningNumberInCaption.meaning_number;
                    _caption        = ImageParserRu.MeaningNumberInCaption.caption_without_number;
                }
            }
        
            if(-1 == _meaning_number)
                _meaning_number = 1;        // default number is 'first' meaning
            
            Image i = new Image(_filename, _caption, _meaning_number);
            result.add( i );
            
            //if(DEBUG)
            //    System.out.println("page_title:" + page_title + ";  caption=" + _caption + ";  _filename=" + _filename);
        }
        
        // 2. outdated variant: bare figure of Commons, for example: [[Файл:Declaration of Independence - USA.jpg|мини|200 px|декларация]]
        // todo ...
        
        return ((Image[])result.toArray(NULL_IMAGE_ARRAY));
    }
}
