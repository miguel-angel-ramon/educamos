package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc.DgcCentroDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc.DgcEmpleadoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidades centro", description = "Descripcion para el modelo de unidades centro")
public class UnidadesCentroDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de unidades centro")
    private Long id;
	
	@Schema(description = "Anno")
	private Integer cAnno;
	
	@Schema(description = "Fecha toma posesion del empleado")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaTomaPosesion;
	
	@Schema(description = "Periodo")
	private Integer periodo;
	
	@Schema(description = "Orden")
	private Integer orden;
	
	@Schema(description = "Tipo")
	private String tipo;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "Numero de alumnos")
	private Integer numeroAlumnos;
	
	@Schema(description = "Capacidad")
	private Integer capacidad;
	
	@Schema(description = "Ubicacion externa")
	private String ubicacionExterna;
	
	@Schema(description = "horario")
	private String horario;
	
	@Schema(description = "Esta publicada")
	private String publicada;
	
	@Schema(description = "Es visible")
	private String visible;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Centro")
	private DgcCentroDto centro;
	
	@Schema(description = "Empleado")
	private DgcEmpleadoDto empleado;
	
	@Schema(description = "Provincia")
	private Long provincia;
	
	@Schema(description = "Municipio")
	private Long municipio;
	
	@Schema(description = "Localidad")
	private Long localidad;
	
	@Schema(description = "ActAulaDu")
	private Long actAulaDu;
	
	@Schema(description = "Turno")
	private Long turno;
	
	@Schema(description = "Matricula del delegado")
	private Long matriculaDelegado;
	
	@Schema(description = "Matricula del subdelegado")
	private Long matriculaSubdelegado;
	
	@Schema(description = "Agrupacion")
	private Long agrupacion;
	
	

}
