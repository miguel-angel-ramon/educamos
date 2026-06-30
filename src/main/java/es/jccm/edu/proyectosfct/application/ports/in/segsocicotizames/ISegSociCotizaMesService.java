package es.jccm.edu.proyectosfct.application.ports.in.segsocicotizames;

import es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model.ListadoHistoricoMesDto;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.entities.ListadoSegSocialCotizaMes;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoHistoricoCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoSegSocialCotizaMesProjection;
import es.jccm.edu.shared.adapter.in.rest.segsocial.model.RegisterDaysContributedDto;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterDaysContributed;

import org.apache.tomcat.util.json.ParseException;

import java.util.List;

public interface ISegSociCotizaMesService {

//    List<ListadoSegSocialCotizaMes> getListadoCotizaMes(Long idTutorfctdual,
//                                                        Long idCentro,
//                                                        Integer cAnno,
//                                                        Integer tipoEmpresa,
//                                                        Long idEmpresa,
//                                                        Long idOfertamatrig,
//                                                        Long idUnidad,
//                                                        Long idPerfil,
//                                                        Long idCentroCombo,
//                                                        Long idProvincia,
//                                                        Integer idEstado,
//                                                        Integer nuMes,
//                                                        Long idUsuario) throws ParseException;

    List<ListadoSegSocialCotizaMes> getListadoCotizaMes(Long idTutorfctdual,
                                                                  Long idCentro,
                                                                  Integer cAnno,
                                                                  Integer tipoEmpresa,
                                                                  Long idEmpresa,
                                                                  Long idOfertamatrig,
                                                                  Long idUnidad,
                                                                  Long idPerfil,
                                                                  Long idCentroCombo,
                                                                  Long idProvincia,
                                                                  Integer idEstado,
                                                                  Integer nuMes,
                                                                  Long idUsuario,
                                                                  Integer idTipo) throws ParseException;

    void createUpdateListadoCotizaMes(List<ListadoSegSocialCotizaMes> listadoSegSocialCotizaMesIn, Long xUsuarioDelphos);

    List<ElementoSelect> getEstadosSS(Long idPerfil);

	void resetEstadoValidarMes(Long id, Long idPerfil, String tipoEmpresa);

	Integer envioCotizacionesMensuales(List<RegisterDaysContributed> registerDaysContributed, Long xUsuarioDelphos);

    List<ListadoHistoricoCotizaMesProjection> getListadoHistoricoMes(Long idCotizaMes, String tipo);

    void modifyHistoricoMes(ListadoHistoricoMesDto listadoHistoricoMesDtoIn, Long xUsuarioDelphos);

    void revertUltimoEstado(Long idCotizaMes, String nuPeticion);

    int getConsistenciaMesGestora(Long id, String tipo, Integer nuMes, Integer nuDiasReal, Integer nuDiasNacu, Integer nuDiasInte, Integer nuDiasInteEra);

    Integer getMaxDiaLiquidacionMensual();
}
