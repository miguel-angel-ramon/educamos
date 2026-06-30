package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import lombok.Data;

@Data

public class PeriodoLOFP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PERIODO_NUMERO")
	private Integer periodoNumero;

	@Column(name = "FECHA_INICIO_PERIODO")
	private String fechaInicioPeriodo;
	
	@Column(name = "ES_PERIODO_ACTUAL")
	private Integer esPeriodoActual;

	@Column(name = "FECHA_FIN_PERIODO")
	private String fechaFinPeriodo;
	
	@Column(name = "PERIODO_VISIBLE")
	private Integer periodoVisible;

	private List<MesLOFP> meses;
}
