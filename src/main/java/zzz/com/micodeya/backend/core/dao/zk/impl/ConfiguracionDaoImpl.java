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

import zzz.com.micodeya.backend.core.entities.zk.Configuracion;
import zzz.com.micodeya.backend.core.dao.zk.ConfiguracionJpa;
import zzz.com.micodeya.backend.core.dao.zk.ConfiguracionDao;



@Slf4j
@Service
public class ConfiguracionDaoImpl extends GenericDAO<Configuracion, Integer> implements ConfiguracionDao {

    @Autowired
    private ConfiguracionJpa jpa;
    
    public ConfiguracionDaoImpl(){
        referenceId="idConfiguracion";
        atributosDefault="infoAuditoria,idConfiguracion,nivel,codigo,subcodigo,usuario,datoNumero,datoTexto,datoNumeroDecimal,datoObjeto,datoFechaHoraMask,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";

        //nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idConfiguracion","ID", "nivel","Nivel", "codigo","Código", "subcodigo","Subcódigo", "usuario","Usuario", "datoNumero","Dato número", "datoTexto","Dato texto", "datoNumeroDecimal","Dato número decimal", "datoObjeto","Dato objeto JSON", "datoFechaHora","Dato fecha hora"));
		etiquetaAtributos.putAll(Map.of("oFechaHoraMask","Dato fecha hora", "activo","Activo"));
        
    }
    
    @Override
    protected Class<Configuracion> getEntityBeanType() {
        return Configuracion.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public Configuracion getById(Integer idConfiguracion) {
        return jpa.findById(idConfiguracion)
            .orElseThrow(() -> new ValidacionException("Configuración no encontrada", "idConfiguracion", idConfiguracion));
    }

    @Override
    @Transactional(readOnly = true) 
    public Configuracion getByExample(Configuracion configuracion) {	
        return jpa.findOne(Example.of(configuracion,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Configuracion configuracion) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=configuracion.getIdConfiguracion()!=null;

        

        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, Configuracion configuracion){

        

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, Configuracion configuracionDto, Configuracion configuracionExistente){

        
        configuracionExistente.setNivel(configuracionDto.getNivel());
		configuracionExistente.setCodigo(configuracionDto.getCodigo());
		configuracionExistente.setSubcodigo(configuracionDto.getSubcodigo());
		configuracionExistente.setUsuario(configuracionDto.getUsuario());
		configuracionExistente.setDatoNumero(configuracionDto.getDatoNumero());
		configuracionExistente.setDatoTexto(configuracionDto.getDatoTexto());
		configuracionExistente.setDatoNumeroDecimal(configuracionDto.getDatoNumeroDecimal());
		configuracionExistente.setDatoObjeto(configuracionDto.getDatoObjeto());
		configuracionExistente.setDatoFechaHora(configuracionDto.getDatoFechaHora());
		configuracionExistente.setActivo(configuracionDto.getActivo());

        setearDatosDefault(infoAuditoria, configuracionExistente);

    }
        
    @Override 
    @Transactional
    public Configuracion agregar(InfoAuditoria infoAuditoria, Configuracion configuracion) {

        setearDatosDefault(infoAuditoria, configuracion);

        jpa.save(this.preGuardado(configuracion, infoAuditoria));

        return configuracion; 
    }

    @Override
    @Transactional
    public Configuracion modificar(InfoAuditoria infoAuditoria, Configuracion configuracion) {

        Configuracion configuracionExistente = getById(configuracion.getIdConfiguracion());
        
        setearDatosModificar(infoAuditoria, configuracion, configuracionExistente);

        jpa.save(this.preGuardado(configuracionExistente, infoAuditoria));

        return configuracionExistente;

    }
    
    @Override
    @Transactional
    public Configuracion eliminarPorId(InfoAuditoria infoAuditoria, Integer idConfiguracion) {

        Configuracion configuracionExistente = jpa.findById(idConfiguracion)
                .orElseThrow(() -> new ValidacionException("Configuración no encontrada para eliminar", "idConfiguracion", idConfiguracion));

        jpa.deleteById(this.preEliminado(idConfiguracion,infoAuditoria));

        return configuracionExistente;
    }	

    // Otros
    @Override
    @Transactional
    public Configuracion getByCodigoSubcodigoCuenta(String empresaCore, String codigo, String subcodigo, String cuenta) {

        Configuracion example= new Configuracion();
        example.setZkEmpresaCore(empresaCore);
        example.setNivel("CUENTA");
        example.setActivo(true);

        example.setCodigo(codigo);
        example.setSubcodigo(subcodigo);
        example.setZkCuenta(cuenta);
        

        return getByExample(example);
        
    
    }


} 
