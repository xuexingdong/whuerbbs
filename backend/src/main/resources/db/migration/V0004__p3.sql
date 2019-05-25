ALTER TABLE comment
    ADD COLUMN `parent_id` int UNSIGNED NOT NULL DEFAULT 0 AFTER `like_count`;

CREATE TABLE post_collection
(
    `id`         int UNSIGNED NOT NULL AUTO_INCREMENT,
    `post_id`    int UNSIGNED NOT NULL,
    `user_id`    char(32)     NOT NULL,
    `created_at` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`post_id`, `user_id`)
);
