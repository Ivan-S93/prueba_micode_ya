package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.NotificacionEnviada;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface NotificacionEnviadaDao extends GenericDAOInterface<NotificacionEnviada, Integer>{

  	//lectura
	public NotificacionEnviada getById(Integer idNotificacionEnviada);
	public NotificacionEnviada getByExample(NotificacionEnviada notificacionEnviada);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada);

    //transaccion
    public NotificacionEnviada agregar(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada);
    public NotificacionEnviada modificar(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada);
    public NotificacionEnviada eliminarPorId(InfoAuditoria infoAuditoria, Integer idNotificacionEnviada);

    // KGC-NOREPLACE-OTROS-INI
    public NotificacionEnviada marcarComoVisto(InfoAuditoria infoAuditoria, Integer idNotificacionEnviada);
    // KGC-NOREPLACE-OTROS-FIN
    
} 
