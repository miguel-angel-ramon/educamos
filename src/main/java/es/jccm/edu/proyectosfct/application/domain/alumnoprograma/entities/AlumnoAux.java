package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class AlumnoAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FECHAINICIOEXT")
	private String fini;
	
//	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="FECHAFINEXT")
	private String ffin;
	
	@Column(name="HORARIO")
	private String horario;
	
//	@Column(name="LG_FINDE_EXT")
//	private Integer lgfindeext;
//	
//	@Column(name="LG_VACAC_EXT")
//	private Integer lgvacacext;
	
}
