/* DebugPanel.fx - Tools (now only one Debug chechbox) which helps to track
 *                 the database of the machine-readable dictionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wiwordik.WConstants;

import javafx.scene.layout.HBox;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;

/** Tools (now only one Debug chechbox) which helps to track the database
 *  of the machine-readable dictionary.
 */
public class DebugPanel {
    
    public CheckBox debug_checkbox = new CheckBox();
    
    /** Set parameters of the class.
     */
    public void initialize() {
        
        // GUI
        debug_checkbox.setText("Debug");
        Tooltip tp = new Tooltip();
        tp.setText("ID of records\n" +
                    "from the database of the machine-readable dictionary\n" +
                    "(in new opened word cards)");
        debug_checkbox.setTooltip(tp);
        
        // If user clicks CheckBox and select to debug
        debug_checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val)
            {
                boolean b_selected = debug_checkbox.isSelected();
                
                if (WConstants.DEBUGUI != b_selected) {
                    WConstants.DEBUGUI  = b_selected;

                    // println("CheckBox: WConstants.DEBUG={WConstants.DEBUG}");
                }
            }
        });
        
    }

    /*
      public var debug_HBox: HBox = HBox {
        content: [
                debug_CheckBox
        ]
        //spacing: 10
    };*/

}
