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
public class ListadoGastoAlumnado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOMBREALUMNO")
	private String nombreAlumno;
	
	@Column(name="IDMATRICULA")
	private Long idMatricula;

	@Column(name="NOMBRETUTOR")
	private String nombreTutor;
	
	@Column(name="ANNOPERIODO")
	private Integer annoPeriodo;
	
	@Column(name="FINICIO")
	private Date fInicio;
	
	@Column(name="FFIN")
	private Date fFin;
	
	@Column(name="DESPLAZACENTRO")
	private Integer desplazaCentro;
	
	@Column(name="DESPLAZADOMICILIO")
	private Integer desplazaDomicilio;
	
	@Column(name="IMPORTE")
	private Double importe;
	
	@Column(name="fultgen")
	private String fultgen;
	
	@Column(name="DIASCOLECTIVO")
	private Integer diasColectivo;
	
	@Column(name="KM")
	private Double km;
	
	@Column(name="DIASVEHICULO")
	private Integer diasVehiculo;
	
	@Column(name="COSTEIMPORTEKM")
	private Double costeImporteKm;
	
	@Column(name="TOTALTRANSPORTE")
	private Double totalTransporte;
	
	@Column(name="OTROSGASTOS")
	private Double otrosGastos;
	
	@Column(name="TOTAL")
	private Double total;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="Editar estado")
	private Integer editarestado;
	
	@Column(name="Id tutor")
	private Long idTutor;	
	
	@Column(name="FINICIOGASTO")
	private Date finiciogasto;
	
	@Column(name="FFINGASTO")
	private Date ffingasto;

	@Column(name="PUEDEBORRAR")
	private String puedeBorrar;
	
}