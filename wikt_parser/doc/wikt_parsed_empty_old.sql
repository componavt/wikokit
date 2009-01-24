-- MySQL dump 10.11
--
-- Host: localhost    Database: idfsimplewiki
-- ------------------------------------------------------
-- Server version	5.0.51-3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `page` (
  `page_id` int(10) unsigned NOT NULL auto_increment,
  `page_title` varchar(255) NOT NULL COMMENT 'copy from MediaWiki page.page_title, see http://www.mediawiki.org/wiki/Page_table',
  `word_count` int(6) unsigned NOT NULL COMMENT 'number of words in the article',
  PRIMARY KEY  (`page_id`),
  UNIQUE KEY `page_title` (`page_title`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='titles of wiki articles';
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `related_page`
--

DROP TABLE IF EXISTS `related_page`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `related_page` (
  `page_id` int(10) unsigned NOT NULL,
  `related_titles` mediumblob NOT NULL COMMENT 'Comma separated list of related pages'' titles',
  UNIQUE KEY `page_id` (`page_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='List of related pages found by the algorithm';
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `related_page`
--

LOCK TABLES `related_page` WRITE;
/*!40000 ALTER TABLE `related_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `related_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `term` (
  `term_id` int(10) unsigned NOT NULL auto_increment,
  `lemma` varchar(255) NOT NULL COMMENT 'the word\'s lemma (term), unique',
  `doc_freq` int(11) NOT NULL COMMENT 'document\'s frequency, number of documents where the term appears',
  `corpus_freq` int(11) NOT NULL COMMENT 'frequency of the term in the corpus',
  PRIMARY KEY  (`term_id`),
  UNIQUE KEY `lemma` (`lemma`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='terms found in wiki-texts';
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term_page`
--

DROP TABLE IF EXISTS `term_page`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `term_page` (
  `term_id` int(10) unsigned NOT NULL,
  `page_id` int(10) unsigned NOT NULL,
  `term_freq` int(6) unsigned NOT NULL COMMENT 'term frequency in the document',
  UNIQUE KEY `term_page_id` (`term_id`,`page_id`),
  KEY `term_id` (`term_id`),
  KEY `page_id` (`page_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='pages which contain the term';
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `term_page`
--

LOCK TABLES `term_page` WRITE;
/*!40000 ALTER TABLE `term_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `term_page` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-02-06 18:01:42
