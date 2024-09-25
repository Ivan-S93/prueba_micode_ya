package zzz.com.micodeya.backend.core.util.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class InicioSesionDto {

    @NotEmpty
    @Size(min = 3, max = 60, message = "Cantidad de caracteres debe ser entre 3 y 60")
    private String user;

    @NotEmpty
    @Size(min = 3, max = 60, message = "Cantidad de caracteres debe ser entre 3 y 60")
    private String password;

    private String empresaCore;
}
