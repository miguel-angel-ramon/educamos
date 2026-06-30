package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
@Data
@Schema(name = "Departamento", description = "Departamento de un centro")
public class EvaDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idDepartamento;

    private String nombreDepartamento;
}
