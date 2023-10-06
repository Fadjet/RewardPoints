CREATE TABLE customer
(
    id           SERIAL PRIMARY KEY,
    first_name   VARCHAR(64),
    last_name    VARCHAR(64),
    created_date timestamp
);

CREATE TABLE transaction
(
    id            SERIAL PRIMARY KEY,
    amount        numeric(10, 2),
    reward_points integer,
    customer_id   integer,
    created_date  timestamp
);