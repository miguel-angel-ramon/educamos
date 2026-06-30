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
public class ListadoGastoAnexo implements Serializable {
	
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
	
	@Column(name="NOMBRETIPO")
	private String nombreTipo;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="ID Tutor")
	private Long idTutor;
	
	@Column(name="Nombre tutor")
	private String tutor;
	
	@Column(name="Id fichero rodal")
	private String idRodal;
	
	@Column(name="Nombre fichero")
	private String fichero;
	
	@Column(name="idHistorial Rodal")
	private Long idHistorialRodal;
	
	@Column(name="idHistorial")
	private Long idHistorial;
	
	@Column(name="Periodo")
	private String periodo;
	
	@Column(name="Curso")
	private String curso;
	
	@Column(name="Unidad")
	private String unidad;
	
	@Column(name="fultgen")
	private String fultgen;
	
	@Column(name="ID Periodo")
	private Long idPeriodo;
	
	@Column(name="ID Curso")
	private Long idCurso;
	
	@Column(name="ID Unidad")
	private Long idUnidad;
	
	@Column(name="Puede firmar")
	private Integer puedefirmar;
	
	@Column(name="id Perfil")
	private Long idperfil;
	
	@Column(name="Puede generar")
	private Integer puedegenerar;

	@Column(name="Fecha de actualización/creación del último gasto asignado")
	private String fhGastMax;
	
}