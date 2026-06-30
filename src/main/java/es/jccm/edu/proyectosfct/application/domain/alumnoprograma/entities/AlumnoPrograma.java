package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

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

import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONVPROG_ALU")
public class AlumnoPrograma implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONVPROG_ALU")
	private Long id;
	
	@Column(name="TX_ORIENTACIONES")
	private String orientaciones;
	
	@Column(name = "LG_EXCLUIR")
	private Integer lgExcluir;
	
	@Column(name ="LG_ERASMUS")
	private Integer lgErasmus;
	
	@Column(name = "LG_COTIZA")
	private Integer lgCotiza;
	
	@Column(name = "ID_EVAFIR_RODAL")
	private String idEvaRodal;
	
	@Column(name = "TX_EVAFIR_FICHERO")
	private String txEvafirFichero;		
	
	@Column(name = "LG_NUSS")
	private Integer lgNuss;
	
	@Column(name = "F_FIRMA")
	private Date fechaFirma;	

	@Transient
	private String tnuss;
	
	@Transient
	private String accion;

	@Column(name = "NU_HORAS_EVA")
	private Integer nuHorasEva;

	@Transient
	private String periodo;

	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONV_PROG")
	private ConveniosProgramasFct convenioPrograma;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@Column(name="ID_EVALUACION")
	private Long idEvaluacion;	
	
	public AlumnoPrograma() {
		super();
	}

	public AlumnoPrograma(String orientaciones, ConveniosProgramasFct convenioPrograma, Long idMatricula,
			Long idEvaluacion,String idEvaRodal, String txEvafirFichero, Date fechaFirma) {
		super();
		this.id = 0L;
		this.orientaciones = orientaciones;
		this.convenioPrograma = convenioPrograma;
		this.idMatricula = idMatricula;
		this.idEvaluacion = idEvaluacion;
		this.idEvaRodal = idEvaRodal;
		this.txEvafirFichero = txEvafirFichero;
		this.fechaFirma = fechaFirma;
		
	}

}