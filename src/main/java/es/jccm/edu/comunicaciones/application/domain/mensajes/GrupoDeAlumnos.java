package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class GrupoDeAlumnos implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    private long idUnidad;
    private long xOfertMatrig;
    private String dOfertMatrig;
    private String tNombre;

}
