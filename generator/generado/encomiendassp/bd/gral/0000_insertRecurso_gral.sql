

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(95, 'Ciudad Geográfica - Menu', 'gral', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(96, 'Ciudad Geográfica - Listar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(97, 'Ciudad Geográfica - Agregar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(98, 'Ciudad Geográfica - Modificar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(99, 'Ciudad Geográfica - Eliminar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;



INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(95, 'Departamento Geografico - Menu', 'gral', '', 'VIS', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(96, 'Departamento Geografico - Listar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(97, 'Departamento Geografico - Agregar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(98, 'Departamento Geografico - Modificar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;

INSERT INTO zk_recurso
(id_recurso, nombre, grupo, descripcion, tipo, activo, zk_usr_crea, zk_usr_modi, zk_fec_crea, zk_ult_modi, zk_empresa_core, zk_cuenta, zk_eliminado, zk_fec_elim, zk_uuid)
VALUES
	(99, 'Departamento Geografico - Eliminar', 'gral', '', 'FUN', true, 'base', null, NOW(), NOW(), 'EMPKUAA', 'base', false, null, uuid_generate_v4())
ON CONFLICT (id_recurso)
DO UPDATE SET nombre = EXCLUDED.nombre, grupo = EXCLUDED.grupo, descripcion = EXCLUDED.descripcion, tipo = EXCLUDED.tipo, zk_usr_modi = 'base', zk_ult_modi = EXCLUDED.zk_ult_modi;


