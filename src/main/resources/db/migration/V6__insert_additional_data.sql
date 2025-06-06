-- AUTH_USERS для новых родителей
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (12, now(), now(), '77032221100', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT'),
       (13, now(), now(), '77034445566', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT'),
       (14, now(), now(), '77039998877', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'PARENT');

-- PARENTS
INSERT INTO parents (id, created_date, update_date, first_name, last_name, phone_number, birth_date, email, address,
                     balance, auth_user_id)
VALUES (12, now(), now(), 'Aisulu', 'Nur', '77032221100', '1991-01-01', 'aisulu@mail.kz', 'Samal 12', 120000, 12),
       (13, now(), now(), 'Bekzat', 'Ali', '77034445566', '1989-12-12', 'bekzat@mail.kz', 'Altyn Orda 22', 180000, 13),
       (14, now(), now(), 'Diana', 'Esen', '77039998877', '1990-03-03', 'diana@mail.kz', 'Sayran 9', 100000, 14);


-- AUTH_USERS для детей
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (22, now(), now(), '77071112233', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD'),
       (23, now(), now(), '77075554411', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD'),
       (24, now(), now(), '77079997755', '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'CHILD');

-- CHILDREN
INSERT INTO children (id, created_date, update_date, first_name, last_name, phone_number, email, birth_date,
                      auth_user_id, gender, parent_id)
VALUES (22, now(), now(), 'Samat', 'Nur', '77071112233', 'samat@edu.kz', '2012-06-15', 22, 'MALE', 12),
       (23, now(), now(), 'Dana', 'Bek', '77075554411', 'dana@edu.kz', '2013-07-20', 23, 'FEMALE', 13),
       (24, now(), now(), 'Adil', 'Esen', '77079997755', 'adil@edu.kz', '2011-08-30', 24, 'MALE', 14);

INSERT INTO child_group (child_id, group_id, created_date)
VALUES (22, 1, '2025-02-01'), -- Samat в Scratch
       (23, 2, '2025-03-01'), -- Dana в Math
       (24, 3, '2025-05-01');
-- Adil в English


-- PAYMENTS
INSERT INTO payments (id, created_date, update_date, payment_time, amount, percent_of_vat, payment_method,
                      payment_status, transaction_id, child_id, course_id, parent_id)
VALUES (31, now(), now(), '2025-02-10', 15000, 12, 'CARD', 'PAID', 'TXN101', 22, 1, 12),
       (32, now(), now(), '2025-03-15', 15000, 12, 'CARD', 'PAID', 'TXN102', 22, 1, 12),
       (33, now(), now(), '2025-03-01', 14000, 12, 'CASH', 'PAID', 'TXN103', 23, 5, 13),
       (34, now(), now(), '2025-04-01', 14000, 12, 'CARD', 'PAID', 'TXN104', 23, 5, 13),
       (35, now(), now(), '2025-03-05', 18000, 12, 'CARD', 'PAID', 'TXN105', 24, 3, 14),
       (36, now(), now(), '2025-04-05', 18000, 12, 'CARD', 'PAID', 'TXN106', 24, 3, 14);

-- RECEIPTS
INSERT INTO receipts (created_date, update_date, payment_id, receipt_number, description, file_url, issuer)
VALUES (now(), now(), 31, gen_random_uuid(), 'Оплата Scratch', 'https://example.com/receipt101.pdf', 'Kaspi'),
       (now(), now(), 32, gen_random_uuid(), 'Оплата Scratch (повтор)', 'https://example.com/receipt102.pdf', 'Kaspi'),
       (now(), now(), 33, gen_random_uuid(), 'Оплата Math', 'https://example.com/receipt103.pdf', 'Eurasian'),
       (now(), now(), 34, gen_random_uuid(), 'Оплата Math (повтор)', 'https://example.com/receipt104.pdf', 'Eurasian'),
       (now(), now(), 35, gen_random_uuid(), 'Оплата English', 'https://example.com/receipt105.pdf', 'Halyk'),
       (now(), now(), 36, gen_random_uuid(), 'Оплата English (повтор)', 'https://example.com/receipt106.pdf', 'Halyk');
