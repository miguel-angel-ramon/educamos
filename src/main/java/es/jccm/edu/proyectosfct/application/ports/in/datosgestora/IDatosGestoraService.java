package es.jccm.edu.proyectosfct.application.ports.in.datosgestora;

import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoHistoricoAltasDto;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.entities.DatosGestora;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.projection.ListadoDatosGestoraProjection;
import java.util.List;

public interface IDatosGestoraService {
    List<DatosGestora> saveDatosSegSocial(Long xCentro, Integer anno, Long xUsuarioDelphos);

    List<ListadoDatosGestoraProjection> getListadoDatosGestora(Long idTutorfctdual, 
    														   Long idCentro, 
    														   Integer cAnno, 
    														   Long idOfertamatrig, 
    														   Long idUnidad, 
    														   Long idPerfil, 
    														   Long idCentroCombo, 
    														   Long idProvincia, 
    														   Long xUsuarioDelphos, 
    														   String cEstado,
    														   Long idTipo);

	Integer saveDatosSegSocialBack(Integer anno, Long xUsuarioDelphos);

	void actualizarDatosGestoraDeAlumno(String nif, Long idCentro, Long xUsuarioDelphos);

	List<DatosGestora> getDatosGestoraByAlumno(String nif, Long id, String tipo, Long idInterno);

	byte[] getDatosGestoraById(Integer idGestora, String estado, String tipoDocumento, Long xUsuarioDelphos);
	
	Integer programadaDatosGestora();

	Integer programadaDatosGestoraCotizacionesMes();

	List<ListadoHistoricoAltasDto> getListadoHistoricoAltas(List<ListadoHistoricoAltasProjection> listadoHistoricoAltas, String nif, Long id, String tipo, Long idInterno) throws Exception;

	boolean trabajoEnProgreso(String nombreTarea);

	void comenzarTrabajo(String nombreTarea);

	void finalizarTrabajo(String nombreTarea);
}
