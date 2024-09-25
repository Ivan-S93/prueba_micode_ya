package com.micodeya.encomiendassp.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.jar.Manifest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.service.FileStorageService;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.KwfBootConfig;
import zzz.com.micodeya.backend.core.util.security.SesionJwt;

@Slf4j
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"zzz.com.micodeya.backend.core", "com.micodeya.encomiendassp.backend"})
@EntityScan(basePackages = {"zzz.com.micodeya.backend.core", "com.micodeya.encomiendassp.backend"})
@EnableJpaRepositories(basePackages = {"zzz.com.micodeya.backend.core", "com.micodeya.encomiendassp.backend"})
public class EncomiendasspApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncomiendasspApplication.class, args);
	}


	@Value("${server.cors.permitido}") 
    private String corsPermitidoString;

	@Value("${jwt.duracion}")
    private String duracionJwt;

	@Value("${file.upload.directory}")
	private String fileUploadDirectory;

	@Value("${server.port}")
	private String puerto;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Value("${nombre.largo}")
	private String nombreLargo;

	@Value("${nombre.mobile}")
	private String nombreMobile;

	@Value("${server.entorno}")
	private String serverEntorno;


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				String arrayPermitido[] = corsPermitidoString.split(",");
				registry.addMapping("/**").allowedOrigins(arrayPermitido);
				
			}
		};
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

		//Setear estatico
		SesionJwt.DURACION_HORAS_JWT=Integer.parseInt(duracionJwt);
		FileStorageService.FILE_UPLOAD_DIRECTORY=fileUploadDirectory;
		
		KwfBootConfig.initClean();

		
		Date date = new Date();
		String DD_MM_AAAA=JeBoot.getFechaString(date);
		String HH_MM_SS=JeBoot.getHoraStringHHMMSS(date);


		String implementationVersion = null;
        try (InputStream is = EncomiendasspApplication.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
			implementationVersion = new Manifest(is).getMainAttributes().getValue("Implementation-Version");
		} catch (IOException e) {
			log.warn("No se pudo obtener información del archivo MANIFEST.MF", e);
		}

		System.out.println("\n           ▄█████▄");
		System.out.println("        ▄███████████▄");
		System.out.println("     ▄██████       █████▄");
        System.out.println("    ██████   LISTO   █████");
        System.out.println("   █████  micodeya.com  ████");
        System.out.println("  ████     "+serverEntorno.toUpperCase()+"     ████");
        System.out.println("   ████   "+DD_MM_AAAA+"   ████");
        System.out.println("    ████▄  "+HH_MM_SS+"  ▄████");
        System.out.println("       ███         ▄███");
        System.out.println("         ███████████"); 
		System.out.println("            █████ \n"); 

		System.out.println("\n" + nombreLargo);
		System.out.println("Versión: "+implementationVersion);
		System.out.println(nombreMobile);
		System.out.println("http://localhost:" + puerto + "" + contextPath);
		System.out.println("check http://localhost:" + puerto + "" + contextPath+"/server/status");
		System.out.println("\n\n");

	}

	@Value("${fcm.path.keyFile}")
	private String fcmFilePath;


	/** 
	 * TODO Se debe realizar la configuracion correspondiente
	 * https://firebase.google.com/docs/admin/setup?hl=es-419
	*/
	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {

		try {
			// System.out.println("\n\nfcmFilePath=> " + fcmFilePath);
			// System.out.println("existe=> " + new File(fcmFilePath).exists());
			// Leemos la clave generada en firebase
			FileInputStream credentialsFileInputStream = new FileInputStream(fcmFilePath);
			GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(credentialsFileInputStream);

			FirebaseOptions firebaseOptions = FirebaseOptions
					.builder()
					.setCredentials(googleCredentials)
					.build();
			FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
			return FirebaseMessaging.getInstance(app);

		} catch (Exception e) {
			log.error("ERROR AL INICIALIZAR FIREBASE", e);
		}

		return null;

	}


}
