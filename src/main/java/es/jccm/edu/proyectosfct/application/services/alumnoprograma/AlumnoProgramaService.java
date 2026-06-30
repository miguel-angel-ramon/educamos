package es.jccm.edu.proyectosfct.application.services.alumnoprograma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialprog.AltaSegSocialProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasprog.HistoricoAltasProgRepository;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasprogramas.entities.HistoricoAltasProg;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasFctDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.AlumnoProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorPeriodoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorTramoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosProgramasFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesprog.SegSocialCotizaMesProgRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.Alumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.CentroAlumnos;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.CentroAlumnosProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.CotizaMesProgramas;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities.CotizaMesProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma.IAlumnoProgramaService;
import es.jccm.edu.proyectosfct.application.services.conveniosprogramas.ConveniosProgramasService;

@Service
public class AlumnoProgramaService implements IAlumnoProgramaService {
	
	@Autowired
	private AlumnoProgramaRepository alumnoProgramaRepository;
	
	@Autowired
	private SegSocialCotizaMesProgRepository segSocialCotizaMesProgRepository;

	@Autowired
	private HistoricoAltasProgRepository historicoAltasProgRepository;

	@Autowired
	private AltaSegSocialProgRepository altaSegSocialProgRepository;
		
	@Autowired
	private ConvProgAluHorPeriodoFctRepository convProgAluHorPeriodoFctRepository;
	
	@Autowired
	private ConvProgAluHorTramoFctRepository convProgAluHorTramoFctRepository;
	
	@Autowired
	private ConveniosProgramasService conveniosService;
	
	@Autowired
	private AlumnadoRepository alumnadoRepository;
	
	@Autowired
	private ConveniosProgramasFctRepository conveniosProgramasFctRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<AlumnoPrograma> createAlumnoPrograma(Long idConvProg, List<AlumnoPrograma> alumnosPrograma) {
		if (alumnosPrograma !=null && !alumnosPrograma.isEmpty()) {		

			List<ConvProgHorPeriodoFctDto> horario = conveniosService.getConvenioProgramaPeriodosHorarios(idConvProg);		
			ConveniosProgramasFct convenioOrigen = conveniosService.getConvenioProgramaFct(idConvProg);
			
			for (AlumnoPrograma alumnoPrograma : alumnosPrograma) {

				if ((idConvProg != null) && (idConvProg != 0) && (alumnoPrograma.getIdMatricula() != null)) {

					switch(alumnoPrograma.getAccion()) {
						case "D":
							deleteConvenioAlumnoPrograma(idConvProg, alumnoPrograma);
							break;
						case "C":
							createConvenioAlumnoPrograma(horario, convenioOrigen, alumnoPrograma);
							break;
						case "U":
							updateConvenioAlumnoPrograma(alumnoPrograma);
							break;
						default :break;	
					}
				}
			}   
		}
		return alumnosPrograma;		
	}

	private void deleteConvenioAlumnoPrograma(Long idConvProg, AlumnoPrograma alumnoPrograma) {		
		List<AltasSegSociProg> aluSegSociProg = altaSegSocialProgRepository.findByIdConvProgAlu(alumnoPrograma.getId());

//		if(aluSegSociProg != null && (aluSegSociProg.getFechaEnvio() == null || aluSegSociProg.getLgAnulado() == 1)) {


		if(aluSegSociProg != null) {

			for (AltasSegSociProg altas:aluSegSociProg) {

				
				List<AlumnoPrograma> otrosConvProgAlu = obtenerOtrosConvenios(alumnoPrograma);
				
				trasladaIdentificadorSS(alumnoPrograma, altas, otrosConvProgAlu);				
				
				if(altas.getFechaEnvio() == null || altas.getLgAnulado() == 1) {
					List<HistoricoAltasProg> listadoHistoricoAltasProg = historicoAltasProgRepository.findByIdAltassProg(altas.getId());
					List<CotizaMesProgramas> cotis = segSocialCotizaMesProgRepository.findByIdConvProgAlu(alumnoPrograma.getId());

					if (cotis.size() > 0) segSocialCotizaMesProgRepository.deleteAll(cotis);
					if (listadoHistoricoAltasProg.size() > 0)
						historicoAltasProgRepository.deleteAll(listadoHistoricoAltasProg);
					
					altaSegSocialProgRepository.delete(altas);
				}
				
				
				
			}
		}		
		
		borrarPeriodos(idConvProg, alumnoPrograma.getIdMatricula());
		alumnoProgramaRepository.deleteById(alumnoPrograma.getId());		
	}

	private void trasladaIdentificadorSS(AlumnoPrograma alumnoPrograma, AltasSegSociProg altas,
			List<AlumnoPrograma> otrosConvProgAlu) {
		if (!otrosConvProgAlu.isEmpty()) {
			// Actualizamos el idConvProyAlu en las altas y obtenemos el nuevo idConvProgAlu máximo
			Long nuevoIdConvProgAlu = actualizarIdConvProgAluAltas(altas, otrosConvProgAlu);

			// Ahora obtenemos las cotizaciones asociadas al convProgAlu actual
			List<CotizaMesProgramas> cotis = segSocialCotizaMesProgRepository.findByIdConvProgAlu(alumnoPrograma.getId());

			// Actualizamos el idConvProyAlu en las altas
			for (CotizaMesProgramas cotiza : cotis) {
				actualizarIdConvProgAluCotizacion(cotiza, nuevoIdConvProgAlu);
			}
		}
	}
	
	
	
	private void actualizarIdConvProgAluCotizacion(CotizaMesProgramas cotiza, Long nuevoIdConvProgAlu) {
		cotiza.setIdConvProgAlu(nuevoIdConvProgAlu);
		segSocialCotizaMesProgRepository.save(cotiza);
	}
	
	private Long actualizarIdConvProgAluAltas(AltasSegSociProg altas, List<AlumnoPrograma> otrosConvProgAlu) {
		Long nuevoIdConvProgAlu = otrosConvProgAlu.stream()
				.mapToLong(AlumnoPrograma::getId)
				.max()
				.orElse(altas.getIdConvProgAlu());

		altas.setIdConvProgAlu(nuevoIdConvProgAlu);
		altaSegSocialProgRepository.save(altas);

		return nuevoIdConvProgAlu; // Retornamos el nuevo idConvProgAlu máximo
	}
	
	private List<AlumnoPrograma> obtenerOtrosConvenios(AlumnoPrograma convProgAlu) {
		List<AlumnoPrograma> otrosConvProgAlu = alumnoProgramaRepository.findByMatricula(convProgAlu.getIdMatricula());
		otrosConvProgAlu.removeIf(cp -> cp.getId().equals(convProgAlu.getId()));
		return otrosConvProgAlu;
	}

	private void updateConvenioAlumnoPrograma(AlumnoPrograma alumnoPrograma) {
		AlumnoPrograma aluProg = null;
		aluProg = alumnoProgramaRepository.findById(alumnoPrograma.getId()).orElse(null);
		if (aluProg != null) {
			aluProg.setLgCotiza(alumnoPrograma.getLgCotiza());
			aluProg.setLgErasmus(alumnoPrograma.getLgErasmus());
			updateSegSocialAlumno(alumnoPrograma, aluProg);
			alumnoProgramaRepository.save(aluProg);
		}
	}

	private void createConvenioAlumnoPrograma(List<ConvProgHorPeriodoFctDto> horario,
		ConveniosProgramasFct convenioOrigen,AlumnoPrograma alumnoProg) {
		AlumnoPrograma newAluProg = null;
		newAluProg = new AlumnoPrograma();				
		newAluProg.setOrientaciones(alumnoProg.getOrientaciones());
		newAluProg.setConvenioPrograma(convenioOrigen);
		newAluProg.setIdMatricula(alumnoProg.getIdMatricula());
		newAluProg.setIdEvaluacion(alumnoProg.getIdEvaluacion());
		newAluProg.setLgNuss(0); 
		newAluProg.setLgCotiza(alumnoProg.getLgCotiza());
		newAluProg.setLgErasmus(alumnoProg.getLgErasmus());
		newAluProg.setTnuss(alumnoProg.getTnuss());
		updateSegSocialAlumno(alumnoProg, newAluProg); 							
		alumnoProgramaRepository.save(newAluProg); 
		anadirPeriodosGeneral(horario,newAluProg.getIdMatricula(),convenioOrigen);
	}
	
	@Override
	public List<ConvProgAluHorPeriodoFctDto> createConvenioProgramaPeriodosHorariosAlumno(List<ConvProgAluHorPeriodoFctDto> listConvProgHorPeriodoFctDto) {
		List<ConvProgAluHorPeriodoFctDto> listConvProgHorPeriodoFctOut = new ArrayList<>();
		if(!listConvProgHorPeriodoFctDto.isEmpty()) {
			borrarPeriodos(listConvProgHorPeriodoFctDto.get(0).getConvenioPrograma().getId(), listConvProgHorPeriodoFctDto.get(0).getIdMatricula());
			anadirPeriodos(listConvProgHorPeriodoFctDto);
			actualizaConvenioPrograma(listConvProgHorPeriodoFctDto);
		}		
		return listConvProgHorPeriodoFctOut;
	}

	private void actualizaConvenioPrograma(List<ConvProgAluHorPeriodoFctDto> listConvProgHorPeriodoFctDto){
		
		Date maxDate = getMaxDateAlumno(listConvProgHorPeriodoFctDto);
		if (maxDate != null) {
			ConveniosProgramasFct convProg= conveniosService.getConvenioProgramaFct(listConvProgHorPeriodoFctDto.get(0).getConvenioPrograma().getId());
			
			if (convProg.getFechaFin().before(maxDate)) {				
				convProg.setFechaFin(maxDate);
				conveniosProgramasFctRepository.save(convProg);
				
			}
		}
	}
	
    private Date getMaxDateAlumno(List<ConvProgAluHorPeriodoFctDto> listConvProgHorPeriodoFctDto){
		
    	Optional<Date> maxFechaFin = listConvProgHorPeriodoFctDto.stream()
                .map(ConvProgAluHorPeriodoFctDto::getFechaFin) // Obtenemos las fechas de fin
                .filter(fecha -> fecha != null) // Filtramos para evitar nulos
                .max(Date::compareTo); // Obtenemos la fecha máxima
        
        // Si no hay elementos o todos son nulos, devolverá null
        return maxFechaFin.orElse(null);
	}
	
	
	
	
	
	private void anadirPeriodos(List<ConvProgAluHorPeriodoFctDto> listPeriodosHorarios){
		
		ConveniosProgramasFctDto convenioProgramaDto = listPeriodosHorarios.get(0).getConvenioPrograma();
		ConveniosProgramasFct convenioPrograma = modelMapper.map(convenioProgramaDto, ConveniosProgramasFct.class);		
		
		for (int i = 0; i < listPeriodosHorarios.size(); i++) {
			ConvProgAluHorPeriodoFct periodoAlumno = new ConvProgAluHorPeriodoFct();		
			
			periodoAlumno.setConvenioPrograma(convenioPrograma);
			periodoAlumno.setAnnoPeriodo(listPeriodosHorarios.get(i).getAnnoPeriodo());			
			periodoAlumno.setFechaFin(listPeriodosHorarios.get(i).getFechaFin());
			periodoAlumno.setFechaInicio(listPeriodosHorarios.get(i).getFechaInicio());
			periodoAlumno.setIdMatricula(listPeriodosHorarios.get(i).getIdMatricula());	
			periodoAlumno.setNhoras(listPeriodosHorarios.get(i).getNHoras());
			ConvProgAluHorPeriodoFct periodoSave = convProgAluHorPeriodoFctRepository.save(periodoAlumno);
			
			for (int j = 0; j < listPeriodosHorarios.get(i).getTramosHorarios().size(); j++) {
				ConvProgAluHorTramoFct tramoAlumno = new ConvProgAluHorTramoFct();
				//tramoAlumno = modelMapper.map(listPeriodosHorarios.get(i).getTramosHorarios().get(j), ConvProgAluHorTramoFct.class);
				tramoAlumno.setDiaSemana(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getDiaSemana());
				tramoAlumno.setHoraFin(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getHoraFin());
				tramoAlumno.setHoraInicio(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getHoraInicio());
				tramoAlumno.setOrdenTramo(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getOrdenTramo());
				tramoAlumno.setPeriodoAlumnoHorario(periodoSave);				
				convProgAluHorTramoFctRepository.save(tramoAlumno);
			}			
		}		
		/*listPeriodosHorarios.forEach(periodoGeneral ->{
			ConvProgAluHorPeriodoFct periodoAlumno = modelMapper.map(periodoGeneral, ConvProgAluHorPeriodoFct.class);
			
			ConvProgAluHorPeriodoFct periodoSave = convProgAluHorPeriodoFctRepository.save(periodoAlumno);			
			for (int i = 0; i < periodoGeneral.getTramosHorarios().size(); i++) {
				ConvProgAluHorTramoFct tramoAlumno = modelMapper.map(periodoGeneral.getTramosHorarios().get(i), ConvProgAluHorTramoFct.class);
				tramoAlumno.setPeriodoAlumnoHorario(periodoSave);
				convProgAluHorTramoFctRepository.save(tramoAlumno);
			}
		}); */
		
		
	}
	
	private void anadirPeriodosGeneral(List<ConvProgHorPeriodoFctDto> listPeriodosHorarios, Long idMatricula, ConveniosProgramasFct convenioPrgrama){
			
		for (int i = 0; i < listPeriodosHorarios.size(); i++) {
			ConvProgAluHorPeriodoFct periodoAlumno = new ConvProgAluHorPeriodoFct();
			//ConvProgHorPeriodoFctDto periodosHorarios = listPeriodosHorarios.get(i);
			//periodoAlumno = modelMapper.map(periodosHorarios, ConvProgAluHorPeriodoFct.class);
			periodoAlumno.setAnnoPeriodo(listPeriodosHorarios.get(i).getAnnoPeriodo());
			periodoAlumno.setFechaInicio(listPeriodosHorarios.get(i).getFechaInicio());
			periodoAlumno.setFechaFin(listPeriodosHorarios.get(i).getFechaFin());
			periodoAlumno.setIdMatricula(idMatricula);
			periodoAlumno.setNhoras(listPeriodosHorarios.get(i).getNhoras());
			periodoAlumno.setConvenioPrograma(convenioPrgrama);
			
			ConvProgAluHorPeriodoFct periodoSave = convProgAluHorPeriodoFctRepository.save(periodoAlumno);
			
			for (int j = 0; j < listPeriodosHorarios.get(i).getTramosHorarios().size();j++) {
				ConvProgAluHorTramoFct tramoAlumno = new ConvProgAluHorTramoFct();
				//ConvProgHorTramoFctDto tramo = listPeriodosHorarios.get(i).getTramosHorarios().get(j);
				//tramoAlumno = modelMapper.map(tramo,ConvProgAluHorTramoFct.class);
				tramoAlumno.setDiaSemana(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getDiaSemana());
				tramoAlumno.setHoraInicio(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getHoraInicio());
				tramoAlumno.setHoraFin(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getHoraFin());
				tramoAlumno.setOrdenTramo(listPeriodosHorarios.get(i).getTramosHorarios().get(j).getOrdenTramo());
				tramoAlumno.setPeriodoAlumnoHorario(periodoSave);
				convProgAluHorTramoFctRepository.save(tramoAlumno);
			}
			
		}
		
		/*listPeriodosHorarios.forEach(periodoGeneral ->{
			ConvProgAluHorPeriodoFct periodoAlumno = modelMapper.map(periodoGeneral, ConvProgAluHorPeriodoFct.class);
			periodoAlumno.setIdMatricula(idMatricula);
			periodoAlumno.setConvenioPrograma(convenioPrgrama);
			
			ConvProgAluHorPeriodoFct periodoSave = convProgAluHorPeriodoFctRepository.save(periodoAlumno);
			
			for (int i = 0; i < periodoGeneral.getTramosHorarios().size(); i++) {
				ConvProgAluHorTramoFct tramoAlumno = modelMapper.map(periodoGeneral.getTramosHorarios().get(i), ConvProgAluHorTramoFct.class);
				tramoAlumno.setPeriodoAlumnoHorario(periodoSave);
				convProgAluHorTramoFctRepository.save(tramoAlumno);
			}
		}); */
	}
	
	private void borrarPeriodos(Long idConvProg, Long idMatricula){
		List<ConvProgAluHorPeriodoFctDto> listHorariosGenerales = conveniosService.getConvenioProgramaPeriodosHorariosAlumno(idConvProg, idMatricula);
		
	    for (int i = 0; i < listHorariosGenerales.size(); i++) {
			
			ConvProgAluHorPeriodoFctDto periodoHor = listHorariosGenerales.get(i);
			
			for (int j = 0; j < periodoHor.getTramosHorarios().size(); j++) {
			
				//ConvProgAluHorTramoFct tramo = modelMapper.map(periodoHor.getTramosHorarios(), ConvProgAluHorTramoFct.class);
				//convProgAluHorTramoFctRepository.delete(tramo);
				convProgAluHorTramoFctRepository.deleteById(periodoHor.getTramosHorarios().get(j).getId());
				
			}
			//ConvProgAluHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgAluHorPeriodoFct.class);
			convProgAluHorPeriodoFctRepository.deleteById(periodoHor.getId());		
		 }		
		
		/*listHorariosGenerales.forEach( periodoHor -> {
			periodoHor.getTramosHorarios().forEach(tramosHor ->{
				ConvProgAluHorTramoFct tramo = modelMapper.map(tramosHor, ConvProgAluHorTramoFct.class);
				convProgAluHorTramoFctRepository.delete(tramo);
			});	
			ConvProgAluHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgAluHorPeriodoFct.class);
			convProgAluHorPeriodoFctRepository.delete(periodo);
		}); */
	}
	
//	@Transactional
	public void deleteAlumnoPrograma(Long idConvProg) {
		List<AlumnoPrograma> alumnosPrograma = alumnoProgramaRepository.findAllByConvenioProgramaId(idConvProg);
		
		if (alumnosPrograma.size()>0) {
			Long idConvenioPrograma = alumnosPrograma.get(0).getConvenioPrograma().getId();	
			
			for (int i = 0; i < alumnosPrograma.size(); i++) {
				Long idMatricula = alumnosPrograma.get(i).getIdMatricula();				
				borrarPeriodos(idConvenioPrograma, idMatricula );
			}			
			/*alumnosPrograma.forEach( aluProg -> {
				borrarPeriodos(aluProg.getConvenioPrograma().getId(), aluProg.getIdMatricula());
			}); */
			alumnoProgramaRepository.deleteAll(alumnosPrograma);
		}		
	}
	
	@Override
	public AlumnoPrograma updateAlumnoConvenioPrograma(AlumnoPrograma alumnoPrograma) {
		
		if ((alumnoPrograma.getConvenioPrograma() != null) && (alumnoPrograma.getConvenioPrograma().getId() != 0) && (alumnoPrograma.getIdMatricula() != null)) {
			
			Optional<AlumnoPrograma> aluProg = alumnoProgramaRepository.findById(alumnoPrograma.getId());
			
			ConveniosProgramasFct convenioOrigen = conveniosService.getConvenioProgramaFct(alumnoPrograma.getConvenioPrograma().getId());
			
			aluProg.get().setOrientaciones(alumnoPrograma.getOrientaciones());
			aluProg.get().setConvenioPrograma(convenioOrigen);
			aluProg.get().setIdMatricula(alumnoPrograma.getIdMatricula());
			aluProg.get().setIdEvaluacion(alumnoPrograma.getIdEvaluacion());
			aluProg.get().setIdEvaRodal(alumnoPrograma.getIdEvaRodal());
			aluProg.get().setTxEvafirFichero(alumnoPrograma.getTxEvafirFichero());
			alumnoProgramaRepository.save(aluProg.get());
		}
		
		return alumnoPrograma;	
	}
	
	@Override
	public AlumnoPrograma getAlumnoProgramaById(Long idAluConvProg) {
		Optional<AlumnoPrograma> aluConvProg = alumnoProgramaRepository.getAlumnoProgramaById(idAluConvProg);		
		if(aluConvProg.isPresent()) {
			String  periodoEval= alumnoProgramaRepository.getPeriodoEval(aluConvProg.get().getConvenioPrograma().getId());
			if(aluConvProg.get().getNuHorasEva() == null || aluConvProg.get().getNuHorasEva() == 0 ) {
				aluConvProg.get().setNuHorasEva(aluConvProg.get().getConvenioPrograma().getNuHorasTotales()) ;
			}
			aluConvProg.ifPresent(alumnoPrograma -> alumnoPrograma.setPeriodo(periodoEval));
			return aluConvProg.get();
		   
		} else 
		{		
			throw new NotFoundException("No se ha encontrado el objeto relacionado con " + AlumnoPrograma.class.getSimpleName());
		}
	}
	
	@Override
	public List<Alumno> getAlumnosPrograma(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProgAlu) {
		
		List<AlumnoProjection> alumnoProjection = alumnoProgramaRepository.getAlumnosConvenio(idCentro, idOfertamatrig, cAnno, idConvProgAlu);

		return  alumnoProjection.stream().map(entity -> modelMapper.map(entity, Alumno.class)).collect(Collectors.toList());

	}
	
	@Override
	public List<Alumno> getAlumnosSeleccionados(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProg) {
		List<AlumnoProjection> alumnoProjection = alumnoProgramaRepository.getAlumnosConvenioSeleccionados(idCentro, idOfertamatrig, cAnno, idConvProg); 
		List<Alumno> alumnos = alumnoProjection.stream().map(entity -> modelMapper.map(entity, Alumno.class)).collect(Collectors.toList());

		for (Alumno alumno: alumnos){
			alumno.setCotizaIntermitente(1);
			checkFechaEnvioAltasSSProg(alumno);
		}

		return alumnos;
	}

	private void checkFechaEnvioAltasSSProg(Alumno alumno) {
		List<AltasSegSociProg> altasSegSociProg = altaSegSocialProgRepository.findByIdConvProgAlu(alumno.getIdConvAlu());

		if(altasSegSociProg != null) {
			for (AltasSegSociProg altas : altasSegSociProg) {
				if (altas.getFechaEnvio() == null) {
					checkFechaEnvioHistoricoProg(alumno, altas);
				} else {
					alumno.setCotizaIntermitente(0);
				}
			}
		}
	}

	private void checkFechaEnvioHistoricoProg(Alumno alumno, AltasSegSociProg altasSegSociProg) {
		List<HistoricoAltasProg> historicoAltasProgList = historicoAltasProgRepository.findByIdAltassProg(altasSegSociProg.getId());
		if(historicoAltasProgList != null){
			for (HistoricoAltasProg historicoAltasProg: historicoAltasProgList){
				if(historicoAltasProg.getFechaEnvio() != null){
					alumno.setCotizaIntermitente(0);
					break;
				}
			}
		}
	}

	@Override
	public List<AlumnoPrograma> getAlumnosConveniosProgramas(Long idConvenio) {
		return (List<AlumnoPrograma>) alumnoProgramaRepository.findAllByConvenioProgramaId(idConvenio);
	}

	@Override
	public List<UnidadCurso> getUnidades(Long idCentro, Long idOfertamatrig, int cAnno) {
		List<UnidadCursoProjection> unidadesProjection = alumnoProgramaRepository.getUnidades(idCentro, idOfertamatrig, cAnno); 
		
		return unidadesProjection.stream().map(entity -> modelMapper.map(entity, UnidadCurso.class)).collect(Collectors.toList());
	}

	@Override
	public CentroAlumnos getNombreCentro(Long idCentro) {
		
		CentroAlumnosProjection centro = alumnoProgramaRepository.findNombreCentro(idCentro);						
		
		return  modelMapper.map(centro, CentroAlumnos.class);	

	}

	@Override
	public Integer countByconvenioProgramaId(Long idConvProg) {
		return alumnoProgramaRepository.countByconvenioProgramaId(idConvProg);
	}

	@Override
	public AlumnoPrograma getAlumnoProgramaByIdRodal(String idEvaRodal) {
        Optional<AlumnoPrograma> res = alumnoProgramaRepository.findByIdEvaRodal(idEvaRodal); 
		
		return res.isPresent() ? res.get() : null;
	}


	@Override
	public void updateAlumSeleccionados(List<AlumnoDto> alumSeleccionados) {

		//Recorrer la lista
		for (AlumnoDto alum: alumSeleccionados){
			//Actualizar lgCotiza de la tabla FCT_CONVPROG_ALU mediante el idConvProgAlu
			AlumnoPrograma convProgAlu =  alumnoProgramaRepository.findById(alum.getIdConvAlu()).orElse(null);

			if(convProgAlu != null){
				convProgAlu.setLgCotiza(alum.getLgCotiza());
				convProgAlu.setLgErasmus(alum.getLgErasmus()); 
				
				//Obtener el objeto Alumno
				Alumnado alu = alumnadoRepository.findById(alum.getId()).orElse(null);

				if(alu != null)
				{
					//Comprobar si es necesario actualizar el TNUSS					
					if (alu.getTnuss() == null || (convProgAlu.getLgNuss() == 1 && alum.getTnuss().compareTo(alu.getTnuss()) != 0)) {
						alu.setTnuss(alum.getTnuss());
						alumnadoRepository.save(alu);
						if (convProgAlu.getLgNuss() == 0) convProgAlu.setLgNuss(1);
					}
					delAltas(alum, convProgAlu);

					//Guardamos el objeto convenioProgramaAlumno
					alumnoProgramaRepository.save(convProgAlu);
				}
			  }
			}
		}

	private void delAltas(AlumnoDto alum, AlumnoPrograma convProgAlu) {
		//Obtenemos el objeto de altas en la seg social de un alumno para un programa
		List<AltasSegSociProg> altasSegSociProg = altaSegSocialProgRepository.findByIdConvProgAlu(convProgAlu.getId());
		//Cmprobamos si existe registro, si tiene no tiene fecha de envío y si el LgCotiza que recibimos es un 0 (deja de cotizar)

//					if(altasSegSociProg != null && altasSegSociProg.getFechaEnvio() == null && alum.getLgCotiza() == 0){
		if(altasSegSociProg != null) {
			for(AltasSegSociProg altas: altasSegSociProg) {
				if(altas.getFechaEnvio() == null && alum.getLgCotiza() == 0) {
					//Comprobamos si no tiene histórico asociados al registro de la tabla de altasSS
					List<HistoricoAltasProg> listadoHistoricoAltasProg = historicoAltasProgRepository.findByIdAltassProg(altas.getId());
					//Si no existe eliminamos de la tabla de altaSS
					if (listadoHistoricoAltasProg.isEmpty()) {
						altaSegSocialProgRepository.delete(altas);
					}
				}
			}
		}
	}

	public void updateSegSocialAlumno(AlumnoPrograma alumnoPrograma, AlumnoPrograma alumnoGenerado) {
		AlumnoProjection aluProj = alumnadoRepository.findAlumnoByIdMatricula(alumnoPrograma.getIdMatricula());					
	
		Alumnado alum = alumnadoRepository.findById(aluProj.getId()).orElse(null);
		
		if (alum == null) {
			throw new NotFoundException("No se ha encontrado el alumno " + AlumnoPrograma.class.getSimpleName());
		}
		
		if (!Objects.equals(alum.getTnuss(), alumnoGenerado.getTnuss()))
		{
			alum.setTnuss(alumnoPrograma.getTnuss());
			alumnadoRepository.save(alum);
			if (alumnoGenerado.getLgNuss()==0) {
				alumnoGenerado.setLgNuss(1);	
			}
		}
	}

	@Override
	public List<UnidadCurso> getUnidadesModalidad(Long idCentro, Long idModalidad, int cAnno, Long idTutor) {
        List<UnidadCursoProjection> unidadesProjection = alumnoProgramaRepository.getUnidadesModalidad(idCentro, idModalidad, cAnno, idTutor); 
		
		return unidadesProjection.stream().map(entity -> modelMapper.map(entity, UnidadCurso.class)).collect(Collectors.toList());
	}

    @Override
    public Date obtenerFechaFinAltaSS(Long idConvProgAlu, Long idMatricula) {
		Date diaFinAltaSSProg =  altaSegSocialProgRepository.findFechaFinAltaSS(idConvProgAlu, idMatricula);
		if (diaFinAltaSSProg == null) {
			diaFinAltaSSProg = new Date(Long.MAX_VALUE);
		}
		return diaFinAltaSSProg;
    }
}
