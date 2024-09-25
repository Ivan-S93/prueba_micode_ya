package zzz.com.micodeya.backend.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class MensajeMailDto {
    
    private String de;
    private String para;
    private String asunto;
    private String html;

    
    public MensajeMailDto(String de, String para, String asunto, String html) {
        this.de = de;
        this.para = para;
        this.asunto = asunto;
        this.html = html;
    }
   


   

}
