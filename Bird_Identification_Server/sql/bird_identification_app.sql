/*
Navicat MySQL Data Transfer

Source Server         : xxs
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : bird_identification_app

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-04-14 14:09:06
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of birds
-- ----------------------------
INSERT INTO `birds` VALUES ('1', '黑脚信天翁,为鹱形目信天翁科下的一种大型海鸟， 单型种，无亚种分化。体长79-83厘米。嘴黑色，嘴基和眼下方灰白色，其余全身黑褐色。个别尾上覆羽和尾下覆羽白色，脚黑色，能与短尾信天翁区别。', 'Black_footed_Albatross');
INSERT INTO `birds` VALUES ('2', '黑背信天翁,是一种中等体型的信天翁，81厘米长，体重2210-2800克，是一种有极长翼羽的大鸟，翅狭长，翼展195-215厘米。雄性略大。鼻孔侧位，不愈合。特征为自颏至臀部为全白色，但翼上及背深色。翼下主要为白色，具深色边缘，覆羽具近黑色纵纹。', 'Laysan_Albatross');
INSERT INTO `birds` VALUES ('3', '乌信天翁,乌信天翁属鸟纲、鹱形目、信天翁科、乌信天翁属，是中型信天翁，栖息于南极，大部分时间飞翔在南大西洋和印度洋亚热带海域，主要食物是头足类动物，鱼类，甲壳动物和腐肉。', 'Sooty_Albatross');
INSERT INTO `birds` VALUES ('4', '\r\n沟嘴犀鹃,属杜鹃科犀鹃属。全身黑色，分布于德克萨斯南部至秘鲁西部和巴西北部。', 'Groove_billed_Ani');
INSERT INTO `birds` VALUES ('5', '冠小海雀（Aethiacristatella）是海雀科的一种海鸟，身高约9英寸，羽毛为黑褐色或黑色，在繁殖季节，眼后会出现白色羽毛，头顶有一卷弯向前方的羽毛。喙为亮橙色，尾短，脚为黑色，有蹼。分部于阿拉斯加海岸地区，在开阔的海域过冬。以浮游生物为食。', 'Crested_Auklet');
