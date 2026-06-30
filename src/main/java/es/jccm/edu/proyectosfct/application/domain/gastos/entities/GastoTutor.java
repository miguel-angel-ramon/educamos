package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

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
import javax.persistence.Table;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_GASTOS_TUTORES")
public class GastoTutor extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GASTOS_TUTOR")
	private Long id;
	
	@Column(name="N_MANUTENCION")
	private Double costeManutencion;

	@Column(name="N_ALOJAMIENTO")
	private Double costeAlojamiento;
	
	@Column(name="N_BILLETES")
	private Double costeBilletes;
	
	@Column(name="N_TAXI")
	private Double costeTaxi;
	
	@Column(name="N_KM_VEHICULO")
	private Double costeKmVehiculo;
	
	@Column(name="N_GASTOS_KM")
	private Double costeKm;
	
	@Column(name="N_APARCAMIENTO")
	private Double costeAparcamiento;
	
	@Column(name="N_PEAJE")
	private Double costePeaje;
	
	@Column(name="N_TOTAL")
	private Double costeTotal;
	
	@Column(name="FH_INICIO")
	private Date fechaInicio;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO_GASTO")
	private PeriodoGasto periodoGasto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private Centro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TUTORFCTDUAL")
	private TutorFctDual tutorFct;

}