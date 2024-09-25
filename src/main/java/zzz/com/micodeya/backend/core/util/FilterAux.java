 package zzz.com.micodeya.backend.core.util;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@Setter
@Getter
@ToString
public class FilterAux {
	
	private String data;  
	private String op;
	private Object value;
	private String opUnion;
	private List<Object> valueList;  
  
	

	//"all": false, "page":1, "limit": 10, "orderBy": "nombre asc" 
	public FilterAux(JSONObject filtroJson) throws JSONException{
		
		this.data=filtroJson.getString("data");
		this.op=filtroJson.getString("op"); 
		
		if(filtroJson.has("value")){
			this.value=filtroJson.get("value");
		}

		// if(filtroJson.has("valueList")){
		// 	this.valor=filtroJson.get("value");
		// }
		
		

	}

	public FilterAux(String data, String op, Object value, String opUnion){
		
		this.data=data;
		this.op=op; 
		this.value=value;
		this.opUnion=opUnion;
	}

	public FilterAux(String data, String op, List<Object> valueList){
		
		this.data=data;
		this.op=op; 
		this.valueList=valueList;
	}


	//@Formula("TO_CHAR(zk_fec_crea, 'dd/MM/yyyy HH24:MI:SS')")
	private String getPostgresToDate(){
		String fechaString=this.value.toString();

		if(fechaString.length()==10){ // fecha 16/04/1999
			return "to_date('"+fechaString+"','dd/MM/yyyy')";
		}else if(fechaString.length()==16){ // fechahhmm 16/04/1999 23:03
			return "to_timestamp('"+fechaString+"','dd/MM/yyyy HH24:MI')";
		}else if(fechaString.length()==19){ // fechahhmm 16/04/1999 23:03:00
			return "to_timestamp('"+fechaString+"','dd/MM/yyyy HH24:MI:SS')";
		}else if(fechaString.length()==5){ // hhmm 23:03
			return "to_timestamp('"+fechaString+"','HH24:MI')";
		}else if(fechaString.length()==8){ // hhmm 23:03:05
			return "to_timestamp('"+fechaString+"','HH24:MI:SS')";
		}


		return "FORMATO_TO_DATE_NO_ENCONTRADO";

	}

	private String getMariaDbToDate(){
		String fechaString=this.value.toString();

		if(fechaString.length()==10){ // fecha 16/04/1999
			return "STR_TO_DATE('"+fechaString+"','%d/%m/%Y')";
		}else if(fechaString.length()==16){ // fechahhmm 16/04/1999 23:03
			return "STR_TO_DATE('"+fechaString+"','%d/%m/%Y %H:%i')";
		}else if(fechaString.length()==19){ // fechahhmm 16/04/1999 23:03:00
			return "STR_TO_DATE('"+fechaString+"','%d/%m/%Y %H:%i:%S')";
		}else if(fechaString.length()==5){ // hhmm 23:03
			return "STR_TO_DATE('"+fechaString+"','%H:%i')";
		}else if(fechaString.length()==8){ // hhmm 23:03:05
			return "STR_TO_DATE('"+fechaString+"','%H:%i:%S')";
		}


		return "FORMATO_TO_DATE_NO_ENCONTRADO";

	}
	
	public String atributoOpValueHql(String alias ){
		
		String maskAtributo="%s";
		String maskValor="%s";
		String dataFinal=this.data;
		Object valorFinal=this.value;

		if(this.value!= null && this.value.getClass().getName().equalsIgnoreCase("java.lang.String")){
			maskAtributo="lower(%s)";
			maskValor="lower('%s')";
		}

		if(this.data.endsWith("Mask")){
			dataFinal=this.data.substring(0, this.data.length() - 4);
			maskValor="%s";
			maskAtributo="%s";
			valorFinal=getPostgresToDate();
		}

		if(this.op.equals("eq")){ //igual
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"=",
				valorFinal);
		}else if(this.op.equals("ne")){ //no igual
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"!=",
				valorFinal);
		}else if(this.op.equals("cn")){ //contiene 
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"like",
				"%"+valorFinal+"%");
		}else if(this.op.equals("nc")){ //no contiene 
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"not like",
				"%"+valorFinal+"%");
		}else if(this.op.equals("nu")){ //isnull
			return String.format(
				maskAtributo+" %s ",
				alias+"."+dataFinal,
				"IS NULL");
		}else if(this.op.equals("nn")){ //not null
			return String.format(
				maskAtributo+" %s ",
				alias+"."+dataFinal,
				"IS NOT NULL");
		}else if(this.op.equals("lt")){ //menor
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"<",
				valorFinal);
		}else if(this.op.equals("le")){ //menor o igual
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"<=",
				valorFinal);
		}else if(this.op.equals("gt")){ //mayor
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				">",
				valorFinal);
		}else if(this.op.equals("ge")){ //mayot o igual
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				">=",
				valorFinal);
		}else if(this.op.equals("bw")){ //comienza con
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"like",
				valorFinal+"%");
		}else if(this.op.equals("ew")){ //termina con
			return String.format(
				maskAtributo+" %s "+maskValor,
				alias+"."+dataFinal,
				"like",
				"%"+valorFinal);
		}else if(this.op.equals("in")){ //IN

			if(this.valueList.get(0).getClass().getName().equalsIgnoreCase("java.lang.String")){
				maskAtributo="%s";
				maskValor="(:%sInList)";
			}else if(this.valueList.get(0).getClass().getName().equalsIgnoreCase("java.lang.Integer")){
				maskAtributo="%s";
				maskValor="(:%sInList)";
			}
			
			return String.format(
				maskAtributo+" %s "+ maskValor,
				alias+"."+dataFinal,
				"in",
				dataFinal.replace(".", "_"));
		}

		return "OPERADOR_SQL_["+this.op+"]_NO_ENCONTRADO";
	}
	

	/*
							//no comienza con
							if(operacion.equals("bn")){
								filter = Filter.not(Filter.ilike(atributo, valorObject+"%"));
								tieneFiltro=true;
							}else
							//in
							if(operacion.equals("in")){
								JSONArray jsonArray=new JSONArray(regla.getString("data"));
								
								filter = Filter.in(atributo,jsonArray);
								tieneFiltro=true;
							}else
							//Not in
							if(operacion.equals("ni")){
								filter = Filter.notIn(atributo, valorObject);
								tieneFiltro=true;
							}else 
							//no termina con
							if(operacion.equals("en")){
								filter = Filter.not(Filter.ilike(atributo, "%"+valorObject));
								tieneFiltro=true;
							}else 
							//Not contiene
							if(operacion.equals("nc")){
								filter = Filter.not(Filter.ilike(atributo, "%"+valorObject+"%"));
								tieneFiltro=true;
							}else { //es igual por defecto
								filter = Filter.equal(atributo, valorObject);
								tieneFiltro=true;
							}
						}
						
						
						//null
						if(operacion.equals("nu")){
							filter = Filter.isNull(atributo);
							tieneFiltro=true;
						}
						
						//Not null
						if(operacion.equals("nn")){
							filter = Filter.isNotNull(atributo);
							tieneFiltro=true;
						}



//NO PROSPERO
						else if(this.op.equals("in")){ //IN
			//para postgres
			//https://www.postgresql.org/docs/8.2/functions-subquery.html#AEN13969
			//https://stackoverflow.com/questions/34627026/in-vs-any-operator-in-postgresql
			//JSONArray jsonArray=new JSONArray(regla.getString("data"));

			boolean esString=false;
			if(this.valueList.get(0).getClass().getName().equalsIgnoreCase("java.lang.String")){
				maskAtributo="lower(%s)";
				esString=true;
			}

			String array= this.valueList.stream().map(o -> {
				
				// if(esString){
				// 	return "lower('"+o+"')";
				// }

					if(o.getClass().getName().equalsIgnoreCase("java.lang.String")){
						return "lower('"+o+"')";
					}
					return o.toString();
				}).collect(Collectors.joining(","));

			return String.format(
				maskAtributo+" %s "+" any (array[%s])", //maskValor, //no usar el mask
				alias+"."+dataFinal,
				"=",
				array
				);

		}

						

	 */
	
	

}
