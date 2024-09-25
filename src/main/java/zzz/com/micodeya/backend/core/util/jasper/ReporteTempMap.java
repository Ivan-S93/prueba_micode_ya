package zzz.com.micodeya.backend.core.util.jasper;

import java.util.HashMap;
import java.util.Map;

public class ReporteTempMap {
	
	public static int ITERADOR_MAP=0;
	
	public static Map<String, String> REPORTE_MAP_00=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_01=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_02=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_03=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_04=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_05=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_06=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_07=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_08=new HashMap<String, String>();
	public static Map<String, String> REPORTE_MAP_09=new HashMap<String, String>();

	
	public static String set(String identificador, String archivoFinal){
	
		
		try {
			
			switch (ITERADOR_MAP) {
			case 0:
				identificador="0"+identificador;
				REPORTE_MAP_00.put(identificador, archivoFinal);
				ITERADOR_MAP=1;
				break;
			case 1:
				identificador="1"+identificador;
				REPORTE_MAP_01.put(identificador, archivoFinal);
				ITERADOR_MAP=2;
				break;
			case 2:
				identificador="2"+identificador;
				REPORTE_MAP_02.put(identificador, archivoFinal);
				ITERADOR_MAP=3;
				break;
			case 3:
				identificador="3"+identificador;
				REPORTE_MAP_03.put(identificador, archivoFinal);
				ITERADOR_MAP=4;
				break;
			case 4:
				identificador="4"+identificador;
				REPORTE_MAP_04.put(identificador, archivoFinal);
				ITERADOR_MAP=5;
				break;
			case 5:
				identificador="5"+identificador;
				REPORTE_MAP_05.put(identificador, archivoFinal);
				ITERADOR_MAP=6;
				break;
			case 6:
				identificador="6"+identificador;
				REPORTE_MAP_06.put(identificador, archivoFinal);
				ITERADOR_MAP=7;
				break;
			case 7:
				identificador="7"+identificador;
				REPORTE_MAP_07.put(identificador, archivoFinal);
				ITERADOR_MAP=8;
				break;
			case 8:
				identificador="8"+identificador;
				REPORTE_MAP_08.put(identificador, archivoFinal);
				ITERADOR_MAP=9;
				break;
			case 9:
				identificador="9"+identificador;
				REPORTE_MAP_09.put(identificador, archivoFinal);
				ITERADOR_MAP=0;
				break;
			default:
				identificador="0"+identificador;
				REPORTE_MAP_00.put(identificador, archivoFinal);
				ITERADOR_MAP=1;
				break;
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException("kwfException Error Grave en setear reporte.",e);
		}
		
		
		
		return identificador;
	}
	
	
	public static String get(String identificador){
		
		int posicionIterador=Integer.parseInt(identificador.substring(0,1));
		
		String archivoFinal=null;		
		try {
			
			switch (posicionIterador) {
			case 0:
				archivoFinal=REPORTE_MAP_00.get(identificador);
				REPORTE_MAP_00.remove(identificador);
				break;
			case 1:
				archivoFinal=REPORTE_MAP_01.get(identificador);
				REPORTE_MAP_01.remove(identificador);
				break;
			case 2:
				archivoFinal=REPORTE_MAP_02.get(identificador);
				REPORTE_MAP_02.remove(identificador);
				break;
			case 3:
				archivoFinal=REPORTE_MAP_03.get(identificador);
				REPORTE_MAP_03.remove(identificador);
				break;
			case 4:
				archivoFinal=REPORTE_MAP_04.get(identificador);
				REPORTE_MAP_04.remove(identificador);
				break;
			case 5:
				archivoFinal=REPORTE_MAP_05.get(identificador);
				REPORTE_MAP_05.remove(identificador);
				break;
			case 6:
				archivoFinal=REPORTE_MAP_06.get(identificador);
				REPORTE_MAP_06.remove(identificador);
				break;
			case 7:
				archivoFinal=REPORTE_MAP_07.get(identificador);
				REPORTE_MAP_07.remove(identificador);
				break;
			case 8:
				archivoFinal=REPORTE_MAP_08.get(identificador);
				REPORTE_MAP_08.remove(identificador);
				break;
			case 9:
				archivoFinal=REPORTE_MAP_09.get(identificador);
				REPORTE_MAP_09.remove(identificador);
				break;
			default:
				archivoFinal=REPORTE_MAP_00.get(identificador);
				REPORTE_MAP_00.remove(identificador);
				break;
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException("kwfException Error Grave al recuperar reporte.",e);
		}
		
		
		
		return archivoFinal;
	}
}
