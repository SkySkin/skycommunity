CREATE TABLE comment
(
    id bigint auto_increment PRIMARY KEY,
    parent_id bigint NOT NULL,
    type int NOT NULL,
    content varchar(1024) not null,
    commentator int not null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint DEFAULT 0
);
COMMENT ON COLUMN comment.parent_id IS '父类id';
COMMENT ON COLUMN comment.type IS '类型';
COMMENT ON COLUMN comment.content IS '内容';
COMMENT ON COLUMN comment.commentator IS '评论人ID';
COMMENT ON COLUMN comment.gmt_create IS '创建时间';
COMMENT ON COLUMN comment.gmt_modified IS '修改时间';
COMMENT ON COLUMN comment.like_count IS '点赞数';