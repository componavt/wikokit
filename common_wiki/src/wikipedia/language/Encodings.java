/*
 * Encodings.java - provide UTF8 and latin1 encodings and decodings.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.language;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
//import java.nio.charset.CharsetDecoder;
//import java.nio.charset.CharsetEncoder;
//import java.nio.charset.CharacterCodingException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Encodings {

    private final static String   EMPTY_STRING  = "";
    /** the database encoding, e.g.: ISO8859_1, Cp1251, UTF8 */
    private String enc_db;
    
    /** internal encoding, e.g.: UTF8, ISO8859_1, Cp1251 */
    private String enc_int;
    /** default internal encoding */
    public static final String enc_int_default = "UTF8";
    
    /** user encoding, e.g.: Cp1251, UTF8, ISO8859_1 */
    private String enc_ui;
    
    /** java source code encoding (it is used in junit tests), e.g.: Cp1251, UTF8, ISO8859_1 */
    private String enc_java;
    public static final String enc_java_default   = "UTF8";     // Debian
    //public static final String enc_java_default = "ISO8859_1";     // Debian
    //public static final String enc_java_default = "Cp1251";      // Mandriva
    
    public Encodings() {
        enc_db      = "ISO8859_1";  // Debian
        //enc_db    = "Cp1251";     // Mandriva
        
        enc_int     = enc_int_default;
        
        //enc_ui    = "UTF8";
        enc_ui      = "Cp1251";     // Mandriva ?
        
        enc_java    = enc_java_default;
    }
    
    /** Define the way of characters conversion via setting encoding of the database and encoding at the user side.
     *  @param database_encoding    encoding of the database, e.g.: ISO8859_1 (default), Cp1251, UTF8
     *  @param internal_encoding    internal encoding, e.g.: UTF8, ISO8859_1, Cp1251
     *  @param user_interface_encoding encoding at the user's side (user interface), e.g.: Cp1251 (default), ISO8859_1, UTF8
     */ 
    public void SetEncodings (String database_encoding, String internal_encoding, String user_interface_encoding) {
        enc_db      = database_encoding;
        enc_int     = internal_encoding;
        enc_ui      = user_interface_encoding;
        
    }
    public void SetEncodingJavaSourceCode (String e) {
        enc_java    = e;
    }
    
    
    /** Define the way of characters conversion via setting encoding of the database and encoding at the user side.
     *  @param user_interface_encoding_source   encoding at the user's side (user interface), e.g.: Cp1251 (default), ISO8859_1, UTF8
     *  @param database_encoding_dest           encoding of the database, e.g.: ISO8859_1 (default), Cp1251, UTF8
     */ /*
    public void SetEncodingUserToDB (String user_interface_encoding_source, String database_encoding_dest) {
        ui_source     = user_interface_encoding_source;
        db_dest   = database_encoding_dest;
    }*/
    
    
    /** Gets encoding of the database */
    public String GetDBEnc          (){ return enc_db;  }
    /** Gets internal encoding */
    public String GetInternalEnc    (){ return enc_int; };  
    /** Gets encoding at the user's side (user interface) */
    public String GetUserEnc        (){ return enc_ui;  };
    /** Gets java sources encoding */
    public String GetJavaEnc        (){ return enc_java;  };
    
    /** Convert string from database to internal encoding */
    public String EncodeFromDB(String text) {
        return FromTo(text, enc_db, enc_int);
    }
    /** Convert string from internal encoding to database */
    public String EncodeToDB(String text) {
        return FromTo(text, enc_int, enc_db);
    }
    
    /** Convert string from user to internal encoding */
    public String EncodeFromUser(String text) {
        return FromTo(text, enc_ui, enc_int);
    }
    /** Convert string from internal to user encoding */
    public String EncodeToUser(String text) {
        return FromTo(text, enc_int, enc_ui);
    }
    
    /** Convert string from Java sources to internal encoding */
    public String EncodeFromJava(String text) {
        return FromTo(text, enc_java, enc_int);
    }
    
    // Static functions
    
    public static String bytesToUTF8(byte[] bytes) {
        return bytesTo(bytes, "UTF8");
    }
    
    //FromTo("text", "UTF8", "ISO8859_1");
    public static String FromTo(String text, String encode_from, String encode_to) {
        try {
            if(null == text || 0 == text.length()) { return EMPTY_STRING;
            }
            byte[] b = text.getBytes(encode_from);
            return new String(b, encode_to);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }    
    }
    
    
    // choose an encoding
    /*private static final Charset latin1 = Charset.forName( "ISO-8859-1" );
    private static final CharsetDecoder latin1_decoder = latin1.newDecoder();   // for byte to char
    private static final CharsetEncoder latin1_encoder = latin1.newEncoder();   // for char to byte
    
    private static final Charset cp1251 = Charset.forName( "Cp1251" );
    private static final CharsetDecoder cp1251_decoder = latin1.newDecoder();
    private static final CharsetEncoder cp1251_encoder = latin1.newEncoder();
    
    private static final Charset utf8 = Charset.forName( "UTF8" );
    private static final CharsetDecoder utf8_decoder = latin1.newDecoder();
    private static final CharsetEncoder utf8_encoder = latin1.newEncoder();
*/
    // effectively convert byte[] to char[] after a read
    //CharBuffer charBuffer = decoder.decode( byteBuffer  );

    // effectively convert char[] to byte[] before a write
    //ByteBuffer byteBuffer = encoder.encode( charBuffer );
    
    private static int len = 1024;
    private static CharBuffer cb = CharBuffer.allocate(len);
    private static ByteBuffer bb = ByteBuffer.allocate(len);
    
    //public static StringBuffer sb = new StringBuffer (1024);
    //public static String FromToFast(String text, String encode_from, String encode_to) 
    /*
    public static String FromToFast(String text, EncodingType et_from, EncodingType et_to) 
    {
        try {
            if(null == text) { return EMPTY_STRING;
            }
            
            if (text.length() >= len) {
                throw new IndexOutOfBoundsException(
                    "invalid index");
            }*/
            /*
            CharsetEncoder e = null;
            if(encode_from.equals("ISO8859_1")) {
                e = latin1_encoder;
            } else if(encode_from.equals("Cp1251")) {
                e = cp1251_encoder;
            } else if(encode_from.equals("UTF8")) {
                e = utf8_encoder;
            }
            
            CharsetDecoder d = null;
            if(encode_to.equals("ISO8859_1")) {
                d = latin1_decoder;
            } else if(encode_to.equals("Cp1251")) {
                d = cp1251_decoder;
            } else if(encode_to.equals("UTF8")) {
                d = utf8_decoder;
            }*/
            /*
            //byte[] b = text.getBytes(encode_from);
            bb.put( et_from.getEncoder().encode( cb.put(text) ) );
            cb.rewind();
            cb.put( et_to.getDecoder().decode( bb ) );
            bb.rewind();
            
            //return new String(b, encode_to);
            return cb.toString();
            //} catch (UnsupportedEncodingException e) {
          } catch (CharacterCodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
          }
    }*/
    
    public static String bytesTo(byte[] bytes, String encode) {
        try {
            return new String(bytes, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }    
    }
    
    public static String UTF8ToLatin1(String str) {
        try {
            if(null == str) { return EMPTY_STRING;
            }
            byte[] bytes = str.getBytes();
            return new String(bytes, "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }
    }
    
    public static String UTF8ToCp1251(String str) {
        try {
            if(null == str) { return EMPTY_STRING;
            }
            byte[] bytes = str.getBytes();
            return new String(bytes, "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }
    }
    
    public static String Latin1ToUTF8(String str) {
        try {
            if(null == str) { return EMPTY_STRING;
            }
            byte[] bytes = str.getBytes();
            return new String(bytes, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }
    }
    
    /** Prints available encodings to stdout */
    public static void printEncodings() {
        Map availcs = Charset.availableCharsets();
        for (Object o:availcs.keySet()) {
                System.out.println(o);
        }
    }
    /** Gets available encodings */
    public static Map getEncodings() {
        return Charset.availableCharsets();
    }
}

