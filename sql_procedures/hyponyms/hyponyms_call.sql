SELECT * FROM cat_count//

source /mnt/win_e/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms.sql//
source /mnt/win_e/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms_recur.sql//
source /mnt/win_e/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms_cycles.sql//
source /mnt/win_e/projects/java/synonyms/synarcher/sql_procedures/hyponyms/recalculate_ic.sql//

source /mnt/win_e/all/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms.sql//
source /mnt/win_e/all/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms_recur.sql//
source /mnt/win_e/all/projects/java/synonyms/synarcher/sql_procedures/hyponyms/hyponyms_cycles.sql//

SHOW VARIABLEs LIKE '%max_sp_recursion_depth%'//
SET max_sp_recursion_depth=255//

SHOW GLOBAL VARIABLEs LIKE '%thread_stack%'//
edit my.cnf:
thread_stack		= 1024K

DELETE FROM cat_count//
/** Fills cat_count by category titles */
INSERT IGNORE INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14)//
0.65 - 15 sec - simplewiki

/** test */
SELECT * FROM cat_count WHERE page_title='Articles'//
SELECT * FROM page WHERE page_title='Computer'//

/** Dump results */
mysqldump --opt -u root -p sw cat_count > simplewiki20070811_cat_count.sql
tar zcvf simplewiki20070811_cat_count.tar.gz simplewiki20070811_cat_count.sql

-- copy page_id, page_title FROM page TO cat_count --
INSERT INTO cat_count (page_id,page_title) VALUES (1,'sss');
INSERT INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14 LIMIT 5);

=== enwiki =======================
CALL hyponyms('Fundamental')// No, better is:

CALL hyponyms('Main_topic_classifications')//
244618 - categories


=== ruwiki =======================
CALL hyponyms('Всё')//

=== simplewiki ===================
/** Root category */
Category:Main_page - root of articles in Simple Wikipedia. It is not parent of additional categories, which have root category:Wikipedia
CALL hyponyms('Main_page')//

CALL hyponyms('Project')//
CALL hyponyms('Articles')//
CALL hyponyms('Science')//

CALL hyponyms('Prizes')//
CALL hyponyms('Monarchs_of_Armenia')//
CALL hyponyms('Kings_of_Urartu')//

CALL hyponyms('Science')//
CALL hyponyms('Computing')//
CALL hyponyms('Internet')//
CALL hyponyms('Websites')//
CALL hyponyms('Wikis')//
CALL hyponyms_recur('Wikis',0,100)//

