-- 1. Image 데이터 생성
INSERT INTO image (image_id, bytes, file_name, content_type)
VALUES ('user-img-id-1', X'89504E470D0A1A01', 'profile1.png', 'image/png'),
       ('user-img-id-2', X'89504E470D0A1A02', 'profile2.png', 'image/png'),
       ('user-img-id-3', X'89504E470D0A1A03', 'profile3.png', 'image/png'),
       ('user-img-id-4', X'89504E470D0A1A04', 'profile4.png', 'image/png'),
       ('user-img-id-5', X'89504E470D0A1A05', 'profile5.png', 'image/png'),
       ('article-img-id-1', X'89504E470D0A1A41', 'art_img1.png', 'image/png'),
       ('article-img-id-2', X'89504E470D0A1A42', 'art_img2.png', 'image/png'),
       ('article-img-id-3', X'89504E470D0A1A43', 'art_img3.png', 'image/png'),
       ('article-img-id-4', X'89504E470D0A1A44', 'art_img4.png', 'image/png'),
       ('article-img-id-5', X'89504E470D0A1A45', 'art_img5.png', 'image/png');

-- 2. User 데이터 생성
INSERT INTO users (user_id, password, name, email, image_id)
VALUES ('ti', 'tp', '테스트유저', 'ti@example.com', 'user-img-id-1'),
       ('user2', 'pwd2', '김철수', null, 'user-img-id-2'),
       ('user3', 'pwd3', '이영희', 'younghee@example.com', 'user-img-id-3'),
       ('user4', 'pwd4', '박지성', null, 'user-img-id-4'),
       ('user5', 'pwd5', '손흥민', 'heungmin@example.com', 'user-img-id-5');

-- 3. Article 데이터 생성
INSERT INTO article (article_id, title, content, user_id, image_id)
VALUES ('art-001', '첫 번째 게시글', '안녕하세요, 테스트유저입니다.', 'ti', 'article-img-id-1'),
       ('art-002', '반갑습니다', '철수의 게시글입니다.', 'user2', 'article-img-id-2'),
       ('art-003', '오늘의 날씨', '영희가 쓴 글입니다.', 'user3', 'article-img-id-3'),
       ('art-004', '축구 소식', '지성이 쓴 글입니다.', 'user4', 'article-img-id-4'),
       ('art-005', '프리미어리그', '흥민이 쓴 글입니다.', 'user5', 'article-img-id-5');