package wikokit.kiwidict.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class GUI {

    /** Converts pixels to Density-independent Pixels.
     * 
     * @see http://stackoverflow.com/questions/4914039/margins-of-a-linearlayout-programmatically-with-dp
     */
    public static int ConvertPixelsToDP (Activity context, int pixels) {
        
        float d = context.getResources().getDisplayMetrics().density;
        return (int)(pixels * d);
    }
    
    
    /** Converts Device Independent Pixels to pixels.
     * 
     * @see http://stackoverflow.com/a/2406790/1173350 
     */
    public static int ConvertDPToPixels (Context context, int dp) {
        
        Resources r = context.getResources();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

}
