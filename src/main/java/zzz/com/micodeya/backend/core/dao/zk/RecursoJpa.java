package zzz.com.micodeya.backend.core.dao.zk;

import zzz.com.micodeya.backend.core.entities.zk.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoJpa extends JpaRepository<Recurso, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    long countByNombreIgnoreCase(String nombre);
	long countByIdRecursoNotAndNombreIgnoreCase(Integer idRecurso, String nombre);


}
