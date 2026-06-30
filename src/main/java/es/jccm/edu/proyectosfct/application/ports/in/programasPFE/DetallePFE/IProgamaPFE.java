package es.jccm.edu.proyectosfct.application.ports.in.programasPFE.DetallePFE;

import java.io.IOException;
import java.util.List;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.DatosVigentePFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface IProgamaPFE {

	List<ElementoSelect> getCursosCiclos(Long idCentro, Integer cAnno);

	List<ElementoSelect> getCursosEspecializacion(Long idCentro, Integer cAnno);

	ProgramasPFE getProgramaPFE(Long id, Long xPerfil, Long xUsuario, Integer esJefe);

	List<DatosVigentePFE> getExistePFE(Long id, Long idCentro, Long idCurso, Integer nuAnnoDesde, Integer nuAnnoHasta,
			Integer lgAlcance, Integer lgModalidad);

	ProgramasPFE savePFE(ProgramasPFE pfeRequest, Long xUsuarioDelphos) throws RodalExceptionService, InsertarDocFault, IOException;

	List<ElementoSelect> getAnnosIni();

	List<ElementoSelect> getAnnosFin();

	byte[] getAnexoV(Long idProgPerFor);

	Integer generarAnexoV(Long idPrograma, Long xUsuarioDelphos, Integer modo, String sigEstado, Long idPerfil, Integer esJefe, Integer esDirector) throws Exception;

	List<AutorizacionFlujoSiguiente> getSiguienteEstadoFlujoPFE(Long idPerfil, Long idPrograma, Integer esJefe, Long cUsuario, Integer esDirector, boolean esMasivo);

	List<HistoricoFlujoAutorizacion> getHistoricoFlujoAutorizacionPFE(Long id);

	HistorialProgramaPFE createSiguienteEstadoFlujoPfeAnexo(AutorizacionesAnexosHistorialDto historialAnexoDto,
			Long idPerfil, Long xUsuarioDelphos);
	
	HistorialProgramaPFE createSiguienteEstadoFlujoPfePrograma(Long idPrograma,
			Long idFlujo, Long xUsuarioDelphos);

	List<AutorizacionFlujoSiguiente> getSiguienteEstadoFlujoPFEDelegacion();
}
