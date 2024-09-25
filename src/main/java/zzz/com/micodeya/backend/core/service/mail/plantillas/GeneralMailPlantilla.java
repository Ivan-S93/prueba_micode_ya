package zzz.com.micodeya.backend.core.service.mail.plantillas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dto.MensajeMailDto;

@Slf4j
@Component
public class GeneralMailPlantilla {

    @Value("${nombre.largo}")
    private String nombreLargo;

    @Value("${nombre.mobile}")
    private String nombreMobile;
 
    public GeneralMailPlantilla() {
        log.info("\n##########\nGeneralMailPlantilla init\n##########");
    }

    public MensajeMailDto mensajeVerificarEmailAutorregistro(String codigo) {
        String asunto = "Código de verificación " + nombreMobile;

        String html = "<h2>" + asunto + "</h2>";
        html += "<p>Hemos recibido una solicitud para confirmar su correo.</p>";
        html += "<p>Tu código es:</p>";
        html += "<h4>" + codigo + "</h4>";

        return new MensajeMailDto(null, null, asunto, html);

    }
    public MensajeMailDto mensajeRecuperarPasswordPorEmailOtp(String codigo) {
        String asunto = "Recuperación de contraseña " + nombreMobile;

        String html = "<h2>" + asunto + "</h2>";
        html += "<p>Hemos recibido una solicitud para recuperar su contraseña.</p>";
        html += "<p>Tu código es:</p>";
        html += "<h4>" + codigo + "</h4>";

        return new MensajeMailDto(null, null, asunto, html);

    }
}
