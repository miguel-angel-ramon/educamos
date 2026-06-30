package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "DatosFormacionPlanDto", description = "Información completa para la formación del alumnado en empresas para planes")
public class DatosFormacionPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Schema(description = "Curso académico")
    private String anno;

    @Schema(description = "Tutor del alumno")
    private String tutor;
    
    @Schema(description = "Curso de la matricula")
    private String curso;    
    
    @Schema(description = "Nombre alumno")
    private String alumno;

    @Schema(description = "Flag que indica el estado de la validación del plan")
    private String validado;

    @Schema(description = "Fecha de validación del plan")
    private String fvalidacion;

    @Schema(description = "Empresa participantes en el plan")
    private List<DatosFormacionEmpresaPlanDto> empresas;
    
    public DatosFormacionPlanDto(String anno, String curso, String tutor, String validado, String fvalidacion, List<DatosFormacionEmpresaPlanDto> empresas, String alumno) {
        this.anno = anno;
        this.curso = curso;
        this.tutor = tutor;
        this.validado = validado;
        this.fvalidacion = fvalidacion;
        this.empresas = empresas;
        this.alumno = alumno;
    }


    
    
   
}
