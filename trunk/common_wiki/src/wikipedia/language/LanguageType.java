/*
 * LanguageType.java - code of languages in wiki.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikipedia.language;

import java.util.Map;
import java.util.HashMap;

/** Languages of wiki: code and name, e.g. ru and Russian. 
 *
 * Source of data: mediawiki-1.10.1/languages/Names.php
 */
public class LanguageType {
    
    /** Two (or more) letter language code, e.g. 'en', 'ru'. */
    private final String code;
    /** Language name, e.g. 'English', 'Russian'. */
    private final String name;
    
    private static Map<String, String>       code2name = new HashMap<String, String>();
    private static Map<String, LanguageType> code2lang = new HashMap<String, LanguageType>();
    
    private LanguageType(String code,String name) { 
        this.code = code;
        this.name = name;
        code2name.put(code, name);
        code2lang.put(code, this);
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
    
    /** Returns true if the language has this 'code'. */
    public boolean equals(String code) {
        return code.equalsIgnoreCase(this.code);
    }
    
    /** Gets LanguageType by language code */
    public static LanguageType get(String code) throws NullPointerException
    {
        if(code2lang.containsKey(code)) {
            return code2lang.get(code);
         }    
        throw new NullPointerException("Null LanguageType");
    }

    /** Counts number of languages. */
    public static int size() {

        return code2name.size();
    }
    
    // English Wiktionary specific codes
    public static final LanguageType translingual = new LanguageType("translingual", "Translingual");// translingual, INTernational
    
    // Russian Wiktionary specific codes
    public static final LanguageType ain    = new LanguageType("ain", "Ainu");// Ainu, Айнский
    public static final LanguageType art    = new LanguageType("art", "Toki pona");// Toki pona, Токипона
    public static final LanguageType slovio = new LanguageType("slovio", "Slovio");// Slovio, Словио
    public static final LanguageType slovio_la = new LanguageType("slovio-la", "Slovio");// Slovio, Словио
    public static final LanguageType translingual_ru = new LanguageType("INT", "Translingual");// translingual, INTernational
    
    
    // manually added languages:
    public static final LanguageType crh_latn = new LanguageType("crh_latn", "Crimean Tatar (Latin)");// Crimean Tatar (Latin)
    public static final LanguageType crh_cyrl = new LanguageType("crh_cyrl", "Crimean Tatar (Cyrillic)");// Crimean Tatar (Cyrillic)
    
    public static final LanguageType gn = new LanguageType("gn", "Guarani");// Guarani, Paraguayan
    
    public static final LanguageType ht = new LanguageType("ht", "Haitian Creole French");// Haitian Creole French, Krey??l ayisyen
            
    public static final LanguageType kk_cn = new LanguageType("kk_cn", "Kazakh Arabic");// Kazakh Arabic
    public static final LanguageType kk_kz = new LanguageType("kk_kz", "Kazakh Cyrillic");// Kazakh Cyrillic
    public static final LanguageType kk_tr = new LanguageType("kk_tr", "Kazakh Latin");// Kazakh Latin
    public static final LanguageType kk_arab = new LanguageType("kk_arab", "Kazakh Arabic");// Kazakh Arabic
    public static final LanguageType kk_cyrl = new LanguageType("kk_cyrl", "Kazakh Latin");// Kazakh Cyrillic
    public static final LanguageType kk_latn = new LanguageType("kk_latn", "Kazakh Latin");// Kazakh Latin
    
    public static final LanguageType ku = new LanguageType("ku", "Kurdish");// Kurdish
    public static final LanguageType ku_latn = new LanguageType("ku_latn", "Northern Kurdish Latin script");// Northern Kurdish Latin script
    public static final LanguageType ku_arab = new LanguageType("ku_arab", "Northern Kurdish Arabic script");// Northern Kurdish Arabic script
    
    public static final LanguageType ksh_c_a = new LanguageType("ksh-c-a", "Ripoarisch c a");// Ripuarian 
    public static final LanguageType ksh_p_b = new LanguageType("ksh-p-b", "Ripoarisch p b");// Ripuarian 
    public static final LanguageType nb = new LanguageType("nb", "Norwegian (Bokmal)");// Norwegian (Bokmal)
    
    public static final LanguageType new_ = new LanguageType("new", "Newar / Nepal Bhasa");// Newar / Nepal Bhasa

    public static final LanguageType nn = new LanguageType("nn", "Norwegian (Nynorsk)");// Norwegian (Nynorsk)
    public static final LanguageType no = new LanguageType("no", "Norwegian");// Norwegian
    
    public static final LanguageType uz = new LanguageType("uz", "Uzbek");// Uzbek
    public static final LanguageType ve = new LanguageType("ve", "Tshivenda");// Venda
    
    public static final LanguageType zh_cn = new LanguageType("zh_cn", "Chinese (PRC)");// Chinese (PRC)
    public static final LanguageType zh_hans = new LanguageType("zh_hans", "Chinese written using the Simplified Chinese script"); // Chinese written using the Simplified Chinese script
    public static final LanguageType zh_hant = new LanguageType("zh_hant", "Chinese written using the Traditional Chinese script");// Chinese written using the Traditional Chinese script
    public static final LanguageType zh_sg = new LanguageType("zh_sg", "Chinese (Singapore)");// Chinese (Singapore)
    public static final LanguageType zh_tw = new LanguageType("zh_tw", "Chinese (Taiwan)");// Chinese (Taiwan)
    public static final LanguageType zh_hk = new LanguageType("zh_hk", "Chinese (Hong Kong)");// Chinese (Hong Kong)
    public static final LanguageType zh_mo = new LanguageType("zh_mo", "Chinese (Macau)");// Chinese (Macau)
    public static final LanguageType zh_my = new LanguageType("zh_my", "Chinese (Malaysia)");// Chinese (Malaysia)
    
    public static final LanguageType zu = new LanguageType("zu", "isiZulu");// Cantonese -- (see bug 8217)
        
    // automatically
    /** Vim commands to convert mediawiki/languages/Names.php to the following
     * lines:
     * 0. e ++enc=utf8
     * 1. %s/#/\/\//g               PHP to Java comments  
     * 2. code to underscore, e.g. bat-smg -> bat_smg (PHP to Java variable names)
     *    %s/\(\t'[^'-]\+\)-\([^'-]\+' => \)/\1_\2/g
     * (44 languages, exception: zh-min-nan, be-x-old) */
    // 3. %s/\t'\([^']\+\)' => ['"]\([^']\+\)['"],[ \t]*/    public static final LanguageType \1 = new LanguageType("\1", "\2");/
    
    public static final LanguageType aa = new LanguageType("aa", "Afar");// Afar
    public static final LanguageType ab = new LanguageType("ab", "А??суа");// Abkhaz, should possibly add ' бысж??а'
    public static final LanguageType af = new LanguageType("af", "Afrikaans");// Afrikaans
    public static final LanguageType ak = new LanguageType("ak", "Akan");// Akan
    public static final LanguageType aln = new LanguageType("aln", "Geg??");// Gheg Albanian
    public static final LanguageType als = new LanguageType("als", "Alemannisch");// Alemannic -- not a valid code, for compatibility. See gsw.
    public static final LanguageType am = new LanguageType("am", "????????????");// Amharic
    public static final LanguageType an = new LanguageType("an", "Aragon??s");// Aragonese
    public static final LanguageType ang = new LanguageType("ang", "Anglo-Saxon");// Old English
    public static final LanguageType ar = new LanguageType("ar", "??????????????");// Arabic
    public static final LanguageType arc = new LanguageType("arc", "??????????");// Aramaic
    public static final LanguageType arn = new LanguageType("arn", "Mapudungun");// Mapuche, Mapudungu, Araucanian (Araucano)
    public static final LanguageType arz = new LanguageType("arz", "????????");// Egyptian Spoken Arabic
    public static final LanguageType as = new LanguageType("as", "??????????????????");// Assamese
    public static final LanguageType ast = new LanguageType("ast", "Asturianu");// Asturian
    public static final LanguageType av = new LanguageType("av", "Авар");// Avar
    public static final LanguageType avk = new LanguageType("avk", "Kotava");// Kotava
    public static final LanguageType ay = new LanguageType("ay", "Aymar");// Aymara, should possibly be Aymar??
    public static final LanguageType az = new LanguageType("az", "Az??rbaycan");// Azerbaijani
    public static final LanguageType ba = new LanguageType("ba", "Баш??орт");// Bashkir
    public static final LanguageType bar = new LanguageType("bar", "Boarisch");// Bavarian (Austro-Bavarian and South Tyrolean)
    public static final LanguageType bat_smg = new LanguageType("bat_smg", "??emait????ka");// Samogitian
    public static final LanguageType bcc = new LanguageType("bcc", "?????????? ????????????");// Southern Balochi
    public static final LanguageType bcl = new LanguageType("bcl", "Bikol Central");// Bikol: Central Bicolano language
    public static final LanguageType be = new LanguageType("be", "Беларуская");//  Belarusian normative
    public static final LanguageType be_tarask = new LanguageType("be_tarask", "Беларуская (тарашкевіца)");// Belarusian in Taraskievica orthography
    public static final LanguageType be_x_old = new LanguageType("be_x_old", "Беларуская (тарашкевіца)");// Belarusian in Taraskievica orthography; compat link
    public static final LanguageType bg = new LanguageType("bg", "Български");// Bulgarian
    public static final LanguageType bh = new LanguageType("bh", "?????????????????????");// Bhojpuri
    public static final LanguageType bi = new LanguageType("bi", "Bislama");// Bislama
    public static final LanguageType bm = new LanguageType("bm", "Bamanankan");// Bambara
    public static final LanguageType bn = new LanguageType("bn", "???????????????");// Bengali
    public static final LanguageType bo = new LanguageType("bo", "?????????????????????");// Tibetan
    public static final LanguageType bpy = new LanguageType("bpy", "???????????? ?????????/??????????????????????????????????????? ?????????????????????");// Bishnupriya Manipuri
    public static final LanguageType br = new LanguageType("br", "Brezhoneg");// Breton
    public static final LanguageType bs = new LanguageType("bs", "Bosanski");// Bosnian
    public static final LanguageType bto = new LanguageType("bto", "Iriga Bicolano");// Iriga Bicolano/Rinconada Bikol
    public static final LanguageType bug = new LanguageType("bug", "?????? ????????????");// Bugis
    public static final LanguageType bxr = new LanguageType("bxr", "Буряад");// Buryat (Russia)
    public static final LanguageType ca = new LanguageType("ca", "Catal??");// Catalan
    public static final LanguageType cbk_zam = new LanguageType("cbk_zam", "Chavacano de Zamboanga");// Zamboanga Chavacano
    public static final LanguageType cdo = new LanguageType("cdo", "M??ng-d????ng-ng?????");// Min Dong
    public static final LanguageType ce = new LanguageType("ce", "Нохчийн");// Chechen
    public static final LanguageType ceb = new LanguageType("ceb", "Cebuano");// Cebuano
    public static final LanguageType ch = new LanguageType("ch", "Chamoru");// Chamorro
    public static final LanguageType cho = new LanguageType("cho", "Choctaw");// Choctaw
    public static final LanguageType chr = new LanguageType("chr", "?????????");// Cherokee
    public static final LanguageType chy = new LanguageType("chy", "Tsets??hest??hese");// Cheyenne
    public static final LanguageType co = new LanguageType("co", "Corsu");// Corsican
    public static final LanguageType cr = new LanguageType("cr", "N??hiyaw??win / ?????????????????????");// Cree
    public static final LanguageType crh = new LanguageType("crh", "Q??r??mtatarca");// Crimean Tatar
    
    public static final LanguageType cs = new LanguageType("cs", "??esky");// Czech
    public static final LanguageType csb = new LanguageType("csb", "Kasz??bsczi");// Cassubian
    public static final LanguageType cu = new LanguageType("cu", "Слов????ньскъ / ??????????????????????????????");// Old Church Slavonic (ancient language)
    public static final LanguageType cv = new LanguageType("cv", "Ч??вашла");// Chuvash
    public static final LanguageType cy = new LanguageType("cy", "Cymraeg");// Welsh
    public static final LanguageType da = new LanguageType("da", "Dansk");// Danish
    public static final LanguageType de = new LanguageType("de", "Deutsch");// German ("Du")
    public static final LanguageType de_formal = new LanguageType("de_formal", "Deutsch (Sie-Form)");// German - formal address ("Sie")
    public static final LanguageType diq = new LanguageType("diq", "Zazaki");// Zazaki
    public static final LanguageType dk = new LanguageType("dk", "Dansk (deprecated:da)");// Unused code currently falls back to Danish, 'da' is correct for the language
    public static final LanguageType dsb = new LanguageType("dsb", "Dolnoserbski");// Lower Sorbian
    public static final LanguageType dv = new LanguageType("dv", "????????????????????");// Dhivehi
    public static final LanguageType dz = new LanguageType("dz", "???????????????");// Bhutani
    public static final LanguageType ee = new LanguageType("ee", "E??egbe");// ??w??
    public static final LanguageType el = new LanguageType("el", "????????????????");// Greek
    public static final LanguageType eml = new LanguageType("eml", "Emili??n e rumagn??l");// Emiliano-Romagnolo / Sammarinese
    public static final LanguageType en = new LanguageType("en", "English");// English
    public static final LanguageType en_gb = new LanguageType("en_gb", "British English");// British English
    public static final LanguageType eo = new LanguageType("eo", "Esperanto");// Esperanto
    public static final LanguageType es = new LanguageType("es", "Espa??ol");// Spanish
    public static final LanguageType et = new LanguageType("et", "Eesti");// Estonian
    public static final LanguageType eu = new LanguageType("eu", "Euskara");// Basque
    public static final LanguageType ext = new LanguageType("ext", "Estreme??u");// Extremaduran
    public static final LanguageType fa = new LanguageType("fa", "??????????");// Persian
    public static final LanguageType ff = new LanguageType("ff", "Fulfulde");// Fulfulde, Maasina
    public static final LanguageType fi = new LanguageType("fi", "Suomi");// Finnish
    public static final LanguageType fiu_vro = new LanguageType("fiu_vro", "V??ro");// V??ro
    public static final LanguageType fj = new LanguageType("fj", "Na Vosa Vakaviti");// Fijian
    public static final LanguageType fo = new LanguageType("fo", "F??royskt");// Faroese
    public static final LanguageType fr = new LanguageType("fr", "Fran??ais");// French
    public static final LanguageType frc = new LanguageType("frc", "Fran??ais cadien");// Cajun French
    public static final LanguageType frp = new LanguageType("frp", "Arpetan");// Franco-Proven??al/Arpitan
    public static final LanguageType fur = new LanguageType("fur", "Furlan");// Friulian
    public static final LanguageType fy = new LanguageType("fy", "Frysk");// Frisian
    public static final LanguageType ga = new LanguageType("ga", "Gaeilge");// Irish
    public static final LanguageType gag = new LanguageType("gag", "Gagauz");// Gagauz
    public static final LanguageType gan = new LanguageType("gan", "??????");// Gan
    public static final LanguageType gd = new LanguageType("gd", "G??idhlig");// Scots Gaelic
    public static final LanguageType gl = new LanguageType("gl", "Galego");// Galician
    public static final LanguageType glk = new LanguageType("glk", "??????????");// Gilaki
	
    public static final LanguageType got = new LanguageType("got", "????????????????????????");// Gothic
    public static final LanguageType grc = new LanguageType("grc", "????????????? ??????????????????");// Ancient Greece
    public static final LanguageType gsw = new LanguageType("gsw", "Alemannisch");// Alemannic
    public static final LanguageType gu = new LanguageType("gu", "?????????????????????");// Gujarati
    public static final LanguageType gv = new LanguageType("gv", "Gaelg");// Manx
    public static final LanguageType ha = new LanguageType("ha", "????????????");// Hausa
    public static final LanguageType hak = new LanguageType("hak", "Hak-k??-fa");// Hakka
    public static final LanguageType haw = new LanguageType("haw", "Hawai`i");// Hawaiian
    public static final LanguageType he = new LanguageType("he", "??????????");// Hebrew
    public static final LanguageType hi = new LanguageType("hi", "??????????????????");// Hindi
    public static final LanguageType hif = new LanguageType("hif", "Fiji Hindi");// Fijian Hindi (falls back to hif-latn)
    public static final LanguageType hif_deva = new LanguageType("hif_deva", "??????????????? ??????????????????");// Fiji Hindi (devangari)
    public static final LanguageType hif_latn = new LanguageType("hif_latn", "Fiji Hindi");// Fiji Hindi (latin)
    public static final LanguageType hil = new LanguageType("hil", "Ilonggo");// Hiligaynon
    public static final LanguageType ho = new LanguageType("ho", "Hiri Motu");// Hiri Motu
    public static final LanguageType hr = new LanguageType("hr", "Hrvatski");// Croatian
    public static final LanguageType hsb = new LanguageType("hsb", "Hornjoserbsce");// Upper Sorbian
	
    public static final LanguageType hu = new LanguageType("hu", "Magyar");// Hungarian
    public static final LanguageType hy = new LanguageType("hy", "??????????????");// Armenian
    public static final LanguageType hz = new LanguageType("hz", "Otsiherero");// Herero
    public static final LanguageType ia = new LanguageType("ia", "Interlingua");// Interlingua (IALA)
    public static final LanguageType id = new LanguageType("id", "Bahasa Indonesia");// Indonesian
    public static final LanguageType ie = new LanguageType("ie", "Interlingue");// Interlingue (Occidental)
    public static final LanguageType ig = new LanguageType("ig", "Igbo");// Igbo
    public static final LanguageType ii = new LanguageType("ii", "??????");// Sichuan Yi
    public static final LanguageType ik = new LanguageType("ik", "I??upiak");// Inupiak (Inupiatun, Northwest Alaska / Inupiatun, North Alaskan)
    public static final LanguageType ike_cans = new LanguageType("ike_cans", "??????????????????");// Inuktitut, Eastern Canadian/Eastern Canadian "Eskimo"/"Eastern Arctic Eskimo"/Inuit (Unified Canadian Aboriginal Syllabics)
    public static final LanguageType ike_latn = new LanguageType("ike_latn", "inuktitut");// Inuktitut, Eastern Canadian (Latin script)
    public static final LanguageType ilo = new LanguageType("ilo", "Ilokano");// Ilokano
    public static final LanguageType inh = new LanguageType("inh", "ГІалгІай ??al??aj");// Ingush
    public static final LanguageType io = new LanguageType("io", "Ido");// Ido
    public static final LanguageType is = new LanguageType("is", "??slenska");// Icelandic
    public static final LanguageType it = new LanguageType("it", "Italiano");// Italian
    public static final LanguageType iu = new LanguageType("iu", "??????????????????/inuktitut");// Inuktitut (macro language - do no localise, see ike/ikt - falls back to ike-cans)
    public static final LanguageType ja = new LanguageType("ja", "?????????");// Japanese
    public static final LanguageType jbo = new LanguageType("jbo", "Lojban");// Lojban
    public static final LanguageType jut = new LanguageType("jut", "Jysk");// Jutish / Jutlandic
    public static final LanguageType jv = new LanguageType("jv", "Basa Jawa");// Javanese
    public static final LanguageType ka = new LanguageType("ka", "?????????????????????");// Georgian
    public static final LanguageType kaa = new LanguageType("kaa", "Qaraqalpaqsha");// Karakalpak
    public static final LanguageType kab = new LanguageType("kab", "Taqbaylit");// Kabyle
    public static final LanguageType kg = new LanguageType("kg", "Kongo");// Kongo, (FIXME!) should probaly be KiKongo or KiKoongo
    public static final LanguageType ki = new LanguageType("ki", "G??k??y??");// Gikuyu
    public static final LanguageType kj = new LanguageType("kj", "Kwanyama");// Kwanyama
    public static final LanguageType kk = new LanguageType("kk", "??аза??ша");// Kazakh
    
    public static final LanguageType kl = new LanguageType("kl", "Kalaallisut");// Inuktitut, Greenlandic/Greenlandic/Kalaallisut (kal)
    public static final LanguageType km = new LanguageType("km", "???????????????????????????");// Khmer, Central
    public static final LanguageType kn = new LanguageType("kn", "???????????????");// Kannada
    public static final LanguageType ko = new LanguageType("ko", "?????????");// Korean
    public static final LanguageType kr = new LanguageType("kr", "Kanuri");// Kanuri, Central
    public static final LanguageType kri = new LanguageType("kri", "Krio");// Krio
    public static final LanguageType krj = new LanguageType("krj", "Kinaray-a");// Kinaray-a
    public static final LanguageType ks = new LanguageType("ks", "????????????????????? - (????????????)");// Kashmiri
    public static final LanguageType ksh = new LanguageType("ksh", "Ripoarisch");// Ripuarian 
	
    public static final LanguageType kv = new LanguageType("kv", "Коми");// Komi-Zyrian, cyrillic is common script but also written in latin script
    public static final LanguageType kw = new LanguageType("kw", "Kernewek");// Cornish
    public static final LanguageType ky = new LanguageType("ky", "Кыргызча");// Kirghiz
    public static final LanguageType la = new LanguageType("la", "Latina");// Latin
    public static final LanguageType lad = new LanguageType("lad", "Ladino");// Ladino
    public static final LanguageType lb = new LanguageType("lb", "L??tzebuergesch");// Luxemburguish
    public static final LanguageType lbe = new LanguageType("lbe", "Лакку");// Lak
    public static final LanguageType lez = new LanguageType("lez", "Лезги");// Lezgi
    public static final LanguageType lfn = new LanguageType("lfn", "Lingua Franca Nova");// Lingua Franca Nova
    public static final LanguageType lg = new LanguageType("lg", "Luganda");// Ganda
    public static final LanguageType li = new LanguageType("li", "Limburgs");// Limburgian
    public static final LanguageType lij = new LanguageType("lij", "L??guru");// Ligurian
    public static final LanguageType lld = new LanguageType("lld", "Ladin");// Ladin
    public static final LanguageType lmo = new LanguageType("lmo", "Lumbaart");// Lombard
    public static final LanguageType ln = new LanguageType("ln", "Ling??la");// Lingala
    public static final LanguageType lo = new LanguageType("lo", "?????????");// Laotian
    public static final LanguageType loz = new LanguageType("loz", "Silozi");// Lozi
    public static final LanguageType lt = new LanguageType("lt", "Lietuvi??");// Lithuanian
    public static final LanguageType lv = new LanguageType("lv", "Latvie??u");// Latvian
    public static final LanguageType lzz = new LanguageType("lzz", "Lazuri Nena");//Laz
    public static final LanguageType mai = new LanguageType("mai", "??????????????????");// Maithili
    public static final LanguageType map_bms = new LanguageType("map_bms", "Basa Banyumasan");// Banyumasan 
    public static final LanguageType mdf = new LanguageType("mdf", "Мокшень");// Moksha
    public static final LanguageType mg = new LanguageType("mg", "Malagasy");// Malagasy
    public static final LanguageType mh = new LanguageType("mh", "Ebon");// Marshallese
    public static final LanguageType mi = new LanguageType("mi", "M??ori");// Maori
    public static final LanguageType mk = new LanguageType("mk", "Македонски");// Macedonian
    public static final LanguageType ml = new LanguageType("ml", "??????????????????");// Malayalam
    public static final LanguageType mn = new LanguageType("mn", "Монгол");// Halh Mongolian (Cyrillic) (ISO 639-3: khk)
    public static final LanguageType mo = new LanguageType("mo", "Молдовеняскэ");// Moldovan
    public static final LanguageType mr = new LanguageType("mr", "???????????????");// Marathi
    public static final LanguageType ms = new LanguageType("ms", "Bahasa Melayu");// Malay
    public static final LanguageType mt = new LanguageType("mt", "Malti");// Maltese
    public static final LanguageType mus = new LanguageType("mus", "Mvskoke");// Muskogee/Creek
    public static final LanguageType mwl = new LanguageType("mwl", "Mirand??s");// Mirandese
    public static final LanguageType my = new LanguageType("my", "Myanmasa");// Burmese
    public static final LanguageType myv = new LanguageType("myv", "Эрзянь");// Erzya
    public static final LanguageType mzn = new LanguageType("mzn", "????????????????");// Mazanderani
    public static final LanguageType na = new LanguageType("na", "Ekakair?? Naoero");// Nauruan
    public static final LanguageType nah = new LanguageType("nah", "Nahuatl");// Nahuatl, en:Wikipedia writes Nahuatlahtolli, while another form is N??huatl
    public static final LanguageType nan = new LanguageType("nan", "B??n-l??m-g??");// Min-nan -- (bug 8217) nan instead of zh-min-nan, http://www.sil.org/iso639-3/codes.asp?order=639_3&letter=n
    public static final LanguageType nap = new LanguageType("nap", "Nnapulitano");// Neapolitan
    
    public static final LanguageType nds = new LanguageType("nds", "Plattd????tsch");// Low German ''or'' Low Saxon
    public static final LanguageType nds_nl = new LanguageType("nds_nl", "Nedersaksisch");// Dutch Low Saxon
    public static final LanguageType ne = new LanguageType("ne", "??????????????????");// Nepali
    
    public static final LanguageType ng = new LanguageType("ng", "Oshiwambo");// Ndonga
    public static final LanguageType niu = new LanguageType("niu", "Niu??");// Niuean
    public static final LanguageType nl = new LanguageType("nl", "Nederlands");// Dutch
    
    public static final LanguageType nov = new LanguageType("nov", "Novial");// Novial
    public static final LanguageType nrm = new LanguageType("nrm", "Nouormand");// Norman
    public static final LanguageType nso = new LanguageType("nso", "Sesotho sa Leboa");// Northern Sotho
    public static final LanguageType nv = new LanguageType("nv", "Din?? bizaad");// Navajo
    public static final LanguageType ny = new LanguageType("ny", "Chi-Chewa");// Chichewa
    public static final LanguageType oc = new LanguageType("oc", "Occitan");// Occitan
    public static final LanguageType om = new LanguageType("om", "Oromoo");// Oromo
    public static final LanguageType or = new LanguageType("or", "????????????");// Oriya
    public static final LanguageType os = new LanguageType("os", "Иронау");// Ossetic
    public static final LanguageType pa = new LanguageType("pa", "??????????????????");// Punjabi
    public static final LanguageType pag = new LanguageType("pag", "Pangasinan");// Pangasinan
    public static final LanguageType pam = new LanguageType("pam", "Kapampangan");// Pampanga
    public static final LanguageType pap = new LanguageType("pap", "Papiamentu");// Papiamentu
    public static final LanguageType pdc = new LanguageType("pdc", "Deitsch");// Pennsylvania German
    public static final LanguageType pdt = new LanguageType("pdt", "Plautdietsch");// Plautdietsch/Mennonite Low German
    public static final LanguageType pfl = new LanguageType("pfl", "Pf??lzisch");// Palatinate German
    public static final LanguageType pi = new LanguageType("pi", "????????????");// Pali
    public static final LanguageType pih = new LanguageType("pih", "Norfuk / Pitkern");// Norfuk/Pitcairn/Norfolk
    public static final LanguageType pl = new LanguageType("pl", "Polski");// Polish
    public static final LanguageType plm = new LanguageType("plm", "Palembang");// Palembang
    public static final LanguageType pms = new LanguageType("pms", "Piemont??is");// Piedmontese
    public static final LanguageType pnt = new LanguageType("pnt", "????????????????");// Pontic/Pontic Greek
    public static final LanguageType ps = new LanguageType("ps", "????????");// Pashto, Northern/Paktu/Pakhtu/Pakhtoo/Afghan/Pakhto/Pashtu/Pushto/Yusufzai Pashto
    public static final LanguageType pt = new LanguageType("pt", "Portugu??s");// Portuguese
    public static final LanguageType pt_br = new LanguageType("pt_br", "Portugu??s do Brasil");// Brazilian Portuguese
    public static final LanguageType qu = new LanguageType("qu", "Runa Simi");// Quechua
    public static final LanguageType rif = new LanguageType("rif", "Tarifit");// Tarifit
    public static final LanguageType rm = new LanguageType("rm", "Rumantsch");// Raeto-Romance
    public static final LanguageType rmy = new LanguageType("rmy", "Romani");// Vlax Romany
    public static final LanguageType rn = new LanguageType("rn", "Kirundi");// Rundi/Kirundi/Urundi
    public static final LanguageType ro = new LanguageType("ro", "Rom??n??");// Romanian
    public static final LanguageType roa_rup = new LanguageType("roa_rup", "Arm??neashce");// Aromanian
    public static final LanguageType roa_tara = new LanguageType("roa_tara", "Tarand??ne");// Tarantino
    public static final LanguageType ru = new LanguageType("ru", "Русский");// Russian
    public static final LanguageType ruq = new LanguageType("ruq", "Vl??he??te");// Megleno-Romanian (falls back to ruq-latn)
    public static final LanguageType ruq_cyrl = new LanguageType("ruq_cyrl", "Влахесте");// Megleno-Romanian (Cyrillic script)
    public static final LanguageType ruq_grek = new LanguageType("ruq_grek", "??????????????");// Megleno-Romanian (Greek script)
    public static final LanguageType ruq_latn = new LanguageType("ruq_latn", "Vl??he??te");// Megleno-Romanian (Latin script)
    public static final LanguageType rw = new LanguageType("rw", "Kinyarwanda");// Kinyarwanda, should possibly be Kinyarwandi
    public static final LanguageType sa = new LanguageType("sa", "?????????????????????");// Sanskrit
    public static final LanguageType sah = new LanguageType("sah", "Саха тыла");// Sakha
    public static final LanguageType sc = new LanguageType("sc", "Sardu");// Sardinian
    public static final LanguageType scn = new LanguageType("scn", "Sicilianu");// Sicilian
    public static final LanguageType sco = new LanguageType("sco", "Scots");// Scots
    public static final LanguageType sd = new LanguageType("sd", "????????");// Sindhi
    public static final LanguageType sdc = new LanguageType("sdc", "Sassaresu");// Sassarese
    public static final LanguageType se = new LanguageType("se", "S??megiella");// Northern Sami
    public static final LanguageType sei = new LanguageType("sei", "Cmique Itom");// Seri
    public static final LanguageType sg = new LanguageType("sg", "S??ng??");// Sango/Sangho
    public static final LanguageType sh = new LanguageType("sh", "Srpskohrvatski / Српскохрватски");// Serbocroatian
    public static final LanguageType shi = new LanguageType("shi", "Ta??l???iyt");// Tachelhit
    public static final LanguageType si = new LanguageType("si", "???????????????");// Sinhalese
    public static final LanguageType simple = new LanguageType("simple", "Simple English");// Simple English
    public static final LanguageType sk = new LanguageType("sk", "Sloven??ina");// Slovak
    public static final LanguageType sl = new LanguageType("sl", "Sloven????ina");// Slovenian
    public static final LanguageType sm = new LanguageType("sm", "Gagana Samoa");// Samoan
    public static final LanguageType sma = new LanguageType("sma", "??arjelsaemien g??ele");// Southern Sami
    public static final LanguageType sn = new LanguageType("sn", "chiShona");// Shona
    public static final LanguageType so = new LanguageType("so", "Soomaaliga");// Somali
    public static final LanguageType sq = new LanguageType("sq", "Shqip");// Albanian
    public static final LanguageType sr = new LanguageType("sr", "Српски / Srpski");// Serbian
    public static final LanguageType sr_ec = new LanguageType("sr_ec", "ћирилица");// Serbian cyrillic ekavian
    public static final LanguageType sr_el = new LanguageType("sr_el", "latinica");// Serbian latin ekavian
    public static final LanguageType srn = new LanguageType("srn", "Sranantongo");// Sranan Tongo
    public static final LanguageType ss = new LanguageType("ss", "SiSwati");// Swati
    public static final LanguageType st = new LanguageType("st", "Sesotho");// Southern Sotho
    public static final LanguageType stq = new LanguageType("stq", "Seeltersk");// Saterland Frisian
    public static final LanguageType su = new LanguageType("su", "Basa Sunda");// Sundanese
    public static final LanguageType sv = new LanguageType("sv", "Svenska");// Swedish
    public static final LanguageType sw = new LanguageType("sw", "Kiswahili");// Swahili
    public static final LanguageType szl = new LanguageType("szl", "??l??nski");// Silesian
    public static final LanguageType ta = new LanguageType("ta", "???????????????");// Tamil
    public static final LanguageType te = new LanguageType("te", "??????????????????");// Telugu
    public static final LanguageType tet = new LanguageType("tet", "Tetun");// Tetun
    public static final LanguageType tg = new LanguageType("tg", "То??ик??/tojik??");// Tajiki (falls back to tg-cyrl)
    public static final LanguageType tg_cyrl = new LanguageType("tg_cyrl", "То??ик??");// Tajiki (Cyrllic script) (default)
    public static final LanguageType tg_latn = new LanguageType("tg_latn", "tojik??");// Tajiki (Latin script)
    public static final LanguageType th = new LanguageType("th", "?????????");// Thai
    public static final LanguageType ti = new LanguageType("ti", "????????????");// Tigrinya
    public static final LanguageType tk = new LanguageType("tk", "T??rkmen");// Turkmen
    public static final LanguageType tl = new LanguageType("tl", "Tagalog");// Tagalog (Filipino)
	//'tlh' => 'tlhIngan-Hol',	// Klingon - no interlanguage links allowed
    public static final LanguageType tn = new LanguageType("tn", "Setswana");// Setswana
    public static final LanguageType to = new LanguageType("to", "faka-Tonga");// Tonga (Tonga Islands)
    public static final LanguageType tokipona = new LanguageType("tokipona", "Toki Pona");// Toki Pona
    public static final LanguageType tp = new LanguageType("tp", "Toki Pona (deprecated:tokipona)");// Toki Pona - non-standard language code
    public static final LanguageType tpi = new LanguageType("tpi", "Tok Pisin");// Tok Pisin
    public static final LanguageType tr = new LanguageType("tr", "T??rk??e");// Turkish
    public static final LanguageType ts = new LanguageType("ts", "Xitsonga");// Tsonga
    public static final LanguageType tt = new LanguageType("tt", "Tatar??a/Татарча");// Tatar (multiple scripts - defaults to Latin)
    public static final LanguageType tt_cyrl = new LanguageType("tt_cyrl", "Татарча");// Tatar (Cyrillic script)
    public static final LanguageType tt_latn = new LanguageType("tt_latn", "Tatar??a");// Tatar (Latin script)
    public static final LanguageType tum = new LanguageType("tum", "chiTumbuka");// Tumbuka
    public static final LanguageType tw = new LanguageType("tw", "Twi");// Twi, (FIXME!)
    public static final LanguageType ty = new LanguageType("ty", "Reo M??`ohi");// Tahitian
    public static final LanguageType tyv = new LanguageType("tyv", "Тыва дыл");// Tyvan
    public static final LanguageType tzm = new LanguageType("tzm", "????????????????????????");// (Central Morocco) Tamazight
    public static final LanguageType udm = new LanguageType("udm", "Удмурт");// Udmurt
    public static final LanguageType ug = new LanguageType("ug", "Uyghurche??? / ????????????????");// Uyghur
    public static final LanguageType uk = new LanguageType("uk", "Українська");// Ukrainian
    public static final LanguageType ur = new LanguageType("ur", "????????");// Urdu
	
    public static final LanguageType vec = new LanguageType("vec", "V??neto");// Venetian
    public static final LanguageType vi = new LanguageType("vi", "Ti???ng Vi???t");// Vietnamese
    public static final LanguageType vls = new LanguageType("vls", "West-Vlams");// West Flemish
    public static final LanguageType vo = new LanguageType("vo", "Volap??k");// Volap??k
    public static final LanguageType wa = new LanguageType("wa", "Walon");// Walloon
    public static final LanguageType war = new LanguageType("war", "Winaray");// Waray-Waray
    public static final LanguageType wo = new LanguageType("wo", "Wolof");// Wolof
    public static final LanguageType wuu = new LanguageType("wuu", "??????");// Wu Chinese
    public static final LanguageType xal = new LanguageType("xal", "Хальмг");// Kalmyk-Oirat (Kalmuk, Kalmuck, Kalmack, Qalmaq, Kalmytskii Jazyk, Khal:mag, Oirat, Volga Oirat, European Oirat, Western Mongolian)
    public static final LanguageType xh = new LanguageType("xh", "isiXhosa");// Xhosan
    public static final LanguageType xmf = new LanguageType("xmf", "???????????????????????????");// Mingrelian
    public static final LanguageType ydd = new LanguageType("ydd", "????????????????????????");// Eastern Yiddish
    public static final LanguageType yi = new LanguageType("yi", "????????????");// Yiddish
    public static final LanguageType yo = new LanguageType("yo", "Yor??b??");// Yoruba
    public static final LanguageType yue = new LanguageType("yue", "??????");// Cantonese -- (bug 8217) yue instead of zh-yue, http://www.sil.org/iso639-3/codes.asp?order=639_3&letter=y
    public static final LanguageType za = new LanguageType("za", "(Cuengh)");// Zhuang
    public static final LanguageType zea = new LanguageType("zea", "Ze??uws");// Zeeuws/Zeaws
    public static final LanguageType zh = new LanguageType("zh", "??????");// (Zh??ng W??n) - Chinese
    public static final LanguageType zh_classical = new LanguageType("zh_classical", "??????");// Classical Chinese/Literary Chinese
    
    public static final LanguageType zh_min_nan = new LanguageType("zh_min_nan", "B??n-l??m-g??");// Min-nan -- (see bug 8217)
    public static final LanguageType zh_yue = new LanguageType("zh_yue", "??????");// Cantonese -- (see bug 8217)
    
    
}
