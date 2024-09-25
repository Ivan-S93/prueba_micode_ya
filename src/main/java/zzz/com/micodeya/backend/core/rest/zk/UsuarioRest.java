package zzz.com.micodeya.backend.core.rest.zk;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioDao;
import zzz.com.micodeya.backend.core.dto.CodigoOtpDto;
import zzz.com.micodeya.backend.core.dto.MensajeMailDto;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.entities.zk.UsuarioRol;
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
public class UsuarioRest {

    private final String BASE_API = "/api/zk/usuario";
    private final String BASE_API_USUARIO_CUENTA = "/api/zk/usuarioCuenta";

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(BASE_API + "/listar")
    public Map<String, Object> listar(
            HttpServletRequest request,
            @RequestBody(required = false) PaginacionAux paginacionAux) {

        Integer[] idRecursoPermisoArray = { 11 };
        JeResponse jeResponse = new JeResponse("Listado correcto de Usuario", "Error grave al listar Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {
                jeResponse.putResultadoListar(
                        usuarioDao.listarAtributosPorPagina(new Usuario(),
                                paginacionAux.getAtributos(), paginacionAux));
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

    @PostMapping(BASE_API + "/agregar")
    public Map<String, Object> agregar(
            HttpServletRequest request,
            @RequestParam(name = "avatarFiles", required = false) MultipartFile[] avatarFiles,
            @RequestParam(name = "imagenPortadaFiles", required = false) MultipartFile[] imagenPortadaFiles,
            @RequestParam(name = "model", required = true) String model) {

        Integer[] idRecursoPermisoArray = { 12 };
        JeResponse jeResponse = new JeResponse("Usuario creado correctamente", "Error grave al crear Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            Usuario usuario = new ObjectMapper().readValue(model, Usuario.class);
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), usuario);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(userSession.getInfoAuditoria(), usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }

            if (jeResponse.sinErrorValidacion()) {
                  //Guardar con nombre fijo
                  if(avatarFiles!=null && avatarFiles.length==1){
                    //Eliminar archivos previos
                    String originalFileName = avatarFiles[0].getOriginalFilename();
                    if(originalFileName!=null && originalFileName.equals("ya-estoy-en-el-server.png")){

                    }else{
                        //eliminar crear imagen por usuario solo si cambio el avatar
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".jpg");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".png");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".jpeg");

                        
                        List<ArchivoDatoExtra> adjuntoDatoExtraNombreEspecificoList = fileStorageService.guardarArchivoUploadResourceMultipart(
                            avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario());
                    }
                    
                }
            }

            if (jeResponse.sinErrorValidacion()) {
                List<ArchivoDatoExtra> adjuntoDatoExtraList = fileStorageService.guardarArchivoUploadResourceMultipart(
                        imagenPortadaFiles, usuario.getImagenPortada(), userSession.getInfoAuditoria(),
                        BASE_API_USUARIO_CUENTA + "/imagenPortada");
            }

            if (jeResponse.sinErrorValidacion()) {

                usuario.setFechaNacimiento(JeBoot.getFecha(usuario.getFechaNacimientoMask()));

                jeResponse.putResultado("idUsuario",
                        usuarioDao.agregar(userSession.getInfoAuditoria(), usuario).getIdUsuario());
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

    @PostMapping(BASE_API + "/agregarDirecto")
    public Map<String, Object> agregarDirecto(
            HttpServletRequest request,
            @RequestBody Usuario usuario) {

        Integer[] idRecursoPermisoArray = { 12 };
        JeResponse jeResponse = new JeResponse("Usuario creado correctamente", "Error grave al crear Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            InfoAuditoria infoAuditoriaDefautl = userSession.getInfoAuditoria();
            // InfoAuditoria infoAuditoriaDefautl=new InfoAuditoria(true);

            // Usuario usuario = new ObjectMapper().readValue(model, Usuario.class);
            /** TRANSFORMACION BASICA */
            transformacionBasica(infoAuditoriaDefautl, usuario);
            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(infoAuditoriaDefautl, usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }

            if (jeResponse.sinErrorValidacion()) {

                usuario.setFechaNacimiento(JeBoot.getFecha(usuario.getFechaNacimientoMask()));
                usuario.setUsuarioRolList(getRolBasicoList(infoAuditoriaDefautl));


                jeResponse.putResultado("idUsuario",
                        usuarioDao.agregar(infoAuditoriaDefautl, usuario).getIdUsuario());
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

        Integer[] idRecursoPermisoArray = { 13 };
        JeResponse jeResponse = new JeResponse("Usuario modificado correctamente", "Error grave al modificar Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            Usuario usuario = new ObjectMapper().readValue(model, Usuario.class);
            /** TRANSFORMACION BASICA */
            transformacionBasica(userSession.getInfoAuditoria(), usuario);

            /** VERIFICACION BASICA */
            if (usuario.getIdUsuario() == null)
                jeResponse.addErrorValidacion("Usuario sin id para modificar");
            jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(userSession.getInfoAuditoria(), usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }

            if (jeResponse.sinErrorValidacion()) {
        
                //Guardar con nombre fijo
                if(avatarFiles!=null && avatarFiles.length==1){
                    //Eliminar archivos previos
                    String originalFileName = avatarFiles[0].getOriginalFilename();
                    if(originalFileName!=null && originalFileName.equals("ya-estoy-en-el-server.png")){

                    }else{
                        //eliminar crear imagen por usuario solo si cambio el avatar
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".jpg");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".png");
                        fileStorageService.eliminarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario()+".jpeg");

                        
                        List<ArchivoDatoExtra> adjuntoDatoExtraNombreEspecificoList = fileStorageService.guardarArchivoUploadResourceMultipart(
                            avatarFiles, usuario.getAvatar(), userSession.getInfoAuditoria(), BASE_API_USUARIO_CUENTA + "/avatar", userSession.getUsuario());
                    }
                    
                }
                        
            }

            if (jeResponse.sinErrorValidacion()) {
                List<ArchivoDatoExtra> adjuntoDatoExtraList = fileStorageService.guardarArchivoUploadResourceMultipart(
                        imagenPortadaFiles, usuario.getImagenPortada(), userSession.getInfoAuditoria(),
                        BASE_API_USUARIO_CUENTA + "/imagenPortada");
            }

            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("idUsuario",
                        usuarioDao.modificar(userSession.getInfoAuditoria(), usuario).getIdUsuario());
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

    @PostMapping(BASE_API + "/eliminar")
    public Map<String, Object> eliminar(
            HttpServletRequest request,
            @RequestBody Usuario usuario) {

        Integer[] idRecursoPermisoArray = { 14 };
        JeResponse jeResponse = new JeResponse("Usuario eliminado correctamente", "Error grave al elimimar Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                usuarioDao.eliminarPorId(userSession.getInfoAuditoria(), usuario.getIdUsuario());

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
            HttpServletRequest request, @PathVariable String fileName,
            @RequestParam(value = "thumb", required = false) String thumbFile
            ) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                Resource resource = fileStorageService.recuperarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/avatar", fileName, thumbFile);

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
            HttpServletRequest request, @PathVariable String fileName,
            @RequestParam(value = "thumb", required = false) String thumbFile
            ) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Archivo Avatar descargado correctamente",
                "Error grave al descargar archivo Avatar");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            if (jeResponse.sinErrorValidacion()) {

                Resource resource = fileStorageService.recuperarArchivoUploadResource(BASE_API_USUARIO_CUENTA + "/imagenPortada",
                        fileName, thumbFile);

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
        usuario.setAlias(usuario.getUsuario());

        transformacionAdicional(infoAuditoria, usuario);

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

        if (modificar){
            // solo si hay contraseña
            if(usuario.getContrasenha() != null && usuario.getContrasenha().trim().length()>0) {
                securityService.verificarPassword(usuario.getContrasenha());
                usuario.setContrasenha(securityService.encriptar(usuario.getContrasenha(), usuario.getUsuario()));
            }else{
                usuario.setContrasenha(null);
            }
        } else {
            securityService.verificarPassword(usuario.getContrasenha());
            usuario.setContrasenha(securityService.encriptar(usuario.getContrasenha(), usuario.getUsuario()));

        }

    }

    @PostMapping(BASE_API + "/infoCuenta")
    public Map<String, Object> infoCuenta(
            HttpServletRequest request) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Listado correcto de información de usuario", "Error grave al listar información del Usuario");
        UsuarioSesionInterno userSession = null;

        try {

            userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            
            if (jeResponse.sinErrorValidacion()) {

                String atributos="";

                jeResponse.putResultadoListar(
                        usuarioDao.listarAtributosPorPagina(new Usuario(userSession.getIdUsuario()),
                                atributos, null));
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

    @PostMapping("/api/public/encriptarPassword")
    public Map<String, Object> encriptarPassword(
            HttpServletRequest request,
            @RequestBody EncriptarPasswordDto encriptarPasswordDto) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Contraseña modificada correctamente",
                "Error grave al modificar Contraseña");
        UsuarioSesionInterno userSession = null;

        try {

            // userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            /** TRANSFORMACION BASICA */

            /** VERIFICACION BASICA */

            /** OTRAS VERIFICACIONES */

            String newPassword = null;

            if (jeResponse.sinErrorValidacion()) {
                securityService.verificarPassword(encriptarPasswordDto.getPassword());

                newPassword = securityService.encriptar(encriptarPasswordDto.getPassword(),
                        encriptarPasswordDto.getUser());
            }

            if (jeResponse.sinErrorValidacion()) {

                jeResponse.putResultado("pass", newPassword);
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

    @PostMapping("/api/public/registro/verificarExistenciaUsuario")
    public Map<String, Object> verificarExistenciaUsuario(
            HttpServletRequest request,
            @RequestBody ExistenciaUsuarioDto existenciaUsuarioDto) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Usuario disponible",
                "Error grave al verificar existencia de usuario");
        UsuarioSesionInterno userSession = null;

        try {

            // userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

            /** TRANSFORMACION BASICA */

            /** VERIFICACION BASICA */

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {

            }

            Usuario usuario = new Usuario();
            usuario.setUsuario( JeBoot.normalizarAlias(existenciaUsuarioDto.getUser()) );
            usuario.setCuenta( JeBoot.normalizarAlias(existenciaUsuarioDto.getUser()) );
            jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(null, usuario));

            if (jeResponse.sinErrorValidacion()) {
                // jeResponse.putResultado("idUsuario",);
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

    @PostMapping("/api/public/registro/recuperarPasswordPorMail")
    public Map<String, Object> recuperarPassword(
            HttpServletRequest request,
            @RequestBody RecuperarPasswordPorMailDto recuperarPasswordPorMailDto) {

        Integer[] idRecursoPermisoArray = { 9 };
        JeResponse jeResponse = new JeResponse("Contraseña recuperada correctamente",
                "Error grave al recuperar Contraseña");
        UsuarioSesionInterno userSession = null;

        try {

            // userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);
            InfoAuditoria infoAuditoria = new InfoAuditoria(true);

            /** TRANSFORMACION BASICA */

            /** VERIFICACION BASICA */

            /** OTRAS VERIFICACIONES */

            if (jeResponse.sinErrorValidacion()) {
                if (recuperarPasswordPorMailDto.getDatoBuscar().equals("usuario")
                        && Strings.isBlank(recuperarPasswordPorMailDto.getUsuario())) {
                    jeResponse.addErrorValidacion("Usuario está vacio");
                }
            }

            if (jeResponse.sinErrorValidacion()) {
                if (recuperarPasswordPorMailDto.getDatoBuscar().equals("correo")
                        && Strings.isBlank(recuperarPasswordPorMailDto.getCorreo())) {
                    jeResponse.addErrorValidacion("Correo está vacio");
                }
            }

            if (jeResponse.sinErrorValidacion()) {
                if (Strings.isBlank(recuperarPasswordPorMailDto.getUsuario())
                        && Strings.isBlank(recuperarPasswordPorMailDto.getCorreo())) {
                    jeResponse.addErrorValidacion("Usuario o correo vacio");
                }
            }
            // buscar por usuario o correo

            if (jeResponse.sinErrorValidacion()) {
                Usuario usuarioEjemplo = new Usuario();
                usuarioEjemplo.setUsuario(
                        recuperarPasswordPorMailDto.getUsuario() != null
                                ? recuperarPasswordPorMailDto.getUsuario().toLowerCase().trim()
                                : null);
                usuarioEjemplo.setCorreoPrincipal(
                        recuperarPasswordPorMailDto.getCorreo() != null
                                ? recuperarPasswordPorMailDto.getCorreo().toLowerCase().trim()
                                : null);

                PaginacionAux result = usuarioDao.listarAtributosPorPagina(usuarioEjemplo,
                        "idUsuario,usuario,correoPrincipal,estado",
                        new PaginacionAux("usuario asc"));

                if (result.getList().size() == 0) {
                    if (recuperarPasswordPorMailDto.getDatoBuscar().equals("usuario")) {
                        jeResponse.addErrorValidacion(
                                "Usuario '" + recuperarPasswordPorMailDto.getUsuario() + "' no encontrado");
                    }
                    if (recuperarPasswordPorMailDto.getDatoBuscar().equals("correo")) {
                        jeResponse.addErrorValidacion(
                                "Correo '" + recuperarPasswordPorMailDto.getUsuario() + "' no encontrado");
                    }

                } else if (result.getList().size() > 1) {
                    jeResponse.addErrorValidacion(
                            "Se encontró más de un Usuario '" + recuperarPasswordPorMailDto.getUsuario() + "'");
                    if (recuperarPasswordPorMailDto.getDatoBuscar().equals("usuario")) {
                        jeResponse.addErrorValidacion(
                                "Se encontró más de un Usuario '" + recuperarPasswordPorMailDto.getUsuario() + "'");
                    }
                    if (recuperarPasswordPorMailDto.getDatoBuscar().equals("correo")) {
                        jeResponse.addErrorValidacion(
                                "Se encontró más de un Correo '" + recuperarPasswordPorMailDto.getUsuario() + "'");
                    }
                } else {

                    if(!result.getList().get(0).get("estado").toString().equals("ACT")){
                        jeResponse.addErrorValidacion("Usuario inactivo");
                    }

                    recuperarPasswordPorMailDto
                            .setIdUsuario(Integer.parseInt(result.getList().get(0).get("idUsuario").toString()));
                    recuperarPasswordPorMailDto.setUsuario(result.getList().get(0).get("usuario").toString());
                    recuperarPasswordPorMailDto.setCorreo(result.getList().get(0).get("correoPrincipal").toString());
                }

            }

            // enviar mail con OTP
            String keyMapOtp = recuperarPasswordPorMailDto.getUsuario() + "-" + recuperarPasswordPorMailDto.getCorreo();
            if (jeResponse.sinErrorValidacion() && recuperarPasswordPorMailDto.getSolicitudOtpEmail()) {

                String valorCodigo = String.valueOf((new Random().nextInt(9999 - 1000) + 1000));
                // System.out.println("valorCodigo: "+ valorCodigo);
                OTP_EMAIL_RECUPERAR_PASSWORD_MAP.put(keyMapOtp, new CodigoOtpDto(valorCodigo));

                MensajeMailDto mensaje = mailPlantilla.mensajeRecuperarPasswordPorEmailOtp(valorCodigo);
                mensaje.setPara(recuperarPasswordPorMailDto.getCorreo());

                mailService.enviarMail(mensaje);

                jeResponse.replaceMensajeExito("Correo enviado con un código único.");
                jeResponse.putResultado("correoEncontrado", JeBoot.ocultarCorreo(recuperarPasswordPorMailDto.getCorreo()));
                jeResponse.putResultado("codigoOtpEnviado", true);
            }

            // verificar codigo y continuar
            if (jeResponse.sinErrorValidacion() && !recuperarPasswordPorMailDto.getSolicitudOtpEmail()) {

                String valorCodigo = recuperarPasswordPorMailDto.getCodigoOtpEmail();

                if (!OTP_EMAIL_RECUPERAR_PASSWORD_MAP.containsKey(keyMapOtp)) {
                    jeResponse.addErrorValidacion("Código no encontrado");
                }

                if (jeResponse.sinErrorValidacion()) {
                    if (StringUtils.isEmpty(valorCodigo)) {
                        jeResponse.addErrorValidacion("Código ingresado está vacío");
                    }
                }
                if (jeResponse.sinErrorValidacion()) {
                    CodigoOtpDto codigoOtpDto = OTP_EMAIL_RECUPERAR_PASSWORD_MAP.get(keyMapOtp);
                    if (!codigoOtpDto.verificarCodigo(valorCodigo)) {
                        jeResponse.addErrorValidacion("Código ingresado incorrecto");
                    }
                }

                if (jeResponse.sinErrorValidacion()) {
                    if (!recuperarPasswordPorMailDto.getPasswordNuevo()
                            .equals(recuperarPasswordPorMailDto.getPasswordNuevoRepetir())) {
                        jeResponse.addErrorValidacion("Contraseña nueva no son iguales");
                    }
                }

                if (jeResponse.sinErrorValidacion()) {
                    securityService.verificarPassword(recuperarPasswordPorMailDto.getPasswordNuevo());
                }
                // guardar en la BD
                if (jeResponse.sinErrorValidacion()) {

                    String newPassword = securityService.encriptar(recuperarPasswordPorMailDto.getPasswordNuevo(),
                            recuperarPasswordPorMailDto.getUsuario().toLowerCase());

                    // usuario.setUsuarioRolList(usuarioRolList);

                    jeResponse.putResultado("idUsuario",
                            usuarioDao.recuperarPassword(infoAuditoria, recuperarPasswordPorMailDto.getIdUsuario(),
                                    newPassword).getIdUsuario());
                    jeResponse.putResultado("codigoOtpEnviado", false);
                }

            }

            if (jeResponse.sinErrorValidacion()) {

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

    @PostMapping("/api/public/registro/porMail")
    public Map<String, Object> autoregistroPorMail(
            HttpServletRequest request,
            @RequestBody Usuario usuario) {

        // Integer[] idRecursoPermisoArray = { 12 };
        JeResponse jeResponse = new JeResponse("Usuario creado correctamente", "Error grave al crear Usuario");
        UsuarioSesionInterno userSession = null;

        try {
            // se crea uno ya que no hay sesion
            InfoAuditoria infoAuditoria = new InfoAuditoria(true);

            /** TRANSFORMACION BASICA */
            transformacionBasica(infoAuditoria, usuario);

            usuario.setTimeOutSesion(30);
            usuario.setEstado("ACT");
            usuario.setConfirmarCorreo("S");
            usuario.setConfirmarTelefono("N");
            usuario.setTipoRegistro("AAP");
            usuario.setAvatar("[]");
            usuario.setImagenPortada("[]");

            /** VERIFICACION BASICA */
            jeResponse.addErrorValidacion(usuarioDao.verificacionBasica(infoAuditoria, usuario));

            /** OTRAS VERIFICACIONES */
            if (jeResponse.sinErrorValidacion()) {
                if (StringUtils.isEmpty(usuario.getCorreoPrincipal())) {
                    jeResponse.addErrorValidacion("Correo no debe estar vacío");
                }
            }

            if (jeResponse.sinErrorValidacion()) {

                String keyMapOtp = usuario.getUsuario() + "-" + usuario.getCorreoPrincipal();

                // enviar codigo
                if (usuario.getSolicitudOtpEmail() != null && usuario.getSolicitudOtpEmail()) {

                    String valorCodigo = String.valueOf((new Random().nextInt(9999 - 1000) + 1000));
                    // System.out.println("valorCodigo: "+ valorCodigo);
                    OTP_EMAIL_AUTORREGISTR0_MAP.put(keyMapOtp, new CodigoOtpDto(valorCodigo));

                    MensajeMailDto mensaje = mailPlantilla.mensajeVerificarEmailAutorregistro(valorCodigo);
                    mensaje.setPara(usuario.getCorreoPrincipal());

                    mailService.enviarMail(mensaje);

                    jeResponse.replaceMensajeExito("Correo enviado con un código único.");
                    jeResponse.putResultado("codigoOtpEnviado", true);

                } else {
                    // verificar codigo y guardar

                    String valorCodigo = usuario.getCodigoOtpEmail();

                    if (!OTP_EMAIL_AUTORREGISTR0_MAP.containsKey(keyMapOtp)) {
                        jeResponse.addErrorValidacion("Código no encontrado");
                    }

                    if (jeResponse.sinErrorValidacion()) {
                        if (StringUtils.isEmpty(valorCodigo)) {
                            jeResponse.addErrorValidacion("Código ingresado está vacío");
                        }
                    }
                    if (jeResponse.sinErrorValidacion()) {
                        CodigoOtpDto codigoOtpDto = OTP_EMAIL_AUTORREGISTR0_MAP.get(keyMapOtp);
                        if (!codigoOtpDto.verificarCodigo(valorCodigo)) {
                            jeResponse.addErrorValidacion("Código ingresado incorrecto");
                        }
                    }

                    // guardar en la BD
                    if (jeResponse.sinErrorValidacion()) {

                        usuario.setUsuarioRolList(getRolBasicoList(infoAuditoria));

                        jeResponse.putResultado("idUsuario",
                                usuarioDao.agregar(infoAuditoria, usuario).getIdUsuario());
                        jeResponse.putResultado("codigoOtpEnviado", false);
                    }

                }
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

    private List<UsuarioRol> getRolBasicoList(InfoAuditoria infoAuditoria){

        
        List<UsuarioRol> usuarioRolList = new LinkedList<UsuarioRol>();

        // Acceso basico Web
        UsuarioRol usuarioRol98 = new UsuarioRol();
        usuarioRol98.setActivo(true);
        usuarioRol98.setRol(new Rol(98));
        usuarioRol98.setCodigoEmpresaCore(infoAuditoria.getEmpresaCore());
        usuarioRolList.add(usuarioRol98);

        // Acceso basico App
        UsuarioRol usuarioRol97 = new UsuarioRol();
        usuarioRol97.setActivo(true);
        usuarioRol97.setRol(new Rol(97));
        usuarioRol97.setCodigoEmpresaCore(infoAuditoria.getEmpresaCore());
        usuarioRolList.add(usuarioRol97);

        return usuarioRolList;
    }

}

@Data
class RecuperarPasswordPorMailDto {
    private Integer idUsuario; // se carga al recuperar
    private String datoBuscar; // usuario o correo
    private String usuario;
    private String correo;
    private String passwordNuevo;
    private String passwordNuevoRepetir;

    private Boolean solicitudOtpEmail;
    private String codigoOtpEmail;
}

@Data
class EncriptarPasswordDto {
    private String user;
    private String password;
}

@Data
class ExistenciaUsuarioDto {
    private String user;
}

