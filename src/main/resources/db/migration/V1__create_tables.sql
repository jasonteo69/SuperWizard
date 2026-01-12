-- Flyway migration: create players and highscores tables
-- Portable SQL usable on Postgres and H2 (H2 supports UUID type)
CREATE TABLE IF NOT EXISTS players (
  id UUID PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  level INTEGER DEFAULT 1,
  xp BIGINT DEFAULT 0,
  inventory TEXT,
  last_saved TIMESTAMP
);

CREATE TABLE IF NOT EXISTS highscores (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  player_id UUID,
  score BIGINT NOT NULL,
  recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (player_id) REFERENCES players(id)
);
