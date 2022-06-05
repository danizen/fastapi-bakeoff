
CREATE TABLE contact_types (
	type_id SERIAL PRIMARY KEY,
	type_name VARCHAR(30)
);

INSERT INTO contact_types (type_name) 
VALUES ('Relatives'), ('Friends'), ('Colleagues');


CREATE TABLE contacts (
	contact_id SERIAL PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	type_id INT NOT NULL
		REFERENCES contact_types(type_id)
);


CREATE TABLE contact_phones (
	phone_id SERIAL PRIMARY KEY,
	phone_number VARCHAR(30),
	contact_id INT NOT NULL
		REFERENCES contacts(contact_id) ON DELETE CASCADE
);


CREATE TABLE contact_emails (
	email_id SERIAL PRIMARY KEY,
	email_address VARCHAR(256),
	contact_id INT NOT NULL
		REFERENCES contacts(contact_id) ON DELETE CASCADE
);
