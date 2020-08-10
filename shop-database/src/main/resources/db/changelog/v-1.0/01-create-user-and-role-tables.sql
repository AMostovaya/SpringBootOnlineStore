CREATE TABLE roles (
       id bigint NOT NULL auto_increment,
        name VARCHAR (255) NOT NULL,
        PRIMARY KEY (id)
    );
GO

    CREATE TABLE users (
       id bigint NOT NULL AUTO_INCREMENT,
        age INTEGER,
        name VARCHAR (255),
        password VARCHAR (128) NOT NULL,
        PRIMARY KEY (id)
    ) ;

GO
    CREATE TABLE users_roles (
       user_id bigint NOT NULL,
        role_id bigint NOT NULL,
        PRIMARY KEY (user_id, role_id)
    ) ;

GO
   CREATE TABLE categories (
        id bigint NOT NULL AUTO_INCREMENT,
        name VARCHAR (255) NOT NULL,
        PRIMARY KEY (id)
    ) ;
GO


    CREATE TABLE products (
    id bigint NOT NULL AUTO_INCREMENT,
        price DOUBLE (15,2),
        name VARCHAR (255) NOT NULL,
        category_id bigint not null references categories (id),
        PRIMARY KEY (id)
    ) ;

GO




    ALTER TABLE roles
       ADD CONSTRAINT UK_ofx66keruapi6vyqpv6f2or37 UNIQUE (name);
GO

    ALTER TABLE users_roles
       ADD CONSTRAINT FKj6m8fwv7oqv74fcehir1a9ffy
       FOREIGN KEY (role_id)
       REFERENCES roles (id);
GO

    ALTER TABLE users_roles
       ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdxaa
       FOREIGN KEY (user_id)
       REFERENCES users (id);
GO

    ALTER TABLE  categories
        ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdx33 UNIQUE (name);
GO

ALTER TABLE  products
        ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdx34 UNIQUE (name);

GO


