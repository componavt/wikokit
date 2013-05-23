package wikokit.base.wikt.sql.lang;

import java.util.Comparator;

import wikokit.base.wikt.sql.TLang;

public /** Sorts languages by size, i.e. by the number of POS, descending order. */
class LanguageSizeComparator implements Comparator<TLang> {

    public int compare(TLang a, TLang b) {
        int a1 = a.getNumberPOS();
        int b1 = b.getNumberPOS();
        
        //System.out.println("LanguageSizeComparator: a="+a + "; b="+b+"; a.getNumberPOS()="+a.getNumberPOS()+"; b.getNumberPOS()="+b.getNumberPOS());
        if (a1 > b1) {
            return -1;
        } else {
            if(a1 < b1) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}