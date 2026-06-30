package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class ObjetivoEspecifico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idObjEsp;

	private Long idObjetivo;

	private String descripcion;

	private String activo;

	@OneToMany(mappedBy = "idLinAct")
	private List<LineaActuacion> lineasActuacion;

}
