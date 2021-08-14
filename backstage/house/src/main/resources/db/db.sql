-- ------------------------------------------------------
-- 创建并使用数据库
-- ------------------------------------------------------
set charset utf8;
create database if not exists house character set UTF8;
use house;

-- ------------------------------------------------------
-- 创建增加主键存储过程
-- ------------------------------------------------------

-- 替换语句中断符
DELIMITER ||

-- 如果存在则删除存储过程
drop procedure if exists create_primary_key_procedure ||

-- 将黑名单导出到布控表
-- 参数1：table_name：表名称
-- 参数2：pk_column_name：主键对应列名
-- 参数3：pk_name：主键名
create procedure create_primary_key_procedure (in tb_name varchar(64), in pk_column_name varchar(64), in pk_name varchar(64), in create_new_one int)
begin

	-- 参数声明
  	declare no_more INT DEFAULT 0;

  	-- 查找约束是否存在'
	set @no_more=(select COUNT(*) from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where TABLE_NAME=tb_name and CONSTRAINT_NAME='PRIMARY');
  	IF @no_more=0
  	THEN
      -- 增加主键约束
      set @sql = CONCAT('alter table ',tb_name, ' add constraint ' , pk_name, ' primary key(' , pk_column_name, ' )' );
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  	elseif create_new_one != 0
  	THEN
      -- 如果有auto_increment，先删除之--修改类型即可
      set @sql1 = CONCAT('alter table ', tb_name, ' modify ', pk_column_name, ' int(11)');
      PREPARE stmt FROM @sql1;
      EXECUTE stmt;

      -- 删除主键约束
      set @sql = CONCAT('alter table ', tb_name, ' drop primary key' );
      PREPARE stmt FROM @sql;
      EXECUTE stmt;

       -- 增加主键约束
      set @sql = CONCAT('alter table ',tb_name, ' add constraint ' , pk_name, ' primary key(' , pk_column_name, ' )' );
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  	END IF;

end ||

-- 恢复语句中断符
DELIMITER ;


-- ------------------------------------------------------
-- 创建增加外键存储过程
-- ------------------------------------------------------
-- 替换语句中断符
DELIMITER ||

-- 如果存在则删除存储过程
drop procedure if exists create_foreign_key_procedure ||

-- 将黑名单导出到布控表
-- 参数1：db_name：数据库名称
-- 参数2：table_name：表名称
-- 参数3：pk_column_name：主键对应列名
-- 参数3：pk_name：主键名
create procedure create_foreign_key_procedure (in tb_name varchar(64), in fk_column_name varchar(64), in fk_name varchar(64), in fk_tb_name varchar(64), IN fk_reference_column_name varchar(64), IN create_new_one int)
begin

	-- 参数声明
  declare no_more INT DEFAULT 0;

  -- 查找约束是否存在'
  set @no_more=(SELECT COUNT(*) FROM information_schema.KEY_COLUMN_USAGE WHERE CONSTRAINT_NAME=fk_name AND TABLE_NAME=tb_name);
  IF @no_more=0
  THEN
      -- 增加外键约束
      set @sql = CONCAT('alter table ',tb_name, ' add constraint ' , fk_name, ' foreign key(' , fk_column_name, ') references ', fk_tb_name, '(', fk_reference_column_name, ') on delete cascade on update cascade');
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  elseif create_new_one != 0
  THEN
      -- 如果有auto_increment，先删除之--修改类型即可
      set @sql1 = CONCAT('alter table ', tb_name, ' drop foreign key ', fk_name);
      PREPARE stmt FROM @sql1;
      EXECUTE stmt;

      -- 增加外键约束
      set @sql = CONCAT('alter table ',tb_name, ' add constraint ' , fk_name, ' foreign key(' , fk_column_name, ') references ', fk_tb_name, '(', fk_reference_column_name, ') on delete cascade on update cascade');
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  END IF;

end ||

-- 恢复语句中断符
DELIMITER ;


-- ------------------------------------------------------
-- 创建增加索引存储过程
-- ------------------------------------------------------
-- 替换语句中断符
DELIMITER ||
-- 如果存在则删除存储过程
drop procedure if exists create_index_procedure ||

-- 参数1：tb_name：表名称
-- 参数2：tb_index_name：索引名称
-- 参数3：index_column_1：索引列1
-- 参数4：index_column_2：索引列2
-- 参数5：index_column_3：索引列3
create procedure create_index_procedure (in tb_name varchar(64), in tb_index_name varchar(64), in index_column_1 varchar(32), in index_column_2 varchar(32), in index_column_3 varchar(32), in create_new_one int)
begin

    declare sql_string varchar(1024) DEFAULT NULL;

  	-- 查找约束是否存在'
	  IF NOT EXISTS (SELECT * FROM information_schema.statistics WHERE table_name = tb_name AND index_name = tb_index_name) THEN

      set sql_string = CONCAT('alter table ', tb_name, ' add index ' , tb_index_name, '(');
      IF (index_column_1 IS NOT NULL && index_column_2 IS NULL && index_column_3 IS NULL)
      THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ')');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NULL)
      THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ')');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NOT NULL)
      THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ',', index_column_3, ')');
      END IF;

      -- 增加联合索引
      -- 注意很重要，将连成成的字符串赋值给一个变量（可以之前没有定义，但要以@开头）
      set @sql=sql_string;
      PREPARE stmt FROM @sql;
      EXECUTE stmt;

  	elseif create_new_one != 0 THEN
      -- 如果有auto_increment，先删除之--修改类型即可
      set @sql1 = CONCAT('alter table ', tb_name, ' drop index ', tb_index_name);
      PREPARE stmt FROM @sql1;
      EXECUTE stmt;

      -- 重新添加索引
      set sql_string = CONCAT('alter table ', tb_name, ' add index ' , tb_index_name, '(');
      IF (index_column_1 IS NOT NULL && index_column_2 IS NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ')');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ')');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NOT NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ',', index_column_3, ')');
      ELSE
          SET sql_string = CONCAT('SELECT DATE()');
      END IF;

      -- 增加联合索引
      set @sql=sql_string;
      PREPARE stmt FROM @sql;
      EXECUTE stmt;

  	END IF;

end ||

-- 恢复语句中断符
DELIMITER ;


-- ------------------------------------------------------
-- 创建增加唯一索引存储过程
-- ------------------------------------------------------
-- 替换语句中断符
DELIMITER ||
-- 如果存在则删除存储过程
drop procedure if exists create_unique_index_procedure ||

-- 参数1：tb_name：表名称
-- 参数2：tb_index_name：唯一索引名
-- 参数3：index_column：索引列
create procedure create_unique_index_procedure (in tb_name varchar(64), in tb_index_name varchar(64), in index_column varchar(32), in delete_if_exist int)
begin
  	-- 查找约束是否存在'
	IF NOT EXISTS (SELECT * FROM information_schema.statistics WHERE table_name = tb_name AND index_name = tb_index_name) THEN
		-- 增加联合索引
		set @sql=CONCAT('alter table ', tb_name, ' add constraint ' , tb_index_name, ' unique(', index_column, ')');
		PREPARE stmt FROM @sql;
		EXECUTE stmt;
	elseif delete_if_exist != 0 THEN
	  	set @sql = CONCAT('alter table ', tb_name, ' drop index ', tb_index_name);
	  	PREPARE stmt FROM @sql;
	  	EXECUTE stmt;
	END IF;
end ||

-- 恢复语句中断符
DELIMITER ;


-- 替换语句中断符
DELIMITER ||
-- 如果存在则删除存储过程
drop procedure if exists create_unique_index_procedure2 ||

-- 参数1：tb_name：表名称
-- 参数2：tb_index_name：唯一索引名
-- 参数3：index_column：索引列
create procedure create_unique_index_procedure2 (in tb_name varchar(64), in tb_index_name varchar(64), in index_column1 varchar(32), in index_column2 varchar(32), in delete_if_exist int)
begin
    -- 查找约束是否存在
    IF NOT EXISTS (SELECT * FROM information_schema.statistics WHERE table_name = tb_name AND index_name = tb_index_name) THEN
        -- 增加联合索引
        IF (index_column1 IS NOT NULL && index_column2 IS NULL) THEN
            set @sql=CONCAT('alter table ', tb_name, ' add constraint ' , tb_index_name, ' unique(', index_column1, ')');
        ELSEIF (index_column1 IS NOT NULL && index_column2 IS NOT NULL) THEN
            set @sql=CONCAT('alter table ', tb_name, ' add constraint ' , tb_index_name, ' unique(', index_column1, ',', index_column2, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
        END IF;
    elseif delete_if_exist != 0 THEN
        set @sql = CONCAT('alter table ', tb_name, ' drop index ', tb_index_name);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;

        -- 增加联合索引
        IF (index_column1 IS NOT NULL && index_column2 IS NULL) THEN
            set @sql=CONCAT('alter table ', tb_name, ' add constraint ' , tb_index_name, ' unique(', index_column1, ')');
        ELSEIF (index_column1 IS NOT NULL && index_column2 IS NOT NULL) THEN
            set @sql=CONCAT('alter table ', tb_name, ' add constraint ' , tb_index_name, ' unique(', index_column1, ',', index_column2, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
        END IF;

    END IF;
end ||

-- 恢复语句中断符
DELIMITER ;

-- ------------------------------------------------------
-- 创建全文索引存储过程-MyISAM引擎支持FULLTEXT索引，但是InnoDB不行
-- InnoDB引擎对FULLTEXT索引的支持是MySQL5.6新引入的特性,使用插件！
-- 从MySQL 5.7开始，MySQL内置了ngram全文检索插件，用来支持中文分词，
-- 并且对MyISAM和InnoDB引擎有效
-- ------------------------------------------------------

-- ------------------------------------------------------
-- 使用方式：
-- 按自然语言搜索模式查询
-- SELECT * FROM articles WHERE MATCH (title,body) AGAINST ('关键词' IN NATURAL LANGUAGE MODE);
-- 按布尔全文搜索模式查询
-- 2.1 匹配既有管理又有数据库的记录
-- SELECT * FROM articles WHERE MATCH (title,body) AGAINST ('+数据库 +管理' IN BOOLEAN MODE);
-- 2.2匹配有数据库，但是没有管理的记录
-- SELECT * FROM articles WHERE MATCH (title,body) AGAINST ('+数据库 -管理' IN BOOLEAN MODE);
-- 2.3匹配MySQL，但是把数据库的相关性降低
-- SELECT * FROM articles WHERE MATCH (title,body) AGAINST ('>数据库 +MySQL' INBOOLEAN MODE);
-- ------------------------------------------------------

-- 替换语句中断符
DELIMITER ||
-- 如果存在则删除存储过程
drop procedure if exists create_fulltext_procedure ||

-- 参数1：tb_name：表名称
-- 参数2：tb_index_name：唯一索引名
-- 参数3：index_column：索引列
-- 参数1：tb_name：表名称
-- 参数2：tb_index_name：索引名称
-- 参数3：index_column_1：索引列1
-- 参数4：index_column_2：索引列2
-- 参数5：index_column_3：索引列3
create procedure create_fulltext_procedure (in tb_name varchar(64), in tb_index_name varchar(64), in index_column_1 varchar(32), in index_column_2 varchar(32), in index_column_3 varchar(32), in create_new_one int)
begin
	-- 声明变量
    declare sql_string varchar(1024) DEFAULT NULL;
  	-- 查找约束是否存在'
	IF NOT EXISTS (SELECT * FROM information_schema.statistics WHERE table_name = tb_name AND index_name = tb_index_name) THEN
	  -- 生成对应语句
      set sql_string = CONCAT('alter table ', tb_name, ' add fulltext index ' , tb_index_name, '(');
      IF (index_column_1 IS NOT NULL && index_column_2 IS NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ') with parser ngram');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ') with parser ngram');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NOT NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ',', index_column_3, ') with parser ngram');
      END IF;
      -- 增加联合索引
      -- 注意很重要，将连成成的字符串赋值给一个变量（可以之前没有定义，但要以@开头）
      set @sql=sql_string;
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  	elseif create_new_one != 0 THEN
      -- 如果有auto_increment，先删除之--修改类型即可
      set @sql1 = CONCAT('alter table ', tb_name, ' drop index ', tb_index_name);
      PREPARE stmt FROM @sql1;
      EXECUTE stmt;
      -- 重新添加索引
      set sql_string = CONCAT('alter table ', tb_name, ' add fulltext index ' , tb_index_name, '(');
      IF (index_column_1 IS NOT NULL && index_column_2 IS NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ') with parser ngram');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ') with parser ngram');
      ELSEIF (index_column_1 IS NOT NULL && index_column_2 IS NOT NULL && index_column_3 IS NOT NULL) THEN
          SET sql_string = CONCAT(sql_string, index_column_1, ',', index_column_2, ',', index_column_3, ') with parser ngram');
      END IF;
      -- 增加联合索引
      -- 注意很重要，将连成成的字符串赋值给一个变量（可以之前没有定义，但要以@开头）
      set @sql=sql_string;
      PREPARE stmt FROM @sql;
      EXECUTE stmt;
  	END IF;
end ||

-- 恢复语句中断符
DELIMITER ;

-- *********************************************************************************************************************************************************************************************************************



-- ***************************************************************服务相关表建设*********************************************************
/* 用户表 */
CREATE TABLE IF NOT EXISTS t_user
(
	id 		VARCHAR(32) NOT NULL COMMENT '用户id	',			/* 主键，用户id			*/
	userName 	 VARCHAR(64) NOT NULL COMMENT '账号名',			/* 账号名				*/
	password VARCHAR(64) NOT NULL COMMENT '密码',				/* 密码(加密后)			*/
	personName VARCHAR(32) DEFAULT '' COMMENT '账号使用者',		/* 账号使用者名称		*/
	idNumber VARCHAR(32) DEFAULT '' COMMENT '证件号码',			/* 证件号码				*/
	telphone VARCHAR(32) DEFAULT '' COMMENT '联系号码',			/* 联系号码				*/
	loginWay TINYINT DEFAULT 0 COMMENT '登录方式',				/* 登录方式				*/
	score   FLOAT   DEFAULT 5 COMMENT '用户评分',                 /* 用户评分				*/
	enabled TINYINT DEFAULT 1 COMMENT '是否认证启用',				/* 是否启用				*/
	expired TINYINT DEFAULT 0 COMMENT '是否失效',				/* 是否失效 			*/
	locked 	TINYINT DEFAULT 0 COMMENT '是否锁定',				/* 是否锁定 			*/
	createTime datetime default now() COMMENT '创建时间',        /* 创建时间 			*/
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息 ';

/* 用户认证附件表 */
CREATE TABLE IF NOT EXISTS t_user_enclosure
(
    id 		VARCHAR(32) NOT NULL COMMENT 'id	',
    userId  VARCHAR(32) NOT NULL COMMENT '用户id	',
    fileUrl VARCHAR(264) NOT NULL COMMENT '文件地址',
    fileName VARCHAR(128) NOT NULL COMMENT '文件名称',
    fileSize VARCHAR(16) NOT NULL COMMENT '文件大小',
    fileType BIGINT(4) NOT NULL   COMMENT '文件类型  0身份证 1驾驶证',
    FOREIGN KEY(userId) REFERENCES t_user(id)  ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证 ';

-- 初始化数据
INSERT INTO `house`.`t_user` (`id`, `userName`, `password`, `personName`, `idNumber`, `telphone`, `loginWay`, `enabled`, `expired`, `locked`, `createTime`) VALUES ('1', 'YWRtaW4=', 'fe3ca4c5097cd9f957b61f9d65035b6c', 'admin', '43052319981221071X', '13357214920', '0', '1', '0', '0', '2021-06-11 16:49:18');
INSERT INTO `house`.`t_user` (`id`, `userName`, `password`, `personName`, `idNumber`, `telphone`, `loginWay`, `score`, `enabled`, `expired`, `locked`, `createTime`) VALUES ('2', 'dHdq', 'MTIzNDU2', 'admin', '43052319981221071X', '13357214920', '0', '5', '1', '0', '0', '2021-07-15 16:17:34');


/* 角色表*/
CREATE TABLE IF NOT EXISTS t_role
(
	id 		VARCHAR(32) NOT NULL COMMENT '角色id',			/* 主键，角色id			*/
	name 	VARCHAR(64) NOT NULL COMMENT '名称',			/* 角色名称				*/
	roleKey VARCHAR(32) NOT NULL COMMENT '角色key',
	level  TINYINT COMMENT '用户级别',						/* 用户级别			*/
	status TINYINT DEFAULT 0 COMMENT '状态',				/* 状态 启用、禁止		*/
	remark	VARCHAR(128) DEFAULT '' COMMENT '描述',			/* 描述					*/
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息 ';

-- 初始化数据
INSERT INTO `house`.`t_role` (`id`, `name`, `roleKey`, `level`, `status`, `remark`) VALUES ('1', '超级管理员', 'superAdmin', '1', '0', '超级管理员拥有平台所有权限');
INSERT INTO `house`.`t_role` (`id`, `name`, `roleKey`, `level`, `status`, `remark`) VALUES ('2', '管理员', 'admin', '2', '0', '系统管理员，除去添加管理员、查看日志和修改超级管理员信息功能外拥有平台所有权限');
INSERT INTO `house`.`t_role` (`id`, `name`, `roleKey`, `level`, `status`, `remark`) VALUES ('3', '房东', 'landlord', '3', '0', '房东能添加房子,和修改自己房子的配套信息');
INSERT INTO `house`.`t_role` (`id`, `name`, `roleKey`, `level`, `status`, `remark`) VALUES ('4', '普通用户', 'user', '4', '0', '普通用户拥有查看、预约和评价房子与工作人员');

/* 用户-角色中间表*/
CREATE TABLE IF NOT EXISTS t_user_role
(
	id 		VARCHAR(32) NOT NULL COMMENT 'id',				/* 主键id			*/
	userId 	VARCHAR(32) NOT NULL COMMENT '用户',			/* 用户id COMMENT '',用户表外键			*/
	roleId 	VARCHAR(32) NOT NULL COMMENT '角色',			/* 角色id COMMENT '',角色表外键			*/
	PRIMARY KEY (id),
	FOREIGN KEY(userId) REFERENCES t_user(id)  ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(roleId) REFERENCES t_role(id)  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色中间表 ';
-- 初始化数据
INSERT INTO `house`.`t_user_role` (`id`, `userId`, `roleId`) VALUES ('1', '1', '1');
INSERT INTO `house`.`t_user_role` (`id`, `userId`, `roleId`) VALUES ('2', '2', '2');
INSERT INTO `house`.`t_user_role` (`id`, `userId`, `roleId`) VALUES ('3', '1', '2');



/* 菜单表 */
CREATE TABLE IF NOT EXISTS t_menu
(
    id 		VARCHAR(32) NOT NULL COMMENT 'id',				                        /* 主键id			*/
    name    VARCHAR(32) NOT NULL COMMENT '菜单名称',                                 /*  菜单名称          */
    folderOrMenu  BIGINT(4) NOT NULL DEFAULT 0 COMMENT '是目录还是菜单 0目录 1菜单',  /*  是目录还是菜单  0目录 1菜单   */
    menuHref VARCHAR(256)         COMMENT '菜单链接',                               /*  菜单链接          */
    menuRoute    VARCHAR(128)  COMMENT '菜单路由',                                /*  菜单路由          */
    menuLevel       BIGINT(4)     COMMENT '菜单排序',                               /*  菜单排序          */
    status          BIGINT(2) NOT NULL DEFAULT 0   COMMENT '是否停用',              /*  是否停用 0 正常 1 停用 */
    remark    VARCHAR(256)        COMMENT '备注',                                  /*  备注 */
    parentId  VARCHAR(16)        COMMENT '父菜单目录id',                              /*  父菜单目录id */
    permissionName  VARCHAR(32) NOT NULL COMMENT'菜单权限名称',                            /* 菜单权限名称 */
    reserve1  VARCHAR(128)        COMMENT '备用字段1',                               /*  备用字段1 */
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单资源表 ';
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('1', '系统管理', '0', 'system', 'system', '0', '0', '系统管理', NULL, 'system', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('10', '修改', '2', NULL, 'system/roleMange', '3', '0', '修改', '7', 'update', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('11', '查询', '2', NULL, 'system/roleMange', '4', '0', '查询', '7', 'query', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('12', '菜单管理', '1', 'menuMange', 'system/menuMange', '3', '0', '菜单管理', '1', 'system-menu', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('13', '添加', '2', NULL, 'system/menuMange', '1', '0', '添加', '12', 'add', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('14', '删除', '2', NULL, 'system/menuMange', '2', '0', '删除', '12', 'delete', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('15', '修改', '2', NULL, 'system/menuMange', '3', '0', '修改', '12', 'update', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('16', '查询', '2', NULL, 'system/menuMange', '4', '0', '查询', '12', 'query', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('17', '系统日志', '1', 'operateLog', 'system/operate', '4', '0', '系统日志', '1', 'system-log', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('18', '出租屋管理', '1', 'houseMange', 'house', '1', '0', '出租屋管理', NULL, 'house', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('19', '添加', '2', NULL, 'house', '1', '0', '添加', '18', 'add', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('2', '用户管理', '1', 'userMange', 'system/userMange', '1', '0', '用户管理', '1', 'system-user', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('20', '删除', '2', NULL, 'house', '2', '0', '删除', '18', 'delete', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('21', '修改', '2', NULL, 'house', '3', '0', '修改', '18', 'update', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('22', '查询', '2', NULL, 'house', '4', '0', '查询', '18', 'query', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('23', '评价管理', '1', 'evaluateMange', 'house/evaluateMange', '2', '0', '评价管理', '18', 'house-evaluate', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('24', '添加', '2', NULL, 'house/evaluateMange', '1', '0', '添加', '23', 'add', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('25', '删除', '2', NULL, 'house/evaluateMange', '2', '0', '删除', '23', 'delete', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('26', '修改', '2', NULL, 'house/evaluateMange', '3', '0', '修改', '23', 'update', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('27', '查询', '2', NULL, 'house/evaluateMange', '4', '0', '查询', '23', 'query', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('3', '添加', '2', NULL, 'system/userMange', '1', '0', '添加', '2', 'add', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('4', '删除', '2', NULL, 'system/userMange', '2', '0', '删除', '2', 'delete', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('5', '修改', '2', NULL, 'system/userMange', '3', '0', '修改', '2', 'update', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('6', '查询', '2', NULL, 'system/userMange', '4', '0', '查询', '2', 'query', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('7', '角色管理', '1', 'roleMange', 'system/roleMange', '2', '0', '角色管理', '1', 'system-role', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('8', '添加', '2', NULL, 'system/roleMange', '1', '0', '添加', '7', 'add', NULL);
INSERT INTO `house`.`t_menu` (`id`, `name`, `folderOrMenu`, `menuHref`, `menuRoute`, `menuLevel`, `status`, `remark`, `parentId`, `permissionName`, `reserve1`) VALUES ('9', '删除', '2', NULL, 'system/roleMange', '2', '0', '删除', '7', 'delete', NULL);



/* 角色菜单 */
CREATE TABLE IF NOT EXISTS t_role_menu
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    roleId VARCHAR(32) NOT NULL COMMENT '角色id',
    menuId VARCHAR(32) NOT NULL COMMENT '菜单id',
    PRIMARY KEY (id),
	FOREIGN KEY(roleId) REFERENCES t_role(id)  ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(menuId) REFERENCES t_menu(id)  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单中间表 ';
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('1', '1', '1');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('2', '1', '2');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('3', '1', '3');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('4', '1', '4');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('5', '1', '5');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('6', '2', '1');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('7', '2', '2');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('8', '2', '3');
INSERT INTO `house`.`t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('9', '2', '4');


/* 出租屋表 */
CREATE TABLE IF NOT EXISTS t_house
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    userId VARCHAR(32) NOT NULL COMMENT '房屋所属人',			/* 用户id COMMENT '',用户表外键			*/
    name VARCHAR(32) NOT NULL COMMENT '房子名称',
    floor BIGINT(4) NOT NULL COMMENT '房子所在第几层',
    floorTop BIGINT(4) NOT NULL COMMENT '房子有多少层',
    starTime VARCHAR(4) NOT NULL COMMENT '起租时间 例子： 0.5年起',
    elevator BIGINT(2) NOT NULL DEFAULT 0 COMMENT '是否有电梯 0 没有 1 有',
    pets  BIGINT(2) NOT NULL DEFAULT 0 COMMENT '宠物 0 没有 1 有',
    cook BIGINT(2) NOT NULL DEFAULT 0 COMMENT '做饭 0 没有 1 有',
    machine BIGINT(2) NOT NULL DEFAULT 0 COMMENT '洗衣机 0 没有 1 有',
    conditioner BIGINT(2) NOT NULL DEFAULT 0 COMMENT '空调 0 没有 1 有',
    heater	BIGINT(2) NOT NULL DEFAULT 0 COMMENT '热水器 0 没有 1 有',
    clean   BIGINT(2) NOT NULL DEFAULT 0 COMMENT '干净 0 没有 1 有',
    houseScore float  NOT NULL DEFAULT 80.0 COMMENT '房屋评分 默认80分，随着浏览量高而自动加分 同理反之，每周更新一次',
    province VARCHAR(16) NOT NULL COMMENT '房屋所在省份',
    provinceCode VARCHAR(32) NOT NULL COMMENT '城市所在省code',
    cityStreet  VARCHAR(32) NOT NULL COMMENT '房屋所在城市具体位置',
    cityCode    VARCHAR(16) NOT NULL COMMENT '所在城市code',
    status BIGINT(4)   NOT NULL COMMENT '房子状态 0待出租 1已出租 2正在装修 3合同纠纷 4其他',
    longitude VARCHAR(32) COMMENT '房屋经度',
    latitude VARCHAR(32) COMMENT '房屋纬度',
    create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    remark  VARCHAR(128)  COMMENT '备注',
    reserve1  VARCHAR(128) COMMENT '备用字段1',       /*  备用字段1 */
    PRIMARY KEY (id),
    FOREIGN KEY(userId) REFERENCES t_user(id)  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租屋表 ';

/* 出租屋附件表 */
CREATE TABLE IF NOT EXISTS t_house_enclosure
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    hourseId VARCHAR(32) NOT NULL COMMENT '出租屋id',
    fileUrl VARCHAR(264) NOT NULL COMMENT '文件地址',
    fileName VARCHAR(128) NOT NULL COMMENT '文件名称',
    fileSize VARCHAR(16) NOT NULL COMMENT '文件大小',
    PRIMARY KEY (id),
    FOREIGN KEY(hourseId) REFERENCES t_house(id)  ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租屋附件表 ';

/* 预约看房表 */
CREATE TABLE IF NOT EXISTS t_appointment_house
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    userId VARCHAR(32) NOT NULL COMMENT '看房人用户id',
    hourseId VARCHAR(32) NOT NULL COMMENT '所看的房子id',
    seeTime DATETIME NOT NULL COMMENT '预约看房时间',
    telphone VARCHAR(11) NOT NULL COMMENT '看房人手机号',
    onTime BIGINT(2) NOT NULL COMMENT '看房人是否准时到达 0迟到  1早到 2准时',
    PRIMARY KEY (id),
    FOREIGN KEY(hourseId) REFERENCES t_house(id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(userId) REFERENCES t_user(id)  ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租屋看房预约表 ';

CREATE TABLE IF NOT EXISTS t_house_log
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    userId VARCHAR(32) NOT NULL COMMENT '用户id',
    hourseId VARCHAR(32) NOT NULL COMMENT '房子id',
    userTelphone VARCHAR(12) NOT NULL COMMENT '操作人手机号',
    style VARCHAR(32) NOT NULL COMMENT '操作方式',
    createTime datetime default now() COMMENT '操作时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租屋操作记录表 ';

/* 评价表 */
CREATE TABLE IF NOT EXISTS t_evaluate
(
    id  VARCHAR(32) NOT NULL COMMENT 'id',
    userId VARCHAR(32) NOT NULL COMMENT '看房人用户id',
    hourseId VARCHAR(32) NOT NULL COMMENT '所看的房子id',
    onTime BIGINT(2) NOT NULL COMMENT '工作人员是否准时到达 0迟到  1早到 2准时',
    userOnTime BIGINT(2) NOT NULL COMMENT '看房人员是否准时到达 0迟到  1早到 2准时',
    attitude BIGINT(2) NOT NULL DEFAULT 5 COMMENT '工作人员服务态度 5分满分',
    userAttitude BIGINT(2) NOT NULL COMMENT '看房人员服务态度 5分满分',
    authenticity BIGINT(2) NOT NULL DEFAULT 5 COMMENT '房屋真实度 5分满分',
    remark VARCHAR(128) COMMENT '其他描述',
    PRIMARY KEY (id),
    FOREIGN KEY(hourseId) REFERENCES t_house(id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(userId) REFERENCES t_user(id)  ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评价表 ';

-- 操作日志
--
CREATE TABLE IF NOT EXISTS t_operate_log
(
	id varchar(32) NOT NULL,					/*  编号			    */
	userName varchar(32) NOT NULL,				/*  用户名			    */
	userId varchar(32) NOT NULL,				/*  用户编号			*/
	name varchar(32) DEFAULT '',				/*  用户姓名			*/
	module varchar(16) DEFAULT '' ,				/*	操作的系统模块		*/
	style varchar(16) DEFAULT '' ,				/*	操作方式			*/
	url varchar(256) DEFAULT '' ,				/*	操作方式			*/
	description VARCHAR(64) DEFAULT '' ,		/*  操作内容描述		*/
	operateTime DATETIME DEFAULT NOW() ,		/*	操作时间			*/
	operand VARCHAR(2048) DEFAULT '' ,			/*	操作对象			*/
    result VARCHAR(2048) DEFAULT '' ,			/*	操作结果			*/
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='operate log';

-- 增加索引
call create_index_procedure('t_operate_log', 'index_t_operate_log_userName', 'userName', null, null, 0);

