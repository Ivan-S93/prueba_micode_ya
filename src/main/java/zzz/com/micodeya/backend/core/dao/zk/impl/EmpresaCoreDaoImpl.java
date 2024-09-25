package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.dao.GenericDAO;

import zzz.com.micodeya.backend.core.entities.zk.EmpresaCore;
import zzz.com.micodeya.backend.core.dao.zk.EmpresaCoreJpa;
import zzz.com.micodeya.backend.core.dao.zk.EmpresaCoreDao;



@Slf4j
@Service
public class EmpresaCoreDaoImpl extends GenericDAO<EmpresaCore, Integer> implements EmpresaCoreDao {

    @Autowired
    private EmpresaCoreJpa jpa;
    
    public EmpresaCoreDaoImpl(){
        referenceId="idEmpresaCore";
        atributosDefault="infoAuditoria,idEmpresaCore,codigo,nombreCorto,nombreCompleto,observacion,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";

        //nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idEmpresaCore","ID", "codigo","Código", "nombreCorto","Nombre Corto", "nombreCompleto","Nombre Completo", "observacion","Observación", "activo","Activo"));
        
    }
    
    @Override
    protected Class<EmpresaCore> getEntityBeanType() {
        return EmpresaCore.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public EmpresaCore getById(Integer idEmpresaCore) {
        return jpa.findById(idEmpresaCore)
            .orElseThrow(() -> new ValidacionException("EmpresaCore no encontrada", "idEmpresaCore", idEmpresaCore));
    }

    @Override
    @Transactional(readOnly = true) 
    public EmpresaCore getByExample(EmpresaCore empresaCore) {	
        return jpa.findOne(Example.of(empresaCore,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, EmpresaCore empresaCore) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=empresaCore.getIdEmpresaCore()!=null;

        

        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, EmpresaCore empresaCore){

        

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, EmpresaCore empresaCoreDto, EmpresaCore empresaCoreExistente){

        
        empresaCoreExistente.setCodigo(empresaCoreDto.getCodigo());
		empresaCoreExistente.setNombreCorto(empresaCoreDto.getNombreCorto());
		empresaCoreExistente.setNombreCompleto(empresaCoreDto.getNombreCompleto());
		empresaCoreExistente.setObservacion(empresaCoreDto.getObservacion());
		empresaCoreExistente.setActivo(empresaCoreDto.getActivo());

        setearDatosDefault(infoAuditoria, empresaCoreExistente);

    }
        
    @Override 
    @Transactional
    public EmpresaCore agregar(InfoAuditoria infoAuditoria, EmpresaCore empresaCore) {

        setearDatosDefault(infoAuditoria, empresaCore);

        jpa.save(this.preGuardado(empresaCore, infoAuditoria));

        return empresaCore; 
    }

    @Override
    @Transactional
    public EmpresaCore modificar(InfoAuditoria infoAuditoria, EmpresaCore empresaCore) {

        EmpresaCore empresaCoreExistente = getById(empresaCore.getIdEmpresaCore());
        
        setearDatosModificar(infoAuditoria, empresaCore, empresaCoreExistente);

        jpa.save(this.preGuardado(empresaCoreExistente, infoAuditoria));

        return empresaCoreExistente;

    }
    
    @Override
    @Transactional
    public EmpresaCore eliminarPorId(InfoAuditoria infoAuditoria, Integer idEmpresaCore) {

        EmpresaCore empresaCoreExistente = jpa.findById(idEmpresaCore)
                .orElseThrow(() -> new ValidacionException("EmpresaCore no encontrada para eliminar", "idEmpresaCore", idEmpresaCore));

        jpa.deleteById(this.preEliminado(idEmpresaCore,infoAuditoria));

        return empresaCoreExistente;
    }	

    // Otros


} 
