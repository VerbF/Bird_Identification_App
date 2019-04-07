/*
Navicat MySQL Data Transfer

Source Server         : xxs
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : bird_identification_app

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-04-07 10:26:25
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
INSERT INTO `birds` VALUES ('1', '黑脚信天翁,为鹱形目信天翁科下的一种大型海鸟， 单型种，无亚种分化。体长79-83厘米。嘴黑色，嘴基和眼下方灰白色，其余全身黑褐色。个别尾上覆羽和尾下覆羽白色，脚黑色，能与短尾信天翁区别。', 'Black_footed_Albatross');
INSERT INTO `birds` VALUES ('2', '黑背信天翁,是一种中等体型的信天翁，81厘米长，体重2210-2800克，是一种有极长翼羽的大鸟，翅狭长，翼展195-215厘米。雄性略大。鼻孔侧位，不愈合。特征为自颏至臀部为全白色，但翼上及背深色。翼下主要为白色，具深色边缘，覆羽具近黑色纵纹。', 'Laysan_Albatross');
