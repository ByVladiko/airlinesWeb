--
-- Encoding: UTF-8
--
PRAGMA foreign_keys = off;

-- Table: category

INSERT INTO category (id, name) VALUES (1, 'ECONOMY');
INSERT INTO category (id, name) VALUES (2, 'PREMIUM');
INSERT INTO category (id, name) VALUES (3, 'BUSINESS');

-- Table: status

INSERT INTO status (id_status, name) VALUES (1, 'CLOSED');
INSERT INTO status (id_status, name) VALUES (2, 'RESERVED');
INSERT INTO status (id_status, name) VALUES (3, 'SOLD');
INSERT INTO status (id_status, name) VALUES (4, 'FREE');

PRAGMA foreign_keys = on;