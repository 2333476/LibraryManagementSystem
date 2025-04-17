DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS department_positions;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS employee_phonenumbers;


create table if not exists employees (
                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         employee_id VARCHAR(36),
                                         first_name VARCHAR(50),
                                         last_name VARCHAR(50),
                                         email_address VARCHAR(50),
                                         phone_number VARCHAR(50),
                                         salary DECIMAL(19,4),
                                         commission_rate DECIMAL(3,1),
                                         department VARCHAR(50),
                                         street_address VARCHAR (50),
                                         city VARCHAR (50),
                                         province VARCHAR (50),
                                         country VARCHAR (50),
                                         postal_code VARCHAR (9)
);

create table if not exists department_positions (
                                                    department_id INTEGER,
                                                    title VARCHAR(50),
                                                    code VARCHAR(50)
);

create table if not exists departments (
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           department_id VARCHAR(36) UNIQUE,
                                           name VARCHAR(50),
                                           head_count INTEGER
);

create table if not exists employee_phonenumbers (
                                                     employee_id INTEGER,
                                                     type VARCHAR(50),
                                                     number VARCHAR(50)
);