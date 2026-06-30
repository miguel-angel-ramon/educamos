package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

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
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONV_PROGALUHORATRA")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ConvProgAluHorTramoFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONV_PROGALUHORATRA")
	private Long id;
	
	@Column(name="N_TRAMO")
	private Integer ordenTramo;
	
	@Column(name="N_DIASEMANA")
	private Integer diaSemana;
	
	@Column(name="N_HORINI")
	private Integer horaInicio;
	
	@Column(name="N_HORFIN")
	private Integer horaFin;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONV_PROGALUHORAPER")
	private ConvProgAluHorPeriodoFct periodoAlumnoHorario;	

}
