package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;

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
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUT_DES")
public class AutorizacionDesplazamiento extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autdes_seq")
    @SequenceGenerator(name = "autdes_seq", sequenceName = "SQ_FCT_AUTDES", allocationSize = 1)
	@Column(name="ID_AUT_DES")
	private Long id;
	
	@Column(name="NU_DIAS")
	private Integer numeroDias;
	
	@Column(name="TX_MATRICULA")
	private String txtMatricula;
	
	@Column(name="TX_ITINERARIO")
	private String txtItinerario;
	
	@Column(name="PC_KMDIA")
	private Double porcetanjeKmDia;
	
	@Column(name="PC_TOTALKMS")
	private Double porcetanjeTotalKm;

	@Column(name="NU_DIAS_2")
	private Integer numeroDias2;

	@Column(name="TX_MATRICULA_2")
	private String txtMatricula2;

	@Column(name="TX_ITINERARIO_2")
	private String txtItinerario2;

	@Column(name="PC_KMDIA_2")
	private Double porcetanjeKmDia2;

	@Column(name="PC_TOTALKMS_2")
	private Double porcetanjeTotalKm2;

	@Column(name="NU_DIAS_3")
	private Integer numeroDias3;

	@Column(name="TX_MATRICULA_3")
	private String txtMatricula3;

	@Column(name="TX_ITINERARIO_3")
	private String txtItinerario3;

	@Column(name="PC_KMDIA_3")
	private Double porcetanjeKmDia3	;

	@Column(name="PC_TOTALKMS_3")
	private Double porcetanjeTotalKm3;

	@Column(name="ID_AUTTUT_RODAL")
	private String idAutTutRodal;
	
	@Column(name="TX_AUTTUT_FICHERO")
	private String nombreFichero;

	@Column(name="NU_AUT")
	private Integer nuAut;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO_GASTO")
	private PeriodoGasto periodoGasto;
	
	@Column(name="ID_TUTORFCTDUAL")
	private Long idTutorFct;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@Column(name="X_CENTRO")
	private Long idCentro;

}