# Introduction #

This guide helps you convert the database of Wiktionary into the machine-readable format. English Wiktionary and Russian Wiktionary are supported now.

# A Step-by-Step Guide #

A step-by-step guide to launching your Wiktionary parser:
  * Download the latest [Wiktionary dump](http://download.wikimedia.org/backup-index.html) (...-pages-articles.xml.bz2, ...-pagelinks.sql.gz and ...-categorylinks.sql.gz)
  * [Import Wiktionary database into local MySQL database](MySQL_import.md)
  * [Load empty Wiktionary parsed database into MySQL](File_wikt_parsed_empty_sql.md)
  * [Setup NetBeans environment for parsing](SetupNetBeansForParsing.md)
  * [Convert the Wiktionary parsed database (MySQL) into SQLite file](SQLite.md)
  * [Create wiwordik-XX.jar and wiwordik-XX.jnlp files](JNLPandJavaWebStart.md)
  * ...

# See also #
  * [SQLExamples](SQLExamples.md)
  * [Database\_statistics](Database_statistics.md)
  * [SQLWiktParsedPhantasmagoria](SQLWiktParsedPhantasmagoria.md)
  * [Wordlist\_for\_each\_language\_\_\_Database\_tables\_e\_g\_\_index\_en](Wordlist_for_each_language___Database_tables_e_g__index_en.md)