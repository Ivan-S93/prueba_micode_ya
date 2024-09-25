package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.Configuracion;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface ConfiguracionDao extends GenericDAOInterface<Configuracion, Integer>{

  	//lectura
	public Configuracion getById(Integer idConfiguracion);
	public Configuracion getByExample(Configuracion configuracion);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Configuracion configuracion);

    //transaccion
    public Configuracion agregar(InfoAuditoria infoAuditoria, Configuracion configuracion);
    public Configuracion modificar(InfoAuditoria infoAuditoria, Configuracion configuracion);
    public Configuracion eliminarPorId(InfoAuditoria infoAuditoria, Integer idConfiguracion);

    // Otros
    public Configuracion getByCodigoSubcodigoCuenta(String empresaCore, String codigo, String subcodigo, String cuenta);


} 
