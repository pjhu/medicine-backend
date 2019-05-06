DELETE FROM operator;
DELETE FROM external_user;

ALTER TABLE user_order ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE catalog ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE operator ALTER COLUMN id TYPE BIGINT using id::bigint;
ALTER TABLE external_user ALTER COLUMN id TYPE BIGINT using id::bigint;

INSERT INTO operator (id, username, role, active, created_at, created_by, last_modified_at, last_modified_by)
  VALUES (15295695556608, 'admin', 'ADMIN', 'ENABLE', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

INSERT INTO external_user (id, username, password, role, active, created_at, created_by, last_modified_at, last_modified_by)
  VALUES (15295695556609, 'user', '123456', 'USER', 'ENABLE', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');