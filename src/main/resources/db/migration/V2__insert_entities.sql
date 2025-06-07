------------------------------------------------PARENTS-----------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77011112233',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (2, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77025556677',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (12, now(), now(), '77032221100', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT'),
       (13, now(), now(), '77034445566', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT'),
       (14, now(), now(), '77039998877', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT');

INSERT INTO parents (id, created_date, update_date, first_name, last_name, phone_number, birth_date, email, address,
                     balance, auth_user_id)
VALUES (12, now(), now(), 'Aisulu', 'Nur', '77032221100', '1991-01-01', 'aisulu@mail.kz', 'Samal 12', 120000, 12),
       (13, now(), now(), 'Bekzat', 'Ali', '77034445566', '1989-12-12', 'bekzat@mail.kz', 'Altyn Orda 22', 180000, 13),
       (14, now(), now(), 'Diana', 'Esen', '77039998877', '1990-03-03', 'diana@mail.kz', 'Sayran 9', 100000, 14);

INSERT INTO parents (id, created_date, update_date, first_name, last_name, phone_number, birth_date, email, address,
                     balance, auth_user_id)
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'Nurgali', 'Khatep',
        '77011112233', '1999-01-01', 'nurgali.khatep2@gmail.com',
        'Masanchi 90b', 540000, 1);

INSERT INTO parents (id, created_date, update_date, first_name, last_name, phone_number, birth_date, email, address,
                     balance, auth_user_id)
VALUES (2, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'Altynai', 'Zhenis',
        '77025556677', '2000-01-01', 'aserty666qer@gmail.com',
        'Almaly 13a', 200000, 2);


------------------------------------------------CHILDREN----------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (3, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77037778899',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (4, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77045554433',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (15, now(), now(), '77071112233', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD'),
       (16, now(), now(), '77075554411', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD'),
       (17, now(), now(), '77079997755', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD');

INSERT INTO children (id, created_date, update_date, first_name, last_name, phone_number, email, birth_date,
                      auth_user_id, gender, parent_id)
VALUES (15, now(), now(), 'Samat', 'Nur', '77071112233', 'samat@edu.kz', '2012-06-15', 15, 'MALE', 12),
       (16, now(), now(), 'Dana', 'Bek', '77075554411', 'dana@edu.kz', '2013-07-20', 16, 'FEMALE', 13),
       (17, now(), now(), 'Adil', 'Esen', '77079997755', 'adil@edu.kz', '2011-08-30', 17, 'MALE', 14);

INSERT INTO children (id, created_date, update_date, first_name, last_name, phone_number, email, birth_date,
                      auth_user_id,
                      gender, parent_id)
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'Alina', 'Kalyeva',
        '77037778899', 'bryantjennifer@gmail.com', '2017-01-01', 3,
        'FEMALE', 1);

INSERT INTO children (id, created_date, update_date, first_name, last_name, phone_number, email, birth_date,
                      auth_user_id,
                      gender, parent_id)
VALUES (2, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'Marat', 'Almas',
        '77045554433', 'linda39@gmail.com', '2012-01-01', 4,
        'MALE', 2);

------------------------------------------EDUCATION CENTERS-------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (5, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77273334455',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'EDUCATION_CENTER');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (6, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77274445566',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'EDUCATION_CENTER');

INSERT INTO education_centers (id, created_date, update_date, name, phone_number, email, address, instagram_link,
                               date_of_created, balance, auth_user_id)
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'IT easy',
        '77273334455', 'iteasy@gmail.com', 'Abay 45a',
        'https://instagram.com/iteasy', '2016-01-01', 710000, 5);

INSERT INTO education_centers (id, created_date, update_date, name, phone_number, email, address, instagram_link,
                               date_of_created, balance, auth_user_id)
VALUES (2, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'English for kids',
        '77274445566', 'kids@gmail.com', 'Dostyk 113k',
        'https://instagram.com/englishfor', '2020-01-01', 910000, 6);

-------------------------------------------------COURSES----------------------------------------------------------------
INSERT INTO courses (id, created_date, update_date, course_name, description, course_category, age_range, price,
                     number_of_lessons_in_week, durability_by_weeks, education_center_id)
VALUES (1, '2025-01-01', '2025-01-01', 'Юный программист',
        'Основы программирования на языке Scratch для детей 7–10 лет.', 'PROGRAMMING',
        '7-10', 15000.00, 3, 4, 1),
       (2, '2025-01-01', '2025-01-01', 'Детская йога',
        'Курс расслабления и растяжки для малышей. Улучшает осанку и внимание.', 'SPORT',
        '5-8', 10000.00, 3, 4, 2),
       (3, '2025-01-01', '2025-01-01', 'Английский для начинающих',
        'Интерактивные занятия с носителем языка, направленные на развитие речи и словарного запаса.',
        'LANGUAGES', '9-12', 18000.00, 3,
        4, 2),
       (4, '2025-01-01', '2025-01-01', 'Рисование акварелью',
        'Изучение техники акварели и развитие художественного мышления.', 'ART',
        '6-9', 12000.00, 3, 4, 2),
       (5, '2025-01-01', '2025-01-01', 'Математика с логикой',
        'Развитие логического мышления и решение нестандартных задач.', 'MATH',
        '10-13', 14000.00, 3, 4, 1),
        (6, '2025-01-01', '2025-01-01', 'Python для детей',
        'Основы языка программирования Python', 'PROGRAMMING',
        '10-13', 14000.00, 3, 4, 1);

--UPDATE TABLE courses SET content ->> ;

------------------------------------------------TEACHERS----------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (7, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77019998877',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'TEACHER');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (8, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77018887766',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'TEACHER');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (9, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77019997733',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'TEACHER');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (10, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77075467712',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'TEACHER');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (11, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77011284390',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'TEACHER');

INSERT INTO teachers (id, created_date, update_date, first_name, last_name, birth_date, phone_number, email, salary,
                      gender,
                      auth_user_id)
VALUES (1, '2025-01-01', '2025-01-01', 'Айгүл', 'Жумабаева',
        '1985-03-14', '77019998877', 'a.zhumbayeva@example.com', 280000,
        'FEMALE', 7),
       (2, '2025-01-01', '2025-01-01', 'Ермек', 'Серикбаев',
        '1990-06-02', '77018887766', 'e.serikbayev@example.com', 320000,
        'MALE', 8),
       (3, '2025-01-01', '2025-01-01', 'Асель', 'Тлеуханова',
        '1988-09-22', '77019997733', 'a.tleukhanova@example.com', 310000,
        'FEMALE', 9),
       (4, '2025-01-01', '2025-01-01', 'Руслан', 'Муратов',
        '1984-02-11', '77075467712', 'r.muratov@example.com', 330000,
        'MALE', 10),
       (5, '2025-01-01', '2025-01-01', 'Жанна', 'Калдыбаева',
        '1992-07-05', '77011284390', 'zh.kaldybaeva@example.com', 290000,
        'FEMALE', 11);
------------------------------------------------ADMINS------------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (80, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77012223344',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'ADMIN');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (81, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77016667788',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'ADMIN');

------------------------------------------------------------------------------------------------------------------------

-- ГРУППЫ
-- Дополнительные группы
INSERT INTO groups (id, created_date, update_date, name, start_education_date, language, min_participants,
                    max_participants, current_participants, group_full, course_id, teacher_id)
VALUES (1, now(), now(), 'Группа Scratch 1', '2025-01-10', 'Русский', 3, 10, 2, false, 1, 1),
       (2, now(), now(), 'Группа Математика', '2025-02-15', 'Русский', 3, 10, 1, false, 5, 1),
       (3, now(), now(), 'Группа Английский', '2025-02-20', 'Казахский', 3, 8, 1, false, 3, 2),

       -- Группы по Scratch
       (4, now(), now(), 'Группа Scratch 2', '2025-01-15', 'Казахский', 3, 12, 3, false, 1, 2),
       (5, now(), now(), 'Группа Scratch 3', '2025-01-22', 'Русский', 3, 10, 4, false, 1, 3),

       -- Группы по Йоге
       (6, now(), now(), 'Йога для Детей', '2025-02-01', 'Русский', 2, 8, 5, false, 2, 4),
       (7, now(), now(), 'Йога для Подростков', '2025-02-05', 'Казахский', 2, 8, 3, false, 2, 4),

       -- Группы по Английскому
       (8, now(), now(), 'Английский с нуля 1', '2025-03-01', 'Русский', 3, 9, 2, false, 3, 2),
       (9, now(), now(), 'Английский с нуля 2', '2025-03-10', 'Казахский', 3, 9, 4, false, 3, 3),

       -- Группы по Рисованию
       (10, now(), now(), 'Акварель 1', '2025-02-10', 'Русский', 2, 6, 2, false, 4, 5),
       (11, now(), now(), 'Акварель 2', '2025-02-20', 'Казахский', 2, 6, 1, false, 4, 5),

       -- Группы по Математике
       (12, now(), now(), 'Математика логикум 1', '2025-03-05', 'Русский', 3, 10, 3, false, 5, 1),
       (13, now(), now(), 'Математика логикум 2', '2025-03-15', 'Казахский', 3, 10, 2, false, 5, 2);

-- CHILD-GROUP связи
INSERT INTO child_group (child_id, group_id, created_date)
VALUES (1, 1, '2025-01-01'),  -- Alina в Scratch
       (2, 2, '2025-02-01'),  -- Adil в English
       (15, 1, '2025-02-01'), -- Samat в Scratch
       (16, 2, '2025-03-01'), -- Dana в Math
       (17, 3, '2025-05-01');
-- Marat в Math

-- ---------------------------------------
-- ОПЛАТЫ (PAID, CANCELLED, PENDING)
INSERT INTO payments (id, created_date, update_date, payment_time, amount, percent_of_vat,
                      payment_method, payment_status, transaction_id,
                      child_id, course_id, parent_id)
VALUES (10, '2025-01-13', '2025-01-13', '2025-01-13 08:53:57', 18000.0, 12, 'CASH', 'PAID', 'TXN011', 1, 3, 1),
       (11, '2025-01-20', '2025-01-20', '2025-01-20 13:29:29', 10000.0, 12, 'CARD', 'PAID', 'TXN012', 16, 2, 1),
       (12, '2025-01-11', '2025-01-11', '2025-01-11 15:05:55', 14000.0, 12, 'CARD', 'PAID', 'TXN013', 16, 5, 12),
       (13, '2025-01-08', '2025-01-08', '2025-01-08 10:18:45', 14000.0, 12, 'CASH', 'PAID', 'TXN014', 1, 5, 2),
       (14, '2025-01-18', '2025-01-18', '2025-01-18 16:16:51', 12000.0, 12, 'CASH', 'PAID', 'TXN015', 17, 4, 14),
       (15, '2025-01-16', '2025-01-16', '2025-01-16 13:30:03', 10000.0, 12, 'CARD', 'PAID', 'TXN016', 2, 2, 1),

       (16, '2025-02-03', '2025-02-03', '2025-02-03 14:56:21', 18000.0, 12, 'CARD', 'PAID', 'TXN017', 15, 3, 14),
       (17, '2025-02-12', '2025-02-12', '2025-02-12 11:30:06', 15000.0, 12, 'CARD', 'PAID', 'TXN018', 15, 1, 13),
       (18, '2025-02-11', '2025-02-11', '2025-02-11 12:45:56', 14000.0, 12, 'CARD', 'PAID', 'TXN019', 1, 5, 13),
       (19, '2025-02-16', '2025-02-16', '2025-02-16 09:55:52', 10000.0, 12, 'CASH', 'PAID', 'TXN020', 17, 2, 1),
       (20, '2025-02-07', '2025-02-07', '2025-02-07 12:24:03', 14000.0, 12, 'CASH', 'PAID', 'TXN021', 15, 5, 12),
       (21, '2025-02-22', '2025-02-22', '2025-02-22 14:14:37', 18000.0, 12, 'CASH', 'PAID', 'TXN022', 16, 3, 1),

       (22, '2025-03-25', '2025-03-25', '2025-03-25 09:00:07', 12000.0, 12, 'CASH', 'PAID', 'TXN023', 2, 4, 14),
       (23, '2025-03-14', '2025-03-14', '2025-03-14 09:18:03', 14000.0, 12, 'CARD', 'PAID', 'TXN024', 1, 5, 14),
       (24, '2025-03-23', '2025-03-23', '2025-03-23 09:15:59', 12000.0, 12, 'CARD', 'PAID', 'TXN025', 1, 4, 1),
       (25, '2025-03-10', '2025-03-10', '2025-03-10 10:44:13', 12000.0, 12, 'CARD', 'PAID', 'TXN026', 16, 4, 13),
       (26, '2025-03-14', '2025-03-14', '2025-03-14 15:22:09', 14000.0, 12, 'CASH', 'PAID', 'TXN027', 17, 5, 2),
       (27, '2025-03-17', '2025-03-17', '2025-03-17 13:28:25', 10000.0, 12, 'CASH', 'PAID', 'TXN028', 16, 2, 14),

       (28, '2025-04-02', '2025-04-02', '2025-04-02 13:28:12', 14000.0, 12, 'CASH', 'PAID', 'TXN029', 1, 5, 13),
       (29, '2025-04-12', '2025-04-12', '2025-04-12 08:46:44', 18000.0, 12, 'CASH', 'PAID', 'TXN030', 2, 3, 12),
       (30, '2025-04-07', '2025-04-07', '2025-04-07 18:24:01', 12000.0, 12, 'CARD', 'PAID', 'TXN031', 2, 4, 13),
       (31, '2025-04-24', '2025-04-24', '2025-04-24 09:47:35', 12000.0, 12, 'CARD', 'PAID', 'TXN032', 17, 4, 12),
       (32, '2025-04-08', '2025-04-08', '2025-04-08 18:08:22', 12000.0, 12, 'CARD', 'PAID', 'TXN033', 15, 4, 14),
       (33, '2025-04-06', '2025-04-06', '2025-04-06 17:24:20', 18000.0, 12, 'CASH', 'PAID', 'TXN034', 17, 3, 13),

       (34, '2025-05-20', '2025-05-20', '2025-05-20 08:54:06', 10000.0, 12, 'CASH', 'PAID', 'TXN035', 17, 2, 14),
       (35, '2025-05-17', '2025-05-17', '2025-05-17 12:12:23', 14000.0, 12, 'CASH', 'PAID', 'TXN036', 17, 5, 12),
       (36, '2025-05-12', '2025-05-12', '2025-05-12 11:31:56', 14000.0, 12, 'CARD', 'PAID', 'TXN037', 16, 5, 2);


--
-- INSERT INTO receipts (created_date, update_date, payment_id, receipt_number, description, file_url, issuer)
-- VALUES