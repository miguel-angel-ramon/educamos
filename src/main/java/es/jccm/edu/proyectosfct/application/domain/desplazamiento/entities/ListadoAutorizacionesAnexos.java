package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ListadoAutorizacionesAnexos implements Serializable {
	
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
	
	@Column(name="Familia")
	private String familia;
	
	@Column(name="Curso")
	private String curso;
	
	@Column(name="Unidad")
	private String unidad;
	
	@Column(name="ID Periodo")
	private Long idPeriodo;
	
	@Column(name="ID Familia")
	private Long idFamilia;
	
	@Column(name="ID Curso")
	private Long idCurso;
	
	@Column(name="ID Unidad")
	private Long idUnidad;
	
	@Column(name="Puede firmar")
	private Integer puedefirmar;
	
	@Column(name="ID perfil")
	private Long idPerfil;	
	
	@Column(name="Puede generar")
	private Integer puedegenerar;
	
	@Column(name="Puede editar")
	private Integer puedeeditar;
	
	@Column(name="idHistorial Adjunto")
	private Long idHistorialAdjunto;
	
	@Column(name="Nombre adjunto")
	private String adjunto;	
	
	@Column(name="Fecha de ultima generación del anexo")
	private String fultgen;

	@Column(name = "Fecha de actualización de la última autorización asignada")
	private String fhAutMax;

	@Column(name = "NU_PETICION")
	private Integer nuPeticion;
	
	@Column(name="Puede crear")
	private Integer puedecrear;
	
	@Column(name="comunicacion")
	private Integer comunicacion;
	
	
}