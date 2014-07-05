create table entities (
  id      INT NOT NULL,
  name    VARCHAR(100) NOT NULL,
  owned   VARCHAR(1) not null,
  shared  VARCHAR(1) not null,
  primary key (id)
);

create table users (
  id        INT NOT NULL,
  username  VARCHAR(100) NOT NULL,
  password  VARCHAR(100) NOT NULL,
  email     VARCHAR(250) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  enabled   VARCHAR(1) NOT NULL,
  entity_id INT not null,
  PRIMARY KEY (id)
);

create table events (
  id  int not null,
  type varchar(15) not null,
  tms timestamp not null,
  user_id int not null
);
alter table events
add constraint events_pk
primary key (id);

alter table events
add constraint events_user_id_fk
foreign key (user_id) references users (id);

create table authorities (
  user_id     INT NOT NULL,
  authority   VARCHAR(25) NOT NULL,
  PRIMARY KEY (user_id, authority)
);

create table tokens (
  id              int not null,
  value           varchar(40) not null,
  entity_id       int not null,
  email           varchar(250) not null,
  expiration_date timestamp not null,
  status          varchar(20) not null,
  type            varchar(20) not null,
  primary key (id),
  unique (email)
);

alter table authorities
    add foreign key (user_id) references users(id);
alter table users
  add foreign key (entity_id) references entities(id);
alter table tokens
  add foreign key (entity_id) references entities(id);