package com.micodeya.encomiendassp.backend.entities.per;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "PER_CLIENTE")
@SQLDelete(sql = "UPDATE PER_CLIENTE SET zk_eliminado = true, zk_fec_elim= now() WHERE id_cliente=?")
@Where(clause = "zk_eliminado=false")
public class Cliente extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "per_cliente_seq", allocationSize = 1)
	private Integer idCliente;

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 2, max = 60, message = "Nombre: cantidad de caracteres debe ser entre 2 y 60")
	private String nombre;

	@NotEmpty(message = "Apellido: no debe estar vacio")
	@Size(min = 2, max = 60, message = "Apellido: cantidad de caracteres debe ser entre 2 y 60")
	private String apellido;

	@NotNull(message = "Número Documento: no debe ser nulo")
	private Integer numeroDocumento;

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@Formula("TO_CHAR(fecha_nacimiento, 'dd/MM/yyyy')")
	private String fechaNacimientoMask;

	@NotNull(message = "Número Telefono: no debe ser nulo")
	@Size(min = 0, max = 30, message = "Número Telefono: cantidad de caracteres debe ser entre 0 y 30")
	private String numeroTelefono;

	@Size(min = 5, max = 60, message = "Dirección: cantidad de caracteres debe ser entre 5 y 60")
	private String direccion;

	@NotEmpty(message = "Ciudad: no debe estar vacio")
	@Size(min = 2, max = 60, message = "Ciudad: cantidad de caracteres debe ser entre 2 y 60")
	private String ciudad;

	@NotNull(message = "Activo: no debe ser nulo")
	private Boolean activo;


	public Cliente(Integer id) {
		this.idCliente = id;
	}

	public Cliente(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public Cliente(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
