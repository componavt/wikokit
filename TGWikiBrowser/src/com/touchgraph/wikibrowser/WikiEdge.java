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
import java.io.*;
import java.awt.*;

/**  WikiEdge:  A WikiBrowser Edge.
  *
  *  @author   Alexander Shapiro                                        
  *  @version  1.02 $Id: WikiEdge.java,v 1.1.1.1 2007/01/07 07:58:19 andrew Exp $
  */

public class WikiEdge extends com.touchgraph.graphlayout.Edge {

    public WikiEdge(WikiNode f, WikiNode t) {
        this(f, t, DEFAULT_LENGTH);
	}	

    public WikiEdge(WikiNode f, WikiNode t, int len) {
        super(f,t,len);
	}	

	public void paint(Graphics g, TGPanel tgPanel) {
        Color c;
        
        if (tgPanel.getMouseOverN()==from || tgPanel.getMouseOverE()==this) 
            c = MOUSE_OVER_COLOR; 
        else
            c = col;        

		int x1=(int) from.drawx;
		int y1=(int) from.drawy;
		int x2=(int) to.drawx;
		int y2=(int) to.drawy;
		if (intersects(tgPanel.getSize())) {
            paintArrow(g, x1, y1, x2, y2, c);
		}
	}	
}