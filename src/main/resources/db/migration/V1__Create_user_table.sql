create table USER
(
    ID int auto_increment primary key,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_UPDATE BIGINT
);