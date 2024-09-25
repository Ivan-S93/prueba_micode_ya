package zzz.com.micodeya.backend.core.dao.zk;

import zzz.com.micodeya.backend.core.entities.zk.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolJpa extends JpaRepository<Rol, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    long countByCodigoIgnoreCase(String codigo);
	long countByIdRolNotAndCodigoIgnoreCase(Integer idRol, String codigo);

		long countByNombreIgnoreCase(String nombre);
	long countByIdRolNotAndNombreIgnoreCase(Integer idRol, String nombre);


}
