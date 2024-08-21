/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : xm-account

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 27/12/2023 09:16:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ac
-- ----------------------------
DROP TABLE IF EXISTS `ac`;
CREATE TABLE `ac`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `balance` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '余额',
  `income` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '收入',
  `pay` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '支出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ac
-- ----------------------------
INSERT INTO `ac` VALUES (4, 4, 800.00, 1100.00, 300.00);
INSERT INTO `ac` VALUES (5, 5, 50.00, 50.00, 0.00);

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9090/files/1697438073596-avatar.png', 'ADMIN', '13677889922', 'admin@xm.com');

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账单分类',
  `pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账户类型',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账单类型',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES (9, '工资', '支付宝', 1000.00, '收入', NULL, '2023-12-14 12:25:34', 4);
INSERT INTO `bill` VALUES (10, '兼职', '支付宝', 100.00, '收入', NULL, '2023-12-14 12:26:02', 4);
INSERT INTO `bill` VALUES (11, '日用', '支付宝', 100.00, '支出', NULL, '2023-12-14 12:26:19', 4);
INSERT INTO `bill` VALUES (12, '兼职', '支付宝', 50.00, '收入', NULL, '2023-12-14 12:38:52', 5);
INSERT INTO `bill` VALUES (13, '购物', '支付宝', 200.00, '支出', NULL, '2023-12-15 14:10:45', 4);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账单分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '餐饮', '支出');
INSERT INTO `category` VALUES (2, '日用', '支出');
INSERT INTO `category` VALUES (3, '交通', '支出');
INSERT INTO `category` VALUES (4, '购物', '支出');
INSERT INTO `category` VALUES (5, '工资', '收入');
INSERT INTO `category` VALUES (6, '兼职', '收入');
INSERT INTO `category` VALUES (7, '理财', '收入');
INSERT INTO `category` VALUES (8, '礼金', '收入');
INSERT INTO `category` VALUES (9, '其他', '收入');

-- ----------------------------
-- Table structure for notebook
-- ----------------------------
DROP TABLE IF EXISTS `notebook`;
CREATE TABLE `notebook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '记账日记' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notebook
-- ----------------------------
INSERT INTO `notebook` VALUES (3, '我的日记怎么没了？？', 'http://localhost:9090/files/1702607044974-微信截图_20231117154820.png', '<p>我的日记怎么没了？？</p><p><br/></p><p>真的很垃圾哦&nbsp; 这个破烂系统把我日记删了&nbsp; 难受</p>', 4, '2023-12-15');
INSERT INTO `notebook` VALUES (4, '我的新日记', 'http://localhost:9090/files/1702607059784-微信截图_20231117155159.png', '<p>我的新日记我的新日记我的新日记我的新日记</p><p>我的新日记我的新日记我的新日记</p><p><br/></p><p><br/></p><p>我的新日记我的新日记我的新日记</p>', 4, '2023-12-15');
INSERT INTO `notebook` VALUES (5, '这是我现在做的新玩意儿', 'http://localhost:9090/files/1702607935506-活动6.png', '<p><span style=\"background-color: rgb(194, 79, 74);\"><font color=\"#ffffff\">这是我现在做的新玩意儿这是我现在做的新玩意儿</font></span></p><p><br/></p><p><font color=\"#8baa4a\">这是我现在做的新玩意儿这是我现在做的新玩意儿</font></p><p><br/></p><p><font color=\"#f9963b\">这是我现在做的新玩意儿这是我现在做的新玩意儿这是我现在做的新玩意儿</font></p>', 4, '2023-12-15');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '今天系统正式上线，开始内测', '今天系统正式上线，开始内测', '2023-09-05', 'admin');
INSERT INTO `notice` VALUES (2, '所有功能都已完成，可以正常使用', '所有功能都已完成，可以正常使用', '2023-09-05', 'admin');
INSERT INTO `notice` VALUES (3, '今天天气很不错，可以出去一起玩了', '今天天气很不错，可以出去一起玩了', '2023-09-05', 'admin');

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '计划名称',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '开始日期',
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结束日期',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '存钱计划' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (2, '12月存钱计划1000元', 'http://localhost:9090/files/1702611613279-钱包.png', 1000.00, '2023-12-15', '2023-12-31', 4, '2023-12-15');
INSERT INTO `plan` VALUES (3, '12存2000元', 'http://localhost:9090/files/1702611629797-记账.png', 2000.00, '2023-12-15', '2023-12-30', 4, '2023-12-15');

-- ----------------------------
-- Table structure for plan_detail
-- ----------------------------
DROP TABLE IF EXISTS `plan_detail`;
CREATE TABLE `plan_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日期',
  `plan_id` int(11) NULL DEFAULT NULL COMMENT '存钱计划ID',
  `sum` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '计划明细' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of plan_detail
-- ----------------------------
INSERT INTO `plan_detail` VALUES (1, 100.00, '2023-12-15', 3, 100.00);
INSERT INTO `plan_detail` VALUES (2, 2000.00, '2023-12-15', 3, 2100.00);
INSERT INTO `plan_detail` VALUES (3, 100.00, '2023-12-15', 2, 100.00);
INSERT INTO `plan_detail` VALUES (4, 222.00, '2023-12-15', 2, 322.00);
INSERT INTO `plan_detail` VALUES (9, 100.00, '2023-12-15', 2, 422.00);
INSERT INTO `plan_detail` VALUES (10, 50.00, '2023-12-15', 2, 472.00);
INSERT INTO `plan_detail` VALUES (11, 200.00, '2023-12-15', 2, 672.00);
INSERT INTO `plan_detail` VALUES (12, 30.00, '2023-12-15', 2, 702.00);
INSERT INTO `plan_detail` VALUES (13, 80.00, '2023-12-15', 2, 782.00);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'bbb', '123', '小青哥哥1', 'http://localhost:9090/files/1702437658057-微信截图_20231114162539.png', 'USER', '13899887799', 'bbb1@xm.com', '女', '1999-10-02');
INSERT INTO `user` VALUES (4, 'aaa', '123', '小武哥哥', 'http://localhost:9090/files/1702437704985-微信截图_20231018172512.png', 'USER', '13988997799', 'xw@xm.com', '男', '2023-12-13');
INSERT INTO `user` VALUES (5, 'ccc', '123', '小青哥哥', 'http://localhost:9090/files/1702523483497-微信截图_20231018172452.png', 'USER', NULL, NULL, '男', '2001-12-10');

SET FOREIGN_KEY_CHECKS = 1;
