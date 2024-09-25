package zzz.com.micodeya.backend.core.entities.zk;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zzz.com.micodeya.backend.core.entities.AbstractModelZk;
import zzz.com.micodeya.backend.core.entities.zk.Rol;
import zzz.com.micodeya.backend.core.entities.zk.Usuario;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ZK_USUARIO_ROL")
@SQLDelete(sql = "UPDATE ZK_USUARIO_ROL SET zk_eliminado = true, zk_fec_elim= now() WHERE id_usuario_rol=?")
@Where(clause = "zk_eliminado=false")
public class UsuarioRol extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_usuario_rol_seq", allocationSize = 1)
	private Integer idUsuarioRol;

	@Size(min = 1, max = 30, message = "Código Empresa Core: cantidad de caracteres debe ser entre 1 y 30")
	private String codigoEmpresaCore;

	@NotNull(message = "Usuario: no debe ser nulo")
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario; 

	@NotNull(message = "Rol: no debe ser nulo")
	@ManyToOne
	@JoinColumn(name = "id_rol")
	private Rol rol; 

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public UsuarioRol(Integer id) {
		this.idUsuarioRol = id;
	}

	public UsuarioRol(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public UsuarioRol(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
