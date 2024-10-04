package com.micodeya.encomiendassp.backend.entities.gral;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "GRAL_DEPARTAMENTO_GEOGRAFICO")
@SQLDelete(sql = "UPDATE GRAL_DEPARTAMENTO_GEOGRAFICO SET zk_eliminado = true, zk_fec_elim= now() WHERE id_departamento_geografico=?")
@Where(clause = "zk_eliminado=false")
public class DepartamentoGeografico extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "gral_departamento_geografico_seq", allocationSize = 1)
	private Integer idDepartamentoGeografico;

	//Formato 000 por orden departamental. Ejem: 000 Asunción. 001 Concepción 
	@NotEmpty(message = "Codigo: no debe estar vacio")
	@Size(min = 0, message = "0: cantidad de caracteres debe ser como mínimo Codigo")
	private String codigo;

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 0, max = 60, message = "Nombre: cantidad de caracteres debe ser entre 0 y 60")
	private String nombre;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public DepartamentoGeografico(Integer id) {
		this.idDepartamentoGeografico = id;
	}

	public DepartamentoGeografico(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public DepartamentoGeografico(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
