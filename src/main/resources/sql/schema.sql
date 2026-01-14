DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS comment;

CREATE TABLE IF NOT EXISTS image (
    image_id      VARCHAR(255) PRIMARY KEY,
    bytes         BLOB,
    file_name     VARCHAR(255),
    content_type  VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS users (
    user_id       VARCHAR(50) PRIMARY KEY,
    password      VARCHAR(255) NOT NULL,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100),
    image_id      VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS article (
    article_id    VARCHAR(255) PRIMARY KEY,
    content       CLOB,
    user_id       VARCHAR(50) NOT NULL,
    image_id      VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS comment (
    comment_id    VARCHAR(255) PRIMARY KEY,
    content       CLOB NOT NULL,
    user_id       VARCHAR(50) NOT NULL,
    article_id    VARCHAR(255) NOT NULL,
    );