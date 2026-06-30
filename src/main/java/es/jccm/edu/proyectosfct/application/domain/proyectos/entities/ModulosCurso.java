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
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_MODULOS_CURSOS")
public class ModulosCurso implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MODULO_CURSO")
	private Long id;
		
	@Column(name="NU_HORAS_TOTALES")
	private Integer horasTotales;
	
	@Column(name="NU_HORAS_SEMANALES")
	private Integer horasSemanales;
	
	@Column(name="NU_HORAS_CENTRO")
	private Integer horasCentro;
	
	@Column(name="NU_HORAS_EMPRESA")
	private Integer horasEmpresa;

	@Transient
	private Integer esInsert;

	@Transient
	private Integer esDelete;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO_PROYECTO")
	private CursoProyecto cursoProyecto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_MATERIAOMG")
	private MateriaCursoOfertaMatriculaGenerica idMateriaomg;

}
