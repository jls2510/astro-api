DROP TABLE IF EXISTS star;

CREATE TABLE star (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  catalog VARCHAR(250) DEFAULT NULL,
  catalog_id VARCHAR(250) DEFAULT NULL,
  constellation VARCHAR(250) DEFAULT NULL,
  constellation_id VARCHAR(250) DEFAULT NULL,
  v_mag FLOAT DEFAULT NULL,
  binary_class VARCHAR(250) DEFAULT NULL,
  hip VARCHAR(250) DEFAULT NULL,
  ra FLOAT DEFAULT NULL,
  dec FLOAT DEFAULT NULL
);
