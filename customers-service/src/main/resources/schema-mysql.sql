DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS customer_phonenumbers;


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

create table if not exists customer_phonenumbers (
             customer_id INTEGER NOT NULL,
             type VARCHAR(50),
             number VARCHAR(50),
             FOREIGN KEY (customer_id) REFERENCES customers(id)
);

