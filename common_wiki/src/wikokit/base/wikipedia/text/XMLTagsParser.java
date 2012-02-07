/*
 * XMLTagsParser.java - parses XML tags.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.text;

import java.text.StringCharacterIterator;

/** Parser of XML (HTML) tags, e.g. &nbsp; &quot; &lt;br />, etc.
 */
public class XMLTagsParser {
    
    private final static String NULL_STRING     = new String("");
    
    
    /** If the 'text' (from the position 'pos') is one of tags: 
     * &lt; &gt; &amp; &quot; &#039; &nbsp;, &ndash; or &mdash; 
     * then this tag is returned, else empty string will be returned.
     * 
     * @param  pos position in 'text' from which the tag will be extracted
     */
    protected static String isAmpersandTag(String text, int pos)
    {
        if (null == text) {
            return NULL_STRING;
        }
        
        int len = text.length();
        if(pos < 0 || pos > len-4) {
            return NULL_STRING;
        }
        
        if ('&' != text.charAt(pos)) {
            return NULL_STRING;
        }
        
        String result = NULL_STRING;
                                                                // cut off the text till the first ';'
        String s = text.substring(pos, Math.min(len, pos+7));   // the longest tag &ndash; has length 7
        int column_pos = s.indexOf(';');
        if (-1 != column_pos) {
            if (column_pos < s.length() - 1)
                s = s.substring(0, column_pos+1);
            
            if( XMLTag.existsTag(s) )
                return s;
        }
        
        return result;        
    }
    
    
    /** If the 'text' (from the position 'pos') is one of tags: 
     * &lt;br />,&lt;br/>,&lt;br> then this tag is returned, 
     * else empty string will be returned.
     * 
     * @param  pos position in 'text' from which the tag will be extracted
     */
    protected static String isBRNewlineTag(String text, int pos)
    {
        if (null == text) {
            return NULL_STRING;
        }
        
        //          <br />, <br/>, <br>
        // length:  6,      5,     4
        int len = text.length();
        if(pos < 0 || pos > len-4) {
            return NULL_STRING;
        }
        
        if ('<' != text.charAt(pos)
         || 'b' != text.charAt(pos + 1)
         || 'r' != text.charAt(pos + 2)) {
            return NULL_STRING;
        }
        
        String result = NULL_STRING;
              
        if(len > pos+3) {
            
            char ch3 = text.charAt(pos+3);
            
            if('>' == ch3) {
                result = "<br>";
            
            } else if (len > pos+4)
            {
                if ('/' == ch3 &&
                    '>' == text.charAt(pos+4))
                {
                    result = "<br/>";
                    
                } else if (len > pos+5 &&
                        ' ' == ch3  && 
                        '/' == text.charAt(pos+4) &&
                        '>' == text.charAt(pos+5))
                {
                    result = "<br />";
                }
            }
        }
        return result;        
    }
    
    
    /** Removes the following characters from the text: 
     * &lt;, >, &, ", also their expansions (&amp;lt;, &amp;gt;, &amp;amp;, 
     * &amp;quot;, &amp;#039;, &amp;nbsp;, &amp;ndash;, &amp;mdash;) 
     * by the 'replacement' character.<br><br>
     * 
     * Replaces &lt;br />,&lt;br/>,&lt;br> by newline symbol.<br><br>
     * 
     * Remains the character '.<br><br>
     * 
     * Attention: the parsing of other XML (HTML) tags should be done before this 
     * function execution, since open bracket '&lt;' will be deleted.
     */
    public static String replaceCharFromXML(String text, char replacement)
    {
        if (null == text) {
            System.out.println("Error in StringUtil.escapeCharFromXML(), argument is null.");
            return NULL_STRING;
        }
        
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(text);
        char character =  iterator.current();
        
        while (character != StringCharacterIterator.DONE )
        {
            if (character != ' '  && 
                character != '\'' && // remain words like "Let's"
                XMLTag.existsGlyph(character)) 
            {
                if (character == '<') {

                    // break symbols
                    int save = iterator.getIndex();
                    String br_tag = isBRNewlineTag(text, save);

                    int blen = br_tag.length();
                    if (blen > 0) {
                        iterator.setIndex(save + blen - 1);
                        result.append("\n");
                    } else {
                        result.append(replacement);
                    }

                } else if (character == '&') {

                    // skip (error) replacement: &lt; -> lt;, should be &lt; -> ' '
                    int save = iterator.getIndex();
                    String ampersand_tag = isAmpersandTag(text, save);

                    int alen = ampersand_tag.length();
                    if (alen > 0) {
                        iterator.setIndex(save + alen - 1);
                    }
                    result.append(replacement);
                    
                }  else // if ( character == '>'  || character == '\"' // || character == '\''
                {
                    result.append(replacement);
                }
            }
            else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
    
    
    /**
      * Escapes characters for text appearing as XML data by HTML tags.
      * 
      * <P>The following characters are replaced with corresponding character entities : 
      * <table border='1' cellpadding='3' cellspacing='0'>
      * <tr><th> Character </th><th> Encoding </th></tr>
      * <tr><td> < </td><td> &amp;lt; </td></tr>
      * <tr><td> > </td><td> &amp;gt; </td></tr>
      * <tr><td> & </td><td> &amp;amp; </td></tr>
      * <tr><td> " </td><td> &amp;quot;</td></tr>
      * <tr><td> ' </td><td> &amp;#039;</td></tr>
      * </table>
      * 
      * <P>Note that JSTL's {@code <c:out>} escapes the exact same set of 
      * characters as this method. <span class='highlight'>That is, {@code <c:out>}
      *  is good for escaping to produce valid XML, but not for producing safe HTML.</span>
      * see: http://www.javapractices.com/topic/TopicAction.do?Id=96
      */
    public static String escapeCharFromXML(String text)
    {
        if (null == text) {
            System.out.println("Error in StringUtil.escapeCharFromXML(), argument is null.");
            return NULL_STRING;
        }
        
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(text);
        char character =  iterator.current();
        
        while (character != StringCharacterIterator.DONE ){
          if (character == '&') {
              
              // skip double (error) parsing: &lt; -> &amp;lt;
              int save = iterator.getIndex();
              String ampersand_tag = isAmpersandTag(text, save);
              
              if(ampersand_tag.length() > 0) {
                  result.append(ampersand_tag);
                  iterator.setIndex(save + ampersand_tag.length() - 1);
              } else {
                  result.append("&amp;");
              }
          } else if (' ' != character && XMLTag.existsGlyph(character)) {
            result.append(XMLTag.getHTML(character));
          }
          else {
            //the char is not a special one
            //add it to the result as is
            result.append(character);
          }
          character = iterator.next();
        }
        return result.toString();
    }
}
