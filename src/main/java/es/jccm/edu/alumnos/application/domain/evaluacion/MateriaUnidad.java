package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class MateriaUnidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long idMateria;
	
	private String nombreMateria;

	@OneToMany(mappedBy = "idCalifica")
	private List<ListCalificaciones> sistemaCalificacion;
	
	private String abreviatura;
	
	private Long idEtapa;

	private Long idMateriaOmg;
	
	private Long idOfertaMatrig;
	
	private Long lomloe;

	private String cursoMateria;
}
