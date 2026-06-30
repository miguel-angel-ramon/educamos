package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

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
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_PROYECTOS")
public class Proyectos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PROYECTO")
	private Long id;
	
	@NotBlank
	@Column(name="DS_PROYECTO")
	private String ds_proyecto;
	
	@NotNull
	@Column(name="C_ANNO_DESDE")
	private Integer c_anno_desde;
	
	@Column(name="C_ANNO_HASTA")
	private Integer c_anno_hasta= null;
	
	@Column(name="NU_HORAS")
	private Integer nu_horas;
	
	@Column(name="NU_ALUMNOS")
	private Integer nu_alumnos;

	@Column(name="LG_LOFP")
	private Integer lgLofp;

	@Column(name="X_OFERTAMATRIG")
	private Long idOfertaMatrig;

	@Column(name="LG_IDIARIO")
	private Integer lg_idiario;

	@Column(name="LG_ISEMANAL")
	private Integer lg_isemanal;

	@Column(name="LG_IMENSUAL")
	private Integer lg_imensual;

	@Column(name="LG_IOTROS")
	private Integer lg_iotros;

	@Column(name="LG_IVARIAS_EMPRESAS")
	private Integer lg_ivarias_empresas;

	@Column(name="LG_REGIMEN")
	private Integer lg_regimen;


	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_PROYECTO")
	private TipoProyecto tipo;
	
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
	@JoinColumn(name="X_MODALIDAD")
	private Modalidad modalidad;
	
	

}
