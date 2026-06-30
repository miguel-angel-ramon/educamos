package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLLOCALIDADES")
public class HLocalidad implements Serializable{
	private static final long serialVersionUID=1L;

	@Id
	@Column(name="X_LOCALIDAD")
	private Long id;
	
	@Column(name="D_LOCALIDAD")
	private String descripcion;

}
