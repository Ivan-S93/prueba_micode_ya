package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.dao.zk.LoginLogDao;
import zzz.com.micodeya.backend.core.dao.zk.LoginLogJpa;
import zzz.com.micodeya.backend.core.entities.zk.LoginLog;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class LoginLogDaoImpl extends GenericDAO<LoginLog, Integer> implements LoginLogDao {

    @Autowired
    private LoginLogJpa jpa;
    
    public LoginLogDaoImpl() {
        referenceId = "idLoginLog";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idLoginLog,usuario,tipo,requestInfo,exitoso,ip,motivo,activo";

        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idLoginLog","ID", "usuario","Usuario", "tipo","Tipo", "requestInfo","Request Info", "exitoso","Exitoso", "ip","Ip", "motivo","Motivo", "activo","Activo"));
        
    }
    
    @Override
    protected Class<LoginLog> getEntityBeanType() {
        return LoginLog.class;
    }					

    @Override
    @Transactional(readOnly = true)
    public LoginLog getById(Integer idLoginLog) {
        return jpa.findById(idLoginLog)
            .orElseThrow(() -> new ValidacionException("Login Log no encontrado", "idLoginLog", idLoginLog));
    }

    @Override
    @Transactional(readOnly = true) 
    public LoginLog getByExample(LoginLog loginLog) {	
        return jpa.findOne(Example.of(loginLog, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, LoginLog loginLog) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = loginLog.getIdLoginLog()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, loginLog));

        return errorValList;

    }


    private void setearDatosDefault(InfoAuditoria infoAuditoria, LoginLog loginLog){

        

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, LoginLog loginLogDto, LoginLog loginLogExistente){

        loginLogExistente.setUsuario(loginLogDto.getUsuario());
		loginLogExistente.setTipo(loginLogDto.getTipo());
		loginLogExistente.setRequestInfo(loginLogDto.getRequestInfo());
		loginLogExistente.setExitoso(loginLogDto.getExitoso());
		loginLogExistente.setIp(loginLogDto.getIp());
		loginLogExistente.setMotivo(loginLogDto.getMotivo());
		loginLogExistente.setActivo(loginLogDto.getActivo());

        setearDatosDefault(infoAuditoria, loginLogExistente);

    }
        
    @Override 
    @Transactional
    public LoginLog agregar(InfoAuditoria infoAuditoria, LoginLog loginLog) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, loginLog);
        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(loginLog, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return loginLog; 
    }

    @Override
    @Transactional
    public LoginLog modificar(InfoAuditoria infoAuditoria, LoginLog loginLog) {

        LoginLog loginLogExistente = getById(loginLog.getIdLoginLog());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, loginLog, loginLogExistente);
        //preModificar(infoAuditoria, loginLog, loginLogExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(loginLogExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return loginLogExistente;

    }
    
    @Override
    @Transactional
    public LoginLog eliminarPorId(InfoAuditoria infoAuditoria, Integer idLoginLog) {

        LoginLog loginLogExistente = jpa.findById(idLoginLog)
                .orElseThrow(() -> new ValidacionException("Login Log no encontrado para eliminar", "idLoginLog", idLoginLog));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idLoginLog,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return loginLogExistente;
    }	



    // KGC-NOREPLACE-OTROS-INI
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, LoginLog loginLog) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = loginLog.getIdLoginLog() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    // KGC-NOREPLACE-OTROS-FIN
}
