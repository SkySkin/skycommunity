create table USER
(
  ID int auto_increment primary key not null ,
  NAME         VARCHAR(50),
  ACCOUNT_ID   VARCHAR(100),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
);
