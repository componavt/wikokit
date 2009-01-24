/** test in simplewiki: CALL recalculate_ic(426077); */
DROP PROCEDURE IF EXISTS recalculate_ic//
CREATE PROCEDURE recalculate_ic (
        IN category_size INT(10)       /** Number of passed categories and articles */
)
COMMENT 'Recalculate IC'
proc:
BEGIN
        DECLARE done, error BOOL DEFAULT FALSE;
        DECLARE cur_hyponyms MEDIUMINT UNSIGNED;
        DECLARE cur_ic FLOAT;
        DECLARE cur_title VARCHAR(255);
        DECLARE LOG_CATEGORY_SIZE FLOAT;

        DECLARE cur
                CURSOR FOR 
                SELECT n_hyponyms, page_title FROM cat_count;
        DECLARE
                CONTINUE HANDLER FOR
                SQLSTATE '02000'
                SET done = TRUE;


        SET LOG_CATEGORY_SIZE = LOG(category_size);

        OPEN cur;
        IF error THEN SELECT 'ERROR OPEN CURSOR failed in PROCEDURE recalculate_ic'; LEAVE proc; END IF;

        myLoop: LOOP
                FETCH cur INTO cur_hyponyms, cur_title;
                IF done THEN
                        CLOSE cur;
                        LEAVE myLoop;
                END IF;
             
                IF cur_hyponyms > 0 THEN 
                /**  if 0 hyponyms then ic = -1, remain it */
                        /** information content of the category*/
                        SET cur_ic = 1 - (LOG(cur_hyponyms + 1)) / LOG_CATEGORY_SIZE;

                        UPDATE cat_count SET 
                            ic=cur_ic
                        WHERE page_title=cur_title;
                END IF;
        END LOOP;
END; //
