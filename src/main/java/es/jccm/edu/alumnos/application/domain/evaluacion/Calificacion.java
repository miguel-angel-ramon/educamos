package es.jccm.edu.alumnos.application.domain.evaluacion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long idMatMatricula;

	Long idGrupoAct;
	
	Long idMateria;

	Long idMateriaOmg;

	Long idMateriaOmgLlave;

	Boolean isMateriaLlave = false;

	Long idConvCentroOmc;

	Long idConvocatoria;

	Long idEstado;

	String nombreEstado;

	String nota;
	
    String descripcionEstado;
    
    Long idNotaPropuesta;
    
    String notaPropuesta;
    
    String apruebaMateriaNotaPropuesta;

	String aprueba;

	Long acnee;

	String materiaAdap;
    
    Long idUnidad;

	String idOfertaMatrig;

	String notaConvocatoriaOrdinaria;

	Boolean materiaAprobada;

}
