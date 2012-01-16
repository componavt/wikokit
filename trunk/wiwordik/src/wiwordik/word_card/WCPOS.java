/* WCLangPOS.java - A part of word card corresponds to a language-POS part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikt.constant.POS;
import wiwordik.WConstants;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.FontWeight;

/** Part of speech (POS) part of word card (WC) of a Wiktionary page.
 *
 * @see wikt.word.WPOS
 */
public class WCPOS {
    
    /** Part of speech. */
    POS pos_type;

    /** Title of the POS card. */
    String POS_header_value = "POS";


    /** (1) Meaning consists of Definitions + Quotations. */
    WCMeaning[] meaning;    // private WMeaning[] meaning;
    
    private final static WCMeaning[] NULL_WCMEANING_ARRAY = new WCMeaning[0];
    
    Text text_pos_header;

    public VBox group = new VBox();
/*    var meaning_group : VBox = VBox { spacing: 14 };

    public var group: VBox = VBox {
        spacing: 5
        // width: WCConstants.wrapping_width - 35
        content: [
            Text {
                content: bind POS_header_value
                font: Font {  size: 14  }
                fill: Color.BROWN
            }
            meaning_group]
    };
*/

    /** Creates text with a name of part of speech, e.g. "noun", or "conjunction". */
    public void createPOSText(TLangPOS _lang_pos) {
        
        POS _pos = _lang_pos.getPOS().getPOS();

        POS_header_value = _pos.toString(WConstants.native_lang);
        if (WConstants.DEBUGUI)
            POS_header_value += String.format("; lang_pos.id = %d", _lang_pos.getID());

        text_pos_header = new Text(POS_header_value);
        text_pos_header.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        text_pos_header.setFill(Color.BROWN);
    }
    
    /** Creates a POS part of card (parts of wiki pages),
     * builds visual block with this language.
    **/
    public void create(Connect conn,
                            //_tpage : TPage,
                            //_lang : LanguageType,
                       TLangPOS _lang_pos
                      )
    {
        //if(null != _tpage and null != _lang_pos) {
        if(null == _lang_pos) 
            return;
        
        createPOSText(_lang_pos);
        
        // get all translation blocks (for every meaning)
        //def _lang_pos : TLangPOS = _tmeaning.getLangPOS(conn);
        TLang lang = _lang_pos.getLang();
        TTranslation[] ttranslations = TTranslation.getByLangPOS(conn, _lang_pos);
        //System.out.println("WCPOS.create() _lang={_lang.getLanguage().toString()}; pos={_lang_pos.getPOS().getPOS().toString()}; sizeof translations={sizeof ttranslations}");

        VBox meaning_group = new VBox();
        List<WCMeaning> list_meaning = new ArrayList<>();
        
        TMeaning[] mm = TMeaning.get(conn, _lang_pos);
        for(TMeaning m : mm) {

            WCMeaning _meaning = new WCMeaning();
            _meaning.create(conn, m, mm.length, lang, ttranslations);
            list_meaning.add(_meaning);    // logic: insert _meaning into meaning;
                                       
//          insert _meaning.group_mrt into meaning_group.content;   // visual
            meaning_group.getChildren().addAll(_meaning.group_mrt); // visual

        }
        meaning = ((WCMeaning[])list_meaning.toArray(NULL_WCMEANING_ARRAY)); // logic
        
        meaning_group.setSpacing(14);        
        group.setSpacing(5);
        group.getChildren().addAll(text_pos_header, meaning_group);
    }
}
