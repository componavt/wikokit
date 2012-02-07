/*
 * EncodingsTest.java
 * JUnit based test
 */

package wikokit.base.wikipedia.language;

//import wikipedia.language.Encodings;
import wikokit.base.wikipedia.language.Encodings;
import junit.framework.*;
/*import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Set;*/
import java.util.Map;


public class EncodingsTest extends TestCase {
    
    public EncodingsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }


    public void testGetEncodings() {
        System.out.println("getEncodings");
        
        Map expResult = null;
        Map result = Encodings.getEncodings();
        for(Object o:result.keySet()) {
            //System.out.println(o.toString());
        }
    }
    
    
    public void testFromTo() {
        System.out.println("FromTo");
        
        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();
        
        String text = "text";
        for(int i=0; i<300000; i++) {
            String encode_from = "UTF8";
            String encode_to = "ISO8859_1";
            text = Encodings.FromTo(text, encode_from, encode_to);
            text = Encodings.FromTo(text, encode_to, encode_from);
        }
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        
        System.out.println("FromTo() total time: " + t_work + "sec.");
    }
    
    /*
    public void testFromToFast() {
        System.out.println("FromToFast");
        
        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();
        
        String text = "text";
        for(int i=0; i<300000; i++) {
            EncodingType encode_from = EncodingType.get("UTF8");
            EncodingType encode_to   = EncodingType.get("ISO8859_1");
            
            text = Encodings.FromToFast(text, encode_from, encode_to);
            text = Encodings.FromToFast(text, encode_to, encode_from);
        }
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        
        System.out.println("FromToFast() total time: " + t_work + "sec.");
    }*/
}


/*
static void dumpEncodings(String db_str,String str_enc) {
        
        String sText;
                                                                System.out.println(" ****** Start ****** " );
                                                                System.out.println(" *                 * " );
                                                                System.out.println("rsDB.getBytes "+str_enc+" :" + db_str);
        //sText = Encodings.FromTo(db_str,"ISO8859_1","UTF8");  System.out.println("ISO8859_1 to UTF8  : " + sText);
        sText = Encodings.FromTo(db_str,"Cp1252","UTF8");       System.out.println("Cp1252 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"Cp850","UTF8");        System.out.println("Cp850 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"Cp855","UTF8");        System.out.println("Cp855 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"Cp852","UTF8");        System.out.println("Cp852 to UTF8     : " + sText);
        
        
        sText = Encodings.FromTo(db_str,"WINDOWS-1252","UTF8"); System.out.println("WINDOWS-1252 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"Cp866","UTF8");        System.out.println("Cp866 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"windows-1251","UTF8"); System.out.println("windows-1251 to UTF8     : " + sText);
        
        //sText = Encodings.FromTo(db_str,"koi8r","UTF8");        System.out.println("koi8r to UTF8     : " + sText);
        
        sText = Encodings.FromTo(db_str,"cp866","UTF8");        System.out.println("cp866 to UTF8     : " + sText);
        
        //sText = Encodings.FromTo(db_str,"latin1","UTF8");       System.out.println("latin1 to UTF8     : " + sText);
        //sText = Encodings.FromTo(db_str,"latin2","UTF8");       System.out.println("latin2 to UTF8     : " + sText);
                                                                System.out.println(" *                 * " );
                                                                
                                                                
        sText = Encodings.FromTo(db_str,"UTF8","ISO8859_1");    System.out.println("UTF8 to ISO8859_1  : " + sText);
        
        sText = Encodings.FromTo(db_str,"Cp1251","UTF8");       System.out.println("Cp1251 to UTF8     : " + sText);
        sText = Encodings.FromTo(db_str,"UTF8","Cp1251");       System.out.println("UTF8 to Cp1251     : " + sText);
        sText = Encodings.FromTo(db_str,"Cp1251","ISO8859_1");  System.out.println("Cp1251 to ISO8859_1: " + sText);
        sText = Encodings.FromTo(db_str,"ISO8859_1","Cp1251");  System.out.println("ISO8859_1 to Cp1251: " + sText);

        sText = Encodings.FromTo(db_str,"UTF8","Cp1252");       System.out.println("UTF8 to Cp1252     : " + sText);
        sText = Encodings.FromTo(db_str,"ISO8859_1","Cp1252");  System.out.println("ISO8859_1 to Cp1252: " + sText);
        sText = Encodings.FromTo(db_str,"Cp1252","ISO8859_1");  System.out.println("Cp1252 to ISO8859_1: " + sText);
        sText = Encodings.FromTo(db_str,"Cp1251","Cp1252");       System.out.println("Cp1251 to Cp1252 : " + sText);
        sText = Encodings.FromTo(db_str,"Cp1252","Cp1251");       System.out.println("Cp1252 to Cp1251 : " + sText);
                                                                System.out.println(" *                 * " );
                                                                System.out.println(" ******  End  ****** " );
    }
 
 				
					byte[] aa;
					String sText1;
					
					Encodings e = new Encodings();
					byte[] aa =  rsDB.getBytes( "Text" );
					if ( ( aa == null ) || ( aa.length == 0 ) ) sText  = null;
					else
					{
						String db_str = Encodings.bytesTo( aa, e.GetDBEnc());
						sText = e.EncodeFromDB(db_str);
					};
					
					aa =  rsDB.getBytes( "Text" );
					if ( ( aa == null ) || ( aa.length == 0 ) ) sText = null;
					else sText = new String( aa, "UTF-8" );	//	
					aa = null;
					
					sText = rsDB.getString( "Text" );
					
					aa =  sText.getBytes( "UTF-8" );
					if ( ( aa == null ) || ( aa.length == 0 ) ) sText1 = null;
					else sText1 = new String( aa, "UTF-8" );
					aa = null;
					System.out.println( sText  );	//	+  " " + sText.length()
					System.out.println( sText1 );
					
					//	sText = new String( rsDB.getBytes( "Text" ), "Cp1251" );
                    
                    
                    String db_str;
                    String str_enc;
                    
					//sText = rsDB.getString( "Text" );
                    //Encodings e = new Encodings();
                    
                    //String db_str = Encodings.bytesTo(rsDB.getBytes("Text"), e.GetDBEnc());
                    
                    //db_str = Encodings.bytesTo(rsDB.getBytes("Text"), "ISO8859_1");
                    
                    
                    //db_str = rsDB.getString("Text");
                    //db_str = Encodings.bytesTo(rsDB.getBytes("Text"), "UTF8");
                    //sText = e.EncodeFromDB(db_str);
                    
                    
                    db_str = rsDB.getString("Text");
                    if ( null != db_str) {
                        System.out.println("");
                        System.out.println(" ****** Start ************************************************ " );
                        System.out.println(" *                                                           * " );
                        
                        for (Object o:availcs.keySet()) {
                            String so = (String)o;
                            if(0 == so.compareToIgnoreCase("ISO-2022-CN") || 
                               0 == so.compareToIgnoreCase("x-JISAutoDetect")) {
                                continue;
                            }
                            
                            db_str = Encodings.bytesTo(rsDB.getBytes("Text"), so);
                            String db_str2 = Encodings.FromTo(db_str, so, "UTF8");
                            System.out.println(o + ": " + db_str + ": " + db_str2);
                        }
                        
                        str_enc = "String";
                        dumpEncodings(db_str, str_enc);
                    
                        str_enc = "UTF8";
                        db_str = Encodings.bytesTo(rsDB.getBytes("Text"), str_enc);
                        dumpEncodings(db_str, str_enc);
                    
                        str_enc = "ISO8859_1";
                        db_str = Encodings.bytesTo(rsDB.getBytes("Text"), str_enc);
                        dumpEncodings(db_str, str_enc);
                        
                        
                        str_enc = "cp866";
                        db_str = Encodings.bytesTo(rsDB.getBytes("Text"), str_enc);
                        dumpEncodings(db_str, str_enc);
        
                        str_enc = "Cp1251";
                        db_str = Encodings.bytesTo(rsDB.getBytes("Text"), str_enc);
                        dumpEncodings(db_str, str_enc);
                        
                        str_enc = "Cp1252";
                        db_str = Encodings.bytesTo(rsDB.getBytes("Text"), str_enc);
                        dumpEncodings(db_str, str_enc);
                        
                    }
                    */