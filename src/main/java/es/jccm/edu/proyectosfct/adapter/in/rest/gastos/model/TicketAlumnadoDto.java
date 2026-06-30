package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ticket gasto alumno", description = "Descripcion para el modelo de Ticket del gasto del alumno")
public class TicketAlumnadoDto extends BaseAudited implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gasto del alumno")
	private Long id;
	
	@Schema(description = "Id del gasto del alumno")
	private Date fechaRegistro;

	@Schema(description = "Id del gasto del alumno")
	private String idTicketAluRodal;
	
	@Schema(description = "Id del gasto del alumno")
	private String nombreFichero;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Id del gasto del alumno")
	private GastoAlumnadoDto gastoAlumnado;
	
	@Schema(description = "Id del gasto del alumno")
	private Long idUsuario;
}
