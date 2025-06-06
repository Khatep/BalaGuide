-- ---------------------------------------
-- ГРУППЫ
-- ---------------------------------------
INSERT INTO groups (id, created_date, update_date, name, start_education_date, language, min_participants,
                    max_participants, current_participants, group_full, course_id, teacher_id)
VALUES (1, now(), now(), 'Группа Scratch 1', '2025-01-10', 'Русский', 3, 10, 2, false, 1, 1),
       (2, now(), now(), 'Группа Математика', '2025-02-15', 'Русский', 3, 10, 1, false, 5, 1),
       (3, now(), now(), 'Группа Английский', '2025-02-20', 'Казахский', 3, 8, 1, false, 3, 2);

-----------------------------------------
-- CHILD-GROUP связи
-- ---------------------------------------
INSERT INTO child_group (child_id, group_id, created_date)
VALUES (1, 1, '2025-01-01'), -- Alina в Scratch
       (2, 2, '2025-02-01');
-- Marat в Math

-- ---------------------------------------
-- ОПЛАТЫ (PAID, CANCELLED, PENDING)

------------------------
-- Январь 2025 (основной старт)
INSERT INTO payments (id, created_date, update_date, payment_time, amount, percent_of_vat,
                      payment_method, payment_status, transaction_id,
                      child_id, course_id, parent_id)
VALUES
-- Январь 2025
(10, '2025-01-05', now(), '2025-01-05', 15000.00, 12, 'CARD', 'PAID', 'TXN011', 1, 1, 1),
(11, '2025-01-06', now(), '2025-01-06', 14000.00, 12, 'CARD', 'PAID', 'TXN012', 2, 5, 2),
(12, '2025-01-08', now(), '2025-01-08', 18000.00, 12, 'CARD', 'PAID', 'TXN013', 2, 3, 2),

-- Февраль 2025
(13, '2025-02-03', now(), '2025-02-03', 15000.00, 12, 'CASH', 'PAID', 'TXN014', 1, 1, 1),
(14, '2025-02-10', now(), '2025-02-10', 14000.00, 12, 'CARD', 'PAID', 'TXN015', 1, 5, 1),
(15, '2025-02-18', now(), '2025-02-18', 18000.00, 12, 'CARD', 'PAID', 'TXN016', 2, 3, 2),

-- Март 2025
(16, '2025-03-01', now(), '2025-03-01', 18000.00, 12, 'CASH', 'PAID', 'TXN017', 2, 3, 2),
(17, '2025-03-05', now(), '2025-03-05', 15000.00, 12, 'CARD', 'PAID', 'TXN018', 1, 1, 1),
(18, '2025-03-12', now(), '2025-03-12', 14000.00, 12, 'CASH', 'PAID', 'TXN019', 1, 5, 1),
(19, '2025-03-15', now(), '2025-03-15', 12000.00, 12, 'CARD', 'PAID', 'TXN020', 2, 4, 2),
(20, '2025-03-20', now(), '2025-03-20', 10000.00, 12, 'CARD', 'PAID', 'TXN021', 1, 2, 1),

-- Апрель 2025
(21, '2025-04-01', now(), '2025-04-01', 15000.00, 12, 'CARD', 'PAID', 'TXN022', 1, 1, 1),
(22, '2025-04-05', now(), '2025-04-05', 18000.00, 12, 'CARD', 'PAID', 'TXN023', 2, 3, 2),
(23, '2025-04-10', now(), '2025-04-10', 14000.00, 12, 'CASH', 'PAID', 'TXN024', 2, 5, 2),
(24, '2025-04-15', now(), '2025-04-15', 10000.00, 12, 'CARD', 'PAID', 'TXN025', 1, 2, 1),
(25, '2025-04-20', now(), '2025-04-20', 12000.00, 12, 'CARD', 'PAID', 'TXN026', 2, 4, 2),

-- Май 2025
(26, '2025-05-01', now(), '2025-05-01', 15000.00, 12, 'CARD', 'PAID', 'TXN027', 1, 1, 1),
(27, '2025-05-05', now(), '2025-05-05', 18000.00, 12, 'CASH', 'PAID', 'TXN028', 2, 3, 2),
(28, '2025-05-10', now(), '2025-05-10', 14000.00, 12, 'CARD', 'PAID', 'TXN029', 1, 5, 1),
(29, '2025-05-15', now(), '2025-05-15', 12000.00, 12, 'CARD', 'PAID', 'TXN030', 2, 4, 2),
(30, '2025-05-20', now(), '2025-05-20', 10000.00, 12, 'CARD', 'PAID', 'TXN031', 2, 2, 2);


INSERT INTO receipts (created_date, update_date, payment_id, receipt_number, description, file_url, issuer)
VALUES
-- Январь
(now(), now(), 10, gen_random_uuid(), 'Чек за Юный программист', 'https://files.balaguide.kz/receipts/10.pdf', 'Kaspi'),
(now(), now(), 11, gen_random_uuid(), 'Чек за Математика с логикой', 'https://files.balaguide.kz/receipts/11.pdf',
 'Kaspi'),
(now(), now(), 12, gen_random_uuid(), 'Чек за Английский для начинающих', 'https://files.balaguide.kz/receipts/12.pdf',
 'Halyk'),

-- Февраль
(now(), now(), 13, gen_random_uuid(), 'Чек за Юный программист', 'https://files.balaguide.kz/receipts/13.pdf',
 'Eurasian'),
(now(), now(), 14, gen_random_uuid(), 'Чек за Математика с логикой', 'https://files.balaguide.kz/receipts/14.pdf',
 'Eurasian'),
(now(), now(), 15, gen_random_uuid(), 'Чек за Английский для начинающих', 'https://files.balaguide.kz/receipts/15.pdf',
 'Halyk'),

-- Март
(now(), now(), 16, gen_random_uuid(), 'Чек за Английский для начинающих', 'https://files.balaguide.kz/receipts/16.pdf',
 'Kaspi'),
(now(), now(), 17, gen_random_uuid(), 'Чек за Юный программист', 'https://files.balaguide.kz/receipts/17.pdf', 'Kaspi'),
(now(), now(), 18, gen_random_uuid(), 'Чек за Математика с логикой', 'https://files.balaguide.kz/receipts/18.pdf',
 'Halyk'),
(now(), now(), 19, gen_random_uuid(), 'Чек за Рисование акварелью', 'https://files.balaguide.kz/receipts/19.pdf',
 'Kaspi'),
(now(), now(), 20, gen_random_uuid(), 'Чек за Детская йога', 'https://files.balaguide.kz/receipts/20.pdf', 'Eurasian'),

-- Апрель
(now(), now(), 21, gen_random_uuid(), 'Чек за Юный программист', 'https://files.balaguide.kz/receipts/21.pdf', 'Kaspi'),
(now(), now(), 22, gen_random_uuid(), 'Чек за Английский для начинающих', 'https://files.balaguide.kz/receipts/22.pdf',
 'Halyk'),
(now(), now(), 23, gen_random_uuid(), 'Чек за Математика с логикой', 'https://files.balaguide.kz/receipts/23.pdf',
 'Kaspi'),
(now(), now(), 24, gen_random_uuid(), 'Чек за Детская йога', 'https://files.balaguide.kz/receipts/24.pdf', 'Eurasian'),
(now(), now(), 25, gen_random_uuid(), 'Чек за Рисование акварелью', 'https://files.balaguide.kz/receipts/25.pdf',
 'Halyk'),

-- Май
(now(), now(), 26, gen_random_uuid(), 'Чек за Юный программист', 'https://files.balaguide.kz/receipts/26.pdf', 'Kaspi'),
(now(), now(), 27, gen_random_uuid(), 'Чек за Английский для начинающих', 'https://files.balaguide.kz/receipts/27.pdf',
 'Kaspi'),
(now(), now(), 28, gen_random_uuid(), 'Чек за Математика с логикой', 'https://files.balaguide.kz/receipts/28.pdf',
 'Eurasian'),
(now(), now(), 29, gen_random_uuid(), 'Чек за Рисование акварелью', 'https://files.balaguide.kz/receipts/29.pdf',
 'Halyk'),
(now(), now(), 30, gen_random_uuid(), 'Чек за Детская йога', 'https://files.balaguide.kz/receipts/30.pdf', 'Kaspi');
