package es.jccm.edu.proyectosfct.application.ports.in.modalidades;

import java.util.List;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;


public interface IModalidadesService {
	
	List<Modalidad> getAllModalidadesFamiliaCentro(Long idCentro, int cAnno, Long idFamilia, Long idTipo);

}
