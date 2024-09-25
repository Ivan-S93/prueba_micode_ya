package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.dao.GenericDAO;

import zzz.com.micodeya.backend.core.entities.zk.Recurso;
import zzz.com.micodeya.backend.core.dao.zk.RecursoJpa;
import zzz.com.micodeya.backend.core.dao.zk.RecursoDao;



@Service
public class RecursoDaoImpl extends GenericDAO<Recurso, Integer> implements RecursoDao {

    @Autowired
    private RecursoJpa jpa;
    
    public RecursoDaoImpl(){
        referenceId="idRecurso";
        atributosDefault="infoAuditoria,idRecurso,nombre,grupo,descripcion,tipo,activo";

        //atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras="";
        
    }
    
    @Override
    protected Class<Recurso> getEntityBeanType() {
        return Recurso.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public Recurso getById(Integer idRecurso) {
        return jpa.findById(idRecurso)
            .orElseThrow(() -> new ValidacionException("Recurso no encontrado", "idRecurso", idRecurso));
    }

    @Override
    @Transactional(readOnly = true) 
    public Recurso getByExample(Recurso recurso) {	
        return jpa.findOne(Example.of(recurso,ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String>  verificacionBasica(InfoAuditoria infoAuditoria, Recurso recurso) {

        List<String> errorValList=new LinkedList<String>();
        boolean modificar=recurso.getIdRecurso()!=null;

        if ((!modificar && jpa.countByNombreIgnoreCase(recurso.getNombre()) > 0)
				 || (modificar && jpa.countByIdRecursoNotAndNombreIgnoreCase(
					recurso.getIdRecurso(), recurso.getNombre()) > 0)) {
			errorValList.add("Recurso con mismo nombre existente");
		}

        return errorValList;

    }


    private void setearDatosDefault(Recurso recurso){

        recurso.setActivo(JeBoot.nvl(recurso.getActivo(),true));

    }

    private void setearDatosModificar(Recurso recursoDto, Recurso recursoExistente){

        //recursoExistente.setRecurso(recursoDto.getRecurso());
        recursoExistente.setNombre(recursoDto.getNombre());
		recursoExistente.setGrupo(recursoDto.getGrupo());
		recursoExistente.setDescripcion(recursoDto.getDescripcion());
		recursoExistente.setTipo(recursoDto.getTipo());
		recursoExistente.setActivo(recursoDto.getActivo());

        setearDatosDefault(recursoExistente);

    }
        
    @Override 
    @Transactional
    public Recurso agregar(InfoAuditoria infoAuditoria, Recurso recurso) {

        setearDatosDefault(recurso);

        jpa.save(this.preGuardado(recurso, infoAuditoria));

        return recurso; 
    }

    @Override
    @Transactional
    public Recurso modificar(InfoAuditoria infoAuditoria, Recurso recurso) {

        Recurso recursoExistente = getById(recurso.getIdRecurso());
        
        setearDatosModificar(recurso, recursoExistente);

        jpa.save(this.preGuardado(recursoExistente, infoAuditoria));

        return recursoExistente;

    }
    
    @Override
    @Transactional
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idRecurso) {

        jpa.findById(idRecurso)
                .orElseThrow(() -> new ValidacionException("Recurso no encontrado para eliminar", "idRecurso", idRecurso));

        jpa.deleteById(this.preEliminado(idRecurso,infoAuditoria));
    }	

    


} 
