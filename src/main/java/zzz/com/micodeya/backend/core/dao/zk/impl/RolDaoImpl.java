package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.dao.GenericDAO;

import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.dao.zk.RolJpa;
import zzz.com.micodeya.backend.core.dao.zk.RolDao;



@Service
public class RolDaoImpl extends GenericDAO<Rol, Integer> implements RolDao {

    @Autowired
    private RolJpa jpa;
    
    public RolDaoImpl(){
        referenceId="idRol";
        atributosDefault="infoAuditoria,idRol,nombre,codigo,descripcion,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";
        
    }
    
    @Override
    protected Class<Rol> getEntityBeanType() {
        return Rol.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public Rol getById(Integer idRol) {
        return jpa.findById(idRol)
            .orElseThrow(() -> new ValidacionException("Rol no encontrado", "idRol", idRol));
    }

    @Override
    @Transactional(readOnly = true) 
    public Rol getByExample(Rol rol) {	
        return jpa.findOne(Example.of(rol,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Rol rol) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=rol.getIdRol()!=null;

        if ((!modificar && jpa.countByCodigoIgnoreCase(rol.getCodigo()) > 0)
				 || (modificar && jpa.countByIdRolNotAndCodigoIgnoreCase(
					rol.getIdRol(), rol.getCodigo()) > 0)) {
			errorValList.add("Rol con mismo código existente");
		}
		if ((!modificar && jpa.countByNombreIgnoreCase(rol.getNombre()) > 0)
				 || (modificar && jpa.countByIdRolNotAndNombreIgnoreCase(
					rol.getIdRol(), rol.getNombre()) > 0)) {
			errorValList.add("Rol con mismo nombre existente");
		}

        return errorValList;

    }


    private void setearDatosDefault(Rol rol){

        

    }

    private void setearDatosModificar(Rol rolDto, Rol rolExistente){

        //rolExistente.setRol(rolDto.getRol());
        rolExistente.setNombre(rolDto.getNombre());
		rolExistente.setCodigo(rolDto.getCodigo());
		rolExistente.setDescripcion(rolDto.getDescripcion());
		rolExistente.setActivo(rolDto.getActivo());

        setearDatosDefault(rolExistente);

    }
        
    @Override 
    @Transactional
    public Rol agregar(InfoAuditoria infoAuditoria, Rol rol) {

        setearDatosDefault(rol);

        jpa.save(this.preGuardado(rol, infoAuditoria));

        return rol; 
    }

    @Override
    @Transactional
    public Rol modificar(InfoAuditoria infoAuditoria, Rol rol) {

        Rol rolExistente = getById(rol.getIdRol());
        
        setearDatosModificar(rol, rolExistente);

        jpa.save(this.preGuardado(rolExistente, infoAuditoria));

        return rolExistente;

    }
    
    @Override
    @Transactional
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRol) {

        jpa.findById(idRol)
                .orElseThrow(() -> new ValidacionException("Rol no encontrado para eliminar", "idRol", idRol));

        jpa.deleteById(this.preEliminado(idRol,infoAuditoria));
    }	

    


} 
