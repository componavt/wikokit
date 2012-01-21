package wikokit.kiwidict;

import java.io.File;

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
	
	private static boolean b_downloading_first_message = true;
	private static boolean b_unzipping_first_message = true;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.download_install);

		boolean b_enough_memory = false;
		int mbytes_required = KWConstants.DB_FILE_SIZE_MB + KWConstants.DB_ZIPFILE_SIZE_MB;
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
		textview_progressbar_message.setText("Downloading...");

		if( Downloader.isOnline(this) ) {
			download_thread = new DownloadProgressThread(handler_downloading);
			download_thread.start();
		} else {
			textview_progressbar_message.setText("Downloading... Error: network is not available!");
		}
	}
	
	private void unzip() {
		textview_progressbar_message.setText("Unzipping...");
		progressbar_downloading_unzipping.setProgress(0);
		
		unzipping_thread = new UnzippingProgressThread(handler_unzipping);
		unzipping_thread.start();
	}
	
	/** Starts downloading when user clicks button "Install". */
	private void showInstallButtonAndPrepareProgressBar() {
		button_install = (Button) findViewById(R.id.button_DownloadAndInstall);
		button_install.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				button_install.setEnabled(false);
				layout_progress_bar.setVisibility(View.VISIBLE);
				
				// 2. check zipped file, if it exists - unzip it.
				if( FileUtil.isFileExist(KWConstants.DB_DIR, KWConstants.DB_ZIPFILE) ) {// 2.
					unzip();
		    		return; // isDatabaseAvailable();
		    	}
				
				// 3. else download and unzip database to SD card
		    	FileUtil.createDirIfNotExists(KWConstants.DB_DIR);
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
	        	if( b_downloading_first_message ) {
	        		b_downloading_first_message = false;
	        		int total_size = (int)msg.getData().getLong("total_size");
	        		progressbar_downloading_unzipping.setMax( total_size );	// = about KWConstants.DB_ZIPFILE_SIZE_MB
	        	}	            
	            int downloaded_size = (int)msg.getData().getLong("downloaded_size");
	            progressbar_downloading_unzipping.setProgress(downloaded_size);
        	}
        }
    };
    
    /** Handler on the "unzipping" thread, to update the progress bar. */
    final Handler handler_unzipping = new Handler() {
    	
        public void handleMessage(Message msg) {
        	
        	boolean b_done = msg.getData().getBoolean("done");
        	if(b_done)
        	{
        		// database unzipped, then exit from this activity to dictionary
        		boolean b_success = msg.getData().getBoolean("success");
        		if(b_success) {
        			
        			// delete ZIP file and exit
        			File file = new File( FileUtil.getFilePathAtExternalStorage( 
        							KWConstants.DB_DIR, KWConstants.DB_ZIPFILE));
        			file.delete();
        			finalSuccess();
        		}else 
        			finalFailed();
        		
        	} else {
        	
	        	if( b_unzipping_first_message ) {
	        		b_unzipping_first_message = false;
	        		int total_size = (int)msg.getData().getLong("total_size");
	        		progressbar_downloading_unzipping.setMax( total_size );	// = about KWConstants.DB_FILE_SIZE_MB
	        	}
	            
	            int unzipped_size = (int)msg.getData().getLong("unzipped_size");
	            progressbar_downloading_unzipping.setProgress(unzipped_size);
        	}
        }
    };
	
	private void finalSuccess()
    {
    	setResult(Activity.RESULT_OK);
    	finish();
    }
	
	private void finalFailed()
    {
    	setResult(Activity.RESULT_CANCELED);
    	finish();
    }

}
