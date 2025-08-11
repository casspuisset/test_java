#Backend pour ChâTop

## Language

This application uses Java 17 with Spring Boot (v. 3.5.3). The database is in mySql.

## Backend configuration

Clone the repository folder on your local IDE.

## Database configuration

Initialize the database with the following script :

```sql
DROP DATABASE IF EXISTS `portaillocataire`;

CREATE DATABASE `portaillocataire`;

USE `portaillocataire`;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `RENTALS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `MESSAGES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);
```

## Cloudinary configuration

To upload the rentals pictures, use cloudinary. You can create your free account here `https://cloudinary.com/` and find your API keys at `https://console.cloudinary.com/settings/api-keys` to configurate the `application.properties` file.

## Add the application.properties file

In src/main/ressources, create a new file `application.properties` with :

```
spring.application.name=api
server.port= 3001

spring.h2.console.enabled=true

security.jwt.key=<YOUR SECURITY KEY TO ENCODE PASSWORDS>

spring.datasource.url=jdbc:mysql://localhost:3306/portaillocataire
spring.datasource.username= <YOUR DATABASE USERNAME>
spring.datasource.password=<YOUR DATABASE PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true

api.cloudinary.secret = <YOUR CLOUDINARY API SECRET>
api.cloudinary.apiKey = <YOUR CLOUDINARY API KEY>
api.cloudinary.cloudName = <YOUR CLOUDINARY CLOUD NAME>
```

The backend service requires this file with your own passwords and keys instead of the `<YOUR VARIABLE>` to work.

## Documentation

You can access the API documentation with Swagger on `http://localhost:3001/swagger-ui/index.html`

## Used dependancies

### Spring Boot

-**spring-boot-starter-data-jpa**

-**spring-boot-starter-oauth2-resource-server**

-**spring-boot-starter-web**

-**spring-boot-devtools**

-**spring-boot-starter-test**

### Security

-**spring-security-test**

### Database

-**com.h2database**

-**mysql-connector-j**

### Documentation

-**springdoc-openapi-starter-webmvc-ui**

### Picture managing

-**cloudinary-http5**

-**cloudinary-taglib**

### Additionnal dependancies

-**lombock**
