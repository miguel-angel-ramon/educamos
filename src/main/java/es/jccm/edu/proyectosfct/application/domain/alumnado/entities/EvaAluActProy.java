package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

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
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_EVAALU_ACTPROY")
public class EvaAluActProy implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_EVAALU_ACT")
	private Long id;
	
	@Column(name="LG_REALIZADA")
	private Integer realizada;
	
	@Column(name="LG_ADQUIRIDA")
	private Integer adquirida;
	
	@Column(name="TX_OBSERVACIONES")
	private String observaciones;

	@Column(name="TX_CRITERIOS")
	private String criterios;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONVPROY_ALU")
	private ConveniosProyectoAlumno aluConvProy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DATO_PROYECTO")
	private DatosProyectosFct datosProyecto;
	
}
