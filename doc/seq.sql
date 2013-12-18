/*
Navicat MySQL Data Transfer

Source Server         : po
Source Server Version : 50527
Source Host           : localhost:4000
Source Database       : spruce

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2013-07-20 08:32:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for idtb64_1
-- ----------------------------
DROP TABLE IF EXISTS `idtb64_1`;
CREATE TABLE `idtb64_1` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `table` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`table`),
  UNIQUE KEY `idx_table` (`table`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of idtb64_1
-- ----------------------------
INSERT INTO `idtb64_1` VALUES ('6', 'photos');
INSERT INTO `idtb64_1` VALUES ('0', 'user');

-- ----------------------------
-- Table structure for idtb64_2
-- ----------------------------
DROP TABLE IF EXISTS `idtb64_2`;
CREATE TABLE `idtb64_2` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `table` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`table`),
  UNIQUE KEY `idx_table` (`table`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of idtb64_2
-- ----------------------------
INSERT INTO `idtb64_2` VALUES ('5', 'photos');
INSERT INTO `idtb64_2` VALUES ('0', 'user');

-- ----------------------------
-- Table structure for idtbl64
-- ----------------------------
DROP TABLE IF EXISTS `idtbl64`;
CREATE TABLE `idtbl64` (
  `id` bigint(20) unsigned NOT NULL,
  `table` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`table`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of idtbl64
-- ----------------------------

-- ----------------------------
-- Table structure for tickets64
-- ----------------------------
DROP TABLE IF EXISTS `tickets64`;
CREATE TABLE `tickets64` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stub` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tickets64
-- ----------------------------

-- ----------------------------
-- Table structure for user_pwd
-- ----------------------------
DROP TABLE IF EXISTS `user_pwd`;
CREATE TABLE `user_pwd` (
  `id_user` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `create_at` int(11) unsigned NOT NULL DEFAULT '0',
  `update_at` bigint(20) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '10' COMMENT '账户状态，<0 账户不可用，>0可用, 0=注册的，1=激活的',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `user_statuc` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_pwd
-- ----------------------------
INSERT INTO `user_pwd` VALUES ('24', 'mylooon1@163.com', 'tietang', 'MTIzNDU2', '0', '0', '0');
INSERT INTO `user_pwd` VALUES ('25', 'i@bashu.in', 'Mr.Yang', 'bWluZ3lhbmc=', '0', '0', '0');
INSERT INTO `user_pwd` VALUES ('26', 'ceo@muyizu.com', '网络~混混', 'bWluZ3lhbmc=', '0', '0', '0');
INSERT INTO `user_pwd` VALUES ('27', 'tietang.wang9@symvio.com', 'p2', 'MTIzNDU2', '0', '0', '10');
INSERT INTO `user_pwd` VALUES ('28', 'tietang.wang9@symbio.com', 'p3', 'MTIzNDU2', '0', '0', '10');
INSERT INTO `user_pwd` VALUES ('33', 'tietang.wang@symbio.com', 'p10', 'MTIzNDU2', '1374051835', '1374051835930', '10');
