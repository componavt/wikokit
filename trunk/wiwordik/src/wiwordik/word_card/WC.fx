/* WC.fx - Word card corresponds to a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.api.*;
import wikt.sql.*;
import wikt.constant.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import java.lang.*;

import javafx.scene.control.ScrollView;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import wiwordik.WConstants;
import javafx.scene.control.Hyperlink;
import java.net.*;

/** One word card describes one word (page, entry in Wiktionary).
 *
 * Word's definition, semantic relations, quotations, translations.
 */
public class WC{ // extends JFrame {
    
    /** Current TPage corresponds to selected word. */
    public var tpage : TPage;

    /** Languages of the word. @see wikt.word.WLanguage */
    //var cx_lang : WCLanguage[];
    
    /** Todo: text of card should be available by Ctrl+C. */
    var card_text_value : String;
    
/*var page_title_Label: Label = Label {
            //x: 10  y: 30
            font: Font { size: 16 }
            //content: "слово"
            text: "page_title"
        }
*/
    var header_page_title: String;

    var height : Integer = 600;
    
    var headerText: Text = Text {

            content: "headerText (page.is_in_wiktionary)" // bind definition_text_value // "line 1\n\nline2\n \nline3"
            // wrappingWidth: WCConstants.wrapping_width - 30
            //wrappingWidth: bind card_scene.width - 30
            
            font: Font {
                name: "sansserif"
                size: 14
                oblique: true
            }
            fill: Color.YELLOWGREEN
        }

    var link_to_wikt: Hyperlink = Hyperlink{
        //text: "http://{WCConstants.native_lang}.wiktionary.org/wiki/"
        //text: wiwordik.Main.native_lang.toString()
        font: Font {
                name: "sansserif"
                size: 12
                oblique: true
            }
        
        action: function():Void{
            try {
                // java.awt.Desktop.getDesktop().browse(new URI(link_to_wikt.text));
                def desktopClass = Class.forName("java.awt.Desktop");
                def desktop = desktopClass.getMethod("getDesktop").invoke(null);
                def browseMethod = desktopClass.getMethod("browse", [URI.class] as java.lang.Class[]);
                browseMethod.invoke(desktop, new URI(link_to_wikt.text));
                // AppletStageExtension.showDocument(link_to_wikt.text);
            } catch (e) {
                System.out.println("Error: the following link could not be open: {link_to_wikt.text}");
            }
        }
    }

    // temp (all POS as one text) ---------------------------
    var langPOSCards: Text = Text {

                //content: "langPOSCards todo"
                content: bind card_text_value // "line 1\n\nline2\n \nline3"
                wrappingWidth: WConstants.wrapping_width

                font: Font {
                    name: "sansserif"
                    size: 10
                }
                fill: Color.BLUE
            }
    // ---------------------- eo temp

    
    var lang_VBox : VBox = VBox {
            //opacity:  0.5
            width: bind card_scene.width - 30
            //width: wrapping_width +2 - 30
            spacing: 20
        };
    
    /*public var card : Group = Group {
        content : VBox {
            //content: [headerText, langPOSCards]
            content: [headerText, lang_VBox, langPOSCards]
            spacing: 10
        };
    }*/

    /*public var card : Stage = Stage {
    title : bind header_page_title
    scene: Scene {
        width: 382
        height: 500
        //content: Flow{
            content:

            //SwingScrollPane{
                //height: bind Math.min(150, (sizeof swing_list_group) * (font_size +5)) // 165
            //    scrollable: true
                //font: Font {  size: font_size }

           //     view:
                
                    VBox {
                        content: [headerText, lang_VBox]
                        //content: [headerText, lang_VBox, langPOSCards]
                        spacing: 10
                    }
            //}
        //}
      }
    }*/

    var card_scene: Scene;

    public var card : Stage = Stage {
    title : bind header_page_title
    scene: card_scene = Scene {
        width: WConstants.wrapping_width + 2
        height: height
        content:
        ScrollView {
                width: bind card_scene.width
                height: bind card_scene.height
                //fitToWidth: true
                //nodeWidth: width + 20
                //nodeHeight: height + 20
                //hbarPolicy: ScrollBarPolicy.NEVER

                node: HBox {
                    padding: Insets { left: 5 top: 1 bottom: 10}
                    spacing: 20
                    content: [

                        VBox {
                            // x: bind scene.width-scene.width*0.9
                            
                            //padding: Insets {left: 5 right: 5}
                            content: [

                                HBox {
                                    spacing: 12
                                    content: [ headerText, link_to_wikt]
                                }

                                lang_VBox]
                            //content: [headerText, lang_VBox, langPOSCards]
                            spacing: 10
                        }
                    ]
                }
            }
        }
    }



    /** Prints meanings for each language.

        Wiktionary language (e.g. Russian in Russian Wiktionary)
        should be in first place in enumeration of definitions in different languages.
    */
    function getDefinitionsForLangPOS(
                    conn : Connect,
                    page_title : String,            // source word
                    _lang_pos_array : TLangPOS[])
                    : String {

        var lang_pos_def_collection : String[];     // collection of definitions for each Lang and POS

        for (i in [0.. sizeof _lang_pos_array-1]) {
            def lang_pos : TLangPOS = _lang_pos_array[i];
            //TMeaning.get(arg0, arg1) //lang_pos.
            //definition_Text.content = _lang_pos_array.size().toString(); // number of lang-POS pairs

            // simple
            var lang : LanguageType = lang_pos.getLang().getLanguage();
            var pos : POS = lang_pos.getPOS().getPOS();

            var s : String = "{lang.getName()} ({lang.getCode()}):{pos.toString()}";
            if (WConstants.DEBUGUI)
                s = "{s}; lang_pos.id = {lang_pos.getID()}";
            s = "{s}\n";

            def definitions : String[] = WTMeaning.getDefinitionsByLangPOS(conn, lang_pos);

            def synonyms : String[] = WTRelation.getForEachMeaningByPageLang(conn, lang_pos, Relation.synonymy);
            //def synonyms : String[] = ["", "synonyms 2"];

            for (j in [0.. sizeof definitions-1]) {
                s = s.concat("  {j+1}. {definitions[j]}\n");

                if(sizeof synonyms > j and synonyms[j].length() > 0)
                    //s = s.concat("    Syn.: {synonyms[j]}\n");
                    s = s.concat("    Syn.: {synonyms[j]}\n");
                            //fill: Color.rgb (0xec, 0xed, 0xee)
                    // todo: Text {fill: Color.BLUE; content: synonyms[j]}
            }
            
            if(lang == conn.getNativeLanguage()) {
                insert s before lang_pos_def_collection[0];
            } else {
                insert s into lang_pos_def_collection;
            }
        }

        var res : String;
        if (1 == sizeof lang_pos_def_collection) { // let's skip numbering "1)" if there is only one lang
            res = lang_pos_def_collection[0];
        } else {
            for (i in [0.. sizeof lang_pos_def_collection-1]) {
                def s : String = lang_pos_def_collection[i];
                res = "{res}{i+1}) {s}\n \n"; // "\n \n" because the bug in JavaFX http://javafx-jira.kenai.com/browse/JFXC-3299
            }
        }
        return res;
    }

    /** Gets link to entry, if there is a corresponding Wiktionary article.*/
    public function getLinktoWiktionaryEntry (_tpage : TPage):String {

        var s : String = "";
        if(_tpage.isInWiktionary()) {

            // replace all spaces by underscores "_"
            var s_underscored = _tpage.getPageTitle().replaceAll(" ", "_");

            s = "http://{WConstants.native_lang}.wiktionary.org/wiki/{s_underscored}";
            //System.out.println("page_title={page_title}, but link_to_wikt.text={link_to_wikt.text}");
        }
        return s;
    }

    /** Prints page_title as a header of word card.
     *  Prints sign "+" or "-" if there is a separate page for this word in WT.
    */
    public function printHeaderText (_tpage : TPage) {

        if(null != _tpage) {

            def page_title : String = _tpage.getPageTitle();
            header_page_title = page_title;

            var s : String = "";
            if(_tpage.isInWiktionary()) {
                s = "{page_title} (+)"; }
            else
                s = "{page_title} (-)";

            if(_tpage.isRedirect())
                s += "\n \nСм. {_tpage.getRedirect()} (Redirect)";

            headerText.content = s;

            link_to_wikt.text = getLinktoWiktionaryEntry(_tpage);
        }
    }
    
    /** Word is a selected word in the list, an index is a number of the word.
     *  Prints meanings for each language.
     */
    public function getDataForSelectedWordByTPage (
                                        conn : Connect,
                                        _tpage : TPage) {
                                        //word : String, index:Integer) {
        if(null != _tpage) {

            //System.out.println("Ops 1. getDataForSelectedWordByTPage");
            // printHeaderText (_tpage); it will be printed in createCXLangList

            if(_tpage.isRedirect()) {
                headerText.content = "\n \nСм. {_tpage.getRedirect()} (Redirect)";

                // System.out.println("\nRedirect.");
            } else {
                // Prints meanings for each language
                var lang_pos_array : TLangPOS[] = TLangPOS.get(conn, _tpage);     // var lang_pos_array : TLangPOS[]
                card_text_value = getDefinitionsForLangPOS(conn, _tpage.getPageTitle(), lang_pos_array);

                // System.out.println("\nNot a redirect.");
            }
        } else
            card_text_value = "";

        // System.out.println(" title={_tpage.getPageTitle()}.");
        // System.out.println(" getRedirect={_tpage.getRedirect()}.");
    }
    
    
    /** Word is given by user, language is uknown, so prints all languages.
     *
     * If a word (printed by user) is absent in dictionary, then print first
     * word from the list of nearest words.
     *
     * @params page_array list of titles of pages in ListView window (scroll)
    **/
    public function getDataByWord(conn : Connect, word : String,
                                   page_array: TPage[]) {

        headerText.content = word;

        var _tpage = TPage.get(conn, word);

        if(null == _tpage and sizeof page_array > 0) // If a word is absent in dictionary...
            _tpage = page_array[0];                  // then let's take first from the scroll list

        getDataForSelectedWordByTPage (conn, _tpage);
        // System.out.println("\n\n. getDataByWord(). definition_Text.content = {definition_text_value}.");
    }


    /** Creates list of sub-languages (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills ->lang_VBox.content[]
     *
     * @params page_array list of titles of pages in ListView window (scroll).
    **/
    public function createCXLangList( conn : Connect,
                                      _tpage : TPage) {
        if(null != _tpage) {

            //System.out.println("Ops 2. createCXLangList");
            printHeaderText (_tpage);

            // Prints meanings for each language
            var lang_pos_array : TLangPOS[] = TLangPOS.get(conn, _tpage);     // var lang_pos_array : TLangPOS[]

            // get list of languages for this word
            def tlanguages : TLang[] = TLangPOS.getLanguages(conn, _tpage);

            lang_VBox.content = [];     // clear
            for (tl in tlanguages) {

                def l : WCLanguage = new WCLanguage();
                l.create(conn, card_scene, tl.getLanguage(), lang_pos_array);

                // Wiktionary (native) language should be before foreign languages
                if(tl.getLanguage() == conn.getNativeLanguage()) {
                    insert l.group before lang_VBox.content[0];
                } else {
                    insert l.group into lang_VBox.content;
                }
            }
        }
    }
    
    /** Creates list of sub-languages (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills ->lang_VBox.content[]
     *
     * @params page_array list of titles of pages in ListView window (scroll).
    **/
    public function createCXLangListByWord( conn : Connect, word : String,
                                        page_array: TPage[]) {

        //headerText.content = word;

        var _tpage = TPage.get(conn, word);

        if(null == _tpage and sizeof page_array > 0) // If a word is absent in dictionary...
            _tpage = page_array[0];                  // then let's take first from the scroll list

        createCXLangList (conn, _tpage);

        //group.parent.notifyAll();
        //card.notifyAll();
    }


}
