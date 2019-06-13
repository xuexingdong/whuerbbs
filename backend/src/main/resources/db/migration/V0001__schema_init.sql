SET NAMES utf8mb4;

CREATE TABLE `user`
(
    `id`            char(32)     NOT NULL,
    `unionid`       varchar(255) NULL,
    `nickname`      varchar(255) NOT NULL,
    `avatar_url`    varchar(255) NOT NULL,
    `gender`        tinyint(1)   NOT NULL,
    `country`       varchar(255) NOT NULL,
    `province`      varchar(255) NOT NULL,
    `city`          varchar(255) NOT NULL,
    `school`        varchar(255) NOT NULL DEFAULT '',
    `grade`         varchar(255) NOT NULL DEFAULT '',
    `diploma`       varchar(255) NOT NULL DEFAULT '',
    `last_login_at` datetime     NOT NULL,
    `created_at`    datetime     NOT NULL,
    `deleted`       int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`nickname`)
);

CREATE TABLE `attachment`
(
    `id`         char(32)     NOT NULL,
    `name`       varchar(255) NOT NULL,
    `path`       varchar(255) NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `post`
(
    `id`             int UNSIGNED      NOT NULL AUTO_INCREMENT,
    `title`          varchar(255)      NOT NULL,
    `board`          smallint UNSIGNED NOT NULL,
    `content`        text              NOT NULL,
    `like_count`     int UNSIGNED      NOT NULL DEFAULT 0,
    `comment_count`  int UNSIGNED      NOT NULL DEFAULT 0,
    `created_at`     datetime          NOT NULL,
    `last_active_at` datetime          NOT NULL,
    `user_id`        char(32)          NOT NULL,
    `deleted`        int UNSIGNED      NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`user_id`)
);


CREATE TABLE `post_attachment`
(
    `id`            int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`       int UNSIGNED NOT NULL,
    `attachment_id` char(32)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`),
    KEY (`attachment_id`)
);

CREATE TABLE `comment`
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `content`    text         NOT NULL,
    `like_count` int UNSIGNED NOT NULL DEFAULT 0,
    `parent_id`  int UNSIGNED NOT NULL DEFAULT 0,
    `post_id`    int UNSIGNED NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`post_id`),
    KEY (`user_id`)
);

CREATE TABLE `attitude`
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `target`     smallint     NOT NULL,
    `target_id`  varchar(255) NOT NULL,
    `status`     tinyint(2)   NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`target`, `target_id`, `user_id`, `deleted`)
);

CREATE TABLE `notification`
(
    `id`           int UNSIGNED NOT NULL AUTO_INCREMENT,
    `type`         smallint     NOT NULL,
    `reference_id` varchar(255) NOT NULL,
    `summary`      varchar(255) NOT NULL,
    `content`      varchar(255) NOT NULL,
    `be_read`      tinyint(1)   NOT NULL default 0,
    `from_user_id` char(32)     NOT NULL,
    `to_user_id`   char(32)     NOT NULL,
    `created_at`   datetime     NOT NULL,
    `deleted`      int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`to_user_id`),
    KEY (`reference_id`)
);

CREATE TABLE `user_wx`
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `openid`     varchar(255) NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE (`openid`, `user_id`)
);

CREATE TABLE `secondhand_post`
(
    `id`             int UNSIGNED      NOT NULL AUTO_INCREMENT,
    `post_id`        int UNSIGNED      NOT NULL,
    `trade_category` smallint UNSIGNED NOT NULL,
    `campus`         smallint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`)
);

CREATE TABLE `anonymous_post`
(
    `id`             int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`        int UNSIGNED NOT NULL,
    `anonymous_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`)
);

CREATE TABLE `topic`
(
    `id`                     int UNSIGNED        NOT NULL AUTO_INCREMENT,
    `board`                  smallint UNSIGNED   NOT NULL,
    `title`                  varchar(255)        NOT NULL,
    `description`            varchar(255)        NOT NULL,
    `attachment_id`          char(32)            NOT NULL,
    `participate_user_count` int UNSIGNED        NOT NULL,
    `discussion_mount`       int UNSIGNED        NOT NULL,
    `active`                 tinyint(1) UNSIGNED NOT NULL DEFAULT 1,
    `created_at`             datetime            NOT NULL,
    `deleted`                int UNSIGNED        NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`board`)
);

CREATE TABLE `post_topic`
(
    `id`       int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`  int UNSIGNED NOT NULL,
    `topic_id` int UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`post_id`, `topic_id`)
);

CREATE TABLE `post_collection`
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`    int UNSIGNED NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`post_id`, `user_id`)
);