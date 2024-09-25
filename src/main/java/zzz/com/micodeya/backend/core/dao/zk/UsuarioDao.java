package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.InicioSesionDto;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;


public interface UsuarioDao extends GenericDAOInterface<Usuario, Integer>{

  	//lectura
	public Usuario getById(Integer idUsuario);
	public Usuario getByExample(Usuario usuario);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Usuario usuario);

    //transaccion
    public Usuario agregar(InfoAuditoria infoAuditoria, Usuario usuario);
    public Usuario modificar(InfoAuditoria infoAuditoria, Usuario usuario);
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idUsuario);

   // KGC-AUTO-OTROS: lo que está debajo no se reemplazará al regenerar
   public UsuarioSesionInterno obtenerUsuarioSesion(HttpServletRequest request, InicioSesionDto datosInicioSesion); 
   public UsuarioSesionInterno obtenerUsuarioSesionPorUsuario(HttpServletRequest request, String nombreUsuario, String empresaCore); 


   public boolean verificarTieneAvatar(String usuario);
   public Usuario modificarDatosCuenta(InfoAuditoria infoAuditoria, Usuario usuario);
   public Usuario modificarAvatarCuenta(InfoAuditoria infoAuditoria, Usuario usuario);
   public Usuario modificarPassword(InfoAuditoria infoAuditoria, Integer idUsuario, String passwordActual, String passwordNuevo);
   public Usuario recuperarPassword(InfoAuditoria infoAuditoria, Integer idUsuario, String passwordNuevo);
   public Usuario suspenderCuenta(InfoAuditoria infoAuditoria, Integer idUsuario,String passwordActual);


} 
