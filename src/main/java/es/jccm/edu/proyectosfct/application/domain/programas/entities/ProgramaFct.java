package es.jccm.edu.proyectosfct.application.domain.programas.entities;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaGenerico;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_PROGRAMAS")
public class ProgramaFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID_PROGRAMA")
	private Long id;
	
	@NotBlank
	@Column(name="DS_PROGRAMA")
	private String descripcion;

	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TUTORFCTDUAL")
	private TutorFctDual tutor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private Centro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_FAMILIA")
	private Familia familia;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIG")
	private OfertaMatriculaGenerico ofertaMatriculaGenerico;
	
	@NotNull
	@Column(name="C_ANNO_DESDE")
	private Integer c_anno_desde;
	
	@Column(name="C_ANNO_HASTA")
	private Integer c_anno_hasta= null;
	
	@Column(name="NU_HORAS")
	private Integer nu_horas;
	
	
}

