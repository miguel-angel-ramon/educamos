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
@Table(name="FCT_AUTDES_HISTORIAL")
public class AutorizacionDesplazamientoHistorial extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autdeshistorial_seq")
    @SequenceGenerator(name = "autdeshistorial_seq", sequenceName = "SQ_FCT_AUTDES_HISTORIAL", allocationSize = 1)
	@Column(name="ID_AUTDES_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="TX_OBSERVACIONES")
	private String txtObservaciones;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUT_DES")
	private AutorizacionDesplazamiento autorizacionDesplazamiento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUTORIZACION_FLUJO")
	private AutorizacionFlujo flujo;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;

}