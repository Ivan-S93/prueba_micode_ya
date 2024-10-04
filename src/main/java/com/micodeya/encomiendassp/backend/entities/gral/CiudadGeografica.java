package com.micodeya.encomiendassp.backend.entities.gral;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.micodeya.encomiendassp.backend.entities.gral.DepartamentoGeografico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "GRAL_CIUDAD_GEOGRAFICA")
@SQLDelete(sql = "UPDATE GRAL_CIUDAD_GEOGRAFICA SET zk_eliminado = true, zk_fec_elim= now() WHERE id_ciudad_geografica=?")
@Where(clause = "zk_eliminado=false")
public class CiudadGeografica extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "gral_ciudad_geografica_seq", allocationSize = 1)
	private Integer idCiudadGeografica;

	@NotNull(message = "Departamento Geográfico: no debe ser nulo")
	@ManyToOne
	@JoinColumn(name = "id_departamento_geografico")
	private DepartamentoGeografico departamentoGeografico; 

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 0, max = 60, message = "Nombre: cantidad de caracteres debe ser entre 0 y 60")
	private String nombre;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public CiudadGeografica(Integer id) {
		this.idCiudadGeografica = id;
	}

	public CiudadGeografica(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public CiudadGeografica(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
