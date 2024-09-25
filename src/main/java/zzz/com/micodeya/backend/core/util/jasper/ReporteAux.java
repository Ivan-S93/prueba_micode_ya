package zzz.com.micodeya.backend.core.util.jasper;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReporteAux {
	
	private String identificador;
	private String archivoJrxml;
	private String titulo;
	private String nombreDescarga;
	private String motor;  
	
	private String salida;
	private String salidaEspecifica;
	
	private Map<String, Object> parametroMap;

	private String formato;
	private DataSourceType dataSource;
	
	
	private String jsonDataSource;
	private Connection conexion;
	private Date date;
	
	
	
	public ReporteAux(){
		 
		this.motor="ireport";
		this.identificador = UUID.randomUUID().toString();
		this.parametroMap=new HashMap<String, Object>();
		this.date=new Date();
		 
	}
	
	public ReporteAux(String file, String titulo,
			Map<String, Object> parametroMap, String formato,
			String jsonDataSource, Connection conexion) {
		
		this.motor="ireport";
		this.identificador = UUID.randomUUID().toString();
		this.archivoJrxml = file;
		this.titulo = titulo;
		this.parametroMap = parametroMap;
		this.formato = formato;
		this.jsonDataSource = jsonDataSource;
		this.conexion = conexion;
		this.date=new Date();
	}
	
	public String getArchivoJrxml() {
		// return archivo+(this.formato.equals("pdf")?"":"_k"+this.formato);
		return this.archivoJrxml;
	}
	public void setArchivoJrxml(String archivo) {
		this.archivoJrxml = archivo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String nombre) {
		this.titulo = nombre;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getJsonDataSource() {
		return jsonDataSource;
	}
	public void setJsonDataSource(String jsonDataSource) {
		this.jsonDataSource = jsonDataSource;
	}
	public Connection getConexion() {
		return conexion;
	}
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	public Map<String, Object> getParametroMap() {
		return parametroMap;
	}
	public void setParametroMap(Map<String, Object> parametroMap) {
		this.parametroMap = parametroMap;
	}
	
	public void addParametro(String clave, Object valor) {
		this.parametroMap.put(clave, valor);
	}

	public String getIdentificador() {
		return identificador;
	}

	public DataSourceType getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSourceType dataSource) {
		this.dataSource = dataSource;
	}

	public Date getDate() {
		return date;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombreDescarga() {
		return nombreDescarga;
	}

	public void setNombreDescarga(String nombreDescarga) {
		this.nombreDescarga = nombreDescarga;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getSalidaEspecifica() {
		return salidaEspecifica;
	}

	public void setSalidaEspecifica(String salidaEspecifica) {
		this.salidaEspecifica = salidaEspecifica;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}
	 
	
	
	public static enum DataSourceType {
		JSON, 
		HQL, 
		SQL
	}
	

}

