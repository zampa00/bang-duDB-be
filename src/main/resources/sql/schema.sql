CREATE TABLE IF NOT EXISTS cards (
  id                VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  character_name    VARCHAR      NOT NULL,
  card_name         VARCHAR      NOT NULL,
  src_base          VARCHAR      NOT NULL,
  src_idl           VARCHAR      NOT NULL
);
