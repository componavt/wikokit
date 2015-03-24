

# wikt\_mean\_semrel database #

The database with meaning and semantic relations based on the Wiktionary parsed database (filled by data from the Wiktionary), see GettingStartedWiktionaryParser.

Now this database is used in the Android word game [magnetowordik](https://market.android.com/details?id=wordik.magneto).

# Load empty wikt\_mean\_semrel into MySQL #

## Requirements ##

You have to have parsed Wiktionary database (_wikt\_parsed_) loaded into MySQL. The wikt\_parsed database will be used as a source of data to create _wikt\_mean\_semrel_ database, which is described below.

## MySQL ##

```
mysql$ CREATE DATABASE enwikt20110618_mean_semrel;
mysql$ USE enwikt20110618_mean_semrel
mysql$ SOURCE D:\all\projects\java\synonyms\wikokit\wikt_parser\doc\parsed\mean_semrel\mean_semrel_empty.sql

mysql$ GRANT SELECT ON enwikt20110618_parsed.* TO javawiki@'%';
mysql$ GRANT ALL ON enwikt20110618_mean_semrel.* TO javawiki@'%';
mysql$ FLUSH PRIVILEGES;
```

If MySQL whines and prints error message that access is denied then you can try use "localhost" instead of "%":
```
mysql$ GRANT SELECT ON enwikt20110618_parsed.* TO javawiki@'localhost';
```

## Database schema ##

The structure (tables and relations) of the _mean\_semrel_ database.

![http://wikokit.googlecode.com/svn/trunk/wikt_parser/doc/screenshots/mean_semrel.png](http://wikokit.googlecode.com/svn/trunk/wikt_parser/doc/screenshots/mean_semrel.png)

Remark: It will be automatically added tables **mean\_semrel\_XX** (the same as _mean\_semrel\_en_) for each language code XX, if there are Wiktionary entries with _Semantic relations_ for that languages.

### Tables lang and lang ###

There are almost the same tables (see figure below), which contains list of languages and language codes:
  * _wikt\_parsed.lang_ corresponds to Java class _wikt.sql.TLang_
  * _wikt\_mean\_semrel.lang_ - class _wiktparsed.mean\_semrel.parser.sql.MSRLang_

![http://wikokit.googlecode.com/svn/trunk/wikt_parser/doc/screenshots/mean_semrel/lang_wikt_parsed__wikt_mean_semrel.png](http://wikokit.googlecode.com/svn/trunk/wikt_parser/doc/screenshots/mean_semrel/lang_wikt_parsed__wikt_mean_semrel.png)

# Setup NetBeans for parsing #

Setup NetBeans environment for parsing, run parser.

## Connect.java ##

Open file `wikipedia.sql.Connect.java`.

Add lines:
```
// English Wiktionary parsed database
public final static String ENWIKT_PARSED_DB = "enwikt20110618_parsed?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";

// Meaning + semantic relations database based on English Wiktionary parsed database
public final static String ENWIKT_MEAN_SEMREL = "enwikt20110618_mean_semrel?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
```

## wikt\_parser ##

  * Set **wikt\_parser** as a main project in NetBeans.
  * Compile it.
  * Open project properties / Run. Set:
    * Main class: **wiktparsed.mean\_semrel.parser.Main**
    * Arguments: `en 0 "&/" 1000`
      * language\_code - language code of MySQL Wiktionary database to be parsed
      * n\_start\_from - number of records in database to start from
      * delimiter - symbol between words in the table fields "synonyms", "antonyms", etc.
      * min\_meaning - minimum number of records in mean\_semrel\_XX, the lesser tables (mean\_semrel\_XX) and records (lang.XX) will be deleted
    * VM options: `-Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC -verbose:gc`
  * Run main project.

# Run parser #

Create directory for the parser, copy project files, run .bat file:
```
$mkdir /enwikt20110618_mean_semrel/mean_semrel_parser

parser/run_mean_semrel_parser.bat
parser/dist/wikt_parser.jar
parser/dist/lib/common_wiki.jar
parser/dist/lib/mysql-connector-java-5.1.17-bin.jar

mysql$ DROP DATABASE enwikt20110618_mean_semrel;
mysql$ CREATE DATABASE enwikt20110618_mean_semrel;
mysql$ USE enwikt20110618_mean_semrel
mysql$ SOURCE D:\all\projects\java\synonyms\wikokit\wikt_parser\doc\parsed\mean_semrel\mean_semrel_empty.sql
```

Read twice very attentively parameters of your MySQL config file (tune your server to make it as fast as possible):
```
NET STOP MySQL
gvim "C:\w\MySQL\MySQL Server 5.1\my.ini"
NET START MySQL
```

OK. Run it:
```
$cd /enwikt20110618_mean_semrel/parser
$run_mean_semrel_parser.bat en 0 "&/" 1000
```

### mysqldump ###

The result database is ready. Let's dump it into file (just in case):

  * mysqldump --opt -u root -p --compatible=ansi --default-character-set=binary enwikt20110618\_mean\_semrel > enwikt20110618\_mean\_semrel.sql

Turn off extended-insert with single line statements for each record which then gets processed flawlessly by the database systems:

  * mysqldump --opt -u root -p --compatible=ansi --default-character-set=binary --max\_allowed\_packet=1M --skip-extended-insert ruwikt20130815\_parsed > ruwikt20130815\_parsed.sql

### Usefull SQL commands ###

Check the result database by SQL commands:
```
mysql$ SHOW TABLES;
mysql$ SELECT * FROM lang ORDER BY n_meaning;
mysql$ SELECT * FROM mean_semrel_en LIMIT 33;
mysql$ SELECT * FROM mean_semrel_ne ORDER BY n_sem_rel DESC LIMIT 7; % top words with maximum semantic relations
```

# Conversion into SQLite file #

Let's convert the wikt\_mean\_semrel  (MySQL) database into SQLite file =

## Tables' structure preparations ##

Prepare only once the SQLite file with table structures of the the Wiktionary parsed database (automatic mode):
  * `cd ./wiwordik/mysql2sqlite/mean_semrel`
  * `mysqldump -u root -p --no-data --compatible=ansi --default-character-set=binary enwikt20110618_mean_semrel > mean_semrel_structure_source.sql`
  * `mysql2sqlite_mean_semrel.bat`

The result file with SQLite tables' structure is `mean_semrel_structure.sql`.

Or in manual mode (Vim regular expressions):
```
%s/COMMENT.*/,/g    // remove COMMENT
%s/unsigned\s*//g   // remove "unsigned"
%s/\n\?.*\PRIMARY KEY.*//g      // remove lines with text "PRIMARY KEY"
%s/AUTO_INCREMENT/PRIMARY KEY/g // replace AUTO_INCREMENT by PRIMARY KEY
%s/CHARACTER SET latin1 COLLATE latin1_bin\s*//g    // remove "CHAR... "
```

Update this file if the stucture of the wikt\_mean\_semrel Wiktionary parsed database is changed, if new language codes were added.

## Data conversion ##
Dump data of the the Wiktionary parsed database:
  * `mysqldump -u root -p --quick --no-create-info --skip-extended-insert --compatible=ansi --default-character-set=binary enwikt20110618_mean_semrel > enwikt20110618_mean_semrel_data.sql`

Vim regular expressions in order to change data of INSERT commands into SQLite format:
```
                                // remove two types of lines:
%s/UNLOCK TABLES;\n//g          // UNLOCK TABLES;
%s/LOCK TABLES \".\+\n//g       // LOCK TABLES "page" WRITE;
%s/\\'/''/g                     // replace \' by ''
%s/\([,(]\)'\\''\([,)]\)/\1"'"\2/g // replace '\'' by "'"
```

## Data and tables merging ##

Merge two files (with tables structure and data) into one:
  * `enwikt20110618_mean_semrel_data.sql += mean_semrel_structure.sql`

But... split `wikt_parsed_structure.sql` so, that:
  * `CREATE TABLE...` at the top of the file
  * `CREATE INDEX...` at the end of the file

## SQLite shell ##

Read the note "[Let's speed up SQLite](http://componavt.livejournal.com/3393.html)".

Run [SQLite](http://sqlite.org), load and backup the SQLite database:
```
sqlite3
sqlite$ .read "C:/w/bin/mean_semrel/enwikt20110618_mean_semrel.sql"
sqlite$ PRAGMA integrity_check;
sqlite$ VACUUM;
sqlite$ .backup "C:/w/bin/mean_semrel/enwikt20110618_mean_semrel.sqlite"
```

# Preparing the SQLite database for Android #

See article [Using your own SQLite database in Android applications](http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/).

```
sqlite$ .read "C:/w/bin/mean_semrel/enwikt20110618_mean_semrel.sql"
sqlite$ CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT 'en_US')
sqlite$ INSERT INTO "android_metadata" VALUES ('en_US')
sqlite$ .backup "C:/w/bin/mean_semrel/enwikt20110618_mean_semrel_a.sqlite"
```

## Outdated? ##

```
cp enwikt20110618_mean_semrel_a.sqlite wikokit\magnetowordik\assets\enwikt_mean_semrel.sqlite.mp3
```

The SQLite database was renamed to .mp3 file in order to avoid the file from being compressed by aapt. See [Dealing with Asset Compression in Android Apps](http://ponystyle.com/blog/2010/03/26/dealing-with-asset-compression-in-android-apps/) and [Android and fairly large SQLite datafiles](http://stackoverflow.com/questions/4709649/android-and-fairly-large-sqlite-datafiles).

## Split file (skip now, to add if user complaints that application failed) ##

**Bad news.** There are constraints at the file size in the Android folder _assets_. See [Load files bigger than 1M from assets folder](http://stackoverflow.com/questions/2860157/load-files-bigger-than-1m-from-assets-folder).

**Good news.** You can split file :)

  1. Split database file `enwikt_mean_semrel_sqlite` into small chunks (1048576 bytes) using [some splitter](http://www.spadixbd.com/freetools/jsplit.htm).
  1. Add suffix `.mp3` (extra extension) to chunk files using [Bulk Rename Utility](http://www.bulkrenameutility.co.uk/Download.php). This `.mp3` suffix (read [here](http://stackoverflow.com/questions/2860157/load-files-bigger-than-1m-from-assets-folder)) allows to keep your assets uncompressed (during installation), so Android system do not need to uncompress the entire thing to memory .
  1. Copy files `enwikt.1.mp3`, `enwikt.2.mp3`... to the folder `wikokit\magnetowordik\assets\`.


# See also #
  * [Load empty Wiktionary parsed database into MySQL](File_wikt_parsed_empty_sql.md)