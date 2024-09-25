package zzz.com.micodeya.backend.core.dao.zk;

import zzz.com.micodeya.backend.core.entities.zk.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioJpa extends JpaRepository<Usuario, Integer>{

    /** Docs referencia
    * https://www.baeldung.com/spring-data-derived-queries
    * https://www.baeldung.com/spring-data-jpa-query
    */
    
    long countByUsuarioIgnoreCase(String usuario);
	long countByIdUsuarioNotAndUsuarioIgnoreCase(Integer idUsuario, String usuario);

    long countByCuentaIgnoreCase(String usuario);
	long countByIdUsuarioNotAndCuentaIgnoreCase(Integer idUsuario, String cuenta);

    long countByCorreoPrincipalIgnoreCase(String correoPrincipal);
	long countByIdUsuarioNotAndCorreoPrincipalIgnoreCase(Integer idUsuario, String correoPrincipal);

    // KGC-AUTO-OTROS: lo que está debajo no se reemplazará al regenerar

    @Query("SELECT DISTINCT rr.recurso.idRecurso "
    + " FROM RolRecurso rr "
    + " WHERE rr.rol IN (select ur.rol from UsuarioRol ur where ur.usuario = :usuario) ")
    List<Integer> getRecursosPermitidos(@Param("usuario") Usuario usuario);

    //encode(to_char(numero_entero, '999999999999'), 'base64') AS base64
    @Query("SELECT DISTINCT rr.recurso.idRecursoBase16 "
        + " FROM RolRecurso rr "
        + " WHERE rr.rol IN (select ur.rol from UsuarioRol ur where ur.usuario = :usuario) ")
    List<String> getRecursosPermitidosBase16(@Param("usuario") Usuario usuario);

     @Query("SELECT DISTINCT rr.recurso.idRecurso "
    + " FROM RolRecurso rr "
    + " WHERE rr.rol IN (select ur.rol from UsuarioRol ur where ur.usuario = :usuario AND ur.codigoEmpresaCore = :codigoEmpresaCore) ")
    List<Integer> getRecursosPermitidosEmpresaCore(@Param("usuario") Usuario usuario, @Param("codigoEmpresaCore") String empresaCore);

    //encode(to_char(numero_entero, '999999999999'), 'base64') AS base64
    @Query("SELECT DISTINCT rr.recurso.idRecursoBase16 "
        + " FROM RolRecurso rr "
        + " WHERE rr.rol IN (select ur.rol from UsuarioRol ur where ur.usuario = :usuario AND ur.codigoEmpresaCore = :codigoEmpresaCore) ")
    List<String> getRecursosPermitidosEmpresaCoreBase16(@Param("usuario") Usuario usuario, @Param("codigoEmpresaCore") String empresaCore);


}
