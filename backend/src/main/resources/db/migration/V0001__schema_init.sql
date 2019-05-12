SET NAMES utf8mb4;

CREATE TABLE user
(
    `id`            char(32)     NOT NULL,
    `unionid`       varchar(255) NULL,
    `nickname`      varchar(255) NOT NULL,
    `avatar_url`    varchar(255) NOT NULL,
    `gender`        tinyint(1)   NOT NULL,
    `country`       varchar(255) NOT NULL,
    `province`      varchar(255) NOT NULL,
    `city`          varchar(255) NOT NULL,
    `last_login_at` datetime     NOT NULL,
    `created_at`    datetime     NOT NULL,
    `deleted`       int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`nickname`)
);

CREATE TABLE attachment
(
    `id`         char(32)     NOT NULL,
    `name`       varchar(255) NOT NULL,
    `path`       varchar(255) NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE post
(
    `id`             int UNSIGNED NOT NULL AUTO_INCREMENT,
    `title`          varchar(255) NOT NULL,
    `content`        text         NOT NULL,
    `like_count`     int UNSIGNED NOT NULL DEFAULT 0,
    `comment_count`  int UNSIGNED NOT NULL DEFAULT 0,
    `created_at`     datetime     NOT NULL,
    `last_active_at` datetime     NOT NULL,
    `user_id`        char(32)     NOT NULL,
    `deleted`        int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`user_id`)
);


CREATE TABLE post_attachment
(
    `id`            int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`       int UNSIGNED NOT NULL,
    `attachment_id` char(32)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`) USING HASH,
    KEY (`attachment_id`) USING HASH
);

CREATE TABLE comment
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `content`    text         NOT NULL,
    `like_count` int UNSIGNED NOT NULL DEFAULT 0,
    `post_id`    int UNSIGNED NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`post_id`) USING HASH,
    KEY (`user_id`) USING HASH
);

CREATE TABLE attitude
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `target`     smallint     NOT NULL,
    `target_id`  varchar(255) NOT NULL,
    `status`     tinyint(2)   NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`target`, `target_id`, `user_id`, `deleted`) USING HASH
);

CREATE TABLE notification
(
    `id`           int UNSIGNED NOT NULL AUTO_INCREMENT,
    `type`         smallint     NOT NULL,
    `reference_id` varchar(255) NOT NULL,
    `content`      varchar(255) NOT NULL,
    `be_read`      tinyint(1)   NOT NULL default 0,
    `from_user_id` char(32)     NOT NULL,
    `to_user_id`   char(32)     NOT NULL,
    `created_at`   datetime     NOT NULL,
    `deleted`      int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`to_user_id`) USING HASH,
    KEY (`reference_id`) USING HASH
);

CREATE TABLE user_wx
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `openid`     varchar(255) NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    `deleted`    int UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE (`openid`, `user_id`) USING HASH
);