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

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaGenerico;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CURSOS_PROYECTOS")
public class CursoProyecto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CURSO_PROYECTO")
	private Long id;
	
	@Column(name="NU_HORAS_TOTALES")
	private Integer horasTotales;
	
	@Column(name="NU_HORAS_CENTRO")
	private Integer horasCentro;
	
	@Column(name="NU_HORAS_EMPRESA")
	private Integer horasEmpresa;
	
	@Column(name="TX_PRACTIVAS")
	private String practivas;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROYECTO")
	private Proyectos proyecto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIG")
	private OfertaMatriculaGenerico idOfertamatrig;

}
