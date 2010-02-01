/*
 * LanguageType.java - code of languages in wiki.
 * 
 * Copyright (c) 2008-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

//http://ru.wiktionary.org/w/index.php?title=%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BF%D0%B5%D1%80%D0%B5%D0%B2-%D0%B1%D0%BB%D0%BE%D0%BA&diff=next&oldid=1243557

package wikipedia.language;

import java.util.Map;
import java.util.HashMap;

/** Languages of wiki: code and name, e.g. ru and Russian. 
 *
 * Source of data: 
 * 1) mediawiki-1.10.1/languages/Names.php
 * 2) Russian Wikipedia: http://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4%D1%8B_%D1%8F%D0%B7%D1%8B%D0%BA%D0%BE%D0%B2
 * 3) Russian Wiktionary:
 *   Шаблон:перев-блок  or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BF%D0%B5%D1%80%D0%B5%D0%B2-%D0%B1%D0%BB%D0%BE%D0%BA
 *   Шаблон:lang        or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:lang
 * 4) http://en.wikipedia.org/wiki/ISO_639
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

    private LanguageType(String code,String name,String english_name) {
        this.code = code;

        //this.name         = name;
        //this.english_name = english_name;
        
        // only name in English is used now, name is skipped
        this.name           = english_name;
        this.english_name   = english_name;

        code2name.put(code, name);
        code2lang.put(code, this);
        english2lang.put(english_name, this);
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
        return code2name.containsKey(code);
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
        LanguageType lt = code2lang.get(code);

        // speedup ?:
        //if(code2lang.containsKey(code)) {
        //    return code2lang.get(code);
        // }
        
        if(null == lt)
            throw new NullPointerException("Null LanguageType");
        return lt;
    }
    
    /** Gets LanguageType by language name in English */
    public static LanguageType getByEnglishName (String english)
    {
        return english2lang.get(english);
    }

    /** Counts number of languages. */
    public static int size() {
        return code2name.size();
    }
    
    /** Gets all languages. */
    public static Map<String, LanguageType> getAllLanguages() {
        return code2lang;
    }
    
    // English Wiktionary specific codes
    public static final LanguageType translingual = new LanguageType("translingual", "Translingual", "Translingual");

    // more than one language code for one language
    public static final LanguageType bat_ltg= new LanguageType("bat-ltg", "Latgalian", "Latgalian");// Латгальский
    public static final LanguageType ltg    = new LanguageType("ltg", "Latgalian", "Latgalian");// Латгальский

    public static final LanguageType crh_latn = new LanguageType("crh-latn", "Crimean Tatar (Latin)", "Crimean Tatar (Latin)");
    public static final LanguageType crh_cyrl = new LanguageType("crh-cyrl", "Crimean Tatar (Cyrillic)", "Crimean Tatar (Cyrillic)");

    public static final LanguageType ee     = new LanguageType("ee", "EК‹egbe", "Г‰wГ©");
    public static final LanguageType ewe    = new LanguageType("ewe", "Ewe", "Ewe");// Эве

    public static final LanguageType el = new LanguageType("el", "О•О»О»О·ОЅО№ОєО¬", "Greek");
    public static final LanguageType el_dhi = new LanguageType("el.dhi", "Greek", "Greek dhi");// Греческий демот.
    public static final LanguageType el_kat = new LanguageType("el.kat", "Greek", "Greek kat");// Греческий кафар.

    public static final LanguageType kl     = new LanguageType("kl", "Kalaallisut", "Inuktitut, Greenlandic/Greenlandic/Kalaallisut (kal)");
    public static final LanguageType kal     = new LanguageType("kal", "Kalaallisut", "Inuktitut, Greenlandic/Greenlandic/Kalaallisut (kal)");

    public static final LanguageType kk     = new LanguageType("kk", "ТљР°Р·Р°Т›С€Р°", "Kazakh");
    public static final LanguageType kk_arab = new LanguageType("kk-arab", "Kazakh Arabic", "Kazakh Arabic");
    public static final LanguageType kk_cyrl = new LanguageType("kk-cyrl", "Kazakh Cyrillic", "Kazakh Cyrillic");
    public static final LanguageType kk_latn = new LanguageType("kk-latn", "Kazakh Latin", "Kazakh Latin");
    public static final LanguageType kk_cn = new LanguageType("kk-cn", "Kazakh (China)", "Kazakh (China)");
    public static final LanguageType kk_kz = new LanguageType("kk-kz", "Kazakh (Kazakhstan)", "Kazakh (Kazakhstan)");
    public static final LanguageType kk_tr = new LanguageType("kk-tr", "Kazakh (Turkey)", "Kazakh (Turkey)");
    
    public static final LanguageType kk_arab2 = new LanguageType("kk.arab", "Kazakh Arabic", "Kazakh Arabic");
    public static final LanguageType kk_cyr = new LanguageType("kk.cyr", "Kazakh Cyrillic", "Kazakh Cyrillic");
    public static final LanguageType kk_lat = new LanguageType("kk.lat", "Kazakh Latin", "Kazakh Latin");

    public static final LanguageType koi    = new LanguageType("koi", "Komi-Permyak", "Komi-Permyak");// Коми-пермяцкий
    public static final LanguageType kom    = new LanguageType("kom", "Komi", "Komi");// Коми-зырянский
    public static final LanguageType kv     = new LanguageType("kv", "РљРѕРјРё", "Komi-Zyrian, cyrillic is common script but also written in latin script");

    public static final LanguageType sm     = new LanguageType("sm", "Gagana Samoa", "Samoan");
    public static final LanguageType smo    = new LanguageType("smo", "Gagana Samoa", "Samoan");// Самоанский

    public static final LanguageType sr_ec  = new LanguageType("sr-ec", "С›РёСЂРёР»РёС†Р°", "Serbian cyrillic ekavian");
    public static final LanguageType sr_el  = new LanguageType("sr-el", "latinica", "Serbian latin ekavian");

    public static final LanguageType sr = new LanguageType("sr", "РЎСЂРїСЃРєРё / Srpski", "Serbian");
    public static final LanguageType sr_c   = new LanguageType("sr-c", "Serbian (Cyrillic)", "Serbian (Cyrillic)");// Сербский (кир)
    public static final LanguageType sr_l   = new LanguageType("sr-l", "Serbian (Latin)", "Serbian (Latin)");// Сербский (лат)

    public static final LanguageType ti = new LanguageType("ti", "б‰µбЊЌб€­бЉ›", "Tigrinya");

    public static final LanguageType tt = new LanguageType("tt", "TatarГ§a/РўР°С‚Р°СЂС‡Р°", "Tatar");// Татарский
    public static final LanguageType tt_cyrl = new LanguageType("tt-cyrl", "РўР°С‚Р°СЂС‡Р°", "Tatar (Cyrillic script)");
    public static final LanguageType tt_latn = new LanguageType("tt-latn", "TatarГ§a", "Tatar (Latin script)");
    public static final LanguageType tt_cyrl2 = new LanguageType("tt.cyr", "РўР°С‚Р°СЂС‡Р°", "Tatar (Cyrillic script)");
    public static final LanguageType tt_latn2 = new LanguageType("tt.lat", "TatarГ§a", "Tatar (Latin script)");

    public static final LanguageType ts = new LanguageType("ts", "Xitsonga", "Tsonga");
    public static final LanguageType tso = new LanguageType("ts", "Xitsonga", "Tsonga");

    public static final LanguageType ty     = new LanguageType("ty", "Reo MДЃ`ohi", "Tahitian");// Таитянский
    public static final LanguageType tah    = new LanguageType("tah", "Reo MДЃ`ohi", "Tahitian");// Таитянский

    public static final LanguageType xh     = new LanguageType("xh", "isiXhosa", "Xhosan");
    public static final LanguageType xho    = new LanguageType("xho", "Xhosa", "Xhosa");// Коса


    // Russian Wiktionary specific codes
    // 1. todo check errors:  has too long unknown language code
    // 2. todo error:  unknown language code
    
    public static final LanguageType letter_ru = new LanguageType("Буква", "Letter", "Letter");// Буква
    public static final LanguageType bagua  = new LanguageType("bagua", "Ba gua", "Ba gua");// Багуа
    public static final LanguageType hanzi  = new LanguageType("hanzi", "Chinese character", "Chinese character");// Китайский иероглиф

    public static final LanguageType abq    = new LanguageType("abq", "Abaza", "Abaza");// Абазинский
    public static final LanguageType abs    = new LanguageType("abs", "abs", "abs");// Амбонезский todo - find English name
    public static final LanguageType ady    = new LanguageType("ady", "Adyghe", "Adyghe");// Адыгейский
    public static final LanguageType agf    = new LanguageType("agf", "Arguni", "Arguni");// Аргуни
    public static final LanguageType agx    = new LanguageType("agx", "Agul", "Agul");// Агульский

    public static final LanguageType aie    = new LanguageType("aie", "Amara", "Amara");// Амара

    public static final LanguageType aii    = new LanguageType("aii", "Assyrian", "Assyrian");// Ассирийский
    public static final LanguageType syc    = new LanguageType("syc", "Syriac", "Syriac");// Сирийский

    public static final LanguageType ain    = new LanguageType("ain", "Ainu", "Ainu");// Айнский
    public static final LanguageType ain_lat = new LanguageType("ain.lat", "Ainu (Latin)", "Ainu (Latin)");//айнский
    public static final LanguageType ain_kana = new LanguageType("ain.kana", "Ainu (Kana)", "Ainu (Kana)");//айнский

    public static final LanguageType aja    = new LanguageType("aja", "Aja (Sudan)", "Aja (Sudan)");//аджа (Судан)
    public static final LanguageType ajg    = new LanguageType("ajg", "Aja (Benin)", "Aja (Benin)");//аджа (Бенин)

    public static final LanguageType akg    = new LanguageType("akg", "Anakalangu", "Anakalangu");// Анакалангу
    public static final LanguageType akk    = new LanguageType("akk", "Akkadian", "Akkadian");// Аккадский

    public static final LanguageType ale    = new LanguageType("ale", "Aleut", "Aleut");// Алеутский
    public static final LanguageType alp    = new LanguageType("alp", "Alune", "Alune");// Алуне
    public static final LanguageType alr    = new LanguageType("alr", "Alyutor", "Alyutor");// Алюторский
    public static final LanguageType alt    = new LanguageType("alt", "Altai", "Altai");//Алтайский
    public static final LanguageType am     = new LanguageType("am", "бЉ б€›б€­бЉ›", "Amharic");
    public static final LanguageType amh    = new LanguageType("amh", "бЉ б€›б€­бЉ›", "Amharic");
    public static final LanguageType amk    = new LanguageType("amk", "Ambai", "Ambai");
    public static final LanguageType aqc    = new LanguageType("aqc", "Archi", "Archi");// Арчинский

    public static final LanguageType arc_syr = new LanguageType("arc.syr", "Aramaic", "Aramaic");//Арамейский (сир.)
    public static final LanguageType arc_jud = new LanguageType("arc.jud", "Aramaic", "Aramaic");//Арамейский (иуд.)
    
    public static final LanguageType art    = new LanguageType("art", "Toki pona", "Toki pona (art)");// Токипона
    public static final LanguageType art_oou= new LanguageType("art-oou", "oou", "oou");// Ооу

    public static final LanguageType asm    = new LanguageType("asm", "Assamese", "Assamese");// Ассамский

    public static final LanguageType ave    = new LanguageType("ave", "Avestan", "Avestan");// Авестийский

    public static final LanguageType ban    = new LanguageType("ban", "Bali", "Bali");//Balinese or Bali language (Nigeria)?
    public static final LanguageType bdk    = new LanguageType("bdk", "Budukh", "Budukh");// Будухский
    public static final LanguageType bem    = new LanguageType("bem", "Bemba", "Bemba");// Бемба
    public static final LanguageType bib    = new LanguageType("bib", "Bissa", "Bissa");// Биса
    public static final LanguageType bph    = new LanguageType("bph", "Botlikh", "Botlikh");// Ботлихский
    public static final LanguageType bua    = new LanguageType("bua", "Buryat", "Buryat");// Бурятский
    public static final LanguageType byn    = new LanguageType("byn", "Blin", "Blin");// Билин

    public static final LanguageType cel = new LanguageType("cel", "Tselinsky", "Tselinsky");// Целинский - in English?

    public static final LanguageType chg    = new LanguageType("chg", "Chagatai", "Chagatai");// Чагатайский

    public static final LanguageType chm    = new LanguageType("chm", "Mari", "Mari");// марийский
    public static final LanguageType mhr = new LanguageType("mhr", "РћР»С‹Рє РњР°СЂРёР№", "Eastern Mari");// луговомарийский
    public static final LanguageType mrj = new LanguageType("mrj", "Western Mari", "Western Mari");// горномарийский

    public static final LanguageType chu    = new LanguageType("chu", "Old Church Slavonic", "Old Church Slavonic");//Старославянский
    public static final LanguageType chu_cyr = new LanguageType("chu.cyr", "Old Church Slavonic (Cyrillic)", "Old Church Slavonic (Cyrillic)");//Старославянский
    public static final LanguageType chu_glag = new LanguageType("chu.glag", "Old Church Slavonic (Glagolitic)", "Old Church Slavonic (Glagolitic)");//Старославянский
    public static final LanguageType chu_ru = new LanguageType("chu-ru", "Church Slavonic", "Church Slavonic");// Церковнославянский
    public static final LanguageType cjs    = new LanguageType("cjs", "Shor", "Shor");// Шорский
    public static final LanguageType ckt    = new LanguageType("ckt", "Chukchi", "Chukchi");// Чукотский
    public static final LanguageType cop    = new LanguageType("cop", "Coptic", "Coptic");// Коптский

    public static final LanguageType dar    = new LanguageType("dar", "Dargin", "Dargin");// Даргинский
    public static final LanguageType de_a   = new LanguageType("de-a", "de-a", "de-a");// Немецкий (австрийский)
    public static final LanguageType dlg    = new LanguageType("dlg", "Dolgan", "Dolgan");// Долганский
    public static final LanguageType dng    = new LanguageType("dng", "Dungan", "Dungan");// Дунганский
    public static final LanguageType drw    = new LanguageType("drw", "Drow (Dungeons & Dragons)", "Drow (Dungeons & Dragons)");// Дроу
    public static final LanguageType dum    = new LanguageType("dum", "Middle Dutch", "Middle Dutch");// Нидерландский средневековый

    public static final LanguageType egy    = new LanguageType("egy", "Egyptian", "Egyptian");// Египетский
    public static final LanguageType en_au  = new LanguageType("en-au", "Australian English", "Australian English");// Английский (австралийский диалект)
    public static final LanguageType en_nz  = new LanguageType("en-nz", "New Zealand English", "New Zealand English");// Новозеландский вариант английского языка
    public static final LanguageType en_us  = new LanguageType("en-us", "American English", "American English");// Английский (американский)
    public static final LanguageType enm    = new LanguageType("enm", "Middle English", "Middle English");// Среднеанглийский

    public static final LanguageType eve    = new LanguageType("eve", "Even", "Even");// Эвенский
    public static final LanguageType evn    = new LanguageType("evn", "Evenki", "Evenki");// Эвенкийский

    public static final LanguageType fic_drw= new LanguageType("fic-drw", "Drow (Dungeons & Dragons)", "Drow (Dungeons & Dragons)");// Дроу
    public static final LanguageType fon    = new LanguageType("fon", "Fon", "Fon");// Фон

    public static final LanguageType fr_be  = new LanguageType("fr-be", "Belgian French", "Belgian French");// Французский (бельгийский)
    public static final LanguageType fr_ch  = new LanguageType("fr-ch", "Swiss French", "Swiss French");// Французский (швейцарский)

    public static final LanguageType gld    = new LanguageType("gld", "Nanai", "Nanai");// Нанайский
    public static final LanguageType gni    = new LanguageType("gni", "Gooniyandi", "Gooniyandi");// Гуниянди
    public static final LanguageType goh    = new LanguageType("goh", "Old High German", "Old High German"); // Древневерхненемецкий
    public static final LanguageType grc_att= new LanguageType("grc-att", "Attic Greek", "Attic Greek"); // Древнегреческий (аттический)
    public static final LanguageType grc_ion= new LanguageType("grc-ion", "Ionic Greek", "Ionic Greek"); // Древнегреческий (ионический)
    public static final LanguageType ha_lat = new LanguageType("ha.lat", "Hausa (lat)", "Hausa (lat)"); // Хауса (лат.)
    public static final LanguageType ha_arab = new LanguageType("ha.arab", "Hausa (arab)", "Hausa (arab)"); // Хауса (араб.)

    public static final LanguageType hbo    = new LanguageType("hbo", "Ancient Hebrew", "Ancient Hebrew"); // Древнееврейский
    public static final LanguageType hit    = new LanguageType("hit", "Hittite", "Hittite");// Хеттский

    public static final LanguageType ium    = new LanguageType("ium", "Iu Mien", "Iu Mien");// Яо
    public static final LanguageType itl    = new LanguageType("itl", "Itelmen", "Itelmen");// ительменский
    public static final LanguageType izh    = new LanguageType("izh", "Ingrian", "Ingrian");// Ижорский

    public static final LanguageType jct    = new LanguageType("jct", "Krymchak", "Krymchak");// Крымчакский

    public static final LanguageType kas    = new LanguageType("kas", "Kashmiri", "Kashmiri");// Кашмири
    public static final LanguageType kbd    = new LanguageType("kbd", "Kabardian", "Kabardian");// Кабардино-черкесский
    public static final LanguageType kca    = new LanguageType("kca", "Khanty", "Khanty");// Хантыйский
    public static final LanguageType kdr    = new LanguageType("kdr", "Karaim", "Karaim");// Караимский
    public static final LanguageType ket    = new LanguageType("ket", "Ket", "Ket");// Кетский

    public static final LanguageType kim    = new LanguageType("kim", "Tofa", "Tofa");// Тофаларский
    public static final LanguageType kjh    = new LanguageType("kjh", "Khakas", "Khakas");// Хакасский
    public static final LanguageType kpy    = new LanguageType("kpy", "Koryak", "Koryak");// Корякский
    public static final LanguageType krc    = new LanguageType("krc", "Karachay-Balkar", "Karachay-Balkar");// Карачаево-балкарский
    public static final LanguageType krl    = new LanguageType("krl", "Karjalan kieli", "Karelian");// Карельский
    public static final LanguageType kum    = new LanguageType("kum", "Kumyk", "Kumyk");// Кумыкский

    public static final LanguageType ku_cyr = new LanguageType("ku.cyr", "Kurdish Cyrillic", "Kurdish Cyrillic");
    public static final LanguageType ku_lat2 = new LanguageType("ku.lat", "Kurdish Latin script", "Kurdish Latin script");
    public static final LanguageType ku_arab2 = new LanguageType("ku.arab", "Kurdish Arabic script", "Northern Kurdish Arabic script");

    public static final LanguageType liv    = new LanguageType("liv", "Livonian ", "Livonian ");// Livonian - Ливский

    public static final LanguageType mаs    = new LanguageType("mas", "Maasai", "Maasai");// Масайский
    public static final LanguageType mgm    = new LanguageType("mgm", "Mambae", "Mambae");// Мамбай
    public static final LanguageType mnk    = new LanguageType("mnk", "Mandinka", "Mandinka");// Мандиго
    public static final LanguageType man_lat= new LanguageType("man.lat", "Mandinka", "Mandinka");// Мандиго

    public static final LanguageType mns    = new LanguageType("mns", "Mansi", "Mansi");// Мансийский
    public static final LanguageType mos    = new LanguageType("mos", "Mossi", "Mossi");// Море
    public static final LanguageType mrv    = new LanguageType("mrv", "Mangareva", "Mangareva");// Мангаревский

    public static final LanguageType nio    = new LanguageType("nio", "Nganasan", "Nganasan");// Нганасанский
    public static final LanguageType niv    = new LanguageType("niv", "Nivkh", "Nivkh");// Нивхский
    public static final LanguageType non    = new LanguageType("non", "Old Norse", "Old Norse");// Древнеисландский
    public static final LanguageType num    = new LanguageType("num", "Niuafo'ou", "Niuafo'ou");// Ниуафооу
    
    public static final LanguageType odt    = new LanguageType("odt", "Old Dutch", "Old Dutch");// # ?
    public static final LanguageType oj     = new LanguageType("oj", "Ojibwe", "Ojibwe");// Оджибва
    public static final LanguageType orv    = new LanguageType("orv", "Old East Slavic", "Old East Slavic");// Древнерусский

    public static final LanguageType pau    = new LanguageType("pau", "Palau", "Palau");// Палау
    public static final LanguageType PIE    = new LanguageType("PIE", "Proto-Indo-European", "Proto-Indo-European");// Праиндоевропейский
    public static final LanguageType pinyin = new LanguageType("pinyin", "Pinyin", "Pinyin");// Пиньинь
    public static final LanguageType pmt    = new LanguageType("pmt", "Tuamotuan", "Tuamotuan");// Туамоту
    public static final LanguageType pox    = new LanguageType("pox", "Polabian", "Polabian");// Полабский
    public static final LanguageType ppol   = new LanguageType("ppol", "Proto-Polynesian", "Proto-Polynesian");// Протополинезийский
    public static final LanguageType prg    = new LanguageType("prg", "Old Prussian", "Old Prussian");// Прусский
    public static final LanguageType prs    = new LanguageType("prs", "Dari (Eastern Persian)", "Dari (Eastern Persian)");// Дари
    public static final LanguageType psl    = new LanguageType("psl", "Proto-Slavic", "Proto-Slavic");// Праславянский

    public static final LanguageType qya    = new LanguageType("qya", "Quenya", "Quenya");// квэнья

    public static final LanguageType rmr    = new LanguageType("rmr", "Calo", "Calo");// Кало - язык испанских цыган
    public static final LanguageType rom    = new LanguageType("rom", "Romani", "Romani");// Цыганский
    public static final LanguageType romaji = new LanguageType("romaji", "Romaji", "Romaji");// Ромадзи
    public static final LanguageType ru_old = new LanguageType("ru-old", "Russian (before 1917)", "Russian (before 1917)");// Русский (дореформенная орфография)
    
    public static final LanguageType sel    = new LanguageType("sel", "Selkup", "Selkup");// Селькупский
    public static final LanguageType sjd    = new LanguageType("sjd", "Kildin Sami", "Kildin Sami");// Саамский (кильдинский)
    public static final LanguageType sjn    = new LanguageType("sjn", "Sindarin", "Sindarin");// Синдарин
    
    public static final LanguageType slovio = new LanguageType("slovio", "Slovio", "Slovio");// Словио
    public static final LanguageType slovio_la = new LanguageType("slovio-la", "Slovio", "Slovio-la");// Словио
    public static final LanguageType slovio_c = new LanguageType("slovio-c", "Slovio (Cyrillic)", "Slovio (Cyrillic)");//Словио (кир)
    public static final LanguageType slovio_l = new LanguageType("slovio-l", "Slovio (Latin)", "Slovio (Latin)");//Словио (лат)

    public static final LanguageType sms    = new LanguageType("sms", "Skolt Sami", "Skolt Sami");// Колтта-саамский
    public static final LanguageType sot    = new LanguageType("sot", "Sesotho", "Sesotho");// Сесото
    
    public static final LanguageType solresol = new LanguageType("solresol", "Solresol", "Solresol");// Сольресоль
    public static final LanguageType sol    = new LanguageType("sol", "Solresol", "Solresol");// Сольресоль

    public static final LanguageType sux    = new LanguageType("sux", "Sumerian", "Sumerian");// Шумерский
    public static final LanguageType sva    = new LanguageType("sva", "Svan", "Svan");// Сванский

    public static final LanguageType tab    = new LanguageType("tab", "Tabasaran", "Tabasaranagx");// Табасаранский
    public static final LanguageType tkl    = new LanguageType("tkl", "Tokelau", "Tokelau");// Токелау
    public static final LanguageType tly    = new LanguageType("tly", "Talysh", "Tokelau");// Талышский
    public static final LanguageType translingual_ru = new LanguageType("INT", "Translingual", "Translingual (INT)");// INTernational

    public static final LanguageType ttt    = new LanguageType("ttt", "Tat", "Tat");// Татский
    public static final LanguageType tup    = new LanguageType("tup", "Tupí-Guaraní", "Tupí-Guaraní");// гуарани
    public static final LanguageType tvl    = new LanguageType("tvl", "Tuvalu", "Tuvalu");// Тувалу

    public static final LanguageType uby    = new LanguageType("uby", "Ubykh", "Ubykh");// Убыхский
    public static final LanguageType udi    = new LanguageType("udi", "Udi", "Udi");// Удинский
    public static final LanguageType ulc    = new LanguageType("ulc", "Ulch", "Ulch");// Ульчский
    public static final LanguageType ulk    = new LanguageType("ulk", "Meriam", "Meriam");// Мериам

    public static final LanguageType vep    = new LanguageType("vep", "Veps", "Veps");// Вепсский
    public static final LanguageType vot    = new LanguageType("vot", "Votic", "Votic");// Водский
    public static final LanguageType vro    = new LanguageType("vro", "Võro", "Võro");// Выруский диалект
    public static final LanguageType fiu    = new LanguageType("fiu", "Võro", "Võro");// Выруский диалект

    public static final LanguageType ykg    = new LanguageType("ykg", "Northern Yukaghir", "Northern Yukaghir");// Северноюкагирский
    public static final LanguageType yux    = new LanguageType("yux", "Southern Yukaghir", "Southern dYukaghir");// Южноюкагирский
    public static final LanguageType yrk    = new LanguageType("yrk", "Nenets", "Nenets");// Ненецкий

    public static final LanguageType zza    = new LanguageType("zza", "Zazaki", "Zazaki");// зазаки

    







    // todo: check and merge Russian Wiktionary types with correspondend English language (Mediawiki)
    
    public static final LanguageType zh_nan = new LanguageType("zh-nan", "Min-nan", "Min-nan");//Китайский (южноминьский) Min Nan, Minnan, or Min-nan, Southern Min

    public static final LanguageType oen    = new LanguageType("oen", "Old English", "Old English");//Староанглийский
    
    
    
    // manually added languages:
    public static final LanguageType aar    = new LanguageType("aar", "Afar", "Afar");// Афар/Афарский
    public static final LanguageType ab     = new LanguageType("ab", "РђТ§СЃСѓР°", "Abkhaz");
    public static final LanguageType aiw    = new LanguageType("aiw", "Aari", "Aari");// Аари

    public static final LanguageType bat_smg = new LanguageType("bat-smg", "ЕЅemaitД—ЕЎka", "Samogitian");

    public static final LanguageType bal    = new LanguageType("bal", "Balochi", "Balochi");// Белуджский

    public static final LanguageType be_tarask = new LanguageType("be-tarask", "Р‘РµР»Р°СЂСѓСЃРєР°СЏ (С‚Р°СЂР°С€РєРµРІС–С†Р°)", "Belarusian in Taraskievica orthography");
    public static final LanguageType be_x_old = new LanguageType("be-x-old", "Р‘РµР»Р°СЂСѓСЃРєР°СЏ (С‚Р°СЂР°С€РєРµРІС–С†Р°)", "Belarusian in Taraskievica orthography; compat link");
    
    public static final LanguageType brh    = new LanguageType("brh", "Brahui", "Brahui");// Брауи

    public static final LanguageType btk    = new LanguageType("btk", "Batak", "Batak");// Батакский
    public static final LanguageType bya    = new LanguageType("bya", "Batak", "Batak");// Батакский

    public static final LanguageType cbk_zam = new LanguageType("cbk-zam", "Chavacano de Zamboanga", "Zamboanga Chavacano");
    public static final LanguageType ccc    = new LanguageType("ccc", "Chamicuro", "Chamicuro");// Чамикуро
    public static final LanguageType cy     = new LanguageType("cy", "Cymraeg", "Welsh");// Валлийский
    public static final LanguageType cym    = new LanguageType("cym", "Cymraeg", "Welsh");// Валлийский

    public static final LanguageType de_formal = new LanguageType("de-formal", "Deutsch (Sie-Form)", "German - formal address (\"Sie\")");
    public static final LanguageType dif    = new LanguageType("dif", "Dieri", "Dieri");// Диери
    public static final LanguageType dk     = new LanguageType("dk", "Dansk (deprecated:da)", "Unused code currently falls back to Danish, da is correct for the language");
    public static final LanguageType en_gb  = new LanguageType("en-gb", "British English", "British English");
    public static final LanguageType fiu_vro = new LanguageType("fiu-vro", "VГµro", "VГµro");

    public static final LanguageType frm    = new LanguageType("frm", "Middle French", "Middle French"); // Среднефранцузский
    public static final LanguageType fro    = new LanguageType("fro", "Old French", "Old French"); // Старофранцузский
    
    public static final LanguageType gmh    = new LanguageType("gmh", "Middle High German", "Middle High German");// Средневерхненемецкий
    public static final LanguageType gn = new LanguageType("gn", "AvaГ±e\'бєЅ", "Guarani, Paraguayan");

    public static final LanguageType hif_deva = new LanguageType("hif-deva", "а¤«а¤јаҐЂа¤њаҐЂ а¤№а¤їа¤ЁаҐЌа¤¦аҐЂ", "Fiji Hindi (devangari)");
    public static final LanguageType hif_latn = new LanguageType("hif-latn", "Fiji Hindi", "Fiji Hindi (latin)");

    public static final LanguageType ht = new LanguageType("ht", "KreyГІl ayisyen", "Haitian Creole French");
    
    public static final LanguageType ike_cans = new LanguageType("ike-cans", "бђѓб“„б’ѓб‘Ћб‘ђб‘¦", "Inuktitut, Eastern Canadian/Eastern Canadian \"Eskimo\"/\"Eastern Arctic Eskimo\"/Inuit (Unified Canadian Aboriginal Syllabics)");
    public static final LanguageType ike_latn = new LanguageType("ike-latn", "inuktitut", "Inuktitut, Eastern Canadian (Latin script)");

    public static final LanguageType ith_lat = new LanguageType("ith.lat", "Ithkuil", "Ithkuil");// Ифкуиль

    public static final LanguageType krh    = new LanguageType("krh", "Kurama", "Kurama");

    public static final LanguageType kok    = new LanguageType("kok", "Konkani", "Konkani");// Конкани
    public static final LanguageType ku     = new LanguageType("ku", "Kurdish", "Kurdish");
    public static final LanguageType kur    = new LanguageType("kur", "Kurdish", "Kurdish");
    public static final LanguageType ckb    = new LanguageType("ckb", "Central Kurdish", "Central Kurdish");
    public static final LanguageType kmr    = new LanguageType("kmr", "Northern Kurdish", "Northern Kurdish");
    public static final LanguageType sdh    = new LanguageType("sdh", "Southern Kurdish", "Southern Kurdish");
    public static final LanguageType ku_latn = new LanguageType("ku-latn", "Northern Kurdish Latin script", "Northern Kurdish Latin script");
    public static final LanguageType ku_arab = new LanguageType("ku-arab", "Northern Kurdish Arabic script", "Northern Kurdish Arabic script");

    public static final LanguageType ksh_c_a = new LanguageType("ksh-c-a", "Ripoarisch c a", "Ripuarian c a");
    public static final LanguageType ksh_p_b = new LanguageType("ksh-p-b", "Ripoarisch p b", "Ripuarian p b");
    
    public static final LanguageType luy    = new LanguageType("luy", "Luhya", "Luhya");// Лухья

    public static final LanguageType mnc    = new LanguageType("mnc", "Manchu", "Manchu");

    public static final LanguageType new_ = new LanguageType("new", "а¤ЁаҐ‡а¤Єа¤ѕа¤І а¤­а¤ѕа¤·а¤ѕ", "Newar / Nepal Bhasa");

    public static final LanguageType nn = new LanguageType("nn", "Norwegian (Nynorsk)", "Norwegian (Nynorsk)");
    public static final LanguageType no = new LanguageType("no", "Norwegian", "Norwegian");
    
    public static final LanguageType nv     = new LanguageType("nv", "DinГ© bizaad", "Navajo");
    public static final LanguageType nav    = new LanguageType("nv", "DinГ© bizaad", "Navajo");
    
    public static final LanguageType nb     = new LanguageType("nb", "Norwegian (Bokmal)", "Norwegian (Bokmal)");
    public static final LanguageType nds    = new LanguageType("nds", "PlattdГјГјtsch", "Low German \'\'or\'\' Low Saxon");
    public static final LanguageType nds_nl = new LanguageType("nds-nl", "Nedersaksisch", "Dutch Low Saxon");

    public static final LanguageType map_bms = new LanguageType("map-bms", "Basa Banyumasan", "Banyumasan ");
    public static final LanguageType pcd    = new LanguageType("pcd", "Picard", "Picard");// Пикардский
    public static final LanguageType pt_br  = new LanguageType("pt-br", "PortuguГЄs do Brasil", "Brazilian Portuguese");
    
    public static final LanguageType roa_rup = new LanguageType("roa-rup", "ArmГЈneashce", "Aromanian");
    public static final LanguageType roa_tara = new LanguageType("roa-tara", "TarandГ­ne", "Tarantino");

    public static final LanguageType ruq_cyrl = new LanguageType("ruq-cyrl", "Р’Р»Р°С…РµСЃС‚Рµ", "Megleno-Romanian (Cyrillic script)");
    public static final LanguageType ruq_grek = new LanguageType("ruq-grek", "О’О»О±ОµПѓП„Оµ", "Megleno-Romanian (Greek script)");
    public static final LanguageType ruq_latn = new LanguageType("ruq-latn", "VlДѓheЕџte", "Megleno-Romanian (Latin script)");

    public static final LanguageType rap    = new LanguageType("rap", "Rapa Nui", "Rapa Nui");// Рапануйский
    public static final LanguageType rar    = new LanguageType("rap", "Rarotongan", "Rarotongan");// Раротонга
    public static final LanguageType sat    = new LanguageType("sat", "Santali", "Santali");// Сантали
    public static final LanguageType smn    = new LanguageType("smn", "Inari Sami", "Inari Sami");// Инари-саамский

    public static final LanguageType tg_cyrl = new LanguageType("tg-cyrl", "РўРѕТ·РёРєУЈ", "Tajiki (Cyrllic script) (default)");
    public static final LanguageType tg_latn = new LanguageType("tg-latn", "tojikД«", "Tajiki (Latin script)");

    public static final LanguageType tl     = new LanguageType("tl", "Tagalog", "Tagalog");
    public static final LanguageType tgl    = new LanguageType("tgl", "Tagalog", "Tagalog");

    public static final LanguageType tig    = new LanguageType("tig", "Tigre", "Tigre");// Тигре
    public static final LanguageType tir    = new LanguageType("tir", "Tigrinya", "Tigrinya");// Тигринья
    
    public static final LanguageType tpc    = new LanguageType("tpc", "Tlapanec", "Tlapanec");// ?

    public static final LanguageType tix    = new LanguageType("tix", "Tiwa", "Tiwa");// ?
    public static final LanguageType twf    = new LanguageType("twf", "Tiwa", "Tiwa");// ?

    public static final LanguageType uga    = new LanguageType("uga", "Ugaritic", "Ugaritic");// Угаритский
    public static final LanguageType uz = new LanguageType("uz", "Uzbek", "Uzbek");

    public static final LanguageType tlh    = new LanguageType("tlh", "tlhIngan-Hol", "Klingon"); // - no interlanguage links allowed

    public static final LanguageType val    = new LanguageType("val", "Vehes", "Vehes");// ?
    public static final LanguageType xbc    = new LanguageType("xbc", "Bactrian", "Bactrian");// Бактрийский
    public static final LanguageType yua    = new LanguageType("yua", "Yucatec Maya", "Yucatec Maya");// Юкатекский
    public static final LanguageType yue    = new LanguageType("yue", "зІµиЄћ", "Cantonese");

    public static final LanguageType ze = new LanguageType("ze", "Zeneize", "Zeneize");// Генуэзский диалект лигурского языка
    
    public static final LanguageType zh_cn = new LanguageType("zh-cn", "Chinese (PRC)", "Chinese (PRC)");
    public static final LanguageType zh_hans = new LanguageType("zh-hans", "Chinese written using the Simplified Chinese script", "Chinese written using the Simplified Chinese script");
    public static final LanguageType zh_hant = new LanguageType("zh-hant", "Chinese written using the Traditional Chinese script", "Chinese written using the Traditional Chinese script");
    public static final LanguageType zh_sg = new LanguageType("zh-sg", "Chinese (Singapore)", "Chinese (Singapore)");
    public static final LanguageType zh_tw = new LanguageType("zh-tw", "Chinese (Taiwan)", "Chinese (Taiwan)");
    public static final LanguageType zh_hk = new LanguageType("zh-hk", "Chinese (Hong Kong)", "Chinese (Hong Kong)");
    public static final LanguageType zh_mo = new LanguageType("zh-mo", "Chinese (Macau)", "Chinese (Macau)");
    public static final LanguageType zh_my = new LanguageType("zh-my", "Chinese (Malaysia)", "Chinese (Malaysia)");

    public static final LanguageType zh_classical = new LanguageType("zh-classical", "ж–‡иЁЂ", "Classical Chinese/Literary Chinese");
    public static final LanguageType zh_min_nan = new LanguageType("zh-min-nan", "BГўn-lГўm-gГє", "Min-nan -- (see bug 8217)");
    public static final LanguageType zh_yue = new LanguageType("zh-yue", "зІµиЄћ", "Cantonese");

    public static final LanguageType zu     = new LanguageType("zu", "isiZulu", "Cantonese");


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
    
//    public static final LanguageType aa = new LanguageType("aa", "Afar");// Afar
    public static final LanguageType aa = new LanguageType("aa", "Afar", "Afar");
    public static final LanguageType ace = new LanguageType("ace", "AchГЁh", "Aceh");
    public static final LanguageType af = new LanguageType("af", "Afrikaans", "Afrikaans");
    public static final LanguageType ak = new LanguageType("ak", "Akan", "Akan");
    public static final LanguageType aln = new LanguageType("aln", "GegГ«", "Gheg Albanian");
    public static final LanguageType als = new LanguageType("als", "Alemannisch", "Alemannic -- not a valid code, for compatibility. See gsw.");
    
    public static final LanguageType an = new LanguageType("an", "AragonГ©s", "Aragonese");
    public static final LanguageType ang = new LanguageType("ang", "Anglo-Saxon", "Old English");
    public static final LanguageType ar = new LanguageType("ar", "Ш§Щ„Ш№Ш±ШЁЩЉШ©", "Arabic");
    public static final LanguageType arc = new LanguageType("arc", "ЬђЬЄЬЎЬќЬђ", "Aramaic");
    public static final LanguageType arn = new LanguageType("arn", "Mapudungun", "Mapuche, Mapudungu, Araucanian (Araucano)");// Арауканский
    public static final LanguageType arz = new LanguageType("arz", "Щ…ШµШ±Щ‰", "Egyptian Spoken Arabic");
    public static final LanguageType as = new LanguageType("as", "а¦…а¦ёа¦®а§Ђа§џа¦ѕ", "Assamese");
    public static final LanguageType ast = new LanguageType("ast", "Asturianu", "Asturian");
    public static final LanguageType av = new LanguageType("av", "РђРІР°СЂ", "Avar");
    public static final LanguageType avk = new LanguageType("avk", "Kotava", "Kotava");
    public static final LanguageType ay = new LanguageType("ay", "Aymar aru", "Aymara");
    public static final LanguageType az = new LanguageType("az", "AzЙ™rbaycan", "Azerbaijani");
    public static final LanguageType ba = new LanguageType("ba", "Р‘Р°С€ТЎРѕСЂС‚", "Bashkir");
    public static final LanguageType bar = new LanguageType("bar", "Boarisch", "Bavarian (Austro-Bavarian and South Tyrolean)");
    
    public static final LanguageType bcc = new LanguageType("bcc", "ШЁЩ„Щ€Ъ†ЫЊ Щ…Ъ©Ш±Ш§Щ†ЫЊ", "Southern Balochi");
    public static final LanguageType bcl = new LanguageType("bcl", "Bikol Central", "Bikol: Central Bicolano language");
    public static final LanguageType be = new LanguageType("be", "Р‘РµР»Р°СЂСѓСЃРєР°СЏ", "Belarusian normative");
    
    public static final LanguageType bg = new LanguageType("bg", "Р‘СЉР»РіР°СЂСЃРєРё", "Bulgarian");
    public static final LanguageType bh = new LanguageType("bh", "а¤­аҐ‹а¤ња¤ЄаҐЃа¤°аҐЂ", "Bhojpuri");
    public static final LanguageType bi = new LanguageType("bi", "Bislama", "Bislama");
    public static final LanguageType bm = new LanguageType("bm", "Bamanankan", "Bambara");
    public static final LanguageType bn = new LanguageType("bn", "а¦¬а¦ѕа¦‚а¦Іа¦ѕ", "Bengali");
    public static final LanguageType bo = new LanguageType("bo", "аЅ–аЅјаЅ‘ај‹аЅЎаЅІаЅ‚", "Tibetan");
    public static final LanguageType bpy = new LanguageType("bpy", "а¦‡а¦®а¦ѕа¦° а¦ а¦ѕа¦°/а¦¬а¦їа¦·а§Ќа¦Ја§Ѓа¦Єа§Ќа¦°а¦їа¦Їа¦ја¦ѕ а¦®а¦Ја¦їа¦Єа§Ѓа¦°а§Ђ", "Bishnupriya Manipuri");
    public static final LanguageType bqi = new LanguageType("bqi", "ШЁШ®ШЄЩЉШ§Ш±ЩЉ", "Bakthiari");
    public static final LanguageType br = new LanguageType("br", "Brezhoneg", "Breton");
    public static final LanguageType bs = new LanguageType("bs", "Bosanski", "Bosnian");
    public static final LanguageType bto = new LanguageType("bto", "Iriga Bicolano", "Iriga Bicolano/Rinconada Bikol");
    public static final LanguageType bug = new LanguageType("bug", "бЁ…бЁ” бЁ•бЁбЁЃбЁ—", "Bugis");
    public static final LanguageType bxr = new LanguageType("bxr", "Р‘СѓСЂСЏР°Рґ", "Buryat (Russia)");
    public static final LanguageType ca = new LanguageType("ca", "CatalГ ", "Catalan");
    
    public static final LanguageType cdo = new LanguageType("cdo", "MГ¬ng-dД•М¤ng-ngб№іМ„", "Min Dong");
    public static final LanguageType ce = new LanguageType("ce", "РќРѕС…С‡РёР№РЅ", "Chechen");
    public static final LanguageType ceb = new LanguageType("ceb", "Cebuano", "Cebuano");
    public static final LanguageType ch = new LanguageType("ch", "Chamoru", "Chamorro");
    public static final LanguageType cho = new LanguageType("cho", "Choctaw", "Choctaw");
    public static final LanguageType chr = new LanguageType("chr", "бЏЈбЋібЋ©", "Cherokee");
    public static final LanguageType chy = new LanguageType("chy", "TsetsГЄhestГўhese", "Cheyenne");
    public static final LanguageType co = new LanguageType("co", "Corsu", "Corsican");
    public static final LanguageType cr = new LanguageType("cr", "NД“hiyawД“win / б“Ђбђ¦бђѓб”­бђЌбђЏбђЈ", "Cree");
    public static final LanguageType crh = new LanguageType("crh", "QД±rД±mtatarca", "Crimean Tatar");
    
    public static final LanguageType cs = new LanguageType("cs", "ДЊesky", "Czech");
    public static final LanguageType csb = new LanguageType("csb", "KaszГ«bsczi", "Cassubian");
    public static final LanguageType cu = new LanguageType("cu", "РЎР»РѕРІСЈМЃРЅСЊСЃРєСЉ / в°”в°Ћв°‘в°‚в°Ўв°ђв° в°”в°Ќв°џ", "Old Church Slavonic (ancient language)");
    public static final LanguageType cv = new LanguageType("cv", "Р§ДѓРІР°С€Р»Р°", "Chuvash");

    public static final LanguageType da = new LanguageType("da", "Dansk", "Danish");
    public static final LanguageType de = new LanguageType("de", "Deutsch", "German (\"Du\")");
    
    public static final LanguageType diq = new LanguageType("diq", "Zazaki", "Zazaki");
    
    public static final LanguageType dsb = new LanguageType("dsb", "Dolnoserbski", "Lower Sorbian");
    public static final LanguageType dv = new LanguageType("dv", "Ю‹ЮЁЮ€Ю¬ЮЂЮЁЮ„Ю¦ЮђЮ°", "Dhivehi");
    public static final LanguageType dz = new LanguageType("dz", "аЅ‡аЅјаЅ„ај‹аЅЃ", "Bhutani");
    
    public static final LanguageType eml = new LanguageType("eml", "EmiliГ n e rumagnГІl", "Emiliano-Romagnolo / Sammarinese");
    public static final LanguageType en = new LanguageType("en", "English", "English");
    
    public static final LanguageType eo = new LanguageType("eo", "Esperanto", "Esperanto");
    public static final LanguageType es = new LanguageType("es", "EspaГ±ol", "Spanish");
    public static final LanguageType et = new LanguageType("et", "Eesti", "Estonian");
    public static final LanguageType eu = new LanguageType("eu", "Euskara", "Basque");
    public static final LanguageType ext = new LanguageType("ext", "EstremeГ±u", "Extremaduran");
    public static final LanguageType fa = new LanguageType("fa", "ЩЃШ§Ш±ШіЫЊ", "Persian");
    public static final LanguageType ff = new LanguageType("ff", "Fulfulde", "Fulfulde, Maasina");
    public static final LanguageType fi = new LanguageType("fi", "Suomi", "Finnish");
    
    public static final LanguageType fj = new LanguageType("fj", "Na Vosa Vakaviti", "Fijian");
    public static final LanguageType fo = new LanguageType("fo", "FГёroyskt", "Faroese");
    public static final LanguageType fr = new LanguageType("fr", "FranГ§ais", "French");
    public static final LanguageType frc = new LanguageType("frc", "FranГ§ais cadien", "Cajun French");
    public static final LanguageType frp = new LanguageType("frp", "Arpetan", "Franco-ProvenГ§al/Arpitan");
    public static final LanguageType fur = new LanguageType("fur", "Furlan", "Friulian");
    public static final LanguageType fy = new LanguageType("fy", "Frysk", "Frisian");
    public static final LanguageType ga = new LanguageType("ga", "Gaeilge", "Irish");
    public static final LanguageType gag = new LanguageType("gag", "Gagauz", "Gagauz");
    public static final LanguageType gan = new LanguageType("gan", "иґ›иЄћ", "Gan");
    public static final LanguageType gd = new LanguageType("gd", "GГ idhlig", "Scots Gaelic");
    public static final LanguageType gl = new LanguageType("gl", "Galego", "Galician");
    public static final LanguageType glk = new LanguageType("glk", "ЪЇЫЊЩ„Ъ©ЫЊ", "Gilaki");
	
    public static final LanguageType got = new LanguageType("got", "н ЂнјІн Ђнјїн ЂнЅ„н Ђнј№н ЂнЅѓн Ђнјє", "Gothic");
    public static final LanguageType grc = new LanguageType("grc", "бј€ПЃП‡О±ОЇО± бј‘О»О»О·ОЅО№ОєбЅґ", "Ancient Greece");
    public static final LanguageType gsw = new LanguageType("gsw", "Alemannisch", "Alemannic");
    public static final LanguageType gu = new LanguageType("gu", "аЄ—а«ЃаЄњаЄ°аЄѕаЄ¤а«Ђ", "Gujarati");
    public static final LanguageType gv = new LanguageType("gv", "Gaelg", "Manx");
    public static final LanguageType ha = new LanguageType("ha", "Щ‡ЩЋЩ€ЩЏШіЩЋ", "Hausa");
    public static final LanguageType hak = new LanguageType("hak", "Hak-kГў-fa", "Hakka");
    public static final LanguageType haw = new LanguageType("haw", "Hawai`i", "Hawaiian");
    public static final LanguageType he = new LanguageType("he", "ЧўЧ‘ЧЁЧ™ЧЄ", "Hebrew");
    public static final LanguageType hi = new LanguageType("hi", "а¤№а¤їа¤ЁаҐЌа¤¦аҐЂ", "Hindi");
    public static final LanguageType hif = new LanguageType("hif", "Fiji Hindi", "Fijian Hindi (falls back to hif-latn)");
    
    public static final LanguageType hil = new LanguageType("hil", "Ilonggo", "Hiligaynon");
    public static final LanguageType ho = new LanguageType("ho", "Hiri Motu", "Hiri Motu");
    public static final LanguageType hr = new LanguageType("hr", "Hrvatski", "Croatian");
    public static final LanguageType hsb = new LanguageType("hsb", "Hornjoserbsce", "Upper Sorbian");
	
    public static final LanguageType hu = new LanguageType("hu", "Magyar", "Hungarian");
    public static final LanguageType hy = new LanguageType("hy", "ХЂХЎХµХҐЦЂХҐХ¶", "Armenian");
    public static final LanguageType hz = new LanguageType("hz", "Otsiherero", "Herero");
    public static final LanguageType ia = new LanguageType("ia", "Interlingua", "Interlingua (IALA)");
    public static final LanguageType id = new LanguageType("id", "Bahasa Indonesia", "Indonesian");
    public static final LanguageType ie = new LanguageType("ie", "Interlingue", "Interlingue (Occidental)");
    public static final LanguageType ig = new LanguageType("ig", "Igbo", "Igbo");
    public static final LanguageType ii = new LanguageType("ii", "к†‡к‰™", "Sichuan Yi");
    public static final LanguageType ik = new LanguageType("ik", "IГ±upiak", "Inupiak (Inupiatun, Northwest Alaska / Inupiatun, North Alaskan)");
    
    public static final LanguageType ilo = new LanguageType("ilo", "Ilokano", "Ilokano");
    public static final LanguageType inh = new LanguageType("inh", "Р“Р†Р°Р»РіР†Р°Р№ ДћalДџaj", "Ingush");
    public static final LanguageType io = new LanguageType("io", "Ido", "Ido");
    public static final LanguageType is = new LanguageType("is", "ГЌslenska", "Icelandic");
    public static final LanguageType it = new LanguageType("it", "Italiano", "Italian");
    public static final LanguageType iu = new LanguageType("iu", "бђѓб“„б’ѓб‘Ћб‘ђб‘¦/inuktitut", "Inuktitut (macro language - do no localise, see ike/ikt - falls back to ike-cans)");
    public static final LanguageType ja = new LanguageType("ja", "ж—Ґжњ¬иЄћ", "Japanese");
    public static final LanguageType jbo = new LanguageType("jbo", "Lojban", "Lojban");
    public static final LanguageType jut = new LanguageType("jut", "Jysk", "Jutish / Jutlandic");
    public static final LanguageType jv = new LanguageType("jv", "Basa Jawa", "Javanese");
    public static final LanguageType ka = new LanguageType("ka", "бѓҐбѓђбѓ бѓ—бѓЈбѓљбѓ", "Georgian");
    public static final LanguageType kaa = new LanguageType("kaa", "Qaraqalpaqsha", "Karakalpak");
    public static final LanguageType kab = new LanguageType("kab", "Taqbaylit", "Kabyle");
    public static final LanguageType kg = new LanguageType("kg", "Kongo", "Kongo, (FIXME!) should probaly be KiKongo or KiKoongo");
    public static final LanguageType ki = new LanguageType("ki", "GД©kЕ©yЕ©", "Gikuyu");
    public static final LanguageType kj = new LanguageType("kj", "Kwanyama", "Kwanyama");
    
    public static final LanguageType km = new LanguageType("km", "бћ—бћ¶бћџбћ¶бћЃбџ’бћбџ‚бћљ", "Khmer, Central");
    public static final LanguageType kn = new LanguageType("kn", "аІ•аІЁаіЌаІЁаІЎ", "Kannada");
    public static final LanguageType ko = new LanguageType("ko", "н•њкµ­м–ґ", "Korean");
    public static final LanguageType kr = new LanguageType("kr", "Kanuri", "Kanuri, Central");
    public static final LanguageType kri = new LanguageType("kri", "Krio", "Krio");
    public static final LanguageType krj = new LanguageType("krj", "Kinaray-a", "Kinaray-a");
    public static final LanguageType ks = new LanguageType("ks", "а¤•а¤¶аҐЌа¤®аҐЂа¤°аҐЂ - (ЩѓШґЩ…ЩЉШ±ЩЉ)", "Kashmiri");
    public static final LanguageType ksh = new LanguageType("ksh", "Ripoarisch", "Ripuarian ");
	
    public static final LanguageType kw = new LanguageType("kw", "Kernewek", "Cornish");
    public static final LanguageType ky = new LanguageType("ky", "РљС‹СЂРіС‹Р·С‡Р°", "Kirghiz");
    public static final LanguageType la = new LanguageType("la", "Latina", "Latin");
    public static final LanguageType lad = new LanguageType("lad", "Ladino", "Ladino");
    public static final LanguageType lb = new LanguageType("lb", "LГ«tzebuergesch", "Luxemburguish");
    public static final LanguageType lbe = new LanguageType("lbe", "Р›Р°РєРєСѓ", "Lak");
    public static final LanguageType lez = new LanguageType("lez", "Р›РµР·РіРё", "Lezgi");
    public static final LanguageType lfn = new LanguageType("lfn", "Lingua Franca Nova", "Lingua Franca Nova");
    public static final LanguageType lg = new LanguageType("lg", "Luganda", "Ganda");
    public static final LanguageType li = new LanguageType("li", "Limburgs", "Limburgian");
    public static final LanguageType lij = new LanguageType("lij", "LГ­guru", "Ligurian");
    public static final LanguageType lld = new LanguageType("lld", "Ladin", "Ladin");
    public static final LanguageType lmo = new LanguageType("lmo", "Lumbaart", "Lombard");
    public static final LanguageType ln = new LanguageType("ln", "LingГЎla", "Lingala");
    public static final LanguageType lo = new LanguageType("lo", "аєҐаєІає§", "Laotian");
    public static final LanguageType loz = new LanguageType("loz", "Silozi", "Lozi");
    public static final LanguageType lt = new LanguageType("lt", "LietuviЕі", "Lithuanian");
    public static final LanguageType lv = new LanguageType("lv", "LatvieЕЎu", "Latvian");
    public static final LanguageType lzz = new LanguageType("lzz", "Lazuri Nena", "Laz");
    public static final LanguageType mai = new LanguageType("mai", "а¤®аҐ€а¤Ґа¤їа¤ІаҐЂ", "Maithili");
    
    public static final LanguageType mdf = new LanguageType("mdf", "РњРѕРєС€РµРЅСЊ", "Moksha");
    public static final LanguageType mg = new LanguageType("mg", "Malagasy", "Malagasy");
    public static final LanguageType mh = new LanguageType("mh", "Ebon", "Marshallese");
    
    public static final LanguageType mi = new LanguageType("mi", "MДЃori", "Maori");
    public static final LanguageType mk = new LanguageType("mk", "РњР°РєРµРґРѕРЅСЃРєРё", "Macedonian");
    public static final LanguageType ml = new LanguageType("ml", "аґ®аґІаґЇаґѕаґіаґ‚", "Malayalam");
    public static final LanguageType mn = new LanguageType("mn", "РњРѕРЅРіРѕР»", "Halh Mongolian (Cyrillic) (ISO 639-3: khk)");
    public static final LanguageType mo = new LanguageType("mo", "РњРѕР»РґРѕРІРµРЅСЏСЃРєСЌ", "Moldovan");
    public static final LanguageType mr = new LanguageType("mr", "а¤®а¤°а¤ѕа¤ аҐЂ", "Marathi");
    public static final LanguageType ms = new LanguageType("ms", "Bahasa Melayu", "Malay");
    public static final LanguageType mt = new LanguageType("mt", "Malti", "Maltese");
    public static final LanguageType mus = new LanguageType("mus", "Mvskoke", "Muskogee/Creek");
    public static final LanguageType mwl = new LanguageType("mwl", "MirandГ©s", "Mirandese");
    public static final LanguageType my = new LanguageType("my", "Myanmasa", "Burmese");
    public static final LanguageType myv = new LanguageType("myv", "Р­СЂР·СЏРЅСЊ", "Erzya");
    public static final LanguageType mzn = new LanguageType("mzn", "Щ…ЩЋШІЩђШ±Щ€Щ†ЩЉ", "Mazanderani");
    public static final LanguageType na = new LanguageType("na", "Dorerin Naoero", "Nauruan");
    public static final LanguageType nah = new LanguageType("nah", "NДЃhuatl", "Nahuatl");
    public static final LanguageType nan = new LanguageType("nan", "BГўn-lГўm-gГє", "Min-nan");
    public static final LanguageType nap = new LanguageType("nap", "Nnapulitano", "Neapolitan");
    
    public static final LanguageType ne = new LanguageType("ne", "а¤ЁаҐ‡а¤Єа¤ѕа¤ІаҐЂ", "Nepali");
    
    public static final LanguageType ng = new LanguageType("ng", "Oshiwambo", "Ndonga");
    public static final LanguageType niu = new LanguageType("niu", "NiuД“", "Niuean");
    public static final LanguageType nl = new LanguageType("nl", "Nederlands", "Dutch");
    
    public static final LanguageType nov = new LanguageType("nov", "Novial", "Novial");
    public static final LanguageType nrm = new LanguageType("nrm", "Nouormand", "Norman");
    public static final LanguageType nso = new LanguageType("nso", "Sesotho sa Leboa", "Northern Sotho");
    
    public static final LanguageType ny = new LanguageType("ny", "Chi-Chewa", "Chichewa");
    public static final LanguageType oc = new LanguageType("oc", "Occitan", "Occitan");
    public static final LanguageType om = new LanguageType("om", "Oromoo", "Oromo");
    public static final LanguageType or = new LanguageType("or", "а¬“а­ња¬їа¬†", "Oriya");
    public static final LanguageType os = new LanguageType("os", "РСЂРѕРЅР°Сѓ", "Ossetic");
    public static final LanguageType pa = new LanguageType("pa", "аЁЄа©°аЁњаЁѕаЁ¬а©Ђ", "Eastern Punjabi (pan)");
    public static final LanguageType pag = new LanguageType("pag", "Pangasinan", "Pangasinan");
    public static final LanguageType pam = new LanguageType("pam", "Kapampangan", "Pampanga");
    public static final LanguageType pap = new LanguageType("pap", "Papiamentu", "Papiamentu");
    public static final LanguageType pdc = new LanguageType("pdc", "Deitsch", "Pennsylvania German");
    public static final LanguageType pdt = new LanguageType("pdt", "Plautdietsch", "Plautdietsch/Mennonite Low German");
    public static final LanguageType pfl = new LanguageType("pfl", "PfГ¤lzisch", "Palatinate German");
    public static final LanguageType pi = new LanguageType("pi", "а¤Єа¤ѕа¤їа¤ґ", "Pali");
    public static final LanguageType pih = new LanguageType("pih", "Norfuk / Pitkern", "Norfuk/Pitcairn/Norfolk");
    public static final LanguageType pl = new LanguageType("pl", "Polski", "Polish");
    public static final LanguageType plm = new LanguageType("plm", "Palembang", "Palembang");
    public static final LanguageType pms = new LanguageType("pms", "PiemontГЁis", "Piedmontese");
    public static final LanguageType pnb = new LanguageType("pnb", "ЩѕЩ†Ш¬Ш§ШЁЫЊ", "Western Punjabi");
    public static final LanguageType pnt = new LanguageType("pnt", "О ОїОЅП„О№О±ОєО¬", "Pontic/Pontic Greek");
    public static final LanguageType ps = new LanguageType("ps", "ЩѕЪљШЄЩ€", "Pashto, Northern/Paktu/Pakhtu/Pakhtoo/Afghan/Pakhto/Pashtu/Pushto/Yusufzai Pashto");
    public static final LanguageType pt = new LanguageType("pt", "PortuguГЄs", "Portuguese");
    
    public static final LanguageType qu = new LanguageType("qu", "Runa Simi", "Quechua");
    public static final LanguageType rif = new LanguageType("rif", "Tarifit", "Tarifit");
    public static final LanguageType rm = new LanguageType("rm", "Rumantsch", "Raeto-Romance");
    public static final LanguageType roh = new LanguageType("roh", "Rumantsch", "Raeto-Romance");// Романшский
    public static final LanguageType rmy = new LanguageType("rmy", "Romani", "Vlax Romany");
    public static final LanguageType rn = new LanguageType("rn", "Kirundi", "Rundi/Kirundi/Urundi");
    public static final LanguageType ro = new LanguageType("ro", "RomГўnДѓ", "Romanian");
    
    public static final LanguageType ru = new LanguageType("ru", "Р СѓСЃСЃРєРёР№", "Russian");
    public static final LanguageType ruq = new LanguageType("ruq", "VlДѓheЕџte", "Megleno-Romanian (falls back to ruq-latn)");
    
    public static final LanguageType rw = new LanguageType("rw", "Kinyarwanda", "Kinyarwanda, should possibly be Kinyarwandi");
    public static final LanguageType sa = new LanguageType("sa", "а¤ёа¤‚а¤ёаҐЌа¤•аҐѓа¤¤", "Sanskrit");
    public static final LanguageType sah = new LanguageType("sah", "РЎР°С…Р° С‚С‹Р»Р°", "Sakha");
    public static final LanguageType sc = new LanguageType("sc", "Sardu", "Sardinian");
    public static final LanguageType scn = new LanguageType("scn", "Sicilianu", "Sicilian");
    public static final LanguageType sco = new LanguageType("sco", "Scots", "Scots");
    public static final LanguageType sd = new LanguageType("sd", "ШіЩ†ЪЊЩЉ", "Sindhi");
    public static final LanguageType sdc = new LanguageType("sdc", "Sassaresu", "Sassarese");
    public static final LanguageType se = new LanguageType("se", "SГЎmegiella", "Northern Sami");
    public static final LanguageType sei = new LanguageType("sei", "Cmique Itom", "Seri");
    public static final LanguageType sg = new LanguageType("sg", "SГ¤ngГ¶", "Sango/Sangho");
    public static final LanguageType sh = new LanguageType("sh", "Srpskohrvatski / РЎСЂРїСЃРєРѕС…СЂРІР°С‚СЃРєРё", "Serbocroatian");
    public static final LanguageType shi = new LanguageType("shi", "TaЕЎlбёҐiyt", "Tachelhit");
    public static final LanguageType si = new LanguageType("si", "а·ѓа·’а¶‚а·„а¶Ѕ", "Sinhalese");
    public static final LanguageType simple = new LanguageType("simple", "Simple English", "Simple English");
    public static final LanguageType sk = new LanguageType("sk", "SlovenДЌina", "Slovak");
    public static final LanguageType sl = new LanguageType("sl", "SlovenЕЎДЌina", "Slovenian");
    public static final LanguageType sma = new LanguageType("sma", "Г…arjelsaemien", "Southern Sami");
    public static final LanguageType sn = new LanguageType("sn", "chiShona", "Shona");
    public static final LanguageType so = new LanguageType("so", "Soomaaliga", "Somali");
    public static final LanguageType sq = new LanguageType("sq", "Shqip", "Albanian");
    
    public static final LanguageType srn = new LanguageType("srn", "Sranantongo", "Sranan Tongo");
    public static final LanguageType ss = new LanguageType("ss", "SiSwati", "Swati");
    public static final LanguageType st = new LanguageType("st", "Sesotho", "Southern Sotho");
    public static final LanguageType stq = new LanguageType("stq", "Seeltersk", "Saterland Frisian");
    public static final LanguageType su = new LanguageType("su", "Basa Sunda", "Sundanese");
    public static final LanguageType sv = new LanguageType("sv", "Svenska", "Swedish");
    public static final LanguageType sw = new LanguageType("sw", "Kiswahili", "Swahili");
    public static final LanguageType szl = new LanguageType("szl", "ЕљlЕЇnski", "Silesian");
    public static final LanguageType ta = new LanguageType("ta", "а®¤а®®а®їа®ґаЇЌ", "Tamil");
    public static final LanguageType tcy = new LanguageType("tcy", "аІ¤аіЃаІіаіЃ", "Tulu");
    public static final LanguageType te = new LanguageType("te", "а°¤а±†а°Іа±Ѓа°—а±Ѓ", "Telugu");
    public static final LanguageType tet = new LanguageType("tet", "Tetun", "Tetun");
    public static final LanguageType tg = new LanguageType("tg", "РўРѕТ·РёРєУЈ", "Tajiki (falls back to tg-cyrl)");
    
    public static final LanguageType th = new LanguageType("th", "а№„аё—аёў", "Thai");
    public static final LanguageType tk = new LanguageType("tk", "TГјrkmen", "Turkmen");

    public static final LanguageType tn = new LanguageType("tn", "Setswana", "Setswana");
    public static final LanguageType to = new LanguageType("to", "faka-Tonga", "Tonga (Tonga Islands)");
    public static final LanguageType tokipona = new LanguageType("tokipona", "Toki Pona", "Toki Pona");
    public static final LanguageType tp = new LanguageType("tp", "Toki Pona (deprecated:tokipona)", "Toki Pona - non-standard language code");
    public static final LanguageType tpi = new LanguageType("tpi", "Tok Pisin", "Tok Pisin");
    public static final LanguageType tr = new LanguageType("tr", "TГјrkГ§e", "Turkish");
    
    public static final LanguageType tum = new LanguageType("tum", "chiTumbuka", "Tumbuka");
    public static final LanguageType tw = new LanguageType("tw", "Twi", "Twi, (FIXME!)");
    public static final LanguageType tyv = new LanguageType("tyv", "РўС‹РІР° РґС‹Р»", "Tyvan");
    public static final LanguageType tzm = new LanguageType("tzm", "вµњвґ°вµЋвґ°вµЈвµ‰вµ–вµњ", "(Central Morocco) Tamazight");
    public static final LanguageType udm = new LanguageType("udm", "РЈРґРјСѓСЂС‚", "Udmurt");
    public static final LanguageType ug = new LanguageType("ug", "UyghurcheвЂЋ / Ш¦Ы‡ЩЉШєЫ‡Ш±Ъ†Ы•", "Uyghur");
    public static final LanguageType uk = new LanguageType("uk", "РЈРєСЂР°С—РЅСЃСЊРєР°", "Ukrainian");
    public static final LanguageType ur = new LanguageType("ur", "Ш§Ш±ШЇЩ€", "Urdu");

    public static final LanguageType ve = new LanguageType("ve", "Tshivenda", "Venda");
    public static final LanguageType vec = new LanguageType("vec", "VГЁneto", "Venetian");
    public static final LanguageType vi = new LanguageType("vi", "Tiбєїng Viб»‡t", "Vietnamese");
    public static final LanguageType vls = new LanguageType("vls", "West-Vlams", "West Flemish");
    public static final LanguageType vo = new LanguageType("vo", "VolapГјk", "VolapГјk");
    public static final LanguageType wa = new LanguageType("wa", "Walon", "Walloon");
    public static final LanguageType war = new LanguageType("war", "Winaray", "Waray-Waray");
    public static final LanguageType wo = new LanguageType("wo", "Wolof", "Wolof");
    public static final LanguageType wuu = new LanguageType("wuu", "еђґиЇ­", "Wu Chinese");
    public static final LanguageType xal = new LanguageType("xal", "РҐР°Р»СЊРјРі", "Kalmyk-Oirat (Kalmuk, Kalmuck, Kalmack, Qalmaq, Kalmytskii Jazyk, Khal:mag, Oirat, Volga Oirat, European Oirat, Western Mongolian)");
    public static final LanguageType xmf = new LanguageType("xmf", "бѓ›бѓђбѓ бѓ’бѓђбѓљбѓЈбѓ бѓ", "Mingrelian");
    public static final LanguageType ydd = new LanguageType("ydd", "ЧћЧ™Ч–ЧЁЧ—ЦѕЧ™Ч™ЦґЧ“Ч™Ч©", "Eastern Yiddish");
    public static final LanguageType yi = new LanguageType("yi", "Ч™Ч™ЦґЧ“Ч™Ч©", "Yiddish");
    public static final LanguageType yo = new LanguageType("yo", "YorГ№bГЎ", "Yoruba");
    
    public static final LanguageType za = new LanguageType("za", "(Cuengh)", "Zhuang");
    public static final LanguageType zea = new LanguageType("zea", "ZeГЄuws", "Zeeuws/Zeaws");
    public static final LanguageType zh = new LanguageType("zh", "дё­ж–‡", "(ZhЕЌng WГ©n) - Chinese");
    
}
