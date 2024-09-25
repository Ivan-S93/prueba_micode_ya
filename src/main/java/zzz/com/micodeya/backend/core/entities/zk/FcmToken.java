package zzz.com.micodeya.backend.core.entities.zk;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "ZK_FCM_TOKEN")
@SQLDelete(sql = "UPDATE ZK_FCM_TOKEN SET zk_eliminado = true, zk_fec_elim= now() WHERE id_fcm_token=?")
@Where(clause = "zk_eliminado=false")
public class FcmToken extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_fcm_token_seq", allocationSize = 1)
	private Integer idFcmToken;

	@NotEmpty(message = "Fcm Token: no debe estar vacio")
	@Size(min = 5, max = 200, message = "Fcm Token: cantidad de caracteres debe ser entre 5 y 200")
	private String fcmToken;

	@Size(min = 0, max = 60, message = "Cuenta: cantidad de caracteres debe ser entre 0 y 60")
	private String cuenta;

	//Alguna información del dispositvo o login, tales como SO, version App, IP, etc
	@NotEmpty(message = "Informacion: no debe estar vacio")
	@Size(min = 0, max = 10000, message = "Informacion: cantidad de caracteres debe ser entre 0 y 10000")
	private String informacion;

	//Alguna información del dispositvo o login, tales como SO, version App, IP, etc
	@NotNull(message = "Ultima Actualizacion: no debe ser nulo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimaActualizacion;

	@Formula("TO_CHAR(ultima_actualizacion, 'dd/MM/yyyy HH24:MI:SS')")
	private String ultimaActualizacionMask;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public FcmToken(Integer id) {
		this.idFcmToken = id;
	}

	public FcmToken(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public FcmToken(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
