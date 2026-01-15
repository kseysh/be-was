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
INSERT INTO article (content, user_id, image_id, like_count)
VALUES ( '안녕하세요, 테스트유저입니다.', 'ti', 'article-img-id-1', 1),
       ( '안녕하세요, 테스트유저2입니다.', 'ti', 'article-img-id-1', 11),
       (  '철수의 게시글입니다.', 'user2', 'article-img-id-2', 2),
       (  '영희가 쓴 글입니다.', 'user3', 'article-img-id-3', 3),
       (  '지성이 쓴 글입니다.', 'user4', 'article-img-id-4', 4),
       (  '흥민이 쓴 글입니다.', 'user5', 'article-img-id-5', 5);

INSERT INTO comment (content, user_id, article_id) VALUES
('첫 번째 댓글입니다!', 'user2', 1),
( '좋은 글이네요.', 'user3', 1),
('공감하고 갑니다.', 'user4', 1),
( '반가워요 테스트유저님.', 'user5', 1),
( '저도 작성해봅니다.', 'ti', 1),

('철수님 안녕하세요.', 'ti', 2),
( '와 신기하네요.', 'user3', 2),
( '잘 읽었습니다.', 'user4', 2),
( '내용이 알차네요.', 'user5', 2),
( '댓글 남깁니다!', 'user2', 2),

( '오늘 날씨 정말 좋죠?', 'user4', 3),
( '영희님 글 솜씨가 좋으시네요.', 'user5', 3),
( '좋은 정보 감사합니다.', 'ti', 3),
( '저도 궁금했던 내용이에요.', 'user2', 3),
( '하하하 그렇군요.', 'user3', 3),

( '박지성 화이팅!', 'user5', 4),
( '역시 전설이네요.', 'ti', 4),
( '대단합니다.', 'user2', 4),
( '멋진 글입니다.', 'user3', 4),
( '축구 소식 더 알려주세요.', 'user4', 4),

('손흥민 골!', 'ti', 5),
( '주말 경기 기대되네요.', 'user2', 5),
( '최고입니다.', 'user3', 5),
( '항상 응원합니다.', 'user4', 5),
( 'EPL 소식 감사합니다.', 'user5', 5);