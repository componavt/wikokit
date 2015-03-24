_Language is a city to the building of which every human being brought a stone._ /Ralph Waldo Emerson/

## Wikokit - Machine-readable Wiktionary ##

Stone I. Parser <b>wikokit</b>. This program parses Wiktionaries, constructs and fills machine-readable Wiktionaries.

Stone II. PHP API [piwidict](https://github.com/componavt/piwidict)

Stone III. Dictionary kiwidict. A visual interface to the parsed English Wiktionary and Russian Wiktionary databases.

The goal of this project is to extract [semi-structured information](http://en.wikipedia.org/wiki/Semi-structured_data) from Wiktionary and construct [machine-readable dictionary](http://en.wikipedia.org/wiki/Machine-readable_dictionary) (database + [API](http://en.wikipedia.org/wiki/API) + [GUI](http://en.wikipedia.org/wiki/GUI)).

<font color='red'>Attention: download new files from the page <a href='http://whinger.krc.karelia.ru/soft/wikokit/index.html'>http://whinger.krc.karelia.ru/soft/wikokit/index.html</a>. See "External links" (left panel).</font>

### Stone III: kiwidict - Android applications ###

  * [kiwidict](https://play.google.com/store/apps/details?id=wikokit.kiwidict.enwikt) offline multilingual dictionary and thesaurus based on the English Wiktionary.
  * [kiwidict-ru](https://play.google.com/store/apps/details?id=wikokit.kiwidict) offline multilingual dictionary and thesaurus based on the Russian Wiktionary.
  * [magnetowordik](https://market.android.com/details?id=wordik.magneto) word game based on data extracted from the English Wiktionary.

Graphical user interface (kiwidict and kiwidict-ru) supports (see [release\_notes.txt](http://wikokit.googlecode.com/svn/trunk/wiwordik/release_notes.txt)):
  * words filtering by language code (e.g. de, fr);
  * wildcard characters: the percent sign (%) matches zero or more characters, and underscore (`_`) a single character;
  * todo: list of words only with meanings and / or semantic relations (use checkboxes).

After installation you can find the parsed Wiktionary database in SQLite format on your phone in the folder `SD card/kiwidict/`.

### Stone I: Parser and dictionary description ###

I) The maximum goal (in distant future) is to extract all information (i.e. [all sections of entry](http://en.wiktionary.org/wiki/Wiktionary:ELE)) from all wiktionaries and convert data to machine-readable format.

II) Today's result. Now machine-readable Wiktionary contains the following information extracted from Russian Wiktionary and English Wiktionary:
  1. word's language and part of speech;
  1. meanings / definitions;
  1. semantic relations;
  1. translations;
  1. (^) quotations (text + bibliographic data).

(^) Quotations were extracted only from Russian Wiktionary.

Machine-readable Wiktionary framework:
![http://wikokit.googlecode.com/svn/trunk/wiki/wiwordik.attach/db_scheme/MRDW_framework_external_applications.png](http://wikokit.googlecode.com/svn/trunk/wiki/wiwordik.attach/db_scheme/MRDW_framework_external_applications.png)

I am interested that all two hundred Wiktionaries were parsed by this parser. But I know only Russian and English :)

If you are developer and if you are interested in adding modules to parse "your Wiktionary", then
  * start from the paper describing the database (tables and relations) of machine-readable Wiktionary: [Transformation of Wiktionary entry structure into tables and relations in a relational database schema.](http://arxiv.org/abs/1011.1368). 2010. But there are new tables (absent in the publication) related to _quotations_ and _context labels_, see [Machine-readable database schema](http://code.google.com/p/wikokit/wiki/File_wikt_parsed_empty_sql#Machine-readable_database_schema);
  * GettingStartedWiktionaryParser - install parser and try to parse English Wiktionary and Russian Wiktionary;
  * Play with parsed English or Russian Wiktionary SQL dump (see Donwload section);
  * OneMoreWiktionary - extend parser in order to extract invaluable information from your Wiktionary.

### Statistics ###

The machine-readable dictionary database statistics:
  * English Wiktionary: [total](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Parameters_of_the_database_created_by_the_Wiktionary_parser), [semantic relations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Semantic_relations), [translations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Translations), [part of speech](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:POS)
  * Russian Wiktionary: [total](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A0%D0%B0%D0%B7%D0%BC%D0%B5%D1%80%D1%8B_%D0%B1%D0%B0%D0%B7%D1%8B_%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85,_%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%BD%D0%BE%D0%B9_%D0%BF%D0%B0%D1%80%D1%81%D0%B5%D1%80%D0%BE%D0%BC_%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8F), [semantic relations](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A1%D0%B5%D0%BC%D0%B0%D0%BD%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5_%D0%BE%D1%82%D0%BD%D0%BE%D1%88%D0%B5%D0%BD%D0%B8%D1%8F), [translations](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%9F%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4%D1%8B), [part of speech](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:POS), [context labels](https://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%9F%D0%BE%D0%BC%D0%B5%D1%82%D1%8B), quote ([languages & sources](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B), [authors with clusters](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B0%D0%B2%D1%82%D0%BE%D1%80%D1%8B%29), [other authors](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B0%D0%B2%D1%82%D0%BE%D1%80%D1%8B,_%D0%B1%D0%B5%D0%B7_%D0%BA%D0%BB%D0%B0%D1%81%D1%82%D0%B5%D1%80%D0%BE%D0%B2%29), [years](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B4%D0%B0%D1%82%D0%B0%29))

## Project structure ##

Wiki tool kit (wikokit) contains several projects related to wiki

./common\_wiki - common (low-level) functions to handle data of Wikipedia and Wiktionary in MySQL database,

./common\_wiki\_jdbc - functions to handle data of Wiktionary in MySQL and SQLite databases (JDBC, Java SE) (depends on common\_wiki.jar).

./android/common\_wiki\_alink - Eclipse copy (source link) of ./common\_wiki (NetBeans)

./android/common\_wiki\_android - functions for access to Wiktionary in Android SQLite version of database (depends on common\_wiki.jar).

./android/[magnetowordik](https://market.android.com/details?id=wordik.magneto) - Android word game (Wiktionary thesaurus).

./hits\_wiki - API for access to Wikipedia in MySQL database, algorithms to search synonyms in Wikipedia (depends on jcfd.jar, common\_wiki.jar).

./TGWikiBrowser - visual browser to search for synonyms in local or remote Wikipedia (depends on hits\_wiki.jar and common\_wiki.jar)

./wikidf - Wiki Index Database (list of lemmas and links to wiki pages, which contain these lemmas).

./[wikt\_parser](GettingStartedWiktionaryParser.md) - Wiktionary parser creates a MySQL database (like [WordNet](http://en.wikipedia.org/wiki/WordNet)) from an Wiktionary MySQL dump file. The project goal is to convert Wiktionary articles to machine readable format. (It depends on common\_wiki, common\_wiki\_jdbc)

./wiwordik - Visualization of parsed Wiktionary database. wiki + word = wiwordik.

The code of previous project [Synarcher](http://synarcher.sourceforge.net/) are used in wikokit.

## Further reading ##
### In English ###
  * A. Krizhanovsky, A. Smirnov. [An approach to automated construction of a general-purpose lexical ontology based on Wiktionary](http://scipeople.com/publication/113533/) // Journal of Computer and Systems Sciences International, 2013, Vol. 52, No. 2, pp. 215–225.
  * A. Smirnov, T. Levashova, A. Karpov, I. Kipyatkova, A. Ronzhin, A. Krizhanovsky, N. Krizhanovsky. [Analysis of the quotation corpus of the Russian Wiktionary](http://www.micai.org/rcs/2012_56/Analysis%20of%20the%20Quotation%20Corpus%20of%20the%20Russian%20Wiktionary.html) // Research in Computing Science, Vol. 56, pp. 101-112, 2012.
  * A. Krizhanovsky. [A quantitative analysis of the English lexicon in Wiktionaries and WordNet](http://www.igi-global.com/article/quantitative-analysis-english-lexicon-wiktionaries/74827) // International Journal of Intelligent Information Technologies (IJIIT), October-December 2012, Vol. 8, No. 4, pp. 13-22. [preprint](http://scipeople.ru/publication/108159/)
  * F. Lin, A. Krizhanovsky. [Multilingual ontology matching based on Wiktionary data accessible via SPARQL endpoint](http://arxiv.org/abs/1109.0732) // In: Proceedings of the 13th Russian Conference on Digital Libraries RCDL’2011. October 19-22, Voronezh, Russia. – pp. 19-26. [link2](http://ceur-ws.org/Vol-803/paper1.pdf)
  * A. A. Krizhanovsky. [Transformation of Wiktionary entry structure into tables and relations in a relational database schema.](http://arxiv.org/abs/1011.1368) Preprint. 2010.
  * A. A. Krizhanovsky. [The comparison of Wiktionary thesauri transformed into the machine-readable format.](http://arxiv.org/abs/1006.5040) Preprint. 2010.
  * A. A. Krizhanovsky, F. Lin. [Related terms search based on WordNet / Wiktionary and its application in Ontology Matching](http://arxiv.org/abs/0907.2209) // In: Proceedings of the 11th Russian Conference on Digital Libraries RCDL’2009. September 17-21, Petrozavodsk, Russia. – pp. 363-369.

### In Russian ###
  * Крижановский А.А., Смирнов А.В., Круглов В.М., Крижановская Н.Б., Кипяткова И.С. [Автоматическое извлечение словарных помет из Русского Викисловаря](http://scipeople.com/publication/117259/) //  Труды СПИИРАН. 2014. Вып. 2(33). С. 164-185. ([PDF](http://cdn.scipeople.com/materials/97/context_labels_ruwikt_2014_KrizhanovskyAA_color.pdf)).
  * Крижановский А.А., Смирнов А.В. [Подход к автоматизированному построению общецелевой лексической онтологии на основе данных викисловаря](http://scipeople.com/publication/111654/) // Известия РАН. Теория и системы управления. N2, 2013, С. 53-63.
  * Крижановский А. А., Луговая Н. Б., Круглов В. М. [Извлечение и анализ дат произведений в корпусе цитат онлайн-словаря](http://textualheritage.org/content/view/398/188/lang,russian/) // Информационные технологии и письменное наследие: материалы VI междунар. науч. конф. El'Manuscript-12 (Петрозаводск, 3-8 сентября 2012) / отв. ред. В.А.Баранов, А.Г.Варфоломеев. – Петрозаводск; Ижевск, 2012. – 328 с. – C. 137—142. ISBN 978-5-8021-1402-5. ([PDF](http://textualheritage.org/component/option,com_docman/task,doc_download/gid,290/Itemid,/lang,russian/))
  * Смирнов А.В., Круглов В.М., Крижановский А.А., Луговая Н.Б., Карпов А.А., Кипяткова И.С. [Количественный анализ лексики русского WordNet и викисловарей](http://scipeople.com/publication/113406/) // Труды СПИИРАН. 2012. Вып. 23. С. 231–253. ([PDF](http://cdn.scipeople.com/materials/97/quant_WN_Wikt_ru_en_2012.pdf)).
  * Крижановский А. [Количественный анализ лексики английского языка в викисловарях и Wordnet](http://scipeople.com/publication/106012/) // Труды СПИИРАН. 2011. Вып. 19. С. 87–101. ([PDF](http://cdn.scipeople.com/materials/97/quant_English_2011.pdf)).
  * Крижановский А. [Оценка использования корпусов и электронных библиотек в Русском Викисловаре](http://scipeople.com/publication/102432/) // Труды международной конференции «Корпусная лингвистика–2011». – СПб.: С.-Петербургский гос. университет, Филологический факультет, 2011, 348 с. – C. 217—222. ISBN 978-5-8465-0005-5 ([PDF](http://cdn.scipeople.com/materials/97/wikt_ruscorpora_14_full.pdf), [слайды](http://scipeople.com/uploads/materials/97/slides_corpora_in_russian_wiktionary2011.pdf)).
  * Крижановский А. [Преобразование структуры словарной статьи Викисловаря в таблицы и отношения реляционной базы данных.](http://scipeople.com/publication/100231/) Препринт. 2010.
  * Крижановский А. [Сравнение тезаурусов Русского и Английского Викисловарей, преобразованных в машинно-читаемый формат.](http://scipeople.com/publication/99331/) Препринт. 2010.
  * Крижановский А. Построение машинно-читаемого словаря на основе Русского Викисловаря. [Презентация](http://scipeople.ru/uploads/materials/97/14_nlpseminar_aot_ruwikt_ru.pdf). [Видео](http://mathlingvo.ru/nlpseminar/archive/s_31) // Семинар: Natural Language Processing, 20 марта 2010, Санкт-Петербург.
  * Крижановский А. [Машинная обработка Русского Викисловаря](http://ru.wikipedia.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F:%D0%92%D0%B8%D0%BA%D0%B8-%D0%BA%D0%BE%D0%BD%D1%84%D0%B5%D1%80%D0%B5%D0%BD%D1%86%D0%B8%D1%8F_2009/%D0%9F%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0/%D0%94%D0%BE%D0%BA%D0%BB%D0%B0%D0%B4%D1%8B/%D0%9C%D0%B0%D1%88%D0%B8%D0%BD%D0%BD%D0%B0%D1%8F_%D0%BE%D0%B1%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B0_%D0%A0%D1%83%D1%81%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8F) // Вики-конференция 2009. 24—25 октября, Санкт-Петербург.

## See also ##
  * [Java Wiktionary Library (JWKTL)](http://www.ukp.tu-darmstadt.de/software/jwktl/) and [jwktl](http://code.google.com/p/jwktl) at code.google.com
  * [English Wiktionary parser in Java](http://personal.inet.fi/koti/korhoj/) (or [here](http://personal.inet.fi/koti/korhoj/source/wikt_cide_latest.7z)) by Joel Korhonen
  * [perl-wiktionary-parser](https://github.com/clbecker/perl-wiktionary-parser/) // github.com, Perl module
  * [wiktionary\_parser](https://github.com/benreynwar/wiktionary-parser/tree/master/wiktionary_parser) // github.com, Perl module
  * [wikily-supervised-pos-tagger](http://code.google.com/p/wikily-supervised-pos-tagger/) - part-of-speech taggers for multiple languages implemented by training Hidden Markov with Wiktionary (see paper: Shen Li; João Graça; Ben Taskar, [Wiki-ly Supervised Part-of-Speech Tagging](http://aclweb.org/anthology-new/D/D12/D12-1127.pdf), 2012, [bib](http://aclweb.org/anthology-new/D/D12/D12-1127.bib))
  * [DBpedia Wiktionary](http://dbpedia.org/Wiktionary)
  * [Dbnary](http://kaiko.getalp.org/about-dbnary/)
  * [Wiktionary RDF versions](http://wiki.okfn.org/Linguistics/llod/datasets/wiktionary)
  * [YARN](http://russianword.net/) (open WordNet-like thesaurus for Russian)

## License ##

This program is multi-licensed and may be used under the terms of any of the following licenses:
  * EPL, Eclipse Public License V1.0 or later, http://www.eclipse.org/legal
  * LGPL, GNU Lesser General Public License V3.0 or later, http://www.gnu.org/licenses/lgpl.html
  * GPL, GNU General Public License V3.0 or later, http://www.gnu.org/licenses/gpl.html
  * AL, Apache License, V2.0 or later, http://www.apache.org/licenses
  * BSD, New BSD License, http://www.opensource.org/licenses/bsd-license