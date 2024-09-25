package zzz.com.micodeya.backend.core.entities.zk;


import zzz.com.micodeya.backend.core.entities.AbstractModelZk;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Formula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Temporal;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GenerationType;
import java.math.BigDecimal;
import jakarta.persistence.Id;
import java.util.Date;




@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ZK_CONFIGURACION")
@SQLDelete(sql = "UPDATE ZK_CONFIGURACION SET zk_eliminado = true, zk_fec_elim= now() WHERE id_configuracion=?")
@Where(clause = "zk_eliminado=false")
public class Configuracion extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_configuracion_seq", allocationSize = 1)
	private Integer idConfiguracion;

	//Nivel que corresponde la configurcion valueList-> USUARIO:Usuario;CUENTA:Cuenta;EMPRESA:EmpresaCore;SISTEMA:Sistema
	@NotEmpty(message = "Nivel: no debe estar vacio")
	@Size(min = 1, max = 10, message = "Nivel: cantidad de caracteres debe ser entre 1 y 10")
	private String nivel;

	//Identificador de la configuracion
	@NotEmpty(message = "Código: no debe estar vacio")
	@Size(min = 1, max = 30, message = "Código: cantidad de caracteres debe ser entre 1 y 30")
	private String codigo;

	//Identificador secundario de la configuracion
	@Size(min = 0, max = 30, message = "Subcódigo: cantidad de caracteres debe ser entre 0 y 30")
	private String subcodigo;

	@Size(min = 0, max = 60, message = "Usuario: cantidad de caracteres debe ser entre 0 y 60")
	private String usuario;

	private Integer datoNumero;

	@Size(min = 0, max = 500, message = "Dato texto: cantidad de caracteres debe ser entre 0 y 500")
	private String datoTexto;

	//DECIMAL(20,5)
	private BigDecimal datoNumeroDecimal;

	@Size(min = 0, max = 10000, message = "Dato objeto JSON: cantidad de caracteres debe ser entre 0 y 10000")
	private String datoObjeto;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datoFechaHora;

	@Formula("TO_CHAR(dato_fecha_hora, 'dd/MM/yyyy HH24:MI:SS')")
	private String datoFechaHoraMask;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;

	
	public Configuracion(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idConfiguracion=id;
	}

	public Configuracion(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
