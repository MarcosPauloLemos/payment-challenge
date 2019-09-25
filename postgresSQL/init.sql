CREATE DATABASE payment_challenge;

\c payment_challenge

CREATE SCHEMA payment_service;

CREATE TABLE payment_service.ps01a_buyer (
    ps01a_id bigserial,
    ps01a_name varchar(200) not null,
    ps01a_email varchar(200) not null,
    ps01a_cpf bigint not null,
    PRIMARY KEY(ps01a_id)
);

CREATE TABLE payment_service.ps02a_payment (
    ps02a_id bigserial,
    ps02a_id_client bigint not null,
    ps02a_id_buyer bigint not null,
    ps02a_amount numeric not null,
    ps02a_type int not null,
    ps02a_status int not null,
    ps02a_card_number bigint,
    ps02a_boleto_number bigint,
    ps02a_authorizer_number bigint,
    PRIMARY KEY(ps02a_id),
    CONSTRAINT fk_ps01a_buyer FOREIGN KEY (ps02a_id_buyer) REFERENCES payment_service.ps01a_buyer (ps01a_id)
);

CREATE SCHEMA payment_authorizer;

CREATE TABLE payment_authorizer.pa01a_boleto (
    pa01a_id bigserial,
    pa01a_id_client bigint not null,
    pa01a_number bigint not null,
    pa01a_amount numeric not null,
    pa01a_status int not null,
    pa01a_paymento_number bigint not null,
    PRIMARY KEY(pa01a_id)
);

CREATE TABLE payment_authorizer.pa01b_credit_card (
    pa01b_id bigserial,
    pa01b_id_client bigint not null,
    pa01b_holder_name varchar(200) not null,
    pa01b_number bigint not null,
    pa01b_expiration_date timestamp not null,
    pa01b_cvv int not null,
    pa01b_amount numeric not null,
    pa01b_payment_number bigint not null,
    pa01b_status int not null,
    PRIMARY KEY(pa01b_id)
);
