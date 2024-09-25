package zzz.com.micodeya.backend.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JeExcelToJson {
	private String directorio;
	private String nombreArchivo;
	private String extensionArchivo;
	private Boolean primeraFilaCabecera;
	private XSSFWorkbook workbook;
	private Integer cantidadHojas;

	private JSONObject cabeceraJson;
	private JSONArray contenidoJsonArray;
	private final String[] LETRA_COLUMNA=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
	"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ"};
	
	

	public JeExcelToJson(String directorio, String nombreArchivo, String extensionArchivo, Boolean primeraFilaCabecera) {
		this.directorio = directorio;
		this.nombreArchivo = nombreArchivo;
		this.extensionArchivo = extensionArchivo;
		this.primeraFilaCabecera = primeraFilaCabecera;
		this.cantidadHojas = 0;
		
	}

	public void leerArchivo() {

		try {

			// String directorio = nombreArchivo + extensionArchivo;

			// String rootPath = System.getProperty("catalina.home");

			FileInputStream file = new FileInputStream(new File(
					directorio.replace("/", File.separator) + File.separator + nombreArchivo + extensionArchivo));

			// if(file==null){
			// 	throw new RuntimeException("Error al leer archivo excel");
			// }

			

			workbook = new XSSFWorkbook(file);			
			this.cantidadHojas=workbook.getNumberOfSheets();

		} catch (Exception e) {
			String mensajeError = "Error grave al leer archivo excel. CodSeg: " + new Date();
			log.error(mensajeError, e);
		}
	}

	
	public  void procesarHoja(int numeroHoja) throws IOException, JSONException{
		
		cabeceraJson=new JSONObject();
		contenidoJsonArray=new JSONArray();
		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(numeroHoja);
		
		Integer cantidadFilas=sheet.getPhysicalNumberOfRows();
		
		if(cantidadFilas<1){
			return;
		}
		// Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();

		int posicionFila=0;
		while (rowIterator.hasNext()) {
			posicionFila++;
			Row row = rowIterator.next();

			if(primeraFilaCabecera && posicionFila==1){
				procesarFilaCabecera(row);
			}else{
				
				procesarFila(row);
			}

			

		} // fin while filas excel

	}


	private void procesarFila(Row row) throws JSONException{

		Iterator<Cell> cellIterator=row.cellIterator();
		int posicionColumna=0;
		
		JSONObject contenidoJson=new JSONObject();

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			Object value=getCellValue(cell);
			contenidoJson.put(LETRA_COLUMNA[posicionColumna], value);
			posicionColumna++;
		}

		contenidoJsonArray.put(contenidoJson);

	}

	private void procesarFilaCabecera(Row row) throws JSONException{

		Iterator<Cell> cellIterator=row.cellIterator();
		int posicionColumna=0;
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String nombre=stringDeCell(cell);
			String nombreTransformado=nombre.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
			JSONObject cabeceraJsonContenido=new JSONObject();
			cabeceraJsonContenido.put("letra",LETRA_COLUMNA[posicionColumna]);
			cabeceraJsonContenido.put("nombreReal", nombre);
			cabeceraJsonContenido.put("nombreCodificado", nombreTransformado);
			
			cabeceraJson.put(LETRA_COLUMNA[posicionColumna],cabeceraJson);
			posicionColumna++;

		}

	}

	private static String stringDeCell(Cell cell){

		if(cell==null) return "NULL";
		

		

		if(cell.getCellType()==CellType.NUMERIC){
			return String.valueOf(cell.getNumericCellValue());
		}else if(cell.getCellType()==CellType.STRING){
			return cell.getStringCellValue();
		}else if(cell.getCellType()==CellType.BLANK){
			return String.valueOf(cell.getStringCellValue());
		}else if(cell.getCellType()==CellType.BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}else if(cell.getCellType()==CellType.ERROR){
			return String.valueOf(cell.getErrorCellValue());
		}else if(cell.getCellType()==CellType.FORMULA){
			return String.valueOf(cell.getCellFormula());
		}
		
		return "NULL";
		
	}


	private static Object getCellValue(Cell cell){

		if(cell==null) return "NULL";
		
		if(cell.getCellType()==CellType.NUMERIC){

            if (DateUtil.isCellDateFormatted(cell)) { //es fecha
                //System.out.println ("Valor Fecha" + " " + cell.getDateCellValue());
                Date fechaDate=cell.getDateCellValue();
                if (fechaDate == null) return null;
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			    sdf.setLenient(false);
			    return sdf.format(fechaDate);

            }else{ //es numerico comun
                return cell.getNumericCellValue();
            }

			
		}else if(cell.getCellType()==CellType.STRING){
			return cell.getStringCellValue();
		}else if(cell.getCellType()==CellType.BLANK){
			return cell.getStringCellValue();
		}else if(cell.getCellType()==CellType.BOOLEAN){
			return cell.getBooleanCellValue();
		}else if(cell.getCellType()==CellType.ERROR){
			return cell.getErrorCellValue();
		}else if(cell.getCellType()==CellType.FORMULA){
			return cell.getCellFormula();
		}
		
		return "NULL";
		
	}

	public Integer getCantidadHojas() {
		return cantidadHojas;
	}

	public JSONObject getCabeceraJsonArray() {
		return cabeceraJson;
	}

	public JSONArray getContenidoJsonArray() {
		return contenidoJsonArray;
	}




}
