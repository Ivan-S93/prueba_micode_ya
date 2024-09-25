package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.AuditoriaEspecifica;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface AuditoriaEspecificaDao extends GenericDAOInterface<AuditoriaEspecifica, Integer>{

  	//lectura
	public AuditoriaEspecifica getById(Integer idAuditoriaEspecifica);
	public AuditoriaEspecifica getByExample(AuditoriaEspecifica auditoriaEspecifica);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica);

    //transaccion
    public AuditoriaEspecifica agregar(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica);
    public AuditoriaEspecifica modificar(InfoAuditoria infoAuditoria, AuditoriaEspecifica auditoriaEspecifica);
    public AuditoriaEspecifica eliminarPorId(InfoAuditoria infoAuditoria, Integer idAuditoriaEspecifica);

    // Otros


} 
