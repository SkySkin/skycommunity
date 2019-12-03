ALTER TABLE QUESTION ALTER COLUMN TITLE varchar(1024);
CREATE TABLE notification
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    notifier bigint NOT NULL,
    receiver bigint NOT NULL,
    outerId bigint NOT NULL,
    type int NOT NULL,
    gmt_create bigint NOT NULL,
    status int DEFAULT 0 NOT NULL
);
COMMENT ON COLUMN notification.notifier IS '要通知的人';
COMMENT ON COLUMN notification.receiver IS '要接收的人';
COMMENT ON COLUMN notification.outerId IS '所需要通知内容的id';
COMMENT ON COLUMN notification.type IS '要通知的类型';
COMMENT ON COLUMN notification.gmt_create IS '创建时间';
COMMENT ON COLUMN notification.status IS '状态';
COMMENT ON TABLE notification IS '通知';