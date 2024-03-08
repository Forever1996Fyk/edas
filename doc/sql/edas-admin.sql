-- MySQL dump 10.13  Distrib 8.0.30, for macos12.5 (arm64)
--
-- Host: 127.0.0.1    Database: edas-admin
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
-- Table structure for table `app_resource`
--

DROP TABLE IF EXISTS `app_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_resource` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `app_id` bigint NOT NULL DEFAULT '0' COMMENT 'appId',
                                `app_code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'appCode',
                                `env` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'env',
                                `type` tinyint NOT NULL DEFAULT '0' COMMENT '资源类型',
                                `name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源名称',
                                `resource_config` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源配置',
                                `url` varchar(1024) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源url',
                                `url_mask` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源url掩码',
                                `port` int NOT NULL DEFAULT '0' COMMENT '资源端口号',
                                `port_mask` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源端口号掩码',
                                `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源用户名',
                                `username_mask` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源用户名掩码',
                                `password` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源密码',
                                `password_mask` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源密码掩码',
                                `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
                                `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
                                `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行创建时间',
                                `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行修改时间',
                                `version` bigint NOT NULL DEFAULT '0' COMMENT '版本号',
                                `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '扩展字段',
                                `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_resource`
--

--
-- Table structure for table `sys_api`
--

DROP TABLE IF EXISTS `sys_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_api` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                           `api_code` varchar(32) NOT NULL DEFAULT '' COMMENT '接口编码',
                           `api_name` varchar(50) NOT NULL DEFAULT '' COMMENT '接口名称',
                           `api_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '接口类型',
                           `read_or_write` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '读写类型',
                           `api_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '接口描述',
                           `api_url` varchar(100) NOT NULL DEFAULT '' COMMENT '请求路径',
                           `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '账号状态(1:启用,2:停用)',
                           `is_auth` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否认证',
                           `is_open` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否公开',
                           `is_global` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否全局',
                           `changeable` tinyint NOT NULL DEFAULT '1' COMMENT '是否可变',
                           `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
                           `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                           `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                           `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                           `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                           `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                           `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                           `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                           PRIMARY KEY (`id`),
                           KEY `idx_api_code` (`api_code`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统接口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_api`
--

/*!40000 ALTER TABLE `sys_api` DISABLE KEYS */;
insert into sys_api (id, api_code, api_name, api_type, read_or_write, api_desc, api_url, status, is_auth, is_open, is_global, changeable, remark, creator, modifier, created_time, modified_time, version, extension, deleted)
values  (1, 'userAdd', '新增用户', 1, 2, '新增用户', '/user/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-07-29 00:14:33', '2022-08-08 18:14:08', 0, '', 0),
    (2, 'getUserInfo', '获取当前登录人信息', 1, 1, '获取当前登录人信息', '/user/getUserInfoVo', 1, 1, 1, 1, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-01 22:55:51', '2024-01-30 11:04:53', 0, '', 0),
    (3, 'apiAdd', '新增api', 1, 2, '新增api', '/api/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-02 16:16:27', '2022-08-08 18:14:08', 0, '', 0),
    (4, 'listUserRouter', '获取用户菜单路由', 1, 1, '获取用户菜单路由', '/router/listRouterVoByUserId', 1, 1, 1, 1, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-02 16:40:38', '2024-01-30 11:18:26', 0, '', 0),
    (5, 'userUpdate', '更新用户', 1, 2, '新增用户', '/user/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-02 16:51:01', '2022-08-08 18:14:08', 0, '', 0),
    (6, 'selectDictLabelList', '选择字典标签集合', 1, 1, '选择字典标签集合', '/dictDetail/selectDictLabelList/*', 1, 1, 1, 1, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-04 14:03:20', '2024-01-30 11:28:15', 0, '', 0),
    (8, 'findUserById', '根据id查询用户信息', 1, 1, '根据id查询用户信息', '/user/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-04 17:06:41', '2022-08-08 18:14:08', 0, '', 0),
    (10, 'deleteUser', '删除用户', 1, 2, '删除用户', '/user/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-06 11:40:17', '2022-08-08 18:14:08', 0, '', 0),
    (11, 'pageApiList', '分页查询接口列表', 1, 1, '分页查询接口列表', '/api/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:42', '2022-08-08 18:14:08', 0, '', 0),
    (12, 'apiUpdate', '更新接口', 1, 2, '更新接口', '/api/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:42', '2022-08-08 18:14:08', 0, '', 0),
    (13, 'apiDelete', '删除接口', 1, 2, '删除接口', '/api/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:42', '2022-08-08 18:14:08', 0, '', 0),
    (14, 'findApiById', '根据id查询接口', 1, 1, '根据id查询接口', '/api/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:42', '2022-08-08 18:14:08', 0, '', 0),
    (15, 'pageUserList', '分页查询用户列表', 1, 1, '分页查询用户列表', '/user/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:06:45', '2022-08-09 09:41:18', 8, '', 0),
    (16, 'testApi', '测试接口', 1, 1, '测试接口', '/test/api', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-08 14:06:52', '2022-08-08 18:14:08', 6, '', 16),
    (17, 'changeApiStatus', '变更api状态', 1, 2, '变更api状态', '/api/changeApiStatus/**', 1, 1, 1, 0, 0, '', 'SYSTEM', 'SYSTEM', '2022-08-08 16:12:30', '2022-08-08 18:21:20', 4, '', 0),
    (18, 'changeApiAuth', '变更api权限', 1, 2, '变更api状态', '/api/changeApiAuth/**', 1, 1, 1, 0, 0, '', 'SYSTEM', 'SYSTEM', '2022-08-08 16:12:30', '2022-08-09 09:37:14', 3, '', 0),
    (19, 'changeApiOpen', '变更api开放类型', 1, 2, '变更api开放类型', '/api/changeApiOpen/**', 1, 1, 1, 0, 0, '', 'SYSTEM', 'SYSTEM', '2022-08-08 16:12:30', '2022-08-09 09:37:20', 1, '', 0),
    (20, 'menuAdd', '新增菜单', 1, 2, '新增菜单', '/menu/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:19', '2022-08-09 14:04:19', 0, '', 0),
    (21, 'menuUpdate', '更新菜单', 1, 2, '更新菜单', '/menu/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:19', '2022-08-09 14:04:19', 0, '', 0),
    (22, 'menuDelete', '删除菜单', 1, 2, '删除菜单', '/menu/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:19', '2022-08-09 14:04:19', 0, '', 0),
    (23, 'findMenuById', '根据id查询菜单信息', 1, 1, '根据id查询菜单信息', '/menu/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:19', '2022-08-09 14:04:19', 0, '', 0),
    (24, 'listSysMenuTree', '获取菜单树集合', 1, 1, '分页查询菜单', '/menu/listSysMenuTree', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:19', '2022-08-09 14:04:19', 0, '', 0),
    (25, 'listMenuSelectTree', '获取菜单选择树集合', 1, 1, '获取菜单选择树集合', '/menu/listMenuSelectTree', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:04:20', '2022-08-23 16:46:43', 0, '', 0),
    (26, 'roleAdd', '新增角色', 1, 2, '新增角色', '/role/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 17:08:37', '2022-08-23 16:45:40', 0, '', 0),
    (27, 'roleUpdate', '更新角色', 1, 2, '更新角色', '/role/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 17:08:37', '2022-08-23 16:45:40', 0, '', 0),
    (28, 'roleDelete', '删除角色', 1, 2, '删除角色', '/role/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 17:08:37', '2022-08-23 16:45:40', 0, '', 0),
    (29, 'findRoleById', '根据id查询角色信息', 1, 1, '根据id查询角色信息', '/role/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 17:08:37', '2022-08-23 16:45:40', 0, '', 0),
    (30, 'pageRoleList', '分页查询角色列表', 1, 1, '分页查询角色列表', '/role/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-09 17:08:37', '2022-08-23 16:35:47', 0, '', 0),
    (31, 'setUserMenu', '设置用户菜单', 1, 2, '设置用户菜单', '/user/setSysMenuForUser', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-11 16:40:12', '2022-08-23 16:45:40', 0, '', 0),
    (32, 'setUserRole', '设置用户角色', 1, 2, '设置用户角色', '/user/setSysUserRole', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-12 15:14:22', '2022-08-23 16:50:55', 0, '', 0),
    (33, 'listSelectUserRole', '查询选择用户已有角色', 1, 1, '查询选择用户已有角色', '/user/listSelectUserRole/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-12 15:14:22', '2022-08-23 16:45:40', 0, '', 0),
    (34, 'pageSelectUserRole', '根据用户id分页获取用户的角色信息', 1, 1, '根据用户id分页获取用户的角色信息', '/user/pageSelectUserRole', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 12:05:39', '2022-08-23 16:45:40', 0, '', 0),
    (35, 'setSysApiForUser', '设置用户接口', 1, 2, '设置用户接口', '/user/setSysApiForUser', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 12:08:23', '2022-08-23 16:48:20', 0, '', 0),
    (36, 'pageSelectUserApi', '根据用户id分页获取用户的接口信息', 1, 1, '根据用户id分页获取用户的接口信息', '/user/pageSelectUserApi', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 12:15:49', '2022-08-23 16:45:40', 0, '', 0),
    (37, 'cancelSysApiForUser', '取消用户接口权限', 1, 2, '取消用户接口权限', '/user/cancelSysApiForUser', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 14:08:01', '2022-08-23 16:45:40', 0, '', 0),
    (38, 'setSysMenuForRole', '设置角色菜单', 1, 2, '设置角色菜单', '/role/setSysMenuForRole', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 15:38:32', '2022-08-23 16:45:40', 0, '', 0),
    (39, 'setSysApiForRole', '设置角色api', 1, 2, '设置角色api', '/role/setSysApiForRole', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 15:38:32', '2022-08-23 16:45:40', 0, '', 0),
    (40, 'cancelSysApiForRole', '取消角色api', 1, 2, '取消角色api', '/role/cancelSysApiForRole', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 15:38:32', '2022-08-23 16:45:40', 0, '', 0),
    (41, 'pageSelectRoleApi', '根据角色编码分页获取角色的api信息', 1, 1, '根据角色编码分页获取角色的api信息', '/role/pageSelectRoleApi', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 16:06:05', '2022-08-23 16:45:40', 0, '', 0),
    (42, 'setSysApiForMenu', '设置菜单api', 1, 2, '设置菜单api', '/menu/setSysApiForMenu', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 18:06:52', '2022-08-23 16:45:40', 0, '', 0),
    (43, 'cancelSysApiForMenu', '取消菜单api', 1, 2, '取消菜单api', '/menu/cancelSysApiForMenu', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 18:06:52', '2022-08-23 16:45:40', 0, '', 0),
    (44, 'pageSelectMenuApi', '根据菜单编码分页获取菜单的api信息', 1, 1, '根据菜单编码分页获取菜单的api信息', '/menu/pageSelectMenuApi', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-15 18:06:52', '2022-08-23 16:45:40', 0, '', 0),
    (45, 'pageSysApiList', '分页查询系统api集合', 1, 1, '分页查询系统api集合', '/api/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:44:26', '2022-08-16 11:44:33', 0, '', 0),
    (46, 'listSysMenuButtonPermissionVo', '查询按钮权限集合', 1, 1, '查询按钮权限集合', '/menu/listSysMenuButtonPermissionVo', 1, 1, 1, 1, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-16 16:09:27', '2024-01-30 11:29:06', 0, '', 0),
    (47, 'cs', '测试', 1, 1, '测试', 'cs', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-16 17:40:30', '2022-08-17 15:18:02', 1, '', 47),
    (48, 'listSysDeptTree', '查询部门树列表', 1, 1, '查询菜单树列表', '/dept/listSysDeptTree', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:24:52', '2022-08-18 15:25:28', 1, '', 0),
    (49, 'deptAdd', '添加部门', 1, 2, '添加部门', '/dept/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:25:18', '2022-08-18 15:25:18', 0, '', 0),
    (50, 'deptUpdate', '更新部门', 1, 2, '更新部门', '/dept/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:25:53', '2022-08-18 15:25:53', 0, '', 0),
    (51, 'deptDelete', '删除部门', 1, 2, '删除部门', '/dept/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:26:18', '2022-08-18 15:26:18', 0, '', 0),
    (52, 'findDeptById', '根据id查询部门', 1, 1, '根据id查询部门', '/dept/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:26:54', '2022-08-18 15:26:54', 0, '', 0),
    (53, 'listDeptSelectTree', '获取部门树形选择结构列表', 1, 1, '获取部门树形选择结构列表', '/dept/listDeptSelectTree', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:31:19', '2022-08-18 15:31:19', 0, '', 0),
    (54, 'dictAdd', '添加字典', 1, 2, '添加字典', '/dict/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:34:24', '2022-08-23 16:45:40', 1, '', 0),
    (55, 'dictUpdate', '更新字典', 1, 2, '更新字典', '/dict/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:34:48', '2022-08-18 18:34:48', 0, '', 0),
    (56, 'dictDelete', '删除字典', 1, 2, '删除字典', '/dict/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:35:30', '2022-08-18 18:35:30', 0, '', 0),
    (57, 'findDictById', '根据id获取字典', 1, 1, '根据id获取字典', '/dict/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:35:56', '2022-08-18 18:35:56', 0, '', 0),
    (58, 'pageSysDictList', '分页查询字典', 1, 1, '分页查询字典', '/dict/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:36:20', '2022-08-18 18:36:20', 0, '', 0),
    (59, 'pageDictDetailList', '分页查询字典详情', 1, 1, '分页查询字典详情', '/dictDetail/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:18:51', '2022-08-19 18:18:51', 0, '', 0),
    (60, 'dictDetailAdd', '添加字典详情', 1, 2, '添加字典详情', '/dictDetail/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:19:27', '2022-08-19 18:19:27', 0, '', 0),
    (61, 'dictDetailUpdate', '更新字典详情', 1, 2, '更新字典详情', '/dictDetail/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:20:36', '2022-08-19 18:20:36', 0, '', 0),
    (62, 'findDictDetailById', '根据id查询字典详情', 1, 1, '根据id查询字典详情', '/dictDetail/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:22:14', '2022-08-19 18:22:14', 0, '', 0),
    (63, 'dictDetailDelete', '删除字典详情', 1, 2, '删除字典详情', '/dictDetail/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:22:43', '2022-08-19 18:22:43', 0, '', 0),
    (64, 'findUserPersonalInfo', '获取用户个人信息', 1, 1, '获取用户个人信息', '/user/findUserPersonalInfo', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-21 16:53:57', '2024-02-02 11:11:22', 0, '', 0),
    (65, 'updateSysUserIntro', '更新用户个人简介', 1, 2, '更新用户个人简介', '/intro/updateSysUserIntro', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-22 11:11:54', '2022-08-22 11:11:54', 0, '', 0),
    (66, 'updatePersonalBaseUserInfo', '更新个人中心基本信息', 1, 2, '更新个人中心基本信息', '/user/updatePersonalBaseUserInfo', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-22 11:12:25', '2022-08-22 11:12:25', 0, '', 0),
    (67, 'uploadUserAvatar', '上传用户头像', 1, 2, '上传用户头像', '/user/uploadUserAvatar', 1, 0, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-08-22 15:17:22', '2022-08-22 17:39:38', 1, '', 0),
    (68, 'addCategory', '添加类目', 2, 2, '添加类目', '/zky/category/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:36:35', '2022-09-20 16:36:35', 0, '', 0),
    (69, 'updateCategory', '编辑类目', 2, 2, '编辑类目', '/zky/category/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:37:08', '2022-09-20 16:38:01', 0, '', 0),
    (70, 'findCategoryById', '根据id查询类目', 2, 1, '根据id查询类目', '/zky/category/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:37:37', '2022-09-20 16:37:37', 0, '', 0),
    (71, 'deleteCategoryById', '删除类目', 2, 2, '删除类目', '/zky/category/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:38:33', '2022-09-20 16:38:33', 0, '', 0),
    (72, 'listCategoryTree', '查询类目列表树', 2, 1, '查询类目列表树', '/zky/category/listCategoryTree', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:38:58', '2022-09-20 16:38:58', 0, '', 0),
    (73, 'listCategorySelectTree', '查询类目选择树', 2, 1, '查询类目选择树', '/zky/category/listCategorySelectTree/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-20 17:27:06', '2022-09-20 17:27:06', 0, '', 0),
    (74, 'addCourse', '添加课程', 2, 2, '添加课程', '/zky/course/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:28:18', '2022-09-21 20:28:18', 0, '', 0),
    (75, 'updateCourse', '更新课程', 2, 2, '更新课程', '/zky/course/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:28:43', '2022-09-21 20:28:43', 0, '', 0),
    (76, 'findCourseById', '根据id查询课程', 2, 1, '根据id查询课程', '/zky/course/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:29:09', '2022-09-21 20:29:09', 0, '', 0),
    (77, 'deleteCourseById', '删除课程', 2, 2, '删除课程', '/zky/course/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:29:41', '2022-09-21 20:29:41', 0, '', 0),
    (78, 'pageCourseList', '分页查询课程列表', 2, 1, 'v', '/zky/course/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:30:13', '2022-09-21 20:30:13', 0, '', 0),
    (79, 'addChapter', '添加章节', 2, 2, '添加章节', '/zky/chapter/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 20:58:31', '2022-09-21 20:58:31', 0, '', 0),
    (80, 'updateChapter', '更新章节', 2, 2, '更新章节', '/zky/chapter/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 21:26:35', '2022-09-21 21:26:35', 0, '', 0),
    (81, 'findChapterById', '根据id查询章节', 2, 1, '根据id查询章节', '/zky/chapter/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 21:27:02', '2022-09-21 21:27:02', 0, '', 0),
    (82, 'deleteChapterById', '删除章节', 2, 2, '删除章节', '/zky/chapter/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 21:27:30', '2022-09-21 21:27:30', 0, '', 0),
    (83, 'pageChapterList', '分页查询章节列表', 2, 1, '分页查询章节列表', '/zky/chapter/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-21 21:27:51', '2022-09-21 21:27:51', 0, '', 0),
    (84, 'pageVideoList', '分页查询视频列表', 2, 1, '分页查询视频列表', '/zky/video/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:32:25', '2022-09-22 17:32:25', 0, '', 0),
    (85, 'importAliyunVod', '导入阿里云视频', 2, 2, '导入阿里云视频', '/zky/video/importAliyunVod', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:33:08', '2022-09-22 17:33:08', 0, '', 0),
    (86, 'updateVideoName', '更新视频标题', 2, 2, '更新视频标题', '/zky/video/updateVideoName', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:33:35', '2022-09-22 17:33:35', 0, '', 0),
    (87, 'findVideoById', '根据id查询视频', 2, 1, '根据id查询视频', '/zky/video/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:34:03', '2022-09-22 17:34:03', 0, '', 0),
    (88, 'deleteVideoById', '根据id删除视频', 2, 2, '根据id删除视频', '/zky/video/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:34:34', '2022-09-22 17:34:34', 0, '', 0),
    (89, 'pageImportAliyunVodList', '分页查询导入阿里云视频列表', 2, 1, '分页查询导入阿里云视频列表', '/aliyun/vod/pageImportAliyunVodList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 17:35:07', '2022-09-22 17:35:07', 0, '', 0),
    (90, 'bindSourceToChapter', '绑定视频到章节', 2, 2, '绑定视频到章节', '/zky/chapterSource/bindSourceToChapter', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 22:52:05', '2022-09-22 23:02:10', 1, '', 0),
    (91, 'deleteSourceFromChapter', '从章节中删除资源', 2, 2, '从章节中删除资源', '/zky/chapterSource/deleteSourceFromChapter/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 22:52:37', '2022-09-22 22:52:37', 0, '', 0),
    (92, 'batchUpdateSource', '批量更新资源', 2, 2, '批量更新资源', '/zky/chapterSource/batchUpdateSource', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 22:53:04', '2022-09-22 22:53:04', 0, '', 0),
    (93, 'pageSourceListFromChapter', '分页查询资源列表', 2, 1, '分页查询资源列表', '/zky/chapterSource/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-22 22:53:33', '2022-09-22 22:53:33', 0, '', 0),
    (94, 'addCourseCategory', '新增课程到类目', 2, 2, '新增课程到类目', '/zky/courseCategory/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:28:31', '2022-09-23 10:28:31', 0, '', 0),
    (95, 'deleteCourseFromCategoryById', '从类目中删除课程', 2, 2, '从类目中删除课程', '/zky/courseCategory/deleteCourseFromCategoryById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:29:36', '2022-09-23 14:30:56', 1, '', 0),
    (96, 'pageCourseCategoryList', '分页查询类目课程', 2, 1, '分页查询类目课程', '/zky/courseCategory/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:30:10', '2022-09-23 10:30:10', 0, '', 0),
    (97, 'batchChangeCourseCategory', '批量调整课程信息', 2, 2, '批量调整课程信息', '/zky/courseCategory/batchChangeCourseCategory', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:31:04', '2022-09-23 10:31:04', 0, '', 0),
    (98, 'changeFrontView', '变更首页是否显示', 2, 2, '变更首页是否显示', '/zky/courseCategory/changeFrontView', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:32:00', '2022-09-23 10:32:00', 0, '', 0),
    (99, 'findByCategoryId', '根据categoryId获取类目信息', 2, 1, '根据categoryId获取类目信息', '/zky/category/findByCategoryId/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-23 14:11:24', '2022-09-23 14:11:24', 0, '', 0),
    (100, 'uploadForUrl', '上传文件', 2, 2, '上传文件', '/resource/uploadForUrl', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-24 14:55:23', '2022-09-24 14:55:23', 0, '', 0),
    (101, 'uploadCourseCover', '上传更新课程封面', 2, 2, '上传更新课程封面', '/zky/course/uploadCourseCover', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-09-24 15:37:00', '2022-09-24 15:37:00', 0, '', 0),
    (102, 'addLive', '添加直播', 2, 2, '添加直播', '/zky/live/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:36:15', '2022-10-18 20:36:15', 0, '', 0),
    (103, 'updateLive', '更新直播', 2, 2, '更新直播', '/zky/live/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:36:41', '2022-10-18 20:36:41', 0, '', 0),
    (104, 'findLiveById', '根据id查询直播', 2, 1, '根据id查询直播', '/zky/live/findById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:37:08', '2022-10-18 20:37:08', 0, '', 0),
    (105, 'deleteLiveById', '删除直播', 2, 2, '删除直播', '/zky/live/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:37:36', '2022-10-18 20:37:36', 0, '', 0),
    (106, 'pageLiveList', '分页查询直播列表', 2, 1, '分页查询直播列表', '/zky/live/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:38:01', '2022-10-18 20:38:01', 0, '', 0),
    (107, 'findLiveStreamInfo', '获取直播流地址', 2, 1, '获取直播流地址', '/zky/live/findLiveStreamInfo/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:38:34', '2022-10-18 20:38:34', 0, '', 0),
    (108, 'listSelectUser', '获取用户选择列表', 2, 1, '获取用户选择列表', '/user/listSelectSysUserVo', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-21 11:09:07', '2022-10-21 11:12:04', 1, '', 0),
    (109, 'updateUserAvatar', '更新用户头像', 1, 2, '更新用户头像', '/user/updateUserAvatar', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-10-28 17:05:48', '2022-10-28 17:05:48', 0, '', 0),
    (110, 'createExportTask', '创建导出任务', 2, 2, '创建导出任务', '/importExportTask/createExportTask', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-11-02 15:16:22', '2022-11-02 15:16:22', 0, '', 0),
    (111, 'pageImportExportTaskList', '分页查询任务列表', 2, 1, '分页查询任务列表', '/importExportTask/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-11-02 22:42:39', '2022-11-02 22:42:39', 0, '', 0),
    (112, 'download', '文件下载', 2, 2, '文件下载', '/resource/download/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-11-03 13:55:30', '2022-11-03 13:55:30', 0, '', 0),
    (113, 'getDownloadUrl', '获取文件下载地址', 2, 1, '获取文件下载地址', '/resource/getDownloadUrl/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2022-11-07 17:22:03', '2022-11-07 17:22:03', 0, '', 0),
    (114, 'createImportTemplate', '创建导入模版', 1, 2, '创建导入模版', '/importTemplate/add', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:16:25', '2022-11-08 15:16:25', 0, '', 0),
    (115, 'updateImportTemplate', '更新导入模板', 1, 2, '更新导入模板', '/importTemplate/update', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:16:58', '2022-11-08 15:16:58', 0, '', 0),
    (116, 'deleteImportTemplateById', '根据id删除模板', 1, 2, '根据id删除模板', '/importTemplate/deleteById/*', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:17:35', '2022-11-08 15:17:35', 0, '', 0),
    (117, 'findImportTemplateById', '根据id获取模板信息', 1, 1, '根据id获取模板信息', '/importTemplate/findById/*', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:18:11', '2022-11-08 15:18:11', 0, '', 0),
    (118, 'pageImportTemplateList', '分页获取模板信息', 1, 1, '分页获取模板信息', '/importTemplate/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:18:39', '2022-11-08 15:18:39', 0, '', 0),
    (119, 'findImportTemplateDownInfo', '根据业务类型获取下载模板信息', 1, 1, '根据业务类型获取下载模板信息', '/importTemplate/findImportTemplateDownInfo/*', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 15:19:47', '2022-11-09 11:16:27', 2, '', 0),
    (120, 'changeImportTemplateStatus', '变更导入模版状态', 1, 2, '变更导入模版状态', '/importTemplate/changeImportTemplateStatus', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-08 18:01:23', '2022-11-08 18:01:23', 0, '', 0),
    (121, 'createImportTask', '创建导入任务', 1, 2, '创建导入任务', '/importExportTask/createImportTask', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-09 11:50:21', '2022-11-09 11:50:21', 0, '', 0),
    (122, 'changeSysUserStatus', '变更用户状态', 2, 2, '变更用户状态', '/user/changeSysUserStatus', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2022-11-09 17:50:46', '2022-11-11 11:46:11', 4, '', 0),
    (123, 'deleteAppUserById', '根据id删除app用户', 2, 2, '根据id删除app用户', '/zky/appUser/deleteAppUserById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-01-09 09:22:09', '2023-01-09 09:23:13', 1, '', 0),
    (126, 'appAdd', '新增app应用', 1, 2, '新增app应用', '/application/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:22:19', '2023-12-12 11:22:19', 0, '', 0),
    (127, 'deleteAppById', '删除app应用', 1, 2, '删除app应用', '/application/deleteById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:23:23', '2023-12-12 11:23:23', 0, '', 0),
    (128, 'pageAppList', '分页查询appList', 1, 1, '分页查询appList', '/application/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:23:52', '2023-12-12 11:23:52', 0, '', 0),
    (129, 'appAssign', '分配app权限', 1, 2, '分配app权限', '/application/assign', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:24:18', '2023-12-12 11:24:18', 0, '', 0),
    (130, 'deleteUserAppScopeById', '删除用户权限', 1, 2, '删除用户权限', '/application/deleteUserAppScopeById/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:24:56', '2023-12-12 11:24:56', 0, '', 0),
    (131, 'pageUserAppScopeList', '查询用户app权限集合', 1, 1, '查询用户app权限集合', '/application/pageUserAppScopeList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:25:18', '2023-12-12 11:25:18', 0, '', 0),
    (132, 'pageMyAppList', '查询当前用户app权限集合', 1, 1, '查询当前用户app权限集合', '/application/pageMyAppList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 11:25:40', '2023-12-12 11:25:40', 0, '', 0),
    (133, 'cancelAppAssign', '取消用户app权限', 1, 2, '取消用户app权限', '/application/cancelAssign', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 15:42:01', '2023-12-12 15:42:01', 0, '', 0),
    (134, 'pageGatewayApiList', '分页查询网关api', 1, 1, '分页查询网关api', '/gateway/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-12 17:07:08', '2023-12-12 17:07:08', 0, '', 0),
    (135, 'getMyAppSelectList', '获取我的app选择列表', 1, 1, '获取我的app选择列表', '/application/getMyAppSelectList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-13 17:14:55', '2023-12-13 17:16:25', 1, '', 0),
    (136, 'selectApiMetadataList', '根据服务id获取api元数据选择列表', 1, 1, '根据服务id获取api元数据选择列表', '/metadata/selectApiMetadataList/*', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-13 17:15:35', '2023-12-13 17:15:35', 0, '', 0),
    (137, 'addDevopsConfig', '新增devops配置', 1, 2, '新增devops配置', '/devops/config/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-15 10:23:22', '2023-12-15 10:23:22', 0, '', 0),
    (138, 'updateDevopsConfig', '更新devops配置', 1, 2, '更新devops配置', '/devops/config/updateById', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-15 10:23:54', '2023-12-15 10:23:54', 0, '', 0),
    (139, 'findDevopsConfigByAppid', '根据appId查询devops配置', 1, 1, '根据appId查询devops配置', '/devops/config/findByAppId/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-15 10:24:33', '2023-12-15 10:24:33', 0, '', 0),
    (140, 'getGitlabProjectSelectList', '查询gitlab项目选择列表', 1, 1, '查询gitlab项目选择列表', '/devops/gitlab/getProjectSelectList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-15 10:25:02', '2023-12-15 10:25:02', 0, '', 0),
    (141, 'appResourceAdd', '添加资源', 1, 2, '添加资源', '/app/resource/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-20 15:41:12', '2023-12-20 15:41:12', 0, '', 0),
    (142, 'appResourceUpdate', '编辑资源', 1, 2, '编辑资源', '/app/resource/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-20 15:41:40', '2023-12-20 15:41:40', 0, '', 0),
    (143, 'deleteAppResourceById', '根据id删除资源', 1, 2, '根据id删除资源', '/app/resource/deleteById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-20 15:42:17', '2023-12-20 15:43:43', 0, '', 0),
    (144, 'findAppResourceById', '根据id查询资源信息', 1, 1, '根据id查询资源信息', '/app/resource/findDetailById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-20 15:43:04', '2023-12-20 16:18:54', 1, '', 0),
    (145, 'appResourcePage', '分页查询资源列表', 1, 1, '分页查询资源列表', '/app/resource/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-20 15:44:25', '2023-12-20 15:44:25', 0, '', 0),
    (146, 'findAppByAppId', '根据appId获取应用详情', 1, 1, '根据appId获取应用详情', '/application/findAppByAppId/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 10:27:10', '2023-12-21 10:27:10', 0, '', 0),
    (147, 'addNacosConfig', '新增nacos配置', 1, 2, '新增nacos配置', '/nacos/config/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:07:02', '2023-12-27 17:04:06', 1, '', 0),
    (148, 'updateNacosConfig', '修改nacos配置', 1, 2, '修改nacos配置', '/nacos/config/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:07:30', '2023-12-21 15:07:30', 0, '', 0),
    (149, 'deleteNacosConfigById', '删除nacos配置', 1, 2, '删除nacos配置', '/nacos/config/deleteById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:08:04', '2023-12-21 15:08:04', 0, '', 0),
    (150, 'findNacosConfigById', '查询nacos配置详情', 1, 2, '查询nacos配置详情', '/nacos/config/findById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:08:35', '2023-12-21 15:08:35', 0, '', 0),
    (151, 'pageNacosConfigList', '分页查询nacos配置列表', 1, 1, '分页查询nacos配置列表', '/nacos/config/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:09:08', '2023-12-21 15:09:08', 0, '', 0),
    (152, 'pageNacosHistoryConfigList', '分页查询nacos配置历史记录列表', 1, 1, '分页查询nacos配置历史记录列表', '/nacos/config/pageHistoryConfigList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:09:39', '2023-12-21 15:09:39', 0, '', 0),
    (153, 'findNacosHistoryConfigById', '查询nacos历史配置详情', 1, 1, '查询nacos历史配置详情', '/nacos/config/findHistoryConfigById', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:10:09', '2023-12-21 15:10:09', 0, '', 0),
    (154, 'getNamespaceSelectList', '查询nacos命名空间选择列表', 1, 1, '查询nacos命名空间选择列表', '/nacos/config/getNamespaceSelectList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-21 15:10:35', '2023-12-21 15:10:35', 0, '', 0),
    (155, 'nacosConfigRollback', 'nacos配置版本回退', 1, 2, 'nacos配置版本回退', '/nacos/config/rollback', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-22 17:15:18', '2023-12-22 17:15:18', 0, '', 0),
    (156, 'addDevopsJob', '新增devops任务', 1, 2, '新增devops任务', '/devops/job/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2023-12-24 18:22:42', '2023-12-27 17:04:00', 1, '', 0),
    (157, 'addRmqConfig', '新增rocketmq配置', 1, 2, '新增rocketmq配置', '/rmqConfig/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-15 17:30:55', '2024-01-15 17:30:55', 0, '', 0),
    (158, 'findRmqConfigByAppId', '根据appId查询rocketmq配置', 1, 1, '根据appId查询rocketmq配置', '/rmqConfig/findConfigByAppId/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-15 17:31:39', '2024-01-15 17:31:39', 0, '', 0),
    (159, 'addTopic', '新增topic', 1, 2, '新增topic', '/topic/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:14:45', '2024-01-19 10:14:45', 0, '', 0),
    (160, 'updateTopicById', '更新topic', 1, 2, '更新topic', '/topic/updateById', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:15:13', '2024-01-19 10:15:13', 0, '', 0),
    (161, 'deleteTopicById', '删除topic', 1, 2, '删除topic', '/topic/deleteById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:15:38', '2024-01-19 10:17:47', 1, '', 0),
    (162, 'findTopicById', '查询topic详情', 1, 1, '查询topic详情', '/topic/findById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:16:09', '2024-01-19 10:16:09', 0, '', 0),
    (163, 'pageTopicList', '分页查询topic', 1, 1, '分页查询topic', '/topic/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:16:30', '2024-01-19 10:16:30', 0, '', 0),
    (164, 'addSubscribe', '新增订阅', 1, 2, '新增订阅', '/subscribe/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:17:04', '2024-01-19 10:17:04', 0, '', 0),
    (165, 'updateSubscribeById', '更新订阅', 1, 2, '更新订阅', '/subscribe/updateById', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:17:34', '2024-01-19 10:17:34', 0, '', 0),
    (166, 'findSubscribeById', '查询订阅详情', 1, 1, '查询订阅详情', '/subscribe/findSubscribeById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:18:30', '2024-01-19 10:18:30', 0, '', 0),
    (167, 'deleteSubscribeById', '删除订阅', 1, 2, '删除订阅', '/subscribe/deleteById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:19:05', '2024-01-23 09:31:46', 0, '', 0),
    (168, 'pageSubscribeList', '分页查询订阅', 1, 1, '分页查询订阅', '/subscribe/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:19:39', '2024-01-19 10:19:39', 0, '', 0),
    (169, 'resetSubscribeOffset', '重置消费位点', 1, 2, '重置消费位点', '/subscribe/resetOffset', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:20:24', '2024-01-19 10:20:24', 0, '', 0),
    (170, 'pageMessageList', '分页查询消息列表', 1, 1, '分页查询消息列表', '/message/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:20:51', '2024-01-19 10:20:51', 0, '', 0),
    (171, 'findMessageById', '根据消息id查询消息详情', 1, 1, '根据消息id查询消息详情', '/message/findById', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:21:15', '2024-01-19 10:21:15', 0, '', 0),
    (172, 'consumeMessageDirectly', '查询消费消息结果', 1, 1, '查询消费消息结果', '/message/consumeMessageDirectly', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:21:43', '2024-01-19 10:21:43', 0, '', 0),
    (173, 'findSubscribeGroupConsumeDetail', '查询订阅组消费详情', 1, 1, '查询订阅组消费详情', '/topic/findSubscribeGroupConsumeDetail', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:22:57', '2024-01-19 10:22:57', 0, '', 0),
    (174, 'getSelectSubscribeList', '查询订阅组列表', 1, 1, '查询订阅组列表', '/topic/getSelectSubscribeList/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-19 10:23:21', '2024-01-19 10:23:21', 0, '', 0),
    (175, 'getSelectBrokerNameList', '获取broker选择列表', 1, 1, '获取broker选择列表', '/topic/getSelectBrokerNameList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-20 11:40:51', '2024-01-20 11:40:51', 0, '', 0),
    (176, 'getAppSelectListByUserId', '获取用户app选择列表', 1, 1, '获取用户app选择列表', '/application/getAppSelectListByUserId/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-22 16:51:19', '2024-01-22 16:51:19', 0, '', 0),
    (177, 'addPowerJobAppInfo', '新增PowerJobAppInfo', 1, 2, '新增PowerJobAppInfo', '/powerjob/appInfo/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:31:22', '2024-01-27 19:31:22', 0, '', 0),
    (178, 'assertPowerJobAppInfo', '校验PowerJobAppInfo是否存在', 1, 1, '校验PowerJobAppInfo是否存在', '/powerjob/appInfo/assert/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:32:05', '2024-01-27 19:32:05', 0, '', 0),
    (179, 'addPowerJob', '新增PowerJob任务', 1, 2, '新增PowerJob任务', '/powerjob/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:32:42', '2024-01-27 19:32:42', 0, '', 0),
    (180, 'updatePowerJob', '更新PowerJob任务', 1, 2, '更新PowerJob任务', '/powerjob/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:33:14', '2024-01-27 19:33:14', 0, '', 0),
    (181, 'findPowerJobInfo', '查询PowerJob任务详情', 1, 1, '查询PowerJob任务详情', '/powerjob/findJobInfo', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:33:55', '2024-01-27 19:33:55', 0, '', 0),
    (182, 'pagePowerJobList', '分页查询PowerJob任务列表', 1, 1, '分页查询PowerJob任务列表', '/powerjob/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:34:27', '2024-01-27 19:34:27', 0, '', 0),
    (183, 'runPowerJob', '执行PowerJob任务', 1, 2, '执行PowerJob任务', '/powerjob/runJob', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:35:05', '2024-01-27 19:35:05', 0, '', 0),
    (184, 'disablePowerJob', '禁止PowerJob任务', 1, 2, '禁止PowerJob任务', '/powerjob/disableJob', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:35:36', '2024-01-27 19:35:36', 0, '', 0),
    (185, 'enablePowerJob', '启动PowerJob任务', 1, 2, '启动PowerJob任务', '/powerjob/enableJob', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:36:12', '2024-01-27 19:36:12', 0, '', 0),
    (186, 'deletePowerJob', '删除PowerJob任务', 1, 2, '删除PowerJob任务', '/powerjob/deleteJob', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:36:39', '2024-01-27 19:36:39', 0, '', 0),
    (187, 'pagePowerJobInstanceList', '分页查询PowerJob实例列表', 1, 1, '分页查询PowerJob实例列表', '/powerjob/instance/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:37:28', '2024-01-27 19:37:28', 0, '', 0),
    (188, 'stopPowerJobInstance', '停止PowerJob实例', 1, 2, '停止PowerJob实例', '/powerjob/instance/stop', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:38:03', '2024-01-27 19:38:03', 0, '', 0),
    (189, 'cancalPowerJobInstance', '取消PowerJob实例', 1, 2, '取消PowerJob实例', '/powerjob/instance/cancel', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:38:35', '2024-01-27 19:38:35', 0, '', 0),
    (190, 'retryPowerJobInstance', '重试PowerJob实例', 1, 2, '重试PowerJob实例', '/powerjob/instance/retry', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:39:02', '2024-01-27 19:39:02', 0, '', 0),
    (191, 'queryPowerJobInstanceStatus', '查询PowerJob实例状态', 1, 1, '查询PowerJob实例状态', '/powerjob/instance/queryStatus/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:39:39', '2024-01-27 19:39:39', 0, '', 0),
    (192, 'queryPowerJobInstanceInfo', '查询PowerJob实例详情', 1, 1, '查询PowerJob实例详情', '/powerjob/instance/queryInfo/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-27 19:40:17', '2024-01-27 19:40:17', 0, '', 0),
    (193, 'validateTimeExpression', '表达式校验', 1, 1, '表达式校验', '/validate/timeExpression', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-28 16:15:12', '2024-01-28 16:15:12', 0, '', 0),
    (194, 'getBranchSelectList', '查询gitlab分支选择列表', 1, 1, '查询gitlab分支选择列表', '/devops/gitlab/getBranchSelectList', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:45:00', '2024-01-29 09:45:00', 0, '', 0),
    (195, 'pageDevlopsJobList', '分页查询Devops历史任务列表', 1, 1, '分页查询Devops历史任务列表', '/devops/job/page', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:46:55', '2024-01-29 09:46:55', 0, '', 0),
    (196, 'getDevopsConsoleOutput', '获取Devops控制台输出', 1, 1, '获取Devops控制台输出', '/devops/job/getConsoleOutput/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:47:28', '2024-01-29 09:47:28', 0, '', 0),
    (197, 'addGatewayApi', '新增网关api', 2, 2, '新增网关api', '/gateway/add', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:25:15', '2024-01-29 11:25:15', 0, '', 0),
    (198, 'updateGatewayApi', '更新网关api', 2, 2, '更新网关api', '/gateway/update', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:25:42', '2024-01-29 11:25:42', 0, '', 0),
    (199, 'findGatewayApiById', '根据id查询网关信息', 2, 1, '根据id查询网关信息', '/gateway/findById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:26:33', '2024-01-29 11:26:33', 0, '', 0),
    (200, 'deleteGatewayApiById', '根据id删除网关', 2, 2, '根据id删除网关', '/gateway/deleteById/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:27:04', '2024-01-29 11:27:04', 0, '', 0),
    (201, 'onlineGatewayApi', '上线网关api', 2, 2, '上线网关api', '/gateway/onlineApi/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:27:37', '2024-01-29 11:27:37', 0, '', 0),
    (202, 'offlineGatewayApi', '下线网关api', 2, 2, '下线网关api', '/gateway/offlineApi/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:28:06', '2024-01-29 11:28:06', 0, '', 0),
    (203, 'changeGatewayApiAuth', '变更网关api认证', 2, 2, '变更网关api认证', '/gateway/changeApiAuth/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:28:34', '2024-01-29 11:28:34', 0, '', 0),
    (204, 'selectGatewayApiMetadataList', '根据服务id获取网关api元数据选择列表', 2, 1, '根据服务id获取网关api元数据选择列表', '/metadata/selectApiMetadataList/**', 1, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:29:03', '2024-01-29 11:29:03', 0, '', 0),
    (205, 'getButtonPermissionMap', '查询按钮权限', 1, 1, '查询按钮权限', '/menu/getButtonPermissionMap', 2, 1, 1, 0, 1, '', 'SYSTEM', 'SYSTEM', '2024-01-29 16:05:02', '2024-01-29 16:05:04', 1, '', 0),
    (206, 'addPulsarTenant', '新增租户', 1, 2, '新增租户', '/pulsar/tenant/add', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:50:56', '2024-02-28 09:50:56', 0, '', 0),
    (207, 'updatePulsarTenant', '修改租户', 1, 2, '修改租户', '/pulsar/tenant/update', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:51:44', '2024-02-28 09:51:44', 0, '', 0),
    (208, 'findPulsarTenantById', '根据id查询租户详情', 1, 1, '根据id查询租户详情', '/pulsar/tenant/findById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:53:42', '2024-02-28 09:53:42', 0, '', 0),
    (209, 'deletePulsarTenantById', '根据id删除租户', 1, 2, '根据id删除租户', '/pulsar/tenant/deleteById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:54:58', '2024-02-28 09:54:58', 0, '', 0),
    (210, 'pagePulsarTenantList', '分页查询租户列表', 1, 1, '分页查询租户列表', '/pulsar/tenant/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:55:33', '2024-02-28 09:55:33', 0, '', 0),
    (211, 'getPulsarTenantSelectList', '查询租户选择列表', 1, 1, '查询租户选择列表', '/pulsar/tenant/getTenantSelectList', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 09:59:19', '2024-02-28 09:59:19', 0, '', 0),
    (212, 'addPulsarNamespace', '新增命名空间', 1, 2, '新增命名空间', '/pulsar/namespace/add', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:00:04', '2024-02-28 10:00:04', 0, '', 0),
    (213, 'updatePulsarNamespace', '更新命名空间', 1, 2, '更新命名空间', '/pulsar/namespace/update', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:00:31', '2024-02-28 10:00:31', 0, '', 0),
    (214, 'findPulsarNamespaceById', '根据id查询命名空间详情', 1, 1, '根据id查询命名空间详情', '/pulsar/namespace/findById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:01:01', '2024-02-28 10:01:01', 0, '', 0),
    (215, 'deletePulsarNamespaceByid', '根据id删除命名空间', 1, 2, '根据id删除命名空间', '/pulsar/namespace/deleteById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:02:03', '2024-02-28 10:02:03', 0, '', 0),
    (216, 'pagePulsarNamespaceList', '分页查询命名空间列表', 1, 1, '分页查询命名空间列表', '/pulsar/namespace/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:02:30', '2024-02-28 10:02:30', 0, '', 0),
    (217, 'initPulsarToken', '初始化PulsarToken', 1, 2, '初始化PulsarToken', '/pulsar/auth/initToken', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:04:00', '2024-02-28 10:04:00', 0, '', 0),
    (218, 'getBrokerToken', '获取PulsarToken', 1, 1, '获取PulsarToken', '/pulsar/auth/getBrokerToken/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:04:31', '2024-02-28 17:22:50', 0, '', 0),
    (219, 'getPulsarBookieList', '查询bookie列表', 1, 1, '查询bookie列表', '/pulsar/bookie/list', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:05:01', '2024-02-28 10:05:01', 0, '', 0),
    (220, 'pulsarHeartbeatBookie', '跳转bookie心跳链接', 1, 1, '跳转bookie心跳链接', '/pulsar/bookie/heartbeat/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:05:50', '2024-02-28 10:05:50', 0, '', 0),
    (221, 'getPulsarBrokerList', '查询broker集合', 1, 1, '查询broker集合', '/pulsar/broker/list/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:06:22', '2024-02-28 10:06:22', 0, '', 0),
    (222, 'getBrokerStatsMetrics', '查询broker监控指标链接', 1, 1, '查询broker监控指标链接', '/pulsar/broker/stats/metrics', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:06:59', '2024-02-28 10:06:59', 0, '', 0),
    (223, 'getPulsarClusterList', '查询pulsar集群列表', 1, 1, '查询pulsar集群列表', '/pulsar/cluster/list', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:07:37', '2024-02-28 10:07:37', 0, '', 0),
    (224, 'addPulsarTopic', '新增PulsarTopic', 1, 2, '新增PulsarTopic', '/pulsar/topic/add', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:08:11', '2024-02-28 10:08:11', 0, '', 0),
    (225, 'updatePulsarTopic', '更新PulsarTopic', 1, 2, '更新PulsarTopic', '/pulsar/topic/update', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:08:35', '2024-02-28 10:08:35', 0, '', 0),
    (226, 'findPulsarTopicById', '根据id查询topic详情', 1, 1, '根据id查询topic详情', '/pulsar/topic/findById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:09:43', '2024-02-28 10:09:43', 0, '', 0),
    (227, 'deletePulsarTopicById', '根据id删除topic', 1, 2, '根据id删除topic', '/pulsar/topic/deleteById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:10:13', '2024-02-28 10:10:13', 0, '', 0),
    (228, 'pagePulsarTopicList', '分页查询topic列表', 1, 1, '分页查询topic列表', '/pulsar/topic/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:10:37', '2024-02-28 10:10:37', 0, '', 0),
    (229, 'findPulsarTopicDetail', '查询topic详情', 1, 1, '查询topic详情', '/pulsar/topic/findTopicDetail/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:11:16', '2024-02-28 10:11:16', 0, '', 0),
    (230, 'assignSubscriptionPermission', '分配订阅权限', 1, 2, '分配订阅权限', '/pulsar/topic/assignSubscriptionPermission', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:12:05', '2024-02-28 10:12:05', 0, '', 0),
    (231, 'listPulsarTopicProduces', '查询主题生产者列表', 1, 1, '查询主题生产者列表', '/pulsar/topic/listTopicProduces/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:12:35', '2024-02-28 10:12:35', 0, '', 0),
    (232, 'addPulsarSubscription', '新增subscription', 1, 2, '新增Subscription', '/pulsar/subscription/add', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:13:43', '2024-02-28 10:13:43', 0, '', 0),
    (233, 'findPulsarSubscriptionById', '根据id查询Subscription详情', 1, 1, '根据id查询Subscription详情', '/pulsar/subscription/findById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:14:54', '2024-02-28 10:14:54', 0, '', 0),
    (234, 'deletePulsarSubscriptionById', '根据id删除subscription', 1, 2, '根据id删除subscription', '/pulsar/subscription/deleteById/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:15:23', '2024-02-28 10:15:23', 0, '', 0),
    (235, 'pagePulsarSubscriptionList', '分页查询subscription列表', 1, 1, '分页查询subscription列表', '/pulsar/subscription/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:15:52', '2024-02-28 10:15:52', 0, '', 0),
    (236, 'listPulsarSubscriptionConsumes', '查询订阅消费者实例列表', 1, 1, '查询订阅消费者实例列表', '/pulsar/subscription/listSubscriptionConsumes/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:16:19', '2024-02-28 10:16:19', 0, '', 0),
    (237, 'listPulsarConsumeProcess', 'Pulsar消费进度', 1, 1, 'Pulsar消费进度', '/pulsar/subscription/listConsumeProcess/**', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:16:54', '2024-02-28 10:16:54', 0, '', 0),
    (238, 'offsetPulsar', 'Pulsar消息offset设置', 1, 2, 'Pulsar消息offset设置', '/pulsar/subscription/offset', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-02-28 10:17:32', '2024-02-28 10:17:32', 0, '', 0),
    (239, 'pagePulsarMessageList', '分页查询Pulsar消息列表', 1, 1, '分页查询Pulsar消息列表', '/pulsar/message/page', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-03-01 16:51:38', '2024-03-01 16:51:38', 0, '', 0),
    (240, 'findPulsarMessageDetail', '查询Pulsar消息详情', 1, 1, '查询Pulsar消息详情', '/pulsar/message/findMessageDetail', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-03-01 16:52:06', '2024-03-01 16:52:06', 0, '', 0),
    (241, 'updatePulsarSubscription', '更新Pulsar订阅', 1, 2, '更新Pulsar订阅', '/pulsar/subscription/update', 1, 1, 1, 0, 1, '', 'admin', 'admin', '2024-03-04 10:46:29', '2024-03-04 10:46:29', 0, '', 0);
/*!40000 ALTER TABLE `sys_api` ENABLE KEYS */;

--
-- Table structure for table `sys_app_park`
--

DROP TABLE IF EXISTS `sys_app_park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_app_park` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `app_id` bigint NOT NULL DEFAULT '0' COMMENT 'appId',
                                `app_code` varchar(64) NOT NULL DEFAULT '' COMMENT 'app编码',
                                `app_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'app名称',
                                `env` varchar(8) NOT NULL DEFAULT 'dev' COMMENT '环境变量',
                                `type` tinyint NOT NULL DEFAULT '1' COMMENT 'app类型',
                                `app_desc` varchar(512) NOT NULL DEFAULT '' COMMENT '应用描述',
                                `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
                                `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_app` (`app_id`,`app_code`,`env`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_app_park`
--

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `dept_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门编码',
                            `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父id',
                            `parent_dept_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父部门编码',
                            `name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权部门名称',
                            `dept_order` tinyint NOT NULL DEFAULT '0' COMMENT '部门序号',
                            `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                            `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                            `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                            `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                            `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                            `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                            `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uniq_dept_code` (`dept_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (2,'master',0,'','总公司',1,'','SYSTEM','SYSTEM','2022-08-18 07:46:27','2022-08-18 07:46:27',0,'',0);
INSERT INTO `sys_dept` VALUES (3,'hefeiBranch',2,'','合肥分公司',1,'','SYSTEM','SYSTEM','2022-08-18 07:49:01','2022-08-18 07:49:01',0,'',0);
INSERT INTO `sys_dept` VALUES (4,'hefeiTechnical',3,'','技术部',1,'','SYSTEM','SYSTEM','2022-08-18 07:51:44','2022-08-18 07:51:44',0,'',0);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;

--
-- Table structure for table `sys_dept_relation`
--

DROP TABLE IF EXISTS `sys_dept_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_relation` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `ancestor_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父部门编码',
                                     `descendant_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '子部门编码',
                                     `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                     `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                                     `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                                     `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                     `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                     `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                     `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                                     `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uniq_code` (`ancestor_code`,`descendant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统部门关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_relation`
--

/*!40000 ALTER TABLE `sys_dept_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept_relation` ENABLE KEYS */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `dict_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典编码',
                            `dict_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典名称',
                            `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型:系统/业务',
                            `is_changed` tinyint NOT NULL DEFAULT '1' COMMENT '是否可变',
                            `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                            `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                            `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                            `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                            `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uniq_code` (`dict_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'sex','性别',1,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict` VALUES (2,'userStatus','用户状态',1,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict` VALUES (3,'readOrWrite','读写枚举',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:49','2022-08-08 03:32:49',0);
INSERT INTO `sys_dict` VALUES (4,'apiType','接口类型',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:49','2022-08-08 03:32:49',0);
INSERT INTO `sys_dict` VALUES (5,'menuType','菜单类型',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:48','2022-08-09 06:11:48',0);
INSERT INTO `sys_dict` VALUES (6,'openType','菜单打开类型',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:48','2022-08-09 06:11:48',0);
INSERT INTO `sys_dict` VALUES (7,'yesOrNo','是否类型',1,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:00','2022-08-15 06:46:00',0);
INSERT INTO `sys_dict` VALUES (8,'dataScope','数据权限',1,0,'','SYSTEM','SYSTEM','2022-08-18 08:43:13','2022-08-18 08:43:13',0);
INSERT INTO `sys_dict` VALUES (9,'test','测试',1,1,'','SYSTEM','SYSTEM','2022-08-18 10:47:04','2022-08-19 03:11:09',9);
INSERT INTO `sys_dict` VALUES (10,'importBizType','导入模版类型',1,1,'','admin','admin','2022-11-08 08:23:03','2022-11-08 08:23:03',0);
INSERT INTO `sys_dict` VALUES (11,'appType','应用类型',1,1,'','SYSTEM','SYSTEM','2023-12-12 02:46:43','2023-12-12 02:46:43',0);
INSERT INTO `sys_dict` VALUES (12,'resourceType','应用资源类型',1,1,'','SYSTEM','SYSTEM','2023-12-20 07:52:27','2023-12-20 07:52:27',0);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;

--
-- Table structure for table `sys_dict_detail`
--

DROP TABLE IF EXISTS `sys_dict_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_detail` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                   `dict_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典编码',
                                   `dict_detail_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典详情编码',
                                   `dict_detail_desc` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典详情名称',
                                   `detail_order` tinyint NOT NULL DEFAULT '0' COMMENT '排序',
                                   `is_edit` tinyint NOT NULL DEFAULT '1' COMMENT '是否可编辑',
                                   `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                   `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                                   `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                                   `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                   `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                   `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uniq_code` (`dict_code`,`dict_detail_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统字典详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_detail`
--

/*!40000 ALTER TABLE `sys_dict_detail` DISABLE KEYS */;
INSERT INTO `sys_dict_detail` VALUES (1,'sex','1','男',1,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-19 10:43:49',0);
INSERT INTO `sys_dict_detail` VALUES (2,'sex','2','女',2,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict_detail` VALUES (3,'userStatus','1',' 正常',1,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict_detail` VALUES (4,'userStatus','2',' 禁用',2,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict_detail` VALUES (5,'userStatus','3',' 注销',3,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0);
INSERT INTO `sys_dict_detail` VALUES (6,'apiType','1','web端系统类型',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0);
INSERT INTO `sys_dict_detail` VALUES (7,'apiType','2','web端业务类型',2,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0);
INSERT INTO `sys_dict_detail` VALUES (8,'apiType','3','移动端系统类型',3,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0);
INSERT INTO `sys_dict_detail` VALUES (9,'apiType','4','移动端业务类型',4,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0);
INSERT INTO `sys_dict_detail` VALUES (10,'readOrWrite','1','读',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:33:01','2022-08-08 03:33:01',0);
INSERT INTO `sys_dict_detail` VALUES (11,'readOrWrite','2','写',2,0,'','SYSTEM','SYSTEM','2022-08-08 03:33:01','2022-08-08 03:33:01',0);
INSERT INTO `sys_dict_detail` VALUES (12,'menuType','1','目录',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0);
INSERT INTO `sys_dict_detail` VALUES (13,'menuType','2','菜单',2,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0);
INSERT INTO `sys_dict_detail` VALUES (14,'menuType','3','按钮',3,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0);
INSERT INTO `sys_dict_detail` VALUES (15,'openType','1','页面内',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0);
INSERT INTO `sys_dict_detail` VALUES (16,'openType','2','外部链接',2,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0);
INSERT INTO `sys_dict_detail` VALUES (17,'yesOrNo','1','是',1,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:23','2022-08-15 06:46:23',0);
INSERT INTO `sys_dict_detail` VALUES (18,'yesOrNo','0','否',2,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:23','2022-08-15 06:46:23',0);
INSERT INTO `sys_dict_detail` VALUES (19,'dataScope','1','全部',1,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0);
INSERT INTO `sys_dict_detail` VALUES (20,'dataScope','2','本级',2,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0);
INSERT INTO `sys_dict_detail` VALUES (21,'dataScope','3','下级',3,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0);
INSERT INTO `sys_dict_detail` VALUES (22,'dataScope','4','本级及下级',4,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0);
INSERT INTO `sys_dict_detail` VALUES (23,'dataScope','5','本人',5,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0);
INSERT INTO `sys_dict_detail` VALUES (24,'sex','3','人妖',3,1,'','SYSTEM','SYSTEM','2022-08-19 10:44:53','2022-08-19 10:44:58',24);
INSERT INTO `sys_dict_detail` VALUES (25,'importBizType','SYS_USER_IMPORT','系统用户导入',1,1,'','admin','admin','2022-11-08 08:23:42','2022-11-08 08:23:42',0);
INSERT INTO `sys_dict_detail` VALUES (26,'appType','1','java',1,1,'','SYSTEM','SYSTEM','2023-12-12 02:48:22','2023-12-12 02:48:22',0);
INSERT INTO `sys_dict_detail` VALUES (27,'appType','2','node',2,1,'','SYSTEM','SYSTEM','2023-12-12 02:48:51','2023-12-12 02:48:51',0);
INSERT INTO `sys_dict_detail` VALUES (28,'appType','3','html',3,1,'','SYSTEM','SYSTEM','2023-12-12 02:49:01','2023-12-12 02:49:01',0);
INSERT INTO `sys_dict_detail` VALUES (29,'resourceType','1','mysql',1,1,'','SYSTEM','SYSTEM','2023-12-20 07:52:48','2023-12-20 07:52:48',0);
INSERT INTO `sys_dict_detail` VALUES (30,'resourceType','2','redis',2,1,'','SYSTEM','SYSTEM','2023-12-20 07:52:55','2023-12-20 07:52:55',0);
INSERT INTO `sys_dict_detail` VALUES (31,'resourceType','3','es',3,1,'','SYSTEM','SYSTEM','2023-12-20 07:53:03','2023-12-20 07:53:03',0);
INSERT INTO `sys_dict_detail` VALUES (32,'resourceType','4','mongodb',4,1,'','SYSTEM','SYSTEM','2023-12-20 07:53:12','2023-12-20 07:53:12',0);
/*!40000 ALTER TABLE `sys_dict_detail` ENABLE KEYS */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单id',
                            `parent_menu_code` varchar(16) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父菜单编码',
                            `menu_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单编码',
                            `menu_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
                            `menu_type` tinyint NOT NULL DEFAULT '1' COMMENT '菜单类型',
                            `menu_order` tinyint NOT NULL DEFAULT '0' COMMENT '菜单序号',
                            `component` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组件路径',
                            `menu_path` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单url',
                            `open_type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '打开方式(1:页面内,2:外链)',
                            `active_menu` varchar(500) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '激活当前页面的菜单路径',
                            `dynamic_new_tab` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否动态创建新的tab页',
                            `hidden` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏',
                            `menu_icon` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
                            `remark` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                            `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录创建者',
                            `modifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '行记录最近更新人',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                            `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                            `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                            `extension` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                            `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                            PRIMARY KEY (`id`),
                            KEY `idx_menu_code` (`menu_code`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
insert into edas-admin.sys_menu (id, parent_id, parent_menu_code, menu_code, menu_name, menu_type, menu_order, component, menu_path, open_type, active_menu, dynamic_new_tab, hidden, menu_icon, remark, creator, modifier, created_time, modified_time, version, extension, deleted)
values  (1, 0, '', 'BaseData', '基础数据', 1, 1, 'Layout', '/baseData', 1, '', 1, 0, 'settings-line', '', 'SYSTEM', 'SYSTEM', '2022-08-02 17:09:08', '2022-08-09 16:52:24', 5, '', 0),
    (2, 1, '', 'Permission', '权限中心', 1, 1, '', 'permission', 1, '', 1, 0, 'shield-keyhole-line', '', 'SYSTEM', 'SYSTEM', '2022-08-02 17:09:08', '2022-08-18 11:15:09', 6, '', 0),
    (3, 2, '', 'SysUser', '用户管理', 2, 1, '@/views/baseData/permission/sysUser/index', 'sysUser', 1, '', 1, 0, 'user-line', '', 'SYSTEM', 'SYSTEM', '2022-08-02 17:09:08', '2022-08-15 17:11:05', 1, '', 0),
    (4, 2, '', 'SysApi', '接口管理', 2, 2, '@/views/baseData/permission/sysApi/index', 'sysApi', 1, '', 1, 0, 'flashlight-line', '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:36', '2022-08-15 17:11:10', 1, '', 0),
    (5, 2, '', 'SysRole', '角色管理', 2, 3, '@/views/baseData/permission/sysRole/index', 'sysRole', 1, '', 1, 0, 'account-pin-circle-line', '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:36', '2022-08-15 17:11:13', 1, '', 0),
    (6, 2, '', 'SysMenu', '菜单管理', 2, 4, '@/views/baseData/permission/sysMenu/index', 'sysMenu', 1, '', 1, 0, 'menu-fill', '', 'SYSTEM', 'SYSTEM', '2022-08-08 10:05:36', '2022-08-15 17:11:18', 1, '', 0),
    (7, 0, '', 'Workflow', '工作流', 1, 1, 'Layout', '/workflow', 1, '', 1, 0, 'settings-line', '', 'SYSTEM', 'SYSTEM', '2022-08-09 10:47:00', '2022-08-18 11:15:21', 1, '', 7),
    (8, 1, '', 'Log', '日志管理', 1, 1, 'Layout', 'log', 1, '', 1, 0, 'settings-line', '', 'SYSTEM', 'SYSTEM', '2022-08-09 10:47:55', '2022-08-09 14:47:05', 1, '', 8),
    (9, 7, '', 'ProcessModel', '流程模型管理', 2, 1, 'Layout', 'processModel', 1, '', 1, 0, 'function-line', '', 'SYSTEM', 'SYSTEM', '2022-08-09 14:27:52', '2022-08-09 14:36:03', 1, '', 9),
    (11, 2, '', 'AuthDetail', '权限详情页', 2, 1, '@/views/baseData/permission/sysUser/authDetail', 'sysUser/authDetail', 1, '/baseData/permission/sysUser/index', 1, 1, 'user-line', '', 'SYSTEM', 'SYSTEM', '2022-08-11 11:20:33', '2022-08-16 11:41:49', 1, '', 0),
    (12, 2, '', 'RoleAutDetail', '角色权限详情', 2, 6, '@/views/baseData/permission/sysRole/authDetail', 'sysRole/authDetail', 1, '/baseData/permission/sysRole/index', 1, 1, 'account-circle-line', '', 'SYSTEM', 'SYSTEM', '2022-08-15 14:50:46', '2022-08-15 14:51:25', 0, '', 0),
    (13, 2, '', 'MenuAuthDetail', '菜单权限详情', 2, 7, '@/views/baseData/permission/sysMenu/authDetail', 'sysMenu/authDetail', 1, '/baseData/permission/sysMenu/index', 1, 1, 'menu-2-fill', '', 'SYSTEM', 'SYSTEM', '2022-08-15 17:21:05', '2022-08-15 17:21:19', 0, '', 0),
    (14, 3, '', 'userAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:21:31', '2022-08-16 10:34:23', 1, '', 0),
    (15, 3, '', 'userEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:21:53', '2022-08-16 10:34:23', 1, '', 0),
    (16, 3, '', 'userDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:23:03', '2022-08-16 10:34:23', 1, '', 0),
    (17, 3, '', 'isDisable', '是否禁用', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:33:33', '2022-08-16 10:34:23', 0, '', 0),
    (18, 4, '', 'apiAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:36:24', '2022-08-16 10:36:24', 0, '', 0),
    (19, 4, '', 'apiEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:37:06', '2022-08-16 10:37:06', 0, '', 0),
    (20, 4, '', 'apiDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:37:24', '2022-08-16 10:37:24', 0, '', 0),
    (21, 4, '', 'isOpen', '是否公开', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:37:48', '2022-08-16 10:37:48', 0, '', 0),
    (22, 4, '', 'isAuth', '是否认证', 3, 5, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:38:01', '2022-08-16 10:38:01', 0, '', 0),
    (23, 4, '', 'isDisabled', '是否禁用', 3, 6, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:38:24', '2022-08-16 10:38:24', 0, '', 0),
    (24, 5, '', 'roleAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:47:38', '2022-08-16 10:47:38', 0, '', 0),
    (25, 5, '', 'roleEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:47:54', '2022-08-16 10:47:54', 0, '', 0),
    (26, 5, '', 'roleDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:48:09', '2022-08-16 10:48:09', 0, '', 0),
    (27, 5, '', 'setRoleAuth', '设置权限', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:48:36', '2022-08-16 10:48:36', 0, '', 0),
    (28, 6, '', 'menuAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:51:06', '2022-08-16 10:51:06', 0, '', 0),
    (29, 6, '', 'menuEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:51:22', '2022-08-16 10:51:22', 0, '', 0),
    (30, 6, '', 'menuDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:51:35', '2022-08-16 10:51:35', 0, '', 0),
    (31, 6, '', 'setMenuAuth', '设置权限', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 10:51:50', '2022-08-16 10:51:50', 0, '', 0),
    (32, 3, '', 'setUserAuth', '设置权限', 3, 5, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-16 11:12:29', '2022-08-16 11:25:03', 0, '', 0),
    (33, 1, '', 'Tenant', '租户中心', 1, 2, 'Layout', '/tenant', 1, '', 0, 0, 'admin-line', '', 'SYSTEM', 'SYSTEM', '2022-08-18 11:18:06', '2023-12-04 09:12:35', 2, '', 33),
    (34, 2, '', 'SysDept', '部门管理', 2, 5, '@/views/baseData/permission/sysDept/index', 'sysDept', 1, '', 0, 0, 'account-box-line', '', 'SYSTEM', 'SYSTEM', '2022-08-18 14:03:27', '2022-08-18 14:03:27', 0, '', 0),
    (35, 34, '', 'deptAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:23:47', '2022-08-18 15:23:47', 0, '', 0),
    (36, 34, '', 'deptEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:24:00', '2022-08-18 15:24:00', 0, '', 0),
    (37, 34, '', 'deptDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 15:24:13', '2022-08-18 15:24:13', 0, '', 0),
    (38, 3, '', 'userProfile', '个人中心', 2, 6, '@/views/baseData/permission/sysUser/userProfile', 'sysUser/userProfile', 1, '', 1, 1, 'file-user-line', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:11:25', '2022-08-18 17:15:28', 1, '', 38),
    (39, 1, '', 'personalCenter', '个人中心', 2, 3, '@/views/baseData/personalCenter/index', 'personalCenter', 1, '', 1, 1, 'file-user-line', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:18:08', '2022-08-18 17:18:08', 0, '', 0),
    (40, 1, '', 'SysDict', '字典管理', 2, 2, '', 'dict', 1, '', 0, 0, 'book-2-line', '', 'SYSTEM', 'admin', '2022-08-18 17:25:39', '2022-11-08 14:28:51', 4, '', 0),
    (41, 46, '', 'dictAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:25:56', '2022-08-19 17:53:10', 0, '', 0),
    (42, 46, '', 'dictEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:28:00', '2022-08-19 17:53:10', 0, '', 0),
    (43, 46, '', 'dictDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:28:13', '2022-08-19 17:53:10', 0, '', 0),
    (44, 46, '', 'dictDetail', '详情', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-18 17:28:38', '2022-08-19 17:53:10', 0, '', 0),
    (45, 40, '', 'SysDictDetail', '字典详情', 2, 5, '@/views/baseData/dict/detail', 'detail', 1, '/baseData/dict/index', 1, 1, 'book-2-line', '', 'SYSTEM', 'SYSTEM', '2022-08-18 18:09:06', '2022-08-19 17:56:55', 3, '', 0),
    (46, 40, '', 'dictList', '字典列表', 2, 1, '@/views/baseData/dict/index', 'index', 1, '', 0, 0, 'book-3-line', '', 'SYSTEM', 'SYSTEM', '2022-08-19 17:51:20', '2022-08-19 17:56:17', 2, '', 0),
    (47, 45, '', 'dictDetailAdd', '添加', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:17:41', '2022-08-19 18:17:41', 0, '', 0),
    (48, 45, '', 'dictDetailEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:17:58', '2022-08-19 18:17:58', 0, '', 0),
    (49, 45, '', 'dictDetailDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2022-08-19 18:18:09', '2022-08-19 18:18:09', 0, '', 0),
    (50, 0, '', 'Business', '业务管理', 1, 99, 'Layout', '/business', 1, '', 1, 0, 'bubble-chart-line', '', 'SYSTEM', 'SYSTEM', '2022-09-13 16:59:49', '2023-12-04 09:21:58', 2, '', 50),
    (51, 50, '', 'Category', '类目管理', 2, 1, '', 'category', 1, '', 1, 0, 'align-left', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:17:33', '2022-09-22 16:46:18', 1, '', 0),
    (52, 50, '', 'Course', '课程管理', 2, 2, '', 'course', 1, '', 1, 0, 'book-mark-line', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:18:30', '2022-09-22 16:46:14', 1, '', 0),
    (53, 50, '', 'Video', '视频管理', 2, 3, '', 'video', 1, '', 1, 0, 'video-line', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:19:09', '2022-09-22 16:46:22', 1, '', 0),
    (54, 51, '', 'FrontCategory', '前台类目', 2, 1, '@/views/business/category/front/index', 'front', 1, '', 1, 0, 'align-left', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:31:00', '2022-09-20 16:31:00', 0, '', 0),
    (55, 52, '', 'CourseBase', '课程基础信息', 2, 1, '@/views/business/course/base/index', 'base', 1, '', 1, 0, 'book-open-line', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:32:14', '2022-09-20 16:32:14', 0, '', 0),
    (56, 53, '', 'AliyunVod', '基础视频', 2, 1, '@/views/business/video/base/index', 'base', 1, '', 1, 0, 'video-add-line', '', 'SYSTEM', 'SYSTEM', '2022-09-20 16:33:04', '2022-09-22 17:01:02', 1, '', 0),
    (57, 50, '', 'CourseChapter', '章节管理', 2, 2, '@/views/business/chapter/base/index', '/business/chapter/base', 1, '/business/course/base/index', 1, 1, 'a-b', '', 'SYSTEM', 'SYSTEM', '2022-09-22 16:22:45', '2022-09-22 16:34:45', 5, '', 0),
    (58, 57, '', 'ChapterVideo', '章节视频管理', 2, 1, '@/views/business/chapter/base/video', 'video', 1, '/business/chapter/base/index', 1, 1, 'video-download-line', '', 'SYSTEM', 'SYSTEM', '2022-09-22 20:12:46', '2022-09-22 20:13:21', 2, '', 58),
    (59, 51, '', 'CourseCategory', '课程类目管理', 2, 2, '@/views/business/category/front/courseCategory', 'courseCategory', 1, '', 1, 0, 'artboard-2-line', '', 'SYSTEM', 'SYSTEM', '2022-09-23 10:27:55', '2022-09-23 10:27:55', 0, '', 0),
    (60, 50, '', 'Live', '直播管理', 2, 5, '', 'live', 1, '', 1, 0, 'live-line', '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:26:31', '2022-10-18 20:27:13', 0, '', 0),
    (61, 60, '', 'AliyunLive', '阿里云直播', 2, 1, '@/views/business/live/base/index', 'base', 1, '', 1, 0, 'live-line', '', 'SYSTEM', 'SYSTEM', '2022-10-18 20:28:29', '2022-10-18 20:28:40', 1, '', 0),
    (62, 52, '', 'CourseAuthorization', '课程授权', 2, 2, '@/views/business/course/authorization/index', 'authorization', 1, '', 1, 0, 'contacts-line', '', 'SYSTEM', 'SYSTEM', '2022-10-27 10:07:54', '2022-10-27 10:07:54', 0, '', 0),
    (63, 52, '', 'UserCourse', '用户课程', 2, 3, '@/views/business/course/authorization/userCourse', '/business/course/authorization/userCourse', 1, '/business/course/authorization/index', 1, 1, '24-hours-fill', '', 'SYSTEM', 'SYSTEM', '2022-10-27 10:12:44', '2022-10-27 10:14:21', 1, '', 0),
    (64, 0, '', 'Infrastructure', '基础保障', 1, 2, 'Layout', '/infrastructure', 1, '', 1, 0, 'codepen-line', '', 'admin', 'SYSTEM', '2022-11-08 14:31:21', '2023-12-04 11:31:23', 4, '', 64),
    (65, 64, '', 'TemplateManager', '模版管理', 2, 1, '', 'template', 1, '', 1, 0, 'device-line', '', 'admin', 'admin', '2022-11-08 14:37:12', '2022-11-08 14:37:12', 0, '', 0),
    (66, 65, '', 'ImportTemplate', '导入模版', 2, 1, '@/views/infrastructure/template/import/index', 'import', 1, '', 1, 0, 'sim-card-line', '', 'admin', 'admin', '2022-11-08 14:39:29', '2022-11-08 15:15:36', 1, '', 0),
    (67, 1, '', 'applicationManager', '应用管理', 2, 2, '@/views/baseData/application/index', 'application', 1, '', 1, 0, 'app-store-fill', '', 'SYSTEM', 'SYSTEM', '2023-12-04 11:33:36', '2023-12-20 15:39:50', 1, '', 0),
    (68, 0, '', 'workspace', '工作空间', 1, 2, 'Layout', '/workspace', 1, '', 1, 0, 'space-ship-fill', '', 'SYSTEM', 'SYSTEM', '2023-12-12 15:59:30', '2023-12-12 15:59:30', 0, '', 0),
    (69, 68, '', 'myApp', '我的应用', 2, 1, '@/views/workspace/myApp/index', 'myApp', 1, '', 1, 0, 'apps-line', '', 'SYSTEM', 'SYSTEM', '2023-12-12 16:01:25', '2023-12-14 16:31:10', 0, '', 0),
    (70, 68, '', 'gateway', '网关管理', 2, 2, '@/views/workspace/gateway/index', '/gateway', 1, '', 1, 0, 'usb-line', '', 'SYSTEM', 'SYSTEM', '2023-12-12 16:09:32', '2023-12-12 16:09:32', 0, '', 0),
    (71, 68, '', 'appDetail', '应用详情', 2, 1, '@/views/workspace/myApp/appDetail', 'myApp/appDetail', 1, '/workspace/myApp/index', 1, 1, '24-hours-fill', '', 'SYSTEM', 'SYSTEM', '2023-12-14 16:21:55', '2023-12-14 16:37:54', 0, '', 0),
    (73, 1, '', 'appResource', '应用资源', 2, 1, '@/views/baseData/application/appResource', 'application/appResource', 1, '/baseData/application/index', 1, 1, '24-hours-fill', '', 'SYSTEM', 'SYSTEM', '2023-12-14 16:21:55', '2023-12-20 15:39:50', 0, '', 0),
    (74, 68, '', 'RocketMQ', '消息队列RocketMQ', 2, 3, '@/views/workspace/rocketmq/index', 'rocketmq', 1, '', 1, 0, 'chat-poll-line', '', 'SYSTEM', 'SYSTEM', '2024-01-15 15:45:29', '2024-01-15 15:45:29', 0, '', 0),
    (75, 68, '', 'RocketMqSubscription', 'RocketMq订阅详情', 2, 1, '@/views/workspace/rocketmq/subscription', 'rocketmq/subscription', 1, '/workspace/rocketmq/index', 1, 1, '24-hours-fill', '', 'SYSTEM', 'SYSTEM', '2024-01-16 23:56:02', '2024-01-19 10:13:44', 0, '', 0),
    (76, 68, '', 'MessageList', '消息列表', 2, 1, '@/views/workspace/rocketmq/messageList', 'rocketmq/messageList', 1, '/workspace/rocketmq/index', 1, 1, '24-hours-fill', '', 'SYSTEM', 'SYSTEM', '2024-01-16 23:56:02', '2024-01-19 10:13:44', 0, '', 0),
    (77, 71, '', 'develop', '部署', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:18:05', '2024-01-29 09:18:05', 0, '', 0),
    (78, 71, '', 'developJobHistory', '部署记录', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:18:44', '2024-01-29 09:18:44', 0, '', 0),
    (79, 71, '', 'addNacosConfig', '新增Nacos配置', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:38:19', '2024-01-29 09:38:19', 0, '', 0),
    (80, 71, '', 'updateNacosConfig', '编辑Nacos配置', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:38:46', '2024-01-29 09:38:46', 0, '', 0),
    (81, 71, '', 'nacosHistory', 'nacos历史配置', 3, 5, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:39:49', '2024-01-29 09:39:49', 0, '', 0),
    (82, 71, '', 'devopsConsoleOut', 'Devops控制台输出', 3, 6, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:48:56', '2024-01-29 09:48:56', 0, '', 0),
    (83, 71, '', 'devopsAbort', 'Devops终止运行', 3, 7, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 09:49:29', '2024-01-29 09:49:29', 0, '', 0),
    (84, 71, '', 'deleteNacosConfig', '删除nacos配置', 3, 8, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:06:37', '2024-01-29 10:06:37', 0, '', 0),
    (85, 71, '', 'viewNacosHistoryConfig', '查看Nacos历史配置', 3, 9, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:12:45', '2024-01-29 10:12:45', 0, '', 0),
    (86, 71, '', 'initializeRocketMQ', '初始化RocketMQ配置', 3, 10, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:15:15', '2024-01-29 10:15:15', 0, '', 0),
    (87, 71, '', 'openPowerJob', '开通PowerJob', 3, 11, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:17:17', '2024-01-29 10:17:17', 0, '', 0),
    (88, 71, '', 'addPowerJob', '新增PowerJob任务', 3, 12, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:18:47', '2024-01-29 10:18:47', 0, '', 0),
    (89, 71, '', 'updatePowerJob', '编辑PowerJob任务', 3, 13, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:19:19', '2024-01-29 10:19:19', 0, '', 0),
    (90, 71, '', 'viewInstance', '查看PowerJob任务实例', 3, 14, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:19:55', '2024-01-29 10:19:55', 0, '', 0),
    (91, 71, '', 'runOncePowerJob', '执行一次PowerJob任务', 3, 15, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:20:18', '2024-01-29 10:20:18', 0, '', 0),
    (92, 71, '', 'enablePowerJob', '启动PowerJob任务', 3, 15, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:20:37', '2024-01-29 10:20:37', 0, '', 0),
    (93, 71, '', 'disablePowerJob', '禁止PowerJob任务', 3, 16, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:20:57', '2024-01-29 10:20:57', 0, '', 0),
    (94, 71, '', 'deletePowerJob', '删除PowerJob任务', 3, 17, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:21:16', '2024-01-29 10:21:16', 0, '', 0),
    (95, 71, '', 'viewPowerJobInstanceDetail', '查看PowerJob实例详情', 3, 18, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:25:22', '2024-01-29 10:25:22', 0, '', 0),
    (96, 71, '', 'retryPowerJobInstance', '重试PowerJob实例', 3, 19, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:25:44', '2024-01-29 10:25:44', 0, '', 0),
    (97, 71, '', 'stopPowerJobInstance', '停止PowerJob实例', 3, 20, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:26:08', '2024-01-29 10:26:08', 0, '', 0),
    (98, 71, '', 'cancelPowerJobInstance', '取消PowerJob实例', 3, 21, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:26:29', '2024-01-29 10:26:29', 0, '', 0),
    (99, 74, '', 'addRocketMQTopic', '新增RocketM_Topic', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:36:16', '2024-01-29 10:36:16', 0, '', 0),
    (100, 74, '', 'updateRocketMqTopic', '编辑RocketMQ_Topic', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:36:50', '2024-01-29 10:36:50', 0, '', 0),
    (101, 74, '', 'subscriptionRocketMqTopic', 'RocketMQ订阅列表', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:37:48', '2024-01-29 10:48:48', 1, '', 0),
    (102, 74, '', 'viewRocketMqMessage', 'RocketMQ消息列表', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:38:17', '2024-01-29 10:38:17', 0, '', 0),
    (103, 75, '', 'addRocketMqSubscription', '新增RocketMq_Subscription', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:51:23', '2024-01-29 10:51:23', 0, '', 0),
    (104, 75, '', 'updateRocketMqSubscription', '编辑RocketMq_Subscription', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:51:56', '2024-01-29 10:52:36', 0, '', 0),
    (105, 75, '', 'viewRocketMQSubscriptionDetail', '查看RocketMq订阅详情', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 10:53:27', '2024-01-29 10:53:27', 0, '', 0),
    (106, 75, '', 'deleteRocketMqSubscription', '删除RocketMq订阅', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:04:23', '2024-01-29 11:04:23', 0, '', 0),
    (107, 76, '', 'viewRocketMqMessageDetail', '消息详情', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:10:01', '2024-01-29 11:10:01', 0, '', 0),
    (108, 76, '', 'consumeDirectly', '消费结果', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:10:26', '2024-01-29 11:10:26', 0, '', 0),
    (109, 75, '', 'subscribeResetOffset', '重置消费点位', 3, 5, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:17:36', '2024-01-29 11:17:36', 0, '', 0),
    (110, 70, '', 'gatewayApiAdd', '新增', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:21:00', '2024-01-29 11:24:07', 1, '', 0),
    (111, 70, '', 'gatewatApiEdit', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:21:28', '2024-01-29 11:21:28', 0, '', 0),
    (112, 70, '', 'gatewayApiOnline', '上线', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:22:13', '2024-01-29 11:22:13', 0, '', 0),
    (113, 70, '', 'gatewayApiOffline', '下线', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:22:26', '2024-01-29 11:22:26', 0, '', 0),
    (114, 70, '', 'gatewayApiDelete', '删除', 3, 5, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:22:40', '2024-01-29 11:22:40', 0, '', 0),
    (115, 70, '', 'gatewayAuth', '是否认证', 3, 6, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 11:24:00', '2024-01-29 11:24:00', 0, '', 0),
    (116, 67, '', 'appParkAdd', '新增', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:47:47', '2024-01-29 14:47:47', 0, '', 0),
    (117, 67, '', 'appConfig', '应用配置', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:48:22', '2024-01-29 14:48:22', 0, '', 0),
    (118, 67, '', 'appResourceView', '应用资源', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:49:12', '2024-01-29 14:49:12', 0, '', 0),
    (119, 67, '', 'appParkDelete', '删除', 3, 4, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:49:36', '2024-01-29 14:49:36', 0, '', 0),
    (120, 73, '', 'appResourceAdd', '新增', 3, 1, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:58:46', '2024-01-29 14:58:46', 0, '', 0),
    (121, 73, '', 'appResourceUpdate', '编辑', 3, 2, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:59:06', '2024-01-29 14:59:06', 0, '', 0),
    (122, 73, '', 'appResourceDelete', '删除', 3, 3, '', '', 1, '', 0, 1, '', '', 'SYSTEM', 'SYSTEM', '2024-01-29 14:59:20', '2024-01-29 14:59:20', 0, '', 0),
    (123, 0, '', 'ComponentManager', '组件管理', 1, 3, 'Layout', '/componentManager', 1, '', 1, 0, 'chrome-fill', '', 'admin', 'admin', '2024-02-28 10:28:41', '2024-02-28 10:28:41', 0, '', 0),
    (124, 123, '', 'Pulsar', '消息队列Pulsar', 1, 1, '', 'pulsar', 1, '', 1, 0, 'file-ppt-fill', '', 'admin', 'admin', '2024-02-28 10:30:37', '2024-02-28 10:30:37', 0, '', 0),
    (125, 124, '', 'PulsarTenant', '租户管理', 2, 1, '@/views/componentManager/pulsar/tenant/index', 'tenant', 1, '', 1, 0, 'shield-user-fill', '', 'admin', 'admin', '2024-02-28 10:35:09', '2024-02-28 10:35:09', 0, '', 0),
    (126, 125, '', 'tenantAdd', '新增租户', 3, 1, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 10:45:47', '2024-02-28 10:45:47', 0, '', 0),
    (127, 125, '', 'tenantDetail', '租户详情', 3, 2, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 10:46:05', '2024-02-28 10:46:05', 0, '', 0),
    (128, 125, '', 'tenantDelete', '删除租户', 3, 3, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 10:46:21', '2024-02-28 10:46:21', 0, '', 0),
    (129, 124, '', 'PulsarTenantDetail', 'Pulsar租户详情', 2, 2, '@/views/componentManager/pulsar/tenant/tenantDetail', 'tenant/tenantDetail', 1, '/componentManager/pulsar/tenant/index', 0, 1, 'shield-user-line', '', 'admin', 'admin', '2024-02-28 11:26:15', '2024-02-28 11:28:23', 0, '', 0),
    (130, 129, '', 'namespaceAdd', '新增命名空间', 3, 1, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 15:07:36', '2024-02-28 15:08:21', 0, '', 0),
    (131, 129, '', 'namespaceEdit', '更新命名空间', 3, 2, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 15:07:59', '2024-02-28 15:07:59', 0, '', 0),
    (132, 129, '', 'namespaceDelete', '删除命名空间', 3, 3, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 15:08:11', '2024-02-28 15:08:11', 0, '', 0),
    (133, 124, '', 'PulsarBroker', 'broker管理', 2, 3, '@/views/componentManager/pulsar/broker/index', 'broker', 1, '', 1, 0, 'server-fill', '', 'admin', 'admin', '2024-02-28 15:53:00', '2024-02-28 15:53:00', 0, '', 0),
    (134, 124, '', 'PulsarCluster', '集群管理', 2, 3, '@/views/componentManager/pulsar/cluster/index', 'cluster', 1, '', 1, 0, 'artboard-2-fill', '', 'admin', 'admin', '2024-02-28 15:57:01', '2024-02-28 15:57:01', 0, '', 0),
    (135, 68, '', 'PulsarMQ', '消息队列Pulsar', 2, 4, '@/views/workspace/pulsar/index', 'pulsar', 1, '', 1, 0, 'file-ppt-2-fill', '', 'admin', 'admin', '2024-02-28 16:12:50', '2024-02-28 16:12:50', 0, '', 0),
    (136, 135, '', 'addPulsarTopic', '新增topic', 3, 1, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 16:15:12', '2024-02-28 16:15:12', 0, '', 0),
    (137, 135, '', 'editPulsarTopic', '编辑topic', 3, 2, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 16:15:31', '2024-02-28 16:15:31', 0, '', 0),
    (138, 135, '', 'pulsarTopicDetail', '详情', 3, 3, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 16:15:46', '2024-02-28 23:36:48', 0, '', 0),
    (139, 135, '', 'pulsarMessage', 'Pulsar消息列表', 3, 4, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-02-28 23:37:42', '2024-02-28 23:38:03', 0, '', 0),
    (140, 68, '', 'PulsarTopicDetailPage', 'PulsarTopic详情', 2, 5, '@/views/workspace/pulsar/topicDetail', 'pulsar/topicDetail', 1, '@/views/workspace/pulsar/index', 0, 1, 'bar-chart-box-line', '', 'admin', 'admin', '2024-03-01 11:31:37', '2024-03-01 11:34:34', 1, '', 0),
    (141, 140, '', 'addPulsarSubscription', '新增Pulsar订阅', 3, 1, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:12:58', '2024-03-01 15:12:58', 0, '', 0),
    (142, 140, '', 'pulsarSubscriptionConsumeProcess', 'Pulsar订阅消费进度', 3, 2, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:13:19', '2024-03-01 15:13:19', 0, '', 0),
    (143, 140, '', 'pulsarSubscriptionOffset', 'Pulsar订阅Offset设置', 3, 3, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:13:41', '2024-03-01 15:13:41', 0, '', 0),
    (144, 140, '', 'editPulsarSubscription', '编辑Pulsar订阅', 3, 4, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:13:57', '2024-03-01 15:13:57', 0, '', 0),
    (145, 140, '', 'deletePulsarSubscription', '删除Pulsar订阅', 3, 5, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:14:11', '2024-03-01 15:14:11', 0, '', 0),
    (146, 140, '', 'pulsarQueryMessage', 'Pulsar消息查询', 3, 6, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:14:40', '2024-03-01 15:14:40', 0, '', 0),
    (147, 140, '', 'pulsarMessageDetail', 'Pulsar消息详情', 3, 7, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:14:57', '2024-03-01 15:14:57', 0, '', 0),
    (148, 71, '', 'initializePulsar', '初始化PulsarToken', 3, 22, '', '', 1, '', 0, 1, '', '', 'admin', 'admin', '2024-03-01 15:21:30', '2024-03-01 15:21:30', 0, '', 0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

--
-- Table structure for table `sys_menu_api`
--

DROP TABLE IF EXISTS `sys_menu_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu_api` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `menu_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单id',
                                `menu_code` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单编码',
                                `api_code` varchar(32) NOT NULL COMMENT '接口编码',
                                `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_menu_api` (`menu_id`,`api_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_api`
--

/*!40000 ALTER TABLE `sys_menu_api` DISABLE KEYS */;
insert into edas-admin.sys_menu_api (id, menu_id, menu_code, api_code, is_available, creator, modifier, created_time, modified_time, version, extension, deleted)
values  (1, 13, 'MenuAuthDetail', 'pageSelectMenuApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:02', '2022-08-16 16:11:23', 0, '', 1),
    (2, 13, 'MenuAuthDetail', 'pageSelectMenuApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (3, 13, 'MenuAuthDetail', 'setSysApiForMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (4, 13, 'MenuAuthDetail', 'cancelSysApiForMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (5, 13, 'MenuAuthDetail', 'pageSelectRoleApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (6, 13, 'MenuAuthDetail', 'setSysApiForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (7, 13, 'MenuAuthDetail', 'cancelSysApiForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (8, 13, 'MenuAuthDetail', 'setSysMenuForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (9, 13, 'MenuAuthDetail', 'cancelSysApiForUser', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (10, 13, 'MenuAuthDetail', 'pageSelectUserApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (11, 13, 'MenuAuthDetail', 'setSysApiForUser', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:13', '2022-08-16 16:11:23', 0, '', 0),
    (12, 13, 'MenuAuthDetail', 'pageSelectUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 12),
    (13, 13, 'MenuAuthDetail', 'setUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 13),
    (14, 13, 'MenuAuthDetail', 'listSelectUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 14),
    (15, 13, 'MenuAuthDetail', 'setUserMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 15),
    (16, 13, 'MenuAuthDetail', 'roleAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 16),
    (17, 13, 'MenuAuthDetail', 'roleUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 17),
    (18, 13, 'MenuAuthDetail', 'roleDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 18),
    (19, 13, 'MenuAuthDetail', 'findRoleById', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 19),
    (20, 13, 'MenuAuthDetail', 'pageRoleList', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 20),
    (21, 13, 'MenuAuthDetail', 'listMenuSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-15 18:26:19', '2022-08-16 16:11:23', 0, '', 21),
    (22, 3, 'SysUser', 'pageUserList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:25:11', '2022-08-16 16:11:23', 0, '', 0),
    (23, 3, 'SysUser', 'userUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:25:35', '2022-08-16 16:11:23', 0, '', 23),
    (24, 3, 'SysUser', 'userAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:25:35', '2022-08-16 16:11:23', 0, '', 24),
    (25, 14, 'userAdd', 'userAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:26:29', '2022-08-16 16:11:23', 0, '', 0),
    (26, 14, 'userAdd', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:26:36', '2022-08-16 16:11:23', 0, '', 0),
    (27, 15, 'userEdit', 'userUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:26:48', '2022-08-16 16:11:23', 0, '', 0),
    (28, 15, 'userEdit', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:26:54', '2022-08-16 16:11:23', 0, '', 0),
    (29, 4, 'SysApi', 'pageSysApiList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:44:40', '2022-08-16 16:11:23', 0, '', 0),
    (30, 18, 'apiAdd', 'findApiById', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:45:11', '2022-08-16 16:11:23', 0, '', 30),
    (31, 18, 'apiAdd', 'apiAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:45:25', '2022-08-16 16:11:23', 0, '', 0),
    (32, 18, 'apiAdd', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:45:34', '2022-08-16 16:11:23', 0, '', 0),
    (33, 19, 'apiEdit', 'apiUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:46:05', '2022-08-16 16:11:23', 0, '', 0),
    (34, 19, 'apiEdit', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:46:16', '2022-08-16 16:11:23', 0, '', 0),
    (35, 20, 'apiDelete', 'apiDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:46:31', '2022-08-16 16:11:23', 0, '', 0),
    (36, 21, 'isOpen', 'changeApiOpen', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:46:49', '2022-08-16 16:11:23', 0, '', 0),
    (37, 22, 'isAuth', 'changeApiAuth', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:47:11', '2022-08-16 16:11:23', 0, '', 0),
    (38, 23, 'isDisabled', 'changeApiStatus', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:47:22', '2022-08-16 16:11:23', 0, '', 0),
    (39, 5, 'SysRole', 'pageRoleList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:48:53', '2022-08-16 16:11:23', 0, '', 0),
    (40, 24, 'roleAdd', 'roleAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:49:10', '2022-08-16 16:11:23', 0, '', 0),
    (41, 25, 'roleEdit', 'roleUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:49:20', '2022-08-16 16:11:23', 0, '', 0),
    (42, 26, 'roleDelete', 'roleDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:49:30', '2022-08-16 16:11:23', 0, '', 0),
    (43, 27, 'setRoleAuth', 'setSysApiForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:49:53', '2022-08-16 16:11:23', 0, '', 0),
    (44, 27, 'setRoleAuth', 'cancelSysApiForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:49:56', '2022-08-16 16:11:23', 0, '', 0),
    (45, 27, 'setRoleAuth', 'pageSelectRoleApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:50:05', '2022-08-16 16:11:23', 0, '', 0),
    (46, 27, 'setRoleAuth', 'setSysMenuForRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:50:06', '2022-08-16 16:11:23', 0, '', 0),
    (47, 25, 'roleEdit', 'findRoleById', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:50:50', '2022-08-16 16:11:23', 0, '', 0),
    (48, 6, 'SysMenu', 'listMenuSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:52:09', '2022-08-16 16:11:23', 0, '', 48),
    (49, 6, 'SysMenu', 'listSysMenuTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:00', '2022-08-16 16:11:23', 0, '', 0),
    (50, 28, 'menuAdd', 'listMenuSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:28', '2022-08-16 16:11:23', 0, '', 0),
    (51, 28, 'menuAdd', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:33', '2022-08-16 16:11:23', 0, '', 0),
    (52, 28, 'menuAdd', 'menuAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:39', '2022-08-16 16:11:23', 0, '', 0),
    (53, 29, 'menuEdit', 'menuUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:56', '2022-08-16 16:11:23', 0, '', 0),
    (54, 29, 'menuEdit', 'listMenuSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:53:58', '2022-08-16 16:11:23', 0, '', 0),
    (55, 29, 'menuEdit', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:54:06', '2022-08-16 16:11:23', 0, '', 0),
    (56, 30, 'menuDelete', 'menuDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:54:17', '2022-08-16 16:11:23', 0, '', 0),
    (57, 31, 'setMenuAuth', 'pageSelectMenuApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:54:41', '2022-08-16 16:11:23', 0, '', 0),
    (58, 31, 'setMenuAuth', 'setSysApiForMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:54:44', '2022-08-16 16:11:23', 0, '', 0),
    (59, 31, 'setMenuAuth', 'cancelSysApiForMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 10:54:46', '2022-08-16 16:11:23', 0, '', 0),
    (60, 4, 'SysApi', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 11:00:57', '2022-08-16 16:11:23', 0, '', 0),
    (61, 28, 'menuAdd', 'listSysMenuButtonPermissionVo', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 16:18:08', '2022-08-16 16:19:07', 0, '', 61),
    (62, 32, 'setUserAuth', 'cancelSysApiForUser', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:20', '2022-08-16 17:28:20', 0, '', 0),
    (63, 32, 'setUserAuth', 'pageSelectUserApi', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:22', '2022-08-16 17:28:22', 0, '', 0),
    (64, 32, 'setUserAuth', 'setSysApiForUser', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:45', '2022-08-16 17:28:45', 0, '', 0),
    (65, 32, 'setUserAuth', 'pageSelectUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:45', '2022-08-16 17:28:45', 0, '', 0),
    (66, 32, 'setUserAuth', 'setUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:45', '2022-08-16 17:28:45', 0, '', 0),
    (67, 32, 'setUserAuth', 'listSelectUserRole', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:45', '2022-08-16 17:28:45', 0, '', 0),
    (68, 32, 'setUserAuth', 'setUserMenu', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:28:45', '2022-08-16 17:28:45', 0, '', 0),
    (69, 16, 'userDelete', 'deleteUser', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:40:43', '2022-08-16 17:40:43', 0, '', 0),
    (70, 16, 'userDelete', 'cs', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 17:40:47', '2022-08-16 18:50:56', 0, '', 70),
    (71, 21, 'isOpen', 'cs', 1, 'SYSTEM', 'SYSTEM', '2022-08-16 19:41:55', '2022-08-16 19:42:12', 0, '', 71),
    (72, 34, 'SysDept', 'listSysDeptTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:31:32', '2022-08-18 15:31:32', 0, '', 0),
    (73, 35, 'deptAdd', 'deptAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:31:52', '2022-08-18 15:31:52', 0, '', 0),
    (74, 35, 'deptAdd', 'listDeptSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:31:52', '2022-08-18 15:31:52', 0, '', 0),
    (75, 36, 'deptEdit', 'deptUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:32:05', '2022-08-18 15:32:05', 0, '', 0),
    (76, 36, 'deptEdit', 'listDeptSelectTree', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:32:05', '2022-08-18 15:32:05', 0, '', 0),
    (77, 36, 'deptEdit', 'findDeptById', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:32:10', '2022-08-18 15:32:10', 0, '', 0),
    (78, 37, 'deptDelete', 'deptDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 15:32:18', '2022-08-18 15:32:18', 0, '', 0),
    (79, 40, 'SysDict', 'pageSysDictList', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 18:36:50', '2022-08-18 18:36:50', 0, '', 0),
    (80, 41, 'dictAdd', 'dictAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 18:37:02', '2022-08-18 18:37:02', 0, '', 0),
    (81, 42, 'dictEdit', 'dictUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 18:37:11', '2022-08-18 18:37:11', 0, '', 0),
    (82, 43, 'dictDelete', 'dictDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 18:37:22', '2022-08-18 18:37:22', 0, '', 0),
    (83, 42, 'dictEdit', 'findDictById', 1, 'SYSTEM', 'SYSTEM', '2022-08-18 19:02:08', '2022-08-18 19:02:08', 0, '', 0),
    (84, 45, 'SysDictDetail', 'pageDictDetailList', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:05', '2022-08-19 18:23:05', 0, '', 0),
    (85, 47, 'dictDetailAdd', 'dictDetailAdd', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:17', '2022-08-19 18:23:17', 0, '', 0),
    (86, 47, 'dictDetailAdd', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:28', '2022-08-19 18:23:28', 0, '', 0),
    (87, 48, 'dictDetailEdit', 'dictDetailUpdate', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:44', '2022-08-19 18:23:44', 0, '', 0),
    (88, 48, 'dictDetailEdit', 'findDictDetailById', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:46', '2022-08-19 18:23:46', 0, '', 0),
    (89, 48, 'dictDetailEdit', 'selectDictLabelList', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:23:51', '2022-08-19 18:23:51', 0, '', 0),
    (90, 49, 'dictDetailDelete', 'dictDetailDelete', 1, 'SYSTEM', 'SYSTEM', '2022-08-19 18:24:00', '2022-08-19 18:24:00', 0, '', 0),
    (91, 69, 'myApp', 'pageMyAppList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:20:09', '2024-01-29 09:20:09', 0, '', 0),
    (92, 71, 'appDetail', 'findAppByAppId', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:23:10', '2024-01-29 09:23:10', 0, '', 0),
    (93, 71, 'appDetail', 'findDevopsConfigByAppid', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:25:36', '2024-01-29 09:25:36', 0, '', 0),
    (94, 71, 'appDetail', 'appResourcePage', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:26:41', '2024-01-29 09:26:41', 0, '', 0),
    (96, 71, 'appDetail', 'getNamespaceSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:28:58', '2024-01-29 10:07:32', 0, '', 96),
    (97, 71, 'appDetail', 'addNacosConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:29:46', '2024-01-29 09:33:15', 0, '', 97),
    (98, 71, 'appDetail', 'updateNacosConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:29:48', '2024-01-29 09:33:31', 0, '', 98),
    (99, 71, 'appDetail', 'deleteNacosConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:29:50', '2024-01-29 09:33:18', 0, '', 99),
    (100, 71, 'appDetail', 'findNacosConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:29:52', '2024-01-29 10:07:26', 0, '', 100),
    (101, 71, 'appDetail', 'pageNacosConfigList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:29:56', '2024-01-29 09:29:56', 0, '', 0),
    (102, 71, 'appDetail', 'pageNacosHistoryConfigList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:30:01', '2024-01-29 10:07:41', 0, '', 102),
    (103, 71, 'appDetail', 'findNacosHistoryConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:30:03', '2024-01-29 10:07:30', 0, '', 103),
    (104, 71, 'appDetail', 'nacosConfigRollback', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:30:11', '2024-01-29 09:33:27', 0, '', 104),
    (105, 77, 'develop', 'addDevopsJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:42:48', '2024-01-29 09:42:48', 0, '', 0),
    (106, 77, 'develop', 'getBranchSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:45:17', '2024-01-29 09:45:17', 0, '', 0),
    (107, 78, 'developJobHistory', 'pageDevlopsJobList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:47:52', '2024-01-29 09:47:52', 0, '', 0),
    (108, 79, 'addNacosConfig', 'getNamespaceSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 09:50:20', '2024-01-29 09:50:20', 0, '', 0),
    (109, 79, 'addNacosConfig', 'addNacosConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:05:07', '2024-01-29 10:05:07', 0, '', 0),
    (110, 79, 'addNacosConfig', 'updateNacosConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:05:08', '2024-01-29 10:05:23', 0, '', 110),
    (111, 80, 'updateNacosConfig', 'getNamespaceSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:06:08', '2024-01-29 10:06:08', 0, '', 0),
    (112, 80, 'updateNacosConfig', 'updateNacosConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:06:11', '2024-01-29 10:06:11', 0, '', 0),
    (113, 84, 'deleteNacosConfig', 'deleteNacosConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:07:04', '2024-01-29 10:07:04', 0, '', 0),
    (114, 71, 'appDetail', 'findRmqConfigByAppId', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:08:32', '2024-01-29 10:08:32', 0, '', 0),
    (115, 81, 'nacosHistory', 'pageNacosHistoryConfigList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:12:07', '2024-01-29 10:12:07', 0, '', 0),
    (116, 85, 'viewNacosHistoryConfig', 'findNacosHistoryConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:13:12', '2024-01-29 10:13:12', 0, '', 0),
    (117, 85, 'viewNacosHistoryConfig', 'nacosConfigRollback', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:13:30', '2024-01-29 10:13:30', 0, '', 0),
    (118, 86, 'initializeRocketMQ', 'addRmqConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:15:31', '2024-01-29 10:15:31', 0, '', 0),
    (120, 87, 'openPowerJob', 'addPowerJobAppInfo', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:17:29', '2024-01-29 10:17:29', 0, '', 0),
    (121, 71, 'appDetail', 'assertPowerJobAppInfo', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:17:42', '2024-01-29 10:17:42', 0, '', 0),
    (122, 71, 'appDetail', 'pagePowerJobList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:17:47', '2024-01-29 10:17:47', 0, '', 0),
    (123, 88, 'addPowerJob', 'addPowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:21:48', '2024-01-29 10:21:48', 0, '', 0),
    (124, 88, 'addPowerJob', 'validateTimeExpression', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:21:56', '2024-01-29 10:21:56', 0, '', 0),
    (125, 89, 'updatePowerJob', 'validateTimeExpression', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:22:24', '2024-01-29 10:22:24', 0, '', 0),
    (126, 89, 'updatePowerJob', 'updatePowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:22:31', '2024-01-29 10:22:31', 0, '', 0),
    (127, 90, 'viewInstance', 'pagePowerJobInstanceList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:27:48', '2024-01-29 10:27:48', 0, '', 0),
    (128, 91, 'runOncePowerJob', 'enablePowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:28:21', '2024-01-29 10:28:21', 0, '', 0),
    (129, 92, 'enablePowerJob', 'enablePowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:28:52', '2024-01-29 10:28:52', 0, '', 0),
    (130, 93, 'disablePowerJob', 'disablePowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:29:06', '2024-01-29 10:29:06', 0, '', 0),
    (131, 94, 'deletePowerJob', 'deletePowerJob', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:30:01', '2024-01-29 10:30:01', 0, '', 0),
    (132, 95, 'viewPowerJobInstanceDetail', 'queryPowerJobInstanceInfo', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:30:34', '2024-01-29 10:30:34', 0, '', 0),
    (133, 96, 'retryPowerJobInstance', 'retryPowerJobInstance', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:31:35', '2024-01-29 10:31:35', 0, '', 0),
    (134, 97, 'stopPowerJobInstance', 'stopPowerJobInstance', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:31:52', '2024-01-29 10:31:52', 0, '', 0),
    (135, 98, 'cancelPowerJobInstance', 'cancalPowerJobInstance', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:32:07', '2024-01-29 10:32:07', 0, '', 0),
    (136, 74, 'RocketMQ', 'pageTopicList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:35:35', '2024-01-29 10:35:35', 0, '', 0),
    (137, 99, 'addRocketMQTopic', 'addTopic', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:40:34', '2024-01-29 10:40:34', 0, '', 0),
    (138, 99, 'addRocketMQTopic', 'getSelectBrokerNameList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:44:12', '2024-01-29 10:44:12', 0, '', 0),
    (139, 99, 'addRocketMQTopic', 'getMyAppSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:45:28', '2024-01-29 10:45:28', 0, '', 0),
    (140, 100, 'updateRocketMqTopic', 'getSelectBrokerNameList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:46:05', '2024-01-29 10:46:05', 0, '', 0),
    (141, 100, 'updateRocketMqTopic', 'getMyAppSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:46:18', '2024-01-29 10:46:18', 0, '', 0),
    (142, 100, 'updateRocketMqTopic', 'updateSubscribeById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:46:29', '2024-01-29 10:46:29', 0, '', 0),
    (143, 101, 'subscriptionRocketMqTopic', 'pageSubscribeList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:50:14', '2024-01-29 10:50:14', 0, '', 0),
    (144, 102, 'viewRocketMqMessage', 'pageMessageList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 10:50:28', '2024-01-29 10:50:28', 0, '', 0),
    (145, 75, 'RocketMqSubscription', 'pageSubscribeList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:05:09', '2024-01-29 11:05:09', 0, '', 0),
    (146, 103, 'addRocketMqSubscription', 'getSelectBrokerNameList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:05:50', '2024-01-29 11:05:50', 0, '', 0),
    (147, 103, 'addRocketMqSubscription', 'addSubscribe', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:06:05', '2024-01-29 11:06:05', 0, '', 0),
    (148, 103, 'addRocketMqSubscription', 'getAppSelectListByUserId', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:06:20', '2024-01-29 11:06:20', 0, '', 0),
    (149, 103, 'addRocketMqSubscription', 'findAppByAppId', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:06:37', '2024-01-29 11:06:37', 0, '', 0),
    (150, 104, 'updateRocketMqSubscription', 'getSelectBrokerNameList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:06:55', '2024-01-29 11:06:55', 0, '', 0),
    (151, 104, 'updateRocketMqSubscription', 'findSubscribeById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:07:16', '2024-01-29 11:07:16', 0, '', 0),
    (152, 104, 'updateRocketMqSubscription', 'updateSubscribeById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:07:19', '2024-01-29 11:07:19', 0, '', 0),
    (153, 105, 'viewRocketMQSubscriptionDetail', 'findSubscribeGroupConsumeDetail', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:08:16', '2024-01-29 11:08:16', 0, '', 0),
    (154, 76, 'MessageList', 'pageMessageList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:10:41', '2024-01-29 11:10:41', 0, '', 0),
    (155, 107, 'viewRocketMqMessageDetail', 'findMessageById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:12:33', '2024-01-29 11:12:33', 0, '', 0),
    (156, 108, 'consumeDirectly', 'getSelectSubscribeList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:12:59', '2024-01-29 11:12:59', 0, '', 0),
    (157, 108, 'consumeDirectly', 'consumeMessageDirectly', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:13:20', '2024-01-29 11:13:20', 0, '', 0),
    (158, 109, 'subscribeResetOffset', 'resetSubscribeOffset', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:17:45', '2024-01-29 11:17:45', 0, '', 0),
    (159, 70, 'gateway', 'pageGatewayApiList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:23:17', '2024-01-29 11:23:17', 0, '', 0),
    (160, 110, 'gatewayApiAdd', 'addGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:31:05', '2024-01-29 11:31:05', 0, '', 0),
    (161, 111, 'gatewatApiEdit', 'findGatewayApiById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:31:22', '2024-01-29 11:31:22', 0, '', 0),
    (162, 111, 'gatewatApiEdit', 'updateGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:31:23', '2024-01-29 11:31:23', 0, '', 0),
    (163, 110, 'gatewayApiAdd', 'selectGatewayApiMetadataList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:32:45', '2024-01-29 11:32:45', 0, '', 0),
    (164, 110, 'gatewayApiAdd', 'getMyAppSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:32:51', '2024-01-29 11:32:51', 0, '', 0),
    (165, 111, 'gatewatApiEdit', 'selectGatewayApiMetadataList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:33:32', '2024-01-29 11:33:32', 0, '', 0),
    (166, 111, 'gatewatApiEdit', 'getMyAppSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:33:36', '2024-01-29 11:33:36', 0, '', 0),
    (167, 112, 'gatewayApiOnline', 'offlineGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:12', '2024-01-29 11:34:12', 0, '', 0),
    (168, 112, 'gatewayApiOnline', 'onlineGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:14', '2024-01-29 11:34:14', 0, '', 0),
    (169, 113, 'gatewayApiOffline', 'offlineGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:23', '2024-01-29 11:34:23', 0, '', 0),
    (170, 113, 'gatewayApiOffline', 'onlineGatewayApi', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:24', '2024-01-29 11:34:24', 0, '', 0),
    (171, 114, 'gatewayApiDelete', 'deleteGatewayApiById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:34', '2024-01-29 11:34:34', 0, '', 0),
    (172, 115, 'gatewayAuth', 'changeGatewayApiAuth', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 11:34:44', '2024-01-29 11:34:44', 0, '', 0),
    (173, 116, 'appParkAdd', 'appAdd', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:53:00', '2024-01-29 14:53:00', 0, '', 0),
    (174, 117, 'appConfig', 'findDevopsConfigByAppid', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:55:58', '2024-01-29 14:55:58', 0, '', 0),
    (175, 117, 'appConfig', 'addDevopsConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:56:23', '2024-01-29 14:56:23', 0, '', 0),
    (176, 117, 'appConfig', 'updateDevopsConfig', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:56:31', '2024-01-29 14:56:31', 0, '', 0),
    (177, 117, 'appConfig', 'getGitlabProjectSelectList', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:56:55', '2024-01-29 14:56:55', 0, '', 0),
    (178, 118, 'appResourceView', 'appResourcePage', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:57:47', '2024-01-29 14:57:47', 0, '', 0),
    (179, 118, 'appResourceView', 'deleteAppResourceById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:57:54', '2024-01-29 14:58:01', 0, '', 179),
    (180, 119, 'appParkDelete', 'deleteAppById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:58:23', '2024-01-29 14:58:23', 0, '', 0),
    (181, 120, 'appResourceAdd', 'appResourceAdd', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 14:59:50', '2024-01-29 14:59:50', 0, '', 0),
    (182, 121, 'appResourceUpdate', 'appResourceUpdate', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 15:00:12', '2024-01-29 15:00:12', 0, '', 0),
    (183, 121, 'appResourceUpdate', 'findAppResourceById', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 15:00:14', '2024-01-29 15:00:14', 0, '', 0),
    (184, 122, 'appResourceDelete', 'appResourceUpdate', 1, 'SYSTEM', 'SYSTEM', '2024-01-29 15:00:34', '2024-01-29 15:00:34', 0, '', 0),
    (185, 80, 'updateNacosConfig', 'findNacosConfigById', 1, 'SYSTEM', 'SYSTEM', '2024-01-30 15:37:11', '2024-01-30 15:37:11', 0, '', 0),
    (186, 39, 'personalCenter', 'findUserPersonalInfo', 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:03:28', '2024-02-02 11:03:28', 0, '', 0),
    (187, 125, 'PulsarTenant', 'pagePulsarTenantList', 1, 'admin', 'admin', '2024-03-07 09:42:14', '2024-03-07 09:42:14', 0, '', 0),
    (188, 125, 'PulsarTenant', 'deletePulsarTenantById', 1, 'admin', 'admin', '2024-03-07 09:42:19', '2024-03-07 10:27:51', 0, '', 188),
    (189, 126, 'tenantAdd', 'getPulsarClusterList', 1, 'admin', 'admin', '2024-03-07 10:25:30', '2024-03-07 10:25:30', 0, '', 0),
    (190, 126, 'tenantAdd', 'addPulsarTenant', 1, 'admin', 'admin', '2024-03-07 10:25:39', '2024-03-07 10:25:39', 0, '', 0),
    (191, 128, 'tenantDelete', 'deletePulsarTenantById', 1, 'admin', 'admin', '2024-03-07 10:27:38', '2024-03-07 10:27:38', 0, '', 0),
    (192, 129, 'PulsarTenantDetail', 'findPulsarTenantById', 1, 'admin', 'admin', '2024-03-07 10:31:51', '2024-03-07 10:31:51', 0, '', 0),
    (193, 129, 'PulsarTenantDetail', 'pagePulsarNamespaceList', 1, 'admin', 'admin', '2024-03-07 10:32:31', '2024-03-07 10:32:31', 0, '', 0),
    (194, 130, 'namespaceAdd', 'addPulsarNamespace', 1, 'admin', 'admin', '2024-03-07 10:32:49', '2024-03-07 10:32:49', 0, '', 0),
    (195, 131, 'namespaceEdit', 'updatePulsarNamespace', 1, 'admin', 'admin', '2024-03-07 10:33:07', '2024-03-07 10:33:07', 0, '', 0),
    (196, 131, 'namespaceEdit', 'findPulsarNamespaceById', 1, 'admin', 'admin', '2024-03-07 10:33:10', '2024-03-07 10:33:10', 0, '', 0),
    (197, 132, 'namespaceDelete', 'deletePulsarNamespaceByid', 1, 'admin', 'admin', '2024-03-07 10:36:50', '2024-03-07 10:36:50', 0, '', 0),
    (198, 133, 'PulsarBroker', 'getPulsarBrokerList', 1, 'admin', 'admin', '2024-03-07 10:37:43', '2024-03-07 10:37:43', 0, '', 0),
    (199, 133, 'PulsarBroker', 'getPulsarClusterList', 1, 'admin', 'admin', '2024-03-07 10:37:59', '2024-03-07 10:37:59', 0, '', 0),
    (200, 134, 'PulsarCluster', 'getPulsarClusterList', 1, 'admin', 'admin', '2024-03-07 11:00:18', '2024-03-07 11:00:18', 0, '', 0),
    (201, 135, 'PulsarMQ', 'pagePulsarTopicList', 1, 'admin', 'admin', '2024-03-07 11:01:15', '2024-03-07 11:01:15', 0, '', 0),
    (202, 136, 'addPulsarTopic', 'addPulsarTopic', 1, 'admin', 'admin', '2024-03-07 11:02:43', '2024-03-07 11:02:43', 0, '', 0),
    (203, 136, 'addPulsarTopic', 'getPulsarTenantSelectList', 1, 'admin', 'admin', '2024-03-07 11:03:14', '2024-03-07 11:03:14', 0, '', 0),
    (204, 136, 'addPulsarTopic', 'getMyAppSelectList', 1, 'admin', 'admin', '2024-03-07 11:03:28', '2024-03-07 11:03:28', 0, '', 0),
    (205, 136, 'addPulsarTopic', 'findAppByAppId', 1, 'admin', 'admin', '2024-03-07 11:03:33', '2024-03-07 11:03:33', 0, '', 0),
    (206, 137, 'editPulsarTopic', 'updatePulsarTopic', 1, 'admin', 'admin', '2024-03-07 11:04:25', '2024-03-07 11:04:25', 0, '', 0),
    (207, 137, 'editPulsarTopic', 'findPulsarTopicById', 1, 'admin', 'admin', '2024-03-07 11:04:27', '2024-03-07 11:04:27', 0, '', 0),
    (208, 138, 'pulsarTopicDetail', 'findPulsarTopicDetail', 1, 'admin', 'admin', '2024-03-07 11:05:57', '2024-03-07 11:05:57', 0, '', 0),
    (209, 138, 'pulsarTopicDetail', 'listPulsarTopicProduces', 1, 'admin', 'admin', '2024-03-07 11:09:00', '2024-03-07 11:09:00', 0, '', 0),
    (210, 138, 'pulsarTopicDetail', 'pagePulsarSubscriptionList', 1, 'admin', 'admin', '2024-03-07 11:09:07', '2024-03-07 11:09:07', 0, '', 0),
    (211, 141, 'addPulsarSubscription', 'addPulsarSubscription', 1, 'admin', 'admin', '2024-03-07 11:10:04', '2024-03-07 11:10:04', 0, '', 0),
    (212, 141, 'addPulsarSubscription', 'findPulsarTopicById', 1, 'admin', 'admin', '2024-03-07 11:10:20', '2024-03-07 11:10:20', 0, '', 0),
    (213, 141, 'addPulsarSubscription', 'findAppByAppId', 1, 'admin', 'admin', '2024-03-07 11:10:34', '2024-03-07 11:10:34', 0, '', 0),
    (214, 141, 'addPulsarSubscription', 'getAppSelectListByUserId', 1, 'admin', 'admin', '2024-03-07 11:10:40', '2024-03-07 11:10:40', 0, '', 0),
    (215, 142, 'pulsarSubscriptionConsumeProcess', 'listPulsarConsumeProcess', 1, 'admin', 'admin', '2024-03-07 11:11:54', '2024-03-07 11:11:54', 0, '', 0),
    (216, 143, 'pulsarSubscriptionOffset', 'offsetPulsar', 1, 'admin', 'admin', '2024-03-07 11:12:17', '2024-03-07 11:12:17', 0, '', 0),
    (217, 144, 'editPulsarSubscription', 'updatePulsarSubscription', 1, 'admin', 'admin', '2024-03-07 11:12:33', '2024-03-07 11:12:33', 0, '', 0),
    (218, 144, 'editPulsarSubscription', 'findPulsarSubscriptionById', 1, 'admin', 'admin', '2024-03-07 11:12:39', '2024-03-07 11:12:39', 0, '', 0),
    (219, 145, 'deletePulsarSubscription', 'deletePulsarSubscriptionById', 1, 'admin', 'admin', '2024-03-07 11:31:38', '2024-03-07 11:31:38', 0, '', 0),
    (220, 146, 'pulsarQueryMessage', 'pagePulsarMessageList', 1, 'admin', 'admin', '2024-03-07 11:32:13', '2024-03-07 11:32:13', 0, '', 0),
    (221, 147, 'pulsarMessageDetail', 'findPulsarMessageDetail', 1, 'admin', 'admin', '2024-03-07 11:32:27', '2024-03-07 11:32:27', 0, '', 0);
/*!40000 ALTER TABLE `sys_menu_api` ENABLE KEYS */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `role_code` varchar(16) NOT NULL DEFAULT '' COMMENT '角色编码',
                            `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
                            `data_scope` tinyint NOT NULL DEFAULT '0' COMMENT '数据权限类型',
                            `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
                            `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                            `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                            `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                            `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                            `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                            `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'developer','开发人员',0,'','SYSTEM','SYSTEM','2024-01-30 01:21:42','2024-01-30 01:21:42',0,'',0);
INSERT INTO `sys_role` VALUES (2,'tester','测试人员',0,'','SYSTEM','SYSTEM','2024-01-31 07:07:44','2024-01-31 07:07:44',0,'',0);
INSERT INTO `sys_role` VALUES (3,'projectManager','项目管理员',0,'','SYSTEM','SYSTEM','2024-02-01 02:54:23','2024-02-01 02:54:23',0,'',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

--
-- Table structure for table `sys_role_api`
--

DROP TABLE IF EXISTS `sys_role_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_api` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `role_code` varchar(32) NOT NULL DEFAULT '' COMMENT '角色编码',
                                `api_code` varchar(32) NOT NULL COMMENT '接口编码',
                                `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_role_api` (`role_code`,`api_code`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_api`
--

/*!40000 ALTER TABLE `sys_role_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_api` ENABLE KEYS */;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                 `role_code` varchar(32) NOT NULL DEFAULT '' COMMENT '角色编码',
                                 `menu_id` bigint NOT NULL COMMENT '菜单id',
                                 `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                 `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                 `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                 `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                 `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                 `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                 `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                 `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uniq_role_menu` (`role_code`,`menu_id`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

insert into edas-admin.sys_role_menu (id, role_code, menu_id, is_available, creator, modifier, created_time, modified_time, version, extension, deleted)
values  (175, 'tester', 1, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (176, 'tester', 39, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (177, 'tester', 68, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (178, 'tester', 69, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (179, 'tester', 71, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (180, 'tester', 77, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (181, 'tester', 78, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (182, 'tester', 82, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (183, 'tester', 83, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:32', '2024-02-02 11:02:32', 0, '', 0),
    (184, 'projectManager', 1, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (185, 'projectManager', 39, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (186, 'projectManager', 68, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (187, 'projectManager', 69, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (188, 'projectManager', 70, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (189, 'projectManager', 110, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (190, 'projectManager', 111, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (191, 'projectManager', 112, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (192, 'projectManager', 113, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (193, 'projectManager', 114, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (194, 'projectManager', 115, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (195, 'projectManager', 71, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (196, 'projectManager', 77, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (197, 'projectManager', 78, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (198, 'projectManager', 79, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (199, 'projectManager', 80, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (200, 'projectManager', 81, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (201, 'projectManager', 82, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (202, 'projectManager', 83, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (203, 'projectManager', 84, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (204, 'projectManager', 85, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (205, 'projectManager', 86, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (206, 'projectManager', 87, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (207, 'projectManager', 88, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (208, 'projectManager', 89, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (209, 'projectManager', 90, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (210, 'projectManager', 91, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (211, 'projectManager', 92, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (212, 'projectManager', 93, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (213, 'projectManager', 94, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (214, 'projectManager', 95, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (215, 'projectManager', 96, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (216, 'projectManager', 97, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (217, 'projectManager', 98, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (218, 'projectManager', 74, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (219, 'projectManager', 99, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (220, 'projectManager', 100, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (221, 'projectManager', 101, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (222, 'projectManager', 102, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (223, 'projectManager', 75, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (224, 'projectManager', 103, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (225, 'projectManager', 104, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (226, 'projectManager', 105, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (227, 'projectManager', 106, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (228, 'projectManager', 109, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (229, 'projectManager', 76, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (230, 'projectManager', 107, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (231, 'projectManager', 108, 1, 'SYSTEM', 'SYSTEM', '2024-02-02 11:02:43', '2024-02-02 11:02:43', 0, '', 0),
    (287, 'developer', 1, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (288, 'developer', 39, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (289, 'developer', 68, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (290, 'developer', 69, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (291, 'developer', 70, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (292, 'developer', 110, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (293, 'developer', 111, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (294, 'developer', 112, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (295, 'developer', 113, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (296, 'developer', 114, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (297, 'developer', 115, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (298, 'developer', 71, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (299, 'developer', 77, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (300, 'developer', 78, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (301, 'developer', 79, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (302, 'developer', 80, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (303, 'developer', 81, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (304, 'developer', 82, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (305, 'developer', 83, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (306, 'developer', 85, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (307, 'developer', 88, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (308, 'developer', 89, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (309, 'developer', 90, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (310, 'developer', 91, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (311, 'developer', 92, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (312, 'developer', 93, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (313, 'developer', 94, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (314, 'developer', 95, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (315, 'developer', 96, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (316, 'developer', 97, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (317, 'developer', 98, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (318, 'developer', 74, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (319, 'developer', 99, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (320, 'developer', 100, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (321, 'developer', 101, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (322, 'developer', 102, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (323, 'developer', 75, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (324, 'developer', 103, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (325, 'developer', 104, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (326, 'developer', 105, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (327, 'developer', 106, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (328, 'developer', 109, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (329, 'developer', 76, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (330, 'developer', 107, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (331, 'developer', 108, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (332, 'developer', 135, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (333, 'developer', 136, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (334, 'developer', 137, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (335, 'developer', 138, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (336, 'developer', 139, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (337, 'developer', 140, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (338, 'developer', 141, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (339, 'developer', 142, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (340, 'developer', 143, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (341, 'developer', 144, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (342, 'developer', 145, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (343, 'developer', 146, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0),
    (344, 'developer', 147, 1, 'admin', 'admin', '2024-03-07 11:32:51', '2024-03-07 11:32:51', 0, '', 0);

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `user_id` bigint NOT NULL COMMENT '用户id',
                            `name` varchar(32) NOT NULL COMMENT '用户姓名',
                            `account` varchar(20) NOT NULL DEFAULT '' COMMENT '登录账号',
                            `nick_name` varchar(16) NOT NULL DEFAULT '' COMMENT '昵称',
                            `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户id',
                            `dept_code` varchar(16) NOT NULL DEFAULT '' COMMENT '部门编码',
                            `data_scope` tinyint NOT NULL DEFAULT '0' COMMENT '数据权限类型',
                            `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
                            `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
                            `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
                            `sex` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '性别(1: 男, 2: 女)',
                            `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用于头像路径',
                            `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '账号状态(1:正常,2:停用,3:注销)',
                            `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                            `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                            `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                            `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                            `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                            `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `unique_user_id` (`user_id`,`deleted`) COMMENT '账号唯一索引',
                            UNIQUE KEY `unique_account` (`account`,`deleted`) COMMENT '账号唯一索引',
                            UNIQUE KEY `unique_email` (`email`,`deleted`) COMMENT '邮箱唯一索引',
                            UNIQUE KEY `unique_phone` (`phone`,`deleted`) COMMENT '手机号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,100000,'admin','17856941755','超级管理员66','','hefeiTechnical',1,'$2a$10$40OlRZfuPEFnE1HuoszQ4udPU069O4QI6KNmRltQzST6tXzrzxY5W','17856941755','michaelkai@aliyun.com',1,'https://i.gtimg.cn/club/item/face/img/3/15643_100.gif',1,'1','SYSTEM','2023-12-01 09:04:36','2024-02-02 03:07:20',1,'',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

--
-- Table structure for table `sys_user_api`
--

DROP TABLE IF EXISTS `sys_user_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_api` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
                                `api_code` varchar(32) NOT NULL COMMENT '接口编码',
                                `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_user_api` (`user_id`,`api_code`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_api`
--

/*!40000 ALTER TABLE `sys_user_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_api` ENABLE KEYS */;

--
-- Table structure for table `sys_user_app_scope`
--

DROP TABLE IF EXISTS `sys_user_app_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_app_scope` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                      `user_id` bigint NOT NULL DEFAULT '0' COMMENT 'appId',
                                      `app_id` bigint NOT NULL DEFAULT '0' COMMENT 'appId',
                                      `app_code` varchar(64) NOT NULL DEFAULT '' COMMENT 'app编码',
                                      `env` varchar(8) NOT NULL DEFAULT 'dev' COMMENT '环境变量',
                                      `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
                                      `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                      `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                      `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                      `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                      `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                      `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                      `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uniq_user_app` (`user_id`,`app_id`,`app_code`,`env`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户app作用域';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_app_scope`
--

--
-- Table structure for table `sys_user_intro`
--

DROP TABLE IF EXISTS `sys_user_intro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_intro` (
                                  `id` bigint NOT NULL COMMENT '主键id',
                                  `user_id` bigint NOT NULL COMMENT '用户id',
                                  `introduction` varchar(500) NOT NULL DEFAULT '' COMMENT '个人简介',
                                  `tags` varchar(500) NOT NULL DEFAULT '' COMMENT '标签',
                                  `location` varchar(500) NOT NULL DEFAULT '' COMMENT '位置',
                                  `birth_day` datetime DEFAULT NULL COMMENT '生日',
                                  `skills` varchar(500) NOT NULL DEFAULT '' COMMENT '技能',
                                  `hobby` varchar(500) NOT NULL DEFAULT '' COMMENT '爱好',
                                  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                  `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uniq_user_id` (`user_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户简介表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_intro`
--

/*!40000 ALTER TABLE `sys_user_intro` DISABLE KEYS */;
INSERT INTO `sys_user_intro` VALUES (1753237927585316865,100000,'牛逼','技术大牛,Java开发','安徽合肥','1995-12-31 11:14:28','','','SYSTEM','SYSTEM','2024-02-02 02:04:30','2024-02-03 08:56:38',9,'',0);
/*!40000 ALTER TABLE `sys_user_intro` ENABLE KEYS */;

--
-- Table structure for table `sys_user_menu`
--

DROP TABLE IF EXISTS `sys_user_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_menu` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                 `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
                                 `menu_id` bigint NOT NULL COMMENT '菜单id',
                                 `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                 `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                 `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                 `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                 `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                 `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                 `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                 `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uniq_user_menu` (`user_id`,`menu_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_menu`
--

/*!40000 ALTER TABLE `sys_user_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_menu` ENABLE KEYS */;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                 `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
                                 `role_code` varchar(32) NOT NULL COMMENT '角色编码',
                                 `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
                                 `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
                                 `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
                                 `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
                                 `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
                                 `version` bigint NOT NULL DEFAULT '0' COMMENT '行版本号',
                                 `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
                                 `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uniq_user_role` (`user_id`,`role_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-04 10:17:14
