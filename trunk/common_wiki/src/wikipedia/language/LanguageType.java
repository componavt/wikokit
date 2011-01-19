/*
 * LanguageType.java - code of languages in wiki.
 * 
 * Copyright (c) 2008-2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

//http://ru.wiktionary.org/w/index.php?title=%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BF%D0%B5%D1%80%D0%B5%D0%B2-%D0%B1%D0%BB%D0%BE%D0%BA&diff=next&oldid=1243557
//http://en.wiktionary.org/wiki/Wiktionary:Index_to_templates/languages#Template_table

package wikipedia.language;

import wikipedia.language.local.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


/** Languages of wiki: code and name, e.g. ru and Russian. 
 *
 * Source of data: 
 * mediawiki-1.10.1/languages/Names.php
 * http://www.ethnologue.com/show_language.asp?code=ntk
 *
 * Russian Wikipedia: http://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4%D1%8B_%D1%8F%D0%B7%D1%8B%D0%BA%D0%BE%D0%B2
 *
 * Russian Wiktionary:
 *   Шаблон:перев-блок  or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BF%D0%B5%D1%80%D0%B5%D0%B2-%D0%B1%D0%BB%D0%BE%D0%BA
 *   Шаблон:lang        or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:lang
 *
 * English Wiktionary
 *  http://en.wiktionary.org/wiki/Wiktionary:Index_to_templates/languages
 *  http://en.wiktionary.org/wiki/Wiktionary:LANGCODE
 *  http://en.wiktionary.org/wiki/Wiktionary:Translations/Wikification
 *  http://en.wiktionary.org/wiki/Wiktionary:Language_codes
 *  http://en.wiktionary.org/wiki/Wiktionary:Wikimedia_language_codes
 *
 * English Wikipedia
 *  http://en.wikipedia.org/wiki/ISO_639
 *  http://meta.wikimedia.org/wiki/List_of_Wikipedias
 *  http://en.wikipedia.org/wiki/List_of_languages_by_number_of_native_speakers
 */
public class LanguageType {
    
    /** Two (or more) letter language code, e.g. 'en', 'ru'. */
    private final String code;
    
    /** Language name, e.g. 'English', 'Русский'. */
    private final String name;

    /** Language name in English (ASCII), e.g. 'English', 'Russian'. 
     * See http://en.wiktionary.org/wiki/Wiktionary:Language_names
     */
    private final String english_name;
    
    private static Map<String, String>       code2name = new HashMap<String, String>();
    private static Map<String, LanguageType> code2lang = new HashMap<String, LanguageType>();

    /** It is need for English Wiktionary */
    private static Map<String, LanguageType> english2lang = new HashMap<String, LanguageType>();

    // synonyms, alternate names, or closely related languages:
    /** If there are more than one English names for the language */
    private static Map<String, LanguageType> multiple_names2type = new HashMap<String, LanguageType>();
 
    /** If there are more than one language code for the language */
    private static Map<String, LanguageType> multiple_codes2type = new HashMap<String, LanguageType>();


    private LanguageType(String code,String name,String english_name) {
        this.code = code;

        if(code.length() == 0 || name.length() == 0 || english_name.length() == 0)
            System.out.println("Error in LanguageType.LanguageType(): one of parameters is empty! The language code="+code+"; name=\'"+name+"\'; english_name=\'"+english_name+"\'.");

        //this.name         = name;
        //this.english_name = english_name;
        
        // only name in English is used now, name is skipped
        this.name           = english_name;
        this.english_name   = english_name;

        // check the uniqueness of the language code and the english_name
        String name_prev = code2name.get(code);
        LanguageType english_name_prev = english2lang.get(english_name);
        
        if(null != name_prev)
            System.out.println("Error in LanguageType.LanguageType(): duplication of code! The language code="+code+
                    " language='"+english_name+
                    "'. Check the maps code2name and english2lang.");

        if(null != english_name_prev)
            System.out.println("Error in LanguageType.LanguageType(): duplication of language! The language code="+code+
                    " language='"+english_name+
                    "'. Check the maps code2name and english2lang.");

        code2name.put(code, name);
        code2lang.put(code, this);
        english2lang.put(english_name, this);

        checksPrefixSuffixSpace(code);
        checksPrefixSuffixSpace(english_name);
    }

    /** Checks whitespace characters in the prefix or suffix of a string.
     * Prints "error" message if there is any.
     */
    private static void checksPrefixSuffixSpace(String s) {

        if(s.charAt(0) == ' ' || s.charAt(s.length()-1) == ' ')
            System.out.println("Error in LanguageType.LanguageType(): there are leading spaces in code " +
                    "or language name, string='"+s+"'.");
    }

    /** Gets language code. */
    public String getCode() {
        return code;
    }

    /** Gets language name. */
    public String getName() {
        return name;
    }
    
    /** Checks weather exists the language code 'code'. */
    public static boolean has(String code) {
        return code2name.containsKey(code) ||
               multiple_codes2type.containsKey(code);
    }
    
    public String toString() { return code; }

    /** Gets language code in English (ASCII). */
    public String toStringASCII() {
        
        if(code.equalsIgnoreCase("Буква"))
            return "letter_ru";
            
        return code;
    }
    
    /** Returns true if the language has this 'code'. */
    public boolean equals(String code) {
        return code.equalsIgnoreCase(this.code);
    }
    
    /** Gets LanguageType by language code */
    public static LanguageType get(String code) throws NullPointerException
    {
        LanguageType lt;

        if(null != (lt = code2lang.get(code)))
            return  lt;

        if(null != (lt = multiple_codes2type.get(code)))
            return  lt;

        throw new NullPointerException("Null LanguageType (get)");
    }

    /** Checks weather exists the language name in English. */
    public static boolean hasEnglishName(String english) {
        return english2lang.containsKey(english) ||
        multiple_names2type.containsKey(english);
    }
    
    /** Gets LanguageType by language name in English.
     * 
     *  @return null if there is no corresponded language
     */
    public static LanguageType getByEnglishName (String english) // throws NullPointerException
    {
        LanguageType lt;

        if(null != (lt = english2lang.get(english)))
            return  lt;


        if(null != (lt = multiple_names2type.get(english)))
            return  lt;

        return null; // throw new NullPointerException("Null LanguageType (getByEnglishName)");
    }

    /** Counts number of languages. */
    public static int size() {
        return code2name.size();
    }
    
    /** Gets all languages. */
    public static Map<String, LanguageType> getAllLanguages() {
        return code2lang;
    }

    /////////////////////////////////////

    /** Adds one more language code and language name for the same language.
     */
    public static LanguageType addNonUnique(LanguageType lt,
                                             String code, String english_name) {
        addNonUniqueName(lt, english_name);
        return addNonUniqueCode(lt, code);
    }

    /** Adds one more language name for this language.
     */
    public static LanguageType addNonUniqueName(LanguageType lt, String english_name) {

        checksPrefixSuffixSpace(english_name);
        if(english_name.length() == 0) {
            System.out.println("Error in LanguageType.addNonUniqueName(): empty language name! The language code="+lt+".");
            return null;
        }

        if(english2lang.containsKey(english_name)) {
            System.out.println("Error in LanguageType.addNonUniqueName(): the language '"+english_name+
                    "' is already presented in the map english2lang!");
            return null;
        }

        if(multiple_names2type.containsKey(english_name)) {
            System.out.println("Error in LanguageType.addNonUniqueName(): the language '"+english_name+
                    "' is already presented in the map multiple_names2type!");
            return null;
        }

        multiple_names2type.put(english_name, lt);
        return lt;
    }
    
    /** Adds one more language code for this language.
     */
    public static LanguageType addNonUniqueCode(LanguageType lt, String code) {

        checksPrefixSuffixSpace(code);
        if(code.length() > 12) {
            System.out.println("Error in LanguageType.addNonUniqueCode(): the language code '"+code+
                    "' is too long (.length() > 12)!");// zh-classical
            return null;
        }


        if(code2lang.containsKey(code)) {
            System.out.println("Error in LanguageType.addNonUniqueCode(): the language code '"+code+
                    "' is already presented in the map code2lang!");
            return null;
        }
        
        if(multiple_codes2type.containsKey(code)) {
            System.out.println("Error in LanguageType.addNonUniqueName(): the language '"+code+
                    "' is already presented in the map multiple_codes2type!");
            return null;
        }
        
        multiple_codes2type.put(code, lt);
        return lt;
    }

    /////////////////////////////////////
    
    /** The set of unknown language codes, which were found during parsing.
     * There is only one message for one uknown language code (for concise logging).
     */
    private static Set<String> unknown_lang_code = new HashSet<String>();
    private static Set<String> unknown_lang_name = new HashSet<String>();

    /** Checks weather exists the unknown language code 'code'. */
    public static boolean hasUnknownLangCode(String code) {
        return unknown_lang_code.contains(code);
    }

    /** Adds unknown language code 'code'. */
    public static boolean addUnknownLangCode(String code) {
        return unknown_lang_code.add(code);
    }

    /** Checks weather exists the unknown language name. */
    public static boolean hasUnknownLangName(String name) {
        return unknown_lang_name.contains(name);
    }

    /** Adds unknown language name. */
    public static boolean addUnknownLangName(String code) {
        return unknown_lang_name.add(code);
    }

    /////////////////////////////////////

    // List of localizations / translations

    // private static final LanguageTypeLocal local_ru = new LanguageTypeRu();
    //                                     local_de
    //                                     local_fr etc...
    
    /** Translates the language name into the target language.
     * 
     *  @return Language name in English if there is no a translation
     */
    public String translateTo (LanguageType target)
    {
        if(null == target || LanguageType.en == target)
            return english_name;

        if(LanguageType.ru == target) {
            return LanguageTypeRu.get(this);
        //} else if(l == LanguageType.de) { // todo some day
        }
        
        return english_name; // if there is no translation into local language, then English name
    }

    /** Check wheather exists the translation of the language into the target
     * language. */
    public boolean hasTranslation (LanguageType target)
    {
        if(LanguageType.en == target)
            return true;

        if(null == target)
            return false;

        if(LanguageType.ru == target) {
            return LanguageTypeRu.has(this);
        //} else if(l == LanguageType.de) { // todo some day
        }

        return false; // if there is no translation into local language, then English name
    }


    
    // English Wiktionary specific codes
    public static final LanguageType mul = new LanguageType("mul", "Translingual", "Translingual");
    public static final LanguageType INT = LanguageType.addNonUniqueCode(mul, "INT");// Russian Wiktionary

    public static final LanguageType aus_dar = new LanguageType("aus-dar", "Darkinjung", "Darkinjung");

    public static final LanguageType aus_wwg = new LanguageType("aus-wwg", "Woiwurrung", "Woiwurrung");
    public static final LanguageType aus_wwg2 = LanguageType.addNonUniqueName(aus_wwg, "Woiworung");
    public static final LanguageType aus_wwg3 = LanguageType.addNonUniqueName(aus_wwg, "Woiwurrong");
    public static final LanguageType aus_wwg4 = LanguageType.addNonUniqueName(aus_wwg, "Wuywurung");

    public static final LanguageType roa_gal = new LanguageType("roa-gal", "Gallo", "Gallo");

    public static final LanguageType roa_ptg = new LanguageType("roa-ptg", "Galician-Portuguese", "Galician-Portuguese");
    public static final LanguageType roa_ptg2 = LanguageType.addNonUniqueName(roa_ptg, "Old Portuguese");
    public static final LanguageType roa_ptg3 = LanguageType.addNonUniqueName(roa_ptg, "Galician Portuguese");

    public static final LanguageType tgt = new LanguageType("tgt", "Tagbanwa", "Tagbanwa");
    public static final LanguageType tgt2 = LanguageType.addNonUniqueName(tgt, "Central Tagbanwa");
    public static final LanguageType tgt3 = LanguageType.addNonUniqueName(tgt, "Tagbanwa script");




    // more than one language code (or language name) for one language
    public static final LanguageType aar = new LanguageType("aar", "Afar", "Afar");
    public static final LanguageType aa = LanguageType.addNonUniqueCode(aar, "aa");

    public static final LanguageType abk = new LanguageType("abk", "аҧсшәа", "Abkhaz");
    public static final LanguageType ab  = LanguageType.addNonUniqueCode(abk, "ab");
    
    public static final LanguageType acr = new LanguageType("acr", "Achi", "Achi");
    public static final LanguageType acc = LanguageType.addNonUnique(acr, "acc", "Cubulco");

    public static final LanguageType ace = new LanguageType("ace", "AchГЁh", "Acehnese");
    public static final LanguageType ace2 = LanguageType.addNonUniqueName(ace, "Aceh");
    
    public static final LanguageType acv = new LanguageType("acv", "Achumawi", "Achumawi");
    public static final LanguageType acv2 = LanguageType.addNonUniqueName(acv, "Achomawi");
    
    public static final LanguageType ada = new LanguageType("ada", "Adangme", "Adangme");
    public static final LanguageType ada2 = LanguageType.addNonUniqueName(ada, "Dangme");
    
    public static final LanguageType adz = new LanguageType("adz", "Adzera", "Adzera");
    public static final LanguageType zsu = LanguageType.addNonUnique(adz, "zsu", "Sukurum");
    public static final LanguageType zsa = LanguageType.addNonUnique(adz, "zsa", "Sarasira");

    public static final LanguageType afr = new LanguageType("afr", "Afrikaans", "Afrikaans");
    public static final LanguageType af = LanguageType.addNonUniqueCode(afr, "af");
    
    public static final LanguageType agx = new LanguageType("agx", "Aghul", "Aghul");
    public static final LanguageType agx2 = LanguageType.addNonUniqueName(agx, "Agul");

    public static final LanguageType aib = new LanguageType("aib", "Äynu", "Äynu");
    public static final LanguageType aib2 = LanguageType.addNonUniqueName(aib, "Aynu");

    public static final LanguageType ain = new LanguageType("ain", "Ainu", "Ainu");
    public static final LanguageType ain_lat = LanguageType.addNonUnique(ain, "ain.lat", "Ainu (Latin)");//In Russian Wiktionary
    public static final LanguageType ain_kana = LanguageType.addNonUnique(ain, "ain.kana", "Ainu (Kana)");//In Russian Wiktionary

    public static final LanguageType aka = new LanguageType("aka", "Akan", "Akan");
    public static final LanguageType ak = LanguageType.addNonUnique(aka, "ak", "Twi-Fante");
    public static final LanguageType twi = LanguageType.addNonUnique(aka, "twi", "Twi");
    public static final LanguageType tw = LanguageType.addNonUniqueCode(aka, "tw");
    public static final LanguageType fat = LanguageType.addNonUnique(aka, "fat", "Fante");
    public static final LanguageType fat2 = LanguageType.addNonUniqueName(aka, "Fanti");

    public static final LanguageType ale = new LanguageType("ale", "Aleut", "Aleut");
    public static final LanguageType mud = LanguageType.addNonUnique(ale, "mud", "Mednyj Aleut");

    public static final LanguageType alr = new LanguageType("alr", "Alyutor", "Alyutor");
    public static final LanguageType alr2 = LanguageType.addNonUniqueName(alr, "Alutor");

    public static final LanguageType alt = new LanguageType("alt", "Altai", "Altai");
    public static final LanguageType alt2 = LanguageType.addNonUniqueName(alt, "Southern Altai");
    public static final LanguageType atv = new LanguageType("atv", "Northern Altai", "Northern Altai");

    public static final LanguageType am = new LanguageType("am", "amarəñña", "Amharic");
    public static final LanguageType amh = LanguageType.addNonUniqueCode(am, "amh");
    
    public static final LanguageType apk = new LanguageType("apk", "Plains Apache", "Plains Apache");
    public static final LanguageType apk2 = LanguageType.addNonUniqueName(apk, "Kiowa Apache");
    public static final LanguageType apw = new LanguageType("apw", "Western Apache", "Western Apache");
    
    
    // Arabic ------------
    public static final LanguageType ara = new LanguageType("ara", "Arabic", "Arabic");
    public static final LanguageType ar = LanguageType.addNonUniqueCode(ara, "ar");

    public static final LanguageType arb = new LanguageType("arb", "Standard Arabic", "Standard Arabic");
    public static final LanguageType arb2 = LanguageType.addNonUniqueName(arb, "Modern Standard Arabic");
    public static final LanguageType arb3 = LanguageType.addNonUniqueName(arb, "Literary Arabic");
    
    public static final LanguageType aao = new LanguageType("aao", "Saharan Arabic", "Saharan Arabic");
    public static final LanguageType aao2 = LanguageType.addNonUniqueName(aao, "Algerian Saharan Arabic");

    public static final LanguageType acq = LanguageType.addNonUnique(ara, "acq", "Ta'izzi-Adeni Arabic");
    public static final LanguageType acx = new LanguageType("acx", "Omani Arabic", "Omani Arabic");
    public static final LanguageType aeb = new LanguageType("aeb", "Tunisian Arabic", "Tunisian Arabic");
    public static final LanguageType afb = new LanguageType("afb", "Gulf Arabic", "Gulf Arabic");

    public static final LanguageType ajp = new LanguageType("ajp", "South Levantine Arabic", "South Levantine Arabic");
    public static final LanguageType apc = new LanguageType("apc", "North Levantine Arabic", "North Levantine Arabic");

    public static final LanguageType apd = new LanguageType("apd", "Sudanese Arabic", "Sudanese Arabic");

    public static final LanguageType arq = new LanguageType("arq", "Algerian Arabic", "Algerian Arabic");
    public static final LanguageType ars = new LanguageType("ars", "Najdi Arabic", "Najdi Arabic");
    public static final LanguageType ary = new LanguageType("ary", "Moroccan Arabic", "Moroccan Arabic");
    public static final LanguageType arz = new LanguageType("arz", "Maṣri", "Egyptian Arabic");
    
    public static final LanguageType auz = LanguageType.addNonUnique(ara, "auz", "Uzbeki Arabic");
    public static final LanguageType abh = LanguageType.addNonUnique(ara, "abh", "Tajiki Arabic");

    public static final LanguageType avl = LanguageType.addNonUnique(ara, "avl", "Bedawi");
    public static final LanguageType avl2 = LanguageType.addNonUniqueName(ara, "Eastern Egyptian Bedawi Arabic");

    public static final LanguageType ayh = LanguageType.addNonUnique(ara, "ayh", "Hadhrami");
    public static final LanguageType ayh2 = LanguageType.addNonUniqueName(ara, "Hadrami Arabic");

    public static final LanguageType ayl = new LanguageType("ayl", "Libyan Arabic", "Libyan Arabic");
    public static final LanguageType ayl2 = LanguageType.addNonUniqueName(ayl, "Sulaimitian Arabic");

    public static final LanguageType ayn = LanguageType.addNonUnique(ara, "ayn", "Sanaani Arabic");
    
    public static final LanguageType ayp = new LanguageType("ayp", "North Mesopotamian Arabic", "North Mesopotamian Arabic");

    public static final LanguageType pga = new LanguageType("pga", "Juba Arabic", "Juba Arabic");
    
    public static final LanguageType shu = new LanguageType("shu", "Chadian Arabic", "Chadian Arabic");
    public static final LanguageType shu2 = LanguageType.addNonUniqueName(shu, "Shuwa Arabic");
    // ------------ eo Arabic


    // Aramaic ------------
    public static final LanguageType arc = new LanguageType("arc", "Ārāmāyâ", "Aramaic");
    public static final LanguageType arc2 = LanguageType.addNonUniqueName(arc, "Official Aramaic");
    public static final LanguageType tmr = LanguageType.addNonUnique(arc, "tmr", "Jewish Babylonian Aramaic");

    public static final LanguageType arc_jud = LanguageType.addNonUniqueCode(arc, "arc.jud");// Russian Wiktionary

    public static final LanguageType aii = new LanguageType("aii", "Assyrian Neo-Aramaic", "Assyrian Neo-Aramaic");

    public static final LanguageType amw = LanguageType.addNonUnique(arc, "amw", "Western Neo-Aramaic");
    public static final LanguageType bhn = LanguageType.addNonUnique(arc, "bhn", "Bohtan Neo-Aramaic");
    public static final LanguageType bjf = LanguageType.addNonUnique(arc, "bjf", "Barzani Jewish Neo-Aramaic");
    public static final LanguageType cld = LanguageType.addNonUnique(arc, "cld", "Chaldean Neo-Aramaic");
    public static final LanguageType jpa = LanguageType.addNonUnique(arc, "jpa", "Jewish Palestinian Aramaic");

    public static final LanguageType sam = new LanguageType("sam", "Samaritan Aramaic", "Samaritan Aramaic");
    
    public static final LanguageType syc = new LanguageType("syc", "Syriac", "Syriac");
    public static final LanguageType syr = LanguageType.addNonUnique(syc, "syr", "Classical Syriac");
    public static final LanguageType arc_syr = LanguageType.addNonUniqueCode(syc, "arc.syr");// Russian Wiktionary

    public static final LanguageType tru = new LanguageType("tru", "Turoyo", "Turoyo");
    public static final LanguageType tru2 = LanguageType.addNonUniqueName(tru, "Surayt");
    // not yet in English Wiktionary:
    //
    // oar – Old Aramaic    Ancient Aramaic
    // aij – Lishanid Noshan, Neo-Aramaic or Judeo-Aramaic
    // hrt – Hértevin
    // huy – Hulaulá
    // kqd – Koy Sanjaq Surat
    // lhs – Mlahsô
    // lsd – Lishana Deni
    // mid – Modern Mandaic
    // myz – Classical Mandaic
    // syn – Senaya
    // trg – Lishán Didán
    // ------------ eo Aramaic


    public static final LanguageType are = new LanguageType("are", "Arrernte", "Arrernte");
    public static final LanguageType are2 = LanguageType.addNonUniqueName(are, "Western Arrernte");
    public static final LanguageType aer = LanguageType.addNonUnique(are, "aer", "Eastern Arrernte");
    public static final LanguageType amx = LanguageType.addNonUnique(are, "amx", "Anmatjirra");
    public static final LanguageType aly = LanguageType.addNonUnique(are, "aly", "Alyawarr");
    public static final LanguageType adg = LanguageType.addNonUnique(are, "adg", "Antekerrepenhe");

    public static final LanguageType arg = new LanguageType("arg", "aragonés", "Aragonese");
    public static final LanguageType an = LanguageType.addNonUniqueCode(arg, "an");
    
    public static final LanguageType aru = new LanguageType("aru", "Aruá", "Arua");
    public static final LanguageType aru2 = LanguageType.addNonUniqueName(aru, "Aruá");



    // Sign Language ------------
    public static final LanguageType ase = new LanguageType("ase", "American Sign Language", "American Sign Language");
    public static final LanguageType ase2 = LanguageType.addNonUniqueName(ase, "Ameslan");
    public static final LanguageType ase3 = LanguageType.addNonUniqueName(ase, "ASL");

    // ads Adamorobe Sign Language
    // aed Argentine Sign Language
    // aen Armenian Sign Language
    // afg Afghan Sign Language
    // asf Auslan 		Auslan, Australian Sign Language
    // asp Algerian Sign Language
    // asq Austrian Sign Language
    // asw Australian Aborigines Sign Language
    // bfi British Sign Language 		British Sign Language, BSL
    // bfk Ban Khor Sign Language
    // bog Bamako Sign Language
    // bqn Bulgarian Sign Language
    // bqy Bengkala Sign Language
    // bvl Bolivian Sign Language
    // bzs Brazilian Sign Language 		Brazilian Sign Language, LGB (obsolete), LSB (obsolete), LSCB (obsolete), Libras
    // cds Chadian Sign Language
    // csc Catalan Sign Language
    // csd Chiangmai Sign Language
    // cse Czech Sign Language
    // csf Cuba Sign Language
    // csg Chilean Sign Language
    // csl Chinese Sign Language
    // csn Colombian Sign Language
    // csq Croatia Sign Language
    // csr Costa Rican Sign Language
    // doq Dominican Sign Language
    // dse Dutch Sign Language
    // dsl Danish Sign Language
    // ecs Ecuadorian Sign Language
    // esl Egypt Sign Language
    // esn Salvadoran Sign Language
    // eso Estonian Sign Language
    // eth Ethiopian Sign Language
    // fcs Quebec Sign Language
    // fse Finnish Sign Language
    // fsl French Sign Language
    // fss Finnish-Swedish Sign Language
    // gse Ghanaian Sign Language
    // gsg German Sign Language
    // gsm Guatemalan Sign Language
    // gss Greek Sign Language
    // gus Guinean Sign Language
    // hab Hanoi Sign Language
    // haf Haiphong Sign Language
    // hds Honduras Sign Language
    // hks Hong Kong Sign Language
    // hos Ho Chi Minh City Sign Language
    // hps Hawai'i Pidgin Sign Language
    // hsh Hungarian Sign Language
    // hsl Hausa Sign Language
    // icl Icelandic Sign Language
    // inl Indonesian Sign Language
    // ins Indian Sign Language
    // ise Italian Sign Language
    // isg Irish Sign Language
    // isr Israeli Sign Language
    // jcs Jamaican Country Sign Language
    // jhs Jhankot Sign Language
    // jls Jamaican Sign Language
    // jos Jordanian Sign Language
    // jsl Japanese Sign Language
    // jus Jumla Sign Language
    // kgi Selangor Sign Language
    // kvk Korean Sign Language
    // lbs Libyan Sign Language
    // lls Lithuanian Sign Language
    // lsg Lyons Sign Language
    // lsl Latvian Sign Language
    // lso Laos Sign Language
    // lsp Panamanian Sign Language
    // lso Laos Sign Language
    // lsp Panamanian Sign Language
    // lst Trinidad and Tobago Sign Language
    // lsy Mauritian Sign Language
    // mdl Maltese Sign Language
    // mfs Mexican Sign Language
    // mre Martha's Vineyard Sign Language
    // msd Yucatec Maya Sign Language
    // msr Mongolian Sign Language
    // mzc Madagascar Sign Language
    // mzg Monastic Sign Language
    // mzy Mozambican Sign Language
    // nbs Namibian Sign Language
    // ncs Nicaraguan Sign Language
    // nsi Nigerian Sign Language
    // nsl Norwegian Sign Language
    // nsp Nepalese Sign Language
    // nsr Maritime Sign Language
    // nzs New Zealand Sign Language
    // okl Old Kentish Sign Language
    // pks Pakistan Sign Language
    // prl Peruvian Sign Language
    // prz Providencia Sign Language
    // psc Persian Sign Language
    // psd Plains Indian Sign Language
    // psg Penang Sign Language
    // psl Puerto Rican Sign Language
    // pso Polish Sign Language
    // psp Philippine Sign Language
    // psr Portuguese Sign Language
    // pys Paraguayan Sign Language
    // rms Romanian Sign Language
    // rsi Rennellese Sign Language
    // rsl Russian Sign Language
    // sdl Saudi Arabian Sign Language
    // sfb French Belgian Sign Language
    // sfs South African Sign Language
    // sgg Swiss-German Sign Language
    // sgx Sierra Leone Sign Language
    // slf Swiss-Italian Sign Language
    // sls Singapore Sign Language
    // sqs Sri Lankan Sign Language
    // ssp Spanish Sign Language
    // ssr Swiss-French Sign Language
    // svk Slovakian Sign Language
    // swl Swedish Sign Language
    // syy Al-Sayyid Bedouin Sign Language
    // tse Tunisian Sign Language
    // tsm Turkish Sign Language
    // tsq Thai Sign Language
    // tss Taiwan Sign Language
    // tsy Tebul Sign Language
    // tza Tanzanian Sign Language
    // ugn Ugandan Sign Language
    // ugy Uruguayan Sign Language
    // ukl Ukrainian Sign Language
    // uks Kaapor Sign Language
    // vgt Flemish Sign Language
    // vsi Moldova Sign Language
    // vsl Venezuelan Sign Language
    // vsv Valencian Sign Language
    // xki Kenyan Sign Language
    // xml Malaysian Sign Language
    // xms Moroccan Sign Language
    // yds Yiddish Sign Language
    // ysl Yugoslavian Sign Language
    // zib Zimbabwe Sign Language
    // zsl Zambian Sign Language
    // ------------ eo Sign Language

    

    public static final LanguageType ast = new LanguageType("ast", "Asturianu", "Asturian");
    public static final LanguageType ast2 = LanguageType.addNonUniqueName(ast, "Leonese");

    public static final LanguageType aus_syd = new LanguageType("aus-syd", "Dharug", "Sydney");// enwikt code
    public static final LanguageType aus_syd2 = LanguageType.addNonUniqueName(aus_syd, "Darug");
    public static final LanguageType aus_syd3 = LanguageType.addNonUniqueName(aus_syd, "Dharug");
    public static final LanguageType aus_syd4 = LanguageType.addNonUniqueName(aus_syd, "Dharruk");
    public static final LanguageType aus_syd5 = LanguageType.addNonUniqueName(aus_syd, "Dharuk");
    public static final LanguageType aus_syd6 = LanguageType.addNonUniqueName(aus_syd, "Eora");
    public static final LanguageType aus_syd7 = LanguageType.addNonUniqueName(aus_syd, "Iora");
    public static final LanguageType aus_syd8 = LanguageType.addNonUniqueName(aus_syd, "Iyora");
    
    public static final LanguageType av = new LanguageType("av", "Авар мацӀ", "Avar");
    public static final LanguageType ava = LanguageType.addNonUnique(av, "ava", "Avaric");
    
    public static final LanguageType ave = new LanguageType("ave", "Avestan", "Avestan");
    public static final LanguageType ae = LanguageType.addNonUniqueCode(ave, "ae");

    public static final LanguageType awk = new LanguageType("awk", "Awabakal", "Awabakal");
    public static final LanguageType awk2 = LanguageType.addNonUniqueName(awk, "Awabagal");
    
    public static final LanguageType ay = new LanguageType("ay", "Aymar aru", "Aymara");
    public static final LanguageType aym = LanguageType.addNonUniqueCode(ay, "aym");
    public static final LanguageType ayr = LanguageType.addNonUnique(ay, "ayr", "Central Aymara");
    public static final LanguageType ayc = LanguageType.addNonUnique(ay, "ayc", "Southern Aymara");

    
    
    // Azerbaijani ------------
    // Azari - Azerbaijani or Azari Language
    public static final LanguageType az = new LanguageType("az", "Azərbaycan", "Azerbaijani");
    public static final LanguageType aze = LanguageType.addNonUnique(az, "aze", "Azeri");
    public static final LanguageType az2 = LanguageType.addNonUniqueName(az, "Azerbaijani Turkic");
    public static final LanguageType az3 = LanguageType.addNonUniqueName(az, "Azeri Turkic");


    public static final LanguageType azj = LanguageType.addNonUnique(az, "azj", "North Azeri");
    public static final LanguageType azj2 = LanguageType.addNonUniqueName(az, "North Azerbaijani");

    public static final LanguageType azb = LanguageType.addNonUnique(az, "azb", "South Azeri");
    public static final LanguageType azb2 = LanguageType.addNonUniqueName(az, "South Azerbaijani");
    // ------------ eo Azerbaijani

    

    public static final LanguageType ba = new LanguageType("ba", "Башҡорт теле", "Bashkir");
    public static final LanguageType bak = LanguageType.addNonUniqueCode(ba, "bak");

    public static final LanguageType bal = new LanguageType("bal", "Balochi", "Balochi");
    public static final LanguageType bal2 = LanguageType.addNonUniqueName(bal, "Baluchi");
    public static final LanguageType bgp = LanguageType.addNonUnique(bal, "bgp", "Eastern Balochi");
    public static final LanguageType bgn = LanguageType.addNonUnique(bal, "bgn", "Western Balochi");
    public static final LanguageType bcc = LanguageType.addNonUnique(bal, "bcc", "Southern Balochi");

    public static final LanguageType ban = new LanguageType("ban", "Basa Bali", "Balinese");// Balinese or Bali (Nigeria)?
    public static final LanguageType bcn = new LanguageType("bcn", "Bibaali", "Bibaali");
    public static final LanguageType bcp = new LanguageType("bcp", "Bali", "Bali");

    public static final LanguageType bas = new LanguageType("bas", "Basaa", "Basaa");
    public static final LanguageType bas2 = LanguageType.addNonUniqueName(bas, "Bissa");
    // 3 Basa and 4 Bassa: see the problem at http://en.wikipedia.org/wiki/Basaa_language
    public static final LanguageType bas3 = LanguageType.addNonUniqueName(bas, "Basa");// Bassa==Kainji language
    public static final LanguageType bas4 = LanguageType.addNonUniqueName(bas, "Bassa");// Problem: Bassa==Kru language


    // Bikol ------------
    public static final LanguageType bcl = new LanguageType("bcl", "Bikol Central", "Bikol Central");
    public static final LanguageType bcl2 = LanguageType.addNonUniqueName(bcl, "Central Bikolano");

    public static final LanguageType bik = LanguageType.addNonUnique(bcl, "bik", "Bikol");
    public static final LanguageType bto = LanguageType.addNonUnique(bcl, "bto", "Iriga Bicolano");
    // ------------ eo Bikol


    public static final LanguageType be = new LanguageType("be", "Беларуская мова", "Belarusian");// Belarusian normative
    public static final LanguageType bel = LanguageType.addNonUniqueCode(be, "bel");
    public static final LanguageType be_tarask = LanguageType.addNonUniqueCode(be, "be-tarask");// Belarusian (Taraškievica)
    public static final LanguageType be_x_old = LanguageType.addNonUniqueCode(be, "be-x-old");

    // Bena: {{bez}}, {{yun}}
    public static final LanguageType bez = new LanguageType("bez", "Bena", "Bena");
    public static final LanguageType yun = new LanguageType("yun", "Binna", "Binna");

    public static final LanguageType bg = new LanguageType("bg", "Български език", "Bulgarian");
    public static final LanguageType bul = LanguageType.addNonUniqueCode(bg, "bul");


    
    // Bihari ------------
    public static final LanguageType bh = new LanguageType("bh", "Bihari", "Bihari");

    public static final LanguageType anp = new LanguageType("anp", "Angika", "Angika");
    public static final LanguageType bho = new LanguageType("bho", "Bhojpuri", "Bhojpuri");

    public static final LanguageType hif = new LanguageType("hif", "Fiji Hindi", "Fiji Hindi");
    public static final LanguageType hif2 = LanguageType.addNonUniqueName(hif, "Fijian Hindi");
    public static final LanguageType hif_deva = LanguageType.addNonUniqueCode(hif, "hif-deva");// Fiji Hindi (devangari)
    public static final LanguageType hif_latn = LanguageType.addNonUniqueCode(hif, "hif-latn");// Fiji Hindi (latin)

    public static final LanguageType mag = new LanguageType("mag", "Magahi", "Magahi");

    public static final LanguageType mai = new LanguageType("mai", "Maithili", "Maithili");
    public static final LanguageType mai2 = LanguageType.addNonUniqueName(mai, "Vajjika");

    // not yet in English Wiktionary:
    public static final LanguageType kyw = LanguageType.addNonUnique(bh, "kyw", "Kudmali");
    public static final LanguageType mjz = LanguageType.addNonUnique(bh, "mjz", "Majhi");
    public static final LanguageType smm = LanguageType.addNonUnique(bh, "smm", "Musasa");
    public static final LanguageType tdb = LanguageType.addNonUnique(bh, "tdb", "Panchpargania");// Панчпарганья
    public static final LanguageType sck = LanguageType.addNonUnique(bh, "sck", "Sadri");// Садри
    public static final LanguageType sdr = LanguageType.addNonUnique(bh, "sdr", "Sadri, Oraon");
    public static final LanguageType hns = LanguageType.addNonUnique(bh, "hns", "Sarnami Hindustani");
    public static final LanguageType sjp = LanguageType.addNonUnique(bh, "sjp", "Surajpuri");
    // ------------ eo Bihari

    

    public static final LanguageType bi = new LanguageType("bi", "Bislama", "Bislama");
    public static final LanguageType bis = LanguageType.addNonUniqueCode(bi, "bis");

    public static final LanguageType bm = new LanguageType("bm", "Bamanankan", "Bambara");
    public static final LanguageType bam = LanguageType.addNonUnique(bm, "bam", "Bamanankan");

    public static final LanguageType bn = new LanguageType("bn", "Bengali", "Bengali");
    public static final LanguageType ben = LanguageType.addNonUnique(bn, "ben", "Bangla");



    // Tibetan ------------
    public static final LanguageType bo = new LanguageType("bo", "bod skad", "Tibetan");
    public static final LanguageType bo2 = LanguageType.addNonUniqueName(bo, "Central Tibetan");
    public static final LanguageType bod = LanguageType.addNonUnique(bo, "bod", "Standard Tibetan");
    public static final LanguageType xct = LanguageType.addNonUnique(bo, "xct", "Classical Tibetan");

    public static final LanguageType khg = new LanguageType("khg", "Khams skad", "Khams Tibetan");

    public static final LanguageType otb = new LanguageType("otb", "Old Tibetan", "Old Tibetan");
    
    public static final LanguageType adx = new LanguageType("adx", "Amdo Tibetan", "Amdo Tibetan");
    // ------------ eo Tibetan



    public static final LanguageType bs = new LanguageType("bs", "Bosanski", "Bosnian");
    public static final LanguageType bos = LanguageType.addNonUniqueCode(bs, "bos");

    public static final LanguageType br = new LanguageType("br", "Brezhoneg", "Breton");
    public static final LanguageType bre = LanguageType.addNonUniqueCode(br, "bre");
    public static final LanguageType obt = new LanguageType("obt", "Old Breton", "Old Breton");
    public static final LanguageType xbm = new LanguageType("xbm", "Middle Breton", "Middle Breton");

    public static final LanguageType bra = new LanguageType("bra", "Braj", "Braj");
    public static final LanguageType bra2 = LanguageType.addNonUniqueName(bra, "Braj Bhasha");

    public static final LanguageType bsh = new LanguageType("bsh", "Kamkata-viri", "Kamkata-viri");
    public static final LanguageType xvi = LanguageType.addNonUnique(bsh, "xvi", "Kamviri");

    public static final LanguageType btk = new LanguageType("btk", "Batak", "Batak");
    public static final LanguageType bya = LanguageType.addNonUniqueCode(btk, "bya");

    public static final LanguageType bua = new LanguageType("bua", "Buryat", "Buryat");
    public static final LanguageType bxr = LanguageType.addNonUnique(bua, "bxr", "Russia Buriat");
    public static final LanguageType bxu = LanguageType.addNonUnique(bua, "bxu", "China Buriat");
    public static final LanguageType bxm = LanguageType.addNonUnique(bua, "bxm", "Mongolia Buriat");

    public static final LanguageType bug = new LanguageType("bug", "Basa Ugi", "Buginese");
    public static final LanguageType bug2 = LanguageType.addNonUniqueName(bug, "Bugi");

    public static final LanguageType ca = new LanguageType("ca", "Català", "Catalan");
    public static final LanguageType cat = LanguageType.addNonUnique(ca, "cat", "Valencian");

    public static final LanguageType caa = new LanguageType("caa", "Ch'orti'", "Ch'orti'");
    public static final LanguageType caa2 = LanguageType.addNonUniqueName(caa, "Chorti");

    public static final LanguageType car = new LanguageType("car", "Carib", "Carib");
    public static final LanguageType crb = LanguageType.addNonUnique(car, "crb", "Galibi Carib");

    public static final LanguageType cbk = new LanguageType("cbk", "Chavacano", "Chavacano");
    public static final LanguageType cbk_zam = LanguageType.addNonUnique(cbk, "cbk-zam", "Zamboanga Chavacano");
    
    public static final LanguageType ce = new LanguageType("ce", "Нохчийн мотт", "Chechen");
    public static final LanguageType che = LanguageType.addNonUniqueCode(ce, "che");

    public static final LanguageType cjm = new LanguageType("cjm", "Eastern Cham", "Eastern Cham");
    public static final LanguageType cjm2 = LanguageType.addNonUniqueName(cjm, "Cham");
    public static final LanguageType cja = LanguageType.addNonUnique(cjm, "cja", "Western Cham");

    public static final LanguageType ch = new LanguageType("ch", "Chamoru", "Chamorro");
    public static final LanguageType cha = LanguageType.addNonUniqueCode(ch, "cha");

    public static final LanguageType chh = new LanguageType("chh", "Chinook", "Chinook"); 
    public static final LanguageType chh2 = LanguageType.addNonUniqueName(chh, "Lower Chinook");
    public static final LanguageType wac = LanguageType.addNonUnique(chh, "wac", "Wasco-Wishram");
    public static final LanguageType wac2 = LanguageType.addNonUniqueName(chh, "Upper Chinook");

    public static final LanguageType chk = new LanguageType("chk", "Chuukese", "Chuukese");
    public static final LanguageType chk2 = LanguageType.addNonUniqueName(chk, "Trukese");

    public static final LanguageType chp = new LanguageType("chp", "Chipewyan", "Chipewyan");
    public static final LanguageType chp2 = LanguageType.addNonUniqueName(chp, "Dene Suline");



    // Chumashan ------------
    public static final LanguageType chumas = new LanguageType("Chumashan", "Chumashan", "Chumashan");

    public static final LanguageType inz = LanguageType.addNonUnique(chumas, "inz", "Ineseño");
    public static final LanguageType boi = LanguageType.addNonUnique(chumas, "boi", "Barbareño");
    public static final LanguageType crz = LanguageType.addNonUnique(chumas, "crz", "Cruzeño");
    public static final LanguageType obi = LanguageType.addNonUnique(chumas, "obi", "Obispeño");
    public static final LanguageType puy = LanguageType.addNonUnique(chumas, "puy", "Purisimeño");
    public static final LanguageType veo = LanguageType.addNonUnique(chumas, "veo", "Ventureño");
    // ------------ eo Chumashan



    public static final LanguageType co = new LanguageType("co", "Corsu", "Corsican");
    public static final LanguageType cos = LanguageType.addNonUniqueCode(co, "cos");

    

    // Cree ------------
    public static final LanguageType cr = new LanguageType("cr", "Cree", "Cree");
    public static final LanguageType cre = LanguageType.addNonUniqueCode(cr, "cre");

    public static final LanguageType atj = LanguageType.addNonUnique(cr, "atj", "Atikamekw");
    public static final LanguageType crj = LanguageType.addNonUnique(cr, "crj", "Southern East Cree");
    public static final LanguageType crk = new LanguageType("crk", "Plains Cree", "Plains Cree");
    public static final LanguageType crl = LanguageType.addNonUnique(cr, "crl", "Northern East Cree");
    public static final LanguageType crm = LanguageType.addNonUnique(cr, "crm", "Moose Cree");
    public static final LanguageType csw = LanguageType.addNonUnique(cr, "csw", "Swampy Cree");
    public static final LanguageType cwd = LanguageType.addNonUnique(cr, "cwd", "Woods Cree");

    public static final LanguageType moe = new LanguageType("moe", "Montagnais", "Innu-aimun");
    public static final LanguageType moe2 = LanguageType.addNonUniqueName(moe, "Montagnais");
    
    public static final LanguageType nsk = LanguageType.addNonUnique(cr, "nsk", "Naskapi");
    // ------------ eo Cree



    public static final LanguageType crh = new LanguageType("crh", "Qırım", "Crimean Tatar");
    public static final LanguageType crh2 = LanguageType.addNonUniqueName(crh, "Crimean");
    public static final LanguageType crh3 = LanguageType.addNonUniqueName(crh, "Crimean Turkish");
    public static final LanguageType uum = LanguageType.addNonUnique(crh, "uum", "Urum");
    
    public static final LanguageType cs = new LanguageType("cs", "Čeština", "Czech");
    public static final LanguageType ces = LanguageType.addNonUniqueCode(cs, "ces");
    public static final LanguageType cze = LanguageType.addNonUniqueCode(cs, "cze");

    public static final LanguageType csb = new LanguageType("csb", "KaszГ«bsczi", "Cassubian");
    public static final LanguageType csb2 = LanguageType.addNonUniqueName(csb, "Kashubian");
    
    public static final LanguageType cu = new LanguageType("cu", "Old Church Slavonic", "Old Church Slavonic");
    public static final LanguageType chu = LanguageType.addNonUniqueCode(cu, "chu");
    public static final LanguageType chu_cyr = LanguageType.addNonUnique(cu, "chu.cyr", "Old Church Slavonic (Cyrillic)");// in Russian Wiktionary
    public static final LanguageType chu_glag = LanguageType.addNonUnique(cu, "chu.glag", "Old Church Slavonic (Glagolitic)");// in Russian Wiktionary
    public static final LanguageType chu_ru = LanguageType.addNonUnique(cu, "chu-ru", "Church Slavonic");// Russian Wiktionary

    public static final LanguageType cuh = new LanguageType("cuh", "Chuka", "Chuka");
    public static final LanguageType cuh2 = LanguageType.addNonUniqueName(cuh, "Gichuka");

    public static final LanguageType cuk = new LanguageType("cuk", "Kuna", "Kuna");
    public static final LanguageType kvn = LanguageType.addNonUniqueCode(cuk, "kvn");

    public static final LanguageType cv = new LanguageType("cv", "Čăvašla", "Chuvash");
    public static final LanguageType chv = LanguageType.addNonUniqueCode(cv, "chv");

    public static final LanguageType da = new LanguageType("da", "Dansk", "Danish");
    public static final LanguageType dan = LanguageType.addNonUniqueCode(da, "dan");
    public static final LanguageType jut = LanguageType.addNonUnique(da, "jut", "Jutlandic");
    public static final LanguageType jut2 = LanguageType.addNonUniqueName(da, "Jutish");
    public static final LanguageType rmd = LanguageType.addNonUnique(da, "rmd", "Traveller Danish");

    public static final LanguageType dar = new LanguageType("dar", "Dargwa", "Dargwa");
    public static final LanguageType dar2 = LanguageType.addNonUniqueName(dar, "Dargin");

    

    // German ------------
    public static final LanguageType de = new LanguageType("de", "Deutsch", "German");
    public static final LanguageType de2 = LanguageType.addNonUniqueName(de, "Ripuarian");
    public static final LanguageType deu = LanguageType.addNonUnique(de, "deu", "New High German");
    public static final LanguageType pfl = LanguageType.addNonUnique(de, "pfl", "Palatinate German");

    public static final LanguageType bar = new LanguageType("bar", "Boarisch", "Bavarian");
    public static final LanguageType bar2 = LanguageType.addNonUniqueName(bar, "Austro-Bavarian");

    public static final LanguageType gmh = new LanguageType("gmh", "Middle High German", "Middle High German");
    public static final LanguageType gml = new LanguageType("gml", "Middle Low German", "Middle Low German");
    public static final LanguageType goh = new LanguageType("goh", "Old High German", "Old High German");
    public static final LanguageType pdc = new LanguageType("pdc", "Deitsch", "Pennsylvania German");
    public static final LanguageType ksh = new LanguageType("ksh", "Kölsch", "Kölsch");
    
    public static final LanguageType gsw = new LanguageType("gsw", "Schwyzerdütsch", "Swiss German");
    public static final LanguageType gsw2 = LanguageType.addNonUniqueName(gsw, "Alemannic German");
    public static final LanguageType gsw3 = LanguageType.addNonUniqueName(gsw, "Alemannisch");
    public static final LanguageType gsw4 = LanguageType.addNonUniqueName(gsw, "Alemannisch (Swiss German)");

    public static final LanguageType lb = new LanguageType("lb", "Lëtzebuergesch", "Luxembourgish");
    public static final LanguageType ltz = LanguageType.addNonUniqueCode(lb, "ltz");

    public static final LanguageType nds = new LanguageType("nds", "Plattdüütsch", "Low Saxon");
    public static final LanguageType nds2 = LanguageType.addNonUniqueName(nds, "Low German");
    public static final LanguageType nds3 = LanguageType.addNonUniqueName(nds, "Modern Low German");
    public static final LanguageType pdt = LanguageType.addNonUnique(nds, "pdt", "Plautdietsch");

    public static final LanguageType osx = new LanguageType("osx", "Old Saxon", "Old Saxon");

    public static final LanguageType nds_nl = new LanguageType("nds-nl", "Nedersaksisch", "Dutch Low Saxon");

    public static final LanguageType uln = new LanguageType("uln", "Unserdeutsch", "Unserdeutsch");
    
    public static final LanguageType gct = LanguageType.addNonUnique(de, "gct", "Alemán Coloniero");
    public static final LanguageType gos = LanguageType.addNonUnique(de, "gos", "Gronings");
    public static final LanguageType cim = LanguageType.addNonUnique(de, "cim", "Cimbrian");
    public static final LanguageType geh = LanguageType.addNonUnique(de, "geh", "Hutterite German");
    public static final LanguageType sli = LanguageType.addNonUnique(de, "sli", "Lower Silesian");
    public static final LanguageType vmf = LanguageType.addNonUnique(de, "vmf", "Main-Franconian");
    public static final LanguageType mhn = LanguageType.addNonUnique(de, "mhn", "Mócheno");
    public static final LanguageType swg = LanguageType.addNonUnique(de, "swg", "Swabian German");
    public static final LanguageType swg2 = LanguageType.addNonUniqueName(de, "Swabian");
    public static final LanguageType sxu = LanguageType.addNonUnique(de, "sxu", "Upper Saxon");
    public static final LanguageType wae = LanguageType.addNonUnique(de, "wae", "Walser German");
    public static final LanguageType wep = LanguageType.addNonUnique(de, "wep", "Westphalian");
    // ------------ eo German



    // Delaware ------------
    public static final LanguageType del = new LanguageType("del", "Delaware", "Delaware");
    public static final LanguageType del2 = LanguageType.addNonUniqueName(del, "Lenape");

    public static final LanguageType dep = LanguageType.addNonUnique(del, "dep", "Pidgin Delaware");
    public static final LanguageType unm = LanguageType.addNonUnique(del, "unm", "Unami");
    public static final LanguageType umu = LanguageType.addNonUnique(del, "umu", "Munsee");
    // ------------ eo Delaware



    public static final LanguageType den = new LanguageType("den", "Slavey", "Slavey");
    public static final LanguageType scs = LanguageType.addNonUnique(den, "scs", "North Slavey");
    public static final LanguageType xsl = LanguageType.addNonUnique(den, "xsl", "South Slavey");



    // Dagaare ------------
    public static final LanguageType dgi = new LanguageType("dgi", "Northern Dagara", "Northern Dagara");
    public static final LanguageType dga = LanguageType.addNonUnique(dgi, "dga", "Southern Dagaare");
    public static final LanguageType dgd = LanguageType.addNonUnique(dgi, "dgd", "Dagaari Dioula");
    // ------------ eo Dagaare



    public static final LanguageType diu = new LanguageType("diu", "Gciriku", "Gciriku");
    public static final LanguageType diu2 = LanguageType.addNonUniqueName(diu, "Dciriku");
    public static final LanguageType diu3 = LanguageType.addNonUniqueName(diu, "Diriku");
    public static final LanguageType diu4 = LanguageType.addNonUniqueName(diu, "Diudish");
    
    public static final LanguageType doi = new LanguageType("doi", "Dogri", "Dogri");
    public static final LanguageType dgo = LanguageType.addNonUnique(doi, "dgo", "Hindi Dogri");
    public static final LanguageType xnr = LanguageType.addNonUnique(doi, "xnr", "Kangri");

    public static final LanguageType dsb = new LanguageType("dsb", "Dolnoserbski", "Lower Sorbian");
    public static final LanguageType dsb2 = LanguageType.addNonUniqueName(dsb, "Lower Lusatian");
    public static final LanguageType dsb3 = LanguageType.addNonUniqueName(dsb, "Lower Wendish");

    public static final LanguageType dtp = new LanguageType("dtp", "Dusun Bundu-liwan", "Dusun");
    public static final LanguageType dtp2 = LanguageType.addNonUniqueName(dtp, "Central Dusun");

    public static final LanguageType duj = new LanguageType("duj", "Dhuwal", "Dhuwal");
    public static final LanguageType duj2 = LanguageType.addNonUniqueName(duj, "Datiwuy");

    public static final LanguageType dv = new LanguageType("dv", "Dhivehi", "Dhivehi");
    public static final LanguageType div = LanguageType.addNonUniqueCode(dv, "div");

    public static final LanguageType dyu = new LanguageType("dyu", "Dioula", "Dioula");
    public static final LanguageType dyu2 = LanguageType.addNonUniqueName(dyu, "Dyula");
    public static final LanguageType dyu3 = LanguageType.addNonUniqueName(dyu, "Diula");
    public static final LanguageType dyu4 = LanguageType.addNonUniqueName(dyu, "Jula");

    public static final LanguageType dz = new LanguageType("dz", "Jong-kă", "Dzongkha");
    public static final LanguageType dzo = LanguageType.addNonUnique(dz, "dzo", "Bhutanese");

    public static final LanguageType ewe = new LanguageType("ewe", "Ewe", "Ewe");
    public static final LanguageType ee = LanguageType.addNonUniqueCode(ewe, "ee");

    public static final LanguageType efi = new LanguageType("efi", "Efik", "Efik");
    public static final LanguageType efi2 = LanguageType.addNonUniqueName(efi, "Ibibio-Efik");
    public static final LanguageType ibb = LanguageType.addNonUnique(efi, "ibb", "Ibibio");
    public static final LanguageType anw = LanguageType.addNonUnique(efi, "anw", "Anaang");
    public static final LanguageType ukq = LanguageType.addNonUnique(efi, "ukq", "Ukwa");

    

    // Greek ------------
    public static final LanguageType el = new LanguageType("el", "ελληνικά", "Greek");
    public static final LanguageType ell = LanguageType.addNonUnique(el, "ell", "Modern Greek");
    public static final LanguageType el2 = LanguageType.addNonUniqueName(el, "Neo-Hellenic");
    public static final LanguageType el_dhi = LanguageType.addNonUniqueCode(el, "el.dhi");// ruwikt Греческий демот.
    public static final LanguageType el_kat = LanguageType.addNonUniqueCode(el, "el.kat");// ruwikt Греческий кафар.

    public static final LanguageType gkm = LanguageType.addNonUnique(el, "gkm", "Medieval Greek");
    public static final LanguageType gkm2 = LanguageType.addNonUniqueName(el, "Byzantine Greek");// enwikt

    public static final LanguageType cpg = LanguageType.addNonUnique(el, "cpg", "Cappadocian Greek");
    public static final LanguageType pnt = LanguageType.addNonUnique(el, "pnt", "Pontic Greek");
    public static final LanguageType tsd = LanguageType.addNonUnique(el, "tsd", "Tsakonian");
    
    public static final LanguageType gmy = new LanguageType("gmy", "Mycenaean Greek", "Mycenaean Greek");
    public static final LanguageType rge = new LanguageType("rge", "Ελληνο-ρομανική", "Romano-Greek");

    public static final LanguageType grc = new LanguageType("grc", "Ancient Greek", "Ancient Greek");
    public static final LanguageType grc_att = LanguageType.addNonUnique(grc, "grc-att", "Attic Greek");
    public static final LanguageType grc_ion = LanguageType.addNonUnique(grc, "grc-ion", "Ionic Greek");
    // ------------ eo Greek



    public static final LanguageType eml = new LanguageType("eml", "Emiliano-Romagnolo", "Emiliano-Romagnolo");
    public static final LanguageType eml_rom = LanguageType.addNonUniqueCode(eml, "eml-rom");
    public static final LanguageType egl = LanguageType.addNonUnique(eml, "egl", "Emilian");
    public static final LanguageType rgn = LanguageType.addNonUnique(eml, "rgn", "Romagnol");


    
    // English ------------ 
    public static final LanguageType en = new LanguageType("en", "English", "English");
    public static final LanguageType eng = LanguageType.addNonUnique(en, "eng", "Modern English");
    public static final LanguageType en_gb = LanguageType.addNonUnique(en, "en-gb", "British English");

    public static final LanguageType en_au = LanguageType.addNonUnique(en, "en-au", "Australian English");// Russian Wiktionary
    public static final LanguageType en_nz = LanguageType.addNonUnique(en, "en-nz", "New Zealand English");// Russian Wiktionary
    public static final LanguageType en_us = LanguageType.addNonUnique(en, "en-us", "American English");// Russian Wiktionary

    public static final LanguageType ang = new LanguageType("ang", "Anglo-Saxon", "Old English");
    public static final LanguageType oen = LanguageType.addNonUnique(ang, "oen", "Anglo-Saxon");

    public static final LanguageType simple = new LanguageType("simple", "Simple English", "Simple English");

    public static final LanguageType enm = new LanguageType("enm", "Middle English", "Middle English");
    public static final LanguageType enm2 = LanguageType.addNonUniqueName(enm, "Medieval English");
    // ------------ eo English



    public static final LanguageType eo = new LanguageType("eo", "Esperanto", "Esperanto");
    public static final LanguageType epo = LanguageType.addNonUniqueCode(eo, "epo");

    public static final LanguageType es = new LanguageType("es", "Español", "Spanish");
    public static final LanguageType spa = LanguageType.addNonUniqueCode(es, "spa");

    public static final LanguageType spq = LanguageType.addNonUnique(es, "spq", "Amazonic Spanish");
    public static final LanguageType spq2 = LanguageType.addNonUniqueName(es, "Loreto-Ucayali Spanish");



    // Yupik ------------ 
    public static final LanguageType esu = new LanguageType("esu", "Yupik", "Central Alaskan Yup'ik");
    public static final LanguageType esu2 = LanguageType.addNonUniqueName(esu, "Yup'ik");
    public static final LanguageType esu3 = LanguageType.addNonUniqueName(esu, "Yupik");
    public static final LanguageType esu4 = LanguageType.addNonUniqueName(esu, "Central Yup'ik");

    public static final LanguageType ess = new LanguageType("ess", "Юпик", "Central Siberian Yupik");
    public static final LanguageType ess2 = LanguageType.addNonUniqueName(ess, "Siberian Yupik");

    public static final LanguageType ems = new LanguageType("ems", "Sugpiaq", "Alutiiq");
    public static final LanguageType ynk = new LanguageType("ynk", "Naukan", "Naukan");
    // ------------ eo Yupik
    


    public static final LanguageType et = new LanguageType("et", "Eesti", "Estonian");
    public static final LanguageType est = LanguageType.addNonUniqueCode(et, "est");
    public static final LanguageType ekk = LanguageType.addNonUnique(et, "ekk", "Standard Estonian");

    public static final LanguageType eu = new LanguageType("eu", "Euskara", "Basque");
    public static final LanguageType eus = LanguageType.addNonUnique(eu, "eus", "Euskara");



    // Persian ------------
    public static final LanguageType fa = new LanguageType("fa", "Persian", "Persian");
    public static final LanguageType fas = LanguageType.addNonUniqueCode(fa, "fas");
    public static final LanguageType def = LanguageType.addNonUnique(fa, "def", "Dezfuli");

    public static final LanguageType jpr = new LanguageType("jpr", "Judeo-Persian", "Judeo-Persian");
    public static final LanguageType jpr2 = LanguageType.addNonUniqueName(jpr, "Dzhidi");
    public static final LanguageType jpr3 = LanguageType.addNonUniqueName(jpr, "Judæo-Persian");
    public static final LanguageType jpr4 = LanguageType.addNonUniqueName(jpr, "Jidi");

    public static final LanguageType drw = new LanguageType("drw", "Darwazi", "Darwazi");
    public static final LanguageType pal = new LanguageType("pal", "Middle Persian", "Middle Persian");
    public static final LanguageType peo = new LanguageType("peo", "Old Persian", "Old Persian");
    public static final LanguageType prs = new LanguageType("prs", "Eastern Persian", "Eastern Persian");
    // not yet in English Wiktionary:
    public static final LanguageType pes = LanguageType.addNonUnique(fa, "pes", "Western Persian");
    public static final LanguageType aiq = LanguageType.addNonUnique(fa, "aiq", "Aimaq");
    public static final LanguageType bhh = LanguageType.addNonUnique(fa, "bhh", "Bukharic");
    public static final LanguageType haz = LanguageType.addNonUnique(fa, "haz", "Hazaragi");
    public static final LanguageType phv = LanguageType.addNonUnique(fa, "phv", "Pahlavani");
    
    public static final LanguageType bqi = LanguageType.addNonUnique(fa, "bqi", "Bakthiari");
    // ------------ eo Persian
    


    public static final LanguageType fan = new LanguageType("fan", "Fang", "Fang");
    public static final LanguageType fan2 = LanguageType.addNonUniqueName(fan, "Pahouin");

    public static final LanguageType ff = new LanguageType("ff", "Fula", "Fula");
    public static final LanguageType ful = LanguageType.addNonUniqueCode(ff, "ful");

    public static final LanguageType fi = new LanguageType("fi", "Suomi", "Finnish");
    public static final LanguageType fin = LanguageType.addNonUniqueCode(fi, "fin");

    public static final LanguageType fj = new LanguageType("fj", "Na Vosa Vakaviti", "Fijian");
    public static final LanguageType fij = LanguageType.addNonUniqueCode(fj, "fij");

    public static final LanguageType fo = new LanguageType("fo", "Føroyskt", "Faroese");
    public static final LanguageType fao = LanguageType.addNonUniqueCode(fo, "fao");



    // French ------------
    public static final LanguageType fr = new LanguageType("fr", "Français", "French");
    public static final LanguageType fra = LanguageType.addNonUnique(fr, "fra", "Modern French");
    public static final LanguageType frc = LanguageType.addNonUnique(fr, "frc", "Cajun French");
    public static final LanguageType fr_ca = LanguageType.addNonUnique(fr, "fr-ca", "Canadian French");

    public static final LanguageType fr_be = LanguageType.addNonUnique(fr, "fr-be", "Belgian French");// Russian Wiktionary
    public static final LanguageType fr_ch = LanguageType.addNonUnique(fr, "fr-ch", "Swiss French");// Russian Wiktionary

    public static final LanguageType frm = new LanguageType("frm", "Middle French", "Middle French");
    public static final LanguageType fro = new LanguageType("fro", "Old French", "Old French");
    // ------------ eo French



    public static final LanguageType frk = new LanguageType("frk", "Frankish", "Frankish");
    public static final LanguageType frk2 = LanguageType.addNonUniqueCode(frk, "Old Frankish");


    
    // Frisian ------------
    public static final LanguageType frr = new LanguageType("frr", "North Frisian", "North Frisian");
    public static final LanguageType frs = new LanguageType("frs", "Eastern Frisian", "Eastern Frisian");

    public static final LanguageType fy = new LanguageType("fy", "Frysk", "West Frisian");
    public static final LanguageType fry = LanguageType.addNonUnique(fy, "fry", "Western Frisian");
    
    public static final LanguageType stq = new LanguageType("stq", "Seeltersk", "Saterland Frisian");
    public static final LanguageType ofs = new LanguageType("ofs", "Old Frisian", "Old Frisian");
    // ------------ eo Frisian



    public static final LanguageType fud = new LanguageType("fud", "Fakafutuna", "Futunan");
    public static final LanguageType fud2 = LanguageType.addNonUniqueName(fud, "East Futuna");
    public static final LanguageType fud3 = LanguageType.addNonUniqueName(fud, "East-Futunan");
    public static final LanguageType fud4 = LanguageType.addNonUniqueName(fud, "Futunian");

    public static final LanguageType ga = new LanguageType("ga", "Gaeilge", "Irish");
    public static final LanguageType gle = LanguageType.addNonUnique(ga, "gle", "Irish Gaelic");
    public static final LanguageType mga = new LanguageType("mga", "Middle Irish", "Middle Irish");
    public static final LanguageType sga = new LanguageType("sga", "Old Irish", "Old Irish");

    // Gaulish ------------
    public static final LanguageType xtg = new LanguageType("xtg", "Gaulish", "Gaulish");
    public static final LanguageType xtg2 = LanguageType.addNonUniqueName(xtg, "Transalpine Gaulish");
    
    public static final LanguageType xcg = LanguageType.addNonUnique(xtg, "xcg", "Cisalpine Gaulish");
    public static final LanguageType xlp = LanguageType.addNonUnique(xtg, "xlp", "Lepontic");
    public static final LanguageType xga = LanguageType.addNonUnique(xtg, "xga", "Galatian");
    // ------------ eo Gaulish


    public static final LanguageType gba = new LanguageType("gba", "Gbaya", "Gbaya");

    public static final LanguageType gbj = new LanguageType("gbj", "Gutob", "Gutob");
    public static final LanguageType gbj2 = LanguageType.addNonUniqueName(gbj, "Bodo Gadaba");

    public static final LanguageType gd = new LanguageType("gd", "Gàidhlig", "Scottish Gaelic");
    public static final LanguageType gla = LanguageType.addNonUniqueCode(gd, "gla");
    public static final LanguageType gd2 = LanguageType.addNonUniqueName(gd, "Gàidhlig");
    public static final LanguageType gd3 = LanguageType.addNonUniqueName(gd, "Highland Gaelic");
    public static final LanguageType gd4 = LanguageType.addNonUniqueName(gd, "Scots Gaelic");
    public static final LanguageType gd5 = LanguageType.addNonUniqueName(gd, "Scottish");

    public static final LanguageType gl = new LanguageType("gl", "Galego", "Galician");
    public static final LanguageType glg = LanguageType.addNonUniqueCode(gl, "glg");

    public static final LanguageType gbp = new LanguageType("gbp", "Gbaya-Bossangoa", "Gbaya-Bossangoa");
    public static final LanguageType gbp2 = LanguageType.addNonUniqueName(gbp, "Bossangoa");
    public static final LanguageType gbq = LanguageType.addNonUnique(gbp, "gbq", "Gbaya-Bozoum");
    public static final LanguageType gbq2 = LanguageType.addNonUniqueName(gbp, "Bozoum");
    public static final LanguageType gbq3 = LanguageType.addNonUniqueName(gbp, "Bozom");
    public static final LanguageType sqm = LanguageType.addNonUnique(gbp, "sqm", "Suma");
    public static final LanguageType sqm2 = LanguageType.addNonUniqueName(gbp, "Souma");

    public static final LanguageType gez = new LanguageType("gez", "Ge'ez", "Ge'ez");
    public static final LanguageType gez2 = LanguageType.addNonUniqueName(gez, "Ethiopic");
    public static final LanguageType gez3 = LanguageType.addNonUniqueName(gez, "Gi'iz");



    // Guarani ------------
    public static final LanguageType gn = new LanguageType("gn", "Guaraní", "Guaraní");
    public static final LanguageType grn = LanguageType.addNonUnique(gn, "grn", "Guarani");
    public static final LanguageType gug = LanguageType.addNonUnique(gn, "gug", "Paraguayan Guaraní");
    public static final LanguageType gui = LanguageType.addNonUnique(gn, "gui", "Eastern Bolivian Guaraní");
    public static final LanguageType gun = LanguageType.addNonUnique(gn, "gun", "Mbyá Guaraní");
    public static final LanguageType gnw = LanguageType.addNonUnique(gn, "gnw", "Western Bolivian Guaraní");
    // ------------ eo Guarani



    public static final LanguageType gu = new LanguageType("gu", "Gujarati", "Gujarati");
    public static final LanguageType guj = LanguageType.addNonUniqueCode(gu, "guj");

    public static final LanguageType gud = new LanguageType("gud", "Yocoboué Dida", "Yocoboué Dida");
    public static final LanguageType dic = LanguageType.addNonUnique(gud, "dic", "Lakota Dida");

    public static final LanguageType gut = new LanguageType("gut", "Maléku", "Maléku");
    public static final LanguageType gut2 = LanguageType.addNonUniqueName(gut, "Maléku Jaíka");

    public static final LanguageType gv = new LanguageType("gv", "Gaelg", "Manx");
    public static final LanguageType glv = LanguageType.addNonUnique(gv, "glv", "Manx Gaelic");

    public static final LanguageType ha = new LanguageType("ha", "Hausa", "Hausa");
    public static final LanguageType hau = LanguageType.addNonUniqueCode(ha, "hau");
    public static final LanguageType ha_lat = LanguageType.addNonUniqueCode(ha, "ha.lat");// Russian Wiktionary
    public static final LanguageType ha_arab = LanguageType.addNonUniqueCode(ha, "ha.arab");// Russian Wiktionary

    public static final LanguageType he = new LanguageType("he", "Hebrew", "Hebrew");
    public static final LanguageType heb = LanguageType.addNonUniqueCode(he, "heb");
    public static final LanguageType hbo = LanguageType.addNonUnique(he, "hbo", "Ancient Hebrew");

    public static final LanguageType hi = new LanguageType("hi", "Hindī", "Hindi");
    public static final LanguageType hin = LanguageType.addNonUniqueCode(hi, "hin");

    public static final LanguageType hio = new LanguageType("hio", "Tsoa", "Tsoa");
    public static final LanguageType tyu = LanguageType.addNonUnique(hio, "tyu", "Kua");

    public static final LanguageType ho = new LanguageType("ho", "Hiri Motu", "Hiri Motu");
    public static final LanguageType hmo = LanguageType.addNonUniqueCode(ho, "hmo");
    
    public static final LanguageType hr = new LanguageType("hr", "Hrvatski", "Croatian");
    public static final LanguageType hrv = LanguageType.addNonUniqueCode(hr, "hrv");

    public static final LanguageType hsb = new LanguageType("hsb", "Hornjoserbsce", "Upper Sorbian");
    public static final LanguageType hsb2 = LanguageType.addNonUniqueName(hsb, "Upper Lusatian");
    public static final LanguageType hsb3 = LanguageType.addNonUniqueName(hsb, "Upper Wendish");

    public static final LanguageType hu = new LanguageType("hu", "Magyar", "Hungarian");
    public static final LanguageType hun = LanguageType.addNonUniqueCode(hu, "hun");
    public static final LanguageType ohu = LanguageType.addNonUnique(hu, "ohu", "Old Hungarian");// enwikt

    public static final LanguageType hwc = new LanguageType("hwc", "Hawaiian Pidgin", "Hawaiian Pidgin");
    public static final LanguageType hwc2 = LanguageType.addNonUniqueName(hwc, "Hawaii Creole English");
    public static final LanguageType hwc3 = LanguageType.addNonUniqueName(hwc, "Hawaii Pidgin English");
    public static final LanguageType hwc4 = LanguageType.addNonUniqueName(hwc, "HCE");


    
    // Armenian ------------
    public static final LanguageType hy = new LanguageType("hy", "Armenian", "Armenian");
    public static final LanguageType hye = LanguageType.addNonUnique(hy, "hye", "Modern Armenian");

    public static final LanguageType axm = new LanguageType("axm", "Middle Armenian", "Middle Armenian");

    public static final LanguageType xcl = new LanguageType("xcl", "Classical Armenian", "Classical Armenian");
    public static final LanguageType xcl2 = LanguageType.addNonUniqueName(xcl, "Old Armenian");
    public static final LanguageType xcl3 = LanguageType.addNonUniqueName(xcl, "Liturgical Armenian");
    // ------------ eo Armenian



    public static final LanguageType hz = new LanguageType("hz", "Otsiherero", "Herero");
    public static final LanguageType her = LanguageType.addNonUniqueCode(hz, "her");

    public static final LanguageType ibo = new LanguageType("ibo","Igbo", "Igbo");
    public static final LanguageType ig  = LanguageType.addNonUniqueCode(ibo, "ig");

    // id_ (instead of "id") because of the problems with names in SQLite
    public static final LanguageType id_ = new LanguageType("id_", "Bahasa Indonesia", "Indonesian");
    public static final LanguageType id  = LanguageType.addNonUniqueCode(id_, "id");
    public static final LanguageType ind = LanguageType.addNonUniqueCode(id_, "ind");

    public static final LanguageType ie = new LanguageType("ie", "Interlingue", "Occidental");
    public static final LanguageType ile = LanguageType.addNonUnique(ie, "ile", "Interlingue");

    public static final LanguageType iii = new LanguageType("iii", "Nuosu", "Nuosu");
    public static final LanguageType ii = LanguageType.addNonUnique(iii, "ii", "Sichuan Yi");

    public static final LanguageType ik = new LanguageType("ik", "Iñupiaq", "Inupiaq");
    public static final LanguageType ik2 = LanguageType.addNonUniqueName(ik, "Inupiatun");
    public static final LanguageType ipk = LanguageType.addNonUnique(ik, "ipk", "Inupiak");
    public static final LanguageType esi = LanguageType.addNonUnique(ik, "esi", "North Alaskan Inupiatun");
    public static final LanguageType esk = LanguageType.addNonUnique(ik, "esk", "Northwest Alaska Inupiatun");

    public static final LanguageType ilo = new LanguageType("ilo", "Ilokano", "Ilokano");
    public static final LanguageType ilo2 = LanguageType.addNonUniqueName(ilo, "Ilocano");

    public static final LanguageType ina = new LanguageType("ina", "Interlingua", "Interlingua");
    public static final LanguageType ia  = LanguageType.addNonUniqueCode(ina, "ia");

    public static final LanguageType io = new LanguageType("io", "Ido", "Ido");
    public static final LanguageType ido = LanguageType.addNonUniqueCode(io, "ido");

    public static final LanguageType is = new LanguageType("is", "Íslenska", "Icelandic");
    public static final LanguageType isl = LanguageType.addNonUniqueCode(is, "isl");

    public static final LanguageType it = new LanguageType("it", "Italiano", "Italian");
    public static final LanguageType ita = LanguageType.addNonUniqueCode(it, "ita");
    public static final LanguageType nap_cal = LanguageType.addNonUnique(it, "nap-cal", "Calabrese");
    public static final LanguageType itk = LanguageType.addNonUnique(it, "itk", "Judeo-Italian");



    // Inuktitut ------------
    public static final LanguageType iu = new LanguageType("iu", "Inuktitut", "Inuktitut");
    public static final LanguageType iku = LanguageType.addNonUniqueCode(iu, "iku");

    public static final LanguageType ike = LanguageType.addNonUnique(iu, "ike", "Eastern Canadian Inuktitut");
    public static final LanguageType ike_cans = LanguageType.addNonUniqueCode(iu, "ike-cans");
    public static final LanguageType ike_latn = LanguageType.addNonUniqueCode(iu, "ike_latn");// Latin script

    public static final LanguageType ikt = LanguageType.addNonUnique(iu, "ikt", "Western Canadian Inuktitut");
    // ------------ eo Inuktitut



    public static final LanguageType ja = new LanguageType("ja", "Japanese", "Japanese");
    public static final LanguageType jpn = LanguageType.addNonUnique(ja, "jpn", "Modern Japanese");
    public static final LanguageType ja2 = LanguageType.addNonUniqueName(ja, "Nipponese");
    public static final LanguageType ojp = LanguageType.addNonUnique(ja, "ojp", "Old Japanese");
    public static final LanguageType yoi = LanguageType.addNonUnique(ja, "yoi", "Yonaguni");



    // Jivaroan ------------
    public static final LanguageType jiv = new LanguageType("jiv", "Shuar", "Shuar");

    public static final LanguageType acu = LanguageType.addNonUnique(jiv, "acu", "Achuar-Shiwiar");
    public static final LanguageType acu2 = LanguageType.addNonUniqueName(jiv, "Achuar");
    public static final LanguageType acu3 = LanguageType.addNonUniqueName(jiv, "Shiwiar");

    public static final LanguageType agr = LanguageType.addNonUnique(jiv, "agr", "Aguaruna");
    public static final LanguageType hub = LanguageType.addNonUnique(jiv, "hub", "Huambisa");
    // ------------ eo Jivaroan



    // Judeo-Arabic ------------
    public static final LanguageType jrb = new LanguageType("jrb", "Judeo-Arabic", "Judeo-Arabic");
    public static final LanguageType yhd = LanguageType.addNonUnique(jrb, "yhd", "Judeo-Iraqi Arabic");
    public static final LanguageType aju = LanguageType.addNonUnique(jrb, "aju", "Judeo-Moroccan Arabic");
    public static final LanguageType yud = LanguageType.addNonUnique(jrb, "yud", "Judeo-Tripolitanian Arabic");
    public static final LanguageType ajt = LanguageType.addNonUnique(jrb, "ajt", "Judeo-Tunisian Arabic");
    public static final LanguageType jye = LanguageType.addNonUnique(jrb, "jye", "Judeo-Yemeni Arabic");
    // ------------ eo Judeo-Arabic



    // Javanese ------------
    public static final LanguageType jv = new LanguageType("jv", "Basa Jawa", "Javanese");
    public static final LanguageType jav = LanguageType.addNonUniqueCode(jv, "jav");

    public static final LanguageType jvn = LanguageType.addNonUnique(jv, "jvn", "Caribbean Javanese");
    public static final LanguageType jas = LanguageType.addNonUnique(jv, "jas", "New Caledonian Javanese");
    public static final LanguageType osi = LanguageType.addNonUnique(jv, "osi", "Osing language");
    public static final LanguageType tes = LanguageType.addNonUnique(jv, "tes", "Tenggerese");

    public static final LanguageType kaw = new LanguageType("kaw", "Kawi", "Kawi");
    public static final LanguageType kaw2 = LanguageType.addNonUniqueCode(kaw, "Old Javanese");

    public static final LanguageType map_bms = LanguageType.addNonUnique(jv, "map-bms", "Banyumasan");
    // ------------ eo Javanese



    public static final LanguageType ka = new LanguageType("ka", "Georgian", "Georgian");
    public static final LanguageType kat = LanguageType.addNonUniqueCode(ka, "kat");
    public static final LanguageType jge = LanguageType.addNonUnique(ka, "jge", "Judeo-Georgian");
    public static final LanguageType oge = LanguageType.addNonUnique(ka, "oge", "Old Georgian");

    public static final LanguageType kayah = new LanguageType("kayah", "Kayah", "Kayah");
    public static final LanguageType kxf = LanguageType.addNonUnique(kayah, "kxf", "Karen, Manumanaw");
    public static final LanguageType kvu = LanguageType.addNonUnique(kayah, "kvu", "Karen, Yinbaw");
    public static final LanguageType kvy = LanguageType.addNonUnique(kayah, "kvy", "Karen, Yintale");
    public static final LanguageType eky = LanguageType.addNonUnique(kayah, "eky", "Eastern Kayah");
    public static final LanguageType kyu = LanguageType.addNonUnique(kayah, "kyu", "Western Kayah");
    
    public static final LanguageType kal = new LanguageType("kal", "Kalaallisut", "Greenlandic");
    public static final LanguageType kl = LanguageType.addNonUnique(kal, "kl", "Kalaallisut");// add lang name? : Inuktitut



    // Kazakh ------------
    public static final LanguageType kaz    = new LanguageType("kaz", "Қазақ тілі", "Kazakh");
    public static final LanguageType kk     = LanguageType.addNonUniqueCode(kaz, "kk");

    public static final LanguageType kk_arab = LanguageType.addNonUniqueCode(kaz, "kk-arab");// Kazakh Arabic
    public static final LanguageType kk_cyrl = LanguageType.addNonUniqueCode(kaz, "kk-cyrl");// Kazakh Cyrillic
    public static final LanguageType kk_latn = LanguageType.addNonUniqueCode(kaz, "kk-latn");// Kazakh Latin

    public static final LanguageType kk_cn   = LanguageType.addNonUniqueCode(kaz, "kk-cn");// Kazakh (China)
    public static final LanguageType kk_kz   = LanguageType.addNonUniqueCode(kaz, "kk-kz");// Kazakh (Kazakhstan)
    public static final LanguageType kk_tr   = LanguageType.addNonUniqueCode(kaz, "kk-tr");// Kazakh (Turkey)

                                     // Russian Wiktionary
    public static final LanguageType kk_arab2 = LanguageType.addNonUniqueCode(kaz, "kk.arab");// Kazakh Arabic
    public static final LanguageType kk_cyr = LanguageType.addNonUniqueCode(kaz, "kk.cyr");// Kazakh Cyrillic
    public static final LanguageType kk_lat = LanguageType.addNonUniqueCode(kaz, "kk.lat");// Kazakh Latin
    // ------------ eo Kazakh



    public static final LanguageType kea = new LanguageType("kea", "Kabuverdianu", "Kabuverdianu");
    public static final LanguageType kea2 = LanguageType.addNonUniqueName(kea, "Cape Verdean Creole");

    // Konda: {{knd}}, {{kfc}}
    public static final LanguageType kfc = new LanguageType("kfc", "Konda-Dora", "Konda-Dora");
    public static final LanguageType knd = new LanguageType("knd", "Konda", "Konda");

    public static final LanguageType khv = new LanguageType("khv", "Kedaes hikwa", "Khwarshi");
    public static final LanguageType khv2 = LanguageType.addNonUniqueName(khv, "Khvarshi");
    
    public static final LanguageType kg = new LanguageType("kg", "Kongo", "Kongo");
    public static final LanguageType kon = LanguageType.addNonUniqueCode(kg, "kon");

    public static final LanguageType khb = new LanguageType("khb", "Kwam Tai Lue", "Tai Lü");
    public static final LanguageType khb2 = LanguageType.addNonUniqueName(khb, "Lü");
    public static final LanguageType khb3 = LanguageType.addNonUniqueName(khb, "Tai Lue");

    public static final LanguageType ki = new LanguageType("ki", "Gĩkũyũ", "Gikuyu");
    public static final LanguageType kik = LanguageType.addNonUnique(ki, "kik", "Kikuyu");

    public static final LanguageType kio = new LanguageType("kio", "Kiowa", "Kiowa");
    public static final LanguageType tew = LanguageType.addNonUnique(kio, "tew", "Tewa");



    // Ovambo ------------
    public static final LanguageType kj = new LanguageType("kj", "Ovambo", "Ovambo");
    public static final LanguageType kua = LanguageType.addNonUnique(kj, "kua", "Oshiwambo");
    public static final LanguageType kj2 = LanguageType.addNonUniqueName(kj, "Kwanyama");
    public static final LanguageType kj3 = LanguageType.addNonUniqueName(kj, "Oshikwanyama");

    public static final LanguageType ng = LanguageType.addNonUnique(kj, "ng", "Ndonga");
    public static final LanguageType ndo = LanguageType.addNonUniqueCode(kj, "ndo");
    public static final LanguageType kwm = LanguageType.addNonUnique(kj, "kwm", "Kwambi");
    public static final LanguageType lnb = LanguageType.addNonUnique(kj, "lnb", "Mbalanhu");
    public static final LanguageType nne = LanguageType.addNonUnique(kj, "nne", "Ngandyera");
    // ------------ eo Ovambo



    public static final LanguageType kjb = new LanguageType("kjb", "Q'anjob'al", "Q'anjob'al");
    public static final LanguageType kjb2 = LanguageType.addNonUniqueName(kjb, "Kanjobal");

    public static final LanguageType km = new LanguageType("km", "Khmer", "Khmer");
    public static final LanguageType khm = LanguageType.addNonUnique(km, "khm", "Central Khmer");
    public static final LanguageType kxm = LanguageType.addNonUnique(km, "kxm", "Northern Khmer");
    public static final LanguageType km2 = LanguageType.addNonUniqueName(km, "Khmer Krom");
    public static final LanguageType km3 = LanguageType.addNonUniqueName(km, "Southern Khmer");

    public static final LanguageType kky = new LanguageType("kky", "Guugu Yimithirr", "Guugu Yimithirr");
    public static final LanguageType kky2 = LanguageType.addNonUniqueName(kky, "Guugu Yimidhirr");

    public static final LanguageType kn = new LanguageType("kn", "Kannada", "Kannada");
    public static final LanguageType kan = LanguageType.addNonUniqueCode(kn, "kan");
    public static final LanguageType kfi = LanguageType.addNonUnique(kn, "kfi", "Kannada Kurumba");

    public static final LanguageType knw = new LanguageType("knw", "!Kung", "!Kung");
    public static final LanguageType knw2 = LanguageType.addNonUniqueName(knw, "Kung-Ekoka");
    public static final LanguageType oun = LanguageType.addNonUnique(knw, "oun", "!O!ung");
    public static final LanguageType mwj = LanguageType.addNonUnique(knw, "mwj", "Maligo");
    public static final LanguageType khi_kun = LanguageType.addNonUniqueCode(knw, "khi-kun");// enwikt

    public static final LanguageType ko = new LanguageType("ko", "Korean", "Korean");
    public static final LanguageType kor = LanguageType.addNonUnique(ko, "kor", "Modern Korean");
    public static final LanguageType oko = LanguageType.addNonUnique(ko, "oko", "Old Korean");
    public static final LanguageType okm = LanguageType.addNonUnique(ko, "okm", "Middle Korean");

    public static final LanguageType kok = new LanguageType("kok", "Konkani", "Konkani");
    public static final LanguageType gom = LanguageType.addNonUniqueCode(kok, "gom");

    public static final LanguageType kv = new LanguageType("kv", "Komi", "Komi");
    public static final LanguageType kom = LanguageType.addNonUniqueCode(kv, "kom");
    public static final LanguageType koi = LanguageType.addNonUnique(kv, "koi", "Komi-Permyak");
    public static final LanguageType kpv = LanguageType.addNonUnique(kv, "kpv", "Komi-Zyrian");// cyrillic is common script but also written in latin script
    
    public static final LanguageType kpe = new LanguageType("kpe", "Kpelle", "Kpelle");
    public static final LanguageType gkp = LanguageType.addNonUnique(kpe, "gkp", "Guinea Kpelle");
    public static final LanguageType xpe = LanguageType.addNonUnique(kpe, "xpe", "Liberia Kpelle");

    public static final LanguageType kr = new LanguageType("kr", "Kanuri", "Kanuri");
    public static final LanguageType kau = LanguageType.addNonUniqueCode(kr, "kau");
    public static final LanguageType knc = LanguageType.addNonUnique(kr, "knc", "Central Kanuri");
    public static final LanguageType kby = LanguageType.addNonUnique(kr, "kby", "Manga Kanuri");
    public static final LanguageType krt = LanguageType.addNonUnique(kr, "krt", "Tumari Kanuri");
    public static final LanguageType bms = LanguageType.addNonUnique(kr, "bms", "Bilma Kanuri");
    public static final LanguageType kbl = LanguageType.addNonUnique(kr, "kbl", "Kanembu");

    public static final LanguageType ks = new LanguageType("ks", "Kashmiri", "Kashmiri");
    public static final LanguageType kas = LanguageType.addNonUniqueCode(ks, "kas");

    public static final LanguageType ksi = new LanguageType("ksi", "I'saka", "I'saka");
    public static final LanguageType ksi2 = LanguageType.addNonUniqueName(ksi, "Isaka");
    public static final LanguageType ksi3 = LanguageType.addNonUniqueName(ksi, "Krisa");
    
    
    
    // Kurdish ------------
    public static final LanguageType ku = new LanguageType("ku", "Kurdish", "Kurdish");
    public static final LanguageType kur = LanguageType.addNonUniqueCode(ku, "kur");
    
    public static final LanguageType ckb = LanguageType.addNonUnique(ku, "ckb", "Soranî");
    public static final LanguageType ckb2 = LanguageType.addNonUniqueName(ku, "Central Kurdish");

    public static final LanguageType sdh = LanguageType.addNonUnique(ku, "sdh", "Southern Kurdish");

    public static final LanguageType kmr = LanguageType.addNonUnique(ku, "kmr", "Kurmanji");
    public static final LanguageType kmr2 = LanguageType.addNonUniqueName(ku, "Northern Kurdish");
    
    public static final LanguageType ku_latn = LanguageType.addNonUniqueCode(ku, "ku-latn");// "Northern Kurdish Latin script"
    public static final LanguageType ku_arab = LanguageType.addNonUniqueCode(ku, "ku-arab");// "Northern Kurdish Arabic script"

                                     // Russian Wiktionary
    public static final LanguageType ku_cyr = LanguageType.addNonUniqueCode(ku, "ku.cyr");// Kurdish Cyrillic
    public static final LanguageType ku_lat2 = LanguageType.addNonUniqueCode(ku, "ku.lat");// Kurdish Latin script
    public static final LanguageType ku_arab2 = LanguageType.addNonUniqueCode(ku, "ku.arab");// Northern Kurdish Arabic script
    // ------------ eo Kurdish


    
    public static final LanguageType kw = new LanguageType("kw", "Kernewek", "Cornish");
    public static final LanguageType cor = LanguageType.addNonUniqueCode(kw, "cor");
    public static final LanguageType cnx = LanguageType.addNonUnique(kw, "cnx", "Middle Cornish");
    public static final LanguageType oco = LanguageType.addNonUnique(kw, "oco", "Old Cornish");

    public static final LanguageType ky = new LanguageType("ky", "Kyrgyz", "Kyrgyz");
    public static final LanguageType kir = LanguageType.addNonUnique(ky, "kir", "Kirghiz");
    public static final LanguageType ky2 = LanguageType.addNonUniqueName(ky, "Kirgiz");

    public static final LanguageType la = new LanguageType("la", "Latina", "Latin");
    public static final LanguageType lat = LanguageType.addNonUniqueCode(la, "lat");

    public static final LanguageType lad = new LanguageType("lad", "Judaeo-Spanish", "Judaeo-Spanish");
    public static final LanguageType lad2 = LanguageType.addNonUniqueName(lad, "Ladino");
    public static final LanguageType lad3 = LanguageType.addNonUniqueName(lad, "Judæo-Spanish");
    public static final LanguageType lad4 = LanguageType.addNonUniqueName(lad, "Judeo-Spanish");

    public static final LanguageType lag = new LanguageType("lag", "Rangi", "Rangi");
    public static final LanguageType lag2 = LanguageType.addNonUniqueName(lag, "Langi");

    public static final LanguageType lez = new LanguageType("lez", "Lezgian", "Lezgian");
    public static final LanguageType lez2 = LanguageType.addNonUniqueName(lez, "Lezgi");

    public static final LanguageType lg = new LanguageType("lg", "Luganda", "Luganda");
    public static final LanguageType lug = LanguageType.addNonUnique(lg, "lug", "Ganda");

    public static final LanguageType li = new LanguageType("li", "Limburgs", "Limburgish");
    public static final LanguageType lim = LanguageType.addNonUnique(li, "lim", "Limburgian");
    public static final LanguageType li2 = LanguageType.addNonUniqueName(li, "Limburgic");
    public static final LanguageType li3 = LanguageType.addNonUniqueName(li, "Limburgan");

    public static final LanguageType lif = new LanguageType("lif", "Limbu", "Limbu");
    public static final LanguageType ncd = LanguageType.addNonUnique(lif, "ncd", "Nachering");

    public static final LanguageType ln = new LanguageType("ln", "Lingala", "Lingala");
    public static final LanguageType lin = LanguageType.addNonUniqueCode(ln, "lin");

    public static final LanguageType lo = new LanguageType("lo", "Laotian", "Lao");
    public static final LanguageType lao = LanguageType.addNonUnique(lo, "lao", "Laotian");
    public static final LanguageType pht = LanguageType.addNonUnique(lo, "pht", "Phu Thai");

    public static final LanguageType loc = new LanguageType("loc", "Onhan", "Onhan");
    public static final LanguageType loc2 = LanguageType.addNonUniqueName(loc, "Inonhan");

    public static final LanguageType lt = new LanguageType("lt", "Lietuvių kalba", "Lithuanian");
    public static final LanguageType lit = LanguageType.addNonUniqueCode(lt, "lit");
    public static final LanguageType sgs = LanguageType.addNonUnique(lt, "sgs", "Samogitian");
    public static final LanguageType bat_smg = LanguageType.addNonUniqueCode(lt, "bat-smg");
    public static final LanguageType lt2 = LanguageType.addNonUniqueName(lt, "Old Lithuanian");

    public static final LanguageType ltg = new LanguageType("ltg", "Latgalian", "Latgalian");
    public static final LanguageType bat_ltg = LanguageType.addNonUniqueCode( ltg, "bat-ltg");

    public static final LanguageType lua = new LanguageType("lua", "Tshiluba", "Luba-Kasai");
    public static final LanguageType lua2 = LanguageType.addNonUniqueName(lua, "Tshiluba");

    public static final LanguageType luo = new LanguageType("luo", "Dholuo", "Dholuo");
    public static final LanguageType luo2 = LanguageType.addNonUniqueName(luo, "Luo");

    
    
    // Luhya ------------
    public static final LanguageType luy = new LanguageType("luy", "Luhya", "Luhya");
    
    public static final LanguageType lsm = LanguageType.addNonUnique(luy, "lsm", "Saamia");
    public static final LanguageType lsm2 = LanguageType.addNonUniqueName(luy, "Lusamia");

    public static final LanguageType bxk = LanguageType.addNonUnique(luy, "bxk", "Bukusu");
    public static final LanguageType bxk2 = LanguageType.addNonUniqueName(luy, "Lubukusu");

    public static final LanguageType ida = LanguageType.addNonUnique(luy, "ida", "Luidakho-Luisukha-Lutirichi");
    public static final LanguageType ida2 = LanguageType.addNonUniqueName(luy, "Luidakho");
    public static final LanguageType ida3 = LanguageType.addNonUniqueName(luy, "Luisukha");
    public static final LanguageType ida4 = LanguageType.addNonUniqueName(luy, "Lutirichi");

    public static final LanguageType lkb = LanguageType.addNonUnique(luy, "lkb", "Lukabaras");
    public static final LanguageType lkb2 = LanguageType.addNonUniqueName(luy, "Kabras");
    public static final LanguageType lkb3 = LanguageType.addNonUniqueName(luy, "Lukabarasi");

    public static final LanguageType lko = LanguageType.addNonUnique(luy, "lko", "Olukhayo");
    public static final LanguageType lks = LanguageType.addNonUnique(luy, "lks", "Olushisa");
    public static final LanguageType lri = LanguageType.addNonUnique(luy, "lri", "Olumarachi");
    public static final LanguageType lrm = LanguageType.addNonUnique(luy, "lrm", "Olumarama");

    public static final LanguageType lkt = new LanguageType("lkt", "Lakota", "Lakota");
    public static final LanguageType lkt2 = LanguageType.addNonUniqueName(lkt, "Lakhota");

    public static final LanguageType nle = LanguageType.addNonUnique(luy, "nle", "Nyala");
    public static final LanguageType nle2 = LanguageType.addNonUniqueName(luy, "Lunyala");

    public static final LanguageType lts = LanguageType.addNonUnique(luy, "lts", "Lutachoni");
    public static final LanguageType lto = LanguageType.addNonUnique(luy, "lto", "Olutsotso");
    public static final LanguageType lwg = LanguageType.addNonUnique(luy, "lwg", "Oluwanga");
    public static final LanguageType nyd = LanguageType.addNonUnique(luy, "nyd", "Olunyole");
    public static final LanguageType nuj = LanguageType.addNonUnique(luy, "nuj", "Nyole");
    public static final LanguageType rag = LanguageType.addNonUnique(luy, "rag", "Logooli");
    // ------------ eo Luhya



    public static final LanguageType lv = new LanguageType("lv", "Latviešu", "Latvian");
    public static final LanguageType lav = LanguageType.addNonUniqueCode(lv, "lav");
    public static final LanguageType lvs = LanguageType.addNonUnique(lv, "lvs", "Standard Latvian");

    public static final LanguageType mak = new LanguageType("mak", "Makassarese", "Makassarese");
    public static final LanguageType mak2 = LanguageType.addNonUniqueName(mak, "Makasar");

    

    // Mandingo ------------
    public static final LanguageType man = new LanguageType("man", "Mandingo", "Mandingo");
    public static final LanguageType man_arab = LanguageType.addNonUniqueCode(man, "man.arab");// Russian Wiktionary
    public static final LanguageType man_lat = LanguageType.addNonUniqueCode(man, "man.lat");// Russian Wiktionary

    public static final LanguageType mnk = new LanguageType("mnk", "Mandinka", "Mandinka");
    // ------------ eo Mandingo
    


    // Mara: {{mec}}, {{mrh}}; mrh → Mara Chin (ISO name)
    public static final LanguageType mec = new LanguageType("mec", "Mara", "Mara");
    public static final LanguageType mrh = new LanguageType("mrh", "Mara Chin", "Mara Chin");

    public static final LanguageType mey = new LanguageType("mey", "Hassānīya", "Hassānīya");
    public static final LanguageType mey2 = LanguageType.addNonUniqueName(mey, "Hassānīya Arabic");



    // Malagasy ------------
    public static final LanguageType mg = new LanguageType("mg", "Malagasy", "Malagasy");
    public static final LanguageType mlg = LanguageType.addNonUniqueCode(mg, "mlg");

    public static final LanguageType bhr = LanguageType.addNonUnique(mg, "bhr", "Bara");
    public static final LanguageType bhr2 = LanguageType.addNonUniqueName(mg, "Bara Malagasy");
    public static final LanguageType bmm = LanguageType.addNonUnique(mg, "bmm", "Northern Betsimisaraka");
    public static final LanguageType bmm2 = LanguageType.addNonUniqueName(mg, "Northern Betsimisaraka Malagasy");
    public static final LanguageType bjq = LanguageType.addNonUnique(mg, "bjq", "Southern Betsimisaraka");
    public static final LanguageType bjq2 = LanguageType.addNonUniqueName(mg, "Southern Betsimisaraka Malagasy");
    public static final LanguageType msh = LanguageType.addNonUnique(mg, "msh", "Masikoro");
    public static final LanguageType msh2 = LanguageType.addNonUniqueName(mg, "Masikoro Malagasy");
    public static final LanguageType plt = LanguageType.addNonUnique(mg, "plt", "Plateau Malagasy");
    public static final LanguageType skg = LanguageType.addNonUnique(mg, "skg", "Sakalava");
    public static final LanguageType skg2 = LanguageType.addNonUniqueName(mg, "Sakalava Malagasy");
    public static final LanguageType tdx = LanguageType.addNonUnique(mg, "tdx", "Tandroy-Mafahaly");
    public static final LanguageType tdx2 = LanguageType.addNonUniqueName(mg, "Tandroy-Mahafaly Malagasy");
    public static final LanguageType txy = LanguageType.addNonUnique(mg, "txy", "Tanosy");
    public static final LanguageType txy2 = LanguageType.addNonUniqueName(mg, "Tanosy Malagasy");
    public static final LanguageType xmv = LanguageType.addNonUnique(mg, "xmv", "Antankarana");
    public static final LanguageType xmv2 = LanguageType.addNonUniqueName(mg, "Antankarana Malagasy");
    public static final LanguageType xmw = LanguageType.addNonUnique(mg, "xmw", "Tsimihety");
    public static final LanguageType xmw2 = LanguageType.addNonUniqueName(mg, "Tsimihety Malagasy");
    // ------------ eo Malagasy


    
    // Mixe ------------
    public static final LanguageType mixe = new LanguageType("mixe", "Mixe", "Mixe");

    public static final LanguageType mco = LanguageType.addNonUnique(mixe, "mco", "Coatlán Mixe");
    public static final LanguageType mir = LanguageType.addNonUnique(mixe, "mir", "Isthmus Mixe");
    public static final LanguageType mto = LanguageType.addNonUnique(mixe, "mto", "Totontepec Mixe");
    public static final LanguageType mxp = LanguageType.addNonUnique(mixe, "mxp", "Tlahuitoltepec Mixe");
    public static final LanguageType mxq = LanguageType.addNonUnique(mixe, "mxq", "Juquila Mixe");
    public static final LanguageType mzl = LanguageType.addNonUnique(mixe, "mzl", "Mazatlán Mixe");
    public static final LanguageType neq = LanguageType.addNonUnique(mixe, "neq", "North Central Mixe");
    public static final LanguageType pxm = LanguageType.addNonUnique(mixe, "pxm", "Quetzaltepec Mixe");
    public static final LanguageType plo = LanguageType.addNonUnique(mixe, "plo", "Oluta Popoluca");
    public static final LanguageType pos = LanguageType.addNonUnique(mixe, "pos", "Sayula Popoluca");
    // ------------ eo Mixe



    // [mjh] similar to Mwera [mwe] in the Lindi region, but not the same... mjh is the smaller one
    public static final LanguageType mwe = new LanguageType("mwe", "Mwera", "Mwera");
    public static final LanguageType mwe2 = LanguageType.addNonUniqueName(mwe, "Chimwera");
    public static final LanguageType mwe3 = LanguageType.addNonUniqueName(mwe, "Cimwera");
    public static final LanguageType mwe4 = LanguageType.addNonUniqueName(mwe, "Mwela");
    public static final LanguageType mjh = LanguageType.addNonUniqueCode(mwe, "mjh");
    public static final LanguageType mjh2 = LanguageType.addNonUniqueName(mwe, "Kinyasa");
    public static final LanguageType mjh3 = LanguageType.addNonUniqueName(mwe, "Nyanza");
    // mgs → Nyasa (alternative name); zma→Australian Manda; mha → Manda (removed the qualifier). To avoid duplication: mjh → Nyanza (alternative name)
    public static final LanguageType mgs = new LanguageType("mgs", "Nyasa", "Nyasa");
    public static final LanguageType zma = new LanguageType("zma", "Australian Manda", "Australian Manda");
    public static final LanguageType mha = new LanguageType("mha", "Manda", "Manda");

    public static final LanguageType mh = new LanguageType("mh", "Ebon", "Marshallese");
    public static final LanguageType mah = LanguageType.addNonUniqueCode(mh, "mah");

    public static final LanguageType mi = new LanguageType("mi", "Māori", "Maori");
    public static final LanguageType mri = LanguageType.addNonUnique(mi, "mri", "Māori");

    public static final LanguageType mia = new LanguageType("mia", "Miami-Illinois", "Miami-Illinois");
    public static final LanguageType mia2 = LanguageType.addNonUniqueName(mia, "Miami");



    // Mishmi ------------
    public static final LanguageType mhu = new LanguageType("mhu", "Digaro-Mishmi", "Digaro-Mishmi");

    public static final LanguageType mxj = LanguageType.addNonUnique(mhu, "mxj", "Miju-Mishmi");

    public static final LanguageType clk = LanguageType.addNonUnique(mhu, "clk", "Idu Mishmi");
    public static final LanguageType clk2 = LanguageType.addNonUniqueName(mhu, "Idu-Mishmi");
    // ------------ eo Mishmi



    public static final LanguageType mk = new LanguageType("mk", "Македонски", "Macedonian");
    public static final LanguageType mkd = LanguageType.addNonUniqueCode(mk, "mkd");

    public static final LanguageType ml = new LanguageType("ml", "Malayalam", "Malayalam");
    public static final LanguageType mal = LanguageType.addNonUniqueCode(ml, "mal");

    public static final LanguageType mmp = new LanguageType("mmp", "Siawi", "Siawi");
    public static final LanguageType mmp2 = LanguageType.addNonUniqueName(mmp, "Musan");
    public static final LanguageType mmp3 = LanguageType.addNonUniqueName(mmp, "Musian");
    public static final LanguageType mmp4 = LanguageType.addNonUniqueName(mmp, "Musa");

    public static final LanguageType mn = new LanguageType("mn", "Mongolian", "Mongolian");
    public static final LanguageType mon = LanguageType.addNonUniqueCode(mn, "mon");
    public static final LanguageType cmg = LanguageType.addNonUnique(mn, "cmg", "Classical Mongolian");
    public static final LanguageType khk = LanguageType.addNonUnique(mn, "khk", "Khalkha Mongolian");
    public static final LanguageType mvf = LanguageType.addNonUnique(mn, "mvf", "Peripheral Mongolian");
    public static final LanguageType xng = LanguageType.addNonUnique(mn, "xng", "Middle Mongolian");

    public static final LanguageType mni = new LanguageType("mni", "Meitei", "Meitei");
    public static final LanguageType mni2 = LanguageType.addNonUniqueName(mni, "Manipuri");

    public static final LanguageType mnw = new LanguageType("mnw", "Mon", "Mon");
    public static final LanguageType omx = LanguageType.addNonUnique(mnw, "omx", "Old Mon");

    public static final LanguageType mo = new LanguageType("mo", "Moldovenească", "Moldovan");
    public static final LanguageType mol = LanguageType.addNonUniqueName(mo, "Moldavian");

    public static final LanguageType mod = new LanguageType("mod", "Yamá", "Mobilian");
    public static final LanguageType mod2 = LanguageType.addNonUniqueName(mod, "Mobilian Jargon");
    
    public static final LanguageType mr = new LanguageType("mr", "Marathi", "Marathi");
    public static final LanguageType mar = LanguageType.addNonUniqueCode(mr, "mar");
    public static final LanguageType omr = LanguageType.addNonUnique(mr, "omr", "Old Marathi");

    public static final LanguageType mop = new LanguageType("mop", "Mopan", "Mopan");
    public static final LanguageType mop2 = LanguageType.addNonUniqueName(mop, "Mopán");

    public static final LanguageType mos = new LanguageType("mos", "Mòoré", "Mòoré");
    public static final LanguageType mos2 = LanguageType.addNonUniqueName(mos, "Moore");
    public static final LanguageType mos3 = LanguageType.addNonUniqueName(mos, "More");
    public static final LanguageType mos4 = LanguageType.addNonUniqueName(mos, "Mossi");

    public static final LanguageType mrv = new LanguageType("mrv", "Mangareva", "Mangareva");
    public static final LanguageType mrv2 = LanguageType.addNonUniqueName(mrv, "Mangarevan");


    
    // Malay ------------
    // ms 	Malay 	msa
    public static final LanguageType ms = new LanguageType("ms", "Bahasa Melayu", "Malay");
    public static final LanguageType zlm = LanguageType.addNonUniqueCode(ms, "zlm");
    
    public static final LanguageType abs = new LanguageType("abs", "Ambonese", "Ambonese");
    public static final LanguageType abs2 = LanguageType.addNonUniqueName(abs, "Ambonese Malay");
    
    public static final LanguageType btj = LanguageType.addNonUnique(ms, "btj", "Bacanese Malay");
    public static final LanguageType bve = LanguageType.addNonUnique(ms, "bve", "Berau Malay");
    public static final LanguageType bvu = LanguageType.addNonUnique(ms, "bvu", "Bukit Malay");
    public static final LanguageType coa = LanguageType.addNonUnique(ms, "coa", "Cocos Islands Malay");
    public static final LanguageType jax = LanguageType.addNonUnique(ms, "jax", "Jambi Malay");
    public static final LanguageType lrt = LanguageType.addNonUnique(ms, "lrt", "Larantuka Malay");
    public static final LanguageType max = LanguageType.addNonUnique(ms, "max", "North Moluccan Malay");
    public static final LanguageType mbf = LanguageType.addNonUnique(ms, "mbf", "Baba Malay");
    public static final LanguageType meo = LanguageType.addNonUnique(ms, "meo", "Kedah Malay");
    public static final LanguageType mfa = LanguageType.addNonUnique(ms, "mfa", "Pattani Malay");
    public static final LanguageType mqg = LanguageType.addNonUnique(ms, "mqg", "Kota Bangun Kutai Malay");
    public static final LanguageType msi = LanguageType.addNonUnique(ms, "msi", "Sabah Malay");
    public static final LanguageType plm = LanguageType.addNonUnique(ms, "plm", "Palembang");
    public static final LanguageType pmy = LanguageType.addNonUnique(ms, "pmy", "Papuan Malay");
    public static final LanguageType pse = LanguageType.addNonUnique(ms, "pse", "Central Malay");

    public static final LanguageType sci = new LanguageType("sci", "Sri Lankan Malay", "Sri Lankan Malay");
    public static final LanguageType sci2 = LanguageType.addNonUniqueName(sci, "Sri Lankan Creole Malay");

    public static final LanguageType vkt = LanguageType.addNonUnique(ms, "vkt", "Tenggarong Kutai Malay");
    public static final LanguageType xmm = LanguageType.addNonUnique(ms, "xmm", "Manado Malay");
    public static final LanguageType zmi = LanguageType.addNonUnique(ms, "zmi", "Negeri Sembilan Malay");
    public static final LanguageType zsm = LanguageType.addNonUnique(ms, "zsm", "Standard Malay");

    // in enwikt, but not in enwiki:
    // bpq Banda Malay
    // ccm Malaccan Creole Malay
    // mfp Makassar Malay
    // mhp Balinese Malay
    // mkn Kupang Malay
    // zlm Colloquial Malay
    // ------------ eo Malay
    


    public static final LanguageType mt = new LanguageType("mt", "Malti", "Maltese");
    public static final LanguageType mlt = LanguageType.addNonUniqueCode(mt, "mlt");

    public static final LanguageType mwn = new LanguageType("mwn", "Mwanga", "Mwanga");
    public static final LanguageType mwn2 = LanguageType.addNonUniqueName(mwn, "Nyamwanga");



    // Marwari ------------
    public static final LanguageType mwr = new LanguageType("mwr", "Marwari", "Marwari");

    public static final LanguageType dhd = LanguageType.addNonUnique(mwr, "dhd", "Dhundari");
    public static final LanguageType rwr = LanguageType.addNonUniqueCode(mwr, "rwr");
    public static final LanguageType mve = LanguageType.addNonUniqueCode(mwr, "mve");
    public static final LanguageType wry = LanguageType.addNonUnique(mwr, "wry", "Merwari");
    public static final LanguageType mtr = LanguageType.addNonUnique(mwr, "mtr", "Mewari");
    public static final LanguageType smv = LanguageType.addNonUnique(mwr, "smv", "Shekhawati");
    // ------------ eo Marwari



    public static final LanguageType my = new LanguageType("my", "Myanmasa", "Burmese");
    public static final LanguageType mya = LanguageType.addNonUnique(my, "mya", "Myanmar");
    public static final LanguageType obr = LanguageType.addNonUnique(my, "obr", "Old Burmese");

    public static final LanguageType myx = new LanguageType("myx", "Masaba", "Masaba");
    public static final LanguageType myx2 = LanguageType.addNonUniqueName(myx, "Masaaba");

    public static final LanguageType mzn = new LanguageType("mzn", "Mazandarani", "Mazandarani");
    public static final LanguageType mzn2 = LanguageType.addNonUniqueName(mzn, "Mazanderani");

    public static final LanguageType na = new LanguageType("na", "Dorerin Naoero", "Nauruan");
    public static final LanguageType nau = LanguageType.addNonUnique(na, "nau", "Nauru");



    // Nahuatl ------------
    public static final LanguageType nah = new LanguageType("nah", "Nāhuatl", "Nahuatl");
    
    public static final LanguageType azz = LanguageType.addNonUnique(nah, "azz", "Highland Puebla Nahuatl");
    public static final LanguageType naz = LanguageType.addNonUnique(nah, "naz", "Coatepec Nahuatl");
    public static final LanguageType nch = LanguageType.addNonUnique(nah, "nch", "Central Huasteca Nahuatl");
    public static final LanguageType nci = LanguageType.addNonUnique(nah, "nci", "Classical Nahuatl");
    public static final LanguageType ncj = LanguageType.addNonUnique(nah, "ncj", "Northern Puebla Nahuatl");
    public static final LanguageType ncl = LanguageType.addNonUnique(nah, "ncl", "Michoacán Nahuatl");
    public static final LanguageType ncx = LanguageType.addNonUnique(nah, "ncx", "Central Puebla Nahuatl");
    public static final LanguageType ngu = LanguageType.addNonUnique(nah, "ngu", "Guerrero Nahuatl");
    public static final LanguageType nhc = LanguageType.addNonUnique(nah, "nhc", "Tabasco Nahuatl");
    public static final LanguageType nhe = LanguageType.addNonUnique(nah, "nhe", "Eastern Huasteca Nahuatl");
    public static final LanguageType nhg = LanguageType.addNonUnique(nah, "nhg", "Tetelcingo Nahuatl");

    public static final LanguageType nhi = LanguageType.addNonUnique(nah, "nhi", "Tenango Nahuatl");
    public static final LanguageType nhi2 = LanguageType.addNonUniqueName(nah, "Zacatlán-Ahuacatlán-Tepetzintla Nahuatl");

    public static final LanguageType nhk = LanguageType.addNonUnique(nah, "nhk", "Isthmus-Cosoleacaque Nahuatl");
    public static final LanguageType nhm = LanguageType.addNonUnique(nah, "nhm", "Morelos Nahuatl");
    public static final LanguageType nhn = LanguageType.addNonUnique(nah, "nhn", "Central Nahuatl");
    public static final LanguageType nhp = LanguageType.addNonUnique(nah, "nhp", "Isthmus-Pajapan Nahuatl");
    public static final LanguageType nhq = LanguageType.addNonUnique(nah, "nhq", "Huaxcaleca Nahuatl");
    public static final LanguageType nht = LanguageType.addNonUnique(nah, "nht", "Ometepec Nahuatl");
    public static final LanguageType nhv = LanguageType.addNonUnique(nah, "nhv", "Temascaltepec Nahuatl");
    public static final LanguageType nhw = LanguageType.addNonUnique(nah, "nhw", "Western Huasteca Nahuatl");
    public static final LanguageType nhx = LanguageType.addNonUnique(nah, "nhx", "Isthmus-Mecayapan Nahuatl");
    public static final LanguageType nhy = LanguageType.addNonUnique(nah, "nhy", "Northern Oaxaca Nahuatl");
    public static final LanguageType nhz = LanguageType.addNonUnique(nah, "nhz", "Santa María La Alta Nahuatl");
    public static final LanguageType nln = LanguageType.addNonUnique(nah, "nln", "Durango Nahuatl");
    public static final LanguageType nlv = LanguageType.addNonUnique(nah, "nlv", "Orizaba Nahuatl");
    public static final LanguageType npl = LanguageType.addNonUnique(nah, "npl", "Southeastern Puebla Nahuatl");
    public static final LanguageType nsu = LanguageType.addNonUnique(nah, "nsu", "Sierra Negra Nahuatl");
    public static final LanguageType nuz = LanguageType.addNonUnique(nah, "nuz", "Tlamacazapa Nahuatl");
    public static final LanguageType ppl = LanguageType.addNonUnique(nah, "ppl", "Pipil");
    // ------------ eo Nahuatl
    

    
    public static final LanguageType nam = new LanguageType("nam", "Ngan'gityemerri", "Ngan'gityemerri");
    public static final LanguageType nam2 = LanguageType.addNonUniqueName(nam, "Nangikurrunggurr");

    public static final LanguageType naq = new LanguageType("naq", "Nama", "Nama");
    public static final LanguageType hgm = LanguageType.addNonUnique(naq, "hgm", "Hai||om");

    public static final LanguageType nd = new LanguageType("nd", "Northern Ndebele", "Northern Ndebele");
    public static final LanguageType nde = LanguageType.addNonUnique(nd, "nde", "Sindebele");

    public static final LanguageType ne = new LanguageType("ne", "Nepālī", "Nepali");
    public static final LanguageType nep = LanguageType.addNonUnique(ne, "nep", "Nepalese");

    public static final LanguageType new_ = new LanguageType("new", "Newari", "Newari");
    public static final LanguageType new2 = LanguageType.addNonUniqueName(new_, "Nepal Bhasa");
    public static final LanguageType new3 = LanguageType.addNonUniqueName(new_, "Newah Bhaye");
    public static final LanguageType nwc = new LanguageType("nwc", "Classical Newari", "Classical Newari");
    public static final LanguageType nwc2 = LanguageType.addNonUniqueName(nwc, "Classical Nepal Bhasa");

    public static final LanguageType nga = new LanguageType("nga", "Ngbaka Gbaya", "Ngbaka Gbaya");
    public static final LanguageType nga2 = LanguageType.addNonUniqueName(nga, "Ngbaka");
    public static final LanguageType ngg = LanguageType.addNonUnique(nga, "ngg", "Ngbaka Manza");
    public static final LanguageType mzv = LanguageType.addNonUnique(nga, "mzv", "Manza");

    public static final LanguageType niv = new LanguageType("niv", "Nivkh", "Nivkh");
    public static final LanguageType niv2 = LanguageType.addNonUniqueName(niv, "Gilyak");

    public static final LanguageType nl = new LanguageType("nl", "Nederlands", "Dutch");
    public static final LanguageType nld = LanguageType.addNonUniqueCode(nl, "nld");
    public static final LanguageType zea = LanguageType.addNonUnique(nl, "zea", "Zeelandic");
    public static final LanguageType zea2 = LanguageType.addNonUniqueName(nl, "Zeeuws");
    public static final LanguageType dum = LanguageType.addNonUnique(nl, "dum", "Middle Dutch");
    public static final LanguageType odt = LanguageType.addNonUnique(nl, "odt", "Old Dutch");

    public static final LanguageType nmn = new LanguageType("nmn", "Taa", "Taa");
    public static final LanguageType nmn2 = LanguageType.addNonUniqueName(nmn, "!Xóõ");



    // Norwegian ------------
    public static final LanguageType no = new LanguageType("no", "Norwegian", "Norwegian");
    public static final LanguageType nor = LanguageType.addNonUniqueCode(no, "nor");
    public static final LanguageType gmq_mno = LanguageType.addNonUnique(no, "gmq-mno", "Middle Norwegian");

    public static final LanguageType nn = new LanguageType("nn", "Norwegian Nynorsk", "Norwegian Nynorsk");
    public static final LanguageType nno = LanguageType.addNonUnique(nn, "nno", "Nynorsk");
    
    public static final LanguageType nb = new LanguageType("nb", "Bokmål", "Bokmål");
    public static final LanguageType nob = LanguageType.addNonUnique(nb, "nob", "Norwegian Bokmål");

    public static final LanguageType rmg = new LanguageType("rmg", "Rodi", "Rodi");
    public static final LanguageType rmg2 = LanguageType.addNonUniqueName(no, "Traveller Norwegian");
    // ------------ eo Norwegian



    public static final LanguageType noo = new LanguageType("noo", "Nuu-chah-nulth", "Nuu-chah-nulth");
    public static final LanguageType noo2 = LanguageType.addNonUniqueName(noo, "Nootka");

    public static final LanguageType nr = new LanguageType("nr", "Southern Ndebele", "Southern Ndebele");
    public static final LanguageType nbl = LanguageType.addNonUnique(nr, "nbl", "Nrebele");

    public static final LanguageType nrm = new LanguageType("nrm", "Narom", "Narom");
    public static final LanguageType nrm2 = LanguageType.addNonUniqueName(nrm, "Narum");

    public static final LanguageType ntk = new LanguageType("ntk", "Ikoma", "Ikoma");
    public static final LanguageType ntk2 = LanguageType.addNonUniqueName(ntk, "Ikoma-Nata-Isenye");

    public static final LanguageType nv = new LanguageType("nv", "Diné bizaad", "Navajo");
    public static final LanguageType nav = LanguageType.addNonUniqueCode(nv, "nav");
    
    public static final LanguageType ny = new LanguageType("ny", "Chi-Chewa", "Chewa");
    public static final LanguageType nya = LanguageType.addNonUnique(ny, "nya", "Chichewa");
    public static final LanguageType ny2 = LanguageType.addNonUniqueName(ny, "Nyanja");
    public static final LanguageType ny3 = LanguageType.addNonUniqueName(ny, "Chinyanja");

    public static final LanguageType nyn = new LanguageType("nyn", "Nyankole", "Nyankole");
    public static final LanguageType nyn2 = LanguageType.addNonUniqueName(nyn, "Nkore");

    public static final LanguageType nyy = new LanguageType("nyy", "Nyakyusa", "Nyakyusa");
    public static final LanguageType nyy2 = LanguageType.addNonUniqueName(nyy, "Nyakyusa-Ngonde");

    public static final LanguageType nzi = new LanguageType("nzi", "Nzema", "Nzema");
    public static final LanguageType nzi2 = LanguageType.addNonUniqueName(nzi, "Nzima");

    public static final LanguageType oc = new LanguageType("oc", "Occitan", "Occitan");
    public static final LanguageType oci = LanguageType.addNonUniqueCode(oc, "oci");
    public static final LanguageType pro = new LanguageType("pro", "Old Occitan", "Old Occitan");
    public static final LanguageType pro2 = LanguageType.addNonUniqueName(pro, "Old Provençal");
    
    
    
    // Ojibwe and Algonquin ------------
    public static final LanguageType oj = new LanguageType("oj", "Ojibwe", "Ojibwe");
    public static final LanguageType oji = LanguageType.addNonUniqueCode(oj, "oji");

    public static final LanguageType ciw = LanguageType.addNonUnique(oj, "ciw", "Chippewa");
    public static final LanguageType ciw2 = LanguageType.addNonUniqueName(oj, "Ojibway");
    public static final LanguageType ciw3 = LanguageType.addNonUniqueName(oj, "Ojibwemowin");
    public static final LanguageType ciw4 = LanguageType.addNonUniqueName(oj, "Southwestern Ojibwa");

    public static final LanguageType otw = LanguageType.addNonUnique(oj, "otw", "Ottawa");

    public static final LanguageType alq = new LanguageType("alq", "Algonquin", "Algonquin");
    public static final LanguageType alg = LanguageType.addNonUniqueCode(alq, "alg");// old: alg ISO 639-2
    // not yet in English Wiktionary:
    public static final LanguageType ojs = LanguageType.addNonUnique(oj, "ojs", "Severn Ojibwa");
    public static final LanguageType ojg = LanguageType.addNonUnique(oj, "ojg", "Eastern Ojibwa");
    public static final LanguageType ojc = LanguageType.addNonUnique(oj, "ojc", "Central Ojibwa");
    public static final LanguageType ojb = LanguageType.addNonUnique(oj, "ojb", "Northwestern Ojibwa");
    public static final LanguageType ojw = LanguageType.addNonUnique(oj, "ojw", "Western Ojibwa");

    public static final LanguageType abe = new LanguageType("abe", "Abenaki", "Abenaki");
    public static final LanguageType abe2 = LanguageType.addNonUniqueName(abe, "Western Abenaki");
    public static final LanguageType aaq = LanguageType.addNonUnique(abe, "aaq", "Eastern Abenaki");
    public static final LanguageType aaq2 = LanguageType.addNonUniqueName(abe, "Penobscot");
    public static final LanguageType aaq3 = LanguageType.addNonUniqueName(abe, "Abenaki-Penobscot");
    // ------------ eo Ojibwe



    // Oromo ------------
    public static final LanguageType om = new LanguageType("om", "Oromoo", "Oromo");
    public static final LanguageType orm = LanguageType.addNonUniqueCode(om, "orm");
    
    public static final LanguageType gax = LanguageType.addNonUnique(om, "gax", "Borana");
    public static final LanguageType gax2 = LanguageType.addNonUniqueName(om, "Borana-Arsi-Guji Oromo");

    public static final LanguageType hae = LanguageType.addNonUnique(om, "hae", "Eastern Oromo");
    public static final LanguageType orc = LanguageType.addNonUnique(om, "orc", "Orma");
    public static final LanguageType gaz = LanguageType.addNonUnique(om, "gaz", "West Central Oromo");
    // ------------ eo Oromo



    public static final LanguageType or = new LanguageType("or", "Oriya", "Oriya");
    public static final LanguageType ori = LanguageType.addNonUniqueCode(or, "ori");

    public static final LanguageType os = new LanguageType("os", "Иронау", "Ossetian");
    public static final LanguageType oss = LanguageType.addNonUnique(os, "oss", "Ossetic");
    public static final LanguageType os2 = LanguageType.addNonUniqueName(os, "Ossete");



    // Punjabi ------------
    public static final LanguageType pa = new LanguageType("pa", "Punjabi", "Punjabi");
    public static final LanguageType pan = LanguageType.addNonUnique(pa, "pan", "Panjabi");
    
    public static final LanguageType lah = new LanguageType("lah", "Lahnda", "Lahnda");
    
    // not yet in English Wiktionary:
    public static final LanguageType pnb = LanguageType.addNonUnique(pa, "pnb", "Western Panjabi");
    public static final LanguageType pmu = LanguageType.addNonUnique(pa, "pmu", "Mirpur Punjabi");
    // ------------ eo Punjabi



    public static final LanguageType pam = new LanguageType("pam", "Kapampangan", "Kapampangan");
    public static final LanguageType pam2 = LanguageType.addNonUniqueName(pam, "Pampanga");

    public static final LanguageType pap = new LanguageType("pap", "Papiamento", "Papiamento");
    public static final LanguageType pap2 = LanguageType.addNonUniqueName(pap, "Papiamentu");

    public static final LanguageType pau = new LanguageType("pau", "Palauan", "Palauan");
    public static final LanguageType pau2 = LanguageType.addNonUniqueName(pau, "Palau");

    public static final LanguageType pcm = new LanguageType("pcm", "Nigerian Pidgin", "Nigerian Pidgin");
    public static final LanguageType pcm2 = LanguageType.addNonUniqueName(pcm, "Naija");

    public static final LanguageType pi = new LanguageType("pi", "Pāli", "Pali");
    public static final LanguageType pli = LanguageType.addNonUniqueCode(pi, "pli");

    public static final LanguageType pih = new LanguageType("pih", "Pitcairn-Norfolk", "Pitcairn-Norfolk");
    public static final LanguageType cpe_pit = LanguageType.addNonUnique(pih, "cpe-pit", "Pitkern");// enwikt code
    public static final LanguageType pih3 = LanguageType.addNonUniqueName(pih, "Pitcairnese");
    public static final LanguageType cpe_nor = LanguageType.addNonUnique(pih, "cpe-nor", "Norfuk");// enwikt code
    public static final LanguageType pih5 = LanguageType.addNonUniqueName(pih, "Norfolk");

    public static final LanguageType pit = new LanguageType("pit", "Pitta-Pitta", "Pitta-Pitta");
    public static final LanguageType pit2 = LanguageType.addNonUniqueName(pit, "Pitta Pitta");

    public static final LanguageType pl = new LanguageType("pl", "Polski", "Polish");
    public static final LanguageType pol = LanguageType.addNonUniqueCode(pl, "pol");
    public static final LanguageType zlw_opl = LanguageType.addNonUnique(pl, "zlw-opl", "Old Polish");// enwikt code
    public static final LanguageType pl_Slovincian = LanguageType.addNonUniqueName(pl, "Slovincian");

    public static final LanguageType pot = new LanguageType("pot", "Neshnabémwen", "Potawatomi");
    public static final LanguageType pot2 = LanguageType.addNonUniqueName(pot, "Pottawatomie");

    public static final LanguageType ps = new LanguageType("ps", "Pashto", "Pashto");
    public static final LanguageType pus = LanguageType.addNonUniqueCode(ps, "pus");
    public static final LanguageType pbt = LanguageType.addNonUnique(ps, "pbt", "Southern Pashto");
    public static final LanguageType pbu = LanguageType.addNonUnique(ps, "pbu", "Northern Pashto");
    public static final LanguageType pst = LanguageType.addNonUnique(ps, "pst", "Central Pashto");

    public static final LanguageType pt = new LanguageType("pt", "Português", "Portuguese");
    public static final LanguageType por = LanguageType.addNonUniqueCode(pt, "por");
    public static final LanguageType pt_br = LanguageType.addNonUnique(pt, "pt-br", "Brazilian Portuguese");// Russian Wiktionary
    public static final LanguageType fax = LanguageType.addNonUnique(pt, "fax", "Fala");

    public static final LanguageType pua = new LanguageType("pua", "Purepecha", "Purepecha");
    public static final LanguageType tsz = LanguageType.addNonUnique(pua, "tsz", "P'urhépecha");

    public static final LanguageType qu = new LanguageType("qu", "Runa Simi", "Quechua");
    public static final LanguageType que = LanguageType.addNonUniqueCode(qu, "que");

    public static final LanguageType rar = new LanguageType("rar", "Rarotongan", "Rarotongan");
    public static final LanguageType rar2 = LanguageType.addNonUniqueName(rar, "Cook Islands Maori");
    public static final LanguageType rar3 = LanguageType.addNonUniqueName(rar, "Cook Islands Māori");

    public static final LanguageType rm = new LanguageType("rm", "Rumantsch", "Romansch");
    public static final LanguageType roh = LanguageType.addNonUnique(rm, "roh", "Rumantsch");
    public static final LanguageType rm2 = LanguageType.addNonUniqueName(rm, "Rhaeto-Romance");



    // Romanian ------------
    public static final LanguageType ro = new LanguageType("ro", "Română", "Romanian");
    public static final LanguageType ron = LanguageType.addNonUnique(ro, "ron", "Daco-Romanian");
    public static final LanguageType ro2 = LanguageType.addNonUniqueName(ro, "Roumanian");
    public static final LanguageType ro3 = LanguageType.addNonUniqueName(ro, "Rumanian");
    public static final LanguageType ruq = LanguageType.addNonUnique(ro, "ruq", "Megleno-Romanian");

    // Russian Wiktionary? (to delete if there are no one entry):
    public static final LanguageType ruq_cyrl = LanguageType.addNonUnique(ro, "ruq-cyrl", "Megleno-Romanian (Cyrillic script)");
    public static final LanguageType ruq_grek = LanguageType.addNonUnique(ro, "ruq-grek", "Megleno-Romanian (Greek script)");
    public static final LanguageType ruq_latn = LanguageType.addNonUnique(ro, "ruq-latn", "Megleno-Romanian (Latin script)");
    // ------------ eo Romanian


    public static final LanguageType roa_nor = new LanguageType("roa-nor", "Norman", "Norman");
    public static final LanguageType roa_jer = LanguageType.addNonUnique(roa_nor, "roa-jer", "Jèrriais");
    public static final LanguageType roa_nor2 = LanguageType.addNonUniqueName(roa_nor, "Jersey French");
    public static final LanguageType roa_nor3 = LanguageType.addNonUniqueName(roa_nor, "Jersey Norman");
    public static final LanguageType roa_nor4 = LanguageType.addNonUniqueName(roa_nor, "Jersey Norman French");
    public static final LanguageType xno = new LanguageType("xno", "Anglo-Norman", "Anglo-Norman");

    
    
    // Romani ------------
    public static final LanguageType rom = new LanguageType("rom", "Romani", "Romani");
    public static final LanguageType rmn = LanguageType.addNonUnique(rom, "rmn", "Balkan Romani");
    public static final LanguageType rml = LanguageType.addNonUnique(rom, "rml", "Baltic Romani");
    public static final LanguageType rmc = LanguageType.addNonUnique(rom, "rmc", "Carpathian Romani");
    public static final LanguageType rmf = LanguageType.addNonUnique(rom, "rmf", "Finnish Kalo");
    public static final LanguageType rmo = LanguageType.addNonUnique(rom, "rmo", "Sinte Romani");
    public static final LanguageType rmy = LanguageType.addNonUnique(rom, "rmy", "Vlax Romani");
    public static final LanguageType rmw = LanguageType.addNonUnique(rom, "rmw", "Welsh Romani");

    public static final LanguageType rmu = new LanguageType("rmu", "Scandoromani", "Scandoromani");
    public static final LanguageType rmu2 = LanguageType.addNonUniqueName(rmu, "Tavringer Romani");
    // ------------ eo Romani



    // Creole ------------
    public static final LanguageType rop = new LanguageType("rop", "Kriol", "Kriol");
    public static final LanguageType rop2 = LanguageType.addNonUniqueName(rop, "Australian Kriol");

    public static final LanguageType brc = new LanguageType("brc", "Berbice Creole Dutch", "Berbice Creole Dutch");
    public static final LanguageType brc2 = LanguageType.addNonUniqueName(brc, "Berbice Dutch Creole");

    public static final LanguageType gcf = new LanguageType("gcf", "Antillean Creole", "Antillean Creole");
    public static final LanguageType gcf2 = LanguageType.addNonUniqueName(gcf, "Guadeloupean Creole French");
    public static final LanguageType acf = LanguageType.addNonUnique(gcf, "acf", "Saint Lucian Creole French");
    
    public static final LanguageType ht = new LanguageType("ht", "Kreyòl ayisyen", "Haitian Creole");
    public static final LanguageType hat = LanguageType.addNonUnique(ht, "hat", "Kreyòl");
    public static final LanguageType ht2 = LanguageType.addNonUniqueName(ht, "Haitian");

    public static final LanguageType jam = new LanguageType("jam", "Jamaican Creole", "Jamaican Creole");
    public static final LanguageType jam2 = LanguageType.addNonUniqueName(jam, "Jamaican Patois");
    public static final LanguageType jam3 = LanguageType.addNonUniqueName(jam, "Patois");
    public static final LanguageType jam4 = LanguageType.addNonUniqueName(jam, "Jamaican");
    public static final LanguageType jam5 = LanguageType.addNonUniqueName(jam, "Patwa");

    public static final LanguageType rcf = new LanguageType("rcf", "Kreol Réyoné", "Réunion Creole");
    public static final LanguageType rcf2 = LanguageType.addNonUniqueName(rcf, "Réunion Creole French");
    public static final LanguageType rcf3 = LanguageType.addNonUniqueName(rcf, "Reunionese Creole");

    public static final LanguageType tcs = new LanguageType("tcs", "Torres Strait Creole", "Torres Strait Creole");
    public static final LanguageType tcs2 = LanguageType.addNonUniqueName(tcs, "Big Thap");
    public static final LanguageType tcs3 = LanguageType.addNonUniqueName(tcs, "Blaikman, Brokan");
    public static final LanguageType tcs4 = LanguageType.addNonUniqueName(tcs, "Broken");
    public static final LanguageType tcs5 = LanguageType.addNonUniqueName(tcs, "Broken English");
    public static final LanguageType tcs6 = LanguageType.addNonUniqueName(tcs, "Cape York Creole");
    public static final LanguageType tcs7 = LanguageType.addNonUniqueName(tcs, "Lockhart Creole");
    public static final LanguageType tcs8 = LanguageType.addNonUniqueName(tcs, "Papuan Pidgin English");
    public static final LanguageType tcs9 = LanguageType.addNonUniqueName(tcs, "Torres Strait Brokan");
    public static final LanguageType tcs10 = LanguageType.addNonUniqueName(tcs, "Torres Strait Broken");
    public static final LanguageType tcs11 = LanguageType.addNonUniqueName(tcs, "Torres Strait Pidgin");
    public static final LanguageType tcs12 = LanguageType.addNonUniqueName(tcs, "Yumplatok");

    // not yet in English Wiktionary:
    public static final LanguageType bzj = LanguageType.addNonUniqueName(rop, "Belizean Creole");
    public static final LanguageType pov = LanguageType.addNonUnique(rop, "pov", "Guinea-Bissau Creole");
    // ------------ eo Creole



    public static final LanguageType ru = new LanguageType("ru", "Русский", "Russian");
    public static final LanguageType rus = LanguageType.addNonUniqueCode(ru, "rus");
    // crp-tpr 	Taimyr Pidgin Russian

    public static final LanguageType run = new LanguageType("run", "Kirundi", "Rundi");
    public static final LanguageType rn  = LanguageType.addNonUnique(run, "rn", "Kirundi");
    
    public static final LanguageType rup = new LanguageType("rup", "Armãneshce", "Aromanian");
    public static final LanguageType roa_rup = LanguageType.addNonUniqueCode(rup, "roa-rup");

    public static final LanguageType ryn = new LanguageType("ryn", "Northern Amami", "Northern Amami");
    public static final LanguageType ams = new LanguageType("ams", "Southern Amami", "Southern Amami");
    public static final LanguageType ams2 = LanguageType.addNonUniqueName(pcm, "Southern Amami-Oshima");

    public static final LanguageType rw = new LanguageType("rw", "Kinyarwanda", "Kinyarwanda");
    public static final LanguageType kin = LanguageType.addNonUnique(rw, "kin", "Rwanda");

    public static final LanguageType sa = new LanguageType("sa", "Sanskrit", "Sanskrit");
    public static final LanguageType san = LanguageType.addNonUniqueCode(sa, "san");

    public static final LanguageType sah = new LanguageType("sah", "Sakha", "Sakha");
    public static final LanguageType sah2 = LanguageType.addNonUniqueName(sah, "Yakut");



    // Sardinian ------------
    public static final LanguageType sc = new LanguageType("sc", "Sardu", "Sardinian");
    public static final LanguageType srd = LanguageType.addNonUniqueCode(sc, "srd");

    public static final LanguageType sdc = LanguageType.addNonUnique(sc, "sdc", "Sassarese");

    public static final LanguageType sdn = LanguageType.addNonUnique(sc, "sdn", "Gallurese");
    public static final LanguageType sdn2 = LanguageType.addNonUniqueName(sc, "Gallurese Sardinian");
    
    public static final LanguageType src = LanguageType.addNonUnique(sc, "src", "Logudorese");
    public static final LanguageType src2 = LanguageType.addNonUniqueName(sc, "Logudorese Sardinian");

    public static final LanguageType sro = LanguageType.addNonUnique(sc, "sro", "Campidanese");
    public static final LanguageType sro2 = LanguageType.addNonUniqueName(sc, "Campidanese Sardinian");
    public static final LanguageType sro3 = LanguageType.addNonUniqueName(sc, "Sardu campidanesu");
    // ------------ eo Sardinian



    public static final LanguageType sco = new LanguageType("sco", "Scots", "Scots");
    public static final LanguageType sco2 = LanguageType.addNonUniqueName(sco, "Lowland Scots");

    public static final LanguageType sd = new LanguageType("sd", "Sindhī", "Sindhi");
    public static final LanguageType snd = LanguageType.addNonUniqueCode(sd, "snd");

    public static final LanguageType sh = new LanguageType("sh", "српскохрватски", "Serbo-Croatian");
    public static final LanguageType hbs = LanguageType.addNonUnique(sh, "hbs", "Serbo-Croat");
    public static final LanguageType sh2 = LanguageType.addNonUniqueName(sh, "BCS");
    public static final LanguageType sh3 = LanguageType.addNonUniqueName(sh, "Croato-Serbian");

    public static final LanguageType sk = new LanguageType("sk", "SlovenДЌina", "Slovak");
    public static final LanguageType slk = LanguageType.addNonUniqueCode(sk, "slk");
    public static final LanguageType slo = LanguageType.addNonUniqueCode(sk, "slo");

    public static final LanguageType sl = new LanguageType("sl", "Slovenščina", "Slovene");
    public static final LanguageType slv = LanguageType.addNonUnique(sl, "slv", "Slovenian");

    public static final LanguageType slovio = new LanguageType("slovio", "Slovio", "Slovio");// ruwikt
    public static final LanguageType slovio_la = LanguageType.addNonUniqueCode(slovio, "slovio-la");// the longest language code in ruwikt
    public static final LanguageType slovio_c = LanguageType.addNonUnique(slovio, "slovio-c", "Slovio (Cyrillic)");// ruwikt
    public static final LanguageType slovio_l = LanguageType.addNonUnique(slovio, "slovio-l", "Slovio (Latin)");// ruwikt



    // Sami ------------
    public static final LanguageType se = new LanguageType("se", "Davvisámegiella", "Northern Sami");
    public static final LanguageType sme = LanguageType.addNonUniqueCode(se, "sme");

    public static final LanguageType sjd = new LanguageType("sjd", "Kildin Sami", "Kildin Sami");
    public static final LanguageType sjk = new LanguageType("sjk", "Kemi Sami", "Kemi Sami");
    public static final LanguageType sjt = new LanguageType("sjt", "Ter Sami", "Ter Sami");
    public static final LanguageType sma = new LanguageType("sma", "Southern Sami", "Southern Sami");
    public static final LanguageType smj = new LanguageType("smj", "Julevsámegiella", "Lule Sami");
    public static final LanguageType smn = new LanguageType("smn", "Inari Sami", "Inari Sami");

    public static final LanguageType sms = new LanguageType("sms", "Skolt Sami", "Skolt Sami");
    public static final LanguageType sia = LanguageType.addNonUnique(sms, "sia", "Akkala Sami");

    // sje Pite Sami        // does not have an written language
    // sju Ume Sami
    // ------------ eo Sami


    
    public static final LanguageType sg = new LanguageType("sg", "Sängö", "Sango");
    public static final LanguageType sag = LanguageType.addNonUniqueCode(sg, "sag");

    public static final LanguageType shi = new LanguageType("shi", "Tacelḥit", "Shilha");
    public static final LanguageType shi2 = LanguageType.addNonUniqueName(shi, "Tachelhit");

    public static final LanguageType si = new LanguageType("si", "Sinhala", "Sinhala");
    public static final LanguageType sin = LanguageType.addNonUnique(si, "sin", "Sinhalese");
    public static final LanguageType si2 = LanguageType.addNonUniqueName(si, "Singhalese");

    public static final LanguageType smo = new LanguageType("smo", "Gagana Samoa", "Samoan");
    public static final LanguageType sm = LanguageType.addNonUniqueCode(smo, "sm");

    public static final LanguageType sn = new LanguageType("sn", "chiShona", "Shona");
    public static final LanguageType sna = LanguageType.addNonUniqueCode(sn, "sna");

    public static final LanguageType so = new LanguageType("so", "Soomaaliga", "Somali");
    public static final LanguageType som = LanguageType.addNonUniqueCode(so, "som");

    public static final LanguageType soz = new LanguageType("soz", "Sonjo", "Sonjo");
    public static final LanguageType soz2 = LanguageType.addNonUniqueName(soz, "Temi");


    
    // Albanian ------------
    public static final LanguageType sqi = new LanguageType("sqi", "Shqip", "Albanian");
    public static final LanguageType sq  = LanguageType.addNonUniqueCode(sqi, "sq");

    public static final LanguageType aln  = new LanguageType("aln", "Gege", "Gheg");
    public static final LanguageType aln2 = LanguageType.addNonUniqueName(aln, "Gheg Albanian");

    public static final LanguageType aae  = new LanguageType("aae", "Arbëreshë Albanian", "Arbëreshë");
    public static final LanguageType aae2 = LanguageType.addNonUniqueName(aae, "Arbëreshë Albanian");

    public static final LanguageType aat  = new LanguageType("aat", "Arvanitika Albanian", "Arvanitika");
    public static final LanguageType aat2 = LanguageType.addNonUniqueName(aat, "Arvanitika Albanian");

    public static final LanguageType als = new LanguageType("als", "Tosk", "Tosk");
    public static final LanguageType als2 = LanguageType.addNonUniqueName(als, "Tosk Albanian");
    // ------------ eo Albanian



    public static final LanguageType sr = new LanguageType("sr", "Srpski", "Serbian");
    public static final LanguageType srp = LanguageType.addNonUnique(sr, "srp", "Montenegrin");
    public static final LanguageType sr_c = LanguageType.addNonUnique(sr, "sr-c", "Serbian (Cyrillic)");// Russian Wiktionary
    public static final LanguageType sr_l = LanguageType.addNonUnique(sr, "sr-l", "Serbian (Latin)");// Russian Wiktionary

    public static final LanguageType srs = new LanguageType("srs", "Tsuut’ina", "Tsuut’ina");
    public static final LanguageType srs2 = LanguageType.addNonUniqueName(srs, "Sarsi");

    public static final LanguageType ss = new LanguageType("ss", "SiSwati", "Swati");
    public static final LanguageType ssw = LanguageType.addNonUniqueCode(ss, "ssw");
    
    public static final LanguageType st = new LanguageType("st", "Sesotho", "Sotho");
    public static final LanguageType sot = LanguageType.addNonUnique(st, "sot", "Sesotho");
    public static final LanguageType st2 = LanguageType.addNonUniqueName(st, "Southern Sotho");
    public static final LanguageType st3 = LanguageType.addNonUniqueName(st, "Southern Sesotho");
    public static final LanguageType nso = LanguageType.addNonUnique(st, "nso", "Northern Sotho");

    public static final LanguageType stp = new LanguageType("stp", "O'otham", "Tepehuán");
    public static final LanguageType stp2 = LanguageType.addNonUniqueName(stp, "Southeastern Tepehuan");

    public static final LanguageType su = new LanguageType("su", "Basa Sunda", "Sundanese");
    public static final LanguageType sun = LanguageType.addNonUniqueCode(su, "sun");

    public static final LanguageType sv = new LanguageType("sv", "Svenska", "Swedish");
    public static final LanguageType swe = LanguageType.addNonUniqueCode(sv, "swe");
    public static final LanguageType non = new LanguageType("non", "Old Norse", "Old Norse");
    public static final LanguageType gmq_osw = LanguageType.addNonUnique(non, "gmq-osw", "Old Swedish"); //enwikt code



    // Swahili ------------
    public static final LanguageType sw = new LanguageType("sw", "Kiswahili", "Swahili");
    public static final LanguageType swa = LanguageType.addNonUniqueCode(sw, "swa");
    public static final LanguageType swc = LanguageType.addNonUnique(sw, "swc", "Congo Swahili");
    public static final LanguageType swh = LanguageType.addNonUnique(sw, "swh", "Coastal Swahili");

    public static final LanguageType swb = LanguageType.addNonUnique(sw, "swb", "Shimaore");
    public static final LanguageType swb2 = LanguageType.addNonUniqueName(sw, "Maore Comorian");
    public static final LanguageType swb3 = LanguageType.addNonUniqueName(sw, "Comorian");
    public static final LanguageType zdj = LanguageType.addNonUnique(sw, "zdj", "Ngazidja");
    public static final LanguageType zdj2 = LanguageType.addNonUniqueName(sw, "Ngazidja Comorian");
    public static final LanguageType wni = LanguageType.addNonUnique(sw, "wni", "Ndzwani");
    public static final LanguageType wni2 = LanguageType.addNonUniqueName(sw, "Ndzwani Comorian");
    public static final LanguageType wlc = LanguageType.addNonUnique(sw, "wlc", "Mwali");
    public static final LanguageType wlc2 = LanguageType.addNonUniqueName(sw, "Mwali Comorian");
    // ------------ eo Swahili
    
    
    
    public static final LanguageType ta = new LanguageType("ta", "Tamil", "Tamil");
    public static final LanguageType tam = LanguageType.addNonUniqueCode(ta, "tam");
    public static final LanguageType oty = LanguageType.addNonUnique(ta, "oty", "Old Tamil");

    public static final LanguageType tar = new LanguageType("tar", "Tarahumara", "Tarahumara");
    public static final LanguageType tar2 = LanguageType.addNonUniqueName(tar, "Central Tarahumara");
    public static final LanguageType tac = LanguageType.addNonUnique(tar, "tac", "Lowland Tarahumara");
    public static final LanguageType thh = LanguageType.addNonUnique(tar, "thh", "Northern Tarahumara");
    public static final LanguageType tcu = LanguageType.addNonUnique(tar, "tcu", "Southeastern Tarahumara");
    public static final LanguageType twr = LanguageType.addNonUnique(tar, "twr", "Southwestern Tarahumara");

    public static final LanguageType te = new LanguageType("te", "Telugu", "Telugu");
    public static final LanguageType tel = LanguageType.addNonUniqueCode(te, "tel");

    public static final LanguageType tem = new LanguageType("tem", "Temne", "Temne");
    public static final LanguageType tem2 = LanguageType.addNonUniqueName(tem, "Timne");

    public static final LanguageType ter = new LanguageType("ter", "Tereno", "Tereno");
    public static final LanguageType ter2 = LanguageType.addNonUniqueName(ter, "Terêna");
    public static final LanguageType ter3 = LanguageType.addNonUniqueName(ter, "Etelena");

    public static final LanguageType tet = new LanguageType("tet", "Tetum", "Tetum");
    public static final LanguageType tet2 = LanguageType.addNonUniqueName(tet, "Tetun");

    public static final LanguageType tgk = new LanguageType("tgk", "Tajik", "Tajik");
    public static final LanguageType tg = LanguageType.addNonUnique(tgk, "tg", "Tajiki");
    public static final LanguageType tgk2 = LanguageType.addNonUniqueName(tgk, "Tadjik");
    public static final LanguageType tgk3 = LanguageType.addNonUniqueName(tgk, "Tadzhik");
    public static final LanguageType tgk4 = LanguageType.addNonUniqueName(tgk, "Tajik Persian");


    public static final LanguageType tgl = new LanguageType("tgl", "Tagalog", "Tagalog");
    public static final LanguageType tl = LanguageType.addNonUniqueCode(tgl, "tl");
    public static final LanguageType fil = new LanguageType("fil", "Filipino", "Filipino");

    public static final LanguageType th = new LanguageType("th", "Phasa Thai", "Thai");
    public static final LanguageType tha = LanguageType.addNonUniqueCode(th, "tha");
    public static final LanguageType nod = new LanguageType("nod", "Northern Thai", "Northern Thai");
    public static final LanguageType sou = LanguageType.addNonUnique(th, "sou", "Southern Thai");
    public static final LanguageType tts = new LanguageType("tts", "Isan", "Isan");
    public static final LanguageType tts2 = LanguageType.addNonUniqueName(tts, "Northeastern Thai");

    public static final LanguageType tix = new LanguageType("tix", "Southern Tiwa", "Southern Tiwa");
    public static final LanguageType tix2 = LanguageType.addNonUniqueName(tix, "Tiwa");
    public static final LanguageType pie = LanguageType.addNonUnique(tix, "pie", "Piro");
    public static final LanguageType PIE = new LanguageType("PIE", "Proto-Indo-European", "Proto-Indo-European");// Russian Wiktionary
    
    public static final LanguageType tir = new LanguageType("tir", "Tigriññā", "Tigrinya");
    public static final LanguageType ti = LanguageType.addNonUniqueCode(tir, "ti");

    public static final LanguageType tk = new LanguageType("tk", "Türkmençe", "Turkmen");
    public static final LanguageType tuk = LanguageType.addNonUniqueCode(tk, "tuk");
    
    public static final LanguageType tmh = new LanguageType("tmh", "Tamashaq", "Tamashaq");
    public static final LanguageType tmh2 = LanguageType.addNonUniqueName(tmh, "Tamashek");
    public static final LanguageType taq = LanguageType.addNonUnique(tmh, "taq", "Tamasheq");
    public static final LanguageType thv = LanguageType.addNonUnique(tmh, "thv", "Tahaggart Tamahaq");
    public static final LanguageType ttq = LanguageType.addNonUnique(tmh, "ttq", "Tawallammat Tamajaq");
    public static final LanguageType thz = LanguageType.addNonUnique(tmh, "thz", "Tayart Tamajeq");

    public static final LanguageType to = new LanguageType("to", "faka-Tonga", "Tongan");
    public static final LanguageType ton = LanguageType.addNonUniqueCode(to, "ton");

    public static final LanguageType tpi = new LanguageType("tpi", "Tok Pisin", "Tok Pisin");
    public static final LanguageType tpi2 = LanguageType.addNonUniqueName(tpi, "Melanesian Pidgin English");
    public static final LanguageType tpi3 = LanguageType.addNonUniqueName(tpi, "Neo-Melanesian");
    public static final LanguageType tpi4 = LanguageType.addNonUniqueName(tpi, "New Guinea Pidgin");

    public static final LanguageType tt = new LanguageType("tt", "Татарча", "Tatar");
    public static final LanguageType tat = LanguageType.addNonUniqueCode(tt, "tat");
    public static final LanguageType tt_cyr = LanguageType.addNonUnique(tt, "tt.cyr", "Tatar (Cyrillic)"); // ruwikt
    public static final LanguageType tt_lat = LanguageType.addNonUnique(tt, "tt.lat", "Tatar (Latin)"); // ruwikt

    // Tonga: {{toi}}, {{tog}}; tog → Siska (alternative name)
    public static final LanguageType toi = new LanguageType("toi", "Tonga", "Tonga");
    public static final LanguageType tog = new LanguageType("tog", "Siska", "Siska");

    public static final LanguageType tokipona = new LanguageType("tokipona", "Toki Pona", "Toki Pona");
    public static final LanguageType art = LanguageType.addNonUniqueCode(tokipona, "art");// Russian Wiktionary

    public static final LanguageType tr = new LanguageType("tr", "Türkçe", "Turkish");
    public static final LanguageType tur = LanguageType.addNonUniqueCode(tr, "tur");
    public static final LanguageType ota = new LanguageType("ota", "Ottoman Turkish", "Ottoman Turkish");
    public static final LanguageType ota2 = LanguageType.addNonUniqueCode(ota, "Ottoman");
    public static final LanguageType bgx = new LanguageType("bgx", "Balkan Gagauz Turkish", "Balkan Gagauz Turkish");
    
    public static final LanguageType tsn = new LanguageType("tsn", "Setswana", "Tswana");
    public static final LanguageType tn = LanguageType.addNonUnique(tsn, "tn", "Setswana");
    public static final LanguageType tsn2 = LanguageType.addNonUniqueName(tsn, "Sitswana");

    public static final LanguageType tso = new LanguageType("tso", "Xitsonga", "Tsonga");
    public static final LanguageType ts = LanguageType.addNonUniqueCode(tso, "ts");

    public static final LanguageType tvl = new LanguageType("tvl", "Tuvalu", "Tuvaluan");
    public static final LanguageType tvl2 = LanguageType.addNonUniqueName(tvl, "Tuvalu");

    public static final LanguageType twf = new LanguageType("twf", "Taos", "Taos");
    public static final LanguageType twf2 = LanguageType.addNonUniqueName(twf, "Northern Tiwa");

    public static final LanguageType ty = new LanguageType("ty", "Reo Tahiti", "Tahitian");
    public static final LanguageType tah = LanguageType.addNonUniqueCode(ty, "tah");

    public static final LanguageType tyv = new LanguageType("tyv", "Тыва дыл", "Tuvan");
    public static final LanguageType tyv2 = LanguageType.addNonUniqueName(tyv, "Tyvan");
    
    public static final LanguageType tzj    = new LanguageType("tzj", "Tz'utujil", "Tz'utujil");
    public static final LanguageType tzt    = LanguageType.addNonUniqueCode(tzj, "tzt");

    public static final LanguageType tzm = new LanguageType("tzm", "Tamazight", "Central Morocco Tamazight");
    public static final LanguageType tzm2 = LanguageType.addNonUniqueName(tzm, "Central Atlas Tamazight");
    public static final LanguageType tzm3 = LanguageType.addNonUniqueName(tzm, "Tamazight");

    public static final LanguageType ug = new LanguageType("ug", "Uyghur", "Uyghur");
    public static final LanguageType uig = LanguageType.addNonUnique(ug, "uig", "Uigur");
    public static final LanguageType ug2 = LanguageType.addNonUniqueName(ug, "Uighur");
    public static final LanguageType ug3 = LanguageType.addNonUniqueName(ug, "Uygur");

    public static final LanguageType uk = new LanguageType("uk", "Українська мова", "Ukrainian");
    public static final LanguageType ukr = LanguageType.addNonUniqueCode(uk, "ukr");

    public static final LanguageType ur = new LanguageType("ur", "Urdu", "Urdu");
    public static final LanguageType urd = LanguageType.addNonUniqueCode(ur, "urd");


    // Utian ------------
    public static final LanguageType csm = new LanguageType("csm", "Central Sierra Miwok", "Central Sierra Miwok");

    public static final LanguageType css = new LanguageType("css", "Southern Ohlone", "Southern Ohlone");
    public static final LanguageType cst = new LanguageType("cst", "Northern Ohlone", "Northern Ohlone");
    // ------------ eo Utian


    public static final LanguageType uz = new LanguageType("uz", "Ўзбекча", "Uzbek");
    public static final LanguageType uzb = LanguageType.addNonUniqueCode(uz, "uzb");
    public static final LanguageType uzn = LanguageType.addNonUnique(uz, "uzn", "Northern Uzbek");
    public static final LanguageType uzs = LanguageType.addNonUnique(uz, "uzs", "Southern Uzbek");

    public static final LanguageType vai = new LanguageType("vai", "Vai", "Vai");
    public static final LanguageType vai2 = LanguageType.addNonUniqueName(vai, "Gallinas");
    public static final LanguageType vai3 = LanguageType.addNonUniqueName(vai, "Vy");

    public static final LanguageType ve = new LanguageType("ve", "Venda", "Venda");
    public static final LanguageType ven = LanguageType.addNonUnique(ve, "ven", "Tshivenda");
    public static final LanguageType ve2 = LanguageType.addNonUniqueName(ve, "Luvenda");

    public static final LanguageType vi = new LanguageType("vi", "Vietnamese", "Vietnamese");
    public static final LanguageType vie = LanguageType.addNonUniqueCode(vi, "vie");



    // Visayan ------------
    public static final LanguageType akl = new LanguageType("akl", "Aklanon", "Aklanon");
    public static final LanguageType ceb = new LanguageType("ceb", "Cebuano", "Cebuano");
    public static final LanguageType hil = new LanguageType("hil", "Ilonggo", "Hiligaynon");
    public static final LanguageType krj = new LanguageType("krj", "Kinaray-a", "Kinaray-a");
    public static final LanguageType tsg = new LanguageType("tsg", "Tausug", "Tausug");
    
    public static final LanguageType war = new LanguageType("war", "Winaray", "Waray-Waray");
    public static final LanguageType war2 = LanguageType.addNonUniqueName(war, "Waray");
    // not yet in English Wiktionary:
    // Ati 	ati
    // ------------ eo Visayan


    
    public static final LanguageType vls = new LanguageType("vls", "Vlaams", "Flemish");
    public static final LanguageType vls2 = LanguageType.addNonUniqueName(vls, "West Flemish");

    public static final LanguageType vol = new LanguageType("vol", "Volapük", "Volapük");
    public static final LanguageType vo = LanguageType.addNonUnique(vol, "vo", "Volapuk");

    public static final LanguageType vro = new LanguageType("vro", "Võro", "Võro");
    public static final LanguageType fiu = LanguageType.addNonUniqueCode(vro, "fiu");
    public static final LanguageType fiu_vro = LanguageType.addNonUniqueCode(vro, "fiu-vro");

    public static final LanguageType vun = new LanguageType("vun", "Kivunjo", "Wunjo");
    public static final LanguageType vun2 = LanguageType.addNonUniqueName(vun, "Vunjo");
    public static final LanguageType vun3 = LanguageType.addNonUniqueName(vun, "Kivunjo");

    public static final LanguageType wa = new LanguageType("wa", "Walon", "Walloon");
    public static final LanguageType wln = LanguageType.addNonUniqueCode(wa, "wln");

    public static final LanguageType wal = new LanguageType("wal", "Walamo", "Wolaytta");
    public static final LanguageType wal2 = LanguageType.addNonUniqueName(wal, "Walamo");
    public static final LanguageType wal3 = LanguageType.addNonUniqueName(wal, "Ometo");

    public static final LanguageType waq = new LanguageType("waq", "Wagiman", "Wagiman");
    public static final LanguageType waq2 = LanguageType.addNonUniqueName(waq, "Wageman");

    public static final LanguageType wbi = new LanguageType("wbi", "Wanji", "Wanji");
    public static final LanguageType wbi2 = LanguageType.addNonUniqueName(wbi, "Vwanji");
    


    // Welsh ------------
    public static final LanguageType cy = new LanguageType("cy", "Cymraeg", "Welsh");
    public static final LanguageType cym = LanguageType.addNonUniqueCode(cy, "cym");
    public static final LanguageType wel = LanguageType.addNonUniqueCode(cy, "wel");

    public static final LanguageType owl = new LanguageType("owl", "Hen Gymraeg", "Old Welsh");

    public static final LanguageType wlm = new LanguageType("wlm", "Middle Welsh", "Middle Welsh");
    // ------------ eo Welsh



    public static final LanguageType wim = new LanguageType("wim", "Wik-Mungknh", "Wik-Mungknh");
    public static final LanguageType wim2 = LanguageType.addNonUniqueName(wim, "Wik-Mungkan");

    public static final LanguageType wiv = new LanguageType("wiv", "Vitu", "Vitu");
    public static final LanguageType wiv2 = LanguageType.addNonUniqueName(wiv, "Muduapa");

    public static final LanguageType wo = new LanguageType("wo", "Wolof", "Wolof");
    public static final LanguageType wol = LanguageType.addNonUniqueCode(wo, "wol");
    public static final LanguageType wof = LanguageType.addNonUnique(wo, "wof", "Gambian Wolof");

    public static final LanguageType wrh = new LanguageType("wrh", "Wiradjuri", "Wiradjuri");
    public static final LanguageType wrh2 = LanguageType.addNonUniqueName(wrh, "Wiradhuri");

    public static final LanguageType wyb = new LanguageType("wyb", "Ngiyambaa", "Ngiyambaa");
    public static final LanguageType wyb2 = LanguageType.addNonUniqueName(wyb, "Wangaaybuwan-Ngiyambaa");

    public static final LanguageType xal = new LanguageType("xal", "Kalmyk", "Kalmyk");
    public static final LanguageType xal2 = LanguageType.addNonUniqueName(xal, "Kalmyk-Oirat");

    public static final LanguageType xho = new LanguageType("xho", "isiXhosa", "Xhosa");
    public static final LanguageType xh = LanguageType.addNonUniqueCode(xho, "xh");
    public static final LanguageType xho2 = LanguageType.addNonUniqueName(xho, "Xhosan");

    public static final LanguageType xlu = new LanguageType("xlu", "Luwili", "Luwian");
    public static final LanguageType xlu2 = LanguageType.addNonUniqueName(xlu, "Cuneiform Luwian");
    public static final LanguageType hlu = LanguageType.addNonUnique(xlu, "hlu", "Hieroglyphic Luwian");

    public static final LanguageType xmf = new LanguageType("xmf", "Margaluri nina", "Mingrelian");
    public static final LanguageType xmf2 = LanguageType.addNonUniqueName(xmf, "Megrelian");

    public static final LanguageType xto = new LanguageType("xto", "Tocharian",  "Tocharian");
    public static final LanguageType xto2 = LanguageType.addNonUniqueName(xto,   "Tocharian A");
    public static final LanguageType txb = LanguageType.addNonUnique(xto, "txb", "Tocharian B");

    public static final LanguageType xum = new LanguageType("xum", "Umbrian", "Umbrian");
    public static final LanguageType ims = LanguageType.addNonUnique(xum, "ims", "Marsian");

    public static final LanguageType yag = new LanguageType("yag", "Háusi Kúta",  "Yaghan");
    public static final LanguageType yag2 = LanguageType.addNonUniqueName(yag, "Yámana");
    public static final LanguageType yag3 = LanguageType.addNonUniqueName(yag, "Yagán");

    public static final LanguageType yai = new LanguageType("yai", "яғнобӣ зивок", "Yaghnobi");
    public static final LanguageType yai2 = LanguageType.addNonUniqueName(yai, "Yagnobi");

    public static final LanguageType yi = new LanguageType("yi", "Yiddish", "Yiddish");
    public static final LanguageType yid = LanguageType.addNonUniqueCode(yi, "yid");
    public static final LanguageType ydd = LanguageType.addNonUnique(yi, "ydd", "Eastern Yiddish");
    public static final LanguageType yih = LanguageType.addNonUnique(yi, "yih", "Western Yiddish");

    public static final LanguageType yo = new LanguageType("yo", "Yorùbá", "Yoruba");
    public static final LanguageType yor = LanguageType.addNonUniqueCode(yo, "yor");

    public static final LanguageType yuf = new LanguageType("yuf", "Yavapai", "Yavapai");
    public static final LanguageType yuf2 = LanguageType.addNonUniqueName(yuf, "Havasupai-Walapai-Yavapai");



    // Zhuang ------------ 
    public static final LanguageType za = new LanguageType("za", "Vahcuengh", "Zhuang");
    public static final LanguageType zha = LanguageType.addNonUniqueCode(za, "zha");
    public static final LanguageType zch = LanguageType.addNonUnique(za, "zch", "Central Hongshuihe Zhuang");
    public static final LanguageType zeh = LanguageType.addNonUnique(za, "zeh", "Eastern Hongshuihe Zhuang");
    public static final LanguageType zgb = LanguageType.addNonUnique(za, "zgb", "Guibei Zhuang");
    public static final LanguageType zgm = LanguageType.addNonUnique(za, "zgm", "Minz Zhuang");
    public static final LanguageType zgn = LanguageType.addNonUnique(za, "zgn", "Guibian Zhuang");
    public static final LanguageType zhd = LanguageType.addNonUnique(za, "zhd", "Dai Zhuang");
    public static final LanguageType zhn = LanguageType.addNonUnique(za, "zhn", "Nong Zhuang");
    public static final LanguageType zlj = LanguageType.addNonUnique(za, "zlj", "Liujiang Zhuang");
    public static final LanguageType zln = LanguageType.addNonUnique(za, "zln", "Lianshan Zhuang");
    public static final LanguageType zlq = LanguageType.addNonUnique(za, "zlq", "Liuqian Zhuang");
    public static final LanguageType zqe = LanguageType.addNonUnique(za, "zqe", "Qiubei Zhuang");
    public static final LanguageType zyb = LanguageType.addNonUnique(za, "zyb", "Yongbei Zhuang");
    public static final LanguageType zyg = LanguageType.addNonUnique(za, "zyg", "Yang Zhuang");
    public static final LanguageType zyj = LanguageType.addNonUnique(za, "zyj", "Youjiang Zhuang");
    public static final LanguageType zyn = LanguageType.addNonUnique(za, "zyn", "Yongnan Zhuang");
    public static final LanguageType zzj = LanguageType.addNonUnique(za, "zzj", "Zuojiang Zhuang");
    // ------------ eo Zhuang



    // Zapotec ------------ 
    public static final LanguageType zam = new LanguageType("zam", "Central Mahuatlán Zapoteco", "Central Mahuatlán Zapoteco");
    public static final LanguageType zav = new LanguageType("zav", "Yatzachi Zapotec", "Yatzachi Zapotec");

    public static final LanguageType zpc = new LanguageType("zpc", "Choapan Zapoteco", "Choapan Zapoteco");
    public static final LanguageType zpp = new LanguageType("zpp", "El Alto Zapoteco", "El Alto Zapoteco");
    public static final LanguageType zpq = new LanguageType("zpq", "Zoogocho Zapotec", "Zoogocho Zapotec");

    public static final LanguageType ztu = new LanguageType("ztu", "San Pablo Güilá Zapotec", "San Pablo Güilá Zapotec");
    public static final LanguageType ztx = new LanguageType("ztx", "Zaachila Zapoteco", "Zaachila Zapoteco");
    // ------------ eo Zapotec



    // Chinese ------------
    public static final LanguageType zh = new LanguageType("zh", "Chinese", "Chinese");
    public static final LanguageType zho = LanguageType.addNonUniqueCode(zh, "zho");

    public static final LanguageType cmn = new LanguageType("cmn", "Mandarin", "Mandarin");
    public static final LanguageType cmn2 = LanguageType.addNonUniqueName(cmn, "Mandarin Chinese");
    public static final LanguageType cmn3 = LanguageType.addNonUniqueName(cmn, "Putonghua");

    public static final LanguageType czh = LanguageType.addNonUnique(zh, "czh", "Huizhou");
    public static final LanguageType pinyin = LanguageType.addNonUnique(zh, "pinyin", "Pinyin");// Russian Wiktionary

    public static final LanguageType cdo = new LanguageType("cdo", "Min Dong", "Min Dong");
    
    public static final LanguageType cjy = new LanguageType("cjy", "Jinyu", "Jinyu");
    public static final LanguageType cjy2 = LanguageType.addNonUniqueName(cjy, "Jin Chinese");
    public static final LanguageType cjy3 = LanguageType.addNonUniqueName(cjy, "Jin-yu");

    public static final LanguageType cpi = new LanguageType("cpi", "Chinese Pidgin English", "Chinese Pidgin English");

    public static final LanguageType hak = new LanguageType("hak", "Hakka", "Hakka");

    public static final LanguageType gan = new LanguageType("gan", "Gàn", "Gan");

    public static final LanguageType ltc = new LanguageType("ltc", "Middle Chinese", "Middle Chinese");
    public static final LanguageType ltc2 = LanguageType.addNonUnique(ltc, "zhx-mid", "Ancient Chinese");
    public static final LanguageType ltc3 = LanguageType.addNonUniqueName(ltc, "Late Middle Chinese");

    public static final LanguageType mnp = new LanguageType("mnp", "Min Bei", "Min Bei");
    public static final LanguageType mnp2 = LanguageType.addNonUniqueName(mnp, "Northern Min");
    public static final LanguageType mnp3 = LanguageType.addNonUniqueName(mnp, "Min Pei");

    public static final LanguageType cpx = new LanguageType("cpx", "Pu-Xian", "Pu-Xian");
    public static final LanguageType cpx2 = LanguageType.addNonUniqueName(cpx, "Puxian Min");
    public static final LanguageType cpx3 = LanguageType.addNonUniqueName(cpx, "Puxian");
    public static final LanguageType cpx4 = LanguageType.addNonUniqueName(cpx, "Xinghua");

    public static final LanguageType wuu = new LanguageType("wuu", "Wu", "Wu");

            // todo : enwikt:template:zh-ts -> trad. (zh-tw), simpl. (zh-cn)
    public static final LanguageType zh_tw = new LanguageType("zh-tw", "Traditional Chinese", "Traditional Chinese");
    public static final LanguageType zh_hant = LanguageType.addNonUniqueCode(zh_tw, "zh-hant");// Chinese written using the Traditional Chinese script

    public static final LanguageType zh_cn = new LanguageType("zh-cn", "Simplified Chinese", "Simplified Chinese");
    public static final LanguageType zh_hans = LanguageType.addNonUnique(zh_cn, "zh-hans", "Chinese (PRC)");// Chinese written using the Simplified Chinese script

    public static final LanguageType lzh = new LanguageType("lzh", "Classical Chinese", "Classical Chinese");
    public static final LanguageType och = LanguageType.addNonUnique(lzh, "och", "Old Chinese");
    public static final LanguageType zh_classical = LanguageType.addNonUnique(lzh, "zh-classical", "Literary Chinese");

    public static final LanguageType nan = new LanguageType("nan", "Min Nan", "Min Nan");// Min Nan, Minnan, or Min-nan, Southern Min
    public static final LanguageType zh_min_nan = LanguageType.addNonUniqueCode(nan, "zh-min-nan");
    public static final LanguageType zh_nan = LanguageType.addNonUniqueCode(nan, "zh-nan");

    public static final LanguageType czo = new LanguageType("czo", "Min Zhong", "Min Zhong");
    public static final LanguageType czo2 = LanguageType.addNonUniqueName(czo, "Central Min");

    public static final LanguageType yue = new LanguageType("yue", "Cantonese", "Cantonese");
    public static final LanguageType zh_yue = LanguageType.addNonUnique(yue, "zh-yue", "Yüeh");
    public static final LanguageType yue2 = LanguageType.addNonUniqueName(yue, "Yue");
    // ------------ eo Chinese



    public static final LanguageType zu = new LanguageType("zu", "isiZulu", "Zulu");
    public static final LanguageType zul = LanguageType.addNonUniqueCode(zu, "zul");



    // Zazaki ------------
    public static final LanguageType zza = new LanguageType("zza", "Zazaki", "Zazaki");
    
    public static final LanguageType kiu = new LanguageType("kiu", "Kirmanjki", "Kirmanjki");
    public static final LanguageType kiu2 = LanguageType.addNonUniqueName(kiu, "Northern Zazaki");

    public static final LanguageType diq = new LanguageType("diq", "Dimli", "Dimli");
    public static final LanguageType diq2 = LanguageType.addNonUniqueName(diq, "Southern Zazaki");
    // ------------ eo Zazaki






    // Russian Wiktionary specific codes
    // 1. todo check errors:  has too long unknown language code
    // 2. todo error:  unknown language code
    
    public static final LanguageType letter_ru = new LanguageType("Буква", "Letter", "Letter");
    public static final LanguageType bagua  = new LanguageType("bagua", "Ba gua", "Ba gua");
    public static final LanguageType hanzi  = new LanguageType("hanzi", "Chinese character", "Chinese character");

    public static final LanguageType abq = new LanguageType("abq", "Abaza", "Abaza");
    public static final LanguageType ady    = new LanguageType("ady", "Adyghe", "Adyghe");
    
    public static final LanguageType agf    = new LanguageType("agf", "Arguni", "Arguni");

    public static final LanguageType aie    = new LanguageType("aie", "Amara", "Amara");

    public static final LanguageType aja    = new LanguageType("aja", "Aja (Sudan)", "Aja (Sudan)");
    public static final LanguageType ajg    = new LanguageType("ajg", "Aja (Benin)", "Aja (Benin)");

    public static final LanguageType alp    = new LanguageType("alp", "Alune", "Alune");
    
    public static final LanguageType aqc = new LanguageType("aqc", "Archi", "Archi");
    
    public static final LanguageType art_oou= new LanguageType("art-oou", "oou", "oou");

    public static final LanguageType asm = new LanguageType("asm", "Assamese", "Assamese");
    public static final LanguageType as = LanguageType.addNonUniqueCode(asm, "as");
    
    public static final LanguageType bdk    = new LanguageType("bdk", "Budukh", "Budukh");
    public static final LanguageType bib    = new LanguageType("bib", "Bissa", "Bissa");
    public static final LanguageType bph    = new LanguageType("bph", "Botlikh", "Botlikh");
    public static final LanguageType byn    = new LanguageType("byn", "Blin", "Blin");

    public static final LanguageType cel = new LanguageType("cel", "Tselinsky", "Tselinsky");// Целинский - in English?
    public static final LanguageType chg    = new LanguageType("chg", "Chagatai", "Chagatai");

    public static final LanguageType chm = new LanguageType("chm", "марий йылме", "Mari");
    public static final LanguageType mhr = LanguageType.addNonUnique(chm, "mhr", "Eastern Mari");
    public static final LanguageType mhr2 = LanguageType.addNonUniqueName(chm, "Meadow Mari");
    public static final LanguageType mrj = LanguageType.addNonUnique(chm, "mrj", "Western Mari");

    public static final LanguageType cjs = new LanguageType("cjs", "Shor", "Shor");
    public static final LanguageType ckt = new LanguageType("ckt", "Chukchi", "Chukchi");

    public static final LanguageType de_a   = new LanguageType("de-a", "de-a", "de-a");
    public static final LanguageType dlg    = new LanguageType("dlg", "Dolgan", "Dolgan");
    public static final LanguageType dng    = new LanguageType("dng", "Dungan", "Dungan");
    
    public static final LanguageType eve    = new LanguageType("eve", "Even", "Even");
    public static final LanguageType evn    = new LanguageType("evn", "Evenki", "Evenki");

    public static final LanguageType fic_drw = new LanguageType("fic-drw", "Drow (Dungeons & Dragons)", "Drow (Dungeons & Dragons)");// Russian Wiktionary

    public static final LanguageType ium    = new LanguageType("ium", "Iu Mien", "Iu Mien");
    public static final LanguageType itl    = new LanguageType("itl", "Itelmen", "Itelmen");
    public static final LanguageType izh    = new LanguageType("izh", "Ingrian", "Ingrian");

    public static final LanguageType jct    = new LanguageType("jct", "Krymchak", "Krymchak");

    public static final LanguageType kca    = new LanguageType("kca", "Khanty", "Khanty");
    public static final LanguageType kdr    = new LanguageType("kdr", "Karaim", "Karaim");
    public static final LanguageType ket    = new LanguageType("ket", "Ket", "Ket");

    public static final LanguageType kim    = new LanguageType("kim", "Tofa", "Tofa");

    public static final LanguageType kpy = new LanguageType("kpy", "Koryak", "Koryak");

    public static final LanguageType ppol   = new LanguageType("ppol", "Proto-Polynesian", "Proto-Polynesian");
    public static final LanguageType psl    = new LanguageType("psl", "Proto-Slavic", "Proto-Slavic");

    public static final LanguageType qya    = new LanguageType("qya", "Quenya", "Quenya");

    public static final LanguageType rmr    = new LanguageType("rmr", "Calo", "Calo");
    public static final LanguageType romaji = new LanguageType("romaji", "Romaji", "Romaji");
    public static final LanguageType ru_old = new LanguageType("ru-old", "Russian (before 1917)", "Russian (before 1917)");
    
    public static final LanguageType sjn    = new LanguageType("sjn", "Sindarin", "Sindarin");
    public static final LanguageType solresol = new LanguageType("solresol", "Solresol", "Solresol");
    public static final LanguageType sol = LanguageType.addNonUniqueCode(solresol, "sol");
    
    public static final LanguageType tly    = new LanguageType("tly", "Talysh", "Tokelau");
    public static final LanguageType ttt    = new LanguageType("ttt", "Tat", "Tat");    

    public static final LanguageType xrn = new LanguageType("xrn", "Arin", "Arin");

    public static final LanguageType ykg = new LanguageType("ykg", "wadul", "Tundra Yukaghir");
    public static final LanguageType ykg2 = LanguageType.addNonUniqueName(solresol, "Northern Yukaghir");
    public static final LanguageType yux = new LanguageType("yux", "Southern Yukaghir", "Southern Yukaghir");
    



    
    // manually added languages:
    public static final LanguageType aaa = new LanguageType("aaa", "Ghotuo", "Ghotuo");
    public static final LanguageType aab = new LanguageType("aab", "Arum-Tesu", "Arum-Tesu");
    public static final LanguageType aak = new LanguageType("aak", "Ankave", "Ankave");

    public static final LanguageType abl = new LanguageType("abl", "Abung", "Abung");
    public static final LanguageType abm = new LanguageType("abm", "Abanyom", "Abanyom");

    public static final LanguageType ach = new LanguageType("ach", "Acholi", "Acholi");
    
    public static final LanguageType ade = new LanguageType("ade", "Adele", "Adele");
    public static final LanguageType adj = new LanguageType("adj", "Adioukrou", "Adioukrou");
    public static final LanguageType adt = new LanguageType("adt", "Adnyamathanha", "Adnyamathanha");

    public static final LanguageType agh = new LanguageType("agh", "Ngelima", "Ngelima");
    public static final LanguageType agj = new LanguageType("agj", "Argobba", "Argobba");
    public static final LanguageType ahs = new LanguageType("ahs", "Ashe", "Ashe");
    public static final LanguageType aiw = new LanguageType("aiw", "Aari", "Aari");
    public static final LanguageType aji = new LanguageType("aji", "Ajië", "Ajië");

    public static final LanguageType ake = new LanguageType("ake", "Akawaio", "Akawaio");
    public static final LanguageType akg = new LanguageType("akg", "Anakalangu", "Anakalangu");
    public static final LanguageType akk = new LanguageType("akk", "Akkadian", "Akkadian");
    
    public static final LanguageType akz = new LanguageType("akz", "Alabama", "Alabama");
    public static final LanguageType alc = new LanguageType("alc", "Qawasqar", "Qawasqar");
    public static final LanguageType ali = new LanguageType("ali", "Amaimon", "Amaimon");
    public static final LanguageType alu = new LanguageType("alu", "'Are'are", "'Are'are");
    public static final LanguageType amk = new LanguageType("amk", "Ambai", "Ambai");
    public static final LanguageType amn = new LanguageType("amn", "Amanab", "Amanab");
    public static final LanguageType amt = new LanguageType("amt", "Amto", "Amto");
    public static final LanguageType amu = new LanguageType("amu", "Amuzgo", "Amuzgo");
    public static final LanguageType and = new LanguageType("and", "Ansus", "Ansus");
    public static final LanguageType ani = new LanguageType("ani", "къIaваннаб мицци", "Andi");
    public static final LanguageType ant = new LanguageType("ant", "Antakarinya", "Antakarinya");
    public static final LanguageType apj = new LanguageType("apj", "Jicarilla", "Jicarilla");
    public static final LanguageType apl = new LanguageType("apl", "Lipan", "Lipan");
    public static final LanguageType apm = new LanguageType("apm", "Chiricahua", "Chiricahua");
    public static final LanguageType apy = new LanguageType("apy", "Apalaí", "Apalaí");
    public static final LanguageType arl = new LanguageType("arl", "Arabela", "Arabela");
    public static final LanguageType arn = new LanguageType("arn", "Mapudungun", "Mapudungun");
    public static final LanguageType arp = new LanguageType("arp", "Arapaho", "Arapaho");
    public static final LanguageType arw = new LanguageType("arw", "Arawak", "Arawak");
    public static final LanguageType aty = new LanguageType("aty", "Aneityum", "Aneityum");
    public static final LanguageType awa = new LanguageType("awa", "Awadhi", "Awadhi");

    public static final LanguageType brg = new LanguageType("brg", "Baure", "Baure");
    public static final LanguageType bdp = new LanguageType("bdp", "Bende", "Bende");
    public static final LanguageType bdy = new LanguageType("bdy", "Bandjalang", "Bandjalang");
    public static final LanguageType bej = new LanguageType("bej", "Beja", "Beja");
    public static final LanguageType bem = new LanguageType("bem", "Bemba", "Bemba");
    public static final LanguageType bew = new LanguageType("bew", "Betawi", "Betawi");
    public static final LanguageType bft = new LanguageType("bft", "Balti", "Balti");
    public static final LanguageType bgc = new LanguageType("bgc", "Haryanvi", "Haryanvi");
    public static final LanguageType bhw = new LanguageType("bhw", "Biak", "Biak");
    public static final LanguageType bin = new LanguageType("bin", "Bini", "Bini");
    public static final LanguageType bjn = new LanguageType("bjn", "Bahasa Banjar", "Banjarese");
    public static final LanguageType bjz = new LanguageType("bjz", "Baruga", "Baruga");
    public static final LanguageType bku = new LanguageType("bku", "Buhid", "Buhid");
    public static final LanguageType bla = new LanguageType("bla", "Blackfoot", "Blackfoot");
    public static final LanguageType blt = new LanguageType("blt", "Tai Dam", "Tai Dam");
    public static final LanguageType bns = new LanguageType("bns", "Bundeli", "Bundeli");
    public static final LanguageType bot = new LanguageType("bot", "Bongo", "Bongo");
    public static final LanguageType bou = new LanguageType("bou", "Bondei", "Bondei");
    public static final LanguageType bpl = new LanguageType("bpl", "Broome Pearling Lugger Pidgin", "Broome Pearling Lugger Pidgin");
    public static final LanguageType bpy = new LanguageType("bpy", "Bishnupriya Manipuri", "Bishnupriya Manipuri");
    public static final LanguageType brh = new LanguageType("brh", "Brahui", "Brahui");
    public static final LanguageType brt = new LanguageType("brt", "Bitare", "Bitare");
    public static final LanguageType brx = new LanguageType("brx", "Bodo", "Bodo");
    public static final LanguageType bsb = new LanguageType("bsb", "Brunei Bisaya", "Brunei Bisaya");
    public static final LanguageType bvb = new LanguageType("bvb", "Bube", "Bube");

    public static final LanguageType cab = new LanguageType("cab", "Garifuna", "Garifuna");
    public static final LanguageType cad = new LanguageType("cad", "Caddo", "Caddo");
    public static final LanguageType cax = new LanguageType("cax", "Bésiro", "Chiquitano");
    public static final LanguageType ccc = new LanguageType("ccc", "Chamicuro", "Chamicuro");
    public static final LanguageType cia = new LanguageType("cia", "바하사 찌아찌아", "Cia-Cia");
    public static final LanguageType cgg = new LanguageType("cgg", "Rukiga", "Rukiga");
    public static final LanguageType chb = new LanguageType("chb", "Chibcha", "Chibcha");
    public static final LanguageType chc = new LanguageType("chc", "Catawba", "Catawba");
    public static final LanguageType chl = new LanguageType("chl", "Cahuilla", "Cahuilla");
    public static final LanguageType chn = new LanguageType("chn", "Chinook Jargon", "Chinook Jargon");
    public static final LanguageType cho = new LanguageType("cho", "Choctaw", "Choctaw");
    public static final LanguageType chr = new LanguageType("chr", "Tsalagi Gawonihisdi", "Cherokee");
    public static final LanguageType chy = new LanguageType("chy", "Tsėhesenėstsestotse", "Cheyenne");
    public static final LanguageType cic = new LanguageType("cic", "Chickasaw", "Chickasaw");
    public static final LanguageType com = new LanguageType("com", "Comanche", "Comanche");
    public static final LanguageType coo = new LanguageType("coo", "Comox", "Comox");
    public static final LanguageType cop = new LanguageType("cop", "Coptic", "Coptic");
    public static final LanguageType cow = new LanguageType("cow", "Cowlitz", "Cowlitz");
    public static final LanguageType cpe_spp = new LanguageType("cpe-spp", "Samoan Plantation Pidgin", "Samoan Plantation Pidgin");// enwikt
    public static final LanguageType crp_tpr = new LanguageType("crp-tpr", "Taimyr Pidgin Russian", "Taimyr Pidgin Russian");
    public static final LanguageType crw = new LanguageType("crw", "Chrau", "Chrau");
    public static final LanguageType ctg = new LanguageType("ctg", "Chittagonian", "Chittagonian");
    public static final LanguageType ctu = new LanguageType("ctu", "Chol", "Chol");
    public static final LanguageType cui = new LanguageType("cui", "Cuiba", "Cuiba");
    public static final LanguageType cwe = new LanguageType("cwe", "Kwere", "Kwere");

    public static final LanguageType dak = new LanguageType("dak", "Dakota", "Dakota");
    public static final LanguageType dav = new LanguageType("dav", "Taita", "Taita");
    public static final LanguageType dbj = new LanguageType("dbj", "Ida'an", "Ida'an");
    public static final LanguageType dbl = new LanguageType("dbl", "Dyirbal", "Dyirbal");
    public static final LanguageType dgr = new LanguageType("dgr", "Dogrib", "Dogrib");
    public static final LanguageType dif = new LanguageType("dif", "Dieri", "Dieri");
    public static final LanguageType dim = new LanguageType("dim", "Dime", "Dime");
    public static final LanguageType din = new LanguageType("din", "Dinka", "Dinka");
    public static final LanguageType dlm = new LanguageType("dlm", "Dalmatian", "Dalmatian");
    public static final LanguageType doe = new LanguageType("doe", "Doe", "Doe");
    public static final LanguageType doz = new LanguageType("doz", "Dorze", "Dorze");

    public static final LanguageType drl = new LanguageType("drl", "Darling", "Darling");
    public static final LanguageType dsn = new LanguageType("dsn", "Dusner", "Dusner");
    public static final LanguageType dua = new LanguageType("dua", "Duala", "Duala");
    public static final LanguageType dub = new LanguageType("dub", "Dubli", "Dubli");

    public static final LanguageType ebu = new LanguageType("ebu", "Embu", "Embu");
    public static final LanguageType egy = new LanguageType("egy", "Egyptian", "Egyptian");
    public static final LanguageType eka = new LanguageType("eka", "Ekajuk", "Ekajuk");
    public static final LanguageType elx = new LanguageType("elx", "Elamite", "Elamite");
    public static final LanguageType ett = new LanguageType("ett", "Etruscan", "Etruscan");
    public static final LanguageType ewo = new LanguageType("ewo", "Ewondo", "Ewondo");
    public static final LanguageType ext = new LanguageType("ext", "Estremeñu", "Extremaduran");

    public static final LanguageType fip = new LanguageType("fip", "Fipa", "Fipa");
    public static final LanguageType fit = new LanguageType("fit", "Meänkieli", "Meänkieli");
    public static final LanguageType fkv = new LanguageType("fkv", "Kven", "Kven");
    public static final LanguageType fla = new LanguageType("fla", "Kalispel-Pend d'Oreille", "Kalispel-Pend d'Oreille");
    public static final LanguageType fon = new LanguageType("fon", "Fon", "Fon");
    public static final LanguageType for_ = new LanguageType("for", "Fore", "Fore");
    public static final LanguageType frp = new LanguageType("frp", "Arpetan", "Franco-Provençal");
    public static final LanguageType fuc = new LanguageType("fuc", "Pulaar", "Pulaar");
    public static final LanguageType fur = new LanguageType("fur", "Furlan", "Friulian");

    public static final LanguageType gaa = new LanguageType("gaa", "Ga", "Ga");
    public static final LanguageType gag = new LanguageType("gag", "Gagauz", "Gagauz");
    public static final LanguageType gay = new LanguageType("gay", "Gayo", "Gayo");
    public static final LanguageType gdm = new LanguageType("gdm", "Laal", "Laal");
    public static final LanguageType gil = new LanguageType("gil", "Gilbertese", "Gilbertese");
    public static final LanguageType gld = new LanguageType("gld", "Nanai", "Nanai");
    public static final LanguageType glk = new LanguageType("glk", "Gilaki", "Gilaki");
    public static final LanguageType gnd = new LanguageType("gnd", "Zulgo-Gemzek", "Zulgo-Gemzek");
    public static final LanguageType gni = new LanguageType("gni", "Gooniyandi", "Gooniyandi");
    public static final LanguageType gon = new LanguageType("gon", "Gōndi", "Gondi");
    public static final LanguageType got = new LanguageType("got", "Gothic", "Gothic");
    public static final LanguageType grb = new LanguageType("grb", "Grebo", "Grebo");
    public static final LanguageType gul = new LanguageType("gul", "Gullah", "Gullah");
    public static final LanguageType guz = new LanguageType("guz", "Gusii", "Gusii");
    public static final LanguageType gvf = new LanguageType("gvf", "Golin", "Golin");
    public static final LanguageType gwc = new LanguageType("gwc", "Kalami", "Kalami");
    public static final LanguageType gwe = new LanguageType("gwe", "Gweno", "Gweno");
    public static final LanguageType gwi = new LanguageType("gwi", "Gwich’in", "Gwich’in");
    public static final LanguageType gwr = new LanguageType("gwr", "Gwere", "Gwere");

    public static final LanguageType hai = new LanguageType("hai", "Haida", "Haida");
    public static final LanguageType han = new LanguageType("han", "Hangaza", "Hangaza");
    public static final LanguageType haq = new LanguageType("haq", "Ha", "Ha");
    public static final LanguageType har = new LanguageType("har", "Harari", "Harari");
    public static final LanguageType haw = new LanguageType("haw", "Hawai`i", "Hawaiian");
    public static final LanguageType hay = new LanguageType("hay", "OluHaya", "Haya");
    public static final LanguageType heh = new LanguageType("heh", "Hehe", "Hehe");
    public static final LanguageType hit = new LanguageType("hit", "Hittite", "Hittite");
    public static final LanguageType hmn = new LanguageType("hmn", "Hmong", "Hmong");
    public static final LanguageType hop = new LanguageType("hop", "Hopi", "Hopi");
    public static final LanguageType hsn = new LanguageType("hsn", "Xiang", "Xiang");
    public static final LanguageType huh = new LanguageType("huh", "Huilliche", "Huilliche");
    public static final LanguageType hup = new LanguageType("hup", "Na:tinixwe Mixine:whe", "Hupa");
    public static final LanguageType huq = new LanguageType("huq", "Tsat", "Tsat");
    public static final LanguageType hur = new LanguageType("hur", "Halkomelem", "Halkomelem");

    public static final LanguageType ith_lat = new LanguageType("ith_lat", "Ithkuil", "Ithkuil");// Without dot for D2RQ
    public static final LanguageType ith_lat2 = LanguageType.addNonUniqueCode(ith_lat, "ith.lat");// Russian Wiktionary
    public static final LanguageType iba = new LanguageType("iba", "Iban", "Iban");
    public static final LanguageType ikz = new LanguageType("ikz", "Ikizu", "Ikizu");
    public static final LanguageType inh = new LanguageType("inh", "ГIалгIай", "Ingush");
    public static final LanguageType ish = new LanguageType("ish", "Esan", "Esan");
    public static final LanguageType ist = new LanguageType("ist", "Istriot", "Istriot");

    public static final LanguageType jao = new LanguageType("jao", "Yanyuwa", "Yanyuwa");
    public static final LanguageType jbo = new LanguageType("jbo", "Lojban", "Lojban");
    public static final LanguageType jit = new LanguageType("jit", "Jita", "Jita");
    public static final LanguageType jmc = new LanguageType("jmc", "Machame", "Machame");
    public static final LanguageType juc = new LanguageType("juc", "Jurchen", "Jurchen");

    public static final LanguageType kaa = new LanguageType("kaa", "Qaraqalpaqsha", "Karakalpak");
    public static final LanguageType kab = new LanguageType("kab", "Taqbaylit", "Kabyle");
    public static final LanguageType kac = new LanguageType("kac", "Jingpho", "Jingpho");
    public static final LanguageType kam = new LanguageType("kam", "Kamba", "Kamba");
    public static final LanguageType kbc = new LanguageType("kbc", "Kadiwéu", "Kadiwéu");
    public static final LanguageType kbd = new LanguageType("kbd", "Kabardian", "Kabardian");
    public static final LanguageType kbf = new LanguageType("kbf", "Kakauhua", "Kakauhua");
    public static final LanguageType kcn = new LanguageType("kcn", "Nubi", "Nubi");
    public static final LanguageType kda = new LanguageType("kda", "Worimi", "Worimi");
    public static final LanguageType kdc = new LanguageType("kdc", "Kutu", "Kutu");
    public static final LanguageType kdd = new LanguageType("kdd", "Yankunytjatjara", "Yankunytjatjara");
    public static final LanguageType kde = new LanguageType("kde", "Makonde", "Makonde");
    public static final LanguageType ked = new LanguageType("ked", "Wakerewe", "Kerewe");
    public static final LanguageType kgg = new LanguageType("kgg", "Kusunda", "Kusunda");
    public static final LanguageType kha = new LanguageType("kha", "Khasi", "Khasi");
    public static final LanguageType kho = new LanguageType("kho", "Khotanese", "Khotanese");
    public static final LanguageType khw = new LanguageType("khw", "Khowar", "Khowar");
    public static final LanguageType kiv = new LanguageType("kiv", "Kimbu", "Kimbu");
    public static final LanguageType kiz = new LanguageType("kiz", "Kisi", "Kisi");
    public static final LanguageType kjh = new LanguageType("kjh", "Khakas", "Khakas");
    public static final LanguageType kjr = new LanguageType("kjr", "Kurudu", "Kurudu");
    public static final LanguageType kju = new LanguageType("kju", "Kashaya", "Kashaya");
    public static final LanguageType kld = new LanguageType("kld", "Gamilaraay", "Gamilaraay");
    public static final LanguageType kln = new LanguageType("kln", "Kalenjin", "Kalenjin");
    public static final LanguageType kmb = new LanguageType("kmb", "Kimbundu", "Kimbundu");
    public static final LanguageType kos = new LanguageType("kos", "Kosraean", "Kosraean");
    public static final LanguageType kpg = new LanguageType("kpg", "Kapingamarangi", "Kapingamarangi");
    public static final LanguageType krc = new LanguageType("krc", "Karachay-Balkar", "Karachay-Balkar");
    public static final LanguageType krh = new LanguageType("krh", "Kurama", "Kurama");
    public static final LanguageType kri = new LanguageType("kri", "Krio", "Krio");
    public static final LanguageType krl = new LanguageType("krl", "Karjalan kieli", "Karelian");
    public static final LanguageType kru = new LanguageType("kru", "Kurukh", "Kurukh");
    public static final LanguageType ksb = new LanguageType("ksb", "Shambala", "Shambala");

    public static final LanguageType ksh_c_a = new LanguageType("ksh-c-a", "Ripoarisch c a", "Ripuarian c a");
    public static final LanguageType ksh_p_b = new LanguageType("ksh-p-b", "Ripoarisch p b", "Ripuarian p b");

    public static final LanguageType ktn = new LanguageType("ktn", "Karitiâna", "Karitiâna");
    public static final LanguageType kuc = new LanguageType("kuc", "Kwinsu", "Kwinsu");
    public static final LanguageType kud = new LanguageType("kud", "'Auhelawa", "'Auhelawa");
    public static final LanguageType kuj = new LanguageType("kuj", "Kuria", "Kuria");
    public static final LanguageType kum = new LanguageType("kum", "Къумукъ", "Kumyk");
    public static final LanguageType kut = new LanguageType("kut", "Kutenai", "Kutenai");
    public static final LanguageType kya = new LanguageType("kya", "Kwaya", "Kwaya");
    public static final LanguageType kyh = new LanguageType("kyh", "Karok", "Karok");
    public static final LanguageType kyi = new LanguageType("kyi", "Kiput", "Kiput");
    public static final LanguageType kzg = new LanguageType("kzg", "Kikai", "Kikai");

    public static final LanguageType lai = new LanguageType("lai", "Lambya", "Lambya");
    public static final LanguageType lam = new LanguageType("lam", "Lamba", "Lamba");
    public static final LanguageType lbe = new LanguageType("lbe", "Лакку", "Lak");
    public static final LanguageType lep = new LanguageType("lep", "Lepcha", "Lepcha");
    public static final LanguageType lex = new LanguageType("lex", "Luang", "Luang");
    public static final LanguageType lfn = new LanguageType("lfn", "Lingua Franca Nova", "Lingua Franca Nova");
    public static final LanguageType lij = new LanguageType("lij", "Ligurian", "Ligurian");
    public static final LanguageType liv = new LanguageType("liv", "Livonian", "Livonian");
    public static final LanguageType lld = new LanguageType("lld", "Ladin", "Ladin");
    public static final LanguageType lmo = new LanguageType("lmo", "Lumbaart", "Lombard");
    public static final LanguageType lng = new LanguageType("lng", "Lombardic", "Lombardic");
    public static final LanguageType lol = new LanguageType("lol", "Mongo", "Mongo");
    public static final LanguageType lou = new LanguageType("lou", "Louisiana Creole French", "Louisiana Creole French");
    public static final LanguageType loz = new LanguageType("loz", "Silozi", "Lozi");
    public static final LanguageType lre = new LanguageType("lre", "Laurentian", "Laurentian");
    public static final LanguageType lun = new LanguageType("lun", "Chilunda", "Lunda");
    public static final LanguageType lvk = new LanguageType("lvk", "Lavukaleve", "Lavukaleve");
    public static final LanguageType lzz = new LanguageType("lzz", "Lazuri Nena", "Laz");

    public static final LanguageType mad = new LanguageType("mad", "Madurese", "Madurese");
    public static final LanguageType mаs = new LanguageType("mas", "Maasai", "Maasai");
    public static final LanguageType mbc = new LanguageType("mbc", "Macushi", "Macushi");
    public static final LanguageType mdf = new LanguageType("mdf", "Мокшень", "Moksha");
    public static final LanguageType mdr = new LanguageType("mdr", "Mandar", "Mandar");
    public static final LanguageType men = new LanguageType("men", "Mende", "Mende");
    public static final LanguageType mer = new LanguageType("mer", "Meru", "Meru");
    public static final LanguageType meu = new LanguageType("meu", "Motu", "Motu");
    public static final LanguageType mfe = new LanguageType("mfe", "Kreol Morisyen", "Mauritian Creole");
    public static final LanguageType mfn = new LanguageType("mfn", "Cross River Mbembe", "Cross River Mbembe");
    public static final LanguageType mgh = new LanguageType("mgh", "Makhuwa-Meetto", "Makhuwa-Meetto");
    public static final LanguageType mgm = new LanguageType("mgm", "Mambae", "Mambae");
    public static final LanguageType mgq = new LanguageType("mgq", "Malila", "Malila");
    public static final LanguageType mgr = new LanguageType("mgr", "Mambwe-Lungu", "Mambwe-Lungu");
    public static final LanguageType mgv = new LanguageType("mgv", "Matengo", "Matengo");
    public static final LanguageType mgw = new LanguageType("mgw", "Matumbi", "Matumbi");
    public static final LanguageType mgy = new LanguageType("mgy", "Mbunga", "Mbunga");
    public static final LanguageType mjg = new LanguageType("mjg", "moŋɡuer", "Monguor");
    public static final LanguageType mic = new LanguageType("mic", "Mi'kmaq", "Mi'kmaq");
    public static final LanguageType min = new LanguageType("min", "Minangkabau", "Minangkabau");
    public static final LanguageType miq = new LanguageType("miq", "Mískitu", "Miskito");
    public static final LanguageType mnc = new LanguageType("mnc", "Manchu", "Manchu");
    public static final LanguageType mns = new LanguageType("mns", "Mansi", "Mansi");
    public static final LanguageType moh = new LanguageType("moh", "Mohawk", "Mohawk");
    public static final LanguageType mpa = new LanguageType("mpa", "Mpoto", "Mpoto");
    public static final LanguageType mpl = new LanguageType("mpl", "Middle Watut", "Middle Watut");
    public static final LanguageType mpm = new LanguageType("mpm", "Yosondúa Mixtec", "Yosondúa Mixtec");
    public static final LanguageType mps = new LanguageType("mps", "Dadibi", "Dadibi");
    public static final LanguageType mrc = new LanguageType("mrc", "Piipaash chuukwer", "Maricopa");
    public static final LanguageType mth = new LanguageType("mth", "Munggui", "Munggui");
    public static final LanguageType mtm = new LanguageType("mtm", "Mator", "Mator");
    public static final LanguageType mus = new LanguageType("mus", "Mvskoke", "Creek");
    public static final LanguageType mvi = new LanguageType("mvi", "Miyako", "Miyako");
    public static final LanguageType mvr = new LanguageType("mvr", "Marau", "Marau");
    public static final LanguageType mwf = new LanguageType("mwf", "Murrinh-Patha", "Murrinh-Patha");
    public static final LanguageType mwl = new LanguageType("mwl", "Mirandés", "Mirandese");
    public static final LanguageType mwp = new LanguageType("mwp", "Kala Lagaw Ya", "Kala Lagaw Ya");
    public static final LanguageType mxi = new LanguageType("mxi", "Mozarabic", "Mozarabic");
    public static final LanguageType mxx = new LanguageType("mxx", "Mahou", "Mahou");
    public static final LanguageType myh = new LanguageType("myh", "qʷi·qʷi·diččaq", "Makah");
    public static final LanguageType myp = new LanguageType("myp", "Pirahã", "Pirahã");
    public static final LanguageType myv = new LanguageType("myv", "Эрзянь", "Erzya");

    public static final LanguageType nap = new LanguageType("nap", "Napulitano", "Neapolitan");
    public static final LanguageType nay = new LanguageType("nay", "Ngarrindjeri", "Ngarrindjeri");
    public static final LanguageType nbm = new LanguageType("nbm", "Ngbaka Ma'bo", "Ngbaka Ma'bo");
    public static final LanguageType ndg = new LanguageType("ndg", "Ndengereko", "Ndengereko");
    public static final LanguageType ndh = new LanguageType("ndh", "Ndali", "Ndali");
    public static final LanguageType ndj = new LanguageType("ndj", "Ndamba", "Ndamba");
    public static final LanguageType ngo = new LanguageType("ngo", "Ngoni", "Ngoni");
    public static final LanguageType ngq = new LanguageType("ngq", "Ngoreme", "Ngoreme");
    public static final LanguageType nha = new LanguageType("nha", "Nhanda", "Nhanda");
    public static final LanguageType nia = new LanguageType("nia", "Nias", "Nias");
    public static final LanguageType nim = new LanguageType("nim", "Nilamba", "Nilamba");
    public static final LanguageType nio = new LanguageType("nio", "Nganasan", "Nganasan");
    public static final LanguageType niu = new LanguageType("niu", "Niuean", "Niuean");
    public static final LanguageType nnq = new LanguageType("nnq", "Ngindo", "Ngindo");
    public static final LanguageType nog = new LanguageType("nog", "Nogai", "Nogai");
    public static final LanguageType nom = new LanguageType("nom", "Nocamán", "Nocamán");
    public static final LanguageType nov = new LanguageType("nov", "Novial", "Novial");
    public static final LanguageType now = new LanguageType("now", "Nyambo", "Nyambo");
    public static final LanguageType nqo = new LanguageType("nqo", "N'Ko", "N'Ko");
    public static final LanguageType nrn = new LanguageType("nrn", "Norn", "Norn");
    public static final LanguageType ntj = new LanguageType("ntj", "Ngaanyatjarra", "Ngaanyatjarra");
    public static final LanguageType num = new LanguageType("num", "Niuafo'ou", "Niuafo'ou");
    public static final LanguageType nxe = new LanguageType("nxe", "Nage", "Nage");
    public static final LanguageType nxn = new LanguageType("nxn", "Ngawun", "Ngawun");
    public static final LanguageType nym = new LanguageType("nym", "Nyamwezi", "Nyamwezi");
    public static final LanguageType nyo = new LanguageType("nyo", "Nyoro", "Nyoro");
    public static final LanguageType nys = new LanguageType("nys", "Nyunga", "Nyunga");

    public static final LanguageType obm = new LanguageType("obm", "Moabite", "Moabite");
    public static final LanguageType old = new LanguageType("old", "Mochi", "Mochi");
    public static final LanguageType amf = new LanguageType("amf", "Hamer-Banna", "Hamer-Banna");
    public static final LanguageType ood = new LanguageType("ood", "O'odham", "O'odham");
    public static final LanguageType orv = new LanguageType("orv", "Old East Slavic", "Old East Slavic");
    public static final LanguageType osa = new LanguageType("osa", "Osage", "Osage");
    public static final LanguageType osc = new LanguageType("osc", "Oscan", "Oscan");
    public static final LanguageType osp = new LanguageType("osp", "Old Spanish", "Old Spanish");
    public static final LanguageType otk = new LanguageType("otk", "Old Turkic", "Old Turkic");

    public static final LanguageType pad = new LanguageType("pad", "Paumarí", "Paumarí");
    public static final LanguageType pag = new LanguageType("pag", "Pangasinan", "Pangasinan");
    public static final LanguageType pbr = new LanguageType("pbr", "Pangwa", "Pangwa");
    public static final LanguageType pcd = new LanguageType("pcd", "Picard", "Picard");
    public static final LanguageType phn = new LanguageType("phn", "Phoenician", "Phoenician");
    public static final LanguageType pim = new LanguageType("pim", "Powhatan", "Powhatan");
    public static final LanguageType pis = new LanguageType("pis", "Pijin", "Pijin");
    public static final LanguageType piw = new LanguageType("piw", "Pimbwe", "Pimbwe");
    public static final LanguageType pjt = new LanguageType("pjt", "Pitjantjatjara", "Pitjantjatjara");
    public static final LanguageType pld = new LanguageType("pld", "Polari", "Polari");
    public static final LanguageType ple = new LanguageType("ple", "Lu'a", "Palu'e");
    public static final LanguageType plz = new LanguageType("plz", "Paluan", "Paluan");
    public static final LanguageType pms = new LanguageType("pms", "Piemontèis", "Piedmontese");
    public static final LanguageType pmt = new LanguageType("pmt", "Tuamotuan", "Tuamotuan");
    public static final LanguageType pnw = new LanguageType("pnw", "Panyjima", "Panyjima");
    public static final LanguageType pon = new LanguageType("pon", "Pohnpeian", "Pohnpeian");
    public static final LanguageType pox = new LanguageType("pox", "Polabian", "Polabian");
    public static final LanguageType ppm = new LanguageType("ppm", "Papuma", "Papuma");
    public static final LanguageType prg = new LanguageType("prg", "Old Prussian", "Old Prussian");
    public static final LanguageType pwn = new LanguageType("pwn", "Paiwan", "Paiwan");

    public static final LanguageType quc = new LanguageType("quc", "Qatzijob'al", "K'iche'");

    public static final LanguageType raj = new LanguageType("raj", "Rajasthani", "Rajasthani");
    public static final LanguageType rap = new LanguageType("rap", "Rapa Nui", "Rapa Nui");
    public static final LanguageType rej = new LanguageType("rej", "Rejang", "Rejang");
    public static final LanguageType rhg = new LanguageType("rhg", "Rohingya", "Rohingya");
    public static final LanguageType rif = new LanguageType("rif", "Tarifit", "Tarifit");
    public static final LanguageType rim = new LanguageType("rim", "Nyaturu", "Nyaturu");
    public static final LanguageType rme = new LanguageType("rme", "Angloromani", "Angloromani");
    public static final LanguageType rmi = new LanguageType("rmi", "Lomavren", "Lomavren");
    public static final LanguageType roa_tara = new LanguageType("roa-tara", "Tarantino", "Tarantino");// Wikimedia language code
    public static final LanguageType rof = new LanguageType("rof", "Rombo", "Rombo");
    public static final LanguageType roo = new LanguageType("roo", "Rotokas", "Rotokas");
    public static final LanguageType rtm = new LanguageType("rtm", "Rotuman", "Rotuman");
    public static final LanguageType rue = new LanguageType("rue", "Rusyn", "Rusyn");
    public static final LanguageType rui = new LanguageType("rui", "Rufiji", "Rufiji");
    public static final LanguageType ruo = new LanguageType("ruo", "Istro-Romanian", "Istro-Romanian");
    public static final LanguageType rut = new LanguageType("rut", "Rutul", "Rutul");
    public static final LanguageType ryu = new LanguageType("ryu", "Okinawan", "Okinawan");

    public static final LanguageType sad = new LanguageType("sad", "Sandawe", "Sandawe");
    public static final LanguageType sas = new LanguageType("sas", "Sasak", "Sasak");
    public static final LanguageType sat = new LanguageType("sat", "Santali", "Santali");
    public static final LanguageType saw = new LanguageType("saw", "Sawi", "Sawi");
    public static final LanguageType sbf = new LanguageType("sbf", "Shabo", "Shabo");
    public static final LanguageType sbk = new LanguageType("sbk", "Safwa", "Safwa");
    public static final LanguageType scn = new LanguageType("scn", "Sicilianu", "Sicilian");
    public static final LanguageType see = new LanguageType("see", "Onödowága", "Seneca");
    public static final LanguageType sei = new LanguageType("sei", "Cmiique iitom", "Seri");
    public static final LanguageType sel = new LanguageType("sel", "Selkup", "Selkup");
    public static final LanguageType seu = new LanguageType("seu", "Serui-Laut", "Serui-Laut");
    public static final LanguageType shh = new LanguageType("shh", "Shoshone", "Shoshone");
    public static final LanguageType shn = new LanguageType("shn", "Shan", "Shan");
    public static final LanguageType shp = new LanguageType("shp", "Shipibo", "Shipibo");
    public static final LanguageType sid = new LanguageType("sid", "Sidamo", "Sidamo");
    public static final LanguageType ski = new LanguageType("ski", "Sika", "Sika");
    public static final LanguageType snk = new LanguageType("snk", "Sooninkanxanne", "Soninke");
    public static final LanguageType sog = new LanguageType("sog", "Sogdian", "Sogdian");
    public static final LanguageType sov = new LanguageType("sov", "Sonsorolese", "Sonsorolese");
    public static final LanguageType spp = new LanguageType("spp", "Supyire", "Supyire");
    public static final LanguageType spx = new LanguageType("spx", "South Picene", "South Picene");
    public static final LanguageType sqt = new LanguageType("sqt", "Soqotri", "Soqotri");
    public static final LanguageType srn = new LanguageType("srn", "Sranantongo", "Sranan Tongo");
    public static final LanguageType srr = new LanguageType("srr", "Serer", "Serer");
    public static final LanguageType ssc = new LanguageType("ssc", "Suba-Simbiti", "Suba-Simbiti");
    public static final LanguageType sth = new LanguageType("sth", "Shelta", "Shelta");
    public static final LanguageType str = new LanguageType("str", "Saanich", "Saanich");
    public static final LanguageType suj = new LanguageType("suj", "Shubi", "Shubi");
    public static final LanguageType suk = new LanguageType("suk", "Sukuma", "Sukuma");
    public static final LanguageType sul = new LanguageType("sul", "Surigaonon", "Surigaonon");
    public static final LanguageType sus = new LanguageType("sus", "Susu", "Susu");
    public static final LanguageType suw = new LanguageType("suw", "Sumbwa", "Sumbwa");
    public static final LanguageType sux = new LanguageType("sux", "Sumerian", "Sumerian");
    public static final LanguageType sva = new LanguageType("sva", "Lušnu nin", "Svan");
    public static final LanguageType sxb = new LanguageType("sxb", "Suba", "Suba");
    public static final LanguageType szl = new LanguageType("szl", "Ślůnsko godka", "Silesian");

    public static final LanguageType tab = new LanguageType("tab", "Tabasaran", "Tabassaran");
    public static final LanguageType tay = new LanguageType("tay", "Atayal", "Atayal");
    public static final LanguageType tcy = new LanguageType("tcy", "Tulu", "Tulu");
    public static final LanguageType tfn = new LanguageType("tfn", "Dena'ina", "Dena'ina");
    public static final LanguageType thk = new LanguageType("thk", "Tharaka", "Tharaka");
    public static final LanguageType tig = new LanguageType("tig", "Tigre", "Tigre");
    public static final LanguageType tim = new LanguageType("tim", "Timbe", "Timbe");
    public static final LanguageType tiv = new LanguageType("tiv", "Tivi", "Tivi");
    public static final LanguageType tiw = new LanguageType("tiw", "Tiwi", "Tiwi");
    public static final LanguageType tkl = new LanguageType("tkl", "Tokelau", "Tokelauan");
    public static final LanguageType tkr = new LanguageType("tkr", "Tsakhur", "Tsakhur");
    public static final LanguageType tlh = new LanguageType("tlh", "tlhIngan-Hol", "Klingon");
    public static final LanguageType tli = new LanguageType("tli", "Lingít", "Tlingit");
    public static final LanguageType tna = new LanguageType("tna", "Tacana", "Tacana");
    public static final LanguageType tnq = new LanguageType("tnq", "Taino", "Taino");
    public static final LanguageType tpc = new LanguageType("tpc", "Tlapanec", "Tlapanec");
    public static final LanguageType tpn = new LanguageType("tpn", "Tupinambá", "Tupinambá");
    public static final LanguageType tpw = new LanguageType("tpw", "Old Tupi", "Old Tupi");
    public static final LanguageType tsi = new LanguageType("tsi", "Tsimshian", "Tsimshian");
    public static final LanguageType ttj = new LanguageType("ttj", "Tooro", "Tooro");
    public static final LanguageType tum = new LanguageType("tum", "chiTumbuka", "Tumbuka");
    public static final LanguageType txt = new LanguageType("txt", "Citak", "Citak");

    public static final LanguageType uby = new LanguageType("uby", "Ubykh", "Ubykh");
    public static final LanguageType udm = new LanguageType("udm", "Udmurt", "Udmurt");
    public static final LanguageType udi = new LanguageType("udi", "Udi", "Udi");
    public static final LanguageType uga = new LanguageType("uga", "Ugaritic", "Ugaritic");
    public static final LanguageType ulc = new LanguageType("ulc", "Ulch", "Ulch");
    public static final LanguageType ulk = new LanguageType("ulk", "Meriam", "Meriam");
    public static final LanguageType umb = new LanguageType("umb", "Umbundu", "Umbundu");
    public static final LanguageType ute = new LanguageType("ute", "Ute", "Ute");

    public static final LanguageType val = new LanguageType("val", "Vehes", "Vehes");
    public static final LanguageType vec = new LanguageType("vec", "Vèneto", "Venetian");
    public static final LanguageType vep = new LanguageType("vep", "Vepsän kel'", "Veps");
    public static final LanguageType vin = new LanguageType("vin", "Vinza", "Vinza");
    public static final LanguageType vma = new LanguageType("vma", "Martuthunira", "Martuthunira");
    public static final LanguageType vmb = new LanguageType("vmb", "Mbabaram", "Mbabaram");
    public static final LanguageType vmk = new LanguageType("vmk", "Makhuwa-Shirima", "Makhuwa-Shirima");
    public static final LanguageType vmw = new LanguageType("vmw", "Makhuwa", "Makhuwa");

    public static final LanguageType vot = new LanguageType("vot", "Votic", "Votic");

    public static final LanguageType wad = new LanguageType("wad", "Wandamen", "Wandamen");
    public static final LanguageType wam = new LanguageType("wam", "Wôpanâak", "Massachusett");
    public static final LanguageType was = new LanguageType("was", "Wasuu", "Washo");
    public static final LanguageType wba = new LanguageType("wba", "Warao", "Warao");
    public static final LanguageType wbb = new LanguageType("wbb", "Wabo", "Wabo");
    public static final LanguageType wbh = new LanguageType("wbh", "Wanda", "Wanda");
    public static final LanguageType wbp = new LanguageType("wbp", "Warlpiri", "Warlpiri");
    public static final LanguageType wbw = new LanguageType("wbw", "Woi", "Woi");
    public static final LanguageType wgy = new LanguageType("wgy", "Warrgamay", "Warrgamay");
    public static final LanguageType win = new LanguageType("win", "Winnebago", "Winnebago");
    public static final LanguageType wmt = new LanguageType("wmt", "Walmajarri", "Walmajarri");
    public static final LanguageType wrp = new LanguageType("wrp", "Waropen", "Waropen");
    public static final LanguageType wun = new LanguageType("wun", "Bungu", "Bungu");
    public static final LanguageType wya = new LanguageType("wya", "Wyandot", "Wyandot");
    public static final LanguageType wyy = new LanguageType("wyy", "Western Fijian", "Western Fijian");

    public static final LanguageType xas = new LanguageType("xas", "Kamassian", "Kamassian");
    public static final LanguageType xav = new LanguageType("xav", "Xavante", "Xavante");
    public static final LanguageType xbc = new LanguageType("xbc", "Bactrian", "Bactrian");
    public static final LanguageType xce = new LanguageType("xce", "Celtiberian", "Celtiberian");
    public static final LanguageType xcr = new LanguageType("xcr", "Carian", "Carian");
    public static final LanguageType xdc = new LanguageType("xdc", "Dacian", "Dacian");
    public static final LanguageType xdm = new LanguageType("xdm", "Edomite", "Edomite");
    public static final LanguageType xeb = new LanguageType("xeb", "Eblaite", "Eblaite");
    public static final LanguageType xht = new LanguageType("xht", "Hattic", "Hattic");
    public static final LanguageType xhu = new LanguageType("xhu", "Hurrian", "Hurrian");
    public static final LanguageType xlc = new LanguageType("xlc", "Lycian", "Lycian");
    public static final LanguageType xld = new LanguageType("xld", "Lydian", "Lydian");
    public static final LanguageType xmk = new LanguageType("xmk", "Ancient Macedonian", "Ancient Macedonian");
    public static final LanguageType xog = new LanguageType("xog", "Soga", "Soga");
    public static final LanguageType xpg = new LanguageType("xpg", "Phrygian", "Phrygian");
    public static final LanguageType xpm = new LanguageType("xpm", "Pumpokol", "Pumpokol");
    public static final LanguageType xpo = new LanguageType("xpo", "Pochutec", "Pochutec");
    public static final LanguageType xpr = new LanguageType("xpr", "Parthian", "Parthian");
    public static final LanguageType xpu = new LanguageType("xpu", "Punic", "Punic");
    public static final LanguageType xsc = new LanguageType("xsc", "Scythian", "Scythian");
    public static final LanguageType xsm = new LanguageType("xsm", "Kasem", "Kasem");
    public static final LanguageType xsr = new LanguageType("xsr", "Sherpa", "Sherpa");
    public static final LanguageType xss = new LanguageType("xss", "Assan", "Assan");
    public static final LanguageType xta = new LanguageType("xta", "Alcozauca Mixtec", "Alcozauca Mixtec");
    public static final LanguageType xtc = new LanguageType("xtc", "Katcha-Kadugli-Miri", "Katcha-Kadugli-Miri");
    public static final LanguageType xve = new LanguageType("xve", "Venetic", "Venetic");
    public static final LanguageType xvn = new LanguageType("xvn", "Vandalic", "Vandalic");

    public static final LanguageType yao = new LanguageType("yao", "Yao", "Yao");
    public static final LanguageType yap = new LanguageType("yap", "Yapese", "Yapese");
    public static final LanguageType yej = new LanguageType("yej", "Yevanic", "Yevanic");
    public static final LanguageType yii = new LanguageType("yii", "Yidiny", "Yidiny");
    public static final LanguageType yij = new LanguageType("yij", "Yindjibarndi", "Yindjibarndi");
    public static final LanguageType ymm = new LanguageType("ymm", "Maay", "Maay");
    public static final LanguageType ymo = new LanguageType("ymo", "Yangum Mon", "Yangum Mon");
    public static final LanguageType yrk = new LanguageType("yrk", "Nenets", "Nenets");
    public static final LanguageType yua = new LanguageType("yua", "Yucatec Maya", "Yucatec Maya");
    public static final LanguageType yur = new LanguageType("yur", "Yurok", "Yurok");

    public static final LanguageType zai = new LanguageType("zai", "Isthmus Zapotec", "Isthmus Zapotec");
    public static final LanguageType zaj = new LanguageType("zaj", "Zaramo", "Zaramo");
    public static final LanguageType zak = new LanguageType("zak", "Zanaki", "Zanaki");
    public static final LanguageType zaz = new LanguageType("zaz", "Zari", "Zari");
    public static final LanguageType ze = new LanguageType("ze", "Zeneize", "Zeneize");
    public static final LanguageType zen = new LanguageType("zen", "Zenaga", "Zenaga");
    public static final LanguageType zga = new LanguageType("zga", "Kinga", "Kinga");
    public static final LanguageType ziw = new LanguageType("ziw", "Zigula", "Zigula");
    public static final LanguageType zko = new LanguageType("zko", "Kott", "Kott");
    public static final LanguageType zkt = new LanguageType("zkt", "Khitan", "Khitan");
    public static final LanguageType zku = new LanguageType("zku", "Kaurna", "Kaurna");
    public static final LanguageType zmb = new LanguageType("zmb", "Zimba", "Zimba");
    public static final LanguageType zmg = new LanguageType("zmg", "Marti Ke", "Marti Ke");
    public static final LanguageType zmk = new LanguageType("zmk", "Mandandanyi", "Mandandanyi");
    public static final LanguageType zmx = new LanguageType("zmx", "Bomitaba", "Bomitaba");
    public static final LanguageType zun = new LanguageType("zun", "Zuni", "Zuni");
    
    

    // automatically
    /** Vim commands to convert mediawiki/languages/Names.php to the following
     * lines:
     * skip: 0. e ++enc=utf8 (skip this step)
     * skip: 1. %s/#/\/\//g               PHP to Java comments*/
     // 1. %s/\s*#\s*/ '/               PHP comments to 3rd parameter
     /*
     * 2. code to underscore, e.g. bat-smg -> bat_smg (PHP to Java variable names)
     *    %s/\(\t'[^'-]\+\)-\([^'-]\+' => \)/\1_\2/g
     * (44 languages, exception: zh-min-nan, be-x-old) */
    // 3. %s/\t'\([^']\+\)' => ['"]\([^']\+\)['"],[ \t]*/    public static final LanguageType \1 = new LanguageType("\1", "\2");/
    
    // public static final LanguageType avk = new LanguageType("avk", "Kotava", "Kotava");
    
}
