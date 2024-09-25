package zzz.com.micodeya.backend.core.service.fcm.dto;

import java.util.Map;

import lombok.Data;

@Data
public class SendToTopicFcmDto {

    private String fcmTopic;
    
	private String titulo;
    private String contenido;
    private Map<String, String> cargaUtil;
    private String urlImagen;

    
    
}
