/*
Navicat MySQL Data Transfer

Source Server         : po
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : spruce

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2013-05-31 14:14:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `attr_type`
-- ----------------------------
DROP TABLE IF EXISTS `attr_type`;
CREATE TABLE `attr_type` (
  `id_attr` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `data_type` enum('char','num','bool','blobchar') NOT NULL DEFAULT 'char',
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_attr`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of attr_type
-- ----------------------------
INSERT INTO `attr_type` VALUES ('1', 'Camera', 'char', 'Camera');
INSERT INTO `attr_type` VALUES ('2', 'Lens', 'char', 'Lens');
INSERT INTO `attr_type` VALUES ('3', 'Tripod', 'char', 'tripod  ');

-- ----------------------------
-- Table structure for `camera`
-- ----------------------------
DROP TABLE IF EXISTS `camera`;
CREATE TABLE `camera` (
  `id_user` int(11) NOT NULL,
  `equipment` varchar(64) NOT NULL,
  `type` varchar(16) NOT NULL COMMENT 'camera,lens,tripod,filter',
  `id_camera` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_camera`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of camera
-- ----------------------------
INSERT INTO `camera` VALUES ('1', 'po', 'camera', '1');
INSERT INTO `camera` VALUES ('1', 'pl', 'lens', '2');

-- ----------------------------
-- Table structure for `comments`
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id_comment` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_photo` bigint(20) DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `ip` varchar(96) DEFAULT NULL,
  `create_at` int(11) DEFAULT NULL,
  `create_at_gmt` datetime DEFAULT NULL,
  `approved` tinyint(4) DEFAULT NULL,
  `disabled` tinyint(4) DEFAULT NULL,
  `id_user_reply` int(11) DEFAULT NULL,
  `id_parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_comment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------

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
INSERT INTO `metadata_1_followed` VALUES ('19', '0', '1', '0', '1369816048464', '1369816015');
INSERT INTO `metadata_1_followed` VALUES ('24', '0', '1', '0', '1369725780246', '1369715624');

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
INSERT INTO `metadata_1_following` VALUES ('19', '0', '1', '0', '1369816048464', '1369816015');
INSERT INTO `metadata_1_following` VALUES ('24', '0', '1', '0', '1369725780246', '1369715624');

-- ----------------------------
-- Table structure for `notify`
-- ----------------------------
DROP TABLE IF EXISTS `notify`;
CREATE TABLE `notify` (
  `id_user` int(11) NOT NULL DEFAULT '0',
  `wall` tinyint(4) DEFAULT NULL,
  `photo` tinyint(4) DEFAULT NULL,
  `story` tinyint(4) DEFAULT NULL,
  `favorite` tinyint(4) DEFAULT NULL,
  `editor_choice` tinyint(4) DEFAULT NULL,
  `up_pop` tinyint(4) DEFAULT NULL,
  `following` tinyint(4) DEFAULT NULL,
  `buy` tinyint(4) DEFAULT NULL,
  `updates` tinyint(4) DEFAULT NULL,
  `account_changed` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of notify
-- ----------------------------

-- ----------------------------
-- Table structure for `photo_access`
-- ----------------------------
DROP TABLE IF EXISTS `photo_access`;
CREATE TABLE `photo_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_photo` bigint(20) DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `at` int(11) DEFAULT NULL,
  `cancel` tinyint(4) DEFAULT NULL COMMENT '取消操作',
  `ip` varchar(128) NOT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '访问类型1=view,2=vote,3=favorite',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_photo` (`id_photo`,`id_user`) USING BTREE,
  UNIQUE KEY `idx_ip_photo` (`id_photo`,`ip`),
  KEY `idx_favorite` (`id_photo`,`id_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of photo_access
-- ----------------------------
INSERT INTO `photo_access` VALUES ('1', '19', '24', '1369731067', '0', '127.0.0.1', '1');
INSERT INTO `photo_access` VALUES ('2', '18', '24', '1369788865', '0', '127.0.0.1', '1');
INSERT INTO `photo_access` VALUES ('3', '1', '24', '1369788878', '0', '127.0.0.1', '1');
INSERT INTO `photo_access` VALUES ('4', '17', '24', '1369791980', '0', '127.0.0.1', '1');

-- ----------------------------
-- Table structure for `photo_category`
-- ----------------------------
DROP TABLE IF EXISTS `photo_category`;
CREATE TABLE `photo_category` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `desc` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of photo_category
-- ----------------------------
INSERT INTO `photo_category` VALUES ('Abstract', 'Abstract');
INSERT INTO `photo_category` VALUES ('Animals', 'Animals');
INSERT INTO `photo_category` VALUES ('Black and White', 'Black and White');
INSERT INTO `photo_category` VALUES ('Celebrities', 'Celebrities');
INSERT INTO `photo_category` VALUES ('City &amp; Architecture', 'City &amp; Architecture');
INSERT INTO `photo_category` VALUES ('Commercial', 'Commercial');
INSERT INTO `photo_category` VALUES ('Concert', 'Concert');
INSERT INTO `photo_category` VALUES ('Family', 'Family');
INSERT INTO `photo_category` VALUES ('Fashion', 'Fashion');
INSERT INTO `photo_category` VALUES ('Film', 'Film');
INSERT INTO `photo_category` VALUES ('Fine Art', 'Fine Art');
INSERT INTO `photo_category` VALUES ('Food', 'Food');
INSERT INTO `photo_category` VALUES ('Journalism', 'Journalism');
INSERT INTO `photo_category` VALUES ('Landscapes', 'Landscapes');
INSERT INTO `photo_category` VALUES ('Macro', 'Macro');
INSERT INTO `photo_category` VALUES ('Nature', 'Nature');
INSERT INTO `photo_category` VALUES ('Nude', 'Nude');
INSERT INTO `photo_category` VALUES ('People', 'People');
INSERT INTO `photo_category` VALUES ('Performing Arts', 'Performing Arts');
INSERT INTO `photo_category` VALUES ('Sport', 'Sport');
INSERT INTO `photo_category` VALUES ('Still Life', 'Still Life');
INSERT INTO `photo_category` VALUES ('Street', 'Street');
INSERT INTO `photo_category` VALUES ('Transportation', 'Transportation');
INSERT INTO `photo_category` VALUES ('Travel', 'Travel');
INSERT INTO `photo_category` VALUES ('Uncategorized', 'Uncategorized');
INSERT INTO `photo_category` VALUES ('Underwater', 'Underwater');
INSERT INTO `photo_category` VALUES ('Urban Exploration', 'Urban Exploration');
INSERT INTO `photo_category` VALUES ('Wedding', 'Wedding');

-- ----------------------------
-- Table structure for `photos`
-- ----------------------------
DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos` (
  `id_photo` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `title` varchar(32) DEFAULT 'Untitled',
  `desc` varchar(256) DEFAULT NULL,
  `category` varchar(32) DEFAULT NULL,
  `create_at` int(11) DEFAULT '0',
  `create_at_gmt` datetime DEFAULT NULL,
  `update_at` bigint(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `adult` tinyint(4) DEFAULT '1',
  `copyright` tinyint(4) DEFAULT NULL,
  `tags` varchar(256) DEFAULT NULL,
  `make` varchar(20) DEFAULT NULL,
  `model` varchar(32) DEFAULT NULL,
  `lens` varchar(32) DEFAULT NULL,
  `aperture` varchar(6) DEFAULT NULL,
  `shutter` varchar(10) DEFAULT NULL,
  `iso` varchar(10) DEFAULT NULL,
  `focus` varchar(6) DEFAULT NULL,
  `ev` varchar(6) DEFAULT NULL,
  `original_at` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of photos
-- ----------------------------
INSERT INTO `photos` VALUES ('1', '24', '25559473', '', '0', '1369622717', '2013-05-27 10:45:17', '1369622717288', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '4', '1/1000', '100', '54', '-1/3', '2013-02-13 18:34:05');
INSERT INTO `photos` VALUES ('2', '24', '25559469', '', '0', '1369622722', '2013-05-27 10:45:22', '1369622722075', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '4', '1/8000', '100', '55', '-1/3', '2013-02-13 17:18:04');
INSERT INTO `photos` VALUES ('3', '24', '25559470', '', '0', '1369622733', '2013-05-27 10:45:33', '1369622733853', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/20', '100', '17', '-1/3', '2013-02-13 18:17:41');
INSERT INTO `photos` VALUES ('4', '24', '25559478', '', '0', '1369622765', '2013-05-27 10:46:05', '1369622765855', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '2.8', '1/800', '100', '17', '-1/3', '2013-02-13 18:56:28');
INSERT INTO `photos` VALUES ('5', '24', '25559469', '', '0', '1369622769', '2013-05-27 10:46:09', '1369622769949', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '4', '1/8000', '100', '55', '-1/3', '2013-02-13 17:18:04');
INSERT INTO `photos` VALUES ('6', '24', '25559465', '', '0', '1369622773', '2013-05-27 10:46:13', '1369622773900', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/25', '200', '43', '-1/3', '2013-02-13 11:02:25');
INSERT INTO `photos` VALUES ('7', '24', '25559477', '', '0', '1369622778', '2013-05-27 10:46:18', '1369622778113', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '2.8', '1/800', '100', '33', '-1/3', '2013-02-13 18:54:55');
INSERT INTO `photos` VALUES ('8', '24', '25559434', '', '0', '1369622782', '2013-05-27 10:46:22', '1369622782764', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/400', '100', '55', '+1/3', '2013-02-14 16:15:01');
INSERT INTO `photos` VALUES ('9', '24', '25559432', '', '0', '1369622786', '2013-05-27 10:46:26', '1369622786393', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '5.6', '1/640', '100', '45', '+1/1.5', '2013-02-14 16:01:45');
INSERT INTO `photos` VALUES ('10', '24', '25559432', '', '0', '1369636846', '2013-05-27 14:40:46', '1369636846124', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '5.6', '1/640', '100', '45', '+1/1.5', '2013-02-14 16:01:45');
INSERT INTO `photos` VALUES ('11', '24', '25559437', '', '0', '1369636878', '2013-05-27 14:41:18', '1369636878166', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/800', '100', '23', '+1/3', '2013-02-14 16:16:25');
INSERT INTO `photos` VALUES ('12', '24', '25559434', '', '0', '1369636954', '2013-05-27 14:42:34', '1369636954838', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/400', '100', '55', '+1/3', '2013-02-14 16:15:01');
INSERT INTO `photos` VALUES ('13', '24', '25559439', '', '0', '1369636965', '2013-05-27 14:42:45', '1369636965536', '0', '0', '1', null, 'Canon', 'Canon EOS 60D', 'EF-S17-55mm f/2.8 IS USM', '8', '1/640', '100', '17', '+1/3', '2013-02-14 16:20:15');
INSERT INTO `photos` VALUES ('14', '24', 'Tulips', '', '0', '1369703921', '2013-05-28 09:18:41', '1369703921507', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-02-07 11:33:11');
INSERT INTO `photos` VALUES ('15', '24', 'Chrysanthemum2', '', '0', '1369703957', '2013-05-28 09:19:17', '1369703957984', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-03-14 13:59:26');
INSERT INTO `photos` VALUES ('16', '24', 'Lighthouse', '', '0', '1369703964', '2013-05-28 09:19:24', '1369703964300', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-02-11 11:32:51');
INSERT INTO `photos` VALUES ('17', '24', 'Desert', '', '0', '1369703971', '2013-05-28 09:19:31', '1369703971080', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-03-14 13:59:26');
INSERT INTO `photos` VALUES ('18', '24', 'Hydrangeas', '', '0', '1369703975', '2013-05-28 09:19:35', '1369703975986', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-03-24 16:41:53');
INSERT INTO `photos` VALUES ('19', '24', 'Jellyfish', '', '0', '1369703984', '2013-05-28 09:19:44', '1369703984633', '0', '0', '1', null, '', '', '', '', '', '', '', '', '2008-02-11 11:32:24');

-- ----------------------------
-- Table structure for `rank`
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id_photo` bigint(20) NOT NULL DEFAULT '0',
  `id_user` int(11) DEFAULT NULL,
  `view` int(11) DEFAULT NULL COMMENT '查看次数',
  `vote` int(11) DEFAULT NULL COMMENT '投票数',
  `favorite` int(11) DEFAULT NULL COMMENT '收藏数',
  `comment` int(11) DEFAULT NULL COMMENT '评论数',
  `score` float(11,0) DEFAULT NULL,
  `update_at` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES ('1', '24', '8', '0', '0', '0', '0', '1369788878626');
INSERT INTO `rank` VALUES ('2', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('3', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('4', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('5', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('6', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('7', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('8', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('9', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('10', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('11', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('12', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('13', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('14', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('15', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('16', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('17', '24', '1', '0', '0', '0', '0', '1369791980860');
INSERT INTO `rank` VALUES ('18', '24', '1', '0', '0', '0', '0', '1369788865915');
INSERT INTO `rank` VALUES ('19', '24', '3', '0', '0', '0', '0', '1369732308924');
INSERT INTO `rank` VALUES ('20', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('21', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('22', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('23', '24', '0', '0', '0', '0', '0', '1235435');
INSERT INTO `rank` VALUES ('24', '24', '0', '0', '0', '0', '0', '1235435');

-- ----------------------------
-- Table structure for `rank_copy`
-- ----------------------------
DROP TABLE IF EXISTS `rank_copy`;
CREATE TABLE `rank_copy` (
  `id_photo` bigint(20) NOT NULL DEFAULT '0',
  `id_user` int(11) DEFAULT NULL,
  `view` int(11) DEFAULT NULL COMMENT '查看次数',
  `vote` int(11) DEFAULT NULL COMMENT '投票数',
  `favorite` int(11) DEFAULT NULL COMMENT '收藏数',
  `comment` int(11) DEFAULT NULL COMMENT '评论数',
  `score` float(11,0) DEFAULT NULL,
  `update_at` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of rank_copy
-- ----------------------------
INSERT INTO `rank_copy` VALUES ('0', '24', null, null, null, null, null, '1235435');
INSERT INTO `rank_copy` VALUES ('1', '24', null, null, null, null, null, null);
INSERT INTO `rank_copy` VALUES ('2', '24', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `refresh`
-- ----------------------------
DROP TABLE IF EXISTS `refresh`;
CREATE TABLE `refresh` (
  `id_photo` bigint(20) NOT NULL DEFAULT '0',
  `title` varchar(32) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `at` int(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id_photo`),
  KEY `idx_time` (`at`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of refresh
-- ----------------------------
INSERT INTO `refresh` VALUES ('1', 'gj', '24', 'ss', '1369622717');
INSERT INTO `refresh` VALUES ('2', 'gj', '24', 'ff', '1369622722');
INSERT INTO `refresh` VALUES ('3', 'pip', '24', 'yuy', '1369622733');
INSERT INTO `refresh` VALUES ('4', 'gjh', '24', 'fff', '1369622765');
INSERT INTO `refresh` VALUES ('5', 'etrd', '24', 'ff', '1369622769');
INSERT INTO `refresh` VALUES ('6', 'dg', '24', 'yy', '1369622773');
INSERT INTO `refresh` VALUES ('7', 'dg', '24', 'y', '1369622778');
INSERT INTO `refresh` VALUES ('8', 'dgdg', '24', 'yy', '1369622782');
INSERT INTO `refresh` VALUES ('9', 'dgd', '24', 'yh', '1369622786');
INSERT INTO `refresh` VALUES ('10', 'dg', '24', 'y', '1369636846');
INSERT INTO `refresh` VALUES ('11', 'dg', '24', 'hhh', '1369636878');
INSERT INTO `refresh` VALUES ('12', '25559434uoiurwoeiruweoiruweo', '24', 'Tietang.Wang', '1369636954');
INSERT INTO `refresh` VALUES ('13', '25559439', '24', 'Tietang.Wang', '1369636965');
INSERT INTO `refresh` VALUES ('14', 'Tulips', '24', 'Tietang.Wang', '1369703921');
INSERT INTO `refresh` VALUES ('15', 'Chrysanthemum2', '24', 'Tietang.Wang', '1369703957');
INSERT INTO `refresh` VALUES ('16', 'Lighthouse', '24', 'Tietang.Wang', '1369703964');
INSERT INTO `refresh` VALUES ('17', 'Desert', '24', 'Tietang.Wang', '1369703971');
INSERT INTO `refresh` VALUES ('18', 'Hydrangeas', '24', 'Tietang.Wang', '1369703975');
INSERT INTO `refresh` VALUES ('19', 'Jellyfish', '24', 'Tietang.Wang', '1369703984');

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
INSERT INTO `rs_1_followed` VALUES ('19', '19', '0', '0', '1369816013', '1369816048464');
INSERT INTO `rs_1_followed` VALUES ('24', '24', '0', '0', '1369715624', '1369725780246');

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
INSERT INTO `rs_1_following` VALUES ('19', '19', '0', '0', '1369816013', '1369816048464');
INSERT INTO `rs_1_following` VALUES ('24', '24', '0', '0', '1369715624', '1369725780246');

-- ----------------------------
-- Table structure for `tags`
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id_tag` bigint(20) NOT NULL AUTO_INCREMENT,
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '',
  `type` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_tag`),
  KEY `tag_idx` (`name`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tags
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id_user` int(10) unsigned NOT NULL,
  `first_name` varchar(16) DEFAULT NULL,
  `last_name` varchar(16) DEFAULT NULL,
  `nice_name` varchar(32) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `phone_num` varchar(16) DEFAULT NULL,
  `country` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `about` varchar(512) DEFAULT NULL,
  `create_at` int(11) DEFAULT NULL,
  `update_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('24', '32', '32', 'Tietang.Wang', '1980-02-02', '2', '188909', 'china', 'sc', 'cd', 'hahu', null, null);

-- ----------------------------
-- Table structure for `user_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `user_attribute`;
CREATE TABLE `user_attribute` (
  `id_user` int(10) unsigned NOT NULL DEFAULT '0',
  `id_attr` int(10) unsigned NOT NULL DEFAULT '0',
  `value` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_user`,`id_attr`),
  KEY `user_id` (`id_attr`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `user_link`
-- ----------------------------
DROP TABLE IF EXISTS `user_link`;
CREATE TABLE `user_link` (
  `id_user` int(11) DEFAULT NULL,
  `id_weibo` varchar(20) DEFAULT NULL,
  `id_qq` varchar(20) DEFAULT NULL,
  `id_facebook` varchar(20) DEFAULT NULL,
  `id_twitter` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_link
-- ----------------------------

-- ----------------------------
-- Table structure for `user_pwd`
-- ----------------------------
DROP TABLE IF EXISTS `user_pwd`;
CREATE TABLE `user_pwd` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `create_at` int(11) DEFAULT '0',
  `update_at` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_pwd
-- ----------------------------
INSERT INTO `user_pwd` VALUES ('24', 'mylooon@163.com', 'tietang', 'MTIzNDU2\r\n', null, null);
