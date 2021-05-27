create table banners
(
    id          serial  not null
        constraint banners_pk
            primary key,
    name        varchar not null,
    description varchar not null,
    start_date  date    not null,
    end_date    date    not null,
    image_hq    varchar not null,
    image_lq    varchar not null
);

create table cards
(
    id                          serial       not null
        constraint cards_pk
            primary key,
    banner_id                   int
        constraint cards_banners__fk
            references banners,
    card_id                     varchar(8)   not null,
    character_name              varchar(128) not null,
    band                        varchar(32)  not null,
    card_name                   varchar(128) not null,
    rarity                      integer      not null,
    attribute                   varchar(16)  not null,
    release_date                date         not null,
    power                       integer      not null,
    pf                          integer      not null,
    tec                         integer      not null,
    vi                          integer      not null,
    skill_session_name          varchar      not null,
    skill_session_description   varchar      not null,
    skill_session_type          varchar      not null,
    skill_dailylife_name        varchar      not null,
    skill_dailylife_description varchar      not null,
    is_gacha                    boolean      not null,
    is_unavailable_gacha        boolean      not null,
    is_event                    boolean      not null,
    is_birthday                 boolean      not null,
    is_promo                    boolean      not null,
    src_base_lq                 varchar      not null,
    src_idl_lq                  varchar      not null,
    src_base_hq                 varchar      not null,
    src_idl_hq                  varchar      not null
);
