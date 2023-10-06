CREATE TABLE if not exists customer
(
    id           SERIAL PRIMARY KEY,
    first_name   VARCHAR(64),
    last_name    VARCHAR(64),
    created_date timestamp
);

CREATE TABLE if not exists transaction
(
    id            SERIAL PRIMARY KEY,
    amount        numeric(10, 2),
    customer_id   integer,
    created_date  timestamp
);