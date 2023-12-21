<blockquote>
<p>Language is a city to the building of which every human being brought a stone.</p>
<blockquote><p>Ralph Waldo Emerson</p></blockquote>
</blockquote>

# Wikokit - Machine-readable Wiktionary

Stone I. Parser <b>wikokit</b>. This program parses Wiktionaries, constructs and fills machine-readable Wiktionaries.

Stone II. PHP API ([piwidict](https://github.com/componavt/piwidict) project) to work with machine-readable Wiktionary.

<!--Stone III. Dictionary kiwidict. A visual interface to the parsed English Wiktionary and Russian Wiktionary databases.
-->
The goal of this project is to extract [semi-structured information](http://en.wikipedia.org/wiki/Semi-structured_data) from Wiktionary and construct [machine-readable dictionary](http://en.wikipedia.org/wiki/Machine-readable_dictionary) (database + [API](http://en.wikipedia.org/wiki/API) + [GUI](http://en.wikipedia.org/wiki/GUI)).

**Download** new Wiktionary parsed databases from Academic Torrents:
  * Russian Wiktionary [parsed ruwikt20230901](https://academictorrents.com/details/df5f4f51a50d6ff24f5ee748a7290ae3c490eaac);
  * English Wiktionary [parsed enwikt20231001](https://academictorrents.com/details/d0c67ff5bdba2ca1b1f20ec756d554bc85142537).

 Archives of Wiktionary parsed databases are available at [whinger.krc.karelia.ru/soft/wikokit](http://whinger.krc.karelia.ru/soft/wikokit/index.html).

<!--### Stone III: Dictionary kiwidict - Android applications

  * [kiwidict](https://play.google.com/store/apps/details?id=wikokit.kiwidict.enwikt) offline multilingual dictionary and thesaurus based on the English Wiktionary.
  * [kiwidict-ru](https://play.google.com/store/apps/details?id=wikokit.kiwidict) offline multilingual dictionary and thesaurus based on the Russian Wiktionary.
  * [magnetowordik](https://market.android.com/details?id=wordik.magneto) word game based on data extracted from the English Wiktionary.

Graphical user interface (kiwidict and kiwidict-ru) supports (see [release_notes.txt](https://github.com/componavt/wikokit/blob/master/wiwordik/release_notes.txt)):
  * words filtering by language code (e.g. de, fr)
  * wildcard characters: the percent sign (%) matches zero or more characters, and underscore (_) a single character;
  * todo: list of words only with meanings and / or semantic relations (use checkboxes).

After installation you can find the parsed Wiktionary database in SQLite format on your phone in the folder `SD card/kiwidict/`.
-->
### Stone I: Parser and dictionary description

I) The maximum goal (in distant future) is to extract all information (i.e. [all sections of entry](http://en.wiktionary.org/wiki/Wiktionary:ELE)) from all wiktionaries and convert data to machine-readable format.

II) Today's result. Now machine-readable Wiktionary contains the following information extracted from Russian Wiktionary and English Wiktionary:
  1. word's language and part of speech;
  2. meanings / definitions;
  3. semantic relations;
  4. translations;
  5. (^) context labels (from definitions);
  6. (^) quotations (text + bibliographic data).

(^) Context labels and quotations were extracted only from Russian Wiktionary.


### Parsed Wiktionary database schema

The structure (tables and relations) of the Wiktionary parsed database (database layout, see the file [wikt_parsed_empty_with_foreign_keys.png](https://raw.githubusercontent.com/componavt/wikokit/master/wikt_parser/doc/screenshots/wikt_parsed_empty_with_foreign_keys.png)):

![Wiktionary parsed database](https://raw.githubusercontent.com/componavt/wikokit/master/wikt_parser/doc/screenshots/wikt_parsed_empty_with_foreign_keys.png)

Set of tables related to quotations (fragment of the Wiktionary parsed database):

![quotations tables of the Wiktionary parsed database](https://raw.githubusercontent.com/componavt/wikokit/master/wiki/wiwordik.attach/db_scheme/quote_tables.png)


### State of the art and Future work 

Machine-readable Wiktionary framework:
![Machine-readable Wiktionary framework](https://raw.githubusercontent.com/componavt/wikokit/master/wiki/wiwordik.attach/db_scheme/MRDW_framework_external_applications.png)

I am interested that all two hundred Wiktionaries were parsed by this parser. But I know only Russian and English :)

If you are developer and if you are interested in adding modules to parse "your Wiktionary", then 
  * start from the paper describing the database (tables and relations) of machine-readable Wiktionary: [Transformation of Wiktionary entry structure into tables and relations in a relational database schema](http://arxiv.org/abs/1011.1368). 2010. But there are new tables (absent in the publication) related to _quotations_ and _context labels_, see [Machine-readable database schema](https://github.com/componavt/wikokit/blob/wiki/File_wikt_parsed_empty_sql.md#machine-readable-database-schema);
  * [GettingStartedWiktionaryParser](https://github.com/componavt/wikokit/blob/wiki/GettingStartedWiktionaryParser.md) &mdash; install parser and try to parse English Wiktionary and Russian Wiktionary;
  * Play with parsed English or Russian Wiktionary SQL — download dumps of Wiktionary parsed databases from Academic Torrents;
  * [OneMoreWiktionary](https://github.com/componavt/wikokit/blob/wiki/OneMoreWiktionary.md) &mdash; extend parser in order to extract invaluable information from your Wiktionary.

### Statistics ###

The machine-readable dictionary database statistics:
  * English Wiktionary: [total](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Parameters_of_the_database_created_by_the_Wiktionary_parser), [semantic relations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Semantic_relations), [translations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Translations), [part of speech](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:POS)
  * Russian Wiktionary: [total](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A0%D0%B0%D0%B7%D0%BC%D0%B5%D1%80%D1%8B_%D0%B1%D0%B0%D0%B7%D1%8B_%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85,_%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%BD%D0%BE%D0%B9_%D0%BF%D0%B0%D1%80%D1%81%D0%B5%D1%80%D0%BE%D0%BC_%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8F), [semantic relations](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A1%D0%B5%D0%BC%D0%B0%D0%BD%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5_%D0%BE%D1%82%D0%BD%D0%BE%D1%88%D0%B5%D0%BD%D0%B8%D1%8F), [translations](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%9F%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4%D1%8B), [part of speech](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:POS), [context labels](https://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%9F%D0%BE%D0%BC%D0%B5%D1%82%D1%8B), quote ([languages & sources](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B), [authors with clusters](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B0%D0%B2%D1%82%D0%BE%D1%80%D1%8B%29), [other authors](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B0%D0%B2%D1%82%D0%BE%D1%80%D1%8B,_%D0%B1%D0%B5%D0%B7_%D0%BA%D0%BB%D0%B0%D1%81%D1%82%D0%B5%D1%80%D0%BE%D0%B2%29), [years](http://ru.wiktionary.org/wiki/%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:AKA_MBG/%D0%A1%D1%82%D0%B0%D1%82%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0:%D0%A6%D0%B8%D1%82%D0%B0%D1%82%D1%8B_%28%D0%B4%D0%B0%D1%82%D0%B0%29))

## Project structure ##

Wiki tool kit (wikokit) contains several projects related to wiki

./common_wiki — common (low-level) functions to handle data of Wikipedia and Wiktionary in MySQL database,

./common_wiki_jdbc — functions to handle data of Wiktionary in MySQL and SQLite databases (JDBC, Java SE) (depends on common_wiki.jar).

./android/common_wiki_alink — Eclipse copy (source link) of ./common_wiki (!NetBeans)

./android/common_wiki_android — functions for access to Wiktionary in Android SQLite version of database (depends on common_wiki.jar).

./android/magnetowordik — Android word game (Wiktionary thesaurus).

./hits_wiki — API for access to Wikipedia in MySQL database, algorithms to search synonyms in Wikipedia (depends on jcfd.jar, common_wiki.jar).

./TGWikiBrowser — visual browser to search for synonyms in local or remote Wikipedia (depends on hits_wiki.jar and common_wiki.jar)

./wikidf — Wiki Index Database (list of lemmas and links to wiki pages, which contain these lemmas).

./[wikt_parser](https://github.com/componavt/wikokit/blob/wiki/GettingStartedWiktionaryParser.md) — Wiktionary parser creates a MySQL database (like [WordNet](http://en.wikipedia.org/wiki/WordNet)) from an Wiktionary MySQL dump file. The project goal is to convert Wiktionary articles to machine-readable format. (It depends on common_wiki, common_wiki_jdbc)

./wiwordik — Visualization of parsed Wiktionary database. wiki + word = wiwordik.

The code of previous project [Synarcher](http://synarcher.sourceforge.net/) are used in wikokit.

## Further reading ##
### In English ###
  * A. Krizhanovsky, A. Smirnov. [An approach to automated construction of a general-purpose lexical ontology based on Wiktionary](https://link.springer.com/content/pdf/10.1134%2FS1064230713020068.pdf) // Journal of Computer and Systems Sciences International, 2013, Vol. 52, No. 2, pp. 215–225.
  * A. Smirnov, T. Levashova, A. Karpov, I. Kipyatkova, A. Ronzhin, A. Krizhanovsky, N. Krizhanovsky. [Analysis of the quotation corpus of the Russian Wiktionary](http://www.rcs.cic.ipn.mx/rcs/2012_56/RCS_56_2012.pdf#page=101) // Research in Computing Science, Vol. 56, pp. 101-112, 2012.
  * A. Krizhanovsky. [A quantitative analysis of the English lexicon in Wiktionaries and WordNet](http://mathem.krc.karelia.ru/publ.php?id=11611&plang=e) // International Journal of Intelligent Information Technologies (IJIIT), October-December 2012, Vol. 8, No. 4, pp. 13-22.
  * F. Lin, A. Krizhanovsky. [Multilingual ontology matching based on Wiktionary data accessible via SPARQL endpoint](http://arxiv.org/abs/1109.0732) // In: Proceedings of the 13th Russian Conference on Digital Libraries RCDL’2011. October 19-22, Voronezh, Russia. – pp. 19-26. [link2](http://ceur-ws.org/Vol-803/paper1.pdf)
  * A. A. Krizhanovsky. [Transformation of Wiktionary entry structure into tables and relations in a relational database schema.](http://arxiv.org/abs/1011.1368) Preprint. 2010.
  * A. A. Krizhanovsky. [The comparison of Wiktionary thesauri transformed into the machine-readable format.](http://arxiv.org/abs/1006.5040) Preprint. 2010.
  * A. A. Krizhanovsky, F. Lin. [Related terms search based on WordNet / Wiktionary and its application in Ontology Matching](http://arxiv.org/abs/0907.2209) // In: Proceedings of the 11th Russian Conference on Digital Libraries RCDL’2009. September 17-21, Petrozavodsk, Russia. – pp. 363-369.

### In Russian ###
  * Крижановский А.А., Смирнов А.В., Круглов В.М., Крижановская Н.Б., Кипяткова И.С. [Автоматическое извлечение словарных помет из Русского Викисловаря](http://mathem.krc.karelia.ru/publ.php?id=12043&plang=r) //  Труды СПИИРАН. 2014. Вып. 2(33). С. 164-185.
  * Крижановский А.А., Смирнов А.В. [Подход к автоматизированному построению общецелевой лексической онтологии на основе данных викисловаря](http://mathem.krc.karelia.ru/publ.php?id=11217&plang=r) // Известия РАН. Теория и системы управления. N2, 2013, С. 53-63.
  * Крижановский А. А., Луговая Н. Б., Круглов В. М. [Извлечение и анализ дат произведений в корпусе цитат онлайн-словаря](http://textualheritage.org/content/view/398/188/lang,russian/) // Информационные технологии и письменное наследие: материалы VI междунар. науч. конф. El'Manuscript-12 (Петрозаводск, 3-8 сентября 2012) / отв. ред. В.А.Баранов, А.Г.Варфоломеев. – Петрозаводск; Ижевск, 2012. – 328 с. – C. 137—142. ISBN 978-5-8021-1402-5. ([PDF](http://textualheritage.org/component/option,com_docman/task,doc_download/gid,290/Itemid,/lang,russian/))
  * Смирнов А.В., Круглов В.М., Крижановский А.А., Луговая Н.Б., Карпов А.А., Кипяткова И.С. [Количественный анализ лексики русского WordNet и викисловарей](http://mathem.krc.karelia.ru/publ.php?id=10626&plang=r) // Труды СПИИРАН. 2012. Вып. 23. С. 231–253.
  * Крижановский А. [Количественный анализ лексики английского языка в викисловарях и Wordnet](http://mathem.krc.karelia.ru/publ.php?id=10009&plang=r) // Труды СПИИРАН. 2011. Вып. 19. С. 87–101.
  * Крижановский А. [Оценка использования корпусов и электронных библиотек в Русском Викисловаре](http://mathem.krc.karelia.ru/publ.php?id=13359&plang=r) // Труды международной конференции «Корпусная лингвистика–2011». – СПб.: С.-Петербургский гос. университет, Филологический факультет, 2011, 348 с. – C. 217—222. ISBN 978-5-8465-0005-5.
  * Крижановский А. [Преобразование структуры словарной статьи Викисловаря в таблицы и отношения реляционной базы данных.](http://mathem.krc.karelia.ru/publ.php?id=18513&plang=r) Препринт. 2010.
  * Крижановский А. [ Сравнение тезаурусов Русского и Английского Викисловарей, преобразованных в машиночитаемый формат.](http://mathem.krc.karelia.ru/publ.php?id=18512&plang=r) Препринт. 2010.
  * Крижановский А. [Машинная обработка Русского Викисловаря](https://w.wiki/4S7) // Вики-конференция 2009. 24—25 октября, Санкт-Петербург.

## See also ##
  * [Java Wiktionary Library (JWKTL)](https://dkpro.github.io/dkpro-jwktl/)
  * [perl-wiktionary-parser](https://github.com/clbecker/perl-wiktionary-parser/) // github.com, Perl module
  * [wiktionary_parser](https://github.com/benreynwar/wiktionary-parser/tree/master/wiktionary_parser) // github.com, Perl module
  * [Dbnary](http://kaiko.getalp.org/about-dbnary/)

## License ##

This program is multi-licensed and may be used under the terms of any of the following licenses:
  * EPL, Eclipse Public License V1.0 or later, http://www.eclipse.org/legal
  * LGPL, GNU Lesser General Public License V3.0 or later, http://www.gnu.org/licenses/lgpl.html
  * GPL, GNU General Public License V3.0 or later, http://www.gnu.org/licenses/gpl.html
  * AL, Apache License, V2.0 or later, http://www.apache.org/licenses
  * BSD, New BSD License, http://www.opensource.org/licenses/bsd-license

See [documentation](https://github.com/componavt/wikokit/wiki).
