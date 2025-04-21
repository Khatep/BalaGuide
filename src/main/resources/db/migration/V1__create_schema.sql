CREATE TABLE auth_users
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,

    phone_number VARCHAR(255) NOT NULL,
    password     VARCHAR(255),
    role         VARCHAR(255),

    CONSTRAINT uk_auth_user_phone_number UNIQUE (phone_number),

    CONSTRAINT auth_user_role_check
        CHECK ((role)::text = ANY
               ((ARRAY [
                   'PARENT'::character varying,
                   'CHILD'::character varying,
                   'EDUCATION_CENTER'::character varying,
                   'TEACHER'::character varying,
                   'ADMIN'::character varying
                   ])::text[]))
);

CREATE TABLE bank_cards
(
    id                BIGSERIAL PRIMARY KEY,
    auth_user_id      BIGINT      NOT NULL,
    pan               VARCHAR(16) NOT NULL,
    card_expired_date VARCHAR(5)  NOT NULL,
    cvv               VARCHAR(3)  NOT NULL,
    holder_name       VARCHAR(255),

    CONSTRAINT fk_bank_card_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);

CREATE TABLE education_centers
(
    id              BIGSERIAL PRIMARY KEY,
    created_date    TIMESTAMP           NOT NULL,
    update_date     TIMESTAMP           NOT NULL,

    name            VARCHAR(255)        NOT NULL,
    phone_number    VARCHAR(20) UNIQUE  NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    address         VARCHAR(255)        NOT NULL,
    instagram_link  VARCHAR(255)        NOT NULL,
    date_of_created date                NOT NULL,
    balance         NUMERIC(10, 2)      NOT NULL,
    auth_user_id    BIGINT              NOT NULL UNIQUE,

    CONSTRAINT fk_education_center_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);

CREATE TABLE parents
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
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);

CREATE TABLE children
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
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_child_parent
        FOREIGN KEY (parent_id)
            REFERENCES parents (id)
            ON DELETE SET NULL,

    CONSTRAINT children_gender_check
        CHECK ((gender)::text = ANY
               ((ARRAY [
                   'MALE'::character varying,
                   'FEMALE'::character varying
                   ])::text[]))
);

CREATE TABLE courses
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

    education_center_id BIGINT         NOT NULL,

    CONSTRAINT fk_course_education_center
        FOREIGN KEY (education_center_id)
            REFERENCES education_centers (id)
            ON DELETE CASCADE,

    CONSTRAINT courses_course_category_check
        CHECK ((course_category)::text = ANY
               ((ARRAY [
                   'PROGRAMMING'::character varying,
                   'SPORT'::character varying,
                   'LANGUAGES'::character varying,
                   'ART'::character varying,
                   'MATH'::character varying
                   ])::text[]))

);

CREATE TABLE teachers
(
    id           BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,

    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    birth_date   DATE         NOT NULL,
    phone_number VARCHAR(15)  NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    salary       NUMERIC      NOT NULL,
    gender       VARCHAR(255) NOT NULL,

    auth_user_id BIGINT       NOT NULL UNIQUE,

    CONSTRAINT fk_teacher_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);

CREATE TABLE teacher_course
(
    course_id  BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,

    PRIMARY KEY (course_id, teacher_id),

    CONSTRAINT fk_teacher_course_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_teacher_course_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (id)
            ON DELETE CASCADE
);

CREATE TABLE groups
(
    id                   BIGSERIAL PRIMARY KEY,
    created_date         TIMESTAMP    NOT NULL,
    update_date          TIMESTAMP    NOT NULL,

    name                 VARCHAR(255) NOT NULL,
    language             VARCHAR(50),
    min_participants     INTEGER      NOT NULL CHECK (min_participants > 0),
    max_participants     INTEGER      NOT NULL CHECK (max_participants > 0),
    current_participants INTEGER      NOT NULL,
    group_full           BOOLEAN      NOT NULL,

    course_id            BIGINT       NOT NULL,
    teacher_id           BIGINT       NOT NULL,

    CONSTRAINT fk_group_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_group_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (id)
            ON DELETE CASCADE
);

CREATE TABLE child_group
(
    child_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,

    PRIMARY KEY (child_id, group_id),

    CONSTRAINT fk_child_group_child
        FOREIGN KEY (child_id)
            REFERENCES children (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_child_group_group
        FOREIGN KEY (group_id)
            REFERENCES groups (id)
            ON DELETE CASCADE
);

CREATE TABLE payments
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_date   TIMESTAMP(6)   NOT NULL,
    update_date    TIMESTAMP(6)   NOT NULL,
    payment_time   TIMESTAMP(6)   NOT NULL,

    amount         NUMERIC(38, 2) NOT NULL,
    percent_of_vat INTEGER        NOT NULL,
    payment_method VARCHAR(255)   NOT NULL,
    payment_status VARCHAR(255)   NOT NULL,
    transaction_id VARCHAR(255),

    child_id       BIGINT         NOT NULL,
    course_id      BIGINT         NOT NULL,
    parent_id      BIGINT         NOT NULL,

    CONSTRAINT fk_payment_child
        FOREIGN KEY (child_id)
            REFERENCES children (id),

    CONSTRAINT fk_payment_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id),

    CONSTRAINT fk_payment_parent
        FOREIGN KEY (parent_id)
            REFERENCES parents (id),

    CONSTRAINT payments_payment_status_check
        CHECK ((payment_status)::TEXT = ANY (
            ARRAY [
                'PAID'::VARCHAR,
                'CANCELLED'::VARCHAR,
                'PENDING'::VARCHAR
                ]::TEXT[]))
);

CREATE TABLE receipts
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_date   TIMESTAMP(6) NOT NULL,
    update_date    TIMESTAMP(6) NOT NULL,

    payment_id     BIGINT UNIQUE,
    receipt_number UUID UNIQUE,
    description    VARCHAR(255),
    file_url       VARCHAR(255),
    issuer         VARCHAR(255),

    CONSTRAINT fk_receipt_payment
        FOREIGN KEY (payment_id)
            REFERENCES payments (id)
);

CREATE TABLE response_metadata
(
    id            BIGSERIAL PRIMARY KEY,
    response_code VARCHAR(10) NOT NULL UNIQUE,
    message       TEXT
);

