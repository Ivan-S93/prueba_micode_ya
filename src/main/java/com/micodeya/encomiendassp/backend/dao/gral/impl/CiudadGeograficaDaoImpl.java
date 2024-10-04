package com.micodeya.encomiendassp.backend.dao.gral.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micodeya.encomiendassp.backend.dao.gral.CiudadGeograficaDao;
import com.micodeya.encomiendassp.backend.dao.gral.CiudadGeograficaJpa;
import com.micodeya.encomiendassp.backend.entities.gral.CiudadGeografica;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class CiudadGeograficaDaoImpl extends GenericDAO<CiudadGeografica, Integer> implements CiudadGeograficaDao {

    @Autowired
    private CiudadGeograficaJpa jpa;
    
    public CiudadGeograficaDaoImpl() {
        referenceId = "idCiudadGeografica";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idCiudadGeografica,departamentoGeografico.idDepartamentoGeografico,departamentoGeografico.codigo,departamentoGeografico.nombre,nombre,activo";

        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-INI
        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";
        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-FIN

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idCiudadGeografica","ID", "departamentoGeografico","Departamento Geográfico", "nombre","Nombre", "activo","Activo"));
        
    }
    
    @Override
    protected Class<CiudadGeografica> getEntityBeanType() {
        return CiudadGeografica.class;
    }

    // KGC-NOREPLACE-OTROS-INI
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = ciudadGeografica.getIdCiudadGeografica() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    // KGC-NOREPLACE-OTROS-FIN					

    @Override
    @Transactional(readOnly = true)
    public CiudadGeografica getById(Integer idCiudadGeografica) {
        return jpa.findById(idCiudadGeografica)
            .orElseThrow(() -> new ValidacionException("Ciudad Geográfica no encontrado", "idCiudadGeografica", idCiudadGeografica));
    }

    @Override
    @Transactional(readOnly = true) 
    public CiudadGeografica getByExample(CiudadGeografica ciudadGeografica) {	
        return jpa.findOne(Example.of(ciudadGeografica, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    //Se ejecuta en el Rest, antes de llamar al DAO
    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = ciudadGeografica.getIdCiudadGeografica()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, ciudadGeografica));

        return errorValList;

    }


    // Setea los valores de los datos por default si el valor ingresado es nulo
    private void setearDatosDefault(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica){

        ciudadGeografica.setActivo(JeBoot.nvl(ciudadGeografica.getActivo(),false));

    }

    // Setea los datos de la entidad nueva a la entidad recuperada de la BD
    private void setearDatosModificar(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeograficaDto, CiudadGeografica ciudadGeograficaExistente){

        ciudadGeograficaExistente.setDepartamentoGeografico(ciudadGeograficaDto.getDepartamentoGeografico());
		ciudadGeograficaExistente.setNombre(ciudadGeograficaDto.getNombre());
		ciudadGeograficaExistente.setActivo(ciudadGeograficaDto.getActivo());

        setearDatosDefault(infoAuditoria, ciudadGeograficaExistente);

    }
        
    @Override
    @Transactional
    public CiudadGeografica agregar(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, ciudadGeografica);
        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(ciudadGeografica, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return ciudadGeografica; 
    }

    @Override
    @Transactional
    public CiudadGeografica modificar(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) {

        CiudadGeografica ciudadGeograficaExistente = getById(ciudadGeografica.getIdCiudadGeografica());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, ciudadGeografica, ciudadGeograficaExistente);
        //preModificar(infoAuditoria, ciudadGeografica, ciudadGeograficaExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(ciudadGeograficaExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return ciudadGeograficaExistente;

    }
    
    @Override
    @Transactional
    public CiudadGeografica eliminarPorId(InfoAuditoria infoAuditoria, Integer idCiudadGeografica) {

        CiudadGeografica ciudadGeograficaExistente = jpa.findById(idCiudadGeografica)
                .orElseThrow(() -> new ValidacionException("Ciudad Geográfica no encontrado para eliminar", "idCiudadGeografica", idCiudadGeografica));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idCiudadGeografica,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return ciudadGeograficaExistente;
    }	

}
