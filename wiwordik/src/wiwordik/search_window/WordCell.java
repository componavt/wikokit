/*
 */
package wiwordik.search_window;

import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

/** Word item for the list of words, list of entries.
 */    
public class WordCell extends ListCell<String> {
        
        Text text;
        
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            
            text = new Text(item);
            setGraphic(text);
        }
}
