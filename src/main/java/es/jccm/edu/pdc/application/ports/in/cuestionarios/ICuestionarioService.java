package es.jccm.edu.pdc.application.ports.in.cuestionarios;


import es.jccm.edu.pdc.application.domain.cuestionarios.entities.*;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.CursoAcademicoProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.SugerenciasMejorasProjection;



import java.util.List;

import org.springframework.data.repository.query.Param;


public interface ICuestionarioService {
	
	Cuestionario getCuestionarioDocenteByCodigo(Long xUsuarioComunica, Long idCentro, String codCuestionario);
	Cuestionario getCuestionarioCentro(Long idCentro, Long anio);

	List<Seccion> getSeccionesByIdCuestionario(Long idCuestionario);
	List<Pregunta> getPreguntasByIdSeccion(Long idSeccion);
	List<Respuesta> getRespuestasByIdPregunta(Long idPregunta);
	List<RespuestaSeleccionada> getRespuestaSeleccionada(Long idCuePubUsu, Long idPregunta);
	void setRespuesta(Long idCuePubUsu, Long idPregunta, Long idRespuesta, String textoRespuesta);
    void finalizarCuestionario(Long idCuePubUsu, Long idCuePub);

	List<ValoresAmbitoCinco> getAreasAmbitoCinco(Long codCentro, String x_cuepub);
	
	List<HistoricoCuestionario> getHistoricoCuestionarioDocenteIndividual(Long xUsuario, Long idCentro);
	List<HistoricoCuestionario> getHistoricoCuestionarioDocente(Long idCentro);
	List<HistoricoCuestionario> getHistoricoCuestionarioCentro(Long idCentro);
	
	List<ValoresAmbitoCinco> getSumAreasAmbitoCinco(Long codCentro, String x_cuepub);
	List<MediaPorArea> getMediaPorAreaUsuario(String x_cuepubusu);
	
	public List<SugerenciasMejorasProjection> getSugerenciasMejorasCuestionarioDocente(Long xCentro, Long xUsuario, Long xCuepub );
	public List<SugerenciasMejorasProjection> getSugerenciasMejorasCuestionarioCentro(Long xCentro, Long xCuepub );
	
	List<MediaPorArea> getMediaPorCastillaLaMancha( String x_cuepub);
	
	MediaPorArea getMediaTotalUsuario( String x_cuepubusu);
	
	Cuestionario getCuestionarioDocenteByXCUEPUB(Long xUsuarioComunica, String xcentro,  String xcues);
	Cuestionario getCuestionarioCentroByXCUEPUB(String xcentro,  String xcues);
	
	Double getPorcentajeParticipantesDocentes(String x_centro, String x_cuepub);
	
	List<CargoPDC> getCargos(Long x_empleado, Long xCentro);
	CursoAcademicoProjection getCursoAcademino(Long anio);
	List<String> getPerfiles(Long x_empleado);
	
}
