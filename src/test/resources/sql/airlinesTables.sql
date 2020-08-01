--
-- Encoding: UTF-8
--
PRAGMA foreign_keys = off;

-- Table: airship
CREATE TABLE airship (
    id                VARCHAR (36) PRIMARY KEY
                                   NOT NULL
                                   UNIQUE,
    model             VARCHAR (30) NOT NULL,
    economy_category  INTEGER (3)  DEFAULT (0),
    business_category INTEGER (3)  DEFAULT (0),
    premium_category  INTEGER (3)  DEFAULT (0) 
);


-- Table: category
CREATE TABLE category (
    id   INTEGER      PRIMARY KEY AUTOINCREMENT
                      NOT NULL
                      UNIQUE,
    name VARCHAR (10) NOT NULL
);


-- Table: client
CREATE TABLE client (
    id          VARCHAR (36) PRIMARY KEY
                             NOT NULL
                             UNIQUE,
    first_name  VARCHAR (30),
    middle_name VARCHAR (30),
    last_name   VARCHAR (30),
    bill        FLOAT (7, 2) DEFAULT (0) 
);


-- Table: flight
CREATE TABLE flight (
    id                VARCHAR (36) PRIMARY KEY
                                   UNIQUE
                                   NOT NULL,
    date_of_departure DATETIME     NOT NULL,
    date_of_arrival   DATETIME     NOT NULL,
    airship           VARCHAR (36) REFERENCES airship (id) ON DELETE RESTRICT
                                                           ON UPDATE CASCADE
                                   NOT NULL,
    route             VARCHAR (36) REFERENCES route (id) ON DELETE RESTRICT
                                                         ON UPDATE CASCADE
                                   NOT NULL
);


-- Table: route
CREATE TABLE route (
    id          VARCHAR (36) PRIMARY KEY
                             NOT NULL
                             UNIQUE,
    start_point VARCHAR (50) NOT NULL,
    end_point   VARCHAR (50) NOT NULL
);


-- Table: status
CREATE TABLE status (
    id_status INTEGER      PRIMARY KEY AUTOINCREMENT
                           UNIQUE
                           NOT NULL,
    name      VARCHAR (30) NOT NULL
);


-- Table: ticket
CREATE TABLE ticket (
    id       VARCHAR (36) PRIMARY KEY
                          UNIQUE
                          NOT NULL,
    flight   VARCHAR (36) REFERENCES flight (id) ON DELETE RESTRICT
                                                 ON UPDATE CASCADE
                          NOT NULL,
    category INTEGER (1)  REFERENCES category (id) ON DELETE RESTRICT
                                                   ON UPDATE CASCADE
                          NOT NULL,
    cost     FLOAT (5, 2) NOT NULL,
    baggage  FLOAT (3, 2) DEFAULT (0),
    status   INTEGER (1)  NOT NULL
                          REFERENCES status (id_status),
    client   VARCHAR (36) REFERENCES client (id) ON DELETE RESTRICT
                                                 ON UPDATE CASCADE
);

INSERT INTO category (id, name) VALUES (1, 'ECONOMY');
INSERT INTO category (id, name) VALUES (2, 'PREMIUM');
INSERT INTO category (id, name) VALUES (3, 'BUSINESS');

-- Table: status

INSERT INTO status (id_status, name) VALUES (1, 'CLOSED');
INSERT INTO status (id_status, name) VALUES (2, 'RESERVED');
INSERT INTO status (id_status, name) VALUES (3, 'SOLD');
INSERT INTO status (id_status, name) VALUES (4, 'FREE');

PRAGMA foreign_keys = on;
