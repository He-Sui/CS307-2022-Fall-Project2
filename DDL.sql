create table center(
                       id serial primary key,
                       name varchar not null unique
);

create table product(
                        id serial primary key,
                        number varchar not null unique ,
                        name varchar not null
);

create table enterprise(
                           id serial primary key ,
                           name varchar not null unique,
                           country  varchar not null,
                           city varchar,
                           supply_center varchar not null,
                           industry varchar not null
);

create table model(
                      id serial primary key ,
                      model varchar not null unique ,
                      unit_price integer not null,
                      product_number varchar not null
);

create table staff(
                      id serial primary key ,
                      number varchar(8) not null unique,
                      name varchar not null ,
                      phone_number varchar(11) not null ,
                      gender varchar not null ,
                      age integer not null,
                      type varchar not null ,
                      supply_center varchar not null
);

create table contract(
                         id serial primary key ,
                         number varchar not null unique,
                         enterprise_name  varchar not null,
                         date DATE not null,
                         contract_type varchar,
                         contract_manager varchar(8)
);

create table orders(
                       id serial primary key ,
                       product_model varchar not null,
                       contract_number varchar not null,
                       salesman_number varchar(8),
                       quantity integer not null ,
                       estimated_delivery_date DATE not null ,
                       lodgement_date DATE
);

create table stock(
                      id serial not null primary key ,
                      supply_center varchar not null,
                      product_model varchar not null,
                      quantity INTEGER NOT NULL,
                      constraint stock_uq unique (supply_center, product_model)
);

create table stock_in_record(
                                id serial not null primary key ,
                                supply_center varchar not null,
                                purchase_price INTEGER not null ,
                                quantity INTEGER NOT NULL,
                                product_model varchar not null,
                                supply_staff varchar(8),
                                date DATE not null
);