package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.MateriaCursoOfertaMatriculaGenericaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CentroDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.EmpleadoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.OfertaMatriculaGenericoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AulaVirtualDTO", description = "DTO Aula Virtual")
public class AulaVirtualDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Aula Virtual")
	private Long id;
	
	@Schema(description = "Localizador del Aula Virtual")
	private String localizador;
	
	@Schema(description = "Periodo del curso al que pertenece el Aula Virtual")
	private Integer periodo;
	
	@Schema(description = "Indica si está activa o no el Aula virtual")
	private String lActiva;
	
	@Schema(description = "Id. de la plataforma")
	private String idPlataforma;
	
	@Schema(description = "Token de plataforma")
    private String tokenPlataforma;
	
	@Schema(description = "Id. del Curso")
	private String idCurso;
	
	@Schema(description = "Descripción del Aula Virtual")
	private String descripcion;
	
	@Schema(description = "Año")
	private Integer  anno;
	
	@Schema(description = "Alumnos pertenecientes al Aula Virtual")
    private List<AlumnoDTO> alumnos;
	
	// ---------- Relationships -----------

	@Schema(description = "Curso al que pertenece el Aula Virtual")
	private OfertaMatriculaGenericoDTO ofertaMatriculaGenerico;
	
	@Schema(description = "Materia asociada al aula (podría ser 0 para Tutoría)")
	private MateriaCursoOfertaMatriculaGenericaDTO materiaOMG;
	
	@Schema(description = "Empleado asociado al Aula Virtual")
	private EmpleadoDTO empleado;
	
	@Schema(description = "Descripción larga de Calificación")
	private CentroDTO centro;
}