ALTER TABLE user_order ALTER COLUMN name TYPE BIGINT using id::bigint;
ALTER TABLE user_order RENAME COLUMN name TO catalog_id;

