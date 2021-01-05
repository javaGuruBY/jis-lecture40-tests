DROP TABLE IF EXISTS student;
create table student (
    id bigint AUTO_INCREMENT,
    name varchar(255),
    last_name varchar(255),
    registration_stamp TIMESTAMP
);
