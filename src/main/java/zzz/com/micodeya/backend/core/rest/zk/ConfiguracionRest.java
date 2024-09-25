package zzz.com.micodeya.backend.core.rest.zk;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.dto.PuntoGeoDto;


import zzz.com.micodeya.backend.core.entities.zk.Configuracion;
import zzz.com.micodeya.backend.core.dao.zk.ConfiguracionDao;



@Slf4j
@RestController
public class ConfiguracionRest {

    private final String BASE_API = "/api/zk/configuracion";

    @Autowired
    private ConfiguracionDao configuracionDao;
    
     
    @PostMapping( BASE_API + "/listar" )
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 40 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Configuración","Error grave al listar Configuración");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(configuracionDao.listarAtributosPorPagina(new Configuracion(), paginacionAux.getAtributos(), paginacionAux));
            }

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
@RequestBody Configuracion configuracion
            ) {


        Integer[] idRecursoPermisoArray = { 41 };
        JeResponse jeResponse=new JeResponse("Configuración creada correctamente","Error grave al crear Configuración");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(configuracionDao.verificacionBasica(userSession.getInfoAuditoria(), configuracion));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {

				configuracion.setDatoFechaHora(JeBoot.getFechaHHMMSS(configuracion.getDatoFechaHoraMask()));

                jeResponse.putResultado("idConfiguracion", configuracionDao.agregar(userSession.getInfoAuditoria(), configuracion).getIdConfiguracion());
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
@RequestBody Configuracion configuracion
            ) {


        Integer[] idRecursoPermisoArray = { 42 };
        JeResponse jeResponse=new JeResponse("Configuración modificada correctamente","Error grave al modificar Configuración");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            if(configuracion.getIdConfiguracion()==null) jeResponse.addErrorValidacion("Configuración sin id para modificar");
            jeResponse.addErrorValidacion(configuracionDao.verificacionBasica(userSession.getInfoAuditoria(), configuracion));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {

				configuracion.setDatoFechaHora(JeBoot.getFechaHHMMSS(configuracion.getDatoFechaHoraMask()));

                jeResponse.putResultado("idConfiguracion", configuracionDao.modificar(userSession.getInfoAuditoria(), configuracion).getIdConfiguracion());
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
                @RequestBody Configuracion configuracion
            ) {

        Integer[] idRecursoPermisoArray = { 43 };
        JeResponse jeResponse=new JeResponse("Configuración eliminada correctamente","Error grave al elimimar Configuración");
        UsuarioSesionInterno userSession = null;


        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);


            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idConfiguracion", configuracionDao.eliminarPorId(userSession.getInfoAuditoria(), configuracion.getIdConfiguracion()).getIdConfiguracion());

            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }


        return jeResponse.getRetornoMap();
    }

   
    
    


}
