package com.micodeya.encomiendassp.backend.dao.gral;

import java.util.List;

import com.micodeya.encomiendassp.backend.entities.gral.DepartamentoGeografico;
import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface DepartamentoGeograficoDao extends GenericDAOInterface<DepartamentoGeografico, Integer>{

  	//lectura
	public DepartamentoGeografico getById(Integer idDepartamentoGeografico);
	public DepartamentoGeografico getByExample(DepartamentoGeografico departamentoGeografico);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico);

    //transaccion
    public DepartamentoGeografico agregar(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico);
    public DepartamentoGeografico modificar(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico);
    public DepartamentoGeografico eliminarPorId(InfoAuditoria infoAuditoria, Integer idDepartamentoGeografico);

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN
    
} 
