package zzz.com.micodeya.backend.core.service.mail.resend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dto.MensajeMailDto;

@Slf4j
@Component
public class ResendMailService {

    @Value("${mail.resend.apiKey}")
    private String resendApiKey;

    @Value("${mail.resend.from}")
    private String resendDefaultFrom;

    public ResendMailService() {
        log.info("\n##########\nResendMailService init\n##########");
    }

    public String enviarMail(MensajeMailDto mensaje) throws ResendException {

        String de = mensaje.getDe() == null ? resendDefaultFrom : mensaje.getDe();

        Resend resend = new Resend(resendApiKey);

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from(de)
                .to(mensaje.getPara())
                .subject(mensaje.getAsunto())
                .html(mensaje.getHtml())
                .build();

        SendEmailResponse data = resend.emails().send(sendEmailRequest);
        return data.getId();
    }

}
