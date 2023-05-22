insert into SEC_USER (userName, encryptedPassword, ENABLED)
values ('Utsav', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1),
('Pratik', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1),
('Svayam', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1),
('Rudresh', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1),
('Ansil', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);


--Vender
insert into SEC_USER (userName, encryptedPassword, ENABLED)
values ('Jon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into sec_role (roleName)
values ('ROLE_GUEST');
 
insert into sec_role (roleName)
values ('ROLE_VENDER');
 
insert into user_role (userId, roleId)
values (1, 1),(2, 1),(3, 1),(4, 1),(5, 1);

insert into user_role (userId, roleId)
values (6, 2);

INSERT INTO TICKET (showName,userName,firstName,lastName,email,age,price,quantity) VALUES
('NAVRATRI FRESTIVAL','Utsav','Katharyn', 'Dobrowlski', 'kdobrowlskij@trellian.com' ,19,30,7),
('NAVRATRI FRESTIVAL','Pratik','Drusilla', 'Schultz', 'dschultz0@statcounter.com' ,9,30,8),
('NAVRATRI FRESTIVAL','Svyam','Audra', 'Renouf', 'arenouf1@comcast.net' ,14,30,8),
('NAVRATRI FRESTIVAL','Rudresh','Stillman', 'Olden', 'solden2@alexa.com' ,19,30,4),
('NAVRATRI FRESTIVAL','Utsav','Fitzgerald', 'Bleckly', 'fbleckly3@google.nl' ,19,30,9),
('NAVRATRI FRESTIVAL','Svayam','Darda', 'Doding', 'ddoding4@google.it' ,59,13,80),
('NAVRATRI FRESTIVAL','Ansil','Daniel', 'Cure', 'dcure5@hc360.com',19,96,40),
('NAVRATRI FRESTIVAL','Rudresh','Cati', 'Lukacs', 'clukacs6@cafepress.com' ,9,103,78),
('NAVRATRI FRESTIVAL','Utsav','Homer', 'Pepi', 'hpepif@army.mil' ,19,10,90),
('NAVRATRI FRESTIVAL','Rudresh','Tanny', 'Rosier', 'trosierg@ed.gov' ,19,31,32),
('NAVRATRI FRESTIVAL','Pratik','Tabby', 'Amott', 'tamotth@liveinternet.ru' ,98,78,69),
('NAVRATRI FRESTIVAL','Svayam','Colas', 'Pauling', 'cpaulingi@e-recht24.de' ,9,103,78);
--insert into user_role (userId, roleId)
--values (3, 3);