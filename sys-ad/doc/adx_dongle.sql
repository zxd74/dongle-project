use adx_dongle;
-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: adx_dongle
-- ------------------------------------------------------
-- Server version	5.7.27

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
-- Table structure for table `tb_advertiser`
--

DROP TABLE IF EXISTS `tb_advertiser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_advertiser` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '广告主名称',
  `ident` varchar(100) DEFAULT NULL COMMENT '广告主标识(平台广告主ID)',
  `platform_id` char(32) NOT NULL,
  `person` varchar(20) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `lisense` varchar(100) NOT NULL COMMENT '营业执照',
  `trade_type` int(11) NOT NULL COMMENT '行业类型：0-未知（默认）,其他见行业类型数据',
  `audit_status` int(1) DEFAULT '0' COMMENT '审核状态：0-待审核（默认），1-审核通过，2-审核拒绝',
  `audit_user` char(32) DEFAULT NULL COMMENT '审核人',
  `audit_msg` varchar(50) DEFAULT NULL COMMENT '审核信息（audit_status为拒绝审核时必填）',
  `status` int(1) DEFAULT '0' COMMENT '使用状态：默认0使用中，1-不使用',
  `remark` varchar(45) DEFAULT NULL,
  `op_user` char(32) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_advertiser`
--

LOCK TABLES `tb_advertiser` WRITE;
/*!40000 ALTER TABLE `tb_advertiser` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_advertiser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company` (
  `id` char(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `person` varchar(50) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `lisence` varchar(500) DEFAULT NULL,
  `trade_type` int(11) DEFAULT '0',
  `remark` varchar(50) DEFAULT NULL,
  `op_user` char(32) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--

LOCK TABLES `tb_company` WRITE;
/*!40000 ALTER TABLE `tb_company` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_entity_dsp`
--

DROP TABLE IF EXISTS `tb_entity_dsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_entity_dsp` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '广告名称，不存在则默认以平台名称+时间戳',
  `ident` varchar(100) DEFAULT NULL COMMENT '平台方广告创意ID',
  `adver_id` char(32) NOT NULL COMMENT '广告主ID',
  `plateform_id` char(32) NOT NULL COMMENT '平台ID',
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `land_url` varchar(500) DEFAULT NULL,
  `land_type` int(11) DEFAULT '0' COMMENT '交互类型（点击触发时，落地页的交互类型）：\n0-WEB(默认)，1-DeepLink,2-DownLoad,3-Email,4-Call',
  `img_url_1` varchar(500) DEFAULT NULL,
  `img_url_2` varchar(500) DEFAULT NULL,
  `img_url_3` varchar(500) DEFAULT NULL,
  `video_url` varchar(500) DEFAULT NULL,
  `cover_url` varchar(500) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `ad_type` int(1) NOT NULL DEFAULT '0' COMMENT '广告类型：0-单图，1-视频，2-组图',
  `width` int(11) DEFAULT NULL COMMENT '广告宽度，单位像素px',
  `height` int(11) DEFAULT NULL COMMENT '广告高度，单位像素px',
  `trade_type` int(11) DEFAULT '0' COMMENT '行业类型：0-未知（默认）,其他见行业类型数据',
  `audit_status` varchar(45) DEFAULT '0' COMMENT '审核状态：0-待审核（默认），1-审核通过，2-审核拒绝',
  `audit_user` char(32) DEFAULT NULL,
  `audit_msg` varchar(50) DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '使用状态：默认0使用中，1-不使用',
  `remark` varchar(45) DEFAULT NULL,
  `op_user` char(32) DEFAULT NULL COMMENT '默认为系统内部用户',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DSP方提交的创意（提审平台需要）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_entity_dsp`
--

LOCK TABLES `tb_entity_dsp` WRITE;
/*!40000 ALTER TABLE `tb_entity_dsp` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_entity_dsp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_media`
--

DROP TABLE IF EXISTS `tb_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_media` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `cp_id` char(32) NOT NULL,
  `type` int(1) NOT NULL COMMENT '应用类型：1-android，2-ios，3-h5，4-pc',
  `trade_type` int(11) DEFAULT '0',
  `status` int(1) DEFAULT '0' COMMENT '应用状态：0-启动（默认），1-关闭',
  `audit_status` int(1) DEFAULT '0' COMMENT '审核状态：0-待审核（默认），1-审核通过，2-审核拒绝（拒绝时不允许请求）',
  `audit_user` char(32) DEFAULT NULL,
  `audit_msg` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL COMMENT '应用地址，h5主页面地址，其他为获取（下载）地址',
  `tags` varchar(100) DEFAULT NULL,
  `version` varchar(50) DEFAULT '1.0' COMMENT '版本号，默认1.0',
  `bundle` varchar(100) DEFAULT NULL COMMENT '应用包名，软件包名需要',
  `op_user` char(32) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_media`
--

LOCK TABLES `tb_media` WRITE;
/*!40000 ALTER TABLE `tb_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_platform`
--

DROP TABLE IF EXISTS `tb_platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_platform` (
  `id` char(32) NOT NULL COMMENT '平台ID，UUID去除-后字符',
  `name` varchar(50) NOT NULL COMMENT '平台名称',
  `ident` char(10) NOT NULL COMMENT '平台标识',
  `platform_type` int(1) NOT NULL COMMENT '平台类型：1-SDK，2-DSP，3-SSP',
  `sys_type` int(1) NOT NULL COMMENT '平台协议类型：0-标准，1-非标准',
  `is_sys` int(1) NOT NULL COMMENT '系统平台：0-不是（默认），1-是',
  `request_type` int(1) NOT NULL DEFAULT '0' COMMENT '平台请求类型：0-Post（默认），1-GET',
  `audit_type` int(1) NOT NULL DEFAULT '0' COMMENT '平台审核类型：0-免审（默认），1-先投后审，2-先审后投',
  `url` varchar(100) DEFAULT NULL COMMENT '平台竞价URL：非SDK必填',
  `price_type` int(1) NOT NULL COMMENT '平台竞价方式：1-固定，2-非固定',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '平台状态：0-测试（默认），1-投放，2-停量',
  `source` varchar(10) DEFAULT NULL COMMENT '平台来源标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '平台备注',
  `op_user` char(32) NOT NULL COMMENT '记录操作人ID',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间：不可修改',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录更新时间：首次与创建时间相同',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_platform`
--

LOCK TABLES `tb_platform` WRITE;
/*!40000 ALTER TABLE `tb_platform` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_position`
--

DROP TABLE IF EXISTS `tb_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_position` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '广告位名称',
  `media_id` char(32) NOT NULL COMMENT '媒体ID',
  `width` int(11) DEFAULT '0' COMMENT '广告位宽度，单位像素px，广告处理时有其他样式，以样式宽度为主',
  `height` int(11) DEFAULT '0' COMMENT '广告位高度，单位像素px，广告处理时有其他样式，以样式高度为主',
  `styles` varchar(100) DEFAULT NULL COMMENT '广告位可支持样式ID集合，多个以英文,分隔，即样式/模板',
  `ad_types` varchar(100) DEFAULT NULL COMMENT '广告位可支持广告类型ID集合，多个以英文,分隔\n0-图片，1-文字，2-视频，3-H5，4-JS，其他见字典表',
  `pos_type` int(11) NOT NULL COMMENT '广告位位置类型：0-开屏，1-插页，2-banner，其他见字典表',
  `status` int(1) DEFAULT '0' COMMENT '广告位状态：0-启动（默认），1-关闭',
  `qps` int(11) DEFAULT '0' COMMENT '待留字段',
  `audit_status` int(1) DEFAULT NULL,
  `audit_user` char(32) DEFAULT NULL,
  `audit_msg` varchar(50) DEFAULT NULL,
  `op_user` char(32) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_position`
--

LOCK TABLES `tb_position` WRITE;
/*!40000 ALTER TABLE `tb_position` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_position` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-12 19:47:55
