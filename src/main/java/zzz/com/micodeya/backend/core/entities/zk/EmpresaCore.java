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
@Table(name = "ZK_EMPRESA_CORE")
@SQLDelete(sql = "UPDATE ZK_EMPRESA_CORE SET zk_eliminado = true, zk_fec_elim= now() WHERE id_empresaCore=?")
@Where(clause = "zk_eliminado=false")
public class EmpresaCore extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_empresa_core_seq", allocationSize = 1)
	private Integer idEmpresaCore;

	@NotEmpty(message = "Código: no debe estar vacio")
	@Size(min = 1, max = 30, message = "Código: cantidad de caracteres debe ser entre 1 y 30")
	private String codigo;

	@NotEmpty(message = "Nombre Corto: no debe estar vacio")
	@Size(min = 0, max = 50, message = "Nombre Corto: cantidad de caracteres debe ser entre 0 y 50")
	private String nombreCorto;

	@NotEmpty(message = "Nombre Completo: no debe estar vacio")
	@Size(min = 0, max = 150, message = "Nombre Completo: cantidad de caracteres debe ser entre 0 y 150")
	private String nombreCompleto;

	@Size(min = 0, max = 200, message = "Observación: cantidad de caracteres debe ser entre 0 y 200")
	private String observacion;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;

	
	public EmpresaCore(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idEmpresaCore=id;
	}

	public EmpresaCore(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
