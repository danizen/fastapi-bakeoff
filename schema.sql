
CREATE TABLE contact_types (
	type_id SERIAL PRIMARY KEY,
	type_name VARCHAR(30)
);

INSERT INTO contact_types (type_name) 
VALUES ('Relatives'), ('Friends'), ('Colleagues');


CREATE TABLE contacts (
	contact_id SERIAL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	type_id INT NOT NULL REFERENCES contact_types (type_id),
	phone_number VARCHAR(20),
	email VARCHAR(256)
);
