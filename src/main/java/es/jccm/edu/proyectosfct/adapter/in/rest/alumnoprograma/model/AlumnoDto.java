package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno", description = "Descripcion para el modelo de alumno")
public class AlumnoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del alumno")
	private Long id;
	
	@Schema(description = "Nombre completo")
	private String nombreCompleto;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "id de la matricula")
	private Long idMatricula;
	
	@Schema(description = "id de la unidad")
	private Long idUnidad;
	
	@Schema(description = "Nombre de la unidad")
	private String nombreUnidad;
	
	@Schema(description="Fecha inicio extraordinaria")
	private Date dateIniExt;
	
	@Schema(description="Fecha fin extraordinaria")
	private Date dateFinExt;
	
	@Schema(description="Fecha inicio fuera de provincia")
	private Date dateIniProv;
	
	@Schema(description="Fecha fin fuera de provincia")
	private Date dateFinProv;
	
	@Schema(description="Horario extraordinaria")
	private String horarioExt;
	
	@Schema(description="Horario fuera de provincia")
	private String horarioProv;
	
	@Schema(description="LG_FINDE_EXT")
	private Integer lgfindeext;
	
	@Schema(description="LG_VACAC_EXT")
	private Integer lgvacacext;

	@Schema(description="Número de la SS de un alumno")
	private String tnuss;

	@Schema(description="Campo que recoge 0 si el alumno no cotiza en la SS o 1 si el alumno si cotiza en la SS")
	private Integer lgCotiza;

	@Schema(description="Campo que recoge 0 si el alumno ya tenía un número de la SS y 1 si el alumno no tenía número de la SS")
	private Integer lgNuss;
	
	@Schema(description="Identificador del convenio alumnado")
	private Long idConvAlu;
	
	@Schema(description="Campo que recoge 0 si el campo Número de la SS no es editable y 1 en caso contrario")
	private Integer lgEditable;
	
	@Schema(description="Campo que recoge 0 si el alumnado es mayor de 16 años y si 1 si es menor")
	private Integer lgMenor;
	
    @Schema(description = "Campo que recoge 0 si el alumno no es erasmus, 1 si tiene erasmus sin beca y 2 para erasmus con beca")
    private Integer lgErasmus;

	@Schema(description = "Campo que determina si el alumno puede dejar de cotizar si ya estaba cotizando 1 Puede dejar de cotizar, 0 No puede")
	private Integer cotizaIntermitente;

	@Schema(description = "Campo cuyo valor recoge un mensaje que indica o no si se puede quitar un alumno de un programa")
	private String dsMotivo;
	
    @Schema(description = "Campo que indica la vinculación del alumno con el convenio y proyecto (empresa)")
    private Integer lgAlumnoEnEmpresa;

	@Schema(description = "Campo que recoge la localidad donde el alumno realiza la FCT")
	private String dsLocalidadFct;
}
