package zzz.com.micodeya.backend.core.rest.zk;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.zk.FcmTokenDao;
import zzz.com.micodeya.backend.core.dto.PuntoGeoDto;
import zzz.com.micodeya.backend.core.entities.zk.FcmToken;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;

@Slf4j
@RestController
public class FcmTokenRest {

    private final String BASE_API = "/api/zk/fcmToken";

    @Autowired
    private FcmTokenDao fcmTokenDao;
    
    
    // KGC-NOREPLACE-OTROS-INI

    private void transformacionAdicional(InfoAuditoria infoAuditoria, FcmToken fcmToken) {
        boolean modificar = fcmToken.getIdFcmToken() != null;
    }


    @PostMapping("/api/public/addFcmToken" )
    public Map<String, Object> agregarSiempre(
                HttpServletRequest request,
				@RequestBody FcmToken fcmToken
            ) {

        Integer[] idRecursoPermisoArray = { 9, 6 };
        JeResponse jeResponse=new JeResponse("Fcm Token creado correctamente","Error grave al crear Fcm Token");
        UsuarioSesionInterno userSession = null;

        try {
        
            
            // tratar de obtener la sesion
            try {
                userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            } catch (Exception e) {
                log.warn("fcmToken sin user");
            }

            InfoAuditoria infoAuditoria = new InfoAuditoria(true);
            if(userSession!=null){
                infoAuditoria=userSession.getInfoAuditoria();
            }
            
            /** TRANSFORMACION BASICA */
            //transformacionBasica(userSession.getInfoAuditoria(), fcmToken);
            /** VERIFICACION BASICA */
            // jeResponse.addErrorValidacion(fcmTokenDao.verificacionBasica(infoAuditoria, fcmToken));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {
                fcmToken.setActivo(true);
                fcmToken.setCuenta(infoAuditoria.getCuenta());
                fcmToken.setInformacion(JeBoot.getHttpRequestInfo(request));;
                fcmToken.setUltimaActualizacion(new Date());;

                jeResponse.putResultado("idFcmToken", fcmTokenDao.agregar(infoAuditoria, fcmToken).getIdFcmToken());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

    // KGC-NOREPLACE-OTROS-FIN
    
     
    @PostMapping(value = { BASE_API + "/listar",  BASE_API + "/listar/c/{cuentaCore}"})
    public Map<String, Object> listar(
            HttpServletRequest request,
            @PathVariable(required = false) String cuentaCore,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 47 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Fcm Token","Error grave al listar Fcm Token");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            // KGC-NOREPLACE-PRE-LISTAR-INI
            // JeBoot.verificarCuentaNula(cuentaCore, true);
            // userSession.esMismaCuenta(cuentaCore, true);
            // KGC-NOREPLACE-PRE-LISTAR-FIN
            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(fcmTokenDao.listarAtributosPorPagina(new FcmToken(cuentaCore), paginacionAux.getAtributos(), paginacionAux));
            }
            // KGC-NOREPLACE-POS-LISTAR-INI
            // KGC-NOREPLACE-POS-LISTAR-FIN

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }



    
    @PostMapping( BASE_API + "/agregar" )
    public Map<String, Object> agregar(
                HttpServletRequest request,
				@RequestBody FcmToken fcmToken
            ) {

        Integer[] idRecursoPermisoArray = { 48 };
        JeResponse jeResponse=new JeResponse("Fcm Token creado correctamente","Error grave al crear Fcm Token");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), fcmToken);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(fcmTokenDao.verificacionBasica(userSession.getInfoAuditoria(), fcmToken));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {
                fcmTokenDao.agregar(userSession.getInfoAuditoria(), fcmToken);
                jeResponse.putResultado("idFcmToken", fcmToken.getIdFcmToken());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

    
        
    @PostMapping( BASE_API + "/modificar" )
    public Map<String, Object> modificar(
                HttpServletRequest request,
				@RequestBody FcmToken fcmToken
            ) {

        Integer[] idRecursoPermisoArray = { 49 };
        JeResponse jeResponse=new JeResponse("Fcm Token modificado correctamente","Error grave al modificar Fcm Token");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), fcmToken);
            /** VERIFICACION BASICA */
            if(fcmToken.getIdFcmToken()==null) jeResponse.addErrorValidacion("Fcm Token sin id para modificar");
            jeResponse.addErrorValidacion(fcmTokenDao.verificacionBasica(userSession.getInfoAuditoria(), fcmToken));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {
                fcmTokenDao.modificar(userSession.getInfoAuditoria(), fcmToken);
                jeResponse.putResultado("idFcmToken", fcmToken.getIdFcmToken());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

    
       
    @PostMapping( BASE_API + "/eliminar" )
    public Map<String, Object> eliminar(
                HttpServletRequest request,
                @RequestBody FcmToken fcmToken
            ) {

        Integer[] idRecursoPermisoArray = { 50 };
        JeResponse jeResponse=new JeResponse("Fcm Token eliminado correctamente","Error grave al elimimar Fcm Token");
        UsuarioSesionInterno userSession = null;

        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                fcmTokenDao.eliminarPorId(userSession.getInfoAuditoria(), fcmToken.getIdFcmToken());
                jeResponse.putResultado("idFcmToken", fcmToken.getIdFcmToken());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

   
    
    

    private void transformacionBasica(InfoAuditoria infoAuditoria, FcmToken fcmToken) throws JsonMappingException, JsonProcessingException {
        boolean modificar = fcmToken.getIdFcmToken() != null;

		fcmToken.setUltimaActualizacion(JeBoot.getFechaHHMMSS(fcmToken.getUltimaActualizacionMask()));

        transformacionAdicional(infoAuditoria, fcmToken);

    }


}
