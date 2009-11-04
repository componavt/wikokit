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
  `is_in_wiktionary` TINYINT(1) NULL COMMENT 'true, if the page_title exists in Wiktionary' ,
  `is_redirect` TINYINT(1) NULL COMMENT 'Hard redirect defined by #REDIRECT' ,
  `redirect_target` VARCHAR(255) NULL COMMENT 'Redirected (target or destination) page' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_title` (`page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'titles of wiki articles, entry names';


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
  `lemma` VARCHAR(255) BINARY NOT NULL COMMENT 'The word\'s lemma (term), unique.\nIt\'s rare, but it can be different from page_title, see e.g. \"war\" section Old High German' ,
  `redirect_type` TINYINT UNSIGNED NULL COMMENT 'Type of soft redirect (Wordform, Misspelling)' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_lang_pos_unique` (`page_id` ASC, `lang_id` ASC, `pos_id` ASC, `etymology_n` ASC) )
ENGINE = InnoDB
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wikipedia` ;

CREATE  TABLE IF NOT EXISTS `wikipedia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_title` (`page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'Titles of related Wikipedia articles.';


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
ENGINE = InnoDB
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `lang`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang` ;

CREATE  TABLE IF NOT EXISTS `lang` (
  `id` SMALLINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  `code` VARCHAR(12) NOT NULL COMMENT 'Two (or more) letter language code, e.g. \'en\', \'ru\'.' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code` (`code` ASC) )
ENGINE = MyISAM
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `inflection` ;

CREATE  TABLE IF NOT EXISTS `inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The list of inflextions automatically gathered from wiki-texts.' ,
  `freq` INT(11) UNSIGNED NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears' ,
  `inflected_form` VARCHAR(255) BINARY NOT NULL COMMENT 'Inflected form, e.g. \"cats\" for \"cat\".' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `inflected_form` (`inflected_form` ASC) )
ENGINE = InnoDB
COMMENT = 'terms found in wiki-texts';


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
ENGINE = InnoDB
COMMENT = 'pages which contain the term';


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
ENGINE = InnoDB
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `part_of_speech`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `part_of_speech` ;

CREATE  TABLE IF NOT EXISTS `part_of_speech` (
  `id` SMALLINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = MyISAM
COMMENT = 'The language of the word in question. \n';


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
ENGINE = InnoDB
COMMENT = 'Meaning includes: definition; sem. rel., translations.';


-- -----------------------------------------------------
-- Table `relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation` ;

CREATE  TABLE IF NOT EXISTS `relation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `meaning_id` INT(10) UNSIGNED NOT NULL ,
  `wiki_text_id` INT(10) UNSIGNED NOT NULL ,
  `relation_type_id` TINYINT UNSIGNED NOT NULL COMMENT 'Synonym, antonym, etc.' ,
  PRIMARY KEY (`id`) ,
  INDEX `meaning_id` (`meaning_id` ASC) )
ENGINE = InnoDB
COMMENT = 'Semantic relations (synonymy, antonymy, etc.)';


-- -----------------------------------------------------
-- Table `wiki_text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wiki_text` ;

CREATE  TABLE IF NOT EXISTS `wiki_text` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(1023) BINARY NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `text_unique` (`text`(128) ASC) )
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
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
COMMENT = 'Binds wiki_text with wiki words (inflection)';


-- -----------------------------------------------------
-- Table `relation_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation_type` ;

CREATE  TABLE IF NOT EXISTS `relation_type` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_unique` (`name` ASC) )
ENGINE = InnoDB
COMMENT = 'Types of semantic relations (synonym, antonym, etc.)';


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
  INDEX `lang_pos_id` (`lang_pos_id` ASC) )
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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
