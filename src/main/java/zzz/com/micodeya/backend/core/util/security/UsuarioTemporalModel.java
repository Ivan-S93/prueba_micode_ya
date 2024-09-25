package zzz.com.micodeya.backend.core.util.security;

public class UsuarioTemporalModel {

	public static UsuarioSesionInterno obtenerUsuarioSesion(InicioSesionDto datosInicioSesion) {

		UsuarioSesionInterno usuarioSesion = new UsuarioSesionInterno();


		usuarioSesion.setEmpresaCore("EMPKUAA");
		usuarioSesion.setIdSesion(5);
		usuarioSesion.setUsuario(datosInicioSesion.getUser());
		usuarioSesion.setNombre("nombre "+datosInicioSesion.getUser());
		usuarioSesion.setApellido("apellido "+datosInicioSesion.getUser());
		usuarioSesion.setIdRecursoConcat(",9,100,101,102,103,");
		usuarioSesion.setTimeOutSesion(100);
		

		return usuarioSesion;

	}

	public static void cerrarSesion(String token, Integer idSesion) {

		/*
		 * 
		 * Aqui debe cerrar sesion en DB
		 */

	}
}
