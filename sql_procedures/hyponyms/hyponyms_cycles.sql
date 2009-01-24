DROP PROCEDURE IF EXISTS hyponyms_cycles//
CREATE PROCEDURE hyponyms_cycles (
        IN cur_title VARCHAR(255))      /** title of category which starts the cycle */
COMMENT 'Stores titles of categories (which form the cycle) to the table cat_cycles'
proc2:
BEGIN
        DECLARE n_cycle TINYINT UNSIGNED;
        DECLARE title, cc_titles VARCHAR(255);
        DECLARE done, error BOOL DEFAULT FALSE;

        /** SELECT '' AS '13 DEBUG hyponyms_cycles', cur_title AS 'cur_title';*/
        SELECT n_depth FROM cat_parent_stack WHERE page_title=cur_title INTO n_cycle;
        /** SELECT '' AS '15 DEBUG hyponyms_cycles', n_cycle AS 'n_cycle';*/

        /** Debug message */
        /** SELECT '' AS 'ERROR: The cycle of categories is found', 
        page_title,n_depth FROM cat_parent_stack WHERE n_depth >= n_cycle;*/

        BEGIN
                DECLARE cur
                        CURSOR FOR 
                        SELECT page_title
                        FROM  cat_parent_stack
                        WHERE n_depth >= n_cycle;
                DECLARE
                        CONTINUE HANDLER FOR
                        SQLSTATE '02000'
                        SET done = TRUE;

                OPEN cur;
                IF error THEN SELECT 'ERROR OPEN CURSOR failed in PROCEDURE hyponyms_cycles'; LEAVE proc2; END IF;

                SET cc_titles = '';
                myLoop: LOOP
                        FETCH cur INTO title;
                        IF done THEN
                                CLOSE cur;
                                LEAVE myLoop;
                        END IF;
                       
                        SET cc_titles = CONCAT(cc_titles, '|', title);
                END LOOP;

                IF CHAR_LENGTH(cc_titles) > 1 THEN 
                        SET cc_titles = SUBSTRING(cc_titles,2);  /** Trims first delimiter */
                        INSERT INTO cat_cycles (concat_titles) VALUES (cc_titles);
                END IF;
        END;
        
END; //
