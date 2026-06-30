package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Alumno implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOMBRECOMPLETO")
	private String nombreCompleto;
	
	@Column(name="NOMBRE")
	private String nombre;

	@Column(name="IDMATRICULA")
	private Long idMatricula;
	
	@Column(name="IDUNIDAD")
	private Long idUnidad;
	
	@Column(name="NOMBREUNIDAD")
	private String nombreUnidad;
	
	@Column(name="FECHAINIEXT")
	private Date dateIniExt;
	
	@Column(name="FECHAFINEXT")
	private Date dateFinExt;
	
	@Column(name="FECHAINIPROV")
	private Date dateIniProv;
	
	@Column(name="FECHAFINPROV")
	private Date dateFinProv;
	
	@Column(name="HORARIOEXT")
	private String horarioExt;
	
	@Column(name="HORARIOPROV")
	private String horarioProv;
	
	@Column(name="LG_FINDE_EXT")
	private Integer lgfindeext;
	
	@Column(name="LG_VACAC_EXT")
	private Integer lgvacacext;

	@Column(name="T_NUSS")
	private String tnuss;

	@Column(name="LG_COTIZA")
	private Integer lgCotiza;

	@Column(name="LG_NUSS")
	private Integer lgNuss;
	
	@Column(name="ID_CONVALU")
	private Long idConvAlu;
	
	@Column(name="LG_EDITABLE")
	private Integer lgEditable;		
	
	@Column(name="LG_MENOR")
	private Integer lgMenor;
	
	@Column(name="LG_ERASMUS")
	private Integer lgErasmus;

	@Transient
	private Integer cotizaIntermitente;

	@Column(name="DS_MOTIVO")
	private String dsMotivo;

	@Column(name="DS_LOCALIDAD_FCT")
	private String dsLocalidadFct;
	
	@Transient
	private Integer lgAlumnoEnEmpresa;


}