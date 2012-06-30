/* DownloadProgressThread.java - progress bar for downloading dictionary database file.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.kiwidict.DownloadAndInstallActivity;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.enwikt.R;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class DownloadProgressThread extends Thread {	
        
	Handler mHandler;
	
	// Constructor with an argument that specifies Handler on main thread
	// to which messages will be sent by this thread.

	public DownloadProgressThread(Handler h) {
		mHandler = h;
	}
	
	/** Send message to Handler on UI thread
	 * with progress bar text.
	 */
	private void setProgressBarText(String text) {

        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putBoolean("done", false);
        b.putLong("total_size", -1);
        b.putLong("downloaded_size", -1);
        b.putString("progressbar_message", text);
        msg.setData(b);
        mHandler.sendMessage(msg);
	}

	// Override the run() method that will be invoked automatically when 
	// the Thread starts.  Do the work required to update the progress bar on this
	// thread but send a message to the Handler on the main UI thread to actually
	// change the visual representation of the progress.

	@Override
	public void run() {
		
	    // are there many files (parts) to download or only one?
        boolean multi_part = null != Connect.getDatabaseURL2();
        
        String s = "Downloading...";
        if(multi_part)
            s = "Downloading file 1 (of 2)...";
        setProgressBarText(s);
		downloadToExternalStorage(Connect.getDatabaseURL(), Connect.DB_DIR, Connect.getDBZipFilename());
		
		if(multi_part) {
	        s = "Downloading file 2 (of 2)...";
		    downloadToExternalStorage(Connect.getDatabaseURL2(), Connect.DB_DIR, Connect.getDBZipFilename2());
		}
		
		// Send message to Handler on UI thread
		// that the downloading was finished.

		Message msg = mHandler.obtainMessage();
		Bundle b = new Bundle();
		b.putBoolean("done", true);
		b.putLong("total_size", -1);
		b.putLong("downloaded_size", -1);
		msg.setData(b);
		mHandler.sendMessage(msg);
	}
	
	/** Downloads file from the str_url location to the /sd_card/dir/filename.
	 * @param str_url URL, e.g. "http://www/ruwikt20110521_android_sqlite_LZMA_dict8MB_word64.zip"
	 * @dir file path at SD card, e.g. "kiwidict" folder
	 * @filename file will be saved to
	 */
	public void downloadToExternalStorage(String str_url, String dir, String filename) {

		StringBuilder absolute_file_path = new StringBuilder();
		absolute_file_path.append( Environment.getExternalStorageDirectory().getAbsolutePath() );
		absolute_file_path.append( File.separator);
		absolute_file_path.append( dir );
		absolute_file_path.append( File.separator);
		absolute_file_path.append( filename );

		download(str_url, absolute_file_path.toString());
	}
	
	/** Downloads file from the str_url location to the destination file_path.
	 * str_url = "http://localhost/tempos/ruwikt20110521_android_sqlite_LZMA_dict8MB_word64.zip"
	 * file_path = "/mnt/sdcard/kiwidict/file.zip"
	 */
	public void download(String str_url, String file_path) {
		
		try {
	        URL url = new URL(str_url);
	        HttpURLConnection c = (HttpURLConnection) url.openConnection();	        
	        try {
		        c.setRequestMethod("GET");
		        c.setDoOutput(true);
		        c.connect();
		        
		        File file = new File(file_path);
		        FileOutputStream file_out = new FileOutputStream(file);
	
		        // read data from WWW
		        InputStream in = c.getInputStream();
		        
		        long total_size = c.getContentLength();	// this is the total size of the file
		        long downloaded_size = 0;				// variable to store total downloaded bytes
	
		        byte[] buffer = new byte[4*1024];	// create a buffer...
		        int buf_len = 0;
		        
		        int counter = 0;
		        while ( (buf_len = in.read(buffer)) > 0 ) {
		        	file_out.write(buffer, 0, buf_len);
	                downloaded_size += buf_len;
	                
	                counter ++;
	                if(0 == counter % 100) {
	                	//System.out.println("downloadedSize="+ downloadedSize +
	                	//				 "; totalSize=" + totalSize);
	                	
	                	// Send message (with number of downloaded size of the file) to Handler on UI thread
	        			// so that it can update the progress bar.

	        			Message msg = mHandler.obtainMessage();
	        			Bundle b = new Bundle();
	        			b.putBoolean("done", false);
	        			b.putLong("total_size", total_size);
	        			b.putLong("downloaded_size", downloaded_size);
	        			msg.setData(b);
	        			mHandler.sendMessage(msg);
	                }
		        }
		        file_out.close();
		        
	        } finally {
		    	c.disconnect();
		    }

	    // catch some possible errors...
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
