SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`page`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`page` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`page` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  `word_count` INT(6) UNSIGNED NOT NULL COMMENT 'number of words in the article' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX page_title (`page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `mydb`.`lang_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`lang_code` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`lang_code` (
  `lang_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  `code` VARCHAR(12) NULL COMMENT 'Two (or more) letter language code, e.g. \'en\', \'ru\'.' ,
  PRIMARY KEY (`lang_id`) ,
  UNIQUE INDEX name (`name` ASC) ,
  UNIQUE INDEX code (`code` ASC) )
ENGINE = InnoDB
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `mydb`.`lang_term`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`lang_term` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`lang_term` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `hyphenation` VARCHAR(255) NULL ,
  `audio_files` VARCHAR(255) NULL ,
  PRIMARY KEY (`page_id`, `lang_id`) ,
  INDEX fk_lang_term_page (`page_id` ASC) ,
  INDEX fk_lang_term_lang_code (`lang_id` ASC) ,
  CONSTRAINT `fk_lang_term_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lang_term_lang_code`
    FOREIGN KEY (`lang_id` )
    REFERENCES `mydb`.`lang_code` (`lang_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `mydb`.`part_of_speech`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`part_of_speech` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`part_of_speech` (
  `pos_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  PRIMARY KEY (`pos_id`) ,
  UNIQUE INDEX name (`name` ASC) )
ENGINE = InnoDB
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `mydb`.`pos_term`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`pos_term` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`pos_term` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `pos_id` TINYINT UNSIGNED NOT NULL ,
  `lemma` VARCHAR(255) NOT NULL COMMENT 'The word\'s lemma (term), unique.\nIt\'s rare, but it can be different from page_title, see e.g. \"war\" section Old High German' ,
  PRIMARY KEY (`page_id`, `lang_id`, `pos_id`) ,
  INDEX fk_pos_term_lang_term (`page_id` ASC, `lang_id` ASC) ,
  INDEX fk_pos_term_part_of_speech (`pos_id` ASC) ,
  CONSTRAINT `fk_pos_term_lang_term`
    FOREIGN KEY (`page_id` , `lang_id` )
    REFERENCES `mydb`.`lang_term` (`page_id` , `lang_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pos_term_part_of_speech`
    FOREIGN KEY (`pos_id` )
    REFERENCES `mydb`.`part_of_speech` (`pos_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `mydb`.`wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`wikipedia` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`wikipedia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX page_title (`page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'Titles of related Wikipedia articles.';


-- -----------------------------------------------------
-- Table `mydb`.`page_wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`page_wikipedia` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`page_wikipedia` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `wikipedia_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`wikipedia_id`, `page_id`) ,
  INDEX fk_page_wikipedia_wikipedia (`wikipedia_id` ASC) ,
  INDEX fk_page_wikipedia_page (`page_id` ASC) ,
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
ENGINE = InnoDB
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `mydb`.`inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`inflection` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The list of inflextions automatically gathered from wiki-texts.' ,
  `freq` INT(11) NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears' ,
  `inflected_form` VARCHAR(255) NULL COMMENT 'Inflected form, e.g. \"cats\" for \"cat\".' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX inflected_form (`inflected_form` ASC) )
ENGINE = InnoDB
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `mydb`.`page_inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`page_inflection` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`page_inflection` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `inflection_id` INT(10) UNSIGNED NOT NULL ,
  `term_freq` INT(6) UNSIGNED NOT NULL COMMENT 'term frequency in the document' ,
  PRIMARY KEY (`inflection_id`, `page_id`) ,
  INDEX fk_page_inflection_inflection (`inflection_id` ASC) ,
  INDEX fk_page_inflection_page (`page_id` ASC) ,
  CONSTRAINT `fk_page_inflection_inflection`
    FOREIGN KEY (`inflection_id` )
    REFERENCES `mydb`.`inflection` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page_inflection_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `mydb`.`meaning`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`meaning` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`meaning` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  `pos_id` TINYINT UNSIGNED NOT NULL ,
  `meaning_number` TINYINT UNSIGNED NOT NULL ,
  `definition` VARCHAR(255) NULL ,
  PRIMARY KEY (`meaning_number`, `page_id`, `lang_id`, `pos_id`) ,
  INDEX fk_meaning_pos_term (`page_id` ASC, `lang_id` ASC, `pos_id` ASC) ,
  CONSTRAINT `fk_meaning_pos_term`
    FOREIGN KEY (`page_id` , `lang_id` , `pos_id` )
    REFERENCES `mydb`.`pos_term` (`page_id` , `lang_id` , `pos_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Meaning includes: definition; sem. rel., translations.';


-- -----------------------------------------------------
-- Table `mydb`.`synonyms`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`synonyms` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`synonyms` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `m_page_id` INT(10) UNSIGNED NOT NULL ,
  `m_lang_id` SMALLINT UNSIGNED NOT NULL ,
  `m_pos_id` TINYINT UNSIGNED NOT NULL ,
  `m_number` TINYINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`page_id`, `m_number`, `m_page_id`, `m_lang_id`, `m_pos_id`) ,
  INDEX fk_synonyms_page (`page_id` ASC) ,
  INDEX fk_synonyms_meaning (`m_number` ASC, `m_page_id` ASC, `m_lang_id` ASC, `m_pos_id` ASC) ,
  CONSTRAINT `fk_synonyms_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_synonyms_meaning`
    FOREIGN KEY (`m_number` , `m_page_id` , `m_lang_id` , `m_pos_id` )
    REFERENCES `mydb`.`meaning` (`meaning_number` , `page_id` , `lang_id` , `pos_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'pages which contain the term';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
