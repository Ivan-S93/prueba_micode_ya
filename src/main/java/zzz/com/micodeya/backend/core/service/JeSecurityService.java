package zzz.com.micodeya.backend.core.service;

import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.exception.ValidacionException;

@Slf4j
@Component
public class JeSecurityService {

    
    @Value("${security.service.salt}")
    private String salt;

    @Value("${security.service.iterations}")
    private Integer iterations;
    
    @Value("${security.service.password.min}")
    private Integer passwordMin;

    @Value("${security.service.password.upper}")
    private Boolean passwordUpper;

    @Value("${security.service.password.number}")
    private Boolean passwordNumber;

    @Value("${security.service.password.special}")
    private Boolean passwordSpecial;
    
    public JeSecurityService() {
        log.info("\n##########\nJeSecurityService init\n##########");

    }

     public String encriptar(String password, String salt2) {

        String hash = this.salt+password+salt2; 
        // int iterations=Integer.parseInt(this.iterationsString);
        for (int i = 0; i < iterations; i++) {
            hash = DigestUtils.sha256Hex(hash);
        }
        
        return hash;

    }

    public boolean encriptarComparar(String password, String salt2, String encriptado) {

        String hash = this.encriptar(password,salt2);

        return hash.equals(encriptado);

    }

    public void verificarPassword(String password) {

        if (password == null) {
            throw new ValidacionException("La contraseña debe tener al menos 6 caracteres");
        }

        if (password.length() < this.passwordMin) {
            throw new ValidacionException("La contraseña debe tener al menos "+this.passwordMin+" caracteres");
        }

        if (password.contains(" ")) {
            throw new ValidacionException("La contraseña no debe contener espacio");
        }

        if(passwordUpper && !contieneMayusculaYMinuscula(password)){
                throw new ValidacionException("La contraseña debe tener al menos un caracter mayúscula y minúscula");
        }

        if(passwordNumber && !contieneNumero(password)){
                throw new ValidacionException("La contraseña debe tener al menos un número");
        }

        if(passwordSpecial && !contieneCaracterEspecial(password)){
                throw new ValidacionException("La contraseña debe tener al menos un caracter especial");
        }

    }

    public boolean contieneMayusculaYMinuscula(String str) {
        // La expresión regular verifica si hay al menos una letra minúscula y una letra mayúscula.
        String regex = ".*[a-z].*[A-Z].*";
        return Pattern.matches(regex, str);
    }

    public boolean contieneNumero(String str) {
        // La expresión regular verifica si hay al menos un dígito (número) en la cadena.
        String regex = ".*\\d.*";
        return Pattern.matches(regex, str);
    }

    public static boolean contieneCaracterEspecial(String str) {
        // La expresión regular verifica si hay al menos un caracter especial en la cadena.
        String regex = ".*[^a-zA-Z0-9].*";
        return Pattern.matches(regex, str);
    }

}
