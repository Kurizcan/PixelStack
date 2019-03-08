-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: pixelstack
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_comment`
--

DROP TABLE IF EXISTS `tb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `content` varchar(64) NOT NULL,
  `cdate` date DEFAULT NULL,
  PRIMARY KEY (`cid`),
  KEY `uid` (`uid`),
  CONSTRAINT `tb_comment_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user_info` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_comment`
--

LOCK TABLES `tb_comment` WRITE;
/*!40000 ALTER TABLE `tb_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_comment_relate`
--

DROP TABLE IF EXISTS `tb_comment_relate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_comment_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iid` int(10) NOT NULL,
  `cid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `iid` (`iid`),
  KEY `cid` (`cid`),
  CONSTRAINT `tb_comment_relate_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `tb_image_info` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_comment_relate_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `tb_comment` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_comment_relate`
--

LOCK TABLES `tb_comment_relate` WRITE;
/*!40000 ALTER TABLE `tb_comment_relate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_comment_relate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_follow_relate`
--

DROP TABLE IF EXISTS `tb_follow_relate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_follow_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `fid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fid` (`fid`),
  CONSTRAINT `tb_follow_relate_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user_info` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_follow_relate_ibfk_2` FOREIGN KEY (`fid`) REFERENCES `tb_user_info` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_follow_relate`
--

LOCK TABLES `tb_follow_relate` WRITE;
/*!40000 ALTER TABLE `tb_follow_relate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_follow_relate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_image_info`
--

DROP TABLE IF EXISTS `tb_image_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_image_info` (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  `author` varchar(16) NOT NULL,
  `upload` date DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `count` int(11) DEFAULT '0',
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_image_info`
--

LOCK TABLES `tb_image_info` WRITE;
/*!40000 ALTER TABLE `tb_image_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_image_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_star_relate`
--

DROP TABLE IF EXISTS `tb_star_relate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_star_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `iid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `iid` (`iid`),
  KEY `uid` (`uid`),
  CONSTRAINT `tb_star_relate_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `tb_image_info` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_star_relate_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `tb_user_info` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_star_relate`
--

LOCK TABLES `tb_star_relate` WRITE;
/*!40000 ALTER TABLE `tb_star_relate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_star_relate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_tag`
--

DROP TABLE IF EXISTS `tb_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_tag` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `tagname` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tag`
--

LOCK TABLES `tb_tag` WRITE;
/*!40000 ALTER TABLE `tb_tag` DISABLE KEYS */;
INSERT INTO `tb_tag` VALUES (101,'风景'),(102,'人物'),(103,'人物');
/*!40000 ALTER TABLE `tb_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_tag_relate`
--

DROP TABLE IF EXISTS `tb_tag_relate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_tag_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `iid` (`iid`),
  KEY `tid` (`tid`),
  CONSTRAINT `tb_tag_relate_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `tb_image_info` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_tag_relate_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tb_tag` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tag_relate`
--

LOCK TABLES `tb_tag_relate` WRITE;
/*!40000 ALTER TABLE `tb_tag_relate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_tag_relate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_thumb_relate`
--

DROP TABLE IF EXISTS `tb_thumb_relate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_thumb_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `iid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `iid` (`iid`),
  KEY `uid` (`uid`),
  CONSTRAINT `tb_thumb_relate_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `tb_image_info` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_thumb_relate_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `tb_user_info` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_thumb_relate`
--

LOCK TABLES `tb_thumb_relate` WRITE;
/*!40000 ALTER TABLE `tb_thumb_relate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_thumb_relate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_info`
--

DROP TABLE IF EXISTS `tb_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_user_info` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority` varchar(16) DEFAULT NULL,
  `email` varchar(16) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `introduction` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_info`
--

LOCK TABLES `tb_user_info` WRITE;
/*!40000 ALTER TABLE `tb_user_info` DISABLE KEYS */;
INSERT INTO `tb_user_info` VALUES (100,'root','root','root','lcan@gmail.com','normal',NULL),(101,'Lcanboom','123456','user','lcan@gmail.com','frozen',NULL),(102,'Yangmi','666','user','YangMi@gmail.com','frozen','fuckkkkk'),(103,'Harden','123456','user','lcan@gmail.com','frozen',NULL),(104,'kobe','123456','user','kobe@gmail.com','frozen','fuckkkkk'),(105,'Har','123456','user','lcan@gmail.com','frozen',NULL),(106,'XXXXG','666','user','XXXG@gmail.com','normal',NULL),(107,'XXXA','521','user','lcan@gmail.com','normal',NULL),(108,'VAVA','888','user','lcan@gmail.com','normal',NULL),(109,'Paul','888','user','lcan@gmail.com','normal',NULL),(110,'Dawe','888','user','lcan@gmail.com','normal',NULL),(111,'Curry','888','admin','lcan@gmail.com','normal',NULL);
/*!40000 ALTER TABLE `tb_user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-02 21:54:54
