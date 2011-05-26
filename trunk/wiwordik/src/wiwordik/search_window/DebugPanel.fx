/* DebugPanel.fx - Tools (now only one Debug chechbox) which helps to track
 *                 the database of the machine-readable dictionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import wiwordik.WConstants;

import javafx.scene.layout.HBox;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;

/** Tools (now only one Debug chechbox) which helps to track the database
 *  of the machine-readable dictionary.
 */
public class DebugPanel {

    
    var debug_CheckBox: CheckBox = CheckBox {
        text: "Debug"
        tooltip: Tooltip {
            text: "ID of records\n"
                    "from the database of the machine-readable dictionary\n"
                    "(in new opened word cards)"
        }

        onMouseReleased: function(e:MouseEvent) {

                if (WConstants.DEBUG != debug_CheckBox.selected) {
                    WConstants.DEBUG  = debug_CheckBox.selected;

                    // println("CheckBox: WConstants.DEBUG={WConstants.DEBUG}");
                }
            }
      }

      public var debug_HBox: HBox = HBox {
        content: [
                debug_CheckBox
        ]
        //spacing: 10
    };

}
