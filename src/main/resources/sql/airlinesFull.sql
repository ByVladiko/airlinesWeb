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

INSERT INTO airship (id, model, economy_category, business_category, premium_category) VALUES ('cf52f3ff-3e1c-4d5b-af25-8785be5946df', 'Let', 50, 20, 20);
INSERT INTO airship (id, model, economy_category, business_category, premium_category) VALUES ('e823c8e6-737b-11ea-bc55-0242ac130003', 'Boeing', 20, 30, 20);
INSERT INTO airship (id, model, economy_category, business_category, premium_category) VALUES ('bf3a1844-3b4d-4fac-a3a4-8651b78a9d39', 'TU-154', 45, 15, 20);
INSERT INTO airship (id, model, economy_category, business_category, premium_category) VALUES ('02cab69a-f245-48d4-b591-f3fdaf16ccc8', 'Duglas', 20, 20, 10);
INSERT INTO airship (id, model, economy_category, business_category, premium_category) VALUES ('ce9a5509-df24-4a41-a7aa-476a98749aa7', 'A320', 50, 10, 10);

-- Table: category
CREATE TABLE category (
    id   INTEGER      PRIMARY KEY AUTOINCREMENT
                      NOT NULL
                      UNIQUE,
    name VARCHAR (10) NOT NULL
);

INSERT INTO category (id, name) VALUES (1, 'ECONOMY');
INSERT INTO category (id, name) VALUES (2, 'PREMIUM');
INSERT INTO category (id, name) VALUES (3, 'BUSINESS');

-- Table: route
CREATE TABLE route (
    id          VARCHAR (36) PRIMARY KEY
                             NOT NULL
                             UNIQUE,
    start_point VARCHAR (50) NOT NULL,
    end_point   VARCHAR (50) NOT NULL
);

INSERT INTO route (id, start_point, end_point) VALUES ('96855292-737c-11ea-bc55-0242ac130003', 'Samara', 'Saratov');
INSERT INTO route (id, start_point, end_point) VALUES ('a3411980-737c-11ea-bc55-0242ac130003', 'Sochi', 'Sankt-Peterburg');
INSERT INTO route (id, start_point, end_point) VALUES ('aedf7a20-737c-11ea-bc55-0242ac130003', 'New-York', 'Los-Angeles');
INSERT INTO route (id, start_point, end_point) VALUES ('fc71d5ce-484a-4278-b5b3-92f891f6e1ee', 'Paris', 'Tokyo');
INSERT INTO route (id, start_point, end_point) VALUES ('122529ff-c464-4903-80fd-07890c0e779d', 'Krasnoyarsk', 'Minsk');
INSERT INTO route (id, start_point, end_point) VALUES ('34052887-e938-454a-9cc9-eecfec08f996', 'Lissabon', 'London');
INSERT INTO route (id, start_point, end_point) VALUES ('500d4651-3dbb-4bb6-8e2b-c6bfa9fde99c', 'Brussel', 'Madrid');
INSERT INTO route (id, start_point, end_point) VALUES ('f4142e31-4c74-4502-a8a7-5acf089bd454', 'Warshava', 'Berlin');

-- Table: status
CREATE TABLE status (
    id_status INTEGER      PRIMARY KEY AUTOINCREMENT
                           UNIQUE
                           NOT NULL,
    name      VARCHAR (30) NOT NULL
);

INSERT INTO status (id_status, name) VALUES (1, 'CLOSED');
INSERT INTO status (id_status, name) VALUES (2, 'RESERVED');
INSERT INTO status (id_status, name) VALUES (3, 'SOLD');
INSERT INTO status (id_status, name) VALUES (4, 'FREE');

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

INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('66cb6636-737c-11ea-bc55-0242ac130003', 'Kristina', 'Samoilova', 'Aleksandrova', 20000.0);
INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('7a68a546-737c-11ea-bc55-0242ac130003', 'Filip', 'Petrov', 'Dmitrievich', 30000.0);
INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('9ffc9e34-0604-4503-bb29-4a923c030ecf', 'Гарик', 'Бульдог', 'Харламов', 2000.0);
INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('c4859761-1ec7-4dac-ac73-1a231388d20e', 'Vladislav', 'Nickolaevich', 'Bychkov', 3000.0);
INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('a5f8a364-a453-4440-b2ee-f6c85fe7ece4', 'Vladislav', 'Bychkov', 'Nikolaevich', 0.0);
INSERT INTO client (id, first_name, middle_name, last_name, bill) VALUES ('427acfaf-8697-4def-b08a-f8eeb8b40ada', 'Igraghim', 'Muslim', 'Magamaev', 0.0);

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

INSERT INTO flight (id, date_of_departure, date_of_arrival, airship, route) VALUES ('ab9d2b09-6046-4ef0-ae1e-4916850be310', '2020-04-22 12:30:00', '2020-04-22 15:00:00', 'ce9a5509-df24-4a41-a7aa-476a98749aa7', 'a3411980-737c-11ea-bc55-0242ac130003');
INSERT INTO flight (id, date_of_departure, date_of_arrival, airship, route) VALUES ('feb2c63b-d25a-417a-a85f-6888b0ee580c', '2020-04-21 10:40:00', '2020-04-21 12:30:00', 'bf3a1844-3b4d-4fac-a3a4-8651b78a9d39', '96855292-737c-11ea-bc55-0242ac130003');
INSERT INTO flight (id, date_of_departure, date_of_arrival, airship, route) VALUES ('6a509c31-7462-4c7e-81fe-ab930e51aacb', '2020-04-20 12:00:00', '2020-04-20 17:00:00', 'cf52f3ff-3e1c-4d5b-af25-8785be5946df', 'aedf7a20-737c-11ea-bc55-0242ac130003');
INSERT INTO flight (id, date_of_departure, date_of_arrival, airship, route) VALUES ('dc0d0496-b4ae-4432-9e8a-09867329c820', '2020-04-22 03:10:00', '2020-04-21 22:15:00', 'cf52f3ff-3e1c-4d5b-af25-8785be5946df', 'f4142e31-4c74-4502-a8a7-5acf089bd454');
INSERT INTO flight (id, date_of_departure, date_of_arrival, airship, route) VALUES ('cc79fa02-a6b2-4380-86af-2d6acc518567', '2020-04-22 01:10:00', '2020-04-22 05:45:00', '02cab69a-f245-48d4-b591-f3fdaf16ccc8', '34052887-e938-454a-9cc9-eecfec08f996');

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

INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('4077411d-888d-44f2-860f-0440016ee06c', 'ab9d2b09-6046-4ef0-ae1e-4916850be310', 2, 5000.0, 3.0, 2, 'c4859761-1ec7-4dac-ac73-1a231388d20e');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('12611c7e-09b5-47ca-a065-ffc6604a0b38', 'feb2c63b-d25a-417a-a85f-6888b0ee580c', 1, 10000.0, 10.0, 2, '7a68a546-737c-11ea-bc55-0242ac130003');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('978ba799-bb63-4a97-9a39-85d18ff4acd7', 'cc79fa02-a6b2-4380-86af-2d6acc518567', 3, 2000.0, 4.0, 3, '66cb6636-737c-11ea-bc55-0242ac130003');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('4e85e466-aabd-48b1-8ed2-53e41b29117e', 'ab9d2b09-6046-4ef0-ae1e-4916850be310', 1, 4000.0, 6.0, 3, 'c4859761-1ec7-4dac-ac73-1a231388d20e');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('79b64d4d-bec8-4110-b3f0-406c6de54ba8', 'dc0d0496-b4ae-4432-9e8a-09867329c820', 3, 6000.0, 9.0, 3, '7a68a546-737c-11ea-bc55-0242ac130003');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('dc43e9d8-fb11-4f1c-ad7e-b993dd6509e7', 'cc79fa02-a6b2-4380-86af-2d6acc518567', 2, 7500.0, 12.0, 3, 'c4859761-1ec7-4dac-ac73-1a231388d20e');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('a9844512-0366-4a25-bd95-a09382e7cfe8', 'dc0d0496-b4ae-4432-9e8a-09867329c820', 2, 15000.0, 11.0, 1, '66cb6636-737c-11ea-bc55-0242ac130003');
INSERT INTO ticket (id, flight, category, cost, baggage, status, client) VALUES ('06c96a92-01f6-45b8-a4cc-7d663de30d76', 'dc0d0496-b4ae-4432-9e8a-09867329c820', 1, 5000.0, 0.0, 4, NULL);

PRAGMA foreign_keys = on;
