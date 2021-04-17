--liquibase formatted SQL

--changeset kylsonn.batista:1 dbms:postgresql
CREATE TABLE tb_user
(
  id              BIGSERIAL PRIMARY KEY NOT NULL ,
  username        CHARACTER VARYING(100) UNIQUE NOT NULL,
  password        CHARACTER VARYING(20)  NOT NULL
);

--ROLLBACK DROP TABLE user;