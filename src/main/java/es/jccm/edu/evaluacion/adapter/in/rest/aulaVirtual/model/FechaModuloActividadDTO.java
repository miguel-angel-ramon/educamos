package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FechaModuloActividadDTO", description = "DTO Fecha Modulo Actividad")
public class FechaModuloActividadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private String label;
	private String name;
	private Timestamp timestamp;
	private String dataid;
}