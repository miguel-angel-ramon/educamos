package es.jccm.edu.horarios.application.domain.dependencias;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class DependenciaLibreList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idTramo;

    @Id
    private Long idDependencia;

    private String dependencia;

    private String tipo;

    private Long dimension;

    private Boolean capacitada;

    private String inicioTramo;

    private String finTramo;

}
