package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zzz.com.micodeya.backend.core.entities.zk.FcmToken;

public interface FcmTokenJpa extends JpaRepository<FcmToken, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    

    // KGC-NOREPLACE-OTROS-INI

    List<FcmToken> findTop1ByFcmTokenAndActivoOrderByUltimaActualizacionDesc(String token, boolean activo);
    List<FcmToken> findTop1ByCuentaAndActivoOrderByUltimaActualizacionDesc(String token, boolean activo);

    // KGC-NOREPLACE-OTROS-FIN

}
