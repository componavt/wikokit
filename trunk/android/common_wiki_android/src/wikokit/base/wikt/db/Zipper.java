/* Zipper.java - unzips files.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.db;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.os.Environment;

public class Zipper {	
        
	/* Unzips files from the dir/zipname location at SD card.
	 * 
	 * @see http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 */
	public static boolean unpackZipToExternalStorage(
									String dir, String zip_filename)
	{	
		StringBuilder path = new StringBuilder();
		path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		path.append(File.separator);
		path.append(dir);
		
		return unpackZip(path.toString(), zip_filename);
	}
	
	
	/* Unzips files from the "file_path / zip_file" location.
	 * 
	 * @see http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 */
	public static boolean unpackZip(String file_path, String zip_file)
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
	                	System.out.println("total_size=" + total_size +
	        							"; unzipped_size=" + unzipped_size);
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
}
