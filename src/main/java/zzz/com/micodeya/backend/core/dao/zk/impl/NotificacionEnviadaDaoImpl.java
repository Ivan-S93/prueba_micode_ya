package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.dao.GenericDAO;
import zzz.com.micodeya.backend.core.dao.zk.FcmTokenDao;
import zzz.com.micodeya.backend.core.dao.zk.NotificacionEnviadaDao;
import zzz.com.micodeya.backend.core.dao.zk.NotificacionEnviadaJpa;
import zzz.com.micodeya.backend.core.entities.zk.NotificacionEnviada;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.service.fcm.FirebaseMessagingService;
import zzz.com.micodeya.backend.core.service.fcm.dto.SendOneFcmDto;
import zzz.com.micodeya.backend.core.util.FilterAux;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;

@Slf4j
@Service
public class NotificacionEnviadaDaoImpl extends GenericDAO<NotificacionEnviada, Integer> implements NotificacionEnviadaDao {

    @Autowired
    private NotificacionEnviadaJpa jpa;
    
    public NotificacionEnviadaDaoImpl() {
        referenceId = "idNotificacionEnviada";
        atributosDefault = "infoAuditoria,zkUltimaModificacionMask,idNotificacionEnviada,tipo,cuenta,fechaHoraEnviadoMask,token,titulo,contenido,urlImagen,cargaUtil,visto,fechaHoraVistoMask,fechaHoraVencimientoMask,observacion";

        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-INI
        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = ",aliasCreadoPor";
        // KGC-NOREPLACE-ATRIBUTOS-EXTRAS-FIN

        // nombreAtributo, etiqueta (clave, valor)
		etiquetaAtributos.putAll(Map.of("idNotificacionEnviada","ID", "tipo","Tipo", "cuenta","Cuenta", "fechaHoraEnviado","Fecha Hora Enviado", "haHoraEnviadoMask","Fecha Hora Enviado", "token","Token", "titulo","Titulo", "contenido","Contenido", "urlImagen","Url Imagen", "cargaUtil","Carga Util"));
		etiquetaAtributos.putAll(Map.of("visto","Visto", "fechaHoraVisto","Fecha Hora Visto", "haHoraVistoMask","Fecha Hora Visto", "fechaHoraVencimiento","Fecha  Hora Vencimiento", "haHoraVencimientoMask","Fecha  Hora Vencimiento", "observacion","Observación"));
        
    }
    
    @Override
    protected Class<NotificacionEnviada> getEntityBeanType() {
        return NotificacionEnviada.class;
    }

    // KGC-NOREPLACE-OTROS-INI

    @Autowired
    FcmTokenDao fcmTokenDao;

    @Autowired(required = false)
    FirebaseMessagingService firebaseMessagingService;
    
    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = notificacionEnviada.getIdNotificacionEnviada() != null;
        List<FilterAux> filterList = null;


        return errorValList;
    }

    @Override
    @Transactional
    public NotificacionEnviada marcarComoVisto(InfoAuditoria infoAuditoria, Integer idNotificacionEnviada) {

        NotificacionEnviada notificacionEnviadaExistente = getById(idNotificacionEnviada);
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        // setearDatosModificar(infoAuditoria, notificacionEnviada, notificacionEnviadaExistente);
        // preModificar(infoAuditoria, notificacionEnviada, notificacionEnviadaExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        notificacionEnviadaExistente.setVisto(true);
        notificacionEnviadaExistente.setFechaHoraVisto(new Date());

        jpa.save(this.preGuardado(notificacionEnviadaExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return notificacionEnviadaExistente;

    }

    // KGC-NOREPLACE-OTROS-FIN					

    @Override
    @Transactional(readOnly = true)
    public NotificacionEnviada getById(Integer idNotificacionEnviada) {
        return jpa.findById(idNotificacionEnviada)
            .orElseThrow(() -> new ValidacionException("Notificación Enviada no encontrada", "idNotificacionEnviada", idNotificacionEnviada));
    }

    @Override
    @Transactional(readOnly = true) 
    public NotificacionEnviada getByExample(NotificacionEnviada notificacionEnviada) {	
        return jpa.findOne(Example.of(notificacionEnviada, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }
                    

    //Se ejecuta en el Rest, antes de llamar al DAO
    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = notificacionEnviada.getIdNotificacionEnviada()!=null;
        List<FilterAux> filterList = null;

        


        errorValList.addAll(verificacionAdicional(infoAuditoria, notificacionEnviada));

        return errorValList;

    }


    // Setea los valores de los datos por default si el valor ingresado es nulo
    private void setearDatosDefault(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada){

        notificacionEnviada.setVisto(JeBoot.nvl(notificacionEnviada.getVisto(),false));

    }

    // Setea los datos de la entidad nueva a la entidad recuperada de la BD
    private void setearDatosModificar(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviadaDto, NotificacionEnviada notificacionEnviadaExistente){

        notificacionEnviadaExistente.setTipo(notificacionEnviadaDto.getTipo());
		notificacionEnviadaExistente.setCuenta(notificacionEnviadaDto.getCuenta());
		notificacionEnviadaExistente.setFechaHoraEnviado(notificacionEnviadaDto.getFechaHoraEnviado());
		notificacionEnviadaExistente.setToken(notificacionEnviadaDto.getToken());
		notificacionEnviadaExistente.setTitulo(notificacionEnviadaDto.getTitulo());
		notificacionEnviadaExistente.setContenido(notificacionEnviadaDto.getContenido());
		notificacionEnviadaExistente.setUrlImagen(notificacionEnviadaDto.getUrlImagen());
		notificacionEnviadaExistente.setCargaUtil(notificacionEnviadaDto.getCargaUtil());
		notificacionEnviadaExistente.setVisto(notificacionEnviadaDto.getVisto());
		notificacionEnviadaExistente.setFechaHoraVisto(notificacionEnviadaDto.getFechaHoraVisto());
		notificacionEnviadaExistente.setFechaHoraVencimiento(notificacionEnviadaDto.getFechaHoraVencimiento());
		notificacionEnviadaExistente.setObservacion(notificacionEnviadaDto.getObservacion());

        setearDatosDefault(infoAuditoria, notificacionEnviadaExistente);

    }
        
    @Override
    @Transactional
    public NotificacionEnviada agregar(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) {

        // KGC-NOREPLACE-PRE-AGREGAR-INI
        setearDatosDefault(infoAuditoria, notificacionEnviada);

        //buscar fcmToken de cuenta
        String fcmToken = fcmTokenDao.getFcmTokenDeCuenta(notificacionEnviada.getCuenta());
        if (fcmToken != null) {
            try {
                // enviar notificacion
                SendOneFcmDto pushMsg = new SendOneFcmDto(
                        fcmToken, notificacionEnviada.getTitulo(), notificacionEnviada.getContenido());
                firebaseMessagingService.sendOneNotification(pushMsg);
                log.info("Push enviado");

            } catch (FirebaseMessagingException e) {
                log.error("Error al enviar push", e);
            }
        }


        // KGC-NOREPLACE-PRE-AGREGAR-FIN

        jpa.save(this.preGuardado(notificacionEnviada, infoAuditoria));

        // KGC-NOREPLACE-POS-AGREGAR-INI
        // KGC-NOREPLACE-POS-AGREGAR-FIN

        return notificacionEnviada; 
    }

    @Override
    @Transactional
    public NotificacionEnviada modificar(InfoAuditoria infoAuditoria, NotificacionEnviada notificacionEnviada) {

        NotificacionEnviada notificacionEnviadaExistente = getById(notificacionEnviada.getIdNotificacionEnviada());
        
        // KGC-NOREPLACE-PRE-MODIFICAR-INI
        setearDatosModificar(infoAuditoria, notificacionEnviada, notificacionEnviadaExistente);
        //preModificar(infoAuditoria, notificacionEnviada, notificacionEnviadaExistente);
        // KGC-NOREPLACE-PRE-MODIFICAR-FIN

        jpa.save(this.preGuardado(notificacionEnviadaExistente, infoAuditoria));

        // KGC-NOREPLACE-POS-MODIFICAR-INI
        // KGC-NOREPLACE-POS-MODIFICAR-FIN

        return notificacionEnviadaExistente;

    }
    
    @Override
    @Transactional
    public NotificacionEnviada eliminarPorId(InfoAuditoria infoAuditoria, Integer idNotificacionEnviada) {

        NotificacionEnviada notificacionEnviadaExistente = jpa.findById(idNotificacionEnviada)
                .orElseThrow(() -> new ValidacionException("Notificación Enviada no encontrada para eliminar", "idNotificacionEnviada", idNotificacionEnviada));


        // KGC-NOREPLACE-PRE-ELIMINAR-INI
        // KGC-NOREPLACE-PRE-ELIMINAR-FIN

        jpa.deleteById(this.preEliminado(idNotificacionEnviada,infoAuditoria));

        // KGC-NOREPLACE-POS-ELIMINAR-INI
        // KGC-NOREPLACE-POS-ELIMINAR-FIN

        return notificacionEnviadaExistente;
    }	

}
