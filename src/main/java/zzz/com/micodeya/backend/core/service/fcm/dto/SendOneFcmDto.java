package zzz.com.micodeya.backend.core.service.fcm.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class SendOneFcmDto {
    private String fcmToken;

	private String titulo;
    private String contenido;
    private Map<String, String> cargaUtil;
    private String urlImagen;
    
    
    
	public SendOneFcmDto() {
		super();
	}

	public SendOneFcmDto(String fcmToken, String titulo, String contenido) {
		super();
		this.fcmToken = fcmToken;
		this.titulo = titulo;
		this.contenido = contenido;
		this.cargaUtil = new HashMap<String, String>();
	}



	public SendOneFcmDto(String titulo, String contenido, Map<String, String> cargaUtil, String urlImagen) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.cargaUtil = cargaUtil;
		this.urlImagen = urlImagen;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getContenido() {
		return contenido;
	}



	public void setContenido(String contenido) {
		this.contenido = contenido;
	}



	public Map<String, String> getCargaUtil() {
		return cargaUtil;
	}



	public void setCargaUtil(Map<String, String> cargaUtil) {
		this.cargaUtil = cargaUtil;
	}



	public String getUrlImagen() {
		return urlImagen;
	}



	public void setUrlImage(String urlImagen) {
		this.urlImagen = urlImagen;
	}



	public String getFcmToken() {
		return fcmToken;
	}



	public void setFcmToken(String token) {
		this.fcmToken = token;
	}
    
	
	
    
}
