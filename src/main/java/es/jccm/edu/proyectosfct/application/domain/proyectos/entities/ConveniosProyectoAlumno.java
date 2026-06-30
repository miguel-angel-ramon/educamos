package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

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
import javax.persistence.Transient;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

@Data
@Entity
@Table(name="FCT_CONVPROY_ALU")
public class ConveniosProyectoAlumno implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONVPROY_ALU")
	private Long id;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@Column(name="ID_EVALUACION")
	private Long idEvaluacion;
	
	@Column(name="TX_ORIENTACIONES")
	private String orientaciones;
	
	@Column(name = "ID_EVAFIR_RODAL")
	private String idEvaRodal;
	
	@Column(name = "TX_EVAFIR_FICHERO")
	private String txEvafirFichero;	
	
	@Column(name = "F_FIRMA")
	private Date fechaFirma;

	@Column(name = "LG_COTIZA")
	private Integer lgCotiza;

	@Column(name = "LG_NUSS")
	private Integer lgNuss;

	@Column(name = "LG_ERASMUS")
	private Integer lgErasmus;

	@Column(name = "LG_EXCLUIR")
	private Integer lgExcluir;
	
	@Transient
	private String tnuss;

	@Transient
	private String accion;

	@Column (name = "NU_HORAS_EVA")
	private Integer nuHorasEva;

	@Transient
	private String periodo;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONV_PROY")
	private ConveniosProyecto convenioProyecto;

}
