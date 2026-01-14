DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS image;

-- 1. Image 테이블 (바이너리 데이터를 포함하므로 먼저 생성)
CREATE TABLE IF NOT EXISTS image (
    image_id      VARCHAR(255) PRIMARY KEY, -- UUID 또는 고유 ID
    bytes         BLOB,                     -- byte[] 데이터를 저장하기 위한 Large Object 타입
    file_name     VARCHAR(255),             -- 원본 파일명
    content_type  VARCHAR(50)               -- image/jpeg, image/png 등 (ContentTypes)
    );

-- 2. User 테이블
CREATE TABLE IF NOT EXISTS users (
    user_id       VARCHAR(50) PRIMARY KEY,  -- 사용자 ID (final 필드 고려)
    password      VARCHAR(255) NOT NULL,    -- 암호화된 비밀번호 저장
    name          VARCHAR(100) NOT NULL,    -- 사용자 이름
    email         VARCHAR(100),      -- 이메일 (final 필드, 유니크 제약 추가)
    image_id      VARCHAR(255)             -- 프로필 이미지 ID
    );

-- 3. Article 테이블
CREATE TABLE IF NOT EXISTS article (
    article_id    VARCHAR(255) PRIMARY KEY, -- 게시글 고유 ID
    content       CLOB,                     -- 대용량 텍스트를 위한 Character Large Object
    user_id       VARCHAR(50) NOT NULL,     -- 작성자 ID
    image_id      VARCHAR(255)             -- 게시글 첨부 이미지 ID
    );