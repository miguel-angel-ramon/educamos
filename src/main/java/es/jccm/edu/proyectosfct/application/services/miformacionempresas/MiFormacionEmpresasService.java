package es.jccm.edu.proyectosfct.application.services.miformacionempresas;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.DatosFormacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.AluDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionEmpresaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionEmpresaPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.PeriodoDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.AlumnadoLOFPRepository;
import es.jccm.edu.proyectosfct.application.ports.in.miformacionempresas.IMiFormacionEmpresasService;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MiFormacionEmpresasService implements IMiFormacionEmpresasService {

	@Autowired
	private AlumnadoLOFPRepository alumnadoLOFPRepository;

    @Autowired
    private AlumnadoRepository alumnadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
	public List<DatosFormacionPlanDto> getDatosFormacionPlan(Long idMatricula) {
		List<Object[]> rawData = alumnadoLOFPRepository.getDatosFormacionPlan(idMatricula);
        Map<String, DatosFormacionPlanDto> autorizacionMap = new HashMap<>();

        for (Object[] row : rawData) {
        	String anno = (String) row[0];
            String curso = (String) row[1];
            String tutor = (String) row[2];
            String validado = (String) row[3];
            String fvalidacion = (String) row[4];
            String empresaNombre = (String) row[5];
            String empresaTutor = (String) row[6];
            String mail = (String) row[7];
            String tlf = (String) row[8];
            String periodo = (String) row[9];
            String alumno = (String) row[10];
            String idConvProyAlu = (String) row[11];

            DatosFormacionPlanDto autorizacion = autorizacionMap
                    .computeIfAbsent(anno, k -> new DatosFormacionPlanDto(anno, curso, tutor, validado, fvalidacion, new ArrayList<>(), alumno));

            DatosFormacionEmpresaPlanDto empresa = autorizacion.getEmpresas().stream()
                    .filter(e -> e.getNombre().equals(empresaNombre))
                    .findFirst()
                    .orElseGet(() -> {
                    	DatosFormacionEmpresaPlanDto newEmpresa = new DatosFormacionEmpresaPlanDto(empresaNombre, empresaTutor, mail, tlf, idConvProyAlu, new ArrayList<>());
                        autorizacion.getEmpresas().add(newEmpresa);
                        return newEmpresa;
                    });

            empresa.getPeriodos().add(new PeriodoDto(periodo));
        }

        return new ArrayList<>(autorizacionMap.values());
    }

    @Override
    public List<DatosFormacionDto> getDatosFormacionProyecto(Long idMatricula) {
        List<Object[]> datosProyecto = alumnadoRepository.getDatosFormacionProyecto(idMatricula);
        return gestionarDatosNoPlan(datosProyecto,false);
    }

    @Override
    public List<DatosFormacionDto> getDatosFormacionPrograma(Long idMatricula) {
        List<Object[]> datosPrograma = alumnadoRepository.getDatosFormacionPrograma(idMatricula);

        return gestionarDatosNoPlan(datosPrograma, true);
    }

    private List<DatosFormacionDto> gestionarDatosNoPlan(List<Object[]> rawData, Boolean esPrograma){

        Map<String, DatosFormacionDto> autorizacionMap = new HashMap<>();

        for (Object[] row : rawData) {
            String anno = (String) row[0];
            String tutor = (String) row[1];
            String curso = (String) row[2];
            String unidad = (String) row[3];
            String descripcion = (String) row[4];
            String idConvPro = (String) row[5];
            String idEvaRodal = (String) row[6];
            String txEvaRodal = (String) row[7];
            String fFirma = (String) row[8];
            String cotiza = (String) row[9];
            String empresaNombre = (String) row[10];
            String empresaTutor = (String) row[11];
            String empresaEmail = (String) row[12];
            String empresaTlf = (String) row[13];
//            String periodos = (String) row[14];

            //String partes = (String) row[14];
            //String puedeBorrar = (String) row[15];

            DatosFormacionDto datosCabecera = autorizacionMap
                    .computeIfAbsent(anno, k -> new DatosFormacionDto(anno, tutor, curso, unidad, descripcion, new ArrayList<>()));

            DatosFormacionEmpresaDto empresa = datosCabecera.getEmpresas().stream()
                    .filter(e -> e.getNombre().equals(empresaNombre))
                    .findFirst()
                    .orElseGet(() -> {
                        DatosFormacionEmpresaDto newEmpresa = new DatosFormacionEmpresaDto(empresaNombre, empresaTutor, empresaEmail, empresaTlf, idConvPro, idEvaRodal, txEvaRodal, fFirma, cotiza, new ArrayList<>(), new ArrayList<>());
                        datosCabecera.getEmpresas().add(newEmpresa);
                        return newEmpresa;
                    });
            if(esPrograma) {
                String periodos = (String) row[14];
                empresa.getPeriodo().add(new PeriodoDto(periodos));
                empresa.getIdConvPro().add(new AluDTO(idConvPro));
            }
        }

        return new ArrayList<>(autorizacionMap.values());
    }

    @Override
    public List<ElementoSelectDto> getTipoPracticasAlumno(Integer cAnno, Long idEmpleadoComunica) {
        List<ElementoSelectDto> tiposAlumno = new ArrayList<>();

        Long matriculaPlan = alumnadoLOFPRepository.findAlumnoByAnnoAndIdDelphos(cAnno, idEmpleadoComunica)
                .orElse(-1L); //Este -1L representa que no se ha encontrado la matricula del alumno en ningún plan formativo
        if(matriculaPlan != -1L){
            ElementoSelectDto alumnoPlan = new ElementoSelectDto();
            alumnoPlan.setId(matriculaPlan);
            alumnoPlan.setDescripcion("Plan Individual LOFP");
            tiposAlumno.add(alumnoPlan);
        }

        Long matriculaPrograma = alumnadoRepository.findAlumnoProgramaByAnnoAndIdDelphos(cAnno, idEmpleadoComunica)
                .orElse(-1L); //Este -1L representa que no se ha encontrado la matricula del alumno en ningún programa
        if(matriculaPrograma != -1L){
            ElementoSelectDto alumnoPrograma = new ElementoSelectDto();
            alumnoPrograma.setId(matriculaPrograma);
            alumnoPrograma.setDescripcion("Programa FCT");
            tiposAlumno.add(alumnoPrograma);
        }

        Long matriculaProyecto = alumnadoRepository.findAlumnoProyectoByAnnoAndIdDelphos(cAnno, idEmpleadoComunica)
                .orElse(-1L); //Este -1L representa que no se ha encontrado la matricula del alumno en ningún proyecto
        if(matriculaProyecto != -1L){
            ElementoSelectDto alumnoProyecto = new ElementoSelectDto();
            alumnoProyecto.setId(matriculaProyecto);
            alumnoProyecto.setDescripcion("Proyecto Dual");
            tiposAlumno.add(alumnoProyecto);
        }

        return tiposAlumno;

    }


}
