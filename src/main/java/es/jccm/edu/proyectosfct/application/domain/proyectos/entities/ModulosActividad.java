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

import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_MODULOS_ACTIVIDADES")
public class ModulosActividad implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_MODULO_ACTIVIDAD")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MODULO_CURSO")
	private ModulosCurso moduloCurso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DATO_PROYECTO")
	private DatosProyectosFct datoProyecto;

}
