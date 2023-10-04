CREATE TABLE orders(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    book_isbn VARCHAR(255) NOT NULL,
    book_name VARCHAR(255),
    book_price float8,
    quantity int NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    version integer NOT NULL
);