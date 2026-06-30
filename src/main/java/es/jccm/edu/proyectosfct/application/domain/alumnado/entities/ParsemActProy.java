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
import lombok.Data;

@Data
@Entity
@Table(name="FCT_PARSEM_ACTPROY")
public class ParsemActProy implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PARSEM_ACT")
	private Long id;
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PARSEM_ALU")
	private ParsemAluProy parsemAluProy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DATO_PROYECTO")
	private DatosProyectosFct datosProyecto;

}