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
-- TODO find out max length of iban

drop table accounts if EXISTS CASCADE;
create table accounts (
  id        VARCHAR(40) NOT NULL,
  name      VARCHAR(100) NOT NULL,
  type      VARCHAR(50) not null,
  balance   DOUBLE NOT NULL,
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
  type      VARCHAR(50),
  balance   DOUBLE NOT NULL,
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

------------------
--  CATEGORIES  --
------------------

drop table categories if EXISTS CASCADE;
create table categories (
  id              VARCHAR(40) not null,
  name            VARCHAR(200) not null,
  parent          VARCHAR(40),
  version         int not null
);

alter table categories
    add CONSTRAINT PK_CATEGORIES
    PRIMARY KEY (id);

alter table categories
    add CONSTRAINT FK_CATEGORIES_PARENT
    FOREIGN KEY (parent)
    REFERENCES categories (id);

drop table h_categories IF EXISTS CASCADE;
create TABLE h_categories (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  name            VARCHAR(200),
  parent          VARCHAR(40),
  PRIMARY KEY (id, rev)
);


--------------------
--  PRODUCT_TYPES --
--------------------

drop table product_types if EXISTS CASCADE;
create table product_types (
  id              VARCHAR(40) not null,
  name            VARCHAR(200) not null,
  category_id     VARCHAR(40) not null,
  version         int not null
);

alter table product_types
    add CONSTRAINT PK_PRODUCT_TYPES
    PRIMARY KEY (id);

alter table product_types
    add CONSTRAINT FK_PRODUCT_TYPES_CATEGORY
    FOREIGN KEY (category_id)
    REFERENCES categories (id);

drop table h_product_types if EXISTS CASCADE;
create table h_product_types (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  name            VARCHAR(200),
  category_id     VARCHAR(40),
  PRIMARY KEY (id, rev)
);

----------------
--  PRODUCTS  --
----------------

drop table products if EXISTS CASCADE;
create table products (
  id              VARCHAR(40) not null,
  name            VARCHAR(200) not null,
  type            VARCHAR(40) not null,
  version         int not null
);

ALTER TABLE products
    ADD CONSTRAINT PK_PRODUCTS
    PRIMARY KEY (id);

alter TABLE products
    add CONSTRAINT FK_PRODUCTS_TYPE
    FOREIGN KEY (type)
    REFERENCES product_types (id);

drop table h_products if EXISTS CASCADE;
create table h_products (
  rev             int not null,
  retype          TINYINT,
  ID              VARCHAR(40) not NULL,
  name            VARCHAR(200),
  type            VARCHAR(40),
  PRIMARY KEY (id, rev)
);

--------------
--  STORES  --
--------------

drop TABLE stores if EXISTS CASCADE;
CREATE TABLE stores (
  id              VARCHAR(40) NOT NULL,
  name            VARCHAR(100) NOT null,
  type            VARCHAR(50) NOT NULL,
  version         int NOT NULL
);

ALTER TABLE stores
    ADD CONSTRAINT PK_STORES
    PRIMARY KEY (id);

ALTER TABLE stores
    add CONSTRAINT UQ_STORE_NAME
    UNIQUE (name);

drop table h_stores if EXISTS CASCADE;
create table h_stores (
  rev       int not NULL,
  revtype   TINYINT,
  ID        VARCHAR(40) not NULL,
  name      VARCHAR(100),
  type      VARCHAR(50),
  PRIMARY KEY (id, rev)
);

----------------------
--  STORE_LOCATIONS --
----------------------

drop table store_locations if EXISTS CASCADE;
create TABLE store_locations (
  id              VARCHAR(40) not null,
  name            VARCHAR(200) not null,
  store_id        VARCHAR(40) not null,
  street          VARCHAR(100) not null,
  number          VARCHAR(10) not null,
  zip_code        VARCHAR(5) not null,
  city            VARCHAR(100) not null,
  country         VARCHAR(100) NOT null,
  version         int not null
);

alter table store_locations
    add CONSTRAINT PK_STORE_LOCATIONS
    PRIMARY KEY (id);

alter table store_locations
    add CONSTRAINT FK_STORES_STORE_LOCATION
    FOREIGN KEY (store_id)
    REFERENCES stores (id);

drop table h_store_locations if EXISTS CASCADE;
create table h_store_locations (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  name            VARCHAR(200),
  store_id        VARCHAR(40),
  street          VARCHAR(100),
  number          VARCHAR(10),
  zip_code        VARCHAR(5),
  city            VARCHAR(100),
  country         VARCHAR(100),
  PRIMARY KEY (id, rev)
);

----------------------
--  STORE_PRODUCTS  --
----------------------

drop table store_products if EXISTS CASCADE;
create table store_products (
  id              VARCHAR(40) not null,
  code            VARCHAR(20) not null,
  product_id      VARCHAR(40) not null,
  store_id        VARCHAR(40) not null,
  version         int not null
);

alter table store_products
    add CONSTRAINT PK_STORE_PRODUCTS
    PRIMARY KEY (id);

alter table store_products
    add CONSTRAINT FK_STORE_PRODUCTS_STORE
    FOREIGN KEY (store_id)
    REFERENCES stores (id);

alter TABLE store_products
    add CONSTRAINT FK_STORE_PRODUCTS_PRODUCT
    FOREIGN KEY (product_id)
    REFERENCES products (id);

alter table store_products
    add CONSTRAINT UQ_STORE_PRODUCTS_CODE_PER_STORE
    UNIQUE (store_id, code);

drop table h_store_products if EXISTS CASCADE;
create TABLE h_store_products (
  rev             int not null,
  revtype         tinyint,
  id              VARCHAR(40) not null,
  code            VARCHAR(20),
  product_id      VARCHAR(40),
  store_id        VARCHAR(40),
  primary key (id, rev)
);

----------------
--  PURCHASES --
----------------

drop TABLE purchases if EXISTS CASCADE;
create TABLE purchases (
  id                VARCHAR(40) not null,
  DATE              DATE not null,
  store_location_id VARCHAR(40) not null,
  version           int not null
);

alter TABLE purchases
    add CONSTRAINT PK_PURCHASES
    PRIMARY KEY (id);

alter table purchases
    add CONSTRAINT FK_PURCHASES
    FOREIGN KEY (store_location_id)
    REFERENCES store_locations (id);

drop table h_purchases if EXISTS CASCADE;
create table h_purchases (
  rev               int not null,
  revtype           TINYINT,
  id                VARCHAR(40) not Null,
  DATE              DATE,
  store_location_id VARCHAR(40),
  PRIMARY KEY (id, rev)
);

--------------------------
--  PURCHASED_PRODUCTS  --
--------------------------

drop TABLE purchased_products IF EXISTS CASCADE;
create TABLE purchased_products (
  id                VARCHAR(40) not NULL,
  purchase_id       VARCHAR(40),
  product_id        VARCHAR(40) not null,
  unit_price        DOUBLE not null,
  amount            DOUBLE not null,
  version           int not null
);

alter TABLE purchased_products
    ADD CONSTRAINT PK_PURCHASED_PRODUCTS
    PRIMARY KEY (id);

alter TABLE purchased_products
    ADD CONSTRAINT FK_PURCHASED_PRODUCTS_PURCHASE
    FOREIGN KEY (purchase_id)
    REFERENCES purchases (id);

alter TABLE purchased_products
    add CONSTRAINT FK_PURCHASED_PRODUCTS_PRODUCT
    FOREIGN KEY (product_id)
    REFERENCES products (id);

drop TABLE h_purchased_products if EXISTS CASCADE;
create table h_purchased_products (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  purchase_id     VARCHAR(40),
  product_id      VARCHAR(40),
  unit_price      DOUBLE,
  amount          DOUBLE,
  PRIMARY KEY (id, rev)
);

drop table h_purchase_purchasedproduct if EXISTS CASCADE;
create table h_purchase_purchasedproduct (
  rev             int not null,
  revtype         TINYINT,
  ID              VARCHAR(40) not null,
  purchase_id     VARCHAR(40) not null,
  PRIMARY KEY (rev, id, purchase_id)
);

----------------
--  PAYMENTS  --
----------------

drop table payments if EXISTS CASCADE;
create table payments (
  id              VARCHAR(40) NOT NULL,
  purchase_id     VARCHAR(40),
  account_id      VARCHAR(40) not NULL,
  amount          DOUBLE not null,
  version         int not null
);

ALTER TABLE payments
    add CONSTRAINT PK_PAYMENTS
    PRIMARY KEY (id);

alter TABLE payments
    add CONSTRAINT FK_PAYMENTS_PURCHASE
    FOREIGN KEY (purchase_id)
    REFERENCES purchases (id);

alter TABLE payments
    add CONSTRAINT FK_PAYMENTS_ACCOUNT
    FOREIGN KEY (account_id)
    REFERENCES accounts (id);

drop table h_payments if EXISTS CASCADE;
create table h_payments (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  purchase_id     VARCHAR(40),
  account_id      VARCHAR(40),
  amount          DOUBLE,
  PRIMARY KEY (id, rev)
);

drop table h_purchase_payment IF EXISTS CASCADE;
create table h_purchase_payment (
  rev             int not null,
  revtype         TINYINT,
  id              VARCHAR(40) not null,
  purchase_id     VARCHAR(40) not null,
  PRIMARY KEY (rev, id, purchase_id)
);