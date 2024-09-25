package zzz.com.micodeya.backend.core.dao.zk;

import zzz.com.micodeya.backend.core.entities.zk.EmpresaCore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaCoreJpa extends JpaRepository<EmpresaCore, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    

}
