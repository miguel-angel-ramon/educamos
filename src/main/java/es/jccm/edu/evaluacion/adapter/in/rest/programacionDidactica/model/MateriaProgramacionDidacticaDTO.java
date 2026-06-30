package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Materias Programación Didáctica", description = "Proyección para rescatar las materias de un centro")
public class MateriaProgramacionDidacticaDTO {

	@Schema(description = "Id de materia de curso genérica")
	private Long idMateriaOmg;
	
	@Schema(description = "Descripción de la materia")
	private String materia;

	@Schema(description = "Id de la oferta de la matrícula")
	private Long idOfertaMatrig;

	@Schema(description = "Id del departamento de la asignatura")
	private Long idDepartamento;

	@Schema(description = "nombre del departamento de la asignatura")
	private String nombreDepartamento;
	
	@Schema(description = "Id de curso del centro")
	private Long idCurso;
	
	@Schema(description = "Descripción del curso")
	private String curso;

	@Schema(description = "Descripción del curso de nivel de adaptación cucrricular")
	private String nivelCurricular;
	
	@Schema(description = "Id del curso de nivel de adaptación curricular")
	private Long idNivelCurricular;
	
	@Schema(description = "Estado de la programación didáctica")
	private String estado;

	@Schema(description = "Id de la programación didáctica")
	private Long idProgramacionDidactica;
	
	@Schema(description = "Número de unidades de programación asociadas a la programación didáctica")
	private Integer countUnidadesProgramacion;
	
	@Schema(description = "Nombres separados por comas de las unidades de programación asociadas a la programación didáctica")
	private String nombresUnidadesProgramacion;
	
	@Schema(description = "Identificador del empleado que ha creado y por tanto tan solo él puede editar o borrar la programación didáctica")
	private Long idEmpleado;
	
    @Schema(description = "Nombre del empleado que ha creado y por tanto tan solo él puede editar o borrar la programación didáctica")
    private String nombreEmpleado;
    
    @Schema(description = "Abreviatura de la materia")
    private String abreviatura;

	@Schema(description = "Abreviatura de la materia acnee")
	private String abreviaturaAcnee;

	@Schema(description = "Id del empleado responsable actual de la materia de la programación didáctica")
	private Long idEmpleadoResponsableActual;

	@Schema(description = "Id documento rodal")
	private String idRodal;

	@Schema(description = "Nombre del fichero")
	private String nombreFichero;
	@Schema(description = "Editores")
	private List<HistorialResponsableProgramacionDidacticaDTO> listaEditores;
	
}
