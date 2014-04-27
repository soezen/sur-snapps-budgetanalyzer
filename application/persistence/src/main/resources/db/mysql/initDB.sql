drop table entities;

create table entities (
  id      INT AUTO_INCREMENT NOT NULL,
  name    VARCHAR (100) NOT NULL,
  owned   boolean not null,
  shared  boolean not null,
  primary key (id)
);

DROP TABLE users;

create table users (
  id        INT AUTO_INCREMENT NOT NULL,
  username  VARCHAR(100) NOT NULL,
  password  VARCHAR(100) NOT NULL,
  email     VARCHAR(250) NOT NULL,
  enabled   BOOLEAN NOT NULL,
  admin     boolean not null,
  entity_id INT NOT NULL,
  PRIMARY KEY (id)
);

drop table authorities;

create table authorities (
  user_id     INT NOT NULL,
  authority   VARCHAR (25) NOT NULL,
  PRIMARY KEY (user_id, authority)
);

drop table tokens;

create table tokens (
  id              int auto_increment not null,
  token           varchar (40) not null,
  entity_id       int not null,
  expiration_date DATETIME not null,
  primary key (id)
);

alter table authorities
    add foreign key (user_id) references users(id);
alter table users
  add foreign key (entity_id) references entities(id);
alter table tokens
  add foreign key (entity_id) references entities(id);