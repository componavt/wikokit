# Introduction #

At this moment, there is more information about import here: [synarcher](http://synarcher.sourceforge.net).

# MediaWiki #

My beloved WAMP tutorial pages:
[How to manually install Apache, PHP and MySQL on Windows?](http://superuser.com/questions/748117/how-to-manually-install-apache-php-and-mysql-on-windows),
[Ultimate How To](http://fastec.altervista.org/Apache_PHP_MySQL.php),
[in Russian](http://www.softtime.ru/article/index.php?id_article=95).

Download [MediaWiki](http://www.mediawiki.org/wiki/MediaWiki). Unzip it, e.g. to:
```
D:\all\inetpub\mediawiki-1.16.0.enwikt.20100824 
```
Open file `httpd.conf`
```
gvim "C:\w\Apache Software Foundation\Apache2.2\conf\httpd.conf" 
```
Add the following text to `httpd.conf`:
```
<Directory "D:/all/inetpub">
    Require all granted
</Directory>

<VirtualHost *:80>
    DocumentRoot D:/all/inetpub/mediawiki-1.16.0.enwikt.20100824
    ServerName  enwikt
    ErrorLog D:/all/inetpub/logs/wikt_ru_error_log.txt
    CustomLog D:/all/inetpub/logs/wikt_ru_log.txt common
</VirtualHost>
```
  * Restart Apache.
  * Open in browser [http://localhost/mw-config/index.php](http://localhost/mw-config/index.php).
  * Set up the wiki:
    * select database name, e.g. `enwikt20100824`.
    * select storage engine: `InnoDB` <s><code>MyISAM</code></s>.
    * select database character set: `Binary`.

Add the line: `$wgCapitalLinks=false;` at the end of file `LocalSettings.php`.

# Xml2sql #
Fast upload dump file (e.g. `enwiktionary-20100824-pages-articles.xml`) to MySQL.

## Errors (Redirect, DiscussionThreading) ##

### Intro ###

There are problems with `"<redirect />"`, `"<DiscussionThreading>"`, `"<ns>"` and `"<sha1 />"` in the XML file.

The error message is:
```
./xml2sql unexpected element <redirect> ./xml2sql: parsing aborted at line 33 pos 16.
```

### Solution ###

Strip off the tags (Redirect and others) by using a perl script [`xml2sql\_helper.pl`](http://wikokit.googlecode.com/svn/trunk/wikt_parser/doc/xml2sql_helper/xml2sql_helper.pl) with the following format:
```
perl xml2sql_helper.pl in_file out_file
```
Where `in_file` is `enwiktionary-20100824-pages-articles.xml`.

## Xml2sql ##
Download [Xml2sql](http://meta.wikimedia.org/wiki/Xml2sql).
```
$ cd /var/lib/mediawiki-1.16.0.enwikt.20100824/
$ bzip2 -d enwiktionary-20100824-pages-articles.xml.bz2
$ xml2sql -m enwiktionary-20100824-pages-articles.xml
```
Xml2sql generates three files: page.sql, revision.sql and text.sql

## MySQL ##
```
$ mysql -u root -p
USE enwikt20100824
mysql$ SOURCE /temp/page.sql
mysql$ SOURCE /temp/text.sql
mysql$ SOURCE /temp/revision.sql 
```

### page.sql ###

If you got the error: `Column count doesn't match value count at row 1` in INSERT, then

Replace all (open `page.sql` in a text editor)
```
INSERT INTO `page`
```

by
```
INSERT INTO `page` (page_id, page_namespace, page_title, page_restrictions, page_counter, page_is_redirect, page_is_new, page_random, page_touched, page_latest, page_len) 
```

See [MediaWiki/Manual:page table](https://www.mediawiki.org/wiki/Manual:Page_table).

### revision.sql ###

#### new ####
If you got the error: `Column count doesn't match value count at row 1` in INSERT, then

Replace all (open `revision.sql` in a text editor)
```
INSERT INTO `revision`
```

by
```
INSERT INTO `revision` (rev_id, rev_page, rev_text_id, rev_comment, rev_user, rev_user_text, rev_timestamp, rev_minor_edit, rev_deleted)
```


#### old ####
If there are the errors (due to `SOURCE revision.sql`):
  * _"ERROR 1136 (21S01): Column count doesn't match value count at row 1"_,
  * _"ERROR 1406 (22001): Data too long for column 'rev\_comment'_,
then try the following commands (see [MediaWiki/Talk:Revision\_table](http://www.mediawiki.org/wiki/Talk:Revision_table#Problem%20with%20import%20wiki%20to%20MySQL)):

remove three fields (rev\_len, rev\_parent\_id, rev\_sha1), change rev\_comment from TINYBLOB to BLOB, load by _SOURCE_, and return (add) three fields back:
```
mysql$ ALTER TABLE revision DROP COLUMN rev_len, DROP COLUMN rev_parent_id, DROP COLUMN rev_sha1; 
mysql$ ALTER TABLE revision CHANGE COLUMN `rev_comment` `rev_comment` BLOB NOT NULL;
mysql$ SOURCE /temp/revision.sql

// restore back to square one
mysql$ ALTER TABLE revision ADD COLUMN rev_len INT(10) UNSIGNED DEFAULT NULL AFTER rev_deleted, ADD COLUMN rev_parent_id INT(10) UNSIGNED DEFAULT NULL AFTER rev_len;
mysql$ ALTER TABLE revision ADD COLUMN rev_sha1 VARBINARY(32) NULL AFTER rev_deleted;
```

### pagelinks and categorylinks ###

I do not recommend "SOURCE pagelinks.sql;SOURCE categorylinks.sql;" for very big Wikipedias, e.g. English Wikipedia, see description here [Synarcher](http://synarcher.sourceforge.net/#install_linux) (read text after "Tables pagelinks and categorylinks require special attention...").

The result database is ready. It could be used as a source for the Wiktionary parser. Let's dump it into file (just in case):

  * mysqldump --opt -u root -p --compatible=ansi --default-character-set=binary enwikt20100824 > enwikt20100824.sql

### Grant privilegies ###
Add the user (e.g. 'javawiki') to MySQL database. Grant privileges at database levels (e.g. 'ruwiki' database). Open MySQL command-line and run commands:
```
mysql>CREATE USER javawiki;
mysql>GRANT SELECT ON ruwiki.* TO javawiki@'%';
        (with password; from any computer>GRANT SELECT PRIVILEGES ON ruwiki.* TO javawiki identified by '12345')
mysql>FLUSH PRIVILEGES;
```

# Next step #
  * [Load empty Wiktionary parsed database into MySQL](File_wikt_parsed_empty_sql.md)

# See also #
  * [categorylinks](categorylinks.md)

## Links ##
  * [Talk:Xml2sql (meta)](http://meta.wikimedia.org/wiki/Talk:Xml2sql)
  * [How to solve problems with import of revision.sql](http://synarcher.sourceforge.net). Search the line: ALTER TABLE revision