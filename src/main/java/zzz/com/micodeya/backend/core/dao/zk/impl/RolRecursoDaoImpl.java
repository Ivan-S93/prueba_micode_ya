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

import zzz.com.micodeya.backend.core.entities.zk.RolRecurso;
import zzz.com.micodeya.backend.core.dao.zk.RolRecursoJpa;
import zzz.com.micodeya.backend.core.dao.zk.RolRecursoDao;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Recurso;



@Service
public class RolRecursoDaoImpl extends GenericDAO<RolRecurso, Integer> implements RolRecursoDao {

    @Autowired
    private RolRecursoJpa jpa;
    
    public RolRecursoDaoImpl(){
        referenceId="idRolRecurso";
        atributosDefault="infoAuditoria,idRolRecurso,recurso,rol,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";
        
    }
    
    @Override
    protected Class<RolRecurso> getEntityBeanType() {
        return RolRecurso.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public RolRecurso getById(Integer idRolRecurso) {
        return jpa.findById(idRolRecurso)
            .orElseThrow(() -> new ValidacionException("Rol Recurso no encontrad", "idRolRecurso", idRolRecurso));
    }

    @Override
    @Transactional(readOnly = true) 
    public RolRecurso getByExample(RolRecurso rolRecurso) {	
        return jpa.findOne(Example.of(rolRecurso,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, RolRecurso rolRecurso) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=rolRecurso.getIdRolRecurso()!=null;

        

        return errorValList;

    }


    private void setearDatosDefault(RolRecurso rolRecurso){

        rolRecurso.setActivo(JeBoot.nvl(rolRecurso.getActivo(),true));
        //rolRecurso.setTimeOutSesion(JeBoot.nvl(rolRecurso.getTimeOutSesion(),30));

    }

    private void setearDatosModificar(RolRecurso rolRecursoDto, RolRecurso rolRecursoExistente){

        //rolRecursoExistente.setRolRecurso(rolRecursoDto.getRolRecurso());
        rolRecursoExistente.setRecurso(rolRecursoDto.getRecurso());
		rolRecursoExistente.setRol(rolRecursoDto.getRol());
		rolRecursoExistente.setActivo(rolRecursoDto.getActivo());

        setearDatosDefault(rolRecursoExistente);

    }
        
    @Override 
    @Transactional
    public RolRecurso agregar(InfoAuditoria infoAuditoria, RolRecurso rolRecurso) {

        setearDatosDefault(rolRecurso);

        jpa.save(this.preGuardado(rolRecurso, infoAuditoria));

        return rolRecurso; 
    }

    @Override
    @Transactional
    public RolRecurso modificar(InfoAuditoria infoAuditoria, RolRecurso rolRecurso) {

        RolRecurso rolRecursoExistente = getById(rolRecurso.getIdRolRecurso());

        setearDatosModificar(rolRecurso, rolRecursoExistente);

        jpa.save(this.preGuardado(rolRecursoExistente, infoAuditoria));

        return rolRecursoExistente;

    }
    
    @Override
    @Transactional
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRolRecurso) {

        jpa.findById(idRolRecurso)
                .orElseThrow(() -> new ValidacionException("Rol Recurso no encontrad para eliminar", "idRolRecurso", idRolRecurso));

        jpa.deleteById(this.preEliminado(idRolRecurso,infoAuditoria));
    }	

    @Override
    @Transactional //llamar desde RolDaoImpl
    public void agregarModificar(InfoAuditoria infoAuditoria, List<RolRecurso> rolRecursoList, Rol rol){

        if(rolRecursoList==null) return;

        for (RolRecurso rolRecurso : rolRecursoList) {
            
            rolRecurso.setRol(rol);
            
            if( rolRecurso.getIdRolRecurso()==null){
                agregar(infoAuditoria, rolRecurso);
            }else{
                modificar(infoAuditoria, rolRecurso);
            }
        }
    }

    @Override
    @Transactional //llamar desde RecursoDaoImpl
    public void agregarModificar(InfoAuditoria infoAuditoria, List<RolRecurso> rolRecursoList, Recurso recurso){

        if(rolRecursoList==null) return;

        for (RolRecurso rolRecurso : rolRecursoList) {
            
            rolRecurso.setRecurso(recurso);
            
            if( rolRecurso.getIdRolRecurso()==null){
                agregar(infoAuditoria, rolRecurso);
            }else{
                modificar(infoAuditoria, rolRecurso);
            }
        }
    }


} 
