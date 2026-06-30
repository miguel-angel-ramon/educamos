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

import lombok.Data;

@Data
@Entity
@Table(name="TLMATOFEMATRG")
public class MateriaCursoOfertaMatriculaGenerica implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_MATERIAOMG")
	private Long id;
	
	
	@Column(name="N_HORAS")
	private Long nHoras;
	
	
	@Column(name="N_HORASANUALES")
	private Long nHorasAnuales;
	
	
	// ---------- Relationships -----------
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_MATERIAC")
	private MateriaCursoGenerica materiaCurso;

}
