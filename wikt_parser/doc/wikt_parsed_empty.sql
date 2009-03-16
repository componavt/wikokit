SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `page`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page` ;

CREATE  TABLE IF NOT EXISTS `page` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Number of wikified words (out-bound links).\n' ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  `word_count` INT(6) UNSIGNED NOT NULL COMMENT 'number of words in the article' ,
  `wiki_link_count` INT(6) NOT NULL COMMENT 'number of wikified words (out-bound links) in the article' ,
  `is_in_wiktionary` BOOLEAN NULL COMMENT 'true, if the page_title exists in Wiktionary' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_title` (`page_title` ASC) )
ENGINE = MyISAM
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `part_of_speech`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `part_of_speech` ;

CREATE  TABLE IF NOT EXISTS `part_of_speech` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name` (`name` ASC) )
ENGINE = MyISAM
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `lang`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang` ;

CREATE  TABLE IF NOT EXISTS `lang` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  `code` VARCHAR(12) NOT NULL COMMENT 'Two (or more) letter language code, e.g. \'en\', \'ru\'.' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code` (`code` ASC) )
ENGINE = MyISAM
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `lang_pos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang_pos` ;

CREATE  TABLE IF NOT EXISTS `lang_pos` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `pos_id` TINYINT UNSIGNED NOT NULL ,
  `etymology_n` TINYINT UNSIGNED NOT NULL ,
  `lemma` VARCHAR(255) NOT NULL COMMENT 'The word\'s lemma (term), unique.\nIt\'s rare, but it can be different from page_title, see e.g. \"war\" section Old High German' ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_pos` (`pos_id` ASC) ,
  INDEX `fk_lang` (`lang_id` ASC) ,
  INDEX `fk_page` (`page_id` ASC) ,
  UNIQUE INDEX `page_lang_pos_unique` (`page_id` ASC, `lang_id` ASC, `pos_id` ASC, `etymology_n` ASC) ,
  CONSTRAINT `fk_pos`
    FOREIGN KEY (`pos_id` )
    REFERENCES `mydb`.`part_of_speech` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lang`
    FOREIGN KEY (`lang_id` )
    REFERENCES `mydb`.`lang` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wikipedia` ;

CREATE  TABLE IF NOT EXISTS `wikipedia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_title` (`page_title` ASC) )
ENGINE = MyISAM
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
    REFERENCES `mydb`.`wikipedia` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page_wikipedia_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `inflection` ;

CREATE  TABLE IF NOT EXISTS `inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The list of inflextions automatically gathered from wiki-texts.' ,
  `freq` INT(11) NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears' ,
  `inflected_form` VARCHAR(255) NULL COMMENT 'Inflected form, e.g. \"cats\" for \"cat\".' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `inflected_form` (`inflected_form` ASC) )
ENGINE = MyISAM
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `page_inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page_inflection` ;

CREATE  TABLE IF NOT EXISTS `page_inflection` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `inflection_id` INT(10) UNSIGNED NOT NULL ,
  `term_freq` INT(6) UNSIGNED NOT NULL COMMENT 'term frequency in the document' ,
  INDEX `fk_inflection` (`inflection_id` ASC) ,
  INDEX `fk_page` (`page_id` ASC) ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `page_inflection_id_id` (`page_id` ASC, `inflection_id` ASC) ,
  CONSTRAINT `fk_inflection`
    FOREIGN KEY (`inflection_id` )
    REFERENCES `mydb`.`inflection` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
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
ENGINE = MyISAM
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `wiki_text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wiki_text` ;

CREATE  TABLE IF NOT EXISTS `wiki_text` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(1023) NOT NULL ,
  PRIMARY KEY (`id`) ,
  FULLTEXT INDEX `text` (`text` ASC) )
ENGINE = MyISAM;


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
  INDEX `fk_lang_pos_id` (`lang_pos_id` ASC) ,
  INDEX `fk_meaning_wiki_text` (`wiki_text_id` ASC) ,
  CONSTRAINT `fk_lang_pos_id`
    FOREIGN KEY (`lang_pos_id` )
    REFERENCES `mydb`.`lang_pos` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_meaning_wiki_text`
    FOREIGN KEY (`wiki_text_id` )
    REFERENCES `mydb`.`wiki_text` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
COMMENT = 'Meaning includes: definition; sem. rel., translations.';


-- -----------------------------------------------------
-- Table `relation_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation_type` ;

CREATE  TABLE IF NOT EXISTS `relation_type` (
  `id` TINYINT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
COMMENT = 'Types of semantic relations (synonym, antonym, etc.)';


-- -----------------------------------------------------
-- Table `relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `relation` ;

CREATE  TABLE IF NOT EXISTS `relation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `meaning_id` INT(10) UNSIGNED NOT NULL ,
  `wiki_text_id` INT(10) NOT NULL ,
  `relation_type_id` TINYINT NOT NULL COMMENT 'Synonym, antonym, etc.' ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_meaning` (`meaning_id` ASC) ,
  INDEX `fk_relation_relation_type` (`relation_type_id` ASC) ,
  INDEX `fk_relation_wiki_text` (`wiki_text_id` ASC) ,
  CONSTRAINT `fk_meaning`
    FOREIGN KEY (`meaning_id` )
    REFERENCES `mydb`.`meaning` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_relation_relation_type`
    FOREIGN KEY (`relation_type_id` )
    REFERENCES `mydb`.`relation_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_relation_wiki_text`
    FOREIGN KEY (`wiki_text_id` )
    REFERENCES `mydb`.`wiki_text` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `wiki_text_words`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wiki_text_words` ;

CREATE  TABLE IF NOT EXISTS `wiki_text_words` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `wiki_text_id` INT(10) UNSIGNED NOT NULL ,
  `page_inflection_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_words_inflection` (`page_inflection_id` ASC) ,
  INDEX `fk_words_text` (`wiki_text_id` ASC) ,
  CONSTRAINT `fk_words_inflection`
    FOREIGN KEY (`page_inflection_id` )
    REFERENCES `mydb`.`page_inflection` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_words_text`
    FOREIGN KEY (`wiki_text_id` )
    REFERENCES `mydb`.`wiki_text` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
COMMENT = 'Binds wiki_text with wiki words (inflection)';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
