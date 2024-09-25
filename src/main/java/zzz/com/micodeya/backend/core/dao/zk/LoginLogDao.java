package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.LoginLog;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

public interface LoginLogDao extends GenericDAOInterface<LoginLog, Integer>{

  	//lectura
	public LoginLog getById(Integer idLoginLog);
	public LoginLog getByExample(LoginLog loginLog);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, LoginLog loginLog);

    //transaccion
    public LoginLog agregar(InfoAuditoria infoAuditoria, LoginLog loginLog);
    public LoginLog modificar(InfoAuditoria infoAuditoria, LoginLog loginLog);
    public LoginLog eliminarPorId(InfoAuditoria infoAuditoria, Integer idLoginLog);



    // KGC-NOREPLACE-OTROS-INI
    // KGC-NOREPLACE-OTROS-FIN
    
} 
