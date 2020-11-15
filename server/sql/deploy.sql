-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vsta`
--
DROP DATABASE IF EXISTS `vsta`;
CREATE DATABASE IF NOT EXISTS `vsta`;
USE `vsta`;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(60) NOT NULL,
  `token` varchar(60),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

-- --------------------------------------------------------

--
-- Table structure for table `vessel`
--

DROP TABLE IF EXISTS `vessel`;
CREATE TABLE IF NOT EXISTS `vessel` (
  `uniqueId` varchar(60) NOT NULL,
  `imoN` varchar(12) DEFAULT NULL,
  `fullVslM` varchar(48) NOT NULL,
  `abbrVslM` varchar(32) NOT NULL,
  `fullInVoyN` varchar(500) DEFAULT NULL,
  `inVoyN` varchar(8) NOT NULL,
  `fullOutVoyN` varchar(500) DEFAULT NULL,
  `outVoyN` varchar(8) NOT NULL,
  `shiftSeqN` varchar(5) NOT NULL,
  `bthgDt` varchar(21) NOT NULL,
  `unbthgDt` varchar(21) NOT NULL,
  `berthN` varchar(10) DEFAULT NULL,
  `status` varchar(12) NOT NULL,
  `abbrTerminalM` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`uniqueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vessel`
--

--
-- Triggers `vessel`
--
DROP TRIGGER IF EXISTS `before_insert_set_history`;
DELIMITER $$
CREATE TRIGGER `before_insert_set_history` BEFORE INSERT ON `vessel` FOR EACH ROW BEGIN
    SET FOREIGN_KEY_CHECKS=0;
    	INSERT INTO vessel_history(uniqueId, last_bthgDt, last_unbthgDt, bthgDt_change_count, unbthgDt_change_count, first_arrival)
        VALUES(new.uniqueId, new.`bthgDt`, new.`unbthgDt`, 0, 0, new.`bthgDt`);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `if_berth_changed_update_history`;
DELIMITER $$
CREATE TRIGGER `if_berth_changed_update_history` BEFORE UPDATE ON `vessel` FOR EACH ROW BEGIN
	IF (NEW.unbthgDt <> OLD.unbthgDt AND NEW.bthgDt <> OLD.bthgDt) THEN
            UPDATE vessel_history SET
            last_bthgDt = OLD.`bthgDt`,
            bthgDt_change_count = 	bthgDt_change_count + 1,
            last_unbthgDt = OLD.`unbthgDt`,
            unbthgDt_change_count = 		unbthgDt_change_count + 1
            where `uniqueId` = NEW.`uniqueId`;

     ELSEIF (NEW.`bthgDt` <> OLD.`bthgDt`) THEN
            UPDATE vessel_history SET
            last_bthgDt = OLD.`bthgDt`,
            bthgDt_change_count = 	bthgDt_change_count + 1
            where `uniqueId` = NEW.`uniqueId`;

        ELSEIF (NEW.`unbthgDt` <> OLD.`unbthgDt`) THEN
            UPDATE vessel_history SET
            last_unbthgDt = OLD.`unbthgDt`,
            unbthgDt_change_count = 		unbthgDt_change_count + 1
            where `uniqueId` = NEW.`uniqueId`;
        END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `vessel_history`
--

DROP TABLE IF EXISTS `vessel_history`;
CREATE TABLE IF NOT EXISTS `vessel_history` (
  `uniqueId` varchar(60) NOT NULL,
  `last_bthgDt` varchar(21) NOT NULL,
  `last_unbthgDt` varchar(21) NOT NULL,
  `bthgDt_change_count` int(2) NOT NULL DEFAULT '0',
  `unbthgDt_change_count` int(2) NOT NULL DEFAULT '0',
  `first_arrival` varchar(21) NOT NULL,
  PRIMARY KEY (`uniqueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vessel_history`
--
-- --------------------------------------------------------

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
CREATE TABLE IF NOT EXISTS `subscription` (
  `id` int(11) AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `voyage_id` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `favourite`
--

DROP TABLE IF EXISTS `favourite`;
CREATE TABLE IF NOT EXISTS `favourite` (
  `id` int(11) AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `voyage_id` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for table `vessel_history`
--
ALTER TABLE `vessel`
  ADD CONSTRAINT `vessel_ibfk_1` FOREIGN KEY (`uniqueId`) REFERENCES `vessel_history` (`uniqueId`);
COMMIT;

ALTER TABLE `subscription`
  ADD CONSTRAINT `subscription_ibfk_1` FOREIGN KEY (`voyage_id`) REFERENCES `vessel` (`uniqueId`),
  ADD CONSTRAINT `subscription_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
