-- Create the sequence for the address.id field
CREATE SEQUENCE address_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the address table
CREATE TABLE address (
    id BIGINT PRIMARY KEY,
    street VARCHAR(255),
    client_id BIGINT,
    CONSTRAINT fk_address_client FOREIGN KEY (client_id)
        REFERENCES client(id)
);