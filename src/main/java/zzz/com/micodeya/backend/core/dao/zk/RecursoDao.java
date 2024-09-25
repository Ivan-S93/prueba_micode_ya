package zzz.com.micodeya.backend.core.dao.zk;

import java.util.List;

import zzz.com.micodeya.backend.core.dao.GenericDAOInterface;
import zzz.com.micodeya.backend.core.entities.zk.Recurso;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;


public interface RecursoDao extends GenericDAOInterface<Recurso, Integer>{

  	//lectura
	public Recurso getById(Integer idRecurso);
	public Recurso getByExample(Recurso recurso);
	public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Recurso recurso);

    //transaccion
    public Recurso agregar(InfoAuditoria infoAuditoria, Recurso recurso);
    public Recurso modificar(InfoAuditoria infoAuditoria, Recurso recurso);
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRecurso);

   // 


} 
