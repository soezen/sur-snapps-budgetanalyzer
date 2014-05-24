drop table entities if exists;

create table entities (
  id      INT GENERATED by default as identity NOT NULL,
  name    VARCHAR (100) NOT NULL,
  owned   boolean not null,
  shared  boolean not null,
  primary key (id)
);

DROP TABLE users IF EXISTS;

create table users (
  id        INT GENERATED by default AS identity NOT NULL,
  username  VARCHAR(100) NOT NULL,
  password  VARCHAR(100) NOT NULL,
  email     VARCHAR(250) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  enabled   BOOLEAN NOT NULL,
  entity_id INT not null,
  PRIMARY KEY (id)
);

drop table authorities if exists;

create table authorities (
  user_id     INT NOT NULL,
  authority   VARCHAR (25) NOT NULL,
  PRIMARY KEY (user_id, authority)
);

drop table tokens if exists;

create table tokens (
  id              int generated by default as identity not null,
  value           varchar (40) not null,
  entity_id       int not null,
  email           varchar(250) not null,
  expiration_date DATETIME not null,
  status          varchar(20) not null,
  primary key (id),
  unique (email)
);

alter table authorities
    add foreign key (user_id) references users(id);
alter table users
  add foreign key (entity_id) references entities(id);
alter table tokens
  add foreign key (entity_id) references entities(id);