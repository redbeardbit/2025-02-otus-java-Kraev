-- Create the sequence for the phone.id field
CREATE SEQUENCE phone_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the phone table
CREATE TABLE phone (
    id BIGINT PRIMARY KEY,
    number VARCHAR(15),
    client_id BIGINT,
    CONSTRAINT fk_phone_client FOREIGN KEY (client_id)
        REFERENCES client(id)
);