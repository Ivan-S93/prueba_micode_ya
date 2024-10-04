
/* TABLE GRAL_CIUDAD_GEOGRAFICA */
CREATE TABLE GRAL_CIUDAD_GEOGRAFICA
(
	id_ciudad_geografica numeric(9,0) NOT NULL,
	id_departamento_geografico numeric(9,0) NOT NULL,
	nombre character varying(60) NOT NULL,
	activo boolean NOT NULL DEFAULT 'true',

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

ALTER TABLE GRAL_CIUDAD_GEOGRAFICA 
	ADD CONSTRAINT "GRAL_CIUDAD_GEOGRAFICA-id_ciudad_geografica_pk" PRIMARY KEY (id_ciudad_geografica);

CREATE SEQUENCE GRAL_CIUDAD_GEOGRAFICA_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* TABLE GRAL_DEPARTAMENTO_GEOGRAFICO */
CREATE TABLE GRAL_DEPARTAMENTO_GEOGRAFICO
(
	id_departamento_geografico numeric(9,0) NOT NULL,
	codigo text NOT NULL,
	nombre character varying(60) NOT NULL,
	activo boolean NOT NULL DEFAULT 'true',

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

ALTER TABLE GRAL_DEPARTAMENTO_GEOGRAFICO 
	ADD CONSTRAINT "GRAL_DEPARTAMENTO_GEOGRAFICO-id_departamento_geografico_pk" PRIMARY KEY (id_departamento_geografico);

CREATE SEQUENCE GRAL_DEPARTAMENTO_GEOGRAFICO_SEQ
	increment 1 minvalue 1 maxvalue 9223372036854775807 start 100 cache 1;


/* CONSTRAINT GRAL_CIUDAD_GEOGRAFICA */
ALTER TABLE GRAL_CIUDAD_GEOGRAFICA 
	ADD CONSTRAINT "GRAL_CIUDAD_GEOGRAFICA-id_departamento_geografico_fk"  FOREIGN KEY (id_departamento_geografico)
	REFERENCES gral_departamento_geografico (id_departamento_geografico) ON UPDATE NO ACTION ON DELETE NO ACTION;











