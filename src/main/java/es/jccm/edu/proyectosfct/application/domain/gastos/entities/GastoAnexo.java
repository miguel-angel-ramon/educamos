package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
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
@Table(name="FCT_GASTOS_ANEXOS")
public class GastoAnexo extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GASTOS_ANEXOS")
	private Long id;
	
	@Column(name="X_OFERTAMATRIG")
	private Long idCurso;
	
	@Column(name="X_UNIDAD")
	private Long idUnidad;
	
	@Column(name="C_PROVINCIA")
	private Long idProvincia;
	
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO_GASTO")
	private PeriodoGasto periodoGasto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPOS_GASTOS")
	private TipoGasto tipoGasto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private Centro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TUTORFCTDUAL")
	private TutorFctDual tutorFct;
}