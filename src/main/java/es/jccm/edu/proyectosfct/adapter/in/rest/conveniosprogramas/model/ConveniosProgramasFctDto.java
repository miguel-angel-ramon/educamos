package es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.SedeEmpresaDto;
import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.ConveniosFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.EmpresaTrabajadorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ProgramaFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a programas", description = "Descripcion para el modelo de Convenios a programas")
public class ConveniosProgramasFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5705530794004369745L;

	@Schema(description = "Id del convenio a programa")
	private Long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha inicio del convenio al programa")
	private Date fechaIni;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha fin del convenio al programa")
	private Date fechaFin;
	
	@Schema(description = "Identificar rodal anexo I")
	private String idAneiRodal;
	
	@Schema(description = "Nombre fichero anexo I")
	private String txAneiFichero;
	
	@Schema(description = "Nombre de anexosII firmados y subidos a Rodal")
	private Integer nuAnexosII;

	@Schema(description = "Número total de horas asociadas al convenio")
	private Integer nuHorasTotales;
	
	@Schema(description = "Identificar del convenio asociado")
	private ConveniosFctDto convenio;
	
	@Schema(description = "Identificar de los programas asociados al convenio")
	private ProgramaFctDto programa;

	@Schema(description = "Sede del centro de trabajo del responsable")
	private SedeEmpresaDto sedeResp;

	@Schema(description = "Representante de la empresa que firma el convenio asociado al programa")
	private EmpresaTrabajadorDto trabajador;

	@Schema(description = "Listado de alumnos por centro")
	private List<AlumnoDto> alumnos;

	@Schema(description = "Sede del centro de trabajo del representante")
	private SedeEmpresaDto sede;

	@Schema(description = "Representante del centro de trabajo")
	private EmpresaTrabajadorDto representante;

}
