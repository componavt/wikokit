/* Downloader.java - download database from WWW to SD card.
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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Downloader {

	
	/** Downloads file from the str_url location to the destination file_path. */
	// str_url = "http://localhost/tempos/ruwikt20110521_android_sqlite_LZMA_dict8MB_word64.zip"
	// file_path = "somefile.ext"
	public static void download(String str_url, String file_path) {
		
		try {
	        //set the download URL, a url that points to a file on the internet
	        //this is the file to be downloaded
	        URL url = new URL(str_url);

	        //create the new connection
	        HttpURLConnection c = (HttpURLConnection) url.openConnection();
	        
	        try {
		        c.setRequestMethod("GET");
		        c.setDoOutput(true);
		        c.connect();
		        
		        File sd_card_root = Environment.getExternalStorageDirectory();
		        File file = new File(sd_card_root, file_path);
		        FileOutputStream file_out = new FileOutputStream(file);	        
	
		        //this will be used in reading the data from the internet
		        InputStream in = c.getInputStream();
		        
		        int totalSize = c.getContentLength();	// this is the total size of the file
		        int downloadedSize = 0;					// variable to store total downloaded bytes
	
		        // create a buffer...
		        byte[] buffer = new byte[4*1024];
		        int buf_len = 0; //used to store a temporary size of the buffer
	
		        //now, read through the input buffer and write the contents to the file
		        while ( (buf_len = in.read(buffer)) > 0 ) {
		        	//add the data in the buffer to the file in the file output stream (the file on the sd card
	                file_out.write(buffer, 0, buf_len);
	                //add up the size so we know how much is downloaded
	                downloadedSize += buf_len;
	                
	                //this is where you would do something to report the progress, like this maybe
	                //updateProgress(downloadedSize, totalSize);
	                if(0 == downloadedSize % 4096) {
	                	System.out.println("downloadedSize="+ downloadedSize +
	                					 "; totalSize=" + totalSize);
	                }
		        }
		        //close the output stream when done
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
