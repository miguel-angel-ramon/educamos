package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model;

import java.io.Serializable;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosFormacionDto", description = "Información completa de la empresas participantes en el el plan")
public class EmpresaDto implements Serializable {
	
	public EmpresaDto(String nombre, String tutor, String mail, String tlf,String idConvProyAlu, List<PeriodoDto> periodos) {
        this.nombre = nombre;
        this.tutor = tutor;
        this.mail = mail;
        this.tlf = tlf;
        this.idConvProyAlu = idConvProyAlu;
        this.periodos = periodos; 
    }

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nombre de la empresa")
	private String nombre;
	
	@Schema(description = "Tutor de la empresa")
    private String tutor;
	
	@Schema(description = "Email de la empresa")
    private String mail;
	
	@Schema(description = "Teléfono de la empresa")
    private String tlf;

    @Schema(description = "Id del convenio proyecto del alumno")
    private String idConvProyAlu;
	
	@Schema(description = "Periodos de formación en la empresa")
    private List<PeriodoDto> periodos;

}
