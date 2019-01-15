
DROP TABLE IF EXISTS permission;

CREATE TABLE permission (
  id              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  permission_name varchar(32) DEFAULT NULL COMMENT '权限名',
  permission_sign varchar(128) DEFAULT NULL COMMENT '权限标识,程序中判断使用,如"ps_user:create"',
  description     varchar(256) DEFAULT NULL COMMENT '权限描述,UI界面显示使用',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

/*Data for the table 'permission' */
insert  into permission(id, permission_name, permission_sign, description)
                values (1,'用户新增','ps_user:create',NULL);

/*Table structure for table 'role' */

DROP TABLE IF EXISTS role;

CREATE TABLE role (
  id          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  role_name   varchar(32) DEFAULT NULL COMMENT '角色名',
  role_sign   varchar(128) DEFAULT NULL COMMENT '角色标识,程序中判断使用,如"admin"',
  description varchar(256) DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table 'role' */

insert into role(id,role_name,role_sign,description)
          values (1,'mt','mt','管理教师');
insert into role(id,role_name,role_sign,description)
          values (2,'t','t','任课教师');
insert into role(id,role_name,role_sign,description)
          values (3,'s','s','学生');

/*Table structure for table 'role_permission' */

DROP TABLE IF EXISTS role_permission;

CREATE TABLE role_permission (
  id            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  role_id       bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  permission_id bigint(20) unsigned DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色与权限关联表';

/*Data for the table 'role_permission' */

insert  into role_permission(id,role_id,permission_id) values (1,2,1);

/*Table structure for table 'ps_user' */

DROP TABLE IF EXISTS ps_user;

CREATE TABLE ps_user (
  id          BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  login_name  VARCHAR(50) UNIQUE DEFAULT NULL COMMENT '用户名',
  password    CHAR(64) DEFAULT NULL COMMENT '密码',
  nick_name   VARCHAR(50) COMMENT '昵称',
  sex         CHAR(1) DEFAULT '1' COMMENT '性别',
  id_no       VARCHAR(18) UNIQUE DEFAULT NULL COMMENT '学号/工号',
  u_state     VARCHAR(2) DEFAULT NULL COMMENT '状态',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  login_time  DATETIME DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

/*Data for the table 'ps_user' */

insert  into ps_user(id, login_name , password, nick_name, u_state, create_time)
             values (1,'admin','49','管理教师','1', '2018-10-27 12:59:08');

/*Table structure for table 'user_role' */

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  user_id bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
  role_id bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关联表';

/*Data for the table 'user_role' */

insert  into user_role(id,user_id,role_id) values (1,1,1);

DROP TABLE IF EXISTS course;
CREATE TABLE course (
  id            BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  course_name   VARCHAR(250) NOT NULL COMMENT '课程名称',
  course_type   CHAR(1) COMMENT '课程类型，选修或者必修',
  t_id          BIGINT(20) COMMENT '任课教师ID',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='课程表';

DROP TABLE IF EXISTS sign_in;
CREATE TABLE sign_in (
  id           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  course_id    bigint(20) NOT NULL COMMENT '课程ID',
  signer_id    bigint(20) NOT NULL COMMENT '签到人ID',
  sign_in_time DATETIME   NOT NULL COMMENT '签到时间',
  score_time   DATETIME   COMMENT '评分时间',
  score_1      int(1)     COMMENT '课程总体质量',
  score_2      int(1)     COMMENT '课前对授课内容的掌握程度',
  score_3      int(1)     COMMENT '课后对授课内容的掌握程度',
  score_4      int(1)     COMMENT '课程对临床工作的帮助',
  score_5      int(1)     COMMENT '您觉得教师准备是否充分',
  score_6      int(1)     COMMENT '教师准备教材的PPT是否重点突出，安排得当',
  score_7      int(1)     COMMENT '教师讲课的语音、语调、语速适中，讲课生动，容易理解',
  score_8      int(1)     COMMENT '我愿意参加该讲师主讲的课程',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='签到及课程评价表';