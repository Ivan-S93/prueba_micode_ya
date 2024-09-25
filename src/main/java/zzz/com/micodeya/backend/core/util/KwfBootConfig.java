package zzz.com.micodeya.backend.core.util;

import java.io.File;

import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KwfBootConfig {

    public static final String SQL_DELETE_BASE = "UPDATE zk_dummy SET nombre = 'el' WHERE nombre=(select zk_fn_eliminar_fila('%s','%s','%s',?))";

    public static void initClean() {
        cleanJasper();
    }

    public static void cleanJasper() {

        try {

            log.info("cleanJasper start");

            // String directorioRaiz = "ruta/del/directorio";
            String directorioRaiz = ResourceUtils.getURL("classpath:jasper-report").getPath();
            eliminarArchivosJasper(directorioRaiz);

            directorioRaiz = ResourceUtils.getURL("classpath:jasper-report-out").getPath();
            JeBoot.eliminarSubdirectorios(directorioRaiz);

            log.info("cleanJasper end");
        } catch (Exception e) {
            log.error("ERROR GRAVE EN cleanJasper", e);
        }

    }

    private static void eliminarArchivosJasper(String directorio) {
        File dir = new File(directorio);

        if (dir.exists() && dir.isDirectory()) {
            File[] archivos = dir.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        eliminarArchivosJasper(archivo.getAbsolutePath());
                    } else if (archivo.getName().endsWith(".jasper")) {
                        if (archivo.delete()) {
                            System.out.println("Archivo eliminado: " + archivo.getAbsolutePath());
                        } else {
                            System.out.println("No se pudo eliminar el archivo: " + archivo.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

   




}
