package zzz.com.micodeya.backend.core.service.fcm.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SendMultipleFcmDto {
    private List<String> fcmTokenList;
    private String titulo;
    private String contenido;
    private Map<String, String> cargaUtil;
    private String urlImagen;
    
    
    
	
	
    
}
