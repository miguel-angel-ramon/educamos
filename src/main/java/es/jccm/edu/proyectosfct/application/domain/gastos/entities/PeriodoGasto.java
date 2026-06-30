package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_PERIODOS_GASTOS")
public class PeriodoGasto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PERIODO_GASTO")
	private Long id;
	
	@Column(name="C_ANNO")
	private Integer annoPeriodo;

	@Column(name="FH_INICIO")
	private Date fechaInicio;
	
	@Column(name="FH_FIN")
	private Date fechaFin;

}