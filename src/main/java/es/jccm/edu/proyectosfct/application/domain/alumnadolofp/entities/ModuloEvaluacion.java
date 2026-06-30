package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import lombok.Data;

@Data

public class ModuloEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "moduloId")
	private Long moduloId;

	@Column(name = "moduloMatriculaId")
	private Long moduloMatriculaId;
	
	@Column(name = "moduloNombre")
	private String moduloNombre;
	
	@Column(name = "moduloOrden")
	private Integer moduloOrden;

	@Column(name = "moduloCodigo")
	private Long moduloCodigo;

	@Column(name = "resultadosAprendizaje")
	private List<ResultadoAprendizajeEvaluacion> resultadosAprendizaje;
}
