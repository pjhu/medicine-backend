CREATE TABLE blacklist (
  id BIGINT(20) NOT NULL,
  phone BIGINT(11) NOT NULL,
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;