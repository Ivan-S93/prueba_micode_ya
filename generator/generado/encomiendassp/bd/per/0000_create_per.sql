
/* TABLE PER_CLIENTE */
CREATE TABLE PER_CLIENTE
(
	id_cliente numeric(9,0) NOT NULL,
	nombre character varying(60) NOT NULL,
	apellido character varying(60) NOT NULL,
	numero_documento numeric(9,0) NOT NULL,
	fecha_nacimiento date,
	numero_telefono character varying(30) NOT NULL,
	direccion character varying(60),
	ciudad character varying(60) NOT NULL,
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

ALTER TABLE PER_CLIENTE 
	ADD CONSTRAINT "PER_CLIENTE-id_cliente_pk" PRIMARY KEY (id_cliente);

CREATE SEQUENCE PER_CLIENTE_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


