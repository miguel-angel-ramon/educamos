package es.jccm.edu.alumnos.application.domain.programas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLUNIDADESCEN")
public class PUnidad implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_UNIDAD")
	private Long id;
	
	@Column(name="T_NOMBRE")
	private String nombreUnidad;
	
	
	

}
