-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 04, 2022 at 11:51 AM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tanheeddb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountType` int(11) NOT NULL,
  `FirstName` varchar(63) NOT NULL,
  `Surname` varchar(63) NOT NULL,
  `Email` varchar(63) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Photo` longtext,
  `VerificationKey` varchar(255) NOT NULL,
  `ExpiryDate` datetime DEFAULT NULL,
  `Verified` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
CREATE TABLE IF NOT EXISTS `calendar` (
  `SessionID` int(11) NOT NULL AUTO_INCREMENT,
  `TutorID` int(11) NOT NULL,
  `ParentID` int(11) NOT NULL,
  `StartTime` datetime NOT NULL,
  `EndTime` datetime DEFAULT NULL,
  PRIMARY KEY (`SessionID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `MessageID` int(11) NOT NULL AUTO_INCREMENT,
  `SenderID` int(11) NOT NULL,
  `ReceiverID` int(11) NOT NULL,
  `Message` varchar(1023) NOT NULL,
  `Timestamp` timestamp NOT NULL,
  PRIMARY KEY (`MessageID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tutordescription`
--

DROP TABLE IF EXISTS `tutordescription`;
CREATE TABLE IF NOT EXISTS `tutordescription` (
  `TutorID` int(11) NOT NULL,
  `Subjects` varchar(127) DEFAULT NULL,
  `HourlyCost` decimal(10,2) DEFAULT NULL,
  `Qualifications` varchar(1023) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Verified` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TutorID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
