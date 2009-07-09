/*
 * ScrollNode.fx
 *
 * Created on 14 mars 2009, 14:53:55
 */

package wiwordik.util;

/**
 * @author Alex
 * http://forums.sun.com/thread.jspa?threadID=5366984
 */


//import fxtests.ScrollNode;
import java.lang.Object;
import javafx.ext.swing.SwingSlider;
import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.TextOrigin;


public class ScrollNode extends CustomNode {

    public-init var node: Node;

    public-init var nodeWidth: Number;
    public-init var nodeHeight: Number;

    public var scrollHeight = 20;
    public var Y_speed = 55;
    public var width = 200;
    public var height = 200;
    public var posX: Integer = 0 on replace {
        node.translateX = -posX
    };
    public var posY: Integer =
    nodeWidth - width as Integer on replace {
        node.translateY = (posY-(nodeWidth - width)) * Y_speed
    };


    public override function create(): Node {
        return Group {
            content: [
                // horizontal slider
                SwingSlider {
                    value: bind posX with inverse
                    minimum: 0
                    maximum: nodeWidth - width as Integer
                    width: width
                    height: 20
                    translateX: 20
                    // translateY: nodeHeight
                    vertical: false;
                }

                // vertical slider
                SwingSlider {
                    value: bind posY with inverse
                    minimum: 0
                    maximum: nodeHeight - height as Integer
                    height: height
                    width: 20
                    //translateX: nodeWidth
                    translateY: 20
                    vertical: true
                }


                Group {
                    translateX: scrollHeight
                    translateY: scrollHeight
                    clip: Rectangle {
                        width: width
                        height: height
                    }

                    content: node
                }
                /*Text {
                    translateX: scrollHeight+10
                    translateY: scrollHeight+10
                    textOrigin: TextOrigin.TOP
                    content: bind "node position: {node.translateX}, {node.translateY}"
                }*/

            ]
        };
    }
}
function run() {
    Stage {
        title: "ScrollNode"
        scene: Scene {
            width: 400
            height: 400
            content: [
                ScrollNode {
                    width: 200
                    height: 200
                    nodeHeight: 220
                    nodeWidth: 220
                    node: Rectangle {
                        width: 500
                        height: 500
                        fill: LinearGradient {
                            startX: 0.0
                            startY: 0.0
                            endX: 1.0
                            endY: 1.0
                            stops: [
                                Stop {
                                    color: Color.WHITE
                                    offset: 0.0
                                },
                                Stop {
                                    color: Color.BLACK
                                    offset: 1.0
                                },

                            ]
                        }
                    }

                }

            ]
        }
    }
}
