package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "LOG_ACCESO_USUARIO", schema = "DELPHOS_MODACC")
public class LogAccesoUsuario {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "OID", nullable = false)
	    private Long oid;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "OID_TIPO_ACCESO_APLICACION", nullable = false)
	    private TipoAccesoAplicacion oidTipoAccesoAplicacion;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "OID_USUARIO", nullable = false)
	    private Usuario oidUsuario;
	    
	    @Column(name = "N_ACCESOS")
	    private Long nAccesos;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "F_ULTIMO_ACCESO")
	    private Date fUltimoAcceso;
	    
	    @Column(name = "C_USUCREACION")
	    private Long cUsuCreacion;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "F_CREACION")
	    private Date fCreacion;
	    
	    @Column(name = "C_USUACTUALIZA")
	    private Long cUsuActualiza;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "F_ACTUALIZA")
	    private Date fActualiza;
	    
	   
}
