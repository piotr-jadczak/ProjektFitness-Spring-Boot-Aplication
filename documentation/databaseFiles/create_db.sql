
create table customer
(
    id  serial,
    username        varchar(16) NOT NULL,
    password        varchar(32) NOT NULL,
    email           varchar(64) NOT NULL,
    name            varchar(32) NOT NULL,
    surname         varchar(32) NOT NULL,
    dob             DATE                ,
    phone_number    varchar(11),
    CONSTRAINT		client_id_pk PRIMARY KEY(id)
);

create table club_owner
(
    id  serial,
    username        varchar(16) NOT NULL,
    password        varchar(32) NOT NULL,
    email           varchar(64) NOT NULL,
    name            varchar(32) NOT NULL,
    surname         varchar(32) NOT NULL,
    dob             DATE                ,
    phone_number    varchar(11)         ,
    CONSTRAINT		club_owner_id_pk PRIMARY KEY(id)
);

create table coach
(
    id  serial,
    username        varchar(16) NOT NULL,
    password        varchar(32) NOT NULL,
    email           varchar(64) NOT NULL,
    name            varchar(32) NOT NULL,
    surname         varchar(32) NOT NULL,
    dob             DATE                ,
    phone_number    varchar(11)         ,
    CONSTRAINT		coach_id_pk PRIMARY KEY(id)
);

create table club
(
    id serial,
    owner_id        int         NOT NULL,
    category_id     int         NOT NULL,
    name            varchar(64) NOT NULL,
    description     varchhar(200) NOT NULL,
    address         varchar(256) NOT NULL,
    CONSTRAINT		club_id_pk PRIMARY KEY(id),
    CONSTRAINT      owner_fk FOREIGN KEY(owner_id) REFERENCES club_owner(id),
    CONSTRAINT      category_fk FOREIGN KEY(category_id) REFERENCES club_category(id)
);

create table club_category
(
    id serial,
    name            varchar(64)   NOT NULL,
    CONSTRAINT		club_category_id_pk PRIMARY KEY(id)
);

create table coach_club
(
    coach_id      int           NOT NULL,
    club_id       int           NOT NULL,
    CONSTRAINT    coach_fk FOREIGN KEY(coach_id) REFERENCES coach(id),
    CONSTRAINT    club_fk FOREIGN KEY(club_id) REFERENCES club(id)
    
);

create table training
(
    id serial,
    club_id         int         NOT NULL,
    category_id     int         NOT NULL,
    coach_id        int         NOT NULL,
    name            varchar(64) NOT NULL,
    description     varchhar(200) NOT NULL,
    day_of_week     int         NOT NULL,
    max_participants    int     NOT NULL,
    start_time      timestamp   NOT NULL,
    end_time        timestamp   NOT NULL,
    price           money       NOT NULL,
    point_reward    int                 ,
    CONSTRAINT		training_id_pk PRIMARY KEY(id),
    CONSTRAINT      club_fk FOREIGN KEY(club_id) REFERENCES club(id),
    CONSTRAINT      category_fk FOREIGN KEY(category_id) REFERENCES training_category(id),
    CONSTRAINT      coach_fk FOREIGN KEY(coach_id) REFERENCES coach(id),
);

create table training_category
(
    id serial,
    name            varchar(64)   NOT NULL,
    CONSTRAINT		training_category_id_pk PRIMARY KEY(id)
);

create table user_training
(
    customer_id      int           NOT NULL,
    training_id       int           NOT NULL,
    CONSTRAINT    customer_fk FOREIGN KEY(customer_id) REFERENCES customer(id),
    CONSTRAINT    training_fk FOREIGN KEY(training_id) REFERENCES training(id),

);