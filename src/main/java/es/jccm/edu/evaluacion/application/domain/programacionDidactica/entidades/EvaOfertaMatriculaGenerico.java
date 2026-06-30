package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLOFEMATRGEN", schema = "DELPHOS")
public class EvaOfertaMatriculaGenerico extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_OFERTAMATRIG")
	private Long id;

	@NotNull
	@Column(name="C_ANNO")
	private Integer anno;
	
	@Column(name="C_ANNOTERMINA")
	private Integer annoTermina;

	@Size(max=14, message = "No puede superar los 14 caracteres")
	@Column(name="C_CIRACU")
	private String ciracu;
	
	@Size(max=6, message = "No puede superar los 6 caracteres")
	@Column(name="C_CODDGFP")
	private String codDGFP;
	
	@NotBlank
	@Size(max=250, message = "No puede superar los 250 caracteres")
	@Column(name="D_OFERTAMATRIG")
	private String descripcion;
	
	@NotBlank
	@Size(max=60, message = "No puede superar los 60 caracteres")
	@Column(name="S_OFERTAMATRIG")
	private String descripcionCorta;
	
	@Column(name="L_ADJSERCEN")
	private String lAdjSerCen;
	
	@Column(name="L_ADMDIVCUR")
	private String lAdmDivCur;
	
	@Column(name="L_ADMREFADP")
	private String lAdmRefAdp;
	
	@Column(name="L_ARRASTRA")
	private String lArrastra;
	
	@Column(name="L_CARCONFINUNI")
	private String lCarconfinuni;
	
	@Column(name="L_CONCICFIB")
	private String lConcicfib;
	
	@Column(name="L_CONSITLAB")
	private String lConsitlab;
	
	@Column(name="L_CONTINUA")
	private String lContinua;
	
	@Column(name="L_CONTROLMATERIAS")
	private String lControlMaterias;
	
	@Column(name="L_CONVEXEN")
	private String lConvExen;
	
	@Column(name="L_DECISION")
	private String lDecision;
	
	@Column(name="L_EVALUA")
	private String lEvalua;
	
	@Column(name="L_EXIMATBACREL")
	private String lEximatbacrel;
	
	@Column(name="L_FEMENINO")
	private String lFemenino;
	
	@Column(name="L_GESFALASICON")
	private String lGesfalasicon;
	
	@Column(name="L_MATCOMPLETA")
	private String lMatCompleta;
	
	@Column(name="L_MULTIPERIODO")
	private String lMultiPeriodo;
	
	@Column(name="L_ORGANIZA")
	private String lOrganiza;
	
	@Column(name="L_PERMATLIB")
	private String lPerMatLib;
	
	@Column(name="L_PERMULMATCON")
	private String lPerMulMatCon;
	
	@Column(name="L_PLANTILLA")
	private String lPlantilla;
	
	@Column(name="L_PROMOAUT")
	private String lPromAut;
	
	@Column(name="L_RENUNCIAMATERIA")
	private String lRenunciaMateria;
	
	@Column(name="L_RENUNCIAMATRICULA")
	private String lRenunciaMatricula;
	
	@Column(name="L_REPETIRTODAS")
	private String lRepetirTodas;
	
	@Column(name="L_REQTIT")
	private String lReqTit;
	
	@Column(name="L_SEGURO")
	private String lSeguro;
	
	@Column(name="L_SOLICITABLE")
	private String lSolicitable;
	
	@Column(name="L_TITULO")
	private String lTitulo;
	
	@Column(name="L_VIGENTE")
	private String lVigente;
	
	@Column(name="N_CICLO")
	private Long ciclo;
	
	@Column(name = "N_EDADMAX")
	private Integer edadMax;
	
	@Column(name="N_EDADMIN")
	private Long edadMin;
	
	@Column(name="N_HORAS")
	private Long horas;
	
	@Column(name="N_MAXCONV")
	private Long maxConv;
	
	@Column(name="N_MAXCONVEXT")
	private Long maxConvExt;
	
	@Column(name="N_MAXHOR")
	private Long maxHor;
	
	@Column(name="N_MAXHORSINFCT")
	private Long maxHorSinFCT;
	
	@Column(name="N_MAXMAT")
	private Long maxMat;
	
	@Column(name="N_MAXMATEXT")
	private Long maxMatExt;
	
	@Column(name="N_MAXPEND")
	private Long maxPend;
	
	@Column(name="N_MAXREPNOR")
	private Long maxRepnor;
	
	@Column(name="N_MINMAT")
	private Long minMat;
	
	@Column(name="N_MINMATREPCOM")
	private Long minMatRepCom;
	
	@Column(name="N_ORDEN")
	private Long orden;
	
	@Column(name="N_ORDENCICLO")
	private Long ordenCiclo;
	
	@Column(name="N_ORDENPRES")
	private Long ordenPres;
	
	@Column(name="N_PERIODOS")
	private Long periodos;
	
	@Column(name="N_PORCENTAJE")
	private Long porcentaje;
	
	@NotBlank
	@Size(max=10, message = "No puede superar los 10 caracteres")
	@Column(name="T_ABREVIATURA")
	private String abreviatura;
	
	@Size(max=1000, message = "No puede superar los 1000 caracteres")
	@Column(name="T_ANEXO")
	private String anexo;
	
	@Size(max=80, message = "No puede superar los 80 caracteres")
	@Column(name="T_COMADI")
	private String comAdi;
	
	@Size(max=80, message = "No puede superar los 80 caracteres")
	@Column(name="T_COMADIMATMAT")
	private String comAdiMatMat;
	
	@Size(max=100, message = "No puede superar los 100 caracteres")
	@Column(name="T_CRITERIOREP")
	private String criterioRep;
	
	@Size(max=2000, message = "No puede superar los 2000 caracteres")
	@Column(name="T_DECRETO")
	private String decreto;
	
	@Size(max=30, message = "No puede superar los 30 caracteres")
	@Column(name="T_ETAPA_PLANTILLA")
	private String etapaPlantilla;
	
	@Size(max=2000, message = "No puede superar los 2000 caracteres")
	@Column(name="T_NORMATIVA")
	private String normativa;

}