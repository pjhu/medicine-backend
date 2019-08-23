DROP TABLE IF EXISTS user_order;

CREATE TABLE user_order (
  id BIGINT NOT NULL,
  product_count BIGINT,
  product_amount_total BIGINT,
  order_amount_total BIGINT,
  pay_channel VARCHAR(255),
  order_status VARCHAR(255),
  order_payment_status VARCHAR(255),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  PRIMARY KEY (id)
);

