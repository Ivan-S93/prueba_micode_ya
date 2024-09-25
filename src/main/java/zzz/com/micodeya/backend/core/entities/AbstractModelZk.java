package zzz.com.micodeya.backend.core.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
//public abstract class AbstractModelZk  implements Serializable{
public abstract class AbstractModelZk{

    
    @NotEmpty
    @Column(name = "zk_usr_crea")
    private String zkUsuarioCreacion;

    @Formula("COALESCE(SPLIT_PART(zk_usr_crea,'-',1),'')")
	private String zkUsuarioCreacionMask;
    
    @Column(name = "zk_usr_modi")
    private String zkUsuarioModificacion;

    @Formula("COALESCE(SPLIT_PART(zk_usr_modi,'-',1),'')")
	private String zkUsuarioModificacionMask;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "zk_fec_crea")
    private Date zkFechaCreacion;

    @Formula("TO_CHAR(zk_fec_crea, 'dd/MM/yyyy HH24:MI:SS')")
    private String zkFechaCreacionMask;

    @Formula("TO_CHAR(zk_fec_crea, 'dd/MM/yyyy HH24:MI')")
    private String zkFechaCreacionHHMM;
	
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "zk_ult_modi")
    private Date zkUltimaModificacion;


    @Formula("TO_CHAR(zk_ult_modi, 'dd/MM/yyyy HH24:MI:SS')")
    private String zkUltimaModificacionMask;
	
	@Formula("TO_CHAR(zk_ult_modi, 'dd/MM/yyyy HH24:MI')")
    private String zkUltimaModificacionHHMM;
    
    @NotEmpty
    @Column(name = "zk_empresa_core")
    protected String zkEmpresaCore;

    @NotEmpty
    @Column(name = "zk_cuenta")
    protected String zkCuenta;

    // @NotNull
    @Column(name = "zk_eliminado")
    protected Boolean zkEliminado;


    @NotEmpty
    @Column(name = "zk_uuid")
    private String zkUuid;

    @Formula("COALESCE(SPLIT_PART(zk_usr_crea,'-',1),'-') || ',' ||"  //usuario creacion
            +" TO_CHAR(zk_fec_crea, 'dd/MM/yyyy HH24:MI:SS') || ',' ||" //fecha creacion
            +"  COALESCE(SPLIT_PART(zk_usr_modi,'-',1),'-') || ',' ||" //usuario modificacion
            +" TO_CHAR(zk_ult_modi, 'dd/MM/yyyy HH24:MI:SS')" )  //ultima modificacion
    private String infoAuditoria;

}
