/*
 * EncodingType.java - types of encodings.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.language;

// import java.nio.ByteBuffer;
// import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
// import java.nio.charset.CharacterCodingException;

/**
 * Types of encodings.
 *
 * See more in: Effective Java. Programming language Guide. J.Bloch. 
 */
public class EncodingType {
    
    //private final int       number;
    private final String            name;
    private final Charset           charset;    // = Charset.forName( "ISO-8859-1" );
    private final CharsetDecoder    decoder;    // = charset.newDecoder();   // for byte to char
    private final CharsetEncoder    encoder;    // = charset.newEncoder();   // for char to byte
    
    private EncodingType(String name) {
        this.name = name;
        
        charset = Charset.forName( name );
        decoder = charset.newDecoder();   // for byte to char
        encoder = charset.newEncoder();   // for char to byte
    }
    
    public CharsetDecoder getDecoder () { return decoder; }
    public CharsetEncoder getEncoder () { return encoder; }
    public String         toString() { return name; }
    //public int toInt() { return number; }
    
    //public static final EncodingType DEFAULT        = new EncodingType("UTF8");
    /** latin1 */
    public static final EncodingType LATIN1         = new EncodingType("ISO8859_1");
    /** Cp1251 */
    public static final EncodingType CP1251         = new EncodingType("Cp1251");
    /** UTF8 */
    public static final EncodingType UTF8           = new EncodingType("UTF8");
    
    
    /** Gets EncodingType by name */
    public static EncodingType get(String name) throws NullPointerException
    {
        /*if(name == DEFAULT.toString()) {
            return DEFAULT;
        } else */
        if(name == LATIN1.toString()) {
            return LATIN1;
        } else if(name == CP1251.toString()) {
            return CP1251;
        } else if(name == UTF8.toString()) {
            return UTF8;
        } /*else if(name == .toString()) {
            return ;
        } else if(name == .toString()) {
            return ;
        } else if(name == .toString()) {
            return ;
        }*/ else {
            throw new NullPointerException("Null EncodingType");
        }
    }
}
