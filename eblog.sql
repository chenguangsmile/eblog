/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : 127.0.0.1:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-07-19 21:03:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_category
-- ----------------------------
DROP TABLE IF EXISTS `m_category`;
CREATE TABLE `m_category` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容描述',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `post_count` int(11) unsigned DEFAULT '0' COMMENT '该分类的内容数量',
  `order_num` int(11) DEFAULT NULL COMMENT '排序编码',
  `parent_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父级分类的ID',
  `meta_keywords` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO关键字',
  `meta_description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO描述内容',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_category
-- ----------------------------
INSERT INTO `m_category` VALUES ('1', '提问', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0', '0');
INSERT INTO `m_category` VALUES ('2', '分享', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0', '0');
INSERT INTO `m_category` VALUES ('3', '讨论', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0', '0');
INSERT INTO `m_category` VALUES ('4', '建议', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0', '0');

-- ----------------------------
-- Table structure for m_comment
-- ----------------------------
DROP TABLE IF EXISTS `m_comment`;
CREATE TABLE `m_comment` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论的内容',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '回复的评论ID',
  `post_id` bigint(32) NOT NULL COMMENT '评论的内容ID',
  `user_id` bigint(32) NOT NULL COMMENT '评论的用户ID',
  `vote_up` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '“顶”的数量',
  `vote_down` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '“踩”的数量',
  `level` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT NULL COMMENT '评论的状态',
  `created` datetime NOT NULL COMMENT '评论的时间',
  `modified` datetime DEFAULT NULL COMMENT '评论的更新时间',
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1279754875202191384 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_comment
-- ----------------------------
INSERT INTO `m_comment` VALUES ('1279754875202191363', '嗯 好好', null, '1279754797653704707', '7', '0', '0', '0', null, '2020-07-05 12:39:29', '2020-07-05 12:39:29', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191364', '1234', null, '1279754797653704707', '7', '0', '0', '0', null, '2020-07-05 12:58:11', '2020-07-05 12:58:11', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191365', '嗯 好的', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 13:02:59', '2020-07-05 13:02:59', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191366', '234', null, '1279754797653704707', '6', '0', '0', '0', null, '2020-07-05 13:05:21', '2020-07-05 13:05:21', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191367', '突然進入地獄', null, '1279754797653704707', '6', '0', '0', '0', null, '2020-07-05 13:12:39', '2020-07-05 13:12:39', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191368', '而後讓他', null, '1279754797653704707', '6', '0', '0', '0', null, '2020-07-05 13:12:45', '2020-07-05 13:12:45', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191369', '3456', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 13:17:39', '2020-07-05 13:17:39', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191370', '3456', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 13:17:45', '2020-07-05 13:17:45', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191371', '你好', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 13:24:27', '2020-07-05 13:24:27', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191372', '不错', null, '1279754797653704707', '6', '0', '0', '0', null, '2020-07-05 13:45:33', '2020-07-05 13:45:33', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191373', '@小明 666', null, '1279754797653704708', '1', '0', '0', '0', null, '2020-07-05 14:13:51', '2020-07-05 14:13:51', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191374', '@小明 小明好', null, '1279754797653704708', '7', '0', '0', '0', null, '2020-07-05 14:16:41', '2020-07-05 14:16:41', '1');
INSERT INTO `m_comment` VALUES ('1279754875202191375', '@admin 好好好好', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 14:17:41', '2020-07-05 14:17:41', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191376', '@小明 好好', null, '1279754797653704708', '7', '0', '0', '0', null, '2020-07-05 16:01:11', '2020-07-05 16:01:11', '1');
INSERT INTO `m_comment` VALUES ('1279754875202191377', '哈哈哈', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 16:02:29', '2020-07-05 16:02:29', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191378', '@小明  在不', null, '1279754797653704708', '7', '0', '0', '0', null, '2020-07-05 16:05:55', '2020-07-05 16:05:55', '1');
INSERT INTO `m_comment` VALUES ('1279754875202191379', '在不', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 16:06:58', '2020-07-05 16:06:58', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191380', '有人吗', null, '1279754797653704708', '6', '0', '0', '0', null, '2020-07-05 16:07:10', '2020-07-05 16:07:10', '0');
INSERT INTO `m_comment` VALUES ('1279754875202191381', '@小明  小明同学', null, '1279754797653704708', '7', '0', '0', '0', null, '2020-07-05 16:08:29', '2020-07-05 16:08:29', '1');
INSERT INTO `m_comment` VALUES ('1279754875202191382', '@小明 1234', null, '1279754797653704708', '7', '0', '0', '0', null, '2020-07-05 16:08:53', '2020-07-05 16:08:53', '1');
INSERT INTO `m_comment` VALUES ('1279754875202191383', '@小明 23456', null, '1279754797653704708', '1', '0', '0', '0', null, '2020-07-05 16:11:27', '2020-07-05 16:11:27', '0');

-- ----------------------------
-- Table structure for m_post
-- ----------------------------
DROP TABLE IF EXISTS `m_post`;
CREATE TABLE `m_post` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `edit_mode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '编辑模式：html可视化，markdown ..',
  `category_id` bigint(32) DEFAULT NULL,
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `vote_up` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支持人数',
  `vote_down` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '反对人数',
  `view_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '访问量',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
  `recommend` tinyint(1) DEFAULT '0' COMMENT '是否为精华',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL COMMENT '最后更新日期',
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1279754797653704709 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_post
-- ----------------------------
INSERT INTO `m_post` VALUES ('1279754797653704707', '第一篇文章', ' 呵呵呵哈或或或或\n呵呵呵呵呵呵呵\nface[拜拜] \n[hr]', '0', '1', '7', '0', '0', '2', '6', '0', '0', '0', '2020-07-05 12:39:17', '2020-07-05 12:39:17', '0');
INSERT INTO `m_post` VALUES ('1279754797653704708', '大数据该怎么学', ' 324567耳机有人带那么容易你们委托人\n\n让他你今天日内瓦\n[hr]\n一天吗内容', '0', '3', '7', '0', '0', '39', '10', '0', '1', '0', '2020-07-05 12:59:31', '2020-07-05 12:59:31', '0');

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机电话',
  `point` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个性签名',
  `gender` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `wechat` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号',
  `vip_level` int(32) DEFAULT '0' COMMENT 'vip等级',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `post_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '内容数量',
  `comment_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论数量',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `lasted` datetime DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime NOT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES ('1', '小红', '96e79218965eb72c92a549dd5a330112', '11111@qq.com', null, '0', '关注公众号：MarkerHub，一起学Java！！', '1', null, '0', null, '/upload/avatar/avatar_1.jpg', '0', '0', '0', '2020-07-05 16:11:03', '2020-04-28 14:37:24', null, '0');
INSERT INTO `m_user` VALUES ('2', 'test007', '96e79218965eb72c92a549dd5a330112', '1111@qq.com', null, '0', null, '0', null, '0', null, '/res/images/avatar/default.png', '0', '0', '0', null, '2020-04-28 14:45:07', null, '0');
INSERT INTO `m_user` VALUES ('3', 'test004', '96e79218965eb72c92a549dd5a330112', '144d11@qq.com', null, '0', null, '0', null, '0', null, '/res/images/avatar/default.png', '0', '0', '0', null, '2020-04-28 14:48:26', null, '0');
INSERT INTO `m_user` VALUES ('4', 'test005', '96e79218965eb72c92a549dd5a330112', '144d15@qq.com', null, '0', null, '0', null, '0', null, '/res/images/avatar/default.png', '0', '0', '0', null, '2020-04-28 14:48:26', null, '0');
INSERT INTO `m_user` VALUES ('5', 'test00756', '96e79218965eb72c92a549dd5a330112', '45454541@qq.com', null, '0', null, '0', null, '0', null, '/res/images/avatar/default.png', '0', '0', '0', null, '2020-04-28 14:53:49', null, '0');
INSERT INTO `m_user` VALUES ('6', '小明', '96e79218965eb72c92a549dd5a330112', '111@123.com', null, '0', '哈哈哈哈哈哈哈', '1', null, '0', null, '/upload/avatar/avatar_1276845201016573954.jpg', '0', '0', '0', '2020-07-07 14:58:06', '2020-06-27 11:49:53', '2020-06-27 11:49:53', '0');
INSERT INTO `m_user` VALUES ('7', 'admin', '96e79218965eb72c92a549dd5a330112', 'admin@123.com', null, '0', null, '0', null, '0', null, '/upload/avatar/avatar_7.gif', '0', '0', '0', '2020-07-13 15:31:52', '2020-07-05 04:35:57', '2020-07-05 04:35:57', '0');

-- ----------------------------
-- Table structure for m_user_action
-- ----------------------------
DROP TABLE IF EXISTS `m_user_action`;
CREATE TABLE `m_user_action` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
  `action` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '动作类型',
  `point` int(11) DEFAULT NULL COMMENT '得分',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的帖子ID',
  `comment_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的评论ID',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_action
-- ----------------------------

-- ----------------------------
-- Table structure for m_user_collection
-- ----------------------------
DROP TABLE IF EXISTS `m_user_collection`;
CREATE TABLE `m_user_collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  `post_user_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1279422257998868482 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_collection
-- ----------------------------

-- ----------------------------
-- Table structure for m_user_message
-- ----------------------------
DROP TABLE IF EXISTS `m_user_message`;
CREATE TABLE `m_user_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(20) DEFAULT NULL COMMENT '发送消息的用户ID',
  `to_user_id` bigint(20) DEFAULT NULL COMMENT '接收消息的用户ID',
  `post_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的帖子',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `type` tinyint(2) DEFAULT NULL COMMENT '消息类型',
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_message
-- ----------------------------
INSERT INTO `m_user_message` VALUES ('4', '1', '7', '1279754797653704708', '1279754875202191371', '你好', '1', '2020-07-05 13:24:27', '2020-07-05 13:24:27', '1', '0');
INSERT INTO `m_user_message` VALUES ('5', '6', '7', '1279754797653704707', '1279754875202191372', '不错', '1', '2020-07-05 13:45:33', '2020-07-05 13:45:33', '1', '0');
INSERT INTO `m_user_message` VALUES ('6', '1', '7', '1279754797653704708', '1279754875202191373', '@小明 666', '1', '2020-07-05 14:13:51', '2020-07-05 14:13:51', '1', '0');
INSERT INTO `m_user_message` VALUES ('7', '1', '6', '1279754797653704708', '1279754875202191373', '@小明 666', '2', '2020-07-05 14:13:51', '2020-07-05 14:13:51', '1', '0');
INSERT INTO `m_user_message` VALUES ('8', '7', '6', '1279754797653704708', '1279754875202191374', '@小明 小明好', '2', '2020-07-05 14:16:41', '2020-07-05 14:16:41', '1', '0');
INSERT INTO `m_user_message` VALUES ('9', '6', '7', '1279754797653704708', '1279754875202191375', '@admin 好好好好', '1', '2020-07-05 14:17:41', '2020-07-05 14:17:41', '1', '0');
INSERT INTO `m_user_message` VALUES ('11', '6', '7', '1279754797653704708', '1279754875202191377', '哈哈哈', '1', '2020-07-05 16:02:29', '2020-07-05 16:02:29', '0', '0');
INSERT INTO `m_user_message` VALUES ('12', '6', '7', '1279754797653704708', '1279754875202191379', '在不', '1', '2020-07-05 16:06:58', '2020-07-05 16:06:58', '1', '0');
INSERT INTO `m_user_message` VALUES ('13', '6', '7', '1279754797653704708', '1279754875202191380', '有人吗', '1', '2020-07-05 16:07:10', '2020-07-05 16:07:10', '1', '0');
INSERT INTO `m_user_message` VALUES ('14', '1', '6', '1279754797653704708', '1279754875202191383', '@小明 23456', '2', '2020-07-05 16:11:27', '2020-07-05 16:11:27', '0', '0');
