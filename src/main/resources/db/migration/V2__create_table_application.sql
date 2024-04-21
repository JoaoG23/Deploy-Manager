create table applications (
	id serial primary key not null,
	name_application varchar(200),
	description varchar(200) not null
)