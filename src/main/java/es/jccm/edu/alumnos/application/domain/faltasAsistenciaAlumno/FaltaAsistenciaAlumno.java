package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FaltaAsistenciaAlumno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idMatricula;

    private Long idTramo;

    private String tipoFalta;

    private String modo;

    private String fecha;

    private Long idMateria;

    private String nombreAlumno;

}
