package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "acta evaluacion", description = "Acta evaluacion")
public class ActaEvaluacionListDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Idenfificador convocatoria")
	private Long idConvocatoria;	
	
	@Schema(description = "Descripcion convocatoria")
	private String convocatoria;	
	
	@Schema(description = "Descripcion tipo convocatoria")
	private String tipo;	
	
	@Schema(description = "Idenfificador curso convocatoria")
	private Long idCurso;	
	
	@Schema(description = "Descripcion curso convocatoria")
	private String curso;	
	
	@Schema(description = "Idenfificador omc")
	private Long idOmc;	
	
	@Schema(description = "Idenfificador tipo expediente")
	private Long idTipoexp;	
	
	@Schema(description = "descripcion abrevitura")
	private String abreviatura;	
	
	@Schema(description = "Idenfificador unidad convocatoria")
	private Long idUnidad;	
	
	@Schema(description = "Idenfificador periodo unidad")
	private Integer nPeriodo;
	
	@Schema(description = "Descripcion unidad convocatoria")
	private String unidad;	
	
	@Schema(description = "Fecha sesion convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date sesion;	
	
	@Schema(description = "Descripcion estado convocatoria")
	private String estado;

	@Schema(description = "Identificador convocatoria unidad")
	private Long idConvUnidad;
	
	@Schema(description = "Idenfificador estado convocatoria")
	private Long idEstado;	
	
	@Schema(description = "Id del fichero de Rodal")
	private String idRodal;	
	
	@Schema(description = "Nombre del fichero de Rodal")
	private String fichero;		
	
	@Schema(description = "Nombre de la provincia")
	private String provincia;	
	
	@Schema(description = "Nombre del municipio")
	private String municipio;	
	
	@Schema(description = "Nombre del centro")
	private String centro;
	
	@Schema(description = "Nombre del centro")
	private String acta;	
	
	@Schema(description = "Nombre del centro")
	private Long idDocumento;	
	
	@Schema(description = "Puede editar adjuntos")
	private Integer permiteadjuntos;	
	
	@Schema(description = "Puede generar actas")
	private Integer permitegenerar;
	
	@Schema(description = "Fecha fin convocatoria omc")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date ffinconvomc;	
	
	@Schema(description = "Fecha fin convocatoria centro")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date ffinconvcen;	
	
	@Schema(description = "Identificador convocatoria OMC")
	private Long idConvOmc;
	
	@Schema(description = "Identificador materia curso")
	private Long idmateriac;
	
	@Schema(description = "Descripcion materia curso")
	private String materia;
	
	@Schema(description = "Identificador del adjunto")
	private Long idAdjunto;
	
	@Schema(description = "Puede firmar el documento")
	private Integer permitefirmar;
	
	@Schema(description = "Indica el total de adjuntos")
	private Integer totalAdjuntos;
	
	@Schema(description = "Indica la descripcion del aviso que debe aparecer en la columna Adjuntos pendientes de firma")
	private String aviso2;	
	
	@Schema(description = "Indica la descripcion del aviso que debe aparecer en la columna Adjuntos pendientes de mi firma")
	private String aviso3;
	

}
