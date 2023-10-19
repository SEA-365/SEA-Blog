-- 数据库表初始化

CREATE TABLE `tb_user` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户表主键，自增',
                           `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
                           `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
                           `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                           `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号码',
                           `gender` char(2) DEFAULT NULL COMMENT '性别',
                           `intro` varchar(255) DEFAULT NULL COMMENT '个人简介',
                           `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '头像图片url',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`)
) COMMENT '用户表';


CREATE TABLE `tb_article` (
                              `id` bigint NOT NULL COMMENT '文章表主键，自增',
                              `article` bigint NOT NULL COMMENT '文章id',
                              `author` varchar(128) NOT NULL COMMENT '文章作者',
                              `tag_id` bigint NOT NULL COMMENT '文章标签id',
                              `category_id` bigint NOT NULL COMMENT '文章分类id',
                              `content` longtext NOT NULL COMMENT '文章内容',
                              `count_views` bigint NOT NULL COMMENT '文章浏览量',
                              `total_words` bigint NOT NULL COMMENT '文章总字数',
                              `title` varchar(255) NOT NULL COMMENT '文章标题',
                              `description` varchar(255) DEFAULT NULL COMMENT '文章描述',
                              `image_url` varchar(255) NOT NULL COMMENT '文章logo',
                              `likes` int DEFAULT NULL COMMENT '文章点赞数',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '文章创建时间',
                              `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文章修改时间',
                              PRIMARY KEY (`id`)
) COMMENT '文章表';


CREATE TABLE `tb_comment` (
                              `id` bigint NOT NULL COMMENT '评论表主键，自增',
                              `article_id` bigint(20) unsigned zerofill NOT NULL COMMENT '文章id',
                              `article_author` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文章作者',
                              `comment_create_id` bigint(20) unsigned zerofill NOT NULL COMMENT '评论创建者id',
                              `comment_reply_id` bigint(20) unsigned zerofill DEFAULT NULL COMMENT '被评论回复者id',
                              `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论创建时间',
                              `comment_content` text NOT NULL COMMENT '评论内容',
                              `is_delete` int NOT NULL DEFAULT '0' COMMENT '评论是否被删除，0-否，1-是',
                              `is_review` int NOT NULL DEFAULT '0' COMMENT '评论是否审核通过，0-否，1-是',
                              PRIMARY KEY (`id`)
) COMMENT '评论表';


CREATE TABLE `tb_tag` (
                          `id` bigint NOT NULL COMMENT '标签表主键，自增',
                          `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '标签名',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                          PRIMARY KEY (`id`)
) COMMENT '标签表';

CREATE TABLE `tb_category` (
                               `id` bigint NOT NULL COMMENT '分类表主键，自增',
                               `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '分类名称',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               PRIMARY KEY (`id`)
) COMMENT '分类表';


CREATE TABLE `tb_operation_log` (
                                    `id` bigint NOT NULL COMMENT '操作日志记录表主键，自增',
                                    `operation_ip` varchar(128) DEFAULT NULL COMMENT '主机IP地址',
                                    `operation_location` varchar(255) DEFAULT NULL COMMENT '操作地点',
                                    `method` text COMMENT '方法名',
                                    `args` text COMMENT '参数',
                                    `operation_name` varchar(50) NOT NULL COMMENT '操作 人',
                                    `operation_type` varchar(50) NOT NULL COMMENT '操作类型',
                                    `return_value` text COMMENT '返回参数',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`)
) COMMENT '操作日志记录表';


CREATE TABLE `tb_login_log` (
                                `id` bigint NOT NULL COMMENT '登录日志记录表主键，自增',
                                `login_name` varchar(255) DEFAULT NULL COMMENT '登录账号用户名',
                                `ip_address` varchar(128) DEFAULT NULL COMMENT '登录ip地址',
                                `login_location` varchar(255) DEFAULT NULL COMMENT '登录地点',
                                `browser_type` varchar(50) DEFAULT NULL COMMENT '浏览器类型',
                                `os` varchar(50) NOT NULL COMMENT '操作系统类型',
                                `login_status` tinyint NOT NULL DEFAULT '0' COMMENT '登录状态，默认0，0-成功，1-失败',
                                `message` varchar(255) DEFAULT NULL COMMENT '提示消息',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                PRIMARY KEY (`id`)
) COMMENT '登录日志记录表';