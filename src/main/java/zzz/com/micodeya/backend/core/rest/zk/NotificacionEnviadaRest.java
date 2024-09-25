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
import zzz.com.micodeya.backend.core.dao.zk.NotificacionEnviadaDao;
import zzz.com.micodeya.backend.core.dto.PuntoGeoDto;
import zzz.com.micodeya.backend.core.entities.zk.NotificacionEnviada;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;

@Slf4j
@RestController
public class NotificacionEnviadaRest {

    private final String BASE_API = "/api/zk/notificacionEnviada";

    @Autowired
    private NotificacionEnviadaDao notificacionEnviadaDao;
    
    
    // KGC-NOREPLACE-OTROS-INI

    private void transformacionAdicional(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) {
        boolean modificar = notificacionEnviada.getIdNotificacionEnviada() != null;

        notificacionEnviada.setFechaHoraEnviado(new Date());
        

    }


    @PostMapping(value = { BASE_API + "/listarCuenta/{cuentaCore}"})
    public Map<String, Object> listarPorCuenta(
            HttpServletRequest request,
            @PathVariable(required = false) String cuentaCore,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 96 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Notificación Enviada","Error grave al listar Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            // KGC-NOREPLACE-PRE-LISTAR-INI
            JeBoot.verificarCuentaNula(cuentaCore, true);
            userSession.esMismaCuenta(cuentaCore, true);
            // KGC-NOREPLACE-PRE-LISTAR-FIN
            if (jeResponse.sinErrorValidacion()) {
                NotificacionEnviada ejemplo = new NotificacionEnviada();
                ejemplo.setCuenta(cuentaCore);
                jeResponse.putResultadoListar(notificacionEnviadaDao.listarAtributosPorPagina(
                    ejemplo, paginacionAux.getAtributos(), paginacionAux));
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


    @PostMapping( BASE_API + "/marcarComoVisto/{idNotificacionEnviada}" )
    public Map<String, Object> marcarComoVisto(
                HttpServletRequest request,
                @PathVariable(required = true) Integer idNotificacionEnviada
            ) {

        Integer[] idRecursoPermisoArray = { 98 };
        JeResponse jeResponse=new JeResponse("Notificación Enviada modificada correctamente","Error grave al modificar Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            // transformacionBasica(userSession.getInfoAuditoria(), notificacionEnviada);
            /** VERIFICACION BASICA */
            // if(notificacionEnviada.getIdNotificacionEnviada()==null) jeResponse.addErrorValidacion("Notificación Enviada sin id para modificar");
            // jeResponse.addErrorValidacion(notificacionEnviadaDao.verificacionBasica(userSession.getInfoAuditoria(), notificacionEnviada));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {
                notificacionEnviadaDao.marcarComoVisto(userSession.getInfoAuditoria(), idNotificacionEnviada);
                jeResponse.putResultado("idNotificacionEnviada", idNotificacionEnviada);
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

        Integer[] idRecursoPermisoArray = { 6, 96 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Notificación Enviada","Error grave al listar Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            // KGC-NOREPLACE-PRE-LISTAR-INI
            // JeBoot.verificarCuentaNula(cuentaCore, true);
            // userSession.esMismaCuenta(cuentaCore, true);
            // KGC-NOREPLACE-PRE-LISTAR-FIN
            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(notificacionEnviadaDao.listarAtributosPorPagina(new NotificacionEnviada(cuentaCore), paginacionAux.getAtributos(), paginacionAux));
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
				@RequestBody NotificacionEnviada notificacionEnviada
            ) {

        Integer[] idRecursoPermisoArray = { 97 };
        JeResponse jeResponse=new JeResponse("Notificación Enviada creada correctamente","Error grave al crear Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), notificacionEnviada);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(notificacionEnviadaDao.verificacionBasica(userSession.getInfoAuditoria(), notificacionEnviada));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {
                notificacionEnviadaDao.agregar(userSession.getInfoAuditoria(), notificacionEnviada);
                jeResponse.putResultado("idNotificacionEnviada", notificacionEnviada.getIdNotificacionEnviada());
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
				@RequestBody NotificacionEnviada notificacionEnviada
            ) {

        Integer[] idRecursoPermisoArray = { 98 };
        JeResponse jeResponse=new JeResponse("Notificación Enviada modificada correctamente","Error grave al modificar Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), notificacionEnviada);
            /** VERIFICACION BASICA */
            if(notificacionEnviada.getIdNotificacionEnviada()==null) jeResponse.addErrorValidacion("Notificación Enviada sin id para modificar");
            jeResponse.addErrorValidacion(notificacionEnviadaDao.verificacionBasica(userSession.getInfoAuditoria(), notificacionEnviada));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {
                notificacionEnviadaDao.modificar(userSession.getInfoAuditoria(), notificacionEnviada);
                jeResponse.putResultado("idNotificacionEnviada", notificacionEnviada.getIdNotificacionEnviada());
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
                @RequestBody NotificacionEnviada notificacionEnviada
            ) {

        Integer[] idRecursoPermisoArray = { 99 };
        JeResponse jeResponse=new JeResponse("Notificación Enviada eliminada correctamente","Error grave al elimimar Notificación Enviada");
        UsuarioSesionInterno userSession = null;

        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                notificacionEnviadaDao.eliminarPorId(userSession.getInfoAuditoria(), notificacionEnviada.getIdNotificacionEnviada());
                jeResponse.putResultado("idNotificacionEnviada", notificacionEnviada.getIdNotificacionEnviada());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

   
    
    

    private void transformacionBasica(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) throws JsonMappingException, JsonProcessingException {
        boolean modificar = notificacionEnviada.getIdNotificacionEnviada() != null;

		notificacionEnviada.setFechaHoraEnviado(JeBoot.getFechaHHMMSS(notificacionEnviada.getFechaHoraEnviadoMask()));
		notificacionEnviada.setFechaHoraVisto(JeBoot.getFechaHHMMSS(notificacionEnviada.getFechaHoraVistoMask()));
		notificacionEnviada.setFechaHoraVencimiento(JeBoot.getFechaHHMMSS(notificacionEnviada.getFechaHoraVencimientoMask()));

        transformacionAdicional(infoAuditoria, notificacionEnviada);

    }


}
