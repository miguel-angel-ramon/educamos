package es.jccm.edu.evaluacion.application.services.valoracionCriterios;

import es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaCalificacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaNotaGlobalAlumnoMateriaRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaValoracionCompetenciaEspecificaAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaValoracionCriterioEvaluacionAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios.*;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.CompentenciasEspecificasConPorcentajeYPeso;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.CriterioEvaluacionConPorcentajeYPeso;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CalificacionCalculoTemporalMateriaProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.*;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaNotaGlobalAlumnoMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaValoracionCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaValoracionCriterioEvaluacionAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.ValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.*;
import es.jccm.edu.evaluacion.application.ports.in.valoracionCriterios.IValoracionCriteriosService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValoracionCriteriosService implements IValoracionCriteriosService {

    private static final Logger LOG = LogManager.getLogger(ValoracionCriteriosService.class);

    @Autowired
    private ValoracionCriteriosRepository valoracionCriteriosRepository;
    
    @Autowired
    private ValCriAluRepository valCriAluRepository;

    @Autowired
    private RegSelDocRepository regSelDocRepository;
    
    @Autowired
    private CompetenciaClaveRepository competenciaClaveRepository;
    
    @Autowired
    private DescriptorOperativoRepository descriptorOperativoRepository;
    
    @Autowired
    private EvaNotaGlobalCalculadaAlumnoMateriaTemporalRepository notaGlobalTemporalRepository;
    
    @Autowired
    private EvaValoracionTemporalCompetenciaEspecificaAlumnoRepository valoracionCompetenciaAlumnoTemporalRepository;
    
    @Autowired
    private EvaValoracionTemporalCriterioEvaluacionAlumnoRepository valoracionCriterioAlumnoTemporalRepository;
    
    @Autowired
    private EvaAlumnoRepository alumnoRepository;
    
    @Autowired
    private EvaValoracionTemporalCompetenciaClaveAlumnoRepository valoracionTemporalCompetenciaClaveAlumnoRepository;
    
    @Autowired
    private EvaValoracionTemporalDescriptorOperativoAlumnoRepository valoracionTemporalDescriptorOperativoRepository;

    @Autowired
    private EvaValoracionCriterioEvaluacionAlumnoRepository criterioAlumnoRepository;

    @Autowired
    private EvaCalificacionRepository calificacionRepository;

    @Autowired
    private EvaValoracionCompetenciaClaveAlumnoRepository valoracionCompetenciaClaveAlumnoRepository;

    @Autowired
    private EvaValoracionDescriptorOperativoAlumnoRepository valoracionDescriptorOperativoAlumnoRepository;

    @Autowired
    private PonderacionRepository ponderacionRepository;

    @Autowired
    private EvaCriterioEvaluacionRepository criterioRepository;

    @Autowired
    private EvaValoracionCompetenciaEspecificaAlumnoRepository competenciaRepository;

    @Autowired
    private EvaNotaGlobalAlumnoMateriaRepository notaGlobalRepository;

    @Autowired
    private EvaCalificacionRepository califRepository;

    @Autowired
    private EvaAulaVirtualRepository aulaVirtualRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<AlumnoEvaluacion> getAlumnosEvaluacion(Long idPonderacion, Long idConvCentroOmc, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idUnidad, Boolean pendiente, Long tutorLong, Long direccionLong) {
        LOG.info("Obteniendo alumnos de evaluación");

        List<AlumnoEvaluacionProjection> alumnosProjection = null;
        if (pendiente) {
            alumnosProjection = valoracionCriteriosRepository.getAlumnosEvaluacionPendiente(idEmpleados, idPonderacion, idNivelCurricular, idUnidad, tutorLong, direccionLong, idConvCentroOmc);
        } else {
            alumnosProjection = valoracionCriteriosRepository.getAlumnosEvaluacionYAcnee(idEmpleados, fechaTomaPosesion, idPonderacion, idNivelCurricular, idUnidad, tutorLong, direccionLong, idConvCentroOmc);
        }

        List<AlumnoEvaluacion> alumnos = alumnosProjection.stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacion.class)).collect(Collectors.toList());

        List<CompetenciaAlumnoProjection> competenciasAlumnoProjection = valoracionCriteriosRepository.getCompetenciasAlumnos(idPonderacion);

   
        for (AlumnoEvaluacion alumno : alumnos) {
            Blob img = valoracionCriteriosRepository.getFotoAlumno(alumno.getNumEscolar());
            setFotoAlumno(img, alumno);

            //AÑADIMOS LA COMPROBACIÓN PARA VER SI EL ALUMNO TIENE UNA MATERIA PENDIENTE QUE SEA LLAVE DE UNA DE LA QUE ESTE MATRICULADA
            Long idMateriaOmgAlumno = alumno.getIdMateriaOmg();
            Long idMatriculaAlumno = alumno.getIdMatricula();

            //Llamamos al endpoint que recorre la matricula del alumno y devuelve cuales de sus materias tiene una materia llave como asignatura pendiente matriculada
            List<Long> materiasConLlaveEnMatricula = valoracionCriteriosRepository.getMateriasLlaveByMatricula(idMatriculaAlumno);

            //Si en la lista de materias con materia llave se encuentra la materia actual, ponemos disabled a 1
            if (materiasConLlaveEnMatricula.contains(idMateriaOmgAlumno))
            {
                alumno.setDisabled(1L);
                alumno.setMateriaLlavePendiente(true);
            }


            if(alumno.getDisabled() != 1) {
            	NotaGlobal notaGlobal = getNotaGlobalAlumno(alumno.getIdMatMatriAlu(), idConvCentroOmc);
                alumno = setNotaGlobalAlumno(alumno, notaGlobal);

                List<CompetenciaAlumno> competenciasAlumno = competenciasAlumnoProjection.stream().map(competencia -> modelMapper.map(competencia, CompetenciaAlumno.class)).collect(Collectors.toList());
                alumno.setCompetencias(competenciasAlumno);

                for (CompetenciaAlumno competencia : alumno.getCompetencias()) {
                    competencia.setIdCompetencia(competencia.getComEsp());
                    NotaCompetencia notaCompetencia = getNotaComAlumno(competencia.getIdCompetencia(), alumno.getIdMatMatriAlu(), idConvCentroOmc);

                    competencia = setNotaCompetencia(competencia, notaCompetencia);

                    List<CriterioAlumno> criterios = valoracionCriteriosRepository.getCriteriosAlumno(idPonderacion, competencia.getIdCompetencia()).stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
                    for (CriterioAlumno criterio : criterios) {
                        criterio.setIdCriterio(criterio.getCriEva());
                        NotaCriterio notaCriterio = getNotaCriAlumno(criterio.getIdCriterio(), alumno.getIdMatMatriAlu(), idConvCentroOmc);

                        criterio = setNotaCriterio(criterio, notaCriterio);
                    }
                    competencia.setCriterios(criterios);
                }
            }
        }

        return alumnos;
    }

    public AlumnoEvaluacion setNotaGlobalAlumno(AlumnoEvaluacion alumno, NotaGlobal notaGlobal) {
        if (notaGlobal != null) {
            alumno.setIdNotAlu(notaGlobal.getIdNotAlu());
            if (notaGlobal.getIdCalifica() != null) {
                alumno.setIdCalifica(notaGlobal.getIdCalifica());
                alumno.setNota(notaGlobal.getNota());
                alumno.setDescCal(notaGlobal.getDescCal());
                alumno.setAprueba(notaGlobal.getAprueba());
            }
        }

        return alumno;
    }

    public CompetenciaAlumno setNotaCompetencia(CompetenciaAlumno competencia, NotaCompetencia notaCompetencia) {
        if (notaCompetencia != null) {
            competencia.setIdComAlu(notaCompetencia.getIdComAlu());
            if (notaCompetencia.getIdCalifica() != null) {
                competencia.setIdCalifica(notaCompetencia.getIdCalifica());
                competencia.setNota(notaCompetencia.getNota());
                competencia.setDescCal(notaCompetencia.getDescCal());
                competencia.setAprueba(notaCompetencia.getAprueba());
            }
        }

        return competencia;
    }

    public CriterioAlumno setNotaCriterio(CriterioAlumno criterio, NotaCriterio notaCriterio) {
        if (notaCriterio != null) {
            criterio.setIdCrialu(notaCriterio.getIdCriAlu());
            if (notaCriterio.getIdCalifica() != null) {
                criterio.setIdCalifica(notaCriterio.getIdCalifica());
                criterio.setNota(notaCriterio.getNota());
                criterio.setDescCal(notaCriterio.getDescCal());
                criterio.setAprueba(notaCriterio.getAprueba());
            }
        }

        return criterio;
    }

    public List<CompetenciaAlumno> getAlumnoCompetenciasEvaluacion(Long idConvCentroOmc, Long idMatmatricula) {
        LOG.info("Obteniendo alumnos de competencias de evaluación");

        Long idPonderacion = valoracionCriteriosRepository.getIdPonderacionByMatMatriculaAndIdConvCentroOmc(idMatmatricula, idConvCentroOmc);
        List<CompetenciaAlumno> competenciasAlumno = new ArrayList<>();

        if (idPonderacion != null) {
            List<CompetenciaAlumnoProjection> competenciasAlumnoProjection = valoracionCriteriosRepository.getCompetenciasAlumnos(idPonderacion);

            competenciasAlumno = competenciasAlumnoProjection.stream().map(competencia -> modelMapper.map(competencia, CompetenciaAlumno.class)).collect(Collectors.toList());

            for (CompetenciaAlumno competencia : competenciasAlumno) {
                competencia.setIdCompetencia(competencia.getComEsp());
                NotaCompetencia notaCompetencia = getNotaComAlumno(competencia.getIdCompetencia(), idMatmatricula, idConvCentroOmc);

                if (notaCompetencia != null) {
                    competencia.setIdComAlu(notaCompetencia.getIdComAlu());
                    if (notaCompetencia.getIdCalifica() != null) {
                        competencia.setIdCalifica(notaCompetencia.getIdCalifica());
                        competencia.setNota(notaCompetencia.getNota());
                        competencia.setDescCal(notaCompetencia.getDescCal());
                        competencia.setAprueba(notaCompetencia.getAprueba());
                    }
                }

                List<CriterioAlumno> criterios = valoracionCriteriosRepository.getCriteriosAlumno(idPonderacion, competencia.getIdCompetencia()).stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
                for (CriterioAlumno criterio : criterios) {
                    criterio.setIdCriterio(criterio.getCriEva());
                    NotaCriterio notaCriterio = getNotaCriAlumno(criterio.getIdCriterio(), idMatmatricula, idConvCentroOmc);
                    if (notaCriterio != null) {
                        criterio.setIdCrialu(notaCriterio.getIdCriAlu());
                        if (notaCriterio.getIdCalifica() != null) {
                            criterio.setIdCalifica(notaCriterio.getIdCalifica());
                            criterio.setNota(notaCriterio.getNota());
                            criterio.setDescCal(notaCriterio.getDescCal());
                            criterio.setAprueba(notaCriterio.getAprueba());
                        }
                    }
                }
                competencia.setCriterios(criterios);
            }
        }

        return competenciasAlumno;
    }

    private AlumnoEvaluacion setFotoAlumno(Blob img, AlumnoEvaluacion alumn) {
        try {
            if (img != null) {
                alumn.setFoto(img.getBytes(1, (int) img.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumn;
    }

    private NotaGlobal getNotaGlobalAlumno(Long idMatMatriAlu, Long idConvCentroOmc) {
        NotaGlobalProjection notaGlobalProjection = valoracionCriteriosRepository.getNotaGlobal(idMatMatriAlu, idConvCentroOmc);
        NotaGlobal notaGlobal = null;
        if (notaGlobalProjection != null) {
            notaGlobal = modelMapper.map(notaGlobalProjection, NotaGlobal.class);
        }

        return notaGlobal;
    }

    private NotaCompetencia getNotaComAlumno(Long idCompetencia, Long idMatMatriAlu, Long idConvCentroOmc) {
        NotaCompetenciaProjection notaCompetenciaProjection = valoracionCriteriosRepository.getNotasCompetencias(idCompetencia, idMatMatriAlu, idConvCentroOmc);
        NotaCompetencia notaCompetencia = null;
        if (notaCompetenciaProjection != null) {
            notaCompetencia = modelMapper.map(notaCompetenciaProjection, NotaCompetencia.class);
        }

        return notaCompetencia;
    }

    private NotaCriterio getNotaCriAlumno(Long idCriEva, Long idMatMatriAlu, Long idConvCentroOmc) {
        NotaCriterioProjection notaCriterioProjection = valoracionCriteriosRepository.getNotasCriterios(idCriEva, idMatMatriAlu, idConvCentroOmc);
        NotaCriterio notaCriterio = null;
        if (notaCriterioProjection != null) {
            notaCriterio = modelMapper.map(notaCriterioProjection, NotaCriterio.class);
        }

        return notaCriterio;
    }

    @Transactional
    public void trasladarResultadoEvaluacion(List<AlumnoEvaluacion> alumnos, Long idCentroOmc) {
        for (AlumnoEvaluacion alumno : alumnos) {
            if (getNotaGlobalAlumno(alumno.getIdMatMatriAlu(), idCentroOmc) != null) {
                if (alumno.getIdCalifica() != null && alumno.getNota() != null) {
                    editNotaGlobalAlumno(alumno.getIdNotAlu(), alumno.getIdCalifica(), alumno.getNota());
                } else {
                    deleteNotaGlobalAlumno(alumno.getIdNotAlu());
                }
            } else {
                if (alumno.getIdCalifica() != null && alumno.getNota() != null) {
                    insertNotaGlobalAlumno(alumno.getIdCalifica(), alumno.getIdMatMatriAlu(), idCentroOmc, alumno.getNota());
                }
            }
        }
    }

    public AlumnoEvaluacion calculoNotaCriterioParaAlumnoConvocatoria(AlumnoEvaluacion alumno, Long idPonderacion, Long idCentroOmc, Long idCriterio, Long idCalifica, Long idSistemaCalificacion) {

        List<SistemaCalificacionCua> calificaciones = aulaVirtualRepository.getSistemaCalifActividadCalculoCriterio(idSistemaCalificacion)
                .stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());

        try {
            EvaValoracionCriterioEvaluacionAlumno criterioAlumno = null;
            Long idCriterioAlumno = criterioAlumnoRepository.findByIdPonderacionAndCriteriosEvaluacionAndIdMatMatriculaAndIdConvCentroOmc(idPonderacion, idCriterio, alumno.getIdMatMatriAlu(), idCentroOmc);

            if(idCriterioAlumno != null){
                criterioAlumno = criterioAlumnoRepository.findById(idCriterioAlumno).orElse(null);
            }

            if(criterioAlumno == null) {
                criterioAlumno = new EvaValoracionCriterioEvaluacionAlumno();

                criterioAlumno.setPonderacion(ponderacionRepository.findById(idPonderacion).orElse(null));
                criterioAlumno.setIdMatMatricula(alumno.getIdMatMatriAlu());
                criterioAlumno.setCriteriosEvaluacion(criterioRepository.findById(idCriterio).orElse(null));
                criterioAlumno.setIdConvCentroOmc(idCentroOmc);
            }

            if(idCalifica != null) {

                EvaCalificacion calificacion = califRepository.findById(idCalifica).orElse(null);

                criterioAlumno.setIdCalifica(idCalifica);

                criterioAlumnoRepository.save(criterioAlumno); //ERROR PK DUPLICADA 

                alumno.getCompetencias().stream().flatMap(competencia -> competencia.getCriterios().stream())
                        .filter(criterio -> idCriterio.equals(criterio.getCriEva()))
                        .findFirst()
                        .ifPresent(criterioEncontrado -> {
                            criterioEncontrado.setIdCalifica(calificacion.getId());
                            criterioEncontrado.setDescCal(calificacion.getAbreviatura());
                            criterioEncontrado.setAprueba(calificacion.getLAprueba());
                        });

                alumno = this.calcularYAlmacenarNotaCompetenciaEspecificaConvocatoria(alumno, criterioAlumno, calificaciones);

                if (calificacion != null && calificacion.getSistema() != 747) {
                    alumno = this.calcularYAlmacenarNotaMateriaConvocatoria(alumno, criterioAlumno, calificaciones);
                }

            } else {
                if(criterioAlumno.getId() != null) {
                    criterioAlumnoRepository.deleteById(criterioAlumno.getId());
                    alumno.getCompetencias().stream().flatMap(competencia -> competencia.getCriterios().stream())
                            .filter(criterio -> idCriterio.equals(criterio.getCriEva()))
                            .findFirst()
                            .ifPresent(criterioEncontrado -> {
                                criterioEncontrado.setIdCalifica(null);
                                criterioEncontrado.setDescCal(null);
                                criterioEncontrado.setAprueba(null);
                            });

                    calcularYAlmacenarNotaCompetenciaEspecificaConvocatoria(alumno, criterioAlumno, calificaciones);
                    calcularYAlmacenarNotaMateriaConvocatoria(alumno, criterioAlumno, calificaciones);
                }
            }

            return alumno;
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

    }

    private AlumnoEvaluacion calcularYAlmacenarNotaCompetenciaEspecificaConvocatoria(AlumnoEvaluacion alumno, EvaValoracionCriterioEvaluacionAlumno criterioAlumno, List<SistemaCalificacionCua> calificaciones) {

        //Busco el criterio de evaluación
        Optional<EvaCriterioEvaluacion> criterioEvaluacion = criterioRepository.findById(criterioAlumno.getCriteriosEvaluacion().getId());

        EvaValoracionCompetenciaEspecificaAlumno competenciaEspecifica = null;
        Long idCompetenciaEspecifica = competenciaRepository
                .findByIdPonderacionAndIdCompetenciaEspecificaAndIdMatMatriculaAndIdConvCentroOmc(
                        criterioAlumno.getPonderacion().getId(),
                        criterioAlumno.getCriteriosEvaluacion().getCompetenciaEspecifica().getId(),
                        criterioAlumno.getIdMatMatricula(),
                        criterioAlumno.getIdConvCentroOmc());

        if(idCompetenciaEspecifica != null){
            competenciaEspecifica = competenciaRepository.findById(idCompetenciaEspecifica).orElse(null);
        }

        //Si no existe la creo y completo los datos de la competencia especifica temporal si no existen
        if (competenciaEspecifica == null) {
            competenciaEspecifica = new EvaValoracionCompetenciaEspecificaAlumno();

            competenciaEspecifica.setPonderacion(criterioAlumno.getPonderacion());
            competenciaEspecifica.setIdMatMatricula(criterioAlumno.getIdMatMatricula());
            competenciaEspecifica.setIdCompetenciaEspecifica(criterioAlumno.getCriteriosEvaluacion().getCompetenciaEspecifica().getId());
            competenciaEspecifica.setIdConvCentroOmc(criterioAlumno.getIdConvCentroOmc());
        }

        //Rescato el listado de criterios temporales evaluados del alumno para la competencia específica y ponderación
        List<CriterioEvaluacionConPorcentajeYPeso> listadoCriteriosEvaluacion = competenciaRepository
                .findAllNotasCriterioByIdCompetenciaEspecificaAndIdPonderacionAndIdMatMatriculaAndIdConvCentroOmc(
                        criterioAlumno.getCriteriosEvaluacion().getCompetenciaEspecifica().getId(),
                        criterioAlumno.getPonderacion().getId(),
                        criterioAlumno.getIdMatMatricula(),
                        criterioAlumno.getIdConvCentroOmc())
                .stream().map(x -> modelMapper.map(x, CriterioEvaluacionConPorcentajeYPeso.class)).collect(Collectors.toList());

        if (calificaciones != null && !listadoCriteriosEvaluacion.isEmpty()) {
            //Por cada criterio que haya rescatado le calculo y aplico los porcentajes a la nota del cálculo del criterio temporal
            List<Integer> notas = calcularNotasPorCriterio(listadoCriteriosEvaluacion);

            //Calculo la nota total de la competencia específica
            float calculo = (float) notas.stream().mapToInt(Integer::intValue).sum() / 100;

            List<SistemaCalificacionCua> calificacion = calificaciones.stream()
                    .filter(c -> Math.abs(c.getNota() - Math.round(calculo)) < 0.0001)
                    .collect(Collectors.toList());

            if (!calificacion.isEmpty()) {
                competenciaEspecifica.setIdCalifica(calificacion.get(0).getIdCalifica());
            }

            //Guardo el resultado de la competencia especifica temporal
            competenciaRepository.save(competenciaEspecifica);

            alumno.getCompetencias().stream()
                    .filter(competencia -> criterioAlumno.getCriteriosEvaluacion().getCompetenciaEspecifica().getId().equals(competencia.getIdCompetencia()))
                    .findFirst()
                    .ifPresent(competenciaEncontrada -> {
                        competenciaEncontrada.setIdCalifica(calificacion.get(0).getIdCalifica());
                        competenciaEncontrada.setDescCal(calificacion.get(0).getDescCal());
                    });

        } else if(competenciaEspecifica != null){
                competenciaRepository.deleteById(competenciaEspecifica.getId());

                alumno.getCompetencias().stream()
                        .filter(competencia -> criterioAlumno.getCriteriosEvaluacion().getCompetenciaEspecifica().getId().equals(competencia.getIdCompetencia()))
                        .findFirst()
                        .ifPresent(competenciaEncontrada -> {
                            competenciaEncontrada.setIdCalifica(null);
                            competenciaEncontrada.setDescCal(null);
                            competenciaEncontrada.setAprueba(null);
                        });
        }
        return alumno;
    }

    private List<Integer> calcularNotasPorCriterio(List<CriterioEvaluacionConPorcentajeYPeso> listadoCriteriosEvaluacion) {
        List<Integer> notas = listadoCriteriosEvaluacion.stream().map(CriterioEvaluacionConPorcentajeYPeso::getNumero).collect(Collectors.toList());

        int sumaPeso = listadoCriteriosEvaluacion.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

        for(int i=0;i< listadoCriteriosEvaluacion.size();i++){
            int n = notas.get(i);
            int peso = listadoCriteriosEvaluacion.get(i).getPeso() != null && listadoCriteriosEvaluacion.get(i).getPeso() != 0 ? listadoCriteriosEvaluacion.get(i).getPeso() : 1;
            DecimalFormat df = new DecimalFormat("#.##");
            float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
            notas.set((i), Math.round((n * porcentaje)));
        }
        return notas;
    }

    private AlumnoEvaluacion calcularYAlmacenarNotaMateriaConvocatoria(AlumnoEvaluacion alumno, EvaValoracionCriterioEvaluacionAlumno criterioAlumno, List<SistemaCalificacionCua> calificaciones) {
        //Intento rescatar si ya existe la materia temporal
        EvaNotaGlobalAlumnoMateria notaGlobal = calcularNotaGlobal(criterioAlumno);

        //Rescato el listado de competencias específicas temporales evaluados por ponderación y matMatricula
        List<CompentenciasEspecificasConPorcentajeYPeso> listadoCompentenciasEspecificas = notaGlobalRepository
                .findAllNotasCompentenciasEspecificasIdPonderacionAndIdMatMatricula(
                        criterioAlumno.getPonderacion().getId(),
                        criterioAlumno.getIdMatMatricula(),
                        criterioAlumno.getIdConvCentroOmc())
                .stream().map(x -> modelMapper.map(x, CompentenciasEspecificasConPorcentajeYPeso.class)).collect(Collectors.toList());

        //Si no se elimina la nota de la materia
        if (calificaciones != null && !listadoCompentenciasEspecificas.isEmpty()) {
            float calculo = calcularNotaTotalDeCompetenciaEspecifica(listadoCompentenciasEspecificas);

            //Compruebo si el sistema de calificación pertenece a primaria o secundaria para aplicar los cálculos pertinentes en función a la tabla TLRELNOTSIST
            if(calificaciones.get(0).getIdSistCal() == 749 || calificaciones.get(0).getIdSistCal() == 743) {
                calcularNotaAlumno(notaGlobal, alumno, calculo, calificaciones);
            } else {
                List<SistemaCalificacionCua> listadoCalificacionesCalculoMateria = califRepository.getSistemaCalificacionNotaMateriaByIdMatricula(alumno.getIdMatricula())
                        .stream().map(x -> modelMapper.map(x, SistemaCalificacionCua.class)).collect(Collectors.toList());

                List<SistemaCalificacionCua> calificacion = listadoCalificacionesCalculoMateria.stream()
                        .filter(c -> Math.abs(c.getNota() - Math.round(calculo)) < 0.0001)
                        .collect(Collectors.toList());
                //Si es un sistema de calificación de infantil o bachillerato busco la calificación de manera normal en el sistema de calificaciones
                if (!calificacion.isEmpty()) {
                    notaGlobal.setIdCalifica(calificacion.get(0).getIdCalifica());

                    alumno.setIdCalifica(calificacion.get(0).getIdCalifica());
                    alumno.setDescCal(calificacion.get(0).getDescCal());
                    alumno.setNota(calculo);
                    alumno.setAprueba(calificacion.get(0).getAprueba());
                }
            }
            notaGlobal.setNota((double) calculo);

            //Guardo el resultado de la competencia especifica temporal
            notaGlobalRepository.save(notaGlobal);
        } else {
            eliminarNotaGlobal(notaGlobal, alumno);
        }
        return alumno;
    }

    private void calcularNotaAlumno(EvaNotaGlobalAlumnoMateria notaGlobal, AlumnoEvaluacion alumno, float calculo, List<SistemaCalificacionCua> calificaciones) {
        //Rescato el sistema de calificación especial
        List<CalificacionCalculoTemporalMateriaProjection> listadoCalificacionesCalculoMateria =  notaGlobalRepository
                .getSistemaCalificacion(calificaciones.get(0).getIdSistCal());

        //Busco entre que valor mínimo y máximo está mi cálculo y rescato el idCalifica que corresponde
        calculo = (float) (1.5 + (calculo - 1) * 2);
        for (CalificacionCalculoTemporalMateriaProjection calificacion : listadoCalificacionesCalculoMateria) {
            if (calculo >= calificacion.getValorMinimo() && calculo <= calificacion.getValorMaximo()) {
                notaGlobal.setIdCalifica(calificacion.getIdCalifica());

                alumno.setIdCalifica(calificacion.getIdCalifica());
                alumno.setDescCal(calificacion.getAbreviatura());
                alumno.setNota(calculo);
                alumno.setAprueba(calificacion.getAprueba());
            }
        }
    }

    private void eliminarNotaGlobal(EvaNotaGlobalAlumnoMateria notaGlobal, AlumnoEvaluacion alumno) {
        if(notaGlobal != null) {
            notaGlobalRepository.deleteById(notaGlobal.getId());

            alumno.setIdCalifica(null);
            alumno.setDescCal(null);
            alumno.setNota(null);
            alumno.setAprueba(null);
        }
    }

    private EvaNotaGlobalAlumnoMateria calcularNotaGlobal(EvaValoracionCriterioEvaluacionAlumno criterioAlumno) {
        EvaNotaGlobalAlumnoMateria notaGlobal = notaGlobalRepository.findByIdMatMatriculaAndIdConvCentroOmc(criterioAlumno.getIdMatMatricula(), criterioAlumno.getIdConvCentroOmc());

        //Si no existe la creo y completo los datos de la competencia especifica temporal si no existen
        if (notaGlobal == null) {
            notaGlobal = new EvaNotaGlobalAlumnoMateria();
            notaGlobal.setIdMatMatricula(criterioAlumno.getIdMatMatricula());
            notaGlobal.setIdConvCentroOmc(criterioAlumno.getIdConvCentroOmc());
        }
        return notaGlobal;
    }

    private float calcularNotaTotalDeCompetenciaEspecifica(List<CompentenciasEspecificasConPorcentajeYPeso> listadoCompentenciasEspecificas) {
        //Por cada criterio que haya rescatado le calculo y aplico los porcentajes a la nota del cálculo del criterio temporal
        List<Integer> notas = listadoCompentenciasEspecificas.stream().map(x -> x.getNumero()).collect(Collectors.toList());
        int sumaPeso = listadoCompentenciasEspecificas.stream().mapToInt(x -> x.getPeso() != null && x.getPeso() != 0 ? x.getPeso() : 1).sum();

        for(int i=0;i< listadoCompentenciasEspecificas.size();i++){
            int n = notas.get(i);
            int peso = listadoCompentenciasEspecificas.get(i).getPeso() != null && listadoCompentenciasEspecificas.get(i).getPeso() != 0 ? listadoCompentenciasEspecificas.get(i).getPeso() : 1;
            DecimalFormat df = new DecimalFormat("#.##");
            float porcentaje = Float.parseFloat(df.format(100F * peso/sumaPeso).replace(",", "."));
            notas.set(i, Math.round(n * porcentaje));
        }

        //Calculo la nota total de la competencia específica
        return notas.stream().mapToInt(Integer::intValue).sum() / 100F;
    }

    public void insertNotaGlobalAlumno(Long idCalifica, Long idMatMatriAlu, Long idCentroOmc, Float nota) {
        LOG.info("Insertando nota global del alumno");

        valoracionCriteriosRepository.insertNotaGlobalAlumno(idCalifica, idMatMatriAlu, idCentroOmc, nota);
    }

    public void editNotaGlobalAlumno(Long idNotAlu, Long idCalifica, Float nota) {
        valoracionCriteriosRepository.editNotaGlobalAlumno(idNotAlu, idCalifica, nota);
    }

    public void deleteNotaGlobalAlumno(Long idNotAlu) {
        valoracionCriteriosRepository.deleteNotaGlobalAlumno(idNotAlu);
    }

    @Transactional
    public void insertNotasComAlumnos(Long idPonderacion, Long idCalifica, Long idCompetencia, Long idMatMatriAlu, Long idCentroOmc) {
        LOG.info("Insertando nota de competencias a los alumnos");

        valoracionCriteriosRepository.insertNotasComAlumnos(idPonderacion, idCalifica, idCompetencia, idMatMatriAlu, idCentroOmc);
    }

    @Transactional
    public void editNotasComAlumnos(Long idComAlu, Long idCalifica) {
        valoracionCriteriosRepository.editNotasComAlumnos(idComAlu, idCalifica);
    }

    @Transactional
    public void deleteNotasComAlumnos(Long idComAlu) {
        valoracionCriteriosRepository.deleteNotasComAlumnos(idComAlu);
    }

    @Transactional
    public void insertNotasCriAlumnos(Long idPonderacion, Long idCalifica, Long idCriEva, Long idMatricula, Long idCentroOmc) {
        LOG.info("Insertando notas de criterios a los alumnos");
        
        ValCriAlu valoracion = new ValCriAlu();
        valoracion.setPonderacion(idPonderacion);
        valoracion.setCalifica(idCalifica);
        valoracion.setCrieva(idCriEva);
        valoracion.setMatMatricula(idMatricula);
        valoracion.setConvCentroOmc(idCentroOmc);
        valCriAluRepository.save(valoracion);
       // valoracionCriteriosRepository.insertNotasCriAlumnos(idPonderacion, idCalifica, idCriEva, idMatricula, idCentroOmc);
    }

    @Transactional
    public void editNotasCriAlumnos(Long idCrialu, Long idCalifica) {
    	if(idCalifica != null) {
    		Optional<ValCriAlu> valoracionOpt = valCriAluRepository.findById(idCrialu);
        	if(valoracionOpt.isPresent()) {
        		ValCriAlu valoracion = valoracionOpt.get();
        		valoracion.setCalifica(idCalifica);
        		valCriAluRepository.save(valoracion);
        	}
    	} else {
    		deleteNotasCriAlumnos(idCrialu);
    	}
        
        //valoracionCriteriosRepository.editNotasCriAlumnos(idCrialu, idCalifica);
    }

    @Transactional
    public void deleteNotasCriAlumnos(Long idCriAlu) {
        valoracionCriteriosRepository.deleteNotasCriAlumnos(idCriAlu);
    }

    @Transactional
    public void bloquearPonderacion(Long idPonderacion) {
        valoracionCriteriosRepository.bloquearPonderacion(idPonderacion);
    }

    public List<Annos> getUltimosAnnos() {
        LOG.info("Obteniendo los ultimos años");

        List<AnnosProjection> annosProjection = valoracionCriteriosRepository.getUltimosAnnos();
        List<Annos> annos = annosProjection.stream().map(anno -> modelMapper.map(anno, Annos.class)).collect(Collectors.toList());

        return annos;
    }

    public List<Convocatorias> getConvocatorias(Long anno, Long idUnidad, Long idOfertaMatrig) {
        LOG.info("Obteniendo las convocatorias");

        List<ConvocatoriaProjection> convocatoriaProjection = valoracionCriteriosRepository.getConvocatorias(anno, idUnidad, idOfertaMatrig);
        List<Convocatorias> convocatorias = convocatoriaProjection.stream().map(convocatoria -> modelMapper.map(convocatoria, Convocatorias.class)).collect(Collectors.toList());

        return convocatorias;
    }

    public List<Convocatorias> getConvocatoriasValoracionCriterios(Long anno, Long idUnidad, Long idOfertaMatrig) {
        LOG.info("Obteniendo las convocatorias");

        List<ConvocatoriaProjection> convocatoriaProjection = valoracionCriteriosRepository.getConvocatoriasValoracionCriterios(anno, idUnidad, idOfertaMatrig);
        List<Convocatorias> convocatorias = convocatoriaProjection.stream().map(convocatoria -> modelMapper.map(convocatoria, Convocatorias.class)).collect(Collectors.toList());

        return convocatorias;
    }

	public List<MateriasValoracion> getMateriasValoracion(Long idEmpleado, Long anno, Long idCentro, List<Long> idUnidades, Long idOfertamatrig) {
        LOG.info("Obteniendo las materias de valoración");
        
        List<MateriasValoracionProjection> materiasValoracionProjection = null;
        if (idOfertamatrig == null) {
	        if(idUnidades == null) {
	        	idUnidades = new ArrayList<Long>(); idUnidades.add((long )-1);
	        	materiasValoracionProjection = valoracionCriteriosRepository.getMateriasValoracion(idEmpleado, anno, idCentro, idUnidades);
	        }else {
	        	materiasValoracionProjection = valoracionCriteriosRepository.getMateriasValoracionTutor(anno, idCentro, idUnidades);
	        }
        } else {
        	materiasValoracionProjection = valoracionCriteriosRepository.getMateriasValoracionCurso(anno, idCentro, idOfertamatrig);
        }
        
        List<MateriasValoracion> materiasValoracion = materiasValoracionProjection.stream().map(materia -> modelMapper.map(materia, MateriasValoracion.class)).collect(Collectors.toList());

        return materiasValoracion;
    }


    public List<UnidadesValoracion> getUnidadesValoracion(List<Long> idDocentes, Long anno, Long idMateria, Long idCentro, Long idProgdidac, boolean tutor, boolean direccion) {

        LOG.info("Obteniendo las unidades de valoración");

        List<UnidadesValoracionProjection> unidadesValoracionProjection = valoracionCriteriosRepository.getUnidadesValoracion(idDocentes, anno, idMateria, idCentro, tutor ? 1L:0L, direccion ? 1L:0L, idProgdidac);

        return unidadesValoracionProjection.stream().map(unidad -> modelMapper.map(unidad, UnidadesValoracion.class)).collect(Collectors.toList());
    }

    public List<SistemaCalificacionCua> sistemaCalificacion(Long idEtapa) {
        LOG.info("Obteniendo sistema de calificacion: " + idEtapa);

        List<SistemaCalificacionProjection> sistemaCalificacionProjection = valoracionCriteriosRepository.sistemaCalificacion(idEtapa);

        return sistemaCalificacionProjection.stream().map(calificacion -> modelMapper.map(calificacion, SistemaCalificacionCua.class)).collect(Collectors.toList());
    }

    public List<SistemaCalificacionCua> sistemaCalificacionGlobal(Long idOfertaMatrig) {
        LOG.info("Obteniendo sistema de calificacion global: " + idOfertaMatrig);

        List<SistemaCalificacionProjection> sistemaCalificacionProjection = valoracionCriteriosRepository.sistemaCalificacionGlobal(idOfertaMatrig);
        List<SistemaCalificacionCua> sistemaCalificacion = sistemaCalificacionProjection.stream().map(calificacion -> modelMapper.map(calificacion, SistemaCalificacionCua.class)).collect(Collectors.toList());

        return sistemaCalificacion;
    }

    public List<UnidadEvaluacion> getUnidadesEvaluacion(Long idEmpleado, Long anno) {
        LOG.info("Obteniendo las unidades de evaluación");

        List<UnidadEvaluacionProjection> unidadesValoracionProjection = valoracionCriteriosRepository.getUnidadesEvaluacion(idEmpleado, anno);
        List<UnidadEvaluacion> unidadesValoracion = unidadesValoracionProjection.stream().map(unidad -> modelMapper.map(unidad, UnidadEvaluacion.class)).collect(Collectors.toList());

        for (UnidadEvaluacion unidadEvaluacion: unidadesValoracion) {
            if(unidadEvaluacion.getEtapa().equals("Bachillerato")) {
                unidadEvaluacion.setIdEtapa(unidadEvaluacion.getIdCiclo());
            }
        }

        return unidadesValoracion;
    }
    
    public List<CompetenciaClave> getCompetenciasClave() {
    	return (List<CompetenciaClave>) competenciaClaveRepository.findAll();
    }
    
    public List<DescriptorOperativo> getDescriptoresOperativosByComClaveAndEtapa(CompetenciaClave competenciaClave, Long idEtapa) {
    	return (List<DescriptorOperativo>) descriptorOperativoRepository.findAllByCompetenciaClaveAndIdEtapa(competenciaClave,idEtapa);
    }

    public List<AlumnoEvaluacionSel> getAlumnosUnidad(Long idCurso, Long idUnidad, Long idOfertamatrig) {
        try {
            List<AlumnoEvaluacionSel> alumnos = valoracionCriteriosRepository.getAlumnosUnidad(idCurso, idUnidad, idOfertamatrig).stream().map(x -> modelMapper.map(x, AlumnoEvaluacionSel.class)).collect(Collectors.toList());
            return alumnos;
        } catch (Exception e) {
            LOG.error(e);
            throw e;
        }
    }

    public List<AlumnoEvaluacionSel> getAlumnosUnidadConvExtra(Long idCurso, Long idUnidad, Long idConvocatoria, Long idOfertamatrig) {
        try {
            List<AlumnoEvaluacionSel> alumnos = valoracionCriteriosRepository.getAlumnosUnidadConvExtra(idCurso, idUnidad, idConvocatoria, idOfertamatrig).stream().map(x -> modelMapper.map(x, AlumnoEvaluacionSel.class)).collect(Collectors.toList());
            return alumnos;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

	public List<AlumnoEvaluacionSel> getAlumnosByMatriculas(String idsMatAlu, Long idEtapa) {
		try {
			Long[] idsMatricula = (Long[]) ConvertUtils.convert(idsMatAlu.split(","), Long[].class);
			List<AlumnoEvaluacionSel> alumnos = valoracionCriteriosRepository.getAlumnosByMatriculas(idsMatricula).stream().map(x -> modelMapper.map(x, AlumnoEvaluacionSel.class)).collect(Collectors.toList());
            for(AlumnoEvaluacionSel alumno: alumnos) {
                alumno.setIdEtapa(idEtapa);
            }
			return alumnos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<CompetenciaClaveAlumno> getCompetenciasClaveAlumno(Long idEtapa, Long idMatricula, Long idConvCentroOmc) {
		LOG.info("Obteniendo evaluación de competencias clave de ", idMatricula);
		
		List<CompetenciaClaveAlumno> competenciasClave = valoracionCriteriosRepository.getCompetenciasClaveAlumno(idMatricula, idConvCentroOmc).stream().map(x -> modelMapper.map(x, CompetenciaClaveAlumno.class)).collect(Collectors.toList());
		
		try {
			for(CompetenciaClaveAlumno comClave : competenciasClave) {
				List<DescriptorOperativoAlumno> descriptoresOperativos = valoracionCriteriosRepository.getDescriptoresOperativosAlumno(comClave.getIdCompetenciaClave(), idEtapa, idMatricula, idConvCentroOmc).stream().map(x -> modelMapper.map(x, DescriptorOperativoAlumno.class)).collect(Collectors.toList());
                /*
				Double sumaNotaCompCla = 0D;
				Integer numDescOperCalc = 0;
				if(descriptoresOperativos != null) {
					for (DescriptorOperativoAlumno desOperativo : descriptoresOperativos) {
						if (desOperativo.getIdCalifica() != null) {
							numDescOperCalc++;
							sumaNotaCompCla += desOperativo.getNota();
						} else {
							SistemaCalificacionProjection calificacionDescOperProjection = null;
							List<NotaCompetencia> notasComEsp = getNotasComEspAlumnoByDescOper(desOperativo.getIdDescriptorOperativo(), idMatricula, idConvCentroOmc);
							if (notasComEsp != null && !notasComEsp.isEmpty()) {
								Double notaDescOper = 0D;
								Integer numComEspSum = 0;
								for(NotaCompetencia notaComEsp : notasComEsp) {
									if(notaComEsp.getNota() != null) {
										notaDescOper += notaComEsp.getNota().floatValue();
										numComEspSum++;
									}
								}
								if(numComEspSum > 0) {
									notaDescOper = notaDescOper/numComEspSum;
									numDescOperCalc++;
									calificacionDescOperProjection = valoracionCriteriosRepository.getCalificacionSistemaByNota(idEtapa, Math.round(notaDescOper));
									if (calificacionDescOperProjection != null) {
										SistemaCalificacionCua calificacionDescOper = modelMapper.map(calificacionDescOperProjection, SistemaCalificacionCua.class);
										desOperativo.setIdCalifica(calificacionDescOper.getIdCalifica());
										desOperativo.setDescCal(calificacionDescOper.getDescCal());
										desOperativo.setAprueba(calificacionDescOper.getAprueba());
										desOperativo.setNota(calificacionDescOper.getNota());
									}
									desOperativo.setNota(Math.round(notaDescOper));
									sumaNotaCompCla += notaDescOper;
								} else {
									ValoracionDescriptorOperativoAlumnoProjection valDOHistoricoProjection = valoracionCriteriosRepository.getValoracionDescriptorOperativoAlumnoHistorico(desOperativo.getIdDescriptorOperativo(), idEtapa, idMatricula);
    								if(valDOHistoricoProjection != null) {
    									ValoracionDescriptorOperativoAlumno valDOHistorico = modelMapper.map(valDOHistoricoProjection, ValoracionDescriptorOperativoAlumno.class);
    									desOperativo.setIdCalifica(valDOHistorico.getIdCalifica());
										desOperativo.setDescCal(valDOHistorico.getDescCal());
										desOperativo.setAprueba(valDOHistorico.getAprueba());
										desOperativo.setNota(valDOHistorico.getNota());
    									numDescOperCalc++;
    	    							sumaNotaCompCla += valDOHistorico.getNota();
    								}
								}
							}
						}
					}
                    */
					comClave.setDescriptoresOperativos(descriptoresOperativos);
                    /*
					if (comClave.getIdCalifica() == null) {
						if (numDescOperCalc > 0) {
							SistemaCalificacionProjection calificacionComClaProjection = valoracionCriteriosRepository.getCalificacionSistemaByNota(idEtapa, Math.round(sumaNotaCompCla/numDescOperCalc));
							if (calificacionComClaProjection != null) {
								SistemaCalificacionCua calificacionComCla = modelMapper.map(calificacionComClaProjection, SistemaCalificacionCua.class);
								comClave.setIdCalifica(calificacionComCla.getIdCalifica());
								comClave.setDescCal(calificacionComCla.getDescCal());
								comClave.setAprueba(calificacionComCla.getAprueba());
								comClave.setNota(calificacionComCla.getNota());
							} else {
								comClave.setNota(Math.round(sumaNotaCompCla/numDescOperCalc));
							}
						} else {
							ValoracionCompetenciaClaveAlumnoProjection valCCHistoricoProjection = valoracionCriteriosRepository.getValoracionCompetenciaClaveAlumnoHistorico(comClave.getIdCompetenciaClave(), idMatricula);
    						if (valCCHistoricoProjection != null) {
    							ValoracionCompetenciaClaveAlumno valCCHistorico = modelMapper.map(valCCHistoricoProjection, ValoracionCompetenciaClaveAlumno.class);
    							comClave.setIdCalifica(valCCHistorico.getIdCalifica());
								comClave.setDescCal(valCCHistorico.getDescCal());
								comClave.setAprueba(valCCHistorico.getAprueba());
								comClave.setNota(valCCHistorico.getNota());
    						}
						}
					}
				}
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return competenciasClave;
	}

    @Transactional
    public void saveCompetenciasClaveAlumno(Long idMatricula, Long idConvCentroOmc, CompetenciaClaveAlumnoDTO competenciasClaveIn) {
        try {
        	CompetenciaClaveAlumno competenciasClave = modelMapper.map(competenciasClaveIn, CompetenciaClaveAlumno.class);
        	
            if (valoracionCriteriosRepository.existCompetenciaClave(competenciasClave.getIdCompetenciaClave(), idMatricula, idConvCentroOmc) != null) {
                valoracionCriteriosRepository.editCompetenciasClave(competenciasClave.getIdCompetenciaClave(), competenciasClave.getIdCalifica(), idMatricula, idConvCentroOmc);
            } else {
                valoracionCriteriosRepository.insertCompetenciasClave(competenciasClave.getIdCompetenciaClave(), competenciasClave.getIdCalifica(), idMatricula, idConvCentroOmc);
            }

            for (DescriptorOperativoAlumno descriptorOperativo : competenciasClave.getDescriptoresOperativos()) {
                if (descriptorOperativo.getNotaCambiada() == true) {
                    if (valoracionCriteriosRepository.existDescriptoreOperativo(descriptorOperativo.getIdDescriptorOperativo(), idMatricula, idConvCentroOmc) != null) {
                        valoracionCriteriosRepository.editDescriptoresOperativos(descriptorOperativo.getIdDescriptorOperativo(), descriptorOperativo.getIdCalifica(), idMatricula, idConvCentroOmc);
                    } else {
                        valoracionCriteriosRepository.insertDescriptoresOperativos(descriptorOperativo.getIdDescriptorOperativo(), descriptorOperativo.getIdCalifica(), idMatricula, idConvCentroOmc);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional //TODO: refactorizar en un solo método
    public void saveCompetenciaClaveMatriz(Long idMatricula, Long idConvCentroOmc, ValoracionCompetenciaClaveAlumno competenciasClave) {
        try {
            if (valoracionCriteriosRepository.existCompetenciaClave(competenciasClave.getIdCompetenciaClave(), idMatricula, idConvCentroOmc) != null) {
                if(competenciasClave.getIdCalifica() != null) {
                    valoracionCriteriosRepository.editCompetenciasClave(competenciasClave.getIdCompetenciaClave(), competenciasClave.getIdCalifica(), idMatricula, idConvCentroOmc);
                } else {
                    valoracionCriteriosRepository.deleteCompetenciasClave(competenciasClave.getIdCompetenciaClave(), idMatricula, idConvCentroOmc);
                }
            } else {
                valoracionCriteriosRepository.insertCompetenciasClave(competenciasClave.getIdCompetenciaClave(), competenciasClave.getIdCalifica(), idMatricula, idConvCentroOmc);
            }

            for (ValoracionDescriptorOperativoAlumno descriptorOperativo : competenciasClave.getValoracionesDescriptoresOperativos()) {

                saveDescriptoresOperativosMatriz(descriptorOperativo, idMatricula, idConvCentroOmc);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public void saveDescriptoresOperativosMatriz(ValoracionDescriptorOperativoAlumno descriptorOperativo, Long idMatricula, Long idConvCentroOmc) {
        if (descriptorOperativo.getNotaCambiada()) {
            //Añadir comprobacion para hacer delete y crear delete
            if (valoracionCriteriosRepository.existDescriptoreOperativo(descriptorOperativo.getIdDescriptorOperativo(), idMatricula, idConvCentroOmc) != null) {

                if(descriptorOperativo.getIdCalifica() != null){
                    valoracionCriteriosRepository.editDescriptoresOperativos(descriptorOperativo.getIdDescriptorOperativo(), descriptorOperativo.getIdCalifica(), idMatricula, idConvCentroOmc);
                } else {
                    valoracionCriteriosRepository.deleteDescriptoresOperativos(descriptorOperativo.getIdDescriptorOperativo(), idMatricula, idConvCentroOmc);
                }

            } else {
                valoracionCriteriosRepository.insertDescriptoresOperativos(descriptorOperativo.getIdDescriptorOperativo(), descriptorOperativo.getIdCalifica(), idMatricula, idConvCentroOmc);
            }
        }
    }
    
    private List<NotaCompetencia> getNotasComEspAlumnoByDescOper(Long idDescriptorOperativo, Long idMatricula, Long idConvCentroOmc) {
    	
    	List<NotaCompetenciaProjection> notasCompetenciasProjection = valoracionCriteriosRepository.getNotasCompetenciasEspAlumnoByDescriptorOperativo(idDescriptorOperativo, idMatricula, idConvCentroOmc);
    	List<NotaCompetencia> notasCompetencias = notasCompetenciasProjection.stream().map(notaCompetencia -> modelMapper.map(notaCompetencia, NotaCompetencia.class)).collect(Collectors.toList());
    	
    	return notasCompetencias;
    
    }

    @Transactional
    public Long createRegSelDocsInformeValoracionCriterios(String idAlumnos) {

        Long idEjecucion = regSelDocRepository.getIdEjecucionRegSelDoc();

        Long[] idsAlumnos = (Long[]) ConvertUtils.convert(idAlumnos.split(","), Long[].class);

        for (Long idAlumno : idsAlumnos) {
            regSelDocRepository.insertRegSelDocAlumno(idEjecucion, idAlumno);
        }

        return idEjecucion;
    }
    
    public List<CompetenciaEspecifica> getCompetenciaEspecifica(Long descripcionOperativa, Long etapa, Long idMatricula, Long idConvCentroOmc) {
    	
    	List<CompetenciaEspecificaProjection> competenciaProjection = valoracionCriteriosRepository.getCompetenciasEspecificas(descripcionOperativa, etapa, idMatricula, idConvCentroOmc);
    	List<CompetenciaEspecifica> competenciaEsp = competenciaProjection.stream().map(competencia -> modelMapper.map(competencia, CompetenciaEspecifica.class)).collect(Collectors.toList());
    	
    	return competenciaEsp;
    
    }
    
    public ValoracionDescriptorOperativoAlumno getValoracionDescriptorOperativoAlumno(Long idDescriptorOperativo, Long idEtapa, Long idMatricula, Long idConvCentroOmc) {
		ValoracionDescriptorOperativoAlumnoProjection valoracionProjection = valoracionCriteriosRepository.getValoracionDescriptorOperativoAlumno(idDescriptorOperativo, idEtapa, idMatricula, idConvCentroOmc); 
		ValoracionDescriptorOperativoAlumno valoracion = null;
		if (valoracionProjection != null) {
			valoracion = modelMapper.map(valoracionProjection, ValoracionDescriptorOperativoAlumno.class);
			if(valoracion.getIdCalifica() == null) {
				// Se rescatan las notas de las competencias específicas
				List<NotaCompetencia> notasComEsp = getNotasComEspAlumnoByDescOper(idDescriptorOperativo, idMatricula, idConvCentroOmc);
				if (notasComEsp != null && !notasComEsp.isEmpty()) { // Si hay notas, se hace la media de todas las que estén guardadas para calcular la valoración de cada descriptor operativo
					Double notaDescOper = 0D; // Se fija a cero la nota calculada del descriptor operativo
					Integer numComEspSum = 0; // Se reinicia el número de competencias específicas que participan en la suma de las notas
					for(NotaCompetencia notaComEsp : notasComEsp) {
						if(notaComEsp.getNota() != null) {
							notaDescOper += notaComEsp.getNota().floatValue();
							numComEspSum++;
						}
					}
					if(numComEspSum > 0) {
						notaDescOper = notaDescOper/numComEspSum; // Se hace la media de las notas de las competencias específicas asociadas al descriptor operativo
						SistemaCalificacionProjection calificacionDOProjection = valoracionCriteriosRepository.getCalificacionSistemaByNota(idEtapa, Math.round(notaDescOper));
						if (calificacionDOProjection != null) {
							SistemaCalificacionCua calificacionDO = modelMapper.map(calificacionDOProjection, SistemaCalificacionCua.class);
							valoracion.setIdCalifica(calificacionDO.getIdCalifica());
							valoracion.setDescCal(calificacionDO.getDescCal());
							valoracion.setAprueba(calificacionDO.getAprueba());
							valoracion.setNota(calificacionDO.getNota());
							valoracion.setDescDetCal(calificacionDO.getDescripcion());
						}
					}
				}
			}
		}
		return valoracion;
	}
    
    private AlumnoValoracion setFotoAlumno(Blob img, AlumnoValoracion alumn) {
        try {
            if (img != null) {
                alumn.setFoto(img.getBytes(1, (int) img.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumn;
    }
    
    public String getBachillerato(Long idEtapa, Long idOfertamatrig) {
    	return valoracionCriteriosRepository.getBachillerato(idEtapa, idOfertamatrig);
    }
    
    public List<AlumnoEvaluacionCalculadaDTO> getAlumnosEvaluacionCalculada(Long idPonderacion, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idUnidad, boolean tutor, boolean direccion) {

    	
    	List<AlumnoEvaluacionCalculadaDTO> alumnosEvaluacionCalculada = valoracionCriteriosRepository.getAlumnosEvaluacionYAcnee(idEmpleados, fechaTomaPosesion, idPonderacion, idNivelCurricular, idUnidad, tutor ? 1L:0L, direccion ? 1L:0L, (long) -1 ).stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacionCalculadaDTO.class)).collect(Collectors.toList());
    	
    	for (AlumnoEvaluacionCalculadaDTO alumno : alumnosEvaluacionCalculada) {
            Blob img = valoracionCriteriosRepository.getFotoAlumno(alumno.getNumEscolar());
            setFotoAlumno(img, alumno);
            
            if(alumno.getDisabled() == 0) {

            	EvaNotaGlobalCalculadaAlumnoMateriaTemporal notaGlobalTemporal = notaGlobalTemporalRepository.findByMatMatricula(alumno.getIdMatMatriAlu());

                if (notaGlobalTemporal != null) {
                    alumno.setNotaGlobalTemporal(modelMapper.map(notaGlobalTemporalRepository.getDatosNotaGlobalTemporal(notaGlobalTemporal.getId()), NotaGlobalCalculadaAlumnoMateriaTemporalDTO.class));
                }

                List<ValoracionTemporalCompetenciaEspecificaAlumnoDTO> valoracionesCompetenciaAlumnoTempOut = new ArrayList<>();

                List<ValoracionTemporalCompetenciaEspecificaAlumno> valoracionesCompetenciaAlumnoTemp = valoracionCompetenciaAlumnoTemporalRepository
                        .findAllByIdMatMatriculaAndIdPonderacion(alumno.getIdMatMatriAlu(), idPonderacion)
                        .stream().map(x -> modelMapper.map(x, ValoracionTemporalCompetenciaEspecificaAlumno.class)).collect(Collectors.toList());

                for (ValoracionTemporalCompetenciaEspecificaAlumno valComAluTemp : valoracionesCompetenciaAlumnoTemp) {
                    ValoracionTemporalCompetenciaEspecificaAlumnoDTO valComAluTempOut = modelMapper.map(valComAluTemp, ValoracionTemporalCompetenciaEspecificaAlumnoDTO.class);

                    List<ValoracionTemporalCriterioEvaluacionAlumnoDTO> valoracionesCriterioAlumnoTempOut = valoracionCriterioAlumnoTemporalRepository
                            .findAllByIdMatMatriAluAndIdComEspAndIdPonderacion(alumno.getIdMatMatriAlu(), valComAluTemp.getComEsp(), idPonderacion)
                            .stream().map(x -> modelMapper.map(x, ValoracionTemporalCriterioEvaluacionAlumnoDTO.class)).collect(Collectors.toList());

                    valComAluTempOut.setCriterios(valoracionesCriterioAlumnoTempOut);

                    valoracionesCompetenciaAlumnoTempOut.add(valComAluTempOut);
                }

                alumno.setCompetencias(valoracionesCompetenciaAlumnoTempOut);
            }

    	}
    	
    	return alumnosEvaluacionCalculada;
    }
    
    private AlumnoEvaluacionCalculadaDTO setFotoAlumno(Blob img, AlumnoEvaluacionCalculadaDTO alumn) {
        try {
            if (img != null) {
                alumn.setFoto(img.getBytes(1, (int) img.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumn;
    }
    
    @Transactional
    public void trasladarCalculoConvocatoria(Long idPonderacion, Long idConvCentroOmc, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idSistemaCalifica, Long idUnidad, boolean tutor, boolean direccion) {
    	
    	List<AlumnoEvaluacionCalculadaDTO> alumnosEvaluacionCalculada = valoracionCriteriosRepository.getAlumnosEvaluacionYAcnee(idEmpleados, fechaTomaPosesion, idPonderacion, idNivelCurricular, idUnidad, tutor ? 1L:0L, direccion ? 1L:0L, idConvCentroOmc).stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacionCalculadaDTO.class)).collect(Collectors.toList());

        //Añadimos la comprobación de que el alumno no tenga ninguna materia llave sin aprobar

    	for (AlumnoEvaluacionCalculadaDTO alumno : alumnosEvaluacionCalculada) {

            //AÑADIMOS LA COMPROBACIÓN PARA VER SI EL ALUMNO TIENE UNA MATERIA PENDIENTE QUE SEA LLAVE DE UNA DE LA QUE ESTE MATRICULADA
            Long idMateriaOmgAlumno = alumno.getIdMateriaOmg();
            Long idMatriculaAlumno = alumno.getIdMatricula();

            //Llamamos al endpoint que recorre la matricula del alumno y devuelve cuales de sus materias tiene una materia llave como asignatura pendiente matriculada
            List<Long> materiasConLlaveEnMatricula = valoracionCriteriosRepository.getMateriasLlaveByMatricula(idMatriculaAlumno);

            //Si en la lista de materias con materia llave se encuentra la materia actual, ponemos disabled a 1
            if (materiasConLlaveEnMatricula.contains(idMateriaOmgAlumno))
            {
                alumno.setDisabled(1L);
                alumno.setMateriaLlavePendiente(true);
            }

    		if(alumno.getDisabled() == 0) {
    			saveNotasAlumnoCalcToConv(alumno, idPonderacion, idConvCentroOmc, idSistemaCalifica);
    		}
    	}
		
	}

    public List<ActividadCriterioDTO> getActividadesbyCriterio(Long idMatriAlu, Long idCriterio, Long idPonderacion) {

        List<ActividadCriterioProjection> projections = valoracionCriteriosRepository.getActividadesByCriterio(idMatriAlu,idCriterio,idPonderacion);

        return projections.stream().map(actividadCriterio -> modelMapper.map(actividadCriterio, ActividadCriterioDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void saveNotasAlumnoCalcToConv(AlumnoEvaluacionCalculadaDTO alumno, Long idPonderacion, Long idConvCentroOmc, Long idSistemaCalifica) {
    	
    	// Recorre por las valoraciones temporales de competencias específicas de la materia del alumno
    	List<EvaValoracionTemporalCompetenciaEspecificaAlumno> valoracionesTempComEspAlu = valoracionCompetenciaAlumnoTemporalRepository.findByIdPonderacionAndMatMatricula(idPonderacion, alumno.getIdMatMatriAlu());
    	
    	for (EvaValoracionTemporalCompetenciaEspecificaAlumno valComAluTemp : valoracionesTempComEspAlu) {
    		NotaCompetencia notaComEsp = getNotaComAlumno(valComAluTemp.getComEsp(), alumno.getIdMatMatriAlu(), idConvCentroOmc);
    		
    		// Si la valoración de competencia existe, lo edita. En caso contrario, lo inserta.
    		if (notaComEsp != null) {
    			editNotasComAlumnos(notaComEsp.getIdComAlu(), valComAluTemp.getCalificacion().getId());
    		} else {
    			insertNotasComAlumnos(valComAluTemp.getIdPonderacion(), valComAluTemp.getCalificacion().getId(), valComAluTemp.getComEsp(), alumno.getIdMatMatriAlu(), idConvCentroOmc);
    		}
    		
    		// Recorre por las valoraciones temporales de criterios de evaluación asociado a la competencia específica de la materia del alumno
    		getEvaluacionTemporal(alumno, idPonderacion, idConvCentroOmc, valComAluTemp);
    	}
    	
    	
    	// Rescata las valoraciones de competencias que no tienen valoración temporal y las borra
    	List<NotaCompetencia> notasCompetenciasSinCalculo = valoracionCriteriosRepository.getNotasCompetenciasAlumnoSinCalculo(alumno.getIdMatMatriAlu(), idConvCentroOmc, idPonderacion).stream().map(notaComEsp -> modelMapper.map(notaComEsp, NotaCompetencia.class)).collect(Collectors.toList());
    	for (NotaCompetencia notaComEsp : notasCompetenciasSinCalculo) {
    		deleteNotasComAlumnos(notaComEsp.getIdComAlu());
    	}
    	
    	rescatarEvaluaciones(alumno, idPonderacion, idConvCentroOmc, idSistemaCalifica);
    	
    }

	private void getEvaluacionTemporal(AlumnoEvaluacionCalculadaDTO alumno, Long idPonderacion, Long idConvCentroOmc,
			EvaValoracionTemporalCompetenciaEspecificaAlumno valComAluTemp) {
		List<EvaValoracionTemporalCriterioEvaluacionAlumno> valoracionesTempCriEvaAlu = valoracionCriterioAlumnoTemporalRepository.findAllByIdPonderacionAndIdMatMatriAluAndIdComEsp(idPonderacion, alumno.getIdMatMatriAlu(), valComAluTemp.getComEsp());
		
		for (EvaValoracionTemporalCriterioEvaluacionAlumno valCriAluTemp : valoracionesTempCriEvaAlu) {
			NotaCriterio notaCriEva = getNotaCriAlumno(valCriAluTemp.getCriEva(), alumno.getIdMatMatriAlu(), idConvCentroOmc);
			
			//Si la valoración de criterio existe, lo edita. En caso contrario, lo inserta.
			if (notaCriEva != null) {
				editNotasCriAlumnos(notaCriEva.getIdCriAlu(), valCriAluTemp.getCalificacion().getId());
			} else {
				insertNotasCriAlumnos(valCriAluTemp.getIdPonderacion(), valCriAluTemp.getCalificacion().getId(), valCriAluTemp.getCriEva(), alumno.getIdMatMatriAlu(), idConvCentroOmc);
			}
		}
	}

	private void rescatarEvaluaciones(AlumnoEvaluacionCalculadaDTO alumno, Long idPonderacion, Long idConvCentroOmc,
			Long idSistemaCalifica) {
		// Rescata las valoraciones de criterio que no tienen valoración temporal y las borra
    	List<NotaCriterio> notasCriteriosSinCalculo = valoracionCriteriosRepository.getNotasCriteriosAlumnoSinCalculo(alumno.getIdMatMatriAlu(), idConvCentroOmc, idPonderacion).stream().map(notaCriEva -> modelMapper.map(notaCriEva, NotaCriterio.class)).collect(Collectors.toList());
    	for (NotaCriterio notaCriEva : notasCriteriosSinCalculo) {
    		deleteNotasCriAlumnos(notaCriEva.getIdCriAlu());
    	}
    	
    	// Comprueba si no es educación infantil
    	if (idSistemaCalifica != 747) {
	    	// Rescata la nota global calculada temporal de la materia del alumno
	    	EvaNotaGlobalCalculadaAlumnoMateriaTemporal notCalMatTemp = notaGlobalTemporalRepository.findByMatMatricula(alumno.getIdMatMatriAlu());
	    	NotaGlobal notaGlobal = getNotaGlobalAlumno(alumno.getIdMatMatriAlu(), idConvCentroOmc);
	    	
	    	// Si la nota global y nota global calculada temporal existen, se edita. Si es solo la nota global, se borra. Si es solo la nota global calculada temporal, se inserta.
	    	if (notaGlobal != null) {
	    		if (notCalMatTemp != null) {
	    			editNotaGlobalAlumno(notaGlobal.getIdNotAlu(), notCalMatTemp.getCalificacion().getId(), Float.valueOf(notCalMatTemp.getNota().floatValue()));
	    		} else {
	    			deleteNotaGlobalAlumno(notaGlobal.getIdNotAlu());
	    		}
	    	} else {
	    		if (notCalMatTemp != null) {
	    			insertNotaGlobalAlumno(notCalMatTemp.getCalificacion().getId(), alumno.getIdMatMatriAlu(), idConvCentroOmc, Float.valueOf(notCalMatTemp.getNota().floatValue()));
	    		}
	    	}
    	}
	}
    
    public List<UnidadEvaluacion> getUnidadesEvaluacionCompClave(List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idCentro, Long anno, Boolean direccion) {
        List<UnidadEvaluacionProjection> unidadesValoracionProjection = new ArrayList<>();

        if(direccion) {
            unidadesValoracionProjection = valoracionCriteriosRepository.getUnidadesEvaluacionCompClaveDirector(idCentro, anno);
        } else {
            unidadesValoracionProjection = valoracionCriteriosRepository.getUnidadesEvaluacionCompClave(idEmpleados, fechaTomaPosesion, idCentro, anno);
        }

        List<UnidadEvaluacion> unidadesValoracion = unidadesValoracionProjection.stream().map(unidad -> modelMapper.map(unidad, UnidadEvaluacion.class)).collect(Collectors.toList());

        for (UnidadEvaluacion unidadEvaluacion: unidadesValoracion) {
            if(unidadEvaluacion.getEtapa().equals("Bachillerato")) {
                unidadEvaluacion.setIdEtapa(unidadEvaluacion.getIdCiclo());
            }
        }

        return unidadesValoracion;
    }
    
    public List<AlumnoValoracion> getHistoricoAlumnosValoracionCurricular(Long idCurso, Long idUnidad, Long idConvocatoria, Long idEtapa, Long idOfertamatrig) {
    	LOG.info("Obteniendo evaluación curricular de la unidad ", idUnidad);
    	
    	try {
    		// Se rescatan los alumnos de la unidad a evaluar
    		List<AlumnoValoracion> alumnos = valoracionCriteriosRepository.getAlumnosValoracionByUnidadAndConvocatoria(idCurso, idUnidad, idConvocatoria, idOfertamatrig).stream().map(x -> modelMapper.map(x, AlumnoValoracion.class)).collect(Collectors.toList());
    		// Se recorren los alumnos
    		for(AlumnoValoracion alum : alumnos)  {
                alum.setIdEtapa(idEtapa);
    			// Se comprueba si el alumno se ha evaluado de todas sus materias de la convocatoria y se almacena el valor
    			alum.setTodasMateriasEvaluadas(valoracionCriteriosRepository.isTodasMateriasEvaluadas(alum.getIdMatricula(), alum.getIdConvCentroOmc()));

    			// Se rescata la foto del alumno
    			Blob img = valoracionCriteriosRepository.getFotoAlumno(alum.getNumEscolar());
    			setFotoAlumno(img, alum); // Se almacena la foto en la clase
    			List<ValoracionCompetenciaClaveAlumno> valoracionesComClaAlu = valoracionCriteriosRepository.getValoracionesCompetenciasClaveAlumno(alum.getIdMatricula(), alum.getIdConvCentroOmc()).stream().map(x -> modelMapper.map(x, ValoracionCompetenciaClaveAlumno.class)).collect(Collectors.toList());
    			// Se recorren las competencias clave
    			for(ValoracionCompetenciaClaveAlumno valCC : valoracionesComClaAlu) {
    				List<ValoracionDescriptorOperativoAlumno> valoracionesDesOpeAlu = valoracionCriteriosRepository.getValoracionesDescriptoresOperativosAlumno(valCC.getIdCompetenciaClave(), alum.getIdEtapa(), alum.getIdMatricula(), alum.getIdConvCentroOmc()).stream().map(x -> modelMapper.map(x, ValoracionDescriptorOperativoAlumno.class)).collect(Collectors.toList());
    				if(valoracionesDesOpeAlu != null) {
    					// Se almacenan las valoraciones de los descriptores operativos en las de su respectiva competencia clave
    					valCC.setValoracionesDescriptoresOperativos(valoracionesDesOpeAlu);
    				}
    			}
    			// Se almacenan las valoraciones de las competencias clave en el currículo de valoraciones del alumno
    			alum.setValoracionesCompetenciasClave(valoracionesComClaAlu);
    		}
    		// Se devuelve la lista de alumnos
    		return alumnos;
		} catch (Exception e) {
	        e.printStackTrace();
	        throw e;
        }
    }
    

    public void setMateriasNoEvaluadas( List<AlumnoValoracionDTO> alumnos){
        for(AlumnoValoracionDTO alum : alumnos){
            Boolean todasMateriasEvaluadas = alum.getTodasMateriasEvaluadas();
            List<String> listMateriasNoEvaluadas =  todasMateriasEvaluadas ? null : this.valoracionCriteriosRepository.getNombreMateriasNoEvaluadas(alum.getIdMatricula(), alum.getIdConvCentroOmc());
            alum.setListMateriasNoEvaluadas((listMateriasNoEvaluadas));
        }
    }

    public List<AlumnoValoracionAdquisicionDTO> getAlumnosValoracionTemporalCurricular(Long idUnidad, Long idEtapa, Long idOfertamatrig) {
    	LOG.info("Obteniendo evaluación temporal de la adquisición curricular de la unidad ", idUnidad);
    	List<AlumnoValoracionAdquisicionDTO> alumnos = alumnoRepository.findAllAlumnosValoracionAdquisicionCompetenciaClaveByIdUnidadAndIdOfertaMatricula(idUnidad, idOfertamatrig).stream().map(x -> modelMapper.map(x, AlumnoValoracionAdquisicionDTO.class)).collect(Collectors.toList());
    	
    	for(AlumnoValoracionAdquisicionDTO alum : alumnos) {
    		//Se asigna el idEtapa
    		alum.setIdEtapa(idEtapa);
    		
    		if (alum.getAcnee() == 1) { // En caso de que sea un alumno ACNEE, se asigna el idEtapa del nivel de adaptación
    			alum.setIdEtapaAdaptacion(valoracionCriteriosRepository.getIdEtapaAdapatacionAlumnoACNEE(alum.getIdMatricula()));
    		}
    		
    		// Se rescata la foto del alumno
			Blob img = valoracionCriteriosRepository.getFotoAlumno(alum.getNumEscolar());
			setFotoAlumno(img, alum); // Se almacena la foto en la clase
			
			List<ValoracionTemporalCompetenciaClaveAlumnoDTO> valoracionesTemporalesCompetenciasClaveAlumno = valoracionTemporalCompetenciaClaveAlumnoRepository.getValoracionesByIdMatricula(alum.getIdMatricula()).stream().map(x -> modelMapper.map(x, ValoracionTemporalCompetenciaClaveAlumnoDTO.class)).collect(Collectors.toList());
			
			List<ValoracionTemporalCompetenciaClaveAlumnoDTO> valoracionesTempCompClaveAlum = new ArrayList<>();
			
			for(ValoracionTemporalCompetenciaClaveAlumnoDTO valTCC : valoracionesTemporalesCompetenciasClaveAlumno) {
				valoracionesTempCompClaveAlum.add(calcularValoracionTemporalCompetenciaClaveAlumno(valTCC, alum, idEtapa));
			}
			
			alum.setValoracionesCompetenciasClave(valoracionesTempCompClaveAlum);
			
    	}
    	
    	return alumnos;
    }
    
    private AlumnoValoracionAdquisicionDTO setFotoAlumno(Blob img, AlumnoValoracionAdquisicionDTO alumn) {
        try {
            if (img != null) {
                alumn.setFoto(img.getBytes(1, (int) img.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumn;
    }
    
    private ValoracionTemporalCompetenciaClaveAlumnoDTO calcularValoracionTemporalCompetenciaClaveAlumno(ValoracionTemporalCompetenciaClaveAlumnoDTO valTCC, AlumnoValoracionAdquisicionDTO alum, Long idEtapa) {
        Optional<EvaValoracionTemporalCompetenciaClaveAlumno> valoracionTempCompClaveGuardada = Optional.empty();

        if (valTCC.getId() != null) {
            valoracionTempCompClaveGuardada = valoracionTemporalCompetenciaClaveAlumnoRepository.findById(valTCC.getId());
        }

        List<ValoracionTemporalDescriptorOperativoAlumnoDTO> valoracionesTemporalesDescriptoresOperativosAlumno = valoracionTemporalDescriptorOperativoRepository.findAllByIdCompetenciaClaveAndIdEtapaAndIdMatricula(valTCC.getIdCompetenciaClave(), idEtapa, alum.getIdMatricula()).stream().map(x -> modelMapper.map(x, ValoracionTemporalDescriptorOperativoAlumnoDTO.class)).collect(Collectors.toList());
        List<ValoracionTemporalDescriptorOperativoAlumnoDTO> valoracionesTemporalesDescriptoresOperativosAlumnoCalculado = new ArrayList<>();

        // Se pone a cero la nota calculada de la competencia clave
        Double sumaNotaCompCla = 0D;
        // Se establece el número de notas de descriptores operativos calculadas a cero
        Integer numDescOperCalc = 0;

        for(ValoracionTemporalDescriptorOperativoAlumnoDTO valTDO : valoracionesTemporalesDescriptoresOperativosAlumno) {


            Optional<EvaValoracionTemporalDescriptorOperativoAlumno> valoracionTempDescOperGuardada = Optional.empty();

            if (valTDO.getId() != null) {
                valoracionTempDescOperGuardada = valoracionTemporalDescriptorOperativoRepository.findById(valTDO.getId());
            }

            Integer notaDescOper = getCalculoNotaMediaTemporalDescriptorOperativo(valTDO, alum);

            if(notaDescOper != null) {
                Optional<EvaCalificacion> calificacionDescOperOpt = calificacionRepository.findByIdEtapaAndNota(alum.getIdEtapa(), notaDescOper);
                if (calificacionDescOperOpt.isPresent()) {
                    valoracionesTemporalesDescriptoresOperativosAlumnoCalculado.add(guardarValoracionTemporalDescriptorOperativo(calificacionDescOperOpt.get(), valTDO, alum, valoracionTempDescOperGuardada));
                }
                numDescOperCalc++;
                sumaNotaCompCla += notaDescOper;
            }
        }

        if (numDescOperCalc > 0) {

            Optional<EvaCalificacion> calificacionCompClaveOpt = calificacionRepository.findByIdEtapaAndNota(alum.getIdEtapa(), toLong(sumaNotaCompCla, numDescOperCalc));

            if(calificacionCompClaveOpt.isPresent()) {
                valTCC = guardarValoracionTemporalCompetenciaClave(calificacionCompClaveOpt.get(), valTCC, alum, valoracionTempCompClaveGuardada);
            }
        }

        valTCC.setValoracionesDescriptoresOperativos(valoracionesTemporalesDescriptoresOperativosAlumnoCalculado);

        return valTCC;
    }

    private Integer getCalculoNotaMediaTemporalDescriptorOperativo(ValoracionTemporalDescriptorOperativoAlumnoDTO valTDO, AlumnoValoracionAdquisicionDTO alum) {
        Boolean calculoDescriptorOperativoACNEE = false;
        List<Long> idsMatMatriculaACNEE = null;

        if(alum.getIdEtapaAdaptacion() != null) {
            idsMatMatriculaACNEE = valoracionCriteriosRepository.getIdsMatMatriculaACNEEByIdDescriptorOperativoAndIdMatricula(valTDO.getIdDescriptorOperativo(), alum.getIdMatricula());
            calculoDescriptorOperativoACNEE = idsMatMatriculaACNEE != null && !idsMatMatriculaACNEE.isEmpty();
        }

        if (calculoDescriptorOperativoACNEE) {
            return valoracionCompetenciaAlumnoTemporalRepository.getNotaMediaRedondeadaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(valTDO.getIdDescriptorOperativo(), alum.getIdMatricula(), alum.getIdEtapaAdaptacion(), idsMatMatriculaACNEE.toArray(new Long[idsMatMatriculaACNEE.size()]));
        } else {
            return valoracionCompetenciaAlumnoTemporalRepository.getNotaMediaRedondeadaByIdDescriptorOperativoAndIdMatricula(valTDO.getIdDescriptorOperativo(), alum.getIdMatricula());
        }
    }

    private ValoracionTemporalDescriptorOperativoAlumnoDTO guardarValoracionTemporalDescriptorOperativo(EvaCalificacion calificacionDescOper, ValoracionTemporalDescriptorOperativoAlumnoDTO valTDO, AlumnoValoracionAdquisicionDTO alum, Optional<EvaValoracionTemporalDescriptorOperativoAlumno> valoracionTempDescOperGuardada){
        EvaValoracionTemporalDescriptorOperativoAlumno valoracionTempDescOperAlum;

        // Si existe la valoración temporal, se actualiza, de lo contrario se crea un objeto nuevo
        if (valoracionTempDescOperGuardada.isPresent()) {
            valoracionTempDescOperAlum = valoracionTempDescOperGuardada.get();
        } else {
            valoracionTempDescOperAlum = new EvaValoracionTemporalDescriptorOperativoAlumno();
            valoracionTempDescOperAlum.setIdDescriptorOperativo(valTDO.getIdDescriptorOperativo());
            valoracionTempDescOperAlum.setIdMatricula(alum.getIdMatricula());
        }
        // Se guarda la nota calculada del alumno del descriptor operativo
        valoracionTempDescOperAlum.setCalificacion(calificacionDescOper);
        EvaValoracionTemporalDescriptorOperativoAlumno valoracionTempDescOperAlumGuardado = valoracionTemporalDescriptorOperativoRepository.save(valoracionTempDescOperAlum);

        // Se rescatan los datos de la valoración guardada del descriptor operativo del alumno
        valTDO = modelMapper.map(valoracionTempDescOperAlumGuardado, ValoracionTemporalDescriptorOperativoAlumnoDTO.class);
        valTDO.setDescCal(calificacionDescOper.getDescripcionCorta());
        valTDO.setAprueba(calificacionDescOper.getLAprueba());
        valTDO.setNota(calificacionDescOper.getNumero());
        valTDO.setDescDetCal(calificacionDescOper.getDescripcion());

        return valTDO;
    }

    private ValoracionTemporalCompetenciaClaveAlumnoDTO guardarValoracionTemporalCompetenciaClave(EvaCalificacion calificacionCompClave, ValoracionTemporalCompetenciaClaveAlumnoDTO valTCC, AlumnoValoracionAdquisicionDTO alum, Optional<EvaValoracionTemporalCompetenciaClaveAlumno> valoracionTempCompClaveGuardada) {
        EvaValoracionTemporalCompetenciaClaveAlumno valoracionTempCompClaveAlum;

        // Si existe la valoración temporal, se actualiza, de lo contrario se crea un objeto nuevo
        if (valoracionTempCompClaveGuardada.isPresent()) {
            valoracionTempCompClaveAlum = valoracionTempCompClaveGuardada.get();
        } else {
            valoracionTempCompClaveAlum = new EvaValoracionTemporalCompetenciaClaveAlumno();
            valoracionTempCompClaveAlum.setIdCompetenciaClave(valTCC.getIdCompetenciaClave());
            valoracionTempCompClaveAlum.setIdMatricula(alum.getIdMatricula());
        }

        // Se guarda la nota calculada del alumno de la competencia clave
        valoracionTempCompClaveAlum.setCalificacion(calificacionCompClave);
        EvaValoracionTemporalCompetenciaClaveAlumno valoracionTempCompClaveAlumGuardado = valoracionTemporalCompetenciaClaveAlumnoRepository.save(valoracionTempCompClaveAlum);

        // Se rescatan los datos de la valoración guardada del descriptor operativo del alumno
        valTCC = modelMapper.map(valoracionTempCompClaveAlumGuardado, ValoracionTemporalCompetenciaClaveAlumnoDTO.class);
        valTCC.setDescCal(calificacionCompClave.getDescripcionCorta());
        valTCC.setAprueba(calificacionCompClave.getLAprueba());
        valTCC.setNota(calificacionCompClave.getNumero());
        valTCC.setDescDetCal(calificacionCompClave.getDescripcion());

        return valTCC;
    }

	@Override
	public List<CompetenciaClaveAlumno> getCompetenciasClaveTemporalesAlumno(Long idMatricula, Long idEtapa) {
		List<CompetenciaClaveAlumno> competenciasClave = valoracionCriteriosRepository.getCompetenciasClaveTemporalesAlumno(idMatricula).stream().map(x -> modelMapper.map(x, CompetenciaClaveAlumno.class)).collect(Collectors.toList());
		for(CompetenciaClaveAlumno compClave : competenciasClave) {
			Optional<EvaValoracionTemporalCompetenciaClaveAlumno> valoracionTempCompClaveGuardada = Optional.empty();
			
			if (compClave.getIdValComClaAluTemp() != null) {
				valoracionTempCompClaveGuardada = valoracionTemporalCompetenciaClaveAlumnoRepository.findById(compClave.getIdValComClaAluTemp());
			}
			
			Long idEtapaAdaptacionACNEE = valoracionCriteriosRepository.getIdEtapaAdapatacionAlumnoACNEE(idMatricula);
			
			List<DescriptorOperativoAlumno> descriptoresOperativos = valoracionCriteriosRepository.getDescriptoresOperativosTemporalesAlumno(compClave.getIdCompetenciaClave(), idMatricula, idEtapa).stream().map(x -> modelMapper.map(x, DescriptorOperativoAlumno.class)).collect(Collectors.toList());
			
			// Se pone a cero la nota calculada de la competencia clave
			Double sumaNotaCompCla = 0D;
			// Se establece el número de notas de descriptores operativos calculadas a cero
			Integer numDescOperCalc = 0;
			
			for(DescriptorOperativoAlumno descOper : descriptoresOperativos) {
				
				
				Optional<EvaValoracionTemporalDescriptorOperativoAlumno> valoracionTempDescOperGuardada = Optional.empty();
				
				if (descOper.getIdValDesOpeAluTemp() != null) {
					valoracionTempDescOperGuardada = valoracionTemporalDescriptorOperativoRepository.findById(descOper.getIdValDesOpeAluTemp());
				}
				
				Boolean calculoDescriptorOperativoACNEE = false;
				List<Long> idsMatMatriculaACNEE = null;
				
				if (idEtapaAdaptacionACNEE != null) {
					idsMatMatriculaACNEE = valoracionCriteriosRepository.getIdsMatMatriculaACNEEByIdDescriptorOperativoAndIdMatricula(descOper.getIdDescriptorOperativo(), idMatricula);
					calculoDescriptorOperativoACNEE = idsMatMatriculaACNEE != null && !idsMatMatriculaACNEE.isEmpty();
				}
				
				Integer notaDescOper = null;
				
				if (calculoDescriptorOperativoACNEE) {
					notaDescOper = valoracionCompetenciaAlumnoTemporalRepository.getNotaMediaRedondeadaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(descOper.getIdDescriptorOperativo(), idMatricula, idEtapaAdaptacionACNEE, idsMatMatriculaACNEE.toArray(new Long[idsMatMatriculaACNEE.size()]));
				} else {
					notaDescOper = valoracionCompetenciaAlumnoTemporalRepository.getNotaMediaRedondeadaByIdDescriptorOperativoAndIdMatricula(descOper.getIdDescriptorOperativo(), idMatricula);
				}
				
				if(notaDescOper != null) {
					Optional<EvaCalificacion> calificacionDescOperOpt = calificacionRepository.findByIdEtapaAndNota(idEtapa, notaDescOper);
					if (calificacionDescOperOpt.isPresent()) {
						EvaCalificacion calificacionDescOper = calificacionDescOperOpt.get();
						
						EvaValoracionTemporalDescriptorOperativoAlumno valoracionTempDescOperAlum;
						
						// Si existe la valoración temporal, se actualiza, de lo contrario se crea un objeto nuevo
						if (valoracionTempDescOperGuardada.isPresent()) {
							valoracionTempDescOperAlum = valoracionTempDescOperGuardada.get();
						} else {
							valoracionTempDescOperAlum = new EvaValoracionTemporalDescriptorOperativoAlumno();
							valoracionTempDescOperAlum.setIdDescriptorOperativo(descOper.getIdDescriptorOperativo());
							valoracionTempDescOperAlum.setIdMatricula(idMatricula);
						}
						// Se guarda la nota calculada del alumno del descriptor operativo
						valoracionTempDescOperAlum.setCalificacion(calificacionDescOper);
						EvaValoracionTemporalDescriptorOperativoAlumno valoracionTempDescOperAlumGuardado = valoracionTemporalDescriptorOperativoRepository.save(valoracionTempDescOperAlum);
						
						// Se rescatan los datos de la valoración guardada del descriptor operativo del alumno
						
						descOper.setIdValDesOpeAluTemp(valoracionTempDescOperAlumGuardado.getId());
						descOper.setIdCalifica(calificacionDescOper.getId());
						descOper.setDescCal(calificacionDescOper.getDescripcionCorta());
						descOper.setAprueba(calificacionDescOper.getLAprueba());
						descOper.setNota(calificacionDescOper.getNumero().longValue());
					}
					numDescOperCalc++;
					sumaNotaCompCla += notaDescOper;
				}
			}
			
			if (numDescOperCalc > 0) {
				
				Optional<EvaCalificacion> calificacionCompClaveOpt = calificacionRepository.findByIdEtapaAndNota(idEtapa, toLong(sumaNotaCompCla, numDescOperCalc));
				
				if(calificacionCompClaveOpt.isPresent()) {
					EvaCalificacion calificacionCompClave = calificacionCompClaveOpt.get();
					
					EvaValoracionTemporalCompetenciaClaveAlumno valoracionTempCompClaveAlum;
					
					// Si existe la valoración temporal, se actualiza, de lo contrario se crea un objeto nuevo
					if (valoracionTempCompClaveGuardada.isPresent()) {
						valoracionTempCompClaveAlum = valoracionTempCompClaveGuardada.get();
					} else {
						valoracionTempCompClaveAlum = new EvaValoracionTemporalCompetenciaClaveAlumno();
						valoracionTempCompClaveAlum.setIdCompetenciaClave(compClave.getIdCompetenciaClave());
						valoracionTempCompClaveAlum.setIdMatricula(idMatricula);
					}
					
					// Se guarda la nota calculada del alumno de la competencia clave
					valoracionTempCompClaveAlum.setCalificacion(calificacionCompClave);
					EvaValoracionTemporalCompetenciaClaveAlumno valoracionTempCompClaveAlumGuardado = valoracionTemporalCompetenciaClaveAlumnoRepository.save(valoracionTempCompClaveAlum);
					
					// Se rescatan los datos de la valoración guardada del descriptor operativo del alumno
					compClave.setIdValComClaAluTemp(valoracionTempCompClaveAlumGuardado.getId());
					compClave.setIdCalifica(calificacionCompClave.getId());
					compClave.setDescCal(calificacionCompClave.getDescripcionCorta());
					compClave.setAprueba(calificacionCompClave.getLAprueba());
					compClave.setNota(calificacionCompClave.getNumero().longValue());					}
			}
			
			compClave.setDescriptoresOperativos(descriptoresOperativos);
		}
		return competenciasClave;
	}

	@Override
	public List<CursoValoracionDTO> getCursosValoracionByCentroAndAnno(Long idCentro, Integer anno) {
		return valoracionCriteriosRepository.getCursosValoracionByCentroAndAnno(idCentro, anno).stream().map(x -> modelMapper.map(x, CursoValoracionDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	public void calcularValoracionCurricularUnidades(String idsUnidad, Long idConvocatoria, List<Long> idsOfertamatrig) {
    	LOG.info("Calculando evaluación de la adquisición curricular de las unidades: ", idsUnidad);

    	Long[] idsUnidades = (Long[]) ConvertUtils.convert((idsUnidad).split(","), Long[].class);

    	for (Long idUnidad : idsUnidades) {

            Long idConvocatoriaFinal = null;
            if (idConvocatoria != null) {
                idConvocatoriaFinal = idConvocatoria;
            } else {
                idConvocatoriaFinal = valoracionCriteriosRepository.getIdConvocatoriaByIdUnidad(idUnidad);
            }

    		List<AlumnoValoracion> alumnos = valoracionCriteriosRepository.getAlumnosValoracionByUnidadAndConvocatoria(idUnidad, idConvocatoriaFinal, idsOfertamatrig).stream().map(x -> modelMapper.map(x, AlumnoValoracion.class)).collect(Collectors.toList());

    		for(AlumnoValoracion alum : alumnos)  {

    			List<ValoracionCompetenciaClaveAlumno> valoracionesComClaAlu = valoracionCriteriosRepository.getValoracionesCompetenciasClaveAlumno(alum.getIdMatricula(), alum.getIdConvCentroOmc()).stream().map(x -> modelMapper.map(x, ValoracionCompetenciaClaveAlumno.class)).collect(Collectors.toList());
    			// Se recorren las competencias clave
    			for(ValoracionCompetenciaClaveAlumno valCC : valoracionesComClaAlu) {

					setCompetenciasClave(alum, valCC);
				}
	    	}
    	}
    }

    public void calcularValoracionCurricularUnidadesConvocatoriasCursos(List<UnidadConvocatoriaCursoDTO> unidadesConvocatoriasCursos) {

        for (UnidadConvocatoriaCursoDTO ucc : unidadesConvocatoriasCursos) {
            LOG.info("Calculando evaluación de la adquisición curricular de la unidad " + ucc.getIdUnidad() + ", convocatoria " + ucc.getIdConvocatoria() + " y curso " + ucc.getIdOfertamatrig());

            List<AlumnoValoracion> alumnos = valoracionCriteriosRepository.getAlumnosValoracionByUnidadAndConvocatoriaAndCurso(ucc.getIdUnidad(), ucc.getIdConvocatoria(), ucc.getIdOfertamatrig()).stream().map(x -> modelMapper.map(x, AlumnoValoracion.class)).collect(Collectors.toList());

            for(AlumnoValoracion alum : alumnos)  {

                List<ValoracionCompetenciaClaveAlumno> valoracionesComClaAlu = valoracionCriteriosRepository.getValoracionesCompetenciasClaveAlumno(alum.getIdMatricula(), alum.getIdConvCentroOmc()).stream().map(x -> modelMapper.map(x, ValoracionCompetenciaClaveAlumno.class)).collect(Collectors.toList());
                // Se recorren las competencias clave
                for(ValoracionCompetenciaClaveAlumno valCC : valoracionesComClaAlu) {

                    setCompetenciasClave(alum, valCC);
                }
            }
        }

    }

    private void setCompetenciasClave(AlumnoValoracion alum, ValoracionCompetenciaClaveAlumno valCC) {
		Optional<EvaValoracionCompetenciaClaveAlumno> valoracionCompClaveGuardada = Optional.empty();

		if (valCC.getId() != null) {
			valoracionCompClaveGuardada = valoracionCompetenciaClaveAlumnoRepository.findById(valCC.getId());
		}

		List<ValoracionDescriptorOperativoAlumno> valoracionesDesOpeAlu = valoracionCriteriosRepository.getValoracionesDescriptoresOperativosAlumno(valCC.getIdCompetenciaClave(), alum.getIdEtapa(), alum.getIdMatricula(), alum.getIdConvCentroOmc()).stream().map(x -> modelMapper.map(x, ValoracionDescriptorOperativoAlumno.class)).collect(Collectors.toList());

		// Se pone a cero la nota calculada de la competencia clave
		Double sumaNotaCompCla = 0D;
		// Se establece el número de notas de descriptores operativos calculadas a cero
		Integer numDescOperCalc = 0;

		for(ValoracionDescriptorOperativoAlumno valDO : valoracionesDesOpeAlu) {


			Optional<EvaValoracionDescriptorOperativoAlumno> valoracionDescOperGuardada = Optional.empty();

			boolean calculoPorDefecto = true;
            boolean esConvocatoriaExtraordinaria = valoracionCriteriosRepository.isConvocatoriaExtraordinaria(alum.getIdConvCentroOmc());
			List<Long> idsMatMatriculaACNEE = null;

            if (valDO.getId() != null && alum.getIdEtapaAdaptacion() != null) {
                valoracionDescOperGuardada = valoracionDescriptorOperativoAlumnoRepository.findById(valDO.getId());
                idsMatMatriculaACNEE = valoracionCriteriosRepository.getIdsMatMatriculaACNEEByIdDescriptorOperativoAndIdMatricula(valDO.getId(), alum.getIdAlumno());

                calculoPorDefecto = idsMatMatriculaACNEE.isEmpty();

            } else if (valDO.getId() != null) {
                valoracionDescOperGuardada = valoracionDescriptorOperativoAlumnoRepository.findById(valDO.getId());
            }
			Integer notaDescOper = getNotaMediaDescriptorOperativo(alum, valDO, calculoPorDefecto, esConvocatoriaExtraordinaria, idsMatMatriculaACNEE);

			if(notaDescOper != null) {
				Optional<EvaCalificacion> calificacionDescOperOpt = calificacionRepository.findByIdEtapaAndNota(alum.getIdEtapa(), notaDescOper);
				getDescripcionCalificacion(alum, valDO, valoracionDescOperGuardada, calificacionDescOperOpt);
				numDescOperCalc++;
				sumaNotaCompCla += notaDescOper;
			}
		}

		if (numDescOperCalc > 0) {

			Optional<EvaCalificacion> calificacionCompClaveOpt = calificacionRepository.findByIdEtapaAndNota(alum.getIdEtapa(), toLong(sumaNotaCompCla, numDescOperCalc));

			if(calificacionCompClaveOpt.isPresent()) {
				saveCalificacionComp(alum, valCC, valoracionCompClaveGuardada, calificacionCompClaveOpt);
			}
		}
	}

    private Integer getNotaMediaDescriptorOperativo(AlumnoValoracion alum, ValoracionDescriptorOperativoAlumno valDO, boolean calculoPorDefecto, boolean esConvocatoriaExtraordinaria, List<Long> idsMatMatriculaACNEE) {
        if (esConvocatoriaExtraordinaria) {
            Long idConvCentroOmcOrdinaria = valoracionCriteriosRepository.getIdConvCentroOmcrdinariaByIdMatricula(alum.getIdMatricula());
            if (calculoPorDefecto) {
                return valoracionCriteriosRepository.getNotaMediaRedondeadaExtraordinariaByIdDescriptorOperativoAndIdMatriculaAndIdsConvCentroOmc(valDO.getIdDescriptorOperativo(), alum.getIdMatricula(), alum.getIdConvCentroOmc(), idConvCentroOmcOrdinaria);
            } else {
                return valoracionCriteriosRepository.getNotaMediaRedondeadaExtarodinariaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdsConvCentroOmcAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(valDO.getIdDescriptorOperativo(), alum.getIdMatricula(), alum.getIdConvCentroOmc(), idConvCentroOmcOrdinaria, alum.getIdEtapaAdaptacion(), idsMatMatriculaACNEE.toArray(new Long[idsMatMatriculaACNEE.size()]));
            }
        } else {
            if (calculoPorDefecto) {
                return valoracionCriteriosRepository.getNotaMediaRedondeadaByIdDescriptorOperativoAndIdMatriculaAndIdConvCentroOmc(valDO.getIdDescriptorOperativo(), alum.getIdMatricula(), alum.getIdConvCentroOmc());
            } else {
                return valoracionCriteriosRepository.getNotaMediaRedondeadaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdConvCentroOmcAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(valDO.getIdDescriptorOperativo(), alum.getIdMatricula(), alum.getIdConvCentroOmc(), alum.getIdEtapaAdaptacion(), idsMatMatriculaACNEE.toArray(new Long[idsMatMatriculaACNEE.size()]));
            }
        }
    }

	private int toLong(Double sumaNotaCompCla, Integer numDescOperCalc) {
		Long a = Math.round(sumaNotaCompCla/numDescOperCalc);
		return a.intValue();
	}


	private void saveCalificacionComp(AlumnoValoracion alum, ValoracionCompetenciaClaveAlumno valCC,
			Optional<EvaValoracionCompetenciaClaveAlumno> valoracionCompClaveGuardada,
			Optional<EvaCalificacion> calificacionCompClaveOpt) {
		
		if(calificacionCompClaveOpt.isPresent()) {
			EvaCalificacion calificacionCompClave = calificacionCompClaveOpt.get();

			EvaValoracionCompetenciaClaveAlumno valoracionCompClaveAlum;

			// Si existe la valoración, se actualiza, de lo contrario se crea un objeto nuevo
			if (valoracionCompClaveGuardada.isPresent()) {
				valoracionCompClaveAlum = valoracionCompClaveGuardada.get();
			} else {
				valoracionCompClaveAlum = new EvaValoracionCompetenciaClaveAlumno();
				valoracionCompClaveAlum.setIdCompetenciaClave(valCC.getIdCompetenciaClave());
				valoracionCompClaveAlum.setIdMatricula(alum.getIdMatricula());
				valoracionCompClaveAlum.setIdConvCentroOmc(alum.getIdConvCentroOmc());
			}

			// Se guarda la nota calculada del alumno de la competencia clave
			valoracionCompClaveAlum.setCalificacion(calificacionCompClave);
			EvaValoracionCompetenciaClaveAlumno valoracionCompClaveAlumGuardado = valoracionCompetenciaClaveAlumnoRepository.save(valoracionCompClaveAlum);
		}
		
	}

	private void getDescripcionCalificacion(AlumnoValoracion alum, ValoracionDescriptorOperativoAlumno valDO,
			Optional<EvaValoracionDescriptorOperativoAlumno> valoracionDescOperGuardada,
			Optional<EvaCalificacion> calificacionDescOperOpt) {
		if (calificacionDescOperOpt.isPresent()) {
			EvaCalificacion calificacionDescOper = calificacionDescOperOpt.get();

			EvaValoracionDescriptorOperativoAlumno valoracionDescOperAlum;

			// Si existe la valoración, se actualiza, de lo contrario se crea un objeto nuevo
			if (valoracionDescOperGuardada.isPresent()) {
				valoracionDescOperAlum = valoracionDescOperGuardada.get();
			} else {
				valoracionDescOperAlum = new EvaValoracionDescriptorOperativoAlumno();
				valoracionDescOperAlum.setIdDescriptorOperativo(valDO.getIdDescriptorOperativo());
				valoracionDescOperAlum.setIdMatricula(alum.getIdMatricula());
				valoracionDescOperAlum.setIdConvCentroOmc(alum.getIdConvCentroOmc());
			}
			// Se guarda la nota calculada del alumno del descriptor operativo
			valoracionDescOperAlum.setCalificacion(calificacionDescOper);
			EvaValoracionDescriptorOperativoAlumno valoracionDescOperAlumGuardado = valoracionDescriptorOperativoAlumnoRepository.save(valoracionDescOperAlum);
		}
	}

	@Transactional
	public void calcularValoracionCurricularCursos(Long idCentro, Integer anno, List<Long> idsOfertamatrig, Long idConvocatoria) {
		List<Long> idsUnidades = valoracionCriteriosRepository.getIdsUnidadesByIdCentroAndIdsOfertamatrigs(idCentro, anno, idsOfertamatrig);

		calcularValoracionCurricularUnidades(StringUtils.join(idsUnidades, ","), idConvocatoria, idsOfertamatrig);
	}

	public List<AlumnoEvaluacion> getAlumnosACNEESinProgramacionAula(List<Long> idsEmpleado, List<String> fechasTomaPosesion, Long idOfertamatrig, Long idMateriaOmg, Long idCentro, Integer anno, boolean tutor, boolean direccion) {
		List<AlumnoEvaluacionProjection> alumnosProjection = valoracionCriteriosRepository.getAlumnosACNEESinProgramacionAula(idsEmpleado, fechasTomaPosesion, idOfertamatrig, idMateriaOmg, idCentro, anno, tutor ? 1L:0L, direccion ? 1L:0L);
		return alumnosProjection.stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacion.class)).collect(Collectors.toList());
	}

    public List<UnidadEvaluacionDTO> getUnidadesEvaluacionConConvocatorias(List<UnidadEvaluacion> unidadesEvaluacion, Long anno){
        List<UnidadEvaluacionDTO> unidadesDTO = unidadesEvaluacion.stream().map(uni -> modelMapper.map(uni, UnidadEvaluacionDTO.class)).collect(Collectors.toList());
        // Filtrar por convocatorias finales.
        unidadesDTO.forEach(unidad -> unidad.setConvocatorias(getConvocatorias(anno, unidad.getIdUnidad(), unidad.getIdOfertamatrig())));
        return unidadesDTO;
    }

    public List<String> getMateriasNoEvaluadasCalculoCompetenciaFinal(Long idUnidad, Long idConvCentroOmc) {
        return valoracionCriteriosRepository.getMateriasNoEvaluadasCalculoCompetenciaFinal(idUnidad, idConvCentroOmc);
    }

    @Override
    public List<UnidadesValoracion> getUnidadesValoracionPendiente(Integer anno, Long idOfertamatrig, Long idMateria, Long idCentro, Long idEmpleado, boolean tutor, boolean direccion) {
        return valoracionCriteriosRepository.getUnidadesValoracionPendiente(anno, idOfertamatrig, idMateria, idCentro, idEmpleado,  tutor ? 1L:0L, direccion ? 1L:0L).stream().map(unidad -> modelMapper.map(unidad, UnidadesValoracion.class)).collect(Collectors.toList());
    }

}
