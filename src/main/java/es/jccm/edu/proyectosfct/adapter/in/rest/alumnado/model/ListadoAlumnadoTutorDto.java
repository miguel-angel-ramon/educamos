package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import javax.persistence.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoAlumnadoTutorDto", description = "ListadoAlumnadoTutorDto")
public class ListadoAlumnadoTutorDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="nombreAlumno")
	private Long id;
	
	@Column(name="nombreAlumno")
	private String nombreAlumno;
	
	@Column(name="tipoEmpresa")
	private String tipoEmpresa;
	
	@Column(name="nombreEmpresa")
	private String nombreEmpresa;
	
	@Column(name="curso")
	private String curso;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="partes")
	private String partes;
	
	@Column(name="unidad")
	private String unidad;
	
	@Column(name = "Identificador del archivo Rodal de evaluacion firmado")
	private String idEvaRodal;
	
	@Column(name = "Nombre del archivo Rodal de evaluacion firmado")
	private String txEvaRodal;
	
	@Column(name = "Fecha de firma de la evaluación")
	private String fFirma;

	@Column(name="Numero de la Seguridad Social del alumno")
	private String tnuss;

	@Column(name="nussActualizado")
	private String nussActualizado;

	@Column(name="Campo que determina si un alumno cotiza o no en la Seguridad Social")
	private Integer cotiza;

	@Column(name = "Campo que determina si se pueden borrar los partes mensuales")
	private Integer puedeBorrar;
	
	@Column(name="nombreTutor")
	private String nombreTutor;
	
	@Column(name="Descripcion familia")
	private String familia;
	
	@Column(name="Descripcion orden")
	private String orden;
	
	@Column(name="Descripcion seguridad social")
	private String seguridad;

	@Column(name="Campo que indica si tiene o no cotizaciones mensuales del mes anterior al actual por enviar")
	private Integer avisoMes;
	
	@Column(name="Matricula")
	private Long xMatricula;

	@Column(name="Campo que indica si tiene o no cotizaciones mensuales del mes anterior al actual por enviar")
	private Integer nussProvisional;
	
	@Column(name="Estado del plan")
	private String estado;

	@Column(name="Campo que indica si el alumno esta excluido de las prácticas")
	private Integer lgExcluir;

}
