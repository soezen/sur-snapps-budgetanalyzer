DROP TABLE product IF EXISTS;

CREATE TABLE product
(
    id INTEGER IDENTITY PRIMARY KEY,
    name      VARCHAR(250),
    description   VARCHAR(250)
);

