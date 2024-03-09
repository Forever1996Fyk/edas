-- MySQL dump 10.13  Distrib 8.0.30, for macos12.5 (arm64)
--
-- Host: 127.0.0.1    Database: edas-gateway
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gateway_api`
--

DROP TABLE IF EXISTS `gateway_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_api` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                               `api_code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接口编码',
                               `api_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接口名称',
                               `api_path` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求路径',
                               `service_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务id',
                               `qualified_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '全限定名',
                               `method_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '方法名',
                               `http_method` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'http方式',
                               `api_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '接口类型',
                               `read_or_write` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '读写类型',
                               `api_desc` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接口描述',
                               `dev` tinyint NOT NULL DEFAULT '0' COMMENT '开发环境',
                               `test` tinyint NOT NULL DEFAULT '0' COMMENT '联调环境',
                               `sit` tinyint NOT NULL DEFAULT '0' COMMENT '测试环境',
                               `pre` tinyint NOT NULL DEFAULT '0' COMMENT '预发环境',
                               `prod` tinyint NOT NULL DEFAULT '0' COMMENT '生产环境',
                               `is_auth` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否认证',
                               `changeable` tinyint(1) DEFAULT '1' COMMENT '是否可变',
                               `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                               `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                               `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                               `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                               `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                               `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                               `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                               `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `unique_api_code` (`api_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='网关接口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_api`
--

/*!40000 ALTER TABLE `gateway_api` DISABLE KEYS */;
INSERT INTO `gateway_api` VALUES (99,'gateway.api.add','添加api接口','/ahcloud-gateway-api/api/add','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','addApi','POST',1,2,'添加api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:16',0,'',0);
INSERT INTO `gateway_api` VALUES (100,'gateway.api.update','更新api接口','/ahcloud-gateway-api/api/update','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','updateApi','POST',1,2,'更新api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:17',0,'',0);
INSERT INTO `gateway_api` VALUES (101,'gateway.api.deleteById','删除api接口','/ahcloud-gateway-api/api/deleteById/**','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','deleteApiById','POST',1,2,'删除api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:17',0,'',0);
INSERT INTO `gateway_api` VALUES (102,'gateway.api.findById','根据id获取api详情','/ahcloud-gateway-api/api/findById/**','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','findApiById','GET',1,1,'根据id获取api详情',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:17',0,'',0);
INSERT INTO `gateway_api` VALUES (103,'gateway.api.page','分页查询api列表','/ahcloud-gateway-api/api/page','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','pageApiList','GET',1,1,'分页查询api列表',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:17',0,'',0);
INSERT INTO `gateway_api` VALUES (104,'gateway.api.offline','下线api接口','/ahcloud-gateway-api/api/offline/**','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','offlineApiById','POST',1,2,'下线api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:15',0,'',0);
INSERT INTO `gateway_api` VALUES (105,'gateway.api.online','上线api接口','/ahcloud-gateway-api/api/online/**','ahcloud-gateway-api','com.ahcloud.gateway.server.api.controller.ApiController','onlineApiById','POST',1,2,'上线api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-02-21 09:36:21','2023-06-13 19:10:17',0,'',0);
INSERT INTO `gateway_api` VALUES (110,'admin.uaa.usernamePasswordLogin','admin用户登录','/ahcloud-admin-api/uaa/usernamePasswordLogin','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','usernamePasswordLogin','POST',1,1,'admin用户登录',1,1,1,1,1,0,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',13,'',0);
INSERT INTO `gateway_api` VALUES (111,'admin.uaa.refreshToken','刷新token','/ahcloud-admin-api/uaa/refreshToken','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','refreshToken','POST',1,1,'刷新token',1,1,1,1,1,0,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (112,'admin.uaa.validateCodeLogin','admin验证码登录','/ahcloud-admin-api/uaa/validateCodeLogin','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','validateCodeLogin','POST',1,1,'admin验证码登录',1,1,1,1,1,0,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (113,'admin.sysUser.add','添加新用户','/ahcloud-admin-api/sysUser/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','add','POST',1,2,'添加新用户',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (114,'admin.sysUser.update','更新用户','/ahcloud-admin-api/sysUser/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','update','POST',1,2,'更新用户',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (115,'admin.sysUser.deleteById','根据id删除用户','/ahcloud-admin-api/sysUser/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','根据id删除用户','POST',1,2,'admin用户登录',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (116,'admin.sysUser.findById','根据id查询用户信息','/ahcloud-admin-api/sysUser/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','findById','GET',1,1,'根据id查询用户信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (117,'admin.sysUser.page','分页查询用户列表','/ahcloud-admin-api/sysUser/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','page','GET',1,1,'分页查询用户列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (118,'admin.sysUser.setSysUserRole','设置用户角色','/ahcloud-admin-api/sysUser/setSysUserRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysUserRole','POST',1,2,'设置用户角色',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (119,'admin.sysUser.setSysMenuForUser','设置菜单','/ahcloud-admin-api/sysUser/setSysMenuForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysMenuForUser','POST',1,2,'设置菜单',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (120,'admin.sysUser.setSysApiForUser','设置接口权限','/ahcloud-admin-api/sysUser/setSysApiForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysApiForUser','POST',1,2,'设置接口权限',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (121,'admin.sysUser.cancelSysApiForUser','取消用户接口权限','/ahcloud-admin-api/sysUser/cancelSysApiForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','cancelSysApiForUser','POST',1,2,'取消用户接口权限',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (122,'admin.sysUser.listSelectUserRole','根据用户id获取用户的角色信息','/ahcloud-admin-api/sysUser/listSelectUserRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','listSelectUserRole','',1,1,'根据用户id获取用户的角色信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (123,'admin.sysUser.pageSelectUserApi','根据用户id分页获取用户的角色信息','/ahcloud-admin-api/sysUser/pageSelectUserApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','pageSelectUserApi','',1,1,'根据用户id分页获取用户的角色信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (124,'admin.sysUser.listSelectSysUserVo','获取系统用户选择列表','/ahcloud-admin-api/sysUser/listSelectSysUserVo','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','listSelectSysUserVo','',1,1,'获取系统用户选择列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (125,'admin.sysRole.add','添加权限角色','/ahcloud-admin-api/sysRole/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','add','POST',1,2,'添加权限角色',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (126,'admin.sysRole.update','编辑权限角色','/ahcloud-admin-api/sysRole/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','update','POST',1,2,'编辑权限角色',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (127,'admin.sysRole.deleteById','根据id删除角色','/ahcloud-admin-api/sysRole/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','deleteById','POST',1,2,'根据id删除角色',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (128,'admin.sysRole.findById','根据id查询权限角色信息','/ahcloud-admin-api/sysRole/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','findById','GET',1,1,'根据id查询权限角色信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (129,'admin.sysRole.page','分页查询角色列表','/ahcloud-admin-api/sysRole/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','page','GET',1,1,'分页查询角色列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (130,'admin.sysRole.setSysMenuForRole','设置角色菜单','/ahcloud-admin-api/sysRole/setSysMenuForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','setSysMenuForRole','POST',1,2,'设置角色菜单',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (131,'admin.sysRole.setSysApiForRole','设置角色api','/ahcloud-admin-api/sysRole/setSysApiForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','setSysApiForRole','POST',1,2,'设置角色api',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (132,'admin.sysRole.cancelSysApiForRole','取消角色接口权限','/ahcloud-admin-api/sysRole/cancelSysApiForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','cancelSysApiForRole','POST',1,2,'取消角色接口权限',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (133,'admin.sysRole.pageSelectRoleApi','根据角色编码分页获取角色的api信息','/ahcloud-admin-api/sysRole/pageSelectRoleApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','pageSelectRoleApi','GET',1,1,'根据角色编码分页获取角色的api信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (134,'admin.sysMenu.add','添加权限菜单','/ahcloud-admin-api/sysMenu/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','add','POST',1,2,'添加权限菜单',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (135,'admin.sysMenu.update','更新权限菜单','/ahcloud-admin-api/sysMenu/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','update','POST',1,2,'更新权限菜单',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (136,'admin.sysMenu.deleteById','根据id删除权限菜单','/ahcloud-admin-api/sysMenu/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','deleteById','POST',1,2,'根据id删除权限菜单',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (137,'admin.sysMenu.findById','根据id查询权限菜单信息','/ahcloud-admin-api/sysMenu/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','findById','GET',1,1,'根据id查询权限菜单信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (138,'admin.sysMenu.listSysMenuTree','获取菜单树列表','/ahcloud-admin-api/sysMenu/listSysMenuTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listSysMenuTree','GET',1,1,'获取菜单树列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (139,'admin.sysMenu.listMenuSelectTree','获取菜单树形选择结构列表','/ahcloud-admin-api/sysMenu/listMenuSelectTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listMenuSelectTree','GET',1,1,'获取菜单树形选择结构列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (140,'admin.sysMenu.setSysApiForMenu','设置菜单api','/ahcloud-admin-api/sysMenu/setSysApiForMenu','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','setSysApiForMenu','POST',1,2,'设置菜单api',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (141,'admin.sysMenu.cancelSysApiForMenu','取消菜单接口权限','/ahcloud-admin-api/sysMenu/cancelSysApiForMenu','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','cancelSysApiForMenu','POST',1,2,'取消菜单接口权限',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (142,'admin.sysMenu.pageSelectMenuApi','根据菜单编码分页获取菜单的api信息','/ahcloud-admin-api/sysMenu/pageSelectMenuApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','pageSelectMenuApi','GET',1,1,'根据菜单编码分页获取菜单的api信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (143,'admin.sysMenu.listSysMenuButtonPermissionVo','查询按钮权限集合','/ahcloud-admin-api/sysMenu/listSysMenuButtonPermissionVo','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listSysMenuButtonPermissionVo','GET',1,1,'查询按钮权限集合',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (144,'admin.sysDict.add','添加字典','/ahcloud-admin-api/sysDict/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','add','POST',1,2,'添加字典',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (145,'admin.sysDict.update','更新字典','/ahcloud-admin-api/sysDict/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','update','POST',1,2,'更新字典',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (146,'admin.sysDict.deleteById','删除字典','/ahcloud-admin-api/sysDict/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','deleteById','POST',1,2,'删除字典',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (147,'admin.sysDict.findById','根据id获取字典','/ahcloud-admin-api/sysDict/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','findById','GET',1,1,'根据id获取字典',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (148,'admin.sysDict.page','分页查询字典','/ahcloud-admin-api/sysDict/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','page','GET',1,1,'分页查询字典',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (149,'admin.sysDictDetail.add','添加字典详情','/ahcloud-admin-api/sysDictDetail/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','add','POST',1,2,'添加字典详情',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (150,'admin.sysDictDetail.update','更新字典详情','/ahcloud-admin-api/sysDictDetail/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','update','POST',1,2,'更新字典详情',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:48',0,'',0);
INSERT INTO `gateway_api` VALUES (151,'admin.sysDictDetail.deleteById','删除字典详情','/ahcloud-admin-api/sysDictDetail/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','deleteById','POST',1,2,'删除字典详情',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (152,'admin.sysDictDetail.findById','根据id获取字典详情','/ahcloud-admin-api/sysDictDetail/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','findById','GET',1,1,'根据id获取字典详情',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (153,'admin.sysDictDetail.page','分页查询字典详情','/ahcloud-admin-api/sysDictDetail/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','page','GET',1,1,'分页查询字典详情',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (154,'admin.sysDictDetail.selectDictLabelList','根据字典编码选择标签列表','/ahcloud-admin-api/sysDictDetail/selectDictLabelList/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','selectDictLabelList','GET',1,1,'根据字典编码选择标签列表',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:47',0,'',0);
INSERT INTO `gateway_api` VALUES (155,'admin.router.listRouterVoByUserId','根据当前登录人id获取菜单路由信息','/ahcloud-admin-api/router/listRouterVoByUserId','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRouterController','listRouterVoByUserId','GET',1,1,'根据当前登录人id获取菜单路由信息',1,1,1,1,1,1,0,'','system','system','2023-02-21 09:36:21','2023-06-13 19:10:49',0,'',0);
INSERT INTO `gateway_api` VALUES (163,'gateway.metadata.selectServiceIdList','获取服务id选择列表','/ahcloud-gateway-api/metadata/selectServiceIdList','ahcloud-gateway-api','com.ahcloud.gateway.api.controller.GatewayApiMetaDataController','selectServiceIdList','GET',1,1,'获取服务id选择列表',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-06-13 19:07:21','2023-06-13 19:07:21',0,'',0);
INSERT INTO `gateway_api` VALUES (164,'gateway.metadata.selectApiMetadataList','根据服务id获取api元数据选择列表','/ahcloud-gateway-api/metadata/selectApiMetadataList','ahcloud-gateway-api','com.ahcloud.gateway.api.controller.GatewayApiMetaDataController','selectApiMetadataList','GET',1,1,'根据服务id获取api元数据选择列表',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-06-13 19:07:21','2023-06-13 19:07:21',0,'',0);
INSERT INTO `gateway_api` VALUES (165,'gateway.refresh.refreshApi','刷新api接口','/ahcloud-gateway-api/refresh/refreshApi','ahcloud-gateway-api','com.ahcloud.gateway.api.controller.RefreshController','refreshApi','POST',1,2,'刷新api接口',1,1,1,1,1,1,0,'','SYSTEM','SYSTEM','2023-06-13 19:07:21','2023-06-13 19:07:21',0,'',0);
INSERT INTO `gateway_api` VALUES (166,'uaa.uaa.usernamePasswordLogin','app用户密码登录','/ahcloud-uaa-api/uaa/usernamePasswordLogin','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.UaaLoginController','usernamePasswordLogin','POST',1,1,'app用户密码登录',1,0,0,0,0,0,1,'','system','system','2023-06-15 23:57:01','2023-06-15 23:58:51',1,'',0);
/*!40000 ALTER TABLE `gateway_api` ENABLE KEYS */;

--
-- Table structure for table `gateway_api_meta_data`
--

DROP TABLE IF EXISTS `gateway_api_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_api_meta_data` (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                         `app_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'app名称',
                                         `api_path` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求路径',
                                         `service_id` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务id',
                                         `qualified_name` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '全限定名',
                                         `method_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '方法名',
                                         `http_method` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'method类型',
                                         `consume` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '提交内容类型（Content-Type）',
                                         `produce` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '返回的内容类型',
                                         `rpc_type` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'rpc类型',
                                         `env` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '环境变量',
                                         `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                         `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                                         `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                                         `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                         `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                         `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                         `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                                         `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `unique_qualified_method` (`env`,`app_name`,`qualified_name`,`method_name`,`deleted`),
                                         UNIQUE KEY `unique_service_api` (`env`,`app_name`,`api_path`,`service_id`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='网关接口元数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_api_meta_data`
--

/*!40000 ALTER TABLE `gateway_api_meta_data` DISABLE KEYS */;
INSERT INTO `gateway_api_meta_data` VALUES (1,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:37','2023-05-30 19:20:37',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (2,'ahcloud-admin-api','/ahcloud-admin-api/dept/listSysDeptTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','listSysDeptTree','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:41','2023-05-30 19:20:41',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (3,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/setSysApiForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','setSysApiForRole','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:41','2023-05-30 19:20:41',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (4,'ahcloud-admin-api','/ahcloud-admin-api/dept/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:41','2023-05-30 19:20:41',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (5,'ahcloud-admin-api','/ahcloud-admin-api/dept/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:41','2023-05-30 19:20:41',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (6,'ahcloud-admin-api','/ahcloud-admin-api/dept/listDeptSelectTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','listDeptSelectTree','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (7,'ahcloud-admin-api','/ahcloud-admin-api/sysDict/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (8,'ahcloud-admin-api','/ahcloud-admin-api/sysDict/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (9,'ahcloud-admin-api','/ahcloud-admin-api/sysDict/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (10,'ahcloud-admin-api','/ahcloud-admin-api/sysDict/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (11,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (12,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/cancelSysApiForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','cancelSysApiForRole','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (13,'ahcloud-admin-api','/ahcloud-admin-api/sysDict/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (14,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (15,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantUser/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantUserController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (16,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (17,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (18,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/selectDictLabelList/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','selectDictLabelList','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:42','2023-05-30 19:20:42',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (19,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (20,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','delete','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (21,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (22,'ahcloud-admin-api','/ahcloud-admin-api/sysDictDetail/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDictDetailController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (23,'ahcloud-admin-api','/ahcloud-admin-api/captcha/genLoginCaptcha','ahcloud-admin-api','com.ahcloud.admin.api.controller.CaptchaController','genLoginCaptcha','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (24,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','delete','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (25,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/setSysApiForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysApiForUser','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (26,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/setSysMenuForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysMenuForUser','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (27,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/pageSelectUserApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','pageSelectUserApi','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (28,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/setSysUserRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','setSysUserRole','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (29,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (30,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (31,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/pageSelectMenuApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','pageSelectRoleApi','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (32,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (33,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantUser/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantUserController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (34,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/listSysMenuTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listSysMenuTree','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (35,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/pageSelectRoleApi','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','pageSelectRoleApi','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (36,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/setSysMenuForRole','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','setSysMenuForRole','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:43','2023-05-30 19:20:43',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (37,'ahcloud-admin-api','/ahcloud-admin-api/router/listRouterVoByUserId','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRouterController','listRouterVoByUserId','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (38,'ahcloud-admin-api','/ahcloud-admin-api/sysTenant/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (39,'ahcloud-admin-api','/ahcloud-admin-api/sysTenant/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (40,'ahcloud-admin-api','/ahcloud-admin-api/sysTenant/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (41,'ahcloud-admin-api','/ahcloud-admin-api/sysTenant/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (42,'ahcloud-admin-api','/ahcloud-admin-api/captcha/genSmsCaptcha','ahcloud-admin-api','com.ahcloud.admin.api.controller.CaptchaController','genSmsCaptcha','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (43,'ahcloud-admin-api','/ahcloud-admin-api/dept/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (44,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantUser/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantUserController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (45,'ahcloud-admin-api','/ahcloud-admin-api/sysTenant/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (46,'ahcloud-admin-api','/ahcloud-admin-api/dept/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysDeptController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (47,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantUser/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantUserController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (48,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantUser/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantUserController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (49,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (50,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (51,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','delete','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (52,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (53,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/listSelectSysUserVo','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','listSelectSysUserVo','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (54,'ahcloud-admin-api','/ahcloud-admin-api/uaa/refreshToken','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','refreshToken','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (55,'ahcloud-admin-api','/ahcloud-admin-api/uaa/checkCaptchaOpen','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','checkCaptchaOpen','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:44','2023-05-30 19:20:44',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (56,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (57,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/listMenuSelectTree','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listMenuSelectTree','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (58,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/listSysMenuButtonPermissionVo','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','listSysMenuButtonPermissionVo','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (59,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/setSysApiForMenu','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','setSysApiForMenu','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (60,'ahcloud-admin-api','/ahcloud-admin-api/uaa/validateCodeLogin','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','validateCodeLogin','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (61,'ahcloud-admin-api','/ahcloud-admin-api/uaa/usernamePasswordLogin','ahcloud-admin-api','com.ahcloud.admin.api.controller.UaaLoginController','usernamePasswordLogin','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (62,'ahcloud-admin-api','/ahcloud-admin-api/sysMenu/cancelSysApiForMenu','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysMenuController','cancelSysApiForMenu','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (63,'ahcloud-admin-api','/ahcloud-admin-api/sysRole/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysRoleController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (64,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/listSelectUserRole/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','listSelectUserRoleByUserId','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (65,'ahcloud-admin-api','/ahcloud-admin-api/sysUser/cancelSysApiForUser','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysUserController','cancelSysApiForUser','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (66,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantPackage/update','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantPackageController','update','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (67,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantPackage/add','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantPackageController','add','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (68,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantPackage/deleteById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantPackageController','deleteById','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (69,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantPackage/findById/**','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantPackageController','findById','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (70,'ahcloud-admin-api','/ahcloud-admin-api/sysTenantPackage/page','ahcloud-admin-api','com.ahcloud.admin.api.controller.SysTenantPackageController','page','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:20:45','2023-05-30 19:20:45',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (71,'ahcloud-uaa-api','/ahcloud-uaa-api/uaa/checkCaptchaOpen','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.UaaLoginController','checkCaptchaOpen','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (72,'ahcloud-uaa-api','/ahcloud-uaa-api/captcha/genSmsCaptcha','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.CaptchaController','genSmsCaptcha','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (73,'ahcloud-uaa-api','/ahcloud-uaa-api/uaa/usernamePasswordLogin','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.UaaLoginController','usernamePasswordLogin','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (74,'ahcloud-uaa-api','/ahcloud-uaa-api/uaa/validateCodeLogin','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.UaaLoginController','validateCodeLogin','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (75,'ahcloud-uaa-api','/ahcloud-uaa-api/uaa/refreshToken','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.UaaLoginController','refreshToken','POST','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
INSERT INTO `gateway_api_meta_data` VALUES (76,'ahcloud-uaa-api','/ahcloud-uaa-api/captcha/genLoginCaptcha','ahcloud-uaa-api','com.ahcloud.uaa.api.controller.CaptchaController','genLoginCaptcha','GET','*/*','*/*','springCloud','local','','SYSTEM','SYSTEM','2023-05-30 19:33:38','2023-05-30 19:33:38',0,'',0);
/*!40000 ALTER TABLE `gateway_api_meta_data` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-04 10:39:34

-- auto-generated definition
create table gateway_route_definition
(
    id                          bigint auto_increment comment '主键id'
        primary key,
    app_id                      bigint           default 0                 not null comment 'appId',
    app_name                    varchar(128)     default ''                not null comment 'app名称',
    route_id                    varchar(64)      default ''                not null comment '路由id',
    service_id                  varchar(128)     default ''                not null comment '服务id',
    uri                         varchar(128)     default ''                not null comment '路由uri',
    rpc_type                    varchar(16)      default ''                not null comment 'rpc类型',
    context_path                varchar(256)     default ''                not null comment '上下文路径',
    predicate_definition_config varchar(2048)    default ''                not null comment '断言定义配置',
    filter_definition_config    varchar(2048)    default ''                not null comment '过滤器定义配置',
    route_type                  tinyint          default 1                 not null comment '路由类型',
    env                         varchar(8)       default ''                not null comment '环境变量',
    remark                      varchar(256)     default ''                not null comment '备注',
    creator                     varchar(64)                                not null comment '行记录创建者',
    modifier                    varchar(64)                                not null comment '行记录最近更新人',
    created_time                timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time               timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                     tinyint unsigned default '0'               not null comment '行版本号',
    extension                   varchar(2048)    default ''                not null comment '拓展字段',
    deleted                     bigint           default 0                 not null comment '是否删除',
    constraint uniq_route
        unique (env, app_id, route_id, service_id, deleted)
)
    comment '网关路由定义表';