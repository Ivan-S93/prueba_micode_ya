package zzz.com.micodeya.backend.core.rest.zk;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioDao;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.exception.KwfAuthException;
import zzz.com.micodeya.backend.core.exception.MyFileNotFoundException;
import zzz.com.micodeya.backend.core.service.FileStorageService;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.jasper.JRUtils;
import zzz.com.micodeya.backend.core.util.jasper.ReporteAux;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;

@Slf4j
@RestController
public class UsuarioRep {

    private final String BASE_API = "/api/zk/usuario/rep";

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(BASE_API + "/eliminar")
    public Map<String, Object> eliminar(
            HttpServletRequest request,
            @RequestBody Usuario usuario) {

        Integer[] idRecursoPermisoArray = { 14 };
        JeResponse jeResponse = new JeResponse("Usuario eliminado correctamente", "Error grave al elimimar Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            // userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                // usuarioDao.eliminarPorId(userSession.getInfoAuditoria(),
                // usuario.getIdUsuario());

                //JRUtils.prepararReporte("", null, false);

            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            if (!jeResponse.isWarning())
                log.error(jeResponse.getErrorForLog(), e);
        }

        return jeResponse.getRetornoMap();
    }

    @GetMapping(BASE_API + "/test")
    public ResponseEntity<Resource> avatarDownload(HttpServletRequest request) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            // userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                ReporteAux reporte = new ReporteAux();

                reporte.setArchivoJrxml("test/pais_listadoGeneral");
                reporte.setFormato("pdf");
                reporte.setTitulo("Nombre del reporte");
                reporte.setNombreDescarga("País descargado");
                reporte.setDataSource(ReporteAux.DataSourceType.JSON);
                reporte.setJsonDataSource(
                        "[{\"idPais\":1,\"nombre\":\"Paraguay\",\"activo\":\"S\"},{\"idPais\":2,\"nombre\":\"Argentina\",\"activo\":\"S\"}]");

                String filePath = JRUtils.prepararReporte(reporte);

                Resource resource = fileStorageService.recuperarArchivoFullPath(filePath);

                String contentType = JeBoot.getResourceMimeType(request, resource);

                String inlineOrAttachment = filePath.endsWith(".down") ? "attachment" : "inline";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                inlineOrAttachment + "; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .body(resource);

            }

        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            log.error(jeResponse.getErrorForLog(), e);
            if (e instanceof KwfAuthException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else if (e instanceof MyFileNotFoundException) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping(BASE_API + "/img/imagenPortada/{fileName:.+}")
    public ResponseEntity<Resource> imagenPortadaDownload(
            HttpServletRequest request, @PathVariable String fileName) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                Resource resource = fileStorageService.recuperarArchivoUploadResource(BASE_API + "/imagenPortada",
                        fileName);

                String contentType = JeBoot.getResourceMimeType(request, resource);

                String inlineOrAttachment = fileName.endsWith(".down") ? "attachment" : "inline";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                inlineOrAttachment + "; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .body(resource);

            }

        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            log.error(jeResponse.getErrorForLog(), e);
            if (e instanceof KwfAuthException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else if (e instanceof MyFileNotFoundException) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.internalServerError().build();
    }

}
