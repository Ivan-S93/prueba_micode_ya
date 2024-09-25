package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.FcmToken;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface FcmTokenDao extends GenericDAOInterface<FcmToken, Integer>{

  	//lectura
	public FcmToken getById(Integer idFcmToken);
	public FcmToken getByExample(FcmToken fcmToken);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, FcmToken fcmToken);

    //transaccion
    public FcmToken agregar(InfoAuditoria infoAuditoria, FcmToken fcmToken);
    public FcmToken modificar(InfoAuditoria infoAuditoria, FcmToken fcmToken);
    public FcmToken eliminarPorId(InfoAuditoria infoAuditoria, Integer idFcmToken);

    // KGC-NOREPLACE-OTROS-INI

    public String getFcmTokenDeCuenta(String cuenta);

    // KGC-NOREPLACE-OTROS-FIN
    
} 
