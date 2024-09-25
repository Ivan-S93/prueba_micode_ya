package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.EmpresaCore;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface EmpresaCoreDao extends GenericDAOInterface<EmpresaCore, Integer>{

  	//lectura
	public EmpresaCore getById(Integer idEmpresaCore);
	public EmpresaCore getByExample(EmpresaCore empresaCore);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, EmpresaCore empresaCore);

    //transaccion
    public EmpresaCore agregar(InfoAuditoria infoAuditoria, EmpresaCore empresaCore);
    public EmpresaCore modificar(InfoAuditoria infoAuditoria, EmpresaCore empresaCore);
    public EmpresaCore eliminarPorId(InfoAuditoria infoAuditoria, Integer idEmpresaCore);

    // Otros


} 
