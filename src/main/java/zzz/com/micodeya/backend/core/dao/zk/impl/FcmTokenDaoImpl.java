package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.Date;
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
import zzz.com.micodeya.backend.core.dao.zk.FcmTokenDao;
import zzz.com.micodeya.backend.core.dao.zk.FcmTokenJpa;
import zzz.com.micodeya.backend.core.entities.zk.FcmToken;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class FcmTokenDaoImpl extends GenericDAO<FcmToken, Integer> implements FcmTokenDao {

    @Autowired
    private FcmTokenJpa jpa;
    
    public FcmTokenDaoImpl() {
        referenceId = "idFcmToken";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idFcmToken,fcmToken,cuenta,informacion,ultimaActualizacionMask,activo";

        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-INI
        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";
        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-FIN

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idFcmToken","ID", "fcmToken","Fcm Token", "cuenta","Cuenta", "informacion","Informacion", "ultimaActualizacion","Ultima Actualizacion", "imaActualizacionMask","Ultima Actualizacion", "activo","Activo"));
        
    }
    
    @Override
    protected Class<FcmToken> getEntityBeanType() {
        return FcmToken.class;
    }

    // KGC-NOREPLACE-OTROS-INI
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, FcmToken fcmToken) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = fcmToken.getIdFcmToken() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    @Override
    @Transactional(readOnly = true)
    public String getFcmTokenDeCuenta(String cuenta){

        List<FcmToken> resultList = jpa.findTop1ByCuentaAndActivoOrderByUltimaActualizacionDesc(cuenta, true);
        //si existe 
        if(resultList.size()==1){
            return resultList.get(0).getFcmToken();
        }

        return null;

    }

    // KGC-NOREPLACE-OTROS-FIN

    @Override
    @Transactional(readOnly = true)
    public FcmToken getById(Integer idFcmToken) {
        return jpa.findById(idFcmToken)
            .orElseThrow(() -> new ValidacionException("Fcm Token no encontrado", "idFcmToken", idFcmToken));
    }

    @Override
    @Transactional(readOnly = true) 
    public FcmToken getByExample(FcmToken fcmToken) {	
        return jpa.findOne(Example.of(fcmToken, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    //Se ejecuta en el Rest, antes de llamar al DAO
    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, FcmToken fcmToken) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = fcmToken.getIdFcmToken()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, fcmToken));

        return errorValList;

    }


    // Setea los valores de los datos por default si el valor ingresado es nulo
    private void setearDatosDefault(InfoAuditoria infoAuditoria, FcmToken fcmToken){

        

    }

    // Setea los datos de la entidad nueva a la entidad recuperada de la BD
    private void setearDatosModificar(InfoAuditoria infoAuditoria, FcmToken fcmTokenDto, FcmToken fcmTokenExistente){

        fcmTokenExistente.setFcmToken(fcmTokenDto.getFcmToken());
		fcmTokenExistente.setCuenta(fcmTokenDto.getCuenta());
		fcmTokenExistente.setInformacion(fcmTokenDto.getInformacion());
		fcmTokenExistente.setUltimaActualizacion(fcmTokenDto.getUltimaActualizacion());
		fcmTokenExistente.setActivo(fcmTokenDto.getActivo());

        setearDatosDefault(infoAuditoria, fcmTokenExistente);

    }
        
    @Override
    @Transactional
    public FcmToken agregar(InfoAuditoria infoAuditoria, FcmToken fcmToken) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, fcmToken);

        //buscar ultimo registro de ese token
        List<FcmToken> resultList = jpa.findTop1ByFcmTokenAndActivoOrderByUltimaActualizacionDesc(fcmToken.getFcmToken(), true);
        //si existe 
        if(resultList.size()==1){
            // es la misma cuenta, actualizar fechaHora
            FcmToken fcmTokenExistente = resultList.get(0);
            if(fcmTokenExistente.getCuenta().equals(infoAuditoria.getCuenta())){
                fcmToken = fcmTokenExistente;
                fcmToken.setUltimaActualizacion(new Date());
            }else{
                // es otro, se debe desactivar
                fcmTokenExistente.setActivo(false);
                jpa.save(this.preGuardado(fcmTokenExistente, infoAuditoria));
                
            }
        }

        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(fcmToken, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return fcmToken; 
    }

    @Override
    @Transactional
    public FcmToken modificar(InfoAuditoria infoAuditoria, FcmToken fcmToken) {

        FcmToken fcmTokenExistente = getById(fcmToken.getIdFcmToken());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, fcmToken, fcmTokenExistente);
        //preModificar(infoAuditoria, fcmToken, fcmTokenExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(fcmTokenExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return fcmTokenExistente;

    }
    
    @Override
    @Transactional
    public FcmToken eliminarPorId(InfoAuditoria infoAuditoria, Integer idFcmToken) {

        FcmToken fcmTokenExistente = jpa.findById(idFcmToken)
                .orElseThrow(() -> new ValidacionException("Fcm Token no encontrado para eliminar", "idFcmToken", idFcmToken));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idFcmToken,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return fcmTokenExistente;
    }	

}
