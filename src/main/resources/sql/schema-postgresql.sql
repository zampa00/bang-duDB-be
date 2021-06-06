create table if not exists banners
(
    id             serial  not null
        constraint banners_pk
            primary key,
    name           varchar not null,
    name_jp        varchar not null,
    description    varchar not null,
    description_jp varchar not null,
    start_date     date    not null,
    end_date       date    not null,
    image_hq       varchar not null
);

create table if not exists events
(
    id              serial
        constraint events_pk
            primary key,
    name            varchar not null,
    name_jp         varchar not null,
    description     varchar not null,
    description_jp  varchar not null,
    start_date      date    not null,
    end_date        date    not null,
    image_hq        varchar not null,
    stamp           varchar not null,
    title_point     varchar not null,
    title_rank      varchar not null,
    instrument      varchar not null,
    accessory_point varchar not null,
    accessory_rank  varchar not null
);

create table if not exists cards
(
    id                             varchar(8)   not null
        constraint cards_pk
            primary key,
    banner_id                      int
        constraint cards_banners__fk
            references banners,
    event_id                       int
        constraint cards_events__fk
            references events,
    character_name                 varchar(128) not null,
    band                           varchar(32)  not null,
    card_name                      varchar(128) not null,
    card_name_jp                   varchar(128) not null,
    rarity                         integer      not null,
    attribute                      varchar(16)  not null,
    release_date                   date         not null,
    power                          integer      not null,
    pf                             integer      not null,
    tec                            integer      not null,
    vi                             integer      not null,
    skill_session_name             varchar      not null,
    skill_session_name_jp          varchar      not null,
    skill_session_description      varchar      not null,
    skill_session_description_jp   varchar      not null,
    skill_session_type             varchar      not null,
    skill_dailylife_name           varchar      not null,
    skill_dailylife_name_jp        varchar      not null,
    skill_dailylife_description    varchar      not null,
    skill_dailylife_description_jp varchar      not null,
    is_gacha                       boolean      not null,
    is_unavailable_gacha           boolean      not null,
    is_event                       boolean      not null,
    is_birthday                    boolean      not null,
    is_promo                       boolean      not null,
    src_base_lq                    varchar      not null,
    src_idl_lq                     varchar      not null,
    src_base_hq                    varchar      not null,
    src_idl_hq                     varchar      not null
);

create table if not exists songs
(
    id           serial  not null
        constraint songs_pk
            primary key,
    name         varchar not null,
    name_jp      varchar not null,
    band         varchar not null,
    lyricist     varchar not null,
    composer     varchar not null,
    arranger     varchar not null,
    difficulty   varchar not null,
    other_info   varchar,
    is_cover     boolean not null,
    release_date date    not null,
    image        varchar not null
);
