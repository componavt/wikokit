/* WC.fx - Word card corresponds to a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.api.WTRelation;
import wikokit.base.wikt.api.WTMeaning;
import java.awt.Dimension;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.language.LanguageType;

import java.lang.*;

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
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.FontWeight;
import javax.swing.JFrame;

/** One word card describes one word (page, entry in Wiktionary).
 *
 * Word's definition, semantic relations, quotations, translations.
 */
public class WC {
    
    /** Current TPage corresponds to selected word. */
    public TPage tpage;

    /** Languages of the word. @see wikt.word.WLanguage */
    //var cx_lang : WCLanguage[];
    
    /** Todo: text of card should be available by Ctrl+C. */
    String card_text_value;
    
/*var page_title_Label: Label = Label {
            //x: 10  y: 30
            font: Font { size: 16 }
            //content: "слово"
            text: "page_title"
        }
*/
    /*var headerText: Text = Text {

            content: "headerText (page.is_in_wiktionary)" // bind definition_text_value // "line 1\n\nline2\n \nline3"
            
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
    }*/
    
    String header_page_title;

    // int height = 600;
    
    
    JFrame card;
    //Stage card;
    Scene card_scene;
    
    //card.setScene(scene);
    //card.show();
    Text headerText;
    Hyperlink link_to_wikt;
    VBox lang_VBox;
    
    /** Prints meanings for each language.

        Wiktionary language (e.g. Russian in Russian Wiktionary)
        should be in first place in enumeration of definitions in different languages.
    */
    String getDefinitionsForLangPOS(
                    Connect conn,
                    String page_title,            // source word
                    TLangPOS[] _lang_pos_array)
    {

        LinkedList<String> lang_pos_def_collection = new LinkedList<String>();     // collection of definitions for each Lang and POS

        for (int i=0; i<_lang_pos_array.length; i++) {
            TLangPOS lang_pos = _lang_pos_array[i];
            //TMeaning.get(arg0, arg1) //lang_pos.
            //definition_Text.content = _lang_pos_array.size().toString(); // number of lang-POS pairs

            // simple
            LanguageType lang = lang_pos.getLang().getLanguage();
            POS pos = lang_pos.getPOS().getPOS();

            String s = lang.getName() + " (" + lang.getCode() + "):" + pos.toString();
            if (WConstants.DEBUGUI)
                s = s + "; lang_pos.id = " + lang_pos.getID();
            s = s + "\n";

            String[] definitions = WTMeaning.getDefinitionsByLangPOS(conn, lang_pos);

            String[] synonyms = WTRelation.getForEachMeaningByPageLang(conn, lang_pos, Relation.synonymy);
            //def synonyms : String[] = ["", "synonyms 2"];

            for (int j=0; j<definitions.length; j++) {
                s = s + "  " + (j+1) + ". " + definitions[j] + " \n";

                if(synonyms.length > j && synonyms[j].length() > 0)
                    s = s + "    Syn.: " + synonyms[j] + "\n";
                            //fill: Color.rgb (0xec, 0xed, 0xee)
                    // todo: Text {fill: Color.BLUE; content: synonyms[j]}
            }
            
            if(lang == conn.getNativeLanguage()) {
                // insert s before lang_pos_def_collection[0];
                lang_pos_def_collection.addFirst(s);
            } else {
                //insert s into lang_pos_def_collection;
                lang_pos_def_collection.add(s);
            }
        }

        String res = "";
        if (1 == lang_pos_def_collection.size()) { // let's skip numbering "1)" if there is only one lang
            res = lang_pos_def_collection.getFirst();
        } else {
            for (int i=0; i<lang_pos_def_collection.size(); i++) {
                String s = lang_pos_def_collection.get(i);
                res = res + (i+1) + ") " + s + "}\n \n"; // "\n \n" because the bug in JavaFX http://javafx-jira.kenai.com/browse/JFXC-3299
                
                // res = "{res}{i+1}) {s}\n \n"; // "\n \n" because the bug in JavaFX http://javafx-jira.kenai.com/browse/JFXC-3299
            }
        }
        return res;
    }

    /** Gets link to entry, if there is a corresponding Wiktionary article.*/
    public String getLinktoWiktionaryEntry (TPage _tpage) {

        String s = "";
        if(null != _tpage) {
        //if(_tpage.isInWiktionary()) {
            // todo - in really, isInWiktionary() should be called - to test more

            // replace all spaces by underscores "_"
            String s_underscored = _tpage.getPageTitle().replaceAll(" ", "_");

            s = "http://" + WConstants.native_lang + ".wiktionary.org/wiki/" + s_underscored;
            //System.out.println("WC.getLinktoWiktionaryEntry: link="+s);
        }
        return s;
    }

    /** Prints page_title as a header of a word card.
     *  Prints sign "+" or "-" if there is a separate page for this word in WT.
    */
    public void printHeaderText (TPage _tpage) {

        if(null != _tpage) {

            String page_title = _tpage.getPageTitle();
            header_page_title = page_title;
            card.setTitle(header_page_title);

            String s = "";
            if(_tpage.isInWiktionary()) {
                s = page_title + " (+)"; }
            else
                s = page_title + " (-)";

            if(_tpage.isRedirect())
                s += "\n \nSee " + _tpage.getRedirect() + " (Redirect)"; // See or См.???
            
            // headerText.content = s;  // content: "headerText (page.is_in_wiktionary)" 
            headerText.setText(s);
            
            String link = getLinktoWiktionaryEntry(_tpage);
            if(link.length() > 0)
                link_to_wikt.setText(link);
        }
    }
    
    /** Word is a selected word in the list, an index is a number of the word.
     *  Prints meanings for each language.
     */
    public void getDataForSelectedWordByTPage (
                                        Connect conn,
                                        TPage _tpage) {
                                        //word : String, index:Integer) {
        if(null != _tpage) {

            //System.out.println("Ops 1. getDataForSelectedWordByTPage");
            // printHeaderText (_tpage); it will be printed in createCXLangList

            if(_tpage.isRedirect()) {
                headerText.setText(
                headerText.getText() + "\n \nSee " + _tpage.getRedirect() + " (Redirect)"); // See or См.???
                //  headerText.content = "\n \nСм. {_tpage.getRedirect()} (Redirect)";

                // System.out.println("\nRedirect.");
            } else {
                // Prints meanings for each language
                TLangPOS[] lang_pos_array = TLangPOS.get(conn, _tpage);     // var lang_pos_array : TLangPOS[]
                card_text_value = getDefinitionsForLangPOS(conn, _tpage.getPageTitle(), lang_pos_array);

                // System.out.println("\nNot a redirect.");
            }
        } else
            card_text_value = "";

        // System.out.println(" title={_tpage.getPageTitle()}.");
        // System.out.println(" getRedirect={_tpage.getRedirect()}.");
    }
    
    
    /** Word is given by user, language is unknown, so prints all languages.
     *
     * If a word (printed by user) is absent in dictionary, then print first
     * word from the list of nearest words.
     *
     * @params page_array list of titles of pages in ListView window (scroll)
    **/
    public void getDataByWord(Connect conn, String word,
                              TPage[] page_array) {

        headerText.setText(word);

        TPage _tpage = TPage.get(conn, word);

        if(null == _tpage && page_array.length > 0) // If a word is absent in dictionary...
            _tpage = page_array[0];                 // then let's take first from the scroll list

        getDataForSelectedWordByTPage (conn, _tpage);
        // System.out.println("\n\n. getDataByWord(). definition_Text.content = {definition_text_value}.");
    }
    
    void initHeaderAndLink() {
        
        headerText = new Text();
        headerText.setFont(Font.font("sansserif", FontWeight.LIGHT, 14));
        headerText.setFill(Color.YELLOWGREEN);  
        
        link_to_wikt = new Hyperlink();
        link_to_wikt.setFont(Font.font("sansserif", FontWeight.LIGHT, 12));
            
        link_to_wikt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //System.out.println("This link is clicked");

                try {
                    URI u = new URI(link_to_wikt.getText());
                    java.awt.Desktop.getDesktop().browse(u);

                    //def desktopClass = Class.forName("java.awt.Desktop");
                    //def desktop = desktopClass.getMethod("getDesktop").invoke(null);
                    //def browseMethod = desktopClass.getMethod("browse", [URI.class] as java.lang.Class[]);
                    //browseMethod.invoke(desktop, new URI(link_to_wikt.text));

                } catch (final Exception exc) {
                    System.out.println("Error: the following link could not be open:" + link_to_wikt.getText());
                }
            }
        });
    }

    void initGUI() {
        initHeaderAndLink();
        
        card = new JFrame();
        card.setPreferredSize(new Dimension(WConstants.wordcard_width, WConstants.wordcard_height));
        card.setMinimumSize(new Dimension(WConstants.wordcard_min_width, WConstants.wordcard_min_height));
        
        final JFXPanel fxPanel = new JFXPanel();
        
        VBox root_vbox = new VBox();
        root_vbox.setSpacing(10);
        root_vbox.setPadding(new Insets(1,  0,    10,    5));

        
        ScrollPane sp = new ScrollPane();
        sp.setVmax(WConstants.wordcard_height*2);
        sp.setPrefSize(WConstants.wordcard_width, WConstants.wordcard_height);

        sp.setContent(root_vbox);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setFitToWidth(true);
        //sp.setFitToHeight(true);
        
        
        Scene scene = new Scene(sp, WConstants.wordcard_width, WConstants.wordcard_height);
        fxPanel.setScene(scene);
        
        HBox root2_hbox = new HBox();
        root2_hbox.setSpacing(12);
        //                          top right bottom left
        root2_hbox.setPadding(new Insets(1,  0,    10,    5));
        
        lang_VBox = new VBox();
        lang_VBox.setSpacing(20);
        
        root2_hbox.getChildren().addAll(headerText);
        root2_hbox.getChildren().addAll(link_to_wikt);

        
        root_vbox.getChildren().addAll(root2_hbox);
        root_vbox.getChildren().addAll(lang_VBox);
        //root_vbox.getChildren().addAll(sp);
        
        fxPanel.setSize(WConstants.wordcard_height, WConstants.wordcard_width);
        card.add(fxPanel);
        card.setVisible(true);
    }
    

    /** Creates list of sub-languages (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills ->lang_VBox.content[]
     *
     * @params page_array list of titles of pages in ListView window (scroll).
    **/
    public void createCXLangList( Connect conn,
                                  TPage _tpage) {
        if(null != _tpage) {
            //System.out.println("Ops 2. createCXLangList");

            initGUI();
            printHeaderText (_tpage);

            // Prints meanings for each language
            TLangPOS[] lang_pos_array = TLangPOS.get(conn, _tpage);     // var lang_pos_array : TLangPOS[]

            // get list of languages for this word
            TLang[] tlanguages = TLangPOS.getLanguages(conn, _tpage);

            lang_VBox.getChildren().clear();
            
            // 1. for 1: add language section in native (e.g., English for English Wiktionary)
            // Wiktionary (native) language should be before foreign languages
            //insert l.group into lang_VBox.content;
            for (TLang tl : tlanguages) {
                if(tl.getLanguage() == conn.getNativeLanguage()) {    
                    WCLanguage l = new WCLanguage();
                    l.create(conn, card_scene, tl.getLanguage(), lang_pos_array);
                    lang_VBox.getChildren().add(l.group);
                } 
            }
            
            // 2. for 2: add all other languages
            for (TLang tl : tlanguages) {
                if(tl.getLanguage() != conn.getNativeLanguage()) {

                    WCLanguage l = new WCLanguage();
                    l.create(conn, card_scene, tl.getLanguage(), lang_pos_array);
                    lang_VBox.getChildren().add(l.group);
                }
            }
            
            if(lang_pos_array.length > 0) {// big new frame (with word card)
                card.setPreferredSize(new Dimension(WConstants.wordcard_width, WConstants.wordcard_height));
                card.setMinimumSize(new Dimension(WConstants.wordcard_width, WConstants.wordcard_height));
            }
        }
    }
    
    /** Creates list of sub-languages (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills ->lang_VBox.content[]
     *
     * @params page_array list of titles of pages in ListView window (scroll).
    **/
    /*public void createCXLangListByWord( Connect conn, String word,
                                        TPage[] page_array) {

        //headerText.content = word;

        TPage _tpage = TPage.get(conn, word);

        if(null == _tpage && page_array.length > 0) // If a word is absent in dictionary...
            _tpage = page_array[0];                  // then let's take first from the scroll list

        createCXLangList (conn, _tpage);

        //group.parent.notifyAll();
        //card.notifyAll();
    }*/


}





/*
  ==================================================================================
  
 public var card : Group = Group {
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