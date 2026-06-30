package es.jccm.edu.proyectosfct.application.services.alumnado;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.AlumnadoTutorLofpDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programas.ParteSemanalAnexosProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ParteSemanalAnexosProyectoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesprog.SegSocialCotizaMesProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesproy.SegSocialCotizaMesProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialprog.AltaSegSocialProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialproy.AltaSegSocialProyRepository;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ParteSemanalAnexosPrograma;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ParteSemanalAnexosProyecto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.EvaAluActProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.EvaAluActProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemActProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemActProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemAluProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemAluProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.AlumnoProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConvProyAlumnoRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltasBajas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoNSS;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AptoEvaluacionAlumno;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ComunicacionDiasPracticas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.DatosActividades;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.DatosEvaluaciones;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.DatosHojaSemanal;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.FechaSemana;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ListadoAlumnadoTutor;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.AlumnadoAltasBajasProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.AlumnadoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.AlumnadoNSSProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.AptoEvaluacionAlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.ComunicacionDiasPracticasProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosActividadesProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosCabeceraEvaluacionProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosEvaluacionesProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosHojaSemanalProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.FechaSemanaProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.ListadoAlumnadoTutorProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;
import es.jccm.edu.proyectosfct.application.ports.in.alumnado.IAlumnadoService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Service
public class AlumnadoService implements IAlumnadoService {
	
	private static final Long ID_PERFIL_GESTOR = 11207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
	private static final Long ID_PERFIL_ALUMNADO = 5000L;
	
	@Autowired
	private ParsemAluProgRepository parsemAluProgRepository;
	
	@Autowired
	private ParsemActProgRepository parsemActProgRepository;
	
	@Autowired
	private ParsemAluProyRepository parsemAluProyRepository;
	
	@Autowired
	private ParsemActProyRepository parsemActProyRepository;
	
	@Autowired
	private EvaAluActProgRepository evaAluActProgRepository;
	
	@Autowired
	private EvaAluActProyRepository evaAluActProyRepository;
	
	@Autowired
	private AlumnadoRepository alumnadoRepository;
	
	@Autowired
	private AlumnoProgramaRepository alumnoProgramaRepository;
	
	@Autowired
	private ConvProyAlumnoRepository convenioProyectoAlumnoRepository;
	
	@Autowired
	private ConveniosFctRepository conveniosFctRepository;

	@Autowired
	private ParteSemanalAnexosProgramaRepository parteSemanalAnexosProgramaRepository;

	@Autowired
	private ParteSemanalAnexosProyectoRepository parteSemanalAnexosProyectoRepository;

	@Autowired
	private SegSocialCotizaMesProyRepository segSocialCotizaMesProyRepository;

	@Autowired
	private SegSocialCotizaMesProgRepository segSocialCotizaMesProgRepository;

	@Autowired
	private AltaSegSocialProgRepository altaSegSocialProgRepository;

	@Autowired
	private AltaSegSocialProyRepository altaSegSocialProyRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
    @Autowired
    private IRodalClient rodalClient;
	
	private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
	private SimpleDateFormat formatoSemana = new SimpleDateFormat("dd/MM/yyyy"); 
	static final String FECHASEMANA = "Fecha Semana";  
	static final String PARSEMACTPROG = "ParsemActProg";
	
	@Override
	public ParsemAluProg createParsemAluProg(ParsemAluProg parsemAluProgIn, String fechaIni, Integer nuDias) {
		
		ParsemAluProg parsemAluProgOut = new ParsemAluProg();		
		
		try {
			//Date FIni = formato.parse(fechaIni);
			Date FIni = formatoSemana.parse(fechaIni.replace("-","/"));
			Optional<ParsemAluProg> parsemAluProg = parsemAluProgRepository.findByAluConvProgIdAndFechaInicioSemana(parsemAluProgIn.getAluConvProg().getId(),FIni);
			
			if(parsemAluProgIn.getId() != null && parsemAluProgIn.getId() != 0 && parsemAluProg.isPresent()) {				
								
				Optional<ParsemAluProg> parsemAluProgUpdate = parsemAluProgRepository.findById(parsemAluProgIn.getId());
				
				if(parsemAluProgUpdate.isPresent()) {
					Optional<AlumnoPrograma> aluConvProg = alumnoProgramaRepository.getAlumnoProgramaById(parsemAluProgIn.getAluConvProg().getId());
					
					if(!aluConvProg.isPresent()) {
						String mes = crearMessage(AlumnoPrograma.class.getSimpleName(), "AlumnoPrograma");
						throw new NotFoundException(mes);
					}
					
					parsemAluProgUpdate.get().setAluConvProg(aluConvProg.get());
					parsemAluProgUpdate.get().setFechaInicioSemana(parsemAluProgIn.getFechaInicioSemana());
					parsemAluProgUpdate.get().setObservaciones(parsemAluProgIn.getObservaciones());
					parsemAluProgUpdate.get().setNu_dias(nuDias);
					
					parsemAluProgOut = parsemAluProgRepository.save(parsemAluProgUpdate.get());
				}
			}else {
				Optional<AlumnoPrograma> aluConvProg = alumnoProgramaRepository.getAlumnoProgramaById(parsemAluProgIn.getAluConvProg().getId());
				
				if(aluConvProg.isPresent()) {
					parsemAluProgIn.setAluConvProg(aluConvProg.get());
					parsemAluProgIn.setFechaInicioSemana(FIni);
					parsemAluProgIn.setObservaciones(parsemAluProgIn.getObservaciones());
					parsemAluProgIn.setNu_dias(nuDias);
				}
					
				
				parsemAluProgOut = parsemAluProgRepository.save(parsemAluProgIn);
			}
		}  catch (ParseException e) {

			e.printStackTrace();
		} 	
		
		return parsemAluProgOut;
	
	}
	
	
	
	@Override
	public List<ParsemActProg> createParsemActProg(List<ParsemActProg> parsemActProgListIn, Long idParsemAluProg) {
		
		deleteParsemActProg(idParsemAluProg);
		
		if (parsemActProgListIn !=null && !parsemActProgListIn.isEmpty()) {		
			
			for (ParsemActProg parsemActProg : parsemActProgListIn) {
				
				if (parsemActProg.getParsemAluProg().getId() != null) {
					
					ParsemActProg parsemActProgUpdate = new ParsemActProg();
					
					Optional<ParsemAluProg> parsemAluProg = parsemAluProgRepository.findById(parsemActProg.getParsemAluProg().getId());
					
					parsemActProgUpdate.setParsemAluProg(parsemAluProg.isPresent()?parsemAluProg.get():null);
					parsemActProgUpdate.setDatosPrograma(parsemActProg.getDatosPrograma());
						
					parsemActProgRepository.save(parsemActProgUpdate);
				}
	        }	
		}
		
		return parsemActProgListIn;		
	}
	
//	@Transactional
	public void deleteParsemActProg(Long idParsemAluProg) {
		List<ParsemActProg> listParsemAct = parsemActProgRepository.findAllByParsemAluProgId(idParsemAluProg);
		
		for (ParsemActProg parsemActProg : listParsemAct) {
			parsemActProgRepository.deleteById(parsemActProg.getId());
		}
	}
	
	@Override
	public ParsemAluProg getParsemAluProgByIdConvProgAlu(Long idConvProgAlu,String fechaIni) {		
		
		
	try {		
			Date FIni = formato.parse(fechaIni);	
			
			Optional<ParsemAluProg> parsemAluProg = parsemAluProgRepository.findByAluConvProgIdAndFechaInicioSemana(idConvProgAlu,FIni);
			
			if (parsemAluProg.isPresent()) {
				return parsemAluProg.get();
			} 
			
			return new ParsemAluProg(); 
			
			
	}  catch (ParseException e) {

			e.printStackTrace();
		} 	

		return null;
	}
	
	@Override
	public ParsemAluProg getParsemAluProgByIdParsemAluProg(Long idParsemAluProg) {
		
		Optional<ParsemAluProg> parsemAluProg = parsemAluProgRepository.findById(idParsemAluProg);
		if (!parsemAluProg.isPresent()) {
			String mensaje = crearMessage(ParsemAluProg.class.getSimpleName(), "ParsemAluProg");
			throw new NotFoundException(mensaje);
		}

		return parsemAluProg.get();
	}
	
	@Override
	public List<ParsemActProg> getAllParsemActProg(Long idParsemAluProg) {
		List<ParsemActProg> parsemActProgList = parsemActProgRepository.findAllByParsemAluProgId(idParsemAluProg);
		//if (parsemActProgList == null || parsemActProgList.isEmpty()) {
		//	String mes = crearMessage(ParsemActProg.class.getSimpleName(), PARSEMACTPROG);
		//	throw new NotFoundException(mes);
		//}
		return parsemActProgList;
	}
	
	@Override
	public List<FechaSemana> getFechasSemana(Long idConvProgAlu) {
		List<FechaSemanaProjection> fechasSemanalList = alumnadoRepository.findFechasSemanal(idConvProgAlu);
		if (fechasSemanalList == null || fechasSemanalList.isEmpty()) {
			String mes = crearMessage(FechaSemana.class.getSimpleName(), FECHASEMANA);
			throw new NotFoundException(mes);
		}
		return fechasSemanalList.stream().map(entity -> modelMapper.map(entity, FechaSemana.class)).collect(Collectors.toList());
	}
	
	@Override
	public Alumno getAlumno(Long idMatricula) {
		AlumnoProjection alumnoProjection = alumnadoRepository.findAlumnoByIdMatricula(idMatricula); 
		
		return modelMapper.map(alumnoProjection, Alumno.class);
	}
	
	
	
	
	
	
	
	
	/*--------------------------------------------------------------------------**/
	
	
	
	
	
	
	
	@Override
	public ParsemAluProy createParsemAluProy(ParsemAluProy parsemAluProyIn, String fechaIni, Integer nuDias) {
		
		
		ParsemAluProy parsemAluProyOut = new ParsemAluProy();
	
		try {
		
			Date FIni = formato.parse(fechaIni);
			
			Optional<ParsemAluProy> parsemAluProy = parsemAluProyRepository.findByAluConvProyIdAndFechaInicioSemana(parsemAluProyIn.getAluConvProy().getId(),FIni);
			
			if(parsemAluProyIn.getId() != null && parsemAluProyIn.getId() != 0 && parsemAluProy.isPresent()) {
				
				Optional<ParsemAluProy> parsemAluProyUpdate = parsemAluProyRepository.findById(parsemAluProyIn.getId());
				if(parsemAluProyUpdate.isPresent()) {
					Optional<ConveniosProyectoAlumno> aluConvProy = convenioProyectoAlumnoRepository.findById(parsemAluProyIn.getAluConvProy().getId());
					
					if(!aluConvProy.isPresent()) {
						String mes = crearMessage(AlumnoPrograma.class.getSimpleName(), "AlumnoPrograma");
						throw new NotFoundException(mes);
					}
					
					parsemAluProyUpdate.get().setAluConvProy(aluConvProy.get());
					parsemAluProyUpdate.get().setFechaInicioSemana(parsemAluProyIn.getFechaInicioSemana());
					parsemAluProyUpdate.get().setObservaciones(parsemAluProyIn.getObservaciones());
					parsemAluProyUpdate.get().setNu_dias(nuDias);
					
					parsemAluProyOut = parsemAluProyRepository.save(parsemAluProyUpdate.get());
				}
				
			}else {				
				
				Optional<ConveniosProyectoAlumno> aluConvProy = convenioProyectoAlumnoRepository.findById(parsemAluProyIn.getAluConvProy().getId());
				
				if(aluConvProy.isPresent()) {
					parsemAluProyIn.setAluConvProy(aluConvProy.get());
					parsemAluProyIn.setFechaInicioSemana(FIni);
					parsemAluProyIn.setObservaciones(parsemAluProyIn.getObservaciones());
					parsemAluProyIn.setNu_dias(nuDias);
				}
				
				parsemAluProyOut = parsemAluProyRepository.save(parsemAluProyIn);
			}
			
		} catch (ParseException e) {

			e.printStackTrace();
		} 		
			
		return parsemAluProyOut;
			
			
	}
	
	@Override
	public List<ParsemActProy> createParsemActProy(List<ParsemActProy> parsemActProyListIn, Long idParsemAluProy) {
		
		deleteParsemActProy(idParsemAluProy);
		
		if (parsemActProyListIn !=null && !parsemActProyListIn.isEmpty()) {		
			
			for (ParsemActProy parsemActProy : parsemActProyListIn) {
				
				if (parsemActProy.getParsemAluProy().getId() != null) {
					
					ParsemActProy parsemActProyUpdate = new ParsemActProy();
					
					Optional<ParsemAluProy> parsemAluProy = parsemAluProyRepository.findById(parsemActProy.getParsemAluProy().getId());
					
					parsemActProyUpdate.setParsemAluProy(parsemAluProy.isPresent()?parsemAluProy.get():null);
					parsemActProyUpdate.setDatosProyecto(parsemActProy.getDatosProyecto());
					
					parsemActProyRepository.save(parsemActProyUpdate);
				}
	        }	
		}
		
		return parsemActProyListIn;		
	}
	
//	@Transactional
	public void deleteParsemActProy(Long idParsemAluProy) {
		List<ParsemActProy> listParsemAct = parsemActProyRepository.findAllByParsemAluProyId(idParsemAluProy);
		
		for (ParsemActProy parsemActProy : listParsemAct) {
			parsemActProyRepository.deleteById(parsemActProy.getId());
		}
	}
	
	@Override
	public ParsemAluProy getParsemAluProyByIdConvProyAlu(Long idConvProyAlu,String fechaIni) {
		
	 ParsemAluProy parseAlu = new ParsemAluProy();	
	 try {		
			Date FIni = formato.parse(fechaIni);
			
			Optional<ParsemAluProy> parsemAluProy = parsemAluProyRepository.findByAluConvProyIdAndFechaInicioSemana(idConvProyAlu,FIni);	
			
			if (parsemAluProy.isPresent()) {
				parseAlu =  parsemAluProy.get();
			}			
			
	    }  catch (ParseException e) {

			e.printStackTrace();
		} 	

		return parseAlu;
	  }
	
	@Override
	public ParsemAluProy getParsemAluProyByIdParsemAluProy(Long idParsemAluProy) {
		
		Optional<ParsemAluProy> parsemAluProy = parsemAluProyRepository.findById(idParsemAluProy);
		if (!parsemAluProy.isPresent()) {
			String mensaje = crearMessage(ParsemAluProg.class.getSimpleName(), "ParsemAluProy");
			throw new NotFoundException(mensaje);
		}

		return parsemAluProy.get();
	}
	
	@Override
	public List<ParsemActProy> getAllParsemActProy(Long idParsemAluProy) {
		List<ParsemActProy> parsemActProyList = parsemActProyRepository.findAllByParsemAluProyId(idParsemAluProy);
		//if (parsemActProyList == null || parsemActProyList.isEmpty()) {
		//	String mes = crearMessage(ParsemActProg.class.getSimpleName(), PARSEMACTPROG);
		//	throw new NotFoundException(mes);
		//}
		return parsemActProyList;
	}
	
	@Override
	public List<FechaSemana> getFechasSemanaProy(Long idConvProyAlu) {
		List<FechaSemanaProjection> fechasSemanalList = alumnadoRepository.findFechasSemanalProy(idConvProyAlu);
		if (fechasSemanalList == null || fechasSemanalList.isEmpty()) {
			String mes = crearMessage(FechaSemana.class.getSimpleName(), FECHASEMANA);
			throw new NotFoundException(mes);
		}
		return fechasSemanalList.stream().map(entity -> modelMapper.map(entity, FechaSemana.class)).collect(Collectors.toList());
	}	
	
	@Override
	public List<ListadoAlumnadoTutor> getListadoAlumnosTutor(Long idTutorfctdual, 
														     Long idCentro, 
														     Integer cAnno, 
														     Integer tipoEmpresa, 
														     Long idEmpresa, 
														     Long idOfertamatrig, 
														     Long idUnidad,
														     Long idPerfil,
															 Long idCentroCombo,
															 Long idProvincia,
															 Long idUsuario,
															 Long idEmpleadoComunica) throws org.apache.tomcat.util.json.ParseException {
		
		List<ListadoAlumnadoTutorProjection> listadoAlumnosTutor = null;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
		  if (ID_PERFIL_GESTOR.equals(idPerfil)) {
			  
				listadoAlumnosTutor = alumnadoRepository.findListadoAlumnosTutorDelegacion(idTutorfctdual, 
																					       idCentro, 
																					       cAnno, 
																					       tipoEmpresa, 
																					       idEmpresa, 
																					       idOfertamatrig, 
																					       idUnidad,
																					       idPerfil,
																					       idCentroCombo,
																					       idUsuario);		  
		  } else {
			  listadoAlumnosTutor = alumnadoRepository.findListadoAlumnosTutorDelegacionProvincias(idTutorfctdual, 
																							       idCentro, 
																							       cAnno, 
																							       tipoEmpresa, 
																							       idEmpresa, 
																							       idOfertamatrig, 
																							       idUnidad,																							  
																							       idCentroCombo,
																							       idProvincia);
			  
		  }

			
		} else {
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
			
				listadoAlumnosTutor = alumnadoRepository.findListadoAlumnosTutorAlumnado(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idOfertamatrig, idUnidad,idEmpleadoComunica);

				//En el caso de acceder por el perfil alumno, no se muestra ningún aviso por lo que sale del método directamente
				return listadoAlumnosTutor.stream().map(entity -> modelMapper.map(entity, ListadoAlumnadoTutor.class)).collect(Collectors.toList());

			} else {
				
				listadoAlumnosTutor = alumnadoRepository.findListadoAlumnosTutor(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idOfertamatrig, idUnidad, idUsuario);

			}
			
		}

		//Se revisa si al alumno se le muestran o no avisos por falta de cotizaciones mensuales en el mes anterior
		return gestionarMensajeAviso(listadoAlumnosTutor, cAnno);

	}
	
	@Override
	public List<CursoModalidad> getCursosEmpleadoCentro(Long idTutorfctdual, 
													    Long idCentro, 
													    Integer cAnno, 
													    Integer tipoEmpresa, 
													    Long idEmpresa,
													    Long idPerfil,
													    Long idCentroCombo,
													    Long idProvincia,
													    Integer sTodos,
													    Long idUsuario,
													    Long idEmpleadoComunica
													    ) throws org.apache.tomcat.util.json.ParseException {
		
		List<CursoModalidadProjection> cursosProjection = null;		
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
			if (ID_PERFIL_GESTOR.equals(idPerfil)) {
				
				cursosProjection = alumnadoRepository.findCursosEmpleadoDelegacion(idTutorfctdual, 
																				   idCentro, 
																				   cAnno, 
																				   tipoEmpresa, 
																				   idEmpresa,
																				   idPerfil,
																				   idCentroCombo,
																				   idUsuario,
																				   sTodos);	
				
			} else {
				
				cursosProjection = alumnadoRepository.findCursosEmpleadoDelegacionProvincias(idTutorfctdual, 
																							 idCentro, 
																							 cAnno, 
																							 tipoEmpresa, 
																							 idEmpresa,						   
																							 idCentroCombo,
																							 idProvincia,
																							 sTodos);			
			}
			
		} else {
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
				
				cursosProjection = alumnadoRepository.findCursosEmpleadoCentroAlumnado(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa,idEmpleadoComunica,sTodos);
				
			} else {
				
				cursosProjection = alumnadoRepository.findCursosEmpleadoCentro(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idUsuario,sTodos);
				
			}
			
		}
		
		return cursosProjection.stream().map(entity -> modelMapper.map(entity, CursoModalidad.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ElementoSelect> getTutoresAlumnado(Long idTutorfctdual, 
													    Long idCentro, 
													    Integer cAnno, 
													    Integer tipoEmpresa, 
													    Long idEmpresa,
													    Long idPerfil,
													    Long idCentroCombo,
													    Long idProvincia,
													    Integer sTodos,
													    Long idUsuario,
													    Long idEmpleadoComunica) throws org.apache.tomcat.util.json.ParseException {
		
		List<ElementoSelectProjection> tutoresProjection = null;		
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
			if (ID_PERFIL_GESTOR.equals(idPerfil)) {
				
				tutoresProjection = alumnadoRepository.findTutoresDelegacion(idCentro, 
																				   cAnno, 
																				   tipoEmpresa, 
																				   idEmpresa,
																				   idPerfil,
																				   idCentroCombo,
																				   idUsuario,
																				   sTodos);	
				
			} else {
				
				tutoresProjection = alumnadoRepository.findTutoresDelegacionProvincias(idCentro, 
																					   cAnno, 
																					   tipoEmpresa, 
																					   idEmpresa,						   
																					   idCentroCombo,
																					   idProvincia,
																					   sTodos);			
			}
			
		} else {
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
				
				tutoresProjection = alumnadoRepository.findTutoresCentroAlumnado(idCentro, cAnno, tipoEmpresa, idEmpresa, idEmpleadoComunica, sTodos);
				
			} else {
				
				tutoresProjection = alumnadoRepository.findTutoresCentro(idCentro, cAnno, tipoEmpresa, idEmpresa, sTodos);
				
			}
			
			
			
		}
		
		return tutoresProjection.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<UnidadCurso> getUnidadesEmpleadoCentro(Long idTutorfctdual, 
													   Long idCentro, 
													   Integer cAnno, 
													   Integer tipoEmpresa, 
													   Long idEmpresa, 
													   Long idOfertamatrig,
													   Long idPerfil,
													   Long idCentroCombo,
													   Long idProvincia,
													   Long idUsuario,
													   Long idEmpleadoComunica) throws org.apache.tomcat.util.json.ParseException {
		
		List<UnidadCursoProjection> unidadesProjection = null ;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
			if (ID_PERFIL_GESTOR.equals(idPerfil)) {	
				
				unidadesProjection = alumnadoRepository.findUnidadesEmpleadoDelegacion(idTutorfctdual, 
																					   idCentro, 
																					   cAnno, 
																					   tipoEmpresa, 
																					   idEmpresa, 
																					   idOfertamatrig,
																					   idPerfil,
																					   idCentroCombo,
																					   idUsuario);
				
			} else {
				
				unidadesProjection = alumnadoRepository.findUnidadesEmpleadoDelegacionProvincias(idTutorfctdual, 
																							     idCentro, 
																							     cAnno, 
																							     tipoEmpresa, 
																							     idEmpresa, 
																							     idOfertamatrig,																							  
																							     idCentroCombo,
																							     idProvincia);			
				
			}
			

			
		} else {
		
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
				
				unidadesProjection = alumnadoRepository.findUnidadesEmpleadoCentroAlumnado(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idOfertamatrig, idEmpleadoComunica);
				
			} else {
				
				unidadesProjection = alumnadoRepository.findUnidadesEmpleadoCentro(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idOfertamatrig, idUsuario);
				
			}
		}
		
		

		return unidadesProjection.stream().map(entity -> modelMapper.map(entity, UnidadCurso.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<EvaAluActProg> getAllEvaAluActProg(Long idConvProgAlu) {
		return evaAluActProgRepository.findAllByAluConvProgIdOrderByDatosProgramaOrden(idConvProgAlu);
	}
	
	@Override
	public List<EvaAluActProy> getAllEvaAluActProy(Long idConvProyAlu) {
		return evaAluActProyRepository.findAllByAluConvProyIdOrderByDatosProyectoOrden(idConvProyAlu);
	}
	
	@Override
	public List<EvaAluActProg> createEvaAluActProg(List<EvaAluActProg> evaAluActProgListIn, Long idConvProgAlu) {
		
		deleteEvaAluActProg(idConvProgAlu);
		
		if (evaAluActProgListIn !=null && !evaAluActProgListIn.isEmpty()) {		
			
			for (EvaAluActProg evaAluActProg : evaAluActProgListIn) {
				
				if (evaAluActProg.getAluConvProg().getId() != null) {
					
					EvaAluActProg evaAluActProgUpdate = new EvaAluActProg();
					
					Optional<AlumnoPrograma> aluConvProg = alumnoProgramaRepository.findById(idConvProgAlu);
					
					evaAluActProgUpdate.setAluConvProg(aluConvProg.isPresent()?aluConvProg.get():null);
					evaAluActProgUpdate.setDatosPrograma(evaAluActProg.getDatosPrograma());
					evaAluActProgUpdate.setRealizada(evaAluActProg.getRealizada());
					evaAluActProgUpdate.setAdquirida(evaAluActProg.getAdquirida());
					evaAluActProgUpdate.setObservaciones(evaAluActProg.getObservaciones());
					evaAluActProgUpdate.setCriterios(evaAluActProg.getCriterios());
					
					evaAluActProgRepository.save(evaAluActProgUpdate);
				}
	        }	
		}
		
		return evaAluActProgListIn;		
	}
	
//	@Transactional
	public void deleteEvaAluActProg(Long idConvProgAlu) {
		List<EvaAluActProg> listEvaAluActProg = evaAluActProgRepository.findAllByAluConvProgId(idConvProgAlu);
		
		for (EvaAluActProg evaAluActProg : listEvaAluActProg) {
			evaAluActProgRepository.deleteById(evaAluActProg.getId());
		}
	}
	
	@Override
	public List<EvaAluActProy> createEvaAluActProy(List<EvaAluActProy> evaAluActProyListIn, Long idConvProyAlu) {
		
		deleteEvaAluActProy(idConvProyAlu);
		
		if (evaAluActProyListIn !=null && !evaAluActProyListIn.isEmpty()) {		
			
			for (EvaAluActProy evaAluActProy : evaAluActProyListIn) {
				
				if (evaAluActProy.getAluConvProy().getId() != null) {
					
					EvaAluActProy evaAluActProyUpdate = new EvaAluActProy();
					
					Optional<ConveniosProyectoAlumno> aluConvProy = convenioProyectoAlumnoRepository.findById(idConvProyAlu);
					
					evaAluActProyUpdate.setAluConvProy(aluConvProy.isPresent()?aluConvProy.get():null);
					evaAluActProyUpdate.setDatosProyecto(evaAluActProy.getDatosProyecto());
					evaAluActProyUpdate.setRealizada(evaAluActProy.getRealizada());
					evaAluActProyUpdate.setAdquirida(evaAluActProy.getAdquirida());
					evaAluActProyUpdate.setObservaciones(evaAluActProy.getObservaciones());
					evaAluActProyUpdate.setCriterios(evaAluActProy.getCriterios());
					
					evaAluActProyRepository.save(evaAluActProyUpdate);
				}
	        }	
		}
		
		return evaAluActProyListIn;		
	}
	
//	@Transactional
	public void deleteEvaAluActProy(Long idConvProyAlu) {
		List<EvaAluActProy> listEvaAluActProy = evaAluActProyRepository.findAllByAluConvProyId(idConvProyAlu);
		
		for (EvaAluActProy evaAluActProy : listEvaAluActProy) {
			evaAluActProyRepository.deleteById(evaAluActProy.getId());
		}
	}
	
	@Override
	public List<AptoEvaluacionAlumno> getAptoEvaluacionAlumno() {
		List<AptoEvaluacionAlumnoProjection> aptoEvaluacionAlumnoList = alumnadoRepository.findAptoEvaluacionAlumno();
		if (aptoEvaluacionAlumnoList == null || aptoEvaluacionAlumnoList.isEmpty()) {
			String mes = crearMessage(FechaSemana.class.getSimpleName(), FECHASEMANA);
			throw new NotFoundException(mes);
		}
		return aptoEvaluacionAlumnoList.stream().map(entity -> modelMapper.map(entity, AptoEvaluacionAlumno.class)).collect(Collectors.toList());
	}
	
	
	// Other methods
	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}

    //get evaluacion jasper pdf
	public byte[] exportReport(Long id, String tipo) throws JRException, IOException {
	 
		 byte[] output = null; 
	     InputStream dbAsStream = null; 
	     
	     Resource resource = resourceLoader.getResource("classpath:reports/InformeEvaluacionIndividual.jrxml");
         dbAsStream = resource.getInputStream();     
         
         Map<String, Object> parameters = new HashMap<>();   
         DatosCabeceraEvaluacionProjection parametros = null;  
         List<DatosEvaluacionesProjection> dsEvaluacionProject = null;
         List<DatosEvaluaciones> evaluacionDataSource = null;
         
         if (tipo.equals("FCT")) {
        	 
        	 parametros = alumnoProgramaRepository.getDatosCabeceraFCT(id);     
        	 
        	 dsEvaluacionProject = alumnoProgramaRepository.getActividadesFCT(id);
        	 
        	 evaluacionDataSource = dsEvaluacionProject.stream().map(evaluacion -> modelMapper.map(evaluacion, DatosEvaluaciones.class))
        			 			.collect(Collectors.toList());        	 
         	
         } else {   
        	 
        	 parametros = alumnoProgramaRepository.getDatosCabeceraDUAL(id);
        	 
        	 dsEvaluacionProject = alumnoProgramaRepository.getActividadesDUAL(id);
        	 
        	 evaluacionDataSource = dsEvaluacionProject.stream().map(evaluacion -> modelMapper.map(evaluacion, DatosEvaluaciones.class))
			 			.collect(Collectors.toList());   	 
        	 
         } 
         
         JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(evaluacionDataSource);
         
         if(parametros != null) {        
        	 
        	 String centro = parametros.getCentro();
        	 String codigo_centro = parametros.getCodigo_centro();
        	 String nombre_alumno = parametros.getNombre_alumno();
        	 String nombre_tutor = parametros.getNombre_tutor();
        	 String familia = parametros.getFamilia();
        	 String centro_trabajo = parametros.getCentro_trabajo();
        	 String curso = parametros.getCurso();
        	 String responsable = parametros.getResponsable();
        	 String area = parametros.getArea();
        	 String periodo = parametros.getPeriodo();
        	 String evaluacion = parametros.getEvaluacion();
        	 String orientaciones = parametros.getOrientaciones();
        	 String localidad = parametros.getLocalidad();	
        	 //String horasS = parametros.getHoras() == null?"":String.valueOf(parametros.getHoras()).replace("\\.0", "");
           	 String horasS = parametros.getHoras() == null?"":(parametros.getHoras() % 1 == 0 ? parametros.getHoras().toString().substring(0, parametros.getHoras().toString().indexOf('.')) : parametros.getHoras().toString());
        	 
 		   	 String sFirma = conveniosFctRepository.getFechaHoy();
		   	
 		   	String descripcionFirma =  "En " + localidad  + " " + sFirma; 
        	 
        	 parameters.put("centro", centro);
        	 parameters.put("codigo_centro", codigo_centro);
        	 parameters.put("nombre_alumno", nombre_alumno);
        	 parameters.put("nombre_tutor", nombre_tutor);
        	 parameters.put("horas", horasS);
        	 parameters.put("familia", familia);
        	 parameters.put("curso", curso);
        	 parameters.put("centro_trabajo", centro_trabajo);
        	 parameters.put("responsable", responsable);
        	 parameters.put("area", area);
        	 parameters.put("periodo", periodo);        	 
        	 parameters.put("evaluacion", evaluacion);
        	 parameters.put("orientaciones", orientaciones); 
        	 parameters.put("descripcionFirma", descripcionFirma);
        	 parameters.put("ds", datasource);
         } 

         
         JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);      	 
     	 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
         
         
         final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         JRExporter exporter = new JRPdfExporter();
         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
         exporter.exportReport();
         output = outputStream.toByteArray();  
        
         return output;
	}



	@Override
	public byte[] exportParteSemanalReport(Long id, String tipo, Long idParsem) throws JRException, IOException {
		
		 byte[] output = null; 
	     InputStream dbAsStream = null; 
	     
	     Resource resource = resourceLoader.getResource("classpath:reports/InformeParteSemanal.jrxml");
         dbAsStream = resource.getInputStream();     
         
         Map<String, Object> parameters = new HashMap<>();   
         DatosCabeceraEvaluacionProjection parametros = null;  
         List<DatosHojaSemanalProjection> dsHojaSemanalProject = null;
         List<DatosHojaSemanal> hojaSemanalDataSource = null;
         
    	 String fechaInicio = "";
    	 String fechaFin = "";
    	 String fechaSemana = "";
         
         if (tipo.equals("FCT")) {
        	 
        	 parametros = alumnoProgramaRepository.getDatosCabeceraFCT(id);
        	 
        	 dsHojaSemanalProject = alumnoProgramaRepository.getHojaSemanalFCT(idParsem);
        	 
        	 
         } else {
        	 
        	 parametros = alumnoProgramaRepository.getDatosCabeceraDUAL(id);
        	 
        	 dsHojaSemanalProject = alumnoProgramaRepository.getHojaSemanalDUAL(idParsem);
        	 
         }
          
         hojaSemanalDataSource = dsHojaSemanalProject.stream().map(hoja -> modelMapper.map(hoja, DatosHojaSemanal.class))
		 										.collect(Collectors.toList());        
         
         for (DatosHojaSemanal semana: hojaSemanalDataSource) {
        	 
        	 String sActividades = "";
        	 Double contadorHoras = 0.0;
        	 fechaSemana = semana.getJornadas();
        	 
        	 List<DatosActividadesProjection> dsActividadesP = null;
        	 List<DatosActividades> dsActividades = null;
        	 
        	 if (tipo.equals("FCT")) {
        		 
        		 dsActividadesP = alumnoProgramaRepository.getActividadesSemanaFCT(semana.getId());
        		 
        		 
        	 
        	 } else {
        		 
        		dsActividadesP = alumnoProgramaRepository.getActividadesSemanaDUAL(semana.getId());
        		 
        		 
        	 }
        	 
        	 dsActividades = dsActividadesP.stream().map(hoja -> modelMapper.map(hoja, DatosActividades.class))
			 				.collect(Collectors.toList());
        	 
        	 for (DatosActividades actividad: dsActividades) {
    			 
    			 sActividades += actividad.getTxactividad() + "\n";  
    		 
    		 }        	 
        	 semana.setActividades(sActividades); 
        	 
             //Separar la fecha de la semana en fechaInicio y fechaFin
             String[] fechas = fechaSemana.split("-");
             fechaInicio = fechas[0].trim();
             fechaFin = fechas[1].trim();
             
             //convertimos las fechas de String a LocalDateTime
             String formato = "dd/MM/yy";
             SimpleDateFormat sdf = new SimpleDateFormat(formato);
             Date fActual;
             Date fFin;
             int nuAsistidos =  semana.getNuDias();
             
    		try {
    			
    			fActual = sdf.parse(fechaInicio);
    	        fFin = sdf.parse(fechaFin);
    	        fFin.setTime(fFin.getTime() + (86400 * 1000));
    	        
    	        //Si el método compmareTo devuelve un número menor que 0 quiere decir que la fecha Inicio es menor que la fecha fin
    	        while(fActual.compareTo(fFin) < 0 && nuAsistidos >0 ) {
    	        	
    	        	Long diaSemana = (long)fActual.getDay() == 0 ? 7 : (long)fActual.getDay();
    	        	
    	        	List<Double> horasDia = null;
    	        	
    	        	if (tipo.equals("FCT")) {
    	        	 horasDia = alumnoProgramaRepository.getHorasSemanales(fActual, diaSemana, id);
    	        	} else {
    	        	 horasDia = alumnoProgramaRepository.getHorasSemanalesDUAL(fActual, diaSemana, id);	
    	        	}       	
    	        	if (!horasDia.isEmpty()) {
    	        		for (int i = 0; i < horasDia.size(); i++) {
    	        		
        	        //if(horasDia.get(i) != null) {
        	        		
       	        		 	contadorHoras += horasDia.get(i);
       	        		 
        	        	}
    	        		nuAsistidos --;
					}
    	        	
    	        	fActual.setTime(fActual.getTime() + (86400*1000));
    	        	//nuAsistidos --;
    	        	 
    	        }
    	        
    	        
    	        //Redondear a la alza maximo 2 decimales
    	        contadorHoras = (double) Math.round(contadorHoras * 100);
    	        contadorHoras = contadorHoras / 100;
    	        
    	        semana.setNhoras(contadorHoras % 1 == 0 ? contadorHoras.toString().substring(0, contadorHoras.toString().indexOf('.')): contadorHoras.toString());
                 
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		
         }        

         Double nHorasTotales = 0.0;
         
         if (tipo.equals("FCT")) { 
             nHorasTotales = alumnoProgramaRepository.getHorasTotales(id);             
         } else {
        	 nHorasTotales = alumnoProgramaRepository.getHorasTotalesDUAL(id);
         }
         
         if (nHorasTotales == null) nHorasTotales = 0.0;          
         nHorasTotales = (double) Math.round(nHorasTotales * 100);
         nHorasTotales = nHorasTotales / 100;
         

         JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(hojaSemanalDataSource);
         
         if(parametros != null) {        
        	 
        	 String centro = parametros.getCentro();
        	 String codigo_centro = parametros.getCodigo_centro();
        	 String nombre_alumno = parametros.getNombre_alumno();
        	 String nombre_tutor = parametros.getNombre_tutor();
        	 String familia = parametros.getFamilia();
        	 String centro_trabajo = parametros.getCentro_trabajo();
        	 String curso = parametros.getCurso();
        	 String responsable = parametros.getResponsable();
        	 String area = parametros.getArea();
        	 String periodo = parametros.getPeriodo();
        	 String evaluacion = parametros.getEvaluacion();
        	 String orientaciones = parametros.getOrientaciones();
        	 String firma = conveniosFctRepository.getFechaActual().substring(1).toUpperCase();
        	 
        	 parameters.put("centro", centro);
        	 parameters.put("codigo_centro", codigo_centro);
        	 parameters.put("nombre_alumno", nombre_alumno);
        	 parameters.put("nombre_tutor", nombre_tutor);
        	 parameters.put("familia", familia);
        	 parameters.put("curso", curso);
        	 parameters.put("centro_trabajo", centro_trabajo);
        	 parameters.put("responsable", responsable);
        	 parameters.put("area", area);
        	 parameters.put("periodo", periodo);        	 
        	 parameters.put("evaluacion", evaluacion);
        	 parameters.put("orientaciones", orientaciones);     
        	 parameters.put("ds", datasource);
        	 parameters.put("hTotales", nHorasTotales % 1 == 0 ? nHorasTotales.toString().subSequence(0, nHorasTotales.toString().indexOf('.')): nHorasTotales.toString());
        	 parameters.put("firmaP", firma);
         } 
         
         JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);      	 
     	 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
         
         
         final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         JRExporter exporter = new JRPdfExporter();
         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
         exporter.exportReport();
         output = outputStream.toByteArray();  
        
         return output;
         
         
	}



	@Override
	public Boolean firmarEvaluacionAlumno(Long idEntidad, String entidad) {
	   
		if (entidad.equals("EVA_PROG_")) 
		{
			Optional<AlumnoPrograma> alumno = alumnoProgramaRepository.findById(idEntidad);
			alumno.get().setFechaFirma(new Date());
	        alumnoProgramaRepository.save(alumno.get());
			
			
		} else {
			Optional<ConveniosProyectoAlumno> alumno = convenioProyectoAlumnoRepository.findById(idEntidad);
			alumno.get().setFechaFirma(new Date());
			convenioProyectoAlumnoRepository.save(alumno.get());			
		}
		
		return true;		
	
	}



	@Override
	public Double getHoras(Long idConvProg, Long idMatricula) {
		return alumnoProgramaRepository.getHoras(idConvProg,idMatricula);
	}
	
	@Override
	public Double getHorasProyecto(Long idConvProy, Long idMatricula) {
		return alumnoProgramaRepository.getHorasProyecto(idConvProy,idMatricula);
	}



	@Override
	public List<AlumnadoNSS> getAlumnadoNSS(Integer cAnno) {
		
		List<AlumnadoNSSProjection> aluProj =  alumnadoRepository.getAlumnadoNSS(cAnno);
		
		return aluProj.stream().map(entity -> modelMapper.map(entity, AlumnadoNSS.class)).collect(Collectors.toList());
		
		
	
	}
	@Override
	public List<AlumnadoAltasBajas> getAlumnadoAltasBajas(Integer cAnno) {
		
		List<AlumnadoAltasBajasProjection> alumAltasBajas =  alumnadoRepository.getAlumnadoAltasBajas(cAnno);
		
		return alumAltasBajas.stream().map(entity -> modelMapper.map(entity, AlumnadoAltasBajas.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<AlumnadoAltas> getAlumnadoAltas(Integer cAnno, Long idCentro) {
		
		List<AlumnadoAltasProjection> alumErasmus =  alumnadoRepository.getAlumnadoAltas(cAnno,idCentro);
		
		return alumErasmus.stream().map(entity -> modelMapper.map(entity, AlumnadoAltas.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ComunicacionDiasPracticas> getComunicacionDiasPracticas(Integer cAnno, Long idCentro, Integer nMes) {
		
		List<ComunicacionDiasPracticasProjection> ComDiasPrac =  alumnadoRepository.getComunicacionDiasPracticas(cAnno,idCentro, nMes);
		
		return ComDiasPrac.stream().map(entity -> modelMapper.map(entity, ComunicacionDiasPracticas.class)).collect(Collectors.toList());
	}



	@Override
	public Integer getNumeroProgProy(Integer cAnno, Long xCentro, Long idEmpleadoComunica) {

		return alumnadoRepository.getNumeroProgProy(cAnno,xCentro,idEmpleadoComunica);
	}

	@Override
	public void deleteAlumnoParteSemanal(Long idConvProAlu, String tipo) {

		if(tipo.equals("FCT")){
			deleteParsemAluProg(idConvProAlu);
		}else{
			deleteParsemAluProy(idConvProAlu);
		}


	}

	private void deleteParsemAluProy(Long idConvProAlu) {

		deleteParsemAnexosProyecto(idConvProAlu);
		Optional<List<ParsemAluProy>> parsemAluProyList = parsemAluProyRepository.findByAluConvProyId(idConvProAlu);
		if(parsemAluProyList.isPresent()){
			for(ParsemAluProy parsemAluProy: parsemAluProyList.get()){

				//Eliminar datos de la tabla actprog
				deleteParsemActProy(parsemAluProy.getId());
				
				deleteParsemRodal(parsemAluProy.getIdrodal());

				//eliminar datos de la tabla aluprog
				parsemAluProyRepository.deleteById(parsemAluProy.getId());
			}
		}
	}

	private void deleteParsemAluProg(Long idConvProAlu) {

		deleteParsemAnexosPrograma(idConvProAlu);
		Optional<List<ParsemAluProg>> parsemAluProgList = parsemAluProgRepository.findByAluConvProgId(idConvProAlu);
		if(parsemAluProgList.isPresent()){
			for(ParsemAluProg parsemAluProg: parsemAluProgList.get()){

				//Eliminar datos de la tabla actprog
				deleteParsemActProg(parsemAluProg.getId());

				deleteParsemRodal(parsemAluProg.getIdrodal());
				//eliminar datos de la tabla aluprog
				parsemAluProgRepository.deleteById(parsemAluProg.getId());
			}
		}
	}

	private void deleteParsemRodal(String idRodal) {
		
		if (idRodal != null) {
			try {
				rodalClient.borrarDocumento(idRodal);
			} catch (RodalExceptionService e) {
				String error = crearMessage(AlumnoPrograma.class.getSimpleName(), "deleteParsemRodal");
				throw new NotFoundException(error);
			}
			
		}
		
	}
	
	
	private void deleteParsemAnexosProyecto(Long idConvProAlu) {
		List<ParteSemanalAnexosProyecto> parteSemanalAnexosProyectoList = parteSemanalAnexosProyectoRepository.findAllByIdConvProyAlu(idConvProAlu);
		for (ParteSemanalAnexosProyecto parteSemanalAnexosProyecto: parteSemanalAnexosProyectoList){
			deleteParsemRodal(parteSemanalAnexosProyecto.getIdAnexoRodal());
			parteSemanalAnexosProyectoRepository.deleteById(parteSemanalAnexosProyecto.getId());
		}
	}

	private void deleteParsemAnexosPrograma(Long idConvProAlu) {
		List<ParteSemanalAnexosPrograma> parteSemanalAnexosProgramaList = parteSemanalAnexosProgramaRepository.findAllByIdConvProgAlu(idConvProAlu);
		for (ParteSemanalAnexosPrograma parteSemanalAnexosPrograma: parteSemanalAnexosProgramaList){
			deleteParsemRodal(parteSemanalAnexosPrograma.getIdAnexoRodal());
			parteSemanalAnexosProgramaRepository.deleteById(parteSemanalAnexosPrograma.getId());
		}
	}

	private List<ListadoAlumnadoTutor> gestionarMensajeAviso(List<ListadoAlumnadoTutorProjection> listadoAlumnosTutor, Integer cAnno){
		List<ListadoAlumnadoTutor> listadoAlumnoParsed = listadoAlumnosTutor.stream().map(entity -> modelMapper.map(entity, ListadoAlumnadoTutor.class)).collect(Collectors.toList());

		LocalDate hoy = LocalDate.now();
		Integer mesAnterior = (hoy.getMonthValue() == 1) ? 12 : hoy.getMonthValue() - 1; //En el caso de enero, ponemos que el anterior sea diciembre

		if(hoy.getDayOfMonth() <= getMaxDiaLiquidacionMensual()){

			for(ListadoAlumnadoTutor alumno : listadoAlumnoParsed){

				if(tienePeriodoActivo(alumno, cAnno, mesAnterior)) {
					setPeridosAvisos(mesAnterior, alumno);
				}
			}
		}

		return listadoAlumnoParsed;
	}



	private void setPeridosAvisos(Integer mesAnterior, ListadoAlumnadoTutor alumno) {
		Object haCotizado;

		if (alumno.getTipoEmpresa().equals("FCT")) {
			haCotizado = segSocialCotizaMesProgRepository.findByIdConvProgAluAndNuMes(alumno.getId(), mesAnterior);
		} else {
			haCotizado = segSocialCotizaMesProyRepository.findByIdConvProyAluAndNuMes(alumno.getId(), mesAnterior);
		}

		// Si no hay registros de que haya cotizado en el mes anterior, se pone el aviso
		if (haCotizado == null) {
			alumno.setAvisoMes(1);
		}
	}

	/*
       Comprobar si tiene algún periodo de alta en activo en el mes anterior al actual
     */
	private boolean tienePeriodoActivo(ListadoAlumnadoTutor alumno, Integer cAnno, Integer nuMesAnterior){
		if(alumno.getTipoEmpresa().equals("FCT")){
			List<AltasSegSociProg> altasAlumnoProg = altaSegSocialProgRepository.findByIdConvProgAlu(alumno.getId());
			return verificarFechasPeriodoActivo(altasAlumnoProg, cAnno, nuMesAnterior);
		} else {
			List<AltasSegSociProy> altasAlumnoProy = altaSegSocialProyRepository.findByIdConvProyAlu(alumno.getId());
			return verificarFechasPeriodoActivo(altasAlumnoProy, cAnno, nuMesAnterior);
		}
	}

	/*
       Verifica si un período está activo en el mes anterior del curso académico.
     */
	private boolean verificarFechasPeriodoActivo(List<?> altasAlumno, Integer cAnno, Integer nuMesAnterior) {
		YearMonth mesComprobar = calcularMesAnteriorAcademico(cAnno, nuMesAnterior);

		for (Object periodo : altasAlumno) {
			Date fechaInicio;
			Date fechaFin;

			// Obtener las fechas de inicio y fin
			if (periodo instanceof AltasSegSociProg) {
				fechaInicio = ((AltasSegSociProg) periodo).getFechaInicio();
				fechaFin = ((AltasSegSociProg) periodo).getFechaFin();
			} else {
				fechaInicio = ((AltasSegSociProy) periodo).getFechaInicio();
				fechaFin = ((AltasSegSociProy) periodo).getFechaFin();
			}

			LocalDate inicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (!(inicio.isAfter(mesComprobar.atEndOfMonth()) || fin.isBefore(mesComprobar.atDay(1)))) {
				return true; // Si alguna parte del período está dentro del mes
			}

		}
		return false; // Si ningún período cubre el mes anterior
	}

	private YearMonth calcularMesAnteriorAcademico(int cAnno, int nuMesAnterior) {
		int annoCalculado = (nuMesAnterior >= 10) ? cAnno : cAnno + 1;

		return YearMonth.of(annoCalculado, nuMesAnterior);
	}

	private Integer getMaxDiaLiquidacionMensual(){
		return segSocialCotizaMesProyRepository.getMaxDiaLiquidacionMensual();
	}

	@Override
	public void updateNuHorasEval(Long idAluCon, String tipo, Integer nuHorasEva) {
		if(tipo.equals("FCT")){
			Optional<AlumnoPrograma> optionalAlumnoProg = alumnoProgramaRepository.findById(idAluCon);
			if(optionalAlumnoProg.isPresent()){
				AlumnoPrograma	alumnoPrograma = optionalAlumnoProg.get();
				alumnoPrograma.setNuHorasEva(nuHorasEva);
				alumnoProgramaRepository.save(alumnoPrograma);
			}
		} else {
			Optional<ConveniosProyectoAlumno> optionalAlumnoProy = convenioProyectoAlumnoRepository.findById(idAluCon);
			if (optionalAlumnoProy.isPresent()) {
				ConveniosProyectoAlumno alumnoProyecto = optionalAlumnoProy.get();
				alumnoProyecto.setNuHorasEva(nuHorasEva);
				convenioProyectoAlumnoRepository.save(alumnoProyecto);
			}
		}

	}
}
