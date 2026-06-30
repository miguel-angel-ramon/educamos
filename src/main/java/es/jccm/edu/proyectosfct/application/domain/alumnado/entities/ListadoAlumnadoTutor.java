package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoAlumnadoTutor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="nombreAlumno")
	private String nombreAlumno;
	
	@Column(name="nombreEmpresa")
	private String nombreEmpresa;
	
	@Column(name="tipoEmpresa")
	private String tipoEmpresa;
	
	@Column(name="curso")
	private String curso;
	
	@Column(name="unidad")
	private String unidad;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="partes")
	private String partes;
	
	@Column(name="Identificador del archivo Rodal de evaluacion firmado")
	private String idevarodal;
	
	@Column(name="Nombre del archivo Rodal de evaluacion firmado")
	private String txevarodal;
	
	@Column(name="Fecha de evaluacion firmado")
	private String ffirma;

	@Column(name="Numero de la Seguridad Social del alumno")
	private String tnuss;

	@Column(name="nussActualizado")
	private String nussActualizado;

	@Column(name = "Campo que determina si se pueden borrar los partes mensuales")
	private Integer puedeBorrar;	
	
	@Column(name="Descripcion seguridad social")
	private String seguridad;
	
	@Column(name="Campo que determina si un alumno cotiza o no en la Seguridad Social")
	private Integer cotiza;
	
	@Column(name="Decripcion familia")
	private String familia;
	
	@Column(name="Descripcion orden")
	private String orden;	

	
	@Column(name="nombreTutor")
	private String nombreTutor;

	@Column(name="Matricula alumno")
	private Long xMatricula;

	@Column(name="Estado del plan")
	private String estado;

	@Column(name="Campo que indica si tiene o no cotizaciones mensuales del mes anterior al actual por enviar")
	private Integer avisoMes;

	@Column(name="Flag que indica si tiene un número de la SS provisional (extranjero)")
	private Integer nussProvisional;

	@Column(name="Campo que indica si esta excluído de la FCT")
	private Integer lgExcluir;

}