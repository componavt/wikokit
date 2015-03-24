There is a problem with import of categorylinks.sql

# Introduction #

There are differences between:

|MediaWiki table | categorylinks table (.sql file) |
|:---------------|:--------------------------------|
|cl\_from int(10) unsigned | `cl_from` int(8) unsigned NOT NULL default '0' |
|cl\_sortkey varbinary(70) | `cl_sortkey` varchar(255) binary |

# Solution #
mysql>
  1. ALTER TABLE `categorylinks` MODIFY COLUMN `cl_from` INT(8) UNSIGNED NOT NULL DEFAULT 0, MODIFY COLUMN `cl_sortkey` varbinary(255) NOT NULL;
  1. SOURCE ..\categorylinks.sql
  1. ALTER TABLE `categorylinks` MODIFY COLUMN `cl_from` INTEGER UNSIGNED NOT NULL DEFAULT 0, MODIFY COLUMN `cl_sortkey` varbinary(70) NOT NULL;

# Epilog #

This solution is interesting for you, only if the second step is failed.

# See also #
  * [How to solve problems with import of revision.sql](http://synarcher.sourceforge.net). Search the line: ALTER TABLE revision