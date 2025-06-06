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