package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado autorizaciones anexos", description = "Descripcion para el modelo listado autorizaciones")
public class ListadoAutorizacionesAnexosDto implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del tipo")
	private Long id;
	
	@Schema(description = "Fecha inicio")
	private Date fInicio;
	
	@Schema(description = "Fecha fin")
	private Date fFin;
	
	@Schema(description = "Periodo")
	private Integer annoPeriodo;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Id tutor")
	private Long idTutor;	
	
	@Schema(description = "Nombre")
	private String nombreTipo;
	
	@Schema(description = "Nombre Tutor")
	private String tutor;	
	
	@Schema(description = "Id de rodal")
	private String idRodal;
	
	@Schema(description = "Fichero de rodal")
	private String fichero;
	
	@Schema(description = "Id del historial rodal")
	private Long idHistorialRodal;
	
	@Schema(description = "Periodo")
	private String periodo;
	
	@Schema(description = "Id periodo")
	private Long idPeriodo;
	
	@Schema(description = "Id del historial")
	private Long idHistorial;
	
	@Schema(description = "Familia")
	private String familia;
	
	@Schema(description = "Unidad")
	private String unidad;
	
	@Schema(description = "Id curso")
	private Long idCurso;
	
	@Schema(description = "Id familia")
	private Long idFamilia;
	
	@Schema(description = "Curso")
	private String curso;
	
	@Schema(description = "Id modalidad")
	private Long idUnidad;
	
	@Schema(description = "Puede editar")
	private Integer puedeeditar;
	
	@Schema(description = "Id perfil")
	private Long idPerfil;
	
	@Schema(description = "Fecha de ultima generación del anexo")
	private String fultgen;
	
	@Schema(description = "Puede firmar")
	private Integer puedefirmar;	
	
	@Schema(description = "Puede generar")
	private Integer puedegenerar;	
	
	@Schema(description = "Id del historial adjunto")
	private Long idHistorialAdjunto;
	
	@Schema(description = "Adjunto de rodal")
	private String adjunto;

	@Schema(description = "Fecha de actualización/creación de la última autorización asignada")
	private String fhAutMax;

	@Schema(description = "Número de petición del anexo")
	private Integer nuPeticion;
	
	@Schema(description = "Puede crear")
	private Integer puedecrear;
	
	@Schema(description = "comunicacion")
	private Integer comunicacion;
	

}

