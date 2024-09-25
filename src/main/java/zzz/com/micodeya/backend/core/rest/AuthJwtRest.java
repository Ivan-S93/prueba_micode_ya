package zzz.com.micodeya.backend.core.rest;

import java.util.Date;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.zk.LoginLogDao;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioDao;
import zzz.com.micodeya.backend.core.entities.zk.LoginLog;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.JeResponse;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.InicioSesionDto;
import zzz.com.micodeya.backend.core.util.security.JwtGeneratorInterface;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;




@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthJwtRest {

	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private LoginLogDao loginLogDao;

	@Autowired
	private JwtGeneratorInterface jwtGenerator;

	@PostMapping("/dummy")
    public Map<String, Object> dummy(
            HttpServletRequest request,
            @RequestBody InicioSesionDto datosInicioSesion) {
 
        JeResponse jeResponse = new JeResponse("Acceso correcto", "Error grave al acceder");
 
        try {
 
            if (jeResponse.sinErrorValidacion()) {
 
                
                UsuarioSesionInterno usuarioSesion = obtenerUsuarioAcaNomas(request, datosInicioSesion);
                usuarioSesion.setIat(new Date().getTime());
 
                jeResponse.putResultado("token", jwtGenerator.generateToken(usuarioSesion.getUsuarioJwt()));
                jeResponse.putResultado("user", usuarioSesion.getUsuarioSesionExterno());
 
            }
 
            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            log.error(jeResponse.getErrorForLog(), e);
        }
 
        return jeResponse.getRetornoMap();
    }

	@PostMapping("/login")
	public Map<String, Object> login(
			HttpServletRequest request,
			@RequestBody InicioSesionDto datosInicioSesion) {

		JeResponse jeResponse = new JeResponse("Acceso correcto", "Error grave al acceder");

		LoginLog loginLog = null;
		try {

			if (jeResponse.sinErrorValidacion()) {
				//(property) OS: "ios" | "android" | "windows" | "macos" | "web"
				//RENEWIOS RENEWDROID
				String sistemaOperativoMovil = request.getHeader("xrn-so") == null ? "WEB"
				: request.getHeader("xrn-so").toString();

				sistemaOperativoMovil = sistemaOperativoMovil.equalsIgnoreCase("android") ? "APPDROID"
					: sistemaOperativoMovil.equalsIgnoreCase("ios") ? "APPIOS" : "WEB";

				loginLog = new LoginLog();

				loginLog.setUsuario(datosInicioSesion.getUser());
				loginLog.setTipo(sistemaOperativoMovil);
				loginLog.setRequestInfo(JeBoot.getHttpRequestInfo(request));
				loginLog.setExitoso(false);
				loginLog.setIp(request.getRemoteAddr());
				loginLog.setMotivo(null);
				loginLog.setActivo(true);
				
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLogDao.agregar(infoAuditoria, loginLog);

				UsuarioSesionInterno usuarioSesion = usuarioDao.obtenerUsuarioSesion(request, datosInicioSesion);
				// UsuarioSesionInterno usuarioSesion = obtenerUsuarioAcaNomas(request,
				// datosInicioSesion);
				usuarioSesion.setIat(new Date().getTime());

				jeResponse.putResultado("token", jwtGenerator.generateToken(usuarioSesion.getUsuarioJwt()));
				jeResponse.putResultado("user", usuarioSesion.getUsuarioSesionExterno());

				//Actualiar loginLog;
				loginLog.setExitoso(true);
				loginLogDao.modificar(infoAuditoria, loginLog);


			}

			/* AUTOGENERADO _KGC_ */
			jeResponse.prepararRetornoMap();
		} catch (Exception e) {
			jeResponse.prepararRetornoErrorMap(e);
			log.error(jeResponse.getErrorForLog(), e);

			// Guardar resultado error
			try {
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLog.setMotivo(jeResponse.getErrorValList().toString());
				loginLogDao.modificar(infoAuditoria, loginLog);
			} catch (Exception e2) {
				log.error("ERROR AL ACTUALIZAR LOGIN LOG",e2);
			}
		}

		return jeResponse.getRetornoMap();
	}


	@PostMapping("/logout")
	public Map<String, Object> logout(
			HttpServletRequest request) {

		Integer[] idRecursoPermisoArray = { 9 };
		JeResponse jeResponse = new JeResponse("Logout correcto", "Error grave al cerrr sesión");

		UsuarioSesionInterno userSession = null;

		LoginLog loginLog = null;
		try {
			userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

			if (jeResponse.sinErrorValidacion()) {

				//(property) OS: "ios" | "android" | "windows" | "macos" | "web"
				//RENEWIOS RENEWDROID
				String sistemaOperativoMovil = request.getHeader("xrn-so") == null ? "WEB"
				: request.getHeader("xrn-so").toString();

				sistemaOperativoMovil = sistemaOperativoMovil.equalsIgnoreCase("android") ? "LOGOUTDROI"
					: sistemaOperativoMovil.equalsIgnoreCase("ios") ? "LOGOUTIOS" : "LOGOUTWEB";

				loginLog = new LoginLog();

				loginLog.setUsuario(userSession.getUsuario());
				loginLog.setTipo(sistemaOperativoMovil);
				loginLog.setRequestInfo(JeBoot.getHttpRequestInfo(request));
				loginLog.setExitoso(false);
				loginLog.setIp(request.getRemoteAddr());
				loginLog.setMotivo(null);
				loginLog.setActivo(true);
				
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLogDao.agregar(infoAuditoria, loginLog);


				// UsuarioSesionInterno usuarioSesion = usuarioDao.obtenerUsuarioSesionPorUsuario(request,
				// 		userSession.getUsuario(), userSession.getEmpresaCore());
				// usuarioSesion.setIat(new Date().getTime());

				// jeResponse.putResultado("token", jwtGenerator.generateToken(usuarioSesion.getUsuarioJwt()));
				// jeResponse.putResultado("user", usuarioSesion.getUsuarioSesionExterno());

				//Actualiar loginLog;
				// loginLog.setExitoso(true);
				// loginLogDao.modificar(infoAuditoria, loginLog);

			}

			/* AUTOGENERADO _KGC_ */
			jeResponse.prepararRetornoMap();
		} catch (Exception e) {
			jeResponse.prepararRetornoErrorMap(e);
			log.error(jeResponse.getErrorForLog(), e);
			// Guardar resultado error
			try {
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLog.setMotivo(jeResponse.getErrorValList().toString());
				loginLogDao.modificar(infoAuditoria, loginLog);
			} catch (Exception e2) {
				log.error("ERROR AL ACTUALIZAR LOGIN LOG RENEW",e2);
			}
		}

		return jeResponse.getRetornoMap();
	}

	

	@PostMapping("/renew")
	public Map<String, Object> renew(
			HttpServletRequest request) {

		Integer[] idRecursoPermisoArray = { 9 };
		JeResponse jeResponse = new JeResponse("Acceso correcto", "Error grave al acceder");

		UsuarioSesionInterno userSession = null;

		LoginLog loginLog = null;
		try {
			userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

			if (jeResponse.sinErrorValidacion()) {

								//(property) OS: "ios" | "android" | "windows" | "macos" | "web"
				//RENEWIOS RENEWDROID
				String sistemaOperativoMovil = request.getHeader("xrn-so") == null ? "WEB"
				: request.getHeader("xrn-so").toString();

				sistemaOperativoMovil = sistemaOperativoMovil.equalsIgnoreCase("android") ? "RENEWDROID"
					: sistemaOperativoMovil.equalsIgnoreCase("ios") ? "RENEWIOS" : "RENEWWEB";

				loginLog = new LoginLog();

				loginLog.setUsuario(userSession.getUsuario());
				loginLog.setTipo(sistemaOperativoMovil);
				loginLog.setRequestInfo(JeBoot.getHttpRequestInfo(request));
				loginLog.setExitoso(false);
				loginLog.setIp(request.getRemoteAddr());
				loginLog.setMotivo(null);
				loginLog.setActivo(true);
				
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLogDao.agregar(infoAuditoria, loginLog);


				UsuarioSesionInterno usuarioSesion = usuarioDao.obtenerUsuarioSesionPorUsuario(request,
						userSession.getUsuario(), userSession.getEmpresaCore());
				usuarioSesion.setIat(new Date().getTime());

				jeResponse.putResultado("token", jwtGenerator.generateToken(usuarioSesion.getUsuarioJwt()));
				jeResponse.putResultado("user", usuarioSesion.getUsuarioSesionExterno());

				//Actualiar loginLog;
				loginLog.setExitoso(true);
				loginLogDao.modificar(infoAuditoria, loginLog);

			}

			/* AUTOGENERADO _KGC_ */
			jeResponse.prepararRetornoMap();
		} catch (Exception e) {
			jeResponse.prepararRetornoErrorMap(e);
			log.error(jeResponse.getErrorForLog(), e);
			// Guardar resultado error
			try {
				InfoAuditoria infoAuditoria = new InfoAuditoria(true);
				loginLog.setMotivo(jeResponse.getErrorValList().toString());
				loginLogDao.modificar(infoAuditoria, loginLog);
			} catch (Exception e2) {
				log.error("ERROR AL ACTUALIZAR LOGIN LOG RENEW",e2);
			}
		}

		return jeResponse.getRetornoMap();
	}

	@PostMapping("/verificarPermiso")
	public Map<String, Object> verificarPermiso(
			HttpServletRequest request,
			@RequestBody VerificarRecursoDto verificarRecursoDto) {

		Integer idRecurso = verificarRecursoDto.getIdRecurso();
		Integer[] idRecursoPermisoArray = { idRecurso };
		JeResponse jeResponse = new JeResponse("Permitido", "Error grave al verificar permiso");

		UsuarioSesionInterno userSession = null;

		try {

			userSession = SesionJwt.getUserSesion(request, idRecursoPermisoArray);

			if (jeResponse.sinErrorValidacion()) {

				System.out.println("idRecurso = " + idRecurso + ", request= " + request);

				// UsuarioSesionInterno
				// usuarioSesion=UsuarioTemporalModel.obtenerUsuarioSesion(datosInicioSesion);
				// String tokenGenerado=SesionJwt.generarToken(usuarioSesion);
				// usuarioSesion.setToken(tokenGenerado);

				// jeResponse.putResultado("obj", usuarioSesion.getUsuarioSesionExterno());

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



	private UsuarioSesionInterno obtenerUsuarioAcaNomas(HttpServletRequest request,
            InicioSesionDto datosInicioSesion) {
 
        UsuarioSesionInterno usuarioSesion = new UsuarioSesionInterno();
        usuarioSesion.setEmpresaCore("EMPKUAA");
        usuarioSesion.setIdSesion(5);
        // usuarioSesion.setIdCuenta(1);
        usuarioSesion.setCuenta(datosInicioSesion.getUser().toLowerCase());
        usuarioSesion.setIdUsuario(1);
        usuarioSesion.setUsuario(datosInicioSesion.getUser().toLowerCase());
        usuarioSesion.setNombre(JeBoot.getPrimeroMayuscula(datosInicioSesion.getUser()));
        usuarioSesion.setApellido(datosInicioSesion.getUser().toUpperCase());
        usuarioSesion.setIdRecursoConcat("," + 0 + ",");
        usuarioSesion.setIdRecursoConcatBase16("," + 0 + ",");
        usuarioSesion.setTimeOutSesion(300);
        return usuarioSesion;
    }

}

@Data
class VerificarRecursoDto {
	private Integer idRecurso;
}