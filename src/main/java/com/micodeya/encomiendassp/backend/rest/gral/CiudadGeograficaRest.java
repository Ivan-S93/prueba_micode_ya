package com.micodeya.encomiendassp.backend.rest.gral;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.micodeya.encomiendassp.backend.dao.gral.CiudadGeograficaDao;
import com.micodeya.encomiendassp.backend.entities.gral.CiudadGeografica;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dto.PuntoGeoDto;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;

@Slf4j
@RestController
public class CiudadGeograficaRest {

    private final String BASE_API = "/api/gral/ciudadGeografica";

    @Autowired
    private CiudadGeograficaDao ciudadGeograficaDao;
    
    
    // KGC-NOREPLACE-OTROS-INI

    private void transformacionAdicional(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) {
        boolean modificar = ciudadGeografica.getIdCiudadGeografica() != null;
    }

    // KGC-NOREPLACE-OTROS-FIN
    
     
    @PostMapping(value = { BASE_API + "/listar",  BASE_API + "/listar/c/{cuentaCore}"})
    public Map<String, Object> listar(
            HttpServletRequest request,
            @PathVariable(required = false) String cuentaCore,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 96 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Ciudad Geográfica","Error grave al listar Ciudad Geográfica");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            // KGC-NOREPLACE-PRE-LISTAR-INI
            // JeBoot.verificarCuentaNula(cuentaCore, true);
            // userSession.esMismaCuenta(cuentaCore, true);
            // KGC-NOREPLACE-PRE-LISTAR-FIN
            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(ciudadGeograficaDao.listarAtributosPorPagina(new CiudadGeografica(cuentaCore), paginacionAux.getAtributos(), paginacionAux));
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
				@RequestBody CiudadGeografica ciudadGeografica
            ) {

        Integer[] idRecursoPermisoArray = { 97 };
        JeResponse jeResponse=new JeResponse("Ciudad Geográfica creado correctamente","Error grave al crear Ciudad Geográfica");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), ciudadGeografica);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(ciudadGeograficaDao.verificacionBasica(userSession.getInfoAuditoria(), ciudadGeografica));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {
                ciudadGeograficaDao.agregar(userSession.getInfoAuditoria(), ciudadGeografica);
                jeResponse.putResultado("idCiudadGeografica", ciudadGeografica.getIdCiudadGeografica());
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
				@RequestBody CiudadGeografica ciudadGeografica
            ) {

        Integer[] idRecursoPermisoArray = { 98 };
        JeResponse jeResponse=new JeResponse("Ciudad Geográfica modificado correctamente","Error grave al modificar Ciudad Geográfica");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), ciudadGeografica);
            /** VERIFICACION BASICA */
            if(ciudadGeografica.getIdCiudadGeografica()==null) jeResponse.addErrorValidacion("Ciudad Geográfica sin id para modificar");
            jeResponse.addErrorValidacion(ciudadGeograficaDao.verificacionBasica(userSession.getInfoAuditoria(), ciudadGeografica));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {
                ciudadGeograficaDao.modificar(userSession.getInfoAuditoria(), ciudadGeografica);
                jeResponse.putResultado("idCiudadGeografica", ciudadGeografica.getIdCiudadGeografica());
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
                @RequestBody CiudadGeografica ciudadGeografica
            ) {

        Integer[] idRecursoPermisoArray = { 99 };
        JeResponse jeResponse=new JeResponse("Ciudad Geográfica eliminado correctamente","Error grave al elimimar Ciudad Geográfica");
        UsuarioSesionInterno userSession = null;

        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                ciudadGeograficaDao.eliminarPorId(userSession.getInfoAuditoria(), ciudadGeografica.getIdCiudadGeografica());
                jeResponse.putResultado("idCiudadGeografica", ciudadGeografica.getIdCiudadGeografica());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

   
    
    

    private void transformacionBasica(InfoAuditoria infoAuditoria, CiudadGeografica ciudadGeografica) throws JsonMappingException, JsonProcessingException {
        boolean modificar = ciudadGeografica.getIdCiudadGeografica() != null;



        transformacionAdicional(infoAuditoria, ciudadGeografica);

    }


}
