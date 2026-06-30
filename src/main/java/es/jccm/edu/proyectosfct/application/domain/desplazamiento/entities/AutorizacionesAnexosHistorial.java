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
@Table(name="FCT_ANEAUT_HISTORIAL")
public class AutorizacionesAnexosHistorial extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autanehistorial_seq")
    @SequenceGenerator(name = "autanehistorial_seq", sequenceName = "SQ_FCT_ANEAUTHISTORIAL", allocationSize = 1)
	@Column(name="ID_ANEAUT_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;

	@Column(name="TX_OBSERVACIONES")
	private String observaciones;	
	
	@Column(name="ID_ANEHIS_RODAL")
	private String idAneHisRodal;	
	
	@Column(name="TX_ANEHIS_FICHERO")
	private String nombreFichero;	
	
	@Column(name="ID_ANEFCT_RODAL")
	private String idAneFctRodal;	
	
	@Column(name="TX_ANEFCT_FICHERO")
	private String nombreAnexo;	
	
	@Column(name="FH_REGISTRO_ANEXO")
	private Date fechaRegistroAnexo;

	@Column(name = "ID_ANEFCT_RODAL_AUTO")
	private String idAnefctRodalAuto;

	@Column(name = "TX_ANEFCT_FICHERO_AUTO")
	private String txAnefctFicheroAuto;

	@Column(name = "FH_REGISTRO_ANEXO_AUTO")
	private Date fhRegistroAnexoAuto;
	
	@Column(name = "LG_VISITADO")
	private Integer lgVisitado;
	
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUTORIZACION_ANEXO")
	private AutorizacionesAnexos anexo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AUTORIZACION_FLUJO")
	private AutorizacionFlujo flujo; 


}
