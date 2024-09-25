package zzz.com.micodeya.backend.core.entities.zk;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import jakarta.validation.constraints.Email;
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
@Table(name = "ZK_USUARIO")
@SQLDelete(sql = "UPDATE ZK_USUARIO SET zk_eliminado = true, zk_fec_elim= now() WHERE id_usuario=?")
@Where(clause = "zk_eliminado=false")
public class Usuario extends AbstractModelZk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "zk_usuario_seq", allocationSize = 1)
	private Integer idUsuario;

	@NotEmpty(message = "Cuenta: no debe estar vacio")
	@Size(min = 3, max = 60, message = "Cuenta: cantidad de caracteres debe ser entre 3 y 60")
	private String cuenta;

	@NotEmpty(message = "Usuario: no debe estar vacio")
	@Size(min = 3, max = 60, message = "Usuario: cantidad de caracteres debe ser entre 3 y 60")
	private String usuario;

	@Size(min = 0, max = 60, message = "Alias: cantidad de caracteres debe ser entre 0 y 60")
	private String alias;

	@NotEmpty(message = "Nombre: no debe estar vacio")
	@Size(min = 1, max = 100, message = "Nombre: cantidad de caracteres debe ser entre 1 y 100")
	private String nombre;

	@NotEmpty(message = "Apellido: no debe estar vacio")
	@Size(min = 1, max = 100, message = "Apellido: cantidad de caracteres debe ser entre 1 y 100")
	private String apellido;

	@Email(message = "Correo: formato de email incorrecto")
	@Size(min = 0, max = 120, message = "Correo: cantidad de caracteres debe ser entre 0 y 120")
	private String correoPrincipal;

	@Size(min = 0, max = 30, message = "Teléfono: cantidad de caracteres debe ser entre 0 y 30")
	private String telefono;

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@Formula("TO_CHAR(fecha_nacimiento, 'dd/MM/yyyy')")
	private String fechaNacimientoMask;

	@NotNull(message = "Time Out Sesion: no debe ser nulo")
	private Integer timeOutSesion;

	@NotEmpty(message = "Contraseña: no debe estar vacio")
	@Size(min = 1, max = 200, message = "Contraseña: cantidad de caracteres debe ser entre 1 y 200")
	private String contrasenha;

	// valueList-> ACT:Activo;ANU:Anulado;LIM:Limitado;AUT:Autocancelado;INA:Inactivo;BLO:Bloqueado;SUS:Autosuspendido
	@NotEmpty(message = "Estado: no debe estar vacio")
	@Size(min = 0, max = 3, message = "Estado: cantidad de caracteres debe ser entre 0 y 3")
	private String estado;

	// valueList-> S:Si;N:No
	@NotEmpty(message = "Confirmar Correo: no debe estar vacio")
	@Size(min = 0, max = 1, message = "Confirmar Correo: cantidad de caracteres debe ser entre 0 y 1")
	private String confirmarCorreo;

	// valueList-> S:Si;N:No
	@NotEmpty(message = "Confirmar Teléfono: no debe estar vacio")
	@Size(min = 0, max = 1, message = "Confirmar Teléfono: cantidad de caracteres debe ser entre 0 y 1")
	private String confirmarTelefono;

	// valueList-> PSI:Por Sistema Web;PAP:Por Sistema App;AWE:Autoregistro Web;AAP:Autoregistro App
	@NotEmpty(message = "Tipo de Registro: no debe estar vacio")
	@Size(min = 0, max = 3, message = "Tipo de Registro: cantidad de caracteres debe ser entre 0 y 3")
	private String tipoRegistro;

	@NotNull(message = "Foto Avatar: no debe ser nulo")
	@Size(min = 2, max = 500, message = "Foto Avatar: cantidad de caracteres debe ser entre 2 y 500")
	private String avatar;

	@NotNull(message = "Imagen Portada: no debe ser nulo")
	@Size(min = 2, max = 500, message = "Imagen Portada: cantidad de caracteres debe ser entre 2 y 500")
	private String imagenPortada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHoraSuspension;

	@Formula("TO_CHAR(fecha_hora_suspension, 'dd/MM/yyyy HH24:MI:SS')")
	private String fechaHoraSuspensionMask;


	public Usuario(Integer id) {
		this.idUsuario = id;
	}

	public Usuario(String zkCuenta) {
		this.zkCuenta = zkCuenta;
	}

	public Usuario(String zkEmpresaCore, String zkCuenta) {
		this.zkEmpresaCore = zkEmpresaCore;
		this.zkCuenta = zkCuenta;
	}

	// KGC-NOREPLACE-OTROS-INI

	// @Enumerated(EnumType.STRING)
	// private UsuarioTipoRegistroType tipoRegistro;

	@Formula("NOMBRE ||' '|| APELLIDO")
	private String nombreApellido;

	@Formula("fn_concat_json_array_property(avatar,'uuidName',',','/zk/usuario/img/avatar/')")
	private String avatarUrl;

	@Formula("fn_concat_json_array_property(avatar,'uuidName',',','/zk/usuario/img/imagenPortada/')")
	private String imagenPortadaUrl;

	@Formula("( SELECT string_agg(lower(zk_rol.nombre),', ') "
			+ " FROM zk_usuario_rol INNER JOIN zk_rol "
			+ " ON zk_usuario_rol.id_rol = zk_rol.id_rol  "
			+ " WHERE zk_usuario_rol.id_usuario=id_usuario "
			+ " AND zk_usuario_rol.activo=true AND zk_rol.activo=true  LIMIT 1)")
	private String rolesNombres;

	@Formula("( SELECT string_agg(TO_CHAR(zk_rol.id_rol,'FM999999999'),',') "
			+ " FROM zk_usuario_rol INNER JOIN zk_rol "
			+ " ON zk_usuario_rol.id_rol = zk_rol.id_rol  "
			+ " WHERE zk_usuario_rol.id_usuario=id_usuario "
			+ " AND zk_usuario_rol.activo=true AND zk_rol.activo=true  LIMIT 1)")
	private String rolesId;

	@Transient
	private List<UsuarioRol> usuarioRolList;

	@Transient
	private Boolean solicitudOtpEmail;

	@Transient
	private String codigoOtpEmail;

	// KGC-NOREPLACE-OTROS-FIN

}
