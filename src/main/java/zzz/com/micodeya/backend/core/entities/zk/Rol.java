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
@Table(name = "ZK_ROL")
@SQLDelete(sql = "UPDATE ZK_ROL SET zk_eliminado = true, zk_fec_elim= now() WHERE id_rol=?")
@Where(clause = "zk_eliminado=false")
public class Rol extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_rol_seq", allocationSize = 1)
	private Integer idRol;

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 3, max = 100, message = "Nombre: cantidad de caracteres debe ser entre 3 y 100")
	private String nombre;

	@NotEmpty(message = "Código: no debe estar vacio")
	@Size(min = 3, max = 60, message = "Código: cantidad de caracteres debe ser entre 3 y 60")
	private String codigo;

	@Size(min = 0, max = 200, message = "Descripción: cantidad de caracteres debe ser entre 0 y 200")
	private String descripcion;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	@Formula(" (SELECT string_agg(TO_CHAR(zk_rol_recurso.id_recurso,'FM999999999'),',') "
			+" FROM  zk_rol_recurso  INNER JOIN zk_recurso "
			+" ON zk_rol_recurso.id_recurso=zk_recurso.id_recurso "
			+" WHERE zk_rol_recurso.id_rol=id_rol AND zk_rol_recurso.activo=true LIMIT 1) ")
	private String recursos;

	@Formula(" (SELECT string_agg(TO_CHAR(zk_rol_recurso.id_recurso,'FM999999999'),',') "
			+" FROM  zk_rol_recurso  INNER JOIN zk_recurso "
			+" ON zk_rol_recurso.id_recurso=zk_recurso.id_recurso "
			+" WHERE zk_recurso.tipo = 'VIS' AND zk_rol_recurso.id_rol=id_rol AND zk_rol_recurso.activo=true LIMIT 1) ")
	private String recursosVista;


	@Formula(" (SELECT string_agg(TO_CHAR(zk_rol_recurso.id_recurso,'FM999999999'),',') "
			+" FROM  zk_rol_recurso  INNER JOIN zk_recurso "
			+" ON zk_rol_recurso.id_recurso=zk_recurso.id_recurso "
			+" WHERE zk_recurso.tipo = 'FUN' AND zk_rol_recurso.id_rol=id_rol AND zk_rol_recurso.activo=true LIMIT 1) ")
	private String recursosFuncion;

	@Formula(" (SELECT string_agg(TO_CHAR(zk_rol_recurso.id_recurso,'FM999999999')||'-',',') "
			+" FROM  zk_rol_recurso  INNER JOIN zk_recurso "
			+" ON zk_rol_recurso.id_recurso=zk_recurso.id_recurso "
			+" WHERE zk_recurso.tipo = 'REP' AND zk_rol_recurso.id_rol=id_rol AND zk_rol_recurso.activo=true LIMIT 1) ")
	private String recursosReporte;

	
	public Rol(Integer id) {
		this.idRol=id;
	}
	public Rol(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore=zkEmpresaCore;
		this.zkCuenta=zkCuenta;
	}

	public Rol(String zkCuenta) {
		this.zkCuenta=zkCuenta;
	}


}
