SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS items (
    id int PRIMARY KEY auto_increment,
    type VARCHAR,
    name VARCHAR,
    price INT,
    dateSold VARCHAR,
    sale BOOLEAN,
    capacity INT,
    color VARCHAR,
    gear INT,
    purpose VARCHAR
    );

CREATE TABLE IF NOT EXISTS orders (
    id int PRIMARY KEY auto_increment,
    number VARCHAR
    );

CREATE TABLE IF NOT EXISTS items_orders (
 id int PRIMARY KEY auto_increment,
 itemId INTEGER,
 orderId INTEGER
);