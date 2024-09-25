package zzz.com.micodeya.backend.core.entities.zk;


import zzz.com.micodeya.backend.core.entities.AbstractModelZk;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.JoinColumn;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import zzz.com.micodeya.backend.core.entities.zk.Recurso;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;




@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ZK_ROL_RECURSO")
@SQLDelete(sql = "UPDATE ZK_ROL_RECURSO SET zk_eliminado = true, zk_fec_elim= now() WHERE id_rol_recurso=?")
@Where(clause = "zk_eliminado=false")
public class RolRecurso extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_rol_recurso_seq", allocationSize = 1)
	private Integer idRolRecurso;

	@NotNull(message = "ID Recurso: no debe ser nulo")
	@ManyToOne
	@JoinColumn(name = "id_recurso")
	private Recurso recurso; 

	@NotNull(message = "ID Rol: no debe ser nulo")
	@ManyToOne
	@JoinColumn(name = "id_rol")
	private Rol rol; 

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;

	
	public RolRecurso(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idRolRecurso=id;
	}

	public RolRecurso(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
