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


import zzz.com.micodeya.backend.core.entities.zk.AuditoriaEspecifica;
import zzz.com.micodeya.backend.core.dao.zk.AuditoriaEspecificaDao;



@Slf4j
@RestController
public class AuditoriaEspecificaRest {

    private final String BASE_API = "/api/zk/auditoriaEspecifica";

    @Autowired
    private AuditoriaEspecificaDao auditoriaEspecificaDao;
    
     
    @PostMapping( BASE_API + "/listar" )
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux
            ) {

        Integer[] idRecursoPermisoArray = { 6, 28 };
        JeResponse jeResponse=new JeResponse("Listado correcto de Auditoría Específica","Error grave al listar Auditoría Específica");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(auditoriaEspecificaDao.listarAtributosPorPagina(new AuditoriaEspecifica(), paginacionAux.getAtributos(), paginacionAux));
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
