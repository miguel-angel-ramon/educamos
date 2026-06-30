package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.jccm.edu.shared.application.domain.baseAudited.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(schema= "DELPHOS_SEGEDU" ,name="TLPDCSEGUILINACT")
public class CenObjLineaSeguimiento extends BaseAudited {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_SEGEDU.TLS_LINSEGUI")
	@SequenceGenerator(name = "DELPHOS_SEGEDU.TLS_LINSEGUI", sequenceName = "DELPHOS_SEGEDU.TLS_LINSEGUI", allocationSize = 1)
	@Column(name = "X_SEGUI_LIN_ACT")
	private Long id;

	@Column(name = "F_INI_SEGUI")
	private Date fechaInicioSeguimiento;

	@Column(name = "F_FIN_SEGUI")
	private Date fechaFinSeguimiento;

	@Column(name = "PORC_EJEC")
	private Double porcentajeEjecucion;

	@Column(name = "D_TAREAS")
	private String tareas;

	@Column(name = "D_VALORACION")
	private String valoracion;

	@Column(name = "D_DIFICULTADES_ACCIONES")
	private String dificultadesAcciones;

	@Column(name = "D_COMENTARIOS")
	private String comentarios;


	@Column(name="X_PDCCENOBJLINACT")
	private Long idCenObjLineaActuacion;


	
}
