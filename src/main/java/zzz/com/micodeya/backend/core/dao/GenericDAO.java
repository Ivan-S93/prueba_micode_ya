package zzz.com.micodeya.backend.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import zzz.com.micodeya.backend.core.entities.AbstractModelZk;
import zzz.com.micodeya.backend.core.exception.KwfCoreException;
import zzz.com.micodeya.backend.core.util.AtributoEntity;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.PaginacionAux;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

/**
 * Clase que implementa las operaciones CRUD y de listado básicos y complejos
 *
 * @author JuanLedesma
 *
 * @param <T>  tipo del objeto a administrar
 * @param <ID> tipo del atributo ID del objeto a administrar
 */
public abstract class GenericDAO<T, ID extends Serializable> implements GenericDAOInterface<T, ID> {


	@Autowired
	protected EntityManager em;

	protected String persistentUnit = "";
	protected String referenceId = "";
	protected String atributosDefault = "";

	protected String atributosExtras = "";
	protected Map<String, String> etiquetaAtributos=new HashMap<String, String>();

	public GenericDAO() {
	}

	protected abstract Class<T> getEntityBeanType();

	protected EntityManager getEm() {
	

		if (em == null) {
			throw new IllegalStateException("EntityManager no esta seteado");
		}
		return em;
	}





	/**
	 * @param infoAuditoria 
	 */
	public T preGuardado(T entity, InfoAuditoria infoAuditoria) {


		if(entity instanceof AbstractModelZk){
			Date fechaActual = new Date();

			
			try {
				Class<? extends Object> clase = entity.getClass();
				Method metodo;
				

				metodo = clase.getMethod("getZkUuid", new Class[] {});
				Object zkUuid=metodo.invoke(entity);

				
				if(zkUuid==null){//agregar
					metodo = clase.getMethod("setZkUsuarioCreacion", new Class[] { java.lang.String.class });
					metodo.invoke(entity, infoAuditoria.getUsuario());
		
					metodo = clase.getMethod("setZkFechaCreacion", new Class[] { java.util.Date.class });
					metodo.invoke(entity, fechaActual);
		
					metodo = clase.getMethod("setZkUltimaModificacion", new Class[] { java.util.Date.class });
					metodo.invoke(entity, fechaActual);
		
					metodo = clase.getMethod("setZkEmpresaCore", new Class[] { java.lang.String.class });
					// metodo.invoke(entity, infoAuditoria.getEmpresaCore());
					metodo.invoke(entity, "EMPKUAA");

					metodo = clase.getMethod("setZkCuenta", new Class[] { java.lang.String.class });
					metodo.invoke(entity, infoAuditoria.getCuenta());
		
					metodo = clase.getMethod("setZkUuid", new Class[] { java.lang.String.class });
					metodo.invoke(entity, UUID.randomUUID().toString());

					metodo = clase.getMethod("setZkEliminado", new Class[] { java.lang.Boolean.class });
					metodo.invoke(entity, false);



		
				}else{ //modificar

					metodo = clase.getMethod("setZkUsuarioModificacion", new Class[] { java.lang.String.class });
					metodo.invoke(entity, infoAuditoria.getUsuario());
		
					metodo = clase.getMethod("setZkUltimaModificacion", new Class[] { java.util.Date.class });
					metodo.invoke(entity, fechaActual);

				}

	
			
	
	
	
	
			} catch (NoSuchMethodException e) {
				// logger.fatal(e);
				e.printStackTrace();
			} catch (SecurityException e) {
				// logger.fatal(e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// logger.fatal(e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// logger.fatal(e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// logger.fatal(e);
				e.printStackTrace();
			}
			
		}


		return entity;

	} 

	@Transactional
	public Integer preEliminado(Integer id, InfoAuditoria infoAuditoria) {

		return id;
		// EliminadoIntencion eliminadoEntity= new EliminadoIntencion();
		// eliminadoEntity.setTabla(getEntityBeanType().getAnnotation(Table.class).name());
		// eliminadoEntity.setAtributoId(this.referenceId);
		// eliminadoEntity.setValorId(id);
		
		// Date fechaActual = new Date();

		// eliminadoEntity.setZkUsuarioCreacion(infoAuditoria.getUsuario()+"-"+infoAuditoria.getIdSesion());
		// eliminadoEntity.setZkFechaCreacion(fechaActual);
		// eliminadoEntity.setZkUltimaModificacion(fechaActual);
		// // eliminadoEntity.setZkEmpresaCoreCuenta(infoAuditoria.getEmpresaCoreCuenta());
		// eliminadoEntity.setZkUuid(UUID.randomUUID().toString());
		
		// this.getEm().persist(eliminadoEntity);
		
		// return id;
	
	}

	private String getWhere(T entityExample, List<FilterAux> filterAux, String alias){


		List<String> whereList=new LinkedList<String>();
	
		whereList.add(exampleToHqlWhere(entityExample, alias, false));
		  
		whereList.add(filterListToWhere(filterAux, alias));

		String where = whereList.stream()
		.filter(o -> o!=null && o.length()>3)//.map( o -> "("+o+")")
		.collect(Collectors.joining(" and "));

		//System.out.println("where ===============> \n" + where);

		return where;
	}

	private String filterListToWhere(List<FilterAux> filterAuxList, String alias){

		if (filterAuxList==null || filterAuxList.size()==0 ) return null;
		
		String whereActual="";
		String where="";

		for (FilterAux filterActual: filterAuxList) {

			if (filterActual.getData().equalsIgnoreCase("agrupar-operacion")) {
				if (whereActual.length() > 3)
					where += "(" + whereActual.replaceFirst(" and ", "").replaceFirst(" or ", "") + ") "
							+ filterActual.getOpUnion() + " ";
				whereActual = "";
				continue;
			} else {
				String groupActual = filterActual.getOpUnion() != null ? filterActual.getOpUnion() : "and";
				whereActual += " " + groupActual + " " + filterActual.atributoOpValueHql(alias);
			}
		}
		if (whereActual.length() > 3)
			where += "(" + whereActual.replaceFirst(" and ", "").replaceFirst(" or ", "") + ")";

		return where;
	}

	private static String exampleToHqlWhere(Object ejemplo, String alias, boolean caseSensitive) {

		List<String> whereList = new ArrayList<String>();

		String stringMask = caseSensitive ? "%s" : "lower(%s)";
		String stringMaskValue = caseSensitive ? "%s" : "lower('%s')";

		// String operadorComparacion = antiExample ? " <> " : " = ";
		String operadorComparacion = " = ";

		try {

			for (AtributoEntity atributoActual : JeBoot.getAtributosValor(ejemplo, true)) {

  				if (atributoActual.getTipoDato().equals(AtributoEntity.TipoDatoSencillo.INTEGER)
						|| atributoActual.getTipoDato().equals(AtributoEntity.TipoDatoSencillo.BIG_DECIMAL)) {
					whereList.add(alias + "." + atributoActual.getNombre() + operadorComparacion
							+ atributoActual.getValor().toString());
				} else if (atributoActual.getTipoDato().equals(AtributoEntity.TipoDatoSencillo.STRING)) {
					whereList.add(
							String.format(stringMask, alias + "." + atributoActual.getNombre()) + operadorComparacion
									+ String.format(stringMaskValue, atributoActual.getValor().toString()) + "");
				} else if (atributoActual.getTipoDato().equals(AtributoEntity.TipoDatoSencillo.BOOLEAN)) {
					whereList.add(alias + "." + atributoActual.getNombre() + operadorComparacion
							+ atributoActual.getValor().toString());
				} else {
					throw new RuntimeException(
							"TipoDatoSencillo [" + atributoActual.getTipoDato() + "] no encontrado para crear HQL");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "errorAlConvertirExampleHQL";
		}

		if (whereList.size() > 0) {
			return "("+String.join(" AND ", whereList)+")";
		}

		return null;

	}

	

	//contar todo
	private int countAll(T entityExample) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Class<? extends Object> clase = entityExample.getClass();
		Method metodo = clase.getMethod(JeBoot.getMetodo("zkEmpresaCore"), new Class[] {});
		Object valor = metodo.invoke(entityExample);


		String hql = String.format("SELECT count(*) FROM %s t WHERE t.zkEmpresaCore = %s",
				entityExample.getClass().getCanonicalName(), valor==null? null: valor.toString());

		Query query = this.getEm().createQuery(hql);
		Object singleResult = query.getSingleResult();
		Long result = (Long) singleResult;
		// System.out.println("hql count result===============> "+result);

		return result.intValue();

	}

	public int count(T entityExample, List<FilterAux> filterList) {

	
		String where=getWhere(entityExample, filterList, "t");

		String hql = String.format("SELECT count(*) FROM %s t %s", entityExample.getClass().getCanonicalName(),
				(where != null && !where.equals("") ? " WHERE " + where : ""));

		
		//System.out.println("hql count ===============> \n"+hql);
		
				
		Query query = this.getEm().createQuery(hql);

		if(filterList!=null && filterList.size()>0){
			List<FilterAux> filterInList = filterList.stream()
    			.filter(p -> p.getOp().equals("in")).collect(Collectors.toList());

			for(FilterAux filterActual: filterInList){
				query.setParameter(filterActual.getData().replace(".", "_")+"InList",
				 filterActual.getValueList());
			}
		}

		Object singleResult=query.getSingleResult();
		Long result=(Long) singleResult; 
		//System.out.println("hql count result===============> "+result);

		return result.intValue();
	}

	// ORDER BY f.name DESC, f.id ASC";
//	private String getOrderByDir(String orderBy, String orderDir) {
//		
//		String[] orderByAttrListArray;
//       	String[] orderByDirListArray;
//    	if(orderBy==null || orderDir==null){
//           	throw new RuntimeException("Campos de ordenamiento no pueden estar nulos"); 
//    	}else{
//    		orderByAttrListArray=orderBy.split(",");
//           	orderByDirListArray=orderDir.split(",");
//    	}
//    	
//    
//		return orderDir;
//    	
//    	
//    	
//	}

	private String prepararOrderBy(String orderBy, String alias) {
		return alias + "." + String.join(", " + alias + ".", orderBy.split(","));
	}

	/*
	 
	@return [atributosSelect, fromJoin]
	 
	 */
	private String[] prepararAtributosV2(String atributos, String alias) {

		if (atributos == null || atributos.trim().length() == 0)
			return new String[] {alias, ""};
		
		String atributosArray[] = atributos.split(",");
		String selectConAlias="";
		Map<String, String> selectObjMap=new HashMap<String, String>();

		for (int i = 0; i < atributosArray.length ; i++) {
			String atributoAtual = atributosArray[i];

			if(!atributoAtual.contains(".")){
				selectConAlias+=", "+alias+"."+atributoAtual+" as "+atributoAtual; 
			}else{
				String atributoAtualArray[]=atributoAtual.split("\\."); 

				if (atributoAtualArray.length == 2) {
					String atributoEntidad = atributoAtualArray[0];
					String atributoSecundario = atributoAtualArray[1];
					if (!selectObjMap.containsKey(atributoEntidad))
						selectObjMap.put(atributoEntidad, ", ");
					selectObjMap.put(atributoEntidad,
							selectObjMap.get(atributoEntidad) + ", " + atributoSecundario + " as o137" + atributoEntidad
									+ "_" + atributoSecundario);

				} else if (atributoAtualArray.length == 3) {
					String atributoEntidad = atributoAtualArray[0];
					String atributoSecundario = atributoAtualArray[1];
					String atributoTerciario = atributoAtualArray[2];

					if (!selectObjMap.containsKey(atributoEntidad))
						selectObjMap.put(atributoEntidad, ", ");
						
					selectObjMap.put(atributoEntidad,
							selectObjMap.get(atributoEntidad) + ", " + atributoSecundario + "." + atributoTerciario
									+ " as o137" + atributoEntidad + "_" + atributoSecundario + "g534"
									+ atributoTerciario);

				} else if (atributoAtualArray.length > 3) {
					String atributoEntidad = atributoAtualArray[0];
					String atributoSecundario = atributoAtualArray[1];
					String atributoTerciario = atributoAtualArray[2];
					String atributoCuaternario = atributoAtualArray[3];

					// si no existe, agregar
					if (!selectObjMap.containsKey(atributoEntidad))
						selectObjMap.put(atributoEntidad, ", ");

					//empresaServicio.servicio.rubro.nombre
					selectObjMap.put(atributoEntidad,
							selectObjMap.get(atributoEntidad) + ", " + atributoSecundario + "." + atributoTerciario + "."+atributoCuaternario
									+ " as o137" + atributoEntidad + "_" + atributoSecundario 
									+ "g534" + atributoTerciario
									+ "h387" + atributoCuaternario);
				}
				
			}
			
		}


		String fromJoin="";
		String aliasJoin[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s".split(",");
		int aliasCount=0;
		String selectOtrosConcat="";
		for (Map.Entry<String, String> entry : selectObjMap.entrySet())  {

			//left join t.rol as r
			fromJoin+= " left join "+alias+"."+entry.getKey()+" as  "+aliasJoin[aliasCount]+" ";
			//System.out.println("fromJoin= "+fromJoin);
			//, idRol, nombre
			//r.nombre as newObj_rol_nombre
			
			//aliasJoin[aliasCount]+"."+
			String selectOtros= entry.getValue().substring(2).replace(", ",", "+aliasJoin[aliasCount]+".").substring(2);
			selectOtrosConcat+=", "+selectOtros;
			//System.out.println("selectOtrosConcat= "+selectOtrosConcat);
			

			aliasCount++;
		}
		
		
		//System.out.println("selectConAlias===> "+ selectConAlias);
		 
		
		return new String[] {
			(selectConAlias + selectOtrosConcat).substring(1),
			 fromJoin
			};
		//return "t.nombre as nombre, t.apellido as apelido";
	}



	// https://stackoverflow.com/questions/55414407/cant-convert-tuple-list-to-hashmap-java
	public static Map<String, Object> jpaTupleToMap(jakarta.persistence.Tuple data, boolean lineal) {
		Map<String, Object> result = new HashMap<>(); // exactly HashMap since it can handle NULL keys & values
	
		if(lineal){
			data.getElements().forEach(col -> {
				if(!col.getAlias().startsWith("o137")){
					result.put(col.getAlias(), data.get(col));
				}else{

					
					result.put(
						col.getAlias().replace("o137", "").replace("_", ".").replace("g534", ".").replace("h387", "."),
						 data.get(col));

					//o137rol_nombre=OTRO ROL 1
					// int posicionGuion=col.getAlias().indexOf("_");
					// String keyObj=col.getAlias().substring(4,posicionGuion);
					
					// if(!result.containsKey(keyObj)) result.put(keyObj,new HashMap<String, Object>());
					
					// HashMap<String, Object> objRecuperado= (HashMap<String, Object>) result.get(keyObj);
					// objRecuperado.put(col.getAlias().substring(posicionGuion+1), data.get(col));
					
					// result.put(keyObj, objRecuperado);
				}
				
			
			});

			return result;

		}
	
		data.getElements().forEach(col -> {
			if(!col.getAlias().startsWith("o137")){
				result.put(col.getAlias(), data.get(col));
			}else{
				//o137rol_nombre=OTRO ROL 1
				String aliasColumna= col.getAlias();
				//System.out.println("aliasColumna=> " + aliasColumna);

				int posicionGuion=aliasColumna.indexOf("_");
				String keyObj=aliasColumna.substring(4,posicionGuion);
				
				if(!result.containsKey(keyObj)) result.put(keyObj,new HashMap<String, Object>());
				
				HashMap<String, Object> objRecuperado= (HashMap<String, Object>) result.get(keyObj);
				

				//tercer nivel
				int posicionTercerNivel=aliasColumna.indexOf("g534");
				//cuarto nivel
				int posicionCuartoNivel=aliasColumna.indexOf("h387");
				
				if(posicionTercerNivel>5){

					//rubro_rubroh387nombre
					if(posicionCuartoNivel>5){
						//servicio
						String keyObjTercer=aliasColumna.substring(posicionGuion+1,posicionTercerNivel);

						if(!objRecuperado.containsKey(keyObjTercer)) objRecuperado.put(keyObjTercer,new HashMap<String, Object>());
						HashMap<String, Object> objRecuperadoTercerNivel= (HashMap<String, Object>) objRecuperado.get(keyObjTercer);

						String keyObjCuarto=aliasColumna.substring(posicionTercerNivel+4,posicionCuartoNivel);
						if(!objRecuperadoTercerNivel.containsKey(keyObjCuarto)) objRecuperadoTercerNivel.put(keyObjCuarto,new HashMap<String, Object>());
						HashMap<String, Object> objRecuperadoCuartoNivel= (HashMap<String, Object>) objRecuperadoTercerNivel.get(keyObjCuarto);

						objRecuperadoCuartoNivel.put(aliasColumna.substring(posicionCuartoNivel+4), data.get(col));
						objRecuperadoTercerNivel.put(keyObjCuarto, objRecuperadoCuartoNivel);
						objRecuperado.put(keyObjTercer, objRecuperadoTercerNivel);

						// //"aliasColumna": "o137empresaServicio_serviciog534rubroh387nombre",
						// objRecuperado.put("aliasColumna", aliasColumna);
						// //"keyObjTercer": "servicio",
						// objRecuperado.put("keyObjTercer", keyObjTercer);
						// //"keyObjCuarto": "rubro"
						// objRecuperado.put("keyObjCuarto", keyObjCuarto);
						
					}else{

						String keyObjTercer=aliasColumna.substring(posicionGuion+1,posicionTercerNivel);
						//System.out.println("keyObjTercer=> " + keyObjTercer);

						if(!objRecuperado.containsKey(keyObjTercer)) objRecuperado.put(keyObjTercer,new HashMap<String, Object>());
						HashMap<String, Object> objRecuperadoTercerNivel= (HashMap<String, Object>) objRecuperado.get(keyObjTercer);
						objRecuperadoTercerNivel.put(aliasColumna.substring(posicionTercerNivel+4), data.get(col));


						objRecuperado.put(keyObjTercer, objRecuperadoTercerNivel);

					}



					


				}else{
					//segundo nivel
					objRecuperado.put(aliasColumna.substring(posicionGuion+1), data.get(col));
				}
				
				
				result.put(keyObj, objRecuperado);
			}
			
		
		});
		return result;
	}

	public static List<Object> jpaTuplesToListObject(List<Tuple> data) {
		return data.stream() // List<Tuple> -> Tuple1,..TupleN
				.map(tuple -> jpaTupleToMap(tuple, false)) // Tuple1 -> HashMap1,..TupleN -> HashMapN
				.collect(Collectors.toList()); // HashMap1,..HashMapN -> List
	}
	
	public static List<Map<String,Object>> jpaTuplesToMaps(List<Tuple> data, boolean lineal) {
		return data.stream() // List<Tuple> -> Tuple1,..TupleN
				.map(tuple -> jpaTupleToMap(tuple, lineal)) // Tuple1 -> HashMap1,..TupleN -> HashMapN
				.collect(Collectors.toList()); // HashMap1,..HashMapN -> List
	}

	
	public Map<String, Object>  getAtributosUno(T entityExample, String atributos) {
		 
		PaginacionAux paginacionAux= new PaginacionAux(this.referenceId+" asc");
		paginacionAux.setAll(false);
		paginacionAux.setPage(1);
		paginacionAux.setLimit(2);
 
		return getAtributosUno(entityExample, atributos, paginacionAux, false);
	}
	
	public Map<String, Object> getAtributosUno(T entityExample, String atributos, PaginacionAux paginacionAux, boolean lineal) {
		
		paginacionAux.setAll(false);
		paginacionAux.setPage(1);
		paginacionAux.setLimit(2);

		PaginacionAux result= listarAtributosPorPagina(entityExample, atributos, paginacionAux,lineal);

		if(result.getList()==null) return null;
		if(result.getList().size()>1){
			throw new KwfCoreException("Más de un elemento encontrado para entidad "+entityExample.getClass().getName() );
		}
		return result.getList().get(0);
	}
	
	public PaginacionAux listarAtributosPorPagina(T entityExample, String atributos, PaginacionAux paginacionAux) {
		return listarAtributosPorPagina(entityExample, atributos, paginacionAux,false);
	}



	/**
	 * @param lineal corresponde si las entidade referenciadas retornaran como objeto o en una sola linea
	 *				Ejemplo: si lineal=false --> { "id": 1, "nombre": "juan", "vehiculo": { "id": 12, "color": "rojo" } }
	 *				Ejemplo: si lineal=true --> { "id": 1, "nombre": "juan", "vehiculo.id": 12, "vehiculo.color": "rojo" }
	 * 
	*/
	
	public PaginacionAux listarAtributosPorPagina(T entityExample, String atributos, PaginacionAux paginacionAux, boolean lineal) {
 
		atributos=(atributos!=null && !atributos.equals(""))?atributos:
		atributosDefault+atributosExtras;
 
		if (paginacionAux.getOrderBy() == null)
			throw new KwfCoreException("Campo de ordenamiento no pueden estar nulo");

		if (paginacionAux.getLimit() == 0 || paginacionAux.isAll()) {
			//paginacionAux.setTotal(countAll(entityExample)); 
		}else{
			paginacionAux.setTotal(count(entityExample, paginacionAux.getFilterList())); 
		}

		//Original 
		// String hql = String.format("SELECT %s FROM %s t %s", prepararAtributos(atributos, "t"),
		// 		entityExample.getClass().getCanonicalName(),
		// 		"ORDER BY " + prepararOrderBy(paginacionAux.getOrderBy(), "t"));

		// String hql = String.format("SELECT %s, r.nombre as newObj_rol_nombre FROM %s t %s %s",
		// 		prepararAtributos(atributos, "t"), //select
		// 		entityExample.getClass().getCanonicalName(), //entidad principal
		// 		" left join t.rol as r ",//join
		// 		"ORDER BY " + prepararOrderBy(paginacionAux.getOrderBy(), "t")); //order by

		String atributosPreparadosArray[]=prepararAtributosV2(atributos, "t");

		String where = getWhere(entityExample, paginacionAux.getFilterList(), "t");
        
		//String whereFilter = getWhere(entityExample, null, "t");

		String hql = String.format("SELECT %s FROM %s t %s %s %s",
				atributosPreparadosArray[0], //select
				entityExample.getClass().getCanonicalName(), // from
				atributosPreparadosArray[1],//join
				(where != null && !where.equals("") ? " WHERE " + where : ""), //where
				"ORDER BY " + prepararOrderBy(paginacionAux.getOrderBy(), "t")); //order by

		 Query query = null;

		if (paginacionAux.getLimit() == 0 || paginacionAux.isAll()) { // trae todo
			query = em.createQuery(hql, Tuple.class);

		} else { // trae por pagina
			query = em.createQuery(hql, Tuple.class)
					.setFirstResult(calculateOffset(paginacionAux.getPage(), paginacionAux.getLimit()))
					.setMaxResults(paginacionAux.getLimit());

		}
		 
		 
		 


		
		if(paginacionAux.getFilterList()!=null && paginacionAux.getFilterList().size()>0){
			List<FilterAux> filterInList = paginacionAux.getFilterList().stream()
    			.filter(p -> p.getOp().equals("in")).collect(Collectors.toList());

			for(FilterAux filterActual: filterInList){
				query.setParameter(filterActual.getData().replace(".", "_")+"InList", filterActual.getValueList());
			}
		}

		
		List<Tuple> tuple = query.getResultList();

		// Map<String, Object> algoMap = tuple.stream().collect(Collectors.toMap(t ->
		// t.get(0, String.class), t -> t.get(1, Object.class)));

        if(paginacionAux.getLineal()==null){
            paginacionAux.setList(jpaTuplesToMaps(tuple, lineal));
        }else{
            paginacionAux.setList(jpaTuplesToMaps(tuple, paginacionAux.getLineal()));
        } 
		// paginacionAux.setListMap(jpaTuplesToMaps(tuple));

	
		paginacionAux.setFilterList(null);
		paginacionAux.setAtributos(null);

		
		List<Map<String, Object>> newList=new LinkedList<Map<String, Object>>();
		
		// 320 
		if(paginacionAux.getDetalles()!=null && paginacionAux.getDetalles().size()>0){
			String nombreIdEntidad="id"+entityExample.getClass().getSimpleName();
			 
			int cantidadEnListado=paginacionAux.getList().size();

			if(cantidadEnListado>0 && !paginacionAux.getList().get(0).containsKey(nombreIdEntidad)){
				throw new RuntimeException("Error al listar detalle, no se encuentra el atributo nexo: "+nombreIdEntidad);
			}

			for(int iList=0;iList<cantidadEnListado;iList++){
			//for(Map<String,Object> actual:paginacionAux.getList()){

				Integer idEntidad=Integer.parseInt(paginacionAux.getList().get(iList).get(nombreIdEntidad).toString());


				for( String detalleActual:paginacionAux.getDetalles()){
					//papaList:hijoAtributo:atributo1,atributo2,atributo3
					String [] detalleActualArray=detalleActual.split(":");
					String nombreAtributoList=detalleActualArray[0];
					String nombreAtributoHijo=detalleActualArray[1];
					String atributosDetalle=detalleActualArray[2];

					String orderByDetalle=null;
					if(detalleActualArray.length==4){
						orderByDetalle=detalleActualArray[3];
					}
	
					try {
						String entityDetalle=JeBoot.getListType(entityExample,nombreAtributoList);
						String atributosDetallePreparapdosArray[]=prepararAtributosV2(atributosDetalle, "t");
	
	
						
						String hqlDetalle = String.format("SELECT %s FROM %s t %s %s %s",
						atributosDetallePreparapdosArray[0], //select
						entityDetalle, // from
						atributosDetallePreparapdosArray[1],//join
						"WHERE t."+nombreAtributoHijo+"."+nombreIdEntidad+" = "+idEntidad, //"WHERE t."+nombreAtributoHijo+".idUsuario", //where
						(orderByDetalle==null? "": "ORDER BY " + prepararOrderBy(orderByDetalle, "t")) 
						); //order by
	
						Query queryDetalle = null;
						queryDetalle = em.createQuery(hqlDetalle, Tuple.class);// trae todo
			
						List<Tuple> tupleDetalle = queryDetalle.getResultList();
						List<Map<String,Object>> listDetalle=jpaTuplesToMaps(tupleDetalle, lineal);
	
						//System.out.println("listDetalleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee ");
						//System.out.println(listDetalle);
						paginacionAux.getList().get(iList).put(nombreAtributoList, listDetalle);
						//paginacionAux.getList().add(iList, paginacionAux.getList().get(iList));
	
	
					} catch (Exception e) {
						throw new RuntimeException("Error al listar detalle de "+detalleActual,e);
					} 
	
					
	
				}// end for detalleList
				
			}//end for list principal
			
			
		}
  

		
/*

		for(Map<String, Object> mapActual: paginacionAux.getList()){
			//System.out.println(entityExample.getClass().getSimpleName());

			String atributoName="id"+entityExample.getClass().getSimpleName();
			String tipoDato=entityExample.getClass().getSimpleName();
			Integer idEntidad= Integer.parseInt(mapActual.get(atributoName).toString());
			System.out.println("idEntidad= "+idEntidad);
			
			//Class<? extends Object> clase = obj.getClass();
			try {
				Object objCabecera = Class.forName(entityExample.getClass().getName()).getConstructor().newInstance();
				objCabecera.getClass().getMethod(JeBoot.setMetodo(atributoName), new Class[] { java.lang.Integer.class }).invoke(objCabecera, idEntidad);
				
				System.out.println("objCabecera= "+objCabecera);


				Object objTemp = Class.forName("zzz.com.micodeya.backend.core.model.zk.RolRecurso").getConstructor().newInstance();
				

				Class<? extends Object> clase = objTemp.getClass();

				Method metodo = clase.getMethod(JeBoot.setMetodo(tipoDato), new Class[] { objCabecera.getClass() });
				metodo.invoke(objTemp, objCabecera);

				System.out.println("objTemp= "+objTemp);
				//clase = objTemp.getClass();
				
				//Class clazz = Class.forName(whichClass);
				//return (SayHelloInterface) clazz.newInstance();


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
						
		
		}

		 */


		return paginacionAux;
		

	}



	public List<T> listar(T entityExample, String orderBy) {
		return listar(entityExample, null, orderBy);
	}

	public List<T> listar(T entityExample, T entityAntiExample, String orderBy) {

		String hql = String.format("SELECT %s FROM %s t %s", "t",
				entityExample.getClass().getCanonicalName(),
				 (orderBy!=null? "ORDER BY "+prepararOrderBy(orderBy, "t") :""));
 
	
			Query query = em.createQuery(hql);
 

 		return query.getResultList();
	} 

	/*
	 * CRITERIA //List<Map<String,Object>> result =
	 * sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	 * 
	 * 
	 * //create a Criteria Builder CriteriaBuilder builder =
	 * getEm().getCriteriaBuilder(); // create a CriteriaQuery which returns student
	 * Objects CriteriaQuery<Persona> cq = builder.createQuery(Persona.class); //
	 * fetch the Student Entity Root<Persona> student = cq.from(Persona.class);
	 * Path<String> namePath = student.get("nombre"); // select the Student entity
	 * //cq.select(student); //cq.multiselect(namePath); // Root<Persona> root =
	 * cq.from(Persona.class); cq.multiselect(student.get("idPersona")); // add a
	 * restriction to fetch only student with ID= 1003
	 * //cq.where(builder.equal(student.get("id"), 10003L)); // fetch the student
	 * result //Persona studentResult = getEm().createQuery(cq).getSingleResult();
	 * List<Persona> studentResult = getEm().createQuery(cq).getResultList();
	 * 
	 */

	/**
	 * @param page
	 * @return
	 */
	private int calculateOffset(int page, int limit) {
		return ((limit * page) - limit);
	}

}
