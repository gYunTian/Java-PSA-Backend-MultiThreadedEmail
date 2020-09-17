-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 16, 2020 at 07:02 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `portnet`
--
Create database portnet;
use portnet;
-- --------------------------------------------------------

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
CREATE TABLE IF NOT EXISTS `configuration` (
  `time_interval` double NOT NULL,
  `access_key` varchar(33) NOT NULL,
  PRIMARY KEY (`time_interval`,`access_key`),
  KEY `access_key` (`access_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `configuration`
--

INSERT INTO `configuration` (`time_interval`, `access_key`) VALUES
(0.5, 'd0ceb61c5edd48ce964d65bffacf3274');

-- --------------------------------------------------------

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
CREATE TABLE IF NOT EXISTS `domain` (
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `favourites`
--

DROP TABLE IF EXISTS `favourites`;
CREATE TABLE IF NOT EXISTS `favourites` (
  `user_id` int(11) NOT NULL,
  `voyage_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`voyage_id`),
  KEY `favourites_fk2` (`voyage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vessel`
--

DROP TABLE IF EXISTS `vessel`;
CREATE TABLE IF NOT EXISTS `vessel` (
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage`
--

DROP TABLE IF EXISTS `voyage`;
CREATE TABLE IF NOT EXISTS `voyage` (
  `voyage_id` int(11) NOT NULL,
  `berth_number` varchar(3) NOT NULL,
  `status` varchar(9) NOT NULL,
  `change_count` int(2) NOT NULL,
  PRIMARY KEY (`voyage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage_id`
--

DROP TABLE IF EXISTS `voyage_id`;
CREATE TABLE IF NOT EXISTS `voyage_id` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vessel_name` varchar(32) NOT NULL,
  `voyage_number` varchar(12) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `voyage_id_fk1` (`vessel_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage_in`
--

DROP TABLE IF EXISTS `voyage_in`;
CREATE TABLE IF NOT EXISTS `voyage_in` (
  `id` int(11) NOT NULL,
  `btrDt` varchar(32) NOT NULL,
  `firstBtrDt` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage_out`
--

DROP TABLE IF EXISTS `voyage_out`;
CREATE TABLE IF NOT EXISTS `voyage_out` (
  `id` int(11) NOT NULL,
  `etdDt` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favourites`
--
ALTER TABLE `favourites`
  ADD CONSTRAINT `favourites_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `favourites_fk2` FOREIGN KEY (`voyage_id`) REFERENCES `voyage_id` (`id`);

--
-- Constraints for table `voyage`
--
ALTER TABLE `voyage`
  ADD CONSTRAINT `voyage_fk1` FOREIGN KEY (`voyage_id`) REFERENCES `voyage_id` (`id`);

--
-- Constraints for table `voyage_id`
--
ALTER TABLE `voyage_id`
  ADD CONSTRAINT `voyage_id_fk1` FOREIGN KEY (`vessel_name`) REFERENCES `vessel` (`name`);

--
-- Constraints for table `voyage_in`
--
ALTER TABLE `voyage_in`
  ADD CONSTRAINT `voyage_in_fk1` FOREIGN KEY (`id`) REFERENCES `voyage` (`voyage_id`);

--
-- Constraints for table `voyage_out`
--
ALTER TABLE `voyage_out`
  ADD CONSTRAINT `voyage_out_fk1` FOREIGN KEY (`id`) REFERENCES `voyage` (`voyage_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;