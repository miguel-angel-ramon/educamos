package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data
public class SemanaLOFP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SEMANA_NUMERO")
	private String semanaNumero;

	@Column(name = "FECHA_INICIO_SEM")
	private String fechaInicioSem;

	@Column(name = "FECHA_FIN_SEM")
	private String fechaFinSem;

	@Column(name = "NUM_ACTIVIDADES")
	private Integer numActividades;
	
	@Column(name = "SEMANA_VISIBLE")
	private Integer semanaVisible;

	@Column(name = "ESTADO_PARTE")
	private Integer estadoParte;
	
	@Column(name = "ES_SEMANA_ACTUAL")
	private Integer esSemanaActual;
	
	@Column(name = "IDENT RODAL")
	private String idRodal;
	
	@Column(name = "NOMBRE FICHERO RODAL")
	private String fichero;

	@Column(name= "PARTE ACTUALIZADO")
	private Integer parteActualizado;
	
	
}