--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal
BEFORE INSERT ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal_update
BEFORE UPDATE ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_usuario
BEFORE INSERT ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idusuario FROM usuario WHERE idusuario = NEW.idusuario) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-USUARIO UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_usuario_update
BEFORE UPDATE ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idusuario FROM usuario WHERE idusuario = NEW.idusuario) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_ciclomateria
BEFORE INSERT ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-CICLOMATERIA UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_ciclomateria_update
BEFORE UPDATE ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-MATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_materia
BEFORE INSERT ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT codmateria FROM materia WHERE codmateria = NEW.codmateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-MATERIA UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_materia_update
BEFORE UPDATE ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT codmateria FROM materia WHERE codmateria = NEW.codmateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-CICLO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_ciclo
BEFORE INSERT ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-CICLO UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_ciclo_update
BEFORE UPDATE ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL FERIADO-CICLO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_feriado_ciclo
BEFORE INSERT ON feriado
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL FERIADO-CICLO UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_feriado_ciclo_update
BEFORE UPDATE ON feriado
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-TIPOGRUPO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_tipogrupo
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idTipoGrupo FROM tipogrupo WHERE idTipoGrupo = NEW.idTipoGrupo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-TIPOGRUPO UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_tipogrupo_update
BEFORE UPDATE ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idTipoGrupo FROM tipogrupo WHERE idTipoGrupo = NEW.idTipoGrupo) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_ciclomateria
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-CICLOMATERIA UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_ciclomateria_update
BEFORE UPDATE ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL EVENTOESPECIAL-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_eventoespecial_ciclomateria
BEFORE INSERT ON eventoespecial
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL SOLICITUD-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_solicitud_usuario
BEFORE INSERT ON solicitud
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO = NEW.IDUSUARIO) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL USUARIO-ROL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_usuario_rol
BEFORE INSERT ON USUARIO
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDROL FROM ROL WHERE IDROL = NEW.IDROL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL ACCESOUSUARIO-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_accesousuario_rol
BEFORE INSERT ON accesousuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDROL FROM ROL WHERE IDROL = NEW.IDROL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL ACCESOUSUARIO-OPERACIONCRUD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_accesousuario_opcioncrud
BEFORE INSERT ON accesousuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDOPCION FROM OPCIONCRUD WHERE IDOPCION = NEW.IDOPCION) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL USUARIO-UNIDAD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_usuario_unidad
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUNIDAD FROM UNIDAD WHERE IDUNIDAD = NEW.IDUNIDAD) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL MATERIA-UNIDAD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_materia_unidad
BEFORE INSERT ON materia
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUNIDAD FROM UNIDAD WHERE IDUNIDAD = NEW.IDUNIDAD) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal
BEFORE INSERT ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal_update
BEFORE UPDATE ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL detalleReserva-Dia
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_detallereserva_dia
BEFORE INSERT ON DETALLERESERVA
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idDia FROM DIA WHERE idDia = NEW.idDia) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Horario
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_detallereserva_horario
BEFORE INSERT ON DETALLERESERVA
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idHora FROM HORARIO WHERE idHora = NEW.idHora) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Local UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_detallereserva_local
BEFORE UPDATE ON DETALLERESERVA
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idLocal FROM LOCAL WHERE idLocal = NEW.idLocal) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL TipoLocal-Usuario
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_tipoLocal_usuario
BEFORE INSERT ON TipoLocal
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idUsuario FROM Usuario WHERE idUsuario = NEW.idEncargado) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL TipoLocal-Usuario UPDATE
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_tipoLocal_usuario_update
BEFORE UPDATE ON TipoLocal
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idUsuario FROM Usuario WHERE idUsuario = NEW.idEncargado) IS NULL)
      THEN RAISE(IGNORE)
END;
END;
--FIN--
CREATE TRIGGER IF NOT EXISTS delete_cascade_detallereserva_eventoespecial
AFTER DELETE ON DETALLERESERVA
BEGIN
	DELETE FROM EVENTOESPECIAL WHERE IDEVENTOESPECIAL = OLD.IDEVENTOESPECIAL;
END;
--FIN--
CREATE TRIGGER IF NOT EXISTS delete_cascade_solicitud
AFTER DELETE ON SOLICITUD
BEGIN
    DELETE FROM RESERVA WHERE IDSOLICITUD = OLD.IDSOLICITUD;
END;
--FIN--
CREATE TRIGGER IF NOT EXISTS delete_cascade_reserva
AFTER DELETE ON RESERVA
BEGIN
    DELETE FROM DETALLERESERVA WHERE IDDETALLERESERVA = OLD.IDDETALLERESERVA;
END;
--FIN--
CREATE TRIGGER IF NOT EXISTS update_fechafinreserva
AFTER UPDATE ON SOLICITUD WHEN NEW.NUEVOFINPERIODO IS NOT NULL
BEGIN
    UPDATE DETALLERESERVA SET FINPERIODORESERVA = NEW.NUEVOFINPERIODO WHERE IDDETALLERESERVA IN (SELECT IDDETALLERESERVA FROM RESERVA WHERE IDSOLICITUD = NEW.IDSOLICITUD);
END;
--FIN--