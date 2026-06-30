package es.jccm.edu.proyectosfct.application.domain.extraordinario.entities;

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

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUTEXTFUE_HISTORIAL")
public class AutorizacionExtraordinarioHistorial extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autextprohistorial_seq")
	@SequenceGenerator(name = "autextprohistorial_seq", sequenceName = "SQ_FCT_AUTEXTFUE_HISTORIAL", allocationSize = 1)
	@Column(name="ID_AUTEXTFUE_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="TX_OBSERVACIONES")
	private String txtObservaciones;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUT_EXTPRO")
	private AutorizacionExtraordinario autorizacionExtraordinario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUTORIZACION_FLUJO")
	private AutorizacionFlujo flujo;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;

}
