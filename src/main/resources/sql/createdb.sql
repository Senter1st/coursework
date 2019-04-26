create table Speciality
(
    idSpeciality integer not null
        constraint Speciality_pk
            primary key autoincrement,
    name         varchar,
    description  text
);

create table Student
(
    idStudent    integer not null
        constraint Student_pk
            primary key autoincrement,
    name         varchar,
    score        float,
    idSpeciality integer
        references Speciality
);

