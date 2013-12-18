/*
Navicat MySQL Data Transfer

Source Server         : po
Source Server Version : 50525
Source Host           : localhost:4000
Source Database       : spruce

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2013-09-27 16:36:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for attr_type
-- ----------------------------
DROP TABLE IF EXISTS `attr_type`;
CREATE TABLE `attr_type` (
  `id_attr` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `data_type` enum('char','num','bool','blobchar') NOT NULL DEFAULT 'char',
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_attr`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Table structure for idtb64__2
-- ----------------------------
DROP TABLE IF EXISTS `idtb64__2`;
CREATE TABLE `idtb64__2` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `table` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_table` (`table`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Table structure for metadata_1_followed
-- ----------------------------
DROP TABLE IF EXISTS `metadata_1_followed`;
CREATE TABLE `metadata_1_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='negative direction';

-- ----------------------------
-- Table structure for metadata_1_following
-- ----------------------------
DROP TABLE IF EXISTS `metadata_1_following`;
CREATE TABLE `metadata_1_following` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='positive direction';

-- ----------------------------
-- Table structure for metadata_2_followed
-- ----------------------------
DROP TABLE IF EXISTS `metadata_2_followed`;
CREATE TABLE `metadata_2_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Table structure for metadata_2_following
-- ----------------------------
DROP TABLE IF EXISTS `metadata_2_following`;
CREATE TABLE `metadata_2_following` (
  `source_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `state` tinyint(4) NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `id_photo` bigint(20) unsigned NOT NULL,
  `id_user` int(11) unsigned NOT NULL,
  `nice_name` varchar(64) DEFAULT NULL,
  `title` varchar(32) DEFAULT 'Untitled',
  `desc` varchar(256) DEFAULT NULL,
  `category` varchar(32) DEFAULT NULL,
  `id_story` bigint(20) unsigned DEFAULT NULL,
  `create_at` int(11) unsigned DEFAULT '0',
  `create_at_gmt` datetime DEFAULT NULL,
  `update_at` bigint(11) unsigned DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `comment_count` int(11) unsigned DEFAULT NULL,
  `adult` tinyint(4) unsigned DEFAULT '1',
  `copyright` tinyint(4) unsigned DEFAULT NULL,
  `can_ps` tinyint(4) DEFAULT '0',
  `license` tinyint(10) DEFAULT NULL,
  `ip` int(11) DEFAULT NULL,
  `tags` varchar(256) DEFAULT NULL,
  `make` varchar(64) DEFAULT NULL,
  `model` varchar(64) DEFAULT NULL,
  `lens` varchar(64) DEFAULT NULL,
  `aperture` varchar(6) DEFAULT NULL,
  `shutter` varchar(10) DEFAULT NULL,
  `iso` varchar(10) DEFAULT NULL,
  `focus` varchar(6) DEFAULT NULL,
  `ev` varchar(6) DEFAULT NULL,
  `original_at` varchar(20) DEFAULT NULL,
  `white_balance` tinyint(4) DEFAULT NULL,
  `software` varchar(64) DEFAULT NULL,
  `flash` smallint(6) DEFAULT NULL,
  `color_space` tinyint(4) DEFAULT NULL,
  `metering_mode` tinyint(4) DEFAULT NULL,
  `exposure_mode` tinyint(4) DEFAULT NULL,
  `exposure_program` tinyint(4) DEFAULT NULL,
  `gps_latitude` double NOT NULL,
  `gps_longitude` double NOT NULL,
  `gps_altitude` double NOT NULL,
  `gps_origin` tinyint(4) DEFAULT '-1' COMMENT '0=photo or 1=google map or 2=baidu map',
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_access
-- ----------------------------
DROP TABLE IF EXISTS `photo_access`;
CREATE TABLE `photo_access` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_photo` bigint(20) unsigned NOT NULL,
  `category` tinyint(4) NOT NULL,
  `title` varchar(64) NOT NULL,
  `id_user` int(11) unsigned NOT NULL,
  `nice_name` varchar(64) NOT NULL,
  `at` int(11) unsigned NOT NULL,
  `cancel` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '取消操作',
  `ip` int(11) NOT NULL DEFAULT '0',
  `access_id_user` int(11) NOT NULL DEFAULT '0',
  `type` tinyint(4) unsigned NOT NULL COMMENT '访问类型1=view,2=vote,3=favorite',
  PRIMARY KEY (`id`),
  KEY `idx_user_photo` (`id_photo`,`id_user`) USING BTREE,
  KEY `idx_ip_photo` (`id_photo`,`ip`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_blink
-- ----------------------------
DROP TABLE IF EXISTS `photo_blink`;
CREATE TABLE `photo_blink` (
  `id_story` bigint(20) NOT NULL,
  `id_photo` bigint(20) NOT NULL,
  `title` varchar(64) NOT NULL,
  `content` varchar(5120) NOT NULL,
  `max_view` int(10) unsigned NOT NULL,
  `share_incr` smallint(6) NOT NULL,
  `create_at` int(10) unsigned NOT NULL,
  `updatea_at` bigint(20) unsigned NOT NULL,
  `view_count` int(10) unsigned NOT NULL,
  `disappeared` enum('false','true') NOT NULL DEFAULT 'false',
  PRIMARY KEY (`id_story`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_blink_access
-- ----------------------------
DROP TABLE IF EXISTS `photo_blink_access`;
CREATE TABLE `photo_blink_access` (
  `id_story` bigint(20) NOT NULL,
  `create_at` int(10) unsigned NOT NULL,
  `id_user` int(10) unsigned DEFAULT NULL,
  `ip` int(10) NOT NULL,
  PRIMARY KEY (`id_story`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_category
-- ----------------------------
DROP TABLE IF EXISTS `photo_category`;
CREATE TABLE `photo_category` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `desc` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_choice
-- ----------------------------
DROP TABLE IF EXISTS `photo_choice`;
CREATE TABLE `photo_choice` (
  `id_photo` bigint(20) unsigned NOT NULL DEFAULT '0',
  `category` tinyint(4) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `id_user` int(11) unsigned DEFAULT NULL,
  `nice_name` varchar(32) DEFAULT NULL,
  `at` int(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id_photo`),
  KEY `idx_time` (`at`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_comments
-- ----------------------------
DROP TABLE IF EXISTS `photo_comments`;
CREATE TABLE `photo_comments` (
  `id_comment` bigint(20) unsigned NOT NULL,
  `id_photo` bigint(20) unsigned DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `id_user` int(11) unsigned DEFAULT NULL,
  `user_nice_name` varchar(64) DEFAULT NULL,
  `ip` varchar(96) DEFAULT NULL,
  `create_at` int(11) unsigned DEFAULT NULL,
  `create_at_gmt` datetime DEFAULT NULL,
  `status` tinyint(4) unsigned DEFAULT NULL,
  `id_user_reply` int(11) unsigned DEFAULT NULL,
  `id_parent` bigint(20) unsigned DEFAULT NULL,
  `comment_photo` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id_comment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_favorite
-- ----------------------------
DROP TABLE IF EXISTS `photo_favorite`;
CREATE TABLE `photo_favorite` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_photo` bigint(20) unsigned NOT NULL,
  `category` tinyint(4) NOT NULL,
  `title` varchar(64) NOT NULL,
  `id_user` int(11) unsigned NOT NULL,
  `nice_name` varchar(64) NOT NULL,
  `at` int(11) unsigned NOT NULL,
  `cancel` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '取消操作',
  `access_id_user` int(11) NOT NULL DEFAULT '0',
  `ip` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_photo` (`id_photo`,`access_id_user`,`ip`) USING BTREE,
  KEY `idx_favorite` (`id_photo`,`id_user`) USING BTREE,
  KEY `idx_id_ip` (`id_photo`,`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_info
-- ----------------------------
DROP TABLE IF EXISTS `photo_info`;
CREATE TABLE `photo_info` (
  `id_photo` bigint(20) unsigned NOT NULL,
  `id_user` int(11) unsigned NOT NULL,
  `tags` varchar(256) DEFAULT NULL,
  `make` varchar(64) DEFAULT NULL,
  `model` varchar(64) DEFAULT NULL,
  `lens` varchar(64) DEFAULT NULL,
  `aperture` varchar(6) DEFAULT NULL,
  `shutter` varchar(10) DEFAULT NULL,
  `iso` varchar(10) DEFAULT NULL,
  `focus` varchar(6) DEFAULT NULL,
  `ev` varchar(6) DEFAULT NULL,
  `original_at` varchar(20) DEFAULT NULL,
  `white_balance` tinyint(4) DEFAULT NULL,
  `software` varchar(64) DEFAULT NULL,
  `flash` smallint(6) DEFAULT NULL,
  `color_space` tinyint(4) DEFAULT NULL,
  `metering_mode` tinyint(4) DEFAULT NULL,
  `exposure_mode` tinyint(4) DEFAULT NULL,
  `exposure_program` tinyint(4) DEFAULT NULL,
  `gps_latitude` double NOT NULL,
  `gps_longitude` double NOT NULL,
  `gps_altitude` double NOT NULL,
  `gps_origin` tinyint(4) DEFAULT '-1' COMMENT '0=photo or 1=google map or 2=baidu map',
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_rank
-- ----------------------------
DROP TABLE IF EXISTS `photo_rank`;
CREATE TABLE `photo_rank` (
  `id_photo` bigint(20) unsigned NOT NULL DEFAULT '0',
  `title` varchar(64) DEFAULT NULL,
  `category` tinyint(4) unsigned DEFAULT '0',
  `id_user` int(11) unsigned DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `view` int(11) unsigned DEFAULT NULL COMMENT '查看次数',
  `vote` int(11) unsigned DEFAULT NULL COMMENT '投票数',
  `favorite` int(11) unsigned DEFAULT NULL COMMENT '收藏数',
  `comment` int(11) unsigned DEFAULT NULL COMMENT '评论数',
  `score` float DEFAULT '0',
  `create_at` int(10) unsigned DEFAULT NULL,
  `update_at` bigint(20) unsigned DEFAULT NULL COMMENT '最后更新时间',
  `max_score` float DEFAULT '0',
  `max_at` bigint(20) unsigned DEFAULT '0',
  PRIMARY KEY (`id_photo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_ranks
-- ----------------------------
DROP TABLE IF EXISTS `photo_ranks`;
CREATE TABLE `photo_ranks` (
  `id_photo` bigint(20) unsigned NOT NULL DEFAULT '0',
  `day` date NOT NULL DEFAULT '0000-00-00',
  `id_user` int(11) unsigned DEFAULT NULL,
  `view` int(11) unsigned DEFAULT NULL COMMENT '查看次数',
  `vote` int(11) unsigned DEFAULT NULL COMMENT '投票数',
  `favorite` int(11) unsigned DEFAULT NULL COMMENT '收藏数',
  `comment` int(11) unsigned DEFAULT NULL COMMENT '评论数',
  `update_at` bigint(20) unsigned DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id_photo`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_refresh
-- ----------------------------
DROP TABLE IF EXISTS `photo_refresh`;
CREATE TABLE `photo_refresh` (
  `id_photo` bigint(20) unsigned NOT NULL DEFAULT '0',
  `category` tinyint(4) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `id_user` int(11) unsigned DEFAULT NULL,
  `nice_name` varchar(32) DEFAULT NULL,
  `at` int(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id_photo`),
  KEY `idx_time` (`at`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_set
-- ----------------------------
DROP TABLE IF EXISTS `photo_set`;
CREATE TABLE `photo_set` (
  `id_set` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `id_user` int(10) unsigned DEFAULT NULL,
  `create_at` int(11) unsigned DEFAULT NULL,
  `update_at` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id_set`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_sets
-- ----------------------------
DROP TABLE IF EXISTS `photo_sets`;
CREATE TABLE `photo_sets` (
  `id_set` bigint(20) unsigned NOT NULL,
  `id_photo` bigint(20) unsigned NOT NULL,
  `id_user` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_photo`),
  KEY `idx_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for photo_story
-- ----------------------------
DROP TABLE IF EXISTS `photo_story`;
CREATE TABLE `photo_story` (
  `id_story` bigint(20) NOT NULL,
  `title` varchar(64) NOT NULL,
  `content` varchar(5120) NOT NULL,
  `photos` varchar(1024) NOT NULL COMMENT '逗号分隔的id_photo list',
  PRIMARY KEY (`id_story`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo_tag
-- ----------------------------
DROP TABLE IF EXISTS `photo_tag`;
CREATE TABLE `photo_tag` (
  `id_tag` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `category` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_tag`),
  KEY `tag_idx` (`name`,`category`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rs_1_followed
-- ----------------------------
DROP TABLE IF EXISTS `rs_1_followed`;
CREATE TABLE `rs_1_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `state` tinyint(4) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='negative direction';

-- ----------------------------
-- Table structure for rs_1_following
-- ----------------------------
DROP TABLE IF EXISTS `rs_1_following`;
CREATE TABLE `rs_1_following` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `state` tinyint(4) unsigned NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='positive direction';

-- ----------------------------
-- Table structure for rs_2_followed
-- ----------------------------
DROP TABLE IF EXISTS `rs_2_followed`;
CREATE TABLE `rs_2_followed` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Table structure for rs_2_following
-- ----------------------------
DROP TABLE IF EXISTS `rs_2_following`;
CREATE TABLE `rs_2_following` (
  `source_id` int(10) unsigned NOT NULL,
  `target_id` int(10) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `created_at` int(10) unsigned NOT NULL,
  `updated_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`source_id`,`target_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `code` int(10) unsigned DEFAULT NULL,
  `value` varchar(128) DEFAULT '20',
  `name` varchar(32) DEFAULT NULL,
  `desc` varchar(128) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id_tag` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_tag`),
  KEY `tag_idx` (`name`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id_user` int(10) unsigned NOT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `nice_name` varchar(32) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `gender` tinyint(4) unsigned DEFAULT NULL,
  `phone_num` varchar(16) DEFAULT NULL,
  `country` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `about` varchar(512) DEFAULT NULL,
  `create_at` int(11) unsigned DEFAULT NULL,
  `update_at` bigint(20) unsigned DEFAULT NULL,
  `head_photo` tinyint(4) unsigned DEFAULT '0',
  `count_view` int(11) unsigned DEFAULT '0' COMMENT '查看次数',
  `count_vote` int(11) unsigned DEFAULT '0' COMMENT '投票数',
  `count_favorite` int(11) unsigned DEFAULT '0' COMMENT '收藏数',
  `count_comment` int(11) unsigned DEFAULT '0' COMMENT '评论数',
  `count_affection` int(11) unsigned DEFAULT '0' COMMENT '影响力，及为登录用户的vote数',
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=1;

-- ----------------------------
-- Table structure for user_attribute
-- ----------------------------
DROP TABLE IF EXISTS `user_attribute`;
CREATE TABLE `user_attribute` (
  `id_user` int(10) unsigned NOT NULL DEFAULT '0',
  `id_attr` int(10) unsigned NOT NULL DEFAULT '0',
  `value` varchar(60) DEFAULT NULL,
  `type` enum('pay','system','user') DEFAULT 'user',
  PRIMARY KEY (`id_user`,`id_attr`),
  KEY `user_id` (`id_attr`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_camera
-- ----------------------------
DROP TABLE IF EXISTS `user_camera`;
CREATE TABLE `user_camera` (
  `id_user` int(11) unsigned NOT NULL,
  `equipment` varchar(64) NOT NULL,
  `type` varchar(16) NOT NULL COMMENT 'camera,lens,tripod,filter',
  `id_camera` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_camera`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
-- Table structure for user_license
-- ----------------------------
DROP TABLE IF EXISTS `user_license`;
CREATE TABLE `user_license` (
  `id_user` int(255) DEFAULT NULL,
  `license` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_link
-- ----------------------------
DROP TABLE IF EXISTS `user_link`;
CREATE TABLE `user_link` (
  `id_user` int(11) unsigned NOT NULL,
  `sina_weibo` varchar(64) DEFAULT NULL,
  `qq` varchar(64) DEFAULT NULL,
  `facebook` varchar(64) DEFAULT NULL,
  `twitter` varchar(64) DEFAULT NULL,
  `google` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_photograph
-- ----------------------------
DROP TABLE IF EXISTS `user_photograph`;
CREATE TABLE `user_photograph` (
  `idUser` int(11) NOT NULL,
  `idPhoto` bigint(20) NOT NULL,
  PRIMARY KEY (`idUser`,`idPhoto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
-- Table structure for user_social
-- ----------------------------
DROP TABLE IF EXISTS `user_social`;
CREATE TABLE `user_social` (
  `id_user` int(10) unsigned NOT NULL,
  `web_site` varchar(64) DEFAULT NULL,
  `weibo` varchar(64) DEFAULT NULL,
  `qq` varchar(64) DEFAULT NULL,
  `qq_weibo` varchar(64) DEFAULT NULL,
  `douban` varchar(64) DEFAULT NULL,
  `twitter` varchar(64) DEFAULT NULL,
  `facebook` varchar(64) DEFAULT NULL,
  `flickr` varchar(64) DEFAULT NULL,
  `blog` varchar(64) DEFAULT NULL,
  `skype` varchar(64) DEFAULT NULL,
  `fengniao` varchar(64) DEFAULT NULL,
  `renren` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_stats
-- ----------------------------
DROP TABLE IF EXISTS `user_stats`;
CREATE TABLE `user_stats` (
  `id_user` int(11) unsigned NOT NULL DEFAULT '0',
  `view` int(11) unsigned DEFAULT NULL COMMENT '查看次数',
  `vote` int(11) unsigned DEFAULT NULL COMMENT '投票数',
  `favorite` int(11) unsigned DEFAULT NULL COMMENT '收藏数',
  `comment` int(11) unsigned DEFAULT NULL COMMENT '评论数',
  `affection` int(11) unsigned DEFAULT '0' COMMENT '影响力，及为登录用户的vote数',
  `update_at` bigint(20) unsigned DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_verify
-- ----------------------------
DROP TABLE IF EXISTS `user_verify`;
CREATE TABLE `user_verify` (
  `id_user` int(10) unsigned NOT NULL DEFAULT '0',
  `verify` varchar(256) NOT NULL,
  `create_at` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for wp_category
-- ----------------------------
DROP TABLE IF EXISTS `wp_category`;
CREATE TABLE `wp_category` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(17) NOT NULL DEFAULT '',
  `id_num` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `content` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wp_comments
-- ----------------------------
DROP TABLE IF EXISTS `wp_comments`;
CREATE TABLE `wp_comments` (
  `id_comment` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_post` bigint(20) unsigned DEFAULT NULL COMMENT 'blog id',
  `content` varchar(256) DEFAULT NULL COMMENT '注释内容',
  `id_user` int(11) unsigned DEFAULT NULL COMMENT 'user id',
  `user_email` varchar(64) DEFAULT NULL COMMENT 'user email',
  `username` varchar(64) DEFAULT NULL COMMENT 'username',
  `user_ip` varchar(96) DEFAULT NULL COMMENT 'user 客户端ip',
  `create_at` int(11) unsigned DEFAULT NULL COMMENT '评论时间 秒数',
  `create_at_gmt` datetime DEFAULT NULL COMMENT '评论当地时间(GMT+0时间) ',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态: <0 不可显示,>0 可显示',
  `type` tinyint(4) unsigned DEFAULT NULL COMMENT '类型:',
  `id_user_reply` int(11) unsigned DEFAULT NULL COMMENT '@评论的user id',
  `id_parent` bigint(20) unsigned DEFAULT NULL COMMENT '@评论的父评论ID',
  PRIMARY KEY (`id_comment`),
  KEY `comments_status` (`status`),
  KEY `comments_id_post` (`id_post`),
  KEY `comments_parent` (`id_parent`) USING BTREE,
  KEY `comments_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wp_comment_meta
-- ----------------------------
DROP TABLE IF EXISTS `wp_comment_meta`;
CREATE TABLE `wp_comment_meta` (
  `id_meta` bigint(20) unsigned DEFAULT NULL,
  `id_comment` bigint(20) unsigned DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` longtext,
  KEY `idx_comments` (`id_comment`),
  KEY `idx_meta_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for wp_options
-- ----------------------------
DROP TABLE IF EXISTS `wp_options`;
CREATE TABLE `wp_options` (
  `id_option` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一ID',
  `id_post` bigint(20) unsigned NOT NULL COMMENT '博客ID，用于多用户博客，默认0',
  `option_name` varchar(64) NOT NULL COMMENT '键名',
  `option_value` varchar(256) NOT NULL COMMENT '键值',
  `autoload` tinyint(4) NOT NULL DEFAULT '0' COMMENT '在WordPress载入时自动载入（yes/no）',
  PRIMARY KEY (`id_option`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wp_posts
-- ----------------------------
DROP TABLE IF EXISTS `wp_posts`;
CREATE TABLE `wp_posts` (
  `id_post` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一ID',
  `id_category` bigint(20) unsigned NOT NULL DEFAULT '0',
  `id_user` bigint(20) unsigned NOT NULL COMMENT 'user id',
  `username` varchar(64) NOT NULL COMMENT '对应作者ID',
  `create_at` datetime NOT NULL COMMENT '发布时间',
  `create_at_gmt` datetime NOT NULL COMMENT '发布时间（GMT+0时间）',
  `content` mediumtext NOT NULL COMMENT '正文',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `excerpt` varchar(512) NOT NULL COMMENT '摘录',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文章状态（publish/auto-draft/inherit等）',
  `comment_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '评论状态（open/closed）',
  `password` varchar(32) DEFAULT NULL COMMENT '文章密码',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  `update_at_gmt` datetime DEFAULT NULL COMMENT '修改时间（GMT+0时间）',
  `content_filtered` varchar(512) DEFAULT NULL COMMENT '内容过滤',
  `guid` varchar(33) DEFAULT NULL COMMENT '未知',
  `menu_order` int(11) NOT NULL DEFAULT '0' COMMENT '排序ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '文章类型（post/page等）',
  `comment_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论总数',
  PRIMARY KEY (`id_post`),
  KEY `post_user` (`id_user`) USING BTREE,
  KEY `post_title` (`title`) USING BTREE,
  KEY `post_type_status_date` (`status`,`update_at_gmt`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wp_posts_meta
-- ----------------------------
DROP TABLE IF EXISTS `wp_posts_meta`;
CREATE TABLE `wp_posts_meta` (
  `id_meta` bigint(20) unsigned DEFAULT NULL,
  `id_post` bigint(20) unsigned DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` longtext,
  KEY `meta_post_id` (`id_post`) USING BTREE,
  KEY `meta__post_key` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Procedure structure for p_while
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_while`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_while`()
BEGIN
DECLARE v INT; 
    SET v = 0; 
    WHILE v < 25 DO 
        insert into rank(id_photo,id_user,update_at) VALUES(v,24,1235435);
        SET v = v + 1; 

    END WHILE; 
  

END
;;
DELIMITER ;
