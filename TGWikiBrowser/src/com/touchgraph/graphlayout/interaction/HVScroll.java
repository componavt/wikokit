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

package com.touchgraph.graphlayout.interaction;

import  com.touchgraph.graphlayout.*;

import  java.awt.event.*;
import  java.awt.Point;
import  javax.swing.*;

/** HVScroll:  Allows for scrolling horizontaly+vertically.  This can be
  * done in all sorts of ways, for instance by using the scrollbars, or by
  * dragging.
  *
  * <p>
  * This code is more complex then it would seem it should be, because scrolling
  * has to be independent of the screen being warped by lenses.  HVScroll needs
  * to use the tgLensSet object because the offset is recorded in real coordinates, while
  * the user interacts with the drawn coordinates.
  *
  * @author   Alexander Shapiro
  * @version  1.21  $Id: HVScroll.java,v 1.1.1.1 2007/01/07 07:58:19 andrew Exp $
  */
public class HVScroll implements GraphListener {

    private DScrollbar horizontalSB;
    private DScrollbar verticalSB;

    HVLens hvLens;
    HVDragUI hvDragUI;
    HVScrollToCenterUI hvScrollToCenterUI;
    public boolean scrolling;

    private boolean adjustmentIsInternal;
    private TGPanel tgPanel;
    private TGLensSet tgLensSet;

    TGPoint2D offset;

  // ............

   /** Constructor with a TGPanel <tt>tgp</tt> and TGLensSet <tt>tgls</tt>.
     */
    public HVScroll(TGPanel tgp, TGLensSet tgls) {
        tgPanel=tgp;
        tgLensSet=tgls;

        offset=new TGPoint2D(0,0);
        scrolling = false;
        adjustmentIsInternal = false;

        horizontalSB = new DScrollbar(JScrollBar.HORIZONTAL, 0, 100, -1000, 1100);
        horizontalSB.setBlockIncrement(100);
        horizontalSB.setUnitIncrement(20);

        horizontalSB.addAdjustmentListener(new horizAdjustmentListener());

        verticalSB = new DScrollbar(JScrollBar.VERTICAL, 0, 100, -1000, 1100);
        verticalSB.setBlockIncrement(100);
        verticalSB.setUnitIncrement(20);

        verticalSB.addAdjustmentListener(new vertAdjustmentListener());

        hvLens=new HVLens();
        hvDragUI = new HVDragUI(); //Hopefully this approach won't eat too much memory
        hvScrollToCenterUI = new HVScrollToCenterUI();

        tgPanel.addGraphListener(this);
    }

    public JScrollBar getHorizontalSB() { return horizontalSB; }

    public JScrollBar getVerticalSB() { return verticalSB; }

    public HVDragUI getHVDragUI() { return hvDragUI; }

    public HVLens getLens() { return hvLens; }

    public TGAbstractClickUI getHVScrollToCenterUI() { return hvScrollToCenterUI; }

    public TGPoint2D getTopLeftDraw() {
        TGPoint2D tld = tgPanel.getTopLeftDraw();
        tld.setLocation(tld.x-tgPanel.getSize().width/4,tld.y-tgPanel.getSize().height/4);
        return tld;
    }

    public TGPoint2D getBottomRightDraw() {
        TGPoint2D brd = tgPanel.getBottomRightDraw();
        brd.setLocation(brd.x+tgPanel.getSize().width/4,brd.y+tgPanel.getSize().height/4);
        return brd;
    }

    public TGPoint2D getDrawCenter() { //Should probably be called from tgPanel
        return new TGPoint2D(tgPanel.getSize().width/2,tgPanel.getSize().height/2);
    }

    public void graphMoved() { //From GraphListener interface
        if (tgPanel.getDragNode()==null && tgPanel.getSize().height>0)    {
            TGPoint2D drawCenter = getDrawCenter();

            TGPoint2D tld = getTopLeftDraw();
            TGPoint2D brd = getBottomRightDraw();

            double newH = (-(tld.x-drawCenter.x)/(brd.x-tld.x)*2000-1000);
            double newV = (-(tld.y-drawCenter.y)/(brd.y-tld.y)*2000-1000);

            boolean beyondBorder;
            beyondBorder = true;

            if(newH<horizontalSB.getMaximum() && newH>horizontalSB.getMinimum() &&
               newV<verticalSB.getMaximum()   && newV>verticalSB.getMinimum() ) beyondBorder=false;

            adjustmentIsInternal = true;
            horizontalSB.setDValue(newH);
            verticalSB.setDValue(newV);
            adjustmentIsInternal = false;

            if (beyondBorder) {
                adjustHOffset();
                adjustVOffset();
                tgPanel.repaint();
            }
        }
    }

    public void graphReset() { //From GraphListener interface
        horizontalSB.setDValue(0);
        verticalSB.setDValue(0);

        adjustHOffset();
        adjustVOffset();
    }

    class DScrollbar extends JScrollBar {
        private double doubleValue;

        DScrollbar(int orient, int val, int vis, int min, int max){
            super(orient, val, vis, min, max);
            doubleValue=val;
        }
        public void setValue(int v) { doubleValue = v; super.setValue(v); }
        public void setIValue(int v) { super.setValue(v); }
        public void setDValue(double v) {
            doubleValue = Math.max(getMinimum(),Math.min(getMaximum(),v));
            setIValue((int) v);
        }
        public double getDValue() { return doubleValue;}
    }

    private void adjustHOffset() { //The inverse of the "graphMoved" function.
        //System.out.println(horizontalSB.getDValue());
        for(int iterate=0;iterate<3;iterate++) { // Iteration needed to yeild cerrect results depite warping lenses
            TGPoint2D center= tgPanel.getCenter();
            TGPoint2D tld = getTopLeftDraw();
            TGPoint2D brd = getBottomRightDraw();

            double newx = ((horizontalSB.getDValue()+1000.0)/2000)*(brd.x-tld.x)+tld.x;
            double newy = tgPanel.getSize().height/2;
            TGPoint2D newCenter = tgLensSet.convDrawToReal(newx,newy);

            offset.setX(offset.x+(newCenter.x-center.x));
            offset.setY(offset.y+(newCenter.y-center.y));
            
            tgPanel.processGraphMove();
        }
    }

    private void adjustVOffset() { //The inverse of the "graphMoved" function.
        for(int iterate=0;iterate<10;iterate++) { // Iteration needed to yeild cerrect results depite warping lenses
            TGPoint2D center= tgPanel.getCenter();
            TGPoint2D tld = getTopLeftDraw();
            TGPoint2D brd = getBottomRightDraw();

            double newx = tgPanel.getSize().width/2;
            double newy = ((verticalSB.getDValue()+1000.0)/2000)*(brd.y-tld.y)+tld.y;

            TGPoint2D newCenter = tgLensSet.convDrawToReal(newx,newy);

            offset.setX(offset.x+(newCenter.x-center.x));
            offset.setY(offset.y+(newCenter.y-center.y));
            
            tgPanel.processGraphMove();
        }
    }

    private class horizAdjustmentListener implements AdjustmentListener {
        public void adjustmentValueChanged(AdjustmentEvent e) {
              if(!adjustmentIsInternal) {
                  adjustHOffset();
                tgPanel.repaintAfterMove();
            }
        }
    }

    private class vertAdjustmentListener implements AdjustmentListener {
        public void adjustmentValueChanged(AdjustmentEvent e) {
            if(!adjustmentIsInternal) {
                adjustVOffset();
                tgPanel.repaintAfterMove();
            }
        }
    }


     class HVLens extends TGAbstractLens {
        protected void applyLens(TGPoint2D p) {
            p.x=p.x-offset.x;
            p.y=p.y-offset.y;
        }

        protected void undoLens(TGPoint2D p) {
            p.x=p.x+offset.x;
            p.y=p.y+offset.y;
        }
     }

     public void setOffset(Point p) {
        offset.setLocation(p.x,p.y);
        tgPanel.processGraphMove(); //Adjust draw coordinates to include new offset
        graphMoved(); //adjusts scrollbars to fit draw coordinates
     }

     public Point getOffset() {
        return new Point((int) offset.x,(int) offset.y);
     }
    
     public void scrollAtoB(TGPoint2D drawFrom, TGPoint2D drawTo) {
        TGPoint2D from = tgLensSet.convDrawToReal(drawFrom);
        TGPoint2D to = tgLensSet.convDrawToReal(drawTo);
        offset.setX(offset.x+(from.x-to.x));
        offset.setY(offset.y+(from.y-to.y));
     }

     public void scrollToCenter(final Node n) {
         final TGPoint2D drawFrom =new TGPoint2D(n.drawx,n.drawy);
         final TGPoint2D drawTo = getDrawCenter();
         scrollAtoB(drawFrom,drawTo);           
     }
     
     public void slowScrollToCenter(final Node n) {
         final TGPoint2D drawFrom =new TGPoint2D(n.drawx,n.drawy);
         final TGPoint2D drawTo = getDrawCenter();
         scrolling = true;
         Thread scrollThread = new Thread() {
             public void run() {
                double fx= drawFrom.x;
                double fy= drawFrom.y;
                double tx= drawTo.x;
                double ty= drawTo.y;
                int scrollSteps = (int) Math.sqrt((fx-tx)*(fx-tx)+(fy-ty)*(fy-ty))/10 + 1;

                for(int i=0;i< scrollSteps-1;i++) {
                    //double fromPcnt = (Math.cos(Math.PI*i/scrollSteps)+1)/2;
                    //double toPcnt = (Math.cos(Math.PI*(i+1)/scrollSteps)+1)/2;

                    double fromPcnt = ((double) scrollSteps-i)/scrollSteps;
                    double toPcnt = ((double) scrollSteps-1-i)/scrollSteps;

                    double midfx = fx*fromPcnt+tx*(1-fromPcnt);
                    double midfy = fy*fromPcnt+ty*(1-fromPcnt);
                    double midtx = fx*toPcnt+tx*(1-toPcnt);
                    double midty = fy*toPcnt+ty*(1-toPcnt);

                    scrollAtoB(new TGPoint2D(midfx,midfy), new TGPoint2D(midtx,midty));
                    tgPanel.repaintAfterMove();
                    try {
                           Thread.currentThread().sleep(50);
                    } catch (InterruptedException ex) {    }
                }
                scrollAtoB(new TGPoint2D(n.drawx,n.drawy),getDrawCenter()); //for good measure
                tgPanel.repaintAfterMove();
                HVScroll.this.scrolling = false;
            }
        };
        scrollThread.start();
     }

    class HVScrollToCenterUI extends TGAbstractClickUI {
        public void mouseClicked(MouseEvent e) {
            Node mouseOverN=tgPanel.getMouseOverN();
            if(!scrolling && mouseOverN!=null)
                slowScrollToCenter(mouseOverN);
        }
    }

    class HVDragUI extends TGAbstractDragUI{
        TGPoint2D lastMousePos;
        HVDragUI() { super(HVScroll.this.tgPanel); }

        public void preActivate() {}
        public void preDeactivate() {}

        public void mousePressed(MouseEvent e) {
            lastMousePos = new TGPoint2D(e.getX(), e.getY());
        }
        public void mouseReleased(MouseEvent e) {}
        public void mouseDragged(MouseEvent e) {
            if(!scrolling) scrollAtoB(lastMousePos, new TGPoint2D(e.getX(), e.getY()));
            lastMousePos.setLocation(e.getX(),e.getY());
            this.tgPanel.repaintAfterMove();
        }
    }

} // end com.touchgraph.graphlayout.interaction.HVScroll
