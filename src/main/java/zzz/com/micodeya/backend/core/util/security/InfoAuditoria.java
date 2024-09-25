package zzz.com.micodeya.backend.core.util.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class InfoAuditoria {
    private Integer idSesion;
    private String usuario;
    private Integer idUsuario;
    private String empresaCore;
    private String cuenta;

    // public String getUsuarioIdSesion(){
    // return usuario+"-"+idSesion;
    // }

    public InfoAuditoria(boolean defecto) {

        this.usuario = "base";

        this.idSesion = null;
        this.idUsuario = null;

        this.cuenta = "base";
        this.empresaCore = "EMPKUAA";
    }

}
