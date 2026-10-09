-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tecnostore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tecnostore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tecnostore` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tecnostore` ;

-- -----------------------------------------------------
-- Table `tecnostore`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`persona` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `identificacion` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identificacion_UNIQUE` (`identificacion` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`rol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(45) NOT NULL,
  `privilegios` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`empleado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `persona_fk` INT NOT NULL,
  `rol_fk` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `contraseña` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `persona_fk_UNIQUE` (`persona_fk` ASC) VISIBLE,
  INDEX `empleado_rol_fk` (`rol_fk` ASC) VISIBLE,
  CONSTRAINT `empleado_persona_fk`
    FOREIGN KEY (`persona_fk`)
    REFERENCES `tecnostore`.`persona` (`id`),
  CONSTRAINT `empleado_rol_fk`
    FOREIGN KEY (`rol_fk`)
    REFERENCES `tecnostore`.`rol` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`auditoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`auditoria` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accion` VARCHAR(150) NOT NULL,
  `fecha` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `empleado_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `auditoria_empleado_fk` (`empleado_fk` ASC) VISIBLE,
  CONSTRAINT `auditoria_empleado_fk`
    FOREIGN KEY (`empleado_fk`)
    REFERENCES `tecnostore`.`empleado` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`marca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`precio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`precio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `valor` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`sistema_operativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`sistema_operativo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`gama`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`gama` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`celular`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`celular` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca_fk` INT NOT NULL,
  `modelo` VARCHAR(45) NOT NULL,
  `precio_fk` INT NOT NULL,
  `stock` INT NOT NULL,
  `sistema_operativo_fk` INT NOT NULL,
  `gama_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `precio_fk_idx` (`precio_fk` ASC) VISIBLE,
  INDEX `celular_marca_fk` (`marca_fk` ASC) VISIBLE,
  INDEX `celular_sistema_operativo_fk` (`sistema_operativo_fk` ASC) VISIBLE,
  INDEX `gama_fk_idx` (`gama_fk` ASC) VISIBLE,
  CONSTRAINT `celular_marca_fk`
    FOREIGN KEY (`marca_fk`)
    REFERENCES `tecnostore`.`marca` (`id`),
  CONSTRAINT `celular_precio_fk`
    FOREIGN KEY (`precio_fk`)
    REFERENCES `tecnostore`.`precio` (`id`),
  CONSTRAINT `celular_sistema_operativo_fk`
    FOREIGN KEY (`sistema_operativo_fk`)
    REFERENCES `tecnostore`.`sistema_operativo` (`id`),
  CONSTRAINT `gama_fk`
    FOREIGN KEY (`gama_fk`)
    REFERENCES `tecnostore`.`gama` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `persona_fk` INT NOT NULL,
  `direccion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `persona_fk_UNIQUE` (`persona_fk` ASC) VISIBLE,
  CONSTRAINT `cliente_persona_fk`
    FOREIGN KEY (`persona_fk`)
    REFERENCES `tecnostore`.`persona` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cliente_fk` INT NOT NULL,
  `empleado_fk` INT NOT NULL,
  `total` DECIMAL(15,2) NOT NULL,
  `fecha` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `venta_cliente_fk` (`cliente_fk` ASC) VISIBLE,
  INDEX `venta_empleado_fk` (`empleado_fk` ASC) VISIBLE,
  CONSTRAINT `venta_cliente_fk`
    FOREIGN KEY (`cliente_fk`)
    REFERENCES `tecnostore`.`cliente` (`id`),
  CONSTRAINT `venta_empleado_fk`
    FOREIGN KEY (`empleado_fk`)
    REFERENCES `tecnostore`.`empleado` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tecnostore`.`detalles_venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tecnostore`.`detalles_venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `venta_fk` INT NOT NULL,
  `celular_fk` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `precio_unitario` DECIMAL(12,2) NOT NULL,
  `subtotal` DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `detalles_venta_fk` (`venta_fk` ASC) VISIBLE,
  INDEX `detalles_celular_fk` (`celular_fk` ASC) VISIBLE,
  CONSTRAINT `detalles_celular_fk`
    FOREIGN KEY (`celular_fk`)
    REFERENCES `tecnostore`.`celular` (`id`),
  CONSTRAINT `detalles_venta_fk`
    FOREIGN KEY (`venta_fk`)
    REFERENCES `tecnostore`.`venta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
