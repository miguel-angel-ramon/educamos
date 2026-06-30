package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnadoTutorLofpDto", description = "DTO para el listado de alumnado con tutor en LOFP")
public class AlumnadoTutorLofpDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "Nombre completo del alumno")
    private String nombreAlumno;

    @Schema(description = "Tipo de empresa")
    private String tipoEmpresa;

    @Schema(description = "Nombre de la empresa asociada")
    private String nombreEmpresa;

    @Schema(description = "Curso del alumno")
    private String curso;

    @Schema(description = "Unidad")
    private String unidad;

    @Schema(description = "Descripción del programa")
    private String descripcion;

    @Schema(description = "Partes")
    private String partes;

    @Schema(description = "Identificador del archivo Rodal de evaluación firmado")
    private String idEvaRodal;

    @Schema(description = "Nombre del archivo Rodal de evaluación firmado")
    private String txEvaRodal;

    @Schema(description = "Fecha de firma de la evaluación")
    private String fFirma;

    @Schema(description = "Número de la Seguridad Social del alumno")
    private String tnuss;

    @Schema(description = "Número de la Seguridad Social actualizado del alumno")
    private String nussActualizado;

    @Schema(description = "Campo que determina si un alumno cotiza o no en la Seguridad Social")
    private Integer cotiza;

    @Schema(description = "Campo que determina si se pueden borrar los partes mensuales")
    private Integer puedeBorrar;

    @Schema(description = "Nombre del tutor")
    private String nombreTutor;

    @Schema(description = "Familia")
    private String familia;

    @Schema(description = "Orden")
    private String orden;

    @Schema(description = "Seguridad social")
    private String seguridad;

    @Schema(description = "Matrícula del alumno")
    private Long xMatricula;

    @Schema(description = "Estado del plan")
    private String estado;

    @Schema(description = "ID del centro del alumno")
    private Long idCentro;
    
    @Schema(description = "Puede evaluar")
    private Integer puedeEvaluar;

    @Schema(description = "Campo que indica si tiene o no cotizaciones mensuales del mes anterior al actual por enviar")
    private Integer avisoMes;

    @Schema(description = "Id del convenio proyecto")
    private Long id;

    @Schema(description = "Campo que indica si el número de la SS es provisional(extranjero)")
    private Integer nussProvisional;

    @Schema(description = "Campo que indica las advertencias en partes")
    private String advertenciaPartes;

    @Schema(description = "Campo que indica si el alumno está excluido)")
    private Integer lgExcluir;

}
