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
@Table(name = "ZK_ELIMINADO_INTENCION")
@SQLDelete(sql = "UPDATE ZK_ELIMINADO_INTENCION SET zk_eliminado = true, zk_fec_elim= now() WHERE id_eliminado_intencion=?")
@Where(clause = "zk_eliminado=false")
public class EliminadoIntencion extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_eliminado_intencion_seq", allocationSize = 1)
	private Integer idEliminadoIntencion;

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

	
	public EliminadoIntencion(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idEliminadoIntencion=id;
	}

	public EliminadoIntencion(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
