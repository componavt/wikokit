package wikokit.kiwidict.lang;


import wikokit.base.wikt.sql.lang.LanguageSplitter;
import android.app.Activity;
import android.widget.ArrayAdapter;

public class LangSpinnerAdapter extends ArrayAdapter<String> {
    
    private final Activity activity;
    private final String[] langs;
    private final LanguageSpinner lspinner;
    private final LanguageSplitter lsplitter;
 
    public LangSpinnerAdapter(Activity activity, int gui, String[] objects, LanguageSpinner lspinner) {
    
        super(activity, gui, objects);
        this.activity = activity;
        this.langs = objects;
      
        this.lspinner  = lspinner;
        this.lsplitter = lspinner.getLanguageSplitter();
    }
    
    /* Let's disable in drop down menu: "--- NNN < entries < NNNNN ---" */
    public boolean isEnabled(int position) {
        if(position == 1 + lsplitter.getPart1Length() ||
           position ==     lsplitter.getPart2Length())
            return false; 

        return true;
      }
    
      public boolean areAllItemsEnabled() { 
        return false; 
      } 

}
