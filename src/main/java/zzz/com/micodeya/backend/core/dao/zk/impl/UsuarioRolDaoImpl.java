package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioRolDao;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioRolJpa;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.entities.zk.UsuarioRol;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class UsuarioRolDaoImpl extends GenericDAO<UsuarioRol, Integer> implements UsuarioRolDao {

    @Autowired
    private UsuarioRolJpa jpa;
    
    public UsuarioRolDaoImpl() {
        referenceId = "idUsuarioRol";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idUsuarioRol,codigoEmpresaCore,usuario.idUsuario,usuario.usuario,usuario.nombre,usuario.apellido,rol.idRol,rol.nombre,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";

        //nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idUsuarioRol","ID", "codigoEmpresaCore","Código Empresa Core", "usuario","Usuario", "rol","Rol", "activo","Activo"));
        
    }
    
    @Override
    protected Class<UsuarioRol> getEntityBeanType() {
        return UsuarioRol.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public UsuarioRol getById(Integer idUsuarioRol) {
        return jpa.findById(idUsuarioRol)
            .orElseThrow(() -> new ValidacionException("Usuario Rol no encontrado", "idUsuarioRol", idUsuarioRol));
    }

    @Override
    @Transactional(readOnly = true) 
    public UsuarioRol getByExample(UsuarioRol usuarioRol) {	
        return jpa.findOne(Example.of(usuarioRol,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=usuarioRol.getIdUsuarioRol()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, usuarioRol));
        
        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol){

        usuarioRol.setCodigoEmpresaCore(JeBoot.nvl(usuarioRol.getCodigoEmpresaCore(),"EMPKUAA"));
        //usuarioRol.setTimeOutSesion(JeBoot.nvl(usuarioRol.getTimeOutSesion(),30));

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, UsuarioRol usuarioRolDto, UsuarioRol usuarioRolExistente){

        usuarioRolExistente.setCodigoEmpresaCore(usuarioRolDto.getCodigoEmpresaCore());
		usuarioRolExistente.setUsuario(usuarioRolDto.getUsuario());
		usuarioRolExistente.setRol(usuarioRolDto.getRol());
		usuarioRolExistente.setActivo(usuarioRolDto.getActivo());

        setearDatosDefault(infoAuditoria, usuarioRolExistente);

    }
        
    @Override 
    @Transactional
    public UsuarioRol agregar(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, usuarioRol);
        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(usuarioRol, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return usuarioRol; 
    }

    @Override
    @Transactional
    public UsuarioRol modificar(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol) {

        UsuarioRol usuarioRolExistente = getById(usuarioRol.getIdUsuarioRol());

        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, usuarioRol, usuarioRolExistente);
        //preModificar(infoAuditoria, usuarioRol, usuarioRolExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(usuarioRolExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return usuarioRolExistente;

    }
    
    @Override
    @Transactional
    public UsuarioRol eliminarPorId(InfoAuditoria infoAuditoria, Integer idUsuarioRol) {

        UsuarioRol usuarioRolExistente = jpa.findById(idUsuarioRol)
                .orElseThrow(() -> new ValidacionException("Usuario Rol no encontrado para eliminar", "idUsuarioRol", idUsuarioRol));

        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idUsuarioRol,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return usuarioRolExistente;
    }	

    @Override
    @Transactional //llamar desde UsuarioDaoImpl
    public void agregarModificar(InfoAuditoria infoAuditoria, List<UsuarioRol> usuarioRolList, Usuario usuario){

        if(usuarioRolList==null) return;

        for (UsuarioRol usuarioRol : usuarioRolList) {
            
            usuarioRol.setUsuario(usuario);
            
            if( usuarioRol.getIdUsuarioRol()==null){
                agregar(infoAuditoria, usuarioRol);
            }else{
                modificar(infoAuditoria, usuarioRol);
            }
        }
    }

    @Override
    @Transactional //llamar desde RolDaoImpl
    public void agregarModificar(InfoAuditoria infoAuditoria, List<UsuarioRol> usuarioRolList, Rol rol){

        if(usuarioRolList==null) return;

        for (UsuarioRol usuarioRol : usuarioRolList) {
            
            usuarioRol.setRol(rol);
            
            if( usuarioRol.getIdUsuarioRol()==null){
                agregar(infoAuditoria, usuarioRol);
            }else{
                modificar(infoAuditoria, usuarioRol);
            }
        }
    }

    // KGC-NOREPLACE-OTROS-INI

    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, UsuarioRol usuarioRol) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = usuarioRol.getIdUsuarioRol() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    // KGC-NOREPLACE-OTROS-FIN


} 
