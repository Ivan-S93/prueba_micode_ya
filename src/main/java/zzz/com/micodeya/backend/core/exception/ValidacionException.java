package zzz.com.micodeya.backend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ValidacionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String atributoCuestion;
	private Integer idCuestion;
	private boolean warning;
	
	public ValidacionException(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = true;
	}

	public ValidacionException(String mensaje, boolean warning) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = warning;
	}

	public ValidacionException(String mensaje, String atributoCuestion, Integer idCuestion) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = true;
		this.atributoCuestion = atributoCuestion;
		this.idCuestion = idCuestion;
	}
	
	
	public String getMensaje() {
		return mensaje;
	}
	
	public boolean isWarning(){
		return warning;
	}
}
