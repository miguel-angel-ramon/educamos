package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.jccm.edu.proyectosfct.application.domain.cursoacademico.CursoAcademico;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "FCT_IMPTIPSER", schema = "DELPHOS")
public class ImpuestoTipoServicio extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_IMPTIPSER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_IMPTIPSER")
	@SequenceGenerator(name = "SQ_FCT_IMPTIPSER", sequenceName = "SQ_FCT_IMPTIPSER", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;
	
	@NotBlank
	@Size(max=200, message = "No puede superar los 200 caracteres")
	@Column(name = "DS_IMPTIPSER")
	private String descripcion;
	
	@Column(name = "IM_MAX_ALOJAMINTO_DIA")
	private Double maximoAlojamientoDia;
	
	@Column(name = "IM_MAX_MANUTENCION_DIA")
	private Double maximoManutencionDia;
	
	@Column(name = "IM_MAX_KM_VEHICULO_DIA")
	private Double maximoKmVehiculo;
	
	@Column(name = "IM_MAX_KM_MOTO_DIA")
	private Double maximoKmMoto;
	
	@Column(name = "IM_MAX_VEHICULO_DIA")
	private Double maximoVehiculoDia;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_TIPSERCENANNO", referencedColumnName="ID_TIPSERCENANNO")
	private TipoServicioCentroAnno tipoServicioCentroAnno;
	

	
	

}
