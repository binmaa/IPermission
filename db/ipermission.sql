-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: localhost    Database: ipermission
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl`
--

LOCK TABLES `sys_acl` WRITE;
/*!40000 ALTER TABLE `sys_acl` DISABLE KEYS */;
INSERT INTO `sys_acl` VALUES (1,'202012201550020','redis权限页面',1,'/sys/redis.page',1,1,1,'备注','马斌','2020-12-20 15:50:03','0:0:0:0:0:0:0:1'),(2,'202012201606280','redis权限列表',1,'/sys/redis.json',1,2,1,'','马斌','2020-12-20 16:06:28','0:0:0:0:0:0:0:1'),(3,'202012201617450','redis权限启动',1,'/sys/redis/on.json',1,3,1,'','马斌','2020-12-20 16:17:45','0:0:0:0:0:0:0:1'),(4,'202012201618580','redis权限停止',1,'/sys/redis/off.json',2,4,1,'','马斌','2020-12-20 16:18:59','0:0:0:0:0:0:0:1'),(5,'202012201623330','redis权限停止1',1,'/sys/redis/off.json',2,7,0,'修改顺序1','马斌','2020-12-20 17:43:52','0:0:0:0:0:0:0:1'),(6,'202012201715340','redis权限停止2',1,'/sys/redis/off.json',2,5,1,'','马斌','2020-12-20 17:15:35','0:0:0:0:0:0:0:1'),(7,'202012201720210','redis权限停止3',1,'/sys/redis/off.json',2,6,1,'','马斌','2020-12-20 17:20:21','0:0:0:0:0:0:0:1'),(8,'202101101655410','rabitmq列表',4,'/sys/rabitmq.page',1,1,1,'','马斌','2021-01-10 16:55:42','0:0:0:0:0:0:0:1'),(9,'202101101659160','rabitmq启动',5,'/sys/rabitmq/on.json',2,1,1,'','马斌','2021-01-10 16:59:16','0:0:0:0:0:0:0:1'),(10,'202101301611090','用户列表查看',6,'/sys/user/page.json',1,1,1,'','马斌','2021-01-30 20:41:17','0:0:0:0:0:0:0:1'),(11,'202101301615310','部门管理页面',6,'/sys/dept/dept.page',1,2,1,'','马斌','2021-01-30 20:40:31','0:0:0:0:0:0:0:1'),(12,'202102281732350','启动',7,'/sys/mysql/on.json',1,1,1,'','马斌','2021-02-28 17:32:36','0:0:0:0:0:0:0:1'),(13,'202102281733040','停止',7,'/sys/mysql/off.json',1,2,1,'','马斌','2021-02-28 17:33:04','0:0:0:0:0:0:0:1'),(14,'202102281735140','启动',8,'/sys/es/on.json',2,1,1,'','马斌','2021-02-28 17:35:15','0:0:0:0:0:0:0:1');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_module`
--

LOCK TABLES `sys_acl_module` WRITE;
/*!40000 ALTER TABLE `sys_acl_module` DISABLE KEYS */;
INSERT INTO `sys_acl_module` VALUES (1,'redis',0,'0',1,1,'','马斌','2020-10-09 07:52:30','127.0.0.1'),(2,'基础知识',1,'0.1',1,1,'','马斌','2020-10-09 21:22:23','127.0.0.1'),(3,'应用',1,'0.1',1,1,'','马斌','2020-10-09 21:22:44','127.0.0.1'),(4,'rabitmq',0,'0',1,1,'','马斌','2020-10-10 07:47:57','127.0.0.1'),(5,'基础知识',4,'0.4',1,1,'','马斌','2021-01-10 16:57:08','127.0.0.1'),(6,'权限管理',0,'0',1,1,'','马斌','2021-01-30 16:10:21','127.0.0.1'),(7,'mysql',0,'0',4,1,'','马斌','2021-02-28 17:32:07','127.0.0.1'),(8,'es',0,'0',5,1,'','马斌','2021-02-28 17:34:08','127.0.0.1');
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (10,'根部门',0,'0',1,'','system-save','2020-08-22 14:51:20','127.0.0.1'),(11,'节点',10,'0.10',1,'','system-update','2020-08-22 17:13:15','127.0.0.1'),(13,'节点2',10,'0.10',1,'','system-update','2020-08-22 18:29:51','127.0.0.1'),(16,'节点2-1',13,'0.10.13',1,'','马斌','2021-01-18 07:57:29','127.0.0.1'),(17,'节点3',10,'0.10',2,'','马斌','2021-02-24 21:09:15','127.0.0.1'),(18,'节点4',10,'0.10',4,'','马斌','2021-02-28 17:31:27','0:0:0:0:0:0:0:1');
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
  `target_id` int(11) NOT NULL DEFAULT '0' COMMENT '基于type对象的id 如用户 权限 角色表的id',
  `old_value` text NOT NULL COMMENT '更新前的值',
  `new_value` text NOT NULL COMMENT '更新的值',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operater_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operater_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 1：复原过 0:没有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (5,1,18,'','{\"id\":18,\"name\":\"节点4\",\"parentId\":10,\"level\":\"0.10\",\"seq\":4,\"operator\":\"马斌\",\"operaterTime\":1614172064350,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-24 21:07:44','0:0:0:0:0:0:0:1',1),(6,1,17,'{\"id\":17,\"name\":\"节点3\",\"parentId\":10,\"level\":\"0.10\",\"seq\":4,\"operator\":\"马斌\",\"operaterTime\":1614171734000,\"operaterIp\":\"127.0.0.1\"}','{\"id\":17,\"name\":\"节点3\",\"parentId\":10,\"level\":\"0.10\",\"seq\":2,\"operator\":\"马斌\",\"operaterTime\":1614172144755,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-24 21:09:18','0:0:0:0:0:0:0:1',1),(8,2,10,'','{\"id\":10,\"username\":\"测试2\",\"telephone\":\"15888884444\",\"mail\":\"ce@qq.com\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"deptId\":17,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614172254103,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-24 21:10:59','0:0:0:0:0:0:0:1',1),(9,1,18,'{\"id\":18,\"name\":\"节点4\",\"parentId\":10,\"level\":\"0.10\",\"seq\":4,\"operator\":\"马斌\",\"operaterTime\":1614172064000,\"operaterIp\":\"127.0.0.1\"}','{\"id\":18,\"name\":\"节点44\",\"parentId\":10,\"level\":\"0.10\",\"seq\":5,\"operator\":\"马斌\",\"operaterTime\":1614504065432,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-28 17:21:17','0:0:0:0:0:0:0:1',1),(10,1,18,'{\"id\":18,\"name\":\"节点44\",\"parentId\":10,\"level\":\"0.10\",\"seq\":5,\"operator\":\"马斌\",\"operaterTime\":1614504065000,\"operaterIp\":\"127.0.0.1\"}','{\"id\":18,\"name\":\"节点4\",\"parentId\":10,\"level\":\"0.10\",\"seq\":4,\"operator\":\"马斌\",\"operaterTime\":1614504686889,\"operaterIp\":\"0:0:0:0:0:0:0:1\"}','马斌','2021-02-28 17:31:28','0:0:0:0:0:0:0:1',1),(11,3,7,'','{\"id\":7,\"name\":\"mysql\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614504726593,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-28 17:32:07','0:0:0:0:0:0:0:1',1),(12,4,12,'','{\"id\":12,\"code\":\"202102281732350\",\"name\":\"启动\",\"aclModuleId\":7,\"url\":\"/sys/mysql/on.json\",\"type\":1,\"seq\":1,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614504755729,\"operaterIp\":\"0:0:0:0:0:0:0:1\"}','马斌','2021-02-28 17:32:36','0:0:0:0:0:0:0:1',1),(13,4,13,'','{\"id\":13,\"code\":\"202102281733040\",\"name\":\"停止\",\"aclModuleId\":7,\"url\":\"/sys/mysql/off.json\",\"type\":1,\"seq\":2,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614504784381,\"operaterIp\":\"0:0:0:0:0:0:0:1\"}','马斌','2021-02-28 17:33:04','0:0:0:0:0:0:0:1',1),(14,3,8,'','{\"id\":8,\"name\":\"es\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614504848352,\"operaterIp\":\"127.0.0.1\"}','马斌','2021-02-28 17:34:08','0:0:0:0:0:0:0:1',1),(15,4,14,'','{\"id\":14,\"code\":\"202102281735140\",\"name\":\"启动\",\"aclModuleId\":8,\"url\":\"/sys/es/on.json\",\"type\":2,\"seq\":1,\"status\":1,\"operator\":\"马斌\",\"operaterTime\":1614504914528,\"operaterIp\":\"0:0:0:0:0:0:0:1\"}','马斌','2021-02-28 17:35:15','0:0:0:0:0:0:0:1',1),(16,6,3,'[]','[]','马斌','2021-02-28 17:35:27','0:0:0:0:0:0:0:1',1),(17,6,3,'[]','[]','马斌','2021-02-28 17:35:40','0:0:0:0:0:0:0:1',1),(18,6,3,'[]','[8,9]','马斌','2021-02-28 17:36:04','0:0:0:0:0:0:0:1',1),(19,6,3,'[8,9]','[8]','马斌','2021-02-28 17:36:14','0:0:0:0:0:0:0:1',1),(20,6,18,'[]','[]','马斌','2021-02-28 17:36:30','0:0:0:0:0:0:0:1',1),(21,6,3,'[8]','[8,9]','马斌','2021-02-28 21:03:18','0:0:0:0:0:0:0:1',1),(22,6,21,'[]','[8]','马斌','2021-02-28 21:03:31','0:0:0:0:0:0:0:1',1),(23,6,3,'[8,9]','[8]','马斌','2021-02-28 21:03:52','0:0:0:0:0:0:0:1',1),(24,6,23,'[]','[8,9]','马斌','2021-02-28 21:10:07','0:0:0:0:0:0:0:1',1),(25,6,3,'[8]','[8,9]','马斌','2021-02-28 21:13:51','0:0:0:0:0:0:0:1',1),(26,6,3,'[8,9]','[8]','马斌','2021-02-28 21:14:00','0:0:0:0:0:0:0:1',1),(27,6,3,'[8]','[8,9]','马斌','2021-02-28 21:14:24','0:0:0:0:0:0:0:1',1),(28,6,3,'[8,9]','[8]','马斌','2021-02-28 21:14:44','0:0:0:0:0:0:0:1',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'redis',1,1,'redis普通','马斌','2020-12-21 21:48:52','0:0:0:0:0:0:0:1'),(2,'nginx',1,1,'nginx','马斌','2020-12-21 21:53:44','0:0:0:0:0:0:0:1'),(3,'mysql',1,1,'','马斌','2020-12-22 20:58:19','0:0:0:0:0:0:0:1');
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_acl`
--

LOCK TABLES `sys_role_acl` WRITE;
/*!40000 ALTER TABLE `sys_role_acl` DISABLE KEYS */;
INSERT INTO `sys_role_acl` VALUES (23,1,1,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(24,1,2,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(25,1,3,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(26,1,4,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(27,1,6,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(28,1,7,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(29,1,8,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(30,1,9,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(31,1,10,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(32,1,11,'管理员','2021-01-30 20:56:05','0:0:0:0:0:0:0:1'),(38,21,8,'马斌','2021-02-28 21:03:31','0:0:0:0:0:0:0:1'),(40,23,8,'马斌','2021-02-28 21:10:07','0:0:0:0:0:0:0:1'),(41,23,9,'马斌','2021-02-28 21:10:07','0:0:0:0:0:0:0:1'),(47,3,8,'马斌','2021-02-28 21:14:44','0:0:0:0:0:0:0:1');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES (18,2,2,'马斌','2021-01-16 10:38:41','0:0:0:0:0:0:0:1'),(19,1,1,'马斌','2021-01-30 20:50:42','0:0:0:0:0:0:0:1'),(20,1,2,'马斌','2021-01-30 20:50:42','0:0:0:0:0:0:0:1'),(21,1,3,'马斌','2021-01-30 20:50:42','0:0:0:0:0:0:0:1'),(22,1,4,'马斌','2021-01-30 20:50:42','0:0:0:0:0:0:0:1');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'管理员','15888888888','admin@qq.com','E10ADC3949BA59ABBE56E057F20F883E','测试',11,1,'管理员','2020-09-19 21:16:59','127.0.0.1'),(2,'马斌','15554443028','m_bingo@qq.com','E10ADC3949BA59ABBE56E057F20F883E','测试',11,1,'system-update','2020-09-19 10:53:23','127.0.0.1'),(3,'马斌2','15888888889','admin@163.com','E10ADC3949BA59ABBE56E057F20F883E','',11,1,'马斌','2020-09-20 16:25:23','127.0.0.1'),(4,'马斌3','15888888887','m_bingo@163.com','E10ADC3949BA59ABBE56E057F20F883E','',11,1,'马斌','2020-09-20 16:26:18','127.0.0.1'),(5,'马斌4','15888888899','admin4@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',10,1,'马斌','2021-02-24 07:56:33','127.0.0.1'),(6,'马斌4','15888888099','admin5@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',10,1,'马斌','2021-02-24 07:57:42','127.0.0.1'),(7,'测试','15888888199','admin6@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',11,1,'马斌','2021-02-24 20:43:10','127.0.0.1'),(8,'测试q','15888883399','admin8@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',11,1,'马斌','2021-02-24 20:50:48','127.0.0.1'),(9,'测试w33','15888833099','admin0@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',11,1,'马斌','2021-02-24 20:56:04','127.0.0.1'),(10,'测试2','15888884444','ce@qq.com','E10ADC3949BA59ABBE56E057F20F883E','',17,1,'马斌','2021-02-24 21:10:54','127.0.0.1');
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

-- Dump completed on 2021-02-28 21:21:46
