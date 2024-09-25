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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;




@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ZK_AUDITORIA_ESPECIFICA")
@SQLDelete(sql = "UPDATE ZK_AUDITORIA_ESPECIFICA SET zk_eliminado = true, zk_fec_elim= now() WHERE id_auditoria_especifica=?")
@Where(clause = "zk_eliminado=false")
public class AuditoriaEspecifica extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_auditoria_especifica_seq", allocationSize = 1)
	private Integer idAuditoriaEspecifica;

	@NotEmpty(message = "Tabla: no debe estar vacio")
	@Size(min = 0, max = 60, message = "Tabla: cantidad de caracteres debe ser entre 0 y 60")
	private String tabla;

	@NotEmpty(message = "Atributo ID: no debe estar vacio")
	@Size(min = 0, max = 60, message = "Atributo ID: cantidad de caracteres debe ser entre 0 y 60")
	private String atributoId;

	@NotNull(message = "Valor ID: no debe ser nulo")
	private Integer valorId;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;

	@NotEmpty(message = "Atributo: no debe estar vacio")
	@Size(min = 0, max = 100, message = "Atributo: cantidad de caracteres debe ser entre 0 y 100")
	private String atributo;

	@Size(min = 0, message = "0: cantidad de caracteres debe ser como mínimo Valor Anterior")
	private String valorAnterior;

	@Size(min = 0, message = "0: cantidad de caracteres debe ser como mínimo Valor Nuevo")
	private String valorNuevo;

	@NotEmpty(message = "UUID Registro: no debe estar vacio")
	@Size(min = 0, max = 100, message = "UUID Registro: cantidad de caracteres debe ser entre 0 y 100")
	private String uuidRegistro;

	
	public AuditoriaEspecifica(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idAuditoriaEspecifica=id;
	}

	public AuditoriaEspecifica(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
