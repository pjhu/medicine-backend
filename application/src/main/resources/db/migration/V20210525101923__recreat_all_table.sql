CREATE TABLE operator (
  id BIGINT(20) NOT NULL,
  username VARCHAR(255),
  role VARCHAR(255),
  active VARCHAR(255),
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE external_user (
  id BIGINT(20) NOT NULL,
  username VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  active VARCHAR(255),
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE product (
  id BIGINT(20) NOT NULL,
  name VARCHAR(255),
  brand VARCHAR(255),
  description VARCHAR(255),
  product_status VARCHAR(255),
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE product_sku (
  product_id BIGINT(20),
  sku VARCHAR(255),
  color VARCHAR(255),
  price INT(8),
  stock INT(8)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE user_order (
  id BIGINT(20) NOT NULL,
  product_count INT(8),
  product_amount_total INT(8),
  order_amount_total INT(8),
  pay_channel VARCHAR(255),
  order_status VARCHAR(255),
  order_payment_status VARCHAR(255),
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE order_product_detail (
  order_id BIGINT(20),
  product_sku VARCHAR(255),
  product_name VARCHAR(255),
  product_price INT(8),
  number INT(8)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE content (
  id BIGINT(20) NOT NULL,
  content_type VARCHAR(255),
  content_status VARCHAR(255),
  published_version BIGINT,
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE content_version (
  id BIGINT(20) NOT NULL,
  content_id BIGINT(20),
  published_version BIGINT(20),
  content_status VARCHAR(255),
  published_at DATETIME,
  published_by VARCHAR(255),
  created_at DATETIME,
  created_by VARCHAR(255),
  last_modified_at DATETIME,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE content_attribute (
  content_version_id BIGINT(20),
  name VARCHAR(255),
  value VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



