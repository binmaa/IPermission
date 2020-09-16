-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: localhost    Database: IPermission
-- ------------------------------------------------------
-- Server version	5.6.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_acl`
--

DROP TABLE IF EXISTS `sys_acl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块id',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '拦截的url',
  `type` int(11) NOT NULL DEFAULT '3' COMMENT '权限类型 1:菜单 2:按钮 3：其他',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限在当前模块顺序',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常 0:冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl`
--

LOCK TABLES `sys_acl` WRITE;
/*!40000 ALTER TABLE `sys_acl` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_acl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_module`
--

DROP TABLE IF EXISTS `sys_acl_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '层级名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限模块id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级顺序',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常 0:停用',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_module`
--

LOCK TABLES `sys_acl_module` WRITE;
/*!40000 ALTER TABLE `sys_acl_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_acl_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级部门id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级顺序',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (10,'根部门',0,'0',1,'','system-save','2020-08-22 14:51:20','127.0.0.1'),(11,'节点',10,'0.10',1,'','system-update','2020-08-22 17:13:15','127.0.0.1'),(12,'子节点',11,'0.10.11',1,'','system-update','2020-08-22 18:29:40','127.0.0.1'),(13,'节点2',10,'0.10',1,'','system-update','2020-08-22 18:29:51','127.0.0.1');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型 1:部门 2：用户 3：权限模块 4：权限 5：角色 6：角色用户关系 7：角色权限关系',
  `target_id` varchar(20) NOT NULL DEFAULT '' COMMENT '基于type对象的id 如用户 权限 角色表的id',
  `old_value` text NOT NULL COMMENT '更新前的值',
  `new_value` text NOT NULL COMMENT '更新的值',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 1：复原过 0:没有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '角色类型 1:管理员角色 2:其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:可用 0:冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_acl`
--

DROP TABLE IF EXISTS `sys_role_acl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `acl_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_acl`
--

LOCK TABLES `sys_role_acl` WRITE;
/*!40000 ALTER TABLE `sys_role_acl` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_acl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_test`
--

DROP TABLE IF EXISTS `sys_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_test` (
  `name` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_test`
--

LOCK TABLES `sys_test` WRITE;
/*!40000 ALTER TABLE `sys_test` DISABLE KEYS */;
INSERT INTO `sys_test` VALUES ('马斌',1,26);
/*!40000 ALTER TABLE `sys_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `telephone` varchar(13) NOT NULL DEFAULT '' COMMENT '电话',
  `mail` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '密码',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属部门id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态 1:正常 0:冻结 2:删除',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'管理员','15888888888','admin@qq.com','E10ADC3949BA59ABBE56E057F20F883E',NULL,11,1,'system-save','2020-09-11 22:27:04','127.0.0.1');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-17  7:49:54
