DROP TABLE product IF EXISTS;

CREATE TABLE product
(
    id INTEGER IDENTITY PRIMARY KEY,
    name      VARCHAR(250),
    description   VARCHAR(250)
);

DROP TABLE users IF EXISTS;

create table users (
  username VARCHAR(100),
  password VARCHAR(100),
  enabled  BOOLEAN
);

drop table authorities if exists;

create table authorities (
  username  VARCHAR (100),
  authority VARCHAR (25)
)