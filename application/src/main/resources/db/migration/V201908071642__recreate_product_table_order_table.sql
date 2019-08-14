DROP TABLE IF EXISTS catalog;

CREATE TABLE product (
  id BIGINT NOT NULL,
  name VARCHAR(255),
  brand VARCHAR(255),
  description VARCHAR(255),
  product_status VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE product_sku (
  product_id BIGINT,
  sku VARCHAR(255),
  color VARCHAR(255),
  price BIGINT,
  stock BIGINT
);

DROP TABLE IF EXISTS user_order;

CREATE TABLE user_order (
  id BIGINT NOT NULL,
  product_count BIGINT,
  product_amount_total BIGINT,
  order_amount_total BIGINT,
  pay_channel VARCHAR(255),
  order_status VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE order_product_detail (
  order_id BIGINT,
  product_sku VARCHAR(255),
  product_name VARCHAR(255),
  product_price BIGINT,
  number BIGINT
);

