drop table entities;

create table entities (
  id      NUMBER NOT NULL,
  name    VARCHAR2(100) NOT NULL,
  owned   VARCHAR2(1) not null,
  shared  VARCHAR2(1) not null,
  primary key (id)
);

create sequence seq_entities;

create or replace trigger id_entities
  before insert on entities
  for each row
    begin
      select seq_entities.nextval
      into :new.id
      from dual;
    end;
  /


DROP TABLE users;

create table users (
  id        NUMBER NOT NULL,
  username  VARCHAR2(100) NOT NULL,
  password  VARCHAR2(100) NOT NULL,
  email     VARCHAR2(250) NOT NULL,
  name      VARCHAR2(100) NOT NULL,
  enabled   VARCHAR2(1) NOT NULL,
  entity_id NUMBER not null,
  PRIMARY KEY (id)
);

drop table events;
create table events (
  id  number not null,
  type varchar2(15) not null,
  tms timestamp not null,
  user_id number not null
);
alter table events
add constraint events_pk
primary key (id);

alter table events
add constraint events_user_id_fk
foreign key (user_id) references users (id);

drop table authorities;

create table authorities (
  user_id     NUMBER NOT NULL,
  authority   VARCHAR2(25) NOT NULL,
  PRIMARY KEY (user_id, authority)
);

drop table tokens;

create table tokens (
  id              number not null,
  value           varchar2(40) not null,
  entity_id       number not null,
  email           varchar2(250) not null,
  expiration_date timestamp not null,
  status          varchar2(20) not null,
  type            varchar2(20) not null,
  primary key (id),
  unique (email)
);

create sequence seq_tokens;

create or replace trigger id_tokens
  before insert on tokens
  for each row
  begin
    select seq_tokens.nextval
    into :new.id
    from dual;
  end;
/

alter table authorities
    add foreign key (user_id) references users(id);
alter table users
  add foreign key (entity_id) references entities(id);
alter table tokens
  add foreign key (entity_id) references entities(id);