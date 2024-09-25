package zzz.com.micodeya.backend.core.dao.zk;

import org.springframework.data.jpa.repository.JpaRepository;

import zzz.com.micodeya.backend.core.entities.zk.LoginLog;

public interface LoginLogJpa extends JpaRepository<LoginLog, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
