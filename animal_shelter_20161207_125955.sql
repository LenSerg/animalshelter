-- Valentina Studio --
-- MySQL dump --
-- ---------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- ---------------------------------------------------------


-- CREATE DATABASE "animal_shelter" ------------------------
CREATE DATABASE IF NOT EXISTS `animal_shelter` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `animal_shelter`;
-- ---------------------------------------------------------


-- CREATE TABLE "animal" -----------------------------------
CREATE TABLE `animal` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`reg_date` DateTime NOT NULL,
	`color` VarChar( 255 ) NOT NULL,
	`sex` Int( 255 ) NOT NULL,
	`age` Int( 255 ) NOT NULL,
	`type_id` Int( 255 ) NOT NULL,
	`breed` VarChar( 255 ) NOT NULL,
	`spec_signs` VarChar( 255 ) NOT NULL,
	`status` Int( 255 ) NOT NULL,
	`cageNumber` Int( 255 ) NOT NULL,
	`owner_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 34;
-- ---------------------------------------------------------


-- CREATE TABLE "animal_type" ------------------------------
CREATE TABLE `animal_type` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 16;
-- ---------------------------------------------------------


-- CREATE TABLE "person" -----------------------------------
CREATE TABLE `person` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`surname` VarChar( 255 ) NOT NULL,
	`phone` VarChar( 11 ) NOT NULL,
	`address` VarChar( 255 ) NOT NULL,
	`patronymic` VarChar( 255 ) NOT NULL,
	`email` VarChar( 255 ) NOT NULL,
	`reg_date` DateTime NOT NULL,
	`passport` VarChar( 255 ) NOT NULL,
	`is_private` Int( 255 ) NOT NULL,
	`role` VarChar( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 14;
-- ---------------------------------------------------------


-- CREATE TABLE "photo" ------------------------------------
CREATE TABLE `photo` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`url` VarChar( 255 ) NOT NULL,
	`animal_id` Int( 255 ) NOT NULL,
	`date_add` DateTime NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 38;
-- ---------------------------------------------------------


-- CREATE TABLE "resource" ---------------------------------
CREATE TABLE `resource` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`needs_degree` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 5;
-- ---------------------------------------------------------


-- CREATE TABLE "sick_animal" ------------------------------
CREATE TABLE `sick_animal` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`animal_id` Int( 255 ) NOT NULL,
	`sick_name` VarChar( 255 ) NOT NULL,
	`description` VarChar( 255 ) NOT NULL,
	`date_begin` DateTime NOT NULL,
	`date_end` DateTime NOT NULL,
	`health_money` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 2;
-- ---------------------------------------------------------


-- CREATE TABLE "sponsor" ----------------------------------
CREATE TABLE `sponsor` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`address` VarChar( 255 ) NOT NULL,
	`phone` VarChar( 255 ) NOT NULL,
	`email` VarChar( 255 ) NOT NULL,
	`type` Int( 255 ) NOT NULL,
	`contact_person` VarChar( 255 ) NOT NULL,
	`reg_date` DateTime NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 7;
-- ---------------------------------------------------------


-- CREATE TABLE "sponsorship" ------------------------------
CREATE TABLE `sponsorship` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`resource_id` Int( 255 ) NOT NULL,
	`sponsor_id` Int( 255 ) NOT NULL,
	`amount` Int( 255 ) NOT NULL,
	`date_add` DateTime NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 23;
-- ---------------------------------------------------------


-- CREATE TABLE "status_animal" ----------------------------
CREATE TABLE `status_animal` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`animal_id` Int( 255 ) NOT NULL,
	`status_str` VarChar( 255 ) NOT NULL,
	`person_id` Int( 255 ) NOT NULL,
	`date_st` DateTime NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 131;
-- ---------------------------------------------------------


-- CREATE TABLE "user" -------------------------------------
CREATE TABLE `user` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`surname` VarChar( 255 ) NOT NULL,
	`patronymic` VarChar( 255 ) NOT NULL,
	`login` VarChar( 255 ) NOT NULL,
	`password` VarChar( 255 ) NOT NULL,
	`email` VarChar( 255 ) NOT NULL,
	`phone` VarChar( 255 ) NOT NULL,
	`reg_date` DateTime NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB
AUTO_INCREMENT = 7;
-- ---------------------------------------------------------


-- Dump data of "animal" -----------------------------------
-- ---------------------------------------------------------


-- Dump data of "animal_type" ------------------------------
-- ---------------------------------------------------------


-- Dump data of "person" -----------------------------------
INSERT INTO `person`(`id`,`name`,`surname`,`phone`,`address`,`patronymic`,`email`,`reg_date`,`passport`,`is_private`,`role`) VALUES ( '1', 'admin', 'admin', '', '', '', '', '2000-01-01 00:00:00', '', '0', '' );
-- ---------------------------------------------------------


-- Dump data of "photo" ------------------------------------
-- ---------------------------------------------------------


-- Dump data of "resource" ---------------------------------
-- ---------------------------------------------------------


-- Dump data of "sick_animal" ------------------------------
-- ---------------------------------------------------------


-- Dump data of "sponsor" ----------------------------------
-- ---------------------------------------------------------


-- Dump data of "sponsorship" ------------------------------
-- ---------------------------------------------------------


-- Dump data of "status_animal" ----------------------------
-- ---------------------------------------------------------


-- Dump data of "user" -------------------------------------
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_animal_type_animal" -------------------
CREATE INDEX `lnk_animal_type_animal` USING BTREE ON `animal`( `type_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_person_animal" ------------------------
CREATE INDEX `lnk_person_animal` USING BTREE ON `animal`( `owner_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_animal_photo" -------------------------
CREATE INDEX `lnk_animal_photo` USING BTREE ON `photo`( `animal_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_animal_sick_animal" -------------------
CREATE INDEX `lnk_animal_sick_animal` USING BTREE ON `sick_animal`( `animal_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_resource_sponsorship" -----------------
CREATE INDEX `lnk_resource_sponsorship` USING BTREE ON `sponsorship`( `resource_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_sponsor_sponsorship" ------------------
CREATE INDEX `lnk_sponsor_sponsorship` USING BTREE ON `sponsorship`( `sponsor_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_animal_status_animal" -----------------
CREATE INDEX `lnk_animal_status_animal` USING BTREE ON `status_animal`( `animal_id` );
-- ---------------------------------------------------------


-- CREATE INDEX "lnk_person_status_animal" -----------------
CREATE INDEX `lnk_person_status_animal` USING BTREE ON `status_animal`( `person_id` );
-- ---------------------------------------------------------


-- CREATE LINK "lnk_animal_type_animal" --------------------
ALTER TABLE `animal`
	ADD CONSTRAINT `lnk_animal_type_animal` FOREIGN KEY ( `type_id` )
	REFERENCES `animal_type`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_person_animal" -------------------------
ALTER TABLE `animal`
	ADD CONSTRAINT `lnk_person_animal` FOREIGN KEY ( `owner_id` )
	REFERENCES `person`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_animal_photo" --------------------------
ALTER TABLE `photo`
	ADD CONSTRAINT `lnk_animal_photo` FOREIGN KEY ( `animal_id` )
	REFERENCES `animal`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_animal_sick_animal" --------------------
ALTER TABLE `sick_animal`
	ADD CONSTRAINT `lnk_animal_sick_animal` FOREIGN KEY ( `animal_id` )
	REFERENCES `animal`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_resource_sponsorship" ------------------
ALTER TABLE `sponsorship`
	ADD CONSTRAINT `lnk_resource_sponsorship` FOREIGN KEY ( `resource_id` )
	REFERENCES `resource`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_sponsor_sponsorship" -------------------
ALTER TABLE `sponsorship`
	ADD CONSTRAINT `lnk_sponsor_sponsorship` FOREIGN KEY ( `sponsor_id` )
	REFERENCES `sponsor`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_animal_status_animal" ------------------
ALTER TABLE `status_animal`
	ADD CONSTRAINT `lnk_animal_status_animal` FOREIGN KEY ( `animal_id` )
	REFERENCES `animal`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


-- CREATE LINK "lnk_person_status_animal" ------------------
ALTER TABLE `status_animal`
	ADD CONSTRAINT `lnk_person_status_animal` FOREIGN KEY ( `person_id` )
	REFERENCES `person`( `id` )
	ON DELETE Cascade
	ON UPDATE Cascade;
-- ---------------------------------------------------------


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- ---------------------------------------------------------


