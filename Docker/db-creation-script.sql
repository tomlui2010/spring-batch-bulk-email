CREATE DATABASE testdb;
use testdb;

CREATE TABLE Person (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100),
    subscription_status VARCHAR(10),
    email_sent_status VARCHAR(10),
    PRIMARY KEY(id)
);