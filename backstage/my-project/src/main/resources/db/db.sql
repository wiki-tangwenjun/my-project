-- ------------------------------------------------------
-- 创建并使用数据库
-- ------------------------------------------------------
set charset utf8;
create database if not exists myproject character set UTF8;
use myproject;

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

  -- 将黑名单导出到布控表
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
	departmentId VARCHAR(32) DEFAULT NULL COMMENT '所属部门',	/* 部门id，部门外键		*/
	userName 	 VARCHAR(64) NOT NULL COMMENT '账号名',			/* 账号名				*/
	password VARCHAR(64) NOT NULL COMMENT '密码',				/* 密码(加密后)			*/
	personName VARCHAR(32) DEFAULT '' COMMENT '账号使用者',		/* 账号使用者名称		*/
	idNumber VARCHAR(32) DEFAULT '' COMMENT '证件号码',			/* 证件号码				*/
	telphone VARCHAR(32) DEFAULT '' COMMENT '联系号码',			/* 联系号码				*/
	loginWay TINYINT DEFAULT 0 COMMENT '登录方式',				/* 登录方式				*/
	GDCAKey VARCHAR(2048) DEFAULT '' COMMENT '数字证书',			/* 数字证书				*/
	enabled TINYINT DEFAULT 1 COMMENT '是否启用',				/* 是否启用				*/
	expired TINYINT DEFAULT 0 COMMENT '是否失效',				/* 是否失效 			*/
	locked 	TINYINT DEFAULT 0 COMMENT '是否锁定',				/* 是否锁定 			*/
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息 ';


/* 角色表*/
CREATE TABLE IF NOT EXISTS t_role
(
	id 		VARCHAR(32) NOT NULL COMMENT '角色id',			/* 主键，角色id			*/
	name 	VARCHAR(64) NOT NULL COMMENT '名称',			/* 角色名称				*/
	level  TINYINT COMMENT '用户级别',						/* 用户级别			*/
	status TINYINT DEFAULT 0 COMMENT '状态',				/* 状态 启用、禁止		*/
	remark	VARCHAR(128) DEFAULT '' COMMENT '描述',			/* 描述					*/
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息 ';

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


/* 页面资源表*/
CREATE TABLE IF NOT EXISTS t_page_Autowired
(
	id 		VARCHAR(32) NOT NULL COMMENT '页面id',			/* 主键，页面id			*/
	parentId  VARCHAR(32) DEFAULT '' COMMENT '父页面id',	/* 父页面id				*/
	name VARCHAR(64) NOT  NULL COMMENT '名称',				/* 页面名称				*/
	icon VARCHAR(64)DEFAULT '' COMMENT '图标',				/* 图标					*/
	zIndex TINYINT DEFAULT 0,								/* 菜单顺序0~255		*/
	type TINYINT DEFAULT 0,									/* 0:菜单  1:页面		*/
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面资源信息 ';

/* 角色-资源中间表*/
CREATE TABLE IF NOT EXISTS t_role_Autowired
(
	id 		VARCHAR(32) NOT NULL COMMENT 'id',				/* 主键id			*/
	roleId 	 VARCHAR(32) NOT NULL COMMENT '用户名',			/* 用户名，用户外键		*/
	AutowiredId 	VARCHAR(32) NOT NULL COMMENT '资源',		/* 资源id',资源表外键			*/
	PRIMARY KEY (id),
	FOREIGN KEY(roleId) REFERENCES t_role(id)  ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(AutowiredId) REFERENCES t_page_Autowired(id)  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-资源中间表 ';

--
-- 操作日志
--
CREATE TABLE IF NOT EXISTS t_operate_log(
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

