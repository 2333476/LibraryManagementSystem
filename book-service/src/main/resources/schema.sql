DROP TABLE IF EXISTS books;

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       book_id VARCHAR(50) UNIQUE NOT NULL,
                       title VARCHAR(100) NOT NULL,
                       author VARCHAR(100),
                       isbn VARCHAR(50),
                       price NUMERIC(10, 2),
                       published_date VARCHAR(50)
);
