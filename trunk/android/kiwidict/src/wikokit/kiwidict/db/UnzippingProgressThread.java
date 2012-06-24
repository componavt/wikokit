/* UnzippingProgressThread.java - progress bar for downloading dictionary database file.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.db.FileUtil;
import wikokit.kiwidict.KWConstants;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class UnzippingProgressThread extends Thread {	
        
	Handler mHandler;
	
	// Constructor with an argument that specifies Handler on main thread
	// to which messages will be sent by this thread.

	public UnzippingProgressThread(Handler h) {
		mHandler = h;
	}

	// Override the run() method that will be invoked automatically when 
	// the Thread starts.  Do the work required to update the progress bar on this
	// thread but send a message to the Handler on the main UI thread to actually
	// change the visual representation of the progress.

	@Override
	public void run() {
		
	    boolean b_success = false;
	    String path = getPathAtExternalStorageByDirectoryName(Connect.DB_DIR);
	    
	    b_success = unpackZip(path, Connect.getDBZipFilename());
        // b_success = unpackZipToExternalStorage(Connect.DB_DIR, Connect.getDBZipFilename());
	    
	    // delete first ZIP file
	    FileUtil.deleteFileAtSDCard( Connect.getDBZipFilename() );
        
		String zip_part2 = Connect.getDBZipFilename2();
		if(b_success && null != zip_part2) {
		    
		    b_success = unpackZip(path, zip_part2);
		    
		    // delete second ZIP file
		    FileUtil.deleteFileAtSDCard( Connect.getDBZipFilename2() );
	        
	        String[] file_parts = new String[2];
            file_parts[0] = Connect.getDBFilenamePart1();
            file_parts[1] = Connect.getDBFilenamePart2();
            b_success = concatenateFiles (path, file_parts, Connect.getDBFilename());
            
            // delete parts
            FileUtil.deleteFileAtSDCard( Connect.getDBFilenamePart1() );
            FileUtil.deleteFileAtSDCard( Connect.getDBFilenamePart2() );
            
		    // extract multi-volume .zip, .z01 archive
		    //String[] zip_file_parts = new String[2];
		    //zip_file_parts[0] = Connect.getDBZipFilename();
		    //zip_file_parts[1] = zip_part2;
		    //b_success = unpackMultiPartZip (path, zip_file_parts);
		}
		
		// Send message to Handler on UI thread
		// that the downloading was finished.

		Message msg = mHandler.obtainMessage();
		Bundle b = new Bundle();
		b.putBoolean("done", true);
		b.putBoolean("success", b_success);
		msg.setData(b);
		mHandler.sendMessage(msg);
	}
	
	/* Unzips files from the dir/zipname location at SD card.
	 * 
	 * @see http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 */
	/*public boolean unpackZipToExternalStorage(
									String dir, String zip_filename)
	{	
		StringBuilder path = new StringBuilder();
		path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		path.append(File.separator);
		path.append(dir);
		
		return unpackZip(path.toString(), zip_filename);
	}*/
	
	/* Gets file path at SD card by the directory.
     * 
     * @see http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
     */
	public String getPathAtExternalStorageByDirectoryName(String dir)
	{   
	    StringBuilder path = new StringBuilder();
	    path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
	    path.append(File.separator);
	    path.append(dir);

	    return path.toString();
	}
	
	/* Unzips files from the "file_path / zip_file" location.
	 * 
	 * @see http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 */
	public boolean unpackZip(String file_path, String zip_file)
	{    
		InputStream is;
		ZipInputStream zis;

		try 
		{
			 is = new FileInputStream(file_path + File.separator + zip_file);
			zis = new ZipInputStream(new BufferedInputStream(is));          
			ZipEntry ze;

			// "if" instead of "while", since we have only one file in our zip-archive
			if ((ze = zis.getNextEntry()) != null) 
			{
				long total_size = ze.getSize();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int count;

				FileOutputStream fout = new FileOutputStream(file_path + File.separator + ze.getName()); // unzipped file

				long unzipped_size = 0;
				int i = 0;
				while ((count = zis.read(buffer)) != -1) 
				{
					baos.write(buffer, 0, count);
					byte[] bytes = baos.toByteArray();
					fout.write(bytes);             
					baos.reset();
					
					unzipped_size += count;
					i ++;
					
					if (0 == i % 100) {
	                	// Send message (with number of downloaded size of the file) to Handler on UI thread
	        			// so that it can update the progress bar.
	
	        			Message msg = mHandler.obtainMessage();
	        			Bundle b = new Bundle();
	        			b.putBoolean("done", false);
	        			b.putLong("total_size", total_size);
	        			b.putLong("unzipped_size", unzipped_size);
	        			msg.setData(b);
	        			mHandler.sendMessage(msg);
					}
				}
				fout.close();               
				zis.closeEntry();
			}
			zis.close();
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
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
            OutputStream os = new BufferedOutputStream(new FileOutputStream(result));
            try {
                final int buffer_size = 1024;
                byte[] buffer = new byte[buffer_size];
                for (int readBytes = -1; (readBytes = seq.read(buffer, 0, buffer_size)) > -1;) {
                    os.write(buffer, 0, readBytes);
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
	
	/* Unzips one file from the multi-volume .zip, .z01 archive, 
	 * from the "file_path / zip_file" location.
     * 
     * @see http://stackoverflow.com/questions/8116443/how-do-you-uncompress-a-split-volume-zip-in-java
     */
	/* failed
	 * private boolean unpackMultiPartZip (String file_path, String[] files) {
	    boolean b_success = true;

	    InputStream seq;
        ZipInputStream zis;

        try {
            Vector<InputStream> v = new Vector<InputStream>(files.length);
    	    for (int x = 0; x < files.length; x++) {
    	        FileInputStream fis = new FileInputStream(file_path + File.separator + files[x]);
    	        v.add(fis);
    	    }
    	    Enumeration<InputStream> e = v.elements();
    
    	    seq = new SequenceInputStream(e);
    	    zis = new ZipInputStream(seq);
    	    try {
    	        for (ZipEntry entry = null; (entry = zis.getNextEntry()) != null;) {
    	            OutputStream os = new BufferedOutputStream(new FileOutputStream(entry.getName()));
    	            try {
    	                final int buffer_size = 1024;
    	                byte[] buffer = new byte[buffer_size];
    	                for (int readBytes = -1; (readBytes = zis.read(buffer, 0, buffer_size)) > -1;) {
    	                    os.write(buffer, 0, readBytes);
    	                }
    	                os.flush();
    	            } finally {
    	                os.close();
    	            }
    	        }
    	    } finally {
    	        zis.close();
    	    }    	    
        } catch (FileNotFoundException e) {
            b_success = false;
            e.printStackTrace();
        } catch (IOException e) {
            b_success = false;
            e.printStackTrace();
        }
        return b_success;
	}*/

}
