/*
Navicat MySQL Data Transfer

Source Server         : alibaba
Source Server Version : 50629
Source Host           : loc-internet.mysql.japan.rds.aliyuncs.com:3306
Source Database       : loc_db

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-12-11 08:31:24
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
  `create_date` timestamp NULL DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `is_child` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_children` (`child_id`),
  KEY `fk_parent` (`parent_id`),
  CONSTRAINT `fk_children` FOREIGN KEY (`child_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_parent` FOREIGN KEY (`parent_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('2', '1', '15', 'I wish I can win the SBCloud Hackathon Contest', '2016-12-10 22:30:25', '2016-12-10 22:30:25', '1');
INSERT INTO `message` VALUES ('3', '1', '15', 'OK I will give you a Macbook pro, then you can use it to hack and win the first pride.', '2016-12-11 01:18:34', '2016-12-11 01:18:34', '0');
INSERT INTO `message` VALUES ('4', '1', '15', 'Thank you very much.', '2016-12-11 01:18:44', '2016-12-11 01:18:44', '1');
INSERT INTO `message` VALUES ('5', '28', '31', 'サンタさん、今年学校で今年学校で頑張ったので、プレステーションがもらえる？', '2016-12-11 07:51:14', '2016-12-11 07:51:14', '1');
INSERT INTO `message` VALUES ('6', '28', '31', 'えーとね、もらえるよ。ただ、私サンタの袋にはプレステーションが切れたよ。ほかの欲しい門があるかい？', '2016-12-11 07:57:59', '2016-12-11 07:57:59', '0');
INSERT INTO `message` VALUES ('7', '28', '31', 'じゃ、釣りのツール釣りのツールセットはどう？休みの日釣りに行きたい', '2016-12-11 07:59:51', '2016-12-11 07:59:51', '1');
INSERT INTO `message` VALUES ('8', '28', '31', 'いいよ。', '2016-12-11 08:00:09', '2016-12-11 08:00:09', '0');
INSERT INTO `message` VALUES ('9', '29', '31', 'サンタクロース。PineAppleApplePenがすきです。くれませんでしょうか。', '2016-12-11 08:08:37', '2016-12-11 08:08:37', '1');
INSERT INTO `message` VALUES ('10', '29', '31', 'いいよ。', '2016-12-11 08:08:56', '2016-12-11 08:08:56', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'nvquocbao@gmail.com', '123456', 'Bao', '2000-01-01', null, '東京都目黒区', '0', '2016-12-10 10:34:12', '2016-12-10 10:34:12');
INSERT INTO `user` VALUES ('2', 'test2@gmail.com', '123456', 'Test2', '2005-09-09', null, '東京都目黒区', '0', '2016-12-10 14:35:26', '2016-12-10 14:35:26');
INSERT INTO `user` VALUES ('13', 'child1@gmail.com', '123456', 'Child1', '2000-01-01', null, '東京都渋谷区', '0', '2016-12-10 18:52:54', '2016-12-10 18:52:54');
INSERT INTO `user` VALUES ('14', 'parent1@gmail.com', '123456', 'Parent1', '1970-03-03', null, '東京都渋谷区', '1', '2016-12-10 18:53:29', '2016-12-10 18:53:29');
INSERT INTO `user` VALUES ('15', 'parent2@gmail.com', '123456', 'Parent2', '1980-09-09', null, '東京都目黒区', '1', '2016-12-10 18:54:54', '2016-12-10 18:54:54');
INSERT INTO `user` VALUES ('16', 'test1@gmail.com', '123457', 'test1', '2000-01-02', null, '東京都目黒区', '0', '2016-12-10 19:26:00', '2016-12-10 19:26:00');
INSERT INTO `user` VALUES ('18', 'test3@gmail.com', '123456', 'Test3', '2005-09-03', null, '東京都目黒区', '0', '2016-12-10 19:27:30', '2016-12-10 19:27:30');
INSERT INTO `user` VALUES ('20', 'trinhnt@gmail.com', '23456', 'Trinh', '1983-05-12', null, 'Chibaken', '1', '2016-12-10 18:47:38', '2016-12-10 18:47:38');
INSERT INTO `user` VALUES ('22', 'trinhnt2@gmail.com', '12343', 'Trinhnt2', '1983-05-12', null, 'Chibaken', '1', '2016-12-10 18:58:25', '2016-12-10 18:58:25');
INSERT INTO `user` VALUES ('23', 'trinhnt3@gmail.com', '123456', 'Trinh', '1983-05-12', null, 'Chibaken', '1', '2016-12-10 19:01:14', '2016-12-10 19:01:14');
INSERT INTO `user` VALUES ('24', 'training@gmail.com', '123456', 'Trinh', '2013-05-12', null, 'Chibaken', '0', '2016-12-10 19:02:20', '2016-12-10 19:02:20');
INSERT INTO `user` VALUES ('25', 'trinhnt5@gmail.com', '123535', 'Trinhnt', '1983-05-05', null, 'Trinh', '1', '2016-12-10 19:04:26', '2016-12-10 19:04:26');
INSERT INTO `user` VALUES ('27', 'child2@gmail.com', '123456', 'Child2', '2005-10-09', null, '東京都千代田区', '0', '2016-12-11 15:35:26', '2016-12-11 15:35:26');
INSERT INTO `user` VALUES ('28', 'child3_1@gmail.com', '123456', 'Child3_1', '2009-10-09', null, '東京都港区3-1-1', '0', '2016-12-11 15:36:26', '2016-12-11 15:36:26');
INSERT INTO `user` VALUES ('29', 'child3_2@gmail.com', '123456', 'Child3_2', '2007-12-12', null, '東京都港区3-1-1', '0', '2016-12-11 15:37:26', '2016-12-11 15:37:26');
INSERT INTO `user` VALUES ('30', 'child3_3@gmail.com', '123456', 'Child3_3', '2000-02-04', null, '東京都港区3-1-1', '0', '2016-12-09 15:37:26', '2016-12-09 15:37:26');
INSERT INTO `user` VALUES ('31', 'parent3@gmail.com', '123456', 'Parent3', '1983-12-04', null, '東京都港区3-1-1', '1', '2016-12-09 03:37:50', '2016-12-09 03:37:50');
