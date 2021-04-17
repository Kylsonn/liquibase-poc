-- liquibase formatted SQL

-- changeset kylsonn.batista:2 dbms:postgresql
alter table tb_user
    add COLUMN created_date timestamp NOT NULL,
    add COLUMN active boolean NOT NULL,
    add COLUMN email_checked boolean NOT NULL,
    add COLUMN last_access timestamp;


-- ROLLBACK ALTER TABLE tb_user
-- ROLLBACK    DROP COLUMN created_date,
-- ROLLBACK    DROP COLUMN active,
-- ROLLBACK    DROP COLUMN email_checked,
-- ROLLBACK    DROP COLUMN last_access;