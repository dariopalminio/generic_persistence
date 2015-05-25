--DROP USER SA;
--CREATE USER SA PASSWORD "";
DROP SCHEMA testdb IF EXISTS;
CREATE SCHEMA testdb AUTHORIZATION DBA;
DROP TABLE PERSONENTITY IF EXISTS;                
CREATE MEMORY TABLE PERSONENTITY (
                    ID integer identity primary key,  
                    FIRSTNAME varchar(40) not null,
                    LASTNAME varchar(40) not null,
                    DNICODE varchar(40) not null
                );
--GRANT DBA TO SA;
--SET WRITE_DELAY 10;
