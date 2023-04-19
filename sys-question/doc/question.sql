DROP TABLE IF EXISTS `tb_questions`;
CREATE TABLE `tb_questions`(
	`id` char(14) NOT NULL comment '试题ID',
	`title` varchar(200) not null comment '试题标题',
	`type` int(11) default 0 comment '试题题型',
	`desc` varchar(200) default null comment '试题描述',
	PRIMARY key (`id`),
	KEY `idx_t` (`title`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='试题表';


DROP TABLE IF EXISTS `tb_question_info`;
CREATE TABLE `tb_question_info`(
	`qid` char(14) NOT NULL comment '试题ID',
	`content` blob not null comment '试题内容',
	`answer` blob not null comment '试题答案',
	PRIMARY key (`qid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='试题详情表';


DROP TABLE IF EXISTS `tb_papers`;
CREATE TABLE `tb_papers`(
	`id` char(14) NOT NULL comment '试卷ID',
	`name` varchar(100) not null comment '试卷名称',
	`type` int(1) default 0 comment '试卷类型',
	`total_score` int(11) default 0 comment '试卷总分',
	`status` int(1) default 0 comment '试卷状态',
	`cdt` datetime comment '试卷创建时间',
	`udt` datetime comment '试卷更新时间',
	PRIMARY key (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';


DROP TABLE IF EXISTS `tb_paper_struct`;
CREATE TABLE `tb_paper_struct`(
	`pid` char(14) NOT NULL comment '试卷ID',
	`bqn` int(1) default 1 comment '试卷大题题号',
	`bname` varchar(10) default null comment '试卷大题名称',
	`info` int(1) default 0 comment '试卷大题简介',
	`desc` int(1) default 0 comment '试卷大题详细描述',
	`order` int(2) default 0 comment '试卷大题顺序',
	`total_score` int(11) default 0 comment '试卷大题总分',
	`cdt` datetime comment '试卷创建时间',
	`udt` datetime comment '试卷更新时间',
	PRIMARY key (`pid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='试卷结构表，定义大题结构';


DROP TABLE IF EXISTS `tb_paper_questions`;
CREATE TABLE `tb_paper_questions`(
	`pid` char(14) NOT NULL comment '试卷ID',
	`qid` char(14) NOT NULL COMMENT '试题ID',
	`bqn` int(1) default 1 comment '试卷大题题号',
	`bname` varchar(10) default null comment '试卷大题名称',
	`info` int(1) default 0 comment '试卷大题简介',
	`desc` int(1) default 0 comment '试卷大题详细描述',
	`order` int(2) default 0 comment '试卷大题顺序',
	`score` int(11) default 0 comment '试卷试题分数',
	`cdt` datetime comment '试卷创建时间',
	`udt` datetime comment '试卷更新时间',
	PRIMARY key (`pid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='试卷试题表';


DROP TABLE IF EXISTS `tb_exams`;
CREATE TABLE `tb_exams`(
	`id` char(14) NOT NULL comment '考试计划ID',
	`name` varchar(100) not null comment '考试名称',
	`pid` char(14) NOT NULL comment '试卷ID',
	`type` int(1) default 0 comment '考试类型',
	`status` int(1) default 0 comment '考试状态',
	`cdt` datetime comment '考试计划创建时间',
	`udt` datetime comment '考试计划更新时间',
	`sdt` datetime comment '考试开始建时间',
	`edt` datetime comment '考试结束时间',
	PRIMARY key (`id`),
	KEY `idx_p` (`pid`),
	KEY `idx_n` (`name`),
	KEY `idx_dt` (`sdt`,`edt`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='考试计划表';


DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users`(
	`id` char(14) NOT NULL comment '用户ID',
	`username` varchar(50) default null comment '用户名',
	`password` char(64) default NULL comment '密码',
	`sex` enum('0','1','2') default '0' comment '性别，0-未知，1-男，2-女',
	`age` int(3) default 0 comment '年龄',
	`birth` int(8) default 0 comment '出生年月日，YYYYMMDD',
	`phone` varchar(20) default null comment '电话号码',
	`email` varchar(50) default null comment '邮箱',
	`logo` varchar(100) default null comment '用户头像',
	`source` varchar(20) default null comment '来源',
	`rdt` datetime comment '注册时间',
	PRIMARY key (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';