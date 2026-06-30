package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.SedeEmpresaDto;
import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.ConveniosFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.EmpresaTrabajadorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Horario Convenios a proyecto", description = "Descripcion para el modelo de asignar convenios a proyectos con horario")
public class ConveniosProyectosHorarioAlumnoFctDto implements Serializable {
	
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
	
	@Schema(description = "Codigo de proyecto")
	private String codProyecto;

	@Schema(description = "Número total de horas convenio proyecto")
	private Integer nuHorasTotales;
	
	//--------------------------------------------------------------
	
	@Schema(description = "Identificar del convenio asociado")
	private ConveniosFctDto convenio;
	
	@Schema(description = "Identificar del proyecto asociado")
	private ProyectosDto proyecto;

	@Schema(description = "Sede del centro de trabajo del responsable")
	private SedeEmpresaDto sedeResp;
	
	@Schema(description = "Representante de la empresa que firma el convenio asociado al proyecto")
	private EmpresaTrabajadorDto trabajador;
	
	@Schema(description = "Periodos y tramos de los horarios del alumnado")
	private List<ConvProyHorPeriodoFctDto> horarioAlumno;

	@Schema(description = "Sede del centro de trabajo del representante")
	private SedeEmpresaDto sede;

	@Schema(description = "Representante del centro de trabajo")
	private EmpresaTrabajadorDto representante;

}
