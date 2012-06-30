/* ConcatenatingProgressThread.java - progress bar for concatenating
 * files (parts of the dictionary database file).
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.kiwidict.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.db.FileUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ConcatenatingProgressThread extends Thread {
    
    Handler mHandler;
    
    // Constructor with an argument that specifies Handler on main thread
    // to which messages will be sent by this thread.

    public ConcatenatingProgressThread (Handler h) {
        mHandler = h;
    }
    
    // Override the run() method that will be invoked automatically when 
    // the Thread starts.  Do the work required to update the progress bar on this
    // thread but send a message to the Handler on the main UI thread to actually
    // change the visual representation of the progress.

    @Override
    public void run() {
        
        boolean b_success = false;
        String path = FileUtil.getPathAtExternalStorageByDirectoryName(Connect.DB_DIR);
                    
        String[] file_parts = new String[2];
        file_parts[0] = Connect.getDBFilenamePart1();
        file_parts[1] = Connect.getDBFilenamePart2();
        b_success = concatenateFiles (path, file_parts, Connect.getDBFilename());
        
        // extract multi-volume .zip, .z01 archive
        //String[] zip_file_parts = new String[2];
        //zip_file_parts[0] = Connect.getDBZipFilename();
        //zip_file_parts[1] = zip_part2;
        //b_success = unpackMultiPartZip (path, zip_file_parts);
    
        
        // Send message to Handler on UI thread
        // that the concatenating was finished.

        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putBoolean("done", true);
        b.putBoolean("success", b_success);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }


    /* Concatenates files to one file, 
     * from the "file_path / zip_file" location. */
    private boolean concatenateFiles (String file_path, String[] files, String result_file) {
        boolean b_success = true;

        SequenceInputStream seq;
        
        try {
            Vector<InputStream> v = new Vector<InputStream>(files.length);
            for (int i = 0; i < files.length; i++) {
                FileInputStream fis = new FileInputStream(file_path + File.separator + files[i]);
                BufferedInputStream bis = new BufferedInputStream(fis);
                v.add(bis);
            }
            Enumeration<InputStream> e = v.elements();
            seq = new SequenceInputStream(e);
            
            String result = file_path + File.separator + result_file;
            FileOutputStream fout = new FileOutputStream(result);
            OutputStream os = new BufferedOutputStream(fout);
            try {
                final int buffer_size = 1024;
                byte[] buffer = new byte[buffer_size];
                int i = 0, current_bytes = 0;
                for (int read_bytes = -1; (read_bytes = seq.read(buffer, 0, buffer_size)) > -1;) {
                    os.write(buffer, 0, read_bytes);
                    
                    i ++;
                    current_bytes += read_bytes;
                    if (0 == i % 100) {
                        // Send message (with number of concatenated size of the file) to Handler on UI thread
                        // so that it can update the progress bar.
    
                        Message msg = mHandler.obtainMessage();
                        Bundle b = new Bundle();
                        b.putBoolean("done", false);
                        //b.putLong("total_size", total_size);
                        b.putLong("read_bytes", current_bytes);
                        msg.setData(b);
                        mHandler.sendMessage(msg);
                    }
                    
                }
                //int ch;
                //while ((ch = seq.read()) != -1) {
                //    os.write(ch);
                //}
                os.flush();
            } finally {
                os.close();
                if (null != seq) {
                    seq.close();
                }
                if (null != fout) {
                    fout.close();
                }
            }
            
        } catch (FileNotFoundException e) {
            b_success = false;
            e.printStackTrace();
        } catch (IOException e) {
            b_success = false;
            e.printStackTrace();
        }            
        return b_success;
    }
    
}
