-- 用户表
CREATE TABLE `user`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)  DEFAULT NULL COMMENT '昵称',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 角色表
CREATE TABLE `role`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT,
    `name`   varchar(64) DEFAULT NULL,
    `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 用户 <==> 角色关联表
CREATE TABLE `user_role`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) DEFAULT NULL,
    `role_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 菜单表
CREATE TABLE `menu`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `url`  varchar(64) DEFAULT NULL,
    `name` varchar(64) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 角色 <==> 权限关联表
CREATE TABLE `menu_role`
(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `menu_id` int(11) DEFAULT NULL,
    `role_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
