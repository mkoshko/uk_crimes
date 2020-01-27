create user crime_app with password 'strong-crime_app-password';

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
constraint PK_location PRIMARY KEY (id),
constraint UQ_location UNIQUE (latitude, longitude),
constraint FK_location_street foreign key (street_id) references street (id)
);

create table category_name
(
    id serial,
    category_name varchar(100),
constraint PK_category_name_id PRIMARY KEY (id),
constraint UQ_category_name UNIQUE (category_name)
);

create table outcome_status
(
    id bigserial,
    category_name_id smallint,
    date varchar(7),
constraint PK_outcome_status PRIMARY KEY (id),
constraint UQ_outcome_status UNIQUE (category_name_id, date),
constraint FK_outcome_status_category_name FOREIGN KEY (category_name_id) references category_name (id)
);

create table crime
(
	id bigint,
	category varchar(40),
	location_type varchar(20),
	location_id bigint,
	context varchar(100),
	outcome_status_id bigint,
	persistent_id char(64),
	location_subtype varchar(100),
    "month" char(7),
constraint PK_crime primary key (id),
constraint FK_crime_outcome_status foreign key (outcome_status_id) references outcome_status (id),
constraint FK_crime_location foreign key (location_id) references "location" (id)
);

grant select, update, insert, delete on all tables in schema public to crime_app;
grant usage, select on all sequences in schema public to crime_app;