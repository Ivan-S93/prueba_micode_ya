package zzz.com.micodeya.backend.core.util.security;

import lombok.Data;

@Data
public class UsuarioSesionExterno {

	private Integer idUsuario;
	private String usuario;
	private String cuenta;
	private String alias;
	private String empresaCore;

	private String nombre;
	private String apellido;
	private String estado;
	private String email;
	
	private String idRecursoConcat;

	private Integer timeOutSesion;
	private long iat;
	
	

}