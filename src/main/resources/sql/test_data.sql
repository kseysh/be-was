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
INSERT INTO article (article_id, content, user_id, image_id)
VALUES ('art-001',  '안녕하세요, 테스트유저입니다.', 'ti', 'article-img-id-1'),
       ('art-002',  '철수의 게시글입니다.', 'user2', 'article-img-id-2'),
       ('art-003',  '영희가 쓴 글입니다.', 'user3', 'article-img-id-3'),
       ('art-004',  '지성이 쓴 글입니다.', 'user4', 'article-img-id-4'),
       ('art-005',  '흥민이 쓴 글입니다.', 'user5', 'article-img-id-5');

INSERT INTO comment (comment_id, content, user_id, article_id) VALUES
-- art-001에 대한 댓글 5개
('cmt-001-1', '첫 번째 댓글입니다!', 'user2', 'art-001'),
('cmt-001-2', '좋은 글이네요.', 'user3', 'art-001'),
('cmt-001-3', '공감하고 갑니다.', 'user4', 'art-001'),
('cmt-001-4', '반가워요 테스트유저님.', 'user5', 'art-001'),
('cmt-001-5', '저도 작성해봅니다.', 'ti', 'art-001'),

-- art-002에 대한 댓글 5개
('cmt-002-1', '철수님 안녕하세요.', 'ti', 'art-002'),
('cmt-002-2', '와 신기하네요.', 'user3', 'art-002'),
('cmt-002-3', '잘 읽었습니다.', 'user4', 'art-002'),
('cmt-002-4', '내용이 알차네요.', 'user5', 'art-002'),
('cmt-002-5', '댓글 남깁니다!', 'user2', 'art-002'),

-- art-003에 대한 댓글 5개
('cmt-003-1', '오늘 날씨 정말 좋죠?', 'user4', 'art-003'),
('cmt-003-2', '영희님 글 솜씨가 좋으시네요.', 'user5', 'art-003'),
('cmt-003-3', '좋은 정보 감사합니다.', 'ti', 'art-003'),
('cmt-003-4', '저도 궁금했던 내용이에요.', 'user2', 'art-003'),
('cmt-003-5', '하하하 그렇군요.', 'user3', 'art-003'),

-- art-004에 대한 댓글 5개
('cmt-004-1', '박지성 화이팅!', 'user5', 'art-004'),
('cmt-004-2', '역시 전설이네요.', 'ti', 'art-004'),
('cmt-004-3', '대단합니다.', 'user2', 'art-004'),
('cmt-004-4', '멋진 글입니다.', 'user3', 'art-004'),
('cmt-004-5', '축구 소식 더 알려주세요.', 'user4', 'art-004'),

-- art-005에 대한 댓글 5개
('cmt-005-1', '손흥민 골!', 'ti', 'art-005'),
('cmt-005-2', '주말 경기 기대되네요.', 'user2', 'art-005'),
('cmt-005-3', '최고입니다.', 'user3', 'art-005'),
('cmt-005-4', '항상 응원합니다.', 'user4', 'art-005'),
('cmt-005-5', 'EPL 소식 감사합니다.', 'user5', 'art-005');