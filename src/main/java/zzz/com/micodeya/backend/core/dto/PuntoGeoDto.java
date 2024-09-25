package zzz.com.micodeya.backend.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PuntoGeoDto {
    Double latitud;
    Double longitud;

    Integer precision;
    Long timestamp;
    String provider;

    @JsonIgnoreProperties(ignoreUnknown = true)
    String etiqueta;

    //{"latitud": -25.435435,"longitud":-15.32425, "precision": 0, "timestamp":234324324, "provider":"web"}
    public PuntoGeoDto(String model) throws JsonMappingException, JsonProcessingException {

        if (model != null && model.trim().length() > 10) {
            PuntoGeoDto punto = new ObjectMapper().readValue(model, PuntoGeoDto.class);

            this.latitud = punto.getLatitud();
            this.longitud = punto.getLongitud();
            this.precision = punto.getPrecision();
            this.timestamp = punto.getTimestamp();
            this.provider = punto.getProvider();
        }

    }
}

// export interface PuntoGeoProps {
// latitud: number;
// longitud: number;
// precision?: number;
// timestamp?: number;
// provider?: "fused" | "gps" | "network" | "passive" | "finger"| "fingerdrag";
// eliminar?: number; //1 para eliminar
// etiqueta?: string; //lo que mostrara
// id?: string;//id unico, para referencia
// data?: any; //para guardar cualquier cosa, con mucho corntrol
// }
