-- Вставка данных в education_center
INSERT INTO education_center (id, name, date_of_created, phone_number, address, instagram_link)
VALUES (101, 'Code School', '2021-03-10', '87731581222', '789 Secondary St, Townsville', 'https://instagram.com/codeschool');

-- Вставка данных в course
INSERT INTO course (id, name, description, educational_center_id, category, age_range, price, durability, address, max_participants, current_participants, course_materials)
VALUES (101, 'Python for Beginners', 'Start your journey with Python', 101, 'PROGRAMMING', '8-16', 200.00, 40, '789 Secondary St, Townsville', 15, 5, 'Course book, Online materials');

INSERT INTO course (id, name, description, educational_center_id, category, age_range, price, durability, address, max_participants, current_participants, course_materials)
VALUES (102, 'Web Development Basics', 'Learn HTML, CSS, and JavaScript', 101, 'PROGRAMMING', '10-18', 350.00, 60, '123 Main St, Cityville', 20, 10, 'Course book, Online materials, Projects');

-- Вставка данных в teacher
INSERT INTO teacher (id, first_name, last_name, date_of_birth, phone_number, password, gender)
VALUES (101, 'Erzhan', 'Khatep', '1990-05-12', '87761080680', 'securepassword456', 'MALE');

INSERT INTO teacher (id, first_name, last_name, date_of_birth, phone_number, password, gender)
VALUES (102, 'ALi', 'Utepov', '2000-11-30', '87771467852', 'securepassword789', 'MALE');

-- Вставка данных в parent
INSERT INTO parent (id, first_name, last_name, phone_number, email, password, address, balance)
VALUES (101, 'Nurgali', 'Harris', '123', 'nurgaloi@example.com', 'parentpassword123', 'Saina, 73', 250.00);

-- Вставка данных в child
INSERT INTO child (id, first_name, last_name, date_of_birth, phone_number, password, gender, parent_id)
VALUES (101, 'Ergali', 'Harris', '2012-04-20', '12345', 'childpassword123', 'MALE', 101);

INSERT INTO child (id, first_name, last_name, date_of_birth, phone_number, password, gender, parent_id)
VALUES (102, 'Zhansaya', 'Li', '2015-02-15', '12345678', 'childpassword456', 'FEMALE', 101);

-- Вставка данных в teacher_course
INSERT INTO teacher_course (id, teacher_id, course_id)
VALUES (101, 101, 101);

INSERT INTO teacher_course (id, teacher_id, course_id)
VALUES (102, 102, 102);

-- Вставка данных в child_course
INSERT INTO child_course (id, child_id, course_id)
VALUES (101, 101, 101);

INSERT INTO child_course (id, child_id, course_id)
VALUES (102, 102, 102);

-- Вставка данных в parent_favorite_course
INSERT INTO parent_favorite_course (id, parent_id, course_id)
VALUES (101, 101, 101);

INSERT INTO parent_favorite_course (id, parent_id, course_id)
VALUES (102, 101, 102);

-- Вставка данных в child_interest
INSERT INTO child_interest (id, child_id, category)
VALUES (101, 101, 'PROGRAMMING');

INSERT INTO child_interest (id, child_id, category)
VALUES (102, 102, 'PROGRAMMING');
