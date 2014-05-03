insert into entities values (1, 'Suzan en Kim', true, false);

INSERT INTO USERS VALUES (1, 'sur', '872fdc2a08772ba2c7c1cddd081bfd96', 'rogge.suzan@gmail.com', 'Rogge Suzan', true, true, 1);
insert into authorities values (1, 'ROLE_USER');

INSERT into tokens values (1, 'de2f7740-bd39-4c08-a4b7-cd760f435c9e', 1, 'test@test.com', CURRENT_DATE + 1 MONTH, 'VALID');