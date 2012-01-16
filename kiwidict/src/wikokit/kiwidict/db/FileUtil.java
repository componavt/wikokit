/* FileUtil.java - miscellaneous file routines.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.db;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class FileUtil {

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

	
}
