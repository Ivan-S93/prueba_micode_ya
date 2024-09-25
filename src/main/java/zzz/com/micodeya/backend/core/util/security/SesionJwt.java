package zzz.com.micodeya.backend.core.util.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.exception.KwfAuthException;
import zzz.com.micodeya.backend.core.util.JeBoot;

@Slf4j
public class SesionJwt {

	// Token, DATE
	public static Map<String, Long> ULTIMA_TRANSACCION_MAP = new HashMap<String, Long>();
	public static Integer DURACION_HORAS_JWT = null;

	public static boolean cerrarSesion(String token, int tipoLogout, HttpSession session) throws Exception {

		UsuarioSesionInterno userRetorno = null;

		if (token == null || token.length() < 20) {
			throw new RuntimeException("tokenerror1"); // token no valido
		}

		// UsuarioTemporalModel.cerrarSesion(token, tipoLogout);

		return true;
	}

	public static boolean cerrarSesion(HttpServletRequest request, int tipoLogout) throws Exception {

		String token = request.getHeader("x-token");

		if (token == null) {
			token = (request.getSession().getAttribute("kwfxtoken") != null
					? request.getSession().getAttribute("kwfxtoken").toString()
					: null);
		}

		return cerrarSesion(token, tipoLogout, request.getSession());
	}

	public static UsuarioSesionInterno getUserSesion(HttpServletRequest request, Integer[] idRecursoArray,
			String tokenParam) throws Exception {
		return getUserSesion(request, idRecursoArray, false, tokenParam);
	}

	public static UsuarioSesionInterno getUserSesion(HttpServletRequest request, Integer[] idRecursoArray,
			boolean requiereCuenta) throws Exception {
		return getUserSesion(request, idRecursoArray, requiereCuenta, null);
	}

	public static UsuarioSesionInterno getUserSesion(HttpServletRequest request, Integer[] idRecursoArray)
			throws Exception {
		return getUserSesion(request, idRecursoArray, false, null);
	}

	public static UsuarioSesionInterno getUserSesion(HttpServletRequest request, Integer[] idRecursoArray,
			boolean requiereCuenta, String tokenParam) throws Exception {

		UsuarioSesionInterno userRetorno = null;

		// String token=request.getHeader("zkx-token");
		String bearerToken = request.getHeader("Authorization");
		// String sessionId=request.getSession().getId();

		if (bearerToken == null) {
			bearerToken = request.getAttribute("authorizationtoken") == null ? null
					: request.getAttribute("authorizationtoken").toString();
		}

		if (bearerToken == null) {
			log.warn(
					KwfAuthException.CodigoEstado.BEARER_TOKEN_NULO + " request=" + JeBoot.getHttpRequestInfo(request));
			// throw new KwfAuthException("Token de autorización no
			// válido",true,KwfAuthException.CodigoEstado.BEARER_TOKEN_NULO);
		}

		String sistemaOperativoMovil = request.getHeader("xrn-so") == null ? null
				: request.getHeader("xrn-so").toString();
		String appVersionMovil = request.getHeader("xrn-version") == null ? "null"
				: request.getHeader("xrn-version").toString();

		String verificarSoloJwt=request.getAttribute("verificarSoloJwt")== null ? "no"
				: request.getAttribute("verificarSoloJwt").toString();	

		Claims claims = request.getAttribute("claims") == null ? null : (Claims) request.getAttribute("claims");

		if (claims == null) {
			log.warn(KwfAuthException.CodigoEstado.JWT_INFO_NULO + " request=" + JeBoot.getHttpRequestInfo(request));
			throw new KwfAuthException("Información de token no válido", true,
					KwfAuthException.CodigoEstado.JWT_INFO_NULO);
		}

		String token = bearerToken.substring(7);

		userRetorno = new UsuarioSesionInterno();
		userRetorno.setFromClaims(claims);

		// Verificar timeout
		long transaccionAhora = new Date().getTime();

		if (sistemaOperativoMovil != null || verificarSoloJwt.equalsIgnoreCase("si")) { // Controlar por vencimiento e JWT
			//// DURACION_HORAS_JWT
			long ultimaTransaccionRecuperada = userRetorno.getIat();
			long duration = transaccionAhora - ultimaTransaccionRecuperada;
			long cantHorasInactivo = TimeUnit.MILLISECONDS.toHours(duration);

			if (cantHorasInactivo > DURACION_HORAS_JWT) {
				log.warn(KwfAuthException.CodigoEstado.TOKEN_EXPIRADO + " usuario=" + userRetorno
						.getUsuario() + ", token=" + token + ", cantHorasInactivo="
						+ cantHorasInactivo + ", ultTransaccion=" + ultimaTransaccionRecuperada + ", ahora="
						+ new Date()
						+ ", request=" + JeBoot.getHttpRequestInfo(request));
				UsuarioTemporalModel.cerrarSesion(token, 2);
				throw new KwfAuthException("Token de autorizacion expirado", true,
						KwfAuthException.CodigoEstado.TOKEN_EXPIRADO);
			}

		} else { // Controlar por sesion de usuario

			if(!ULTIMA_TRANSACCION_MAP.containsKey(token)){
				log.warn(KwfAuthException.CodigoEstado.SESION_TOKEN_NO_ENCONTRADO + " usuario=" + userRetorno
						.getUsuario() + ", token=" + token + ", Token de sesión no encontrado."
						+ ", request=" + JeBoot.getHttpRequestInfo(request));
				UsuarioTemporalModel.cerrarSesion(token, 2);
				throw new KwfAuthException("Token de sesión no encontrado", true,
						KwfAuthException.CodigoEstado.SESION_TOKEN_NO_ENCONTRADO);
			}

			long ultimaTransaccionRecuperada = ULTIMA_TRANSACCION_MAP.get(token);
			long duration = transaccionAhora - ultimaTransaccionRecuperada;
			long cantMinutosInactivo = TimeUnit.MILLISECONDS.toMinutes(duration);

			if (userRetorno.getTimeOutSesion() > 0 && cantMinutosInactivo > userRetorno.getTimeOutSesion()) {
				log.warn(KwfAuthException.CodigoEstado.SESION_TIMEOUT + " usuario=" + userRetorno
						.getUsuario() + ", token=" + token + ", cantMinutosInactivo="
						+ cantMinutosInactivo + ", ultTransaccion=" + ultimaTransaccionRecuperada + ", ahora="
						+ new Date()
						+ ", request=" + JeBoot.getHttpRequestInfo(request));
				UsuarioTemporalModel.cerrarSesion(token, 2);
				throw new KwfAuthException("Tiempo de sesión expirado", true,
						KwfAuthException.CodigoEstado.SESION_TIMEOUT);
			}

		}

		/*
		 * long duration = new Date().getTime() - ultTransaccion.getTime();
		 * 
		 * long cantMinutosInactivo = TimeUnit.MILLISECONDS.toMinutes(duration);
		 * //int cantMinutosInactivo=Je.fechaCantMinutos(ultTransaccion, new Date());
		 * 
		 * if(cantMinutosInactivo>userRetorno.getTimeOutSesion()){
		 * log.warn(KwfAuthException.CodigoEstado.TOKEN_EXPIRADO+" usuario="+userRetorno
		 * .getUsuario()+", token="+token+", cantMinutosInactivo="
		 * +cantMinutosInactivo+", ultTransaccion="+ultTransaccion+", ahora="+new
		 * Date()+", request="+JeBoot.getHttpRequestInfo(request));
		 * UsuarioTemporalModel.cerrarSesion(token, 2);
		 * throw new KwfAuthException("Toke nde autorizacion expirado",true,
		 * KwfAuthException.CodigoEstado.TOKEN_EXPIRADO );
		 * }
		 * 
		 */

		boolean conPermiso = false;
		// 8 es super super usuario, colocar true
		conPermiso=userRetorno.getIdRecursoConcatBase16().contains(",8,");

		String contatIdRecurso = ",";

		if (!conPermiso && idRecursoArray!=null) { 
			for (int i = 0; i < idRecursoArray.length; i++) {
				Integer idActual = idRecursoArray[i];
				contatIdRecurso = contatIdRecurso + idActual + ",";
				if (userRetorno.getIdRecursoConcatBase16()
						.contains("," + Integer.toHexString(idActual).toUpperCase() + ",")) {
					conPermiso = true;
					break;
				}
			}
		}

		if (!conPermiso) {

			// log.warn("<user>"+userRetorno.getUsuarioIdSesion()+"</user>"+"Permiso
			// denegado: "+request.getRequestURI()+ " "+ contatIdRecurso);
			// log.warn("<user>"+userRetorno.getUsuario()+"</user>"+"Permiso denegado:
			// "+request.getRequestURI()+ " "+ contatIdRecurso);
			log.warn(KwfAuthException.CodigoEstado.TOKEN_ACCESO_DENEGADO_RECURSO + " usuario="
					+ userRetorno.getUsuario() + ", token=" + token + ", idRecursoConcat"
					+ userRetorno.getIdRecursoConcatBase16() + ", request=" + JeBoot.getHttpRequestInfo(request));
			throw new KwfAuthException("Sin autorización para el recurso", true,
					KwfAuthException.CodigoEstado.TOKEN_ACCESO_DENEGADO_RECURSO);
			// throw new RuntimeException("tokenerror4"); //sinPermiso
		}

		// actualizar ultima transaccion
		ULTIMA_TRANSACCION_MAP.put(token, transaccionAhora);

		return userRetorno;

	}

}
