-- Luego de crear toda la estructura de la BD de zk ejecutar estos scripts

CREATE OR REPLACE FUNCTION generar_uuid()
RETURNS UUID AS $$
DECLARE
    resultado UUID;
BEGIN
    resultado := md5(now()::text || random()::text)::UUID;
    RETURN resultado;
END;
$$ LANGUAGE plpgsql;


   

/* INSERT zk_usuario si funciona */
insert into zk_usuario
        (  id_usuario, cuenta, usuario, alias, nombre, apellido, correo_principal,
        contrasenha, time_out_sesion, estado, confirmar_correo,confirmar_telefono, tipo_registro, avatar, imagen_portada,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        ( 99, 'admin', 'admin', 'Admin','Admin Name', 'Ape Admin', 'admin@kuaa.com.py',
        '554021dcf9c36d96be8b34991c479ff717b75801944bd6d9f7f86f40cc32ad9f',30, 'ACT','N','N','PSI','[]','[]',
    'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid());     

insert into zk_usuario
        (  id_usuario, cuenta, usuario, alias, nombre, apellido, correo_principal,
        contrasenha, time_out_sesion, estado, confirmar_correo,confirmar_telefono, tipo_registro, avatar, imagen_portada,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        ( 98, 'visitante', 'visitante', 'Visitante','Visitante N', 'Ape Vis', 'visitante@kuaa.com.py',
        '8a9678c7c45717811846bebd4f779be771e52643f178783f009fc37cf5856a73',30, 'ACT','N','N','PSI','[]','[]',
    'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid());     

/* INSERT zk_rol */
insert into zk_rol
        (  id_rol, nombre , codigo,descripcion,activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        ( 99, 'SYS ADMIN','SYSADM', 'Rol SUPER USUARIO - solo para inicial', true,
          'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 98, 'Acceso Básico Web','BASWEB', 'Se asigna al todos los nuevos Web', true,
          'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 97, 'Acceso Básico App','BASAPP', 'Se asigna al todos los nuevos App', true,
          'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 96, 'Visitante Web','VISWEB', 'Rol para pruebas visitante Web', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 95, 'Visitante App','VISAPP', 'Rol para pruebas visitante App', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 94, 'Administrador App','ADMINAPP', 'Rol admin app', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 93, 'Administrador Web','ADMINWEB', 'Rol admin app', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 92, 'Developer App','DEVELOPAPP', 'Rol admin app', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        ( 91, 'Developer Web','DEVELOPWEB', 'Rol admin app', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid());

    
      
/* INSERT zk_recurso */
insert into zk_recurso
        (  id_recurso,grupo, nombre , descripcion, tipo, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (5 ,'zk','Permiso desarrollador - NO LLEVAR A PROD', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (6 ,'zk','Permiso sobre todos los WS listar como invitado', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (7 ,'zk','Permiso interno de sistema', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (8 ,'zk','Full Sys Admin - NO LLEVAR A PROD', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (9 ,'zk','Acceso Básico', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (10 ,'zk','App - Botón Administrar - ', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (11 ,'zk','WEB - Botón Administrar - ', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (12 ,'zk','App - Botón Developer Zone - ', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (13 ,'zk','WEB - Botón Developer Zone - ', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (95 ,'zk','Privilegio Defautl - Menu', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (96 ,'zk','Privilegio Defautl - Listar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (97 ,'zk','Privilegio Defautl - Agregar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (98 ,'zk','Privilegio Defautl - Modificar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (99 ,'zk','Privilegio Defautl - Eliminar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (85 ,'zk','Privilegio Admin Defautl - Menu', '','VIS', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (86 ,'zk','Privilegio Admin Defautl - Listar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (87 ,'zk','Privilegio Admin Defautl - Agregar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (88 ,'zk','Privilegio Admin Defautl - Modificar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (89 ,'zk','Privilegio Admin Defautl - Eliminar', '','FUN', true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;


/* INSERT zk_rol_recurso ROLES SYS ADMIN = 99*/
insert into zk_rol_recurso
        (id_rol_recurso, id_rol, id_recurso, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (nextval('zk_rol_recurso_seq') ,99, 8, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,99, 10, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,99, 11, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;

/* INSERT zk_rol_recurso ROL Acceso Básico Web=98, Acceso Básico App=97, Visitante Web=96, Visitante App=95*/
insert into zk_rol_recurso
        (id_rol_recurso, id_rol, id_recurso, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (nextval('zk_rol_recurso_seq') ,98, 9, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,98, 95, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,98, 96, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,98, 97, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,98, 98, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,98, 99, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 9, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 95, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 96, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 97, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 98, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,97, 99, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,96, 6, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,95, 6, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;

/* INSERT zk_rol_recurso ROL Administrador App=94, Administrador WEB=93*/
insert into zk_rol_recurso
        (id_rol_recurso, id_rol, id_recurso, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (nextval('zk_rol_recurso_seq') ,94, 9, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 10, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 85, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 86, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 87, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 88, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,94, 89, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 9, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 11, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 85, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 86, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 87, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 88, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,93, 89, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;


/* INSERT zk_rol_recurso ROL Developer App=92, Developer WEB=91*/
insert into zk_rol_recurso
        (id_rol_recurso, id_rol, id_recurso, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (nextval('zk_rol_recurso_seq') ,92, 12, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_rol_recurso_seq') ,91, 13, true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;



/* INSERT zk_usuario_rol */
insert into zk_usuario_rol
        (id_usuario_rol, id_usuario, id_rol, codigo_empresa_core, activo,
      zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
    values
        (nextval('zk_usuario_rol_seq') ,99, 99, 'EMPKUAA',true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_usuario_rol_seq') ,98, 96, 'EMPKUAA',true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid()),
        (nextval('zk_usuario_rol_seq') ,98, 95, 'EMPKUAA',true,
        'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
    ;

/*  funcion utilidasd */
CREATE OR REPLACE FUNCTION fn_concat_json_array_property(json_string text, propiedad text, separador text, prefijo text)
RETURNS text AS
$$
DECLARE
  result text := '';
BEGIN
  SELECT string_agg(prefijo || (elemento->>propiedad), separador)
  INTO result
  FROM json_array_elements(json_string::json) AS elemento;
  
  RETURN result;
END;
$$
LANGUAGE plpgsql;




INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(25, 'Eliminado Intencion - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(26, 'Eliminado Intencion - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(44, 'Login Log - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(45, 'Login Log - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(46, 'Fcm Token - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(47, 'Fcm Token - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(48, 'Fcm Token - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(49, 'Fcm Token - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(50, 'Fcm Token - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(95, 'Usuario Rol - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(96, 'Usuario Rol - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(97, 'Usuario Rol - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(98, 'Usuario Rol - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(99, 'Usuario Rol - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(52, 'Empresa Core - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(53, 'Empresa Core - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(54, 'Empresa Core - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(55, 'Empresa Core - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(56, 'Empresa Core - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(20, 'Recurso - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(21, 'Recurso - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(22, 'Recurso - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(23, 'Recurso - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(24, 'Recurso - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(27, 'Auditoria Especifica - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(28, 'Auditoria Especifica - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(10, 'Usuario - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(11, 'Usuario - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(12, 'Usuario - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(13, 'Usuario - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(14, 'Usuario - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(95, 'Rol Recurso - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(96, 'Rol Recurso - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(97, 'Rol Recurso - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(98, 'Rol Recurso - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(99, 'Rol Recurso - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(39, 'Configuracion - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(40, 'Configuracion - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(41, 'Configuracion - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(42, 'Configuracion - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(43, 'Configuracion - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(15, 'Rol - Menu', 'zk', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(16, 'Rol - Listar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(17, 'Rol - Agregar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(18, 'Rol - Modificar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(19, 'Rol - Eliminar', 'zk', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, generar_uuid())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;
