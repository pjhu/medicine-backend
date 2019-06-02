DELETE FROM operator;
DELETE FROM external_user;

ALTER TABLE user_order ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE catalog ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE operator ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE external_user ALTER COLUMN id TYPE BIGINT using id::bigint;