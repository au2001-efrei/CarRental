START TRANSACTION;

-- Tables

CREATE TABLE `Agency` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) NOT NULL UNIQUE KEY,
  `phone` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `longitude` decimal(8,5) NOT NULL,
  `latitude` decimal(7,5) NOT NULL,
  `capacity` int NOT NULL
);

CREATE TABLE `Category` (
  `name` varchar(255) NOT NULL PRIMARY KEY,
  `daily_rate` decimal(9,2) NOT NULL,
  `deposit` decimal(9,2) NOT NULL
);

CREATE TABLE `Customer` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `last_name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  KEY `last_name` (`last_name`),
  KEY `first_name` (`first_name`)
);

CREATE TABLE `Distribution` (
  `id` int NOT NULL PRIMARY KEY,
  `employee_id` int NOT NULL,
  `truck_license_plate` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `source_agency_id` int NOT NULL,
  `destination_agency_id` int NOT NULL,
  KEY `source_agency_id` (`source_agency_id`),
  KEY `destination_agency_id` (`destination_agency_id`),
  KEY `date` (`date`),
  KEY `employee_id` (`employee_id`),
  KEY `truck_license_plate` (`truck_license_plate`)
);

CREATE TABLE `Distribution_Vehicle` (
  `vehicle_license_plate` varchar(255) NOT NULL,
  `distribution_id` int NOT NULL,
  PRIMARY KEY (`vehicle_license_plate`,`distribution_id`),
  KEY `distribution_id` (`distribution_id`)
);

CREATE TABLE `Employee` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `agency_id` int NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `password_hash` char(60) NOT NULL,
  KEY `agency_id` (`agency_id`)
);

CREATE TABLE `Invoice` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `return_rental_reservation_id` int NOT NULL UNIQUE KEY,
  `amount` decimal(9,2) NOT NULL,
  `duration_penalty` decimal(9,2) NOT NULL,
  `fuel_charge` decimal(9,2) NOT NULL,
  `damage_penalty` decimal(9,2) NOT NULL
);

CREATE TABLE `LoyaltyProgram` (
  `name` varchar(255) NOT NULL PRIMARY KEY,
  `duration` int NOT NULL,
  `description` varchar(1023) NOT NULL,
  `price` decimal(9,2) NOT NULL,
  `discount_rate` decimal(2,0) NOT NULL
);

CREATE TABLE `LoyaltySubscription` (
  `customer_id` int NOT NULL PRIMARY KEY,
  `loyalty_program` varchar(255) NOT NULL,
  `subscription_date` date NOT NULL,
  KEY `loyalty_program` (`loyalty_program`)
);

CREATE TABLE `Quote` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `reservation_id` int NOT NULL UNIQUE KEY,
  `amount` decimal(9,2) NOT NULL
);

CREATE TABLE `Rental` (
  `reservation_id` int NOT NULL PRIMARY KEY,
  `employee_id` int DEFAULT NULL,
  `date` date NOT NULL,
  `damage_insurance` tinyint(1) NOT NULL,
  `accident_insurance` tinyint(1) NOT NULL,
  `duration` int NOT NULL,
  KEY `employee_id` (`employee_id`)
);

CREATE TABLE `Reservation` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `agency_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `vehicle_license_plate` varchar(255) NOT NULL,
  `employee_id` int DEFAULT NULL,
  `expected_duration` int NOT NULL,
  KEY `agency_id` (`agency_id`),
  KEY `customer_id` (`customer_id`),
  KEY `vehicle_license_plate` (`vehicle_license_plate`),
  KEY `employee_id` (`employee_id`)
);

CREATE TABLE `Return` (
  `rental_reservation_id` int NOT NULL PRIMARY KEY,
  `agency_id` int NOT NULL,
  `employee_id` int DEFAULT NULL,
  `date` date NOT NULL,
  `fuel_consumption` smallint NOT NULL,
  `damage_level` smallint NOT NULL,
  KEY `agency_id` (`agency_id`),
  KEY `employee_id` (`employee_id`)
);

CREATE TABLE `Truck` (
  `license_plate` varchar(25) NOT NULL PRIMARY KEY
);

CREATE TABLE `Vehicle` (
  `license_plate` varchar(255) NOT NULL PRIMARY KEY,
  `brand` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `mileage` bigint NOT NULL,
  `automatic` tinyint(1) NOT NULL,
  `air_conditioned` tinyint(1) NOT NULL,
  `fuel` varchar(255) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  KEY `brand` (`brand`),
  KEY `category_name` (`category_name`)
);

-- Constraints

ALTER TABLE `Distribution`
  ADD CONSTRAINT `destination_agency_id` FOREIGN KEY (`destination_agency_id`) REFERENCES `Agency` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `source_agency_id` FOREIGN KEY (`source_agency_id`) REFERENCES `Agency` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `truck_license_plate` FOREIGN KEY (`truck_license_plate`) REFERENCES `Truck` (`license_plate`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `Distribution_Vehicle`
  ADD CONSTRAINT `distribution_id` FOREIGN KEY (`distribution_id`) REFERENCES `Distribution` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vehicle_license_plate` FOREIGN KEY (`vehicle_license_plate`) REFERENCES `Vehicle` (`license_plate`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Employee`
  ADD CONSTRAINT `agency_id` FOREIGN KEY (`agency_id`) REFERENCES `Agency` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `Invoice`
  ADD CONSTRAINT `Invoice_return_rental_reservation_id` FOREIGN KEY (`return_rental_reservation_id`) REFERENCES `Return` (`rental_reservation_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `LoyaltySubscription`
  ADD CONSTRAINT `LoytaltySubscription_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `LoytaltySubscription_loyalty_program` FOREIGN KEY (`loyalty_program`) REFERENCES `LoyaltyProgram` (`name`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `Quote`
  ADD CONSTRAINT `Quote_reservation_id` FOREIGN KEY (`reservation_id`) REFERENCES `Reservation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Rental`
  ADD CONSTRAINT `Rental_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Rental_reservation_id` FOREIGN KEY (`reservation_id`) REFERENCES `Reservation` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `Reservation`
  ADD CONSTRAINT `Reservation_agency_id` FOREIGN KEY (`agency_id`) REFERENCES `Agency` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `Reservation_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Reservation_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Reservation_vehicle_license_plate` FOREIGN KEY (`vehicle_license_plate`) REFERENCES `Vehicle` (`license_plate`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `Return`
  ADD CONSTRAINT `Return_agency_id` FOREIGN KEY (`agency_id`) REFERENCES `Agency` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `Return_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Return_rental_reservation_id` FOREIGN KEY (`rental_reservation_id`) REFERENCES `Rental` (`reservation_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Vehicle`
  ADD CONSTRAINT `Vehicle_category_name` FOREIGN KEY (`category_name`) REFERENCES `Category` (`name`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- Data

INSERT INTO `Agency` (`name`, `phone`, `street_number`, `street_name`, `postal_code`, `city`, `longitude`, `latitude`, `capacity`) VALUES
('Villejuif', '+33 (0) 1 23 45 67 89', '30-32', 'avenue de la République', '94800', 'Villejuif', '2.36388', '48.78856', 10);

-- TODO: Category

INSERT INTO `Customer` (`last_name`, `first_name`, `street_number`, `street_name`, `postal_code`, `city`, `phone`) VALUES
('Nguyen', 'Duc-Thomas', NULL, NULL, NULL, NULL, NULL),
('Feldman', 'Yoni', '1', 'rue Machin', '92320', 'Châtillon', NULL),
('aa', 'bb', NULL, NULL, NULL, NULL, NULL),
('bb', 'aa', NULL, NULL, NULL, NULL, NULL);

-- TODO: Distribution
-- TODO: Distribution_Vehicle

INSERT INTO `Employee` (`agency_id`, `last_name`, `first_name`, `street_number`, `street_name`, `postal_code`, `city`, `phone`, `password_hash`) VALUES
(1, 'Garnier', 'Aurélien', '19', 'rue Saint-Honoré', '75001', 'Paris', '+33 (0) 6 12 34 56 78', '$2y$12$nXXNLRonGAX9.T46YM359.ujJozqGP.gGQDHFANY8QcIjQ1DjyLO6'),
(1, 'Sfaxi', 'Lilia', '30-32', 'avenue de la République', '94800', 'Villejuif', '+33 (0) 9 12 34 56 78', '$2y$12$yo6OXp4gqIkDnO0u1CV4zuWBKP2Ibn6tTpEeAvBG6y8ZgTHZAr8UC'); -- Passwords = 123

-- TODO: Invoice
-- TODO: LoyaltyProgram
-- TODO: LoyaltySubscription
-- TODO: Quote
-- TODO: Rental
-- TODO: Reservation
-- TODO: Return
-- TODO: Truck
-- TODO: Vehicle

COMMIT;
