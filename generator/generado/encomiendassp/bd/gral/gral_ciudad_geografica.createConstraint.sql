
/* CONSTRAINT GRAL_CIUDAD_GEOGRAFICA */
ALTER TABLE GRAL_CIUDAD_GEOGRAFICA 
	ADD CONSTRAINT "GRAL_CIUDAD_GEOGRAFICA-id_departamento_geografico_fk"  FOREIGN KEY (id_departamento_geografico)
	REFERENCES gral_departamento_geografico (id_departamento_geografico) ON UPDATE NO ACTION ON DELETE NO ACTION;









