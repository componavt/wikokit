SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `mean_semrel_en`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mean_semrel_en` ;

CREATE  TABLE IF NOT EXISTS `mean_semrel_en` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '\n' ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'headword' ,
  `meaning` VARCHAR(4095) BINARY NOT NULL COMMENT 'Definition text.' ,
  `synonyms` VARCHAR(4095) BINARY NULL COMMENT 'Synonyms joined by some symbol' ,
  `antonyms` VARCHAR(4095) BINARY NULL ,
  `hypernyms` VARCHAR(4095) BINARY NULL ,
  `hyponyms` VARCHAR(4095) BINARY NULL ,
  `holonyms` VARCHAR(4095) BINARY NULL ,
  `meronyms` VARCHAR(4095) BINARY NULL ,
  `troponyms` VARCHAR(4095) BINARY NULL ,
  `coordinate terms` VARCHAR(4095) BINARY NULL ,
  `n_sem_rel` TINYINT UNSIGNED NOT NULL COMMENT 'Positive number of semantic relations (for this meaning)' ,
  `success` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of correct answers with these sem. relations' ,
  `failure` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of wrong answers with these sem. relations' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_page_title` (`page_title`(7) ASC) )
ENGINE = InnoDB
COMMENT = 'entry name, meaning and semantic relations for English words' ;


-- -----------------------------------------------------
-- Table `lang`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lang` ;

CREATE  TABLE IF NOT EXISTS `lang` (
  `id` SMALLINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(64) NOT NULL COMMENT 'language name: English, Русский, etc.' ,
  `code` VARCHAR(12) NOT NULL COMMENT 'Two (or more) letter language code, e.g. en, ru.' ,
  `n_meaning` INT(10) UNSIGNED NOT NULL COMMENT 'Number of meanings (with semantic relations) of words of this language' ,
  `n_sem_rel` INT(10) UNSIGNED NOT NULL COMMENT 'Number of semantic relations (for this language)' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code` (`code` ASC) )
ENGINE = MyISAM, 
COMMENT = 'The language of the word in question. \n' ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
