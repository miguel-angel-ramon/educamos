package es.jccm.edu.proyectosfct.application.ports.in.programasPFE;

import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CombosProgramasPFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CursosProgramasPFEProjection;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.ListadoPFEDto;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

public interface IProgramasPFEService {


    List<CursosProgramasPFEProjection> getCursosProgramasPFE(Long idCentroCombo, Long idFamilia, Integer idProvincia, Long idUsuario, Long idPerfil, Long idCentro);

    List<CombosProgramasPFEProjection> getCombosProgramasPFE( String cbName,Long idCentro,Integer cAnno,Long idPerfil, Long idUsuario,Long idCentroCombo,Integer idProvincia, Long xUsuario, Long idCodigo);

	List<ElementoSelectProjection> getListadoCreadoresProgramasPFE(Long idCentroCombo, Long idFamilia, Integer idProvincia,
			Long idUsuario, Long idPerfil, Long idCentro, Long idCurso, Long idModalidad, Long xUsuario);

	List<ListadoPFEDto> getListadoPFE(Long idCentro, Integer cAnno, Long idCurso, Long idPerfil, Long idCentroCombo,
			Long idProvincia, Long xUsuarioDelphos, Long idUsuCrea, Long idModalidad, Long idFamilia, Long idCodigo,Integer lgVigente,Integer esDirector,Integer idEstado,Integer reqAutorizacion );

	void deletePrograma(Long id)  throws RodalExceptionService, InsertarDocFault, IOException;

    ProgramasPFE getPfeById(Long idProgPerFor);

	void postVistoBuenoPFE(List<Long> lista, Long xUsuarioDelphos,String cPerfil);
	
	void uploadAdjuntoPFE(Long xCentro, Long id, MultipartFile file) throws RodalExceptionService, InsertarDocFault, IOException;

	HistorialProgramaPFE getAdjuntoAnexoHistorial(Long idHistorial);
}
