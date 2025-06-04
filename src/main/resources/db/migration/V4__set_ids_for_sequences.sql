-- Устанавливаем начальные значения sequence на 99, чтобы следующее было 100
SELECT setval('auth_users_id_seq', 99, false);
SELECT setval('bank_cards_id_seq', 99, false);
SELECT setval('children_id_seq', 99, false);
SELECT setval('courses_id_seq', 99, false);
SELECT setval('education_centers_id_seq', 99, false);
SELECT setval('groups_id_seq', 99, false);
SELECT setval('lessons_id_seq', 99, false);
SELECT setval('parents_id_seq', 99, false);
SELECT setval('payments_id_seq', 99, false);
SELECT setval('response_metadata_id_seq', 99, false);
SELECT setval('teachers_id_seq', 99, false);
SELECT setval('receipts_id_seq', 99, false);
SELECT setval('schedule_id_seq', 99, false);
