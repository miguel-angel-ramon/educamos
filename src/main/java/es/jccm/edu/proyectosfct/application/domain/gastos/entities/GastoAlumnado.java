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
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_GASTOS_ALUMNADO")
public class GastoAlumnado extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GASTOS_ALUMNADO")
	private Long id;
	
	@Column(name="L_DESPLAZA_CENTRO")
	private Integer desplazaCentro;

	@Column(name="L_DESPLAZA_DOMICILIO")
	private Integer desplazaDomicilio;
	
	@Column(name="N_IMPORTE")
	private Double costeImporte;
	
	@Column(name="N_DIAS_COLEC")
	private Integer numDiasTransporteColec;
	
	@Column(name="N_KILOMETROS")
	private Double costeKm;
	
	@Column(name="N_DIAS_VEHI")
	private Integer numDiasVehiculo;
	
	@Column(name="N_IMPORTE_KILOMETROS")
	private Double costeImporteKm;
	
	@Column(name="N_TOTAL_TRANSPORTE")
	private Double costeTotalTransporte;
	
	@Column(name="N_OTROS_GASTOS")
	private Double costeOtrosGastos;
	
	@Column(name="N_TOTAL")
	private Double costeTotal;

	@Column(name="N_MANUTENCION")
	private Double costeManutencion;

	@Column(name="N_DIAS_MANU")
	private Integer numDiasManu;

	@Column(name="N_EPIS")
	private Double costeEpis;

	@Column(name="N_OTROS")
	private Double costeOtros;
	
	@Column (name="DS_LOC_CURSO")
	private String localidadCurso;
	
	@Column (name="DS_LOC_ESTANCIA")
	private String localidadEstancia;
	
	@Column (name="DS_LOC_CENTRO_EDU")
	private String localidadCentroEdu;
	
	@Column (name="DS_LOC_CENTRO_TRABAJO")
	private String localidadCentroTrabajo;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO_GASTO")
	private PeriodoGasto periodoGasto;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private Centro centro;

}