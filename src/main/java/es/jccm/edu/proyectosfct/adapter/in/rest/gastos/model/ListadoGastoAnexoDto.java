package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado gasto anexo", description = "Descripcion para el modelo listado gasto anexo")
public class ListadoGastoAnexoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del tipo gasto")
	private Long id;
	
	@Schema(description = "Periodo")
	private Integer annoPeriodo;
	
	@Schema(description = "Fecha inicio")
	private Date fInicio;
	
	@Schema(description = "Fecha fin")
	private Date fFin;
	
	@Schema(description = "Nombre")
	private String nombreTipo;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Id tutor")
	private Long idTutor;	
	
	@Schema(description = "Nombre Tutor")
	private String tutor;	
	
	@Schema(description = "Id de rodal")
	private String idRodal;
	
	@Schema(description = "Fichero de rodal")
	private String fichero;
	
	@Schema(description = "Id del historial rodal")
	private Long idHistorialRodal;
	
	@Schema(description = "Id del historial")
	private Long idHistorial;
	
	@Schema(description = "Periodo")
	private String periodo;
	
	@Schema(description = "Curso")
	private String curso;	
	
	@Schema(description = "Unidad")
	private String unidad;
	
	@Schema(description = "fultgen")
	private String fultgen;
	
	@Schema(description = "Id periodo")
	private Long idPeriodo;
	
	@Schema(description = "Id curso")
	private Long idCurso;
	
	@Schema(description = "Id modalidad")
	private Long idUnidad;
	
	@Schema(description = "Puede firmar")
	private Integer puedefirmar;
	
	@Schema(description = "Id Perfil")
	private Long idperfil;
	
	@Schema(description = "Puede generar")
	private Integer puedegenerar;

	@Schema(description = "Fecha de actualización/creación del último gasto asignado")
	private String fhGastMax;
}

