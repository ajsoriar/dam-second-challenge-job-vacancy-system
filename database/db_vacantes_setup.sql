CREATE DATABASE IF NOT EXISTS gestion_vacantes;
USE gestion_vacantes;

CREATE TABLE Empresas (
  id_empresa INT PRIMARY KEY AUTO_INCREMENT,
  razon_social VARCHAR(45),
  direccion_fiscal VARCHAR(45),
  pais VARCHAR(45)
);

CREATE TABLE categorias (
  id_categoria INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100),
  descripcion VARCHAR(2000)
);

CREATE TABLE vacantes (
  id_vacante INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(200),
  descripcion TEXT,
  fecha DATE,
  salario DOUBLE,
  estatus ENUM('Activa','Inactiva','Eliminada'), -- Ajustar según necesidades
  destacado TINYINT,
  imagen VARCHAR(250),
  detalles TEXT,
  id_Categoria INT,
  id_empresa INT,
  FOREIGN KEY (id_Categoria) REFERENCES categorias(id_categoria),
  FOREIGN KEY (id_empresa) REFERENCES Empresas(id_empresa)
);

CREATE TABLE usuarios (
  username VARCHAR(45) PRIMARY KEY,
  nombre VARCHAR(45),
  apellidos VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(100),
  enabled INT,
  fecha_Registro DATE
);

CREATE TABLE solicitudes (
  id_solicitud INT PRIMARY KEY AUTO_INCREMENT,
  fecha DATE,
  archivo VARCHAR(250),
  comentarios VARCHAR(2000),
  estado TINYINT,
  id_Vacante INT,
  username VARCHAR(45),
  FOREIGN KEY (id_Vacante) REFERENCES vacantes(id_vacante),
  FOREIGN KEY (username) REFERENCES usuarios(username)
);

CREATE TABLE perfiles (
  id_perfil INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100)
);

CREATE TABLE usuarioperfil (
  username VARCHAR(45),
  id_Perfil INT,
  PRIMARY KEY (username, id_Perfil),
  FOREIGN KEY (username) REFERENCES usuarios(username),
  FOREIGN KEY (id_Perfil) REFERENCES perfiles(id_perfil)
);