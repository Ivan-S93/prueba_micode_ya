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

import zzz.com.micodeya.backend.core.entities.zk.EliminadoIntencion;
import zzz.com.micodeya.backend.core.dao.zk.EliminadoIntencionJpa;
import zzz.com.micodeya.backend.core.dao.zk.EliminadoIntencionDao;



@Slf4j
@Service
public class EliminadoIntencionDaoImpl extends GenericDAO<EliminadoIntencion, Integer> implements EliminadoIntencionDao {

    @Autowired
    private EliminadoIntencionJpa jpa;
    
    public EliminadoIntencionDaoImpl(){
        referenceId="idEliminadoIntencion";
        atributosDefault="infoAuditoria,idEliminadoIntencion,tabla,atributoId,valorId,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";

        //nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idEliminadoIntencion","ID", "tabla","Tabla", "atributoId","Atributo ID", "valorId","Valor ID", "activo","Activo"));
        
    }
    
    @Override
    protected Class<EliminadoIntencion> getEntityBeanType() {
        return EliminadoIntencion.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public EliminadoIntencion getById(Integer idEliminadoIntencion) {
        return jpa.findById(idEliminadoIntencion)
            .orElseThrow(() -> new ValidacionException("Eliminado Intencion no encontrado", "idEliminadoIntencion", idEliminadoIntencion));
    }

    @Override
    @Transactional(readOnly = true) 
    public EliminadoIntencion getByExample(EliminadoIntencion eliminadoIntencion) {	
        return jpa.findOne(Example.of(eliminadoIntencion,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=eliminadoIntencion.getIdEliminadoIntencion()!=null;

        

        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion){

        

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencionDto, EliminadoIntencion eliminadoIntencionExistente){

        
        eliminadoIntencionExistente.setTabla(eliminadoIntencionDto.getTabla());
		eliminadoIntencionExistente.setAtributoId(eliminadoIntencionDto.getAtributoId());
		eliminadoIntencionExistente.setValorId(eliminadoIntencionDto.getValorId());
		eliminadoIntencionExistente.setActivo(eliminadoIntencionDto.getActivo());

        setearDatosDefault(infoAuditoria, eliminadoIntencionExistente);

    }
        
    @Override 
    @Transactional
    public EliminadoIntencion agregar(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion) {

        setearDatosDefault(infoAuditoria, eliminadoIntencion);

        jpa.save(this.preGuardado(eliminadoIntencion, infoAuditoria));

        return eliminadoIntencion; 
    }

    @Override
    @Transactional
    public EliminadoIntencion modificar(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion) {

        EliminadoIntencion eliminadoIntencionExistente = getById(eliminadoIntencion.getIdEliminadoIntencion());
        
        setearDatosModificar(infoAuditoria, eliminadoIntencion, eliminadoIntencionExistente);

        jpa.save(this.preGuardado(eliminadoIntencionExistente, infoAuditoria));

        return eliminadoIntencionExistente;

    }
    
    @Override
    @Transactional
    public EliminadoIntencion eliminarPorId(InfoAuditoria infoAuditoria, Integer idEliminadoIntencion) {

        EliminadoIntencion eliminadoIntencionExistente = jpa.findById(idEliminadoIntencion)
                .orElseThrow(() -> new ValidacionException("Eliminado Intencion no encontrado para eliminar", "idEliminadoIntencion", idEliminadoIntencion));

        jpa.deleteById(this.preEliminado(idEliminadoIntencion,infoAuditoria));

        return eliminadoIntencionExistente;
    }	

    // Otros


} 
