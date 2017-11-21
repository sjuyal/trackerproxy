# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: otradb
# Generation Time: 2017-11-21 06:18:32 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table advertiser
# ------------------------------------------------------------

DROP TABLE IF EXISTS `advertiser`;

CREATE TABLE `advertiser` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `email_id` varchar(256) DEFAULT NULL,
  `contact` varchar(256) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table boundaries
# ------------------------------------------------------------

DROP TABLE IF EXISTS `boundaries`;

CREATE TABLE `boundaries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `boundaries` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table campaign
# ------------------------------------------------------------

DROP TABLE IF EXISTS `campaign`;

CREATE TABLE `campaign` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `advertiser_id` int(11) DEFAULT NULL,
  `status` enum('DRAFT','SCHEDULED','LIVE','PAUSED','COMPLETED','CLOSED') NOT NULL DEFAULT 'DRAFT',
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `budget` decimal(11,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table checkpoints
# ------------------------------------------------------------

DROP TABLE IF EXISTS `checkpoints`;

CREATE TABLE `checkpoints` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` int(11) DEFAULT NULL,
  `time` bigint(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table coordinates
# ------------------------------------------------------------

DROP TABLE IF EXISTS `coordinates`;

CREATE TABLE `coordinates` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `latitude` varchar(256) DEFAULT NULL,
  `longitude` varchar(256) DEFAULT NULL,
  `road_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table device
# ------------------------------------------------------------

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gps_device_num` varchar(100) NOT NULL,
  `is_working` tinyint(1) DEFAULT '0',
  `driver_id` int(11) DEFAULT NULL,
  `installation_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `gps_device_num` (`gps_device_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table driver
# ------------------------------------------------------------

DROP TABLE IF EXISTS `driver`;

CREATE TABLE `driver` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `car_no` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  `campaign_id` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table location
# ------------------------------------------------------------

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `road_id` varchar(100) DEFAULT NULL,
  `road_name` varchar(256) DEFAULT NULL,
  `neighbourhood` varchar(256) DEFAULT NULL,
  `suburb` varchar(256) DEFAULT NULL,
  `city` varchar(256) DEFAULT NULL,
  `county` varchar(256) DEFAULT NULL,
  `state_district` varchar(256) DEFAULT NULL,
  `postcode` varchar(256) DEFAULT NULL,
  `country` varchar(256) DEFAULT NULL,
  `country_code` varchar(256) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `roadId` (`road_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table metric_daily
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metric_daily`;

CREATE TABLE `metric_daily` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `metric_id` int(11) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `campaign_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `distance` decimal(11,2) DEFAULT NULL,
  `time` decimal(11,2) DEFAULT NULL,
  `spends` decimal(11,2) DEFAULT NULL,
  `impressions` decimal(11,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table metric_hourly
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metric_hourly`;

CREATE TABLE `metric_hourly` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `metric_id` int(11) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `campaign_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `distance` decimal(11,2) DEFAULT NULL,
  `time` decimal(11,2) DEFAULT NULL,
  `spends` decimal(11,2) DEFAULT NULL,
  `impressions` decimal(11,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table metric_monthly
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metric_monthly`;

CREATE TABLE `metric_monthly` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `metric_id` int(11) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `campaign_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `distance` decimal(11,2) DEFAULT NULL,
  `time` decimal(11,2) DEFAULT NULL,
  `spends` decimal(11,2) DEFAULT NULL,
  `impressions` decimal(11,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table metric_till_date
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metric_till_date`;

CREATE TABLE `metric_till_date` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `metric_id` int(11) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `campaign_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `distance` decimal(11,2) DEFAULT NULL,
  `time` decimal(11,2) DEFAULT NULL,
  `spends` decimal(11,2) DEFAULT NULL,
  `impressions` decimal(11,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
