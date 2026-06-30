package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ListaAlumnosGrupoActividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idMatricula;

    private String alumno;

}
