drop TABLE entities IF EXISTS CASCADE;

create table entities (
  id      VARCHAR(40) NOT NULL,
  name    VARCHAR(100) NOT NULL,
  owned   BOOLEAN not null,
  shared  BOOLEAN not null,
  version INT not null,
  PRIMARY key (id)
);

drop TABLE users if EXISTS CASCADE;
create table users (
  id        VARCHAR(40) NOT NULL ,
  username  VARCHAR(100) NOT NULL,
  password  VARCHAR(100) NOT NULL,
  email     VARCHAR(250) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  enabled   BOOLEAN NOT NULL,
  entity_id VARCHAR(40) not null,
  version INT not null,
  PRIMARY key (id)
);

drop table events if EXISTS CASCADE;
create table events (
  id  VARCHAR(40) not null,
  type varchar(20) not null,
  tms DATETIME not null,
  user_id VARCHAR(40) not null,
  subject_id  VARCHAR(40),
  version INT not null,
  PRIMARY key (id)
);

alter table events
add constraint events_user_id_fk
foreign key (user_id) references users (id);

drop TABLE authorities if EXISTS CASCADE;
create table authorities (
  user_id     VARCHAR(40) NOT NULL,
  authority   VARCHAR(25) NOT NULL,
  PRIMARY KEY (user_id, authority)
);

drop TABLE tokens if EXISTS CASCADE;
create table tokens (
  id              VARCHAR(40) not null,
  value           varchar(40) not null,
  entity_id       VARCHAR(40) not null,
  email           varchar(250) not null,
  expiration_date timestamp not null,
  status          varchar(20) not null,
  type            varchar(20) not null,
  version INT not null,
  PRIMARY KEY (ID),
  unique (email)
);

alter table authorities
    add foreign key (user_id) references users(id);
alter table users
  add foreign key (entity_id) references entities(id);
alter table tokens
  add foreign key (entity_id) references entities(id);