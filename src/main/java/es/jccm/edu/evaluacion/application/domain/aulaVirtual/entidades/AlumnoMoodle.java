package es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.CriterioActividadAulaVirtualDTO;
import lombok.Data;

@Data
@Entity
public class AlumnoMoodle implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
	private Long userid;
	private Long courseid;
	private String userfullname;
	private Long maxdepth;

	@OneToMany(fetch=FetchType.LAZY)
	private List<CriterioActividadAulaVirtualDTO> gradeitems;

}
