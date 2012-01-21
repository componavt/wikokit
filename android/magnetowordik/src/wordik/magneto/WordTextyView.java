package wordik.magneto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/** Clickable TextView. */
public class WordTextyView extends Activity implements OnClickListener {

	TextView t;
	private int r_id;
	
	public void WordTextyView(int _r_id) {
		r_id = _r_id;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    t = (TextView)findViewById(r_id);
	    t.setOnClickListener(this);
	}
	
	public void onClick(View arg0) {
	    t.setText("My text on click");  
	    }
}

