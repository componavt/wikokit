/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wiwordik.word_card;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;


/** Word item for the list of translations.
 */    
public class TranslationCell extends ListCell<wiwordik.word_card.TranslationEntryItem> {
        
        // Text text;
    
        /*public TranslationCell(final ListView<wiwordik.word_card.TranslationEntryItem> list) {
            //label = new Label();       
            //setNode(label);       
        }*/
        
        @Override
        public void updateItem(wiwordik.word_card.TranslationEntryItem item, boolean empty) {
            super.updateItem(item, empty);
            
            // text = new Text(item.text);
            // text = new Text("the very temp");
            if(null != item) {
                setText(item.getLangCodeTranslation());
                //Node n = item.hbox;
                
                //System.out.println("TranslationCell.updateItem(): lang_name="+item.lang_name+
                //        "; lang_code=" + item.lang_code + 
                //        "; text="+item.text);
            
                //setGraphic(n);
                //setNode(n);
            }
            //setGraphic(item.hbox);
            //setNode(item.hbox);
        }
}
