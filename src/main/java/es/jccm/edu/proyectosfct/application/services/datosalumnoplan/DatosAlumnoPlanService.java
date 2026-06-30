package es.jccm.edu.proyectosfct.application.services.datosalumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosSeguridadSocialAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.ListadoAlumnosPlanDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.DatosAlumnoPlanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConvProyAlumnoRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.Alumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.ConvenioSegSocialAlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.entities.DatosAlumnoPlan;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection.DatosAlumnoPlanProjection;
import es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection.ListadoAlumnosPlanProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.ports.in.datosalumnoplan.IDatosAlumnoPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatosAlumnoPlanService implements IDatosAlumnoPlanService {

    @Autowired
    private DatosAlumnoPlanRepository datosAlumnoPlanRepository;

    @Autowired
    private AlumnadoRepository alumnadoRepository;

    @Autowired
    private ConvProyAlumnoRepository convProyAlumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<DatosAlumnoPlanDto> getDatosAlumnoPlanByMatricula(Long xMatricula) {
        Optional<DatosAlumnoPlanProjection> projection = datosAlumnoPlanRepository.findProjectionByXMatricula(xMatricula);
        return projection.map(proj -> modelMapper.map(proj, DatosAlumnoPlanDto.class));
    }
    @Override
    public List<ListadoAlumnosPlanDto> getListadoAlumnosPlan(Long idProyecto) {
        List<ListadoAlumnosPlanProjection> alumnos = datosAlumnoPlanRepository.findListadoAlumnosPlanByProyecto(idProyecto);

        return alumnos.stream().map(alu -> modelMapper.map(alu, ListadoAlumnosPlanDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateDatosAlumnoPlan(DatosAlumnoPlanDto datosAlumnoPlanDto) {

        DatosAlumnoPlan datosAlumnoPlan = modelMapper.map(datosAlumnoPlanDto, DatosAlumnoPlan.class);

        // Actualizar el NUSS si corresponde
        actualizarNuss(datosAlumnoPlan.getXMatricula(), datosAlumnoPlanDto.getNuss());

        datosAlumnoPlanRepository.save(datosAlumnoPlan);

    }

    private void actualizarNuss(Long xMatricula, String nuevoNuss) {

        AlumnoProjection alumnoProjection = alumnadoRepository.findAlumnoByIdMatricula(xMatricula);

        if (alumnoProjection != null) {

            String nussActual = alumnoProjection.getTnuss();

            // Verificar si el NUSS ha cambiado
            if ((nuevoNuss == null && nussActual != null) || (nuevoNuss != null && !nuevoNuss.equals(nussActual))) {

                Optional<Alumnado> alumnadoOptional = alumnadoRepository.findById(alumnoProjection.getId());

                if (alumnadoOptional.isPresent()) {
                    // Actualizar solo el campo NUSS
                    Alumnado alumnado = alumnadoOptional.get();
                    alumnado.setTnuss(nuevoNuss);
                    alumnadoRepository.save(alumnado);
                }
            }
        }
    }


    @Override
    public void setPrlMasivo(List<Long> matriculasAlu) {

        for(Long matriculaAlu : matriculasAlu){
            DatosAlumnoPlanProjection alumno = datosAlumnoPlanRepository.findAlumnoPlanByXMatricula(matriculaAlu);

            DatosAlumnoPlan alumnoGuardar;

            //Si hay datos los carga, sino crea una nueva entidad
            if (alumno != null) {
                alumnoGuardar = modelMapper.map(alumno, DatosAlumnoPlan.class);
            } else{
                alumnoGuardar = new DatosAlumnoPlan();
                alumnoGuardar.setXMatricula(matriculaAlu);
            }

            alumnoGuardar.setLgPrl(1);

            datosAlumnoPlanRepository.save(alumnoGuardar);
        }
    }

    @Override
    public List<DatosSeguridadSocialAlumnoPlanDto> getDatosSeguridadSocialAlumnoByMatricula(Long matricula) {
        // Obtener la lista de proyecciones desde el repositorio
        List<ConvenioSegSocialAlumnoProjection> projections = convProyAlumnoRepository
                .findDatosSeguridadSocialByMatricula(matricula);

        // Mapear cada proyección al DTO
        return projections.stream()
                .map(proj -> modelMapper.map(proj, DatosSeguridadSocialAlumnoPlanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateDatosSeguridadSocialAlumno(List<DatosSeguridadSocialAlumnoPlanDto> datosSSAlumnoDtoList) {
        // Recorrer la lista y procesar los datos
        datosSSAlumnoDtoList.forEach(dto -> {
            // Obtener la entidad asociada por ID
            Optional<ConveniosProyectoAlumno> entidadOpt = convProyAlumnoRepository.findById(dto.getIdConvProyAlu());
            if (entidadOpt.isPresent()) {
                ConveniosProyectoAlumno entidad = entidadOpt.get();

                // Actualizar los valores en la entidad
                entidad.setLgCotiza(dto.getCentroSS());
                entidad.setLgErasmus(dto.getErasmus());

                // Guardar la entidad actualizada
                convProyAlumnoRepository.save(entidad);
            } else {
                throw new IllegalArgumentException("No se encontró la entidad con idConvProyAlu: " + dto.getIdConvProyAlu());
            }
        });
    }
}