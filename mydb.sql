/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2014-04-29 17:07:13
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_friends`
-- ----------------------------
DROP TABLE IF EXISTS `t_friends`;
CREATE TABLE `t_friends` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NUM` int(11) NOT NULL,
  `FRIEND_NUM` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_friends
-- ----------------------------
INSERT INTO `t_friends` VALUES ('14', '1395214786', '1395214596', '0');
INSERT INTO `t_friends` VALUES ('15', '1395214596', '1395214786', '0');
INSERT INTO `t_friends` VALUES ('16', '1395214786', '1395214617', '0');
INSERT INTO `t_friends` VALUES ('17', '1395214617', '1395214786', '0');
INSERT INTO `t_friends` VALUES ('18', '1395214596', '1395214617', '0');
INSERT INTO `t_friends` VALUES ('19', '1395214617', '1395214596', '0');
INSERT INTO `t_friends` VALUES ('20', '1395214596', '1395279242', '0');
INSERT INTO `t_friends` VALUES ('21', '1395279242', '1395214596', '0');
INSERT INTO `t_friends` VALUES ('22', '1395300919', '1395214596', '0');
INSERT INTO `t_friends` VALUES ('23', '1395214596', '1395300919', '0');
INSERT INTO `t_friends` VALUES ('24', '1395374319', '1395214596', '0');
INSERT INTO `t_friends` VALUES ('25', '1395214596', '1395374319', '0');
INSERT INTO `t_friends` VALUES ('26', '1395304817', '1396517723', '0');
INSERT INTO `t_friends` VALUES ('27', '1396517723', '1395304817', '0');
INSERT INTO `t_friends` VALUES ('28', '1396685389', '1396517723', '0');
INSERT INTO `t_friends` VALUES ('29', '1396517723', '1396685389', '0');
INSERT INTO `t_friends` VALUES ('30', '1396517723', '1396706293', '0');
INSERT INTO `t_friends` VALUES ('31', '1396706293', '1396517723', '0');

-- ----------------------------
-- Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `ID` int(11) NOT NULL,
  `GROUP_NAME` varchar(255) NOT NULL,
  `USER_NUM` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------

-- ----------------------------
-- Table structure for `t_history_content`
-- ----------------------------
DROP TABLE IF EXISTS `t_history_content`;
CREATE TABLE `t_history_content` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEND_NAME` varchar(255) NOT NULL,
  `RECEIVE_NAME` varchar(255) NOT NULL,
  `DATE` datetime NOT NULL,
  `MSG` longtext,
  `IS_SEND_MSG` tinyint(4) NOT NULL,
  `RECEIVEID` int(11) NOT NULL,
  `SENDID` int(11) NOT NULL,
  `GROUPPTAG` int(11) NOT NULL,
  `BELONGTO` varchar(255) DEFAULT NULL,
  `ISREAD` varchar(255) NOT NULL,
  `ISLOCALMSG` varchar(255) DEFAULT NULL,
  `MSGTYPE` int(11) DEFAULT NULL,
  `MSGLOCALURL` varchar(255) DEFAULT NULL,
  `UUID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_history_content
-- ----------------------------
INSERT INTO `t_history_content` VALUES ('19', '要', 't3', '2014-04-29 14:34:21', '我哦', '1', '1396517723', '1396685389', '0', '13965177231396685389', 'false', 'false', '0', null, null);

-- ----------------------------
-- Table structure for `t_offline_content`
-- ----------------------------
DROP TABLE IF EXISTS `t_offline_content`;
CREATE TABLE `t_offline_content` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEND_NAME` varchar(255) NOT NULL,
  `RECEIVE_NAME` varchar(255) DEFAULT NULL,
  `DATE` datetime NOT NULL,
  `MSG` longtext,
  `IS_SEND_MSG` tinyint(4) NOT NULL,
  `RECEIVEID` int(11) NOT NULL,
  `SENDID` int(11) NOT NULL,
  `GROUPPTAG` bigint(20) unsigned NOT NULL,
  `BELONGTO` varchar(255) DEFAULT NULL,
  `ISREAD` varchar(255) DEFAULT NULL,
  `ISLOCALMSG` varchar(255) DEFAULT NULL,
  `MSGTYPE` int(11) DEFAULT NULL,
  `MSGLOCALURL` varchar(255) DEFAULT NULL,
  `UUID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_offline_content
-- ----------------------------

-- ----------------------------
-- Table structure for `t_status`
-- ----------------------------
DROP TABLE IF EXISTS `t_status`;
CREATE TABLE `t_status` (
  `ID` int(11) NOT NULL,
  `USER_NUM` int(11) NOT NULL,
  `IS_ONLINE` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NUM` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `PASS` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('27', '1395213631', '5', '5');
INSERT INTO `t_user` VALUES ('28', '1395213649', '4', '4');
INSERT INTO `t_user` VALUES ('33', '1395213702', '1', '1');
INSERT INTO `t_user` VALUES ('34', '1395214596', '联想', '1');
INSERT INTO `t_user` VALUES ('35', '1395214617', '5556', '1');
INSERT INTO `t_user` VALUES ('36', '1395214786', 'Htc', '1');
INSERT INTO `t_user` VALUES ('37', '1395279242', '5554', '5554');
INSERT INTO `t_user` VALUES ('38', '1395300919', '5557', '1');
INSERT INTO `t_user` VALUES ('39', '1395304817', '联想', '1');
INSERT INTO `t_user` VALUES ('40', '1395374319', 'Htc2', '1');
INSERT INTO `t_user` VALUES ('41', '1395646315', '联想', '1');
INSERT INTO `t_user` VALUES ('42', '1395648122', 'Htc2', '1');
INSERT INTO `t_user` VALUES ('43', '1396517723', 't3', '1');
INSERT INTO `t_user` VALUES ('44', '1396685389', '要', '1');
INSERT INTO `t_user` VALUES ('45', '1396706293', 'xuni', '1');
INSERT INTO `t_user` VALUES ('46', '1397580199', 't3', '1');
INSERT INTO `t_user` VALUES ('47', '1398753986', '要', '1');
