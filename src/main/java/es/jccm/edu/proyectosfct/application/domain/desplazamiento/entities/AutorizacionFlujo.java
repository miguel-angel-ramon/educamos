package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUTORIZACIONES_FLUJO")
public class AutorizacionFlujo extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autflujo_seq")
    @SequenceGenerator(name = "autflujo_seq", sequenceName = "SQ_FCT_AUTORIZACIONESFLUJO", allocationSize = 1)
	@Column(name="ID_AUTORIZACION_FLUJO")
	private Long id;
	
	@Column(name="LG_REQADJUNTO")
	private Integer requiereAdjunto;
	
	@Column(name="LG_BORRADO")
	private Integer borrado;
	
	@Column(name="FH_BORRADO")
	private Date fechaBorrado;
	
	@Column(name="C_USUBORRADO")
	private Long usuarioBorrado;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_AUTORIZACION")
	private TipoAutorizacion tipoAutorizacion;
	
	@Column(name="X_PERFIL")
	private Long idPerfil; 
	
	@Column(name="ID_ESTADO_ORI")
	private Long idEstadoOrigen;
	
	@Column(name="ID_ESTADO_DES")
	private Long idEstadoDestino;

}