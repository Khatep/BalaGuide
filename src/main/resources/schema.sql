-- Gender Enum
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');

-- Category Enum
CREATE TYPE category AS ENUM ('PROGRAMMING', 'SPORT', 'LANGUAGES', 'ART', 'MATH');

-- Table for Education Center
CREATE TABLE education_center (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date_of_created DATE NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    instagram_link VARCHAR(255)
);

-- Table for Course
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    educational_center_id BIGINT,
    category category NOT NULL,
    age_range VARCHAR(50),
    price DECIMAL(10, 2),
    durability INT,
    address TEXT,
    max_participants INT,
    current_participants INT,
    course_materials VARCHAR(1024),
    CONSTRAINT fk_course_education_center FOREIGN KEY (educational_center_id) REFERENCES education_center(id)
);

-- Table for Teacher
CREATE TABLE teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone_number VARCHAR(20),
    password VARCHAR(255),
    gender gender
);

-- Table for Parent
CREATE TABLE parent (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    email VARCHAR(255),
    password VARCHAR(255),
    address TEXT,
    balance DECIMAL(10, 2)
);

-- Table for Child
CREATE TABLE child (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone_number VARCHAR(20),
    password VARCHAR(255),
    gender gender,
    parent_id BIGINT,
    CONSTRAINT fk_child_parent FOREIGN KEY (parent_id) REFERENCES parent(id)
);

-- Table for Teacher_Course (many-to-many relation between Teacher and Course)
CREATE TABLE teacher_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_id BIGINT,
    course_id BIGINT,
    CONSTRAINT fk_teacher_course_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    CONSTRAINT fk_teacher_course_course FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Table for linking Child and Course (many-to-many relation between Child and Course)
CREATE TABLE child_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT,
    course_id BIGINT,
    CONSTRAINT fk_child_course_child FOREIGN KEY (child_id) REFERENCES child(id),
    CONSTRAINT fk_child_course_course FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Table for linking Parent and favorite Courses (many-to-many relation between Parent and Course)
CREATE TABLE parent_favorite_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT,
    course_id BIGINT,
    CONSTRAINT fk_parent_favorite_course_parent FOREIGN KEY (parent_id) REFERENCES parent(id),
    CONSTRAINT fk_parent_favorite_course_course FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Table for linking Child and their Interests (many-to-many relation between Child and Category)
CREATE TABLE child_interest (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT,
    category category,
    CONSTRAINT fk_child_interest_child FOREIGN KEY (child_id) REFERENCES child(id)
);
