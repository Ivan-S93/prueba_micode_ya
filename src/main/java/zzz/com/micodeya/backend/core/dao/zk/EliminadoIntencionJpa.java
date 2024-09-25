package zzz.com.micodeya.backend.core.dao.zk;

import zzz.com.micodeya.backend.core.entities.zk.EliminadoIntencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EliminadoIntencionJpa extends JpaRepository<EliminadoIntencion, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    

}
