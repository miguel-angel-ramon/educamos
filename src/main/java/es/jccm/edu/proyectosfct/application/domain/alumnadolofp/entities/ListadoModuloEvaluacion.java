package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data

public class ListadoModuloEvaluacion implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Column(name = "ID MODULO")
	private Long moduloId;

	@Column(name = "NOMBRE MODULO")
	private String moduloNombre;

	@Column(name = "CODIGO MODULO")
	private Long moduloCodigo;

	@Column(name = "ORDEN MODULO")
	private Integer moduloOrden;

	@Column(name = "ID MATRICULA MODULO")
	private Long moduloMatriculaId;

	@Column(name = "ID RESULTADO APRENDIZAJE")
	private Long resultadoId;

	@Column(name = "ABREVIATURA RESULTADO APRENDIZAJE")
	private String resultadoAbv;

	@Column(name = "NOMBRE RESULTADO APRENDIZAJE")
	private String resultadoNombre;

	@Column(name = "ORDEN RESULTADO APRENDIZAJE")
	private Integer resultadoOrden;

	@Column(name = "ID EVALUACIÓN RESULTADO APRENDIZAJE")
	private Long evaluacionId;

	@Column(name = "ID CALIFICACIÓN RESULTADO APRENDIZAJE")
	private Integer calificacionId;

	@Column(name = "MOTIVACIÓN/OBSERVACIÓN RESULTADO APRENDIZAJE")
	private String motivacion;
}
