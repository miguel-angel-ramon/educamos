package es.jccm.edu.proyectosfct.application.domain.historial.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUTEXTFUE_HISTORIAL")
public class AutorizacionHistorial  extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authisto_seq")
    @SequenceGenerator(name = "authisto_seq", sequenceName = "SQ_FCT_AUTEXTFUE_HISTORIAL", allocationSize = 1)
	@Column(name="ID_AUTEXTFUE_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fhregistro;
	
	@Column(name="TX_OBSERVACIONES")
	private String txobservaciones;
	
	
	// ---------- Relationships -----------	
	
	@Column(name="ID_AUT_EXTPRO")
	private String idautextpro;
	
	@Column(name="ID_AUTORIZACION_FLUJO")
	private String idautoflujo;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;
	
	
	
	
	

}
