package zzz.com.micodeya.backend.core.util.security;

import java.util.Date;

import org.json.JSONObject;

import lombok.Data;

@Data
public class UsuarioJwt {

	private String usr;

	private Integer idUsr;
	private Integer tou;

	private String nom;
	private String ape;
	private String cue;
	private String ali;
	private String emp;

	private String idr;
	private long iat;


	// private Map<String,Object> extra;

	// rest
	//private String token;

	public String getPayload() {
		/*
		 * "sub": "1234567890",
		 * "name": "John Doe",
		 * "iat": 1516239022
		 */
		JSONObject json = new JSONObject();
		json.put("idu",idUsr);
		json.put("usr", usr);
		json.put("tou", tou);
		json.put("iat", iat);
		json.put("nom", nom);
		json.put("ape", ape);
		json.put("idr", idr);
		json.put("cue", cue);
		json.put("ali", ali);
		json.put("emp", emp);

		return json.toString();
	}

	// public String getCuentaUsuarioAudit() {
	// //empresaCore-sucursal-cuenta-usuario-idSesion
	// if(username==null || idSesion==null || username.trim().length()==0){
	// return null;
	// }
	// //return empresaCore+"-"+sucursal+"-"+kwfCuentaNombre+"-"+username+"-"+idSesion;
	// return "ande"+"-"+1+"-"+kwfCuentaNombre+"-"+username+"-"+idSesion;
	// }

}