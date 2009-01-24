SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`page`
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
-- Table `mydb`.`lang_term`
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
-- Table `mydb`.`pos_term`
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
-- Table `mydb`.`wikipedia`
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
-- Table `mydb`.`page_wikipedia`
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


-- -----------------------------------------------------
-- Table `mydb`.`meaning`
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
