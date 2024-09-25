package zzz.com.micodeya.backend.core.entities.zk;


import zzz.com.micodeya.backend.core.entities.AbstractModelZk;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.Formula;
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
@Table(name = "ZK_RECURSO")
@SQLDelete(sql = "UPDATE ZK_RECURSO SET zk_eliminado = true, zk_fec_elim= now() WHERE id_recurso=?")
@Where(clause = "zk_eliminado=false")
public class Recurso extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	//@SequenceGenerator(name = "generator", sequenceName = "zk_recurso_seq", allocationSize = 1)
	private Integer idRecurso;

	@Formula("UPPER(to_hex(id_recurso::bigint)::text)")
	private String idRecursoBase16;

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 10, max = 100, message = "Nombre: cantidad de caracteres debe ser entre 10 y 100")
	private String nombre;

	@NotEmpty(message = "Grupo: no debe estar vacio")
	@Size(min = 1, max = 20, message = "Grupo: cantidad de caracteres debe ser entre 1 y 20")
	private String grupo;

	@Size(min = 0, max = 200, message = "Descripción: cantidad de caracteres debe ser entre 0 y 200")
	private String descripcion;

	// valueList-> VIS:Vista Menu;FUN:Funcionalidad;REP:Reporte;OTR:Otro
	@NotEmpty(message = "Tipo: no debe estar vacio")
	@Size(min = 0, max = 3, message = "Tipo: cantidad de caracteres debe ser entre 0 y 3")
	private String tipo;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;

	
	public Recurso(String zkEmpresaCore, Integer id) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.idRecurso=id;
	}

	public Recurso(String zkEmpresaCore) {
		this.zkEmpresaCore=zkEmpresaCore;
	}


}
