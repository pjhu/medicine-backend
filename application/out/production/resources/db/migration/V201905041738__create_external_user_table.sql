CREATE TABLE external_user (
  id VARCHAR(64) NOT NULL,
  username VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  active VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);