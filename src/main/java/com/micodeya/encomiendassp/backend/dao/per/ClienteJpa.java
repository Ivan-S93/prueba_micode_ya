package com.micodeya.encomiendassp.backend.dao.per;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micodeya.encomiendassp.backend.entities.per.Cliente;

public interface ClienteJpa extends JpaRepository<Cliente, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    

    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN

}
