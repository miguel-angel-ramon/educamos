package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Cuestionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCuestionario;
	
	private Integer anio;

	private Long idCuePubUsu;

	private Long IdCuePub;
	
	private String nombre;
	
	private String descripcion;

	private boolean activo;

	private boolean presentado;
	
	private Date fInicioRespuestas;
	
	private Date fFinRespuestas;

}
