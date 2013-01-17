/* LanguageTypeRu.java - name of languages in Russian.
 *
 * Copyright (c) 2010-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.language.LanguageTypeLocal;

import java.util.Map;
import java.util.HashMap;

/** Languages of wiki: name in Russian and link to the LanguageType codes.
 *
 * Source of data:
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
    public static final LanguageTypeLocal agg = new LanguageTypeRu("Ангор", LanguageType.agg);
    public static final LanguageTypeLocal agx = new LanguageTypeRu("Агульский", LanguageType.agx);
    public static final LanguageTypeLocal aib = new LanguageTypeRu("Айнийский", LanguageType.aib);
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
    public static final LanguageTypeLocal ani = new LanguageTypeRu("Андийский", LanguageType.ani);
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
    public static final LanguageTypeLocal arz = new LanguageTypeRu("Египетский арабский", LanguageType.arz);
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
    public static final LanguageTypeLocal bcl = new LanguageTypeRu("Бикольский", LanguageType.bcl);
    public static final LanguageTypeLocal bdk = new LanguageTypeRu("Будухский", LanguageType.bdk);
    public static final LanguageTypeLocal be = new LanguageTypeRu("Белорусский", LanguageType.be);
    public static final LanguageTypeLocal bem = new LanguageTypeRu("Бемба", LanguageType.bem);
    public static final LanguageTypeLocal bg = new LanguageTypeRu("Болгарский", LanguageType.bg);
    public static final LanguageTypeLocal bh = new LanguageTypeRu("Бихарский", LanguageType.bh);
    public static final LanguageTypeLocal bho = new LanguageTypeRu("Бходжпури", LanguageType.bho);
    public static final LanguageTypeLocal bi = new LanguageTypeRu("Бислама", LanguageType.bi);
    public static final LanguageTypeLocal bib = new LanguageTypeRu("Биса", LanguageType.bib);
    public static final LanguageTypeLocal bjn = new LanguageTypeRu("Банджарский", LanguageType.bjn);
    public static final LanguageTypeLocal bla = new LanguageTypeRu("Блэкфут", LanguageType.bla);
    public static final LanguageTypeLocal bm = new LanguageTypeRu("Бамана", LanguageType.bm);
    public static final LanguageTypeLocal bn = new LanguageTypeRu("Бенгальский", LanguageType.bn);
    public static final LanguageTypeLocal bo = new LanguageTypeRu("Тибетский", LanguageType.bo);
    public static final LanguageTypeLocal boa = new LanguageTypeRu("Бора", LanguageType.boa);
    public static final LanguageTypeLocal bph = new LanguageTypeRu("Ботлихский", LanguageType.bph);
    public static final LanguageTypeLocal bpy = new LanguageTypeRu("Бишнуприя-манипури", LanguageType.bpy);
    public static final LanguageTypeLocal br = new LanguageTypeRu("Бретонский", LanguageType.br);
    public static final LanguageTypeLocal bra = new LanguageTypeRu("Брадж бхакха", LanguageType.bra);
    public static final LanguageTypeLocal brh = new LanguageTypeRu("Брауи", LanguageType.brh);
    public static final LanguageTypeLocal brx = new LanguageTypeRu("Бодо", LanguageType.brx);
    public static final LanguageTypeLocal bs = new LanguageTypeRu("Боснийский", LanguageType.bs);
    public static final LanguageTypeLocal bsh = new LanguageTypeRu("Кати", LanguageType.bsh);
    public static final LanguageTypeLocal btk = new LanguageTypeRu("Батакский", LanguageType.btk);
    public static final LanguageTypeLocal bua = new LanguageTypeRu("Бурятский", LanguageType.bua);
    public static final LanguageTypeLocal bug = new LanguageTypeRu("Бугийский", LanguageType.bug);
    public static final LanguageTypeLocal byn = new LanguageTypeRu("Билин", LanguageType.byn);

    public static final LanguageTypeLocal ca = new LanguageTypeRu("Каталанский", LanguageType.ca);
    public static final LanguageTypeLocal cbk = new LanguageTypeRu("Чабакано", LanguageType.cbk);
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
    public static final LanguageTypeLocal chumas = new LanguageTypeRu("Чумашский", LanguageType.chumas);
    // cu chu_ru public static final LanguageTypeLocal chu_ru = new LanguageTypeRu("Церковнославянский", LanguageType.chu_ru);
    public static final LanguageTypeLocal chy = new LanguageTypeRu("Шайенский", LanguageType.chy);
    public static final LanguageTypeLocal cia = new LanguageTypeRu("Чиа-чиа", LanguageType.cia);
    public static final LanguageTypeLocal cic = new LanguageTypeRu("Чикасо", LanguageType.cic);
    public static final LanguageTypeLocal cjs = new LanguageTypeRu("Шорский", LanguageType.cjs);
    public static final LanguageTypeLocal ckb = new LanguageTypeRu("Сорани", LanguageType.ckb);
    public static final LanguageTypeLocal ckt = new LanguageTypeRu("Чукотский", LanguageType.ckt);
    public static final LanguageTypeLocal co = new LanguageTypeRu("Корсиканский", LanguageType.co);
    public static final LanguageTypeLocal com = new LanguageTypeRu("Команчский", LanguageType.com);
    public static final LanguageTypeLocal cop = new LanguageTypeRu("Коптский", LanguageType.cop);
    // el public static final LanguageTypeLocal cpg = new LanguageTypeRu("Каппадокийский", LanguageType.cpg);
    public static final LanguageTypeLocal cr = new LanguageTypeRu("Кри", LanguageType.cr);
    public static final LanguageTypeLocal crg = new LanguageTypeRu("Мичиф", LanguageType.crg);
    public static final LanguageTypeLocal crh = new LanguageTypeRu("Крымскотатарский", LanguageType.crh);

    public static final LanguageTypeLocal crp_rsn = new LanguageTypeRu("Руссенорск", LanguageType.crp_rsn);
    public static final LanguageTypeLocal crp_tpr = new LanguageTypeRu("Говорка", LanguageType.crp_tpr);

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
    public static final LanguageTypeLocal den = new LanguageTypeRu("Слейви", LanguageType.den);
    public static final LanguageTypeLocal dgr = new LanguageTypeRu("Догриб", LanguageType.dgr);
    public static final LanguageTypeLocal dif = new LanguageTypeRu("Диери", LanguageType.dif);
    public static final LanguageTypeLocal din = new LanguageTypeRu("Динка", LanguageType.din);
    public static final LanguageTypeLocal dlg = new LanguageTypeRu("Долганский", LanguageType.dlg);
    public static final LanguageTypeLocal dlm = new LanguageTypeRu("Далматинский", LanguageType.dlm);
    public static final LanguageTypeLocal dng = new LanguageTypeRu("Дунганский", LanguageType.dng);
    public static final LanguageTypeLocal doi = new LanguageTypeRu("Догри", LanguageType.doi);
    public static final LanguageTypeLocal dsb = new LanguageTypeRu("Нижнелужицкий", LanguageType.dsb);
    public static final LanguageTypeLocal dv = new LanguageTypeRu("Мальдивский", LanguageType.dv);
    public static final LanguageTypeLocal dyu = new LanguageTypeRu("Дьюла", LanguageType.dyu);
    public static final LanguageTypeLocal dz = new LanguageTypeRu("Дзонг-кэ", LanguageType.dz);

    public static final LanguageTypeLocal ecr = new LanguageTypeRu("Этеокритский", LanguageType.ecr);
    public static final LanguageTypeLocal egy = new LanguageTypeRu("Египетский", LanguageType.egy);
    public static final LanguageTypeLocal el = new LanguageTypeRu("Греческий", LanguageType.el);
    // el public static final LanguageTypeLocal el_dhi = new LanguageTypeRu("Греческий демот.", LanguageType.el_dhi);
    // el public static final LanguageTypeLocal el_kat = new LanguageTypeRu("Греческий кафар.", LanguageType.el_kat);
    public static final LanguageTypeLocal elx = new LanguageTypeRu("Эламский", LanguageType.elx);
    public static final LanguageTypeLocal eml = new LanguageTypeRu("Эмилиано-романьольский", LanguageType.eml);
    public static final LanguageTypeLocal ems = new LanguageTypeRu("Алютикский", LanguageType.ems);
    public static final LanguageTypeLocal en = new LanguageTypeRu("Английский", LanguageType.en);
    // en public static final LanguageTypeLocal en_au = new LanguageTypeRu("Английский (австралийский диалект)", LanguageType.en_au);
    // en public static final LanguageTypeLocal en_nz = new LanguageTypeRu("Новозеландский вариант английского языка", LanguageType.en_nz);
    // en public static final LanguageTypeLocal en_us = new LanguageTypeRu("Английский (американский)", LanguageType.en_us);
    public static final LanguageTypeLocal enm = new LanguageTypeRu("Среднеанглийский", LanguageType.enm);
    public static final LanguageTypeLocal eo = new LanguageTypeRu("Эсперанто", LanguageType.eo);
    public static final LanguageTypeLocal es = new LanguageTypeRu("Испанский", LanguageType.es);
    public static final LanguageTypeLocal esu = new LanguageTypeRu("Центрально-юпикский", LanguageType.esu);
    public static final LanguageTypeLocal et = new LanguageTypeRu("Эстонский", LanguageType.et);
    public static final LanguageTypeLocal ett = new LanguageTypeRu("Этрусский", LanguageType.ett);
    public static final LanguageTypeLocal eu = new LanguageTypeRu("Баскский", LanguageType.eu);
    public static final LanguageTypeLocal eve = new LanguageTypeRu("Эвенский", LanguageType.eve);
    public static final LanguageTypeLocal evn = new LanguageTypeRu("Эвенкийский", LanguageType.evn);
    public static final LanguageTypeLocal ewe = new LanguageTypeRu("Эве", LanguageType.ewe);
    public static final LanguageTypeLocal ext = new LanguageTypeRu("Эстремадурский", LanguageType.ext);

    public static final LanguageTypeLocal fa = new LanguageTypeRu("Персидский", LanguageType.fa);
    // fat -> aka public static final LanguageTypeLocal fat = new LanguageTypeRu("Фанти", LanguageType.fat);
    public static final LanguageTypeLocal ff = new LanguageTypeRu("Фула", LanguageType.ff);
    public static final LanguageTypeLocal fi = new LanguageTypeRu("Финский", LanguageType.fi);
    public static final LanguageTypeLocal fic_drw = new LanguageTypeRu("Дроу", LanguageType.fic_drw);
    public static final LanguageTypeLocal fip = new LanguageTypeRu("Фипа", LanguageType.fip);
    public static final LanguageTypeLocal fit = new LanguageTypeRu("Меянкиели", LanguageType.fit);
    public static final LanguageTypeLocal fj = new LanguageTypeRu("Фиджийский", LanguageType.fj);
    public static final LanguageTypeLocal fo = new LanguageTypeRu("Фарерский", LanguageType.fo);
    public static final LanguageTypeLocal fon = new LanguageTypeRu("Фон", LanguageType.fon);
    public static final LanguageTypeLocal fr = new LanguageTypeRu("Французский", LanguageType.fr);
    // fr public static final LanguageTypeLocal fr_be = new LanguageTypeRu("Французский (бельгийский)", LanguageType.fr_be);
    // fr public static final LanguageTypeLocal fr_ch = new LanguageTypeRu("Французский (швейцарский)", LanguageType.fr_ch);
    public static final LanguageTypeLocal frm = new LanguageTypeRu("Среднефранцузский", LanguageType.frm);
    public static final LanguageTypeLocal fro = new LanguageTypeRu("Старофранцузский", LanguageType.fro);
    public static final LanguageTypeLocal frp = new LanguageTypeRu("Франкопровансальский", LanguageType.frp);
    public static final LanguageTypeLocal fur = new LanguageTypeRu("Фриульский", LanguageType.fur);
    public static final LanguageTypeLocal fy = new LanguageTypeRu("Фризский", LanguageType.fy);

    public static final LanguageTypeLocal ga = new LanguageTypeRu("Ирландский", LanguageType.ga);
    public static final LanguageTypeLocal gag = new LanguageTypeRu("Гагаузский", LanguageType.gag);
    public static final LanguageTypeLocal gan = new LanguageTypeRu("Гань", LanguageType.gan);
    public static final LanguageTypeLocal gcf = new LanguageTypeRu("Антильский франко-креольский язык", LanguageType.gcf);
    public static final LanguageTypeLocal gd = new LanguageTypeRu("Шотландский (кельтский)", LanguageType.gd);
    public static final LanguageTypeLocal gdm = new LanguageTypeRu("Лаал", LanguageType.gdm);
    public static final LanguageTypeLocal gez = new LanguageTypeRu("Геэз", LanguageType.gez);
    public static final LanguageTypeLocal gil = new LanguageTypeRu("Кирибати", LanguageType.gil);
    public static final LanguageTypeLocal gl = new LanguageTypeRu("Галисийский", LanguageType.gl);
    public static final LanguageTypeLocal gld = new LanguageTypeRu("Нанайский", LanguageType.gld);
    public static final LanguageTypeLocal glk = new LanguageTypeRu("Гилянский", LanguageType.glk);
    public static final LanguageTypeLocal gmh = new LanguageTypeRu("Средневерхненемецкий", LanguageType.gmh);
    public static final LanguageTypeLocal gmy = new LanguageTypeRu("Микенский", LanguageType.gmy);
    public static final LanguageTypeLocal gni = new LanguageTypeRu("Куниянти", LanguageType.gni);
    public static final LanguageTypeLocal goh = new LanguageTypeRu("Древневерхненемецкий", LanguageType.goh);
    public static final LanguageTypeLocal gon = new LanguageTypeRu("Гонди", LanguageType.gon);
    public static final LanguageTypeLocal got = new LanguageTypeRu("Готский", LanguageType.got);
    public static final LanguageTypeLocal grc = new LanguageTypeRu("Древнегреческий", LanguageType.grc);
    public static final LanguageTypeLocal gn = new LanguageTypeRu("Гуарани", LanguageType.gn);
    public static final LanguageTypeLocal gsw = new LanguageTypeRu("Швейцарский диалект", LanguageType.gsw);
    public static final LanguageTypeLocal gu = new LanguageTypeRu("Гуджарати", LanguageType.gu);
    public static final LanguageTypeLocal gv = new LanguageTypeRu("Мэнский", LanguageType.gv);
    public static final LanguageTypeLocal gwi = new LanguageTypeRu("Гвичин", LanguageType.gwi);

    public static final LanguageTypeLocal ha = new LanguageTypeRu("Хауса", LanguageType.ha);
    public static final LanguageTypeLocal hai = new LanguageTypeRu("Хайда", LanguageType.hai);
    public static final LanguageTypeLocal hak = new LanguageTypeRu("Хакка", LanguageType.hak);
    public static final LanguageTypeLocal hanzi = new LanguageTypeRu("Китайский иероглиф", LanguageType.hanzi);
    public static final LanguageTypeLocal haw = new LanguageTypeRu("Гавайский", LanguageType.haw);
    // he hbo hb public static final LanguageTypeLocal hbo = new LanguageTypeRu("Древнееврейский", LanguageType.hbo);
    public static final LanguageTypeLocal he = new LanguageTypeRu("Иврит", LanguageType.he);
    public static final LanguageTypeLocal heh = new LanguageTypeRu("Хехе", LanguageType.heh);
    public static final LanguageTypeLocal hi = new LanguageTypeRu("Хинди", LanguageType.hi);
    public static final LanguageTypeLocal hif = new LanguageTypeRu("Фиджийский хинди", LanguageType.hif);
    public static final LanguageTypeLocal hil = new LanguageTypeRu("Хилигайнон", LanguageType.hil);
    public static final LanguageTypeLocal hit = new LanguageTypeRu("Хеттский", LanguageType.hit);
    public static final LanguageTypeLocal hmn = new LanguageTypeRu("Мяо", LanguageType.hmn);
    public static final LanguageTypeLocal ho = new LanguageTypeRu("Хири-моту", LanguageType.ho);
    public static final LanguageTypeLocal hr = new LanguageTypeRu("Хорватский", LanguageType.hr);
    public static final LanguageTypeLocal hsb = new LanguageTypeRu("Верхнелужицкий", LanguageType.hsb);
    public static final LanguageTypeLocal ht = new LanguageTypeRu("Гаитянский креольский", LanguageType.ht);
    public static final LanguageTypeLocal hu = new LanguageTypeRu("Венгерский", LanguageType.hu);
    public static final LanguageTypeLocal huh = new LanguageTypeRu("Уильиче", LanguageType.huh);
    public static final LanguageTypeLocal hup = new LanguageTypeRu("Хупа", LanguageType.hup);
    public static final LanguageTypeLocal huq = new LanguageTypeRu("Цатский", LanguageType.huq);
    public static final LanguageTypeLocal hur = new LanguageTypeRu("Халкомелем", LanguageType.hur);
    public static final LanguageTypeLocal hy = new LanguageTypeRu("Армянский", LanguageType.hy);
    public static final LanguageTypeLocal hz = new LanguageTypeRu("Гереро", LanguageType.hz);

    public static final LanguageTypeLocal ibo = new LanguageTypeRu("Игбо", LanguageType.ibo);
    public static final LanguageTypeLocal id_ = new LanguageTypeRu("Индонезийский", LanguageType.id_);
    public static final LanguageTypeLocal ie = new LanguageTypeRu("Окциденталь", LanguageType.ie);
    public static final LanguageTypeLocal iii = new LanguageTypeRu("Носу", LanguageType.iii);
    public static final LanguageTypeLocal ik = new LanguageTypeRu("Инупиак", LanguageType.ik);
    public static final LanguageTypeLocal ilo = new LanguageTypeRu("Илоканский", LanguageType.ilo);
    public static final LanguageTypeLocal ina = new LanguageTypeRu("Интерлингва", LanguageType.ina);
    public static final LanguageTypeLocal inh = new LanguageTypeRu("Ингушский", LanguageType.inh);
    public static final LanguageTypeLocal io = new LanguageTypeRu("Идо", LanguageType.io);
    public static final LanguageTypeLocal is = new LanguageTypeRu("Исландский", LanguageType.is);
    public static final LanguageTypeLocal ist = new LanguageTypeRu("Истророманский", LanguageType.ist);
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
    public static final LanguageTypeLocal jrb = new LanguageTypeRu("Еврейско-арабский", LanguageType.jrb);
    public static final LanguageTypeLocal juc = new LanguageTypeRu("Чжурчжэньский", LanguageType.juc);
    public static final LanguageTypeLocal jv = new LanguageTypeRu("Яванский", LanguageType.jv);

    public static final LanguageTypeLocal ka = new LanguageTypeRu("Грузинский", LanguageType.ka);
    public static final LanguageTypeLocal kaa = new LanguageTypeRu("Каракалпакский", LanguageType.kaa);
    public static final LanguageTypeLocal kaw = new LanguageTypeRu("Кави", LanguageType.kaw);
    public static final LanguageTypeLocal kab = new LanguageTypeRu("Кабильский", LanguageType.kab);
    public static final LanguageTypeLocal kal = new LanguageTypeRu("Гренландский", LanguageType.kal);
    public static final LanguageTypeLocal kam = new LanguageTypeRu("Камба", LanguageType.kam);
    public static final LanguageTypeLocal kaz = new LanguageTypeRu("Казахский", LanguageType.kaz);
    public static final LanguageTypeLocal kbd = new LanguageTypeRu("Кабардино-черкесский", LanguageType.kbd);
    public static final LanguageTypeLocal kca = new LanguageTypeRu("Хантыйский", LanguageType.kca);
    public static final LanguageTypeLocal kdr = new LanguageTypeRu("Караимский", LanguageType.kdr);
    public static final LanguageTypeLocal kea = new LanguageTypeRu("Кабувердьяну", LanguageType.kea);
    public static final LanguageTypeLocal ket = new LanguageTypeRu("Кетский", LanguageType.ket);
    public static final LanguageTypeLocal kg = new LanguageTypeRu("Конго", LanguageType.kg);
    public static final LanguageTypeLocal kgg = new LanguageTypeRu("Кусунда", LanguageType.kgg);
    public static final LanguageTypeLocal kha = new LanguageTypeRu("Кхаси", LanguageType.kha);
    public static final LanguageTypeLocal kho = new LanguageTypeRu("Хотаносакский", LanguageType.kho);
    public static final LanguageTypeLocal khv = new LanguageTypeRu("Хваршинский", LanguageType.khv);
    public static final LanguageTypeLocal ki = new LanguageTypeRu("Кикуйю", LanguageType.ki);
    public static final LanguageTypeLocal kj = new LanguageTypeRu("Ошивамбо", LanguageType.kj);
    public static final LanguageTypeLocal kim = new LanguageTypeRu("Тофаларский", LanguageType.kim);
    public static final LanguageTypeLocal kjh = new LanguageTypeRu("Хакасский", LanguageType.kjh);
    // kmr ku public static final LanguageTypeLocal kmr = new LanguageTypeRu("Курманджи", LanguageType.kmr);
    public static final LanguageTypeLocal km = new LanguageTypeRu("Кхмерский", LanguageType.km);
    public static final LanguageTypeLocal kn = new LanguageTypeRu("Каннада", LanguageType.kn);
    public static final LanguageTypeLocal ko = new LanguageTypeRu("Корейский", LanguageType.ko);
    public static final LanguageTypeLocal kv = new LanguageTypeRu("Коми", LanguageType.kv);
    // kom public static final LanguageTypeLocal kom = new LanguageTypeRu("Коми-зырянский", LanguageType.kom);
    public static final LanguageTypeLocal kok = new LanguageTypeRu("Конкани", LanguageType.kok);
    public static final LanguageTypeLocal kpy = new LanguageTypeRu("Корякский", LanguageType.kpy);
    public static final LanguageTypeLocal kr = new LanguageTypeRu("Канури", LanguageType.kr);
    public static final LanguageTypeLocal krc = new LanguageTypeRu("Карачаево-балкарский", LanguageType.krc);
    public static final LanguageTypeLocal kri = new LanguageTypeRu("Крио", LanguageType.kri);
    public static final LanguageTypeLocal krj = new LanguageTypeRu("Кинарайский", LanguageType.krj);
    public static final LanguageTypeLocal krl = new LanguageTypeRu("Карельский", LanguageType.krl);
    public static final LanguageTypeLocal kru = new LanguageTypeRu("Курух", LanguageType.kru);
    public static final LanguageTypeLocal ks = new LanguageTypeRu("Кашмирский", LanguageType.ks);
    public static final LanguageTypeLocal ksb = new LanguageTypeRu("Шамбала", LanguageType.ksb);
    public static final LanguageTypeLocal ksd = new LanguageTypeRu("Толаи", LanguageType.ksd);
    // ku public static final LanguageTypeLocal ku = new LanguageTypeRu("Курдский", LanguageType.ku);
    public static final LanguageTypeLocal kum = new LanguageTypeRu("Кумыкский", LanguageType.kum);
    public static final LanguageTypeLocal kut = new LanguageTypeRu("Кутенай", LanguageType.kut);
    public static final LanguageTypeLocal kw = new LanguageTypeRu("Корнский", LanguageType.kw);
    public static final LanguageTypeLocal ky = new LanguageTypeRu("Киргизский", LanguageType.ky);
    public static final LanguageTypeLocal kyh = new LanguageTypeRu("Карук", LanguageType.kyh);

    public static final LanguageTypeLocal la = new LanguageTypeRu("Латинский", LanguageType.la);
    public static final LanguageTypeLocal lad = new LanguageTypeRu("Сефардский", LanguageType.lad);
    public static final LanguageTypeLocal lah = new LanguageTypeRu("Лахнда", LanguageType.lah);
    public static final LanguageTypeLocal lb = new LanguageTypeRu("Люксембургский", LanguageType.lb);
    public static final LanguageTypeLocal lbe = new LanguageTypeRu("Лакский", LanguageType.lbe);
    public static final LanguageTypeLocal lep = new LanguageTypeRu("Лепча", LanguageType.lep);
    public static final LanguageTypeLocal letter_ru = new LanguageTypeRu("Буква", LanguageType.letter_ru);
    public static final LanguageTypeLocal lez = new LanguageTypeRu("Лезгинский", LanguageType.lez);
    public static final LanguageTypeLocal lfn = new LanguageTypeRu("Лингва франка нова", LanguageType.lfn);
    public static final LanguageTypeLocal lg = new LanguageTypeRu("Луганда", LanguageType.lg);
    public static final LanguageTypeLocal li = new LanguageTypeRu("Лимбургский", LanguageType.li);
    public static final LanguageTypeLocal lif = new LanguageTypeRu("Лимбу", LanguageType.lif);
    public static final LanguageTypeLocal lij = new LanguageTypeRu("Лигурский", LanguageType.lij);
    public static final LanguageTypeLocal liv = new LanguageTypeRu("Ливский", LanguageType.liv);
    public static final LanguageTypeLocal lld = new LanguageTypeRu("Ладинский", LanguageType.lld);
    public static final LanguageTypeLocal lmo = new LanguageTypeRu("Ломбардский", LanguageType.lmo);
    public static final LanguageTypeLocal ln = new LanguageTypeRu("Лингала", LanguageType.ln);
    public static final LanguageTypeLocal lng = new LanguageTypeRu("Лангобардский", LanguageType.lng);
    public static final LanguageTypeLocal lo = new LanguageTypeRu("Лаосский", LanguageType.lo);
    public static final LanguageTypeLocal lre = new LanguageTypeRu("Лаврентийский", LanguageType.lre);
    public static final LanguageTypeLocal lt = new LanguageTypeRu("Литовский", LanguageType.lt);
    public static final LanguageTypeLocal ltg = new LanguageTypeRu("Латгальский", LanguageType.ltg);
    public static final LanguageTypeLocal lua = new LanguageTypeRu("Луба", LanguageType.lua);
    public static final LanguageTypeLocal luo = new LanguageTypeRu("Долуо", LanguageType.luo);
    public static final LanguageTypeLocal luy = new LanguageTypeRu("Лухья", LanguageType.luy);
    public static final LanguageTypeLocal lv = new LanguageTypeRu("Латышский", LanguageType.lv);
    public static final LanguageTypeLocal lvk = new LanguageTypeRu("Лавукалеве", LanguageType.lvk);
    public static final LanguageTypeLocal lzz = new LanguageTypeRu("Лазский", LanguageType.lzz);

    public static final LanguageTypeLocal mad = new LanguageTypeRu("Мадурский", LanguageType.mad);
    public static final LanguageTypeLocal mag = new LanguageTypeRu("Магахи", LanguageType.mag);
    public static final LanguageTypeLocal mai = new LanguageTypeRu("Майтхили", LanguageType.mai);
    public static final LanguageTypeLocal man = new LanguageTypeRu("Мандинго", LanguageType.man);
    public static final LanguageTypeLocal mаs = new LanguageTypeRu("Масайский", LanguageType.mаs);
    public static final LanguageTypeLocal mbc = new LanguageTypeRu("Макуши", LanguageType.mbc);
    public static final LanguageTypeLocal mdf = new LanguageTypeRu("Мокшанский", LanguageType.mdf);
    public static final LanguageTypeLocal men = new LanguageTypeRu("Менде", LanguageType.men);
    public static final LanguageTypeLocal meu = new LanguageTypeRu("Моту", LanguageType.meu);
    public static final LanguageTypeLocal mey = new LanguageTypeRu("Хасания", LanguageType.mey);
    public static final LanguageTypeLocal mg = new LanguageTypeRu("Малагасийский", LanguageType.mg);
    public static final LanguageTypeLocal mgm = new LanguageTypeRu("Мамбай", LanguageType.mgm);
    public static final LanguageTypeLocal mh = new LanguageTypeRu("Маршалльский", LanguageType.mh);
    public static final LanguageTypeLocal mi = new LanguageTypeRu("Маори", LanguageType.mi);
    public static final LanguageTypeLocal mic = new LanguageTypeRu("Микмак", LanguageType.mic);
    public static final LanguageTypeLocal min = new LanguageTypeRu("Минангкабау", LanguageType.min);
    public static final LanguageTypeLocal mjg = new LanguageTypeRu("Монгорский", LanguageType.mjg);
    public static final LanguageTypeLocal mk = new LanguageTypeRu("Македонский", LanguageType.mk);
    public static final LanguageTypeLocal ml = new LanguageTypeRu("Малаялам", LanguageType.ml);
    public static final LanguageTypeLocal mn = new LanguageTypeRu("Монгольский", LanguageType.mn);
    public static final LanguageTypeLocal mnc = new LanguageTypeRu("Маньчжурский", LanguageType.mnc);
    public static final LanguageTypeLocal mni = new LanguageTypeRu("Манипури", LanguageType.mni);
    public static final LanguageTypeLocal mnk = new LanguageTypeRu("Мандинка", LanguageType.mnk);
    public static final LanguageTypeLocal mns = new LanguageTypeRu("Мансийский", LanguageType.mns);
    public static final LanguageTypeLocal mo = new LanguageTypeRu("Молдавский", LanguageType.mo);
    public static final LanguageTypeLocal mod = new LanguageTypeRu("Мобильский", LanguageType.mod);
    public static final LanguageTypeLocal moe = new LanguageTypeRu("Монтанье-наскапи", LanguageType.moe);
    public static final LanguageTypeLocal moh = new LanguageTypeRu("Могаукский", LanguageType.moh);
    public static final LanguageTypeLocal mos = new LanguageTypeRu("Море", LanguageType.mos);
    public static final LanguageTypeLocal mr = new LanguageTypeRu("Маратхи", LanguageType.mr);
    public static final LanguageTypeLocal mrv = new LanguageTypeRu("Мангареванский", LanguageType.mrv);
    public static final LanguageTypeLocal ms = new LanguageTypeRu("Малайский", LanguageType.ms);
    public static final LanguageTypeLocal mt = new LanguageTypeRu("Мальтийский", LanguageType.mt);
    public static final LanguageTypeLocal mtm = new LanguageTypeRu("Маторский", LanguageType.mtm);
    public static final LanguageTypeLocal mul = new LanguageTypeRu("Международное обозначение", LanguageType.mul);// Межъязыковой
    public static final LanguageTypeLocal mus = new LanguageTypeRu("Крикский", LanguageType.mus);
    public static final LanguageTypeLocal mwl = new LanguageTypeRu("Мирандский", LanguageType.mwl);
    public static final LanguageTypeLocal mxi = new LanguageTypeRu("Мосарабский", LanguageType.mxi);
    public static final LanguageTypeLocal my = new LanguageTypeRu("Бирманский", LanguageType.my);
    public static final LanguageTypeLocal myp = new LanguageTypeRu("Пираха", LanguageType.myp);
    public static final LanguageTypeLocal myv = new LanguageTypeRu("Эрзянский", LanguageType.myv);
    public static final LanguageTypeLocal mzn = new LanguageTypeRu("Мазендеранский", LanguageType.mzn);

    public static final LanguageTypeLocal na = new LanguageTypeRu("Науруанский", LanguageType.na);
    public static final LanguageTypeLocal nah = new LanguageTypeRu("Астекский", LanguageType.nah);
    public static final LanguageTypeLocal nan = new LanguageTypeRu("Китайский (южноминьский)", LanguageType.nan);
    public static final LanguageTypeLocal nap = new LanguageTypeRu("Неаполитанский", LanguageType.nap);
    public static final LanguageTypeLocal naq = new LanguageTypeRu("Нама", LanguageType.naq);
    public static final LanguageTypeLocal nb = new LanguageTypeRu("Букмол", LanguageType.nb);
    public static final LanguageTypeLocal nds = new LanguageTypeRu("Нижненемецкий", LanguageType.nds);
    public static final LanguageTypeLocal ne = new LanguageTypeRu("Непальский", LanguageType.ne);
    public static final LanguageTypeLocal new_ = new LanguageTypeRu("Неварский", LanguageType.new_);
    public static final LanguageTypeLocal nia = new LanguageTypeRu("Ниасский", LanguageType.nia);
    public static final LanguageTypeLocal nio = new LanguageTypeRu("Нганасанский", LanguageType.nio);
    public static final LanguageTypeLocal niu = new LanguageTypeRu("Ниуэ", LanguageType.niu);
    public static final LanguageTypeLocal niv = new LanguageTypeRu("Нивхский", LanguageType.niv);
    public static final LanguageTypeLocal nl = new LanguageTypeRu("Нидерландский", LanguageType.nl);
    public static final LanguageTypeLocal nmn = new LanguageTypeRu("Къхонг", LanguageType.nmn);
    public static final LanguageTypeLocal nn = new LanguageTypeRu("Нюнорск", LanguageType.nn);
    public static final LanguageTypeLocal no = new LanguageTypeRu("Норвежский", LanguageType.no);
    public static final LanguageTypeLocal nog = new LanguageTypeRu("Ногайский", LanguageType.nog);
    public static final LanguageTypeLocal non = new LanguageTypeRu("Древнеисландский", LanguageType.non);
    public static final LanguageTypeLocal nov = new LanguageTypeRu("Новиаль", LanguageType.nov);
    public static final LanguageTypeLocal nqo = new LanguageTypeRu("Нко", LanguageType.nqo);
    public static final LanguageTypeLocal nrn = new LanguageTypeRu("Норн", LanguageType.nrn);
    public static final LanguageTypeLocal num = new LanguageTypeRu("Ниуафооу", LanguageType.num);
    public static final LanguageTypeLocal nv = new LanguageTypeRu("Навахо", LanguageType.nv);
    public static final LanguageTypeLocal ny = new LanguageTypeRu("Ньянджа", LanguageType.ny);

    public static final LanguageTypeLocal obm = new LanguageTypeRu("Моавитский", LanguageType.obm);
    public static final LanguageTypeLocal obt = new LanguageTypeRu("Древнебретонский", LanguageType.obt);
    public static final LanguageTypeLocal oc = new LanguageTypeRu("Окситанский", LanguageType.oc);
    public static final LanguageTypeLocal oj = new LanguageTypeRu("Оджибва", LanguageType.oj);
    public static final LanguageTypeLocal om = new LanguageTypeRu("Оромо", LanguageType.om);
    public static final LanguageTypeLocal omn = new LanguageTypeRu("Минойский", LanguageType.omn);
    public static final LanguageTypeLocal ood = new LanguageTypeRu("Оодхам", LanguageType.ood);
    public static final LanguageTypeLocal or = new LanguageTypeRu("Ория", LanguageType.or);
    public static final LanguageTypeLocal orv = new LanguageTypeRu("Древнерусский", LanguageType.orv);
    public static final LanguageTypeLocal os = new LanguageTypeRu("Осетинский", LanguageType.os);
    public static final LanguageTypeLocal osc = new LanguageTypeRu("Оскский", LanguageType.osc);
    public static final LanguageTypeLocal ota = new LanguageTypeRu("Османский", LanguageType.ota);
    public static final LanguageTypeLocal otk = new LanguageTypeRu("Орхоно-енисейский", LanguageType.otk);
    public static final LanguageTypeLocal owl = new LanguageTypeRu("Древневаллийский", LanguageType.owl);

    public static final LanguageTypeLocal pa = new LanguageTypeRu("Панджаби", LanguageType.pa);
    public static final LanguageTypeLocal pag = new LanguageTypeRu("Пангасинанский", LanguageType.pag);
    public static final LanguageTypeLocal pal = new LanguageTypeRu("Среднеперсидский", LanguageType.pal);
    public static final LanguageTypeLocal pam = new LanguageTypeRu("Капампанганский", LanguageType.pam);
    public static final LanguageTypeLocal pap = new LanguageTypeRu("Папьяменто", LanguageType.pap);
    public static final LanguageTypeLocal pau = new LanguageTypeRu("Палауский", LanguageType.pau);
    public static final LanguageTypeLocal pcd = new LanguageTypeRu("Пикардский", LanguageType.pcd);
    public static final LanguageTypeLocal pdc = new LanguageTypeRu("Пенсильванско-немецкий", LanguageType.pdc);
    public static final LanguageTypeLocal peo = new LanguageTypeRu("Древнеперсидский", LanguageType.peo);
    public static final LanguageTypeLocal phn = new LanguageTypeRu("Финикийский", LanguageType.phn);
    public static final LanguageTypeLocal pi = new LanguageTypeRu("Пали", LanguageType.pi);
    public static final LanguageTypeLocal pih = new LanguageTypeRu("Питкэрнский-Норфолкский", LanguageType.pih);
    public static final LanguageTypeLocal pis = new LanguageTypeRu("Пиджин Соломоновых островов", LanguageType.pis);
    // zh public static final LanguageTypeLocal pinyin = new LanguageTypeRu("Пиньинь", LanguageType.pinyin);
    public static final LanguageTypeLocal pjt = new LanguageTypeRu("Питьянтьятьяра", LanguageType.pjt);
    public static final LanguageTypeLocal pl = new LanguageTypeRu("Польский", LanguageType.pl);
    public static final LanguageTypeLocal pms = new LanguageTypeRu("Пьемонтский", LanguageType.pms);
    public static final LanguageTypeLocal pmt = new LanguageTypeRu("Туамоту", LanguageType.pmt);
    // el public static final LanguageTypeLocal pnt = new LanguageTypeRu("Понтийский", LanguageType.pnt);
    public static final LanguageTypeLocal pox = new LanguageTypeRu("Полабский", LanguageType.pox);
    public static final LanguageTypeLocal ppol = new LanguageTypeRu("Протополинезийский", LanguageType.ppol);
    public static final LanguageTypeLocal prg = new LanguageTypeRu("Прусский", LanguageType.prg);
    public static final LanguageTypeLocal prs = new LanguageTypeRu("Дари", LanguageType.prs);
    public static final LanguageTypeLocal ps = new LanguageTypeRu("Пушту", LanguageType.ps);
    public static final LanguageTypeLocal psl = new LanguageTypeRu("Праславянский", LanguageType.psl);
    public static final LanguageTypeLocal pt = new LanguageTypeRu("Португальский", LanguageType.pt);
    public static final LanguageTypeLocal pua = new LanguageTypeRu("Пурепеча", LanguageType.pua);

    public static final LanguageTypeLocal qu = new LanguageTypeRu("Кечуа", LanguageType.qu);
    public static final LanguageTypeLocal quc = new LanguageTypeRu("Киче", LanguageType.quc);
    public static final LanguageTypeLocal qya = new LanguageTypeRu("Квэнья", LanguageType.qya);

    public static final LanguageTypeLocal rap = new LanguageTypeRu("Рапануйский", LanguageType.rap);
    public static final LanguageTypeLocal rar = new LanguageTypeRu("Кукский", LanguageType.rar);
    public static final LanguageTypeLocal rge = new LanguageTypeRu("Цыгано-греческий", LanguageType.rge);
    public static final LanguageTypeLocal rm = new LanguageTypeRu("Романшский", LanguageType.rm);
    public static final LanguageTypeLocal rmi = new LanguageTypeRu("Ломаврен", LanguageType.rmi);
    public static final LanguageTypeLocal rmq = new LanguageTypeRu("Кало", LanguageType.rmq);
    public static final LanguageTypeLocal rmu = new LanguageTypeRu("Скандинавско-цыганский язык", LanguageType.rmu);
    // rn run public static final LanguageTypeLocal rn = new LanguageTypeRu("Рунди", LanguageType.rn);
    public static final LanguageTypeLocal ro = new LanguageTypeRu("Румынский", LanguageType.ro);
    public static final LanguageTypeLocal roa_gal = new LanguageTypeRu("Галло", LanguageType.roa_gal);
    public static final LanguageTypeLocal roa_nor = new LanguageTypeRu("Нормандский", LanguageType.roa_nor);
    public static final LanguageTypeLocal roa_tara = new LanguageTypeRu("Тарантинский", LanguageType.roa_tara);
    public static final LanguageTypeLocal roa_ptg = new LanguageTypeRu("Галисийско-португальский", LanguageType.roa_ptg);
    public static final LanguageTypeLocal rom = new LanguageTypeRu("Цыганский", LanguageType.rom);
    public static final LanguageTypeLocal romaji = new LanguageTypeRu("Ромадзи", LanguageType.romaji);
    public static final LanguageTypeLocal roo = new LanguageTypeRu("Ротокас", LanguageType.roo);
    public static final LanguageTypeLocal rtm = new LanguageTypeRu("Ротуманский", LanguageType.rtm);
    public static final LanguageTypeLocal ru = new LanguageTypeRu("Русский", LanguageType.ru);
    public static final LanguageTypeLocal ru_old = new LanguageTypeRu("Русский (дореформенная орфография)", LanguageType.ru_old);
    public static final LanguageTypeLocal rue = new LanguageTypeRu("Русинский", LanguageType.rue);
    public static final LanguageTypeLocal ruo = new LanguageTypeRu("Истрорумынский", LanguageType.ruo);
    public static final LanguageTypeLocal run = new LanguageTypeRu("Рунди", LanguageType.run);
    public static final LanguageTypeLocal rup = new LanguageTypeRu("Арумынский", LanguageType.rup);
    // ro ruq public static final LanguageTypeLocal ruq = new LanguageTypeRu("Мегленорумынский", LanguageType.ruq);
    public static final LanguageTypeLocal rut = new LanguageTypeRu("Рутульский", LanguageType.rut);
    public static final LanguageTypeLocal rw = new LanguageTypeRu("Руанда", LanguageType.rw);

    public static final LanguageTypeLocal sa = new LanguageTypeRu("Санскрит", LanguageType.sa);
    public static final LanguageTypeLocal sad = new LanguageTypeRu("Сандаве", LanguageType.sad);
    public static final LanguageTypeLocal sah = new LanguageTypeRu("Якутский", LanguageType.sah);
    public static final LanguageTypeLocal sat = new LanguageTypeRu("Сантали", LanguageType.sat);
    public static final LanguageTypeLocal scn = new LanguageTypeRu("Сицилийский", LanguageType.scn);
    public static final LanguageTypeLocal sco = new LanguageTypeRu("Шотландский", LanguageType.sco);
    public static final LanguageTypeLocal sd = new LanguageTypeRu("Синдхи", LanguageType.sd);
    // ku public static final LanguageTypeLocal sdh = new LanguageTypeRu("Южнокурдский", LanguageType.sdh);
    public static final LanguageTypeLocal se = new LanguageTypeRu("Северносаамский", LanguageType.se);
    public static final LanguageTypeLocal see = new LanguageTypeRu("Сенека", LanguageType.see);
    public static final LanguageTypeLocal sei = new LanguageTypeRu("Сери", LanguageType.sei);
    public static final LanguageTypeLocal sel = new LanguageTypeRu("Селькупский", LanguageType.sel);
    public static final LanguageTypeLocal sg = new LanguageTypeRu("Санго", LanguageType.sg);
    public static final LanguageTypeLocal sga = new LanguageTypeRu("Древнеирландский", LanguageType.sga);
    public static final LanguageTypeLocal sh = new LanguageTypeRu("Сербскохорватский", LanguageType.sh);
    public static final LanguageTypeLocal si = new LanguageTypeRu("Сингальский", LanguageType.si);
    public static final LanguageTypeLocal shp = new LanguageTypeRu("Шипибо", LanguageType.shp);
    public static final LanguageTypeLocal sjd = new LanguageTypeRu("Кильдинский саамский", LanguageType.sjd);
    public static final LanguageTypeLocal sjk = new LanguageTypeRu("Кеми-саамский", LanguageType.sjk);
    public static final LanguageTypeLocal sjn = new LanguageTypeRu("Синдарин", LanguageType.sjn);
    public static final LanguageTypeLocal sjt = new LanguageTypeRu("Йоканьгско-саамский", LanguageType.sjt);
    public static final LanguageTypeLocal sk = new LanguageTypeRu("Словацкий", LanguageType.sk);
    public static final LanguageTypeLocal sl = new LanguageTypeRu("Словенский", LanguageType.sl);
    public static final LanguageTypeLocal slovio = new LanguageTypeRu("Словио", LanguageType.slovio);
    public static final LanguageTypeLocal smn = new LanguageTypeRu("Инари-саамский", LanguageType.smn);
    public static final LanguageTypeLocal sma = new LanguageTypeRu("Южносаамский", LanguageType.sma);
    public static final LanguageTypeLocal smj = new LanguageTypeRu("Луле-саамский", LanguageType.smj);
    public static final LanguageTypeLocal smo = new LanguageTypeRu("Самоанский", LanguageType.smo);
    public static final LanguageTypeLocal sms = new LanguageTypeRu("Колтта-саамский", LanguageType.sms);
    public static final LanguageTypeLocal sn = new LanguageTypeRu("Шона", LanguageType.sn);
    public static final LanguageTypeLocal snk = new LanguageTypeRu("Сонинке", LanguageType.snk);
    public static final LanguageTypeLocal solresol = new LanguageTypeRu("Сольресоль", LanguageType.solresol);
    public static final LanguageTypeLocal so = new LanguageTypeRu("Сомалийский", LanguageType.so);
    public static final LanguageTypeLocal sob = new LanguageTypeRu("Собей", LanguageType.sob);
    public static final LanguageTypeLocal sog = new LanguageTypeRu("Согдийский", LanguageType.sog);
    public static final LanguageTypeLocal sov = new LanguageTypeRu("Сонсорол", LanguageType.sov);
    public static final LanguageTypeLocal sqi = new LanguageTypeRu("Албанский", LanguageType.sqi);
    public static final LanguageTypeLocal sqt = new LanguageTypeRu("Сокотрийский", LanguageType.sqt);
    public static final LanguageTypeLocal sr = new LanguageTypeRu("Сербский", LanguageType.sr);
    public static final LanguageTypeLocal srn = new LanguageTypeRu("Сранан-тонго", LanguageType.srn);
    public static final LanguageTypeLocal srr = new LanguageTypeRu("Серер", LanguageType.srr);
    public static final LanguageTypeLocal srs = new LanguageTypeRu("Сарси", LanguageType.srs);
    public static final LanguageTypeLocal ss = new LanguageTypeRu("Свати", LanguageType.ss);
    public static final LanguageTypeLocal st = new LanguageTypeRu("Сесото", LanguageType.st);
    public static final LanguageTypeLocal stq = new LanguageTypeRu("Восточнофризский", LanguageType.stq);
    public static final LanguageTypeLocal su = new LanguageTypeRu("Сунданский", LanguageType.su);
    public static final LanguageTypeLocal suk = new LanguageTypeRu("Сукума", LanguageType.suk);
    public static final LanguageTypeLocal sux = new LanguageTypeRu("Шумерский", LanguageType.sux);
    public static final LanguageTypeLocal sv = new LanguageTypeRu("Шведский", LanguageType.sv);
    public static final LanguageTypeLocal sva = new LanguageTypeRu("Сванский", LanguageType.sva);
    public static final LanguageTypeLocal sw = new LanguageTypeRu("Суахили", LanguageType.sw);
    public static final LanguageTypeLocal syc = new LanguageTypeRu("Сирийский", LanguageType.syc);
    public static final LanguageTypeLocal szl = new LanguageTypeRu("Силезский", LanguageType.szl);

    public static final LanguageTypeLocal ta = new LanguageTypeRu("Тамильский", LanguageType.ta);
    public static final LanguageTypeLocal tab = new LanguageTypeRu("Табасаранский", LanguageType.tab);
    public static final LanguageTypeLocal tay = new LanguageTypeRu("Атаяльский", LanguageType.tay);
    public static final LanguageTypeLocal tcy = new LanguageTypeRu("Тулу", LanguageType.tcy);
    public static final LanguageTypeLocal te = new LanguageTypeRu("Телугу", LanguageType.te);
    public static final LanguageTypeLocal tgk = new LanguageTypeRu("Таджикский", LanguageType.tgk);
    public static final LanguageTypeLocal tgl = new LanguageTypeRu("Тагaльский", LanguageType.tgl);
    public static final LanguageTypeLocal tgt = new LanguageTypeRu("Tagbanwa", LanguageType.tgt);
    public static final LanguageTypeLocal th = new LanguageTypeRu("Тайский", LanguageType.th);
    public static final LanguageTypeLocal tig = new LanguageTypeRu("Тигре", LanguageType.tig);
    public static final LanguageTypeLocal tir = new LanguageTypeRu("Тигринья", LanguageType.tir);
    public static final LanguageTypeLocal tiw = new LanguageTypeRu("Тиви", LanguageType.tiw);
    public static final LanguageTypeLocal tk = new LanguageTypeRu("Туркменский", LanguageType.tk);
    public static final LanguageTypeLocal tkl = new LanguageTypeRu("Токелау", LanguageType.tkl);
    public static final LanguageTypeLocal tkr = new LanguageTypeRu("Цахурский", LanguageType.tkr);
    public static final LanguageTypeLocal tlh = new LanguageTypeRu("Клингонский", LanguageType.tlh);
    public static final LanguageTypeLocal tli = new LanguageTypeRu("Тлингитский", LanguageType.tli);
    public static final LanguageTypeLocal tly = new LanguageTypeRu("Талышский", LanguageType.tly);
    public static final LanguageTypeLocal tnq = new LanguageTypeRu("Таино", LanguageType.tnq);
    public static final LanguageTypeLocal tokipona = new LanguageTypeRu("Токипона", LanguageType.tokipona);
    public static final LanguageTypeLocal to = new LanguageTypeRu("Тонганский", LanguageType.to);
    public static final LanguageTypeLocal tpi = new LanguageTypeRu("Ток-писин", LanguageType.tpi);
    public static final LanguageTypeLocal tr = new LanguageTypeRu("Турецкий", LanguageType.tr);
    public static final LanguageTypeLocal tru = new LanguageTypeRu("Туройо", LanguageType.tru);
    // el public static final LanguageTypeLocal tsd = new LanguageTypeRu("Цаконский", LanguageType.tsd);
    public static final LanguageTypeLocal tsi = new LanguageTypeRu("Цимшианский", LanguageType.tsi);
    public static final LanguageTypeLocal tsn = new LanguageTypeRu("Тсвана", LanguageType.tsn);
    public static final LanguageTypeLocal tso = new LanguageTypeRu("Тсонга", LanguageType.tso);
    public static final LanguageTypeLocal tt = new LanguageTypeRu("Татарский", LanguageType.tt);
    public static final LanguageTypeLocal tts = new LanguageTypeRu("Исанский", LanguageType.tts);
    public static final LanguageTypeLocal ttt = new LanguageTypeRu("Татский", LanguageType.ttt);
    public static final LanguageTypeLocal tum = new LanguageTypeRu("Тумбука", LanguageType.tum);
    public static final LanguageTypeLocal tvl = new LanguageTypeRu("Тувалу", LanguageType.tvl);
    // aka -> twi public static final LanguageTypeLocal twi = new LanguageTypeRu("Чви", LanguageType.twi);
    public static final LanguageTypeLocal ty = new LanguageTypeRu("Таитянский", LanguageType.ty);
    public static final LanguageTypeLocal tyv = new LanguageTypeRu("Тувинский", LanguageType.tyv);

    public static final LanguageTypeLocal uby = new LanguageTypeRu("Убыхский", LanguageType.uby);
    public static final LanguageTypeLocal udi = new LanguageTypeRu("Удинский", LanguageType.udi);
    public static final LanguageTypeLocal udm = new LanguageTypeRu("Удмуртский", LanguageType.udm);
    public static final LanguageTypeLocal ug = new LanguageTypeRu("Уйгурский", LanguageType.ug);
    public static final LanguageTypeLocal uga = new LanguageTypeRu("Угаритский", LanguageType.uga);
    public static final LanguageTypeLocal uk = new LanguageTypeRu("Украинский", LanguageType.uk);
    public static final LanguageTypeLocal ulc = new LanguageTypeRu("Ульчский", LanguageType.ulc);
    public static final LanguageTypeLocal ulk = new LanguageTypeRu("Мериам", LanguageType.ulk);
    public static final LanguageTypeLocal umb = new LanguageTypeRu("Умбунду", LanguageType.umb);
    public static final LanguageTypeLocal ur = new LanguageTypeRu("Урду", LanguageType.ur);
    public static final LanguageTypeLocal ute = new LanguageTypeRu("Юте", LanguageType.ute);
    public static final LanguageTypeLocal uz = new LanguageTypeRu("Узбекский", LanguageType.uz);

    public static final LanguageTypeLocal vai = new LanguageTypeRu("Ваи", LanguageType.vai);
    public static final LanguageTypeLocal ve = new LanguageTypeRu("Венда", LanguageType.ve);
    public static final LanguageTypeLocal vec = new LanguageTypeRu("Венетский", LanguageType.vec);
    public static final LanguageTypeLocal vep = new LanguageTypeRu("Вепсский", LanguageType.vep);
    public static final LanguageTypeLocal vi = new LanguageTypeRu("Вьетнамский", LanguageType.vi);
    public static final LanguageTypeLocal vls = new LanguageTypeRu("Фламандский", LanguageType.vls);
    public static final LanguageTypeLocal vmw = new LanguageTypeRu("Макуа", LanguageType.vmw);
    public static final LanguageTypeLocal vol = new LanguageTypeRu("Волапюк", LanguageType.vol);
    public static final LanguageTypeLocal vot = new LanguageTypeRu("Водский", LanguageType.vot);
    public static final LanguageTypeLocal vro = new LanguageTypeRu("Выруский", LanguageType.vro);

    public static final LanguageTypeLocal wa = new LanguageTypeRu("Валлонский", LanguageType.wa);
    public static final LanguageTypeLocal wam = new LanguageTypeRu("Массачусетский", LanguageType.wam);
    public static final LanguageTypeLocal war = new LanguageTypeRu("Варайский", LanguageType.war);
    public static final LanguageTypeLocal was = new LanguageTypeRu("Уошо", LanguageType.was);
    public static final LanguageTypeLocal wba = new LanguageTypeRu("Варао", LanguageType.wba);
    public static final LanguageTypeLocal wbp = new LanguageTypeRu("Вальбири", LanguageType.wbp);
    public static final LanguageTypeLocal wlm = new LanguageTypeRu("Средневаллийский", LanguageType.wlm);
    public static final LanguageTypeLocal wo = new LanguageTypeRu("Волоф", LanguageType.wo);
    public static final LanguageTypeLocal wuu = new LanguageTypeRu("У (китайский диалект)", LanguageType.wuu);
    public static final LanguageTypeLocal wya = new LanguageTypeRu("Гуронский", LanguageType.wya);
    public static final LanguageTypeLocal wym = new LanguageTypeRu("Вилямовский", LanguageType.wym);

    public static final LanguageTypeLocal xal = new LanguageTypeRu("Калмыцкий", LanguageType.xal);
    public static final LanguageTypeLocal xas = new LanguageTypeRu("Камасинский", LanguageType.xas);
    public static final LanguageTypeLocal xbc = new LanguageTypeRu("Бактрийский", LanguageType.xbc);
    public static final LanguageTypeLocal xbm = new LanguageTypeRu("Среднебретонский", LanguageType.xbm);
    public static final LanguageTypeLocal xce = new LanguageTypeRu("Кельтиберский", LanguageType.xce);
    public static final LanguageTypeLocal xcl = new LanguageTypeRu("Грабар", LanguageType.xcl);
    public static final LanguageTypeLocal xcr = new LanguageTypeRu("Карийский", LanguageType.xcr);
    public static final LanguageTypeLocal xdc = new LanguageTypeRu("Дакский", LanguageType.xdc);
    public static final LanguageTypeLocal xho = new LanguageTypeRu("Коса", LanguageType.xho);
    public static final LanguageTypeLocal xht = new LanguageTypeRu("Хаттский", LanguageType.xht);
    public static final LanguageTypeLocal xlc = new LanguageTypeRu("Ликийский", LanguageType.xlc);
    public static final LanguageTypeLocal xld = new LanguageTypeRu("Лидийский", LanguageType.xld);
    public static final LanguageTypeLocal xlu = new LanguageTypeRu("Лувийский", LanguageType.xlu);
    public static final LanguageTypeLocal xls = new LanguageTypeRu("Лузитанский", LanguageType.xls);
    public static final LanguageTypeLocal xmf = new LanguageTypeRu("Мегрельский", LanguageType.xmf);
    public static final LanguageTypeLocal xmk = new LanguageTypeRu("Древнемакедонский", LanguageType.xmk);
    public static final LanguageTypeLocal hnd = new LanguageTypeRu("Хиндко", LanguageType.hnd);
    public static final LanguageTypeLocal xno = new LanguageTypeRu("Англо-нормандский", LanguageType.xno);
    public static final LanguageTypeLocal xpg = new LanguageTypeRu("Фригийский", LanguageType.xpg);
    public static final LanguageTypeLocal xpm = new LanguageTypeRu("Пумпокольский", LanguageType.xpm);
    public static final LanguageTypeLocal xpr = new LanguageTypeRu("Парфянский", LanguageType.xpr);
    public static final LanguageTypeLocal xrn = new LanguageTypeRu("Аринский", LanguageType.xrn);
    public static final LanguageTypeLocal xsc = new LanguageTypeRu("Скифо-сарматский", LanguageType.xsc);
    public static final LanguageTypeLocal xsr = new LanguageTypeRu("Шерпский", LanguageType.xsr);
    public static final LanguageTypeLocal xss = new LanguageTypeRu("Ассанский", LanguageType.xss);
    public static final LanguageTypeLocal xtg = new LanguageTypeRu("Галльский", LanguageType.xtg);
    public static final LanguageTypeLocal xto = new LanguageTypeRu("Тохарский", LanguageType.xto);
    public static final LanguageTypeLocal xum = new LanguageTypeRu("Умбрский", LanguageType.xum);
    public static final LanguageTypeLocal xve = new LanguageTypeRu("Венетский (древний)", LanguageType.xve);
    public static final LanguageTypeLocal xvn = new LanguageTypeRu("Вандальский", LanguageType.xvn);

    public static final LanguageTypeLocal yai = new LanguageTypeRu("Ягнобский", LanguageType.yai);
    public static final LanguageTypeLocal yej = new LanguageTypeRu("Еврейско-греческий", LanguageType.yej);
    public static final LanguageTypeLocal yi = new LanguageTypeRu("Идиш", LanguageType.yi);
    public static final LanguageTypeLocal ykg = new LanguageTypeRu("Северноюкагирский", LanguageType.ykg);
    public static final LanguageTypeLocal ynk = new LanguageTypeRu("Науканский", LanguageType.ynk);
    public static final LanguageTypeLocal yo = new LanguageTypeRu("Йоруба", LanguageType.yo);
    public static final LanguageTypeLocal yrk = new LanguageTypeRu("Ненецкий", LanguageType.yrk);
    public static final LanguageTypeLocal yua = new LanguageTypeRu("Юкатекский", LanguageType.yua);
    public static final LanguageTypeLocal yur = new LanguageTypeRu("Юрок", LanguageType.yur);
    public static final LanguageTypeLocal yux = new LanguageTypeRu("Южноюкагирский", LanguageType.yux);

    public static final LanguageTypeLocal za = new LanguageTypeRu("Чжуанский", LanguageType.za);
    public static final LanguageTypeLocal ze = new LanguageTypeRu("Генуэзский диалект лигурского языка", LanguageType.ze);
    public static final LanguageTypeLocal zen = new LanguageTypeRu("Зенага", LanguageType.zen);
    public static final LanguageTypeLocal zh = new LanguageTypeRu("Китайский", LanguageType.zh);
    public static final LanguageTypeLocal zh_cn = new LanguageTypeRu("Китайский (упрощ.)", LanguageType.zh_cn);
    public static final LanguageTypeLocal zh_tw = new LanguageTypeRu("Китайский (традиц.)", LanguageType.zh_tw);
    public static final LanguageTypeLocal zko = new LanguageTypeRu("Коттский", LanguageType.zko);
    public static final LanguageTypeLocal zkt = new LanguageTypeRu("Киданьский", LanguageType.zkt);
    public static final LanguageTypeLocal zkz = new LanguageTypeRu("Хазарский", LanguageType.zkz);
    public static final LanguageTypeLocal zun = new LanguageTypeRu("Зуни", LanguageType.zun);
    public static final LanguageTypeLocal zza = new LanguageTypeRu("Зазаки", LanguageType.zza);
}