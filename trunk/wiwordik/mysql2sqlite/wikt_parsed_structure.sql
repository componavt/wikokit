-- mysql2sqlite V0.01 wiwordik (GNU-GPL) 2009 AKA MBG 

-- MySQL dump 10.13  Distrib 5.1.37, for Win32 (ia32)
--
-- Host: localhost    Database: ruwikt20100405_parsed
-- ------------------------------------------------------
-- Server version	5.1.37-community-log
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,ANSI' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table "index_aaa"
--

DROP TABLE IF EXISTS "index_aaa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aaa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aab"
--

DROP TABLE IF EXISTS "index_aab";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aab" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aae"
--

DROP TABLE IF EXISTS "index_aae";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aae" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aak"
--

DROP TABLE IF EXISTS "index_aak";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aak" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aao"
--

DROP TABLE IF EXISTS "index_aao";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aao" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aar"
--

DROP TABLE IF EXISTS "index_aar";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aar" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aat"
--

DROP TABLE IF EXISTS "index_aat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abe"
--

DROP TABLE IF EXISTS "index_abe";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abe" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abk"
--

DROP TABLE IF EXISTS "index_abk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abl"
--

DROP TABLE IF EXISTS "index_abl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abm"
--

DROP TABLE IF EXISTS "index_abm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abq"
--

DROP TABLE IF EXISTS "index_abq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_abs"
--

DROP TABLE IF EXISTS "index_abs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_abs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ace"
--

DROP TABLE IF EXISTS "index_ace";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ace" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ach"
--

DROP TABLE IF EXISTS "index_ach";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ach" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_acr"
--

DROP TABLE IF EXISTS "index_acr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_acr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_acv"
--

DROP TABLE IF EXISTS "index_acv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_acv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_acx"
--

DROP TABLE IF EXISTS "index_acx";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_acx" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ada"
--

DROP TABLE IF EXISTS "index_ada";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ada" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ade"
--

DROP TABLE IF EXISTS "index_ade";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ade" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_adj"
--

DROP TABLE IF EXISTS "index_adj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_adj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_adt"
--

DROP TABLE IF EXISTS "index_adt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_adt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ady"
--

DROP TABLE IF EXISTS "index_ady";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ady" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_adz"
--

DROP TABLE IF EXISTS "index_adz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_adz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aeb"
--

DROP TABLE IF EXISTS "index_aeb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aeb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_afb"
--

DROP TABLE IF EXISTS "index_afb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_afb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_afr"
--

DROP TABLE IF EXISTS "index_afr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_afr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_agf"
--

DROP TABLE IF EXISTS "index_agf";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_agf" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_agj"
--

DROP TABLE IF EXISTS "index_agj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_agj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_agx"
--

DROP TABLE IF EXISTS "index_agx";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_agx" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ahs"
--

DROP TABLE IF EXISTS "index_ahs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ahs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aie"
--

DROP TABLE IF EXISTS "index_aie";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aie" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aii"
--

DROP TABLE IF EXISTS "index_aii";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aii" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ain"
--

DROP TABLE IF EXISTS "index_ain";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ain" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ain.kana"
--

DROP TABLE IF EXISTS "index_ain.kana";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ain.kana" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ain.lat"
--

DROP TABLE IF EXISTS "index_ain.lat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ain.lat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aiw"
--

DROP TABLE IF EXISTS "index_aiw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aiw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aja"
--

DROP TABLE IF EXISTS "index_aja";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aja" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ajg"
--

DROP TABLE IF EXISTS "index_ajg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ajg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aji"
--

DROP TABLE IF EXISTS "index_aji";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aji" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ajp"
--

DROP TABLE IF EXISTS "index_ajp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ajp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aka"
--

DROP TABLE IF EXISTS "index_aka";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aka" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ake"
--

DROP TABLE IF EXISTS "index_ake";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ake" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_akg"
--

DROP TABLE IF EXISTS "index_akg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_akg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_akk"
--

DROP TABLE IF EXISTS "index_akk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_akk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_akl"
--

DROP TABLE IF EXISTS "index_akl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_akl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_akz"
--

DROP TABLE IF EXISTS "index_akz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_akz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alc"
--

DROP TABLE IF EXISTS "index_alc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ale"
--

DROP TABLE IF EXISTS "index_ale";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ale" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ali"
--

DROP TABLE IF EXISTS "index_ali";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ali" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aln"
--

DROP TABLE IF EXISTS "index_aln";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aln" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alp"
--

DROP TABLE IF EXISTS "index_alp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alq"
--

DROP TABLE IF EXISTS "index_alq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alr"
--

DROP TABLE IF EXISTS "index_alr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_als"
--

DROP TABLE IF EXISTS "index_als";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_als" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alt"
--

DROP TABLE IF EXISTS "index_alt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_alu"
--

DROP TABLE IF EXISTS "index_alu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_alu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_am"
--

DROP TABLE IF EXISTS "index_am";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_am" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_amk"
--

DROP TABLE IF EXISTS "index_amk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_amk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_amn"
--

DROP TABLE IF EXISTS "index_amn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_amn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ams"
--

DROP TABLE IF EXISTS "index_ams";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ams" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_amt"
--

DROP TABLE IF EXISTS "index_amt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_amt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_amu"
--

DROP TABLE IF EXISTS "index_amu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_amu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_and"
--

DROP TABLE IF EXISTS "index_and";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_and" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ang"
--

DROP TABLE IF EXISTS "index_ang";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ang" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_anp"
--

DROP TABLE IF EXISTS "index_anp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_anp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ant"
--

DROP TABLE IF EXISTS "index_ant";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ant" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apc"
--

DROP TABLE IF EXISTS "index_apc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apd"
--

DROP TABLE IF EXISTS "index_apd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apj"
--

DROP TABLE IF EXISTS "index_apj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apk"
--

DROP TABLE IF EXISTS "index_apk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apl"
--

DROP TABLE IF EXISTS "index_apl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apm"
--

DROP TABLE IF EXISTS "index_apm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apw"
--

DROP TABLE IF EXISTS "index_apw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_apy"
--

DROP TABLE IF EXISTS "index_apy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_apy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aqc"
--

DROP TABLE IF EXISTS "index_aqc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aqc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ara"
--

DROP TABLE IF EXISTS "index_ara";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ara" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arb"
--

DROP TABLE IF EXISTS "index_arb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arc"
--

DROP TABLE IF EXISTS "index_arc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_are"
--

DROP TABLE IF EXISTS "index_are";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_are" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arg"
--

DROP TABLE IF EXISTS "index_arg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arn"
--

DROP TABLE IF EXISTS "index_arn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arp"
--

DROP TABLE IF EXISTS "index_arp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arq"
--

DROP TABLE IF EXISTS "index_arq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ars"
--

DROP TABLE IF EXISTS "index_ars";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ars" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_art"
--

DROP TABLE IF EXISTS "index_art";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_art" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_art-oou"
--

DROP TABLE IF EXISTS "index_art-oou";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_art-oou" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aru"
--

DROP TABLE IF EXISTS "index_aru";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aru" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arw"
--

DROP TABLE IF EXISTS "index_arw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ary"
--

DROP TABLE IF EXISTS "index_ary";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ary" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_arz"
--

DROP TABLE IF EXISTS "index_arz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_arz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ase"
--

DROP TABLE IF EXISTS "index_ase";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ase" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_asm"
--

DROP TABLE IF EXISTS "index_asm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_asm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ast"
--

DROP TABLE IF EXISTS "index_ast";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ast" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_atv"
--

DROP TABLE IF EXISTS "index_atv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_atv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_aty"
--

DROP TABLE IF EXISTS "index_aty";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_aty" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_av"
--

DROP TABLE IF EXISTS "index_av";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_av" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ave"
--

DROP TABLE IF EXISTS "index_ave";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ave" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_avk"
--

DROP TABLE IF EXISTS "index_avk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_avk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_awa"
--

DROP TABLE IF EXISTS "index_awa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_awa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_awk"
--

DROP TABLE IF EXISTS "index_awk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_awk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_axm"
--

DROP TABLE IF EXISTS "index_axm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_axm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ay"
--

DROP TABLE IF EXISTS "index_ay";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ay" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ayl"
--

DROP TABLE IF EXISTS "index_ayl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ayl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ayp"
--

DROP TABLE IF EXISTS "index_ayp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ayp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_az"
--

DROP TABLE IF EXISTS "index_az";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_az" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ba"
--

DROP TABLE IF EXISTS "index_ba";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ba" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bagua"
--

DROP TABLE IF EXISTS "index_bagua";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bagua" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bal"
--

DROP TABLE IF EXISTS "index_bal";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bal" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ban"
--

DROP TABLE IF EXISTS "index_ban";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ban" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bar"
--

DROP TABLE IF EXISTS "index_bar";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bar" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bas"
--

DROP TABLE IF EXISTS "index_bas";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bas" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bat-smg"
--

DROP TABLE IF EXISTS "index_bat-smg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bat-smg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bcc"
--

DROP TABLE IF EXISTS "index_bcc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bcc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bcl"
--

DROP TABLE IF EXISTS "index_bcl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bcl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bdk"
--

DROP TABLE IF EXISTS "index_bdk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bdk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bdp"
--

DROP TABLE IF EXISTS "index_bdp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bdp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bdy"
--

DROP TABLE IF EXISTS "index_bdy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bdy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_be"
--

DROP TABLE IF EXISTS "index_be";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_be" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bej"
--

DROP TABLE IF EXISTS "index_bej";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bej" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bem"
--

DROP TABLE IF EXISTS "index_bem";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bem" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bew"
--

DROP TABLE IF EXISTS "index_bew";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bew" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bft"
--

DROP TABLE IF EXISTS "index_bft";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bft" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bg"
--

DROP TABLE IF EXISTS "index_bg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bgc"
--

DROP TABLE IF EXISTS "index_bgc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bgc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bh"
--

DROP TABLE IF EXISTS "index_bh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bho"
--

DROP TABLE IF EXISTS "index_bho";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bho" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bhw"
--

DROP TABLE IF EXISTS "index_bhw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bhw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bi"
--

DROP TABLE IF EXISTS "index_bi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bib"
--

DROP TABLE IF EXISTS "index_bib";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bib" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bik"
--

DROP TABLE IF EXISTS "index_bik";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bik" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bin"
--

DROP TABLE IF EXISTS "index_bin";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bin" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bjz"
--

DROP TABLE IF EXISTS "index_bjz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bjz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bku"
--

DROP TABLE IF EXISTS "index_bku";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bku" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bla"
--

DROP TABLE IF EXISTS "index_bla";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bla" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bm"
--

DROP TABLE IF EXISTS "index_bm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bn"
--

DROP TABLE IF EXISTS "index_bn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bo"
--

DROP TABLE IF EXISTS "index_bo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bph"
--

DROP TABLE IF EXISTS "index_bph";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bph" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bpy"
--

DROP TABLE IF EXISTS "index_bpy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bpy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bqi"
--

DROP TABLE IF EXISTS "index_bqi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bqi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_br"
--

DROP TABLE IF EXISTS "index_br";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_br" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_brc"
--

DROP TABLE IF EXISTS "index_brc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_brc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_brh"
--

DROP TABLE IF EXISTS "index_brh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_brh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bs"
--

DROP TABLE IF EXISTS "index_bs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_btk"
--

DROP TABLE IF EXISTS "index_btk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_btk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bto"
--

DROP TABLE IF EXISTS "index_bto";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bto" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bua"
--

DROP TABLE IF EXISTS "index_bua";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bua" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bug"
--

DROP TABLE IF EXISTS "index_bug";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bug" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_bxr"
--

DROP TABLE IF EXISTS "index_bxr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_bxr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_byn"
--

DROP TABLE IF EXISTS "index_byn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_byn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ca"
--

DROP TABLE IF EXISTS "index_ca";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ca" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_caa"
--

DROP TABLE IF EXISTS "index_caa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_caa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cab"
--

DROP TABLE IF EXISTS "index_cab";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cab" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cbk-zam"
--

DROP TABLE IF EXISTS "index_cbk-zam";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cbk-zam" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ccc"
--

DROP TABLE IF EXISTS "index_ccc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ccc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cdo"
--

DROP TABLE IF EXISTS "index_cdo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cdo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ce"
--

DROP TABLE IF EXISTS "index_ce";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ce" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ceb"
--

DROP TABLE IF EXISTS "index_ceb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ceb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cel"
--

DROP TABLE IF EXISTS "index_cel";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cel" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ch"
--

DROP TABLE IF EXISTS "index_ch";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ch" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chc"
--

DROP TABLE IF EXISTS "index_chc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chg"
--

DROP TABLE IF EXISTS "index_chg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chk"
--

DROP TABLE IF EXISTS "index_chk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chm"
--

DROP TABLE IF EXISTS "index_chm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cho"
--

DROP TABLE IF EXISTS "index_cho";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cho" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chr"
--

DROP TABLE IF EXISTS "index_chr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chu"
--

DROP TABLE IF EXISTS "index_chu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chu-ru"
--

DROP TABLE IF EXISTS "index_chu-ru";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chu-ru" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chu.cyr"
--

DROP TABLE IF EXISTS "index_chu.cyr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chu.cyr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chu.glag"
--

DROP TABLE IF EXISTS "index_chu.glag";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chu.glag" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_chy"
--

DROP TABLE IF EXISTS "index_chy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_chy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cic"
--

DROP TABLE IF EXISTS "index_cic";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cic" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cjs"
--

DROP TABLE IF EXISTS "index_cjs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cjs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ckt"
--

DROP TABLE IF EXISTS "index_ckt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ckt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cmn"
--

DROP TABLE IF EXISTS "index_cmn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cmn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_co"
--

DROP TABLE IF EXISTS "index_co";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_co" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cop"
--

DROP TABLE IF EXISTS "index_cop";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cop" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cr"
--

DROP TABLE IF EXISTS "index_cr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_crh"
--

DROP TABLE IF EXISTS "index_crh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_crh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_crh-latn"
--

DROP TABLE IF EXISTS "index_crh-latn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_crh-latn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cs"
--

DROP TABLE IF EXISTS "index_cs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_csb"
--

DROP TABLE IF EXISTS "index_csb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_csb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cu"
--

DROP TABLE IF EXISTS "index_cu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cui"
--

DROP TABLE IF EXISTS "index_cui";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cui" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cuk"
--

DROP TABLE IF EXISTS "index_cuk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cuk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cv"
--

DROP TABLE IF EXISTS "index_cv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_cy"
--

DROP TABLE IF EXISTS "index_cy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_cy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_da"
--

DROP TABLE IF EXISTS "index_da";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_da" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dar"
--

DROP TABLE IF EXISTS "index_dar";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dar" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_de"
--

DROP TABLE IF EXISTS "index_de";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_de" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_de-a"
--

DROP TABLE IF EXISTS "index_de-a";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_de-a" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_de-formal"
--

DROP TABLE IF EXISTS "index_de-formal";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_de-formal" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dif"
--

DROP TABLE IF EXISTS "index_dif";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dif" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_diq"
--

DROP TABLE IF EXISTS "index_diq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_diq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dk"
--

DROP TABLE IF EXISTS "index_dk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dlg"
--

DROP TABLE IF EXISTS "index_dlg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dlg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dng"
--

DROP TABLE IF EXISTS "index_dng";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dng" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_drw"
--

DROP TABLE IF EXISTS "index_drw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_drw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dsb"
--

DROP TABLE IF EXISTS "index_dsb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dsb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_duj"
--

DROP TABLE IF EXISTS "index_duj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_duj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dum"
--

DROP TABLE IF EXISTS "index_dum";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dum" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dv"
--

DROP TABLE IF EXISTS "index_dv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_dz"
--

DROP TABLE IF EXISTS "index_dz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_dz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_egy"
--

DROP TABLE IF EXISTS "index_egy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_egy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_el"
--

DROP TABLE IF EXISTS "index_el";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_el" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_eml"
--

DROP TABLE IF EXISTS "index_eml";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_eml" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_en"
--

DROP TABLE IF EXISTS "index_en";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_en" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_en-au"
--

DROP TABLE IF EXISTS "index_en-au";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_en-au" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_en-gb"
--

DROP TABLE IF EXISTS "index_en-gb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_en-gb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_en-nz"
--

DROP TABLE IF EXISTS "index_en-nz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_en-nz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_en-us"
--

DROP TABLE IF EXISTS "index_en-us";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_en-us" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_enm"
--

DROP TABLE IF EXISTS "index_enm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_enm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_eo"
--

DROP TABLE IF EXISTS "index_eo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_eo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_es"
--

DROP TABLE IF EXISTS "index_es";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_es" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_et"
--

DROP TABLE IF EXISTS "index_et";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_et" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_eu"
--

DROP TABLE IF EXISTS "index_eu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_eu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_eve"
--

DROP TABLE IF EXISTS "index_eve";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_eve" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_evn"
--

DROP TABLE IF EXISTS "index_evn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_evn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ewe"
--

DROP TABLE IF EXISTS "index_ewe";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ewe" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ext"
--

DROP TABLE IF EXISTS "index_ext";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ext" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fa"
--

DROP TABLE IF EXISTS "index_fa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fat"
--

DROP TABLE IF EXISTS "index_fat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ff"
--

DROP TABLE IF EXISTS "index_ff";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ff" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fi"
--

DROP TABLE IF EXISTS "index_fi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fic_drw"
--

DROP TABLE IF EXISTS "index_fic_drw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fic_drw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fil"
--

DROP TABLE IF EXISTS "index_fil";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fil" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fiu-vro"
--

DROP TABLE IF EXISTS "index_fiu-vro";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fiu-vro" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fj"
--

DROP TABLE IF EXISTS "index_fj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fkv"
--

DROP TABLE IF EXISTS "index_fkv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fkv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fo"
--

DROP TABLE IF EXISTS "index_fo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fon"
--

DROP TABLE IF EXISTS "index_fon";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fon" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fr"
--

DROP TABLE IF EXISTS "index_fr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fr-be"
--

DROP TABLE IF EXISTS "index_fr-be";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fr-be" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fr-ch"
--

DROP TABLE IF EXISTS "index_fr-ch";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fr-ch" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_frc"
--

DROP TABLE IF EXISTS "index_frc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_frc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_frm"
--

DROP TABLE IF EXISTS "index_frm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_frm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fro"
--

DROP TABLE IF EXISTS "index_fro";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fro" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_frp"
--

DROP TABLE IF EXISTS "index_frp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_frp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_frr"
--

DROP TABLE IF EXISTS "index_frr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_frr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_frs"
--

DROP TABLE IF EXISTS "index_frs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_frs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fur"
--

DROP TABLE IF EXISTS "index_fur";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fur" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_fy"
--

DROP TABLE IF EXISTS "index_fy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_fy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ga"
--

DROP TABLE IF EXISTS "index_ga";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ga" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gag"
--

DROP TABLE IF EXISTS "index_gag";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gag" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gan"
--

DROP TABLE IF EXISTS "index_gan";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gan" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gd"
--

DROP TABLE IF EXISTS "index_gd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gl"
--

DROP TABLE IF EXISTS "index_gl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gld"
--

DROP TABLE IF EXISTS "index_gld";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gld" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_glk"
--

DROP TABLE IF EXISTS "index_glk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_glk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gmh"
--

DROP TABLE IF EXISTS "index_gmh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gmh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gni"
--

DROP TABLE IF EXISTS "index_gni";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gni" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_goh"
--

DROP TABLE IF EXISTS "index_goh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_goh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_got"
--

DROP TABLE IF EXISTS "index_got";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_got" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_grc"
--

DROP TABLE IF EXISTS "index_grc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_grc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_grc-att"
--

DROP TABLE IF EXISTS "index_grc-att";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_grc-att" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_grc-ion"
--

DROP TABLE IF EXISTS "index_grc-ion";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_grc-ion" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_grn"
--

DROP TABLE IF EXISTS "index_grn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_grn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gsw"
--

DROP TABLE IF EXISTS "index_gsw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gsw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gu"
--

DROP TABLE IF EXISTS "index_gu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_gv"
--

DROP TABLE IF EXISTS "index_gv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_gv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ha"
--

DROP TABLE IF EXISTS "index_ha";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ha" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ha.arab"
--

DROP TABLE IF EXISTS "index_ha.arab";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ha.arab" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ha.lat"
--

DROP TABLE IF EXISTS "index_ha.lat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ha.lat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hak"
--

DROP TABLE IF EXISTS "index_hak";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hak" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hanzi"
--

DROP TABLE IF EXISTS "index_hanzi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hanzi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_haw"
--

DROP TABLE IF EXISTS "index_haw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_haw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hbo"
--

DROP TABLE IF EXISTS "index_hbo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hbo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_he"
--

DROP TABLE IF EXISTS "index_he";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_he" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hi"
--

DROP TABLE IF EXISTS "index_hi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hif"
--

DROP TABLE IF EXISTS "index_hif";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hif" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hil"
--

DROP TABLE IF EXISTS "index_hil";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hil" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hit"
--

DROP TABLE IF EXISTS "index_hit";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hit" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hmn"
--

DROP TABLE IF EXISTS "index_hmn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hmn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ho"
--

DROP TABLE IF EXISTS "index_ho";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ho" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hr"
--

DROP TABLE IF EXISTS "index_hr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hsb"
--

DROP TABLE IF EXISTS "index_hsb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hsb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ht"
--

DROP TABLE IF EXISTS "index_ht";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ht" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hu"
--

DROP TABLE IF EXISTS "index_hu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hy"
--

DROP TABLE IF EXISTS "index_hy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_hz"
--

DROP TABLE IF EXISTS "index_hz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_hz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ibo"
--

DROP TABLE IF EXISTS "index_ibo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ibo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_id"
--

DROP TABLE IF EXISTS "index_id";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_id" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ie"
--

DROP TABLE IF EXISTS "index_ie";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ie" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ii"
--

DROP TABLE IF EXISTS "index_ii";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ii" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ik"
--

DROP TABLE IF EXISTS "index_ik";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ik" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ike-cans"
--

DROP TABLE IF EXISTS "index_ike-cans";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ike-cans" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ike-latn"
--

DROP TABLE IF EXISTS "index_ike-latn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ike-latn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ilo"
--

DROP TABLE IF EXISTS "index_ilo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ilo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ina"
--

DROP TABLE IF EXISTS "index_ina";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ina" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_inh"
--

DROP TABLE IF EXISTS "index_inh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_inh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_int"
--

DROP TABLE IF EXISTS "index_int";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_int" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_io"
--

DROP TABLE IF EXISTS "index_io";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_io" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_is"
--

DROP TABLE IF EXISTS "index_is";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_is" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_it"
--

DROP TABLE IF EXISTS "index_it";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_it" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ith.lat"
--

DROP TABLE IF EXISTS "index_ith.lat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ith.lat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_itl"
--

DROP TABLE IF EXISTS "index_itl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_itl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_iu"
--

DROP TABLE IF EXISTS "index_iu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_iu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ium"
--

DROP TABLE IF EXISTS "index_ium";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ium" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_izh"
--

DROP TABLE IF EXISTS "index_izh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_izh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ja"
--

DROP TABLE IF EXISTS "index_ja";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ja" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jam"
--

DROP TABLE IF EXISTS "index_jam";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jam" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jbo"
--

DROP TABLE IF EXISTS "index_jbo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jbo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jct"
--

DROP TABLE IF EXISTS "index_jct";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jct" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jpr"
--

DROP TABLE IF EXISTS "index_jpr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jpr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jut"
--

DROP TABLE IF EXISTS "index_jut";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jut" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_jv"
--

DROP TABLE IF EXISTS "index_jv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_jv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ka"
--

DROP TABLE IF EXISTS "index_ka";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ka" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kaa"
--

DROP TABLE IF EXISTS "index_kaa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kaa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kab"
--

DROP TABLE IF EXISTS "index_kab";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kab" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kal"
--

DROP TABLE IF EXISTS "index_kal";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kal" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kaz"
--

DROP TABLE IF EXISTS "index_kaz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kaz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kbd"
--

DROP TABLE IF EXISTS "index_kbd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kbd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kca"
--

DROP TABLE IF EXISTS "index_kca";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kca" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kdd"
--

DROP TABLE IF EXISTS "index_kdd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kdd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kdr"
--

DROP TABLE IF EXISTS "index_kdr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kdr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ket"
--

DROP TABLE IF EXISTS "index_ket";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ket" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kg"
--

DROP TABLE IF EXISTS "index_kg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_khk"
--

DROP TABLE IF EXISTS "index_khk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_khk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ki"
--

DROP TABLE IF EXISTS "index_ki";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ki" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kim"
--

DROP TABLE IF EXISTS "index_kim";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kim" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kiu"
--

DROP TABLE IF EXISTS "index_kiu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kiu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kj"
--

DROP TABLE IF EXISTS "index_kj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kjh"
--

DROP TABLE IF EXISTS "index_kjh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kjh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_km"
--

DROP TABLE IF EXISTS "index_km";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_km" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kn"
--

DROP TABLE IF EXISTS "index_kn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_knw"
--

DROP TABLE IF EXISTS "index_knw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_knw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ko"
--

DROP TABLE IF EXISTS "index_ko";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ko" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kok"
--

DROP TABLE IF EXISTS "index_kok";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kok" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kom"
--

DROP TABLE IF EXISTS "index_kom";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kom" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kpy"
--

DROP TABLE IF EXISTS "index_kpy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kpy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kr"
--

DROP TABLE IF EXISTS "index_kr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_krc"
--

DROP TABLE IF EXISTS "index_krc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_krc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_krh"
--

DROP TABLE IF EXISTS "index_krh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_krh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kri"
--

DROP TABLE IF EXISTS "index_kri";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kri" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_krj"
--

DROP TABLE IF EXISTS "index_krj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_krj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_krl"
--

DROP TABLE IF EXISTS "index_krl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_krl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ks"
--

DROP TABLE IF EXISTS "index_ks";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ks" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ksh"
--

DROP TABLE IF EXISTS "index_ksh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ksh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ksh-c-a"
--

DROP TABLE IF EXISTS "index_ksh-c-a";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ksh-c-a" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ksh-p-b"
--

DROP TABLE IF EXISTS "index_ksh-p-b";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ksh-p-b" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ksi"
--

DROP TABLE IF EXISTS "index_ksi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ksi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ku"
--

DROP TABLE IF EXISTS "index_ku";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ku" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kum"
--

DROP TABLE IF EXISTS "index_kum";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kum" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kw"
--

DROP TABLE IF EXISTS "index_kw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ky"
--

DROP TABLE IF EXISTS "index_ky";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ky" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_kyi"
--

DROP TABLE IF EXISTS "index_kyi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_kyi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_la"
--

DROP TABLE IF EXISTS "index_la";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_la" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lad"
--

DROP TABLE IF EXISTS "index_lad";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lad" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lah"
--

DROP TABLE IF EXISTS "index_lah";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lah" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lb"
--

DROP TABLE IF EXISTS "index_lb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lbe"
--

DROP TABLE IF EXISTS "index_lbe";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lbe" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_letter_ru"
--

DROP TABLE IF EXISTS "index_letter_ru";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_letter_ru" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lez"
--

DROP TABLE IF EXISTS "index_lez";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lez" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lfn"
--

DROP TABLE IF EXISTS "index_lfn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lfn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lij"
--

DROP TABLE IF EXISTS "index_lij";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lij" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lim"
--

DROP TABLE IF EXISTS "index_lim";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lim" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_liv"
--

DROP TABLE IF EXISTS "index_liv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_liv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lld"
--

DROP TABLE IF EXISTS "index_lld";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lld" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lmo"
--

DROP TABLE IF EXISTS "index_lmo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lmo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ln"
--

DROP TABLE IF EXISTS "index_ln";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ln" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lo"
--

DROP TABLE IF EXISTS "index_lo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_loz"
--

DROP TABLE IF EXISTS "index_loz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_loz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lt"
--

DROP TABLE IF EXISTS "index_lt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ltc"
--

DROP TABLE IF EXISTS "index_ltc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ltc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ltg"
--

DROP TABLE IF EXISTS "index_ltg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ltg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lug"
--

DROP TABLE IF EXISTS "index_lug";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lug" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_luo"
--

DROP TABLE IF EXISTS "index_luo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_luo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_luy"
--

DROP TABLE IF EXISTS "index_luy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_luy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lv"
--

DROP TABLE IF EXISTS "index_lv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lzh"
--

DROP TABLE IF EXISTS "index_lzh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lzh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_lzz"
--

DROP TABLE IF EXISTS "index_lzz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_lzz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mag"
--

DROP TABLE IF EXISTS "index_mag";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mag" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mai"
--

DROP TABLE IF EXISTS "index_mai";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mai" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_man"
--

DROP TABLE IF EXISTS "index_man";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_man" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_map-bms"
--

DROP TABLE IF EXISTS "index_map-bms";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_map-bms" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mas"
--

DROP TABLE IF EXISTS "index_mas";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mas" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mdf"
--

DROP TABLE IF EXISTS "index_mdf";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mdf" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mg"
--

DROP TABLE IF EXISTS "index_mg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mgm"
--

DROP TABLE IF EXISTS "index_mgm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mgm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mh"
--

DROP TABLE IF EXISTS "index_mh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mhr"
--

DROP TABLE IF EXISTS "index_mhr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mhr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mi"
--

DROP TABLE IF EXISTS "index_mi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_miq"
--

DROP TABLE IF EXISTS "index_miq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_miq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mk"
--

DROP TABLE IF EXISTS "index_mk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ml"
--

DROP TABLE IF EXISTS "index_ml";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ml" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mn"
--

DROP TABLE IF EXISTS "index_mn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mnc"
--

DROP TABLE IF EXISTS "index_mnc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mnc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mnk"
--

DROP TABLE IF EXISTS "index_mnk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mnk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mns"
--

DROP TABLE IF EXISTS "index_mns";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mns" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mo"
--

DROP TABLE IF EXISTS "index_mo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_moh"
--

DROP TABLE IF EXISTS "index_moh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_moh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mos"
--

DROP TABLE IF EXISTS "index_mos";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mos" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mr"
--

DROP TABLE IF EXISTS "index_mr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mrj"
--

DROP TABLE IF EXISTS "index_mrj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mrj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mrv"
--

DROP TABLE IF EXISTS "index_mrv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mrv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ms"
--

DROP TABLE IF EXISTS "index_ms";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ms" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mt"
--

DROP TABLE IF EXISTS "index_mt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mus"
--

DROP TABLE IF EXISTS "index_mus";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mus" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mwf"
--

DROP TABLE IF EXISTS "index_mwf";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mwf" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mwj"
--

DROP TABLE IF EXISTS "index_mwj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mwj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mwl"
--

DROP TABLE IF EXISTS "index_mwl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mwl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_my"
--

DROP TABLE IF EXISTS "index_my";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_my" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_myv"
--

DROP TABLE IF EXISTS "index_myv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_myv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_mzn"
--

DROP TABLE IF EXISTS "index_mzn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_mzn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_na"
--

DROP TABLE IF EXISTS "index_na";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_na" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nah"
--

DROP TABLE IF EXISTS "index_nah";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nah" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nan"
--

DROP TABLE IF EXISTS "index_nan";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nan" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nap"
--

DROP TABLE IF EXISTS "index_nap";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nap" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_native"
--

DROP TABLE IF EXISTS "index_native";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_native" (
  "page_id" int(10) NOT NULL,
  "page_title" varchar(255) NOT NULL,
  "has_relation" tinyint(1) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nb"
--

DROP TABLE IF EXISTS "index_nb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nds"
--

DROP TABLE IF EXISTS "index_nds";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nds" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nds-nl"
--

DROP TABLE IF EXISTS "index_nds-nl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nds-nl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ne"
--

DROP TABLE IF EXISTS "index_ne";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ne" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_new"
--

DROP TABLE IF EXISTS "index_new";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_new" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ng"
--

DROP TABLE IF EXISTS "index_ng";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ng" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nio"
--

DROP TABLE IF EXISTS "index_nio";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nio" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_niu"
--

DROP TABLE IF EXISTS "index_niu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_niu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_niv"
--

DROP TABLE IF EXISTS "index_niv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_niv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nl"
--

DROP TABLE IF EXISTS "index_nl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nn"
--

DROP TABLE IF EXISTS "index_nn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_no"
--

DROP TABLE IF EXISTS "index_no";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_no" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nog"
--

DROP TABLE IF EXISTS "index_nog";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nog" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_non"
--

DROP TABLE IF EXISTS "index_non";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_non" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nov"
--

DROP TABLE IF EXISTS "index_nov";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nov" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nrm"
--

DROP TABLE IF EXISTS "index_nrm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nrm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nso"
--

DROP TABLE IF EXISTS "index_nso";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nso" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_num"
--

DROP TABLE IF EXISTS "index_num";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_num" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_nv"
--

DROP TABLE IF EXISTS "index_nv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_nv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ny"
--

DROP TABLE IF EXISTS "index_ny";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ny" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_obt"
--

DROP TABLE IF EXISTS "index_obt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_obt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_oc"
--

DROP TABLE IF EXISTS "index_oc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_oc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_odt"
--

DROP TABLE IF EXISTS "index_odt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_odt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ofs"
--

DROP TABLE IF EXISTS "index_ofs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ofs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_oj"
--

DROP TABLE IF EXISTS "index_oj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_oj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_oko"
--

DROP TABLE IF EXISTS "index_oko";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_oko" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_om"
--

DROP TABLE IF EXISTS "index_om";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_om" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ood"
--

DROP TABLE IF EXISTS "index_ood";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ood" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_or"
--

DROP TABLE IF EXISTS "index_or";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_or" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_orv"
--

DROP TABLE IF EXISTS "index_orv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_orv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_os"
--

DROP TABLE IF EXISTS "index_os";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_os" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_oun"
--

DROP TABLE IF EXISTS "index_oun";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_oun" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_owl"
--

DROP TABLE IF EXISTS "index_owl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_owl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pa"
--

DROP TABLE IF EXISTS "index_pa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pag"
--

DROP TABLE IF EXISTS "index_pag";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pag" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pal"
--

DROP TABLE IF EXISTS "index_pal";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pal" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pam"
--

DROP TABLE IF EXISTS "index_pam";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pam" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pap"
--

DROP TABLE IF EXISTS "index_pap";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pap" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pau"
--

DROP TABLE IF EXISTS "index_pau";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pau" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pcd"
--

DROP TABLE IF EXISTS "index_pcd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pcd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pcm"
--

DROP TABLE IF EXISTS "index_pcm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pcm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pdc"
--

DROP TABLE IF EXISTS "index_pdc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pdc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pdt"
--

DROP TABLE IF EXISTS "index_pdt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pdt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_peo"
--

DROP TABLE IF EXISTS "index_peo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_peo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pfl"
--

DROP TABLE IF EXISTS "index_pfl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pfl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pga"
--

DROP TABLE IF EXISTS "index_pga";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pga" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pi"
--

DROP TABLE IF EXISTS "index_pi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pie"
--

DROP TABLE IF EXISTS "index_pie";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pie" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pih"
--

DROP TABLE IF EXISTS "index_pih";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pih" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pinyin"
--

DROP TABLE IF EXISTS "index_pinyin";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pinyin" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pjt"
--

DROP TABLE IF EXISTS "index_pjt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pjt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pl"
--

DROP TABLE IF EXISTS "index_pl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_plm"
--

DROP TABLE IF EXISTS "index_plm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_plm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pms"
--

DROP TABLE IF EXISTS "index_pms";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pms" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pmt"
--

DROP TABLE IF EXISTS "index_pmt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pmt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pnb"
--

DROP TABLE IF EXISTS "index_pnb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pnb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pnt"
--

DROP TABLE IF EXISTS "index_pnt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pnt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pox"
--

DROP TABLE IF EXISTS "index_pox";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pox" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ppol"
--

DROP TABLE IF EXISTS "index_ppol";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ppol" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_prg"
--

DROP TABLE IF EXISTS "index_prg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_prg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_prs"
--

DROP TABLE IF EXISTS "index_prs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_prs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ps"
--

DROP TABLE IF EXISTS "index_ps";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ps" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_psl"
--

DROP TABLE IF EXISTS "index_psl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_psl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pt"
--

DROP TABLE IF EXISTS "index_pt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_pt-br"
--

DROP TABLE IF EXISTS "index_pt-br";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_pt-br" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_qu"
--

DROP TABLE IF EXISTS "index_qu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_qu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_qya"
--

DROP TABLE IF EXISTS "index_qya";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_qya" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rap"
--

DROP TABLE IF EXISTS "index_rap";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rap" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rar"
--

DROP TABLE IF EXISTS "index_rar";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rar" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rhg"
--

DROP TABLE IF EXISTS "index_rhg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rhg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rif"
--

DROP TABLE IF EXISTS "index_rif";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rif" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rm"
--

DROP TABLE IF EXISTS "index_rm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rmr"
--

DROP TABLE IF EXISTS "index_rmr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rmr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rmy"
--

DROP TABLE IF EXISTS "index_rmy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rmy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ro"
--

DROP TABLE IF EXISTS "index_ro";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ro" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_roa-tara"
--

DROP TABLE IF EXISTS "index_roa-tara";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_roa-tara" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rom"
--

DROP TABLE IF EXISTS "index_rom";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rom" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_romaji"
--

DROP TABLE IF EXISTS "index_romaji";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_romaji" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rop"
--

DROP TABLE IF EXISTS "index_rop";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rop" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ru-old"
--

DROP TABLE IF EXISTS "index_ru-old";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ru-old" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_run"
--

DROP TABLE IF EXISTS "index_run";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_run" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rup"
--

DROP TABLE IF EXISTS "index_rup";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rup" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ruq"
--

DROP TABLE IF EXISTS "index_ruq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ruq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ruq-cyrl"
--

DROP TABLE IF EXISTS "index_ruq-cyrl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ruq-cyrl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ruq-grek"
--

DROP TABLE IF EXISTS "index_ruq-grek";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ruq-grek" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ruq-latn"
--

DROP TABLE IF EXISTS "index_ruq-latn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ruq-latn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_rw"
--

DROP TABLE IF EXISTS "index_rw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_rw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ryn"
--

DROP TABLE IF EXISTS "index_ryn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ryn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ryu"
--

DROP TABLE IF EXISTS "index_ryu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ryu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sa"
--

DROP TABLE IF EXISTS "index_sa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sah"
--

DROP TABLE IF EXISTS "index_sah";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sah" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sam"
--

DROP TABLE IF EXISTS "index_sam";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sam" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sat"
--

DROP TABLE IF EXISTS "index_sat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sc"
--

DROP TABLE IF EXISTS "index_sc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_scn"
--

DROP TABLE IF EXISTS "index_scn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_scn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sco"
--

DROP TABLE IF EXISTS "index_sco";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sco" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sd"
--

DROP TABLE IF EXISTS "index_sd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sdc"
--

DROP TABLE IF EXISTS "index_sdc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sdc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_se"
--

DROP TABLE IF EXISTS "index_se";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_se" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sei"
--

DROP TABLE IF EXISTS "index_sei";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sei" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sel"
--

DROP TABLE IF EXISTS "index_sel";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sel" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_seu"
--

DROP TABLE IF EXISTS "index_seu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_seu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sg"
--

DROP TABLE IF EXISTS "index_sg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sga"
--

DROP TABLE IF EXISTS "index_sga";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sga" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sh"
--

DROP TABLE IF EXISTS "index_sh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_shi"
--

DROP TABLE IF EXISTS "index_shi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_shi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_shu"
--

DROP TABLE IF EXISTS "index_shu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_shu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_si"
--

DROP TABLE IF EXISTS "index_si";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_si" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_simple"
--

DROP TABLE IF EXISTS "index_simple";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_simple" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sjd"
--

DROP TABLE IF EXISTS "index_sjd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sjd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sjn"
--

DROP TABLE IF EXISTS "index_sjn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sjn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sk"
--

DROP TABLE IF EXISTS "index_sk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sl"
--

DROP TABLE IF EXISTS "index_sl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_slovio"
--

DROP TABLE IF EXISTS "index_slovio";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_slovio" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_slovio-c"
--

DROP TABLE IF EXISTS "index_slovio-c";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_slovio-c" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_slovio-l"
--

DROP TABLE IF EXISTS "index_slovio-l";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_slovio-l" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_slovio-la"
--

DROP TABLE IF EXISTS "index_slovio-la";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_slovio-la" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_slv"
--

DROP TABLE IF EXISTS "index_slv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_slv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sma"
--

DROP TABLE IF EXISTS "index_sma";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sma" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_smn"
--

DROP TABLE IF EXISTS "index_smn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_smn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_smo"
--

DROP TABLE IF EXISTS "index_smo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_smo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sms"
--

DROP TABLE IF EXISTS "index_sms";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sms" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sn"
--

DROP TABLE IF EXISTS "index_sn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_so"
--

DROP TABLE IF EXISTS "index_so";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_so" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_solresol"
--

DROP TABLE IF EXISTS "index_solresol";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_solresol" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sot"
--

DROP TABLE IF EXISTS "index_sot";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sot" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sqi"
--

DROP TABLE IF EXISTS "index_sqi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sqi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_srn"
--

DROP TABLE IF EXISTS "index_srn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_srn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_srp"
--

DROP TABLE IF EXISTS "index_srp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_srp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ss"
--

DROP TABLE IF EXISTS "index_ss";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ss" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_st"
--

DROP TABLE IF EXISTS "index_st";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_st" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_stq"
--

DROP TABLE IF EXISTS "index_stq";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_stq" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_su"
--

DROP TABLE IF EXISTS "index_su";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_su" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sux"
--

DROP TABLE IF EXISTS "index_sux";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sux" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sv"
--

DROP TABLE IF EXISTS "index_sv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sva"
--

DROP TABLE IF EXISTS "index_sva";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sva" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_sw"
--

DROP TABLE IF EXISTS "index_sw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_sw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_syc"
--

DROP TABLE IF EXISTS "index_syc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_syc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_szl"
--

DROP TABLE IF EXISTS "index_szl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_szl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ta"
--

DROP TABLE IF EXISTS "index_ta";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ta" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tab"
--

DROP TABLE IF EXISTS "index_tab";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tab" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tah"
--

DROP TABLE IF EXISTS "index_tah";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tah" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tat"
--

DROP TABLE IF EXISTS "index_tat";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tat" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tcs"
--

DROP TABLE IF EXISTS "index_tcs";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tcs" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tcy"
--

DROP TABLE IF EXISTS "index_tcy";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tcy" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_te"
--

DROP TABLE IF EXISTS "index_te";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_te" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tet"
--

DROP TABLE IF EXISTS "index_tet";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tet" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tgk"
--

DROP TABLE IF EXISTS "index_tgk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tgk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tgl"
--

DROP TABLE IF EXISTS "index_tgl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tgl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_th"
--

DROP TABLE IF EXISTS "index_th";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_th" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tig"
--

DROP TABLE IF EXISTS "index_tig";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tig" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tir"
--

DROP TABLE IF EXISTS "index_tir";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tir" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tix"
--

DROP TABLE IF EXISTS "index_tix";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tix" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tk"
--

DROP TABLE IF EXISTS "index_tk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tkl"
--

DROP TABLE IF EXISTS "index_tkl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tkl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tlh"
--

DROP TABLE IF EXISTS "index_tlh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tlh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tly"
--

DROP TABLE IF EXISTS "index_tly";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tly" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_to"
--

DROP TABLE IF EXISTS "index_to";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_to" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tokipona"
--

DROP TABLE IF EXISTS "index_tokipona";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tokipona" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ton"
--

DROP TABLE IF EXISTS "index_ton";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ton" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tp"
--

DROP TABLE IF EXISTS "index_tp";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tp" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tpc"
--

DROP TABLE IF EXISTS "index_tpc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tpc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tpi"
--

DROP TABLE IF EXISTS "index_tpi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tpi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tpn"
--

DROP TABLE IF EXISTS "index_tpn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tpn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tr"
--

DROP TABLE IF EXISTS "index_tr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_translingual"
--

DROP TABLE IF EXISTS "index_translingual";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_translingual" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tru"
--

DROP TABLE IF EXISTS "index_tru";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tru" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tsn"
--

DROP TABLE IF EXISTS "index_tsn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tsn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tso"
--

DROP TABLE IF EXISTS "index_tso";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tso" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ttt"
--

DROP TABLE IF EXISTS "index_ttt";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ttt" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tum"
--

DROP TABLE IF EXISTS "index_tum";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tum" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tup"
--

DROP TABLE IF EXISTS "index_tup";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tup" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tvl"
--

DROP TABLE IF EXISTS "index_tvl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tvl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_twf"
--

DROP TABLE IF EXISTS "index_twf";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_twf" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_twi"
--

DROP TABLE IF EXISTS "index_twi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_twi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tyv"
--

DROP TABLE IF EXISTS "index_tyv";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tyv" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tzj"
--

DROP TABLE IF EXISTS "index_tzj";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tzj" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_tzm"
--

DROP TABLE IF EXISTS "index_tzm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_tzm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_uby"
--

DROP TABLE IF EXISTS "index_uby";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_uby" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_udi"
--

DROP TABLE IF EXISTS "index_udi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_udi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_udm"
--

DROP TABLE IF EXISTS "index_udm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_udm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ug"
--

DROP TABLE IF EXISTS "index_ug";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ug" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_uga"
--

DROP TABLE IF EXISTS "index_uga";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_uga" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_uk"
--

DROP TABLE IF EXISTS "index_uk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_uk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ulc"
--

DROP TABLE IF EXISTS "index_ulc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ulc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ulk"
--

DROP TABLE IF EXISTS "index_ulk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ulk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ur"
--

DROP TABLE IF EXISTS "index_ur";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ur" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_uz"
--

DROP TABLE IF EXISTS "index_uz";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_uz" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_val"
--

DROP TABLE IF EXISTS "index_val";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_val" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ve"
--

DROP TABLE IF EXISTS "index_ve";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ve" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vec"
--

DROP TABLE IF EXISTS "index_vec";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vec" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vep"
--

DROP TABLE IF EXISTS "index_vep";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vep" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vi"
--

DROP TABLE IF EXISTS "index_vi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vls"
--

DROP TABLE IF EXISTS "index_vls";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vls" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vma"
--

DROP TABLE IF EXISTS "index_vma";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vma" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vol"
--

DROP TABLE IF EXISTS "index_vol";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vol" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vot"
--

DROP TABLE IF EXISTS "index_vot";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vot" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_vro"
--

DROP TABLE IF EXISTS "index_vro";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_vro" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wa"
--

DROP TABLE IF EXISTS "index_wa";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wa" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wad"
--

DROP TABLE IF EXISTS "index_wad";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wad" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_war"
--

DROP TABLE IF EXISTS "index_war";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_war" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wim"
--

DROP TABLE IF EXISTS "index_wim";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wim" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wlm"
--

DROP TABLE IF EXISTS "index_wlm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wlm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wo"
--

DROP TABLE IF EXISTS "index_wo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wrh"
--

DROP TABLE IF EXISTS "index_wrh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wrh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wuu"
--

DROP TABLE IF EXISTS "index_wuu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wuu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_wyb"
--

DROP TABLE IF EXISTS "index_wyb";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_wyb" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xal"
--

DROP TABLE IF EXISTS "index_xal";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xal" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xbc"
--

DROP TABLE IF EXISTS "index_xbc";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xbc" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xbm"
--

DROP TABLE IF EXISTS "index_xbm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xbm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xcl"
--

DROP TABLE IF EXISTS "index_xcl";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xcl" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xho"
--

DROP TABLE IF EXISTS "index_xho";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xho" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xmf"
--

DROP TABLE IF EXISTS "index_xmf";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xmf" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xno"
--

DROP TABLE IF EXISTS "index_xno";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xno" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xrn"
--

DROP TABLE IF EXISTS "index_xrn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xrn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xsm"
--

DROP TABLE IF EXISTS "index_xsm";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xsm" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xsr"
--

DROP TABLE IF EXISTS "index_xsr";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xsr" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xto"
--

DROP TABLE IF EXISTS "index_xto";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xto" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_xvn"
--

DROP TABLE IF EXISTS "index_xvn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_xvn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ydd"
--

DROP TABLE IF EXISTS "index_ydd";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ydd" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yi"
--

DROP TABLE IF EXISTS "index_yi";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yi" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ykg"
--

DROP TABLE IF EXISTS "index_ykg";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ykg" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yo"
--

DROP TABLE IF EXISTS "index_yo";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yo" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yrk"
--

DROP TABLE IF EXISTS "index_yrk";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yrk" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yua"
--

DROP TABLE IF EXISTS "index_yua";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yua" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yue"
--

DROP TABLE IF EXISTS "index_yue";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yue" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_yux"
--

DROP TABLE IF EXISTS "index_yux";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_yux" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_za"
--

DROP TABLE IF EXISTS "index_za";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_za" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zai"
--

DROP TABLE IF EXISTS "index_zai";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zai" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_ze"
--

DROP TABLE IF EXISTS "index_ze";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_ze" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zea"
--

DROP TABLE IF EXISTS "index_zea";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zea" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zh"
--

DROP TABLE IF EXISTS "index_zh";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zh" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zh-cn"
--

DROP TABLE IF EXISTS "index_zh-cn";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zh-cn" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zh-tw"
--

DROP TABLE IF EXISTS "index_zh-tw";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zh-tw" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zko"
--

DROP TABLE IF EXISTS "index_zko";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zko" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zu"
--

DROP TABLE IF EXISTS "index_zu";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zu" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "index_zza"
--

DROP TABLE IF EXISTS "index_zza";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "index_zza" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "foreign_word" varchar(255) NOT NULL,
  "foreign_has_definition" tinyint(1) NOT NULL,
  "native_page_title" varchar(255) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "inflection"
--

DROP TABLE IF EXISTS "inflection";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "inflection" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "freq" int(11) NOT NULL,
  "inflected_form" varchar(255) NOT NULL
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
  "name" varchar(255) NOT NULL,
  "code" varchar(12) NOT NULL,
  "n_foreign_POS" int(10) NOT NULL,
  "n_translations" int(10) NOT NULL
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
  "lemma" varchar(255) NOT NULL,
  "redirect_type" tinyint(3) DEFAULT NULL
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
-- Table structure for table "native_red_link"
--

DROP TABLE IF EXISTS "native_red_link";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "native_red_link" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "page_title" varchar(255) NOT NULL,
  "red_link" varchar(255) NOT NULL,
  "section_type" tinyint(3) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "page"
--

DROP TABLE IF EXISTS "page";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "page" (
  "id" int(10) NOT NULL PRIMARY KEY,
  "page_title" varchar(255) NOT NULL,
  "word_count" int(6) NOT NULL,
  "wiki_link_count" int(6) NOT NULL,
  "is_in_wiktionary" tinyint(1) DEFAULT NULL,
  "is_redirect" tinyint(1) DEFAULT NULL,
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
  "name" varchar(255) NOT NULL
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
  "relation_type_id" tinyint(3) NOT NULL,
  "meaning_summary" varchar(511) DEFAULT NULL
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
  "name" varchar(255) NOT NULL
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
  "text" varchar(1023) NOT NULL
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
  "page_title" varchar(255) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-05-08 11:26:57


-- Index list

CREATE INDEX "index_aaa_foreign_native" ON "index_aaa" ("foreign_word","native_page_title");
CREATE INDEX "index_aaa_foreign_word" ON "index_aaa" ("foreign_word");
CREATE INDEX "index_aaa_native_page_title" ON "index_aaa" ("native_page_title");
CREATE INDEX "index_aab_foreign_native" ON "index_aab" ("foreign_word","native_page_title");
CREATE INDEX "index_aab_foreign_word" ON "index_aab" ("foreign_word");
CREATE INDEX "index_aab_native_page_title" ON "index_aab" ("native_page_title");
CREATE INDEX "index_aae_foreign_native" ON "index_aae" ("foreign_word","native_page_title");
CREATE INDEX "index_aae_foreign_word" ON "index_aae" ("foreign_word");
CREATE INDEX "index_aae_native_page_title" ON "index_aae" ("native_page_title");
CREATE INDEX "index_aak_foreign_native" ON "index_aak" ("foreign_word","native_page_title");
CREATE INDEX "index_aak_foreign_word" ON "index_aak" ("foreign_word");
CREATE INDEX "index_aak_native_page_title" ON "index_aak" ("native_page_title");
CREATE INDEX "index_aao_foreign_native" ON "index_aao" ("foreign_word","native_page_title");
CREATE INDEX "index_aao_foreign_word" ON "index_aao" ("foreign_word");
CREATE INDEX "index_aao_native_page_title" ON "index_aao" ("native_page_title");
CREATE INDEX "index_aar_foreign_native" ON "index_aar" ("foreign_word","native_page_title");
CREATE INDEX "index_aar_foreign_word" ON "index_aar" ("foreign_word");
CREATE INDEX "index_aar_native_page_title" ON "index_aar" ("native_page_title");
CREATE INDEX "index_aat_foreign_native" ON "index_aat" ("foreign_word","native_page_title");
CREATE INDEX "index_aat_foreign_word" ON "index_aat" ("foreign_word");
CREATE INDEX "index_aat_native_page_title" ON "index_aat" ("native_page_title");
CREATE INDEX "index_abe_foreign_native" ON "index_abe" ("foreign_word","native_page_title");
CREATE INDEX "index_abe_foreign_word" ON "index_abe" ("foreign_word");
CREATE INDEX "index_abe_native_page_title" ON "index_abe" ("native_page_title");
CREATE INDEX "index_abk_foreign_native" ON "index_abk" ("foreign_word","native_page_title");
CREATE INDEX "index_abk_foreign_word" ON "index_abk" ("foreign_word");
CREATE INDEX "index_abk_native_page_title" ON "index_abk" ("native_page_title");
CREATE INDEX "index_abl_foreign_native" ON "index_abl" ("foreign_word","native_page_title");
CREATE INDEX "index_abl_foreign_word" ON "index_abl" ("foreign_word");
CREATE INDEX "index_abl_native_page_title" ON "index_abl" ("native_page_title");
CREATE INDEX "index_abm_foreign_native" ON "index_abm" ("foreign_word","native_page_title");
CREATE INDEX "index_abm_foreign_word" ON "index_abm" ("foreign_word");
CREATE INDEX "index_abm_native_page_title" ON "index_abm" ("native_page_title");
CREATE INDEX "index_abq_foreign_native" ON "index_abq" ("foreign_word","native_page_title");
CREATE INDEX "index_abq_foreign_word" ON "index_abq" ("foreign_word");
CREATE INDEX "index_abq_native_page_title" ON "index_abq" ("native_page_title");
CREATE INDEX "index_abs_foreign_native" ON "index_abs" ("foreign_word","native_page_title");
CREATE INDEX "index_abs_foreign_word" ON "index_abs" ("foreign_word");
CREATE INDEX "index_abs_native_page_title" ON "index_abs" ("native_page_title");
CREATE INDEX "index_ace_foreign_native" ON "index_ace" ("foreign_word","native_page_title");
CREATE INDEX "index_ace_foreign_word" ON "index_ace" ("foreign_word");
CREATE INDEX "index_ace_native_page_title" ON "index_ace" ("native_page_title");
CREATE INDEX "index_ach_foreign_native" ON "index_ach" ("foreign_word","native_page_title");
CREATE INDEX "index_ach_foreign_word" ON "index_ach" ("foreign_word");
CREATE INDEX "index_ach_native_page_title" ON "index_ach" ("native_page_title");
CREATE INDEX "index_acr_foreign_native" ON "index_acr" ("foreign_word","native_page_title");
CREATE INDEX "index_acr_foreign_word" ON "index_acr" ("foreign_word");
CREATE INDEX "index_acr_native_page_title" ON "index_acr" ("native_page_title");
CREATE INDEX "index_acv_foreign_native" ON "index_acv" ("foreign_word","native_page_title");
CREATE INDEX "index_acv_foreign_word" ON "index_acv" ("foreign_word");
CREATE INDEX "index_acv_native_page_title" ON "index_acv" ("native_page_title");
CREATE INDEX "index_acx_foreign_native" ON "index_acx" ("foreign_word","native_page_title");
CREATE INDEX "index_acx_foreign_word" ON "index_acx" ("foreign_word");
CREATE INDEX "index_acx_native_page_title" ON "index_acx" ("native_page_title");
CREATE INDEX "index_ada_foreign_native" ON "index_ada" ("foreign_word","native_page_title");
CREATE INDEX "index_ada_foreign_word" ON "index_ada" ("foreign_word");
CREATE INDEX "index_ada_native_page_title" ON "index_ada" ("native_page_title");
CREATE INDEX "index_ade_foreign_native" ON "index_ade" ("foreign_word","native_page_title");
CREATE INDEX "index_ade_foreign_word" ON "index_ade" ("foreign_word");
CREATE INDEX "index_ade_native_page_title" ON "index_ade" ("native_page_title");
CREATE INDEX "index_adj_foreign_native" ON "index_adj" ("foreign_word","native_page_title");
CREATE INDEX "index_adj_foreign_word" ON "index_adj" ("foreign_word");
CREATE INDEX "index_adj_native_page_title" ON "index_adj" ("native_page_title");
CREATE INDEX "index_adt_foreign_native" ON "index_adt" ("foreign_word","native_page_title");
CREATE INDEX "index_adt_foreign_word" ON "index_adt" ("foreign_word");
CREATE INDEX "index_adt_native_page_title" ON "index_adt" ("native_page_title");
CREATE INDEX "index_ady_foreign_native" ON "index_ady" ("foreign_word","native_page_title");
CREATE INDEX "index_ady_foreign_word" ON "index_ady" ("foreign_word");
CREATE INDEX "index_ady_native_page_title" ON "index_ady" ("native_page_title");
CREATE INDEX "index_adz_foreign_native" ON "index_adz" ("foreign_word","native_page_title");
CREATE INDEX "index_adz_foreign_word" ON "index_adz" ("foreign_word");
CREATE INDEX "index_adz_native_page_title" ON "index_adz" ("native_page_title");
CREATE INDEX "index_aeb_foreign_native" ON "index_aeb" ("foreign_word","native_page_title");
CREATE INDEX "index_aeb_foreign_word" ON "index_aeb" ("foreign_word");
CREATE INDEX "index_aeb_native_page_title" ON "index_aeb" ("native_page_title");
CREATE INDEX "index_afb_foreign_native" ON "index_afb" ("foreign_word","native_page_title");
CREATE INDEX "index_afb_foreign_word" ON "index_afb" ("foreign_word");
CREATE INDEX "index_afb_native_page_title" ON "index_afb" ("native_page_title");
CREATE INDEX "index_afr_foreign_native" ON "index_afr" ("foreign_word","native_page_title");
CREATE INDEX "index_afr_foreign_word" ON "index_afr" ("foreign_word");
CREATE INDEX "index_afr_native_page_title" ON "index_afr" ("native_page_title");
CREATE INDEX "index_agf_foreign_native" ON "index_agf" ("foreign_word","native_page_title");
CREATE INDEX "index_agf_foreign_word" ON "index_agf" ("foreign_word");
CREATE INDEX "index_agf_native_page_title" ON "index_agf" ("native_page_title");
CREATE INDEX "index_agj_foreign_native" ON "index_agj" ("foreign_word","native_page_title");
CREATE INDEX "index_agj_foreign_word" ON "index_agj" ("foreign_word");
CREATE INDEX "index_agj_native_page_title" ON "index_agj" ("native_page_title");
CREATE INDEX "index_agx_foreign_native" ON "index_agx" ("foreign_word","native_page_title");
CREATE INDEX "index_agx_foreign_word" ON "index_agx" ("foreign_word");
CREATE INDEX "index_agx_native_page_title" ON "index_agx" ("native_page_title");
CREATE INDEX "index_ahs_foreign_native" ON "index_ahs" ("foreign_word","native_page_title");
CREATE INDEX "index_ahs_foreign_word" ON "index_ahs" ("foreign_word");
CREATE INDEX "index_ahs_native_page_title" ON "index_ahs" ("native_page_title");
CREATE INDEX "index_aie_foreign_native" ON "index_aie" ("foreign_word","native_page_title");
CREATE INDEX "index_aie_foreign_word" ON "index_aie" ("foreign_word");
CREATE INDEX "index_aie_native_page_title" ON "index_aie" ("native_page_title");
CREATE INDEX "index_aii_foreign_native" ON "index_aii" ("foreign_word","native_page_title");
CREATE INDEX "index_aii_foreign_word" ON "index_aii" ("foreign_word");
CREATE INDEX "index_aii_native_page_title" ON "index_aii" ("native_page_title");
CREATE INDEX "index_ain_foreign_native" ON "index_ain" ("foreign_word","native_page_title");
CREATE INDEX "index_ain_foreign_word" ON "index_ain" ("foreign_word");
CREATE INDEX "index_ain_native_page_title" ON "index_ain" ("native_page_title");
CREATE INDEX "index_ain.kana_foreign_native" ON "index_ain.kana" ("foreign_word","native_page_title");
CREATE INDEX "index_ain.kana_foreign_word" ON "index_ain.kana" ("foreign_word");
CREATE INDEX "index_ain.kana_native_page_title" ON "index_ain.kana" ("native_page_title");
CREATE INDEX "index_ain.lat_foreign_native" ON "index_ain.lat" ("foreign_word","native_page_title");
CREATE INDEX "index_ain.lat_foreign_word" ON "index_ain.lat" ("foreign_word");
CREATE INDEX "index_ain.lat_native_page_title" ON "index_ain.lat" ("native_page_title");
CREATE INDEX "index_aiw_foreign_native" ON "index_aiw" ("foreign_word","native_page_title");
CREATE INDEX "index_aiw_foreign_word" ON "index_aiw" ("foreign_word");
CREATE INDEX "index_aiw_native_page_title" ON "index_aiw" ("native_page_title");
CREATE INDEX "index_aja_foreign_native" ON "index_aja" ("foreign_word","native_page_title");
CREATE INDEX "index_aja_foreign_word" ON "index_aja" ("foreign_word");
CREATE INDEX "index_aja_native_page_title" ON "index_aja" ("native_page_title");
CREATE INDEX "index_ajg_foreign_native" ON "index_ajg" ("foreign_word","native_page_title");
CREATE INDEX "index_ajg_foreign_word" ON "index_ajg" ("foreign_word");
CREATE INDEX "index_ajg_native_page_title" ON "index_ajg" ("native_page_title");
CREATE INDEX "index_aji_foreign_native" ON "index_aji" ("foreign_word","native_page_title");
CREATE INDEX "index_aji_foreign_word" ON "index_aji" ("foreign_word");
CREATE INDEX "index_aji_native_page_title" ON "index_aji" ("native_page_title");
CREATE INDEX "index_ajp_foreign_native" ON "index_ajp" ("foreign_word","native_page_title");
CREATE INDEX "index_ajp_foreign_word" ON "index_ajp" ("foreign_word");
CREATE INDEX "index_ajp_native_page_title" ON "index_ajp" ("native_page_title");
CREATE INDEX "index_aka_foreign_native" ON "index_aka" ("foreign_word","native_page_title");
CREATE INDEX "index_aka_foreign_word" ON "index_aka" ("foreign_word");
CREATE INDEX "index_aka_native_page_title" ON "index_aka" ("native_page_title");
CREATE INDEX "index_ake_foreign_native" ON "index_ake" ("foreign_word","native_page_title");
CREATE INDEX "index_ake_foreign_word" ON "index_ake" ("foreign_word");
CREATE INDEX "index_ake_native_page_title" ON "index_ake" ("native_page_title");
CREATE INDEX "index_akg_foreign_native" ON "index_akg" ("foreign_word","native_page_title");
CREATE INDEX "index_akg_foreign_word" ON "index_akg" ("foreign_word");
CREATE INDEX "index_akg_native_page_title" ON "index_akg" ("native_page_title");
CREATE INDEX "index_akk_foreign_native" ON "index_akk" ("foreign_word","native_page_title");
CREATE INDEX "index_akk_foreign_word" ON "index_akk" ("foreign_word");
CREATE INDEX "index_akk_native_page_title" ON "index_akk" ("native_page_title");
CREATE INDEX "index_akl_foreign_native" ON "index_akl" ("foreign_word","native_page_title");
CREATE INDEX "index_akl_foreign_word" ON "index_akl" ("foreign_word");
CREATE INDEX "index_akl_native_page_title" ON "index_akl" ("native_page_title");
CREATE INDEX "index_akz_foreign_native" ON "index_akz" ("foreign_word","native_page_title");
CREATE INDEX "index_akz_foreign_word" ON "index_akz" ("foreign_word");
CREATE INDEX "index_akz_native_page_title" ON "index_akz" ("native_page_title");
CREATE INDEX "index_alc_foreign_native" ON "index_alc" ("foreign_word","native_page_title");
CREATE INDEX "index_alc_foreign_word" ON "index_alc" ("foreign_word");
CREATE INDEX "index_alc_native_page_title" ON "index_alc" ("native_page_title");
CREATE INDEX "index_ale_foreign_native" ON "index_ale" ("foreign_word","native_page_title");
CREATE INDEX "index_ale_foreign_word" ON "index_ale" ("foreign_word");
CREATE INDEX "index_ale_native_page_title" ON "index_ale" ("native_page_title");
CREATE INDEX "index_ali_foreign_native" ON "index_ali" ("foreign_word","native_page_title");
CREATE INDEX "index_ali_foreign_word" ON "index_ali" ("foreign_word");
CREATE INDEX "index_ali_native_page_title" ON "index_ali" ("native_page_title");
CREATE INDEX "index_aln_foreign_native" ON "index_aln" ("foreign_word","native_page_title");
CREATE INDEX "index_aln_foreign_word" ON "index_aln" ("foreign_word");
CREATE INDEX "index_aln_native_page_title" ON "index_aln" ("native_page_title");
CREATE INDEX "index_alp_foreign_native" ON "index_alp" ("foreign_word","native_page_title");
CREATE INDEX "index_alp_foreign_word" ON "index_alp" ("foreign_word");
CREATE INDEX "index_alp_native_page_title" ON "index_alp" ("native_page_title");
CREATE INDEX "index_alq_foreign_native" ON "index_alq" ("foreign_word","native_page_title");
CREATE INDEX "index_alq_foreign_word" ON "index_alq" ("foreign_word");
CREATE INDEX "index_alq_native_page_title" ON "index_alq" ("native_page_title");
CREATE INDEX "index_alr_foreign_native" ON "index_alr" ("foreign_word","native_page_title");
CREATE INDEX "index_alr_foreign_word" ON "index_alr" ("foreign_word");
CREATE INDEX "index_alr_native_page_title" ON "index_alr" ("native_page_title");
CREATE INDEX "index_als_foreign_native" ON "index_als" ("foreign_word","native_page_title");
CREATE INDEX "index_als_foreign_word" ON "index_als" ("foreign_word");
CREATE INDEX "index_als_native_page_title" ON "index_als" ("native_page_title");
CREATE INDEX "index_alt_foreign_native" ON "index_alt" ("foreign_word","native_page_title");
CREATE INDEX "index_alt_foreign_word" ON "index_alt" ("foreign_word");
CREATE INDEX "index_alt_native_page_title" ON "index_alt" ("native_page_title");
CREATE INDEX "index_alu_foreign_native" ON "index_alu" ("foreign_word","native_page_title");
CREATE INDEX "index_alu_foreign_word" ON "index_alu" ("foreign_word");
CREATE INDEX "index_alu_native_page_title" ON "index_alu" ("native_page_title");
CREATE INDEX "index_am_foreign_native" ON "index_am" ("foreign_word","native_page_title");
CREATE INDEX "index_am_foreign_word" ON "index_am" ("foreign_word");
CREATE INDEX "index_am_native_page_title" ON "index_am" ("native_page_title");
CREATE INDEX "index_amk_foreign_native" ON "index_amk" ("foreign_word","native_page_title");
CREATE INDEX "index_amk_foreign_word" ON "index_amk" ("foreign_word");
CREATE INDEX "index_amk_native_page_title" ON "index_amk" ("native_page_title");
CREATE INDEX "index_amn_foreign_native" ON "index_amn" ("foreign_word","native_page_title");
CREATE INDEX "index_amn_foreign_word" ON "index_amn" ("foreign_word");
CREATE INDEX "index_amn_native_page_title" ON "index_amn" ("native_page_title");
CREATE INDEX "index_ams_foreign_native" ON "index_ams" ("foreign_word","native_page_title");
CREATE INDEX "index_ams_foreign_word" ON "index_ams" ("foreign_word");
CREATE INDEX "index_ams_native_page_title" ON "index_ams" ("native_page_title");
CREATE INDEX "index_amt_foreign_native" ON "index_amt" ("foreign_word","native_page_title");
CREATE INDEX "index_amt_foreign_word" ON "index_amt" ("foreign_word");
CREATE INDEX "index_amt_native_page_title" ON "index_amt" ("native_page_title");
CREATE INDEX "index_amu_foreign_native" ON "index_amu" ("foreign_word","native_page_title");
CREATE INDEX "index_amu_foreign_word" ON "index_amu" ("foreign_word");
CREATE INDEX "index_amu_native_page_title" ON "index_amu" ("native_page_title");
CREATE INDEX "index_and_foreign_native" ON "index_and" ("foreign_word","native_page_title");
CREATE INDEX "index_and_foreign_word" ON "index_and" ("foreign_word");
CREATE INDEX "index_and_native_page_title" ON "index_and" ("native_page_title");
CREATE INDEX "index_ang_foreign_native" ON "index_ang" ("foreign_word","native_page_title");
CREATE INDEX "index_ang_foreign_word" ON "index_ang" ("foreign_word");
CREATE INDEX "index_ang_native_page_title" ON "index_ang" ("native_page_title");
CREATE INDEX "index_anp_foreign_native" ON "index_anp" ("foreign_word","native_page_title");
CREATE INDEX "index_anp_foreign_word" ON "index_anp" ("foreign_word");
CREATE INDEX "index_anp_native_page_title" ON "index_anp" ("native_page_title");
CREATE INDEX "index_ant_foreign_native" ON "index_ant" ("foreign_word","native_page_title");
CREATE INDEX "index_ant_foreign_word" ON "index_ant" ("foreign_word");
CREATE INDEX "index_ant_native_page_title" ON "index_ant" ("native_page_title");
CREATE INDEX "index_apc_foreign_native" ON "index_apc" ("foreign_word","native_page_title");
CREATE INDEX "index_apc_foreign_word" ON "index_apc" ("foreign_word");
CREATE INDEX "index_apc_native_page_title" ON "index_apc" ("native_page_title");
CREATE INDEX "index_apd_foreign_native" ON "index_apd" ("foreign_word","native_page_title");
CREATE INDEX "index_apd_foreign_word" ON "index_apd" ("foreign_word");
CREATE INDEX "index_apd_native_page_title" ON "index_apd" ("native_page_title");
CREATE INDEX "index_apj_foreign_native" ON "index_apj" ("foreign_word","native_page_title");
CREATE INDEX "index_apj_foreign_word" ON "index_apj" ("foreign_word");
CREATE INDEX "index_apj_native_page_title" ON "index_apj" ("native_page_title");
CREATE INDEX "index_apk_foreign_native" ON "index_apk" ("foreign_word","native_page_title");
CREATE INDEX "index_apk_foreign_word" ON "index_apk" ("foreign_word");
CREATE INDEX "index_apk_native_page_title" ON "index_apk" ("native_page_title");
CREATE INDEX "index_apl_foreign_native" ON "index_apl" ("foreign_word","native_page_title");
CREATE INDEX "index_apl_foreign_word" ON "index_apl" ("foreign_word");
CREATE INDEX "index_apl_native_page_title" ON "index_apl" ("native_page_title");
CREATE INDEX "index_apm_foreign_native" ON "index_apm" ("foreign_word","native_page_title");
CREATE INDEX "index_apm_foreign_word" ON "index_apm" ("foreign_word");
CREATE INDEX "index_apm_native_page_title" ON "index_apm" ("native_page_title");
CREATE INDEX "index_apw_foreign_native" ON "index_apw" ("foreign_word","native_page_title");
CREATE INDEX "index_apw_foreign_word" ON "index_apw" ("foreign_word");
CREATE INDEX "index_apw_native_page_title" ON "index_apw" ("native_page_title");
CREATE INDEX "index_apy_foreign_native" ON "index_apy" ("foreign_word","native_page_title");
CREATE INDEX "index_apy_foreign_word" ON "index_apy" ("foreign_word");
CREATE INDEX "index_apy_native_page_title" ON "index_apy" ("native_page_title");
CREATE INDEX "index_aqc_foreign_native" ON "index_aqc" ("foreign_word","native_page_title");
CREATE INDEX "index_aqc_foreign_word" ON "index_aqc" ("foreign_word");
CREATE INDEX "index_aqc_native_page_title" ON "index_aqc" ("native_page_title");
CREATE INDEX "index_ara_foreign_native" ON "index_ara" ("foreign_word","native_page_title");
CREATE INDEX "index_ara_foreign_word" ON "index_ara" ("foreign_word");
CREATE INDEX "index_ara_native_page_title" ON "index_ara" ("native_page_title");
CREATE INDEX "index_arb_foreign_native" ON "index_arb" ("foreign_word","native_page_title");
CREATE INDEX "index_arb_foreign_word" ON "index_arb" ("foreign_word");
CREATE INDEX "index_arb_native_page_title" ON "index_arb" ("native_page_title");
CREATE INDEX "index_arc_foreign_native" ON "index_arc" ("foreign_word","native_page_title");
CREATE INDEX "index_arc_foreign_word" ON "index_arc" ("foreign_word");
CREATE INDEX "index_arc_native_page_title" ON "index_arc" ("native_page_title");
CREATE INDEX "index_are_foreign_native" ON "index_are" ("foreign_word","native_page_title");
CREATE INDEX "index_are_foreign_word" ON "index_are" ("foreign_word");
CREATE INDEX "index_are_native_page_title" ON "index_are" ("native_page_title");
CREATE INDEX "index_arg_foreign_native" ON "index_arg" ("foreign_word","native_page_title");
CREATE INDEX "index_arg_foreign_word" ON "index_arg" ("foreign_word");
CREATE INDEX "index_arg_native_page_title" ON "index_arg" ("native_page_title");
CREATE INDEX "index_arn_foreign_native" ON "index_arn" ("foreign_word","native_page_title");
CREATE INDEX "index_arn_foreign_word" ON "index_arn" ("foreign_word");
CREATE INDEX "index_arn_native_page_title" ON "index_arn" ("native_page_title");
CREATE INDEX "index_arp_foreign_native" ON "index_arp" ("foreign_word","native_page_title");
CREATE INDEX "index_arp_foreign_word" ON "index_arp" ("foreign_word");
CREATE INDEX "index_arp_native_page_title" ON "index_arp" ("native_page_title");
CREATE INDEX "index_arq_foreign_native" ON "index_arq" ("foreign_word","native_page_title");
CREATE INDEX "index_arq_foreign_word" ON "index_arq" ("foreign_word");
CREATE INDEX "index_arq_native_page_title" ON "index_arq" ("native_page_title");
CREATE INDEX "index_ars_foreign_native" ON "index_ars" ("foreign_word","native_page_title");
CREATE INDEX "index_ars_foreign_word" ON "index_ars" ("foreign_word");
CREATE INDEX "index_ars_native_page_title" ON "index_ars" ("native_page_title");
CREATE INDEX "index_art_foreign_native" ON "index_art" ("foreign_word","native_page_title");
CREATE INDEX "index_art_foreign_word" ON "index_art" ("foreign_word");
CREATE INDEX "index_art_native_page_title" ON "index_art" ("native_page_title");
CREATE INDEX "index_art-oou_foreign_native" ON "index_art-oou" ("foreign_word","native_page_title");
CREATE INDEX "index_art-oou_foreign_word" ON "index_art-oou" ("foreign_word");
CREATE INDEX "index_art-oou_native_page_title" ON "index_art-oou" ("native_page_title");
CREATE INDEX "index_aru_foreign_native" ON "index_aru" ("foreign_word","native_page_title");
CREATE INDEX "index_aru_foreign_word" ON "index_aru" ("foreign_word");
CREATE INDEX "index_aru_native_page_title" ON "index_aru" ("native_page_title");
CREATE INDEX "index_arw_foreign_native" ON "index_arw" ("foreign_word","native_page_title");
CREATE INDEX "index_arw_foreign_word" ON "index_arw" ("foreign_word");
CREATE INDEX "index_arw_native_page_title" ON "index_arw" ("native_page_title");
CREATE INDEX "index_ary_foreign_native" ON "index_ary" ("foreign_word","native_page_title");
CREATE INDEX "index_ary_foreign_word" ON "index_ary" ("foreign_word");
CREATE INDEX "index_ary_native_page_title" ON "index_ary" ("native_page_title");
CREATE INDEX "index_arz_foreign_native" ON "index_arz" ("foreign_word","native_page_title");
CREATE INDEX "index_arz_foreign_word" ON "index_arz" ("foreign_word");
CREATE INDEX "index_arz_native_page_title" ON "index_arz" ("native_page_title");
CREATE INDEX "index_ase_foreign_native" ON "index_ase" ("foreign_word","native_page_title");
CREATE INDEX "index_ase_foreign_word" ON "index_ase" ("foreign_word");
CREATE INDEX "index_ase_native_page_title" ON "index_ase" ("native_page_title");
CREATE INDEX "index_asm_foreign_native" ON "index_asm" ("foreign_word","native_page_title");
CREATE INDEX "index_asm_foreign_word" ON "index_asm" ("foreign_word");
CREATE INDEX "index_asm_native_page_title" ON "index_asm" ("native_page_title");
CREATE INDEX "index_ast_foreign_native" ON "index_ast" ("foreign_word","native_page_title");
CREATE INDEX "index_ast_foreign_word" ON "index_ast" ("foreign_word");
CREATE INDEX "index_ast_native_page_title" ON "index_ast" ("native_page_title");
CREATE INDEX "index_atv_foreign_native" ON "index_atv" ("foreign_word","native_page_title");
CREATE INDEX "index_atv_foreign_word" ON "index_atv" ("foreign_word");
CREATE INDEX "index_atv_native_page_title" ON "index_atv" ("native_page_title");
CREATE INDEX "index_aty_foreign_native" ON "index_aty" ("foreign_word","native_page_title");
CREATE INDEX "index_aty_foreign_word" ON "index_aty" ("foreign_word");
CREATE INDEX "index_aty_native_page_title" ON "index_aty" ("native_page_title");
CREATE INDEX "index_av_foreign_native" ON "index_av" ("foreign_word","native_page_title");
CREATE INDEX "index_av_foreign_word" ON "index_av" ("foreign_word");
CREATE INDEX "index_av_native_page_title" ON "index_av" ("native_page_title");
CREATE INDEX "index_ave_foreign_native" ON "index_ave" ("foreign_word","native_page_title");
CREATE INDEX "index_ave_foreign_word" ON "index_ave" ("foreign_word");
CREATE INDEX "index_ave_native_page_title" ON "index_ave" ("native_page_title");
CREATE INDEX "index_avk_foreign_native" ON "index_avk" ("foreign_word","native_page_title");
CREATE INDEX "index_avk_foreign_word" ON "index_avk" ("foreign_word");
CREATE INDEX "index_avk_native_page_title" ON "index_avk" ("native_page_title");
CREATE INDEX "index_awa_foreign_native" ON "index_awa" ("foreign_word","native_page_title");
CREATE INDEX "index_awa_foreign_word" ON "index_awa" ("foreign_word");
CREATE INDEX "index_awa_native_page_title" ON "index_awa" ("native_page_title");
CREATE INDEX "index_awk_foreign_native" ON "index_awk" ("foreign_word","native_page_title");
CREATE INDEX "index_awk_foreign_word" ON "index_awk" ("foreign_word");
CREATE INDEX "index_awk_native_page_title" ON "index_awk" ("native_page_title");
CREATE INDEX "index_axm_foreign_native" ON "index_axm" ("foreign_word","native_page_title");
CREATE INDEX "index_axm_foreign_word" ON "index_axm" ("foreign_word");
CREATE INDEX "index_axm_native_page_title" ON "index_axm" ("native_page_title");
CREATE INDEX "index_ay_foreign_native" ON "index_ay" ("foreign_word","native_page_title");
CREATE INDEX "index_ay_foreign_word" ON "index_ay" ("foreign_word");
CREATE INDEX "index_ay_native_page_title" ON "index_ay" ("native_page_title");
CREATE INDEX "index_ayl_foreign_native" ON "index_ayl" ("foreign_word","native_page_title");
CREATE INDEX "index_ayl_foreign_word" ON "index_ayl" ("foreign_word");
CREATE INDEX "index_ayl_native_page_title" ON "index_ayl" ("native_page_title");
CREATE INDEX "index_ayp_foreign_native" ON "index_ayp" ("foreign_word","native_page_title");
CREATE INDEX "index_ayp_foreign_word" ON "index_ayp" ("foreign_word");
CREATE INDEX "index_ayp_native_page_title" ON "index_ayp" ("native_page_title");
CREATE INDEX "index_az_foreign_native" ON "index_az" ("foreign_word","native_page_title");
CREATE INDEX "index_az_foreign_word" ON "index_az" ("foreign_word");
CREATE INDEX "index_az_native_page_title" ON "index_az" ("native_page_title");
CREATE INDEX "index_ba_foreign_native" ON "index_ba" ("foreign_word","native_page_title");
CREATE INDEX "index_ba_foreign_word" ON "index_ba" ("foreign_word");
CREATE INDEX "index_ba_native_page_title" ON "index_ba" ("native_page_title");
CREATE INDEX "index_bagua_foreign_native" ON "index_bagua" ("foreign_word","native_page_title");
CREATE INDEX "index_bagua_foreign_word" ON "index_bagua" ("foreign_word");
CREATE INDEX "index_bagua_native_page_title" ON "index_bagua" ("native_page_title");
CREATE INDEX "index_bal_foreign_native" ON "index_bal" ("foreign_word","native_page_title");
CREATE INDEX "index_bal_foreign_word" ON "index_bal" ("foreign_word");
CREATE INDEX "index_bal_native_page_title" ON "index_bal" ("native_page_title");
CREATE INDEX "index_ban_foreign_native" ON "index_ban" ("foreign_word","native_page_title");
CREATE INDEX "index_ban_foreign_word" ON "index_ban" ("foreign_word");
CREATE INDEX "index_ban_native_page_title" ON "index_ban" ("native_page_title");
CREATE INDEX "index_bar_foreign_native" ON "index_bar" ("foreign_word","native_page_title");
CREATE INDEX "index_bar_foreign_word" ON "index_bar" ("foreign_word");
CREATE INDEX "index_bar_native_page_title" ON "index_bar" ("native_page_title");
CREATE INDEX "index_bas_foreign_native" ON "index_bas" ("foreign_word","native_page_title");
CREATE INDEX "index_bas_foreign_word" ON "index_bas" ("foreign_word");
CREATE INDEX "index_bas_native_page_title" ON "index_bas" ("native_page_title");
CREATE INDEX "index_bat-smg_foreign_native" ON "index_bat-smg" ("foreign_word","native_page_title");
CREATE INDEX "index_bat-smg_foreign_word" ON "index_bat-smg" ("foreign_word");
CREATE INDEX "index_bat-smg_native_page_title" ON "index_bat-smg" ("native_page_title");
CREATE INDEX "index_bcc_foreign_native" ON "index_bcc" ("foreign_word","native_page_title");
CREATE INDEX "index_bcc_foreign_word" ON "index_bcc" ("foreign_word");
CREATE INDEX "index_bcc_native_page_title" ON "index_bcc" ("native_page_title");
CREATE INDEX "index_bcl_foreign_native" ON "index_bcl" ("foreign_word","native_page_title");
CREATE INDEX "index_bcl_foreign_word" ON "index_bcl" ("foreign_word");
CREATE INDEX "index_bcl_native_page_title" ON "index_bcl" ("native_page_title");
CREATE INDEX "index_bdk_foreign_native" ON "index_bdk" ("foreign_word","native_page_title");
CREATE INDEX "index_bdk_foreign_word" ON "index_bdk" ("foreign_word");
CREATE INDEX "index_bdk_native_page_title" ON "index_bdk" ("native_page_title");
CREATE INDEX "index_bdp_foreign_native" ON "index_bdp" ("foreign_word","native_page_title");
CREATE INDEX "index_bdp_foreign_word" ON "index_bdp" ("foreign_word");
CREATE INDEX "index_bdp_native_page_title" ON "index_bdp" ("native_page_title");
CREATE INDEX "index_bdy_foreign_native" ON "index_bdy" ("foreign_word","native_page_title");
CREATE INDEX "index_bdy_foreign_word" ON "index_bdy" ("foreign_word");
CREATE INDEX "index_bdy_native_page_title" ON "index_bdy" ("native_page_title");
CREATE INDEX "index_be_foreign_native" ON "index_be" ("foreign_word","native_page_title");
CREATE INDEX "index_be_foreign_word" ON "index_be" ("foreign_word");
CREATE INDEX "index_be_native_page_title" ON "index_be" ("native_page_title");
CREATE INDEX "index_bej_foreign_native" ON "index_bej" ("foreign_word","native_page_title");
CREATE INDEX "index_bej_foreign_word" ON "index_bej" ("foreign_word");
CREATE INDEX "index_bej_native_page_title" ON "index_bej" ("native_page_title");
CREATE INDEX "index_bem_foreign_native" ON "index_bem" ("foreign_word","native_page_title");
CREATE INDEX "index_bem_foreign_word" ON "index_bem" ("foreign_word");
CREATE INDEX "index_bem_native_page_title" ON "index_bem" ("native_page_title");
CREATE INDEX "index_bew_foreign_native" ON "index_bew" ("foreign_word","native_page_title");
CREATE INDEX "index_bew_foreign_word" ON "index_bew" ("foreign_word");
CREATE INDEX "index_bew_native_page_title" ON "index_bew" ("native_page_title");
CREATE INDEX "index_bft_foreign_native" ON "index_bft" ("foreign_word","native_page_title");
CREATE INDEX "index_bft_foreign_word" ON "index_bft" ("foreign_word");
CREATE INDEX "index_bft_native_page_title" ON "index_bft" ("native_page_title");
CREATE INDEX "index_bg_foreign_native" ON "index_bg" ("foreign_word","native_page_title");
CREATE INDEX "index_bg_foreign_word" ON "index_bg" ("foreign_word");
CREATE INDEX "index_bg_native_page_title" ON "index_bg" ("native_page_title");
CREATE INDEX "index_bgc_foreign_native" ON "index_bgc" ("foreign_word","native_page_title");
CREATE INDEX "index_bgc_foreign_word" ON "index_bgc" ("foreign_word");
CREATE INDEX "index_bgc_native_page_title" ON "index_bgc" ("native_page_title");
CREATE INDEX "index_bh_foreign_native" ON "index_bh" ("foreign_word","native_page_title");
CREATE INDEX "index_bh_foreign_word" ON "index_bh" ("foreign_word");
CREATE INDEX "index_bh_native_page_title" ON "index_bh" ("native_page_title");
CREATE INDEX "index_bho_foreign_native" ON "index_bho" ("foreign_word","native_page_title");
CREATE INDEX "index_bho_foreign_word" ON "index_bho" ("foreign_word");
CREATE INDEX "index_bho_native_page_title" ON "index_bho" ("native_page_title");
CREATE INDEX "index_bhw_foreign_native" ON "index_bhw" ("foreign_word","native_page_title");
CREATE INDEX "index_bhw_foreign_word" ON "index_bhw" ("foreign_word");
CREATE INDEX "index_bhw_native_page_title" ON "index_bhw" ("native_page_title");
CREATE INDEX "index_bi_foreign_native" ON "index_bi" ("foreign_word","native_page_title");
CREATE INDEX "index_bi_foreign_word" ON "index_bi" ("foreign_word");
CREATE INDEX "index_bi_native_page_title" ON "index_bi" ("native_page_title");
CREATE INDEX "index_bib_foreign_native" ON "index_bib" ("foreign_word","native_page_title");
CREATE INDEX "index_bib_foreign_word" ON "index_bib" ("foreign_word");
CREATE INDEX "index_bib_native_page_title" ON "index_bib" ("native_page_title");
CREATE INDEX "index_bik_foreign_native" ON "index_bik" ("foreign_word","native_page_title");
CREATE INDEX "index_bik_foreign_word" ON "index_bik" ("foreign_word");
CREATE INDEX "index_bik_native_page_title" ON "index_bik" ("native_page_title");
CREATE INDEX "index_bin_foreign_native" ON "index_bin" ("foreign_word","native_page_title");
CREATE INDEX "index_bin_foreign_word" ON "index_bin" ("foreign_word");
CREATE INDEX "index_bin_native_page_title" ON "index_bin" ("native_page_title");
CREATE INDEX "index_bjz_foreign_native" ON "index_bjz" ("foreign_word","native_page_title");
CREATE INDEX "index_bjz_foreign_word" ON "index_bjz" ("foreign_word");
CREATE INDEX "index_bjz_native_page_title" ON "index_bjz" ("native_page_title");
CREATE INDEX "index_bku_foreign_native" ON "index_bku" ("foreign_word","native_page_title");
CREATE INDEX "index_bku_foreign_word" ON "index_bku" ("foreign_word");
CREATE INDEX "index_bku_native_page_title" ON "index_bku" ("native_page_title");
CREATE INDEX "index_bla_foreign_native" ON "index_bla" ("foreign_word","native_page_title");
CREATE INDEX "index_bla_foreign_word" ON "index_bla" ("foreign_word");
CREATE INDEX "index_bla_native_page_title" ON "index_bla" ("native_page_title");
CREATE INDEX "index_bm_foreign_native" ON "index_bm" ("foreign_word","native_page_title");
CREATE INDEX "index_bm_foreign_word" ON "index_bm" ("foreign_word");
CREATE INDEX "index_bm_native_page_title" ON "index_bm" ("native_page_title");
CREATE INDEX "index_bn_foreign_native" ON "index_bn" ("foreign_word","native_page_title");
CREATE INDEX "index_bn_foreign_word" ON "index_bn" ("foreign_word");
CREATE INDEX "index_bn_native_page_title" ON "index_bn" ("native_page_title");
CREATE INDEX "index_bo_foreign_native" ON "index_bo" ("foreign_word","native_page_title");
CREATE INDEX "index_bo_foreign_word" ON "index_bo" ("foreign_word");
CREATE INDEX "index_bo_native_page_title" ON "index_bo" ("native_page_title");
CREATE INDEX "index_bph_foreign_native" ON "index_bph" ("foreign_word","native_page_title");
CREATE INDEX "index_bph_foreign_word" ON "index_bph" ("foreign_word");
CREATE INDEX "index_bph_native_page_title" ON "index_bph" ("native_page_title");
CREATE INDEX "index_bpy_foreign_native" ON "index_bpy" ("foreign_word","native_page_title");
CREATE INDEX "index_bpy_foreign_word" ON "index_bpy" ("foreign_word");
CREATE INDEX "index_bpy_native_page_title" ON "index_bpy" ("native_page_title");
CREATE INDEX "index_bqi_foreign_native" ON "index_bqi" ("foreign_word","native_page_title");
CREATE INDEX "index_bqi_foreign_word" ON "index_bqi" ("foreign_word");
CREATE INDEX "index_bqi_native_page_title" ON "index_bqi" ("native_page_title");
CREATE INDEX "index_br_foreign_native" ON "index_br" ("foreign_word","native_page_title");
CREATE INDEX "index_br_foreign_word" ON "index_br" ("foreign_word");
CREATE INDEX "index_br_native_page_title" ON "index_br" ("native_page_title");
CREATE INDEX "index_brc_foreign_native" ON "index_brc" ("foreign_word","native_page_title");
CREATE INDEX "index_brc_foreign_word" ON "index_brc" ("foreign_word");
CREATE INDEX "index_brc_native_page_title" ON "index_brc" ("native_page_title");
CREATE INDEX "index_brh_foreign_native" ON "index_brh" ("foreign_word","native_page_title");
CREATE INDEX "index_brh_foreign_word" ON "index_brh" ("foreign_word");
CREATE INDEX "index_brh_native_page_title" ON "index_brh" ("native_page_title");
CREATE INDEX "index_bs_foreign_native" ON "index_bs" ("foreign_word","native_page_title");
CREATE INDEX "index_bs_foreign_word" ON "index_bs" ("foreign_word");
CREATE INDEX "index_bs_native_page_title" ON "index_bs" ("native_page_title");
CREATE INDEX "index_btk_foreign_native" ON "index_btk" ("foreign_word","native_page_title");
CREATE INDEX "index_btk_foreign_word" ON "index_btk" ("foreign_word");
CREATE INDEX "index_btk_native_page_title" ON "index_btk" ("native_page_title");
CREATE INDEX "index_bto_foreign_native" ON "index_bto" ("foreign_word","native_page_title");
CREATE INDEX "index_bto_foreign_word" ON "index_bto" ("foreign_word");
CREATE INDEX "index_bto_native_page_title" ON "index_bto" ("native_page_title");
CREATE INDEX "index_bua_foreign_native" ON "index_bua" ("foreign_word","native_page_title");
CREATE INDEX "index_bua_foreign_word" ON "index_bua" ("foreign_word");
CREATE INDEX "index_bua_native_page_title" ON "index_bua" ("native_page_title");
CREATE INDEX "index_bug_foreign_native" ON "index_bug" ("foreign_word","native_page_title");
CREATE INDEX "index_bug_foreign_word" ON "index_bug" ("foreign_word");
CREATE INDEX "index_bug_native_page_title" ON "index_bug" ("native_page_title");
CREATE INDEX "index_bxr_foreign_native" ON "index_bxr" ("foreign_word","native_page_title");
CREATE INDEX "index_bxr_foreign_word" ON "index_bxr" ("foreign_word");
CREATE INDEX "index_bxr_native_page_title" ON "index_bxr" ("native_page_title");
CREATE INDEX "index_byn_foreign_native" ON "index_byn" ("foreign_word","native_page_title");
CREATE INDEX "index_byn_foreign_word" ON "index_byn" ("foreign_word");
CREATE INDEX "index_byn_native_page_title" ON "index_byn" ("native_page_title");
CREATE INDEX "index_ca_foreign_native" ON "index_ca" ("foreign_word","native_page_title");
CREATE INDEX "index_ca_foreign_word" ON "index_ca" ("foreign_word");
CREATE INDEX "index_ca_native_page_title" ON "index_ca" ("native_page_title");
CREATE INDEX "index_caa_foreign_native" ON "index_caa" ("foreign_word","native_page_title");
CREATE INDEX "index_caa_foreign_word" ON "index_caa" ("foreign_word");
CREATE INDEX "index_caa_native_page_title" ON "index_caa" ("native_page_title");
CREATE INDEX "index_cab_foreign_native" ON "index_cab" ("foreign_word","native_page_title");
CREATE INDEX "index_cab_foreign_word" ON "index_cab" ("foreign_word");
CREATE INDEX "index_cab_native_page_title" ON "index_cab" ("native_page_title");
CREATE INDEX "index_cbk-zam_foreign_native" ON "index_cbk-zam" ("foreign_word","native_page_title");
CREATE INDEX "index_cbk-zam_foreign_word" ON "index_cbk-zam" ("foreign_word");
CREATE INDEX "index_cbk-zam_native_page_title" ON "index_cbk-zam" ("native_page_title");
CREATE INDEX "index_ccc_foreign_native" ON "index_ccc" ("foreign_word","native_page_title");
CREATE INDEX "index_ccc_foreign_word" ON "index_ccc" ("foreign_word");
CREATE INDEX "index_ccc_native_page_title" ON "index_ccc" ("native_page_title");
CREATE INDEX "index_cdo_foreign_native" ON "index_cdo" ("foreign_word","native_page_title");
CREATE INDEX "index_cdo_foreign_word" ON "index_cdo" ("foreign_word");
CREATE INDEX "index_cdo_native_page_title" ON "index_cdo" ("native_page_title");
CREATE INDEX "index_ce_foreign_native" ON "index_ce" ("foreign_word","native_page_title");
CREATE INDEX "index_ce_foreign_word" ON "index_ce" ("foreign_word");
CREATE INDEX "index_ce_native_page_title" ON "index_ce" ("native_page_title");
CREATE INDEX "index_ceb_foreign_native" ON "index_ceb" ("foreign_word","native_page_title");
CREATE INDEX "index_ceb_foreign_word" ON "index_ceb" ("foreign_word");
CREATE INDEX "index_ceb_native_page_title" ON "index_ceb" ("native_page_title");
CREATE INDEX "index_cel_foreign_native" ON "index_cel" ("foreign_word","native_page_title");
CREATE INDEX "index_cel_foreign_word" ON "index_cel" ("foreign_word");
CREATE INDEX "index_cel_native_page_title" ON "index_cel" ("native_page_title");
CREATE INDEX "index_ch_foreign_native" ON "index_ch" ("foreign_word","native_page_title");
CREATE INDEX "index_ch_foreign_word" ON "index_ch" ("foreign_word");
CREATE INDEX "index_ch_native_page_title" ON "index_ch" ("native_page_title");
CREATE INDEX "index_chc_foreign_native" ON "index_chc" ("foreign_word","native_page_title");
CREATE INDEX "index_chc_foreign_word" ON "index_chc" ("foreign_word");
CREATE INDEX "index_chc_native_page_title" ON "index_chc" ("native_page_title");
CREATE INDEX "index_chg_foreign_native" ON "index_chg" ("foreign_word","native_page_title");
CREATE INDEX "index_chg_foreign_word" ON "index_chg" ("foreign_word");
CREATE INDEX "index_chg_native_page_title" ON "index_chg" ("native_page_title");
CREATE INDEX "index_chk_foreign_native" ON "index_chk" ("foreign_word","native_page_title");
CREATE INDEX "index_chk_foreign_word" ON "index_chk" ("foreign_word");
CREATE INDEX "index_chk_native_page_title" ON "index_chk" ("native_page_title");
CREATE INDEX "index_chm_foreign_native" ON "index_chm" ("foreign_word","native_page_title");
CREATE INDEX "index_chm_foreign_word" ON "index_chm" ("foreign_word");
CREATE INDEX "index_chm_native_page_title" ON "index_chm" ("native_page_title");
CREATE INDEX "index_cho_foreign_native" ON "index_cho" ("foreign_word","native_page_title");
CREATE INDEX "index_cho_foreign_word" ON "index_cho" ("foreign_word");
CREATE INDEX "index_cho_native_page_title" ON "index_cho" ("native_page_title");
CREATE INDEX "index_chr_foreign_native" ON "index_chr" ("foreign_word","native_page_title");
CREATE INDEX "index_chr_foreign_word" ON "index_chr" ("foreign_word");
CREATE INDEX "index_chr_native_page_title" ON "index_chr" ("native_page_title");
CREATE INDEX "index_chu_foreign_native" ON "index_chu" ("foreign_word","native_page_title");
CREATE INDEX "index_chu_foreign_word" ON "index_chu" ("foreign_word");
CREATE INDEX "index_chu_native_page_title" ON "index_chu" ("native_page_title");
CREATE INDEX "index_chu-ru_foreign_native" ON "index_chu-ru" ("foreign_word","native_page_title");
CREATE INDEX "index_chu-ru_foreign_word" ON "index_chu-ru" ("foreign_word");
CREATE INDEX "index_chu-ru_native_page_title" ON "index_chu-ru" ("native_page_title");
CREATE INDEX "index_chu.cyr_foreign_native" ON "index_chu.cyr" ("foreign_word","native_page_title");
CREATE INDEX "index_chu.cyr_foreign_word" ON "index_chu.cyr" ("foreign_word");
CREATE INDEX "index_chu.cyr_native_page_title" ON "index_chu.cyr" ("native_page_title");
CREATE INDEX "index_chu.glag_foreign_native" ON "index_chu.glag" ("foreign_word","native_page_title");
CREATE INDEX "index_chu.glag_foreign_word" ON "index_chu.glag" ("foreign_word");
CREATE INDEX "index_chu.glag_native_page_title" ON "index_chu.glag" ("native_page_title");
CREATE INDEX "index_chy_foreign_native" ON "index_chy" ("foreign_word","native_page_title");
CREATE INDEX "index_chy_foreign_word" ON "index_chy" ("foreign_word");
CREATE INDEX "index_chy_native_page_title" ON "index_chy" ("native_page_title");
CREATE INDEX "index_cic_foreign_native" ON "index_cic" ("foreign_word","native_page_title");
CREATE INDEX "index_cic_foreign_word" ON "index_cic" ("foreign_word");
CREATE INDEX "index_cic_native_page_title" ON "index_cic" ("native_page_title");
CREATE INDEX "index_cjs_foreign_native" ON "index_cjs" ("foreign_word","native_page_title");
CREATE INDEX "index_cjs_foreign_word" ON "index_cjs" ("foreign_word");
CREATE INDEX "index_cjs_native_page_title" ON "index_cjs" ("native_page_title");
CREATE INDEX "index_ckt_foreign_native" ON "index_ckt" ("foreign_word","native_page_title");
CREATE INDEX "index_ckt_foreign_word" ON "index_ckt" ("foreign_word");
CREATE INDEX "index_ckt_native_page_title" ON "index_ckt" ("native_page_title");
CREATE INDEX "index_cmn_foreign_native" ON "index_cmn" ("foreign_word","native_page_title");
CREATE INDEX "index_cmn_foreign_word" ON "index_cmn" ("foreign_word");
CREATE INDEX "index_cmn_native_page_title" ON "index_cmn" ("native_page_title");
CREATE INDEX "index_co_foreign_native" ON "index_co" ("foreign_word","native_page_title");
CREATE INDEX "index_co_foreign_word" ON "index_co" ("foreign_word");
CREATE INDEX "index_co_native_page_title" ON "index_co" ("native_page_title");
CREATE INDEX "index_cop_foreign_native" ON "index_cop" ("foreign_word","native_page_title");
CREATE INDEX "index_cop_foreign_word" ON "index_cop" ("foreign_word");
CREATE INDEX "index_cop_native_page_title" ON "index_cop" ("native_page_title");
CREATE INDEX "index_cr_foreign_native" ON "index_cr" ("foreign_word","native_page_title");
CREATE INDEX "index_cr_foreign_word" ON "index_cr" ("foreign_word");
CREATE INDEX "index_cr_native_page_title" ON "index_cr" ("native_page_title");
CREATE INDEX "index_crh_foreign_native" ON "index_crh" ("foreign_word","native_page_title");
CREATE INDEX "index_crh_foreign_word" ON "index_crh" ("foreign_word");
CREATE INDEX "index_crh_native_page_title" ON "index_crh" ("native_page_title");
CREATE INDEX "index_crh-latn_foreign_native" ON "index_crh-latn" ("foreign_word","native_page_title");
CREATE INDEX "index_crh-latn_foreign_word" ON "index_crh-latn" ("foreign_word");
CREATE INDEX "index_crh-latn_native_page_title" ON "index_crh-latn" ("native_page_title");
CREATE INDEX "index_cs_foreign_native" ON "index_cs" ("foreign_word","native_page_title");
CREATE INDEX "index_cs_foreign_word" ON "index_cs" ("foreign_word");
CREATE INDEX "index_cs_native_page_title" ON "index_cs" ("native_page_title");
CREATE INDEX "index_csb_foreign_native" ON "index_csb" ("foreign_word","native_page_title");
CREATE INDEX "index_csb_foreign_word" ON "index_csb" ("foreign_word");
CREATE INDEX "index_csb_native_page_title" ON "index_csb" ("native_page_title");
CREATE INDEX "index_cu_foreign_native" ON "index_cu" ("foreign_word","native_page_title");
CREATE INDEX "index_cu_foreign_word" ON "index_cu" ("foreign_word");
CREATE INDEX "index_cu_native_page_title" ON "index_cu" ("native_page_title");
CREATE INDEX "index_cui_foreign_native" ON "index_cui" ("foreign_word","native_page_title");
CREATE INDEX "index_cui_foreign_word" ON "index_cui" ("foreign_word");
CREATE INDEX "index_cui_native_page_title" ON "index_cui" ("native_page_title");
CREATE INDEX "index_cuk_foreign_native" ON "index_cuk" ("foreign_word","native_page_title");
CREATE INDEX "index_cuk_foreign_word" ON "index_cuk" ("foreign_word");
CREATE INDEX "index_cuk_native_page_title" ON "index_cuk" ("native_page_title");
CREATE INDEX "index_cv_foreign_native" ON "index_cv" ("foreign_word","native_page_title");
CREATE INDEX "index_cv_foreign_word" ON "index_cv" ("foreign_word");
CREATE INDEX "index_cv_native_page_title" ON "index_cv" ("native_page_title");
CREATE INDEX "index_cy_foreign_native" ON "index_cy" ("foreign_word","native_page_title");
CREATE INDEX "index_cy_foreign_word" ON "index_cy" ("foreign_word");
CREATE INDEX "index_cy_native_page_title" ON "index_cy" ("native_page_title");
CREATE INDEX "index_da_foreign_native" ON "index_da" ("foreign_word","native_page_title");
CREATE INDEX "index_da_foreign_word" ON "index_da" ("foreign_word");
CREATE INDEX "index_da_native_page_title" ON "index_da" ("native_page_title");
CREATE INDEX "index_dar_foreign_native" ON "index_dar" ("foreign_word","native_page_title");
CREATE INDEX "index_dar_foreign_word" ON "index_dar" ("foreign_word");
CREATE INDEX "index_dar_native_page_title" ON "index_dar" ("native_page_title");
CREATE INDEX "index_de_foreign_native" ON "index_de" ("foreign_word","native_page_title");
CREATE INDEX "index_de_foreign_word" ON "index_de" ("foreign_word");
CREATE INDEX "index_de_native_page_title" ON "index_de" ("native_page_title");
CREATE INDEX "index_de-a_foreign_native" ON "index_de-a" ("foreign_word","native_page_title");
CREATE INDEX "index_de-a_foreign_word" ON "index_de-a" ("foreign_word");
CREATE INDEX "index_de-a_native_page_title" ON "index_de-a" ("native_page_title");
CREATE INDEX "index_de-formal_foreign_native" ON "index_de-formal" ("foreign_word","native_page_title");
CREATE INDEX "index_de-formal_foreign_word" ON "index_de-formal" ("foreign_word");
CREATE INDEX "index_de-formal_native_page_title" ON "index_de-formal" ("native_page_title");
CREATE INDEX "index_dif_foreign_native" ON "index_dif" ("foreign_word","native_page_title");
CREATE INDEX "index_dif_foreign_word" ON "index_dif" ("foreign_word");
CREATE INDEX "index_dif_native_page_title" ON "index_dif" ("native_page_title");
CREATE INDEX "index_diq_foreign_native" ON "index_diq" ("foreign_word","native_page_title");
CREATE INDEX "index_diq_foreign_word" ON "index_diq" ("foreign_word");
CREATE INDEX "index_diq_native_page_title" ON "index_diq" ("native_page_title");
CREATE INDEX "index_dk_foreign_native" ON "index_dk" ("foreign_word","native_page_title");
CREATE INDEX "index_dk_foreign_word" ON "index_dk" ("foreign_word");
CREATE INDEX "index_dk_native_page_title" ON "index_dk" ("native_page_title");
CREATE INDEX "index_dlg_foreign_native" ON "index_dlg" ("foreign_word","native_page_title");
CREATE INDEX "index_dlg_foreign_word" ON "index_dlg" ("foreign_word");
CREATE INDEX "index_dlg_native_page_title" ON "index_dlg" ("native_page_title");
CREATE INDEX "index_dng_foreign_native" ON "index_dng" ("foreign_word","native_page_title");
CREATE INDEX "index_dng_foreign_word" ON "index_dng" ("foreign_word");
CREATE INDEX "index_dng_native_page_title" ON "index_dng" ("native_page_title");
CREATE INDEX "index_drw_foreign_native" ON "index_drw" ("foreign_word","native_page_title");
CREATE INDEX "index_drw_foreign_word" ON "index_drw" ("foreign_word");
CREATE INDEX "index_drw_native_page_title" ON "index_drw" ("native_page_title");
CREATE INDEX "index_dsb_foreign_native" ON "index_dsb" ("foreign_word","native_page_title");
CREATE INDEX "index_dsb_foreign_word" ON "index_dsb" ("foreign_word");
CREATE INDEX "index_dsb_native_page_title" ON "index_dsb" ("native_page_title");
CREATE INDEX "index_duj_foreign_native" ON "index_duj" ("foreign_word","native_page_title");
CREATE INDEX "index_duj_foreign_word" ON "index_duj" ("foreign_word");
CREATE INDEX "index_duj_native_page_title" ON "index_duj" ("native_page_title");
CREATE INDEX "index_dum_foreign_native" ON "index_dum" ("foreign_word","native_page_title");
CREATE INDEX "index_dum_foreign_word" ON "index_dum" ("foreign_word");
CREATE INDEX "index_dum_native_page_title" ON "index_dum" ("native_page_title");
CREATE INDEX "index_dv_foreign_native" ON "index_dv" ("foreign_word","native_page_title");
CREATE INDEX "index_dv_foreign_word" ON "index_dv" ("foreign_word");
CREATE INDEX "index_dv_native_page_title" ON "index_dv" ("native_page_title");
CREATE INDEX "index_dz_foreign_native" ON "index_dz" ("foreign_word","native_page_title");
CREATE INDEX "index_dz_foreign_word" ON "index_dz" ("foreign_word");
CREATE INDEX "index_dz_native_page_title" ON "index_dz" ("native_page_title");
CREATE INDEX "index_egy_foreign_native" ON "index_egy" ("foreign_word","native_page_title");
CREATE INDEX "index_egy_foreign_word" ON "index_egy" ("foreign_word");
CREATE INDEX "index_egy_native_page_title" ON "index_egy" ("native_page_title");
CREATE INDEX "index_el_foreign_native" ON "index_el" ("foreign_word","native_page_title");
CREATE INDEX "index_el_foreign_word" ON "index_el" ("foreign_word");
CREATE INDEX "index_el_native_page_title" ON "index_el" ("native_page_title");
CREATE INDEX "index_eml_foreign_native" ON "index_eml" ("foreign_word","native_page_title");
CREATE INDEX "index_eml_foreign_word" ON "index_eml" ("foreign_word");
CREATE INDEX "index_eml_native_page_title" ON "index_eml" ("native_page_title");
CREATE INDEX "index_en_foreign_native" ON "index_en" ("foreign_word","native_page_title");
CREATE INDEX "index_en_foreign_word" ON "index_en" ("foreign_word");
CREATE INDEX "index_en_native_page_title" ON "index_en" ("native_page_title");
CREATE INDEX "index_en-au_foreign_native" ON "index_en-au" ("foreign_word","native_page_title");
CREATE INDEX "index_en-au_foreign_word" ON "index_en-au" ("foreign_word");
CREATE INDEX "index_en-au_native_page_title" ON "index_en-au" ("native_page_title");
CREATE INDEX "index_en-gb_foreign_native" ON "index_en-gb" ("foreign_word","native_page_title");
CREATE INDEX "index_en-gb_foreign_word" ON "index_en-gb" ("foreign_word");
CREATE INDEX "index_en-gb_native_page_title" ON "index_en-gb" ("native_page_title");
CREATE INDEX "index_en-nz_foreign_native" ON "index_en-nz" ("foreign_word","native_page_title");
CREATE INDEX "index_en-nz_foreign_word" ON "index_en-nz" ("foreign_word");
CREATE INDEX "index_en-nz_native_page_title" ON "index_en-nz" ("native_page_title");
CREATE INDEX "index_en-us_foreign_native" ON "index_en-us" ("foreign_word","native_page_title");
CREATE INDEX "index_en-us_foreign_word" ON "index_en-us" ("foreign_word");
CREATE INDEX "index_en-us_native_page_title" ON "index_en-us" ("native_page_title");
CREATE INDEX "index_enm_foreign_native" ON "index_enm" ("foreign_word","native_page_title");
CREATE INDEX "index_enm_foreign_word" ON "index_enm" ("foreign_word");
CREATE INDEX "index_enm_native_page_title" ON "index_enm" ("native_page_title");
CREATE INDEX "index_eo_foreign_native" ON "index_eo" ("foreign_word","native_page_title");
CREATE INDEX "index_eo_foreign_word" ON "index_eo" ("foreign_word");
CREATE INDEX "index_eo_native_page_title" ON "index_eo" ("native_page_title");
CREATE INDEX "index_es_foreign_native" ON "index_es" ("foreign_word","native_page_title");
CREATE INDEX "index_es_foreign_word" ON "index_es" ("foreign_word");
CREATE INDEX "index_es_native_page_title" ON "index_es" ("native_page_title");
CREATE INDEX "index_et_foreign_native" ON "index_et" ("foreign_word","native_page_title");
CREATE INDEX "index_et_foreign_word" ON "index_et" ("foreign_word");
CREATE INDEX "index_et_native_page_title" ON "index_et" ("native_page_title");
CREATE INDEX "index_eu_foreign_native" ON "index_eu" ("foreign_word","native_page_title");
CREATE INDEX "index_eu_foreign_word" ON "index_eu" ("foreign_word");
CREATE INDEX "index_eu_native_page_title" ON "index_eu" ("native_page_title");
CREATE INDEX "index_eve_foreign_native" ON "index_eve" ("foreign_word","native_page_title");
CREATE INDEX "index_eve_foreign_word" ON "index_eve" ("foreign_word");
CREATE INDEX "index_eve_native_page_title" ON "index_eve" ("native_page_title");
CREATE INDEX "index_evn_foreign_native" ON "index_evn" ("foreign_word","native_page_title");
CREATE INDEX "index_evn_foreign_word" ON "index_evn" ("foreign_word");
CREATE INDEX "index_evn_native_page_title" ON "index_evn" ("native_page_title");
CREATE INDEX "index_ewe_foreign_native" ON "index_ewe" ("foreign_word","native_page_title");
CREATE INDEX "index_ewe_foreign_word" ON "index_ewe" ("foreign_word");
CREATE INDEX "index_ewe_native_page_title" ON "index_ewe" ("native_page_title");
CREATE INDEX "index_ext_foreign_native" ON "index_ext" ("foreign_word","native_page_title");
CREATE INDEX "index_ext_foreign_word" ON "index_ext" ("foreign_word");
CREATE INDEX "index_ext_native_page_title" ON "index_ext" ("native_page_title");
CREATE INDEX "index_fa_foreign_native" ON "index_fa" ("foreign_word","native_page_title");
CREATE INDEX "index_fa_foreign_word" ON "index_fa" ("foreign_word");
CREATE INDEX "index_fa_native_page_title" ON "index_fa" ("native_page_title");
CREATE INDEX "index_fat_foreign_native" ON "index_fat" ("foreign_word","native_page_title");
CREATE INDEX "index_fat_foreign_word" ON "index_fat" ("foreign_word");
CREATE INDEX "index_fat_native_page_title" ON "index_fat" ("native_page_title");
CREATE INDEX "index_ff_foreign_native" ON "index_ff" ("foreign_word","native_page_title");
CREATE INDEX "index_ff_foreign_word" ON "index_ff" ("foreign_word");
CREATE INDEX "index_ff_native_page_title" ON "index_ff" ("native_page_title");
CREATE INDEX "index_fi_foreign_native" ON "index_fi" ("foreign_word","native_page_title");
CREATE INDEX "index_fi_foreign_word" ON "index_fi" ("foreign_word");
CREATE INDEX "index_fi_native_page_title" ON "index_fi" ("native_page_title");
CREATE INDEX "index_fic_drw_foreign_native" ON "index_fic_drw" ("foreign_word","native_page_title");
CREATE INDEX "index_fic_drw_foreign_word" ON "index_fic_drw" ("foreign_word");
CREATE INDEX "index_fic_drw_native_page_title" ON "index_fic_drw" ("native_page_title");
CREATE INDEX "index_fil_foreign_native" ON "index_fil" ("foreign_word","native_page_title");
CREATE INDEX "index_fil_foreign_word" ON "index_fil" ("foreign_word");
CREATE INDEX "index_fil_native_page_title" ON "index_fil" ("native_page_title");
CREATE INDEX "index_fiu-vro_foreign_native" ON "index_fiu-vro" ("foreign_word","native_page_title");
CREATE INDEX "index_fiu-vro_foreign_word" ON "index_fiu-vro" ("foreign_word");
CREATE INDEX "index_fiu-vro_native_page_title" ON "index_fiu-vro" ("native_page_title");
CREATE INDEX "index_fj_foreign_native" ON "index_fj" ("foreign_word","native_page_title");
CREATE INDEX "index_fj_foreign_word" ON "index_fj" ("foreign_word");
CREATE INDEX "index_fj_native_page_title" ON "index_fj" ("native_page_title");
CREATE INDEX "index_fkv_foreign_native" ON "index_fkv" ("foreign_word","native_page_title");
CREATE INDEX "index_fkv_foreign_word" ON "index_fkv" ("foreign_word");
CREATE INDEX "index_fkv_native_page_title" ON "index_fkv" ("native_page_title");
CREATE INDEX "index_fo_foreign_native" ON "index_fo" ("foreign_word","native_page_title");
CREATE INDEX "index_fo_foreign_word" ON "index_fo" ("foreign_word");
CREATE INDEX "index_fo_native_page_title" ON "index_fo" ("native_page_title");
CREATE INDEX "index_fon_foreign_native" ON "index_fon" ("foreign_word","native_page_title");
CREATE INDEX "index_fon_foreign_word" ON "index_fon" ("foreign_word");
CREATE INDEX "index_fon_native_page_title" ON "index_fon" ("native_page_title");
CREATE INDEX "index_fr_foreign_native" ON "index_fr" ("foreign_word","native_page_title");
CREATE INDEX "index_fr_foreign_word" ON "index_fr" ("foreign_word");
CREATE INDEX "index_fr_native_page_title" ON "index_fr" ("native_page_title");
CREATE INDEX "index_fr-be_foreign_native" ON "index_fr-be" ("foreign_word","native_page_title");
CREATE INDEX "index_fr-be_foreign_word" ON "index_fr-be" ("foreign_word");
CREATE INDEX "index_fr-be_native_page_title" ON "index_fr-be" ("native_page_title");
CREATE INDEX "index_fr-ch_foreign_native" ON "index_fr-ch" ("foreign_word","native_page_title");
CREATE INDEX "index_fr-ch_foreign_word" ON "index_fr-ch" ("foreign_word");
CREATE INDEX "index_fr-ch_native_page_title" ON "index_fr-ch" ("native_page_title");
CREATE INDEX "index_frc_foreign_native" ON "index_frc" ("foreign_word","native_page_title");
CREATE INDEX "index_frc_foreign_word" ON "index_frc" ("foreign_word");
CREATE INDEX "index_frc_native_page_title" ON "index_frc" ("native_page_title");
CREATE INDEX "index_frm_foreign_native" ON "index_frm" ("foreign_word","native_page_title");
CREATE INDEX "index_frm_foreign_word" ON "index_frm" ("foreign_word");
CREATE INDEX "index_frm_native_page_title" ON "index_frm" ("native_page_title");
CREATE INDEX "index_fro_foreign_native" ON "index_fro" ("foreign_word","native_page_title");
CREATE INDEX "index_fro_foreign_word" ON "index_fro" ("foreign_word");
CREATE INDEX "index_fro_native_page_title" ON "index_fro" ("native_page_title");
CREATE INDEX "index_frp_foreign_native" ON "index_frp" ("foreign_word","native_page_title");
CREATE INDEX "index_frp_foreign_word" ON "index_frp" ("foreign_word");
CREATE INDEX "index_frp_native_page_title" ON "index_frp" ("native_page_title");
CREATE INDEX "index_frr_foreign_native" ON "index_frr" ("foreign_word","native_page_title");
CREATE INDEX "index_frr_foreign_word" ON "index_frr" ("foreign_word");
CREATE INDEX "index_frr_native_page_title" ON "index_frr" ("native_page_title");
CREATE INDEX "index_frs_foreign_native" ON "index_frs" ("foreign_word","native_page_title");
CREATE INDEX "index_frs_foreign_word" ON "index_frs" ("foreign_word");
CREATE INDEX "index_frs_native_page_title" ON "index_frs" ("native_page_title");
CREATE INDEX "index_fur_foreign_native" ON "index_fur" ("foreign_word","native_page_title");
CREATE INDEX "index_fur_foreign_word" ON "index_fur" ("foreign_word");
CREATE INDEX "index_fur_native_page_title" ON "index_fur" ("native_page_title");
CREATE INDEX "index_fy_foreign_native" ON "index_fy" ("foreign_word","native_page_title");
CREATE INDEX "index_fy_foreign_word" ON "index_fy" ("foreign_word");
CREATE INDEX "index_fy_native_page_title" ON "index_fy" ("native_page_title");
CREATE INDEX "index_ga_foreign_native" ON "index_ga" ("foreign_word","native_page_title");
CREATE INDEX "index_ga_foreign_word" ON "index_ga" ("foreign_word");
CREATE INDEX "index_ga_native_page_title" ON "index_ga" ("native_page_title");
CREATE INDEX "index_gag_foreign_native" ON "index_gag" ("foreign_word","native_page_title");
CREATE INDEX "index_gag_foreign_word" ON "index_gag" ("foreign_word");
CREATE INDEX "index_gag_native_page_title" ON "index_gag" ("native_page_title");
CREATE INDEX "index_gan_foreign_native" ON "index_gan" ("foreign_word","native_page_title");
CREATE INDEX "index_gan_foreign_word" ON "index_gan" ("foreign_word");
CREATE INDEX "index_gan_native_page_title" ON "index_gan" ("native_page_title");
CREATE INDEX "index_gd_foreign_native" ON "index_gd" ("foreign_word","native_page_title");
CREATE INDEX "index_gd_foreign_word" ON "index_gd" ("foreign_word");
CREATE INDEX "index_gd_native_page_title" ON "index_gd" ("native_page_title");
CREATE INDEX "index_gl_foreign_native" ON "index_gl" ("foreign_word","native_page_title");
CREATE INDEX "index_gl_foreign_word" ON "index_gl" ("foreign_word");
CREATE INDEX "index_gl_native_page_title" ON "index_gl" ("native_page_title");
CREATE INDEX "index_gld_foreign_native" ON "index_gld" ("foreign_word","native_page_title");
CREATE INDEX "index_gld_foreign_word" ON "index_gld" ("foreign_word");
CREATE INDEX "index_gld_native_page_title" ON "index_gld" ("native_page_title");
CREATE INDEX "index_glk_foreign_native" ON "index_glk" ("foreign_word","native_page_title");
CREATE INDEX "index_glk_foreign_word" ON "index_glk" ("foreign_word");
CREATE INDEX "index_glk_native_page_title" ON "index_glk" ("native_page_title");
CREATE INDEX "index_gmh_foreign_native" ON "index_gmh" ("foreign_word","native_page_title");
CREATE INDEX "index_gmh_foreign_word" ON "index_gmh" ("foreign_word");
CREATE INDEX "index_gmh_native_page_title" ON "index_gmh" ("native_page_title");
CREATE INDEX "index_gni_foreign_native" ON "index_gni" ("foreign_word","native_page_title");
CREATE INDEX "index_gni_foreign_word" ON "index_gni" ("foreign_word");
CREATE INDEX "index_gni_native_page_title" ON "index_gni" ("native_page_title");
CREATE INDEX "index_goh_foreign_native" ON "index_goh" ("foreign_word","native_page_title");
CREATE INDEX "index_goh_foreign_word" ON "index_goh" ("foreign_word");
CREATE INDEX "index_goh_native_page_title" ON "index_goh" ("native_page_title");
CREATE INDEX "index_got_foreign_native" ON "index_got" ("foreign_word","native_page_title");
CREATE INDEX "index_got_foreign_word" ON "index_got" ("foreign_word");
CREATE INDEX "index_got_native_page_title" ON "index_got" ("native_page_title");
CREATE INDEX "index_grc_foreign_native" ON "index_grc" ("foreign_word","native_page_title");
CREATE INDEX "index_grc_foreign_word" ON "index_grc" ("foreign_word");
CREATE INDEX "index_grc_native_page_title" ON "index_grc" ("native_page_title");
CREATE INDEX "index_grc-att_foreign_native" ON "index_grc-att" ("foreign_word","native_page_title");
CREATE INDEX "index_grc-att_foreign_word" ON "index_grc-att" ("foreign_word");
CREATE INDEX "index_grc-att_native_page_title" ON "index_grc-att" ("native_page_title");
CREATE INDEX "index_grc-ion_foreign_native" ON "index_grc-ion" ("foreign_word","native_page_title");
CREATE INDEX "index_grc-ion_foreign_word" ON "index_grc-ion" ("foreign_word");
CREATE INDEX "index_grc-ion_native_page_title" ON "index_grc-ion" ("native_page_title");
CREATE INDEX "index_grn_foreign_native" ON "index_grn" ("foreign_word","native_page_title");
CREATE INDEX "index_grn_foreign_word" ON "index_grn" ("foreign_word");
CREATE INDEX "index_grn_native_page_title" ON "index_grn" ("native_page_title");
CREATE INDEX "index_gsw_foreign_native" ON "index_gsw" ("foreign_word","native_page_title");
CREATE INDEX "index_gsw_foreign_word" ON "index_gsw" ("foreign_word");
CREATE INDEX "index_gsw_native_page_title" ON "index_gsw" ("native_page_title");
CREATE INDEX "index_gu_foreign_native" ON "index_gu" ("foreign_word","native_page_title");
CREATE INDEX "index_gu_foreign_word" ON "index_gu" ("foreign_word");
CREATE INDEX "index_gu_native_page_title" ON "index_gu" ("native_page_title");
CREATE INDEX "index_gv_foreign_native" ON "index_gv" ("foreign_word","native_page_title");
CREATE INDEX "index_gv_foreign_word" ON "index_gv" ("foreign_word");
CREATE INDEX "index_gv_native_page_title" ON "index_gv" ("native_page_title");
CREATE INDEX "index_ha_foreign_native" ON "index_ha" ("foreign_word","native_page_title");
CREATE INDEX "index_ha_foreign_word" ON "index_ha" ("foreign_word");
CREATE INDEX "index_ha_native_page_title" ON "index_ha" ("native_page_title");
CREATE INDEX "index_ha.arab_foreign_native" ON "index_ha.arab" ("foreign_word","native_page_title");
CREATE INDEX "index_ha.arab_foreign_word" ON "index_ha.arab" ("foreign_word");
CREATE INDEX "index_ha.arab_native_page_title" ON "index_ha.arab" ("native_page_title");
CREATE INDEX "index_ha.lat_foreign_native" ON "index_ha.lat" ("foreign_word","native_page_title");
CREATE INDEX "index_ha.lat_foreign_word" ON "index_ha.lat" ("foreign_word");
CREATE INDEX "index_ha.lat_native_page_title" ON "index_ha.lat" ("native_page_title");
CREATE INDEX "index_hak_foreign_native" ON "index_hak" ("foreign_word","native_page_title");
CREATE INDEX "index_hak_foreign_word" ON "index_hak" ("foreign_word");
CREATE INDEX "index_hak_native_page_title" ON "index_hak" ("native_page_title");
CREATE INDEX "index_hanzi_foreign_native" ON "index_hanzi" ("foreign_word","native_page_title");
CREATE INDEX "index_hanzi_foreign_word" ON "index_hanzi" ("foreign_word");
CREATE INDEX "index_hanzi_native_page_title" ON "index_hanzi" ("native_page_title");
CREATE INDEX "index_haw_foreign_native" ON "index_haw" ("foreign_word","native_page_title");
CREATE INDEX "index_haw_foreign_word" ON "index_haw" ("foreign_word");
CREATE INDEX "index_haw_native_page_title" ON "index_haw" ("native_page_title");
CREATE INDEX "index_hbo_foreign_native" ON "index_hbo" ("foreign_word","native_page_title");
CREATE INDEX "index_hbo_foreign_word" ON "index_hbo" ("foreign_word");
CREATE INDEX "index_hbo_native_page_title" ON "index_hbo" ("native_page_title");
CREATE INDEX "index_he_foreign_native" ON "index_he" ("foreign_word","native_page_title");
CREATE INDEX "index_he_foreign_word" ON "index_he" ("foreign_word");
CREATE INDEX "index_he_native_page_title" ON "index_he" ("native_page_title");
CREATE INDEX "index_hi_foreign_native" ON "index_hi" ("foreign_word","native_page_title");
CREATE INDEX "index_hi_foreign_word" ON "index_hi" ("foreign_word");
CREATE INDEX "index_hi_native_page_title" ON "index_hi" ("native_page_title");
CREATE INDEX "index_hif_foreign_native" ON "index_hif" ("foreign_word","native_page_title");
CREATE INDEX "index_hif_foreign_word" ON "index_hif" ("foreign_word");
CREATE INDEX "index_hif_native_page_title" ON "index_hif" ("native_page_title");
CREATE INDEX "index_hil_foreign_native" ON "index_hil" ("foreign_word","native_page_title");
CREATE INDEX "index_hil_foreign_word" ON "index_hil" ("foreign_word");
CREATE INDEX "index_hil_native_page_title" ON "index_hil" ("native_page_title");
CREATE INDEX "index_hit_foreign_native" ON "index_hit" ("foreign_word","native_page_title");
CREATE INDEX "index_hit_foreign_word" ON "index_hit" ("foreign_word");
CREATE INDEX "index_hit_native_page_title" ON "index_hit" ("native_page_title");
CREATE INDEX "index_hmn_foreign_native" ON "index_hmn" ("foreign_word","native_page_title");
CREATE INDEX "index_hmn_foreign_word" ON "index_hmn" ("foreign_word");
CREATE INDEX "index_hmn_native_page_title" ON "index_hmn" ("native_page_title");
CREATE INDEX "index_ho_foreign_native" ON "index_ho" ("foreign_word","native_page_title");
CREATE INDEX "index_ho_foreign_word" ON "index_ho" ("foreign_word");
CREATE INDEX "index_ho_native_page_title" ON "index_ho" ("native_page_title");
CREATE INDEX "index_hr_foreign_native" ON "index_hr" ("foreign_word","native_page_title");
CREATE INDEX "index_hr_foreign_word" ON "index_hr" ("foreign_word");
CREATE INDEX "index_hr_native_page_title" ON "index_hr" ("native_page_title");
CREATE INDEX "index_hsb_foreign_native" ON "index_hsb" ("foreign_word","native_page_title");
CREATE INDEX "index_hsb_foreign_word" ON "index_hsb" ("foreign_word");
CREATE INDEX "index_hsb_native_page_title" ON "index_hsb" ("native_page_title");
CREATE INDEX "index_ht_foreign_native" ON "index_ht" ("foreign_word","native_page_title");
CREATE INDEX "index_ht_foreign_word" ON "index_ht" ("foreign_word");
CREATE INDEX "index_ht_native_page_title" ON "index_ht" ("native_page_title");
CREATE INDEX "index_hu_foreign_native" ON "index_hu" ("foreign_word","native_page_title");
CREATE INDEX "index_hu_foreign_word" ON "index_hu" ("foreign_word");
CREATE INDEX "index_hu_native_page_title" ON "index_hu" ("native_page_title");
CREATE INDEX "index_hy_foreign_native" ON "index_hy" ("foreign_word","native_page_title");
CREATE INDEX "index_hy_foreign_word" ON "index_hy" ("foreign_word");
CREATE INDEX "index_hy_native_page_title" ON "index_hy" ("native_page_title");
CREATE INDEX "index_hz_foreign_native" ON "index_hz" ("foreign_word","native_page_title");
CREATE INDEX "index_hz_foreign_word" ON "index_hz" ("foreign_word");
CREATE INDEX "index_hz_native_page_title" ON "index_hz" ("native_page_title");
CREATE INDEX "index_ibo_foreign_native" ON "index_ibo" ("foreign_word","native_page_title");
CREATE INDEX "index_ibo_foreign_word" ON "index_ibo" ("foreign_word");
CREATE INDEX "index_ibo_native_page_title" ON "index_ibo" ("native_page_title");
CREATE INDEX "index_id_foreign_native" ON "index_id" ("foreign_word","native_page_title");
CREATE INDEX "index_id_foreign_word" ON "index_id" ("foreign_word");
CREATE INDEX "index_id_native_page_title" ON "index_id" ("native_page_title");
CREATE INDEX "index_ie_foreign_native" ON "index_ie" ("foreign_word","native_page_title");
CREATE INDEX "index_ie_foreign_word" ON "index_ie" ("foreign_word");
CREATE INDEX "index_ie_native_page_title" ON "index_ie" ("native_page_title");
CREATE INDEX "index_ii_foreign_native" ON "index_ii" ("foreign_word","native_page_title");
CREATE INDEX "index_ii_foreign_word" ON "index_ii" ("foreign_word");
CREATE INDEX "index_ii_native_page_title" ON "index_ii" ("native_page_title");
CREATE INDEX "index_ik_foreign_native" ON "index_ik" ("foreign_word","native_page_title");
CREATE INDEX "index_ik_foreign_word" ON "index_ik" ("foreign_word");
CREATE INDEX "index_ik_native_page_title" ON "index_ik" ("native_page_title");
CREATE INDEX "index_ike-cans_foreign_native" ON "index_ike-cans" ("foreign_word","native_page_title");
CREATE INDEX "index_ike-cans_foreign_word" ON "index_ike-cans" ("foreign_word");
CREATE INDEX "index_ike-cans_native_page_title" ON "index_ike-cans" ("native_page_title");
CREATE INDEX "index_ike-latn_foreign_native" ON "index_ike-latn" ("foreign_word","native_page_title");
CREATE INDEX "index_ike-latn_foreign_word" ON "index_ike-latn" ("foreign_word");
CREATE INDEX "index_ike-latn_native_page_title" ON "index_ike-latn" ("native_page_title");
CREATE INDEX "index_ilo_foreign_native" ON "index_ilo" ("foreign_word","native_page_title");
CREATE INDEX "index_ilo_foreign_word" ON "index_ilo" ("foreign_word");
CREATE INDEX "index_ilo_native_page_title" ON "index_ilo" ("native_page_title");
CREATE INDEX "index_ina_foreign_native" ON "index_ina" ("foreign_word","native_page_title");
CREATE INDEX "index_ina_foreign_word" ON "index_ina" ("foreign_word");
CREATE INDEX "index_ina_native_page_title" ON "index_ina" ("native_page_title");
CREATE INDEX "index_inh_foreign_native" ON "index_inh" ("foreign_word","native_page_title");
CREATE INDEX "index_inh_foreign_word" ON "index_inh" ("foreign_word");
CREATE INDEX "index_inh_native_page_title" ON "index_inh" ("native_page_title");
CREATE INDEX "index_int_foreign_native" ON "index_int" ("foreign_word","native_page_title");
CREATE INDEX "index_int_foreign_word" ON "index_int" ("foreign_word");
CREATE INDEX "index_int_native_page_title" ON "index_int" ("native_page_title");
CREATE INDEX "index_io_foreign_native" ON "index_io" ("foreign_word","native_page_title");
CREATE INDEX "index_io_foreign_word" ON "index_io" ("foreign_word");
CREATE INDEX "index_io_native_page_title" ON "index_io" ("native_page_title");
CREATE INDEX "index_is_foreign_native" ON "index_is" ("foreign_word","native_page_title");
CREATE INDEX "index_is_foreign_word" ON "index_is" ("foreign_word");
CREATE INDEX "index_is_native_page_title" ON "index_is" ("native_page_title");
CREATE INDEX "index_it_foreign_native" ON "index_it" ("foreign_word","native_page_title");
CREATE INDEX "index_it_foreign_word" ON "index_it" ("foreign_word");
CREATE INDEX "index_it_native_page_title" ON "index_it" ("native_page_title");
CREATE INDEX "index_ith.lat_foreign_native" ON "index_ith.lat" ("foreign_word","native_page_title");
CREATE INDEX "index_ith.lat_foreign_word" ON "index_ith.lat" ("foreign_word");
CREATE INDEX "index_ith.lat_native_page_title" ON "index_ith.lat" ("native_page_title");
CREATE INDEX "index_itl_foreign_native" ON "index_itl" ("foreign_word","native_page_title");
CREATE INDEX "index_itl_foreign_word" ON "index_itl" ("foreign_word");
CREATE INDEX "index_itl_native_page_title" ON "index_itl" ("native_page_title");
CREATE INDEX "index_iu_foreign_native" ON "index_iu" ("foreign_word","native_page_title");
CREATE INDEX "index_iu_foreign_word" ON "index_iu" ("foreign_word");
CREATE INDEX "index_iu_native_page_title" ON "index_iu" ("native_page_title");
CREATE INDEX "index_ium_foreign_native" ON "index_ium" ("foreign_word","native_page_title");
CREATE INDEX "index_ium_foreign_word" ON "index_ium" ("foreign_word");
CREATE INDEX "index_ium_native_page_title" ON "index_ium" ("native_page_title");
CREATE INDEX "index_izh_foreign_native" ON "index_izh" ("foreign_word","native_page_title");
CREATE INDEX "index_izh_foreign_word" ON "index_izh" ("foreign_word");
CREATE INDEX "index_izh_native_page_title" ON "index_izh" ("native_page_title");
CREATE INDEX "index_ja_foreign_native" ON "index_ja" ("foreign_word","native_page_title");
CREATE INDEX "index_ja_foreign_word" ON "index_ja" ("foreign_word");
CREATE INDEX "index_ja_native_page_title" ON "index_ja" ("native_page_title");
CREATE INDEX "index_jam_foreign_native" ON "index_jam" ("foreign_word","native_page_title");
CREATE INDEX "index_jam_foreign_word" ON "index_jam" ("foreign_word");
CREATE INDEX "index_jam_native_page_title" ON "index_jam" ("native_page_title");
CREATE INDEX "index_jbo_foreign_native" ON "index_jbo" ("foreign_word","native_page_title");
CREATE INDEX "index_jbo_foreign_word" ON "index_jbo" ("foreign_word");
CREATE INDEX "index_jbo_native_page_title" ON "index_jbo" ("native_page_title");
CREATE INDEX "index_jct_foreign_native" ON "index_jct" ("foreign_word","native_page_title");
CREATE INDEX "index_jct_foreign_word" ON "index_jct" ("foreign_word");
CREATE INDEX "index_jct_native_page_title" ON "index_jct" ("native_page_title");
CREATE INDEX "index_jpr_foreign_native" ON "index_jpr" ("foreign_word","native_page_title");
CREATE INDEX "index_jpr_foreign_word" ON "index_jpr" ("foreign_word");
CREATE INDEX "index_jpr_native_page_title" ON "index_jpr" ("native_page_title");
CREATE INDEX "index_jut_foreign_native" ON "index_jut" ("foreign_word","native_page_title");
CREATE INDEX "index_jut_foreign_word" ON "index_jut" ("foreign_word");
CREATE INDEX "index_jut_native_page_title" ON "index_jut" ("native_page_title");
CREATE INDEX "index_jv_foreign_native" ON "index_jv" ("foreign_word","native_page_title");
CREATE INDEX "index_jv_foreign_word" ON "index_jv" ("foreign_word");
CREATE INDEX "index_jv_native_page_title" ON "index_jv" ("native_page_title");
CREATE INDEX "index_ka_foreign_native" ON "index_ka" ("foreign_word","native_page_title");
CREATE INDEX "index_ka_foreign_word" ON "index_ka" ("foreign_word");
CREATE INDEX "index_ka_native_page_title" ON "index_ka" ("native_page_title");
CREATE INDEX "index_kaa_foreign_native" ON "index_kaa" ("foreign_word","native_page_title");
CREATE INDEX "index_kaa_foreign_word" ON "index_kaa" ("foreign_word");
CREATE INDEX "index_kaa_native_page_title" ON "index_kaa" ("native_page_title");
CREATE INDEX "index_kab_foreign_native" ON "index_kab" ("foreign_word","native_page_title");
CREATE INDEX "index_kab_foreign_word" ON "index_kab" ("foreign_word");
CREATE INDEX "index_kab_native_page_title" ON "index_kab" ("native_page_title");
CREATE INDEX "index_kal_foreign_native" ON "index_kal" ("foreign_word","native_page_title");
CREATE INDEX "index_kal_foreign_word" ON "index_kal" ("foreign_word");
CREATE INDEX "index_kal_native_page_title" ON "index_kal" ("native_page_title");
CREATE INDEX "index_kaz_foreign_native" ON "index_kaz" ("foreign_word","native_page_title");
CREATE INDEX "index_kaz_foreign_word" ON "index_kaz" ("foreign_word");
CREATE INDEX "index_kaz_native_page_title" ON "index_kaz" ("native_page_title");
CREATE INDEX "index_kbd_foreign_native" ON "index_kbd" ("foreign_word","native_page_title");
CREATE INDEX "index_kbd_foreign_word" ON "index_kbd" ("foreign_word");
CREATE INDEX "index_kbd_native_page_title" ON "index_kbd" ("native_page_title");
CREATE INDEX "index_kca_foreign_native" ON "index_kca" ("foreign_word","native_page_title");
CREATE INDEX "index_kca_foreign_word" ON "index_kca" ("foreign_word");
CREATE INDEX "index_kca_native_page_title" ON "index_kca" ("native_page_title");
CREATE INDEX "index_kdd_foreign_native" ON "index_kdd" ("foreign_word","native_page_title");
CREATE INDEX "index_kdd_foreign_word" ON "index_kdd" ("foreign_word");
CREATE INDEX "index_kdd_native_page_title" ON "index_kdd" ("native_page_title");
CREATE INDEX "index_kdr_foreign_native" ON "index_kdr" ("foreign_word","native_page_title");
CREATE INDEX "index_kdr_foreign_word" ON "index_kdr" ("foreign_word");
CREATE INDEX "index_kdr_native_page_title" ON "index_kdr" ("native_page_title");
CREATE INDEX "index_ket_foreign_native" ON "index_ket" ("foreign_word","native_page_title");
CREATE INDEX "index_ket_foreign_word" ON "index_ket" ("foreign_word");
CREATE INDEX "index_ket_native_page_title" ON "index_ket" ("native_page_title");
CREATE INDEX "index_kg_foreign_native" ON "index_kg" ("foreign_word","native_page_title");
CREATE INDEX "index_kg_foreign_word" ON "index_kg" ("foreign_word");
CREATE INDEX "index_kg_native_page_title" ON "index_kg" ("native_page_title");
CREATE INDEX "index_khk_foreign_native" ON "index_khk" ("foreign_word","native_page_title");
CREATE INDEX "index_khk_foreign_word" ON "index_khk" ("foreign_word");
CREATE INDEX "index_khk_native_page_title" ON "index_khk" ("native_page_title");
CREATE INDEX "index_ki_foreign_native" ON "index_ki" ("foreign_word","native_page_title");
CREATE INDEX "index_ki_foreign_word" ON "index_ki" ("foreign_word");
CREATE INDEX "index_ki_native_page_title" ON "index_ki" ("native_page_title");
CREATE INDEX "index_kim_foreign_native" ON "index_kim" ("foreign_word","native_page_title");
CREATE INDEX "index_kim_foreign_word" ON "index_kim" ("foreign_word");
CREATE INDEX "index_kim_native_page_title" ON "index_kim" ("native_page_title");
CREATE INDEX "index_kiu_foreign_native" ON "index_kiu" ("foreign_word","native_page_title");
CREATE INDEX "index_kiu_foreign_word" ON "index_kiu" ("foreign_word");
CREATE INDEX "index_kiu_native_page_title" ON "index_kiu" ("native_page_title");
CREATE INDEX "index_kj_foreign_native" ON "index_kj" ("foreign_word","native_page_title");
CREATE INDEX "index_kj_foreign_word" ON "index_kj" ("foreign_word");
CREATE INDEX "index_kj_native_page_title" ON "index_kj" ("native_page_title");
CREATE INDEX "index_kjh_foreign_native" ON "index_kjh" ("foreign_word","native_page_title");
CREATE INDEX "index_kjh_foreign_word" ON "index_kjh" ("foreign_word");
CREATE INDEX "index_kjh_native_page_title" ON "index_kjh" ("native_page_title");
CREATE INDEX "index_km_foreign_native" ON "index_km" ("foreign_word","native_page_title");
CREATE INDEX "index_km_foreign_word" ON "index_km" ("foreign_word");
CREATE INDEX "index_km_native_page_title" ON "index_km" ("native_page_title");
CREATE INDEX "index_kn_foreign_native" ON "index_kn" ("foreign_word","native_page_title");
CREATE INDEX "index_kn_foreign_word" ON "index_kn" ("foreign_word");
CREATE INDEX "index_kn_native_page_title" ON "index_kn" ("native_page_title");
CREATE INDEX "index_knw_foreign_native" ON "index_knw" ("foreign_word","native_page_title");
CREATE INDEX "index_knw_foreign_word" ON "index_knw" ("foreign_word");
CREATE INDEX "index_knw_native_page_title" ON "index_knw" ("native_page_title");
CREATE INDEX "index_ko_foreign_native" ON "index_ko" ("foreign_word","native_page_title");
CREATE INDEX "index_ko_foreign_word" ON "index_ko" ("foreign_word");
CREATE INDEX "index_ko_native_page_title" ON "index_ko" ("native_page_title");
CREATE INDEX "index_kok_foreign_native" ON "index_kok" ("foreign_word","native_page_title");
CREATE INDEX "index_kok_foreign_word" ON "index_kok" ("foreign_word");
CREATE INDEX "index_kok_native_page_title" ON "index_kok" ("native_page_title");
CREATE INDEX "index_kom_foreign_native" ON "index_kom" ("foreign_word","native_page_title");
CREATE INDEX "index_kom_foreign_word" ON "index_kom" ("foreign_word");
CREATE INDEX "index_kom_native_page_title" ON "index_kom" ("native_page_title");
CREATE INDEX "index_kpy_foreign_native" ON "index_kpy" ("foreign_word","native_page_title");
CREATE INDEX "index_kpy_foreign_word" ON "index_kpy" ("foreign_word");
CREATE INDEX "index_kpy_native_page_title" ON "index_kpy" ("native_page_title");
CREATE INDEX "index_kr_foreign_native" ON "index_kr" ("foreign_word","native_page_title");
CREATE INDEX "index_kr_foreign_word" ON "index_kr" ("foreign_word");
CREATE INDEX "index_kr_native_page_title" ON "index_kr" ("native_page_title");
CREATE INDEX "index_krc_foreign_native" ON "index_krc" ("foreign_word","native_page_title");
CREATE INDEX "index_krc_foreign_word" ON "index_krc" ("foreign_word");
CREATE INDEX "index_krc_native_page_title" ON "index_krc" ("native_page_title");
CREATE INDEX "index_krh_foreign_native" ON "index_krh" ("foreign_word","native_page_title");
CREATE INDEX "index_krh_foreign_word" ON "index_krh" ("foreign_word");
CREATE INDEX "index_krh_native_page_title" ON "index_krh" ("native_page_title");
CREATE INDEX "index_kri_foreign_native" ON "index_kri" ("foreign_word","native_page_title");
CREATE INDEX "index_kri_foreign_word" ON "index_kri" ("foreign_word");
CREATE INDEX "index_kri_native_page_title" ON "index_kri" ("native_page_title");
CREATE INDEX "index_krj_foreign_native" ON "index_krj" ("foreign_word","native_page_title");
CREATE INDEX "index_krj_foreign_word" ON "index_krj" ("foreign_word");
CREATE INDEX "index_krj_native_page_title" ON "index_krj" ("native_page_title");
CREATE INDEX "index_krl_foreign_native" ON "index_krl" ("foreign_word","native_page_title");
CREATE INDEX "index_krl_foreign_word" ON "index_krl" ("foreign_word");
CREATE INDEX "index_krl_native_page_title" ON "index_krl" ("native_page_title");
CREATE INDEX "index_ks_foreign_native" ON "index_ks" ("foreign_word","native_page_title");
CREATE INDEX "index_ks_foreign_word" ON "index_ks" ("foreign_word");
CREATE INDEX "index_ks_native_page_title" ON "index_ks" ("native_page_title");
CREATE INDEX "index_ksh_foreign_native" ON "index_ksh" ("foreign_word","native_page_title");
CREATE INDEX "index_ksh_foreign_word" ON "index_ksh" ("foreign_word");
CREATE INDEX "index_ksh_native_page_title" ON "index_ksh" ("native_page_title");
CREATE INDEX "index_ksh-c-a_foreign_native" ON "index_ksh-c-a" ("foreign_word","native_page_title");
CREATE INDEX "index_ksh-c-a_foreign_word" ON "index_ksh-c-a" ("foreign_word");
CREATE INDEX "index_ksh-c-a_native_page_title" ON "index_ksh-c-a" ("native_page_title");
CREATE INDEX "index_ksh-p-b_foreign_native" ON "index_ksh-p-b" ("foreign_word","native_page_title");
CREATE INDEX "index_ksh-p-b_foreign_word" ON "index_ksh-p-b" ("foreign_word");
CREATE INDEX "index_ksh-p-b_native_page_title" ON "index_ksh-p-b" ("native_page_title");
CREATE INDEX "index_ksi_foreign_native" ON "index_ksi" ("foreign_word","native_page_title");
CREATE INDEX "index_ksi_foreign_word" ON "index_ksi" ("foreign_word");
CREATE INDEX "index_ksi_native_page_title" ON "index_ksi" ("native_page_title");
CREATE INDEX "index_ku_foreign_native" ON "index_ku" ("foreign_word","native_page_title");
CREATE INDEX "index_ku_foreign_word" ON "index_ku" ("foreign_word");
CREATE INDEX "index_ku_native_page_title" ON "index_ku" ("native_page_title");
CREATE INDEX "index_kum_foreign_native" ON "index_kum" ("foreign_word","native_page_title");
CREATE INDEX "index_kum_foreign_word" ON "index_kum" ("foreign_word");
CREATE INDEX "index_kum_native_page_title" ON "index_kum" ("native_page_title");
CREATE INDEX "index_kw_foreign_native" ON "index_kw" ("foreign_word","native_page_title");
CREATE INDEX "index_kw_foreign_word" ON "index_kw" ("foreign_word");
CREATE INDEX "index_kw_native_page_title" ON "index_kw" ("native_page_title");
CREATE INDEX "index_ky_foreign_native" ON "index_ky" ("foreign_word","native_page_title");
CREATE INDEX "index_ky_foreign_word" ON "index_ky" ("foreign_word");
CREATE INDEX "index_ky_native_page_title" ON "index_ky" ("native_page_title");
CREATE INDEX "index_kyi_foreign_native" ON "index_kyi" ("foreign_word","native_page_title");
CREATE INDEX "index_kyi_foreign_word" ON "index_kyi" ("foreign_word");
CREATE INDEX "index_kyi_native_page_title" ON "index_kyi" ("native_page_title");
CREATE INDEX "index_la_foreign_native" ON "index_la" ("foreign_word","native_page_title");
CREATE INDEX "index_la_foreign_word" ON "index_la" ("foreign_word");
CREATE INDEX "index_la_native_page_title" ON "index_la" ("native_page_title");
CREATE INDEX "index_lad_foreign_native" ON "index_lad" ("foreign_word","native_page_title");
CREATE INDEX "index_lad_foreign_word" ON "index_lad" ("foreign_word");
CREATE INDEX "index_lad_native_page_title" ON "index_lad" ("native_page_title");
CREATE INDEX "index_lah_foreign_native" ON "index_lah" ("foreign_word","native_page_title");
CREATE INDEX "index_lah_foreign_word" ON "index_lah" ("foreign_word");
CREATE INDEX "index_lah_native_page_title" ON "index_lah" ("native_page_title");
CREATE INDEX "index_lb_foreign_native" ON "index_lb" ("foreign_word","native_page_title");
CREATE INDEX "index_lb_foreign_word" ON "index_lb" ("foreign_word");
CREATE INDEX "index_lb_native_page_title" ON "index_lb" ("native_page_title");
CREATE INDEX "index_lbe_foreign_native" ON "index_lbe" ("foreign_word","native_page_title");
CREATE INDEX "index_lbe_foreign_word" ON "index_lbe" ("foreign_word");
CREATE INDEX "index_lbe_native_page_title" ON "index_lbe" ("native_page_title");
CREATE INDEX "index_letter_ru_foreign_native" ON "index_letter_ru" ("foreign_word","native_page_title");
CREATE INDEX "index_letter_ru_foreign_word" ON "index_letter_ru" ("foreign_word");
CREATE INDEX "index_letter_ru_native_page_title" ON "index_letter_ru" ("native_page_title");
CREATE INDEX "index_lez_foreign_native" ON "index_lez" ("foreign_word","native_page_title");
CREATE INDEX "index_lez_foreign_word" ON "index_lez" ("foreign_word");
CREATE INDEX "index_lez_native_page_title" ON "index_lez" ("native_page_title");
CREATE INDEX "index_lfn_foreign_native" ON "index_lfn" ("foreign_word","native_page_title");
CREATE INDEX "index_lfn_foreign_word" ON "index_lfn" ("foreign_word");
CREATE INDEX "index_lfn_native_page_title" ON "index_lfn" ("native_page_title");
CREATE INDEX "index_lij_foreign_native" ON "index_lij" ("foreign_word","native_page_title");
CREATE INDEX "index_lij_foreign_word" ON "index_lij" ("foreign_word");
CREATE INDEX "index_lij_native_page_title" ON "index_lij" ("native_page_title");
CREATE INDEX "index_lim_foreign_native" ON "index_lim" ("foreign_word","native_page_title");
CREATE INDEX "index_lim_foreign_word" ON "index_lim" ("foreign_word");
CREATE INDEX "index_lim_native_page_title" ON "index_lim" ("native_page_title");
CREATE INDEX "index_liv_foreign_native" ON "index_liv" ("foreign_word","native_page_title");
CREATE INDEX "index_liv_foreign_word" ON "index_liv" ("foreign_word");
CREATE INDEX "index_liv_native_page_title" ON "index_liv" ("native_page_title");
CREATE INDEX "index_lld_foreign_native" ON "index_lld" ("foreign_word","native_page_title");
CREATE INDEX "index_lld_foreign_word" ON "index_lld" ("foreign_word");
CREATE INDEX "index_lld_native_page_title" ON "index_lld" ("native_page_title");
CREATE INDEX "index_lmo_foreign_native" ON "index_lmo" ("foreign_word","native_page_title");
CREATE INDEX "index_lmo_foreign_word" ON "index_lmo" ("foreign_word");
CREATE INDEX "index_lmo_native_page_title" ON "index_lmo" ("native_page_title");
CREATE INDEX "index_ln_foreign_native" ON "index_ln" ("foreign_word","native_page_title");
CREATE INDEX "index_ln_foreign_word" ON "index_ln" ("foreign_word");
CREATE INDEX "index_ln_native_page_title" ON "index_ln" ("native_page_title");
CREATE INDEX "index_lo_foreign_native" ON "index_lo" ("foreign_word","native_page_title");
CREATE INDEX "index_lo_foreign_word" ON "index_lo" ("foreign_word");
CREATE INDEX "index_lo_native_page_title" ON "index_lo" ("native_page_title");
CREATE INDEX "index_loz_foreign_native" ON "index_loz" ("foreign_word","native_page_title");
CREATE INDEX "index_loz_foreign_word" ON "index_loz" ("foreign_word");
CREATE INDEX "index_loz_native_page_title" ON "index_loz" ("native_page_title");
CREATE INDEX "index_lt_foreign_native" ON "index_lt" ("foreign_word","native_page_title");
CREATE INDEX "index_lt_foreign_word" ON "index_lt" ("foreign_word");
CREATE INDEX "index_lt_native_page_title" ON "index_lt" ("native_page_title");
CREATE INDEX "index_ltc_foreign_native" ON "index_ltc" ("foreign_word","native_page_title");
CREATE INDEX "index_ltc_foreign_word" ON "index_ltc" ("foreign_word");
CREATE INDEX "index_ltc_native_page_title" ON "index_ltc" ("native_page_title");
CREATE INDEX "index_ltg_foreign_native" ON "index_ltg" ("foreign_word","native_page_title");
CREATE INDEX "index_ltg_foreign_word" ON "index_ltg" ("foreign_word");
CREATE INDEX "index_ltg_native_page_title" ON "index_ltg" ("native_page_title");
CREATE INDEX "index_lug_foreign_native" ON "index_lug" ("foreign_word","native_page_title");
CREATE INDEX "index_lug_foreign_word" ON "index_lug" ("foreign_word");
CREATE INDEX "index_lug_native_page_title" ON "index_lug" ("native_page_title");
CREATE INDEX "index_luo_foreign_native" ON "index_luo" ("foreign_word","native_page_title");
CREATE INDEX "index_luo_foreign_word" ON "index_luo" ("foreign_word");
CREATE INDEX "index_luo_native_page_title" ON "index_luo" ("native_page_title");
CREATE INDEX "index_luy_foreign_native" ON "index_luy" ("foreign_word","native_page_title");
CREATE INDEX "index_luy_foreign_word" ON "index_luy" ("foreign_word");
CREATE INDEX "index_luy_native_page_title" ON "index_luy" ("native_page_title");
CREATE INDEX "index_lv_foreign_native" ON "index_lv" ("foreign_word","native_page_title");
CREATE INDEX "index_lv_foreign_word" ON "index_lv" ("foreign_word");
CREATE INDEX "index_lv_native_page_title" ON "index_lv" ("native_page_title");
CREATE INDEX "index_lzh_foreign_native" ON "index_lzh" ("foreign_word","native_page_title");
CREATE INDEX "index_lzh_foreign_word" ON "index_lzh" ("foreign_word");
CREATE INDEX "index_lzh_native_page_title" ON "index_lzh" ("native_page_title");
CREATE INDEX "index_lzz_foreign_native" ON "index_lzz" ("foreign_word","native_page_title");
CREATE INDEX "index_lzz_foreign_word" ON "index_lzz" ("foreign_word");
CREATE INDEX "index_lzz_native_page_title" ON "index_lzz" ("native_page_title");
CREATE INDEX "index_mag_foreign_native" ON "index_mag" ("foreign_word","native_page_title");
CREATE INDEX "index_mag_foreign_word" ON "index_mag" ("foreign_word");
CREATE INDEX "index_mag_native_page_title" ON "index_mag" ("native_page_title");
CREATE INDEX "index_mai_foreign_native" ON "index_mai" ("foreign_word","native_page_title");
CREATE INDEX "index_mai_foreign_word" ON "index_mai" ("foreign_word");
CREATE INDEX "index_mai_native_page_title" ON "index_mai" ("native_page_title");
CREATE INDEX "index_man_foreign_native" ON "index_man" ("foreign_word","native_page_title");
CREATE INDEX "index_man_foreign_word" ON "index_man" ("foreign_word");
CREATE INDEX "index_man_native_page_title" ON "index_man" ("native_page_title");
CREATE INDEX "index_map-bms_foreign_native" ON "index_map-bms" ("foreign_word","native_page_title");
CREATE INDEX "index_map-bms_foreign_word" ON "index_map-bms" ("foreign_word");
CREATE INDEX "index_map-bms_native_page_title" ON "index_map-bms" ("native_page_title");
CREATE INDEX "index_mas_foreign_native" ON "index_mas" ("foreign_word","native_page_title");
CREATE INDEX "index_mas_foreign_word" ON "index_mas" ("foreign_word");
CREATE INDEX "index_mas_native_page_title" ON "index_mas" ("native_page_title");
CREATE INDEX "index_mdf_foreign_native" ON "index_mdf" ("foreign_word","native_page_title");
CREATE INDEX "index_mdf_foreign_word" ON "index_mdf" ("foreign_word");
CREATE INDEX "index_mdf_native_page_title" ON "index_mdf" ("native_page_title");
CREATE INDEX "index_mg_foreign_native" ON "index_mg" ("foreign_word","native_page_title");
CREATE INDEX "index_mg_foreign_word" ON "index_mg" ("foreign_word");
CREATE INDEX "index_mg_native_page_title" ON "index_mg" ("native_page_title");
CREATE INDEX "index_mgm_foreign_native" ON "index_mgm" ("foreign_word","native_page_title");
CREATE INDEX "index_mgm_foreign_word" ON "index_mgm" ("foreign_word");
CREATE INDEX "index_mgm_native_page_title" ON "index_mgm" ("native_page_title");
CREATE INDEX "index_mh_foreign_native" ON "index_mh" ("foreign_word","native_page_title");
CREATE INDEX "index_mh_foreign_word" ON "index_mh" ("foreign_word");
CREATE INDEX "index_mh_native_page_title" ON "index_mh" ("native_page_title");
CREATE INDEX "index_mhr_foreign_native" ON "index_mhr" ("foreign_word","native_page_title");
CREATE INDEX "index_mhr_foreign_word" ON "index_mhr" ("foreign_word");
CREATE INDEX "index_mhr_native_page_title" ON "index_mhr" ("native_page_title");
CREATE INDEX "index_mi_foreign_native" ON "index_mi" ("foreign_word","native_page_title");
CREATE INDEX "index_mi_foreign_word" ON "index_mi" ("foreign_word");
CREATE INDEX "index_mi_native_page_title" ON "index_mi" ("native_page_title");
CREATE INDEX "index_miq_foreign_native" ON "index_miq" ("foreign_word","native_page_title");
CREATE INDEX "index_miq_foreign_word" ON "index_miq" ("foreign_word");
CREATE INDEX "index_miq_native_page_title" ON "index_miq" ("native_page_title");
CREATE INDEX "index_mk_foreign_native" ON "index_mk" ("foreign_word","native_page_title");
CREATE INDEX "index_mk_foreign_word" ON "index_mk" ("foreign_word");
CREATE INDEX "index_mk_native_page_title" ON "index_mk" ("native_page_title");
CREATE INDEX "index_ml_foreign_native" ON "index_ml" ("foreign_word","native_page_title");
CREATE INDEX "index_ml_foreign_word" ON "index_ml" ("foreign_word");
CREATE INDEX "index_ml_native_page_title" ON "index_ml" ("native_page_title");
CREATE INDEX "index_mn_foreign_native" ON "index_mn" ("foreign_word","native_page_title");
CREATE INDEX "index_mn_foreign_word" ON "index_mn" ("foreign_word");
CREATE INDEX "index_mn_native_page_title" ON "index_mn" ("native_page_title");
CREATE INDEX "index_mnc_foreign_native" ON "index_mnc" ("foreign_word","native_page_title");
CREATE INDEX "index_mnc_foreign_word" ON "index_mnc" ("foreign_word");
CREATE INDEX "index_mnc_native_page_title" ON "index_mnc" ("native_page_title");
CREATE INDEX "index_mnk_foreign_native" ON "index_mnk" ("foreign_word","native_page_title");
CREATE INDEX "index_mnk_foreign_word" ON "index_mnk" ("foreign_word");
CREATE INDEX "index_mnk_native_page_title" ON "index_mnk" ("native_page_title");
CREATE INDEX "index_mns_foreign_native" ON "index_mns" ("foreign_word","native_page_title");
CREATE INDEX "index_mns_foreign_word" ON "index_mns" ("foreign_word");
CREATE INDEX "index_mns_native_page_title" ON "index_mns" ("native_page_title");
CREATE INDEX "index_mo_foreign_native" ON "index_mo" ("foreign_word","native_page_title");
CREATE INDEX "index_mo_foreign_word" ON "index_mo" ("foreign_word");
CREATE INDEX "index_mo_native_page_title" ON "index_mo" ("native_page_title");
CREATE INDEX "index_moh_foreign_native" ON "index_moh" ("foreign_word","native_page_title");
CREATE INDEX "index_moh_foreign_word" ON "index_moh" ("foreign_word");
CREATE INDEX "index_moh_native_page_title" ON "index_moh" ("native_page_title");
CREATE INDEX "index_mos_foreign_native" ON "index_mos" ("foreign_word","native_page_title");
CREATE INDEX "index_mos_foreign_word" ON "index_mos" ("foreign_word");
CREATE INDEX "index_mos_native_page_title" ON "index_mos" ("native_page_title");
CREATE INDEX "index_mr_foreign_native" ON "index_mr" ("foreign_word","native_page_title");
CREATE INDEX "index_mr_foreign_word" ON "index_mr" ("foreign_word");
CREATE INDEX "index_mr_native_page_title" ON "index_mr" ("native_page_title");
CREATE INDEX "index_mrj_foreign_native" ON "index_mrj" ("foreign_word","native_page_title");
CREATE INDEX "index_mrj_foreign_word" ON "index_mrj" ("foreign_word");
CREATE INDEX "index_mrj_native_page_title" ON "index_mrj" ("native_page_title");
CREATE INDEX "index_mrv_foreign_native" ON "index_mrv" ("foreign_word","native_page_title");
CREATE INDEX "index_mrv_foreign_word" ON "index_mrv" ("foreign_word");
CREATE INDEX "index_mrv_native_page_title" ON "index_mrv" ("native_page_title");
CREATE INDEX "index_ms_foreign_native" ON "index_ms" ("foreign_word","native_page_title");
CREATE INDEX "index_ms_foreign_word" ON "index_ms" ("foreign_word");
CREATE INDEX "index_ms_native_page_title" ON "index_ms" ("native_page_title");
CREATE INDEX "index_mt_foreign_native" ON "index_mt" ("foreign_word","native_page_title");
CREATE INDEX "index_mt_foreign_word" ON "index_mt" ("foreign_word");
CREATE INDEX "index_mt_native_page_title" ON "index_mt" ("native_page_title");
CREATE INDEX "index_mus_foreign_native" ON "index_mus" ("foreign_word","native_page_title");
CREATE INDEX "index_mus_foreign_word" ON "index_mus" ("foreign_word");
CREATE INDEX "index_mus_native_page_title" ON "index_mus" ("native_page_title");
CREATE INDEX "index_mwf_foreign_native" ON "index_mwf" ("foreign_word","native_page_title");
CREATE INDEX "index_mwf_foreign_word" ON "index_mwf" ("foreign_word");
CREATE INDEX "index_mwf_native_page_title" ON "index_mwf" ("native_page_title");
CREATE INDEX "index_mwj_foreign_native" ON "index_mwj" ("foreign_word","native_page_title");
CREATE INDEX "index_mwj_foreign_word" ON "index_mwj" ("foreign_word");
CREATE INDEX "index_mwj_native_page_title" ON "index_mwj" ("native_page_title");
CREATE INDEX "index_mwl_foreign_native" ON "index_mwl" ("foreign_word","native_page_title");
CREATE INDEX "index_mwl_foreign_word" ON "index_mwl" ("foreign_word");
CREATE INDEX "index_mwl_native_page_title" ON "index_mwl" ("native_page_title");
CREATE INDEX "index_my_foreign_native" ON "index_my" ("foreign_word","native_page_title");
CREATE INDEX "index_my_foreign_word" ON "index_my" ("foreign_word");
CREATE INDEX "index_my_native_page_title" ON "index_my" ("native_page_title");
CREATE INDEX "index_myv_foreign_native" ON "index_myv" ("foreign_word","native_page_title");
CREATE INDEX "index_myv_foreign_word" ON "index_myv" ("foreign_word");
CREATE INDEX "index_myv_native_page_title" ON "index_myv" ("native_page_title");
CREATE INDEX "index_mzn_foreign_native" ON "index_mzn" ("foreign_word","native_page_title");
CREATE INDEX "index_mzn_foreign_word" ON "index_mzn" ("foreign_word");
CREATE INDEX "index_mzn_native_page_title" ON "index_mzn" ("native_page_title");
CREATE INDEX "index_na_foreign_native" ON "index_na" ("foreign_word","native_page_title");
CREATE INDEX "index_na_foreign_word" ON "index_na" ("foreign_word");
CREATE INDEX "index_na_native_page_title" ON "index_na" ("native_page_title");
CREATE INDEX "index_nah_foreign_native" ON "index_nah" ("foreign_word","native_page_title");
CREATE INDEX "index_nah_foreign_word" ON "index_nah" ("foreign_word");
CREATE INDEX "index_nah_native_page_title" ON "index_nah" ("native_page_title");
CREATE INDEX "index_nan_foreign_native" ON "index_nan" ("foreign_word","native_page_title");
CREATE INDEX "index_nan_foreign_word" ON "index_nan" ("foreign_word");
CREATE INDEX "index_nan_native_page_title" ON "index_nan" ("native_page_title");
CREATE INDEX "index_nap_foreign_native" ON "index_nap" ("foreign_word","native_page_title");
CREATE INDEX "index_nap_foreign_word" ON "index_nap" ("foreign_word");
CREATE INDEX "index_nap_native_page_title" ON "index_nap" ("native_page_title");
CREATE INDEX "index_native_page_id" ON "index_native" ("page_id");
CREATE INDEX "index_native_idx_page_title" ON "index_native" ("page_title");
CREATE INDEX "index_nb_foreign_native" ON "index_nb" ("foreign_word","native_page_title");
CREATE INDEX "index_nb_foreign_word" ON "index_nb" ("foreign_word");
CREATE INDEX "index_nb_native_page_title" ON "index_nb" ("native_page_title");
CREATE INDEX "index_nds_foreign_native" ON "index_nds" ("foreign_word","native_page_title");
CREATE INDEX "index_nds_foreign_word" ON "index_nds" ("foreign_word");
CREATE INDEX "index_nds_native_page_title" ON "index_nds" ("native_page_title");
CREATE INDEX "index_nds-nl_foreign_native" ON "index_nds-nl" ("foreign_word","native_page_title");
CREATE INDEX "index_nds-nl_foreign_word" ON "index_nds-nl" ("foreign_word");
CREATE INDEX "index_nds-nl_native_page_title" ON "index_nds-nl" ("native_page_title");
CREATE INDEX "index_ne_foreign_native" ON "index_ne" ("foreign_word","native_page_title");
CREATE INDEX "index_ne_foreign_word" ON "index_ne" ("foreign_word");
CREATE INDEX "index_ne_native_page_title" ON "index_ne" ("native_page_title");
CREATE INDEX "index_new_foreign_native" ON "index_new" ("foreign_word","native_page_title");
CREATE INDEX "index_new_foreign_word" ON "index_new" ("foreign_word");
CREATE INDEX "index_new_native_page_title" ON "index_new" ("native_page_title");
CREATE INDEX "index_ng_foreign_native" ON "index_ng" ("foreign_word","native_page_title");
CREATE INDEX "index_ng_foreign_word" ON "index_ng" ("foreign_word");
CREATE INDEX "index_ng_native_page_title" ON "index_ng" ("native_page_title");
CREATE INDEX "index_nio_foreign_native" ON "index_nio" ("foreign_word","native_page_title");
CREATE INDEX "index_nio_foreign_word" ON "index_nio" ("foreign_word");
CREATE INDEX "index_nio_native_page_title" ON "index_nio" ("native_page_title");
CREATE INDEX "index_niu_foreign_native" ON "index_niu" ("foreign_word","native_page_title");
CREATE INDEX "index_niu_foreign_word" ON "index_niu" ("foreign_word");
CREATE INDEX "index_niu_native_page_title" ON "index_niu" ("native_page_title");
CREATE INDEX "index_niv_foreign_native" ON "index_niv" ("foreign_word","native_page_title");
CREATE INDEX "index_niv_foreign_word" ON "index_niv" ("foreign_word");
CREATE INDEX "index_niv_native_page_title" ON "index_niv" ("native_page_title");
CREATE INDEX "index_nl_foreign_native" ON "index_nl" ("foreign_word","native_page_title");
CREATE INDEX "index_nl_foreign_word" ON "index_nl" ("foreign_word");
CREATE INDEX "index_nl_native_page_title" ON "index_nl" ("native_page_title");
CREATE INDEX "index_nn_foreign_native" ON "index_nn" ("foreign_word","native_page_title");
CREATE INDEX "index_nn_foreign_word" ON "index_nn" ("foreign_word");
CREATE INDEX "index_nn_native_page_title" ON "index_nn" ("native_page_title");
CREATE INDEX "index_no_foreign_native" ON "index_no" ("foreign_word","native_page_title");
CREATE INDEX "index_no_foreign_word" ON "index_no" ("foreign_word");
CREATE INDEX "index_no_native_page_title" ON "index_no" ("native_page_title");
CREATE INDEX "index_nog_foreign_native" ON "index_nog" ("foreign_word","native_page_title");
CREATE INDEX "index_nog_foreign_word" ON "index_nog" ("foreign_word");
CREATE INDEX "index_nog_native_page_title" ON "index_nog" ("native_page_title");
CREATE INDEX "index_non_foreign_native" ON "index_non" ("foreign_word","native_page_title");
CREATE INDEX "index_non_foreign_word" ON "index_non" ("foreign_word");
CREATE INDEX "index_non_native_page_title" ON "index_non" ("native_page_title");
CREATE INDEX "index_nov_foreign_native" ON "index_nov" ("foreign_word","native_page_title");
CREATE INDEX "index_nov_foreign_word" ON "index_nov" ("foreign_word");
CREATE INDEX "index_nov_native_page_title" ON "index_nov" ("native_page_title");
CREATE INDEX "index_nrm_foreign_native" ON "index_nrm" ("foreign_word","native_page_title");
CREATE INDEX "index_nrm_foreign_word" ON "index_nrm" ("foreign_word");
CREATE INDEX "index_nrm_native_page_title" ON "index_nrm" ("native_page_title");
CREATE INDEX "index_nso_foreign_native" ON "index_nso" ("foreign_word","native_page_title");
CREATE INDEX "index_nso_foreign_word" ON "index_nso" ("foreign_word");
CREATE INDEX "index_nso_native_page_title" ON "index_nso" ("native_page_title");
CREATE INDEX "index_num_foreign_native" ON "index_num" ("foreign_word","native_page_title");
CREATE INDEX "index_num_foreign_word" ON "index_num" ("foreign_word");
CREATE INDEX "index_num_native_page_title" ON "index_num" ("native_page_title");
CREATE INDEX "index_nv_foreign_native" ON "index_nv" ("foreign_word","native_page_title");
CREATE INDEX "index_nv_foreign_word" ON "index_nv" ("foreign_word");
CREATE INDEX "index_nv_native_page_title" ON "index_nv" ("native_page_title");
CREATE INDEX "index_ny_foreign_native" ON "index_ny" ("foreign_word","native_page_title");
CREATE INDEX "index_ny_foreign_word" ON "index_ny" ("foreign_word");
CREATE INDEX "index_ny_native_page_title" ON "index_ny" ("native_page_title");
CREATE INDEX "index_obt_foreign_native" ON "index_obt" ("foreign_word","native_page_title");
CREATE INDEX "index_obt_foreign_word" ON "index_obt" ("foreign_word");
CREATE INDEX "index_obt_native_page_title" ON "index_obt" ("native_page_title");
CREATE INDEX "index_oc_foreign_native" ON "index_oc" ("foreign_word","native_page_title");
CREATE INDEX "index_oc_foreign_word" ON "index_oc" ("foreign_word");
CREATE INDEX "index_oc_native_page_title" ON "index_oc" ("native_page_title");
CREATE INDEX "index_odt_foreign_native" ON "index_odt" ("foreign_word","native_page_title");
CREATE INDEX "index_odt_foreign_word" ON "index_odt" ("foreign_word");
CREATE INDEX "index_odt_native_page_title" ON "index_odt" ("native_page_title");
CREATE INDEX "index_ofs_foreign_native" ON "index_ofs" ("foreign_word","native_page_title");
CREATE INDEX "index_ofs_foreign_word" ON "index_ofs" ("foreign_word");
CREATE INDEX "index_ofs_native_page_title" ON "index_ofs" ("native_page_title");
CREATE INDEX "index_oj_foreign_native" ON "index_oj" ("foreign_word","native_page_title");
CREATE INDEX "index_oj_foreign_word" ON "index_oj" ("foreign_word");
CREATE INDEX "index_oj_native_page_title" ON "index_oj" ("native_page_title");
CREATE INDEX "index_oko_foreign_native" ON "index_oko" ("foreign_word","native_page_title");
CREATE INDEX "index_oko_foreign_word" ON "index_oko" ("foreign_word");
CREATE INDEX "index_oko_native_page_title" ON "index_oko" ("native_page_title");
CREATE INDEX "index_om_foreign_native" ON "index_om" ("foreign_word","native_page_title");
CREATE INDEX "index_om_foreign_word" ON "index_om" ("foreign_word");
CREATE INDEX "index_om_native_page_title" ON "index_om" ("native_page_title");
CREATE INDEX "index_ood_foreign_native" ON "index_ood" ("foreign_word","native_page_title");
CREATE INDEX "index_ood_foreign_word" ON "index_ood" ("foreign_word");
CREATE INDEX "index_ood_native_page_title" ON "index_ood" ("native_page_title");
CREATE INDEX "index_or_foreign_native" ON "index_or" ("foreign_word","native_page_title");
CREATE INDEX "index_or_foreign_word" ON "index_or" ("foreign_word");
CREATE INDEX "index_or_native_page_title" ON "index_or" ("native_page_title");
CREATE INDEX "index_orv_foreign_native" ON "index_orv" ("foreign_word","native_page_title");
CREATE INDEX "index_orv_foreign_word" ON "index_orv" ("foreign_word");
CREATE INDEX "index_orv_native_page_title" ON "index_orv" ("native_page_title");
CREATE INDEX "index_os_foreign_native" ON "index_os" ("foreign_word","native_page_title");
CREATE INDEX "index_os_foreign_word" ON "index_os" ("foreign_word");
CREATE INDEX "index_os_native_page_title" ON "index_os" ("native_page_title");
CREATE INDEX "index_oun_foreign_native" ON "index_oun" ("foreign_word","native_page_title");
CREATE INDEX "index_oun_foreign_word" ON "index_oun" ("foreign_word");
CREATE INDEX "index_oun_native_page_title" ON "index_oun" ("native_page_title");
CREATE INDEX "index_owl_foreign_native" ON "index_owl" ("foreign_word","native_page_title");
CREATE INDEX "index_owl_foreign_word" ON "index_owl" ("foreign_word");
CREATE INDEX "index_owl_native_page_title" ON "index_owl" ("native_page_title");
CREATE INDEX "index_pa_foreign_native" ON "index_pa" ("foreign_word","native_page_title");
CREATE INDEX "index_pa_foreign_word" ON "index_pa" ("foreign_word");
CREATE INDEX "index_pa_native_page_title" ON "index_pa" ("native_page_title");
CREATE INDEX "index_pag_foreign_native" ON "index_pag" ("foreign_word","native_page_title");
CREATE INDEX "index_pag_foreign_word" ON "index_pag" ("foreign_word");
CREATE INDEX "index_pag_native_page_title" ON "index_pag" ("native_page_title");
CREATE INDEX "index_pal_foreign_native" ON "index_pal" ("foreign_word","native_page_title");
CREATE INDEX "index_pal_foreign_word" ON "index_pal" ("foreign_word");
CREATE INDEX "index_pal_native_page_title" ON "index_pal" ("native_page_title");
CREATE INDEX "index_pam_foreign_native" ON "index_pam" ("foreign_word","native_page_title");
CREATE INDEX "index_pam_foreign_word" ON "index_pam" ("foreign_word");
CREATE INDEX "index_pam_native_page_title" ON "index_pam" ("native_page_title");
CREATE INDEX "index_pap_foreign_native" ON "index_pap" ("foreign_word","native_page_title");
CREATE INDEX "index_pap_foreign_word" ON "index_pap" ("foreign_word");
CREATE INDEX "index_pap_native_page_title" ON "index_pap" ("native_page_title");
CREATE INDEX "index_pau_foreign_native" ON "index_pau" ("foreign_word","native_page_title");
CREATE INDEX "index_pau_foreign_word" ON "index_pau" ("foreign_word");
CREATE INDEX "index_pau_native_page_title" ON "index_pau" ("native_page_title");
CREATE INDEX "index_pcd_foreign_native" ON "index_pcd" ("foreign_word","native_page_title");
CREATE INDEX "index_pcd_foreign_word" ON "index_pcd" ("foreign_word");
CREATE INDEX "index_pcd_native_page_title" ON "index_pcd" ("native_page_title");
CREATE INDEX "index_pcm_foreign_native" ON "index_pcm" ("foreign_word","native_page_title");
CREATE INDEX "index_pcm_foreign_word" ON "index_pcm" ("foreign_word");
CREATE INDEX "index_pcm_native_page_title" ON "index_pcm" ("native_page_title");
CREATE INDEX "index_pdc_foreign_native" ON "index_pdc" ("foreign_word","native_page_title");
CREATE INDEX "index_pdc_foreign_word" ON "index_pdc" ("foreign_word");
CREATE INDEX "index_pdc_native_page_title" ON "index_pdc" ("native_page_title");
CREATE INDEX "index_pdt_foreign_native" ON "index_pdt" ("foreign_word","native_page_title");
CREATE INDEX "index_pdt_foreign_word" ON "index_pdt" ("foreign_word");
CREATE INDEX "index_pdt_native_page_title" ON "index_pdt" ("native_page_title");
CREATE INDEX "index_peo_foreign_native" ON "index_peo" ("foreign_word","native_page_title");
CREATE INDEX "index_peo_foreign_word" ON "index_peo" ("foreign_word");
CREATE INDEX "index_peo_native_page_title" ON "index_peo" ("native_page_title");
CREATE INDEX "index_pfl_foreign_native" ON "index_pfl" ("foreign_word","native_page_title");
CREATE INDEX "index_pfl_foreign_word" ON "index_pfl" ("foreign_word");
CREATE INDEX "index_pfl_native_page_title" ON "index_pfl" ("native_page_title");
CREATE INDEX "index_pga_foreign_native" ON "index_pga" ("foreign_word","native_page_title");
CREATE INDEX "index_pga_foreign_word" ON "index_pga" ("foreign_word");
CREATE INDEX "index_pga_native_page_title" ON "index_pga" ("native_page_title");
CREATE INDEX "index_pi_foreign_native" ON "index_pi" ("foreign_word","native_page_title");
CREATE INDEX "index_pi_foreign_word" ON "index_pi" ("foreign_word");
CREATE INDEX "index_pi_native_page_title" ON "index_pi" ("native_page_title");
CREATE INDEX "index_pie_foreign_native" ON "index_pie" ("foreign_word","native_page_title");
CREATE INDEX "index_pie_foreign_word" ON "index_pie" ("foreign_word");
CREATE INDEX "index_pie_native_page_title" ON "index_pie" ("native_page_title");
CREATE INDEX "index_pih_foreign_native" ON "index_pih" ("foreign_word","native_page_title");
CREATE INDEX "index_pih_foreign_word" ON "index_pih" ("foreign_word");
CREATE INDEX "index_pih_native_page_title" ON "index_pih" ("native_page_title");
CREATE INDEX "index_pinyin_foreign_native" ON "index_pinyin" ("foreign_word","native_page_title");
CREATE INDEX "index_pinyin_foreign_word" ON "index_pinyin" ("foreign_word");
CREATE INDEX "index_pinyin_native_page_title" ON "index_pinyin" ("native_page_title");
CREATE INDEX "index_pjt_foreign_native" ON "index_pjt" ("foreign_word","native_page_title");
CREATE INDEX "index_pjt_foreign_word" ON "index_pjt" ("foreign_word");
CREATE INDEX "index_pjt_native_page_title" ON "index_pjt" ("native_page_title");
CREATE INDEX "index_pl_foreign_native" ON "index_pl" ("foreign_word","native_page_title");
CREATE INDEX "index_pl_foreign_word" ON "index_pl" ("foreign_word");
CREATE INDEX "index_pl_native_page_title" ON "index_pl" ("native_page_title");
CREATE INDEX "index_plm_foreign_native" ON "index_plm" ("foreign_word","native_page_title");
CREATE INDEX "index_plm_foreign_word" ON "index_plm" ("foreign_word");
CREATE INDEX "index_plm_native_page_title" ON "index_plm" ("native_page_title");
CREATE INDEX "index_pms_foreign_native" ON "index_pms" ("foreign_word","native_page_title");
CREATE INDEX "index_pms_foreign_word" ON "index_pms" ("foreign_word");
CREATE INDEX "index_pms_native_page_title" ON "index_pms" ("native_page_title");
CREATE INDEX "index_pmt_foreign_native" ON "index_pmt" ("foreign_word","native_page_title");
CREATE INDEX "index_pmt_foreign_word" ON "index_pmt" ("foreign_word");
CREATE INDEX "index_pmt_native_page_title" ON "index_pmt" ("native_page_title");
CREATE INDEX "index_pnb_foreign_native" ON "index_pnb" ("foreign_word","native_page_title");
CREATE INDEX "index_pnb_foreign_word" ON "index_pnb" ("foreign_word");
CREATE INDEX "index_pnb_native_page_title" ON "index_pnb" ("native_page_title");
CREATE INDEX "index_pnt_foreign_native" ON "index_pnt" ("foreign_word","native_page_title");
CREATE INDEX "index_pnt_foreign_word" ON "index_pnt" ("foreign_word");
CREATE INDEX "index_pnt_native_page_title" ON "index_pnt" ("native_page_title");
CREATE INDEX "index_pox_foreign_native" ON "index_pox" ("foreign_word","native_page_title");
CREATE INDEX "index_pox_foreign_word" ON "index_pox" ("foreign_word");
CREATE INDEX "index_pox_native_page_title" ON "index_pox" ("native_page_title");
CREATE INDEX "index_ppol_foreign_native" ON "index_ppol" ("foreign_word","native_page_title");
CREATE INDEX "index_ppol_foreign_word" ON "index_ppol" ("foreign_word");
CREATE INDEX "index_ppol_native_page_title" ON "index_ppol" ("native_page_title");
CREATE INDEX "index_prg_foreign_native" ON "index_prg" ("foreign_word","native_page_title");
CREATE INDEX "index_prg_foreign_word" ON "index_prg" ("foreign_word");
CREATE INDEX "index_prg_native_page_title" ON "index_prg" ("native_page_title");
CREATE INDEX "index_prs_foreign_native" ON "index_prs" ("foreign_word","native_page_title");
CREATE INDEX "index_prs_foreign_word" ON "index_prs" ("foreign_word");
CREATE INDEX "index_prs_native_page_title" ON "index_prs" ("native_page_title");
CREATE INDEX "index_ps_foreign_native" ON "index_ps" ("foreign_word","native_page_title");
CREATE INDEX "index_ps_foreign_word" ON "index_ps" ("foreign_word");
CREATE INDEX "index_ps_native_page_title" ON "index_ps" ("native_page_title");
CREATE INDEX "index_psl_foreign_native" ON "index_psl" ("foreign_word","native_page_title");
CREATE INDEX "index_psl_foreign_word" ON "index_psl" ("foreign_word");
CREATE INDEX "index_psl_native_page_title" ON "index_psl" ("native_page_title");
CREATE INDEX "index_pt_foreign_native" ON "index_pt" ("foreign_word","native_page_title");
CREATE INDEX "index_pt_foreign_word" ON "index_pt" ("foreign_word");
CREATE INDEX "index_pt_native_page_title" ON "index_pt" ("native_page_title");
CREATE INDEX "index_pt-br_foreign_native" ON "index_pt-br" ("foreign_word","native_page_title");
CREATE INDEX "index_pt-br_foreign_word" ON "index_pt-br" ("foreign_word");
CREATE INDEX "index_pt-br_native_page_title" ON "index_pt-br" ("native_page_title");
CREATE INDEX "index_qu_foreign_native" ON "index_qu" ("foreign_word","native_page_title");
CREATE INDEX "index_qu_foreign_word" ON "index_qu" ("foreign_word");
CREATE INDEX "index_qu_native_page_title" ON "index_qu" ("native_page_title");
CREATE INDEX "index_qya_foreign_native" ON "index_qya" ("foreign_word","native_page_title");
CREATE INDEX "index_qya_foreign_word" ON "index_qya" ("foreign_word");
CREATE INDEX "index_qya_native_page_title" ON "index_qya" ("native_page_title");
CREATE INDEX "index_rap_foreign_native" ON "index_rap" ("foreign_word","native_page_title");
CREATE INDEX "index_rap_foreign_word" ON "index_rap" ("foreign_word");
CREATE INDEX "index_rap_native_page_title" ON "index_rap" ("native_page_title");
CREATE INDEX "index_rar_foreign_native" ON "index_rar" ("foreign_word","native_page_title");
CREATE INDEX "index_rar_foreign_word" ON "index_rar" ("foreign_word");
CREATE INDEX "index_rar_native_page_title" ON "index_rar" ("native_page_title");
CREATE INDEX "index_rhg_foreign_native" ON "index_rhg" ("foreign_word","native_page_title");
CREATE INDEX "index_rhg_foreign_word" ON "index_rhg" ("foreign_word");
CREATE INDEX "index_rhg_native_page_title" ON "index_rhg" ("native_page_title");
CREATE INDEX "index_rif_foreign_native" ON "index_rif" ("foreign_word","native_page_title");
CREATE INDEX "index_rif_foreign_word" ON "index_rif" ("foreign_word");
CREATE INDEX "index_rif_native_page_title" ON "index_rif" ("native_page_title");
CREATE INDEX "index_rm_foreign_native" ON "index_rm" ("foreign_word","native_page_title");
CREATE INDEX "index_rm_foreign_word" ON "index_rm" ("foreign_word");
CREATE INDEX "index_rm_native_page_title" ON "index_rm" ("native_page_title");
CREATE INDEX "index_rmr_foreign_native" ON "index_rmr" ("foreign_word","native_page_title");
CREATE INDEX "index_rmr_foreign_word" ON "index_rmr" ("foreign_word");
CREATE INDEX "index_rmr_native_page_title" ON "index_rmr" ("native_page_title");
CREATE INDEX "index_rmy_foreign_native" ON "index_rmy" ("foreign_word","native_page_title");
CREATE INDEX "index_rmy_foreign_word" ON "index_rmy" ("foreign_word");
CREATE INDEX "index_rmy_native_page_title" ON "index_rmy" ("native_page_title");
CREATE INDEX "index_ro_foreign_native" ON "index_ro" ("foreign_word","native_page_title");
CREATE INDEX "index_ro_foreign_word" ON "index_ro" ("foreign_word");
CREATE INDEX "index_ro_native_page_title" ON "index_ro" ("native_page_title");
CREATE INDEX "index_roa-tara_foreign_native" ON "index_roa-tara" ("foreign_word","native_page_title");
CREATE INDEX "index_roa-tara_foreign_word" ON "index_roa-tara" ("foreign_word");
CREATE INDEX "index_roa-tara_native_page_title" ON "index_roa-tara" ("native_page_title");
CREATE INDEX "index_rom_foreign_native" ON "index_rom" ("foreign_word","native_page_title");
CREATE INDEX "index_rom_foreign_word" ON "index_rom" ("foreign_word");
CREATE INDEX "index_rom_native_page_title" ON "index_rom" ("native_page_title");
CREATE INDEX "index_romaji_foreign_native" ON "index_romaji" ("foreign_word","native_page_title");
CREATE INDEX "index_romaji_foreign_word" ON "index_romaji" ("foreign_word");
CREATE INDEX "index_romaji_native_page_title" ON "index_romaji" ("native_page_title");
CREATE INDEX "index_rop_foreign_native" ON "index_rop" ("foreign_word","native_page_title");
CREATE INDEX "index_rop_foreign_word" ON "index_rop" ("foreign_word");
CREATE INDEX "index_rop_native_page_title" ON "index_rop" ("native_page_title");
CREATE INDEX "index_ru-old_foreign_native" ON "index_ru-old" ("foreign_word","native_page_title");
CREATE INDEX "index_ru-old_foreign_word" ON "index_ru-old" ("foreign_word");
CREATE INDEX "index_ru-old_native_page_title" ON "index_ru-old" ("native_page_title");
CREATE INDEX "index_run_foreign_native" ON "index_run" ("foreign_word","native_page_title");
CREATE INDEX "index_run_foreign_word" ON "index_run" ("foreign_word");
CREATE INDEX "index_run_native_page_title" ON "index_run" ("native_page_title");
CREATE INDEX "index_rup_foreign_native" ON "index_rup" ("foreign_word","native_page_title");
CREATE INDEX "index_rup_foreign_word" ON "index_rup" ("foreign_word");
CREATE INDEX "index_rup_native_page_title" ON "index_rup" ("native_page_title");
CREATE INDEX "index_ruq_foreign_native" ON "index_ruq" ("foreign_word","native_page_title");
CREATE INDEX "index_ruq_foreign_word" ON "index_ruq" ("foreign_word");
CREATE INDEX "index_ruq_native_page_title" ON "index_ruq" ("native_page_title");
CREATE INDEX "index_ruq-cyrl_foreign_native" ON "index_ruq-cyrl" ("foreign_word","native_page_title");
CREATE INDEX "index_ruq-cyrl_foreign_word" ON "index_ruq-cyrl" ("foreign_word");
CREATE INDEX "index_ruq-cyrl_native_page_title" ON "index_ruq-cyrl" ("native_page_title");
CREATE INDEX "index_ruq-grek_foreign_native" ON "index_ruq-grek" ("foreign_word","native_page_title");
CREATE INDEX "index_ruq-grek_foreign_word" ON "index_ruq-grek" ("foreign_word");
CREATE INDEX "index_ruq-grek_native_page_title" ON "index_ruq-grek" ("native_page_title");
CREATE INDEX "index_ruq-latn_foreign_native" ON "index_ruq-latn" ("foreign_word","native_page_title");
CREATE INDEX "index_ruq-latn_foreign_word" ON "index_ruq-latn" ("foreign_word");
CREATE INDEX "index_ruq-latn_native_page_title" ON "index_ruq-latn" ("native_page_title");
CREATE INDEX "index_rw_foreign_native" ON "index_rw" ("foreign_word","native_page_title");
CREATE INDEX "index_rw_foreign_word" ON "index_rw" ("foreign_word");
CREATE INDEX "index_rw_native_page_title" ON "index_rw" ("native_page_title");
CREATE INDEX "index_ryn_foreign_native" ON "index_ryn" ("foreign_word","native_page_title");
CREATE INDEX "index_ryn_foreign_word" ON "index_ryn" ("foreign_word");
CREATE INDEX "index_ryn_native_page_title" ON "index_ryn" ("native_page_title");
CREATE INDEX "index_ryu_foreign_native" ON "index_ryu" ("foreign_word","native_page_title");
CREATE INDEX "index_ryu_foreign_word" ON "index_ryu" ("foreign_word");
CREATE INDEX "index_ryu_native_page_title" ON "index_ryu" ("native_page_title");
CREATE INDEX "index_sa_foreign_native" ON "index_sa" ("foreign_word","native_page_title");
CREATE INDEX "index_sa_foreign_word" ON "index_sa" ("foreign_word");
CREATE INDEX "index_sa_native_page_title" ON "index_sa" ("native_page_title");
CREATE INDEX "index_sah_foreign_native" ON "index_sah" ("foreign_word","native_page_title");
CREATE INDEX "index_sah_foreign_word" ON "index_sah" ("foreign_word");
CREATE INDEX "index_sah_native_page_title" ON "index_sah" ("native_page_title");
CREATE INDEX "index_sam_foreign_native" ON "index_sam" ("foreign_word","native_page_title");
CREATE INDEX "index_sam_foreign_word" ON "index_sam" ("foreign_word");
CREATE INDEX "index_sam_native_page_title" ON "index_sam" ("native_page_title");
CREATE INDEX "index_sat_foreign_native" ON "index_sat" ("foreign_word","native_page_title");
CREATE INDEX "index_sat_foreign_word" ON "index_sat" ("foreign_word");
CREATE INDEX "index_sat_native_page_title" ON "index_sat" ("native_page_title");
CREATE INDEX "index_sc_foreign_native" ON "index_sc" ("foreign_word","native_page_title");
CREATE INDEX "index_sc_foreign_word" ON "index_sc" ("foreign_word");
CREATE INDEX "index_sc_native_page_title" ON "index_sc" ("native_page_title");
CREATE INDEX "index_scn_foreign_native" ON "index_scn" ("foreign_word","native_page_title");
CREATE INDEX "index_scn_foreign_word" ON "index_scn" ("foreign_word");
CREATE INDEX "index_scn_native_page_title" ON "index_scn" ("native_page_title");
CREATE INDEX "index_sco_foreign_native" ON "index_sco" ("foreign_word","native_page_title");
CREATE INDEX "index_sco_foreign_word" ON "index_sco" ("foreign_word");
CREATE INDEX "index_sco_native_page_title" ON "index_sco" ("native_page_title");
CREATE INDEX "index_sd_foreign_native" ON "index_sd" ("foreign_word","native_page_title");
CREATE INDEX "index_sd_foreign_word" ON "index_sd" ("foreign_word");
CREATE INDEX "index_sd_native_page_title" ON "index_sd" ("native_page_title");
CREATE INDEX "index_sdc_foreign_native" ON "index_sdc" ("foreign_word","native_page_title");
CREATE INDEX "index_sdc_foreign_word" ON "index_sdc" ("foreign_word");
CREATE INDEX "index_sdc_native_page_title" ON "index_sdc" ("native_page_title");
CREATE INDEX "index_se_foreign_native" ON "index_se" ("foreign_word","native_page_title");
CREATE INDEX "index_se_foreign_word" ON "index_se" ("foreign_word");
CREATE INDEX "index_se_native_page_title" ON "index_se" ("native_page_title");
CREATE INDEX "index_sei_foreign_native" ON "index_sei" ("foreign_word","native_page_title");
CREATE INDEX "index_sei_foreign_word" ON "index_sei" ("foreign_word");
CREATE INDEX "index_sei_native_page_title" ON "index_sei" ("native_page_title");
CREATE INDEX "index_sel_foreign_native" ON "index_sel" ("foreign_word","native_page_title");
CREATE INDEX "index_sel_foreign_word" ON "index_sel" ("foreign_word");
CREATE INDEX "index_sel_native_page_title" ON "index_sel" ("native_page_title");
CREATE INDEX "index_seu_foreign_native" ON "index_seu" ("foreign_word","native_page_title");
CREATE INDEX "index_seu_foreign_word" ON "index_seu" ("foreign_word");
CREATE INDEX "index_seu_native_page_title" ON "index_seu" ("native_page_title");
CREATE INDEX "index_sg_foreign_native" ON "index_sg" ("foreign_word","native_page_title");
CREATE INDEX "index_sg_foreign_word" ON "index_sg" ("foreign_word");
CREATE INDEX "index_sg_native_page_title" ON "index_sg" ("native_page_title");
CREATE INDEX "index_sga_foreign_native" ON "index_sga" ("foreign_word","native_page_title");
CREATE INDEX "index_sga_foreign_word" ON "index_sga" ("foreign_word");
CREATE INDEX "index_sga_native_page_title" ON "index_sga" ("native_page_title");
CREATE INDEX "index_sh_foreign_native" ON "index_sh" ("foreign_word","native_page_title");
CREATE INDEX "index_sh_foreign_word" ON "index_sh" ("foreign_word");
CREATE INDEX "index_sh_native_page_title" ON "index_sh" ("native_page_title");
CREATE INDEX "index_shi_foreign_native" ON "index_shi" ("foreign_word","native_page_title");
CREATE INDEX "index_shi_foreign_word" ON "index_shi" ("foreign_word");
CREATE INDEX "index_shi_native_page_title" ON "index_shi" ("native_page_title");
CREATE INDEX "index_shu_foreign_native" ON "index_shu" ("foreign_word","native_page_title");
CREATE INDEX "index_shu_foreign_word" ON "index_shu" ("foreign_word");
CREATE INDEX "index_shu_native_page_title" ON "index_shu" ("native_page_title");
CREATE INDEX "index_si_foreign_native" ON "index_si" ("foreign_word","native_page_title");
CREATE INDEX "index_si_foreign_word" ON "index_si" ("foreign_word");
CREATE INDEX "index_si_native_page_title" ON "index_si" ("native_page_title");
CREATE INDEX "index_simple_foreign_native" ON "index_simple" ("foreign_word","native_page_title");
CREATE INDEX "index_simple_foreign_word" ON "index_simple" ("foreign_word");
CREATE INDEX "index_simple_native_page_title" ON "index_simple" ("native_page_title");
CREATE INDEX "index_sjd_foreign_native" ON "index_sjd" ("foreign_word","native_page_title");
CREATE INDEX "index_sjd_foreign_word" ON "index_sjd" ("foreign_word");
CREATE INDEX "index_sjd_native_page_title" ON "index_sjd" ("native_page_title");
CREATE INDEX "index_sjn_foreign_native" ON "index_sjn" ("foreign_word","native_page_title");
CREATE INDEX "index_sjn_foreign_word" ON "index_sjn" ("foreign_word");
CREATE INDEX "index_sjn_native_page_title" ON "index_sjn" ("native_page_title");
CREATE INDEX "index_sk_foreign_native" ON "index_sk" ("foreign_word","native_page_title");
CREATE INDEX "index_sk_foreign_word" ON "index_sk" ("foreign_word");
CREATE INDEX "index_sk_native_page_title" ON "index_sk" ("native_page_title");
CREATE INDEX "index_sl_foreign_native" ON "index_sl" ("foreign_word","native_page_title");
CREATE INDEX "index_sl_foreign_word" ON "index_sl" ("foreign_word");
CREATE INDEX "index_sl_native_page_title" ON "index_sl" ("native_page_title");
CREATE INDEX "index_slovio_foreign_native" ON "index_slovio" ("foreign_word","native_page_title");
CREATE INDEX "index_slovio_foreign_word" ON "index_slovio" ("foreign_word");
CREATE INDEX "index_slovio_native_page_title" ON "index_slovio" ("native_page_title");
CREATE INDEX "index_slovio-c_foreign_native" ON "index_slovio-c" ("foreign_word","native_page_title");
CREATE INDEX "index_slovio-c_foreign_word" ON "index_slovio-c" ("foreign_word");
CREATE INDEX "index_slovio-c_native_page_title" ON "index_slovio-c" ("native_page_title");
CREATE INDEX "index_slovio-l_foreign_native" ON "index_slovio-l" ("foreign_word","native_page_title");
CREATE INDEX "index_slovio-l_foreign_word" ON "index_slovio-l" ("foreign_word");
CREATE INDEX "index_slovio-l_native_page_title" ON "index_slovio-l" ("native_page_title");
CREATE INDEX "index_slovio-la_foreign_native" ON "index_slovio-la" ("foreign_word","native_page_title");
CREATE INDEX "index_slovio-la_foreign_word" ON "index_slovio-la" ("foreign_word");
CREATE INDEX "index_slovio-la_native_page_title" ON "index_slovio-la" ("native_page_title");
CREATE INDEX "index_slv_foreign_native" ON "index_slv" ("foreign_word","native_page_title");
CREATE INDEX "index_slv_foreign_word" ON "index_slv" ("foreign_word");
CREATE INDEX "index_slv_native_page_title" ON "index_slv" ("native_page_title");
CREATE INDEX "index_sma_foreign_native" ON "index_sma" ("foreign_word","native_page_title");
CREATE INDEX "index_sma_foreign_word" ON "index_sma" ("foreign_word");
CREATE INDEX "index_sma_native_page_title" ON "index_sma" ("native_page_title");
CREATE INDEX "index_smn_foreign_native" ON "index_smn" ("foreign_word","native_page_title");
CREATE INDEX "index_smn_foreign_word" ON "index_smn" ("foreign_word");
CREATE INDEX "index_smn_native_page_title" ON "index_smn" ("native_page_title");
CREATE INDEX "index_smo_foreign_native" ON "index_smo" ("foreign_word","native_page_title");
CREATE INDEX "index_smo_foreign_word" ON "index_smo" ("foreign_word");
CREATE INDEX "index_smo_native_page_title" ON "index_smo" ("native_page_title");
CREATE INDEX "index_sms_foreign_native" ON "index_sms" ("foreign_word","native_page_title");
CREATE INDEX "index_sms_foreign_word" ON "index_sms" ("foreign_word");
CREATE INDEX "index_sms_native_page_title" ON "index_sms" ("native_page_title");
CREATE INDEX "index_sn_foreign_native" ON "index_sn" ("foreign_word","native_page_title");
CREATE INDEX "index_sn_foreign_word" ON "index_sn" ("foreign_word");
CREATE INDEX "index_sn_native_page_title" ON "index_sn" ("native_page_title");
CREATE INDEX "index_so_foreign_native" ON "index_so" ("foreign_word","native_page_title");
CREATE INDEX "index_so_foreign_word" ON "index_so" ("foreign_word");
CREATE INDEX "index_so_native_page_title" ON "index_so" ("native_page_title");
CREATE INDEX "index_solresol_foreign_native" ON "index_solresol" ("foreign_word","native_page_title");
CREATE INDEX "index_solresol_foreign_word" ON "index_solresol" ("foreign_word");
CREATE INDEX "index_solresol_native_page_title" ON "index_solresol" ("native_page_title");
CREATE INDEX "index_sot_foreign_native" ON "index_sot" ("foreign_word","native_page_title");
CREATE INDEX "index_sot_foreign_word" ON "index_sot" ("foreign_word");
CREATE INDEX "index_sot_native_page_title" ON "index_sot" ("native_page_title");
CREATE INDEX "index_sqi_foreign_native" ON "index_sqi" ("foreign_word","native_page_title");
CREATE INDEX "index_sqi_foreign_word" ON "index_sqi" ("foreign_word");
CREATE INDEX "index_sqi_native_page_title" ON "index_sqi" ("native_page_title");
CREATE INDEX "index_srn_foreign_native" ON "index_srn" ("foreign_word","native_page_title");
CREATE INDEX "index_srn_foreign_word" ON "index_srn" ("foreign_word");
CREATE INDEX "index_srn_native_page_title" ON "index_srn" ("native_page_title");
CREATE INDEX "index_srp_foreign_native" ON "index_srp" ("foreign_word","native_page_title");
CREATE INDEX "index_srp_foreign_word" ON "index_srp" ("foreign_word");
CREATE INDEX "index_srp_native_page_title" ON "index_srp" ("native_page_title");
CREATE INDEX "index_ss_foreign_native" ON "index_ss" ("foreign_word","native_page_title");
CREATE INDEX "index_ss_foreign_word" ON "index_ss" ("foreign_word");
CREATE INDEX "index_ss_native_page_title" ON "index_ss" ("native_page_title");
CREATE INDEX "index_st_foreign_native" ON "index_st" ("foreign_word","native_page_title");
CREATE INDEX "index_st_foreign_word" ON "index_st" ("foreign_word");
CREATE INDEX "index_st_native_page_title" ON "index_st" ("native_page_title");
CREATE INDEX "index_stq_foreign_native" ON "index_stq" ("foreign_word","native_page_title");
CREATE INDEX "index_stq_foreign_word" ON "index_stq" ("foreign_word");
CREATE INDEX "index_stq_native_page_title" ON "index_stq" ("native_page_title");
CREATE INDEX "index_su_foreign_native" ON "index_su" ("foreign_word","native_page_title");
CREATE INDEX "index_su_foreign_word" ON "index_su" ("foreign_word");
CREATE INDEX "index_su_native_page_title" ON "index_su" ("native_page_title");
CREATE INDEX "index_sux_foreign_native" ON "index_sux" ("foreign_word","native_page_title");
CREATE INDEX "index_sux_foreign_word" ON "index_sux" ("foreign_word");
CREATE INDEX "index_sux_native_page_title" ON "index_sux" ("native_page_title");
CREATE INDEX "index_sv_foreign_native" ON "index_sv" ("foreign_word","native_page_title");
CREATE INDEX "index_sv_foreign_word" ON "index_sv" ("foreign_word");
CREATE INDEX "index_sv_native_page_title" ON "index_sv" ("native_page_title");
CREATE INDEX "index_sva_foreign_native" ON "index_sva" ("foreign_word","native_page_title");
CREATE INDEX "index_sva_foreign_word" ON "index_sva" ("foreign_word");
CREATE INDEX "index_sva_native_page_title" ON "index_sva" ("native_page_title");
CREATE INDEX "index_sw_foreign_native" ON "index_sw" ("foreign_word","native_page_title");
CREATE INDEX "index_sw_foreign_word" ON "index_sw" ("foreign_word");
CREATE INDEX "index_sw_native_page_title" ON "index_sw" ("native_page_title");
CREATE INDEX "index_syc_foreign_native" ON "index_syc" ("foreign_word","native_page_title");
CREATE INDEX "index_syc_foreign_word" ON "index_syc" ("foreign_word");
CREATE INDEX "index_syc_native_page_title" ON "index_syc" ("native_page_title");
CREATE INDEX "index_szl_foreign_native" ON "index_szl" ("foreign_word","native_page_title");
CREATE INDEX "index_szl_foreign_word" ON "index_szl" ("foreign_word");
CREATE INDEX "index_szl_native_page_title" ON "index_szl" ("native_page_title");
CREATE INDEX "index_ta_foreign_native" ON "index_ta" ("foreign_word","native_page_title");
CREATE INDEX "index_ta_foreign_word" ON "index_ta" ("foreign_word");
CREATE INDEX "index_ta_native_page_title" ON "index_ta" ("native_page_title");
CREATE INDEX "index_tab_foreign_native" ON "index_tab" ("foreign_word","native_page_title");
CREATE INDEX "index_tab_foreign_word" ON "index_tab" ("foreign_word");
CREATE INDEX "index_tab_native_page_title" ON "index_tab" ("native_page_title");
CREATE INDEX "index_tah_foreign_native" ON "index_tah" ("foreign_word","native_page_title");
CREATE INDEX "index_tah_foreign_word" ON "index_tah" ("foreign_word");
CREATE INDEX "index_tah_native_page_title" ON "index_tah" ("native_page_title");
CREATE INDEX "index_tat_foreign_native" ON "index_tat" ("foreign_word","native_page_title");
CREATE INDEX "index_tat_foreign_word" ON "index_tat" ("foreign_word");
CREATE INDEX "index_tat_native_page_title" ON "index_tat" ("native_page_title");
CREATE INDEX "index_tcs_foreign_native" ON "index_tcs" ("foreign_word","native_page_title");
CREATE INDEX "index_tcs_foreign_word" ON "index_tcs" ("foreign_word");
CREATE INDEX "index_tcs_native_page_title" ON "index_tcs" ("native_page_title");
CREATE INDEX "index_tcy_foreign_native" ON "index_tcy" ("foreign_word","native_page_title");
CREATE INDEX "index_tcy_foreign_word" ON "index_tcy" ("foreign_word");
CREATE INDEX "index_tcy_native_page_title" ON "index_tcy" ("native_page_title");
CREATE INDEX "index_te_foreign_native" ON "index_te" ("foreign_word","native_page_title");
CREATE INDEX "index_te_foreign_word" ON "index_te" ("foreign_word");
CREATE INDEX "index_te_native_page_title" ON "index_te" ("native_page_title");
CREATE INDEX "index_tet_foreign_native" ON "index_tet" ("foreign_word","native_page_title");
CREATE INDEX "index_tet_foreign_word" ON "index_tet" ("foreign_word");
CREATE INDEX "index_tet_native_page_title" ON "index_tet" ("native_page_title");
CREATE INDEX "index_tgk_foreign_native" ON "index_tgk" ("foreign_word","native_page_title");
CREATE INDEX "index_tgk_foreign_word" ON "index_tgk" ("foreign_word");
CREATE INDEX "index_tgk_native_page_title" ON "index_tgk" ("native_page_title");
CREATE INDEX "index_tgl_foreign_native" ON "index_tgl" ("foreign_word","native_page_title");
CREATE INDEX "index_tgl_foreign_word" ON "index_tgl" ("foreign_word");
CREATE INDEX "index_tgl_native_page_title" ON "index_tgl" ("native_page_title");
CREATE INDEX "index_th_foreign_native" ON "index_th" ("foreign_word","native_page_title");
CREATE INDEX "index_th_foreign_word" ON "index_th" ("foreign_word");
CREATE INDEX "index_th_native_page_title" ON "index_th" ("native_page_title");
CREATE INDEX "index_tig_foreign_native" ON "index_tig" ("foreign_word","native_page_title");
CREATE INDEX "index_tig_foreign_word" ON "index_tig" ("foreign_word");
CREATE INDEX "index_tig_native_page_title" ON "index_tig" ("native_page_title");
CREATE INDEX "index_tir_foreign_native" ON "index_tir" ("foreign_word","native_page_title");
CREATE INDEX "index_tir_foreign_word" ON "index_tir" ("foreign_word");
CREATE INDEX "index_tir_native_page_title" ON "index_tir" ("native_page_title");
CREATE INDEX "index_tix_foreign_native" ON "index_tix" ("foreign_word","native_page_title");
CREATE INDEX "index_tix_foreign_word" ON "index_tix" ("foreign_word");
CREATE INDEX "index_tix_native_page_title" ON "index_tix" ("native_page_title");
CREATE INDEX "index_tk_foreign_native" ON "index_tk" ("foreign_word","native_page_title");
CREATE INDEX "index_tk_foreign_word" ON "index_tk" ("foreign_word");
CREATE INDEX "index_tk_native_page_title" ON "index_tk" ("native_page_title");
CREATE INDEX "index_tkl_foreign_native" ON "index_tkl" ("foreign_word","native_page_title");
CREATE INDEX "index_tkl_foreign_word" ON "index_tkl" ("foreign_word");
CREATE INDEX "index_tkl_native_page_title" ON "index_tkl" ("native_page_title");
CREATE INDEX "index_tlh_foreign_native" ON "index_tlh" ("foreign_word","native_page_title");
CREATE INDEX "index_tlh_foreign_word" ON "index_tlh" ("foreign_word");
CREATE INDEX "index_tlh_native_page_title" ON "index_tlh" ("native_page_title");
CREATE INDEX "index_tly_foreign_native" ON "index_tly" ("foreign_word","native_page_title");
CREATE INDEX "index_tly_foreign_word" ON "index_tly" ("foreign_word");
CREATE INDEX "index_tly_native_page_title" ON "index_tly" ("native_page_title");
CREATE INDEX "index_to_foreign_native" ON "index_to" ("foreign_word","native_page_title");
CREATE INDEX "index_to_foreign_word" ON "index_to" ("foreign_word");
CREATE INDEX "index_to_native_page_title" ON "index_to" ("native_page_title");
CREATE INDEX "index_tokipona_foreign_native" ON "index_tokipona" ("foreign_word","native_page_title");
CREATE INDEX "index_tokipona_foreign_word" ON "index_tokipona" ("foreign_word");
CREATE INDEX "index_tokipona_native_page_title" ON "index_tokipona" ("native_page_title");
CREATE INDEX "index_ton_foreign_native" ON "index_ton" ("foreign_word","native_page_title");
CREATE INDEX "index_ton_foreign_word" ON "index_ton" ("foreign_word");
CREATE INDEX "index_ton_native_page_title" ON "index_ton" ("native_page_title");
CREATE INDEX "index_tp_foreign_native" ON "index_tp" ("foreign_word","native_page_title");
CREATE INDEX "index_tp_foreign_word" ON "index_tp" ("foreign_word");
CREATE INDEX "index_tp_native_page_title" ON "index_tp" ("native_page_title");
CREATE INDEX "index_tpc_foreign_native" ON "index_tpc" ("foreign_word","native_page_title");
CREATE INDEX "index_tpc_foreign_word" ON "index_tpc" ("foreign_word");
CREATE INDEX "index_tpc_native_page_title" ON "index_tpc" ("native_page_title");
CREATE INDEX "index_tpi_foreign_native" ON "index_tpi" ("foreign_word","native_page_title");
CREATE INDEX "index_tpi_foreign_word" ON "index_tpi" ("foreign_word");
CREATE INDEX "index_tpi_native_page_title" ON "index_tpi" ("native_page_title");
CREATE INDEX "index_tpn_foreign_native" ON "index_tpn" ("foreign_word","native_page_title");
CREATE INDEX "index_tpn_foreign_word" ON "index_tpn" ("foreign_word");
CREATE INDEX "index_tpn_native_page_title" ON "index_tpn" ("native_page_title");
CREATE INDEX "index_tr_foreign_native" ON "index_tr" ("foreign_word","native_page_title");
CREATE INDEX "index_tr_foreign_word" ON "index_tr" ("foreign_word");
CREATE INDEX "index_tr_native_page_title" ON "index_tr" ("native_page_title");
CREATE INDEX "index_translingual_foreign_native" ON "index_translingual" ("foreign_word","native_page_title");
CREATE INDEX "index_translingual_foreign_word" ON "index_translingual" ("foreign_word");
CREATE INDEX "index_translingual_native_page_title" ON "index_translingual" ("native_page_title");
CREATE INDEX "index_tru_foreign_native" ON "index_tru" ("foreign_word","native_page_title");
CREATE INDEX "index_tru_foreign_word" ON "index_tru" ("foreign_word");
CREATE INDEX "index_tru_native_page_title" ON "index_tru" ("native_page_title");
CREATE INDEX "index_tsn_foreign_native" ON "index_tsn" ("foreign_word","native_page_title");
CREATE INDEX "index_tsn_foreign_word" ON "index_tsn" ("foreign_word");
CREATE INDEX "index_tsn_native_page_title" ON "index_tsn" ("native_page_title");
CREATE INDEX "index_tso_foreign_native" ON "index_tso" ("foreign_word","native_page_title");
CREATE INDEX "index_tso_foreign_word" ON "index_tso" ("foreign_word");
CREATE INDEX "index_tso_native_page_title" ON "index_tso" ("native_page_title");
CREATE INDEX "index_ttt_foreign_native" ON "index_ttt" ("foreign_word","native_page_title");
CREATE INDEX "index_ttt_foreign_word" ON "index_ttt" ("foreign_word");
CREATE INDEX "index_ttt_native_page_title" ON "index_ttt" ("native_page_title");
CREATE INDEX "index_tum_foreign_native" ON "index_tum" ("foreign_word","native_page_title");
CREATE INDEX "index_tum_foreign_word" ON "index_tum" ("foreign_word");
CREATE INDEX "index_tum_native_page_title" ON "index_tum" ("native_page_title");
CREATE INDEX "index_tup_foreign_native" ON "index_tup" ("foreign_word","native_page_title");
CREATE INDEX "index_tup_foreign_word" ON "index_tup" ("foreign_word");
CREATE INDEX "index_tup_native_page_title" ON "index_tup" ("native_page_title");
CREATE INDEX "index_tvl_foreign_native" ON "index_tvl" ("foreign_word","native_page_title");
CREATE INDEX "index_tvl_foreign_word" ON "index_tvl" ("foreign_word");
CREATE INDEX "index_tvl_native_page_title" ON "index_tvl" ("native_page_title");
CREATE INDEX "index_twf_foreign_native" ON "index_twf" ("foreign_word","native_page_title");
CREATE INDEX "index_twf_foreign_word" ON "index_twf" ("foreign_word");
CREATE INDEX "index_twf_native_page_title" ON "index_twf" ("native_page_title");
CREATE INDEX "index_twi_foreign_native" ON "index_twi" ("foreign_word","native_page_title");
CREATE INDEX "index_twi_foreign_word" ON "index_twi" ("foreign_word");
CREATE INDEX "index_twi_native_page_title" ON "index_twi" ("native_page_title");
CREATE INDEX "index_tyv_foreign_native" ON "index_tyv" ("foreign_word","native_page_title");
CREATE INDEX "index_tyv_foreign_word" ON "index_tyv" ("foreign_word");
CREATE INDEX "index_tyv_native_page_title" ON "index_tyv" ("native_page_title");
CREATE INDEX "index_tzj_foreign_native" ON "index_tzj" ("foreign_word","native_page_title");
CREATE INDEX "index_tzj_foreign_word" ON "index_tzj" ("foreign_word");
CREATE INDEX "index_tzj_native_page_title" ON "index_tzj" ("native_page_title");
CREATE INDEX "index_tzm_foreign_native" ON "index_tzm" ("foreign_word","native_page_title");
CREATE INDEX "index_tzm_foreign_word" ON "index_tzm" ("foreign_word");
CREATE INDEX "index_tzm_native_page_title" ON "index_tzm" ("native_page_title");
CREATE INDEX "index_uby_foreign_native" ON "index_uby" ("foreign_word","native_page_title");
CREATE INDEX "index_uby_foreign_word" ON "index_uby" ("foreign_word");
CREATE INDEX "index_uby_native_page_title" ON "index_uby" ("native_page_title");
CREATE INDEX "index_udi_foreign_native" ON "index_udi" ("foreign_word","native_page_title");
CREATE INDEX "index_udi_foreign_word" ON "index_udi" ("foreign_word");
CREATE INDEX "index_udi_native_page_title" ON "index_udi" ("native_page_title");
CREATE INDEX "index_udm_foreign_native" ON "index_udm" ("foreign_word","native_page_title");
CREATE INDEX "index_udm_foreign_word" ON "index_udm" ("foreign_word");
CREATE INDEX "index_udm_native_page_title" ON "index_udm" ("native_page_title");
CREATE INDEX "index_ug_foreign_native" ON "index_ug" ("foreign_word","native_page_title");
CREATE INDEX "index_ug_foreign_word" ON "index_ug" ("foreign_word");
CREATE INDEX "index_ug_native_page_title" ON "index_ug" ("native_page_title");
CREATE INDEX "index_uga_foreign_native" ON "index_uga" ("foreign_word","native_page_title");
CREATE INDEX "index_uga_foreign_word" ON "index_uga" ("foreign_word");
CREATE INDEX "index_uga_native_page_title" ON "index_uga" ("native_page_title");
CREATE INDEX "index_uk_foreign_native" ON "index_uk" ("foreign_word","native_page_title");
CREATE INDEX "index_uk_foreign_word" ON "index_uk" ("foreign_word");
CREATE INDEX "index_uk_native_page_title" ON "index_uk" ("native_page_title");
CREATE INDEX "index_ulc_foreign_native" ON "index_ulc" ("foreign_word","native_page_title");
CREATE INDEX "index_ulc_foreign_word" ON "index_ulc" ("foreign_word");
CREATE INDEX "index_ulc_native_page_title" ON "index_ulc" ("native_page_title");
CREATE INDEX "index_ulk_foreign_native" ON "index_ulk" ("foreign_word","native_page_title");
CREATE INDEX "index_ulk_foreign_word" ON "index_ulk" ("foreign_word");
CREATE INDEX "index_ulk_native_page_title" ON "index_ulk" ("native_page_title");
CREATE INDEX "index_ur_foreign_native" ON "index_ur" ("foreign_word","native_page_title");
CREATE INDEX "index_ur_foreign_word" ON "index_ur" ("foreign_word");
CREATE INDEX "index_ur_native_page_title" ON "index_ur" ("native_page_title");
CREATE INDEX "index_uz_foreign_native" ON "index_uz" ("foreign_word","native_page_title");
CREATE INDEX "index_uz_foreign_word" ON "index_uz" ("foreign_word");
CREATE INDEX "index_uz_native_page_title" ON "index_uz" ("native_page_title");
CREATE INDEX "index_val_foreign_native" ON "index_val" ("foreign_word","native_page_title");
CREATE INDEX "index_val_foreign_word" ON "index_val" ("foreign_word");
CREATE INDEX "index_val_native_page_title" ON "index_val" ("native_page_title");
CREATE INDEX "index_ve_foreign_native" ON "index_ve" ("foreign_word","native_page_title");
CREATE INDEX "index_ve_foreign_word" ON "index_ve" ("foreign_word");
CREATE INDEX "index_ve_native_page_title" ON "index_ve" ("native_page_title");
CREATE INDEX "index_vec_foreign_native" ON "index_vec" ("foreign_word","native_page_title");
CREATE INDEX "index_vec_foreign_word" ON "index_vec" ("foreign_word");
CREATE INDEX "index_vec_native_page_title" ON "index_vec" ("native_page_title");
CREATE INDEX "index_vep_foreign_native" ON "index_vep" ("foreign_word","native_page_title");
CREATE INDEX "index_vep_foreign_word" ON "index_vep" ("foreign_word");
CREATE INDEX "index_vep_native_page_title" ON "index_vep" ("native_page_title");
CREATE INDEX "index_vi_foreign_native" ON "index_vi" ("foreign_word","native_page_title");
CREATE INDEX "index_vi_foreign_word" ON "index_vi" ("foreign_word");
CREATE INDEX "index_vi_native_page_title" ON "index_vi" ("native_page_title");
CREATE INDEX "index_vls_foreign_native" ON "index_vls" ("foreign_word","native_page_title");
CREATE INDEX "index_vls_foreign_word" ON "index_vls" ("foreign_word");
CREATE INDEX "index_vls_native_page_title" ON "index_vls" ("native_page_title");
CREATE INDEX "index_vma_foreign_native" ON "index_vma" ("foreign_word","native_page_title");
CREATE INDEX "index_vma_foreign_word" ON "index_vma" ("foreign_word");
CREATE INDEX "index_vma_native_page_title" ON "index_vma" ("native_page_title");
CREATE INDEX "index_vol_foreign_native" ON "index_vol" ("foreign_word","native_page_title");
CREATE INDEX "index_vol_foreign_word" ON "index_vol" ("foreign_word");
CREATE INDEX "index_vol_native_page_title" ON "index_vol" ("native_page_title");
CREATE INDEX "index_vot_foreign_native" ON "index_vot" ("foreign_word","native_page_title");
CREATE INDEX "index_vot_foreign_word" ON "index_vot" ("foreign_word");
CREATE INDEX "index_vot_native_page_title" ON "index_vot" ("native_page_title");
CREATE INDEX "index_vro_foreign_native" ON "index_vro" ("foreign_word","native_page_title");
CREATE INDEX "index_vro_foreign_word" ON "index_vro" ("foreign_word");
CREATE INDEX "index_vro_native_page_title" ON "index_vro" ("native_page_title");
CREATE INDEX "index_wa_foreign_native" ON "index_wa" ("foreign_word","native_page_title");
CREATE INDEX "index_wa_foreign_word" ON "index_wa" ("foreign_word");
CREATE INDEX "index_wa_native_page_title" ON "index_wa" ("native_page_title");
CREATE INDEX "index_wad_foreign_native" ON "index_wad" ("foreign_word","native_page_title");
CREATE INDEX "index_wad_foreign_word" ON "index_wad" ("foreign_word");
CREATE INDEX "index_wad_native_page_title" ON "index_wad" ("native_page_title");
CREATE INDEX "index_war_foreign_native" ON "index_war" ("foreign_word","native_page_title");
CREATE INDEX "index_war_foreign_word" ON "index_war" ("foreign_word");
CREATE INDEX "index_war_native_page_title" ON "index_war" ("native_page_title");
CREATE INDEX "index_wim_foreign_native" ON "index_wim" ("foreign_word","native_page_title");
CREATE INDEX "index_wim_foreign_word" ON "index_wim" ("foreign_word");
CREATE INDEX "index_wim_native_page_title" ON "index_wim" ("native_page_title");
CREATE INDEX "index_wlm_foreign_native" ON "index_wlm" ("foreign_word","native_page_title");
CREATE INDEX "index_wlm_foreign_word" ON "index_wlm" ("foreign_word");
CREATE INDEX "index_wlm_native_page_title" ON "index_wlm" ("native_page_title");
CREATE INDEX "index_wo_foreign_native" ON "index_wo" ("foreign_word","native_page_title");
CREATE INDEX "index_wo_foreign_word" ON "index_wo" ("foreign_word");
CREATE INDEX "index_wo_native_page_title" ON "index_wo" ("native_page_title");
CREATE INDEX "index_wrh_foreign_native" ON "index_wrh" ("foreign_word","native_page_title");
CREATE INDEX "index_wrh_foreign_word" ON "index_wrh" ("foreign_word");
CREATE INDEX "index_wrh_native_page_title" ON "index_wrh" ("native_page_title");
CREATE INDEX "index_wuu_foreign_native" ON "index_wuu" ("foreign_word","native_page_title");
CREATE INDEX "index_wuu_foreign_word" ON "index_wuu" ("foreign_word");
CREATE INDEX "index_wuu_native_page_title" ON "index_wuu" ("native_page_title");
CREATE INDEX "index_wyb_foreign_native" ON "index_wyb" ("foreign_word","native_page_title");
CREATE INDEX "index_wyb_foreign_word" ON "index_wyb" ("foreign_word");
CREATE INDEX "index_wyb_native_page_title" ON "index_wyb" ("native_page_title");
CREATE INDEX "index_xal_foreign_native" ON "index_xal" ("foreign_word","native_page_title");
CREATE INDEX "index_xal_foreign_word" ON "index_xal" ("foreign_word");
CREATE INDEX "index_xal_native_page_title" ON "index_xal" ("native_page_title");
CREATE INDEX "index_xbc_foreign_native" ON "index_xbc" ("foreign_word","native_page_title");
CREATE INDEX "index_xbc_foreign_word" ON "index_xbc" ("foreign_word");
CREATE INDEX "index_xbc_native_page_title" ON "index_xbc" ("native_page_title");
CREATE INDEX "index_xbm_foreign_native" ON "index_xbm" ("foreign_word","native_page_title");
CREATE INDEX "index_xbm_foreign_word" ON "index_xbm" ("foreign_word");
CREATE INDEX "index_xbm_native_page_title" ON "index_xbm" ("native_page_title");
CREATE INDEX "index_xcl_foreign_native" ON "index_xcl" ("foreign_word","native_page_title");
CREATE INDEX "index_xcl_foreign_word" ON "index_xcl" ("foreign_word");
CREATE INDEX "index_xcl_native_page_title" ON "index_xcl" ("native_page_title");
CREATE INDEX "index_xho_foreign_native" ON "index_xho" ("foreign_word","native_page_title");
CREATE INDEX "index_xho_foreign_word" ON "index_xho" ("foreign_word");
CREATE INDEX "index_xho_native_page_title" ON "index_xho" ("native_page_title");
CREATE INDEX "index_xmf_foreign_native" ON "index_xmf" ("foreign_word","native_page_title");
CREATE INDEX "index_xmf_foreign_word" ON "index_xmf" ("foreign_word");
CREATE INDEX "index_xmf_native_page_title" ON "index_xmf" ("native_page_title");
CREATE INDEX "index_xno_foreign_native" ON "index_xno" ("foreign_word","native_page_title");
CREATE INDEX "index_xno_foreign_word" ON "index_xno" ("foreign_word");
CREATE INDEX "index_xno_native_page_title" ON "index_xno" ("native_page_title");
CREATE INDEX "index_xrn_foreign_native" ON "index_xrn" ("foreign_word","native_page_title");
CREATE INDEX "index_xrn_foreign_word" ON "index_xrn" ("foreign_word");
CREATE INDEX "index_xrn_native_page_title" ON "index_xrn" ("native_page_title");
CREATE INDEX "index_xsm_foreign_native" ON "index_xsm" ("foreign_word","native_page_title");
CREATE INDEX "index_xsm_foreign_word" ON "index_xsm" ("foreign_word");
CREATE INDEX "index_xsm_native_page_title" ON "index_xsm" ("native_page_title");
CREATE INDEX "index_xsr_foreign_native" ON "index_xsr" ("foreign_word","native_page_title");
CREATE INDEX "index_xsr_foreign_word" ON "index_xsr" ("foreign_word");
CREATE INDEX "index_xsr_native_page_title" ON "index_xsr" ("native_page_title");
CREATE INDEX "index_xto_foreign_native" ON "index_xto" ("foreign_word","native_page_title");
CREATE INDEX "index_xto_foreign_word" ON "index_xto" ("foreign_word");
CREATE INDEX "index_xto_native_page_title" ON "index_xto" ("native_page_title");
CREATE INDEX "index_xvn_foreign_native" ON "index_xvn" ("foreign_word","native_page_title");
CREATE INDEX "index_xvn_foreign_word" ON "index_xvn" ("foreign_word");
CREATE INDEX "index_xvn_native_page_title" ON "index_xvn" ("native_page_title");
CREATE INDEX "index_ydd_foreign_native" ON "index_ydd" ("foreign_word","native_page_title");
CREATE INDEX "index_ydd_foreign_word" ON "index_ydd" ("foreign_word");
CREATE INDEX "index_ydd_native_page_title" ON "index_ydd" ("native_page_title");
CREATE INDEX "index_yi_foreign_native" ON "index_yi" ("foreign_word","native_page_title");
CREATE INDEX "index_yi_foreign_word" ON "index_yi" ("foreign_word");
CREATE INDEX "index_yi_native_page_title" ON "index_yi" ("native_page_title");
CREATE INDEX "index_ykg_foreign_native" ON "index_ykg" ("foreign_word","native_page_title");
CREATE INDEX "index_ykg_foreign_word" ON "index_ykg" ("foreign_word");
CREATE INDEX "index_ykg_native_page_title" ON "index_ykg" ("native_page_title");
CREATE INDEX "index_yo_foreign_native" ON "index_yo" ("foreign_word","native_page_title");
CREATE INDEX "index_yo_foreign_word" ON "index_yo" ("foreign_word");
CREATE INDEX "index_yo_native_page_title" ON "index_yo" ("native_page_title");
CREATE INDEX "index_yrk_foreign_native" ON "index_yrk" ("foreign_word","native_page_title");
CREATE INDEX "index_yrk_foreign_word" ON "index_yrk" ("foreign_word");
CREATE INDEX "index_yrk_native_page_title" ON "index_yrk" ("native_page_title");
CREATE INDEX "index_yua_foreign_native" ON "index_yua" ("foreign_word","native_page_title");
CREATE INDEX "index_yua_foreign_word" ON "index_yua" ("foreign_word");
CREATE INDEX "index_yua_native_page_title" ON "index_yua" ("native_page_title");
CREATE INDEX "index_yue_foreign_native" ON "index_yue" ("foreign_word","native_page_title");
CREATE INDEX "index_yue_foreign_word" ON "index_yue" ("foreign_word");
CREATE INDEX "index_yue_native_page_title" ON "index_yue" ("native_page_title");
CREATE INDEX "index_yux_foreign_native" ON "index_yux" ("foreign_word","native_page_title");
CREATE INDEX "index_yux_foreign_word" ON "index_yux" ("foreign_word");
CREATE INDEX "index_yux_native_page_title" ON "index_yux" ("native_page_title");
CREATE INDEX "index_za_foreign_native" ON "index_za" ("foreign_word","native_page_title");
CREATE INDEX "index_za_foreign_word" ON "index_za" ("foreign_word");
CREATE INDEX "index_za_native_page_title" ON "index_za" ("native_page_title");
CREATE INDEX "index_zai_foreign_native" ON "index_zai" ("foreign_word","native_page_title");
CREATE INDEX "index_zai_foreign_word" ON "index_zai" ("foreign_word");
CREATE INDEX "index_zai_native_page_title" ON "index_zai" ("native_page_title");
CREATE INDEX "index_ze_foreign_native" ON "index_ze" ("foreign_word","native_page_title");
CREATE INDEX "index_ze_foreign_word" ON "index_ze" ("foreign_word");
CREATE INDEX "index_ze_native_page_title" ON "index_ze" ("native_page_title");
CREATE INDEX "index_zea_foreign_native" ON "index_zea" ("foreign_word","native_page_title");
CREATE INDEX "index_zea_foreign_word" ON "index_zea" ("foreign_word");
CREATE INDEX "index_zea_native_page_title" ON "index_zea" ("native_page_title");
CREATE INDEX "index_zh_foreign_native" ON "index_zh" ("foreign_word","native_page_title");
CREATE INDEX "index_zh_foreign_word" ON "index_zh" ("foreign_word");
CREATE INDEX "index_zh_native_page_title" ON "index_zh" ("native_page_title");
CREATE INDEX "index_zh-cn_foreign_native" ON "index_zh-cn" ("foreign_word","native_page_title");
CREATE INDEX "index_zh-cn_foreign_word" ON "index_zh-cn" ("foreign_word");
CREATE INDEX "index_zh-cn_native_page_title" ON "index_zh-cn" ("native_page_title");
CREATE INDEX "index_zh-tw_foreign_native" ON "index_zh-tw" ("foreign_word","native_page_title");
CREATE INDEX "index_zh-tw_foreign_word" ON "index_zh-tw" ("foreign_word");
CREATE INDEX "index_zh-tw_native_page_title" ON "index_zh-tw" ("native_page_title");
CREATE INDEX "index_zko_foreign_native" ON "index_zko" ("foreign_word","native_page_title");
CREATE INDEX "index_zko_foreign_word" ON "index_zko" ("foreign_word");
CREATE INDEX "index_zko_native_page_title" ON "index_zko" ("native_page_title");
CREATE INDEX "index_zu_foreign_native" ON "index_zu" ("foreign_word","native_page_title");
CREATE INDEX "index_zu_foreign_word" ON "index_zu" ("foreign_word");
CREATE INDEX "index_zu_native_page_title" ON "index_zu" ("native_page_title");
CREATE INDEX "index_zza_foreign_native" ON "index_zza" ("foreign_word","native_page_title");
CREATE INDEX "index_zza_foreign_word" ON "index_zza" ("foreign_word");
CREATE INDEX "index_zza_native_page_title" ON "index_zza" ("native_page_title");
CREATE INDEX "inflection_idx_inflected_form" ON "inflection" ("inflected_form");
CREATE INDEX "lang_code" ON "lang" ("code");
CREATE INDEX "lang_pos_unique_page_lang_pos" ON "lang_pos" ("page_id","lang_id","pos_id","etymology_n");
CREATE INDEX "meaning_lang_pos_id" ON "meaning" ("lang_pos_id");
CREATE INDEX "native_red_link_idx_red_link" ON "native_red_link" ("red_link");
CREATE INDEX "page_idx_page_title" ON "page" ("page_title");
CREATE INDEX "page_inflection_page_inflection_id_id" ON "page_inflection" ("page_id","inflection_id");
CREATE INDEX "page_wikipedia_page_wikipedia_unique" ON "page_wikipedia" ("page_id","wikipedia_id");
CREATE INDEX "page_wikipedia_fk_page_wikipedia_wikipedia" ON "page_wikipedia" ("wikipedia_id");
CREATE INDEX "page_wikipedia_fk_page_wikipedia_page" ON "page_wikipedia" ("page_id");
CREATE INDEX "part_of_speech_name" ON "part_of_speech" ("name");
CREATE INDEX "relation_meaning_id" ON "relation" ("meaning_id");
CREATE INDEX "relation_type_name_unique" ON "relation_type" ("name");
CREATE INDEX "translation_lang_pos_id" ON "translation" ("lang_pos_id");
CREATE INDEX "translation_meaning_id" ON "translation" ("meaning_id");
CREATE INDEX "translation_entry_translation_id" ON "translation_entry" ("translation_id");
CREATE INDEX "wiki_text_idx_text" ON "wiki_text" ("text");
CREATE INDEX "wiki_text_words_wiki_text_id" ON "wiki_text_words" ("wiki_text_id");
CREATE INDEX "wikipedia_idx_page_title" ON "wikipedia" ("page_title");