package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Curso académico", description = "Descripcion para el modelo del curso académico")
public class CursoAcademicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date inicio;

	private Date fin;
	
}
