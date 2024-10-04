package com.micodeya.encomiendassp.backend.dao.gral.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micodeya.encomiendassp.backend.dao.gral.DepartamentoGeograficoDao;
import com.micodeya.encomiendassp.backend.dao.gral.DepartamentoGeograficoJpa;
import com.micodeya.encomiendassp.backend.entities.gral.DepartamentoGeografico;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class DepartamentoGeograficoDaoImpl extends GenericDAO<DepartamentoGeografico, Integer> implements DepartamentoGeograficoDao {

    @Autowired
    private DepartamentoGeograficoJpa jpa;
    
    public DepartamentoGeograficoDaoImpl() {
        referenceId = "idDepartamentoGeografico";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idDepartamentoGeografico,codigo,nombre,activo";

        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-INI
        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";
        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-FIN

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idDepartamentoGeografico","ID", "codigo","Codigo", "nombre","Nombre", "activo","Activo"));
        
    }
    
    @Override
    protected Class<DepartamentoGeografico> getEntityBeanType() {
        return DepartamentoGeografico.class;
    }

    // KGC-NOREPLACE-OTROS-INI
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = departamentoGeografico.getIdDepartamentoGeografico() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    // KGC-NOREPLACE-OTROS-FIN					

    @Override
    @Transactional(readOnly = true)
    public DepartamentoGeografico getById(Integer idDepartamentoGeografico) {
        return jpa.findById(idDepartamentoGeografico)
            .orElseThrow(() -> new ValidacionException("Departamento Geografico no encontrado", "idDepartamentoGeografico", idDepartamentoGeografico));
    }

    @Override
    @Transactional(readOnly = true) 
    public DepartamentoGeografico getByExample(DepartamentoGeografico departamentoGeografico) {	
        return jpa.findOne(Example.of(departamentoGeografico, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    //Se ejecuta en el Rest, antes de llamar al DAO
    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = departamentoGeografico.getIdDepartamentoGeografico()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, departamentoGeografico));

        return errorValList;

    }


    // Setea los valores de los datos por default si el valor ingresado es nulo
    private void setearDatosDefault(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico){

        departamentoGeografico.setActivo(JeBoot.nvl(departamentoGeografico.getActivo(),false));

    }

    // Setea los datos de la entidad nueva a la entidad recuperada de la BD
    private void setearDatosModificar(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeograficoDto, DepartamentoGeografico departamentoGeograficoExistente){

        departamentoGeograficoExistente.setCodigo(departamentoGeograficoDto.getCodigo());
		departamentoGeograficoExistente.setNombre(departamentoGeograficoDto.getNombre());
		departamentoGeograficoExistente.setActivo(departamentoGeograficoDto.getActivo());

        setearDatosDefault(infoAuditoria, departamentoGeograficoExistente);

    }
        
    @Override
    @Transactional
    public DepartamentoGeografico agregar(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, departamentoGeografico);
        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(departamentoGeografico, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return departamentoGeografico; 
    }

    @Override
    @Transactional
    public DepartamentoGeografico modificar(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) {

        DepartamentoGeografico departamentoGeograficoExistente = getById(departamentoGeografico.getIdDepartamentoGeografico());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, departamentoGeografico, departamentoGeograficoExistente);
        //preModificar(infoAuditoria, departamentoGeografico, departamentoGeograficoExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(departamentoGeograficoExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return departamentoGeograficoExistente;

    }
    
    @Override
    @Transactional
    public DepartamentoGeografico eliminarPorId(InfoAuditoria infoAuditoria, Integer idDepartamentoGeografico) {

        DepartamentoGeografico departamentoGeograficoExistente = jpa.findById(idDepartamentoGeografico)
                .orElseThrow(() -> new ValidacionException("Departamento Geografico no encontrado para eliminar", "idDepartamentoGeografico", idDepartamentoGeografico));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idDepartamentoGeografico,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return departamentoGeograficoExistente;
    }	

}
