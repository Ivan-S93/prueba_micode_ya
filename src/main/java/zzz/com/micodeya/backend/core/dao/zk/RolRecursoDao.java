package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.RolRecurso;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Recurso;


public interface RolRecursoDao extends GenericDAOInterface<RolRecurso, Integer>{

  	//lectura
	public RolRecurso getById(Integer idRolRecurso);
	public RolRecurso getByExample(RolRecurso rolRecurso);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, RolRecurso rolRecurso);

    //transaccion
    public RolRecurso agregar(InfoAuditoria infoAuditoria, RolRecurso rolRecurso);
    public RolRecurso modificar(InfoAuditoria infoAuditoria, RolRecurso rolRecurso);
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRolRecurso);


    //transaccion muchomucho

    /**  
    * 1- Agregar al entity  Rol la propiedad:    
    * @Transient
	* private List<RolRecurso> rolRecursoList;
    * 
    * 2- Llamar desde RolDaoImpl
    * Ej: rolRecursoDao.agregarModificar(infoAuditoria, rol.getRolRecursoList(), rol);
    */
    public void agregarModificar(InfoAuditoria infoAuditoria, List<RolRecurso> rolRecursoList, Rol rol);

    /**  
    * 1- Agregar al entity  Recurso la propiedad:    
    * @Transient
	* private List<RolRecurso> rolRecursoList;
    * 
    * 2- Llamar desde RecursoDaoImpl
    * Ej: rolRecursoDao.agregarModificar(infoAuditoria, recurso.getRolRecursoList(), recurso);
    */
    public void agregarModificar(InfoAuditoria infoAuditoria, List<RolRecurso> rolRecursoList, Recurso recurso);


}  
