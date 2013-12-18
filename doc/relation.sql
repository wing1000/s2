/*
Navicat MySQL Data Transfer

Source Server         : po
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : spruce

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2013-05-09 10:23:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `metadata_1_followed`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_1_followed`;
CREATE TABLE `metadata_1_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Records of metadata_1_followed
-- ----------------------------

-- ----------------------------
-- Table structure for `metadata_1_following`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_1_following`;
CREATE TABLE `metadata_1_following` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='positive direction';

-- ----------------------------
-- Records of metadata_1_following
-- ----------------------------

-- ----------------------------
-- Table structure for `rs_1_followed`
-- ----------------------------
DROP TABLE IF EXISTS `rs_1_followed`;
CREATE TABLE `rs_1_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Records of rs_1_followed
-- ----------------------------

-- ----------------------------
-- Table structure for `rs_1_following`
-- ----------------------------
DROP TABLE IF EXISTS `rs_1_following`;
CREATE TABLE `rs_1_following` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='positive direction';

-- ----------------------------
-- Records of rs_1_following
-- ----------------------------
