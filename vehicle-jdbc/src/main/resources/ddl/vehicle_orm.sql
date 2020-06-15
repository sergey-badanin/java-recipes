DROP TABLE vehicle;

CREATE TABLE vehicle (
    id SERIAL CONSTRAINT mailing_batch_pk PRIMARY KEY,
    vehicle_no VARCHAR(10) NOT NULL,
    color VARCHAR(10),
    wheel INT,
    seat INT
);