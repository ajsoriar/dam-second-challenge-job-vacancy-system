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

-- Insertar datos de prueba
INSERT INTO Empresas (razon_social, direccion_fiscal, pais) VALUES
('Empresa A', 'Calle 123, Ciudad', 'España'),
('Empresa B', 'Avenida 456, Ciudad', 'México');

INSERT INTO categorias (nombre, descripcion) VALUES
('Tecnología', 'Ofertas de empleo en el sector tecnológico'),
('Salud', 'Ofertas de empleo en el sector salud');

INSERT INTO vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_Categoria, id_empresa) VALUES
('Desarrollador Web', 'Se busca desarrollador con experiencia en PHP', '2025-03-20', 35000, 'Activa', 1, 'imagen1.jpg', 'Detalles del puesto', 1, 1),
('Enfermero/a', 'Hospital busca enfermero/a con experiencia', '2025-03-15', 28000, 'Activa', 0, 'imagen2.jpg', 'Detalles del puesto', 2, 2);

INSERT INTO usuarios (username, nombre, apellidos, email, password, enabled, fecha_Registro) VALUES
('usuario1', 'Juan', 'Pérez', 'juan@example.com', '1234', 1, '2025-03-01'),
('usuario2', 'Ana', 'Gómez', 'ana@example.com', '1234', 1, '2025-03-02');

INSERT INTO solicitudes (fecha, archivo, comentarios, estado, id_Vacante, username) VALUES
('2025-03-21', 'cv_juan.pdf', 'Estoy interesado en la vacante', 1, 1, 'usuario1'),
('2025-03-22', 'cv_ana.pdf', 'Me gustaría postularme', 1, 2, 'usuario2');

INSERT INTO perfiles (nombre) VALUES
('Administrador'),
('Candidato');

INSERT INTO usuarioperfil (username, id_Perfil) VALUES
('usuario1', 1),
('usuario2', 2);
