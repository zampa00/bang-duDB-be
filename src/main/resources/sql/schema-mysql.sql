create table banners
(
    id          int unsigned auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    start_date  date         not null,
    end_date    date         null,
    image_hq    varchar(255) not null,
    image_lq    varchar(255) not null
);

create table cards
(
    id                          int(5) auto_increment primary key,
    banner_id                   varchar(4),
    card_id                     varchar(8)   not null,
    character_name              varchar(128) not null,
    band                        varchar(32)  not null,
    card_name                   varchar(128) not null,
    rarity                      varchar(2)   not null,
    attribute                   varchar(16)  not null,
    release_date                date         not null,

    power                       int          not null,
    pf                          int          not null,
    tec                         int          not null,
    vi                          int          not null,

    skill_session_name          varchar(255) not null,
    skill_session_description   varchar(255) not null,
    skill_session_type          varchar(16)  not null,
    skill_dailylife_name        varchar(255) not null,
    skill_dailylife_description varchar(255) not null,

    is_gacha                    boolean      not null,
    is_unavailable_gacha        boolean      not null,
    is_event                    boolean      not null,
    is_birthday                 boolean      not null,
    is_promo                    boolean      not null,

    src_base_lq                 varchar(255) not null,
    src_idl_lq                  varchar(255) not null,
    src_base_hq                 varchar(255) not null,
    src_idl_hq                  varchar(255) not null,

    PRIMARY KEY (id),
    FOREIGN KEY (banner_id) REFERENCES banners (id)
);