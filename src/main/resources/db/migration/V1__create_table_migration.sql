create table customer (
	id int auto_increment PRIMARY KEY,
    name varchar(255) not null,
    email varchar(255) not null unique
);