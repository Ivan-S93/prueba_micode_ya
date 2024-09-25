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

import zzz.com.micodeya.backend.core.entities.zk.RolRecurso;
import zzz.com.micodeya.backend.core.dao.zk.RolRecursoDao;


@Slf4j
@RestController
@RequestMapping("/api/zk/rolRecurso")
public class RolRecursoRest {

    @Autowired
    private RolRecursoDao rolRecursoDao;

     
 	@PostMapping(value = {"/listar"})
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 7 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Rol Recurso","Error grave al listar Rol Recurso");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(rolRecursoDao.listarAtributosPorPagina(new RolRecurso(), paginacionAux.getAtributos(), paginacionAux));
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
            @RequestBody RolRecurso rolRecurso
            ) {


        Integer[] idRecursoPermisoArray = { 7 };
        JeResponse jeResponse=new JeResponse("Rol Recurso cread correctamente","Error grave al crear Rol Recurso");
        UsuarioSesionInterno userSession = null;
        


        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(rolRecursoDao.verificacionBasica(userSession.getInfoAuditoria(), rolRecurso));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {

                

                jeResponse.putResultado("idRolRecurso", rolRecursoDao.agregar(userSession.getInfoAuditoria(), rolRecurso).getIdRolRecurso());
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
                @RequestBody RolRecurso rolRecurso
            ) {


        Integer[] idRecursoPermisoArray = { 7 };
        JeResponse jeResponse=new JeResponse("Rol Recurso modificad correctamente","Error grave al modificar Rol Recurso");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            if(rolRecurso.getIdRolRecurso()==null) jeResponse.addErrorValidacion("Rol Recurso sin id para modificar");
            jeResponse.addErrorValidacion(rolRecursoDao.verificacionBasica(userSession.getInfoAuditoria(), rolRecurso));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            if (jeResponse.sinErrorValidacion()) {

                

                jeResponse.putResultado("idRolRecurso", rolRecursoDao.modificar(userSession.getInfoAuditoria(), rolRecurso).getIdRolRecurso());
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
                @RequestBody RolRecurso rolRecurso
            ) {

        Integer[] idRecursoPermisoArray = { 7 };
        JeResponse jeResponse=new JeResponse("Rol Recurso eliminad correctamente","Error grave al elimimar Rol Recurso");
        UsuarioSesionInterno userSession = null;


        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);


            if (jeResponse.sinErrorValidacion()) {

                rolRecursoDao.eliminarPorId(userSession.getInfoAuditoria(), rolRecurso.getIdRolRecurso());

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
