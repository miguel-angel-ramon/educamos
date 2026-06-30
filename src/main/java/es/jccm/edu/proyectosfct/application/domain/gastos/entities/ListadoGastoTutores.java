package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class ListadoGastoTutores implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="ANNOPERIODO")
	private Integer annoPeriodo;

	@Column(name="FINICIO")
	private Date fInicio;
	
	@Column(name="FFIN")
	private Date fFin;
	
	@Column(name="NOMBRECOMPLETO")
	private String nombreCompleto;
	
	@Column(name="MANUTENCION")
	private Double manutencion;
	
	@Column(name="ALOJAMIENTO")
	private Double alojamiento;
	
	@Column(name="BILLETES")
	private Double billetes;
	
	@Column(name="TAXI")
	private Double taxi;
	
	@Column(name="VEHICULO")
	private Double vehiculo;
	
	@Column(name="GASTOSKM")
	private Double gastosKm;
	
	@Column(name="APARCAMIENTO")
	private Double aparcamiento;
	
	@Column(name="PEAJE")
	private Double peaje;
	
	@Column(name="TOTAL")
	private Double total;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="FULTGEN")
	private String fultgen;
	
	@Column(name="ID_TUTOR")
	private Long idTutor;
	
	@Column(name="Editar estado")
	private Integer editarestado;
	
	@Column(name="FINICIOGASTO")
	private Date finiciogasto;
	
	@Column(name="FFINGASTO")
	private Date ffingasto;

	@Column(name="PUEDEBORRAR")
	private String puedeBorrar;
	
}