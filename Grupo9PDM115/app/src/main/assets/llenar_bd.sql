-- CICLO
DELETE FROM ciclo;
INSERT INTO CICLO (nombreciclo, inicio, fin, estadociclo, inicioperiodoclase, finperiodoclase)
    VALUES ("CICLO I", "2020/02/16", "2020/02/16", 1, "2020/02/16", "2020/02/16");
INSERT INTO CICLO (nombreciclo, inicio, fin, estadociclo, inicioperiodoclase, finperiodoclase)
    VALUES ("CICLO II", "2020/07/25", "2020/07/25", 0, "2020/07/25", "2020/07/25");

-- TIPO GRUPO 
DELETE FROM TIPOGRUPO;
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Teórico");
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Laboratorio");
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Discusión");

-- DIA 
DELETE FROM DIA;
INSERT INTO DIA (nombredia) VALUES ("Sunday");
INSERT INTO DIA (nombredia) VALUES ("Lunes");
INSERT INTO DIA (nombredia) VALUES ("Martes");
INSERT INTO DIA (nombredia) VALUES ("Miércoles");
INSERT INTO DIA (nombredia) VALUES ("Jueves");
INSERT INTO DIA (nombredia) VALUES ("Viernes");
INSERT INTO DIA (nombredia) VALUES ("Sábado");

-- UNIDAD 
DELETE FROM UNIDAD;
INSERT INTO UNIDAD (nombreent, descripcionent, prioridad)
    VALUES ("EISI", "Escuela de Ingeniería de Sistemas Informáticos", 2);
INSERT INTO UNIDAD (nombreent, descripcionent, prioridad)
    VALUES ("UCB", "Unidad de Ciencias Básicas", 1);

-- MATERIA 
DELETE FROM MATERIA;
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("MAT115", "Matemáticas 1", 1, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN115", "Programación para Dispositivos Móviles", 2, 1);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("FIR115", "Física I", 1, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("TSI115", "Teoría de Sistemas", 2, 1);

-- CICLO MATERIA 
DELETE FROM CICLOMATERIA;
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (1, "MAT115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (1, "PRN115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (2, "IEC115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (2, "TSI115");