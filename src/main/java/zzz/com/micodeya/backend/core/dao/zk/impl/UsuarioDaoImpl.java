package zzz.com.micodeya.backend.core.dao.zk.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import zzz.com.micodeya.backend.core.exception.KwfAuthException;
import zzz.com.micodeya.backend.core.exception.ValidacionException;
import zzz.com.micodeya.backend.core.service.JeSecurityService;
import zzz.com.micodeya.backend.core.util.security.InfoAuditoria;
import zzz.com.micodeya.backend.core.util.security.InicioSesionDto;
import zzz.com.micodeya.backend.core.util.security.UsuarioSesionInterno;
import zzz.com.micodeya.backend.core.util.JeBoot;
import zzz.com.micodeya.backend.core.dao.GenericDAO;

import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioJpa;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioRolDao;
import zzz.com.micodeya.backend.core.dao.zk.UsuarioDao;

@Slf4j
@Service
public class UsuarioDaoImpl extends GenericDAO<Usuario, Integer> implements UsuarioDao {

    @Autowired
    private UsuarioJpa jpa;

    @Autowired
    UsuarioRolDao usuarioRolDao;

    public UsuarioDaoImpl() {
        referenceId = "idUsuario";
        atributosDefault = "infoAuditoria,idUsuario,cuenta,usuario,alias,nombre,apellido,correoPrincipal,telefono,fechaNacimientoMask,timeOutSesion,estado,confirmarCorreo,confirmarTelefono,tipoRegistro,avatar,imagenPortada,avatarUrl,imagenPortadaUrl";

        // atributos que no estan en el default. Debe iniciar con "," o estar vacio
        atributosExtras = "";

    }

    @Override
    protected Class<Usuario> getEntityBeanType() {
        return Usuario.class;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getById(Integer idUsuario) {
        return jpa.findById(idUsuario)
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado", "idUsuario", idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getByExample(Usuario usuario) {
        return jpa.findOne(Example.of(usuario, ExampleMatcher.matching().withIgnoreCase())).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> verificacionBasica(InfoAuditoria infoAuditoria, Usuario usuario) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = usuario.getIdUsuario() != null;

         // Verficar si ya existe usuario
         if ((!modificar && jpa.countByUsuarioIgnoreCase(usuario.getUsuario()) > 0)
         || (modificar && jpa.countByIdUsuarioNotAndUsuarioIgnoreCase(
                 usuario.getIdUsuario(), usuario.getUsuario()) > 0)) {
     errorValList.add("Mismo usuario ya existe, elige otro.");
 }

        // Verficar si ya existe cuenta
        if ((!modificar && jpa.countByCuentaIgnoreCase(usuario.getCuenta()) > 0)
                || (modificar && jpa.countByIdUsuarioNotAndCuentaIgnoreCase(
                        usuario.getIdUsuario(), usuario.getCuenta()) > 0)) {
            errorValList.add("Misma cuenta ya existe, elige otro.");
        }

       
        if(Strings.isNotEmpty(usuario.getCorreoPrincipal())){
            if ((!modificar && jpa.countByCorreoPrincipalIgnoreCase(usuario.getCorreoPrincipal()) > 0)
                    || (modificar && jpa.countByIdUsuarioNotAndCorreoPrincipalIgnoreCase(
                            usuario.getIdUsuario(), usuario.getCorreoPrincipal()) > 0)) {
                errorValList.add("Correo ya se encuentra registrado en otra cuenta, elige otro.");
            }
        }

        if(errorValList.size()==0)
            errorValList.addAll(verificacionAdicional(infoAuditoria, usuario));

        return errorValList;

    }


    @Override
    @Transactional(readOnly = true)
    public boolean verificarTieneAvatar(String usuario) {

        Usuario usuarioEjemplo = new Usuario();
        usuarioEjemplo.setUsuario(usuario);
        Usuario usuarioExistente = getByExample(usuarioEjemplo);

        if(usuarioExistente==null){
            throw new ValidacionException("Usuario "+usuarioEjemplo.getUsuario()+" no existe");
        }
        
        //tiene avatar 
        if(usuarioExistente.getAvatar().length()>5){
            return true;
        }

        return false;

    }

    private void setearDatosDefault(Usuario usuario) {

        usuario.setTimeOutSesion(JeBoot.nvl(usuario.getTimeOutSesion(), 30));
        usuario.setEstado(JeBoot.nvl(usuario.getEstado(), "ACT"));
        usuario.setConfirmarCorreo(JeBoot.nvl(usuario.getConfirmarCorreo(), "N"));
        usuario.setAvatar(JeBoot.nvl(usuario.getAvatar(), "[]"));
        usuario.setImagenPortada(JeBoot.nvl(usuario.getImagenPortada(), "[]"));

        usuario.setUsuario(usuario.getUsuario().toLowerCase());

        if(usuario.getCorreoPrincipal()!=null){
            usuario.setCorreoPrincipal(usuario.getCorreoPrincipal().toLowerCase());
        }

    }

    private void setearDatosModificar(InfoAuditoria infoAuditoria, Usuario usuarioDto, Usuario usuarioExistente) {

        if (!customModificar(infoAuditoria, usuarioDto, usuarioExistente))
            return;

        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setApellido(usuarioDto.getApellido());
        usuarioExistente.setCorreoPrincipal(usuarioDto.getCorreoPrincipal());
        usuarioExistente.setTelefono(usuarioDto.getTelefono());
        usuarioExistente.setFechaNacimiento(usuarioDto.getFechaNacimiento());
        usuarioExistente.setTimeOutSesion(usuarioDto.getTimeOutSesion());

        usuarioExistente.setEstado(usuarioDto.getEstado());
        usuarioExistente.setConfirmarCorreo(usuarioDto.getConfirmarCorreo());
        usuarioExistente.setConfirmarTelefono(usuarioDto.getConfirmarTelefono());
        usuarioExistente.setTipoRegistro(usuarioDto.getTipoRegistro());
        usuarioExistente.setAvatar(usuarioDto.getAvatar());
        usuarioExistente.setImagenPortada(usuarioDto.getImagenPortada());

        //temporal para poder modificar
        //97c66adc148c21c01d0ab12bd803a019e29b9542170c2a0b82170ce700e028a4
        if (usuarioDto.getContrasenha() != null)
            usuarioExistente.setContrasenha(securityService.encriptar(usuarioExistente.getContrasenha(), usuarioDto.getUsuario()));

        setearDatosDefault(usuarioExistente);

    }

    @Override
    @Transactional
    public Usuario agregar(InfoAuditoria infoAuditoria, Usuario usuario) {

        setearDatosDefault(usuario);

        usuario.setCuenta(usuario.getUsuario().toLowerCase());
        //usuario.setAlias(usuario.getUsuario().toLowerCase());
        jpa.save(this.preGuardado(usuario, infoAuditoria));

        usuarioRolDao.agregarModificar(infoAuditoria, usuario.getUsuarioRolList(), usuario);

        return usuario;
    }

    @Override
    @Transactional
    public Usuario modificar(InfoAuditoria infoAuditoria, Usuario usuario) {

        Usuario usuarioExistente = getById(usuario.getIdUsuario());

        setearDatosModificar(infoAuditoria, usuario, usuarioExistente);

        jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));

        usuarioRolDao.agregarModificar(infoAuditoria, usuario.getUsuarioRolList(), usuario);

        return usuarioExistente;

    }


    @Override
    @Transactional
    public void eliminarPorId(InfoAuditoria infoAuditoria, Integer idUsuario) {

        jpa.findById(idUsuario)
                .orElseThrow(
                        () -> new ValidacionException("Usuario no encontrado para eliminar", "idUsuario", idUsuario));

        jpa.deleteById(this.preEliminado(idUsuario, infoAuditoria));
    }

    // KGC-AUTO-OTROS: lo que está debajo no se reemplazará al regenerar

    @Autowired
    JeSecurityService securityService;

    @Transactional(readOnly = true)
    private List<String> verificacionAdicional(InfoAuditoria infoAuditoria, Usuario usuario) {

        List<String> errorValList = new LinkedList<String>();
        boolean modificar = usuario.getIdUsuario() != null;

        if (usuario.getUsuario().contains(" ")) {
            errorValList.add("El usuario no debe contener espacio");
        }

        if (usuario.getUsuario().length() < 4) {
            errorValList.add("El usuario  debe tener al menos 4 caracteres");
        }

        if (usuario.getUsuario().equalsIgnoreCase("base")) {
            errorValList.add("Usuario base ya existe");
        }
        if (usuario.getUsuario().equalsIgnoreCase("admin")) {
            errorValList.add("Usuario admin ya existe");
        }
        if (usuario.getUsuario().equalsIgnoreCase("administrador")) {
            errorValList.add("Usuario administrador ya existe");
        }

        return errorValList;

    }

    /**
     * Para aplicar reglas de negocio custom con los datos nuevos y existente
     * 
     * @return true para continuar setearDatosModificar y false para detener
     */
    private boolean customModificar(InfoAuditoria infoAuditoria, Usuario usuarioDto, Usuario usuarioExistente) {

        if (!usuarioDto.getUsuario().equalsIgnoreCase(usuarioExistente.getUsuario())) {
            throw new ValidacionException("Usuario no puede ser modificado");
        }

        // si hay contrasena nueva cambiar, sino usar el que ya esta
        if (usuarioDto.getContrasenha() != null) {
            usuarioExistente.setContrasenha(usuarioDto.getContrasenha());
        } else {
            usuarioDto.setContrasenha(usuarioExistente.getContrasenha());

        }

        return true;
    }

    private UsuarioSesionInterno procesarUsuarioAcceso(HttpServletRequest request, Usuario usuario, String empresaCore) {
        UsuarioSesionInterno usuarioSesion = null;

        if (usuario.getEstado().equals("SUS")) {
            log.warn(KwfAuthException.CodigoEstado.USUARIO_SUSPENDIDO + " usuario=" + usuario.getUsuario()
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario suspendido", true, KwfAuthException.CodigoEstado.USUARIO_SUSPENDIDO);
        }

        if (!usuario.getEstado().equals("ACT")) {
            log.warn(KwfAuthException.CodigoEstado.USUARIO_INACTIVO + " usuario=" + usuario.getUsuario()
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario inactivo", true, KwfAuthException.CodigoEstado.USUARIO_INACTIVO);
        }

        empresaCore = empresaCore.toUpperCase();

        List<Integer> recursosPemitidosList = jpa.getRecursosPermitidosEmpresaCore(usuario, empresaCore);
        String idRecursosConcat = recursosPemitidosList.stream()
                .map(o -> o.toString()).collect(Collectors.joining(","));
        System.out.println("idRecursosConcat: " + idRecursosConcat);

        List<String> recursosPemitidosBase16List = jpa.getRecursosPermitidosEmpresaCoreBase16(usuario, empresaCore);
        String idRecursosBase16Concat = recursosPemitidosBase16List.stream()
                .map(o -> o.toString().toUpperCase()).collect(Collectors.joining(","));

        // System.out.println("idRecursosBase16Concat: " + idRecursosBase16Concat);

        if (recursosPemitidosList == null || recursosPemitidosList.size() == 0) {
            log.warn(KwfAuthException.CodigoEstado.USUARIO_SIN_PRIVILEGIO + " usuario=" + usuario.getUsuario()
                    + " empresaCore=" + empresaCore
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario sin privilegios", true,
                    KwfAuthException.CodigoEstado.USUARIO_SIN_PRIVILEGIO);
        }

        usuarioSesion = new UsuarioSesionInterno();
        usuarioSesion.setCuenta(usuario.getCuenta());
        usuarioSesion.setAlias(usuario.getAlias());
        usuarioSesion.setEmpresaCore(empresaCore);
        usuarioSesion.setIdSesion(5);
        usuarioSesion.setIdUsuario(usuario.getIdUsuario());
        usuarioSesion.setUsuario(usuario.getUsuario());
        usuarioSesion.setNombre(usuario.getNombre());
        usuarioSesion.setApellido(usuario.getApellido());
        usuarioSesion.setEmail(usuario.getCorreoPrincipal());
        usuarioSesion.setEstado(usuario.getEstado());
        usuarioSesion.setIdRecursoConcat("," + idRecursosConcat + ",");
        usuarioSesion.setIdRecursoConcatBase16("," + idRecursosBase16Concat + ",");
        usuarioSesion.setTimeOutSesion(usuario.getTimeOutSesion());

        return usuarioSesion;

    }

    @Override
    @Transactional
    public UsuarioSesionInterno obtenerUsuarioSesion(HttpServletRequest request, InicioSesionDto datosInicioSesion) {

        Usuario usuarioEjemplo = new Usuario();

        //es un correo
        if(datosInicioSesion.getUser().contains("@")){
            usuarioEjemplo.setCorreoPrincipal(datosInicioSesion.getUser().toLowerCase().trim());
        }else{
            usuarioEjemplo.setUsuario(datosInicioSesion.getUser().toLowerCase().trim());
        }

        String empresaCore = datosInicioSesion.getEmpresaCore() == null ? "EMPKUAA" : datosInicioSesion.getEmpresaCore();

        Usuario usuario = getByExample(usuarioEjemplo);

        if (usuario == null) {
            log.warn(KwfAuthException.CodigoEstado.USUARIO_NO_ENCONTRADO + " usuario=" + datosInicioSesion.getUser()
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario o contraseña incorrecta", true,
                    KwfAuthException.CodigoEstado.USUARIO_NO_ENCONTRADO);
        }

        if (!securityService.encriptarComparar(datosInicioSesion.getPassword(),
            usuario.getUsuario().toLowerCase(), usuario.getContrasenha())) {

            log.warn(KwfAuthException.CodigoEstado.PASWORD_INCORRECTO + " usuario=" + datosInicioSesion.getUser()
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario o contraseña incorrecta", true,
                    KwfAuthException.CodigoEstado.PASWORD_INCORRECTO);
        }

        return procesarUsuarioAcceso(request, usuario, empresaCore);

    }

    @Override
    @Transactional
    public UsuarioSesionInterno obtenerUsuarioSesionPorUsuario(HttpServletRequest request, String nombreUsuario,
            String empresaCore) {

        Usuario ejemplo = new Usuario();
        ejemplo.setUsuario(nombreUsuario);
        Usuario usuario = getByExample(ejemplo);

        if (usuario == null) {
            log.warn(KwfAuthException.CodigoEstado.USUARIO_NO_ENCONTRADO + " usuario=" + nombreUsuario
                    + ", request=" + JeBoot.getHttpRequestInfo(request));
            throw new KwfAuthException("Usuario o contraseña incorrecta", true,
                    KwfAuthException.CodigoEstado.USUARIO_NO_ENCONTRADO);
        }

        return procesarUsuarioAcceso(request, usuario, empresaCore);
    }
 

    @Override
    @Transactional
    public Usuario modificarPassword(InfoAuditoria infoAuditoria, Integer idUsuario,String passwordActual, String passwordNuevo) {
        
        Usuario usuarioExistente = getById(idUsuario);

        //comparar si las contrasenas actuales coinciden
        if(!securityService.encriptarComparar(passwordActual, usuarioExistente.getUsuario(), usuarioExistente.getContrasenha())){
            throw new ValidacionException("Contraseña actual incorrrecta");
        }

        usuarioExistente.setContrasenha(passwordNuevo);
        jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));

        return usuarioExistente;

    }

    @Override
    @Transactional
    public Usuario recuperarPassword(InfoAuditoria infoAuditoria, Integer idUsuario, String passwordNuevo) {
        
        Usuario usuarioExistente = getById(idUsuario);

        usuarioExistente.setContrasenha(passwordNuevo);
        jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));

        return usuarioExistente;

    }

        
    @Override
    @Transactional
    public Usuario modificarDatosCuenta(InfoAuditoria infoAuditoria, Usuario usuario) {

        Usuario usuarioExistente = getById(usuario.getIdUsuario());
    
        //Verificar que el usaurio que se desea modificar es el mismo de la sesion
        if(!usuarioExistente.getIdUsuario().equals(infoAuditoria.getIdUsuario())){
            throw new ValidacionException("No se pudo comprobar que la sesión corresponde al usuario que desea modificar");
        }

        //setear solo algunos permitidos

        usuarioExistente.setAlias(usuario.getAlias());
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        //usuarioExistente.setCorreoPrincipal(usuario.getCorreoPrincipal());
        //usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
    
        usuarioExistente.setAvatar(usuario.getAvatar());
        usuarioExistente.setImagenPortada(usuario.getImagenPortada());

        setearDatosDefault(usuarioExistente);

        jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));

        return usuarioExistente;

    }

    @Override
    @Transactional
    public Usuario modificarAvatarCuenta(InfoAuditoria infoAuditoria, Usuario usuario) {

        Usuario usuarioEjemplo = new Usuario();
        usuarioEjemplo.setUsuario(usuario.getUsuario());
        Usuario usuarioExistente = getByExample(usuarioEjemplo);


        if(usuarioExistente==null){
            throw new ValidacionException("Usuario "+usuarioEjemplo.getUsuario()+" no existe");
        }
    
        //Verificar que el usaurio que se desea modificar es el mismo de la sesion
        if(!usuarioExistente.getIdUsuario().equals(infoAuditoria.getIdUsuario())){
            //temporal, solo para importaciones masivas
            // throw new ValidacionException("No se pudo comprobar que la sesión corresponde al usuario que desea modificar");
        }

        //solo cuando no tiene avatar
        if(usuarioExistente.getAvatar().length()<5){
            usuarioExistente.setAvatar(usuario.getAvatar());
            jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));
        }

        //setearDatosDefault(usuarioExistente);


        return usuarioExistente;

    }

    @Override
    @Transactional
    public Usuario suspenderCuenta(InfoAuditoria infoAuditoria, Integer idUsuario,String passwordActual) {
        
        Usuario usuarioExistente = getById(idUsuario);

        //comparar si las contrasenas actuales coinciden
        if(!securityService.encriptarComparar(passwordActual, usuarioExistente.getUsuario(), usuarioExistente.getContrasenha())){
            throw new ValidacionException("Contraseña incorrrecta");
        }

        usuarioExistente.setEstado("SUS");
        usuarioExistente.setFechaHoraSuspension(new Date());
        jpa.save(this.preGuardado(usuarioExistente, infoAuditoria));

        return usuarioExistente;

    }

}
