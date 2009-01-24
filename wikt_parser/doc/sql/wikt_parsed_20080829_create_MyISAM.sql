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
  `page_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  `word_count` INT(6) UNSIGNED NOT NULL COMMENT 'number of words in the article' ,
  PRIMARY KEY (`page_id`) ,
  UNIQUE INDEX page_title (`page_title` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
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
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'The language of the word in question. \n';


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
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'The language of the word in question. \n';


-- -----------------------------------------------------
-- Table `mydb`.`lang_term`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`lang_term` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`lang_term` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `lang_id` SMALLINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`, `page_id`, `lang_id`) ,
  INDEX fk_lang_term_page (`page_id` ASC) ,
  INDEX fk_lang_term_lang_code (`lang_id` ASC) ,
  CONSTRAINT `fk_lang_term_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`page_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lang_term_lang_code`
    FOREIGN KEY (`lang_id` )
    REFERENCES `mydb`.`lang_code` (`lang_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'titles of wiki articles, entry names';


-- -----------------------------------------------------
-- Table `mydb`.`pos_term`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`pos_term` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`pos_term` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `lemma` VARCHAR(255) NOT NULL COMMENT 'The word\'s lemma (term), unique.\nIt\'s rare, but it can be different from page_title, see e.g. \"war\" section Old High German' ,
  `pos_id` TINYINT UNSIGNED NOT NULL ,
  `lang_term_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`, `pos_id`) ,
  UNIQUE INDEX lemma (`lemma` ASC) ,
  INDEX fk_pos_term_part_of_speech (`pos_id` ASC) ,
  INDEX fk_pos_term_lang_term (`lang_term_id` ASC) ,
  CONSTRAINT `fk_pos_term_part_of_speech`
    FOREIGN KEY (`pos_id` )
    REFERENCES `mydb`.`part_of_speech` (`pos_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pos_term_lang_term`
    FOREIGN KEY (`lang_term_id` )
    REFERENCES `mydb`.`lang_term` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `mydb`.`wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`wikipedia` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`wikipedia` (
  `page_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table' ,
  PRIMARY KEY (`page_id`) ,
  UNIQUE INDEX page_title (`page_title` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'Titles of related Wikipedia articles.';


-- -----------------------------------------------------
-- Table `mydb`.`page_wikipedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`page_wikipedia` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`page_wikipedia` (
  `page_id` INT(10) UNSIGNED NOT NULL ,
  `wikipedia_page_id` INT(10) UNSIGNED NOT NULL ,
  `page_page_id` INT(10) UNSIGNED NOT NULL ,
  UNIQUE INDEX id (`page_id` ASC) ,
  INDEX page_id (`page_id` ASC) ,
  INDEX fk_page_wikipedia_wikipedia (`wikipedia_page_id` ASC) ,
  PRIMARY KEY (`page_page_id`) ,
  INDEX fk_page_wikipedia_page (`page_page_id` ASC) ,
  CONSTRAINT `fk_page_wikipedia_wikipedia`
    FOREIGN KEY (`wikipedia_page_id` )
    REFERENCES `mydb`.`wikipedia` (`page_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page_wikipedia_page`
    FOREIGN KEY (`page_page_id` )
    REFERENCES `mydb`.`page` (`page_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `mydb`.`inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`inflection` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`inflection` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The list of inflextions automatically gathered from wiki-texts.' ,
  `freq` INT(11) NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears' ,
  `inflected_form` VARCHAR(255) NULL COMMENT 'Inflected form, e.g. \"cats\" for \"cat\".' ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COMMENT = 'terms found in wiki-texts';


-- -----------------------------------------------------
-- Table `mydb`.`page_inflection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`page_inflection` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`page_inflection` (
  `term_freq` INT(6) UNSIGNED NOT NULL COMMENT 'term frequency in the document' ,
  `page_id` INT(10) UNSIGNED NULL ,
  `inflection_id` INT(10) UNSIGNED NULL ,
  INDEX fk_page_inflection_page (`page_id` ASC) ,
  INDEX fk_page_inflection_inflection (`inflection_id` ASC) ,
  CONSTRAINT `fk_page_inflection_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`page_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_page_inflection_inflection`
    FOREIGN KEY (`inflection_id` )
    REFERENCES `mydb`.`inflection` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1
COMMENT = 'pages which contain the term';


-- -----------------------------------------------------
-- Table `mydb`.`meaning`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`meaning` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`meaning` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `meaning_number` TINYINT UNSIGNED NOT NULL ,
  `pos_term_id` INT(10) UNSIGNED NOT NULL ,
  `definition` VARCHAR(255) NULL ,
  UNIQUE INDEX id (`id` ASC) ,
  INDEX term_id (`id` ASC) ,
  INDEX page_id (`meaning_number` ASC) ,
  INDEX fk_meaning_pos_term (`pos_term_id` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_meaning_pos_term`
    FOREIGN KEY (`pos_term_id` )
    REFERENCES `mydb`.`pos_term` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1
COMMENT = 'Meaning includes: I definition , or translation for foreign words; II semantic relations & III translations';


-- -----------------------------------------------------
-- Table `mydb`.`synonyms`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`synonyms` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`synonyms` (
  `meaning_id` INT(10) UNSIGNED NOT NULL ,
  `page_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`meaning_id`, `page_id`) ,
  INDEX fk_synonyms_meaning (`meaning_id` ASC) ,
  INDEX fk_synonyms_page (`page_id` ASC) ,
  CONSTRAINT `fk_synonyms_meaning`
    FOREIGN KEY (`meaning_id` )
    REFERENCES `mydb`.`meaning` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_synonyms_page`
    FOREIGN KEY (`page_id` )
    REFERENCES `mydb`.`page` (`page_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1
COMMENT = 'pages which contain the term';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
