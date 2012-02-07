/* Downloader.java - download database from WWW to SD card.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.db;

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

	/** Checks wheather inet is available.
	 * Problem: return true if there is Wi-Fi, but VPN is not connected :(
	 */
	public static boolean isOnline(Context c) {
		
	    boolean connected = false;
		try {
			ConnectivityManager cman = (ConnectivityManager) c
		                        .getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo network_info = cman.getActiveNetworkInfo();
			connected = network_info != null
					 && network_info.isAvailable() && network_info.isConnected();
			
		} catch (Exception e) {
		        System.out.println("CheckConnectivity Exception: " + e.getMessage());
		        //Log.v("connectivity", e.toString());
		}
		return connected;
	}


	
	/** Downloads file from the str_url location to the /sd_card/dir/filename.
	 * @param str_url URL, e.g. "http://www/ruwikt20110521_android_sqlite_LZMA_dict8MB_word64.zip"
	 * @dir file path at SD card, e.g. "kiwidict" folder
	 * @filename file will be saved to
	 */
	public static void downloadToExternalStorage(String str_url, String dir, String filename) {

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
	public static void download(String str_url, String file_path) {
		
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
		        
		        int totalSize = c.getContentLength();	// this is the total size of the file
		        int downloadedSize = 0;					// variable to store total downloaded bytes
	
		        byte[] buffer = new byte[4*1024];	// create a buffer...
		        int buf_len = 0;
		        
		        while ( (buf_len = in.read(buffer)) > 0 ) {
		        	file_out.write(buffer, 0, buf_len);
	                downloadedSize += buf_len;
	                
	                if(0 == downloadedSize % 4096) {
	                	System.out.println("downloadedSize="+ downloadedSize +
	                					 "; totalSize=" + totalSize);
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
