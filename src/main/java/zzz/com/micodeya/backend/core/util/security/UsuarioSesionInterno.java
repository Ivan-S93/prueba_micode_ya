package zzz.com.micodeya.backend.core.util.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zzz.com.micodeya.backend.core.exception.ValidacionException;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSesionInterno {

	private String usuario;
	
	private Integer idUsuario;
	private Integer idSesion;
	
	private String nombre;
	private String apellido;

	private String estado;
	private String email;

	private String activo;
	private Integer tipoLogin;
	private String idRecursoConcat;
	private String idRecursoConcatBase16; 
	
	private Integer timeOutSesion;
	
	
	private String cuenta;
	private String alias;

	private String empresaCore;
	private long iat;
	
	
	//private Map<String,Object> extra;
	
	//rest
	private String token;
	private Date tokenFechaHora;
	private Date ultimaTransaccion;
	private String sessionId;
	

	
	public UsuarioSesionExterno getUsuarioSesionExterno() {
		
		UsuarioSesionExterno user= new UsuarioSesionExterno();
		
		user.setIdUsuario(this.idUsuario);
		user.setUsuario(this.usuario);
		user.setEmpresaCore(this.empresaCore);
		user.setCuenta(this.cuenta);
		user.setAlias(this.alias);
		user.setNombre(this.nombre);
		user.setApellido(this.apellido);
		user.setIdRecursoConcat(this.idRecursoConcat);
		user.setTimeOutSesion(this.timeOutSesion);
		user.setIat(this.iat);
		user.setEstado(this.estado);
		user.setEmail(this.email);

		
		return user;
	
	}
 
	public void setFromClaims(Claims claims){

		
		//this.idSesion(5);
		// this.idCuenta(claims.getIdUsuario());
		// this.idUsuario(usuario.getIdUsuario());
		this.idUsuario=Integer.parseInt(claims.get("idu").toString());
		this.usuario=claims.get("usr").toString();
		this.nombre=claims.get("nom").toString();
		this.apellido=claims.get("ape").toString();
		this.idRecursoConcatBase16=claims.get("idr").toString();
		this.empresaCore=claims.get("emp").toString();
		this.cuenta=claims.get("cue").toString();
		if(claims.get("ali")!=null){
			this.alias=claims.get("ali").toString();
		}else{
			this.alias="sin-alias";
		}
		this.timeOutSesion=Integer.parseInt(claims.get("tou").toString());
		this.iat=Long.valueOf(claims.get("iat").toString());

	}

	public UsuarioJwt getUsuarioJwt() {
		
		UsuarioJwt user= new UsuarioJwt();
		
		user.setIdUsr(this.idUsuario);
		user.setTou(this.timeOutSesion);
		user.setUsr(this.usuario);
		user.setNom(this.nombre);
		user.setApe(this.apellido);
		user.setIdr(this.idRecursoConcatBase16);
		user.setCue(this.cuenta);
		user.setAli(this.alias);
		user.setEmp(this.empresaCore);
		user.setIat(this.iat);
		
		return user;
	
	}


	public InfoAuditoria getInfoAuditoria(){
		InfoAuditoria infoAuditoria= new InfoAuditoria();
        infoAuditoria.setUsuario(this.usuario.toLowerCase());
		
        infoAuditoria.setIdSesion(this.idSesion);
        infoAuditoria.setIdUsuario(this.idUsuario);

        infoAuditoria.setCuenta(this.cuenta.toLowerCase());
        infoAuditoria.setEmpresaCore(this.empresaCore.toUpperCase());

		return infoAuditoria;

	}


	public boolean esMismaCuenta(String cuentaCore, boolean conException){
		if(conException){
			if(!esMismaCuenta(cuentaCore)){
				throw new ValidacionException("Cuenta ingresada incorrecta");
			}
		}
		return esMismaCuenta(cuentaCore);
	}
	
	public boolean esMismaCuenta(String cuentaCore){
		if(cuentaCore==null) return false;
		return cuentaCore.equals(this.getCuenta());
	}

    // public String getEmpresaCoreUsuario(){
    //     return empresaCore+"-"+usuario;
    // }

    // public String getUsuarioIdSesion(){
    //     return usuario+"-"+idSesion;
    // }
	
//	public String getCuentaUsuarioAudit() {
//		//empresaCore-sucursal-cuenta-usuario-idSesion
//		if(username==null || idSesion==null || username.trim().length()==0){
//			return null;
//		}
//		//return empresaCore+"-"+sucursal+"-"+kwfCuentaNombre+"-"+username+"-"+idSesion;
//		return "ande"+"-"+1+"-"+kwfCuentaNombre+"-"+username+"-"+idSesion;
//	}
		


		


	


	
	

}