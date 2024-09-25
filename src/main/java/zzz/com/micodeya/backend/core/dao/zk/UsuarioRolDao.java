package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.entities.zk.UsuarioRol;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface UsuarioRolDao extends GenericDAOInterface<UsuarioRol, Integer>{

  	//lectura
	public UsuarioRol getById(Integer idUsuarioRol);
	public UsuarioRol getByExample(UsuarioRol usuarioRol);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol);

    //transaccion
    public UsuarioRol agregar(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol);
    public UsuarioRol modificar(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol);
    public UsuarioRol eliminarPorId(InfoAuditoria infoAuditoria, Integer idUsuarioRol);


    //transaccion muchomucho

    /**  
    * 1- Agregar al entity  Usuario la propiedad:    
    * @Transient
	* private List<UsuarioRol> usuarioRolList;
    * 
    * 2- Llamar desde UsuarioDaoImpl
    * Ej: usuarioRolDao.agregarModificar(infoAuditoria, usuario.getUsuarioRolList(), usuario);
    */
    public void agregarModificar(InfoAuditoria infoAuditoria, List<UsuarioRol> usuarioRolList, Usuario usuario);

    /**  
    * 1- Agregar al entity  Rol la propiedad:    
    * @Transient
	* private List<UsuarioRol> usuarioRolList;
    * 
    * 2- Llamar desde RolDaoImpl
    * Ej: usuarioRolDao.agregarModificar(infoAuditoria, rol.getUsuarioRolList(), rol);
    */
    public void agregarModificar(InfoAuditoria infoAuditoria, List<UsuarioRol> usuarioRolList, Rol rol);

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

    

}  
