package com.micodeya.encomiendassp.backend.dao.gral;

import java.util.List;

import com.micodeya.encomiendassp.backend.entities.gral.CiudadGeografica;
import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface CiudadGeograficaDao extends GenericDAOInterface<CiudadGeografica, Integer>{

  	//lectura
	public CiudadGeografica getById(Integer idCiudadGeografica);
	public CiudadGeografica getByExample(CiudadGeografica ciudadGeografica);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica);

    //transaccion
    public CiudadGeografica agregar(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica);
    public CiudadGeografica modificar(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica);
    public CiudadGeografica eliminarPorId(InfoAuditoria infoAuditoria, Integer idCiudadGeografica);

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN
    
} 
