-- Désactive les vérifications des clés étrangères

DROP TABLE IF EXISTS employee_phonenumbers;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS department_positions;
DROP TABLE IF EXISTS departments;


-- Table des départements
CREATE TABLE IF NOT EXISTS departments (
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           department_id VARCHAR(36) UNIQUE,
                                           name VARCHAR(50),
                                           head_count INTEGER
);

-- Table des employés (CORRIGÉ ICI ↓)
CREATE TABLE IF NOT EXISTS employees (
                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         employee_id VARCHAR(36) UNIQUE,
                                         first_name VARCHAR(50),
                                         last_name VARCHAR(50),
                                         email_address VARCHAR(50),
                                         phone_number VARCHAR(50),
                                         salary DECIMAL(19,4),
                                         commission_rate DECIMAL(3,1),
                                         department VARCHAR(50), -- ✅ NOUVELLE COLONNE POUR MATCHER AVEC L'ENUM
                                         department_id INTEGER,
                                         street_address VARCHAR(50),
                                         city VARCHAR(50),
                                         province VARCHAR(50),
                                         country VARCHAR(50),
                                         postal_code VARCHAR(9),
                                         FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Table des numéros de téléphone des employés
CREATE TABLE IF NOT EXISTS employee_phonenumbers (
                                                     employee_id INTEGER NOT NULL,
                                                     type VARCHAR(50),
                                                     number VARCHAR(50),
                                                     FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Table des postes dans les départements
CREATE TABLE IF NOT EXISTS department_positions (
                                                    department_id INTEGER,
                                                    title VARCHAR(50),
                                                    code VARCHAR(50),
                                                    FOREIGN KEY (department_id) REFERENCES departments(id)
);
