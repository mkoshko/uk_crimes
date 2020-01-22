create database crimebox;

create user crime_app with password 'strong-crime_app-password'

create table street
(
	id integer,
	name varchar(100),
constraint PK_street primary key (id)
);


create table "location"
(
id bigserial,
street_id integer,
latitude float8,
longitude float8,
constraint PK_location primary key (id),
constraint FK_location_street foreign key (street_id) references street (id) on delete cascade
);


create table crime
(
	id bigint,
	category varchar(40),
	location_type varchar(20),
	location_id bigint,
	context varchar(100),
	outcome_status varchar(100),
	persistent_id char(64),
	location_subtype varchar(100),
    "month" char(7),
constraint PK_crime primary key (id),
constraint FK_crime_location foreign key (location_id) references "location" (id)
);

grant select, update, insert, delete on all tables in schema public to crime_app;
grant usage, select on all sequences in schema public to crime_app;