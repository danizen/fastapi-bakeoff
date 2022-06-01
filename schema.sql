
CREATE TABLE contact_types (
	type_id SERIAL PRIMARY KEY,
	type_name VARCHAR2(30)
);


CREATE TABLE contacts (
	contact_id SERIAL PRIMARY KEY,
	first_name VARCHAR2(50) NOT NULL,
	last_name VARCHAR2(50) NOT NULL,
	type_id INT NOT NULL REFERENCES contact_types (type_id),
	phone_number VARCHAR2(20),
	email VARCHAR2(256)
);
