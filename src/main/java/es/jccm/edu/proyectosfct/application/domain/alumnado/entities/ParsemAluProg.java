package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_PARSEM_ALUPROG")
public class ParsemAluProg implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PARSEM_ALU")
	private Long id;
	
	@Column(name="FH_INISEM")
	private Date fechaInicioSemana;
	
	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name="ID_PARSEM_RODAL")
	private String idrodal;
	
	@Column(name="TX_PARSEM_FICHERO")
	private String fichero;

	@Column(name="NU_DIAS")
	private Integer nu_dias;

	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONVPROG_ALU")
	private AlumnoPrograma aluConvProg;

}