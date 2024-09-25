package zzz.com.micodeya.backend.core.rest.zk;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;

import zzz.com.micodeya.backend.core.entities.zk.Recurso;
import zzz.com.micodeya.backend.core.dao.zk.RecursoDao;


@Slf4j
@RestController
@RequestMapping("/api/zk/recurso")
public class RecursoRest {

    @Autowired
    private RecursoDao recursoDao;

     
 	@PostMapping(value = {"/listar"})
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 21 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Recurso","Error grave al listar Recurso");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(recursoDao.listarAtributosPorPagina(new Recurso(), paginacionAux.getAtributos(), paginacionAux));
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }


        return jeResponse.getRetornoMap();
    }


    
    @PostMapping("/agregar")
    public Map<String, Object> agregar(
            HttpServletRequest request,
            @RequestBody Recurso recurso
            ) {


        Integer[] idRecursoPermisoArray = { 22 };
        JeResponse jeResponse=new JeResponse("Recurso creado correctamente","Error grave al crear Recurso");
        UsuarioSesionInterno userSession = null;
        


        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(recursoDao.verificacionBasica(userSession.getInfoAuditoria(), recurso));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {

                

                jeResponse.putResultado("idRecurso", recursoDao.agregar(userSession.getInfoAuditoria(), recurso).getIdRecurso());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }


        return jeResponse.getRetornoMap();
    }

    
        
    @PostMapping("/modificar")
    public Map<String, Object> modificar(
                HttpServletRequest request,
                @RequestBody Recurso recurso
            ) {


        Integer[] idRecursoPermisoArray = { 23 };
        JeResponse jeResponse=new JeResponse("Recurso modificado correctamente","Error grave al modificar Recurso");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            if(recurso.getIdRecurso()==null) jeResponse.addErrorValidacion("Recurso sin id para modificar");
            jeResponse.addErrorValidacion(recursoDao.verificacionBasica(userSession.getInfoAuditoria(), recurso));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            if (jeResponse.sinErrorValidacion()) {

                

                jeResponse.putResultado("idRecurso", recursoDao.modificar(userSession.getInfoAuditoria(), recurso).getIdRecurso());
            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())log.error(jeResponse.getErrorForLog(), e);
        }


        return jeResponse.getRetornoMap();
    }

    
       
    @PostMapping("/eliminar")
    public Map<String, Object> eliminar(
                HttpServletRequest request,
                @RequestBody Recurso recurso
            ) {

        Integer[] idRecursoPermisoArray = { 24 };
        JeResponse jeResponse=new JeResponse("Recurso eliminado correctamente","Error grave al elimimar Recurso");
        UsuarioSesionInterno userSession = null;


        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);


            if (jeResponse.sinErrorValidacion()) {

                recursoDao.eliminarPorId(userSession.getInfoAuditoria(), recurso.getIdRecurso());

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
