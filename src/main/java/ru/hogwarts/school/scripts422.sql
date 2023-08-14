CREATE TABLE drivers
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    age     INTEGER,
    license BOOLEAN,
    cars_id SERIAL REFERENCES cars (id)
);

CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR,
    model VARCHAR,
    price NUMERIC
);