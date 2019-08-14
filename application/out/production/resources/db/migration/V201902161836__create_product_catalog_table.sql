CREATE TABLE catalog (
  id VARCHAR(64) NOT NULL,
  sku VARCHAR(255),
  name VARCHAR(255),
  description VARCHAR(255),
  note VARCHAR(255),
  stock BIGINT,
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);
