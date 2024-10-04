package com.micodeya.encomiendassp.backend.rest.gral;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.micodeya.encomiendassp.backend.dao.gral.DepartamentoGeograficoDao;
import com.micodeya.encomiendassp.backend.entities.gral.DepartamentoGeografico;
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
public class DepartamentoGeograficoRest {

    private final String BASE_API = "/api/gral/departamentoGeografico";

    @Autowired
    private DepartamentoGeograficoDao departamentoGeograficoDao;
    
    
    // KGC-NOREPLACE-OTROS-INI

    private void transformacionAdicional(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) {
        boolean modificar = departamentoGeografico.getIdDepartamentoGeografico() != null;
    }

    // KGC-NOREPLACE-OTROS-FIN
    
     
    @PostMapping(value = { BASE_API + "/listar",  BASE_API + "/listar/c/{cuentaCore}"})
    public Map<String, Object> listar(
            HttpServletRequest request,
            @PathVariable(required = false) String cuentaCore,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 96 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Departamento Geografico","Error grave al listar Departamento Geografico");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            // KGC-NOREPLACE-PRE-LISTAR-INI
            // JeBoot.verificarCuentaNula(cuentaCore, true);
            // userSession.esMismaCuenta(cuentaCore, true);
            // KGC-NOREPLACE-PRE-LISTAR-FIN
            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(departamentoGeograficoDao.listarAtributosPorPagina(new DepartamentoGeografico(cuentaCore), paginacionAux.getAtributos(), paginacionAux));
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
				@RequestBody DepartamentoGeografico departamentoGeografico
            ) {

        Integer[] idRecursoPermisoArray = { 97 };
        JeResponse jeResponse=new JeResponse("Departamento Geografico creado correctamente","Error grave al crear Departamento Geografico");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), departamentoGeografico);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(departamentoGeograficoDao.verificacionBasica(userSession.getInfoAuditoria(), departamentoGeografico));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {
                departamentoGeograficoDao.agregar(userSession.getInfoAuditoria(), departamentoGeografico);
                jeResponse.putResultado("idDepartamentoGeografico", departamentoGeografico.getIdDepartamentoGeografico());
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
				@RequestBody DepartamentoGeografico departamentoGeografico
            ) {

        Integer[] idRecursoPermisoArray = { 98 };
        JeResponse jeResponse=new JeResponse("Departamento Geografico modificado correctamente","Error grave al modificar Departamento Geografico");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), departamentoGeografico);
            /** VERIFICACION BASICA */
            if(departamentoGeografico.getIdDepartamentoGeografico()==null) jeResponse.addErrorValidacion("Departamento Geografico sin id para modificar");
            jeResponse.addErrorValidacion(departamentoGeograficoDao.verificacionBasica(userSession.getInfoAuditoria(), departamentoGeografico));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {
                departamentoGeograficoDao.modificar(userSession.getInfoAuditoria(), departamentoGeografico);
                jeResponse.putResultado("idDepartamentoGeografico", departamentoGeografico.getIdDepartamentoGeografico());
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
                @RequestBody DepartamentoGeografico departamentoGeografico
            ) {

        Integer[] idRecursoPermisoArray = { 99 };
        JeResponse jeResponse=new JeResponse("Departamento Geografico eliminado correctamente","Error grave al elimimar Departamento Geografico");
        UsuarioSesionInterno userSession = null;

        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                departamentoGeograficoDao.eliminarPorId(userSession.getInfoAuditoria(), departamentoGeografico.getIdDepartamentoGeografico());
                jeResponse.putResultado("idDepartamentoGeografico", departamentoGeografico.getIdDepartamentoGeografico());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

   
    
    

    private void transformacionBasica(InfoAuditoria infoAuditoria, DepartamentoGeografico departamentoGeografico) throws JsonMappingException, JsonProcessingException {
        boolean modificar = departamentoGeografico.getIdDepartamentoGeografico() != null;



        transformacionAdicional(infoAuditoria, departamentoGeografico);

    }


}
