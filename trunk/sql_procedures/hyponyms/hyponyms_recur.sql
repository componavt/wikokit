DROP PROCEDURE IF EXISTS hyponyms_recur//
CREATE PROCEDURE hyponyms_recur (
        IN cur_title VARCHAR(255),      /** title of root category (current) */
        IN cur_depth INT,
        IN LOG_CATEGORY_SIZE FLOAT)     /** number of all categories and articles (constant) */
COMMENT 'Calculates number of hyponyms (sub-categories + articles), fills table cat_count'
proc:
BEGIN
        DECLARE id, CATEGORY_SIZE INT(10);
        DECLARE n_cycle INT;
        DECLARE cur_subcat, is_cycle SMALLINT UNSIGNED;
        DECLARE cur_articles MEDIUMINT UNSIGNED;
        DECLARE cur_hyponyms, subcat_hyponyms, exist_category_in_cat_count MEDIUMINT UNSIGNED;
        DECLARE cur_ic FLOAT;

        DECLARE title VARCHAR(255);
        DECLARE done, error BOOL DEFAULT FALSE;
        DECLARE DEBUG, WARNING BOOL DEFAULT FALSE; 

        /** For each subcategory */
        DECLARE cur
                CURSOR FOR 
                SELECT page_id,page_title
                FROM page,categorylinks
                WHERE cl_from=page_id AND page_namespace=14 AND cl_to=cur_title;
        DECLARE
                CONTINUE HANDLER FOR
                SQLSTATE '02000'
                SET done = TRUE;

        SET DEBUG = FALSE;               /** Print debug information (very verbose mode) */
        SET WARNING = FALSE;             /** Print warnings (verbose mode) */

        IF cur_depth>254 THEN
            SELECT 'depth>254' AS 'ERROR', title AS 'title', cur_depth AS 'cur_depth';
        LEAVE proc; END IF;

        /** Checks category cycles: whether stack contains cur_title? */
        SELECT COUNT(*) FROM cat_parent_stack WHERE page_title=cur_title INTO is_cycle;
        IF 0 = is_cycle THEN 
                INSERT INTO cat_parent_stack (page_title,n_depth) VALUES (cur_title,cur_depth);
        ELSE
                /** Yes, stores titles of cycle's category to the table cat_cycles */
                /** SELECT '' AS '40 DEBUG hyponyms_recur', cur_title AS 'cur_title'; */
                CALL hyponyms_cycles(cur_title);
        LEAVE proc; END IF;

        /** SELECT CONCAT('30 OK cur_depth=',cur_depth); */
        OPEN cur;
        IF error THEN SELECT '33 ERROR OPEN failed'; LEAVE proc; END IF;

        SET cur_hyponyms = 0;
        myLoop: LOOP
                FETCH cur INTO id, title;
                IF done THEN
                        CLOSE cur;      IF error THEN SELECT '40 CLOSE failed'; END IF;
                        LEAVE myLoop;
                END IF;

                IF DEBUG THEN SELECT 'For' AS '60', id AS 'id (sub-category)', title AS 'title (sub-category)',
                cur_title AS 'cur_title (parent-category)', cur_depth AS 'cur_depth'; END IF;

                IF error THEN SELECT '42 FETCH failed'; LEAVE proc; END IF;
                /** SELECT CONCAT('43 OK title=',title, ' cur_depth=',cur_depth); */
                
                IF NULL=title THEN
                        /** Warning message about categories without articles */
                        SELECT 'subcategory title is NULL' AS 'ERROR', cur_title AS 'cur_title (sub-category)',
                        id AS 'id (sub-category)',
                        cur_depth AS 'cur_depth';
                LEAVE proc; END IF;


                SELECT COUNT(*) FROM cat_count WHERE page_title=title INTO exist_category_in_cat_count;
                IF 0 = exist_category_in_cat_count THEN 
                        /** Warning message: category exists in table 'page', but it's absent in cat_count */
                        IF WARNING THEN 
                                SELECT 'Category exists in table "page", but it\'s absent in "cat_count"' 
                                AS 'WARNING 78', 
                                title AS 'category title', cur_depth AS 'cur_depth';
                        END IF;
                ELSE        
                        /** Skip repetitive category calls, if n_hyponyms is already calculated */
                        SET subcat_hyponyms=0;
                        SELECT n_hyponyms FROM cat_count WHERE page_title=title INTO subcat_hyponyms;
                        IF DEBUG THEN 
                                SELECT 'DEBUG' AS '87', subcat_hyponyms AS 'subcat_hyponyms', title AS 'title'; END IF;

                        IF 0 = subcat_hyponyms THEN
                                CALL hyponyms_recur(title, cur_depth+1, LOG_CATEGORY_SIZE);
                                IF error THEN SELECT CONCAT('46 ERROR, title=',title,' id=',id,' cur_depth=',cur_depth); 
                                END IF;
                               
                                SELECT n_hyponyms FROM cat_count WHERE page_title=title INTO subcat_hyponyms;

                                /** Warning message about categories without sub-categories and without articles */
                                IF 0=subcat_hyponyms AND DEBUG THEN
                                        SELECT 'subcat_hyponyms=0' AS 'WARNING 100', 
                                        title AS 'subcategory title', id AS 'id', 
                                        cur_title AS 'category cur_title', cur_depth AS 'cur_depth';
                                END IF;
                        ELSE
                                /** Warning: categories is already calculated... cycle may exist, 
                                (or may be several parents which have one upper-parent) */
                                IF WARNING THEN
                                        SELECT 'WARNING there are several paths to parent (cycle)' AS '107', 
                                        title AS 'subcategory title', id AS 'id', 
                                        cur_title AS 'category cur_title', cur_depth AS 'cur_depth';
                                END IF;
                        END IF;
                        SET cur_hyponyms = cur_hyponyms + subcat_hyponyms;
                END IF;
        END LOOP;

        DELETE FROM cat_parent_stack WHERE page_title=cur_title;

        UPDATE cat_count SET n_depth=cur_depth WHERE page_title=cur_title;

        /** Calculates number of direct sub-categories, writes to n_subcat */
        SELECT COUNT(*) FROM page,categorylinks WHERE cl_from=page_id AND page_namespace=14 AND cl_to=cur_title 
            INTO cur_subcat;

        /** number of articles which belong to this category */
        SELECT COUNT(*) FROM page,categorylinks WHERE cl_from=page_id AND page_namespace=0 AND cl_to=cur_title 
            INTO cur_articles;

        /** number of hyponyms of this category */
        SET cur_hyponyms = cur_hyponyms + cur_subcat + cur_articles;

        /** information content of the category*/
        SET cur_ic = 1 - (LOG(cur_hyponyms + 1)) / LOG_CATEGORY_SIZE;

        UPDATE cat_count SET 
            n_subcat  =cur_subcat,
            n_articles=cur_articles,
            n_hyponyms=cur_hyponyms,
            ic=cur_ic
        WHERE page_title=cur_title;

        /** IF cur_title='Skills' OR cur_title='Tools' OR cur_title='Construction' OR cur_title='Manufacturing' THEN 
                SELECT "DEBUG" AS '134', cur_title AS 'cur_title', cur_depth AS 'cur_depth', 
                cur_subcat AS 'cur_subcat', cur_articles AS 'cur_articles', 
                cur_hyponyms AS 'cur_hyponyms', cur_ic AS 'cur_ic';
                SELECT "DEBUG" AS '137', page_title AS 'Table:cat_parent_stack', n_depth AS 'n_depth' 
                FROM cat_parent_stack LIMIT 0,100;

                SELECT * FROM cat_count WHERE page_title IN ('Skills', 'Tools','Construction','Manufacturing');
        END IF;*/

END; //
SET max_sp_recursion_depth=255//
SELECT * FROM cat_count LIMIT 5//
