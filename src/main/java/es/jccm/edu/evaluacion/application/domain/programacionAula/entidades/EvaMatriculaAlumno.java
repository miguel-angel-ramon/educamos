package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLMATALU", schema = "DELPHOS")
public class EvaMatriculaAlumno implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="X_MATRICULA")
	private Long id;
	
	@OneToOne 
	@JoinColumn(name="X_ALUMNO")
	private EvaAlumno alumno;
	
	@Column (name="C_ANNO")
	private Integer  anno;
	
	@Column (name="X_OFERTAMATRIC")
	private Long idOfertaMatriculacion;
	
	@Column (name="X_UNIDAD")
	private Long idUnidad;

}
