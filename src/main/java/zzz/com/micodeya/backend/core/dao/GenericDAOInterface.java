package zzz.com.micodeya.backend.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface GenericDAOInterface<T, ID extends Serializable> {

	
	public T preGuardado(T entity, InfoAuditoria infoAuditoria);
	
	
	public int count(T entityExample, List<FilterAux> filterList); 
	
	public Map<String, Object> getAtributosUno(T entityExample, String atributos);
	public Map<String, Object> getAtributosUno(T entityExample, String atributos, PaginacionAux paginacionAux, boolean lineal );

	public PaginacionAux listarAtributosPorPagina(T entityExample, String atributos, PaginacionAux paginacionAux);

	public PaginacionAux listarAtributosPorPagina(T entityExample, String atributos, PaginacionAux paginacionAux, boolean lineal);
	
	 
	
}