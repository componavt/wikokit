== test enwiki ==
CALL hyponyms('Network_flow')//

== test simplewiki ==
INSERT INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14 AND (page_title='Websites' OR page_title='Wikimedia' OR page_title='Wikis'));
INSERT INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14 AND (page_title='Websites' OR page_title='Wikimedia' OR page_title='Wikis' OR page_title='Internet'));

INSERT INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14 AND (page_title='Kings_of_Urartu' OR page_title='Monarchs_of_Armenia'));

UPDATE cat_count SET n_depth=1 WHERE page_id=5498;

-- test 1: Computer and Keyboard --
-- Computers Computer_science Computing Writing_tools Tools
Category:Everyday life -> Architecture -> Construction -> Tools
Category:Everyday life -> Tools

Category:Everyday life -> Learning -> Skills -> Tools
Category:Everyday life -> Tools

1.
DELETE FROM cat_count//
2.
INSERT INTO cat_count (page_id,page_title) (SELECT page_id,page_title FROM page WHERE page_namespace=14 AND page_title IN ('Computers', 'Computer_science', 'Computing', 'Writing_tools', 'Tools', 'Appliances', 'Skills'))//
3.
CALL hyponyms('Writing_tools')//
CALL hyponyms('Tools')//
CALL hyponyms('Skills')//
CALL hyponyms('Learning')//
CALL hyponyms('Everyday_life')//

CALL hyponyms('Main_page')//

4.
SELECT * FROM cat_count WHERE page_title IN ('Computers', 'Computer_science', 'Computing', 'Writing_tools', 'Tools', 'Appliances', 'Skills')//
SELECT * FROM cat_count WHERE page_title IN ('Computers', 'Computer_science', 'Computing', 'Writing_tools', 'Tools', 'Appliances', 'Skills', 'Learning') ORDER BY n_hyponyms DESC//
