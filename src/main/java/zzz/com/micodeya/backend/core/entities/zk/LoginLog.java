package zzz.com.micodeya.backend.core.entities.zk;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zzz.com.micodeya.backend.core.entities.AbstractModelZk;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ZK_LOGIN_LOG")
@SQLDelete(sql = "UPDATE ZK_LOGIN_LOG SET zk_eliminado = true, zk_fec_elim= now() WHERE id_login_log=?")
@Where(clause = "zk_eliminado=false")
public class LoginLog extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_login_log_seq", allocationSize = 1)
	private Integer idLoginLog;

	@NotEmpty(message = "Usuario: no debe estar vacio")
	@Size(min = 0, max = 100, message = "Usuario: cantidad de caracteres debe ser entre 0 y 100")
	private String usuario;

	// valueList-> WEB:WEB;APPIOS:APPIOS;APPDROID:APPDROID;RENEWIOS:RENEWIOS;RENEWDROID:RENEWDROID;RENEWWEB:RENEWWEB;LOGOUTIOS:LOGOUTIOS;LOGOUTDROID:LOGOUTDROI;LOGOUTWEB:LOGOUTWEB;
	@NotEmpty(message = "Tipo: no debe estar vacio")
	@Size(min = 0, max = 10, message = "Tipo: cantidad de caracteres debe ser entre 0 y 10")
	private String tipo;

	//Información de la Petición
	@NotEmpty(message = "Request Info: no debe estar vacio")
	@Size(min = 0, max = 5000, message = "Request Info: cantidad de caracteres debe ser entre 0 y 5000")
	private String requestInfo;

	@NotNull(message = "Exitoso: no debe ser nulo")
	private Boolean exitoso;

	@NotEmpty(message = "Ip: no debe estar vacio")
	@Size(min = 0, max = 20, message = "Ip: cantidad de caracteres debe ser entre 0 y 20")
	private String ip;

	//Motivo de resultado no exitoso
	@Size(min = 0, max = 300, message = "Motivo: cantidad de caracteres debe ser entre 0 y 300")
	private String motivo;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public LoginLog(Integer id) {
		this.idLoginLog = id;
	}

	public LoginLog(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public LoginLog(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
