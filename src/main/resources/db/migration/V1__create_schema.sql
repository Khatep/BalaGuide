CREATE TABLE auth_user
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,

    phone_number VARCHAR(255) NOT NULL,
    password     VARCHAR(255),
    role         VARCHAR(255),

    CONSTRAINT uk_auth_user_phone_number UNIQUE (phone_number)
);

CREATE TABLE bank_card
(
    id                BIGSERIAL PRIMARY KEY,
    auth_user_id      BIGINT      NOT NULL,
    pan               VARCHAR(16) NOT NULL,
    card_expired_date VARCHAR(5)  NOT NULL,
    cvv               VARCHAR(3)  NOT NULL,
    holder_name       VARCHAR(255),

    CONSTRAINT fk_bank_card_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_user (id)
            ON DELETE CASCADE
);

CREATE TABLE education_center
(
    id             BIGSERIAL PRIMARY KEY,
    created_date   TIMESTAMP           NOT NULL,
    update_date    TIMESTAMP           NOT NULL,

    name           VARCHAR(255)        NOT NULL,
    phone_number   VARCHAR(20) UNIQUE  NOT NULL,
    email          VARCHAR(255) UNIQUE NOT NULL,
    address        VARCHAR(255)        NOT NULL,
    instagram_link VARCHAR(255)        NOT NULL,
    role           VARCHAR(255),
    balance        NUMERIC(10, 2)      NOT NULL,
    auth_user_id   BIGINT              NOT NULL UNIQUE,

    CONSTRAINT fk_education_center_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_user (id)
            ON DELETE CASCADE
);

CREATE TABLE parent
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP      NOT NULL,
    update_date  TIMESTAMP      NOT NULL,

    first_name   VARCHAR(255)   NOT NULL,
    last_name    VARCHAR(255)   NOT NULL,
    phone_number VARCHAR(255)   NOT NULL UNIQUE,
    birth_date   DATE           NOT NULL,
    email        VARCHAR(255)   NOT NULL UNIQUE,
    address      VARCHAR(255),
    balance      NUMERIC(19, 2) NOT NULL CHECK (balance >= 0),
    auth_user_id BIGINT         NOT NULL UNIQUE,

    CONSTRAINT fk_parent_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_user (id)
            ON DELETE CASCADE
);

CREATE TABLE child
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,

    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    email        VARCHAR(255) NOT NULL UNIQUE,
    birth_date   DATE         NOT NULL,
    auth_user_id BIGINT       NOT NULL UNIQUE,
    gender       VARCHAR(255) NOT NULL,

    parent_id    BIGINT,

    CONSTRAINT fk_child_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_user (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_child_parent
        FOREIGN KEY (parent_id)
            REFERENCES parent (id)
            ON DELETE SET NULL
);

CREATE TABLE course
(
    id                  BIGSERIAL PRIMARY KEY,
    created_date        TIMESTAMP      NOT NULL,
    update_date         TIMESTAMP      NOT NULL,

    course_name         VARCHAR(100)   NOT NULL,
    description         VARCHAR(500)   NOT NULL,
    course_category     VARCHAR(255)   NOT NULL,
    age_range           VARCHAR(255)   NOT NULL,
    price               NUMERIC(10, 2) NOT NULL,
    durability          INTEGER        NOT NULL,

    education_center_id BIGINT,

    CONSTRAINT fk_course_education_center
        FOREIGN KEY (education_center_id)
            REFERENCES education_center (id)
            ON DELETE SET NULL
);

CREATE TABLE teacher
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,

    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    birth_date   DATE         NOT NULL,
    phone_number VARCHAR(15)  NOT NULL,
    password     VARCHAR(60)  NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    salary       NUMERIC      NOT NULL,
    gender       VARCHAR(255) NOT NULL,

    auth_user_id BIGSERIAL    NOT NULL UNIQUE,

    CONSTRAINT fk_teacher_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_user (id)
            ON DELETE CASCADE
);

CREATE TABLE teacher_course
(
    id         BIGSERIAL PRIMARY KEY,
    teacher_id BIGSERIAL NOT NULL,
    course_id  BIGSERIAL NOT NULL,

    CONSTRAINT fk_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teacher (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_course
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE CASCADE
);

CREATE TABLE group
(
    id                   BIGSERIAL PRIMARY KEY,
    created_date         TIMESTAMP    NOT NULL,
    update_date          TIMESTAMP    NOT NULL,

    name                 VARCHAR(255) NOT NULL,
    language             VARCHAR(50),
    min_participants     INTEGER      NOT NULL CHECK (min_participants > 0),
    max_participants     INTEGER      NOT NULL CHECK (max_participants > 0),
    current_participants INTEGER      NOT NULL,

    course_id            BIGINT       NOT NULL,
    teacher_id           BIGINT       NOT NULL,

    CONSTRAINT fk_group_course
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_group_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teacher (id)
            ON DELETE CASCADE
);

CREATE TABLE receipt
(
    id             BIGSERIAL PRIMARY KEY,
    created_date   TIMESTAMP   NOT NULL,
    update_date    TIMESTAMP   NOT NULL,

    percent_of_vat INTEGER     NOT NULL,
    payment_number VARCHAR(255),

    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50),

    parent_id      BIGINT      NOT NULL,
    course_id      BIGINT      NOT NULL,

    CONSTRAINT fk_receipt_parent
        FOREIGN KEY (parent_id)
            REFERENCES parent (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_receipt_child
        FOREIGN KEY (child_id)
            REFERENCES child (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_receipt_course
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE SET NULL
);

CREATE TABLE response_metadata
(
    id            BIGSERIAL PRIMARY KEY,
    response_code VARCHAR(10) NOT NULL UNIQUE,
    message       TEXT
);

