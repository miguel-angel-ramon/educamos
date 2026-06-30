package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosFormacionEmpresaDto", description = "Información completa de la empresas participantes en el plan")
public class DatosFormacionEmpresaDto implements Serializable {
	
	public DatosFormacionEmpresaDto(String nombre, String tutor, String mail, String tlf, String idConvProAlu, String idEvaRodal, String txEvaRodal, String fFirma, String cotiza,List<PeriodoDto> periodo, List<AluDTO> idConvPro) {
        this.nombre = nombre;
        this.tutor = tutor;
        this.tlf = tlf;
        this.mail = mail;
        this.txEvaRodal = txEvaRodal;
        this.idConvProAlu = idConvProAlu;
        this.idEvaRodal = idEvaRodal;
        this.cotiza = cotiza;
        this.fFirma = fFirma;
        this.periodo=periodo;
        this.idConvPro=idConvPro;
    }

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nombre de la empresa")
	private String nombre;
	
	@Schema(description = "Id del convenio proyecto del alumno")
    private String idConvProAlu;
	
	@Schema(description = "Email de la empresa")
    private String mail;
	
	@Schema(description = "Tutor de la empresa")
    private String tutor;
	
	@Schema(description = "Id del convenio proyecto del alumno")
    private String idEvaRodal;
	
	@Schema(description = "Teléfono de la empresa")
    private String tlf;    

    @Schema(description = "Id del convenio proyecto del alumno")
    private String txEvaRodal;   
    
    @Schema(description = "Id del convenio proyecto del alumno")
    private String fFirma;
    
    @Schema(description = "Id del convenio proyecto del alumno")
    private String cotiza;

    @Schema(description = "Periodos de formación en la empresa")
    private List<PeriodoDto> periodo;

    @Schema(description = "Periodos de formación en la empresa")
    private List<AluDTO> idConvPro;
}
