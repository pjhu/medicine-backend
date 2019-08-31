CREATE TABLE content (
  id BIGINT NOT NULL,
  content_type VARCHAR(255),
  content_status VARCHAR(255),
  published_version BIGINT,
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE content_version (
  id BIGINT NOT NULL,
  content_id BIGINT,
  published_version BIGINT,
  content_status VARCHAR(255),
  published_at TIMESTAMP,
  published_by VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE content_attribute (
  content_version_id BIGINT,
  name VARCHAR(255),
  value VARCHAR(255)
);

