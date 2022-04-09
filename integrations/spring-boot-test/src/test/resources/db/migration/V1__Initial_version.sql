CREATE TABLE animal (
    id SERIAL,
    name VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO animal (name) VALUES
    ('dog'),('cat'),('penguin'),
    ('lax'),('whale'),('ostrich');
