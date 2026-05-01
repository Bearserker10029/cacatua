-- 1. CREACIÓN DE LA BASE DE DATOS
DROP DATABASE IF EXISTS cacatua_sulfurea;
CREATE DATABASE cacatua_sulfurea;
USE cacatua_sulfurea;

CREATE TABLE configuracion_juego (
    id INT PRIMARY KEY AUTO_INCREMENT,
    filas INT NOT NULL,
    columnas INT NOT NULL,
    picotazos_maximos INT NOT NULL,
    picotazos_restantes INT NOT NULL,
    cacatua_pos_x INT NOT NULL,
    cacatua_pos_y INT NOT NULL,
    pienso_pos_x INT NOT NULL,
    pienso_pos_y INT NOT NULL,
    estado_juego VARCHAR(20) DEFAULT 'EN_CURSO'
);

CREATE TABLE tipo_madera (
    nivel_resistencia INT PRIMARY KEY,
    nombre_capa VARCHAR(50),
    color_hex VARCHAR(7) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE tablero_celdas (
    pos_x INT NOT NULL,
    pos_y INT NOT NULL,
    nivel_resistencia INT NOT NULL,
    es_cielo BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (pos_x, pos_y),
    FOREIGN KEY (nivel_resistencia) REFERENCES tipo_madera(nivel_resistencia)
);

CREATE TABLE tablero_inicial_backup (
    pos_x INT NOT NULL,
    pos_y INT NOT NULL,
    nivel_resistencia INT NOT NULL
);

INSERT INTO tipo_madera VALUES 
(0, 'Hueco/Cielo', '#FFFFFF', 'Madera perforada o espacio libre'),
(1, 'Madera Clara', '#a67b5b', 'Requiere 1 picotazo'),
(2, 'Madera Media', '#6f4e37', 'Requiere 2 picotazos'),
(3, 'Madera Dura', '#4b3621', 'Requiere 3 picotazos');

INSERT INTO configuracion_juego 
(filas, columnas, picotazos_maximos, picotazos_restantes, cacatua_pos_x, cacatua_pos_y, pienso_pos_x, pienso_pos_y) 
VALUES (6, 5, 45, 45, 3, 1, 5, 6);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia, es_cielo) VALUES 
(1,1,0,TRUE), (2,1,0,TRUE), (3,1,0,TRUE), (4,1,0,TRUE), (5,1,0,TRUE);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia) VALUES 
(1,2,3), (2,2,2), (3,2,3), (4,2,1), (5,2,2);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia) VALUES 
(1,3,2), (2,3,3), (3,3,1), (4,3,2), (5,3,3);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia) VALUES 
(1,4,1), (2,4,2), (3,4,3), (4,4,1), (5,4,2);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia) VALUES 
(1,5,3), (2,5,1), (3,5,2), (4,5,3), (5,5,1);

INSERT INTO tablero_celdas (pos_x, pos_y, nivel_resistencia) VALUES 
(1,6,2), (2,6,3), (3,6,1), (4,6,2), (5,6,0);

INSERT INTO tablero_inicial_backup (pos_x, pos_y, nivel_resistencia)
SELECT pos_x, pos_y, nivel_resistencia FROM tablero_celdas;