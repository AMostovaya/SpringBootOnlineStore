INSERT INTO users (name, password)
VALUE ('admin','c7b6f034-66ac-4cb3-8fd9-8b3da5b513db'),
('guest','c7b6f034-66ac-4cb3-8fd9-8b3da5b513db');

GO

INSERT INTO roles (name)
VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');

GO

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT id FROM users WHERE name='admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM users WHERE name='guest'), (SELECT id FROM roles WHERE name = 'ROLE_GUEST');

GO