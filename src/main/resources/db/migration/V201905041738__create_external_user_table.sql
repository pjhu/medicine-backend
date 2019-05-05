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

INSERT INTO external_user (id, username, password, role, active, created_at, created_by, last_modified_at, last_modified_by)
  VALUES ('e379d2365d7349babbbe3563eab1dee6', 'user', '123456', 'USER', 'ENABLE', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');