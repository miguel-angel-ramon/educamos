package es.jccm.edu.proyectosfct.adapter.in.rest.cursoacademico.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FamiliaDto", description = "Descripcion para el modelo de Familias FCT")
public class CursoAcademicoDto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "cAnno")
	private Long cAnno;
	
	@Schema(description = "fechaInicio")
	private Date fechaInicio;
	
	@Schema(description = "fechaFinal")
	private Date fechaFinal;
	
	@Schema(description = "smi")
	private Long smi;
	
	@Schema(description = "cursoActual")
	private String cursoActual;
	
	@Schema(description = "fechaIniConJunEle")
	private Date fechaIniConJunEle;
	
	@Schema(description = "fechaFinConJunEle")
	private Date fechaFinConJunEle;
	
	@Schema(description = "fechaIniAdmCanConEsc")
	private Date fechaIniAdmCanConEsc;
	
	@Schema(description = "fechaFinAdmCanConEsc")
	private Date fechaFinAdmCanConEsc;
	
	@Schema(description = "fechaLisProCanConEsc")
	private Date fechaLisProCanConEsc;
	
	@Schema(description = "fechaLisDefCanConEsc")
	private Date fechaLisDefCanConEsc;
	
	@Schema(description = "fechaLimConPapConEsc")
	private Date fechaLimConPapConEsc;
	
	@Schema(description = "fechaIniCamEleConEsc")
	private Date fechaIniCamEleConEsc;
	
	@Schema(description = "fechaFinCamEleConEsc")
	private Date fechaFinCamEleConEsc;
	
	@Schema(description = "fechaEleAluConEsc")
	private Date fechaEleAluConEsc;
	
	@Schema(description = "fechaEleTutConEsc")
	private Date fechaEleTutConEsc;
	
	@Schema(description = "fechaEleProConEsc")
	private Date fechaEleProConEsc;
	
	@Schema(description = "fechaLimConConEsc")
	private Date fechaLimConConEsc;
	
	@Schema(description = "esVisible")
	private String esVisible;
	
	@Schema(description = "fechaAdjDefSol")
	private Date fechaAdjDefSol;
	
	@Schema(description = "fechaAdjProSol")
	private Date fechaAdjProSol;
	
	@Schema(description = "fechaFinAdmSol")
	private Date fechaFinAdmSol;
	
	@Schema(description = "fechaIniAdmSol")
	private Date fechaIniAdmSol;
	
	@Schema(description = "fechaEnvSolAyu")
	private Date fechaEnvSolAyu;
	
	@Schema(description = "fechaFinPriTrimestre")
	private Date fechaFinPriTrimestre;
	
	@Schema(description = "fechaIniSegTrimestre")
	private Date fechaIniSegTrimestre;
	
	@Schema(description = "fechaFinSegTrimestre")
	private Date fechaFinSegTrimestre;
	
	@Schema(description = "fechaCierrePriTrim")
	private Date fechaCierrePriTrim;
	
	@Schema(description = "fechaCierreSegTrim")
	private Date fechaCierreSegTrim;
	
	@Schema(description = "fechaCierreTerTrim")
	private Date fechaCierreTerTrim;
	
	@Schema(description = "puedeGestionarFiestasLocales")
	private String puedeGestionarFiestasLocales;

}
