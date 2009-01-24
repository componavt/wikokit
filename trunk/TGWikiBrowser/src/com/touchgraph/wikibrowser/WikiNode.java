/*
 * TouchGraph LLC. Apache-Style Software License
 *
 *
 * Copyright (c) 2001-2002 Alexander Shapiro. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by 
 *        TouchGraph LLC (http://www.touchgraph.com/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "TouchGraph" or "TouchGraph LLC" must not be used to endorse 
 *    or promote products derived from this software without prior written 
 *    permission.  For written permission, please contact 
 *    alex@touchgraph.com
 *
 * 5. Products derived from this software may not be called "TouchGraph",
 *    nor may "TouchGraph" appear in their name, without prior written
 *    permission of alex@touchgraph.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL TOUCHGRAPH OR ITS CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 */

  
package com.touchgraph.wikibrowser;
import com.touchgraph.graphlayout.TGPanel;

import java.awt.*;
import java.io.*;

/**  WikiNode:  A WikiBrowser Node.  Extends node by adding data storage for URL's, hints.
  *  Some minor modifications have been made to the paint methods, allowing node color
  *  to show through on selection.
  *
  *  @author   Alexander Shapiro                                        
  *  @version  1.02 $Id: WikiNode.java,v 1.1.1.1 2007/01/07 07:58:19 andrew Exp $
  */

public class WikiNode extends com.touchgraph.graphlayout.Node {

    public static Color MOUSE_OVER_HYPERLINK_COLOR = Color.cyan;  
    String url;
    
    int hintWidth = 300;
    int hintHeight = 300; //A value of less then MINIMUM_HINT_HEIGHT means that the hint height 
                         //is automatically determined
    
    boolean mouseOverHyperlink;
    
    public WikiNode() {
        this(null,"","");
    }
    
    public WikiNode(String l) {
        this(null,l,"");
    }
    
    public WikiNode(String id, String l, String u) {
        super(id, l);       
        url = u;
        mouseOverHyperlink = false;
    }
    
    public void setURL(String u) {
        url = u;
    }

    public String getURL() {
        return url;
    }

    public void setMouseOverHyperlink(boolean mohl) {
        mouseOverHyperlink = mohl;
    }
    
    public boolean getMouseOverHyperlink() {
        return mouseOverHyperlink;
    }

    
    public int getWidth() {
        if(fontMetrics!=null && lbl!=null) {
            if(typ!=TYPE_ELLIPSE) 
                return fontMetrics.stringWidth(lbl) + 8;
            else
                return fontMetrics.stringWidth(lbl) + 28;
        }
        else
            return 8;
    }
    
    public int getHeight() {
        if (fontMetrics!=null) 
            return fontMetrics.getHeight()+2;
        else 
            return 8;                   
    }
    
    Color myBrighter(Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        
        if(b>r+64&&b>g+64) {r+=32;g+=32;} 
         
        r=Math.min(r+144, 255);
        g=Math.min(g+144, 255);
        b=Math.min(b+144, 255);
        
        return new Color(r,g,b);   
    }

    public void paint(Graphics g, TGPanel tgPanel) {
        if (!intersects(tgPanel.getSize()) ) return;
        paintNodeBody(g, tgPanel);
        
        int ix = (int)drawx;
        int iy = (int)drawy;
        int h = getHeight();
        int w = getWidth();
                
        if ( visibleEdgeCount()<edgeCount() ) {
            int tagX = ix+(w-6)/2-2+w%2;
            int tagY = iy-h/2-3;
            String hiddenEdgeStr = String.valueOf(edgeCount()-visibleEdgeCount());            
            g.setColor(Color.red);
            g.fillRect(tagX, tagY, 3+5*hiddenEdgeStr.length(), 8);
            g.setColor(Color.white);
            g.setFont(SMALL_TAG_FONT);
            g.drawString(hiddenEdgeStr, tagX+2, tagY+7);
        }
    }

    public Color getPaintUnselectedBackColor() {
            if (fixed) return BACK_FIXED_COLOR;
            if (markedForRemoval) return backColor.darker().darker();
            if (justMadeLocal) return myBrighter(backColor);
            return backColor;            
    }
        
    public Color getPaintTextColor(TGPanel tgPanel) {
        if ( this == tgPanel.getSelect() ) {
            return getPaintUnselectedBackColor();
        } else {
            return textColor;
        }
    }    
    
    public Color getPaintBackColor(TGPanel tgPanel) {
        if (mouseOverHyperlink) return MOUSE_OVER_HYPERLINK_COLOR;
        if ( this == tgPanel.getSelect() ) {
            return BACK_SELECT_COLOR;
        } else {
            return getPaintUnselectedBackColor();
        }
    }        
        
    public Color getPaintBorderColor(TGPanel tgPanel) {
        if ( this == tgPanel.getSelect() ) {
            if (fixed) return BACK_FIXED_COLOR;
            if (markedForRemoval) return new Color(100,60,40);
            if (justMadeLocal) return new Color(255,220,200);
            return backColor;            
        } else {
            return super.getPaintBorderColor(tgPanel);
        }
    }
}
