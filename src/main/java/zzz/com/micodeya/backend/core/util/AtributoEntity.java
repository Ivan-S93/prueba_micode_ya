package zzz.com.micodeya.backend.core.util;

import lombok.ToString;

@ToString
public class AtributoEntity {

	private String nombre;
	private String tipoDato;
	private boolean esId;
	private Object valor;
	
	public AtributoEntity(String nombre, String tipoDato, boolean esId, Object valor) {
		super();
		this.nombre = nombre;
		this.tipoDato = tipoDato;
		this.esId = esId;
		this.valor = valor;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public boolean isEsId() {
		return esId;
	}
	public void setEsId(boolean esId) {
		this.esId = esId;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
	
	
	public static final class TipoDatoSencillo {
	    public static final String INTEGER="integer";
	    public static final String STRING="string";
	    public static final String BIG_DECIMAL="bigdecimal";
		public static final String BOOLEAN="boolean";
	    public static final String NO_ENCONTRADO="tipoDatoSencilloNoEncontrado";
	    //public static final String LIST="list";
	    
	}
	
}


