drop table if exists photo;
drop table if exists review;
drop table if exists user_point;
drop table if exists user_point_history;

create table review (
    review_id  varbinary(16)   not null comment '리뷰 id'
        primary key,
    user_id    varbinary(16)   not null comment '유저 id',
    place_id   varbinary(16)   not null comment '장소 id',
    content    varchar(255) null comment '리뷰 내용',
    created_at datetime     not null comment '생성 일시',
    updated_at datetime     null comment '수정 일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '리뷰 테이블' ;

create table photo(
    photo_id   varbinary(16) not null comment '사진 id'
        primary key,
    review_id  varbinary(16) not null comment '리뷰 id',
    created_at datetime   not null comment '생성 일시',
    updated_at datetime   null comment '수정 일시',
    constraint photo_review_fk
        foreign key (review_id) references review (review_id)
        on update cascade on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '사진 테이블';

create table user_point(
    user_id            varbinary(16)    not null comment '유저 id'
        primary key,
    point              int default 0 not null comment '현재 포인트',
    use_point          int default 0 not null comment '사용 포인트',
    accumulative_point int default 0 not null comment '누적 포인트'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '유저 포인트 테이블';

create table user_point_history(
    id              bigint auto_increment comment 'pk id'
        primary key,
    user_id         varbinary(16)   not null comment '유저 id',
    review_id       varbinary(16)   not null comment '리뷰 id',
    user_point_type varchar(255) not null comment '포인트 타입(EARN : 적립,WITHDRAW : 회수)',
    change_point    int          not null comment '변동 포인트',
    created_at      datetime     not null comment '생성 일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '유저 포인트 이력 테이블';

create index user_point_history_user_id_index
    on user_point_history (user_id);