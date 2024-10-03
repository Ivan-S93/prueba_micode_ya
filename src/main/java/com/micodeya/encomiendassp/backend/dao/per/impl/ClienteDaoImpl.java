package com.micodeya.encomiendassp.backend.dao.per.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micodeya.encomiendassp.backend.dao.per.ClienteDao;
import com.micodeya.encomiendassp.backend.dao.per.ClienteJpa;
import com.micodeya.encomiendassp.backend.entities.per.Cliente;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class ClienteDaoImpl extends GenericDAO<Cliente, Integer> implements ClienteDao {

    @Autowired
    private ClienteJpa jpa;
    
    public ClienteDaoImpl() {
        referenceId = "idCliente";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idCliente,nombre,apellido,numeroDocumento,fechaNacimientoMask,numeroTelefono,direccion,ciudad,activo";

        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-INI
        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";
        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-FIN

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idCliente","ID", "nombre","Nombre", "apellido","Apellido", "numeroDocumento","Número Documento", "fechaNacimiento","Fecha de Nacimiento", "haNacimientoMask","Fecha de Nacimiento", "numeroTelefono","Número Telefono", "direccion","Dirección", "ciudad","Ciudad", "activo","Activo"));
        
    }
    
    @Override
    protected Class<Cliente> getEntityBeanType() {
        return Cliente.class;
    }

    // KGC-NOREPLACE-OTROS-INI
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, Cliente cliente) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = cliente.getIdCliente() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    // KGC-NOREPLACE-OTROS-FIN					

    @Override
    @Transactional(readOnly = true)
    public Cliente getById(Integer idCliente) {
        return jpa.findById(idCliente)
            .orElseThrow(() -> new ValidacionException("Cliente no encontrada", "idCliente", idCliente));
    }

    @Override
    @Transactional(readOnly = true) 
    public Cliente getByExample(Cliente cliente) {	
        return jpa.findOne(Example.of(cliente, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    //Se ejecuta en el Rest, antes de llamar al DAO
    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, Cliente cliente) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = cliente.getIdCliente()!=null;
        List<FilterAux> filterList = null;

        


		//numero de documento activo unico
		filterList = new LinkedList<FilterAux>();
		if(modificar) filterList.add(new FilterAux("idCliente","ne",cliente.getIdCliente(),"and"));
		filterList.add(new FilterAux("numeroDocumento","eq",cliente.getNumeroDocumento(),"and"));
		filterList.add(new FilterAux("activo","eq",true,"and"));
		if(this.count(new Cliente(),filterList)>0){
			errorValList.add("Ya existe una persona con el mismo numero de documento.");
		}

        errorValList.addAll(verificacionAdicional(infoAuditoria, cliente));

        return errorValList;

    }


    // Setea los valores de los datos por default si el valor ingresado es nulo
    private void setearDatosDefault(InfoAuditoria infoAuditoria, Cliente cliente){

        

    }

    // Setea los datos de la entidad nueva a la entidad recuperada de la BD
    private void setearDatosModificar(InfoAuditoria infoAuditoria, Cliente clienteDto, Cliente clienteExistente){

        clienteExistente.setNombre(clienteDto.getNombre());
		clienteExistente.setApellido(clienteDto.getApellido());
		clienteExistente.setNumeroDocumento(clienteDto.getNumeroDocumento());
		clienteExistente.setFechaNacimiento(clienteDto.getFechaNacimiento());
		clienteExistente.setNumeroTelefono(clienteDto.getNumeroTelefono());
		clienteExistente.setDireccion(clienteDto.getDireccion());
		clienteExistente.setCiudad(clienteDto.getCiudad());
		clienteExistente.setActivo(clienteDto.getActivo());

        setearDatosDefault(infoAuditoria, clienteExistente);

    }
        
    @Override
    @Transactional
    public Cliente agregar(InfoAuditoria infoAuditoria, Cliente cliente) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, cliente);
        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(cliente, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return cliente; 
    }

    @Override
    @Transactional
    public Cliente modificar(InfoAuditoria infoAuditoria, Cliente cliente) {

        Cliente clienteExistente = getById(cliente.getIdCliente());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, cliente, clienteExistente);
        //preModificar(infoAuditoria, cliente, clienteExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(clienteExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return clienteExistente;

    }
    
    @Override
    @Transactional
    public Cliente eliminarPorId(InfoAuditoria infoAuditoria, Integer idCliente) {

        Cliente clienteExistente = jpa.findById(idCliente)
                .orElseThrow(() -> new ValidacionException("Cliente no encontrada para eliminar", "idCliente", idCliente));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idCliente,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return clienteExistente;
    }	

}
