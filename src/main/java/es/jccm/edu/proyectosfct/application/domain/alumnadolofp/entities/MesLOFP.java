package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import lombok.Data;

@Data

public class MesLOFP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "MES_NUMERO")
	private String mesNumero;

	@Column(name = "MES_NOMBRE")
	private String mesNombre;

	@Column(name = "MES_VISIBLE")
	private Integer mesVisible;

	private List<SemanaLOFP> semanas;
}
