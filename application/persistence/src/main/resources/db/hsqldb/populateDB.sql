insert into entities values (1, 'Suzan en Kim', true, false);

INSERT INTO USERS VALUES (1, 'sur', '872fdc2a08772ba2c7c1cddd081bfd96', 'rogge.suzan@gmail.com', 'Rogge Suzan', true, 1);
insert into authorities values (1, 'ROLE_USER');
insert into authorities values (1, 'ROLE_ADMIN');

insert into users values (2, 'ger', '872fdc2a08772ba2c7c1cddd081bfd96', 'gert@raeyen.be', 'Gert Raeyen', true, 1);
insert into authorities values (2, 'ROLE_USER');

INSERT into tokens values(1, 'de2f7740-bd39-4c08-a4b7-cd760f435c9e', 1, 'valid@test.com', CURRENT_DATE + 1 MONTH, 'VALID');
insert into tokens values(2, 'test-revoked', 1, 'revoke@test.com', CURRENT_DATE + 1 MONTH, 'REVOKED');
insert into tokens values(3, 'test-expired', 1, 'expired@test.com', CURRENT_DATE - 1 DAY, 'VALID');