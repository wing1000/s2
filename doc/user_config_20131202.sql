/*
Navicat MySQL Data Transfer

Source Server         : po1
Source Server Version : 50533
Source Host           : 172.17.20.99:4000
Source Database       : spruce

Target Server Type    : MYSQL
Target Server Version : 50533
File Encoding         : 65001

Date: 2013-12-02 14:52:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_config
-- ----------------------------
DROP TABLE IF EXISTS `user_config`;
CREATE TABLE `user_config` (
  `id_user` int(10) unsigned NOT NULL,
  `dir_size` smallint(5) unsigned DEFAULT '20',
  `license` smallint(4) unsigned DEFAULT NULL,
  `notice` binary(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_config
-- ----------------------------
INSERT INTO `user_config` VALUES ('24', '9', '6', 0x31343600000000000000000000000000);

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
