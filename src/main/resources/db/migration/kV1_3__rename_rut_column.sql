ALTER TABLE client
    RENAME COLUMN rut to dni;

ALTER TABLE client
    ADD COLUMN nacionality varchar(15) not null default 'CHILEAN';

ALTER TABLE users
    RENAME COLUMN rut to dni;

ALTER TABLE users
    ADD COLUMN nacionality varchar(15) not null default 'CHILEAN';

ALTER TABLE watermeter
    RENAME COLUMN client_rut to client_dni;

ALTER TABLE invoice
    RENAME COLUMN client_rut to client_dni;

ALTER TABLE bill
    RENAME COLUMN client_rut to client_dni;