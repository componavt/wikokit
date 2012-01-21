package wikokit.kiwidict.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import wikokit.kiwidict.KWConstants;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/** Joins splitted files into one file.
 * 
 * @see http://stackoverflow.com/questions/2860157/load-files-bigger-than-1m-from-assets-folder
 */
public class JoinerFiles {

	
	/** Joins files "enwikt.N" (from assets folder) into out_db_file (e.g. "enwikt_mean_semrel_sqlite").
	 * 
	 * @param context
	 * @param out_db_file
	 * @throws IOException
	 * 
	 * @see 
	 */
/*	static public void joinDatabaseChunks(Context context, File out_db_file) throws IOException
	{
	    AssetManager am = context.getAssets();
	    OutputStream os = new FileOutputStream(out_db_file);
	    out_db_file.createNewFile();
	    byte []b = new byte[1024];
	    int i, r;
	    String []in_files = am.list("");
	    Arrays.sort(in_files);
	    for(i=1;i< KWConstants.MAX_NUMBER_DB_PARTS ;i++)
	    {
	    	String fn = String.format("%s.%d", KWConstants.DB_ZIPFILE, i);
	    	//Log.d("debug", "fn=" + fn);
	    	
	        if(Arrays.binarySearch(in_files, fn) < 0)
	               break;
	        //Log.d("debug", "fn=" + fn + "; yes");
	        InputStream is = am.open(fn);
	        while((r = is.read(b)) != -1)
	            os.write(b, 0, r);
	        is.close();
	    }
	    os.close();
	}*/
}
