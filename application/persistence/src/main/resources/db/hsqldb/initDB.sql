---------------
--  ENTITIES --
---------------

drop TABLE entities IF EXISTS CASCADE;
create table entities (
  id      VARCHAR(40) NOT NULL,
  name    VARCHAR(100) NOT NULL,
  owned   BOOLEAN not null,
  shared  BOOLEAN not null,
  version INT not null
);

ALTER TABLE entities
    add CONSTRAINT PK_ENTITIES
    PRIMARY KEY (id);

------------
--  USERS --
------------

drop TABLE users if EXISTS CASCADE;
create table users (
  id        VARCHAR(40) NOT NULL ,
  username  VARCHAR(100) NOT NULL,
  password  VARCHAR(100) NOT NULL,
  email     VARCHAR(250) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  enabled   BOOLEAN NOT NULL,
  entity_id VARCHAR(40) not null,
  version INT not null
);

alter table users
    add CONSTRAINT PK_USERS
    PRIMARY KEY (id);

alter table users
    add CONSTRAINT FK_USERS_ENTITY_ID
    foreign key (entity_id) references entities(id);

----------------
--  ACCOUNTS  --
----------------

drop table accounts if EXISTS CASCADE;
create table accounts (
  id        VARCHAR(40) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  user_id   VARCHAR(40) not NULL,
  version   int not NULL
);

alter table accounts
    add CONSTRAINT PK_ACCOUNTS
    PRIMARY KEY (id);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ENTITY_ID
    FOREIGN KEY (user_id)
    REFERENCES users(id);

drop table h_accounts if EXISTS CASCADE;
create table h_accounts (
  rev       int not NULL,
  revtype   TINYINT,
  ID        VARCHAR(40) not NULL,
  name      VARCHAR(100),
  user_id   VARCHAR(40),
  PRIMARY KEY (id, rev)
);

--------------
--  EVENTS  --
--------------

drop table events if EXISTS CASCADE;
create table events (
  id  VARCHAR(40) not null,
  type varchar(20) not null,
  tms DATETIME not null,
  user_id VARCHAR(40) not null,
  subject_id  VARCHAR(40),
  version INT not null
);

alter TABLE events
    add CONSTRAINT PK_EVENTS
    PRIMARY KEY (id);

alter table events
    add constraint FK_EVENTS_USER_ID
    foreign key (user_id) references users (id);


------------------
--  AUTHORITIES --
------------------

drop TABLE authorities if EXISTS CASCADE;
create table authorities (
  user_id     VARCHAR(40) NOT NULL,
  authority   VARCHAR(25) NOT NULL
);

ALTER TABLE authorities
    add CONSTRAINT PK_AUTHORITIES
    PRIMARY KEY (user_id, authority);

alter table authorities
    add CONSTRAINT FK_AUTHORITIES_USER_ID
    foreign key (user_id) references users(id);


--------------
--  TOKENS  --
--------------

drop TABLE tokens if EXISTS CASCADE;
create table tokens (
  id              VARCHAR(40) not null,
  value           varchar(40) not null,
  entity_id       VARCHAR(40) not null,
  email           varchar(250) not null,
  expiration_date timestamp not null,
  status          varchar(20) not null,
  type            varchar(20) not null,
  version INT not null
);

ALTER TABLE tokens
    add CONSTRAINT PK_TOKENS
    PRIMARY KEY (id);

ALTER TABLE tokens
    add CONSTRAINT UQ_TOKEN_EMAIL
    UNIQUE (email);

alter table tokens
    add CONSTRAINT FK_TOKENS_ENTITY_ID
    foreign key (entity_id)
    references entities(id);