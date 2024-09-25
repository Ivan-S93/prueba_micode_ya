
/* TABLE ZK_AUDITORIA_ESPECIFICA */
CREATE TABLE ZK_AUDITORIA_ESPECIFICA
(
	id_auditoria_especifica numeric(9,0) NOT NULL,
	tabla character varying(60) NOT NULL,
	atributo_id character varying(60) NOT NULL,
	valor_id numeric(9,0) NOT NULL,
	activo boolean NOT NULL,
	atributo character varying(100) NOT NULL,
	valor_anterior text,
	valor_nuevo text,
	uuid_registro character varying(100) NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_AUDITORIA_ESPECIFICA 
	ADD CONSTRAINT "ZK_AUDITORIA_ESPECIFICA-id_auditoria_especifica_pk" PRIMARY KEY (id_auditoria_especifica);

CREATE SEQUENCE ZK_AUDITORIA_ESPECIFICA_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_LOGIN_LOG */
CREATE TABLE ZK_LOGIN_LOG
(
	id_login_log numeric(9,0) NOT NULL,
	usuario character varying(100) NOT NULL,
	tipo character varying(10) NOT NULL,
	request_info character varying(5000) NOT NULL,
	exitoso boolean NOT NULL,
	ip character varying(20) NOT NULL,
	motivo character varying(300),
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_LOGIN_LOG 
	ADD CONSTRAINT "ZK_LOGIN_LOG-id_login_log_pk" PRIMARY KEY (id_login_log);

CREATE SEQUENCE ZK_LOGIN_LOG_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_ROL */
CREATE TABLE ZK_ROL
(
	id_rol numeric(9,0) NOT NULL,
	nombre character varying(100) NOT NULL,
	codigo character varying(60) NOT NULL,
	descripcion character varying(200),
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_ROL 
	ADD CONSTRAINT "ZK_ROL-id_rol_pk" PRIMARY KEY (id_rol);

CREATE SEQUENCE ZK_ROL_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_USUARIO_ROL */
CREATE TABLE ZK_USUARIO_ROL
(
	id_usuario_rol numeric(9,0) NOT NULL,
	codigo_empresa_core character varying(30) NOT NULL,
	id_usuario numeric(9,0) NOT NULL,
	id_rol numeric(9,0) NOT NULL,
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_USUARIO_ROL 
	ADD CONSTRAINT "ZK_USUARIO_ROL-id_usuario_rol_pk" PRIMARY KEY (id_usuario_rol);

CREATE SEQUENCE ZK_USUARIO_ROL_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_CONFIGURACION */
CREATE TABLE ZK_CONFIGURACION
(
	id_configuracion numeric(9,0) NOT NULL,
	nivel character varying(10) NOT NULL,
	codigo character varying(30) NOT NULL,
	subcodigo character varying(30),
	usuario character varying(60),
	dato_numero numeric(9,0),
	dato_texto character varying(500),
	dato_numero_decimal numeric(20,5),
	dato_objeto text,
	dato_fecha_hora timestamp without time zone,
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_CONFIGURACION 
	ADD CONSTRAINT "ZK_CONFIGURACION-id_configuracion_pk" PRIMARY KEY (id_configuracion);

CREATE SEQUENCE ZK_CONFIGURACION_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_ELIMINADO_INTENCION */
CREATE TABLE ZK_ELIMINADO_INTENCION
(
	id_eliminado_intencion numeric(9,0) NOT NULL,
	tabla character varying(60) NOT NULL,
	atributo_id character varying(60) NOT NULL,
	valor_id character varying(60) NOT NULL,
	activo numeric(9,0) NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_ELIMINADO_INTENCION 
	ADD CONSTRAINT "ZK_ELIMINADO_INTENCION-id_eliminado_intencion_pk" PRIMARY KEY (id_eliminado_intencion);

CREATE SEQUENCE ZK_ELIMINADO_INTENCION_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_USUARIO */
CREATE TABLE ZK_USUARIO
(
	id_usuario numeric(9,0) NOT NULL,
	cuenta character varying(60) NOT NULL,
	usuario character varying(60) NOT NULL,
	alias character varying(60),
	nombre character varying(100) NOT NULL,
	apellido character varying(100) NOT NULL,
	correo_principal character varying(120),
	telefono character varying(30),
	fecha_nacimiento date,
	time_out_sesion numeric(9,0) NOT NULL DEFAULT 30,
	contrasenha character varying(200) NOT NULL,
	estado character varying(3) NOT NULL DEFAULT 'ACT',
	confirmar_correo character varying(1) NOT NULL DEFAULT 'N',
	confirmar_telefono character varying(1) NOT NULL DEFAULT 'N',
	tipo_registro character varying(3) NOT NULL,
	avatar character varying(500) NOT NULL DEFAULT '[]',
	imagen_portada character varying(500) NOT NULL DEFAULT '[]',
	fecha_hora_suspension timestamp without time zone,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-id_usuario_pk" PRIMARY KEY (id_usuario);

CREATE SEQUENCE ZK_USUARIO_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_ROL_RECURSO */
CREATE TABLE ZK_ROL_RECURSO
(
	id_rol_recurso numeric(9,0) NOT NULL,
	id_rol numeric(9,0) NOT NULL,
	id_recurso numeric(9,0) NOT NULL,
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_ROL_RECURSO 
	ADD CONSTRAINT "ZK_ROL_RECURSO-id_rol_recurso_pk" PRIMARY KEY (id_rol_recurso);

CREATE SEQUENCE ZK_ROL_RECURSO_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_RECURSO */
CREATE TABLE ZK_RECURSO
(
	id_recurso numeric(9,0) NOT NULL,
	nombre character varying(100) NOT NULL,
	grupo character varying(20) NOT NULL,
	descripcion character varying(200),
	tipo character varying(3) NOT NULL,
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_RECURSO 
	ADD CONSTRAINT "ZK_RECURSO-id_recurso_pk" PRIMARY KEY (id_recurso);

CREATE SEQUENCE ZK_RECURSO_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_FCM_TOKEN */
CREATE TABLE ZK_FCM_TOKEN
(
	id_fcm_token numeric(9,0) NOT NULL,
	fcm_token character varying(200) NOT NULL,
	cuenta character varying(60),
	informacion character varying(10000) NOT NULL,
	ultima_actualizacion timestamp without time zone NOT NULL,
	activo boolean NOT NULL,
	kgc_tabla_config character varying(45),

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_FCM_TOKEN 
	ADD CONSTRAINT "ZK_FCM_TOKEN-id_fcm_token_pk" PRIMARY KEY (id_fcm_token);

CREATE SEQUENCE ZK_FCM_TOKEN_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE ZK_EMPRESA_CORE */
CREATE TABLE ZK_EMPRESA_CORE
(
	id_empresa_core numeric(9,0) NOT NULL,
	codigo character varying(30) NOT NULL,
	nombre_corto character varying(50) NOT NULL,
	nombre_completo character varying(150) NOT NULL,
	observacion character varying(200),
	logo character varying(500) NOT NULL DEFAULT '[]',
	activo boolean NOT NULL,

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_EMPRESA_CORE 
	ADD CONSTRAINT "ZK_EMPRESA_CORE-id_empresa_core_pk" PRIMARY KEY (id_empresa_core);

CREATE SEQUENCE ZK_EMPRESA_CORE_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;




/* TABLE ZK_NOTIFICACION_ENVIADA */
CREATE TABLE ZK_NOTIFICACION_ENVIADA
(
	id_notificacion_enviada numeric(9,0) NOT NULL,
	tipo character varying(10) NOT NULL,
	cuenta character varying(60) NOT NULL,
	fecha_hora_enviado timestamp without time zone NOT NULL,
	token character varying(200),
	titulo character varying(100),
	contenido character varying(300) NOT NULL,
	url_imagen character varying(300),
	carga_util character varying(2000),
	visto boolean NOT NULL DEFAULT 'false',
	fecha_hora_visto timestamp without time zone,
	fecha_hora_vencimiento timestamp without time zone,
	observacion character varying(500),

	/*campos genericos auditoria*/
	zk_usr_crea character varying(60) not null,
	zk_usr_modi character varying(60),
	zk_fec_crea timestamp without time zone not null,
	zk_ult_modi timestamp without time zone not null,
	zk_empresa_core character varying(30) not null,
	zk_cuenta character varying(60) not null,
	zk_eliminado boolean not null,
	zk_fec_elim timestamp without time zone,
	zk_uuid character varying(100) not null
)
WITH (
  OIDS=FALSE
);

ALTER TABLE ZK_NOTIFICACION_ENVIADA 
	ADD CONSTRAINT "ZK_NOTIFICACION_ENVIADA-id_notificacion_enviada_pk" PRIMARY KEY (id_notificacion_enviada);

CREATE SEQUENCE ZK_NOTIFICACION_ENVIADA_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;

ALTER TABLE ZK_NOTIFICACION_ENVIADA 
	ADD CONSTRAINT "ZK_NOTIFICACION_ENVIADA-tipo_check" CHECK (tipo in ( 'BLOQUEO', 'INFO', 'REDIREC', 'SOLOPUSH', 'CHAT' ));




ALTER TABLE ZK_ROL 
	ADD CONSTRAINT "ZK_ROL-nombre_uq" UNIQUE (nombre);

ALTER TABLE ZK_ROL 
	ADD CONSTRAINT "ZK_ROL-codigo_uq" UNIQUE (codigo);


/* CONSTRAINT ZK_USUARIO_ROL */
ALTER TABLE ZK_USUARIO_ROL 
	ADD CONSTRAINT "ZK_USUARIO_ROL-id_usuario_fk"  FOREIGN KEY (id_usuario)
	REFERENCES zk_usuario (id_usuario) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE ZK_USUARIO_ROL 
	ADD CONSTRAINT "ZK_USUARIO_ROL-id_rol_fk"  FOREIGN KEY (id_rol)
	REFERENCES zk_rol (id_rol) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE ZK_RECURSO 
	ADD CONSTRAINT "ZK_RECURSO-nombre_uq" UNIQUE (nombre);


ALTER TABLE ZK_RECURSO 
	ADD CONSTRAINT "ZK_RECURSO-tipo_check" CHECK (tipo in ( 'VIS', 'FUN', 'REP', 'OTR' ));


ALTER TABLE ZK_LOGIN_LOG 
	ADD CONSTRAINT "ZK_LOGIN_LOG-tipo_check" CHECK (tipo in ( 'WEB', 'APPIOS', 'APPDROID', 'RENEWIOS', 'RENEWDROID', 'RENEWWEB','LOGOUTIOS','LOGOUTDROID','LOGOUTWEB' ));

 

/* CONSTRAINT ZK_ROL_RECURSO */
ALTER TABLE ZK_ROL_RECURSO 
	ADD CONSTRAINT "ZK_ROL_RECURSO-id_rol_fk"  FOREIGN KEY (id_rol)
	REFERENCES zk_rol (id_rol) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE ZK_ROL_RECURSO 
	ADD CONSTRAINT "ZK_ROL_RECURSO-id_recurso_fk"  FOREIGN KEY (id_recurso)
	REFERENCES zk_recurso (id_recurso) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-usuario_uq" UNIQUE (usuario);

ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-cuenta_uq" UNIQUE (cuenta);


ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-estado_check" CHECK (estado in ( 'ACT', 'ANU', 'LIM', 'AUT', 'INA', 'BLO', 'SUS' ));

ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-confirmar_correo_check" CHECK (confirmar_correo in ( 'S', 'N' ));

ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-confirmar_telefono_check" CHECK (confirmar_telefono in ( 'S', 'N' ));

ALTER TABLE ZK_USUARIO 
	ADD CONSTRAINT "ZK_USUARIO-tipo_registro_check" CHECK (tipo_registro in ( 'PSI', 'PAP', 'AWE', 'AAP' ));


ALTER TABLE ZK_CONFIGURACION 
	ADD CONSTRAINT "ZK_CONFIGURACION-nivel_check" CHECK (nivel in ( 'USUARIO', 'CUENTA', 'EMPRESA', 'SISTEMA' ));

-- Creacion de indice para tabla zk_usuario columna cuenta
DROP INDEX IF EXISTS  zk_usuario_cuenta_idx;
CREATE INDEX zk_usuario_cuenta_idx ON zk_usuario(cuenta);