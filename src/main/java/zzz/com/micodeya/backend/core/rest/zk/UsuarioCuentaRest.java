package zzz.com.micodeya.backend.core.rest.zk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioDao;
import zzz.com.micodeya.backend.core.dto.CodigoOtpDto;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.exception.KwfAuthException;
import zzz.com.micodeya.backend.core.exception.MyFileNotFoundException;
import zzz.com.micodeya.backend.core.service.FileStorageService;
import zzz.com.micodeya.backend.core.service.JeSecurityService;
import zzz.com.micodeya.backend.core.service.mail.JeMailService;
import zzz.com.micodeya.backend.core.service.mail.plantillas.GeneralMailPlantilla;
import zzz.com.micodeya.backend.core.util.ArchivoDatoExtra;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;

@Slf4j
@RestController
public class UsuarioCuentaRest {

    private final String BASE_API = "/api/zk/usuarioCuenta";

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(BASE_API + "/info")
    public Map<String, Object> info(
            HttpServletRequest request) {

                Integer[] idRecursoPermisoArray = { 9 };
                JeResponse jeResponse = new JeResponse("Listado correcto de información de usuario", "Error grave al listar información del Usuario");
                UsuarioSesionInterno userSession = null;
        
                try {
        
                    userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
        
                    
                    if (jeResponse.sinErrorValidacion()) {
        
                        String atributos="idUsuario,cuenta,usuario,alias,nombre,apellido,correoPrincipal,fechaNacimientoMask,avatar,imagenPortada";
        
                        jeResponse.putResultadoListar(
                                usuarioDao.listarAtributosPorPagina(new Usuario(userSession.getIdUsuario()),
                                        atributos, new PaginacionAux("idUsuario asc")));
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

    @PostMapping(BASE_API + "/infoPerfil/{cuenta}")
    public Map<String, Object> infoPerfil(
            HttpServletRequest request,
            @PathVariable(required = true) String cuenta) {

                Integer[] idRecursoPermisoArray = { 9 };
                JeResponse jeResponse = new JeResponse("Listado correcto de información de usuario", "Error grave al listar información del Usuario");
                UsuarioSesionInterno userSession = null;
        
                try {
        
                    userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
        
                    
                    if (jeResponse.sinErrorValidacion()) {
        
                        String atributos="idUsuario,cuenta,usuario,alias,nombre,apellido,correoPrincipal,fechaNacimientoMask,avatar,imagenPortada";

                        Usuario ejemplo = new Usuario();
                        ejemplo.setCuenta(cuenta);
        
                        jeResponse.putResultadoListar(
                                usuarioDao.listarAtributosPorPagina(ejemplo,
                                        atributos, new PaginacionAux("idUsuario asc")));
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

    
    @PostMapping(BASE_API + "/modificar")
    public Map<String, Object> modificar(
            HttpServletRequest request,
            @RequestParam(name = "avatarFiles", required = false) MultipartFile[] avatarFiles,
            @RequestParam(name = "imagenPortadaFiles", required = false) MultipartFile[] imagenPortadaFiles,
            @RequestParam(name = "model", required = true) String model) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Datos de la cuenta modificados exitosamente", "Error grave al modificar datos de la cuenta");
        UsuarioSesionInterno userSession = null;

        try {

            // System.out.println("\n\n\n\n\n\n\n######################");
            // System.out.println("ENTRO ACAAAAAAAAAAAAAAAAA");

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            Usuario usuario = new ObjectMapper().readValue(model, Usuario.class);
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), usuario);

            /** VERIFICACION BASICA */
            if (usuario.getIdUsuario() == null)
                jeResponse.addErrorValidacion("Usuario sin id para modificar");
            //jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(userSession.getInfoAuditoria(), usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }

            if (jeResponse.sinErrorValidacion()) {
                List<ArchivoDatoExtra> adjuntoDatoExtraList = fileStorageService.guardarArchivoUploadResourceMultipart(
                        avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API + "/avatar");

                //Guardar con nombre fijo
                if(avatarFiles!=null && avatarFiles.length==1){
                    //Eliminar archivos previos
                    String originalFileName = avatarFiles[0].getOriginalFilename();
                    if(originalFileName!=null && originalFileName.equals("ya-estoy-en-el-server.png")){

                    }else{
                        //eliminar crear imagen por usuario solo si cambio el avatar
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", userSession.getUsuario()+".jpg");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", userSession.getUsuario()+".png");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", userSession.getUsuario()+".jpeg");

                        
                        List<ArchivoDatoExtra> adjuntoDatoExtraNombreEspecificoList = fileStorageService.guardarArchivoUploadResourceMultipart(
                            avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API + "/avatar", userSession.getUsuario());
                    }

                    
                }
                        

            }

            if (jeResponse.sinErrorValidacion()) {
                List<ArchivoDatoExtra> adjuntoDatoExtraList = fileStorageService.guardarArchivoUploadResourceMultipart(
                        imagenPortadaFiles, usuario.getImagenPortada(), userSession.getInfoAuditoria(),
                        BASE_API + "/imagenPortada");
            }

            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idUsuario",
                        usuarioDao.modificarDatosCuenta(userSession.getInfoAuditoria(), usuario).getIdUsuario());
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



    @PostMapping(BASE_API + "/agregarAvatarDirecto")
    public Map<String, Object> agregarAvatarDirecto(
            HttpServletRequest request,
            @RequestParam(name = "avatarFiles", required = false) MultipartFile[] avatarFiles,
            @RequestParam(name = "model", required = true) String model) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Avatar agregado exitosamente", "Error grave al agregar avatar de la cuenta");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            Usuario usuario = new ObjectMapper().readValue(model, Usuario.class);
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), usuario);

            /** VERIFICACION BASICA */
            // if (usuario.getIdUsuario() == null)
            //     jeResponse.addErrorValidacion("Usuario sin id para modificar");
            //jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(userSession.getInfoAuditoria(), usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {
                if(usuarioDao.verificarTieneAvatar(usuario.getUsuario())){
                    jeResponse.addErrorValidacion("Usuario ya tiene avatar");
                }
            }

            if (jeResponse.sinErrorValidacion()) {
                
                //String usuarioCuenta=userSession.getUsuario();
                String usuarioCuenta=usuario.getUsuario();

                List<ArchivoDatoExtra> adjuntoDatoExtraList = fileStorageService.guardarArchivoUploadResourceMultipart(
                        avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API + "/avatar");

                //Guardar con nombre fijo
                if(avatarFiles!=null && avatarFiles.length==1){
                    //Eliminar archivos previos
                    String originalFileName = avatarFiles[0].getOriginalFilename();
                    if(originalFileName!=null && originalFileName.equals("ya-estoy-en-el-server.png")){

                    }else{
                        //eliminar crear imagen por usuario solo si cambio el avatar
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", usuarioCuenta+".jpg");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", usuarioCuenta+".png");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API + "/avatar", usuarioCuenta+".jpeg");

                        
                        List<ArchivoDatoExtra> adjuntoDatoExtraNombreEspecificoList = fileStorageService.guardarArchivoUploadResourceMultipart(
                            avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API + "/avatar", usuarioCuenta);
                    }

                    
                }
                        

            }

           
            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idUsuario",
                        usuarioDao.modificarAvatarCuenta(userSession.getInfoAuditoria(), usuario).getIdUsuario());
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


    @GetMapping(BASE_API + "/img/avatar/{fileName:.+}")
    public ResponseEntity<Resource> avatarDownload(
            HttpServletRequest request, @PathVariable String fileName) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                Resource resource = fileStorageService.recuperarArchivoUploadResource(BASE_API + "/avatar", fileName);

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


    @GetMapping(BASE_API + "/img/avatarCuenta/{fileName}")
    public ResponseEntity<Resource> avatarCuenta(
            HttpServletRequest request, @PathVariable String fileName) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                //Verificar cual existe del usuario
                String nombreArchivo= fileName;

                String[] extensionesPosiblesArray={".jpg",".jpeg", ".png"};

                for (int i = 0; i < extensionesPosiblesArray.length; i++) {
                    nombreArchivo = fileName + extensionesPosiblesArray[i];
                    if (fileStorageService.existeArchivoUploadResource(BASE_API + "/avatar", nombreArchivo)) {
                        break;
                    }
                }


                Resource resource = fileStorageService.recuperarArchivoUploadResource(BASE_API + "/avatar", nombreArchivo);

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

    private void transformacionBasica(InfoAuditoria infoAuditoria, Usuario usuario) {
        boolean modificar = usuario.getIdUsuario() != null;

        usuario.setFechaNacimiento(JeBoot.getFecha(usuario.getFechaNacimientoMask()));
        usuario.setUsuario(JeBoot.normalizarAlias(usuario.getUsuario()));

        // si el alias no es bueno, colocar el usuario
        if(usuario.getAlias()==null || usuario.getAlias().trim().length()<5){
            usuario.setAlias(usuario.getUsuario());
        }

        //no hace falta
        //transformacionAdicional(infoAuditoria, usuario);

    }

    // KGC-AUTO-OTROS: lo que está debajo no se reemplazará al regenerar

    @Autowired
    JeSecurityService securityService;

    @Autowired
    JeMailService mailService;

    @Autowired
    GeneralMailPlantilla mailPlantilla;

    private static Map<String, CodigoOtpDto> OTP_EMAIL_AUTORREGISTR0_MAP = new HashMap<String, CodigoOtpDto>();
    private static Map<String, CodigoOtpDto> OTP_EMAIL_RECUPERAR_PASSWORD_MAP = new HashMap<String, CodigoOtpDto>();

    private void transformacionAdicional(InfoAuditoria infoAuditoria, Usuario usuario) {
        boolean modificar = usuario.getIdUsuario() != null;

        usuario.setUsuario(usuario.getUsuario().toLowerCase());

        if (modificar && usuario.getContrasenha() != null) {
            securityService.verificarPassword(usuario.getContrasenha());
            usuario.setContrasenha(securityService.encriptar(usuario.getContrasenha(), usuario.getUsuario()));
        } else {
            securityService.verificarPassword(usuario.getContrasenha());
            usuario.setContrasenha(securityService.encriptar(usuario.getContrasenha(), usuario.getUsuario()));

        }

    }

  

    @PostMapping(BASE_API + "/modificarPassword")
    public Map<String, Object> modificarPassword(
            HttpServletRequest request,
            @RequestBody ModificarPasswordDto modificarPasswordDto) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Contraseña modificada correctamente",
                "Error grave al modificar Contraseña");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            /** TRANSFORMACION BASICA */

            /** VERIFICACION BASICA */

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {
                if (!modificarPasswordDto.getPasswordNuevo().equals(modificarPasswordDto.getPasswordNuevoRepetir())) {
                    jeResponse.addErrorValidacion("Contraseña nueva no son iguales");
                }
            }

            String newPassword = null;

            if (jeResponse.sinErrorValidacion()) {
                securityService.verificarPassword(modificarPasswordDto.getPasswordNuevo());

                newPassword = securityService.encriptar(modificarPasswordDto.getPasswordNuevo(),
                        userSession.getUsuario().toLowerCase());
            }

            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idUsuario",
                        usuarioDao.modificarPassword(userSession.getInfoAuditoria(), userSession.getIdUsuario(),
                                modificarPasswordDto.getPassword(),
                                newPassword).getIdUsuario());
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

    @PostMapping(BASE_API + "/suspenderCuenta")
    public Map<String, Object> suspenderCuenta(
            HttpServletRequest request,
            @RequestBody SuspenderCuentaDto suspenderCuentaDto) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Cuenta de usuario suspendido exitosamente",
                "Error grave al suspender cuenta");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            // InfoAuditoria infoAuditoria = new InfoAuditoria(true);

            /** TRANSFORMACION BASICA */

            /** VERIFICACION BASICA */

            /** OTRAS VERIFICACIONES */

            if (jeResponse.sinErrorValidacion()) {

            }

            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idUsuario",
                        usuarioDao.suspenderCuenta(userSession.getInfoAuditoria(), userSession.getIdUsuario(),
                                suspenderCuentaDto.getPassword()).getIdUsuario());

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

}

@Data
class ModificarPasswordDto {
    @NotEmpty(message = "Contraseña Actual: no debe estar vacio")
    private String password;

    @NotEmpty(message = "Contraseña Nueva: no debe estar vacio")
    private String passwordNuevo;

    @NotEmpty(message = "Contraseña Nueva Repetir: no debe estar vacio")
    private String passwordNuevoRepetir;
}

@Data
class SuspenderCuentaDto {
    @NotEmpty(message = "Contraseña Actual: no debe estar vacio")
    private String password;

}