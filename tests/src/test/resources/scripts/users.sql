insert into entities values (1, 'the A-Team', 1, 0);

INSERT INTO USERS (id, username, password, email, name, enabled, entity_id) VALUES (1, 'hannibal', '872fdc2a08772ba2c7c1cddd081bfd96', 'hannibal@a-team.com', 'John Smith', 1, 1);
insert into authorities (user_id, authority) values (1, 'ROLE_USER');
insert into authorities (user_id, authority) values (1, 'ROLE_ADMIN');
insert into users (id, username, password, email, name, enabled, entity_id) values (2, 'face', '872fdc2a08772ba2c7c1cddd081bfd96', 'face@a-team.com', 'Templeton Peck', 1, 1);
insert into authorities (user_id, authority) values (2, 'ROLE_USER');

INSERT into tokens (id, value, entity_id, email, expiration_date, status, type)
  values(1, 'token-valid', 1, 'valid@test.com', sysdate + interval '1' month, 'VALID', 'USER_INVITATION');
insert into tokens (id, value, entity_id, email, expiration_date, status, type)
  values(2, 'token-revoked', 1, 'revoked@test.com', sysdate + interval '1' month, 'REVOKED', 'USER_INVITATION');
insert into tokens (id, value, entity_id, email, expiration_date, status, type)
  values(3, 'token-expired', 1, 'expired@test.com', sysdate - interval '1' day, 'VALID', 'USER_INVITATION');


-- TODO-TECH boolean: Y-N instead of number
-- TODO-TECH exclude comments from execution
-- TODO-TECH allow parameters in sql file?
