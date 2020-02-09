create table outcome_object
(
    id serial,
    identity varchar(50),
    name varchar(50),
    constraint UQ_outcome_object_identity unique (identity)
);

create table stop_and_search
(
    id serial8,
    type varchar(30),
    involved_person boolean,
    datetime timestamp,
    operation boolean,
    operation_name varchar(100),
    location_id bigint,
    gender varchar(6),
    age_range varchar(6),
    self_defined_ethnicity varchar(50),
    officer_defined_ethnicity varchar(50),
    legislation varchar(100),
    object_of_search varchar(50),
    outcome varchar(50),
    outcome_object_id integer,
    outcome_linked_to_object_of_search boolean,
    removal_of_more_than_outer_clothing boolean,
    constraint FK_stop_and_search_location foreign key (location_id) references location(id),
    constraint FK_stop_and_search_outcome_object foreign key (outcome_object_id) references outcome_object(id)
);
