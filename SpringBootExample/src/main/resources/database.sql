#Table: users
create table if not exists users(
                                     id bigint not null  auto_increment PRIMARY KEY ,
                                     username varchar(255) not null unique,
                                     firstname varchar(255) not null,
                                     lastname varchar(255) not null,
                                     password varchar(255) not null);



#Table: roles
create table if not exists roles(
                                     id bigint not null auto_increment PRIMARY KEY ,
                                     role varchar(255) not null );

#Table: users_roles
create table if not exists users_roles(
                                            user_id bigint not null ,
                                            roles_id bigint not null ,
                                            FOREIGN KEY (user_id) REFERENCES users(id),
                                            FOREIGN KEY (roles_id) REFERENCES roles(id),
                                            UNIQUE (user_id, roles_id));

insert into users values (7,	'user',	'Fedya',	'Fedorov',	'user');
insert into users values (8,	'admin',	'Jora',	'Ruchkin',	'admin');

insert into roles values (1, 'ROLE_USER');
insert into roles values (2, 'ROLE_ADMIN');

insert into users_roles values (7, 1);
insert into users_roles values (8, 2);
