package zzz.com.micodeya.backend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KwfAuthException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private boolean warning;
	private String codigoEstado;
	
	public KwfAuthException(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = false;
		this.codigoEstado=CodigoEstado.SIN_DATO;
	}

	public KwfAuthException(String mensaje, boolean warning) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = warning;
		this.codigoEstado=CodigoEstado.SIN_DATO;
	}

	public KwfAuthException(String mensaje, boolean warning, String codigoEstado) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = warning;
		this.codigoEstado=codigoEstado;
	}
	
	
	public String getMensaje() {
		return mensaje;
	}
	
	public boolean isWarning(){
		return warning;
	}

	public String getCodigoEstado() {
		return codigoEstado;
	}

	public class CodigoEstado{
		public static final String SIN_DATO="SIN_DATO"; 
		public static final String EXITOSO="AUTH_EXITOSO";
		
		//login
		public static final String USUARIO_NO_ENCONTRADO="AUTH_ERROR_USUARIO_NO_ENCONTRADO";
		public static final String PASWORD_INCORRECTO="AUTH_ERROR_PASWORD_INCORRECTO";
		public static final String USUARIO_INACTIVO="AUTH_ERROR_USUARIO_INACTIVO";
		public static final String USUARIO_SUSPENDIDO="AUTH_ERROR_USUARIO_SUSPENDIDO";
		public static final String USUARIO_SIN_PRIVILEGIO="AUTH_USUARIO_SIN_PRIVILEGIO";
		//public static final String USUARIO_SIN ="AUTH_ERROR_";

		//Check Token
		public static final String BEARER_TOKEN_NULO="AUTH_ERROR_BEARER_TOKEN_NULO";
		public static final String JWT_INFO_NULO="AUTH_ERROR_JWT_INFO_NULO";
		public static final String BEARER_ARRAY_INCORRECTO="AUTH_ERROR_BEARER_ARRAY_INCORRECTO";
		public static final String TOKEN_NULO="AUTH_ERROR_TOKEN_NULO";
		public static final String TOKEN_PARAM_NULO="AUTH_ERROR_TOKEN_PARAM_NULO";
		public static final String TOKEN_NO_ENCONTRADO="AUTH_ERROR_TOKEN_NO_ENCONTRADO";
		public static final String TOKEN_EXPIRADO="AUTH_ERROR_TOKEN_EXPIRADO";
		public static final String SESION_TIMEOUT="AUTH_ERROR_SESION_TIMEOUT";
		public static final String SESION_TOKEN_NO_ENCONTRADO="SESION_TOKEN_NO_ENCONTRADO";
		public static final String TOKEN_ACCESO_DENEGADO_RECURSO="AUTH_ERROR_TOKEN_ACCESO_DENEGADO_RECURSO";
		
 
	}
}
