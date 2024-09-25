package zzz.com.micodeya.backend.core.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import zzz.com.micodeya.backend.core.exception.KwfCoreException;
import zzz.com.micodeya.backend.core.exception.MyFileNotFoundException;
import zzz.com.micodeya.backend.core.util.ArchivoDatoExtra;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

@Service
@Slf4j
public class FileStorageService {

    // private final Path fileStorageLocation;

    public static String FILE_UPLOAD_DIRECTORY;

    // https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

    @Autowired
    public FileStorageService() {

        // this.fileStorageLocation =
        // Paths.get(FILE_UPLOAD_DIRECTORY.replace("/",File.separator))
        // .toAbsolutePath().normalize();

        // try {
        // Files.createDirectories(this.fileStorageLocation);
        // } catch (Exception ex) {
        // throw new FileStorageException("Could not create the directory where the
        // uploaded files will be stored.", ex);
        // }
    }

    

    private String getDirDestinoFileUploadResource(String destinoArchivo) {

        destinoArchivo = destinoArchivo.replace("/api/", "");
        String dirDestino = FILE_UPLOAD_DIRECTORY + "/" + destinoArchivo;

        dirDestino = dirDestino.replace("/", File.separator);

        return dirDestino;
    }


    public List<ArchivoDatoExtra> guardarArchivoUploadResourceMultipart(MultipartFile[] archivos, String archivoExtraString,
            InfoAuditoria infoAuditoria, String destinoArchivo) {
                return guardarArchivoUploadResourceMultipart(archivos, archivoExtraString, infoAuditoria, destinoArchivo, null);
    }

    public List<ArchivoDatoExtra> guardarArchivoUploadResourceMultipart(MultipartFile[] archivos, String archivoExtraString,
            InfoAuditoria infoAuditoria, String destinoArchivo, String nombreEspecificoForzar) {

        try {

            String dirDestino = getDirDestinoFileUploadResource(destinoArchivo);

            File dir = new File(dirDestino);
            if (!dir.exists())
                dir.mkdirs();

            // nuevos
            if (archivoExtraString != null && archivos != null) {
                List<ArchivoDatoExtra> archivoDatoExtraList = ArchivoDatoExtra
                        .jsonArrayToList(new JSONArray(archivoExtraString));

                if (archivos.length != archivoDatoExtraList.size())
                    throw new KwfCoreException("Dimension de archivos y archivoDatoExtraList no son iguales");

                for (int i = 0; i < archivos.length; i++) {

                    MultipartFile archivo = archivos[i];
                    ArchivoDatoExtra archivoDatoExtra = archivoDatoExtraList.get(i);

                    String nombreArchivo = archivoDatoExtra.getUuidName();

                    String originalFileName=archivo.getOriginalFilename();
                    if(originalFileName!=null && originalFileName.equals("ya-estoy-en-el-server.png")){
                        continue;
                    }

                    if (nombreEspecificoForzar != null) {
                        String extensionRecuperado = JeBoot.getExtensionDeNombreArchivo(nombreArchivo) + "";
                        nombreArchivo = nombreEspecificoForzar + "." + extensionRecuperado.toLowerCase();
                    }

                    String extensionArchivo = "";

                    File file=new File(dirDestino + File.separator + nombreArchivo + extensionArchivo);

                    

                    if (file.exists()) {

                        //forzar reemplazar el existente
                        if (nombreEspecificoForzar != null) {

                            FileOutputStream outputStream = new FileOutputStream(file);

                            byte[] archivoToBytes = archivo.getBytes();
                            outputStream.write(archivoToBytes);

                            outputStream.close();
                        }
                    } else {
                        // guardar
                        FileOutputStream outputStream = new FileOutputStream(file);

                        byte[] archivoToBytes = archivo.getBytes();
                        outputStream.write(archivoToBytes);

                        outputStream.close();
                    }
                    

                }

                return archivoDatoExtraList;
            }

        } catch (Exception e) {
            // log.error("Error al procesar archivos para DropBox", e);
            throw new RuntimeException("Error al guardar archivo", e);

        }

        return null;

    }
    public Resource recuperarArchivoUploadResource(String directorio, String fileName) {
    
        return recuperarArchivoUploadResource(directorio, fileName, null);
    
    }

    
    public Resource recuperarArchivoUploadResource(String directorio, String fileName, String thumbFile) {
        try {

            if(fileName.endsWith(".down")) fileName=fileName.substring(0, fileName.length() - 5);;

            Path filePath = Paths.get(getDirDestinoFileUploadResource(directorio) + File.separator + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                if(thumbFile!=null){
                    try {
                        return compressImage(filePath.toString(), Integer.parseInt(thumbFile));
                    } catch (Exception e) {
                        log.warn("No se pudo convertir a thumbfile: "+filePath.toString(), e);
                    }
                }
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + filePath.toAbsolutePath().toString());
            }
        } catch (MalformedURLException ex) {
            throw new KwfCoreException("File not found MalformedURL:" + directorio + "/"+fileName);
        }
        
    }


    public boolean existeArchivoUploadResource(String directorio, String fileName) {
        try {

            if(fileName.endsWith(".down")) fileName=fileName.substring(0, fileName.length() - 5);;

            File file=new File(getDirDestinoFileUploadResource(directorio) + File.separator + fileName);

            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new KwfCoreException("Error al verificar existencia de archivo:" + directorio + "/"+fileName);
        }
        
    }

    public int eliminarArchivoUploadResource(String directorio, String fileName) {
        try {

            if(fileName.endsWith(".down")) fileName=fileName.substring(0, fileName.length() - 5);;

            File file=new File(getDirDestinoFileUploadResource(directorio) + File.separator + fileName);

            if (file.exists()) {
                file.delete();
                return 1;
            }else{
                return 0;
            }
        } catch (Exception ex) {
            throw new KwfCoreException("Error al eliminar archivo:" + directorio + "/"+fileName);
        }
        
    }



     public Resource recuperarArchivoFullPath(String directorioArchivo) {
        try {

            if(directorioArchivo.endsWith(".down")) directorioArchivo=directorioArchivo.substring(0, directorioArchivo.length() - 5);;


            Path filePath = Paths.get(directorioArchivo.replace("/", File.separator));
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + filePath.toAbsolutePath().toString());
            }
        } catch (MalformedURLException ex) {
            throw new KwfCoreException("File not found MalformedURL:" + directorioArchivo);
        }
        
    } 


    public static void guardarArchivoTexto(String directorio, String nombreArchivo, String extension, String contenido)
			throws IOException {

		byte[] bytesArchivo = contenido.getBytes(StandardCharsets.UTF_8);


		String contenidoRecuperado = new String(bytesArchivo, StandardCharsets.UTF_8);


		nombreArchivo = nombreArchivo + "." + extension;

		// Creating the directory to store file
		File dir = new File(directorio.replace("/", File.separator));
		if (!dir.exists())
			dir.mkdirs();

		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator + nombreArchivo);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(bytesArchivo);
		stream.close();

	}


  
    public static void comprimirCarpeta(String rutaCarpeta, String rutaArchivoComprimido) throws IOException, ArchiveException {
        File carpeta = new File(rutaCarpeta);

        try (FileOutputStream fos = new FileOutputStream(rutaArchivoComprimido);
             ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, fos)) {

            agregarDirectorio(aos, carpeta, "");
        }
    }

    private static void agregarDirectorio(ArchiveOutputStream aos, File directorio, String path) throws IOException {
        for (File archivo : directorio.listFiles()) {
            String entradaPath = path + archivo.getName();

            if (archivo.isDirectory()) {
                agregarDirectorio(aos, archivo, entradaPath + "/");
            } else {
                ArchiveEntry entrada = aos.createArchiveEntry(archivo, entradaPath);
                aos.putArchiveEntry(entrada);
                FileInputStream fis = new FileInputStream(archivo);
                IOUtils.copy(fis, aos);
                fis.close();
                aos.closeArchiveEntry();
            }
        }
    }






    public Resource compressImage(String filePath, Integer ancho) throws IOException {
        
        String extensionRecuperado = JeBoot.getExtensionDeNombreArchivo(filePath);
        ancho= Math.max(ancho, 5);

        
        String fileOutPath=filePath+"-thumb-"+ancho+"."+extensionRecuperado;
        File fileOut=new File(fileOutPath);

        if(fileOut.exists()){ // si ya existe, retornar
            return recuperarArchivoFullPath(fileOutPath);
        }
        // si no existe, crear
        //https://github.com/coobird/thumbnailator/wiki/Examples
        Thumbnails.of(new File(filePath))
        .size(ancho, ancho)
        .toFile(fileOut);
        
        return recuperarArchivoFullPath(fileOutPath);

        /*
      Thumbnails.of(inputStream)
                      .size(ancho, ancho) // Ancho fijo de 200 y altura automática manteniendo la proporción
                      .keepAspectRatio(true)
                      .outputFormat(extensionRecuperado) // Formato de salida, puedes usar jpg, png, etc.
                      .outputQuality(0.8) // Calidad de la imagen (0.0 a 1.0)
                      .toOutputStream(outputStream);
         */
    }



    // public String storeFile(MultipartFile file) {
    // // Normalize file name
    // String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    // try {
    // // Check if the file's name contains invalid characters
    // if (fileName.contains("..")) {
    // throw new FileStorageException("Sorry! Filename contains invalid path
    // sequence " + fileName);
    // }

    // // Copy file to the target location (Replacing existing file with the same
    // name)
    // Path targetLocation = this.fileStorageLocation.resolve(fileName);
    // Files.copy(file.getInputStream(), targetLocation,
    // StandardCopyOption.REPLACE_EXISTING);

    // return fileName;
    // } catch (IOException ex) {
    // throw new FileStorageException("Could not store file " + fileName + ". Please
    // try again!", ex);
    // }
    // }

    // public Resource loadFileAsResource(String fileName) {
    // try {
    // Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
    // Resource resource = new UrlResource(filePath.toUri());
    // if (resource.exists()) {
    // return resource;
    // } else {
    // throw new MyFileNotFoundException("File not found " + fileName);
    // }
    // } catch (MalformedURLException ex) {
    // throw new MyFileNotFoundException("File not found " + fileName, ex);
    // }
    // }
}