------------------------------------------------PARENTS-----------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77711134882',
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
        '77711134882', '1999-01-01', 'nurgali.khatep2@gmail.com',
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
VALUES (1, '2025-01-01 00:00:00', '2025-01-01 00:00:00', 'Школа программирования KnewIT',
        '77273334455', 'knewit@gmail.com', 'Abay 45a',
        'https://instagram.com/knewit', '2019-01-01', 710000, 5);

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

UPDATE courses
SET content = '{
  "Lesson_1": {
    "topic": "Знакомство с Scratch",
    "description": "Введение в интерфейс среды Scratch. Создание первого проекта."
  },
  "Lesson_2": {
    "topic": "Сцены и спрайты",
    "description": "Добавление спрайтов, изменение фонов, основы визуального дизайна."
  },
  "Lesson_3": {
    "topic": "Движение объектов",
    "description": "Программирование перемещения спрайтов с помощью блоков."
  },
  "Lesson_4": {
    "topic": "Анимация",
    "description": "Создание анимации через смену костюмов и координат."
  },
  "Lesson_5": {
    "topic": "События и реакции",
    "description": "Использование событий (например, при нажатии клавиш) для запуска сценариев."
  },
  "Lesson_6": {
    "topic": "Звуки и музыка",
    "description": "Добавление звуков и создание музыкальных эффектов."
  },
  "Lesson_7": {
    "topic": "Циклы и повторения",
    "description": "Применение блоков повторения для оптимизации кода."
  },
  "Lesson_8": {
    "topic": "Условия (if)",
    "description": "Создание интерактивности через условные блоки."
  },
  "Lesson_9": {
    "topic": "Переменные",
    "description": "Введение в переменные. Отслеживание очков и счётчиков."
  },
  "Lesson_10": {
    "topic": "Мини-игра: Лабиринт",
    "description": "Создание простой игры, где спрайт проходит через лабиринт."
  },
  "Lesson_11": {
    "topic": "Итоговый проект: Моя игра",
    "description": "Работа над собственным проектом с использованием изученных элементов."
  },
  "Lesson_12": {
    "topic": "Презентация проектов",
    "description": "Представление работ, обсуждение, обратная связь и закрепление знаний."
  }
}'::jsonb
WHERE id = 1;

UPDATE courses
SET content = '{
  "Lesson_1": {
    "topic": "Логические задачи на сообразительность",
    "description": "Разбор простых логических задач на внимание и мышление."
  },
  "Lesson_2": {
    "topic": "Шифры и коды",
    "description": "Решение задач с заменой символов, простые шифры и логика."
  },
  "Lesson_3": {
    "topic": "Анализ и дедукция",
    "description": "Решение задач с условиями: кто где живёт, кто что делает."
  },
  "Lesson_4": {
    "topic": "Последовательности и закономерности",
    "description": "Нахождение закономерностей в числовых и графических рядах."
  },
  "Lesson_5": {
    "topic": "Головоломки с кубиками и блоками",
    "description": "Задачи на пространственное мышление и логику."
  },
  "Lesson_6": {
    "topic": "Комбинаторика для детей",
    "description": "Счёт вариантов, простейшие принципы подсчёта."
  },
  "Lesson_7": {
    "topic": "Математические ребусы",
    "description": "Цифровые кроссворды, расстановка чисел по условиям."
  },
  "Lesson_8": {
    "topic": "Игры на стратегию и логику",
    "description": "Простые логические игры: крестики-нолики, Ним, и др."
  },
  "Lesson_9": {
    "topic": "Движение по клеткам",
    "description": "Решение задач на клетчатом поле и логические маршруты."
  },
  "Lesson_10": {
    "topic": "Проверка гипотез",
    "description": "Формирование логических предположений и их проверка."
  },
  "Lesson_11": {
    "topic": "Проект: логическая игра",
    "description": "Создание собственной логической игры или головоломки."
  },
  "Lesson_12": {
    "topic": "Турнир по логике",
    "description": "Соревновательное решение задач. Подведение итогов."
  }
}'::jsonb
WHERE id = 5;

UPDATE courses
SET content = '{
  "Lesson_1": {
    "topic": "Знакомство с Python",
    "description": "Что такое Python, где используется, установка и запуск."
  },
  "Lesson_2": {
    "topic": "Переменные и вывод на экран",
    "description": "Создание переменных, функция print()."
  },
  "Lesson_3": {
    "topic": "Типы данных и операции",
    "description": "Числа, строки, булевы значения и арифметика."
  },
  "Lesson_4": {
    "topic": "Ввод данных от пользователя",
    "description": "input(), преобразование типов и взаимодействие с пользователем."
  },
  "Lesson_5": {
    "topic": "Условные конструкции if",
    "description": "Проверка условий и принятие решений."
  },
  "Lesson_6": {
    "topic": "Циклы for и while",
    "description": "Повторяющиеся действия и счётчики."
  },
  "Lesson_7": {
    "topic": "Списки и перебор элементов",
    "description": "Создание списков и работа с ними."
  },
  "Lesson_8": {
    "topic": "Функции в Python",
    "description": "Объявление, вызов и параметры функций."
  },
  "Lesson_9": {
    "topic": "Генерация случайных чисел",
    "description": "Модуль random и простые игры."
  },
  "Lesson_10": {
    "topic": "Работа с условиями и циклами в проектах",
    "description": "Создание мини-программы с логикой."
  },
  "Lesson_11": {
    "topic": "Проект: Угадай число",
    "description": "Создание простой консольной игры."
  },
  "Lesson_12": {
    "topic": "Защита проектов",
    "description": "Презентация работ, рефлексия, идеи для продолжения."
  }
}'::jsonb
WHERE id = 6;

UPDATE courses
SET content = lesson_data.generated_lessons
    FROM (
    SELECT c.id,
           jsonb_object_agg(
               'Lesson_' || i,
               jsonb_build_object(
                   'topic', 'Topic for Lesson ' || i,
                   'description', 'Description for Lesson ' || i
               )
           ) AS generated_lessons
    FROM courses c
    JOIN LATERAL generate_series(1, c.number_of_lessons_in_week * c.durability_by_weeks) AS i ON true
    WHERE c.id IN (2, 3, 4)
    GROUP BY c.id
) AS lesson_data
WHERE courses.id = lesson_data.id;

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
                      auth_user_id, education_center_id)
VALUES (1, '2025-01-01', '2025-01-01', 'Айгүл', 'Жумабаева',
        '1985-03-14', '77019998877', 'a.zhumbayeva@example.com', 280000,
        'FEMALE', 7, 1),
       (2, '2025-01-01', '2025-01-01', 'Ермек', 'Серикбаев',
        '1990-06-02', '77018887766', 'e.serikbayev@example.com', 320000,
        'MALE', 8, 1),
       (3, '2025-01-01', '2025-01-01', 'Асель', 'Тлеуханова',
        '1988-09-22', '77019997733', 'a.tleukhanova@example.com', 310000,
        'FEMALE', 9, 1),
       (4, '2025-01-01', '2025-01-01', 'Руслан', 'Муратов',
        '1984-02-11', '77075467712', 'r.muratov@example.com', 330000,
        'MALE', 10, 1),
       (5, '2025-01-01', '2025-01-01', 'Жанна', 'Калдыбаева',
        '1992-07-05', '77011284390', 'zh.kaldybaeva@example.com', 290000,
        'FEMALE', 11, 1);


-- ГРУППЫ
-- Дополнительные группы
INSERT INTO groups (id, created_date, update_date, name, start_education_date, language, min_participants,
                    max_participants, current_participants, group_full, course_id, teacher_id)
VALUES (1, now(), now(), 'Группа Scratch 1', '2025-01-10', 'Russian', 3, 10, 2, false, 1, 1),
       (2, now(), now(), 'Группа Математика', '2025-02-15', 'Russian', 3, 10, 1, false, 5, 1),
       (3, now(), now(), 'Группа Английский', '2025-02-20', 'Kazakh', 3, 8, 1, false, 3, 2),

       -- Группы по Scratch
       (4, now(), now(), 'Группа Scratch 2', '2025-01-15', 'Kazakh', 3, 12, 3, false, 1, 2),
       (5, now(), now(), 'Группа Scratch 3', '2025-01-22', 'Russian', 3, 10, 4, false, 1, 3),

       -- Группы по Йоге
       (6, now(), now(), 'Йога для Детей', '2025-02-01', 'Russian', 2, 8, 5, false, 2, 4),
       (7, now(), now(), 'Йога для Подростков', '2025-02-05', 'Kazakh', 2, 8, 3, false, 2, 4),

       -- Группы по Английскому
       (8, now(), now(), 'Английский с нуля 1', '2025-03-01', 'Russian', 3, 9, 2, false, 3, 2),
       (9, now(), now(), 'Английский с нуля 2', '2025-03-10', 'Kazakh', 3, 9, 4, false, 3, 3),

       -- Группы по Рисованию
       (10, now(), now(), 'Акварель 1', '2025-02-10', 'Russian', 2, 6, 2, false, 4, 5),
       (11, now(), now(), 'Акварель 2', '2025-02-20', 'Kazakh', 2, 6, 1, false, 4, 5),

       -- Группы по Математике
       (12, now(), now(), 'Математика логикум 1', '2025-03-05', 'Russian', 3, 10, 3, false, 5, 1),
       (13, now(), now(), 'Математика логикум 2', '2025-03-15', 'Казахский', 3, 10, 2, false, 5, 2);

-- CHILD-GROUP связи
INSERT INTO child_group (child_id, group_id, created_date)
VALUES (1, 1, '2025-01-01'),  -- Alina в Scratch
       (2, 2, '2025-02-01'),  -- Adil в English
       (15, 1, '2025-02-01'), -- Samat в Scratch
       (16, 2, '2025-03-01'), -- Dana в Math
       (17, 3, '2025-05-01');
-- Marat в Math

-- ======= SCHEDULE TEMPLATES =======
-- Пн 16:00–17:00 (Scratch)
INSERT INTO schedule (id, created_date, update_date, start_time, end_time, day_of_week, time_zone)
VALUES (1, now(), now(), '16:00', '17:00', 'MONDAY', 'Asia/Almaty');

-- Ср 16:00–17:00 (Math)
INSERT INTO schedule (id, created_date, update_date, start_time, end_time, day_of_week, time_zone)
VALUES (2, now(), now(), '16:00', '17:00', 'WEDNESDAY', 'Asia/Almaty');

-- Пт 16:00–17:00 (English)
INSERT INTO schedule (id, created_date, update_date, start_time, end_time, day_of_week, time_zone)
VALUES (3, now(), now(), '16:00', '17:00', 'FRIDAY', 'Asia/Almaty');

-- Вт 17:30–18:30 (Art)
INSERT INTO schedule (id, created_date, update_date, start_time, end_time, day_of_week, time_zone)
VALUES (4, now(), now(), '17:30', '18:30', 'TUESDAY', 'Asia/Almaty');

-- Чт 18:00–19:00 (Python)
INSERT INTO schedule (id, created_date, update_date, start_time, end_time, day_of_week, time_zone)
VALUES (5, now(), now(), '18:00', '19:00', 'THURSDAY', 'Asia/Almaty');

-- Группа 1: Scratch (course_id = 1)
INSERT INTO lessons (id, created_date, update_date, lesson_number, topic, description, group_id, schedule_id, file_url, date)
VALUES
    (1, now(), now(), 1, 'Знакомство с Scratch', 'Введение в интерфейс среды Scratch. Создание первого проекта.', 1, 1, 'Lesson1.pdf', '2025-01-10'),
    (2, now(), now(), 2, 'Сцены и спрайты', 'Добавление спрайтов, изменение фонов, основы визуального дизайна.', 1, 1, 'Lesson2.pdf', '2025-01-12'),
    (3, now(), now(), 3, 'Движение объектов', 'Программирование перемещения спрайтов с помощью блоков.', 1, 1, 'Lesson3.pdf', '2025-01-14'),
    (4, now(), now(), 4, 'Анимация', 'Создание анимации через смену костюмов и координат.', 1, 1, 'Lesson4.pdf', '2025-01-16'),
    (5, now(), now(), 5, 'События и реакции', 'Использование событий (например, при нажатии клавиш) для запуска сценариев.', 1, 1, 'Lesson5.pdf', '2025-01-18'),
    (6, now(), now(), 6, 'Звуки и музыка', 'Добавление звуков и создание музыкальных эффектов.', 1, 1, 'Lesson6.pdf', '2025-01-20'),
    (7, now(), now(), 7, 'Циклы и повторения', 'Применение блоков повторения для оптимизации кода.', 1, 1, 'Lesson7.pdf', '2025-01-22'),
    (8, now(), now(), 8, 'Условия (if)', 'Создание интерактивности через условные блоки.', 1, 1, 'Lesson8.pdf', '2025-01-24'),
    (9, now(), now(), 9, 'Переменные', 'Введение в переменные. Отслеживание очков и счётчиков.', 1, 1, 'Lesson9.pdf', '2025-01-26'),
    (10, now(), now(), 10, 'Мини-игра: Лабиринт', 'Создание простой игры, где спрайт проходит через лабиринт.', 1, 1, 'Lesson10.pdf', '2025-01-28'),
    (11, now(), now(), 11, 'Итоговый проект: Моя игра', 'Работа над собственным проектом с использованием изученных элементов.', 1, 1, 'Lesson11.pdf', '2025-01-30'),
    (12, now(), now(), 12, 'Презентация проектов', 'Представление работ, обсуждение, обратная связь и закрепление знаний.', 1, 1, 'Lesson12.pdf', '2025-02-01');


-- Группа 2: Математика (course_id = 5)
INSERT INTO lessons (id, created_date, update_date, lesson_number, topic, description, group_id, schedule_id, file_url, date)
VALUES
    (13, now(), now(), 1, 'Логические задачи на сообразительность', 'Разбор простых логических задач на внимание и мышление.', 2, 2, '', '2025-02-15'),
    (14, now(), now(), 2, 'Шифры и коды', 'Решение задач с заменой символов, простые шифры и логика.', 2, 2, '', '2025-02-17'),
    (15, now(), now(), 3, 'Анализ и дедукция', 'Решение задач с условиями: кто где живёт, кто что делает.', 2, 2, '', '2025-02-19'),
    (16, now(), now(), 4, 'Последовательности и закономерности', 'Нахождение закономерностей в числовых и графических рядах.', 2, 2, '', '2025-02-21'),
    (17, now(), now(), 5, 'Головоломки с кубиками и блоками', 'Задачи на пространственное мышление и логику.', 2, 2, '', '2025-02-23'),
    (18, now(), now(), 6, 'Комбинаторика для детей', 'Счёт вариантов, простейшие принципы подсчёта.', 2, 2, '', '2025-02-25'),
    (19, now(), now(), 7, 'Математические ребусы', 'Цифровые кроссворды, расстановка чисел по условиям.', 2, 2, '', '2025-02-27'),
    (20, now(), now(), 8, 'Игры на стратегию и логику', 'Простые логические игры: крестики-нолики, Ним, и др.', 2, 2, '', '2025-03-01'),
    (21, now(), now(), 9, 'Движение по клеткам', 'Решение задач на клетчатом поле и логические маршруты.', 2, 2, '', '2025-03-03'),
    (22, now(), now(), 10, 'Проверка гипотез', 'Формирование логических предположений и их проверка.', 2, 2, '', '2025-03-05'),
    (23, now(), now(), 11, 'Проект: логическая игра', 'Создание собственной логической игры или головоломки.', 2, 2, '', '2025-03-07'),
    (24, now(), now(), 12, 'Турнир по логике', 'Соревновательное решение задач. Подведение итогов.', 2, 2, '', '2025-03-09');


-- Группа 5: Python (course_id = 6)
INSERT INTO lessons (id, created_date, update_date, lesson_number, topic, description, group_id, schedule_id, file_url, date)
VALUES
    (25, now(), now(), 1, 'Знакомство с Python', 'Что такое Python, где используется, установка и запуск.', 5, 5, '', '2025-01-22'),
    (26, now(), now(), 2, 'Переменные и вывод на экран', 'Создание переменных, функция print().', 5, 5, '', '2025-01-24'),
    (27, now(), now(), 3, 'Типы данных и операции', 'Числа, строки, булевы значения и арифметика.', 5, 5, '', '2025-01-26'),
    (28, now(), now(), 4, 'Ввод данных от пользователя', 'input(), преобразование типов и взаимодействие с пользователем.', 5, 5, '', '2025-01-28'),
    (29, now(), now(), 5, 'Условные конструкции if', 'Проверка условий и принятие решений.', 5, 5, '', '2025-01-30'),
    (30, now(), now(), 6, 'Циклы for и while', 'Повторяющиеся действия и счётчики.', 5, 5, '', '2025-02-01'),
    (31, now(), now(), 7, 'Списки и перебор элементов', 'Создание списков и работа с ними.', 5, 5, '', '2025-02-03'),
    (32, now(), now(), 8, 'Функции в Python', 'Объявление, вызов и параметры функций.', 5, 5, '', '2025-02-05'),
    (33, now(), now(), 9, 'Генерация случайных чисел', 'Модуль random и простые игры.', 5, 5, '', '2025-02-07'),
    (34, now(), now(), 10, 'Работа с условиями и циклами в проектах', 'Создание мини-программы с логикой.', 5, 5, '', '2025-02-09'),
    (35, now(), now(), 11, 'Проект: Угадай число', 'Создание простой консольной игры.', 5, 5, '', '2025-02-11'),
    (36, now(), now(), 12, 'Защита проектов', 'Презентация работ, рефлексия, идеи для продолжения.', 5, 5, '', '2025-02-13');


------------------------------------------------ADMINS------------------------------------------------------------------
INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (80, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77012223344',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'ADMIN');

INSERT INTO auth_users (id, created_date, update_date, phone_number, password, role)
VALUES (81, '2025-01-01 00:00:00', '2025-01-01 00:00:00', '77016667788',
        '$2a$10$DloIFFbe/Zak9m3M.bUh3O93DCYel873exKgir3l/3k7/xl2Zwzjy', 'ADMIN');

------------------------------------------------------------------------------------------------------------------------
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