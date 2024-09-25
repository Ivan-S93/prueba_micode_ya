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


import zzz.com.micodeya.backend.core.entities.zk.EmpresaCore;
import zzz.com.micodeya.backend.core.dao.zk.EmpresaCoreDao;



@Slf4j
@RestController
public class EmpresaCoreRest {

    private final String BASE_API = "/api/zk/empresaCore";

    @Autowired
    private EmpresaCoreDao empresaCoreDao;
    
     
    @PostMapping( BASE_API + "/listar" )
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 53 };
        JeResponse jeResponse=new JeResponse("Listado correcto de EmpresaCore","Error grave al listar EmpresaCore");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(empresaCoreDao.listarAtributosPorPagina(new EmpresaCore(), paginacionAux.getAtributos(), paginacionAux));
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
@RequestBody EmpresaCore empresaCore
            ) {


        Integer[] idRecursoPermisoArray = { 54 };
        JeResponse jeResponse=new JeResponse("EmpresaCore creada correctamente","Error grave al crear EmpresaCore");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(empresaCoreDao.verificacionBasica(userSession.getInfoAuditoria(), empresaCore));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            

            if (jeResponse.sinErrorValidacion()) {



                jeResponse.putResultado("idEmpresaCore", empresaCoreDao.agregar(userSession.getInfoAuditoria(), empresaCore).getIdEmpresaCore());
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
@RequestBody EmpresaCore empresaCore
            ) {


        Integer[] idRecursoPermisoArray = { 55 };
        JeResponse jeResponse=new JeResponse("EmpresaCore modificada correctamente","Error grave al modificar EmpresaCore");
        UsuarioSesionInterno userSession = null;

        try {
        
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            
            /** VERIFICACION BASICA */
            if(empresaCore.getIdEmpresaCore()==null) jeResponse.addErrorValidacion("EmpresaCore sin id para modificar");
            jeResponse.addErrorValidacion(empresaCoreDao.verificacionBasica(userSession.getInfoAuditoria(), empresaCore));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }
            
            
            if (jeResponse.sinErrorValidacion()) {



                jeResponse.putResultado("idEmpresaCore", empresaCoreDao.modificar(userSession.getInfoAuditoria(), empresaCore).getIdEmpresaCore());
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
                @RequestBody EmpresaCore empresaCore
            ) {

        Integer[] idRecursoPermisoArray = { 56 };
        JeResponse jeResponse=new JeResponse("EmpresaCore eliminada correctamente","Error grave al elimimar EmpresaCore");
        UsuarioSesionInterno userSession = null;


        try {
            
            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);


            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idEmpresaCore", empresaCoreDao.eliminarPorId(userSession.getInfoAuditoria(), empresaCore.getIdEmpresaCore()).getIdEmpresaCore());

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
