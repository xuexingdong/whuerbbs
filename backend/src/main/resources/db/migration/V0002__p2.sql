ALTER TABLE post
    ADD COLUMN `board` smallint UNSIGNED NOT NULL DEFAULT 1 AFTER `title`;

DROP table if exists secondhand_post;
CREATE TABLE secondhand_post
(
    `id`             int UNSIGNED      NOT NULL AUTO_INCREMENT,
    `post_id`        int UNSIGNED      NOT NULL,
    `trade_category` smallint UNSIGNED NOT NULL,
    `campus`         smallint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`)
);

DROP table if exists anonymous_post;
CREATE TABLE anonymous_post
(
    `id`             int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`        int UNSIGNED NOT NULL,
    `anonymous_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`post_id`)
);

DROP table if exists topic;
CREATE TABLE topic
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

DROP table if exists post_topic;
CREATE TABLE post_topic
(
    `id`       int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`  int UNSIGNED NOT NULL,
    `topic_id` int UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`post_id`, `topic_id`)
);

ALTER table user
    ADD COLUMN `school` varchar(255) NOT NULL DEFAULT '' AFTER `city`,
    ADD COLUMN `grade` varchar(255) NOT NULL DEFAULT '' AFTER `school`,
    ADD COLUMN `diploma` varchar(255) NOT NULL DEFAULT '' AFTER `grade`;