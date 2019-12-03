ALTER TABLE USER ADD notification_Count int NULL;
        COMMENT ON COLUMN USER.notification_Count IS '通知的个数';