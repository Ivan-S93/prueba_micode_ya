package zzz.com.micodeya.backend.core.util.jasper;

import java.io.FileNotFoundException;

import net.sf.jasperreports.engine.JRException;

public class ReportsTest {

	
	public static void prepararReporte() throws JRException, FileNotFoundException{
		
		ReporteAux reporte=new ReporteAux();
		
		reporte.setArchivoJrxml("test/pais_listadoGeneral");
		reporte.setFormato("pdf");
		reporte.setTitulo("Nombre del reporte");
		reporte.setNombreDescarga("País descargado");
		reporte.setDataSource(ReporteAux.DataSourceType.JSON);
		reporte.setJsonDataSource("[{\"idPais\":1,\"nombre\":\"Paraguay\",\"activo\":\"S\"},{\"idPais\":2,\"nombre\":\"Argentina\",\"activo\":\"S\"}]");
		 
		//JasperReportsUtils.prepararReporte(reporte,false);
		String directorioJrxml="/Users/emilio/repo/kuaa-generator/kuaa-generator-core-be/src/main/resources/jasper-report";
		String directorioOutput="/Users/emilio/repo/kuaa-generator/kuaa-generator-core-be/src/main/resources/jasper-report-out";
		//JRUtils.prepararReporte(reporte, true);


		String resultado=JRUtils.prepararReporteSource(directorioJrxml, directorioOutput, reporte);

		System.out.println("resultado= "+resultado);

	  
		
		
		
		
		
	}
	
	public static void main(String[] args) throws JRException, FileNotFoundException {
		System.out.println("hola");
		
		prepararReporte();
		
		
		System.out.println("chau");
		

	}

}
