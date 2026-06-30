package es.jccm.edu.pdc.application.ports.in.cuestionarios;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.LineaActuacion;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoEspecifico;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.AmbitoCompleto;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.DetalleAmbito;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaActuacionPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.ObjetivoEspecificoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionImpactoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionSeguimientoPDC;

import java.util.Date;
import java.util.List;


public interface IPlanActuacionService {

	List<Informe> getAmbitosPlanActuacion(Long idSector, Long idAnno, Long idCentro);

	List<AmbitoCompleto> getAmbitosCompletosCentro(Long idSector, Long idAnno, Long idCentro, Long x_cuepub);
	
	List<Informe> getValoresCentro(Long idSector, Long idAnno);
	
	Double getValorAmbitoCinco(Long codCentro, Long Anno);
	
	Double getValorAmbitoCincoGlobal(Long Anno);

	DetalleAmbito getDetalleAmbitoCentro(Long idCompetencia, Long idCentro, Long anio);

	List<ObjetivoEspecifico> getObjetivosEspecificos(Long codCentro, Long idObjetivo, Long idAnno);

	void setObjetivoEspecifico(Long codCentro, Long anno, Long idObjetivo, String descripcionObjetivo);

	void setLineasDeActuacion(Long idObjEsp, String titulo, String descripcion, Date fechaInicio, Date fechaFin, String responsable, String logro, String instrumentos, String estado, Long idUsuComunica);

	void deleteObjetivoEspecifico(ObjetivoEspecifico objetivoEspecifico);

	void deleteLineaActuacion(Long idLinAct);

	void editLineasActuacion(Long idObjEsp,List<LineaActuacion> listaLineas, Long idUsuComunica);
	
	void editObjetivosEspecificos(Long idObjEsp, String descripcion);
	
	Boolean visualizaPlan(Long codCentro);
	
	List<ObjetivoEspecificoPDC> getObjetivosEspecificosPorAmbito(String anio, String x_competencia, String x_centro);

	List<LineaActuacionPDC> getHistoricoLineasActuacion(Integer idActuacion);
	
	void insertarPropuestasYMejorasPorAmbito(Integer x_centro, Integer x_cuepub, Integer x_competencia, Integer anno, String observacion, String propuesta);
	
	void eliminarPropuestasYMejorasPorAmbito(Integer x_competencia,Integer x_centro, Integer x_cuepub, Integer anno);
	
	void insertarPropuestasYMejorasGlobal(Integer x_centro, Integer x_cuepub, Integer anno, String mejoraGestion, String mejoraApren, String otrastareas);
	
	void eliminarPropuestasYMejorasGlobal( Integer x_centro, Integer x_cuepub, Integer anno );
	
	InformacionImpactoPDC obtenerPropuestasYMejorasGlobal(Integer x_centro, Integer x_cuepub, Integer anno );
	InformacionSeguimientoPDC obtenerPropuestasYMejorasPorAmbito(Integer x_competencia, Integer x_centro, Integer x_cuepub, Integer anno );
	
	List<Informe> getAmbitosConObjetivosEspecificos(String anno,  String x_centro);
	List<Integer> getHistoricoAniosConObjEspYLineasActuacion(String x_centro);
	
}
