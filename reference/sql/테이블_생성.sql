create table blog (
                      blog_id bigint auto_increment,
                      blog_link varchar(255) not null,
                      blog_rss_link varchar(255) not null,
                      blog_title varchar(255) not null,
                      primary key (blog_id)
);

create table blog_contents (
                               blog_contents_id bigint auto_increment,
                               contents longtext,
                               pub_date timestamp,
                               pub_link varchar(255),
                               title varchar(255),
                               blog_id bigint,
                               primary key (blog_contents_id)
);

create table member (
                        member_id bigint auto_increment,
                        email varchar(255) not null,
                        name varchar(255) not null,
                        role varchar(255) not null,
                        primary key (member_id)
);

create table subscription (
                              subscription_id bigint auto_increment,
                              blog_id bigint,
                              member_id bigint,
                              primary key (subscription_id)
);

alter table blog
    add constraint uk_blog_link unique (blog_link);

alter table blog
    add constraint uk_blog_rss_link unique (blog_rss_link);

alter table member
    add constraint uk_email unique (email);

alter table blog_contents
    add constraint fk_blog
        foreign key (blog_id)
            references blog(blog_id);

alter table subscription
    add constraint fk_subscription_blog
        foreign key (blog_id)
            references blog(blog_id);

alter table subscription
    add constraint fk_member
        foreign key (member_id)
            references member(member_id);

/* schema-mysql.sql 그대로 실행 */
create table SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) not null,
    SESSION_ID            CHAR(36) not null,
    CREATION_TIME         BIGINT   not null,
    LAST_ACCESS_TIME      BIGINT   not null,
    MAX_INACTIVE_INTERVAL INT      not null,
    EXPIRY_TIME           BIGINT   not null,
    PRINCIPAL_NAME        VARCHAR(100),
    constraint SPRING_SESSION_PK
        primary key (PRIMARY_ID)
);

create unique index SPRING_SESSION_IX1
    on SPRING_SESSION (SESSION_ID);

create index SPRING_SESSION_IX2
    on SPRING_SESSION (EXPIRY_TIME);

create index SPRING_SESSION_IX3
    on SPRING_SESSION (PRINCIPAL_NAME);


-- auto-generated definition
create table SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)      not null,
    ATTRIBUTE_NAME     VARCHAR(200)  not null,
    ATTRIBUTE_BYTES    BLOB not null,
    constraint SPRING_SESSION_ATTRIBUTES_PK
        primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
        foreign key (SESSION_PRIMARY_ID) references SPRING_SESSION (PRIMARY_ID)
            on delete cascade
);

