/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50548
 Source Host           : localhost:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 50548
 File Encoding         : 65001

 Date: 31/05/2019 16:06:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'test', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'zhongtiancai@zy.com', '测试账号', NULL, '2018-09-29 13:55:30', '2018-09-29 13:55:39', 1);
INSERT INTO `t_admin` VALUES (3, 'admin', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/170157_yIl3_1767531.jpg', 'admin@163.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2019-03-20 15:38:50', 1);
INSERT INTO `t_admin` VALUES (5, 'test2', '$2a$10$vfTyOlZlyC3JEiL.0Y8O3uCMla7NISzXS3Gqfj6P/Zhewri0PC.je', NULL, 'zhongtiancai@zy.com', '钟', NULL, NULL, NULL, 1);
INSERT INTO `t_admin` VALUES (6, 'admin2', '$2a$10$Z9ZHhHU8S4HQim06Mhi8e.EqLCYoKIZj34ml54uQK/JgpzGp5.R3W', NULL, 'zhongtiancai@zy.com', '钟天才', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户和角色关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES (27, 1, 1);
INSERT INTO `t_admin_role` VALUES (28, 1, 2);
INSERT INTO `t_admin_role` VALUES (29, 5, 1);
INSERT INTO `t_admin_role` VALUES (30, 5, 2);
INSERT INTO `t_admin_role` VALUES (31, 3, 2);
INSERT INTO `t_admin_role` VALUES (32, 3, 3);
INSERT INTO `t_admin_role` VALUES (33, 6, 1);
INSERT INTO `t_admin_role` VALUES (34, 6, 2);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父级权限id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` int(1) NULL DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `uri` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后端资源路径',
  `status` int(1) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, 0, '用户管理', 1, NULL, 1, '2019-05-31 11:52:43', 1, 'admin');
INSERT INTO `t_permission` VALUES (2, 1, '用户列表', 1, NULL, 1, '2019-05-31 11:53:06', 1, 'adminlist');
INSERT INTO `t_permission` VALUES (3, 1, '角色管理', 1, NULL, 1, NULL, 1, 'rolelist');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '商品管理员', '商品管理员', '2018-09-30 15:46:11', 1, 0);
INSERT INTO `t_role` VALUES (2, '商品分类管理员', '商品分类管理员', NULL, 1, NULL);
INSERT INTO `t_role` VALUES (3, '商品类型管理员', '商品类型管理员', NULL, 1, NULL);
INSERT INTO `t_role` VALUES (4, '品牌管理员', '品牌管理员', '2018-09-30 15:54:12', 1, 0);
INSERT INTO `t_role` VALUES (5, '新角色', '新角色', '2019-05-31 16:00:46', 1, NULL);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `permission_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户角色和权限关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (18, 3, 1);
INSERT INTO `t_role_permission` VALUES (19, 3, 2);
INSERT INTO `t_role_permission` VALUES (20, 2, 1);
INSERT INTO `t_role_permission` VALUES (21, 2, 2);
INSERT INTO `t_role_permission` VALUES (22, 5, 1);
INSERT INTO `t_role_permission` VALUES (23, 5, 2);

SET FOREIGN_KEY_CHECKS = 1;
