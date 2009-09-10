-- MySQL dump 10.13  Distrib 5.1.37, for Win32 (ia32)
--
-- Host: localhost    Database: ruwikt20090707_parsed
-- ------------------------------------------------------
-- Server version	5.1.37-community
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,ANSI' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table "inflection"
--

DROP TABLE IF EXISTS "inflection";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "inflection" (
  "id" int(10) NOT NULL PRIMARY KEY ,
  "freq" int(11) NOT NULL ,
  "inflected_form" varchar(255) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "lang"
--

DROP TABLE IF EXISTS "lang";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "lang" (
  "id" smallint(6) NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL ,
  "code" varchar(12) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "lang_pos"
--

DROP TABLE IF EXISTS "lang_pos";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "lang_pos" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "page_id" int(10) NOT NULL,
  "pos_id" tinyint(3) NOT NULL,
  "lang_id" smallint(5) NOT NULL,
  "etymology_n" tinyint(3) NOT NULL,
  "lemma" varchar(255) NOT NULL ,
  "redirect_type" tinyint(4) DEFAULT NULL 
  /* UNIQUE KEY "page_lang_pos_unique" ("page_id","lang_id","pos_id","etymology_n") */
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "lang_term_disabled"
--

DROP TABLE IF EXISTS "lang_term_disabled";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "lang_term_disabled" (
  "page_id" int(10) NOT NULL,
  "lang_id" smallint(5) NOT NULL,
  "hyphenation" varchar(255) DEFAULT NULL,
  "audio_files" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "meaning"
--

DROP TABLE IF EXISTS "meaning";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "meaning" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "lang_pos_id" int(10) NOT NULL,
  "meaning_n" tinyint(3) NOT NULL,
  "wiki_text_id" int(10) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "page"
--

DROP TABLE IF EXISTS "page";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "page" (
  "id" int(10) NOT NULL PRIMARY KEY ,
  "page_title" varchar(255) NOT NULL UNIQUE,
  "word_count" int(6) NOT NULL ,
  "wiki_link_count" int(6) NOT NULL ,
  "is_in_wiktionary" tinyint(1) DEFAULT NULL ,
  "is_redirect" tinyint(1) DEFAULT NULL ,
  "redirect_target" varchar(255) DEFAULT NULL 
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "page_inflection"
--

DROP TABLE IF EXISTS "page_inflection";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "page_inflection" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "page_id" int(10) NOT NULL,
  "inflection_id" int(10) NOT NULL,
  "term_freq" int(6) NOT NULL
  /* UNIQUE KEY "page_inflection_id_id" ("page_id","inflection_id") */
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "page_wikipedia"
--

DROP TABLE IF EXISTS "page_wikipedia";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "page_wikipedia" (
  "page_id" int(10) NOT NULL,
  "wikipedia_id" int(10) NOT NULL
  /* UNIQUE KEY "page_wikipedia_unique" ("page_id","wikipedia_id"),
  KEY "fk_page_wikipedia_wikipedia" ("wikipedia_id"),
  KEY "fk_page_wikipedia_page" ("page_id"),
  CONSTRAINT "fk_page_wikipedia_page" FOREIGN KEY ("page_id") REFERENCES "page" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_page_wikipedia_wikipedia" FOREIGN KEY ("wikipedia_id") REFERENCES "wikipedia" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION */
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "part_of_speech"
--

DROP TABLE IF EXISTS "part_of_speech";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "part_of_speech" (
  "id" smallint(6) NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "relation"
--

DROP TABLE IF EXISTS "relation";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "relation" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "meaning_id" int(10) NOT NULL,
  "wiki_text_id" int(10) NOT NULL,
  "relation_type_id" tinyint(3) NOT NULL 
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "relation_type"
--

DROP TABLE IF EXISTS "relation_type";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "relation_type" (
  "id" tinyint(3) NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "translation"
--

DROP TABLE IF EXISTS "translation";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "translation" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "lang_pos_id" int(10) NOT NULL,
  "meaning_summary" varchar(511) DEFAULT NULL,
  "meaning_id" int(10) DEFAULT NULL 
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "translation_entry"
--

DROP TABLE IF EXISTS "translation_entry";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "translation_entry" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "translation_id" int(10) NOT NULL,
  "lang_id" smallint(5) NOT NULL,
  "wiki_text_id" int(10) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "wiki_text"
--

DROP TABLE IF EXISTS "wiki_text";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "wiki_text" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "text" varchar(1023) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "wiki_text_words"
--

DROP TABLE IF EXISTS "wiki_text_words";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "wiki_text_words" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "wiki_text_id" int(10) NOT NULL,
  "page_id" int(10) NOT NULL,
  "page_inflection_id" int(10) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "wikipedia"
--

DROP TABLE IF EXISTS "wikipedia";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "wikipedia" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "page_title" varchar(255) NOT NULL UNIQUE
);
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2009-08-20 19:50:07
