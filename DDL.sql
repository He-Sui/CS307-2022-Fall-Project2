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
                           supply_center varchar not null constraint enterprise_fk references center(name),
                           industry varchar not null
);

create table model(
                      id serial primary key ,
                      model varchar not null unique ,
                      unit_price integer not null,
                      product_number varchar not null
                          constraint model_fk references product(number)
);

create table staff(
                      id serial primary key ,
                      number varchar(8) not null unique unique,
                      name varchar not null ,
                      phone_number varchar(11) not null ,
                      gender varchar not null ,
                      age integer not null,
                      type varchar not null ,
                      supply_center varchar not null
                          constraint staff_pk references center(name)
);

create table contract(
                         id serial primary key ,
                         number varchar not null unique,
                         enterprise_name  varchar not null
                             constraint contract_fk1 references enterprise(name)      ,
                         date DATE not null,
                         contract_type varchar not null ,
                         contract_manager varchar(8) not null
                             constraint contract_fk references staff(number)
);

create table orders(
                       id serial primary key ,
                       product_model varchar not null
                           constraint orders_fk1 references model(model),
                       contract_number varchar not null
                           constraint orders_fk2 references contract(number),
                       salesman_number varchar(8) not null
                           constraint orders_fk references staff(number),
                       quantity integer not null ,
                       estimated_delivery_date DATE not null ,
                       lodgement_date DATE
);


create table stock(
                      id serial not null primary key ,
                      supply_center varchar not null
                          constraint center_stock_fk references center(name),
                      product_model varchar not null
                          constraint stock_model_fk references model(model),
                      quantity INTEGER NOT NULL,
                      constraint stock_uq unique (supply_center, product_model)
);

create table stock_in_record(
                                id serial not null primary key ,
                                supply_center varchar not null
                                    constraint center_record_fk references center(name),
                                purchase_price INTEGER not null ,
                                quantity INTEGER NOT NULL,
                                product_model varchar not null
                                    constraint record_model_fk1 references model(model),
                                supply_staff varchar(8) not null
                                    constraint record_staff_fk references staff(number),
                                date DATE not null
);
