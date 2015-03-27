# Introduction #

Wiktionary is a multilingual dictionary. But user usually is interested in words in one language.

**Goal**: fast search of words in one of Wiktionary languages. E.g. search of English words in Russian Wiktionary.

# Definitions #

_Main language_ - native language in the Wiktionary, e.g. Russian language in Russian Wiktionary, or English in English Wiktionary.

# MySQL index tables #

Table **index\_native** - wordlist of words in main (native) language with non empty definitions.
```
DROP TABLE IF EXISTS `index_native` ;

CREATE  TABLE IF NOT EXISTS `index_native` (
  `page_id` INT(10) UNSIGNED NOT NULL COMMENT 'Copy of page.id' ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'page.page_title of this Wiktionary article in native language' ,
  `has_relation` TINYINT(1) NULL COMMENT 'true, if there is any semantic relation in this Wiktionary article' ,
  UNIQUE INDEX `page_id` (`page_id` ASC) ,
  UNIQUE INDEX `page_title` (`page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'words (with definitions) in native language';
```

Table **index\_XX** - example of auto generated tables for each language code. The table contains two wordlists:
  1. _foreign\_word_ - words in foreign language
  1. _native\_page\_title_ - words in native language

```
DROP TABLE IF EXISTS `index_uk` ;

CREATE  TABLE IF NOT EXISTS `index_uk` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `foreign_word` VARCHAR(255) BINARY NOT NULL COMMENT 'word (in Language \'uk\') found somewhere in the Wiktionary article' ,
  `foreign_has_definition` TINYINT(1) NOT NULL COMMENT 'true, if there is any definition in the Wiktionary article with the title foreign_word' ,
  `native_page_title` VARCHAR(255) BINARY COMMENT 'page.page_title of this Wiktionary article in native language' ,
  PRIMARY KEY (`id`) ,
  INDEX `foreign_word` (`foreign_word` (7) ASC) ,
  INDEX `native_page_title` (`native_page_title` (7) ASC) ,
  UNIQUE `foreign_native` (`foreign_word` ASC, `native_page_title` ASC) )
ENGINE = InnoDB
COMMENT = 'words with this language code (see table postfix)';
```

Table **native\_red\_link** - list of red link words (without articles) in native language, which were found in other WT articles in native language.

```
DROP TABLE IF EXISTS `native_red_link` ;

CREATE  TABLE IF NOT EXISTS `native_red_link` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'page.page_title of the Wiktionary article in native language which contain this red link word.' ,
  `red_link` VARCHAR(255) BINARY NOT NULL COMMENT 'Popular red link words can be found at several WT pages.' ,
  `section_type` TINYINT UNSIGNED NULL COMMENT 'The section which contain red link word, e.g. Definition, or Synonyms, etc.' ,
  PRIMARY KEY (`id`) ,
  INDEX `red_link` (`red_link` ASC) )
ENGINE = InnoDB
COMMENT = 'words (without articles) in native language';
```

# Cases #

There are four cases of parsing Wiktionary article. The following INSERT statements should be executed.

1) Word in main (native) language (page\_title), e.g. Russian word "вода" in Russian Wiktionary.
  * index\_native.page\_title = "вода" // Only with a definition
  * index\_native.has\_relation = true

!Attention. The word will be stored to the table only if the definition section is not empty.

2) **TODO** Case with red-links for words in native language. Both red link and title of page with red link word are in native language.

Word in main (native) language (page\_title), e.g. Russian word "вода" in Russian Wiktionary. This Wiktionary article has a red-link word, i.e. absent, e.g. "ходить по воде".
  * native\_red\_link.page\_title = "вода"
  * native\_red\_link.red\_link = "ходить по воде"

3) Word in foreign language (in the ===Translation=== section), e.g. English word "water" is the translation of Russian "вода" in Russian Wiktionary.
  * index\_en.native\_page\_title = "вода"
  * index\_en.foreign\_word = "water"
  * index\_en.foreign\_has\_definition = 1 // if "water" has article with definition

4) Word in foreign language (page\_title), e.g. English word "water" in Russian Wiktionary.
  * index\_en.native\_page\_title = NULL
  * index\_en.foreign\_word = "water" // Only with a definition
  * index\_en.foreign\_has\_definition = 1 // always in this case

!Attention. The word will be stored to the table only if the definition section is not empty.