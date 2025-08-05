#Backend pour ChâTop

## Language

This application use Java 17 with Spring Boot (v. 3.5.3), and the database is in mySql

## Backend configuration

Clone the repository file on your local IDE

## Database configuration

Script to initialize the database :

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
