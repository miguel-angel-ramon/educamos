package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.SedeEmpresaDto;
import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.ConveniosFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.EmpresaTrabajadorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a proyecto", description = "Descripcion para el modelo de asignar convenios a proyectos")
public class ConveniosProyectoDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3048941123848959085L;

	@Schema(description = "Id del proyecto convenios")
	private Long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha inicio del convenio al programa")
	private Date fechaIni;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha fin del convenio al programa")
	private Date fechaFin;
	
	@Schema(description = "Observaciones")
	private String observaciones;
	
	@Schema(description = "Id Anei Rodal")
	private String idAneiRodal;
	
	@Schema(description = "Tx Anei Ficheros")
	private String txAneiFichero;
	
	@Schema(description = "Nombre de anexosII firmados y subidos a Rodal")
	private Integer nuAnexosII;
	
	@Schema(description = "Codigo de Proyecto")
	private String codProyecto;
	
	//--------------------------------------------------------------
	
	@Schema(description = "Identificar del convenio asociado")
	private ConveniosFctDto convenio;
	
	@Schema(description = "Identificar del proyecto asociado")
	private ProyectosDto proyecto;

	@Schema(description = "Centro de trabajo del responsable")
	private SedeEmpresaDto sedeResp;
	
	@Schema(description = "Representante de la empresa que firma el convenio asociado al proyecto")
	private EmpresaTrabajadorDto trabajador;
	
	@Schema(description = "Listado de alumnos por centro")
	private List<AlumnoDto> alumnos;

	@Schema(description = "Número total de horas asociadas al convenio")
	private Integer nuHorasTotales;

	@Schema(description = "Centro de trabajo del representante")
	private SedeEmpresaDto sede;

	@Schema(description = "Representante del centro de trabajo")
	private EmpresaTrabajadorDto representante;
	
	
	
}
