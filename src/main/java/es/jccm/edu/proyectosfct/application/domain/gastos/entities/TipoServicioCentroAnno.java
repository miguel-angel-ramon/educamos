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
@Table(name = "FCT_TIPSERCENANNO", schema = "DELPHOS")
public class TipoServicioCentroAnno extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TIPSERCENANNO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_TIPSERCENANNO")
	@SequenceGenerator(name = "SQ_FCT_TIPSERCENANNO", sequenceName = "SQ_FCT_TIPSERCENANNO", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_TIPSERCEN", referencedColumnName="ID_TIPSERCEN")
	private TipoServicioCentro tipoServicioCentro;
	
	@Column(name = "C_ANNO")
	private Integer anno;
	

}
