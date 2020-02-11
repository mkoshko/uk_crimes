create table outcome_object
(
    id serial,
    identity varchar(100),
    name varchar(100),
    constraint PK_outcome_object primary key (id),
    constraint UQ_outcome_object_identity unique (identity)
);

create table stop_and_search
(
    id serial8,
    type varchar(50),
    involved_person boolean,
    datetime timestamp,
    operation boolean,
    operation_name varchar(100),
    location_id bigint,
    gender varchar(10),
    age_range varchar(10),
    self_defined_ethnicity varchar(100),
    officer_defined_ethnicity varchar(100),
    legislation varchar(100),
    object_of_search varchar(100),
    outcome varchar(100),
    outcome_object_id integer,
    outcome_linked_to_object_of_search boolean,
    removal_of_more_than_outer_clothing boolean,
    constraint FK_stop_and_search_location foreign key (location_id) references location(street_id),
    constraint FK_stop_and_search_outcome_object foreign key (outcome_object_id) references outcome_object(id)
);

grant select, update, insert, delete on all tables in schema public to crime_app;
grant usage, select on all sequences in schema public to crime_app;
