drop database if exists tienda_higiene_mascotas;
create database tienda_higiene_mascotas;
use tienda_higiene_mascotas;

CREATE TABLE Cliente (
	DNI VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50),
    apellidos VARCHAR(75),
    telefono VARCHAR(9),
    correo VARCHAR(125),
    residencia VARCHAR(125));

CREATE TABLE Especialista (
    DNI VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50),
    apellidos VARCHAR(75),
    telefono VARCHAR(14),
    correo VARCHAR(125),
    residencia VARCHAR(125),
    sueldo DECIMAL(7, 2));

CREATE TABLE Mascota (
    DNI VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50),
    edad INT,
    sexo CHAR(1),
    especie VARCHAR(50),
    raza VARCHAR(50),
    DNI_especialista VARCHAR(9),
    FOREIGN KEY (DNI_especialista) REFERENCES Especialista(DNI));

CREATE TABLE tener (
    Cliente_DNI VARCHAR(9),
    Mascota_DNI VARCHAR(9),
    FOREIGN KEY (Cliente_DNI) REFERENCES Cliente(DNI),
    FOREIGN KEY (Mascota_DNI) REFERENCES Mascota(DNI),
    PRIMARY KEY (Cliente_DNI, Mascota_DNI));

CREATE TABLE Cuentas (
	correo VARCHAR(125) PRIMARY KEY,
    contrasenha VARCHAR(50),
    imagen BLOB DEFAULT NULL);

--  _________________________________________________________________

