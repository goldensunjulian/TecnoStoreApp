-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema tecnostore
-- -----------------------------------------------------
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`marca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`sistema_operativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`sistema_operativo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`celular`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`celular` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca_fk` INT NOT NULL,
  `modelo` VARCHAR(45) NOT NULL,
  `precio` DECIMAL(12,2) NOT NULL,
  `stock` INT NOT NULL,
  `sistema_operativo_fk` INT NOT NULL,
  `gama` ENUM("baja", "media", "alta") NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `marca_fk_idx` (`marca_fk` ASC) VISIBLE,
  INDEX `sistema_operativo_fk_idx` (`sistema_operativo_fk` ASC) VISIBLE,
  CONSTRAINT `marca_fk`
    FOREIGN KEY (`marca_fk`)
    REFERENCES `mydb`.`marca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sistema_operativo_fk`
    FOREIGN KEY (`sistema_operativo_fk`)
    REFERENCES `mydb`.`sistema_operativo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`persona` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `identificacion` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identificacion_UNIQUE` (`identificacion` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`rol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(45) NOT NULL,
  `privilegios` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `persona_fk` INT NOT NULL,
  `direccion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `persona_fk_idx` (`persona_fk` ASC) VISIBLE,
  UNIQUE INDEX `persona_fk_UNIQUE` (`persona_fk` ASC) VISIBLE,
  CONSTRAINT `cliente_persona_fk`
    FOREIGN KEY (`persona_fk`)
    REFERENCES `mydb`.`persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`empleado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `persona_fk` INT NOT NULL,
  `rol_fk` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `contraseña` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `persona_fk_idx` (`persona_fk` ASC) VISIBLE,
  INDEX `rol_fk_idx` (`rol_fk` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `persona_fk_UNIQUE` (`persona_fk` ASC) VISIBLE,
  CONSTRAINT `empleado_persona_fk`
    FOREIGN KEY (`persona_fk`)
    REFERENCES `mydb`.`persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rol_fk`
    FOREIGN KEY (`rol_fk`)
    REFERENCES `mydb`.`rol` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cliente_fk` INT NOT NULL,
  `empleado_fk` INT NOT NULL,
  `total` DECIMAL(15,2) NOT NULL,
  `fecha` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `cliente_fk_idx` (`cliente_fk` ASC) VISIBLE,
  INDEX `empleado_fk_idx` (`empleado_fk` ASC) VISIBLE,
  CONSTRAINT `cliente_fk`
    FOREIGN KEY (`cliente_fk`)
    REFERENCES `mydb`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `empleado_fk`
    FOREIGN KEY (`empleado_fk`)
    REFERENCES `mydb`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`detalles_venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`detalles_venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `venta_fk` INT NOT NULL,
  `celular_fk` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `precio_unitario` DECIMAL(12,2) NOT NULL,
  `subtotal` DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `venta_fk_idx` (`venta_fk` ASC) VISIBLE,
  INDEX `celular_fk_idx` (`celular_fk` ASC) VISIBLE,
  CONSTRAINT `venta_fk`
    FOREIGN KEY (`venta_fk`)
    REFERENCES `mydb`.`venta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `celular_fk`
    FOREIGN KEY (`celular_fk`)
    REFERENCES `mydb`.`celular` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
