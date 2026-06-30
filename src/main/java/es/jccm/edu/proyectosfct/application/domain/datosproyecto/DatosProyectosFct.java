package es.jccm.edu.proyectosfct.application.domain.datosproyecto;

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
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_DATOS_PROYECTOS")
public class DatosProyectosFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID_DATO_PROYECTO")
	private Long id;
	
	@Column(name="TX_ACT_FORMATIVO")
	private String actividadFormativo;
	
	@Column(name="TX_ACT_EVALUACION")
	private String actividadEvaluacion;
	
	@Column(name="TX_RESULTADO")
	private String resultado;
	
	@Column(name="TX_CRITERIOS")
	private String criterios;
	
	@Column(name="NU_ORDEN")
	private Integer orden;
	
	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROYECTO")
	private Proyectos proyecto;
	
	
}

