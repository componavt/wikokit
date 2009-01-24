/** Todo: resolve category redirects, e.g. Category:Buildings -> 
  * Category:Buildings and structures in simple.wikipedia
  */
DROP PROCEDURE IF EXISTS hyponyms//
CREATE PROCEDURE hyponyms (
        IN title VARCHAR(255)       /** title of root category */)
BEGIN
        DECLARE CATEGORY_SIZE,      /** Total number of categories and articles */
                cur_category_size   /** Number of passed categories and articles */
                INT(10);
        DECLARE n_cycles INT;
        DECLARE LOG_CATEGORY_SIZE  FLOAT;

        UPDATE cat_count SET n_depth=0, n_subcat=0, n_articles=0, n_hyponyms=0, ic=-1;

        /** Calculates LOG_CATEGORY_SIZE := log(number of categories and articles) */
        SELECT COUNT(*) FROM page WHERE page_namespace=0 OR page_namespace=14 INTO CATEGORY_SIZE;
        /** SELECT CONCAT('Number of articles and categories: ',CATEGORY_SIZE); */
        SET LOG_CATEGORY_SIZE = LOG(CATEGORY_SIZE);
       
        DELETE FROM cat_parent_stack;
        DELETE FROM cat_cycles;
        SET max_sp_recursion_depth=255;
        CALL hyponyms_recur(title,0,LOG_CATEGORY_SIZE);
      
        SET cur_category_size = 0;
        SELECT n_hyponyms FROM cat_count WHERE page_title=title INTO cur_category_size;

        SELECT COUNT(*) FROM cat_cycles INTO n_cycles;
        IF n_cycles > 0 THEN
                SELECT concat_titles AS 'ERROR: The cycle of categories is found' FROM cat_cycles LIMIT 0,100;
        END IF;

        IF cur_category_size = CATEGORY_SIZE THEN 
                SELECT 'OK' AS '', CATEGORY_SIZE AS 'Total number of articles and categories',
                                   'All categories and articles are connected into one component.' AS 'Result';
        ELSE
                SELECT 'WARNING' AS '', cur_category_size AS 'Passed',
                                        'does not equals to' AS '',
                                        CATEGORY_SIZE AS 'Total number of articles and categories';

                /** Recalculate IC */
                CALL recalculate_ic(cur_category_size);
        END IF;
END; //
