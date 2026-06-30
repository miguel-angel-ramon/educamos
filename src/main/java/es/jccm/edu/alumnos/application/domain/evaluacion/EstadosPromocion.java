package es.jccm.edu.alumnos.application.domain.evaluacion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class EstadosPromocion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long cResultado;

	private String descripcion;
	
	private String abrev;

	private Long idEstado;

}
