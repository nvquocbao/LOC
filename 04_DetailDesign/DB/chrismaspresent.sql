/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : chrismaspresent

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-10 19:03:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `child_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `content` text,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_children` (`child_id`),
  KEY `fk_parent` (`parent_id`),
  CONSTRAINT `fk_children` FOREIGN KEY (`child_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_parent` FOREIGN KEY (`parent_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `birthday` date DEFAULT NULL,
  `avatar_path` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '[0:child,1:parent]',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'nvquocbao@gmail.com', '123456', 'Bao', '2000-01-01', null, '東京都目黒区', '0', '2016-12-10 10:34:12', '2016-12-10 10:34:12');
INSERT INTO `user` VALUES ('2', 'test2@gmail.com', '123456', 'Test2', '2005-09-09', null, '東京都目黒区', '0', '2016-12-10 14:35:26', '2016-12-10 14:35:26');
INSERT INTO `user` VALUES ('13', 'child1@gmail.com', '123456', 'Child1', '2000-01-01', null, '東京都渋谷区', '0', '2016-12-10 18:52:54', '2016-12-10 18:52:54');
INSERT INTO `user` VALUES ('14', 'parent1@gmail.com', '123456', 'Parent1', '1970-03-03', null, '東京都渋谷区', '1', '2016-12-10 18:53:29', '2016-12-10 18:53:29');
INSERT INTO `user` VALUES ('15', 'parent2@gmail.com', '123456', 'Parent2', '1980-09-09', null, '東京都目黒区', '1', '2016-12-10 18:54:54', '2016-12-10 18:54:54');
