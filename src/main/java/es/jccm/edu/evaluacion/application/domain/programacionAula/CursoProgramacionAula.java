package es.jccm.edu.evaluacion.application.domain.programacionAula;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CursoProgramacionAula implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    private Long idOfermatrig;
   
	private String descripcionCorta;

}
