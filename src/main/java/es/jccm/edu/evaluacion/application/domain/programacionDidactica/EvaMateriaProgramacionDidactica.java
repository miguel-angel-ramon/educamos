package es.jccm.edu.evaluacion.application.domain.programacionDidactica;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.HistorialResponsableProgramacionDidacticaDTO;
import lombok.Data;

@Entity
@Data
public class EvaMateriaProgramacionDidactica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idMateriaOmg;
	
	private String materia;

	private Long idOfertaMatrig;

	private Long idDepartamento;

	private String nombreDepartamento;
	
	private Long idCurso;
	
	private String curso;

	private String nivelCurricular;
	
	private Long idNivelCurricular;
	
	private String estado;

	private Long idProgramacionDidactica;
	
	private Integer countUnidadesProgramacion;
	
	private String nombresUnidadesProgramacion;
	
	private Long idEmpleado;

    private String nombreEmpleado;
    
    private String abreviatura;

	private String abreviaturaAcnee;

	private Long idEmpleadoResponsableActual;

	private String idRodal;

	private String nombreFichero;

	@ElementCollection(targetClass=HistorialResponsableProgramacionDidacticaDTO.class)
	private List<HistorialResponsableProgramacionDidacticaDTO> listaEditores;
	
}
