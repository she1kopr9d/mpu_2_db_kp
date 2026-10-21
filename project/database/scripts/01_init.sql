SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `Workload`;
DROP TABLE IF EXISTS `Standard`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Teacher`;
DROP TABLE IF EXISTS `Group`;
DROP TABLE IF EXISTS `AcademycYear`;
DROP TABLE IF EXISTS `ClassType`;
DROP TABLE IF EXISTS `Subject`;
DROP TABLE IF EXISTS `Role`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `Role` (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE `Subject` (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE `ClassType` (
    class_type_id INT AUTO_INCREMENT PRIMARY KEY,
    class_type_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE `AcademycYear` (
    academic_year_id INT AUTO_INCREMENT PRIMARY KEY,
    academic_year_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE `Group` (
    group_id INT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE `Teacher` (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    academic_degree VARCHAR(100),
    work_experience INT NOT NULL CHECK (work_experience >= 0),
    work_position VARCHAR(100)
);

CREATE TABLE `User` (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    role_id INT NOT NULL,
    teacher_id INT UNIQUE,
    FOREIGN KEY (role_id) REFERENCES `Role`(role_id) ON DELETE RESTRICT,
    FOREIGN KEY (teacher_id) REFERENCES `Teacher`(teacher_id) ON DELETE SET NULL
);

CREATE TABLE `Standard` (
    standard_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    class_type_id INT NOT NULL,
    academic_year_id INT NOT NULL,
    hours INT NOT NULL CHECK (hours > 0),
    UNIQUE (subject_id, class_type_id, academic_year_id),
    FOREIGN KEY (subject_id) REFERENCES `Subject`(subject_id) ON DELETE RESTRICT,
    FOREIGN KEY (class_type_id) REFERENCES `ClassType`(class_type_id) ON DELETE RESTRICT,
    FOREIGN KEY (academic_year_id) REFERENCES `AcademycYear`(academic_year_id) ON DELETE RESTRICT
);

CREATE TABLE `Workload` (
    workload_id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT NOT NULL,
    group_id INT NOT NULL,
    standard_id INT NOT NULL,
    hours INT NOT NULL CHECK (hours > 0),
    UNIQUE (teacher_id, group_id, standard_id),
    FOREIGN KEY (teacher_id) REFERENCES `Teacher`(teacher_id) ON DELETE RESTRICT,
    FOREIGN KEY (group_id) REFERENCES `Group`(group_id) ON DELETE RESTRICT,
    FOREIGN KEY (standard_id) REFERENCES `Standard`(standard_id) ON DELETE RESTRICT
);

INSERT INTO `Role` (role_name) VALUES ('ADMIN'), ('TEACHER');

INSERT INTO `Teacher` (first_name, last_name, middle_name, academic_degree, work_experience, work_position) VALUES
('Иван', 'Иванов', 'Иванович', 'Кандидат технических наук', 15, 'Доцент'),
('Петр', 'Петров', 'Петрович', 'Доктор физико-математических наук', 22, 'Профессор'),
('Анна', 'Сидорова', 'Сергеевна', NULL, 5, 'Ассистент'),
('Дмитрий', 'Кузнецов', 'Андреевич', 'Кандидат наук', 8, 'Старший преподаватель');

INSERT INTO `User` (login, password_hash, role_id, teacher_id) VALUES
('admin', '$2a$10$ExU1fY1tGs0GaHSISSmyI.zac7UGB6/jmbpZdI7kRlwds0vP.mrZq', 1, NULL),
('ivanov', '$2a$10$ExU1fY1tGs0GaHSISSmyI.zac7UGB6/jmbpZdI7kRlwds0vP.mrZq', 2, 1),
('petrov', '$2a$10$ExU1fY1tGs0GaHSISSmyI.zac7UGB6/jmbpZdI7kRlwds0vP.mrZq', 2, 2);

INSERT INTO `Subject` (subject_name) VALUES 
('Базы данных'), 
('Объектно-ориентированное программирование'), 
('Высшая математика'),
('Физика');

INSERT INTO `ClassType` (class_type_name) VALUES 
('Лекция'), 
('Практическое занятие'), 
('Лабораторная работа');

INSERT INTO `AcademycYear` (academic_year_name) VALUES 
('2023/2024'), 
('2024/2025');

INSERT INTO `Group` (group_name) VALUES 
('ИВТ-21'), 
('ИВТ-22'), 
('ПИ-21'), 
('ИБ-23');

INSERT INTO `Standard` (subject_id, class_type_id, academic_year_id, hours) VALUES
(1, 1, 2, 40),
(1, 3, 2, 40),
(2, 1, 2, 20),
(3, 2, 2, 30);

INSERT INTO `Workload` (teacher_id, group_id, standard_id, hours) VALUES
(1, 1, 1, 40),
(1, 2, 1, 40),
(4, 1, 2, 40),
(4, 2, 2, 40),
(2, 1, 3, 10),
(3, 1, 3, 10),
(2, 3, 4, 30);