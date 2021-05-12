CREATE TABLE IF NOT EXISTS cards (
  id VARCHAR DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,

  character_name                VARCHAR      NOT NULL,
  band                          VARCHAR      NOT NULL,
  card_name                     VARCHAR      NOT NULL,
  rarity                        VARCHAR(2)      NOT NULL,
  attribute                     VARCHAR      NOT NULL,
  release_date                  DATE         NOT NULL,

  power                         INT          NOT NULL,
  pf                            INT          NOT NULL,
  tec                           INT          NOT NULL,
  vi                            INT          NOT NULL,

  skill_session_name            VARCHAR      NOT NULL,
  skill_session_description     VARCHAR      NOT NULL,
  skill_session_type            VARCHAR      NOT NULL,
  skill_dailylife_name          VARCHAR      NOT NULL,
  skill_dailylife_description   VARCHAR      NOT NULL,

  is_gacha                      BOOLEAN      NOT NULL,
  is_unavailable_gacha          BOOLEAN      NOT NULL,
  is_birthday                   BOOLEAN      NOT NULL,
  is_promo                      BOOLEAN      NOT NULL,

  src_base_lq                   VARCHAR      NOT NULL,
  src_idl_lq                    VARCHAR      NOT NULL,
  src_base_hq                   VARCHAR      NOT NULL,
  src_idl_hq                    VARCHAR      NOT NULL
);
