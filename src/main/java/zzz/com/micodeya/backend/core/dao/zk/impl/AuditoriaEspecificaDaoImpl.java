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

import zzz.com.micodeya.backend.core.entities.zk.AuditoriaEspecifica;
import zzz.com.micodeya.backend.core.dao.zk.AuditoriaEspecificaJpa;
import zzz.com.micodeya.backend.core.dao.zk.AuditoriaEspecificaDao;



@Slf4j
@Service
public class AuditoriaEspecificaDaoImpl extends GenericDAO<AuditoriaEspecifica, Integer> implements AuditoriaEspecificaDao {

    @Autowired
    private AuditoriaEspecificaJpa jpa;
    
    public AuditoriaEspecificaDaoImpl(){
        referenceId="idAuditoriaEspecifica";
        atributosDefault="infoAuditoria,idAuditoriaEspecifica,tabla,atributoId,valorId,activo,atributo,valorAnterior,valorNuevo,uuidRegistro";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";

        //nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idAuditoriaEspecifica","ID", "tabla","Tabla", "atributoId","Atributo ID", "valorId","Valor ID", "activo","Activo", "atributo","Atributo", "valorAnterior","Valor Anterior", "valorNuevo","Valor Nuevo", "uuidRegistro","UUID Registro"));
        
    }
    
    @Override
    protected Class<AuditoriaEspecifica> getEntityBeanType() {
        return AuditoriaEspecifica.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public AuditoriaEspecifica getById(Integer idAuditoriaEspecifica) {
        return jpa.findById(idAuditoriaEspecifica)
            .orElseThrow(() -> new ValidacionException("Auditoría Específica no encontrada", "idAuditoriaEspecifica", idAuditoriaEspecifica));
    }

    @Override
    @Transactional(readOnly = true) 
    public AuditoriaEspecifica getByExample(AuditoriaEspecifica auditoriaEspecifica) {	
        return jpa.findOne(Example.of(auditoriaEspecifica,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=auditoriaEspecifica.getIdAuditoriaEspecifica()!=null;

        

        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica){

        

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecificaDto, AuditoriaEspecifica auditoriaEspecificaExistente){

        
        auditoriaEspecificaExistente.setTabla(auditoriaEspecificaDto.getTabla());
		auditoriaEspecificaExistente.setAtributoId(auditoriaEspecificaDto.getAtributoId());
		auditoriaEspecificaExistente.setValorId(auditoriaEspecificaDto.getValorId());
		auditoriaEspecificaExistente.setActivo(auditoriaEspecificaDto.getActivo());
		auditoriaEspecificaExistente.setAtributo(auditoriaEspecificaDto.getAtributo());
		auditoriaEspecificaExistente.setValorAnterior(auditoriaEspecificaDto.getValorAnterior());
		auditoriaEspecificaExistente.setValorNuevo(auditoriaEspecificaDto.getValorNuevo());
		auditoriaEspecificaExistente.setUuidRegistro(auditoriaEspecificaDto.getUuidRegistro());

        setearDatosDefault(infoAuditoria, auditoriaEspecificaExistente);

    }
        
    @Override 
    @Transactional
    public AuditoriaEspecifica agregar(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica) {

        setearDatosDefault(infoAuditoria, auditoriaEspecifica);

        jpa.save(this.preGuardado(auditoriaEspecifica, infoAuditoria));

        return auditoriaEspecifica; 
    }

    @Override
    @Transactional
    public AuditoriaEspecifica modificar(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica) {

        AuditoriaEspecifica auditoriaEspecificaExistente = getById(auditoriaEspecifica.getIdAuditoriaEspecifica());
        
        setearDatosModificar(infoAuditoria, auditoriaEspecifica, auditoriaEspecificaExistente);

        jpa.save(this.preGuardado(auditoriaEspecificaExistente, infoAuditoria));

        return auditoriaEspecificaExistente;

    }
    
    @Override
    @Transactional
    public AuditoriaEspecifica eliminarPorId(InfoAuditoria infoAuditoria, Integer idAuditoriaEspecifica) {

        AuditoriaEspecifica auditoriaEspecificaExistente = jpa.findById(idAuditoriaEspecifica)
                .orElseThrow(() -> new ValidacionException("Auditoría Específica no encontrada para eliminar", "idAuditoriaEspecifica", idAuditoriaEspecifica));

        jpa.deleteById(this.preEliminado(idAuditoriaEspecifica,infoAuditoria));

        return auditoriaEspecificaExistente;
    }	

    // Otros


} 
