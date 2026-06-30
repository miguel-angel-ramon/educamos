package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Schema(name = "Semanas", description = "Descripcion para el modelo de semanas del seguimiento alumnado")
public class SemanaLOFPDto {

	@Schema(description = "Número de la semana")
	@Column(name = "SEMANA_NUMERO")
	private String semanaNumero;

	@Schema(description = "Flag que indica si la semana es la actual o no")
	@Column(name = "ES_SEMANA_ACTUAL")
	private Integer esSemanaActual;

	@Schema(description = "Flag que indica si es visible o no la semana")
	@Column(name = "SEMANA_VISIBLE")
	private Integer semanaVisible;

	@Schema(description = "Fecha que representa el inicio de la semana")
	@Column(name = "FECHA_INICIO_SEM")
	private String fechaInicioSem;

	@Schema(description = "Fecha que representa el final de la semana")
	@Column(name = "FECHA_FIN_SEM")
	private String fechaFinSem;

	@Schema(description = "Número de actividades asociadas")
	@Column(name = "NUM_ACTIVIDADES")
	private Integer numActividades;

	@Schema(description = "Estado del parte asociado")
	@Column(name = "ESTADO_PARTE")
	private Integer estadoParte;
	
	@Schema(description = "Identificador Rodal")
	@Column(name = "ID RODAL")
	private String idRodal;
	
	@Schema(description = "Nombre del fichero")
	@Column(name = "NOMBRE_FICHERO")
	private String fichero;

	@Schema(description = "Es parte actualziado o no")
	@Column(name= "PARTE ACTUALIZADO")
	private Integer parteActualizado;
}
