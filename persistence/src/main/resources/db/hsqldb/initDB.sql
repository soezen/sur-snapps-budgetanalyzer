DROP TABLE product IF EXISTS;

CREATE TABLE product
(
    id INTEGER IDENTITY PRIMARY KEY,
    name      VARCHAR(250),
    description   VARCHAR(250)
);

DROP TABLE users IF EXISTS;

create table users (
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled  BOOLEAN NOT NULL,
  PRIMARY KEY (username)
);

drop table authorities if exists;

create table authorities (
  username  VARCHAR (100) NOT NULL,
  authority VARCHAR (25) NOT NULL,
  PRIMARY KEY (username, authority)
)