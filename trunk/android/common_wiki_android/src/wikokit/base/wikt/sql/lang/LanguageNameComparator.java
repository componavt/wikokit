package wikokit.base.wikt.sql.lang;

import java.util.Comparator;

import wikokit.base.wikt.sql.TLang;


/** Sorts languages by size, i.e. by the number of POS, descending order. */
public class LanguageNameComparator implements Comparator<TLang> {

    public int compare(TLang a, TLang b) {
        String a1 = a.getLanguage().getName();
        String b1 = b.getLanguage().getName();
        
        return a1.compareTo(b1);
        //System.out.println("LanguageSizeComparator: a="+a + "; b="+b+"; a.getNumberPOS()="+a.getNumberPOS()+"; b.getNumberPOS()="+b.getNumberPOS());
    }
}