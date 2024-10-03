package com.micodeya.encomiendassp.backend.dao.per;

import java.util.List;

import com.micodeya.encomiendassp.backend.entities.per.Cliente;
import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface ClienteDao extends GenericDAOInterface<Cliente, Integer>{

  	//lectura
	public Cliente getById(Integer idCliente);
	public Cliente getByExample(Cliente cliente);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Cliente cliente);

    //transaccion
    public Cliente agregar(InfoAuditoria infoAuditoria, Cliente cliente);
    public Cliente modificar(InfoAuditoria infoAuditoria, Cliente cliente);
    public Cliente eliminarPorId(InfoAuditoria infoAuditoria, Integer idCliente);

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN
    
} 
