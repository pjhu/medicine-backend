CREATE TABLE operator (
  id VARCHAR(64) NOT NULL,
  username VARCHAR(255),
  role VARCHAR(255),
  active VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO operator (id, username, role, active, created_at, created_by, last_modified_at, last_modified_by)
  VALUES ('ec1f1793acf9493581dbffd0ba251e25', 'admin', 'ADMIN', 'ENABLE', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');