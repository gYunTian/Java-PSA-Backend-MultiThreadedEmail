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

-- --------------------------------------------------------

--
-- Table structure for table `configuration`
--

create database portnet;
use portnet;

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
  `vessel_name` varchar(32) NOT NULL,
  `voyage_number` varchar(12) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`vessel_name`,`voyage_number`,`user_id`),
  KEY `user_id` (`user_id`),
  KEY `voyage_number` (`voyage_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage_in`
--

DROP TABLE IF EXISTS `voyage_in`;
CREATE TABLE IF NOT EXISTS `voyage_in` (
  `id` varchar(12) NOT NULL,
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
  `id` varchar(12) NOT NULL,
  `etdDt` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voyage`
--

DROP TABLE IF EXISTS `voyage`;
CREATE TABLE IF NOT EXISTS `voyage` (
  `vessel_name` varchar(32) NOT NULL,
  `voyage_number` varchar(12) NOT NULL,
  `berth_number` varchar(3) NOT NULL,
  `status` varchar(9) NOT NULL,
  `change_count` int(2) NOT NULL,
  PRIMARY KEY (`vessel_name`,`voyage_number`),
  KEY `voyage_number` (`voyage_number`)
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


--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`) VALUES
(0, 'admin', 'admin@smu.edu.sg', '$2y$10$EvaPizh1Wrx9EuLef8I3UeivWWfCThV5XfE05IabwWTr2DnuWo4HW');

-- --------------------------------------------------------

--
-- Table structure for table `vessel`
--

DROP TABLE IF EXISTS `vessel`;
CREATE TABLE IF NOT EXISTS `vessel` (
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favourites`
--
ALTER TABLE `favourites`
  ADD CONSTRAINT `favourites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `favourites_ibfk_2` FOREIGN KEY (`vessel_name`) REFERENCES `voyage` (`vessel_name`),
  ADD CONSTRAINT `favourites_ibfk_3` FOREIGN KEY (`voyage_number`) REFERENCES `voyage` (`voyage_number`);

--
-- Constraints for table `voyage`
--
ALTER TABLE `voyage`
  ADD CONSTRAINT `voyage_ibfk_1` FOREIGN KEY (`voyage_number`) REFERENCES `voyage_in` (`id`),
  ADD CONSTRAINT `voyage_ibfk_2` FOREIGN KEY (`voyage_number`) REFERENCES `voyage_out` (`id`),
  ADD CONSTRAINT `voyage_ibfk_3` FOREIGN KEY (`vessel_name`) REFERENCES `vessel` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
