package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.EliminadoIntencion;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface EliminadoIntencionDao extends GenericDAOInterface<EliminadoIntencion, Integer>{

  	//lectura
	public EliminadoIntencion getById(Integer idEliminadoIntencion);
	public EliminadoIntencion getByExample(EliminadoIntencion eliminadoIntencion);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion);

    //transaccion
    public EliminadoIntencion agregar(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion);
    public EliminadoIntencion modificar(InfoAuditoria infoAuditoria, EliminadoIntencion eliminadoIntencion);
    public EliminadoIntencion eliminarPorId(InfoAuditoria infoAuditoria, Integer idEliminadoIntencion);

    // Otros


} 
