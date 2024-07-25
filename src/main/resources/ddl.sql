CREATE DATABASE mysns;

--

CREATE TABLE users (
                       id bigint auto_increment primary key,
                       name varchar(128),
                       email varchar(255),
                       created_at datetime default CURRENT_TIMESTAMP not null,
                       updated_at datetime default CURRENT_TIMESTAMP not null
);


--

CREATE TABLE posts (
                       id bigint auto_increment primary key,
                       user_id bigint,
                       title varchar(30),
                       content varchar(200),
                       created_at datetime default CURRENT_TIMESTAMP not null,
                       updated_at datetime default CURRENT_TIMESTAMP not null
);


--

create index idx_user_id on posts(user_id);
