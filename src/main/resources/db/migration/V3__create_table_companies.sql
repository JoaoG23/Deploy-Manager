create table companies (
	id serial primary key not null,
	name_company varchar(200),
	description varchar(200) not null
);

CREATE TABLE companies_applications (
    id SERIAL PRIMARY KEY,
    id_companies INTEGER REFERENCES companies(id),
    id_applications INTEGER REFERENCES applications(id),
    notes_frontend TEXT,
    notes_backend TEXT,
    status VARCHAR(255) CHECK (status IN ('RODANDO', 'PARADO'))
);
