package es.jccm.edu.proyectosfct.application.ports.in.datosgestorames;

import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.projection.ListadoDatosGestoraMesProjection;

import java.util.List;

public interface IDatosGestoraMesService {
    List<ListadoDatosGestoraMesProjection> getListadoDatosGestoraMes(Long idTutorfctdual, 
    																 Long idCentro, 
    																 Integer cAnno, 
    																 Long idOfertamatrig, 
    																 Long idUnidad, 
    																 Long idPerfil, 
    																 Long idCentroCombo, 
    																 Long idProvincia, 
    																 Long xUsuarioDelphos, 
    																 String cEstado,
    																 Integer mes);

	void actualizarDatosGestoraMesDeAlumno(String nif, Long idCentro, Integer anno, Integer mes, Long xUsuarioDelphos);

	List<DatosGestoraMes> saveDatosMesSegSocial(Long idCentro, Integer anno, Integer nuMes, Long xUsuarioDelphos);

    List<DatosGestoraMes> getDatosGestoraMesByAlumno(String nif, Integer nuMes, Integer anno, Long id, String tipo);
}
