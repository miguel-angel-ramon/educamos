package es.jccm.edu.proyectosfct.application.ports.in.datosalumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosSeguridadSocialAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.ListadoAlumnosPlanDto;

import java.util.List;
import java.util.Optional;

public interface IDatosAlumnoPlanService {
    Optional<DatosAlumnoPlanDto> getDatosAlumnoPlanByMatricula(Long xMatricula);

    List<ListadoAlumnosPlanDto> getListadoAlumnosPlan(Long idProyecto);

    void updateDatosAlumnoPlan(DatosAlumnoPlanDto datosAlumnoPlanDto);

    void setPrlMasivo(List<Long> matriculasAlu);

    List<DatosSeguridadSocialAlumnoPlanDto> getDatosSeguridadSocialAlumnoByMatricula(Long matricula);


    void updateDatosSeguridadSocialAlumno(List<DatosSeguridadSocialAlumnoPlanDto> datosSSAlumnoDtoList);
}
