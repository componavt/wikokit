/* FileUtil.java - miscellaneous file routines.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.db;

import java.io.File;

import wikokit.base.wikipedia.sql.Connect;

import android.os.StatFs;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	
	/** Checks whether there is enough memory at SD card for the file 
	 * with the size "file_size_mb"? 
	 * @return true if it is enough
	 */
	public static boolean hasEnoughMemory(int file_size_mb) {
		
		String s = "";
		if(FileUtil.isMemoryCardAvailable()) {
			
			long mbytes = FileUtil.getSDFreeMemoryInMBytes();
			if(mbytes >  file_size_mb) {
				return true;
			} else {
				System.out.println(" It is not enough memory :(");
			}
		} else {
			System.out.println("Memory card is not available.");
		} 
		return false;
	}
	
	/** Creates directory at SD card. 
	 * E.g. /mnt/sdcard/kiwidict if path="kiwidict"
	 */
	public static boolean createDirIfNotExists(String path) {
	    boolean result = true;

	    File file = new File(Environment.getExternalStorageDirectory(), path);
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            Log.e("FileUtil::createDirIfNotExists - ", "Problem creating Image folder");
	            result = false;
	        }
	    }
	    return result;
	}
	
	/** Checks wheather exists the file at the location at SD card/path/filename. 
	 * E.g. /mnt/sdcard/db.sqlite if dir="kiwidict" and filename="db.sqlite"
	 */
	public static boolean isFileExist(String path, String filename) {
	    
		String file_path = getFilePathAtExternalStorage(path, filename);
		File file = new File(file_path.toString());
		return file.exists();
	}
	
	/** Gets absolute file path to the file at SD card/path/filename. 
	 * E.g. /mnt/sdcard/db.sqlite if path="kiwidict" and filename="db.sqlite"
	 */
	public static String getFilePathAtExternalStorage(String path, String filename) {
	            
        StringBuilder file_path = new StringBuilder();
		file_path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		file_path.append(File.separator);
		file_path.append(path);
		file_path.append(File.separator); 
		file_path.append(filename);
		
		return file_path.toString();
	}
	
	/** Gets size of free internal memory. */
	/*public static long getFreeInternalMemoryInMBytes() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long)stat.getFreeBlocks() * (long)stat.getBlockSize();;
		return bytesAvailable / 1048576;
	}*/
	
	/** Gets size of free memory at SD card in megabytes. */
	public static long getSDFreeMemoryInMBytes() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());        
		long blockSize = statFs.getBlockSize();
		//long totalSize = statFs.getBlockCount()*blockSize;
		long availableSize = statFs.getAvailableBlocks()*blockSize;
		//long freeSize = statFs.getFreeBlocks()*blockSize;

		return availableSize / 1048576;
	}
	
	
	/** Is memory card available? */
	public static boolean isMemoryCardAvailable()
	{	
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
			// We can read and write the media
			return true;
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
		{
			// We can only read the media
			return false;
		} 
		else 
		{
			// No external media
			return false;
		}
	}
	
	public static void deleteFileAtSDCard(String filename) {
	    File file = new File( FileUtil.getFilePathAtExternalStorage( 
                Connect.DB_DIR, filename));
	    file.delete();
    }

	
}
