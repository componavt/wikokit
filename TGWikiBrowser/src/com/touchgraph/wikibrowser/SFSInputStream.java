/*
 * TouchGraph LLC. Apache-Style Software License
 *
 *
 * Copyright (c) 2002 Alexander Shapiro. All rights reserved.
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

import java.io.*;

/**  SFSInputStream:  A "Specified File Size" input stream used for reading from URL's.
  *  The stream keeps track of how many bites have been read.  If there is more data to
  *  be read, the read() methods are guaranteed to return some data.  If a read method
  *  is called when there is currently no data availiable, it waits untill some is,
  *  and then returns.
  *  
  *  @author   Alexander Shapiro                                        
  *  @version  1.02
  */


class SFSInputStream extends FilterInputStream {
    long fileSize;
    final static int INFINITE_FS = -1;
    long totalBitesRead = 0;
    
    public SFSInputStream(InputStream in) {
        super(in);
        fileSize = INFINITE_FS;
            
    }         
        
    public SFSInputStream(InputStream in, long fsize) {
        super(in);
        fileSize = fsize;
    }         
 
    public int read() throws IOException {
        //When fileSize bites have been read, assume no more bites in stream
        if (totalBitesRead>=fileSize && fileSize!=INFINITE_FS) return -1;
         
        int charRead = super.read();
        for (int attempt=0; charRead==-1 && attempt<100 ; attempt++) {
            charRead = super.read();
            try {
                Thread.currentThread().sleep(100);
                System.out.println("Waiting");
            }
            catch (InterruptedException e) {};
        }
        if (charRead!=-1) totalBitesRead++;
        return charRead;
    }
    
    public int read(byte[] b, int off, int len) throws IOException {             
        //When fileSize bites have been read, assume no more bites in stream
        if (totalBitesRead>=fileSize && fileSize!=INFINITE_FS) {
            b = new byte[0];
            return -1;
        }
        
        int bitesRead = super.read(b, off, len);
        for (int attempt=0;totalBitesRead<fileSize && bitesRead==0 && attempt<100 ; attempt++) {
            bitesRead = super.read(b, off, len);
            try {
                Thread.currentThread().sleep(100);
                System.out.println("Waiting");
            }
            catch (InterruptedException e) {};
        }
        totalBitesRead += bitesRead;        
        return bitesRead;
    }
}
