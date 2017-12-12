/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : frame

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-12 13:48:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bs_user_info
-- ----------------------------
DROP TABLE IF EXISTS `bs_user_info`;
CREATE TABLE `bs_user_info` (
  `id_` varchar(32) NOT NULL,
  `name_` varchar(255) DEFAULT NULL,
  `password_` varchar(255) DEFAULT NULL,
  `created_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(50) DEFAULT NULL COMMENT '更新人id',
  `version_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `description_` varchar(255) DEFAULT NULL COMMENT '描述',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_t
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_t`;
CREATE TABLE `sys_user_t` (
  `id_` varchar(32) NOT NULL,
  `name_` varchar(255) DEFAULT NULL,
  `password_` varchar(255) DEFAULT NULL,
  `created_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(50) DEFAULT NULL COMMENT '更新人id',
  `version_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `description_` varchar(255) DEFAULT NULL COMMENT '描述',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
