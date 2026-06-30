package es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLCURSOACA")
public class CursoAcademicoDoc extends BaseAudited implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="C_ANNO")
	private Long cAnno;
	
	@Column(name="F_INICIO")
	private Date fechaInicio;
	
	@Column(name="F_FINAL")
	private Date fechaFinal;
	
	@Column(name="N_SMI")
	private Long smi;
	
	@Column(name="L_ACTUAL")
	private String cursoActual;
	
	@Column(name="F_INICONJUNELE")
	private Date fechaIniConJunEle;
	
	@Column(name="F_FINCONJUNELE")
	private Date fechaFinConJunEle;
	
	@Column(name="F_INIADMCANCONESC")
	private Date fechaIniAdmCanConEsc;
	
	@Column(name="F_FINADMCANCONESC")
	private Date fechaFinAdmCanConEsc;
	
	@Column(name="F_LISPROCANCONESC")
	private Date fechaLisProCanConEsc;
	
	@Column(name="F_LISDEFCANCONESC")
	private Date fechaLisDefCanConEsc;
	
	@Column(name="F_LIMCONPAPCONESC")
	private Date fechaLimConPapConEsc;
	
	@Column(name="F_INICAMELECONESC")
	private Date fechaIniCamEleConEsc;
	
	@Column(name="F_FINCAMELECONESC")
	private Date fechaFinCamEleConEsc;
	
	@Column(name="F_ELEALUCONESC")
	private Date fechaEleAluConEsc;
	
	@Column(name="F_ELETUTCONESC")
	private Date fechaEleTutConEsc;
	
	@Column(name="F_ELEPROCONESC")
	private Date fechaEleProConEsc;
	
	@Column(name="F_LIMCONCONESC")
	private Date fechaLimConConEsc;
	
	@Column(name="L_VISIBLE")
	private String esVisible;
	
	@Column(name="F_ADJDEFSOL")
	private Date fechaAdjDefSol;
	
	@Column(name="F_ADJPROSOL")
	private Date fechaAdjProSol;
	
	@Column(name="F_FINADMSOL")
	private Date fechaFinAdmSol;
	
	@Column(name="F_INIADMSOL")
	private Date fechaIniAdmSol;
	
	@Column(name="F_ENVSOLAYU")
	private Date fechaEnvSolAyu;
	
	@Column(name="F_FINPRITRIMESTRE")
	private Date fechaFinPriTrimestre;
	
	@Column(name="F_INISEGTRIMESTRE")
	private Date fechaIniSegTrimestre;
	
	@Column(name="F_FINSEGTRIMESTRE")
	private Date fechaFinSegTrimestre;
	
	@Column(name="F_CIERREPRITRIM")
	private Date fechaCierrePriTrim;
	
	@Column(name="F_CIERRESEGTRIM")
	private Date fechaCierreSegTrim;
	
	@Column(name="F_CIERRETERTRIM")
	private Date fechaCierreTerTrim;
	
	@Column(name="L_FIESTASLOCALES")
	private String puedeGestionarFiestasLocales;

}
