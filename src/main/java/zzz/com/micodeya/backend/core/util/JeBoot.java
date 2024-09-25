package zzz.com.micodeya.backend.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.exception.KwfCoreException;
import zzz.com.micodeya.backend.core.exception.ValidacionException;

@Slf4j
public class JeBoot {

	public static String esExcepcionKwf(Exception e) {
		String retorno = "";
		String mensaje = e.getMessage();
		String iniciaCon = "kwfException";
		if (mensaje != null && mensaje.trim().startsWith(iniciaCon)) {
			if (mensaje.trim().length() > iniciaCon.length() + 2) {
				retorno = "\n[" + mensaje.trim().substring(iniciaCon.length()).trim() + "]";

			}
		}
		return retorno;
	}


	public static List<Object> toObjectListFromString(List<String> list){
		if(list==null) return null;
		return list.stream().map(o-> (Object)o).collect(Collectors.toList());
	}

	public static List<Object> toObjectListFromInteger(List<Integer> list){
		return list.stream().map(o-> (Object)o).collect(Collectors.toList());
	}

	public static String getTipoDatoSencillo(String tipoDato) {

		if (tipoDato.compareTo("java.lang.Integer") == 0) {
			return AtributoEntity.TipoDatoSencillo.INTEGER;

		}

		if (tipoDato.compareTo("java.lang.String") == 0) {
			return AtributoEntity.TipoDatoSencillo.STRING;
		}

		if (tipoDato.compareTo("java.math.BigDecimal") == 0) {
			return AtributoEntity.TipoDatoSencillo.BIG_DECIMAL;
		}

		if (tipoDato.compareTo("java.lang.Boolean") == 0) {
			return AtributoEntity.TipoDatoSencillo.BOOLEAN;
		}

		return AtributoEntity.TipoDatoSencillo.NO_ENCONTRADO;
	}

	public static void getTipoDatoCustom(Object obj, String atributo, Object valor) throws NoSuchFieldException, SecurityException {

		Class<?> actualClass = obj.getClass();
		String className = obj.getClass().getName();
		 //System.out.println("className= "+className);
		// System.out.println("atributo= "+atributo);

		Field field = actualClass.getDeclaredField(atributo);
		//System.out.println("field= "+field);
		//System.out.println("fieldType= "+field.getType());


		if(valor!=null){
			throw new KwfCoreException("Tipo de dato custom no implementado, atributo: "+atributo+" ############ ");
		}

	}

	public static List<AtributoEntity> getAtributosValor(Object obj, boolean soloNoNulos)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		List<AtributoEntity> atributEntityList = new LinkedList<AtributoEntity>();

		Class<? extends Object> clase = obj.getClass();
		Method metodo;

		// Se obtiene los atributos de la clase
		for (Field field : obj.getClass().getDeclaredFields()) {
			String nombreAtributo = field.getName();
			String tipoDatoName = JeBoot.getTipoDatoSencillo(field.getType().getName());
			// String tipoDatoNameCanno=field.getType().getCanonicalName();
			boolean esId = false;

			if (nombreAtributo.equalsIgnoreCase("serialVersionUID"))
				continue;

			if (!tipoDatoName.equals(AtributoEntity.TipoDatoSencillo.NO_ENCONTRADO)) {
				for (Annotation anotacion : field.getAnnotations()) {
					if (anotacion.toString().contains("jakarta.persistence.Id")) {
						esId = true;
					}
				}

				metodo = clase.getMethod(JeBoot.getMetodo(nombreAtributo), new Class[] {});
				Object valor = metodo.invoke(obj);

				if (!soloNoNulos) {
					atributEntityList.add(new AtributoEntity(nombreAtributo, tipoDatoName, esId, valor));
				} else if (soloNoNulos && valor != null) {
					atributEntityList.add(new AtributoEntity(nombreAtributo, tipoDatoName, esId, valor));
				}

			} else {
				

				metodo = clase.getMethod(JeBoot.getMetodo(nombreAtributo), new Class[] {});
				Object valor = metodo.invoke(obj);

				
				getTipoDatoCustom(obj, nombreAtributo, valor);
				
				

			}

		}

		// Se obtiene los atributos de la superclase
		for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
			String tipoDatoName = JeBoot.getTipoDatoSencillo(field.getType().getName());
			// String tipoDatoNameCanno=field.getType().getCanonicalName();
			String nombreAtributo = field.getName();
			boolean esId = false;

			if (nombreAtributo.equalsIgnoreCase("serialVersionUID"))
				continue;

			for (Annotation anotacion : field.getAnnotations()) {
				if (anotacion.toString().contains("jakarta.persistence.Id")) {
					esId = true;
				}
			}

			metodo = clase.getMethod(JeBoot.getMetodo(nombreAtributo), new Class[] {});
			Object valor = metodo.invoke(obj);

			if (!soloNoNulos) {
				atributEntityList.add(new AtributoEntity(nombreAtributo, tipoDatoName, esId, valor));
			} else if (soloNoNulos && valor != null) {
				atributEntityList.add(new AtributoEntity(nombreAtributo, tipoDatoName, esId, valor));
			}

		}

		return atributEntityList;
	}

	public static boolean verificarCuentaNula(String cuentaCore, boolean conException){
		if(conException){
			if(cuentaCore==null){
				throw new ValidacionException("Cuenta ingresada nula");
			}
		}
		return cuentaCore!=null;
	}

	public static String getTipoDato(Object obj, String atributo) { // cambiar

		String tipoDato = "";
		String[] atributoArray = atributo.split("\\.");

		try {

			Class<? extends Object> clase = obj.getClass();

			for (int i = 0; i < atributoArray.length; i++) {

				Field field = null;
				try {
					field = clase.getDeclaredField(atributoArray[i]);
				} catch (NoSuchFieldException e) {
					field = clase.getSuperclass().getDeclaredField(atributo);
				}
				tipoDato = field.getType().getCanonicalName().toString();

				if (tipoDato.compareTo("java.util.Date") == 0) {
					for (Annotation anotacion : field.getAnnotations()) {

						/*
						 * if(buscar(anotacion.toString(), "jakarta.persistence.Temporal", false)){
						 * String[] anotacionArray=anotacion.toString().split("=");
						 * if(anotacionArray.length==2){ String resultadoAnotacion=anotacionArray[1];
						 * resultadoAnotacion=resultadoAnotacion.replace(")", "");
						 * resultadoAnotacion=resultadoAnotacion.replace(" ", "");
						 * resultadoAnotacion.trim();
						 * 
						 * if(resultadoAnotacion.compareTo("DATE")==0){ tipoDato="date"; }
						 * if(resultadoAnotacion.compareTo("TIME")==0){ tipoDato="time"; }
						 * 
						 * if(resultadoAnotacion.compareTo("TIMESTAMP")==0){ tipoDato="datetime"; }
						 * 
						 * }
						 * 
						 * 
						 * }
						 */
					}

				} else {
					if (!tipoSimple(tipoDato)) {
						Object objTemp = Class.forName(tipoDato).getConstructor().newInstance();
						clase = objTemp.getClass();
					} else {
						if (tipoDato.compareTo("java.lang.Integer") == 0) {
							tipoDato = "integer";
						}

						if (tipoDato.compareTo("java.lang.String") == 0) {
							tipoDato = "string";
						}

						if (tipoDato.compareTo("java.math.BigDecimal") == 0) {
							tipoDato = "bigdecimal";
						}

					}
				}

			}

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		}

		return tipoDato;
	}

	public static boolean tipoSimple(String tipo) { // cambiar
		if (tipo.equals("java.lang.Integer") || tipo.equals("java.lang.String") || tipo.equals("java.math.BigDecimal")
				|| tipo.equals("java.util.Date") || tipo.equals("java.sql.Date") || tipo.equals("java.sql.Time")
				|| tipo.equals("java.sql.Timestamp") || tipo.equals("[B")) {
			return true;
		}

		return false;
	}

	public static String eliminarPrimeroUltimo(String palabra) {
		if (palabra != null) {
			return palabra.substring(1, palabra.length() - 1);
		}
		return null;
	}

	public static String getPrimeroMayuscula(String palabra) {
		if (palabra != null) {
			String primeraLetraMayusucula = String.valueOf(palabra.charAt(0)).toUpperCase();
			String restoNombre = palabra.substring(1);
			return (primeraLetraMayusucula + restoNombre);
		}
		return null;
	}

	public static String getPrimeroMinuscula(String palabra) {
		if (palabra != null) {
			String primeraLetraMayusucula = String.valueOf(palabra.charAt(0)).toLowerCase();
			String restoNombre = palabra.substring(1);
			return (primeraLetraMayusucula + restoNombre);
		}
		return null;
	}

	public static String espacioToCamelCase(String nombre) {

		nombre = nombre.toLowerCase();

		String retorno = null;
		String[] nombreArray = nombre.split(" ");

		retorno = nombreArray[0];

		if (nombreArray.length > 1) {
			for (int i = 1; i < nombreArray.length; i++) {
				retorno += nombreArray[i].substring(0, 1).toUpperCase() + nombreArray[i].substring(1).toLowerCase();
			}
		}

		return retorno;

	}

	public static String guionBajoToCamelCase(String nombre) {

		nombre = nombre.toLowerCase();

		String retorno = null;
		String[] nombreArray = nombre.split("_");

		retorno = nombreArray[0];

		if (nombreArray.length > 1) {
			for (int i = 1; i < nombreArray.length; i++) {
				retorno += nombreArray[i].substring(0, 1).toUpperCase() + nombreArray[i].substring(1).toLowerCase();
			}
		}

		return retorno;

	}
	public static String guionBajoToCapital(String nombre) {

		nombre = nombre.toLowerCase();

		String retorno = null;
		String[] nombreArray = nombre.split("_");

		retorno = nombreArray[0];

		if (nombreArray.length > 1) {
			for (int i = 1; i < nombreArray.length; i++) {
				retorno += " "+nombreArray[i].substring(0, 1).toUpperCase() + nombreArray[i].substring(1).toLowerCase();
			}
		}

		return getPrimeroMayuscula(retorno);

	}

	public static String getMetodo(String atributo) {

		if (atributo != null) {
			String primeraLetraMayusucula = String.valueOf(atributo.charAt(0)).toUpperCase();
			String restoNombre = atributo.substring(1);

			return ("get" + primeraLetraMayusucula + restoNombre);

		}

		return null;
	}

	public static String setMetodo(String atributo) {

		if (atributo != null) {
			String primeraLetraMayusucula = String.valueOf(atributo.charAt(0)).toUpperCase();
			String restoNombre = atributo.substring(1);

			return ("set" + primeraLetraMayusucula + restoNombre);

		}

		return null;
	}

	public static String getHttpRequestInfo(HttpServletRequest request) {

		JSONObject requestJsonObject = new JSONObject();
		try {
			requestJsonObject.put("ipAddress", request.getRemoteAddr());
			requestJsonObject.put("xForwardedFor", request.getHeader("x-forwarded-for"));
			requestJsonObject.put("serverName", request.getServerName());
			requestJsonObject.put("requestURI", request.getRequestURI());
			requestJsonObject.put("referer", request.getHeader("referer"));
			requestJsonObject.put("origin", request.getHeader("origin"));
			requestJsonObject.put("userAgent", request.getHeader("User-Agent"));
			String sistemaOperativoMovil = request.getHeader("xrn-so") == null ? "NN"
			: request.getHeader("xrn-so").toString();
			requestJsonObject.put("xrnSo", sistemaOperativoMovil);
			
			String sistemaOperativoMovilVersion = request.getHeader("xrn-version") == null ? "NN"
			: request.getHeader("xrn-version").toString();
			requestJsonObject.put("xrnVersion", sistemaOperativoMovilVersion);
			


		} catch (Exception e) {
			log.error("Error al recuperar datos de HttpServletRequest", e);
		}

		return requestJsonObject.toString();
	}

	// dd/MM/yyyy HH:mm:ss:SSS
	public static Date getDateFromString(String fechaString, String format) {
		try {
			if (fechaString == null)
				return null;
			if (fechaString.trim().equals(""))
				return null;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			return sdf.parse(fechaString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new KwfCoreException("Error en conversion de fecha formato: [" + format + "] valor: " + fechaString);
		}
	}
	// dd/MM/yyyy HH:mm:ss:SSS
	public static String getStringFromDate(Date fechaDate, String format) {
		try {
			if (fechaDate == null)
				return null;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);

			return sdf.format(fechaDate);

		} catch (Exception e) {
			e.printStackTrace();
			throw new KwfCoreException("Error en conversion de fecha formato: [" + format + "] valor: " + fechaDate);
		}
	}

	public static Date getFecha(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy");
	}

	public static Date getFechaHHMM(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy HH:mm");
	}

	public static Date getFechaHHMMSS(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy HH:mm:ss");
	}

	public static String getFechaString(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy");
	}

	public static String getFechaStringHHMM(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy HH:mm");
	}

	public static String getFechaStringHHMMSS(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy HH:mm:ss");
	}

	public static String getHoraStringHHMMSS(Date fechaDate) {
		return getStringFromDate(fechaDate, "HH:mm:ss");
	}

	public static Date getHoraHHMMSS(String horaString) {
		return getDateFromString(horaString, "HH:mm:ss");
	}
	
	public static Date getHoraHHMM(String horaString) {
		return getDateFromString(horaString, "HH:mm");
	}

	public static <E> E nvl(E expr1, E expr2) {
        return (null != expr1) ? expr1 : expr2;
    }

	public static  String getListType(Object obj, String atributo) throws NoSuchFieldException, SecurityException{

        Class<? extends Object> testClass = obj.getClass();

        Field listField = testClass.getDeclaredField(atributo);
        ParameterizedType listType = (ParameterizedType) listField.getGenericType();
        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
        //System.out.println(listClass); 

        return listClass.getCanonicalName();
 

    }

	public static FileInputStream recuperarArchivoFileSystem(String directorio, String nombreArchivo)
			throws FileNotFoundException {

		File file = new File(
				directorio.replace("/", File.separator) + File.separator + nombreArchivo);

		if (!file.exists())
			return null;

		if (file.isDirectory())
			return null;

		FileInputStream fileInputStream = new FileInputStream(file);

		return fileInputStream;

	}

	public static String getResourceMimeType(HttpServletRequest request, Resource resource){
		
		 String contentType = null;
                
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			if(contentType==null) contentType="application/octet-stream";
		} catch (IOException ex) {
			log.info("Could not determine file type of: "+(resource.exists()?resource.getFilename():""));
		}

		return contentType;
	}

	public static Integer getAnhoDeFecha(Date date){
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    return cal.get(Calendar.YEAR);
	   /* int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DAY_OF_MONTH);*/
		
	}

	public static String nombreMes(Integer mes) {
		switch (mes) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3:
			return "Marzo";
		case 4:
			return "Abril";
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";
		case 9:
			return "Setiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";
		}
		return "";

	}
	
	public static String nombreDia(Integer dia) {
		switch (dia) {
		case 0:
			return "domingo";
		case 1:
			return "lunes";
		case 2:
			return "martes";
		case 3:
			return "miércoles";
		case 4:
			return "jueves";
		case 5:
			return "viernes";
		case 6:
			return "sábado";
		
		}
		return "";

	}

	public static String separarMiles(String valor){

		return separarMiles(valor, ".", ",");

	}
 
	public static String separarMiles(String valor, String separadorMil, String separadorDecimal){

		// se toma el valor del entero
		int largo = valor.length();

		if (largo > 8) {

			valor = valor.substring(largo - 9, largo - 6) + separadorMil
					+ valor.substring(largo - 6, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);

		} else

		if (largo > 7) {

			valor = valor.substring(largo - 8, largo - 6) + separadorMil
					+ valor.substring(largo - 6, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);

		} else

		if (largo > 6) {

			valor = valor.substring(largo - 7, largo - 6) + separadorMil
					+ valor.substring(largo - 6, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);

		} else if (largo > 5) {
			valor = valor.substring(largo - 6, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);

		} else

		if (largo > 4) {
			valor = valor.substring(largo - 5, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);

		}

		else if (largo > 3) {

			valor = valor.substring(largo - 4, largo - 3) + separadorMil
					+ valor.substring(largo - 3, largo);
		}

		return valor;
	}


	public static void eliminarSubdirectorios(String directorio) {
        File dir = new File(directorio);

        if (dir.exists() && dir.isDirectory()) {
            File[] subdirectorios = dir.listFiles(File::isDirectory);

            if (subdirectorios != null) {
                for (File subdirectorio : subdirectorios) {
                    eliminarDirectorioRecursivo(subdirectorio);
                }
            }
        }
    }

    public static void eliminarDirectorioRecursivo(File directorio) {
        if (directorio.exists() && directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        eliminarDirectorioRecursivo(archivo);
                    } else {
                        if (archivo.delete()) {
                            System.out.println("Archivo eliminado: " + archivo.getAbsolutePath());
                        } else {
                            System.out.println("No se pudo eliminar el archivo: " + archivo.getAbsolutePath());
                        }
                    }
                }
            }

            if (directorio.delete()) {
                System.out.println("Directorio eliminado: " + directorio.getAbsolutePath());
            } else {
                System.out.println("No se pudo eliminar el directorio: " + directorio.getAbsolutePath());
            }
        }
    }


	public static Boolean getBoolean(Object objeto) {
		if (objeto==null) return null;
        if (objeto instanceof Boolean) {
            return (Boolean) objeto; // Conversión segura de tipos
        } else {
            // Manejo en caso de que el objeto no sea del tipo esperado
            throw new RuntimeException("Objeto no es instancia de boolean: "+objeto.getClass().getCanonicalName() );
        }
    }

	public static String normalizarAlias(String alias) {

		alias = alias.toLowerCase();
		alias = alias.replace(" ", "-");
		return alias.trim();

	}

	public static String getExtensionDeNombreArchivo(String nombreArchivo){
        int lastDotIndex = nombreArchivo.lastIndexOf('.');

        if (lastDotIndex > 0) {
            String extension = nombreArchivo.substring(lastDotIndex + 1);
			return extension;
        } 
		return null;
	}


	public static String ocultarCorreo(String correo) {
        StringBuilder resultado = new StringBuilder();
        int arrobaIndex = correo.indexOf('@');
        int longitud = correo.length();
		int cantidadPre= 3;
		int cantidadPos=2;
		
		if(arrobaIndex<=6){
			cantidadPre= 2;
			cantidadPos=1;
		}else if(arrobaIndex<=7){
			cantidadPre= 2;
			cantidadPos=2;
		}

        // Obtiene las primeras tres letras antes del '@'
        String primerasTresLetras = correo.substring(0, Math.min(arrobaIndex, cantidadPre));

        // Obtiene las últimas dos letras antes del '@'
        String ultimasDosLetras = correo.substring(arrobaIndex - cantidadPos, arrobaIndex);

        // Construye el correo oculto
        resultado.append(primerasTresLetras);

        // Llena el resto con asteriscos
        for (int i = 0; i < arrobaIndex - cantidadPre - cantidadPos; i++) {
            resultado.append('*');
        }

        resultado.append(ultimasDosLetras);
        resultado.append(correo.substring(arrobaIndex, longitud));

        return resultado.toString();
    }


	public static boolean validarDefinicionPaquete(String paquete) {
		if (paquete==null) return false;
        // Definir la expresión regular para validar el paquete
        String regex = "^[a-zA-Z]+(\\.[a-zA-Z0-9]+)*$";

        // Compilar la expresión regular en un objeto Pattern
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher para el string de paquete
        Matcher matcher = pattern.matcher(paquete);

        // Verificar si el string de paquete coincide con la expresión regular
        return matcher.matches();
    }

	public static Date fechaSumanMinuto(Date fecha, int minutos) {
        // Convertir los minutos a milisegundos
        long milisegundosASumar = minutos * 60 * 1000;
        // Obtener la fecha actual en milisegundos
        long tiempoActual = fecha.getTime();
        // Sumar los milisegundos a la fecha actual
        long tiempoNuevaFecha = tiempoActual + milisegundosASumar;
        // Crear una nueva instancia de Date con la nueva fecha
        Date nuevaFecha = new Date(tiempoNuevaFecha);
        return nuevaFecha;
    }

	
}
