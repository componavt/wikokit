package wikokit.kiwidict;

import java.io.File;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.db.Downloader;
import wikokit.base.wikt.db.FileUtil;
import wikokit.kiwidict.R;
import wikokit.kiwidict.db.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadAndInstallActivity extends Activity {

	private LinearLayout layout_progress_bar;
	private Button button_install;
	private TextView textview_progressbar_message;
	private ProgressBar progressbar_downloading_unzipping;
	
	private DownloadProgressThread download_thread;
	private UnzippingProgressThread unzipping_thread;
	private ConcatenatingProgressThread concatenating_thread;
	
	//private static boolean b_downloading_first_message = true;
	//private static boolean b_unzipping_first_message = true;
	private static boolean b_concatenating_first_message = true;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.download_install);

		boolean b_enough_memory = false;
		int mbytes_required = Connect.getDatabaseFileSizeMB() + Connect.getDatabaseZIPFileSizeMB();
		TextView textView_SD_free_memory = (TextView) findViewById(R.id.textView_SD_free_memory);
		{
			String s = "";
			if(FileUtil.isMemoryCardAvailable()) {
				
				long mbytes = FileUtil.getSDFreeMemoryInMBytes();
				s = "The memory card has " + mbytes + " MB of free space.";
				
				b_enough_memory = mbytes >  mbytes_required;
				if(!b_enough_memory)
					s += " It is not enough memory :("; 
				
			} else {
				s = "Memory card is not available.";
			} 
			textView_SD_free_memory.setText(s);
		}
		
		TextView textView_memory_requirement = (TextView) findViewById(R.id.textView_memory_requirement);
		textView_memory_requirement.setText(mbytes_required + " MB memory requirement.");

		if( b_enough_memory ) {
			showInstallButtonAndPrepareProgressBar();
			
		} else {
			// close button
			Button button_DownloadAndInstall = (Button)  findViewById(R.id.button_DownloadAndInstall);
			button_DownloadAndInstall.setText("Close");
			button_DownloadAndInstall.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					finalFailed();
				}
			});
			
		}
		
		// hide progress bar, till the user press button "Download and install"
		layout_progress_bar = (LinearLayout)findViewById(R.id.linearLayout_progress_bar);
		layout_progress_bar.setVisibility(View.GONE);

		// initialize invisible fields
		progressbar_downloading_unzipping = (ProgressBar) findViewById(R.id.progressBar_downloading_unzipping);
		textview_progressbar_message = (TextView) findViewById(R.id.textView_progressbar_message);
	}
	
	private void download() {
		//textview_progressbar_message.setText( "Downloading..." ); message will be set from DownloadProgressThread

		if( Downloader.isOnline(this) ) {
			download_thread = new DownloadProgressThread(handler_downloading);
			download_thread.start();
		} else {
			textview_progressbar_message.setText("Downloading... Error: network is not available!");
		}
	}
	
	private void unzip() {
		// textview_progressbar_message.setText("Unzipping...");
		progressbar_downloading_unzipping.setProgress(0);
		
		unzipping_thread = new UnzippingProgressThread(handler_unzipping);
		unzipping_thread.start();
	}
	
	private void concatenate() {
	    
	    if(areThereFilesToConcatenate())
        {
            textview_progressbar_message.setText("Concatenating files...");
            progressbar_downloading_unzipping.setProgress(0);
            
            concatenating_thread = new ConcatenatingProgressThread(handler_concatenating);
            concatenating_thread.start();
        } else {
            finalSuccess();
        }
    }
	
	private static boolean areThereFilesToConcatenate() {
	    String[] file_parts = new String[2];
        file_parts[0] = Connect.getDBFilenamePart1();
        file_parts[1] = Connect.getDBFilenamePart2();
        if( FileUtil.isFileExist(Connect.DB_DIR, file_parts[0]) && 
            FileUtil.isFileExist(Connect.DB_DIR, file_parts[1]) )
            return true;
        else
            return false;
	}
	
	private static void deleteTempFiles() 
	{
	    // delete ZIP files, delete concatenated parts and exit
        //File file = new File( FileUtil.getFilePathAtExternalStorage( 
        //        Connect.DB_DIR, Connect.getDBZipFilename()));
        //file.delete();
	    
        // delete parts
	    FileUtil.deleteFileAtSDCard( Connect.getDBFilenamePart1() );// delete first file
	    
	    String part2 = Connect.getDBFilenamePart2();
        if(null != part2)
            FileUtil.deleteFileAtSDCard( part2 );                   // delete second file
	}

	
	/** Starts downloading when user clicks button "Install". */
	private void showInstallButtonAndPrepareProgressBar() {
		button_install = (Button) findViewById(R.id.button_DownloadAndInstall);
		button_install.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				button_install.setEnabled(false);
				layout_progress_bar.setVisibility(View.VISIBLE);
				
				// 1. check unzipped parts of the huge database, concatenate it
				if(areThereFilesToConcatenate())
	            {
	                concatenate();
	                return;
	            }
	            
				// 2. check zipped file, if it exists - unzip it, and 1. concatenate.
				if( FileUtil.isFileExist(Connect.DB_DIR, Connect.getDBZipFilename()) ) {// 2.
				    
				    String zip_part2 = Connect.getDBZipFilename2();
			        if(null == zip_part2 || FileUtil.isFileExist(Connect.DB_DIR, zip_part2)) {
    					unzip();
    		    		return; // isDatabaseAvailable();
			        }
		    	}
				
				// 3. else download, 2. unzip and 1. concatenate database to SD card
		    	FileUtil.createDirIfNotExists(Connect.DB_DIR);
		    	download();
		    	// unzip() will be called from handler_downloading when file will be downloaded.
			}
		});
	}
	
	
	/** Handler on the "downloading" thread, to update the progress bar. */
    final Handler handler_downloading = new Handler() {
    	
        public void handleMessage(Message msg) {
        	
        	boolean b_done = msg.getData().getBoolean("done");
        	if(b_done)
        	{
        		// downloading finished, start unzipping
        		unzip();
        	} else {
        	
	            // Get the current value of the variable total from the message data
	            // and update the progress bar.
        	    int total_size = (int)msg.getData().getLong("total_size");
	        	if( total_size > 0 ) {
	        		progressbar_downloading_unzipping.setMax( total_size );	// = about KWConstants.DB_ZIPFILE_SIZE_MB
	        		int downloaded_size = (int)msg.getData().getLong("downloaded_size");
	        		progressbar_downloading_unzipping.setProgress(downloaded_size);
	        	} else {
    	            String s = msg.getData().getString("progressbar_message");
    	            textview_progressbar_message.setText( s );
	        	}
        	}
        }
    };
    
    /** Handler on the "unzipping" thread, to update the progress bar. */
    final Handler handler_unzipping = new Handler() {
    	
        public void handleMessage(Message msg) {
        	
        	boolean b_done = msg.getData().getBoolean("done");
        	if(b_done)
        	{
        		// database unzipped, then 
        	    // 1) optional:concatenate unzipped parts  
        	    // 2) exit from this activity to dictionary
        		boolean b_success = msg.getData().getBoolean("success");
        		if(b_success) {
                    concatenate(); // check unzipped parts of the huge database, concatenate it
        		}else 
        			finalFailed();
        		
        	} else {
        	
        	    int total_size = (int)msg.getData().getLong("total_size");
	        	if( total_size > 0 ) {
	        		progressbar_downloading_unzipping.setMax( total_size );	// = about KWConstants.DB_FILE_SIZE_MB
	        		int unzipped_size = (int)msg.getData().getLong("unzipped_size");
	                progressbar_downloading_unzipping.setProgress(unzipped_size);
	        	} else {
	        	    String s = msg.getData().getString("progressbar_message");
                    textview_progressbar_message.setText( s );
	        	}
        	}
        }
    };
    
    /** Handler on the "concatenating" thread, to update the progress bar. */
    final Handler handler_concatenating = new Handler() {
        
        public void handleMessage(Message msg) {
            
            boolean b_done = msg.getData().getBoolean("done");
            if(b_done)
            {
                // database files concatenated, then exit from this activity to dictionary
                boolean b_success = msg.getData().getBoolean("success");
                if(b_success) {
                    finalSuccess();
                }else 
                    finalFailed();
                
            } else {
            
                if( b_concatenating_first_message ) {
                    b_concatenating_first_message = false;
                    //int total_size = (int)msg.getData().getLong("total_size");
                    int total_size = Connect.getDatabaseFileSizeMB() * 1024 * 1024;
                    progressbar_downloading_unzipping.setMax( total_size );
                }
                
                int read_bytes = (int)msg.getData().getLong("read_bytes");
                progressbar_downloading_unzipping.setProgress(read_bytes);
            }
        }
    };
	
	private void finalSuccess()
    {
	    setResult(Activity.RESULT_OK);
	    deleteTempFiles();
        finish();
	    
    }
	
	private void finalFailed()
    {
    	setResult(Activity.RESULT_CANCELED);
    	finish();
    }

}
