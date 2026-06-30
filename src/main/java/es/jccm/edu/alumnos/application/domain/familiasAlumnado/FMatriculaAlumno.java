package es.jccm.edu.alumnos.application.domain.familiasAlumnado;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="TLMATALU")
public class FMatriculaAlumno implements Serializable {	private static final long serialVersionUID=1L;

@Id
@Column(name="X_MATRICULA")
private Long id;

@JsonIgnore
@ManyToOne (optional=false, fetch=FetchType.LAZY)
@JoinColumn(name="X_ALUMNO")
private FAlumno alumno;

@Column (name="X_CENTRO")
private Long idCentro;

@Column(name="C_ANNO")
private int anno;

@Column(name="X_ESTGENMATR")
private Integer estadoMatricula;

@Column(name="L_HISTORICO")
private String historico;

@Column(name="C_RESULTADO")
private Integer resultado;




}
