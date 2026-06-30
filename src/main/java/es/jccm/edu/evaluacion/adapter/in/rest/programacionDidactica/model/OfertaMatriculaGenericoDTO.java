package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "OfertaMatriculaGenericoDTO", description = "DTO Oferta Matrícula Genérico")
public class OfertaMatriculaGenericoDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Oferta Matrícula Genérico")
	private Long id;

	@Schema(description = "Año académico en el que comienza la vigencia de la OMG")
	private Integer anno;
	
	@Schema(description = "Año académico en el que finaliza la vigencia de la OMG")
	private Integer annoTermina;

	@Schema(description = "Almacena la concatenación de los campos 'Cod_Ciclo', 'Cod_Rama', 'Orden' e 'Itinerario' de la tabla 'TLPLAESTINI'")
	private String ciracu;
	
	@Schema(description = "Código del ciclo para la Dirección General de Formación Profesional")
	private String codDGFP;
	
	@Schema(description = "Descripción larga de la Oferta Matrícula Genérico")
	private String descripcion;
	
	@Schema(description = "Descripción corta de la Oferta Matrícula Genérico")
	private String descripcionCorta;
	
	@Schema(description = "Indica si las solicitudes de admisión en esta OMG se gestionan en los Servicios Centrales")
	private String lAdjSerCen;
	
	@Schema(description = "Indica si la OMG admite diversificación curricular")
	private String lAdmDivCur;
	
	private String lAdmRefAdp;
	
	@Schema(description = "Indica si hay arrastre de pendientes del curso anterior")
	private String lArrastra;
	
	@Schema(description = "Id de la Oferta Matrícula Genérico")
	private String lCarconfinuni;
	
	@Schema(description = "Indica si al formalizar matrículas en esa OMG se contempla el ciclo de F.I.B.")
	private String lConcicfib;
	
	@Schema(description = "Indica si al formalizar matrículas en esa OMG se contempla la situación laboral del alumno/a")
	private String lConsitlab;
	
	@Schema(description = "Indica si el curso se evalúa en evaluación continua")
	private String lContinua;
	
	@Schema(description = "Indica si se debe realizar control sobre las materias a la hora de matricular")
	private String lControlMaterias;
	
	@Schema(description = "Indica si el curso gestiona solicitudes de convalidación/exención de materias")
	private String lConvExen;
	
	@Schema(description = "Indica si la promoción es decisión del equipo educativo. Tiene más peso que L_PROMOAUT")
	private String lDecision;
	
	@Schema(description = "Indica si la OMG se evalua en convocatorias finales (en parciales siempre se puede evaluar)")
	private String lEvalua;
	
	@Schema(description = "Existen materias de Bachillerato relacionadas con esta OMG. Sólo tiene sentido para CFGS")
	private String lEximatbacrel;
	
	@Schema(description = "Indica si en la admisión de CFGS tiene prioridad el sexo femenino")
	private String lFemenino;
	
	@Schema(description = "Flag (S/N) para indicar si se gestionan las faltas de asistencia entre las convocatorias ordinaria y extraordinaria.")
	private String lGesfalasicon;
	
	@Schema(description = "Si vale 'N' la matrícula puede incluir sólo algunas materias de la oferta (sin cumplir por tanto las condiciones de los grupos de materias)")
	private String lMatCompleta;
	
	@Schema(description = "Indica si existe más de un periodo de esta OMG para  un mismo año académico")
	private String lMultiPeriodo;
	
	@Schema(description = "Indica si es una organización de un curso")
	private String lOrganiza;
	
	@Schema(description = "Indica si se permiten matrículas libres (no oficiales)")
	private String lPerMatLib;
	
	@Schema(description = "Indica si se permite que un alumno tenga múltiples matrículas (no canceladas) contemporáneas en este curso (como p.e. en los 'Cursos de desarrollo personal y profesional').")
	private String lPerMulMatCon;
	
	@Schema(description = "Indica si la OMG entra en la Composición de Unidades (proviene de TLETAPAS)")
	private String lPlantilla;
	
	@Schema(description = "Indica si hay promoción automática")
	private String lPromAut;
	
	@Schema(description = "Indica si el curso admite renuncias a materias")
	private String lRenunciaMateria;
	
	@Schema(description = "Indica si el curso admite renuncias a materias")
	private String lRenunciaMatricula;
	
	@Schema(description = "Indica si se repiten todas las materias si no promociona de curso")
	private String lRepetirTodas;
	
	@Schema(description = "Indica si el curso requiere cumplir los requisitos académicos para poder tutilar")
	private String lReqTit;
	
	@Schema(description = "Indica si en las matrículas de esta OMG se debe indicar si el alumno ha pagado o no el seguro escolar")
	private String lSeguro;
	
	@Schema(description = "Indica si la oferta se puede solicitar en la admisión")
	private String lSolicitable;
	
	@Schema(description = "Flag que indica si el curso puede dar lugar a titulación")
	private String lTitulo;
	
	@Schema(description = "Indica si la etapa está vigente")
	private String lVigente;
	
	@Schema(description = "Número de ciclo (1, 2, ...), dentro de su etapa padre, al que pertenece la OMG (si ese es el caso)")
	private Long ciclo;
	
	@Schema(description = "Edad máxima de matriculación en el curso")
	private Integer edadMax;
	
	@Schema(description = "Edad mínima de matriculación en el curso")
	private Long edadMin;
	
	private Long horas;
	
	@Schema(description = "Número máximo de convocatorias de que dispone el alumno para superar las materias de esta OMG")
	private Long maxConv;
	
	@Schema(description = "Máximo de convocatorias extraordinarias")
	private Long maxConvExt;
	
	@Schema(description = "Máximo de horas suspensas que impiden la superación del curso")
	private Long maxHor;
	
	@Schema(description = "Máximo de horas de las materias que se pueden incluir en la matrícula, sin contar la FCT")
	private Long maxHorSinFCT;
	
	@Schema(description = "Máximo de materias pendientes con las que se puede elegir si se repite curso completo o no")
	private Long maxMat;
	
	@Schema(description = "Número máximo de matrículas extraordinarias que se permite tener a un alumno en esta OMG")
	private Long maxMatExt;
	
	@Schema(description = "Máximo materias no superadas para promocinar. (cursos evaluables)")
	private Long maxPend;
	
	@Schema(description = "Número máximo de repeticiones 'normales' que se permiten en la OMG")
	private Long maxRepnor;
	
	@Schema(description = "Mínimo de materias pendientes con las que se puede elegir si se repite curso completo o no")
	private Long minMat;
	
	@Schema(description = "Número mínimo de materias pendientes que obligan a repetir el curso completo (relevante sólo si l_repetirtodas = 'N')")
	private Long minMatRepCom;
	
	@Schema(description = "Orden de la OMG dentro de su tipo de expediente")
	private Long orden;
	
	@Schema(description = "Número de orden de la OMG dentro del ciclo al que pertenece (si ese es el caso)")
	private Long ordenCiclo;
	
	@Schema(description = "Orden de presentación absoluto. Es la concatenación de TLTIPOSEXP.N_ORDENPRES y TLOFEMATRGEN.N_ORDEN")
	private Long ordenPres;
	
	@Schema(description = "Número máximo de matriculas por año académico")
	private Long periodos;
	
	@Schema(description = "Porcentaje de horas de materias no superadas. (curso eval.)")
	private Long porcentaje;
	
	@Schema(description = "Abreviatura de la Oferta Matrícula Genérico")
	private String abreviatura;
	
	@Schema(description = "Campo de texto libro para indicar el anexo regulador de la OMG")
	private String anexo;
	
	@Schema(description = "Nombre de la función que realiza las comprobaciones adicionales al formalizar matrículas en la OMG (si existe)")
	private String comAdi;
	
	@Schema(description = "Nombre de la función que realiza las comprobaciones adicionales para las materias de las matrículas en la OMG")
	private String comAdiMatMat;
	
	@Schema(description = "Función para determinar si un alumno repite todas las materias de su actual OMG")
	private String criterioRep;
	
	@Schema(description = "Campo de texto libro para indicar el Decreto regulador de la OMG")
	private String decreto;
	
	@Schema(description = "Literal de la etapa para Composición de Unidades (proviene de TLETAPAS)")
	private String etapaPlantilla;
	
	@Schema(description = "Campo de texto libro para indicar el Real Decreto regulador de la OMG")
	private String normativa;

}