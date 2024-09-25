package zzz.com.micodeya.backend.core.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dto.MensajeMailDto;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.service.mail.resend.ResendMailService;

@Slf4j
@Component
public class JeMailService {

    @Autowired
    ResendMailService resendMail;
    
    public JeMailService() {
        log.info("\n##########\nJeMailService init\n##########");
    }

    public void enviarMail(MensajeMailDto mensaje){

        try {
            resendMail.enviarMail(mensaje);
        } catch (Exception e) {
            long codSeg = System.nanoTime();
            String mensajeError= "Error al enviar correo. " + " CodSeg: " + codSeg;
            log.error(mensajeError, e);
            throw new ValidacionException(mensajeError);
        }

    }
}
