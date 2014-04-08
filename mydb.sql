/*
Navicat MySQL Data Transfer

Source Server         : IM
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-04-08 08:08:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_friends
-- ----------------------------
DROP TABLE IF EXISTS `t_friends`;
CREATE TABLE `t_friends` (
  `ID` int(11) NOT NULL auto_increment,
  `USER_NUM` int(11) NOT NULL,
  `FRIEND_NUM` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `ID` int(11) NOT NULL,
  `GROUP_NAME` varchar(255) NOT NULL,
  `USER_NUM` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------

-- ----------------------------
-- Table structure for t_history_content
-- ----------------------------
DROP TABLE IF EXISTS `t_history_content`;
CREATE TABLE `t_history_content` (
  `ID` int(11) NOT NULL auto_increment,
  `SEND_NAME` varchar(255) NOT NULL,
  `RECEIVE_NAME` varchar(255) NOT NULL,
  `DATE` datetime NOT NULL,
  `MSG` varchar(255) default NULL,
  `IS_SEND_MSG` tinyint(4) NOT NULL,
  `RECEIVEID` int(11) NOT NULL,
  `SENDID` int(11) NOT NULL,
  `GROUPPTAG` int(11) NOT NULL,
  `BELONGTO` varchar(255) NOT NULL,
  `ISREAD` varchar(255) default NULL,
  `ISLOCALMSG` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_history_content
-- ----------------------------


-- ----------------------------
-- Table structure for t_offline_content
-- ----------------------------
DROP TABLE IF EXISTS `t_offline_content`;
CREATE TABLE `t_offline_content` (
  `ID` int(11) NOT NULL auto_increment,
  `SEND_NAME` varchar(255) NOT NULL,
  `RECEIVE_NAME` varchar(255) default NULL,
  `DATE` datetime NOT NULL,
  `MSG` varchar(255) default NULL,
  `IS_SEND_MSG` tinyint(4) NOT NULL,
  `RECEIVEID` int(11) NOT NULL,
  `SENDID` int(11) NOT NULL,
  `GROUPPTAG` bigint(20) unsigned NOT NULL,
  `BELONGTO` varchar(255) NOT NULL,
  `ISREAD` varchar(255) default NULL,
  `ISLOCALMSG` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for t_status
-- ----------------------------
DROP TABLE IF EXISTS `t_status`;
CREATE TABLE `t_status` (
  `ID` int(11) NOT NULL,
  `USER_NUM` int(11) NOT NULL,
  `IS_ONLINE` varchar(255) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` int(11) NOT NULL auto_increment,
  `NUM` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `PASS` varchar(255) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
