package es.jccm.edu.proyectosfct.application.ports.in.altassegsocial;

import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoHistoricoAltasDto;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.entities.ListadoAltasSegSocial;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterTraineeStudent;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.NuevoPeriodoDto;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface IAltasSegSocialService {

    List<ListadoAltasSegSocial> getListadoAltasSegSocial(Long idTutorfctdual,
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
                                                         Long idUsuario) throws ParseException;

    

	List<ElementoSelect> getEstadosSS(Long idPerfil);

    void modifyDataAltasSegSocial(List<ListadoAltasSegSocial> listadoAltasSegSocialIn, Long xUsuarioDelphos);

    void resetEstadoValidar(Long id, Long idPerfil, String tipoEmpresa);
    
    int envioAltasSSEmpresa(List<RegisterTraineeStudent> registerTraineeStudents,
    		                List<RegisterTraineeStudent> updateTraineeStudents,
    		                List<RegisterTraineeStudent> cancelledTraineeStudents,
    		                Long xUsuarioDelphos);

    List<ListadoHistoricoAltasProjection> getListadoHistoricoAltas(Long idAltass, String tipoEmpresa);

    void modifyHistoricoAltas(ListadoHistoricoAltasDto listadoHistoricoAltasDto, Long xUsuarioDelphos);

    AltasSegSociProg getAltaSegSociProg(Long id);

    AltasSegSociProy getAltaSegSociProy(Long id);

    void deleteAlumnoAltas(Long idAltasSS, String tipo);

    void revertEstadoAnulado(Long idAlu, String nuPeticion);
    
    int getConsistenciaGestora(String tipo, Long id, Date fechaInicio, Date fechaFin);   
    
    List<AltasSegSociProg> getAltaSegSociProgByMatricula(Long idMatricula);
   
    List<AltasSegSociProy> getAltaSegSociProyByMatricula(Long idMatricula);
    
    void saveProg(AltasSegSociProg alta);
    
    void saveProy(AltasSegSociProy alta);

    void nuevoPeriodoAltas(NuevoPeriodoDto nuevoPeriodo, Long xUsuarioDelphos);

    void excluirAlumno(Long idConvProxAlu, String tipoEmpresa);

}
