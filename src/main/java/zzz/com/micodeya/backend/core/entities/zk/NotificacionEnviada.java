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
@Table(name = "ZK_NOTIFICACION_ENVIADA")
@SQLDelete(sql = "UPDATE ZK_NOTIFICACION_ENVIADA SET zk_eliminado = true, zk_fec_elim= now() WHERE id_notificacion_enviada=?")
@Where(clause = "zk_eliminado=false")
public class NotificacionEnviada extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_notificacion_enviada_seq", allocationSize = 1)
	private Integer idNotificacionEnviada;

	//Bloqueo: Debe Aceptar si o si.
	//Info: muestra un mensaje en la App.
	//REDIREC: Redirecciona a otra pagina.
	//SOLOPUSH: Envia solo push. valueList-> BLOQUEO:Bloqueo Pantalla;INFO:Información;REDIREC:Redirección;SOLOPUSH:Solo Push;CHAT:Mensaje de Chat
	@NotEmpty(message = "Tipo: no debe estar vacio")
	@Size(min = 0, max = 10, message = "Tipo: cantidad de caracteres debe ser entre 0 y 10")
	private String tipo;

	@NotEmpty(message = "Cuenta: no debe estar vacio")
	@Size(min = 0, max = 60, message = "Cuenta: cantidad de caracteres debe ser entre 0 y 60")
	private String cuenta;

	@NotNull(message = "Fecha Hora Enviado: no debe ser nulo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHoraEnviado;

	@Formula("TO_CHAR(fecha_hora_enviado, 'dd/MM/yyyy HH24:MI:SS')")
	private String fechaHoraEnviadoMask;

	@Size(min = 0, max = 200, message = "Token: cantidad de caracteres debe ser entre 0 y 200")
	private String token;

	@Size(min = 0, max = 100, message = "Titulo: cantidad de caracteres debe ser entre 0 y 100")
	private String titulo;

	@NotEmpty(message = "Contenido: no debe estar vacio")
	@Size(min = 0, max = 300, message = "Contenido: cantidad de caracteres debe ser entre 0 y 300")
	private String contenido;

	@Size(min = 0, max = 300, message = "Url Imagen: cantidad de caracteres debe ser entre 0 y 300")
	private String urlImagen;

	//json que se desea enviar
	@Size(min = 0, max = 2000, message = "Carga Util: cantidad de caracteres debe ser entre 0 y 2000")
	private String cargaUtil;

	@NotNull(message = "Visto: no debe ser nulo")
	private Boolean visto;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHoraVisto;

	@Formula("TO_CHAR(fecha_hora_visto, 'dd/MM/yyyy HH24:MI:SS')")
	private String fechaHoraVistoMask;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHoraVencimiento;

	@Formula("TO_CHAR(fecha_hora_vencimiento, 'dd/MM/yyyy HH24:MI:SS')")
	private String fechaHoraVencimientoMask;

	@Size(min = 0, max = 500, message = "Observación: cantidad de caracteres debe ser entre 0 y 500")
	private String observacion;


	public NotificacionEnviada(Integer id) {
		this.idNotificacionEnviada = id;
	}

	public NotificacionEnviada(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public NotificacionEnviada(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI

	@Formula("(SELECT zk_usuario.alias"
			+" FROM zk_usuario "
			+" WHERE zk_usuario.cuenta=zk_cuenta)")
    private String aliasCreadoPor;

	
    // KGC-NOREPLACE-OTROS-FIN

}
