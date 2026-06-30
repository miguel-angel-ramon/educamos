package es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONV_PROGHORAPER")
public class ConvProgHorPeriodoFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONV_PROGHORAPER")
	private Long id;
	
	@Column(name="C_ANNO")
	private Integer annoPeriodo;
	
	@Column(name="FH_INICIO")
	private Date fechaInicio;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	@Column(name="NU_HORAS")
	private Double nhoras;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONV_PROG")
	private ConveniosProgramasFct convenioPrograma;

}
