DELIMITER //
DROP TABLE IF EXISTS cat_count;//
CREATE TABLE cat_count (
  `page_id` INT(10) UNSIGNED NOT NULL COMMENT 'Category page identifier. Corresponds to page.page_id',
  `page_title` VARCHAR(255)  NOT NULL COMMENT 'Category page title. Copy of page.page_title, see http://www.mediawiki.org/wiki/Page_table',
  `n_depth` TINYINT UNSIGNED NOT NULL COMMENT 'The depth of a node n is the length of the path from the root to the node. The root node is at depth zero.',
  `n_subcat` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of direct sub-categories (childrens). It is zero for category-leaf.',
  `n_articles` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Number of articles which have this category.',

  `n_hyponyms` MEDIUMINT UNSIGNED NOT NULL COMMENT 'n_subcat + n_articles + n_hyponyms_of_sub-categories',
  `ic` FLOAT  NOT NULL DEFAULT -1 COMMENT 'Infromation content, -1 helps to avoid additional categories which are not covered by root category',
  UNIQUE KEY `page_id` (`page_id`),
  UNIQUE KEY `page_title` (`page_title`)
)
ENGINE = MYISAM
COMMENT = 'Category hyponyms counter';//


DROP TABLE IF EXISTS cat_parent_stack;//
CREATE TABLE cat_parent_stack (
  `page_title` VARCHAR(255)  NOT NULL COMMENT 'Copy of page.page_title, see http://www.mediawiki.org/wiki/Page_table',
  `n_depth` TINYINT UNSIGNED NOT NULL COMMENT 'The depth of a node n is the length of the path from the root to the node. The root node is at depth zero.',
  UNIQUE KEY `page_title` (`page_title`)
)
ENGINE = MYISAM
COMMENT = 'Temporary table of categories from root to current category. It is used to skip cycles of categories.';//


DROP TABLE IF EXISTS cat_cycles;//
CREATE TABLE cat_cycles (
  `concat_titles` VARCHAR(255) NOT NULL COMMENT 'List of category titles which forms a cycle'
)
ENGINE = MYISAM
COMMENT = 'List of categories which forms cycles.';//
