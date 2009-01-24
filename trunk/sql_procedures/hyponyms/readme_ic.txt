
Instructions: how to calculate Resnik metric in WP via hyponyms.
        Hyponyms of the category are sub-categories and articles which belong 
        to these categories.

See also the file synarcher/kleinberg/src/wikipedia/experiment/Hyponyms.java


I Create and fill the table cat_count.

1.  Load Enligsh Wikipedia to MySQL (enwiki database)
2.  Create tables cat_count, cat_parent_stack, and cat_cycles in enwiki, 
        use commands from the file cat_count.sql
3a. Load calculated data to the table cat_count 
        mysql> SOURCE /path_to_synarcher/db/cat_count/enwiki20070527_cat_count_Main_topic_classifications.sql
or 
3b. Calculate and fill the table cat_count.
        Info: 'hyponyms' (MySQL stored procedure) calculates number of 
          hyponyms (sub-categories + articles), fills table cat_count.
        Side effect: it fills the table cat_cycles with list of categories 
          which forms cycles.           

3.1 Load four files with MySQL stored procedures to enwiki:
        mysql> SOURCE /path_to/hyponyms.sql//
        mysql> SOURCE /path_to/hyponyms_recur.sql//
        mysql> SOURCE /path_to/hyponyms_cycles.sql//
        mysql> SOURCE /path_to/recalculate_ic.sql//
          See the file hyponyms_call.sql with examples.
3.2 Clear the table cat_count, fill it by categories titles:
        mysql> DELETE FROM cat_count//
        mysql> INSERT IGNORE INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14)//
3.3 Select root category and call MySQL stored procedure hyponyms:
        mysql> CALL hyponyms('Main_topic_classifications')//    # or 'Fundamental'
          This 'CALL' required 1 day 3 hours from my computer. You can test 
          the progress with the help of command (in another MySQL process):
        mysql> SELECT COUNT(*) FROM cat_cycles;
          Tested dump of English Wikipedia (27.05.2007) had 526 cycles when 
          root category was 'Fundamental' (see http://en.wikipedia.org/wiki/User:AKA_MBG/Cycles), 
          580 cycles when root category was 'Main_topic_classifications'.

6. Dump and tar results for future reusing:
mysqldump --opt -u root -p enwiki cat_count > enwiki20070811_cat_count.sql
tar zcvf enwiki20070811_cat_count.tar.gz enwiki20070811_cat_count.sql
mysqldump --opt -u root -p enwiki cat_cycles > enwiki20070811_cat_cycles.sql


II Run testDumpICWordSim353_simple() in the file wikipedia.experiment.HyponymsTest.Java
or testDumpICWordSim353_enwiki() for Simple or English Wikipedia.

It 
* searches nearest parent category for two articles (353 pairs of words),
* takes IC (information content) from cat_count for this category, 
* prints result table to stdout.

