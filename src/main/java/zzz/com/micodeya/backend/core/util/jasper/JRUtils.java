package zzz.com.micodeya.backend.core.util.jasper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Slf4j
@Component
public class JRUtils {

	// @Value("${jasper.report.directorio.jrxml}")
	// private String directorioJrxml;

	// @Value("${jasper.report.directorio.compilado}")
	// private String directorioCompilado;

	// @Value("${jasper.report.directorio.output}")
	// private String directorioOutput;

	public static String DIRECTORIO_EXTERNO_JRXML = "";
	public static String DIRECTORIO_SALIDA = "";

	public static final String RUTA_REPORTES = "reports";
	public static final String RUTA_REPORTES_OUT = "kwf/reptemp";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	// private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public JRUtils() {

		log.info("JRUtils init");
	}

	@Value("${jasper.report.directorio.jrxml}")
	public void setDirectorioExternoStatic(String value) {
		DIRECTORIO_EXTERNO_JRXML = value;
	}

	@Value("${jasper.report.directorio.output}")
	public void setDirectorioSalidaStatic(String value) {
		DIRECTORIO_SALIDA = value;
	}

	public static String prepararReporteSource(
			String directorioJrxml, String directorioOutput,
			ReporteAux reporte) throws FileNotFoundException {

		DIRECTORIO_EXTERNO_JRXML = directorioJrxml;
		DIRECTORIO_SALIDA = directorioOutput;

		return prepararReporte(reporte, false);
	}

	public static String prepararReporte(
			ReporteAux reporte) throws FileNotFoundException {
		return prepararReporte(reporte, true);
	}

	public static String prepararReporte(ReporteAux reporte, boolean appResource)
			throws FileNotFoundException {

		// InputStream inputStream = ClassLoaderUtil.getResourceAsStream("test.csv",
		// YourCallingClass.class);
		// getClass().getClassLoader().getResource(

		boolean compilar = true;
		String retorno = "";
		FileOutputStream fileSalida = null;

		reporte.addParametro("REPORT_LOCALE", new Locale("es", "ES"));
		reporte.setArchivoJrxml(reporte.getArchivoJrxml().replace("/", File.separator));

		// String pathSub = servletContext.getRealPath(separador + RUTA_REPORTES);
		// parametros.put("SUBREPORT_DIR",pathSub+separador);
		// reporte.setDirectorio("C:/Users/asu08588/repos/gra-alert-services-report/src/main/resources/reports");

		// String directorioReporte=directorioContexto+File.separator
		// +reporte.getDirectorio().replace("/", File.separator)+File.separator;

		String grupoModulo = reporte.getArchivoJrxml().split("/")[0];
		String directorioReporte = appResource
				? "" + ResourceUtils.getURL("classpath:jasper-report").getPath() + File.separator
				: DIRECTORIO_EXTERNO_JRXML.replace("/", File.separator) + File.separator;

		String directorioReporteSalida = appResource
				? "" + ResourceUtils.getURL("classpath:jasper-report-out").getPath() + File.separator +grupoModulo + File.separator
				: DIRECTORIO_SALIDA.replace("/", File.separator) + File.separator + grupoModulo
						+ File.separator;

		retorno = grupoModulo + "/" + reporte.getIdentificador() + "/" + reporte.getNombreDescarga();

		try {

			// Compilar
			if (compilar) {
				File archivoCompilar = new File(directorioReporte + reporte.getArchivoJrxml() + ".jasper");

				if (!archivoCompilar.exists()) {
					// File =ResourceUtils.getFile("classpath:json/abcd.json");
					JasperCompileManager.compileReportToFile(directorioReporte
					 + reporte.getArchivoJrxml().replace("/", File.separator) + ".jrxml");
				}

			} 

			JasperPrint jasperPrint;
			// Seleciona DataSource
			String inputStream = directorioReporte + reporte.getArchivoJrxml() + ".jasper";
			JsonDataSource dataSource = null;
			// json
			if (reporte.getDataSource().equals(ReporteAux.DataSourceType.JSON)) {
				String json = "{\"report\":" + reporte.getJsonDataSource() + "}";

				if (reporte.getFormato().equals("excel")) {
					// String subreport = "{\"subreport\":"+ new
					// JSONArray(resultadoList).toString()+ "}";
					// reporteAux.addParametro("P_DS_CONCEPTOS", new JsonDataSource(new
					// ByteArrayInputStream(subreport.getBytes("UTF-8")),"subreport"));
					reporte.addParametro("P_DS_EXCEL",
							new JsonDataSource(new ByteArrayInputStream(json.getBytes()), "report"));

					String unDetalle = "{\"report\":[{\"a\":1}]}";
					dataSource = new JsonDataSource(new ByteArrayInputStream(unDetalle.getBytes()), "report");
				} else {
					dataSource = new JsonDataSource(new ByteArrayInputStream(json.getBytes()), "report");
				}

				jasperPrint = JasperFillManager.fillReport(
						inputStream, reporte.getParametroMap(), dataSource);

			} else {
				jasperPrint = JasperFillManager.fillReport(
						inputStream, reporte.getParametroMap(), reporte.getConexion());

			}

			// String directorioReporteOut=rootPath + File.separator
			// +RUTA_REPORTES_OUT.replace("/", File.separator)
			// + File.separator+reporte.getIdentificador()+File.separator;
			String directorioReporteOut = directorioReporteSalida + reporte.getIdentificador() + File.separator;

			File directory = new File(directorioReporteOut);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String archivoFinal = "";
			// prepara segun formato
			if (reporte.getFormato().equals("excel")) {
				archivoFinal = directorioReporteOut + reporte.getNombreDescarga() + ".xls";
				retorno = retorno + ".xls";

			} else if (reporte.getFormato().equals("word")) {
				archivoFinal = directorioReporteOut + reporte.getNombreDescarga() + ".docx";
				retorno = retorno + ".docx";
			} else {
				archivoFinal = directorioReporteOut + reporte.getNombreDescarga() + "." + reporte.getFormato();
				retorno = retorno + "." + reporte.getFormato();
			}

			fileSalida = new FileOutputStream(archivoFinal);

			String METADATA_AUTHOR = "kwf " + "TITULO_SISTEMA";
			String METADATA_CREATOR = "kwfcreator";
			String METADATA_TITLE = reporte.getTitulo();

			if (reporte.getFormato().equals("pdf")) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				configuration.setMetadataAuthor(METADATA_AUTHOR);
				configuration.setMetadataTitle(METADATA_TITLE);
				configuration.setMetadataCreator(METADATA_CREATOR);
				configuration.setMetadataSubject(METADATA_TITLE);
				configuration.setMetadataKeywords(METADATA_TITLE);

				exporter.setConfiguration(configuration);
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileSalida));

				exporter.exportReport();

			} else if (reporte.getFormato().equals("json")) {

				OutputStreamWriter outStream = new OutputStreamWriter(fileSalida, "UTF-8");
				outStream.write(reporte.getJsonDataSource());
				outStream.flush();
				outStream.close();

			}

			retorno = archivoFinal;

		} catch (JRException e) {
			e.printStackTrace();
			// TODO: handle exception
		} catch (FileNotFoundException fn) {
			fn.printStackTrace();
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fileSalida != null) {
			try {
				fileSalida.flush();
				fileSalida.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return retorno;

	}

}
