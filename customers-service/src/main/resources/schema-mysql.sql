SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS customer_phonenumbers;
DROP TABLE IF EXISTS customers;

SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE IF NOT EXISTS customers (
                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         customer_id VARCHAR(50) UNIQUE,
                                         last_name VARCHAR(50),
                                         first_name VARCHAR(50),
                                         email_address VARCHAR(50),
                                         street_address VARCHAR(50),
                                         postal_code VARCHAR(50),
                                         city VARCHAR(50),
                                         province VARCHAR(50),
                                         username VARCHAR(50),
                                         password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS customer_phonenumbers (
                                                     customer_id INTEGER NOT NULL,
                                                     type VARCHAR(50),
                                                     number VARCHAR(50),
                                                     FOREIGN KEY (customer_id) REFERENCES customers(id)
);
