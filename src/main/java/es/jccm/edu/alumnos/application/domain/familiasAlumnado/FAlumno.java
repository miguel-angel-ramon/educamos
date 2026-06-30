package es.jccm.edu.alumnos.application.domain.familiasAlumnado;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="TLALUMNOS")
public class FAlumno implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_ALUMNO")
	private Long id;
	
	@Column(name="T_APELLIDO1")
	private String apellido1;
	
	@Column(name="T_APELLIDO2")
	private String apellido2;
	
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@JsonIgnore
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_FAMILIA")
	private FFamiliaAlumno familia;
	
	 @JsonIgnore
	@OneToMany(mappedBy="alumno",cascade=CascadeType.ALL)
	private List<FMatriculaAlumno>matriculas;
	
	
	

}
