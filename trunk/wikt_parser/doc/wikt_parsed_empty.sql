SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `page`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page` ;

CREATE  TABLE IF NOT EXISTS `page` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Number of wikified words (out-bound links).\n' ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  `word_count` INT(6) UNSIGNED NOT NULL COMMENT 'number of words in the article' ,
  `wiki_link_count` INT(6) NOT NULL COMMENT 'number of wikified words (out-bound links) in the article' ,
  `is_in_wiktionary` TINYINT(1)  NULL COMMENT 'true, if the page_title exists in Wiktionary' ,
  `is_redirect` TINYINT(1)  NULL COMMENT 'Hard redirect defined by #REDIRECT' ,
  `redirect_target` VARCHAR(255) NULL COMMENT 'Redirected (target or destination) page' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_page_title` (`page_title`(7) ASC) )
ENGINE = InnoDB
COMMENT = 'titles of wiki articles, entry names' ;


-- -----------------------------------------------------
-- Table `lang_pos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang_pos` ;

CREATE  TABLE IF NOT EXISTS `lang_pos` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `pos_id` TINYINT UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `etymology_n` TINYINT UNSIGNED NOT NULL ,
  `lemma` VARCHAR(32) BINARY NOT NULL COMMENT 'The word\'s lemma (term), unique.\nIt\'s rare, but it can be different from page_title, see e.g. \"war\" section Old High German' ,
  `redirect_type` TINYINT UNSIGNED NULL COMMENT 'Type of soft redirect (Wordform, Misspelling)' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_page_lang_pos` (`page_id` ASC, `lang_id` ASC, `pos_id` ASC, `etymology_n` ASC) )
ENGINE = InnoDB, 
COMMENT = 'terms found in wiki-texts' ;


-- -----------------------------------------------------
-- Table `wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wikipedia` ;

CREATE  TABLE IF NOT EXISTS `wikipedia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_page_title` (`page_title`(7) ASC) )
ENGINE = InnoDB, 
COMMENT = 'Titles of related Wikipedia articles.' ;


-- -----------------------------------------------------
-- Table `page_wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page_wikipedia` ;

CREATE  TABLE IF NOT EXISTS `page_wikipedia` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `wikipedia_id` INT(10) UNSIGNED NOT NULL ,
  INDEX `fk_page_wikipedia_wikipedia` (`wikipedia_id` ASC) ,
  INDEX `fk_page_wikipedia_page` (`page_id` ASC) ,
  UNIQUE INDEX `page_wikipedia_unique` (`page_id` ASC, `wikipedia_id` ASC) ,
  CONSTRAINT `fk_page_wikipedia_wikipedia`
    FOREIGN KEY (`wikipedia_id` )
    REFERENCES `wikipedia` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page_wikipedia_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB, 
COMMENT = 'pages which contain the term' ;


-- -----------------------------------------------------
-- Table `lang`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang` ;

CREATE  TABLE IF NOT EXISTS `lang` (
  `id` SMALLINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(64) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  `code` VARCHAR(12) NOT NULL COMMENT 'Two (or more) letter language code, e.g. \'en\', \'ru\'.' ,
  `n_foreign_POS` INT(10) UNSIGNED NOT NULL COMMENT 'Number of foreign POS' ,
  `n_translations` INT(10) UNSIGNED NOT NULL COMMENT 'Number of translation pairs in this lang' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code` (`code` ASC) )
ENGINE = InnoDB, 
COMMENT = 'The language of the word in question. \n' ;


-- -----------------------------------------------------
-- Table `inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `inflection` ;

CREATE  TABLE IF NOT EXISTS `inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The list of inflextions automatically gathered from wiki-texts.' ,
  `freq` INT(11) UNSIGNED NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears' ,
  `inflected_form` VARCHAR(255) BINARY NOT NULL COMMENT 'Inflected form, e.g. \"cats\" for \"cat\".' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_inflected_form` (`inflected_form`(7) ASC) )
ENGINE = InnoDB, 
COMMENT = 'terms found in wiki-texts' ;


-- -----------------------------------------------------
-- Table `page_inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page_inflection` ;

CREATE  TABLE IF NOT EXISTS `page_inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `inflection_id` INT(10) UNSIGNED NOT NULL ,
  `term_freq` INT(6) UNSIGNED NOT NULL COMMENT 'term frequency in the document' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_inflection_id_id` (`page_id` ASC, `inflection_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'pages which contain the term' ;


-- -----------------------------------------------------
-- Table `lang_term_disabled`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang_term_disabled` ;

CREATE  TABLE IF NOT EXISTS `lang_term_disabled` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `hyphenation` VARCHAR(255) NULL ,
  `audio_files` VARCHAR(255) NULL ,
  PRIMARY KEY (`page_id`, `lang_id`) )
ENGINE = InnoDB, 
COMMENT = 'titles of wiki articles, entry names' ;


-- -----------------------------------------------------
-- Table `part_of_speech`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `part_of_speech` ;

CREATE  TABLE IF NOT EXISTS `part_of_speech` (
  `id` TINYINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = InnoDB, 
COMMENT = 'The language of the word in question. \n' ;


-- -----------------------------------------------------
-- Table `meaning`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meaning` ;

CREATE  TABLE IF NOT EXISTS `meaning` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `lang_pos_id` INT(10) UNSIGNED NOT NULL ,
  `meaning_n` TINYINT UNSIGNED NOT NULL ,
  `wiki_text_id` INT(10) UNSIGNED NULL COMMENT 'NULL if word without definition but with synonym' ,
  PRIMARY KEY (`id`) ,
  INDEX `lang_pos_id` (`lang_pos_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Meaning includes: definition; sem. rel., translations.' ;


-- -----------------------------------------------------
-- Table `relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation` ;

CREATE  TABLE IF NOT EXISTS `relation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `meaning_id` INT(10) UNSIGNED NOT NULL ,
  `wiki_text_id` INT(10) UNSIGNED NOT NULL ,
  `relation_type_id` TINYINT UNSIGNED NOT NULL COMMENT 'Synonym, antonym, etc.' ,
  `meaning_summary` VARCHAR(511) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `meaning_id` (`meaning_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Semantic relations (synonymy, antonymy, etc.)' ;


-- -----------------------------------------------------
-- Table `wiki_text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wiki_text` ;

CREATE  TABLE IF NOT EXISTS `wiki_text` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(4095) BINARY NOT NULL ,
  `wikified_text` VARCHAR(4095) BINARY NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_text` (`text`(12) ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wiki_text_words`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wiki_text_words` ;

CREATE  TABLE IF NOT EXISTS `wiki_text_words` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `wiki_text_id` INT(10) UNSIGNED NOT NULL ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `page_inflection_id` INT(10) UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `wiki_text_id` (`wiki_text_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Binds wiki_text with wiki words (inflection)' ;


-- -----------------------------------------------------
-- Table `relation_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation_type` ;

CREATE  TABLE IF NOT EXISTS `relation_type` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_unique` (`name` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Types of semantic relations (synonym, antonym, etc.)' ;


-- -----------------------------------------------------
-- Table `translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `translation` ;

CREATE  TABLE IF NOT EXISTS `translation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `lang_pos_id` INT(10) NOT NULL ,
  `meaning_summary` VARCHAR(511) NULL ,
  `meaning_id` INT(10) NULL COMMENT 'I am afraid it could be null sometimes.' ,
  PRIMARY KEY (`id`) ,
  INDEX `lang_pos_id` (`lang_pos_id` ASC) ,
  INDEX `meaning_id` (`meaning_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `translation_entry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `translation_entry` ;

CREATE  TABLE IF NOT EXISTS `translation_entry` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `translation_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `wiki_text_id` INT(10) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `translation_id` (`translation_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `index_native`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `index_native` ;

CREATE  TABLE IF NOT EXISTS `index_native` (
  `page_id` INT(10) UNSIGNED NOT NULL COMMENT 'Copy of page.id' ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'page.page_title of this Wiktionary article in native language' ,
  `has_relation` TINYINT(1)  NULL COMMENT 'true, if there is any semantic relation in this Wiktionary article' ,
  UNIQUE INDEX `page_id` (`page_id` ASC) ,
  INDEX `idx_page_title` (`page_title`(7) ASC) ,
  PRIMARY KEY (`page_id`) )
ENGINE = InnoDB, 
COMMENT = 'words (with definitions) in native language' ;


-- -----------------------------------------------------
-- Table `native_red_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `native_red_link` ;

CREATE  TABLE IF NOT EXISTS `native_red_link` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'page.page_title of the Wiktionary article in native language which contain this red link word.' ,
  `red_link` VARCHAR(255) BINARY NOT NULL COMMENT 'Popular red link words can be found at several WT pages.' ,
  `section_type` TINYINT UNSIGNED NULL COMMENT 'The section which contain red link word, e.g. Definition, or Synonyms, etc.' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_red_link` (`red_link`(7) ASC) )
ENGINE = InnoDB, 
COMMENT = 'words (without articles) in native language' ;


-- -----------------------------------------------------
-- Table `quote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quote` ;

CREATE  TABLE IF NOT EXISTS `quote` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `meaning_id` INT(10) UNSIGNED NOT NULL COMMENT '==meaning.id' ,
  `lang_id` SMALLINT UNSIGNED NOT NULL COMMENT '== lang_pos.lang_id (duplication), language of the quote text' ,
  `text` VARCHAR(4095) BINARY NOT NULL COMMENT 'quotation sentence text (not UNIQUE!)' ,
  `ref_id` INT(9) UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `meaning_id_INDEX` (`meaning_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Stores quotations and examples.' ;


-- -----------------------------------------------------
-- Table `quot_translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_translation` ;

CREATE  TABLE IF NOT EXISTS `quot_translation` (
  `quote_id` INT(10) UNSIGNED NOT NULL COMMENT '== quote.id' ,
  `text` VARCHAR(1023) BINARY NOT NULL ,
  PRIMARY KEY (`quote_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quot_author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_author` ;

CREATE  TABLE IF NOT EXISTS `quot_author` (
  `id` INT(5) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(512) BINARY NOT NULL ,
  `wikilink` VARCHAR(512) BINARY NOT NULL COMMENT 'a wikilink to Wikipedia (format: [[w:name|]]) for the author ' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_wikilink` (`name` ASC, `wikilink` ASC) ,
  INDEX `name` (`name` ASC) ,
  INDEX `wikilink` (`wikilink` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quot_year`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_year` ;

CREATE  TABLE IF NOT EXISTS `quot_year` (
  `id` INT(5) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `from` INT(5) UNSIGNED NOT NULL COMMENT 'start date of a writing book with the quote' ,
  `to` INT(5) UNSIGNED NOT NULL COMMENT 'finish date of a writing book with the quote' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `from_to_UNIQUE` (`from` ASC, `to` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quot_publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_publisher` ;

CREATE  TABLE IF NOT EXISTS `quot_publisher` (
  `id` INT(5) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(512) BINARY NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `text_UNIQUE` (`text` ASC) )
ENGINE = InnoDB, 
COMMENT = 'in ruwikt: издание' ;


-- -----------------------------------------------------
-- Table `quot_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_source` ;

CREATE  TABLE IF NOT EXISTS `quot_source` (
  `id` INT(5) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(512) BINARY NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `text_UNIQUE` (`text` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quot_transcription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_transcription` ;

CREATE  TABLE IF NOT EXISTS `quot_transcription` (
  `quote_id` INT(10) UNSIGNED NOT NULL ,
  `text` VARCHAR(1023) BINARY NOT NULL ,
  PRIMARY KEY (`quote_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quot_ref`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quot_ref` ;

CREATE  TABLE IF NOT EXISTS `quot_ref` (
  `id` INT(9) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `year_id` INT(5) UNSIGNED NULL ,
  `author_id` INT(5) UNSIGNED NULL ,
  `title` VARCHAR(512) BINARY NOT NULL COMMENT 'source title' ,
  `title_wikilink` VARCHAR(512) BINARY NOT NULL COMMENT 'a wikilink to Wikisource (format: [[s:title|]]) (todo)' ,
  `publisher_id` INT(5) UNSIGNED NULL ,
  `source_id` INT(5) UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `year_auth_tit_pub_s_UNIQUE` (`year_id` ASC, `author_id` ASC, `title` ASC, `publisher_id` ASC, `source_id` ASC) ,
  INDEX `title_INDEX` (`title` ASC) )
ENGINE = InnoDB, 
COMMENT = 'links to tables with reference information (year, etc.)' ;


-- -----------------------------------------------------
-- Table `label`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `label` ;

CREATE  TABLE IF NOT EXISTS `label` (
  `id` INT(7) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `label` VARCHAR(255) NOT NULL COMMENT 'label itself, e.g. {{obsolete}}, {{slang}}' ,
  `full_name` VARCHAR(255) NULL COMMENT 'label name, e.g. \'New Zealand\' for {{NZ}}' ,
  `category_id` TINYINT UNSIGNED NULL COMMENT 'ID of category of context labels' ,
  `added_by_hand` TINYINT(1)  NULL COMMENT 'added manually to code or gathered automatically by parser' ,
  `counter` INT(10) UNSIGNED NULL COMMENT 'number of times that this label was used in articles' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `label_UNIQUE` (`label` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Context labels.' ;


-- -----------------------------------------------------
-- Table `label_meaning`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `label_meaning` ;

CREATE  TABLE IF NOT EXISTS `label_meaning` (
  `label_id` INT(7) UNSIGNED NOT NULL ,
  `meaning_id` INT(10) UNSIGNED NOT NULL ,
  UNIQUE INDEX `unique_label_meaning` (`label_id` ASC, `meaning_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Binds context labels and numbered meaning.' ;


-- -----------------------------------------------------
-- Table `label_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `label_category` ;

CREATE  TABLE IF NOT EXISTS `label_category` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `parent_category_id` TINYINT UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Categories of context labels.' ;


-- -----------------------------------------------------
-- Table `label_relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `label_relation` ;

CREATE  TABLE IF NOT EXISTS `label_relation` (
  `label_id` INT(7) UNSIGNED NOT NULL ,
  `relation_id` INT(10) UNSIGNED NOT NULL ,
  UNIQUE INDEX `unique_label_relation` (`label_id` ASC, `relation_id` ASC) )
ENGINE = InnoDB, 
COMMENT = 'Binds context labels and semantic relations.' ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
