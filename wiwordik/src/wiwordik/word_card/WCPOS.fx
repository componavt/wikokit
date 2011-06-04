/* WCLangPOS.fx - A part of word card corresponds to a language-POS part
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

/** Part of speech (POS) part of word card (WC) of a Wiktionary page.
 *
 * @see wikt.word.WPOS
 */
public class WCPOS {
    
    /** Part of speech. */
    var pos_type : POS;

    /** Title of the POS card. */
    var POS_header_value : String = "POS";


    /** (1) Meaning consists of Definitions + Quotations. */
    var meaning : WCMeaning[];    // private WMeaning[] meaning;

    var meaning_group : VBox = VBox { spacing: 14 };


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


    /** Creates a POS part of card (parts of wiki pages),
     * builds visual block with this language.
    **/
    public function create(conn : Connect,
                            //_tpage : TPage,
                            //_lang : LanguageType,
                            _lang_pos : TLangPOS
                           ) {

        //if(null != _tpage and null != _lang_pos) {
        if(null != _lang_pos) {

            var _pos : POS = _lang_pos.getPOS().getPOS();

            POS_header_value = "{_pos.toString(WConstants.native_lang)}";
            if (WConstants.DEBUGUI)
                POS_header_value = POS_header_value.concat("; lang_pos.id = {_lang_pos.getID()}");
            
            // get all translation blocks (for every meaning)
            //def _lang_pos : TLangPOS = _tmeaning.getLangPOS(conn);
            def lang : TLang = _lang_pos.getLang();
            def ttranslations : TTranslation[] = TTranslation.getByLangPOS(conn, _lang_pos);
            //System.out.println("WCPOS.create() _lang={_lang.getLanguage().toString()}; pos={_lang_pos.getPOS().getPOS().toString()}; sizeof translations={sizeof ttranslations}");

            def mm : TMeaning[] = TMeaning.get(conn, _lang_pos);
            for(m in mm) {

                def _meaning : WCMeaning = new WCMeaning();

                _meaning.create(conn, m, sizeof mm, lang, ttranslations);

                insert _meaning into meaning;                       // logic
                insert _meaning.group_mrt into meaning_group.content;   // visual
            }
        }
    }

}
