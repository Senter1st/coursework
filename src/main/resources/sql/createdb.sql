create table Speciality
(
    idSpeciality integer,
    name        varchar,
    description text
);

create table Student
(
    idStudent   integer,
    name        varchar,
    score       float,
    speciality integer,
    foreign key (idSpeciality) references Speciality (idSpeciality)
);
