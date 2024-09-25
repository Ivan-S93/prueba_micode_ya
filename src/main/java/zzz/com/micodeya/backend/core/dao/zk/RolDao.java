package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface RolDao extends GenericDAOInterface<Rol, Integer>{

  	//lectura
	public Rol getById(Integer idRol);
	public Rol getByExample(Rol rol);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Rol rol);

    //transaccion
    public Rol agregar(InfoAuditoria infoAuditoria, Rol rol);
    public Rol modificar(InfoAuditoria infoAuditoria, Rol rol);
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRol);

   // 


} 
