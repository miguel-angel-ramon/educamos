package es.jccm.edu.proyectosfct.application.services.altassegsocial;

import com.fasterxml.jackson.databind.JsonNode;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoHistoricoAltasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.NuevoPeriodoDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocial.AltaSegSocialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialprog.AltaSegSocialProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialproy.AltaSegSocialProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.AlumnoProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestora.EnviosAlumnosGestoraLogRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasprog.HistoricoAltasProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasproy.HistoricoAltasProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesprog.HistoricoMesProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesproy.HistoricoMesProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConvProyAlumnoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesprog.SegSocialCotizaMesProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesproy.SegSocialCotizaMesProyRepository;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.entities.ListadoAltasSegSocial;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoAltasSegSocialProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosAlumnosGestoraLog;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasprogramas.entities.HistoricoAltasProg;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasproyectos.entities.HistoricoAltasProy;
import es.jccm.edu.proyectosfct.application.domain.historicomesprogramas.entities.HistoricoMesProg;
import es.jccm.edu.proyectosfct.application.domain.historicomesproyectos.entities.HistoricoMesProy;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.CotizaMesProgramas;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities.CotizaMesProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.altassegsocial.IAltasSegSocialService;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterTraineeStudent;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AltasSegSocialService implements IAltasSegSocialService {

	private static final Long ID_PERFIL_GESTOR = 11207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
    private static final Long ID_PERFIL_CENTRO = 161L;
    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String FP_DUAL = "FP Dual";
	
    @Autowired
    private AltaSegSocialProgRepository altaSegSocialProgRepository;

    @Autowired
    private HistoricoAltasProgRepository historicoAltasProgRepository;

    @Autowired
    private HistoricoAltasProyRepository historicoAltasProyRepository;

    @Autowired
    private AltaSegSocialRepository altaSegSocialRepository;

	@Autowired
	private AltaSegSocialProyRepository altaSegSocialProyRepository;

	@Autowired
	private AlumnoProgramaRepository alumnoProgramaRepository;

	@Autowired
	private ConvProyAlumnoRepository convProyAlumnoRepository;

    @Autowired
    private EnviosAlumnosGestoraLogRepository enviosAlumnosGestoraLogRepository;

    @Autowired
    private SegSocialCotizaMesProgRepository cotizaMesProgRepository;

    @Autowired
    private SegSocialCotizaMesProyRepository cotizaMesProyRepository;

    @Autowired
    private HistoricoMesProgRepository historicoMesProgRepository;

    @Autowired
    private HistoricoMesProyRepository historicoMesProyRepository;

	@Autowired
	IClientSegSocial clientSegSocial;
	
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ListadoAltasSegSocial> getListadoAltasSegSocial(Long idTutorfctdual,
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
                                                                Long idUsuario) throws ParseException {

        List<ListadoAltasSegSocialProjection> listadoAlumnosTutor = null;

        
        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
        	
            if (ID_PERFIL_GESTOR.equals(idPerfil)) {
				
            	listadoAlumnosTutor = altaSegSocialRepository.findListadoAltasSegSociProgDelegacion(idTutorfctdual,
																			                        idCentro, 
																			                        cAnno, 
																			                        tipoEmpresa, 
																			                        idEmpresa, 
																			                        idOfertamatrig, 
																			                        idUnidad,
																			                        idPerfil,
																								    idCentroCombo,
																								    idUsuario,
																								    idEstado);	
				
			} else {
				
				listadoAlumnosTutor = altaSegSocialRepository.findListadoAltasSegSociProgDelegacionProvincias(idTutorfctdual,
																										      idCentro, 
																										      cAnno, 
																										      tipoEmpresa, 
																										      idEmpresa, 
																										      idOfertamatrig, 
																										      idUnidad,																							  
																										      idCentroCombo,
																										      idProvincia,
																										      idEstado);			
			}
        	
        } else {
			
        	if (ID_PERFIL_CENTRO.equals(idPerfil)) {
        		
            	listadoAlumnosTutor = altaSegSocialRepository.findListadoAltasSegSociCen(idTutorfctdual,
																	                      idCentro, 
																	                      cAnno, 
																	                      tipoEmpresa, 
																	                      idEmpresa, 
																	                      idOfertamatrig, 
																	                      idUnidad,
																	                      idEstado);
        		
        	} else {
        		
            	listadoAlumnosTutor = altaSegSocialRepository.findListadoAltasSegSociProf(idTutorfctdual,
																                          idCentro, 
																                          cAnno, 
																                          tipoEmpresa, 
																                          idEmpresa, 
																                          idOfertamatrig, 
																                          idUnidad,
																                          idEstado,
																                          idUsuario);
        		
        	}
        	

			
		}
        
        

        return listadoAlumnosTutor.stream().map(entity -> modelMapper.map(entity, ListadoAltasSegSocial.class)).collect(Collectors.toList());
    
    
    
    
    
    }

	@Override
	public List<ElementoSelect> getEstadosSS(Long idPerfil) {
		
		List<ElementoSelectProjection> cursos = altaSegSocialRepository.getEstadosSS(idPerfil);

		return cursos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public void modifyDataAltasSegSocial(List<ListadoAltasSegSocial> listadoAltasSegSocialIn, Long xUsuarioDelphos) {

		for (ListadoAltasSegSocial alumnoAltaSegSocial: listadoAltasSegSocialIn){

			if(alumnoAltaSegSocial.getId() == -1){ //Si el ID es -1 creamos un nuevo registro

                createAltaSeguridadSocial(xUsuarioDelphos, alumnoAltaSegSocial);

            }else{ //Si el ID es no es -1 actualizamos el registro existente

                updateAltaSeguridadSocial(alumnoAltaSegSocial);

            }

		}

	}

    private void updateAltaSeguridadSocial(ListadoAltasSegSocial alumnoAltaSegSocial) {

        //Recuperamos de la tabla FCT_ALTASS_PROG
    	if("FCT".equals(alumnoAltaSegSocial.getTipoEmpresa())){
            updateAltaSegSociPrograma(alumnoAltaSegSocial);

        }  else if(FP_DUAL.equals(alumnoAltaSegSocial.getTipoEmpresa())){
            updateAltaSegSociProyecto(alumnoAltaSegSocial);
        }
    }

    private void updateAltaSegSociPrograma(ListadoAltasSegSocial alumnoAltaSegSocial) {
        AltasSegSociProg segSociProg = null;
        segSociProg = altaSegSocialProgRepository.findById(alumnoAltaSegSocial.getId()).orElse(null);

        if(segSociProg != null){

            segSociProg.setFechaInicio(alumnoAltaSegSocial.getInicio());
            segSociProg.setFechaFin(alumnoAltaSegSocial.getFin());
            segSociProg.setLgEramsusCb(alumnoAltaSegSocial.getAluConBeca());
            segSociProg.setLgErasmusSb(alumnoAltaSegSocial.getAluSinBeca());
            segSociProg.setLgValTut("Sí".equals(alumnoAltaSegSocial.getValTut()) ? 1 : 0);
            segSociProg.setLgValCen("Sí".equals(alumnoAltaSegSocial.getValCen()) ? 1 : 0);
            segSociProg.setLgValDel("Sí".equals(alumnoAltaSegSocial.getValDel()) ? 1 : 0);

            altaSegSocialProgRepository.save(segSociProg);

        }
    }

    private void updateAltaSegSociProyecto(ListadoAltasSegSocial alumnoAltaSegSocial) {
        AltasSegSociProy segSociProy = null;
        //Recuperamos de la tabla FCT_ALTASS_PROY
        segSociProy = altaSegSocialProyRepository.findById(alumnoAltaSegSocial.getId()).orElse(null);

        if(segSociProy != null){

            segSociProy.setFechaInicio(alumnoAltaSegSocial.getInicio());
            segSociProy.setFechaFin(alumnoAltaSegSocial.getFin());
            segSociProy.setLgEramsusCb(alumnoAltaSegSocial.getAluConBeca());
            segSociProy.setLgErasmusSb(alumnoAltaSegSocial.getAluSinBeca());
            segSociProy.setLgValTut("Sí".equals(alumnoAltaSegSocial.getValTut()) ? 1 : 0);
            segSociProy.setLgValCen("Sí".equals(alumnoAltaSegSocial.getValCen()) ? 1 : 0);
            segSociProy.setLgValDel("Sí".equals(alumnoAltaSegSocial.getValDel()) ? 1 : 0);

            altaSegSocialProyRepository.save(segSociProy);

        }
    }

    private void createAltaSeguridadSocial(Long xUsuarioDelphos, ListadoAltasSegSocial alumnoAltaSegSocial) {

        if("FCT".equals(alumnoAltaSegSocial.getTipoEmpresa())){ //Lo creamos en la tabla FCT_ALTASS_PROG

            createAltaSegSociPrograma(xUsuarioDelphos, alumnoAltaSegSocial);

        }else if(FP_DUAL.equals(alumnoAltaSegSocial.getTipoEmpresa())){ //Lo creamos en la tabla FCT_ALTASS_PROY

            createAltaSegSociProyecto(xUsuarioDelphos, alumnoAltaSegSocial);
        }
    }

    private void createAltaSegSociProyecto(Long xUsuarioDelphos, ListadoAltasSegSocial alumnoAltaSegSocial) {
        AltasSegSociProy segSociProy = new AltasSegSociProy();

        segSociProy.setFechaInicio(alumnoAltaSegSocial.getInicio());
        segSociProy.setFechaFin(alumnoAltaSegSocial.getFin());
        segSociProy.setLgEramsusCb(alumnoAltaSegSocial.getAluConBeca());
        segSociProy.setLgErasmusSb(alumnoAltaSegSocial.getAluSinBeca());
        segSociProy.setLgValTut("Sí".equals(alumnoAltaSegSocial.getValTut()) ? 1 : 0);
        segSociProy.setLgValCen("Sí".equals(alumnoAltaSegSocial.getValCen()) ? 1 : 0);
        segSociProy.setLgValDel("Sí".equals(alumnoAltaSegSocial.getValDel()) ? 1 : 0);
        ConveniosProyectoAlumno aluProy = convProyAlumnoRepository.findById(alumnoAltaSegSocial.getIdAluCon()).orElse(null);
        segSociProy.setIdConvProyAlu(alumnoAltaSegSocial.getIdAluCon());
        segSociProy.setXusuarioUlt(xUsuarioDelphos);
        if(aluProy != null && aluProy.getIdMatricula() != null){
            segSociProy.setXMatricula(aluProy.getIdMatricula());
        }

        altaSegSocialProyRepository.save(segSociProy);
    }

    private void createAltaSegSociPrograma(Long xUsuarioDelphos, ListadoAltasSegSocial alumnoAltaSegSocial) {
        AltasSegSociProg segSociProg = new AltasSegSociProg();

        segSociProg.setFechaInicio(alumnoAltaSegSocial.getInicio());
        segSociProg.setFechaFin(alumnoAltaSegSocial.getFin());
        segSociProg.setLgEramsusCb(alumnoAltaSegSocial.getAluConBeca());
        segSociProg.setLgErasmusSb(alumnoAltaSegSocial.getAluSinBeca());
        segSociProg.setLgValTut("Sí".equals(alumnoAltaSegSocial.getValTut()) ? 1 : 0);
        segSociProg.setLgValCen("Sí".equals(alumnoAltaSegSocial.getValCen()) ? 1 : 0);
        segSociProg.setLgValDel("Sí".equals(alumnoAltaSegSocial.getValDel()) ? 1 : 0);
        AlumnoPrograma aluProg = alumnoProgramaRepository.findById(alumnoAltaSegSocial.getIdAluCon()).orElse(null);
        segSociProg.setIdConvProgAlu(alumnoAltaSegSocial.getIdAluCon());
        segSociProg.setXusuarioUlt(xUsuarioDelphos);
        if(aluProg != null && aluProg.getIdMatricula() != null){
            segSociProg.setXMatricula(aluProg.getIdMatricula());
        }

        altaSegSocialProgRepository.save(segSociProg);
    }

    @Override
    public void resetEstadoValidar(Long id, Long idPerfil, String tipoEmpresa){

        if("FCT".equals(tipoEmpresa)){

            resetEstadoPrograma(id, idPerfil);

        }else {

            resetEstadoProyecto(id, idPerfil);

        }

    }

    private void resetEstadoProyecto(Long id, Long idPerfil) {
        AltasSegSociProy altasSegSociProy = altaSegSocialProyRepository.findById(id).orElse(null);

        if(altasSegSociProy != null){

            if(ID_PERFIL_GESTOR.equals(idPerfil))
            {
            	altasSegSociProy.setLgValDel(0);
                altasSegSociProy.setLgValCen(0);
            } else if (ID_PERFIL_CENTRO.equals(idPerfil)) {            	
            	altasSegSociProy.setLgValCen(0);
            }

            altasSegSociProy.setLgValTut(0);
            altaSegSocialProyRepository.save(altasSegSociProy);

        }
    }

    private void resetEstadoPrograma(Long id, Long idPerfil) {
        AltasSegSociProg altasSegSociProg = altaSegSocialProgRepository.findById(id).orElse(null);

        if(altasSegSociProg != null){

        	if(ID_PERFIL_GESTOR.equals(idPerfil)){
        		altasSegSociProg.setLgValDel(0);
        		altasSegSociProg.setLgValCen(0);
            } else if (ID_PERFIL_CENTRO.equals(idPerfil)) {
            	altasSegSociProg.setLgValCen(0);            	
            }

            altasSegSociProg.setLgValTut(0);
            altaSegSocialProgRepository.save(altasSegSociProg);
        }
    }

    private void registrarEnvioLog(RegisterTraineeStudent traineeStudent, String tipoEnvio) {
        EnviosAlumnosGestoraLog log = modelMapper.map(traineeStudent, EnviosAlumnosGestoraLog.class);
        log.setFechaEnvio(new Date());
        log.setTipoEnvio(tipoEnvio);

        enviosAlumnosGestoraLogRepository.save(log);
    }
    
    @Override
	public int envioAltasSSEmpresa(List<RegisterTraineeStudent> registerTraineeStudents, 
								   List<RegisterTraineeStudent> updatecanTraineeStudents,
								   List<RegisterTraineeStudent> cancelledTraineeStudents,
			                       Long xUsuarioDelphos) {

     	int envio = 0 ;

        //INSERT
    	if (registerTraineeStudents != null && registerTraineeStudents.size()>0) {


            envio = getEnvioUnAlumnoAlta(registerTraineeStudents, xUsuarioDelphos);

        }

        //UPDATE
        if (updatecanTraineeStudents != null && updatecanTraineeStudents.size()>0 ) {

            envio = getEnvioUnAlumnoUpdate(updatecanTraineeStudents, xUsuarioDelphos, envio);


        }

        if (cancelledTraineeStudents != null && cancelledTraineeStudents.size()>0) {

            envio = getEnvioUnAlumnoCancelled(cancelledTraineeStudents, xUsuarioDelphos, envio);

        }
        
		return envio;
	}

    private int getEnvioUnAlumnoCancelled(List<RegisterTraineeStudent> cancelledTraineeStudents, Long xUsuarioDelphos, int envio) {

        managementIdGestora(cancelledTraineeStudents, false);

        for (RegisterTraineeStudent registerTraineeStudent : cancelledTraineeStudents) {
            try {
                List<RegisterTraineeStudent> deleteTraineeGestora = new ArrayList<>();
                List<RegisterTraineeStudent> deleteTraineeAlta = new ArrayList<>();

                deleteTraineeAlta.add(registerTraineeStudent);

                registerTraineeStudent = compareDataGestora(registerTraineeStudent, UPDATE);

                deleteTraineeGestora.add(registerTraineeStudent);

                clientSegSocial.envioAltasSSEmpresaCancelled(registerTraineeStudent, xUsuarioDelphos);

                registrarEnvioLog(registerTraineeStudent, "CANCELACION");

                envio += updateEnvioFecha(deleteTraineeAlta, UPDATE);

                eliminarCotizacionesNoEnviadas(registerTraineeStudent.getTipoEmpresa(), registerTraineeStudent.getIdAlu());


            } catch (Exception ignored) {}
        }

        return envio;
    }

    private void eliminarCotizacionesNoEnviadas(String tipoEmpresa, Long idAlta) {
        if ("FCT".equals(tipoEmpresa)) {
            eliminarCotizacionesNoEnviadasProg(idAlta);
        } else if (FP_DUAL.equals(tipoEmpresa)) {
            eliminarCotizacionesNoEnviadasProy(idAlta);
        }
    }

	private void eliminarCotizacionesNoEnviadasProy(Long idAlta) {
		List<CotizaMesProyectos> cotizaciones = cotizaMesProyRepository.findCotizaMesNoEnviadaSolapadaAltaProy(idAlta);
		if (cotizaciones != null && !cotizaciones.isEmpty()) {
		    for (CotizaMesProyectos cot : cotizaciones) {

		        //List<HistoricoMesProy> historicos = historicoMesProyRepository.findByIdCotizaMesProy(cot.getIdCotizaMes());
		        //for (HistoricoMesProy hist : historicos) {
		        //    historicoMesProyRepository.delete(hist);
		        //}
		        cotizaMesProyRepository.delete(cot);    	
		    }
		}
	}

	private void eliminarCotizacionesNoEnviadasProg(Long idAlta) {
		List<CotizaMesProgramas> cotizaciones = cotizaMesProgRepository.findCotizaMesNoEnviadaSolapadaAltaProg(idAlta);
		if (cotizaciones != null && !cotizaciones.isEmpty()) {
		    for (CotizaMesProgramas cot : cotizaciones) {

		        //List<HistoricoMesProg> historicos = historicoMesProgRepository.findByIdCotizaMesProg(cot.getIdCotizaMes());
		        //for (HistoricoMesProg hist : historicos) {
		        //    historicoMesProgRepository.delete(hist);
		        //}
		    	cotizaMesProgRepository.delete(cot);		    	            
		    }
		}
	}

    private int getEnvioUnAlumnoUpdate(List<RegisterTraineeStudent> updatecanTraineeStudents, Long xUsuarioDelphos, int envio) {

        managementIdGestora(updatecanTraineeStudents, true);

        for (RegisterTraineeStudent registerTraineeStudent : updatecanTraineeStudents) {
            try {
                List<RegisterTraineeStudent> updatecanTraineeGestora = new ArrayList<>();
                List<RegisterTraineeStudent> updatecanTraineeAlta = new ArrayList<>();

                updatecanTraineeAlta.add(registerTraineeStudent);

                registerTraineeStudent = compareDataGestora(registerTraineeStudent, UPDATE);

                updatecanTraineeGestora.add(registerTraineeStudent);

                clientSegSocial.envioAltasSSEmpresa(updatecanTraineeGestora, xUsuarioDelphos, UPDATE);

                for (RegisterTraineeStudent student : updatecanTraineeGestora) {
                    registrarEnvioLog(student, "MODIFICACION");
                }

                envio += updateEnvioFecha(updatecanTraineeAlta, UPDATE);
            } catch (Exception ignored) { }
        }
        return envio;
    }

    private int getEnvioUnAlumnoAlta(List<RegisterTraineeStudent> registerTraineeStudents, Long xUsuarioDelphos) {

        int envio = 0;

        for (RegisterTraineeStudent studentRegister : registerTraineeStudents) {
            try {
                List<RegisterTraineeStudent> studentSingleList = Collections.singletonList(studentRegister);

                getSinDuplicados(studentSingleList);

                clientSegSocial.envioAltasSSEmpresa(studentSingleList, xUsuarioDelphos, INSERT);

                for (RegisterTraineeStudent student : studentSingleList) {
                    registrarEnvioLog(student, "ALTA");
                }

                envio += updateEnvioFecha(studentSingleList, INSERT);
            } catch (Exception ignored) {}
        }

        return envio;
    }

    private void getSinDuplicados(List<RegisterTraineeStudent> registerTraineeStudentsSinDup) {
		//Añadir columna worker_id_ext
		registerTraineeStudentsSinDup
		        .forEach(registerTraineeStudent -> {
		            registerTraineeStudent.setWorker_id_ext(altaSegSocialProgRepository.generateWorkerIdExt());
		            if(registerTraineeStudent.getTipoEmpresa().equals("FCT")){
		                AltasSegSociProg altasSegSociProg = altaSegSocialProgRepository.findById(registerTraineeStudent.getIdAlu()).orElse(null);
		                if(altasSegSociProg != null){
		                    altasSegSociProg.setIdInternoAlta(registerTraineeStudent.getWorker_id_ext());
		                    altaSegSocialProgRepository.save(altasSegSociProg);
		                }
		            }else{
		                AltasSegSociProy altasSegSociProy = altaSegSocialProyRepository.findById(registerTraineeStudent.getIdAlu()).orElse(null);
		                if(altasSegSociProy != null){
		                    altasSegSociProy.setIdInternoAlta(registerTraineeStudent.getWorker_id_ext());
		                    altaSegSocialProyRepository.save(altasSegSociProy);
		                }
		            }
		        });
	}    
    
    
    private List<RegisterTraineeStudent> quitarEnviados(List<RegisterTraineeStudent> registerTraineeStudents) {  	
    	
    	List<RegisterTraineeStudent> registerTraineeStudentSinDumpl= new ArrayList<>();
    	
    	for(RegisterTraineeStudent registerTraineeStudent: registerTraineeStudents){
            
    		Integer enviado = altaSegSocialProgRepository.registerEnviado(registerTraineeStudent.getIdAlu(),registerTraineeStudent.getErasmusfpdualScholarship()==null?0:registerTraineeStudent.getErasmusfpdualScholarship());
    				
    		if  (enviado == 0) registerTraineeStudentSinDumpl.add(registerTraineeStudent);	        	 	
        	
        }
    	
    	return registerTraineeStudentSinDumpl;
    	
    }
    
    
    private void managementIdGestora(List<RegisterTraineeStudent> updatecanTraineeStudents, boolean isUpdate) {
        for(RegisterTraineeStudent registerTraineeStudent: updatecanTraineeStudents){
            
        	
        	if (!isUpdate && registerTraineeStudent.getIdGestora() != null) {
        		
        		registerTraineeStudent.setId(registerTraineeStudent.getIdGestora());
        		
        	} else {
        	
	        	locateId(isUpdate, registerTraineeStudent);
        	} 	
        	
        }
    }

	private void locateId(boolean isUpdate, RegisterTraineeStudent registerTraineeStudent) {
		if(registerTraineeStudent.getId() == null){
		    JsonNode data = clientSegSocial.getDatosSegSocialByTipo(registerTraineeStudent.getNif(), "nifFechas", registerTraineeStudent.getInicioHist(), registerTraineeStudent.getFinHist(), "Alta", "OK");

		    if (data != null && !data.isEmpty()){
		        updateIdGestora(isUpdate, registerTraineeStudent, data);
		    } else {
		    	if (registerTraineeStudent.getWorker_id_ext() != null) {	    	
		    	data = clientSegSocial.getDatosSegSocialByTipo(registerTraineeStudent.getWorker_id_ext() + "","worker_id_ext", null, null, null, null);
		    	 if (data != null && !data.isEmpty()){
		    		 updateIdGestora(isUpdate, registerTraineeStudent, data);
		    	 }
		       }
		    	
		    }
		}
	}

	private void updateIdGestora(boolean isUpdate, RegisterTraineeStudent registerTraineeStudent, JsonNode data) {
		for (int i = 0; i < data.size(); i++){
		    JsonNode currentStudent = data.get(i);
		    saveIdGestoraAndCheckUpdate(isUpdate, registerTraineeStudent, currentStudent, i);
		}
	}
    
    

    private static void saveIdGestoraAndCheckUpdate(boolean isUpdate, RegisterTraineeStudent registerTraineeStudent, JsonNode currentStudent, int i) {
        try{
            registerTraineeStudent.setId(Long.parseLong(currentStudent.get("id").asText()));
        }catch(NumberFormatException e){
            registerTraineeStudent.setId(0L);
        }
        //setUpdateError(isUpdate, registerTraineeStudent, currentStudent, i);
    }

    /*private static void setUpdateError(boolean isUpdate, RegisterTraineeStudent registerTraineeStudent, JsonNode currentStudent, int i) {
        if(isUpdate){
            if(currentStudent.get("type").asText().equals("Alta") && currentStudent.get("sign_status").asText().equals("S")){
                registerTraineeStudent.setErrors("No se puede actualizar un alta que ha sido procesada o con fecha de inicio anterior al día actual ");
            }else{
                registerTraineeStudent.setErrors(null);
            }
        }
    } */

    private int updateEnvioFecha(List<RegisterTraineeStudent> registerTraineeStudents, String type) {
    	
    	int envio = 0;
    	
    	for (RegisterTraineeStudent alta : registerTraineeStudents) {	
    		
		        if (alta.getTipoEmpresa().equals("FCT")) {
		
		                Optional<AltasSegSociProg> segSociProg = altaSegSocialProgRepository.findById(alta.getIdAlu());
		
		               if (segSociProg.isPresent() && alta.getIdAlu()!=-1) {
		            	   envio += setAndSaveAltaSegSociProg(alta, segSociProg, type);
		               }
		
		        } else {
		                Optional<AltasSegSociProy> segSociProy = altaSegSocialProyRepository.findById(alta.getIdAlu());
		
		                if (segSociProy.isPresent() && alta.getIdAlu()!=-1) {
		                	envio += setAndSaveAltaSegSociProy(alta, segSociProy, type);
		                }
		        }
    	} 
    	return envio;
    }
    

    private int setAndSaveAltaSegSociProy(RegisterTraineeStudent alta, Optional<AltasSegSociProy> segSociProy, String typeProy) {
    	int envio = 0;
    	boolean esEnviado = false; 
    			
    	if 	(typeProy.equals(INSERT) && (alta.getErrors() == null || 
    			                       alta.getErrors().isEmpty() || 
    			                       alta.getErrors().isBlank())) esEnviado = true;
    	
    	if (typeProy.equals(UPDATE) && alta.getId() != null && alta.getId() != 0L && (alta.getErrors() == null || 
    			                                                                    alta.getErrors().isEmpty() || 
    			                                                                    alta.getErrors().isBlank())) esEnviado = true;
    		
    	if (segSociProy.isPresent()) {
	    	if(esEnviado){
	            segSociProy.get().setLgEnvioEmp(1);
	            segSociProy.get().setFechaEnvio(new Date());
	            if (typeProy.equals(UPDATE)) segSociProy.get().setIdGestora(alta.getId());
	            envio ++;
	        }
	        segSociProy.get().setErrors(alta.getErrors());
	        segSociProy.get().setWarnings(alta.getWarnings());
	        altaSegSocialProyRepository.save(segSociProy.get());
    	}
        return envio;
    }

    private int setAndSaveAltaSegSociProg(RegisterTraineeStudent alta, Optional<AltasSegSociProg> segSociProg, String typeProg) {
        int envio = 0;
    	boolean esEnviado = false; 
		
    	if 	(typeProg.equals(INSERT) && (alta.getErrors() == null || 
    			                       alta.getErrors().isEmpty() || 
    			                       alta.getErrors().isBlank())) esEnviado = true;
    	
    	if (typeProg.equals(UPDATE) && alta.getId() != null && alta.getId() != 0L && (alta.getErrors() == null || 
    			                                                                    alta.getErrors().isEmpty() || 
    			                                                                    alta.getErrors().isBlank())) esEnviado = true;
    	if (segSociProg.isPresent()) {
	    	if(esEnviado){
	          segSociProg.get().setLgEnvioEmp(1);
	          segSociProg.get().setFechaEnvio(new Date());
	          if (typeProg.equals(UPDATE)) segSociProg.get().setIdGestora(alta.getId());
	          envio ++;    			
	        }
	        segSociProg.get().setErrors(alta.getErrors());
	        segSociProg.get().setWarnings(alta.getWarnings());
	        altaSegSocialProgRepository.save(segSociProg.get());
    	}
        return envio;
    }

    @Override
    public List<ListadoHistoricoAltasProjection> getListadoHistoricoAltas(Long idAltass, String tipoEmpresa) {

        List<ListadoHistoricoAltasProjection> listadoHistoricoAltasProjections = null;

        if("FCT".equals(tipoEmpresa)){
            listadoHistoricoAltasProjections = altaSegSocialProgRepository.getListadoHistoricoAltas(idAltass);
        }else {
            listadoHistoricoAltasProjections = altaSegSocialProyRepository.getListadoHistoricoAltas(idAltass);
        }

        return listadoHistoricoAltasProjections;

    }

    @Override
    public void modifyHistoricoAltas(ListadoHistoricoAltasDto listadoHistoricoAltasDto, Long xUsuarioDelphos) {

        if("FCT".equals(listadoHistoricoAltasDto.getTipoEmpresa())){
            updateAltasSegSociProg(listadoHistoricoAltasDto, xUsuarioDelphos);
        }else{
            updateAltasSegSociProy(listadoHistoricoAltasDto, xUsuarioDelphos);
        }

    }

    @Override
    public AltasSegSociProg getAltaSegSociProg(Long id) {
        return altaSegSocialProgRepository.findById(id).orElse(null);
    }

    @Override
    public AltasSegSociProy getAltaSegSociProy(Long id) {
        return altaSegSocialProyRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAlumnoAltas(Long idAltasSS, String tipo) {

        if("FCT".equals(tipo)){
            deleteHistoricoAltasSSProg(idAltasSS);
            altaSegSocialProgRepository.deleteById(idAltasSS);
        }else{
            deleteHistoricoAltasSSProy(idAltasSS);
            altaSegSocialProyRepository.deleteById(idAltasSS);
        }

    }

    @Override
    public void revertEstadoAnulado(Long idAlu, String nuPeticion) {

        HistoricoAltasProg historicoAltasProg = null;
        HistoricoAltasProy historicoAltasProy = null;
        //AltasSegSociProg altasSegSociProg = null;
        //AltasSegSociProy altasSegSociProy = null;

        historicoAltasProg = historicoAltasProgRepository.findByIdAltassProgAndNuPeticion(idAlu, Integer.parseInt(nuPeticion) - 1);

        if(historicoAltasProg != null){
            updateAltaSegSociProgAndHistoricoProg(idAlu, historicoAltasProg);
        }else{
            historicoAltasProy = historicoAltasProyRepository.findByIdAltassProyAndNuPeticion(idAlu, Integer.parseInt(nuPeticion) - 1);
            updateAltaSegSociProyAndHistoricoProy(idAlu, historicoAltasProy);
        }
    }

    private void updateAltaSegSociProyAndHistoricoProy(Long idAlu, HistoricoAltasProy historicoAltasProy) {
        AltasSegSociProy altasSegSociProy;
        if(historicoAltasProy != null){
            altasSegSociProy = altaSegSocialProyRepository.findById(idAlu).orElse(null);
            if(altasSegSociProy != null){
                altasSegSociProy.setFechaInicio(historicoAltasProy.getFechaInicio());
                altasSegSociProy.setFechaFin(historicoAltasProy.getFechaFin());
                altasSegSociProy.setLgEramsusCb(historicoAltasProy.getLgErasumusCb());
                altasSegSociProy.setLgErasmusSb(historicoAltasProy.getLgErasmusSb());
                altasSegSociProy.setLgAnulado(0);
                if (altasSegSociProy.getFechaEnvio()==null){
                    altasSegSociProy.setLgEnvioEmp(1);
                }
                altasSegSociProy.setLgValTut(historicoAltasProy.getLgValTut());
                altasSegSociProy.setLgValCen(historicoAltasProy.getLgValCen());
                altasSegSociProy.setLgValDel(historicoAltasProy.getLgValDel());
                altasSegSociProy.setFechaEnvio(historicoAltasProy.getFechaEnvio());
                altaSegSocialProyRepository.save(altasSegSociProy);
                historicoAltasProyRepository.delete(historicoAltasProy);
            }
        }
    }

    private void updateAltaSegSociProgAndHistoricoProg(Long idAlu, HistoricoAltasProg historicoAltasProg) {
        AltasSegSociProg altasSegSociProg;
        altasSegSociProg = altaSegSocialProgRepository.findById(idAlu).orElse(null);
        if(altasSegSociProg != null){
            altasSegSociProg.setFechaInicio(historicoAltasProg.getFechaInicio());
            altasSegSociProg.setFechaFin(historicoAltasProg.getFechaFin());
            altasSegSociProg.setLgEramsusCb(historicoAltasProg.getLgErasumusCb());
            altasSegSociProg.setLgErasmusSb(historicoAltasProg.getLgErasmusSb());
            altasSegSociProg.setLgAnulado(0);
            if (altasSegSociProg.getFechaEnvio()==null){
                altasSegSociProg.setLgEnvioEmp(1);
            }
            altasSegSociProg.setLgValDel(historicoAltasProg.getLgValDel());
            altasSegSociProg.setLgValCen(historicoAltasProg.getLgValCen());
            altasSegSociProg.setLgValTut(historicoAltasProg.getLgValTut());
            altasSegSociProg.setFechaEnvio(historicoAltasProg.getFechaEnvio());
            altaSegSocialProgRepository.save(altasSegSociProg);
            historicoAltasProgRepository.delete(historicoAltasProg);
        }
    }

    private void deleteHistoricoAltasSSProy(Long idAltasSS) {
        List<HistoricoAltasProy> listHistoricoAltasProy = historicoAltasProyRepository.findByIdAltassProy(idAltasSS);
        if(!listHistoricoAltasProy.isEmpty()){
            for (HistoricoAltasProy historicoAltasProy: listHistoricoAltasProy){
                historicoAltasProyRepository.deleteById(historicoAltasProy.getId());
            }
        }
    }

    private void deleteHistoricoAltasSSProg(Long idAltasSS) {
        List<HistoricoAltasProg> listHistoricoAltasProg = historicoAltasProgRepository.findByIdAltassProg(idAltasSS);
        if(!listHistoricoAltasProg.isEmpty()){
            for (HistoricoAltasProg historicoAltasProg: listHistoricoAltasProg){
                historicoAltasProgRepository.deleteById(historicoAltasProg.getId());
            }
        }
    }

    private void updateAltasSegSociProy(ListadoHistoricoAltasDto listadoHistoricoAltasDto, Long xUsuarioDelphos){
        AltasSegSociProy altasSegSociProy = altaSegSocialProyRepository.findById(listadoHistoricoAltasDto.getIdAltaSS()).orElse(null);
        if(altasSegSociProy != null){
            saveHistoricoAltasProy(altasSegSociProy, xUsuarioDelphos);
            saveAltasSegSociProy(listadoHistoricoAltasDto, altasSegSociProy);
        }
    }

    private void updateAltasSegSociProg(ListadoHistoricoAltasDto listadoHistoricoAltasDto, Long xUsuarioDelphos) {
        AltasSegSociProg altasSegSociProg = altaSegSocialProgRepository.findById(listadoHistoricoAltasDto.getIdAltaSS()).orElse(null);
        if(altasSegSociProg != null){
            saveHistoricoAltaProg(altasSegSociProg, xUsuarioDelphos);
            saveAltasSegSociProg(listadoHistoricoAltasDto, altasSegSociProg);
        }
    }

    private void saveAltasSegSociProy(ListadoHistoricoAltasDto listadoHistoricoAltasDto, AltasSegSociProy altasSegSociProy){
        altasSegSociProy.setFechaInicio(listadoHistoricoAltasDto.getFechaInicio());
        altasSegSociProy.setFechaFin(listadoHistoricoAltasDto.getFechaFin());
        altasSegSociProy.setLgEramsusCb("Si".equals(listadoHistoricoAltasDto.getErasmusCb()) ? 1 : 0);
        altasSegSociProy.setLgErasmusSb("Si".equals(listadoHistoricoAltasDto.getErasmusSb()) ? 1 : 0);
        altasSegSociProy.setLgValTut(0);
        altasSegSociProy.setLgValCen(0);
        altasSegSociProy.setLgValDel(0);
        altasSegSociProy.setFechaEnvio(null);
        altasSegSociProy.setLgEnvioEmp(0);
        altasSegSociProy.setWarnings(listadoHistoricoAltasDto.getDsWarnings());
        altasSegSociProy.setLgAnulado("Si".equals(listadoHistoricoAltasDto.getAnulado()) ? 1 : 0);

        altaSegSocialProyRepository.save(altasSegSociProy);
    }

    private void saveAltasSegSociProg(ListadoHistoricoAltasDto listadoHistoricoAltasDto, AltasSegSociProg altasSegSociProg) {
        altasSegSociProg.setFechaInicio(listadoHistoricoAltasDto.getFechaInicio());
        altasSegSociProg.setFechaFin(listadoHistoricoAltasDto.getFechaFin());
        altasSegSociProg.setLgEramsusCb("Si".equals(listadoHistoricoAltasDto.getErasmusCb()) ? 1 : 0);
        altasSegSociProg.setLgErasmusSb("Si".equals(listadoHistoricoAltasDto.getErasmusSb()) ? 1 : 0);
        altasSegSociProg.setLgValTut(0);
        altasSegSociProg.setLgValCen(0);
        altasSegSociProg.setLgValDel(0);
        altasSegSociProg.setFechaEnvio(null);
        altasSegSociProg.setLgEnvioEmp(0);
        altasSegSociProg.setWarnings(listadoHistoricoAltasDto.getDsWarnings());
        altasSegSociProg.setLgAnulado("Si".equals(listadoHistoricoAltasDto.getAnulado()) ? 1 : 0);

        altaSegSocialProgRepository.save(altasSegSociProg);
    }

    private void saveHistoricoAltasProy(AltasSegSociProy altasSegSociProy, Long xUsuarioDelphos){
        List<HistoricoAltasProy> listadoHistoricoAltasProy = historicoAltasProyRepository.findByIdAltassProy(altasSegSociProy.getId());
        HistoricoAltasProy historicoAltasProy = new HistoricoAltasProy();        
        historicoAltasProy.setIdAltassProy(altasSegSociProy.getId());
        historicoAltasProy.setFechaInicio(altasSegSociProy.getFechaInicio());
        historicoAltasProy.setFechaFin(altasSegSociProy.getFechaFin());
        historicoAltasProy.setLgErasumusCb(altasSegSociProy.getLgEramsusCb());
        historicoAltasProy.setLgErasmusSb(altasSegSociProy.getLgErasmusSb());
        historicoAltasProy.setLgValTut(altasSegSociProy.getLgValTut());
        historicoAltasProy.setLgValCen(altasSegSociProy.getLgValCen());
        historicoAltasProy.setLgValDel(altasSegSociProy.getLgValDel());
        historicoAltasProy.setFechaEnvio(altasSegSociProy.getFechaEnvio());
        historicoAltasProy.setLgAnulado(altasSegSociProy.getLgAnulado());
        historicoAltasProy.setDsWarnings(altasSegSociProy.getWarnings());
        historicoAltasProy.setXusuarioUlt(xUsuarioDelphos);
        historicoAltasProy.setIdGestora(altasSegSociProy.getIdGestora());;
        Date fechaActual = new Date();
        historicoAltasProy.setFechaAuditoria(fechaActual);
        if(listadoHistoricoAltasProy == null){
            historicoAltasProy.setNuPeticion(1);
        }else{
            historicoAltasProy.setNuPeticion(listadoHistoricoAltasProy.size() + 1);
        }
        historicoAltasProyRepository.save(historicoAltasProy);
    }

    private void saveHistoricoAltaProg(AltasSegSociProg altasSegSociProg, Long xUsuarioDelphos) {
        List<HistoricoAltasProg> listadoHistoricoAltasProg = historicoAltasProgRepository.findByIdAltassProg(altasSegSociProg.getId());
        HistoricoAltasProg historicoAltasProg = new HistoricoAltasProg();
        historicoAltasProg.setIdAltassProg(altasSegSociProg.getId());
        historicoAltasProg.setFechaInicio(altasSegSociProg.getFechaInicio());
        historicoAltasProg.setFechaFin(altasSegSociProg.getFechaFin());
        historicoAltasProg.setLgErasumusCb(altasSegSociProg.getLgEramsusCb());
        historicoAltasProg.setLgErasmusSb(altasSegSociProg.getLgErasmusSb());
        historicoAltasProg.setLgValTut(altasSegSociProg.getLgValTut());
        historicoAltasProg.setLgValCen(altasSegSociProg.getLgValCen());
        historicoAltasProg.setLgValDel(altasSegSociProg.getLgValDel());
        historicoAltasProg.setFechaEnvio(altasSegSociProg.getFechaEnvio());
        historicoAltasProg.setLgAnulado(altasSegSociProg.getLgAnulado());
        historicoAltasProg.setDsWarnings(altasSegSociProg.getWarnings());
        historicoAltasProg.setXusuarioUlt(xUsuarioDelphos);
        historicoAltasProg.setIdGestora(altasSegSociProg.getIdGestora());
        Date fechaActual = new Date();
        historicoAltasProg.setFechaAuditoria(fechaActual);
        if(listadoHistoricoAltasProg == null){
            historicoAltasProg.setNuPeticion(1);
        }else{
            historicoAltasProg.setNuPeticion(listadoHistoricoAltasProg.size() + 1);
        }

        historicoAltasProgRepository.save(historicoAltasProg);
    }

	@Override
	public int getConsistenciaGestora(String tipo, Long id, Date fechaInicio, Date fechaFin) {
		
		if (fechaInicio == null || fechaFin == null) {
			return 0;
		}
		
		if (tipo.equals("FCT")) return altaSegSocialProgRepository.getConsistenciaGestora(id,fechaInicio,fechaFin);
		
		else return altaSegSocialProyRepository.getConsistenciaGestora(id,fechaInicio,fechaFin);
		
		
	}

	@Override
	public List<AltasSegSociProg> getAltaSegSociProgByMatricula(Long idMatricula) {
		return altaSegSocialProgRepository.getListForMatricula(idMatricula);
	}

	@Override
	public List<AltasSegSociProy> getAltaSegSociProyByMatricula(Long idMatricula) {		
		return altaSegSocialProyRepository.getListForMatricula(idMatricula);
	}

	@Override
	public void saveProg(AltasSegSociProg alta) {
		altaSegSocialProgRepository.save(alta);
		
	}

	@Override
	public void saveProy(AltasSegSociProy alta) {
		altaSegSocialProyRepository.save(alta);		
	}

    public RegisterTraineeStudent compareDataGestora(RegisterTraineeStudent newDataStudent, String tipo)
    { 	
    	if (newDataStudent.getTipoEmpresa().equals(FP_DUAL)) 
    	{
    		
    		return compareDataGestoraDUAL(newDataStudent, tipo);
    		
    	} else {
    		
    		return compareDataGestoraFCT(newDataStudent, tipo);
        }    	
    }

	private RegisterTraineeStudent compareDataGestoraDUAL(RegisterTraineeStudent newDataStudent, String tipo) {
		List<HistoricoAltasProy> listadoHistoricoDUAL = historicoAltasProyRepository.findByIdAltassProy(newDataStudent.getIdAlu());
		HistoricoAltasProy oldDataStudentDual = listadoHistoricoDUAL.get(listadoHistoricoDUAL.size()-1);
		RegisterTraineeStudent studentModifiedDual = new RegisterTraineeStudent();

		studentModifiedDual.setId(newDataStudent.getId());
		studentModifiedDual.setTipoEmpresa(newDataStudent.getTipoEmpresa());
		studentModifiedDual.setIdAlu(newDataStudent.getIdAlu());
		

		if(tipo.equals("delete")){
			if (!Objects.equals(oldDataStudentDual.getLgAnulado(), newDataStudent.getCancel_registration())) {
				studentModifiedDual.setCancel_registration(newDataStudent.getCancel_registration());
		    }
		    
		} else {
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    LocalDate fechaInicioOldDual = oldDataStudentDual.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    LocalDate fechaInicioNewDual = LocalDate.parse(newDataStudent.getReal_register_date(), dtf);

		    LocalDate fechaFinOldDual = oldDataStudentDual.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    LocalDate fechaFinNewDual = LocalDate.parse(newDataStudent.getEndDateCompletion(), dtf);
		    
		    if (!fechaInicioOldDual.equals(fechaInicioNewDual)) {
		    	studentModifiedDual.setReal_register_date(newDataStudent.getReal_register_date());
		    }
		    if (!fechaFinOldDual.equals(fechaFinNewDual)) {
		    	studentModifiedDual.setEndDateCompletion(newDataStudent.getEndDateCompletion());
		    }
		    //if (oldDataStudent.getLgErasumusCb() != newDataStudent.getErasmusfpdualScholarship()) {
		    //    studentModified.setErasmusfpdualScholarship(newDataStudent.getErasmusfpdualScholarship());
		    //}
		}
		
		return studentModifiedDual;
	}
	
	private RegisterTraineeStudent compareDataGestoraFCT(RegisterTraineeStudent newDataStudent, String tipo) {
		List<HistoricoAltasProg> listadoHistorico = historicoAltasProgRepository.findByIdAltassProg(newDataStudent.getIdAlu());
		HistoricoAltasProg oldDataStudent = listadoHistorico.get(listadoHistorico.size()-1);
		RegisterTraineeStudent studentModified = new RegisterTraineeStudent();

		studentModified.setId(newDataStudent.getId());
		studentModified.setTipoEmpresa(newDataStudent.getTipoEmpresa());
		studentModified.setIdAlu(newDataStudent.getIdAlu());
		

		if(tipo.equals("delete")){
			if (!Objects.equals(oldDataStudent.getLgAnulado(), newDataStudent.getCancel_registration())) {
		        studentModified.setCancel_registration(newDataStudent.getCancel_registration());
		    }
		    
		} else {
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    LocalDate fechaInicioOld = oldDataStudent.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    LocalDate fechaInicioNew = LocalDate.parse(newDataStudent.getReal_register_date(), dtf);

		    LocalDate fechaFinOld = oldDataStudent.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    LocalDate fechaFinNew = LocalDate.parse(newDataStudent.getEndDateCompletion(), dtf);
		    
		    if (!fechaInicioOld.equals(fechaInicioNew)) {
		        studentModified.setReal_register_date(newDataStudent.getReal_register_date());
		    }
		    if (!fechaFinOld.equals(fechaFinNew)) {
		        studentModified.setEndDateCompletion(newDataStudent.getEndDateCompletion());
		    }
		    //if (oldDataStudent.getLgErasumusCb() != newDataStudent.getErasmusfpdualScholarship()) {
		    //    studentModified.setErasmusfpdualScholarship(newDataStudent.getErasmusfpdualScholarship());
		    //}
		}
		
		return studentModified;
	}

	@Override
	public void nuevoPeriodoAltas(NuevoPeriodoDto nuevoPeriodo, Long xUsuarioDelphos) {
		if("FCT".equals(nuevoPeriodo.getTipoEmpresa()))
		{		
			nuevoPeriodoAltasFCT(nuevoPeriodo,xUsuarioDelphos);
		} else {
			nuevoPeriodoAltasDUAL(nuevoPeriodo,xUsuarioDelphos);
			
		}
		
	}

    @Override
    public void excluirAlumno(Long idConvProxAlu, String tipoEmpresa) {
        //encuentro el alumno
        if("FCT".equals(tipoEmpresa)){
            excluirAlumnoPrograma(idConvProxAlu);
        } else {
            excluirAlumnoProyecto(idConvProxAlu);
        }
    }

    private void excluirAlumnoPrograma(Long idConvprogAlu) {
        List<AlumnoPrograma> alumnosPrograma = alumnoProgramaRepository.findByIdMatricula(idConvprogAlu);

        if(!alumnosPrograma.isEmpty()){
            for(AlumnoPrograma alumnoPrograma: alumnosPrograma){
                alumnoPrograma.setLgExcluir(1);
                alumnoProgramaRepository.save(alumnoPrograma);
            }
        }
    }

    private void excluirAlumnoProyecto(Long idConvproyAlu) {
        List<ConveniosProyectoAlumno> conveniosProyectoAlumno = convProyAlumnoRepository.findByIdMatricula(idConvproyAlu);

        if(!conveniosProyectoAlumno.isEmpty()){
            for(ConveniosProyectoAlumno convenioProyectoAlumno: conveniosProyectoAlumno){
                convenioProyectoAlumno.setLgExcluir(1);
                convProyAlumnoRepository.save(convenioProyectoAlumno);
            }
        }
    }

    public void nuevoPeriodoAltasFCT(NuevoPeriodoDto nuevoPeriodo, Long xUsuarioDelphos ) {
		AltasSegSociProg altasSegSociProg = altaSegSocialProgRepository.findById(nuevoPeriodo.getIdAltaSS())
		        .orElseThrow(() -> new IllegalArgumentException("AltasSegSociProg not found for id: " + nuevoPeriodo.getIdAltaSS()));		
		AltasSegSociProg newPeriodo = new AltasSegSociProg(); 
		newPeriodo.setIdConvProgAlu(altasSegSociProg.getIdConvProgAlu());
		newPeriodo.setXMatricula(altasSegSociProg.getXMatricula());		
		newPeriodo.setFechaInicio(nuevoPeriodo.getFechaInicio());
		newPeriodo.setFechaFin(nuevoPeriodo.getFechaFin());
		newPeriodo.setLgEramsusCb(altasSegSociProg.getLgEramsusCb());
		newPeriodo.setLgErasmusSb(altasSegSociProg.getLgErasmusSb());
		newPeriodo.setLgValTut(0);
		newPeriodo.setLgValCen(0);
		newPeriodo.setLgValDel(0);
		newPeriodo.setXusuarioUlt(xUsuarioDelphos);
		newPeriodo.setLgEnvioEmp(0);
		newPeriodo.setFechaEnvio(null);
		newPeriodo.setLgAnulado(0);
		altaSegSocialProgRepository.save(newPeriodo);
		
	}
	
	public void nuevoPeriodoAltasDUAL(NuevoPeriodoDto nuevoPeriodo, Long xUsuarioDelphos) {
		AltasSegSociProy altasSegSociProy = altaSegSocialProyRepository.findById(nuevoPeriodo.getIdAltaSS())
		        .orElseThrow(() -> new IllegalArgumentException("AltasSegSociProy not found for id: " + nuevoPeriodo.getIdAltaSS()));		
		AltasSegSociProy newPeriodo = new AltasSegSociProy(); 
		newPeriodo.setIdConvProyAlu(altasSegSociProy.getIdConvProyAlu());
		newPeriodo.setXMatricula(altasSegSociProy.getXMatricula());		
		newPeriodo.setFechaInicio(nuevoPeriodo.getFechaInicio());
		newPeriodo.setFechaFin(nuevoPeriodo.getFechaFin());
		newPeriodo.setLgEramsusCb(altasSegSociProy.getLgEramsusCb());
		newPeriodo.setLgErasmusSb(altasSegSociProy.getLgErasmusSb());
		newPeriodo.setLgValTut(0);
		newPeriodo.setLgValCen(0);
		newPeriodo.setLgValDel(0);
		newPeriodo.setXusuarioUlt(xUsuarioDelphos);
		newPeriodo.setLgEnvioEmp(0);
		newPeriodo.setFechaEnvio(null);
		newPeriodo.setLgAnulado(0);
		altaSegSocialProyRepository.save(newPeriodo);
		
	}
	
    	
    	
    	
    	
        

}
