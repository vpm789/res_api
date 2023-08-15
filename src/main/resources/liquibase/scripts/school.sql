--liquibase formatted sql

--changeset pvladimirov:1
CREATE INDEX student_name_index ON student (name);

--changeset pvladimirov:2
CREATE INDEX faculty_nc_index ON faculty (name, color);