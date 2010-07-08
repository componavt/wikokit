/* LanguageTypeRu.java - name of languages in Russian.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikipedia.language.local;

import wikipedia.language.LanguageType;
import wikipedia.language.LanguageTypeLocal;

import java.util.Map;
import java.util.HashMap;

/** Languages of wiki: name in Russian and link to the LanguageType codes.
 *
 * Source of data: 
 *
 * Russian Wikipedia: http://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4%D1%8B_%D1%8F%D0%B7%D1%8B%D0%BA%D0%BE%D0%B2
 *
 * Russian Wiktionary:
 *   Шаблон:перев-блок  or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BF%D0%B5%D1%80%D0%B5%D0%B2-%D0%B1%D0%BB%D0%BE%D0%BA
 *   Шаблон:lang        or http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:lang
 */
public class LanguageTypeRu extends LanguageTypeLocal {

    protected final static Map<String, LanguageType> name2type = new HashMap<String, LanguageType>();
    protected final static Map<LanguageType, String> type2name = new HashMap<LanguageType, String>();

    /** Remark: run the RelationTableAll.main() in order to check duplicates
     * of language names and language codes of this locale. 
     */
    protected LanguageTypeRu(String name,LanguageType type) {
        this.name   = name;
        this.type   = type;

        if(name.length() == 0)
            System.out.println("Error in LanguageTypeRu.LanguageTypeRu(): empty language name! The language code="+type+
                    ". Check the maps name2type and type2name.");

        // check the uniqueness of the language type and name
        String name_prev = type2name.get(type);
        LanguageType type_prev = name2type.get(name);

        if(null != name_prev)
            System.out.println("Error in LanguageTypeRu.LanguageTypeRu(): duplication of code! The language code="+type+
                    " language='"+ name +
                    "'. Check the maps name2type and type2name.");

        if(null != type_prev)
            System.out.println("Error in LanguageTypeRu.LanguageTypeRu(): duplication of language! The language code="+type+
                    " language='"+ name +
                    "'. Check the maps name2type and type2name.");

        name2type.put(name, type);
        type2name.put(type, name);
    }

    /** Checks weather exists the LanguageType by its name in Russian language. */
    public static boolean has(String name) {
        return name2type.containsKey(name);
    }

    /** Checks weather exists the translation for this LanguageType. */
    public static boolean has(LanguageType t) {
        return type2name.containsKey(t);
    }

    /** Gets LanguageType by its name in some language*/
    public static LanguageType get(String name) {
        return name2type.get(name);
    }

    public static String get (LanguageType lt) {

        String s = type2name.get(lt);
        if(null == s)
            return lt.getName(); // if there is no translation into local language, then English name

        return s;
    }

    /** Counts number of translations. */
    public static int size() {
        return name2type.size();
    }

    public static final LanguageTypeLocal PIE = new LanguageTypeRu("Праиндоевропейский", LanguageType.PIE);
    public static final LanguageTypeLocal aar = new LanguageTypeRu("Афарский", LanguageType.aar);
    public static final LanguageTypeLocal abk = new LanguageTypeRu("Абхазский", LanguageType.abk);
    public static final LanguageTypeLocal abq = new LanguageTypeRu("Абазинский", LanguageType.abq);
    public static final LanguageTypeLocal abs = new LanguageTypeRu("Амбонезский", LanguageType.abs);
    public static final LanguageTypeLocal ace = new LanguageTypeRu("Ачехский", LanguageType.ace);
    public static final LanguageTypeLocal ady = new LanguageTypeRu("Адыгейский", LanguageType.ady);
    public static final LanguageTypeLocal afr = new LanguageTypeRu("Африкаанс", LanguageType.afr);
    public static final LanguageTypeLocal agf = new LanguageTypeRu("Аргуни", LanguageType.agf);
    public static final LanguageTypeLocal agx = new LanguageTypeRu("Агульский", LanguageType.agx);
    public static final LanguageTypeLocal aie = new LanguageTypeRu("Амара", LanguageType.aie);
    public static final LanguageTypeLocal aii = new LanguageTypeRu("Ассирийский", LanguageType.aii);
    public static final LanguageTypeLocal ain = new LanguageTypeRu("Айнский", LanguageType.ain);
    public static final LanguageTypeLocal aiw = new LanguageTypeRu("Аари", LanguageType.aiw);
    public static final LanguageTypeLocal aja = new LanguageTypeRu("Аджа (Судан)", LanguageType.aja);
    public static final LanguageTypeLocal ajg = new LanguageTypeRu("Аджа (Бенин)", LanguageType.ajg);
    public static final LanguageTypeLocal aka = new LanguageTypeRu("Акан", LanguageType.aka);
    public static final LanguageTypeLocal akg = new LanguageTypeRu("Анакалангу", LanguageType.akg);
    public static final LanguageTypeLocal akk = new LanguageTypeRu("Аккадский", LanguageType.akk);
    public static final LanguageTypeLocal akz = new LanguageTypeRu("Алабамский", LanguageType.akz);
    public static final LanguageTypeLocal ale = new LanguageTypeRu("Алеутский", LanguageType.ale);
    public static final LanguageTypeLocal alp = new LanguageTypeRu("Алуне", LanguageType.alp);
    public static final LanguageTypeLocal alq = new LanguageTypeRu("Алгонкинский", LanguageType.alq);
    public static final LanguageTypeLocal alr = new LanguageTypeRu("Алюторский", LanguageType.alr);
    public static final LanguageTypeLocal alt = new LanguageTypeRu("Алтайский", LanguageType.alt);
    public static final LanguageTypeLocal am = new LanguageTypeRu("Амхарский", LanguageType.am);
    public static final LanguageTypeLocal ang = new LanguageTypeRu("Древнеанглийский", LanguageType.ang);
    public static final LanguageTypeLocal anp = new LanguageTypeRu("Ангика", LanguageType.anp);
    public static final LanguageTypeLocal apl = new LanguageTypeRu("Липан", LanguageType.apl);
    public static final LanguageTypeLocal apw = new LanguageTypeRu("Западно-апачский", LanguageType.apw);
    public static final LanguageTypeLocal aqc = new LanguageTypeRu("Арчинский", LanguageType.aqc);
    public static final LanguageTypeLocal ara = new LanguageTypeRu("Арабский", LanguageType.ara);
    public static final LanguageTypeLocal arb = new LanguageTypeRu("Арабский литературный", LanguageType.arb);
    public static final LanguageTypeLocal arc = new LanguageTypeRu("Арамейский", LanguageType.arc);
    // arc_jud public static final LanguageTypeLocal arc_jud = new LanguageTypeRu("Арамейский (иуд.)", LanguageType.arc_jud);
    // arc_syr syc public static final LanguageTypeLocal arc_syr = new LanguageTypeRu("Арамейский (сир.)", LanguageType.arc_syr);
    public static final LanguageTypeLocal arg = new LanguageTypeRu("Арагонский", LanguageType.arg);
    public static final LanguageTypeLocal arn = new LanguageTypeRu("Арауканский", LanguageType.arn);
    public static final LanguageTypeLocal arq = new LanguageTypeRu("Алжирский диалект арабского", LanguageType.arq);
    public static final LanguageTypeLocal art_oou = new LanguageTypeRu("Ооу", LanguageType.art_oou);
    public static final LanguageTypeLocal arw = new LanguageTypeRu("Аравакский", LanguageType.arw);
    public static final LanguageTypeLocal ase = new LanguageTypeRu("Амслен", LanguageType.ase);
    public static final LanguageTypeLocal asm = new LanguageTypeRu("Ассамский", LanguageType.asm);
    public static final LanguageTypeLocal ast = new LanguageTypeRu("Астурийский", LanguageType.ast);
    public static final LanguageTypeLocal atv = new LanguageTypeRu("Северноалтайский", LanguageType.atv);
    public static final LanguageTypeLocal av = new LanguageTypeRu("Аварский", LanguageType.av);
    public static final LanguageTypeLocal ave = new LanguageTypeRu("Авестийский", LanguageType.ave);
    public static final LanguageTypeLocal axm = new LanguageTypeRu("Среднеармянский", LanguageType.axm);
    public static final LanguageTypeLocal ay = new LanguageTypeRu("Аймара", LanguageType.ay);
    // ay public static final LanguageTypeLocal ayc = new LanguageTypeRu("Южный Аймара", LanguageType.ayc);
    // ay public static final LanguageTypeLocal ayr = new LanguageTypeRu("Центральный Аймара", LanguageType.ayr);
    public static final LanguageTypeLocal az = new LanguageTypeRu("Азербайджанский", LanguageType.az);

    public static final LanguageTypeLocal ba = new LanguageTypeRu("Башкирский", LanguageType.ba);
    public static final LanguageTypeLocal bagua = new LanguageTypeRu("Багуа", LanguageType.bagua);
    public static final LanguageTypeLocal bal = new LanguageTypeRu("Белуджский", LanguageType.bal);
    public static final LanguageTypeLocal ban = new LanguageTypeRu("Балийский", LanguageType.ban);
    public static final LanguageTypeLocal bar = new LanguageTypeRu("Баварский", LanguageType.bar);
    public static final LanguageTypeLocal bat_smg = new LanguageTypeRu("Жемайтский", LanguageType.bat_smg);
    public static final LanguageTypeLocal bcl = new LanguageTypeRu("(Центральный) бикольский", LanguageType.bcl);
    public static final LanguageTypeLocal bdk = new LanguageTypeRu("Будухский", LanguageType.bdk);
    public static final LanguageTypeLocal be = new LanguageTypeRu("Белорусский", LanguageType.be);
    public static final LanguageTypeLocal bem = new LanguageTypeRu("Бемба", LanguageType.bem);
    public static final LanguageTypeLocal bg = new LanguageTypeRu("Болгарский", LanguageType.bg);
    public static final LanguageTypeLocal bh = new LanguageTypeRu("Бихарский", LanguageType.bh);
    public static final LanguageTypeLocal bho = new LanguageTypeRu("Бходжпури", LanguageType.bho);
    public static final LanguageTypeLocal bi = new LanguageTypeRu("Бислама", LanguageType.bi);
    public static final LanguageTypeLocal bib = new LanguageTypeRu("Биса", LanguageType.bib);
    public static final LanguageTypeLocal bik = new LanguageTypeRu("Бикольский", LanguageType.bik);
    public static final LanguageTypeLocal bla = new LanguageTypeRu("Блэкфут", LanguageType.bla);
    public static final LanguageTypeLocal bm = new LanguageTypeRu("Бамана", LanguageType.bm);
    public static final LanguageTypeLocal bn = new LanguageTypeRu("Бенгальский", LanguageType.bn);
    public static final LanguageTypeLocal bo = new LanguageTypeRu("Тибетский", LanguageType.bo);
    public static final LanguageTypeLocal bph = new LanguageTypeRu("Ботлихский", LanguageType.bph);
    public static final LanguageTypeLocal bpy = new LanguageTypeRu("Бишнуприя-манипури", LanguageType.bpy);
    public static final LanguageTypeLocal br = new LanguageTypeRu("Бретонский", LanguageType.br);
    public static final LanguageTypeLocal bra = new LanguageTypeRu("Брадж бхакха", LanguageType.bra);
    public static final LanguageTypeLocal brh = new LanguageTypeRu("Брауи", LanguageType.brh);
    public static final LanguageTypeLocal bs = new LanguageTypeRu("Боснийский", LanguageType.bs);
    public static final LanguageTypeLocal btk = new LanguageTypeRu("Батакский", LanguageType.btk);
    public static final LanguageTypeLocal bua = new LanguageTypeRu("Бурятский", LanguageType.bua);
    public static final LanguageTypeLocal bug = new LanguageTypeRu("Бугийский", LanguageType.bug);
    public static final LanguageTypeLocal byn = new LanguageTypeRu("Билин", LanguageType.byn);

    public static final LanguageTypeLocal ca = new LanguageTypeRu("Каталанский", LanguageType.ca);
    public static final LanguageTypeLocal ccc = new LanguageTypeRu("Чамикуро", LanguageType.ccc);
    public static final LanguageTypeLocal ce = new LanguageTypeRu("Чеченский", LanguageType.ce);
    public static final LanguageTypeLocal ceb = new LanguageTypeRu("Себуанский", LanguageType.ceb);
    public static final LanguageTypeLocal cel = new LanguageTypeRu("Целинский", LanguageType.cel);
    public static final LanguageTypeLocal ch = new LanguageTypeRu("Чаморро", LanguageType.ch);
    public static final LanguageTypeLocal chb = new LanguageTypeRu("Чибча", LanguageType.chb);
    public static final LanguageTypeLocal chc = new LanguageTypeRu("Катоба", LanguageType.chc);
    public static final LanguageTypeLocal chg = new LanguageTypeRu("Чагатайский", LanguageType.chg);
    public static final LanguageTypeLocal chm = new LanguageTypeRu("Марийский", LanguageType.chm);
    public static final LanguageTypeLocal chn = new LanguageTypeRu("Чинукский жаргон", LanguageType.chn);
    public static final LanguageTypeLocal cho = new LanguageTypeRu("Чоктавский", LanguageType.cho);
    public static final LanguageTypeLocal chp = new LanguageTypeRu("Чипевиан", LanguageType.chp);
    public static final LanguageTypeLocal chr = new LanguageTypeRu("Чероки", LanguageType.chr);
    // cu chu_ru public static final LanguageTypeLocal chu_ru = new LanguageTypeRu("Церковнославянский", LanguageType.chu_ru);
    public static final LanguageTypeLocal chy = new LanguageTypeRu("Шайенский", LanguageType.chy);
    public static final LanguageTypeLocal cic = new LanguageTypeRu("Чикасо", LanguageType.cic);
    public static final LanguageTypeLocal cjs = new LanguageTypeRu("Шорский", LanguageType.cjs);
    public static final LanguageTypeLocal ckb = new LanguageTypeRu("Сорани", LanguageType.ckb);
    public static final LanguageTypeLocal ckt = new LanguageTypeRu("Чукотский", LanguageType.ckt);
    public static final LanguageTypeLocal co = new LanguageTypeRu("Корсиканский", LanguageType.co);
    public static final LanguageTypeLocal com = new LanguageTypeRu("Команчский", LanguageType.com);
    public static final LanguageTypeLocal cop = new LanguageTypeRu("Коптский", LanguageType.cop);
    public static final LanguageTypeLocal cpg = new LanguageTypeRu("Каппадокийский", LanguageType.cpg);
    public static final LanguageTypeLocal crh = new LanguageTypeRu("Крымскотатарский", LanguageType.crh);
    public static final LanguageTypeLocal cs = new LanguageTypeRu("Чешский", LanguageType.cs);
    public static final LanguageTypeLocal csb = new LanguageTypeRu("Кашубский", LanguageType.csb);
    public static final LanguageTypeLocal cu = new LanguageTypeRu("Старославянский", LanguageType.cu);
    public static final LanguageTypeLocal cv = new LanguageTypeRu("Чувашский", LanguageType.cv);
    public static final LanguageTypeLocal cy = new LanguageTypeRu("Валлийский", LanguageType.cy);

    public static final LanguageTypeLocal da = new LanguageTypeRu("Датский", LanguageType.da);
    public static final LanguageTypeLocal dar = new LanguageTypeRu("Даргинский", LanguageType.dar);
    public static final LanguageTypeLocal dav = new LanguageTypeRu("Таита", LanguageType.dav);
    public static final LanguageTypeLocal dbl = new LanguageTypeRu("Дирбал", LanguageType.dbl);
    public static final LanguageTypeLocal de = new LanguageTypeRu("Немецкий", LanguageType.de);
    public static final LanguageTypeLocal de_a = new LanguageTypeRu("Немецкий (австрийский)", LanguageType.de_a);
    public static final LanguageTypeLocal dif = new LanguageTypeRu("Диери", LanguageType.dif);
    public static final LanguageTypeLocal dlg = new LanguageTypeRu("Долганский", LanguageType.dlg);
    public static final LanguageTypeLocal dng = new LanguageTypeRu("Дунганский", LanguageType.dng);
    public static final LanguageTypeLocal dum = new LanguageTypeRu("Нидерландский средневековый", LanguageType.dum);

    public static final LanguageTypeLocal egy = new LanguageTypeRu("Египетский", LanguageType.egy);
    public static final LanguageTypeLocal el = new LanguageTypeRu("Греческий", LanguageType.el);
    // el public static final LanguageTypeLocal el_dhi = new LanguageTypeRu("Греческий демот.", LanguageType.el_dhi);
    // el public static final LanguageTypeLocal el_kat = new LanguageTypeRu("Греческий кафар.", LanguageType.el_kat);
    public static final LanguageTypeLocal en = new LanguageTypeRu("Английский", LanguageType.en);
    public static final LanguageTypeLocal en_au = new LanguageTypeRu("Английский (австралийский диалект)", LanguageType.en_au);
    public static final LanguageTypeLocal en_nz = new LanguageTypeRu("Новозеландский вариант английского языка", LanguageType.en_nz);
    public static final LanguageTypeLocal en_us = new LanguageTypeRu("Английский (американский)", LanguageType.en_us);
    public static final LanguageTypeLocal enm = new LanguageTypeRu("Среднеанглийский", LanguageType.enm);
    public static final LanguageTypeLocal eo = new LanguageTypeRu("Эсперанто", LanguageType.eo);
    public static final LanguageTypeLocal es = new LanguageTypeRu("Испанский", LanguageType.es);
    public static final LanguageTypeLocal eu = new LanguageTypeRu("Баскский", LanguageType.eu);
    public static final LanguageTypeLocal et = new LanguageTypeRu("Эстонский", LanguageType.et);
    public static final LanguageTypeLocal eve = new LanguageTypeRu("Эвенский", LanguageType.eve);
    public static final LanguageTypeLocal evn = new LanguageTypeRu("Эвенкийский", LanguageType.evn);
    public static final LanguageTypeLocal ewe = new LanguageTypeRu("Эве", LanguageType.ewe);

    public static final LanguageTypeLocal fa = new LanguageTypeRu("Персидский", LanguageType.fa);
    public static final LanguageTypeLocal fat = new LanguageTypeRu("Фанти", LanguageType.fat);
    public static final LanguageTypeLocal fi = new LanguageTypeRu("Финский", LanguageType.fi);
    public static final LanguageTypeLocal fic_drw = new LanguageTypeRu("Дроу", LanguageType.fic_drw);
    public static final LanguageTypeLocal fj = new LanguageTypeRu("Фиджийский", LanguageType.fj);
    public static final LanguageTypeLocal fo = new LanguageTypeRu("Фарерский", LanguageType.fo);
    public static final LanguageTypeLocal fon = new LanguageTypeRu("Фон", LanguageType.fon);
    public static final LanguageTypeLocal fr = new LanguageTypeRu("Французский", LanguageType.fr);
    public static final LanguageTypeLocal fr_be = new LanguageTypeRu("Французский (бельгийский)", LanguageType.fr_be);
    public static final LanguageTypeLocal fr_ch = new LanguageTypeRu("Французский (швейцарский)", LanguageType.fr_ch);
    public static final LanguageTypeLocal frm = new LanguageTypeRu("Среднефранцузский", LanguageType.frm);
    public static final LanguageTypeLocal fro = new LanguageTypeRu("Старофранцузский", LanguageType.fro);
    public static final LanguageTypeLocal fy = new LanguageTypeRu("Фризский", LanguageType.fy);

    public static final LanguageTypeLocal gd = new LanguageTypeRu("Шотландский", LanguageType.gd);
    public static final LanguageTypeLocal gl = new LanguageTypeRu("Галисийский", LanguageType.gl);
    public static final LanguageTypeLocal gld = new LanguageTypeRu("Нанайский", LanguageType.gld);
    public static final LanguageTypeLocal gmh = new LanguageTypeRu("Средневерхненемецкий", LanguageType.gmh);
    public static final LanguageTypeLocal gmy = new LanguageTypeRu("Микенский", LanguageType.gmy);
    public static final LanguageTypeLocal gni = new LanguageTypeRu("Гуниянди", LanguageType.gni);
    public static final LanguageTypeLocal goh = new LanguageTypeRu("Древневерхненемецкий", LanguageType.goh);
    public static final LanguageTypeLocal got = new LanguageTypeRu("Готский", LanguageType.got);
    public static final LanguageTypeLocal grc = new LanguageTypeRu("Древнегреческий", LanguageType.grc);
    public static final LanguageTypeLocal grn = new LanguageTypeRu("Гуарани", LanguageType.grn);
    public static final LanguageTypeLocal gsw = new LanguageTypeRu("Швейцарский диалект", LanguageType.gsw);

    public static final LanguageTypeLocal ha_arab = new LanguageTypeRu("Хауса (араб.)", LanguageType.ha_arab);
    public static final LanguageTypeLocal ha_lat = new LanguageTypeRu("Хауса (лат.)", LanguageType.ha_lat);
    public static final LanguageTypeLocal hanzi = new LanguageTypeRu("Китайский иероглиф", LanguageType.hanzi);
    // he hbo hb public static final LanguageTypeLocal hbo = new LanguageTypeRu("Древнееврейский", LanguageType.hbo);
    public static final LanguageTypeLocal he = new LanguageTypeRu("Иврит", LanguageType.he);
    public static final LanguageTypeLocal hi = new LanguageTypeRu("Хинди", LanguageType.hi);
    public static final LanguageTypeLocal hif = new LanguageTypeRu("Фиджийский хинди", LanguageType.hif);
    public static final LanguageTypeLocal hil = new LanguageTypeRu("Хилигайнон", LanguageType.hil);
    public static final LanguageTypeLocal hit = new LanguageTypeRu("Хеттский", LanguageType.hit);
    public static final LanguageTypeLocal hmn = new LanguageTypeRu("Мяо", LanguageType.hmn);
    public static final LanguageTypeLocal hr = new LanguageTypeRu("Хорватский", LanguageType.hr);
    public static final LanguageTypeLocal ht = new LanguageTypeRu("Гаитянский креольский", LanguageType.ht);
    public static final LanguageTypeLocal hu = new LanguageTypeRu("Венгерский", LanguageType.hu);
    public static final LanguageTypeLocal hy = new LanguageTypeRu("Армянский", LanguageType.hy);

    public static final LanguageTypeLocal ibo = new LanguageTypeRu("Игбо", LanguageType.ibo);
    public static final LanguageTypeLocal id_ = new LanguageTypeRu("Индонезийский", LanguageType.id_);
    public static final LanguageTypeLocal io = new LanguageTypeRu("Идо", LanguageType.io);
    public static final LanguageTypeLocal ina = new LanguageTypeRu("Интерлингва", LanguageType.ina);
    public static final LanguageTypeLocal is = new LanguageTypeRu("Исландский", LanguageType.is);
    public static final LanguageTypeLocal it = new LanguageTypeRu("Итальянский", LanguageType.it);
    public static final LanguageTypeLocal ith_lat = new LanguageTypeRu("Ифкуиль", LanguageType.ith_lat);
    public static final LanguageTypeLocal itl = new LanguageTypeRu("Ительменский", LanguageType.itl);
    public static final LanguageTypeLocal iu = new LanguageTypeRu("Инуктитут", LanguageType.iu);
    public static final LanguageTypeLocal ium = new LanguageTypeRu("Яо", LanguageType.ium);
    public static final LanguageTypeLocal izh = new LanguageTypeRu("Ижорский", LanguageType.izh);

    public static final LanguageTypeLocal ja = new LanguageTypeRu("Японский", LanguageType.ja);
    public static final LanguageTypeLocal jam = new LanguageTypeRu("Ямайский креольский", LanguageType.jam);
    public static final LanguageTypeLocal jct = new LanguageTypeRu("Крымчакский", LanguageType.jct);
    public static final LanguageTypeLocal jpr = new LanguageTypeRu("Еврейско-персидский", LanguageType.jpr);

    public static final LanguageTypeLocal ka = new LanguageTypeRu("Грузинский", LanguageType.ka);
    public static final LanguageTypeLocal kaz = new LanguageTypeRu("Казахский", LanguageType.kaz);
    public static final LanguageTypeLocal kbd = new LanguageTypeRu("Кабардино-черкесский", LanguageType.kbd);
    public static final LanguageTypeLocal kca = new LanguageTypeRu("Хантыйский", LanguageType.kca);
    public static final LanguageTypeLocal kdr = new LanguageTypeRu("Караимский", LanguageType.kdr);
    public static final LanguageTypeLocal ket = new LanguageTypeRu("Кетский", LanguageType.ket);
    public static final LanguageTypeLocal ki = new LanguageTypeRu("Кикуйю", LanguageType.ki);
    public static final LanguageTypeLocal kim = new LanguageTypeRu("Тофаларский", LanguageType.kim);
    public static final LanguageTypeLocal kjh = new LanguageTypeRu("Хакасский", LanguageType.kjh);
    // kmr ku public static final LanguageTypeLocal kmr = new LanguageTypeRu("Курманджи", LanguageType.kmr);
    public static final LanguageTypeLocal km = new LanguageTypeRu("Кхмерский", LanguageType.km);
    public static final LanguageTypeLocal ko = new LanguageTypeRu("Корейский", LanguageType.ko);
    public static final LanguageTypeLocal koi = new LanguageTypeRu("Коми-пермяцкий", LanguageType.koi);
    public static final LanguageTypeLocal kok = new LanguageTypeRu("Конкани", LanguageType.kok);
    // kom public static final LanguageTypeLocal kom = new LanguageTypeRu("Коми-зырянский", LanguageType.kom);
    public static final LanguageTypeLocal kpy = new LanguageTypeRu("Корякский", LanguageType.kpy);
    public static final LanguageTypeLocal krc = new LanguageTypeRu("Карачаево-балкарский", LanguageType.krc);
    public static final LanguageTypeLocal krj = new LanguageTypeRu("Кинарайский", LanguageType.krj);
    public static final LanguageTypeLocal krl = new LanguageTypeRu("Карельский", LanguageType.krl);
    public static final LanguageTypeLocal ks = new LanguageTypeRu("Кашмири", LanguageType.ks);
    // ku public static final LanguageTypeLocal ku = new LanguageTypeRu("Курдский", LanguageType.ku);
    public static final LanguageTypeLocal kum = new LanguageTypeRu("Кумыкский", LanguageType.kum);
    public static final LanguageTypeLocal ky = new LanguageTypeRu("Киргизский", LanguageType.ky);

    public static final LanguageTypeLocal la = new LanguageTypeRu("Латинский", LanguageType.la);
    public static final LanguageTypeLocal lb = new LanguageTypeRu("Люксембургский", LanguageType.lb);
    public static final LanguageTypeLocal letter_ru = new LanguageTypeRu("Буква", LanguageType.letter_ru);
    public static final LanguageTypeLocal lg = new LanguageTypeRu("Луганда", LanguageType.lg);
    public static final LanguageTypeLocal li = new LanguageTypeRu("Лимбургский", LanguageType.li);
    public static final LanguageTypeLocal lt = new LanguageTypeRu("Литовский", LanguageType.lt);
    public static final LanguageTypeLocal liv = new LanguageTypeRu("Ливский", LanguageType.liv);
    public static final LanguageTypeLocal ltg = new LanguageTypeRu("Латгальский", LanguageType.ltg);
    public static final LanguageTypeLocal luo = new LanguageTypeRu("Долуо", LanguageType.luo);
    public static final LanguageTypeLocal luy = new LanguageTypeRu("Лухья", LanguageType.luy);
    public static final LanguageTypeLocal lv = new LanguageTypeRu("Латышский", LanguageType.lv);

    public static final LanguageTypeLocal mag = new LanguageTypeRu("Магахи", LanguageType.mag);
    public static final LanguageTypeLocal mai = new LanguageTypeRu("Майтхили", LanguageType.mai);
    public static final LanguageTypeLocal man = new LanguageTypeRu("Мандинго", LanguageType.man);
    public static final LanguageTypeLocal mаs = new LanguageTypeRu("Масайский", LanguageType.mаs);
    public static final LanguageTypeLocal mgm = new LanguageTypeRu("Мамбай", LanguageType.mgm);
    public static final LanguageTypeLocal mhr = new LanguageTypeRu("Луговомарийский", LanguageType.mhr);
    public static final LanguageTypeLocal mi = new LanguageTypeRu("Маори", LanguageType.mi);
    public static final LanguageTypeLocal mk = new LanguageTypeRu("Македонский", LanguageType.mk);
    public static final LanguageTypeLocal ml = new LanguageTypeRu("Малаялам", LanguageType.ml);
    public static final LanguageTypeLocal mn = new LanguageTypeRu("Монгольский ", LanguageType.mn);
    public static final LanguageTypeLocal mnk = new LanguageTypeRu("Мандинка", LanguageType.mnk);
    public static final LanguageTypeLocal mns = new LanguageTypeRu("Мансийский", LanguageType.mns);
    public static final LanguageTypeLocal moe = new LanguageTypeRu("Монтанье-наскапи", LanguageType.moe);
    public static final LanguageTypeLocal moh = new LanguageTypeRu("Могаукский", LanguageType.moh);
    public static final LanguageTypeLocal mos = new LanguageTypeRu("Море", LanguageType.mos);
    public static final LanguageTypeLocal mr = new LanguageTypeRu("Маратхи", LanguageType.mr);
    public static final LanguageTypeLocal mrj = new LanguageTypeRu("Горномарийский", LanguageType.mrj);
    public static final LanguageTypeLocal mrv = new LanguageTypeRu("Мангаревский", LanguageType.mrv);
    public static final LanguageTypeLocal my = new LanguageTypeRu("Бирманский", LanguageType.my);

    public static final LanguageTypeLocal nah = new LanguageTypeRu("Астекский", LanguageType.nah);
    public static final LanguageTypeLocal nan = new LanguageTypeRu("Китайский (южноминьский)", LanguageType.nan);
    public static final LanguageTypeLocal nb = new LanguageTypeRu("Букмол", LanguageType.nb);
    public static final LanguageTypeLocal nds = new LanguageTypeRu("Нижненемецкий", LanguageType.nds);
    public static final LanguageTypeLocal ne = new LanguageTypeRu("Непальский", LanguageType.ne);
    public static final LanguageTypeLocal new_ = new LanguageTypeRu("Неварский", LanguageType.new_);
    public static final LanguageTypeLocal nio = new LanguageTypeRu("Нганасанский", LanguageType.nio);
    public static final LanguageTypeLocal niv = new LanguageTypeRu("Нивхский", LanguageType.niv);
    public static final LanguageTypeLocal nl = new LanguageTypeRu("Нидерландский", LanguageType.nl);
    public static final LanguageTypeLocal nn = new LanguageTypeRu("Нюнорск", LanguageType.nn);
    public static final LanguageTypeLocal no = new LanguageTypeRu("Норвежский", LanguageType.no);
    public static final LanguageTypeLocal nog = new LanguageTypeRu("Ногайский", LanguageType.nog);
    public static final LanguageTypeLocal non = new LanguageTypeRu("Древнеисландский", LanguageType.non);
    public static final LanguageTypeLocal num = new LanguageTypeRu("Ниуафооу", LanguageType.num);
    public static final LanguageTypeLocal nv = new LanguageTypeRu("Навахо", LanguageType.nv);

    public static final LanguageTypeLocal obt = new LanguageTypeRu("Древнебретонский", LanguageType.obt);
    public static final LanguageTypeLocal oc = new LanguageTypeRu("Окситанский", LanguageType.oc);
    public static final LanguageTypeLocal oj = new LanguageTypeRu("Оджибва", LanguageType.oj);
    public static final LanguageTypeLocal ood = new LanguageTypeRu("Оодхам", LanguageType.ood);
    public static final LanguageTypeLocal orv = new LanguageTypeRu("Древнерусский", LanguageType.orv);
    public static final LanguageTypeLocal os = new LanguageTypeRu("Осетинский", LanguageType.os);
    public static final LanguageTypeLocal ota = new LanguageTypeRu("Османский", LanguageType.ota);
    public static final LanguageTypeLocal owl = new LanguageTypeRu("Древневаллийский", LanguageType.owl);

    public static final LanguageTypeLocal pal = new LanguageTypeRu("Среднеперсидский", LanguageType.pal);
    public static final LanguageTypeLocal pau = new LanguageTypeRu("Палау", LanguageType.pau);
    public static final LanguageTypeLocal pcd = new LanguageTypeRu("Пикардский", LanguageType.pcd);
    public static final LanguageTypeLocal pdc = new LanguageTypeRu("Пенсильванско-немецкий", LanguageType.pdc);
    public static final LanguageTypeLocal peo = new LanguageTypeRu("Древнеперсидский", LanguageType.peo);
    public static final LanguageTypeLocal pinyin = new LanguageTypeRu("Пиньинь", LanguageType.pinyin);
    public static final LanguageTypeLocal pl = new LanguageTypeRu("Польский", LanguageType.pl);
    public static final LanguageTypeLocal pmt = new LanguageTypeRu("Туамоту", LanguageType.pmt);
    public static final LanguageTypeLocal pnt = new LanguageTypeRu("Понтийский", LanguageType.pnt);
    public static final LanguageTypeLocal pox = new LanguageTypeRu("Полабский", LanguageType.pox);
    public static final LanguageTypeLocal ppol = new LanguageTypeRu("Протополинезийский", LanguageType.ppol);
    public static final LanguageTypeLocal prg = new LanguageTypeRu("Прусский", LanguageType.prg);
    public static final LanguageTypeLocal prs = new LanguageTypeRu("Дари", LanguageType.prs);
    public static final LanguageTypeLocal psl = new LanguageTypeRu("Праславянский", LanguageType.psl);
    public static final LanguageTypeLocal pt = new LanguageTypeRu("Португальский", LanguageType.pt);

    public static final LanguageTypeLocal qya = new LanguageTypeRu("Квэнья", LanguageType.qya);

    public static final LanguageTypeLocal rap = new LanguageTypeRu("Рапануйский", LanguageType.rap);
    public static final LanguageTypeLocal rar = new LanguageTypeRu("Раротонга", LanguageType.rar);
    public static final LanguageTypeLocal rm = new LanguageTypeRu("Романшский", LanguageType.rm);
    public static final LanguageTypeLocal rmr = new LanguageTypeRu("Кало", LanguageType.rmr);
    // rn run public static final LanguageTypeLocal rn = new LanguageTypeRu("Рунди", LanguageType.rn);
    public static final LanguageTypeLocal ro = new LanguageTypeRu("Румынский", LanguageType.ro);
    public static final LanguageTypeLocal rom = new LanguageTypeRu("Цыганский", LanguageType.rom);
    public static final LanguageTypeLocal romaji = new LanguageTypeRu("Ромадзи", LanguageType.romaji);
    public static final LanguageTypeLocal ru = new LanguageTypeRu("Русский", LanguageType.ru);
    public static final LanguageTypeLocal ru_old = new LanguageTypeRu("Русский (дореформенная орфография)", LanguageType.ru_old);
    public static final LanguageTypeLocal run = new LanguageTypeRu("Кирунди", LanguageType.run);
    public static final LanguageTypeLocal rup = new LanguageTypeRu("Арумынский", LanguageType.rup);
    public static final LanguageTypeLocal ruq = new LanguageTypeRu("Мегленорумынский", LanguageType.ruq);

    public static final LanguageTypeLocal sah = new LanguageTypeRu("Якутский", LanguageType.sah);
    public static final LanguageTypeLocal sat = new LanguageTypeRu("Сантали", LanguageType.sat);
    // ku public static final LanguageTypeLocal sdh = new LanguageTypeRu("Южнокурдский", LanguageType.sdh);
    public static final LanguageTypeLocal sel = new LanguageTypeRu("Селькупский", LanguageType.sel);
    public static final LanguageTypeLocal sga = new LanguageTypeRu("Древнеирландский", LanguageType.sga);
    public static final LanguageTypeLocal sh = new LanguageTypeRu("Сербскохорватский", LanguageType.sh);
    public static final LanguageTypeLocal shp = new LanguageTypeRu("Шипибо", LanguageType.shp);
    public static final LanguageTypeLocal sjd = new LanguageTypeRu("Саамский (кильдинский)", LanguageType.sjd);
    public static final LanguageTypeLocal sjn = new LanguageTypeRu("Синдарин", LanguageType.sjn);
    public static final LanguageTypeLocal sk = new LanguageTypeRu("Словацкий", LanguageType.sk);
    public static final LanguageTypeLocal sl = new LanguageTypeRu("Словенский", LanguageType.sl);
    public static final LanguageTypeLocal slovio = new LanguageTypeRu("Словио", LanguageType.slovio);
    public static final LanguageTypeLocal smn = new LanguageTypeRu("Инари-саамский", LanguageType.smn);
    public static final LanguageTypeLocal smo = new LanguageTypeRu("Самоанский", LanguageType.smo);
    public static final LanguageTypeLocal sms = new LanguageTypeRu("Колтта-саамский", LanguageType.sms);
    public static final LanguageTypeLocal solresol = new LanguageTypeRu("Сольресоль", LanguageType.solresol);
    public static final LanguageTypeLocal sot = new LanguageTypeRu("Сесото", LanguageType.sot);
    public static final LanguageTypeLocal sr = new LanguageTypeRu("Сербский", LanguageType.sr);
    public static final LanguageTypeLocal sqi = new LanguageTypeRu("Албанский", LanguageType.sqi);
    public static final LanguageTypeLocal sux = new LanguageTypeRu("Шумерский", LanguageType.sux);
    public static final LanguageTypeLocal sv = new LanguageTypeRu("Шведский", LanguageType.sv);
    public static final LanguageTypeLocal sva = new LanguageTypeRu("Сванский", LanguageType.sva);
    public static final LanguageTypeLocal sw = new LanguageTypeRu("Суахили", LanguageType.sw);
    public static final LanguageTypeLocal syc = new LanguageTypeRu("Сирийский", LanguageType.syc);

    public static final LanguageTypeLocal tab = new LanguageTypeRu("Табасаранский", LanguageType.tab);
    public static final LanguageTypeLocal tah = new LanguageTypeRu("Таитянский", LanguageType.tah);
    public static final LanguageTypeLocal tgk = new LanguageTypeRu("Таджикский", LanguageType.tgk);
    public static final LanguageTypeLocal th = new LanguageTypeRu("Тайский", LanguageType.th);
    public static final LanguageTypeLocal tig = new LanguageTypeRu("Тигре", LanguageType.tig);
    public static final LanguageTypeLocal tir = new LanguageTypeRu("Тигринья", LanguageType.tir);
    public static final LanguageTypeLocal tk = new LanguageTypeRu("Туркменский", LanguageType.tk);
    public static final LanguageTypeLocal tkl = new LanguageTypeRu("Токелау", LanguageType.tkl);
    public static final LanguageTypeLocal tly = new LanguageTypeRu("Талышский", LanguageType.tly);
    public static final LanguageTypeLocal tokipona = new LanguageTypeRu("Токипона", LanguageType.tokipona);
    public static final LanguageTypeLocal ton = new LanguageTypeRu("Тонганский", LanguageType.ton);
    public static final LanguageTypeLocal tr = new LanguageTypeRu("Турецкий", LanguageType.tr);
    public static final LanguageTypeLocal tru = new LanguageTypeRu("Туройо", LanguageType.tru);
    public static final LanguageTypeLocal tsd = new LanguageTypeRu("Цаконский", LanguageType.tsd);
    public static final LanguageTypeLocal tsn = new LanguageTypeRu("Тсвана", LanguageType.tsn);
    public static final LanguageTypeLocal tt = new LanguageTypeRu("Татарский", LanguageType.tt);
    public static final LanguageTypeLocal ttt = new LanguageTypeRu("Татский", LanguageType.ttt);
    public static final LanguageTypeLocal tup = new LanguageTypeRu("Тупи-гуарани", LanguageType.tup);
    public static final LanguageTypeLocal tvl = new LanguageTypeRu("Тувалу", LanguageType.tvl);
    public static final LanguageTypeLocal twi = new LanguageTypeRu("Чви", LanguageType.twi);

    public static final LanguageTypeLocal uby = new LanguageTypeRu("Убыхский", LanguageType.uby);
    public static final LanguageTypeLocal udi = new LanguageTypeRu("Удинский", LanguageType.udi);
    public static final LanguageTypeLocal udm = new LanguageTypeRu("Удмуртский", LanguageType.udm);
    public static final LanguageTypeLocal uga = new LanguageTypeRu("Угаритский", LanguageType.uga);
    public static final LanguageTypeLocal ulc = new LanguageTypeRu("Ульчский", LanguageType.ulc);
    public static final LanguageTypeLocal ulk = new LanguageTypeRu("Мериам", LanguageType.ulk);
    public static final LanguageTypeLocal uk = new LanguageTypeRu("Украинский", LanguageType.uk);
    public static final LanguageTypeLocal uz = new LanguageTypeRu("Узбекский", LanguageType.uz);
    
    public static final LanguageTypeLocal ve = new LanguageTypeRu("Венда", LanguageType.ve);
    public static final LanguageTypeLocal vec = new LanguageTypeRu("Венетский", LanguageType.vec);
    public static final LanguageTypeLocal vep = new LanguageTypeRu("Вепсский", LanguageType.vep);
    public static final LanguageTypeLocal vi = new LanguageTypeRu("Вьетнамский", LanguageType.vi);
    public static final LanguageTypeLocal vot = new LanguageTypeRu("Водский", LanguageType.vot);
    public static final LanguageTypeLocal vro = new LanguageTypeRu("Выруский диалект", LanguageType.vro);

    public static final LanguageTypeLocal war = new LanguageTypeRu("Варайский", LanguageType.war);
    public static final LanguageTypeLocal wlm = new LanguageTypeRu("Средневаллийский", LanguageType.wlm);
    public static final LanguageTypeLocal wo = new LanguageTypeRu("Волоф", LanguageType.wo);
    public static final LanguageTypeLocal wuu = new LanguageTypeRu("У (китайский диалект)", LanguageType.wuu);

    public static final LanguageTypeLocal xal = new LanguageTypeRu("Калмыцкий", LanguageType.xal);
    public static final LanguageTypeLocal xbc = new LanguageTypeRu("Бактрийский", LanguageType.xbc);
    public static final LanguageTypeLocal xbm = new LanguageTypeRu("Среднебретонский", LanguageType.xbm);
    public static final LanguageTypeLocal xcl = new LanguageTypeRu("Грабар", LanguageType.xcl);
    public static final LanguageTypeLocal xho = new LanguageTypeRu("Коса", LanguageType.xho);
    public static final LanguageTypeLocal xmk = new LanguageTypeRu("Древнемакедонский", LanguageType.xmk);
    public static final LanguageTypeLocal xno = new LanguageTypeRu("Англо-нормандский", LanguageType.xno);
    public static final LanguageTypeLocal xrn = new LanguageTypeRu("Аринский", LanguageType.xrn);
    public static final LanguageTypeLocal xsr = new LanguageTypeRu("Шерпский", LanguageType.xsr);
    public static final LanguageTypeLocal xto = new LanguageTypeRu("Тохарские", LanguageType.xto);
    public static final LanguageTypeLocal xvn = new LanguageTypeRu("Вандальский", LanguageType.xvn);

    public static final LanguageTypeLocal yi = new LanguageTypeRu("Идиш", LanguageType.yi);
    public static final LanguageTypeLocal ykg = new LanguageTypeRu("Северноюкагирский", LanguageType.ykg);
    public static final LanguageTypeLocal yo = new LanguageTypeRu("Йоруба", LanguageType.yo);
    public static final LanguageTypeLocal yrk = new LanguageTypeRu("Ненецкий", LanguageType.yrk);
    public static final LanguageTypeLocal yua = new LanguageTypeRu("Юкатекский", LanguageType.yua);
    public static final LanguageTypeLocal yux = new LanguageTypeRu("Южноюкагирский", LanguageType.yux);

    public static final LanguageTypeLocal ze = new LanguageTypeRu("Генуэзский диалект лигурского языка", LanguageType.ze);
    public static final LanguageTypeLocal zh = new LanguageTypeRu("Китайский", LanguageType.zh);
    public static final LanguageTypeLocal zh_cn = new LanguageTypeRu("Китайский (упрощ.)", LanguageType.zh_cn);
    public static final LanguageTypeLocal zh_tw = new LanguageTypeRu("Китайский (традиц.)", LanguageType.zh_tw);
    public static final LanguageTypeLocal zko = new LanguageTypeRu("Коттский", LanguageType.zko);
    public static final LanguageTypeLocal zza = new LanguageTypeRu("Зазаки", LanguageType.zza);

}
