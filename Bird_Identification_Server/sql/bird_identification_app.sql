/*
Navicat MySQL Data Transfer

Source Server         : xxs
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : bird_identification_app

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-04-07 10:02:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for birds
-- ----------------------------
DROP TABLE IF EXISTS `birds`;
CREATE TABLE `birds` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `bird_info` varchar(255) DEFAULT NULL,
  `bird_name` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of birds
-- ----------------------------
INSERT INTO `birds` VALUES ('1', '黑脚信天翁', 'Black_footed_Albatross');
INSERT INTO `birds` VALUES ('2', '黑背信天翁', 'Laysan_Albatross');
