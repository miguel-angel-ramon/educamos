package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Programas formacion ", description = "Dto para los Programas formacion")
public class ProgramasPFEDto implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Schema(name = "id")
    private Long id;

    @Schema(name = "idCurso")
    private Long idCurso;

    @Schema(name = "idCentro")
    private Long idCentro;
    
    @Schema(name = "anno")
    private Integer anno;

    @Schema(name = "nuAnnoDesde")
    private Integer nuAnnoDesde;
    
    @Schema(name = "nuAnnoHasta")
    private Integer nuAnnoHasta;
    
    @Schema(name = "lgActivo")
    private Integer lgActivo;    

    @Schema(name = "lgAlcance")
    private Integer lgAlcance;
    
    @Schema(name = "nuAlumnado")
    private Integer nuAlumnado;
    
    @Schema(name = "lgModalidad")
    private Integer lgModalidad;
	
    @Schema(name = "lgAmpliacion")
    private Integer lgAmpliacion;
    
    @Schema(name = "lgLogse")
    private Integer lgLogse;
    
    @Schema(name = "nuHorasFpe")
    private Integer nuHorasFpe;
    
    @Schema(name = "nuHorasCur")
    private Integer nuHorasCur;    
    
    @Schema(name = "lgDiario")
    private Integer lgDiario;
    
    @Schema(name = "lgSemanal")
    private Integer lgSemanal;
    
    @Schema(name = "lgMensual")
    private Integer lgMensual;
    
    @Schema(name = "lgOtros")
    private Integer lgOtros;   
	
    @Schema(name = "nuHorasComFpe")
    private Integer nuHorasComFpe;
    
    @Schema(name = "nuHorasComCur")
    private Integer nuHorasComCur;
    
    @Schema(name = "dsCooModProf")
    private String dsCooModProf;
    
    @Schema(name = "dsEmpOrgCol")
    private String dsEmpOrgCol;
    
    @Schema(name = "dsCarConAlt")
    private String dsCarConAlt;
    
    @Schema(name = "dsConBeca")
    private String dsConBeca;
    
    @Schema(name = "lgAutorizacion")
    private Integer lgAutorizacion;
    
    @Schema(name = "lgAut400")
    private Integer lgAut400;
    
    @Schema(name = "lgAutForcom")
    private Integer lgAutForcom;
    
    @Schema(name = "lgAutCurEsp")
    private Integer lgAutCurEsp;
    
    @Schema(name = "lgAut3Cur")
    private Integer lgAut3Cur;
    
    @Schema(name = "lgAutCamReg")
    private Integer lgAutCamReg;
    
    @Schema(name = "lgAutProEns")
    private Integer lgAutProEns;    
    
    @Schema(name = "cUsuProg")
    private Long cUsuProg;
    
    @Schema(name = "lgCurso")
    private Integer lgCurso;     
    
    @Schema(name = "puedeModificar")
    private Integer puedeModificar; 
    
    @Schema(name = "dsCurso")
    private String dsCurso;
    
    
    
}
