package zzz.com.micodeya.backend.core.dto;

import java.util.Date;

import jakarta.validation.ValidationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CodigoOtpDto {
    
    private String codigo;
    private Date fechaHora;
    private Integer intentos;
    private Integer minutosVidaUtil;

    public CodigoOtpDto(String codigo){
        this.codigo = codigo;
        this.fechaHora = new Date();
        this.intentos = 5;
        this.minutosVidaUtil = 5;
    }

    public boolean verificarCodigo(String codigoIngresado){

        if(codigoIngresado==null) return false;
        if(this.codigo==null) return false;
        return (codigoIngresado.equals(this.codigo));
        
    }

}
