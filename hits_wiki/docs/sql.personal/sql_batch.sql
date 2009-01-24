-- use ruwiki;
use ruwiki;
-- SELECT cur_title FROM cur WHERE cur_id = 10332;
SELECT cur_id, cur_title FROM cur WHERE cur_id = 22233 OR cur_id = 18991;

-- failed SELECT cur_title FROM cur WHERE cur_title LIKE _cp866 '%лаз%';
-- empty SELECT cur_title FROM cur WHERE cur_title LIKE '%лаз%';
-- empty SELECT cur_title FROM cur WHERE cur_title LIKE '%ырч%';
-- SELECT cur_title FROM cur WHERE 
--             (CONVERT(cur_title         USING utf8))
--        LIKE (CONVERT(_cp1251'├ырч'     USING utf8));