CREATE TABLE "order" (
  id VARCHAR(64) NOT NULL,
  name VARCHAR(255),
  shortDescription VARCHAR(255),
  longDescription VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);
