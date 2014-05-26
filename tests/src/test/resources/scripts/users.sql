insert into entities values (1, 'the A-Team', true, false);

INSERT INTO USERS VALUES (1, 'hannibal', '872fdc2a08772ba2c7c1cddd081bfd96', 'hannibal@a-team.com', 'John Smith', true, 1);
insert into authorities values (1, 'ROLE_USER');
insert into authorities values (1, 'ROLE_ADMIN');

-- TODO exclude comments from execution
-- TODO allow parameters in sql file?