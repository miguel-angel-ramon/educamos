package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.SemanaLOFP;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class ListadoSeguimientoLOFP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "MES_NUMERO")
	private String mesNumero;

	@Column(name = "MES_NOMBRE")
	private String mesNombre;

	@Column(name = "MES_VISIBLE")
	private Integer mesVisible;

	@Column(name = "SEMANA_NUMERO")
	private String semanaNumero;

	@Column(name = "ES_SEMANA_ACTUAL")
	private Integer esSemanaActual;

	@Column(name = "SEMANA_VISIBLE")
	private Integer semanaVisible;

	@Column(name = "FECHA_INICIO_SEM")
	private String fechaInicioSem;

	@Column(name = "FECHA_FIN_SEM")
	private String fechaFinSem;

	@Column(name = "PERIODO_NUMERO")
	private Integer periodoNumero;

	@Column(name = "FECHA_INICIO_PERIODO")
	private String fechaInicioPeriodo;

	@Column(name = "FECHA_FIN_PERIODO")
	private String fechaFinPeriodo;

	@Column(name = "ES_PERIODO_ACTUAL")
	private Integer esPeriodoActual;

	@Column(name = "PERIODO_VISIBLE")
	private Integer periodoVisible;

	@Column(name = "NUM_ACTIVIDADES")
	private Integer numActividades;

	@Column(name = "ESTADO_PARTE")
	private Integer estadoParte;
	
	@Column(name = "IDENT RODAL")
	private String idRodal;
	
	@Column(name = "NOMBRE FICHERO RODAL")
	private String fichero;

	@Column(name= "PARTE ACTUALIZADO")
	private Integer parteActualizado;
	
	
}
