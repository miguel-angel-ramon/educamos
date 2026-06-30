package es.jccm.edu.proyectosfct.application.services.conveniosprogramas;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import es.jccm.edu.proyectosfct.adapter.out.repositories.sedeempresa.SedeEmpresaRepository;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorTramoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorTramoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasHorarioAlumnoFctDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorPeriodoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorTramoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConvProgHorPeriodoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConvProgHorTramoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosProgramasAnexoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosProgramasFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programas.ParteSemanalAnexosProgramaRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConvProgHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConvProgHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasAnexos;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoAnnoCentro;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ParteSemanalAnexosPrograma;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ListadoAnnoCentroProjection;
import es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma.IAlumnoProgramaService;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IEmpresaTrabajadorService;
import es.jccm.edu.proyectosfct.application.ports.in.conveniosprogramas.IConveniosProgramasService;
import es.jccm.edu.proyectosfct.application.ports.in.programas.IProgramasFctService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

@Service
public class ConveniosProgramasService implements IConveniosProgramasService {
	
	@Autowired
	private ConveniosProgramasFctRepository conveniosProgramasFctRepository;
	
	
	@Autowired
	private ParteSemanalAnexosProgramaRepository parteSemanalAnexosProgramaRepository;
	
	@Autowired
	private IConveniosFctService conveniosService;
	
	@Autowired
	private IProgramasFctService programaService;
	
	@Autowired
	private IEmpresaTrabajadorService empresaTrabajadorService;

	@Autowired
	private IAlumnoProgramaService alumnoProgramaService;
	
	@Autowired
	private ConvProgHorPeriodoFctRepository convProgHorPeriodoFctRepository;
	
	@Autowired
	private ConvProgHorTramoFctRepository convProgHorTramoFctRepository;
	
	@Autowired
	private ConvProgAluHorPeriodoFctRepository convProgAluHorPeriodoFctRepository;
	
	@Autowired
	private ConvProgAluHorTramoFctRepository convProgAluHorTramoFctRepository;
	
	@Autowired
	private ConveniosProgramasAnexoFctRepository conveniosProgramasAnexoFctRepository;

	@Autowired
	private SedeEmpresaRepository sedeEmpresaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private IRodalClient rodalClient;
	
	
	@Override
	public List<ConveniosProgramasFct> createConvenioProgramas(List<ConveniosProgramasFct> conveniosProgramasFctListIn) {
		return (List<ConveniosProgramasFct>) conveniosProgramasFctRepository.saveAll(conveniosProgramasFctListIn);
	}
	
	@Override
	public List<ConveniosProgramasFct> createConvenioProgramasHorarioAlumno(List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto) {
		List<ConveniosProgramasFct> conveniosProgramasFctListIn = new ArrayList<>();
		
		listConveniosProgramasHorAluFctDto.forEach( convProgHorAlu -> {
			ConveniosProgramasFct convProg = modelMapper.map(convProgHorAlu, ConveniosProgramasFct.class);
			convProg.setNuHorasTotales(convProgHorAlu.getNuHorasTotales());
			ConveniosProgramasFct convProgSave = conveniosProgramasFctRepository.save(convProg);
			conveniosProgramasFctListIn.add(convProgSave);
			
			convProgHorAlu.getHorarioAlumno().forEach( periodoHor -> {
				ConvProgHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgHorPeriodoFct.class);
				periodo.getConvenioPrograma().setId(convProgSave.getId());
				ConvProgHorPeriodoFct periodoSave = convProgHorPeriodoFctRepository.save(periodo);
				
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProgHorTramoFct tramo = modelMapper.map(tramosHor, ConvProgHorTramoFct.class);
					tramo.getPeriodoHorario().setId(periodoSave.getId());
					convProgHorTramoFctRepository.save(tramo);
				});				
			});
		});
		
		return conveniosProgramasFctListIn;
	}
	
	@Override
	public List<ConvProgHorPeriodoFctDto> createConvenioProgramaPeriodosHorarios(List<ConvProgHorPeriodoFctDto> listConvProgHorPeriodoFctDto) {
		List<ConvProgHorPeriodoFctDto> listConvProgHorPeriodoFctOut = new ArrayList<>();
		
		if(listConvProgHorPeriodoFctDto.get(0) != null) {
			listConvProgHorPeriodoFctOut = getConvenioProgramaPeriodosHorarios(listConvProgHorPeriodoFctDto.get(0).getConvenioPrograma().getId());
			
			listConvProgHorPeriodoFctOut.forEach( periodoHor -> {
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProgHorTramoFct tramo = modelMapper.map(tramosHor, ConvProgHorTramoFct.class);
					convProgHorTramoFctRepository.delete(tramo);
				});	
				ConvProgHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgHorPeriodoFct.class);
				convProgHorPeriodoFctRepository.delete(periodo);
			});
		}		
		
		listConvProgHorPeriodoFctDto.forEach( periodoHor -> {
			ConvProgHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgHorPeriodoFct.class);
			ConvProgHorPeriodoFct periodoSave = convProgHorPeriodoFctRepository.save(periodo);
			
			periodoHor.getTramosHorarios().forEach(tramosHor ->{
				ConvProgHorTramoFct tramo = modelMapper.map(tramosHor, ConvProgHorTramoFct.class);
				tramo.getPeriodoHorario().setId(periodoSave.getId());
				convProgHorTramoFctRepository.save(tramo);
			});				
		});
		
		return listConvProgHorPeriodoFctOut;
	}	

	@Override
	public void deleteConvenioProgramas(List<ConveniosProgramasFct> convenioProgramasFctListIn){
		conveniosProgramasFctRepository.deleteAll(convenioProgramasFctListIn);
	}
	
	@Override
	public void deleteConvenioProgramasHorarioAlumno(List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto){
		listConveniosProgramasHorAluFctDto.forEach( convProgHorAlu -> {
			convProgHorAlu.getHorarioAlumno().forEach( periodoHor -> {
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProgHorTramoFct tramo = modelMapper.map(tramosHor, ConvProgHorTramoFct.class);
					convProgHorTramoFctRepository.delete(tramo);
				});	
				ConvProgHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProgHorPeriodoFct.class);
				convProgHorPeriodoFctRepository.delete(periodo);
			});
			ConveniosProgramasFct convProg = modelMapper.map(convProgHorAlu, ConveniosProgramasFct.class);
			conveniosProgramasFctRepository.delete(convProg);
		});
	}

	
	public List<ConveniosProgramasFct> convenioAprogramas(List<ConveniosProgramasFct> ListConvprogFct, Long idConvenio) {
		
		//Se extraen los convenios-programas actualmentes guardados de BD
		List<ConveniosProgramasFct> listaConvProgrExistente = conveniosProgramasFctRepository.findAllByConvenioId(idConvenio);
		
		//Se crea una copia auxiliar de la lista que nos viene por parametros con las nuevas modificaciones
		List<ConveniosProgramasFct> listaConvProgrAux = ListConvprogFct.stream().collect(Collectors.toList());
		
		//Se identifica y borra los convenios-programas a borrar
		if(!listaConvProgrExistente.isEmpty()) {
			listaConvProgrExistente.forEach( convProgExistente -> {				
				List<AlumnoPrograma> alumnosConvenioPrograma = borrarConvenioPrograma(convProgExistente);
				
				ListConvprogFct.forEach(convProgIndex ->{
					if((convProgExistente.getId().equals(convProgIndex.getId())) && ((convProgIndex.getPrograma() != null) && (convProgIndex.getPrograma().getId() != 0))) {
						createConvenioPrograma(convProgIndex, alumnosConvenioPrograma);
						listaConvProgrAux.remove(convProgIndex); //Se borra de la lista auxiliar los convenios-programas a borrar
					}
				});
			});
		}
		
		//Se recorre la lista auxiliar con los programas nuevos y a modificar restantes
		listaConvProgrAux.forEach(convProgIndex -> {
			List<AlumnoPrograma> alumnosConvenioPrograma = borrarConvenioPrograma(convProgIndex);
			
			if ((convProgIndex.getPrograma() != null) && (convProgIndex.getPrograma().getId() != 0)) {
				createConvenioPrograma(convProgIndex, alumnosConvenioPrograma);
			}
		});
		
		return ListConvprogFct;		
	}
	
	//Metodo para la clase ConveniosProgramasService
	private List<AlumnoPrograma> borrarConvenioPrograma(ConveniosProgramasFct convProg) {
		List<AlumnoPrograma> alumnosConvenioPrograma = alumnoProgramaService.getAlumnosConveniosProgramas(convProg.getId());
		if(!alumnosConvenioPrograma.isEmpty()){
			alumnoProgramaService.deleteAlumnoPrograma(convProg.getId());
		}
		conveniosProgramasFctRepository.delete(convProg);
		
		return alumnosConvenioPrograma;
	}
	
	//Metodo para la clase ConveniosProgramasService
	private void createConvenioPrograma(ConveniosProgramasFct convProgIndex, List<AlumnoPrograma> alumnosConvenioPrograma) {
		ConveniosProgramasFct convenioProgramaFctNew = new ConveniosProgramasFct(); 
           
		ConveniosFct convenioOrigen = conveniosService.getConvenioById(convProgIndex.getConvenio().getId());
		
		ProgramaFct programaOrigen = programaService.getProgramaId(convProgIndex.getPrograma().getId());
				
		EmpresaTrabajador trabajadorOrigen = empresaTrabajadorService.getTrabajadorById(convProgIndex.getTrabajador().getId());

		SedeEmpresa sedeEmpresa = sedeEmpresaRepository.findByIdSede(convProgIndex.getSede().getId());


		convenioProgramaFctNew.setConvenio(convenioOrigen);
		convenioProgramaFctNew.setPrograma(programaOrigen);
		convenioProgramaFctNew.setTrabajador(trabajadorOrigen);
		convenioProgramaFctNew.setFechaIni(convProgIndex.getFechaIni());
		convenioProgramaFctNew.setFechaFin(convProgIndex.getFechaFin());
		
		conveniosProgramasFctRepository.save(convenioProgramaFctNew);
		
		if(!alumnosConvenioPrograma.isEmpty()) {
			List<AlumnoPrograma> alumnosConvenioProgramaSave = new ArrayList<>();
			alumnosConvenioPrograma.forEach(alumno ->{
				alumnosConvenioProgramaSave.add(new AlumnoPrograma(alumno.getOrientaciones(), convenioProgramaFctNew, alumno.getIdMatricula(), alumno.getIdEvaluacion(),"","",null));
			});
			alumnoProgramaService.createAlumnoPrograma(convenioProgramaFctNew.getId(),alumnosConvenioProgramaSave);
		}
	}
	
	@Override
	public Integer countByConvenioId(Long idConvenio) {
		return conveniosProgramasFctRepository.countByConvenioId(idConvenio);
	}

	@Transactional
	public void deleteConvenioPrograma(List<ConveniosProgramasFct> convprogFct) {
		List<ConveniosProgramasFct> conveniosProgramasFct = conveniosProgramasFctRepository.findAllByConvenioId(convprogFct.get(0).getConvenio().getId());
		
		if (conveniosProgramasFct.size()>0) {
			
			conveniosProgramasFctRepository.deleteAll(conveniosProgramasFct);
		}	
	}

	@Override
	public List<ConveniosProgramasFct> getProgramasConvenio(Long idConvenio, Integer cAnno) {

		List<ConveniosProgramasFct> conveniosProg = conveniosProgramasFctRepository.getConveniosAnno(idConvenio, cAnno);
		
		conveniosProg.forEach( conv -> {
			
			Integer numAnexo = conveniosProgramasFctRepository.getCountAnexos(conv.getId());
			conv.setNuAnexosII(numAnexo);	
			
			if (conv.getNuHorasTotales() == 0) {
				conv.setNuHorasTotales(conveniosProgramasFctRepository.getHorasPrograma(conv.getId()));				
			}
			
		});
		
		return conveniosProg;
	}

	@Override
	public ConveniosProgramasFct getConvenioProgramaFct(Long id) {
		return conveniosProgramasFctRepository.findById(id).get();
	}
	
	@Override
	public List<ConvProgAluHorPeriodoFctDto> getConvenioProgramaPeriodosHorariosAlumno(Long idConvProg, Long idMatricula) {
		List<ConvProgAluHorPeriodoFctDto> listPeriodosDto = new ArrayList<>();
		
		List<ConvProgAluHorPeriodoFct> periodos = convProgAluHorPeriodoFctRepository.findAllByConvenioProgramaIdAndIdMatricula(idConvProg, idMatricula);
		
		periodos.forEach(periodo -> {
			List<ConvProgAluHorTramoFctDto> listTramosDto = convProgAluHorTramoFctRepository.findAllByPeriodoAlumnoHorarioId(periodo.getId())
					.stream().map(x -> modelMapper.map(x, ConvProgAluHorTramoFctDto.class)).collect(Collectors.toList());
			
			ConvProgAluHorPeriodoFctDto periodoDto = modelMapper.map(periodo, ConvProgAluHorPeriodoFctDto.class);
			periodoDto.setTramosHorarios(listTramosDto);
			listPeriodosDto.add(periodoDto);
		});

		return listPeriodosDto;
	}


	@Override
	public List<ConvProgHorPeriodoFctDto> getConvenioProgramaPeriodosHorarios(Long idConvProg) {
		List<ConvProgHorPeriodoFctDto> listPeriodosDto = new ArrayList<>();
		
		List<ConvProgHorPeriodoFct> periodos = convProgHorPeriodoFctRepository.findAllByConvenioProgramaId(idConvProg);
		
		periodos.forEach(periodo -> {
			List<ConvProgHorTramoFctDto> listTramosDto = convProgHorTramoFctRepository.findAllByPeriodoHorarioId(periodo.getId())
					.stream().map(x -> modelMapper.map(x, ConvProgHorTramoFctDto.class)).collect(Collectors.toList());
			
			ConvProgHorPeriodoFctDto periodoDto = modelMapper.map(periodo, ConvProgHorPeriodoFctDto.class);
			periodoDto.setTramosHorarios(listTramosDto);
			listPeriodosDto.add(periodoDto);
		});

		return listPeriodosDto;
	}

	@Override
	public Integer getAnnoConvenioPrograma(Long idConvProg) {
        return conveniosProgramasFctRepository.getAnnoConvenioPrograma(idConvProg);
	}
	
	@Override
	public Double getHorasAlumnos(Long id, Long idAlumno) {
		
		List<ConvProgAluHorPeriodoFct> horarioPeriodo = convProgAluHorPeriodoFctRepository.findAllByConvenioProgramaIdAndIdMatricula(id, idAlumno);
		Double totalHoras = 0.0;
		for (int i = 0; i < horarioPeriodo.size(); i++) {
			
			LocalDateTime fechaInicio = horarioPeriodo.get(i).getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			LocalDateTime fechaFin = horarioPeriodo.get(i).getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			LocalDateTime fechaActual = fechaInicio;
			
			List<ConvProgAluHorTramoFct> horarioTramo = convProgAluHorTramoFctRepository.findAllByPeriodoAlumnoHorarioId(horarioPeriodo.get(i).getId());
			
			while(fechaActual.isBefore(fechaFin.plusDays(1))) {
				
				for (int k = 0; k < horarioTramo.size(); k++) {
					
					if(fechaActual.getDayOfWeek().getValue() > horarioTramo.size()) {
						break;
					}
					
					if(horarioTramo.get(k).getDiaSemana() == fechaActual.getDayOfWeek().getValue()) {
						totalHoras += (horarioTramo.get(k).getHoraFin() / 60) - (horarioTramo.get(k).getHoraInicio() / 60);
						break;
					}
				}
				fechaActual = fechaActual.plusDays(1);
			}
			
		}
		
		return totalHoras;
		
	}
	@Override
	public void uploadFicherosAnexo(Long idConvProg, String tipo, List<MultipartFile> archivos) 
																		  throws RodalExceptionService, InsertarDocFault, IOException {
		
		//si solo hay un anexo uploadFicherosAnexoI si hay dos uploadFicherosAnexoII
		ConveniosProgramasFct convProgFct= conveniosProgramasFctRepository.findById(idConvProg).get();
		
		Long idCentro = conveniosProgramasFctRepository.getIdCentroByidConvProg(idConvProg);	
		
		if("ANEXOI".equals(tipo)) {
			anexoIUpload(idConvProg,idCentro,convProgFct, archivos.get(0));
		}else if("ANEXOII".equals(tipo)) {
			anexoIIUpload(idConvProg,idCentro,convProgFct,archivos);
		}
	
		
	}
		private void anexoIUpload(Long idConvProg,Long idCentro,ConveniosProgramasFct convProgFct,MultipartFile archivo) throws RodalExceptionService, InsertarDocFault, IOException  {
		
	
		  if(convProgFct.getIdAneiRodal() == null) {
					RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(archivo, "MFCT", "CONV_PROG_", idConvProg, -1L, idCentro, -1L,"-1",-1L,-1L);
					
					convProgFct.setIdAneiRodal(respuesta.getIdDoc());
					convProgFct.setTxAneiFichero(archivo.getOriginalFilename());	
					conveniosProgramasFctRepository.save(convProgFct);					
				}else {
						
					rodalClient.actualizaDoc(archivo, convProgFct.getIdAneiRodal());
					
					convProgFct.setTxAneiFichero(archivo.getOriginalFilename());
					conveniosProgramasFctRepository.save(convProgFct);
			}	
	}
		private void anexoIIUpload(Long idConvProg,Long idCentro, ConveniosProgramasFct convProgFct, List <MultipartFile> files) throws RodalExceptionService, InsertarDocFault, IOException  {
			
			List<ConveniosProgramasAnexos> conveniosProgs = conveniosProgramasAnexoFctRepository.findAllByIdConvProg(idConvProg);
			
			for (int i = 0; i < conveniosProgs.size(); i++) {			
				
				rodalClient.borrarDocumento(conveniosProgs.get(i).getIdAnexoRodal());
				conveniosProgramasAnexoFctRepository.deleteById(conveniosProgs.get(i).getId());
			}
			
			for (int i = 0; i < files.size(); i++) {				
				
				try {
				
					ConveniosProgramasAnexos anexos = new ConveniosProgramasAnexos();
					anexos.setIdConvProg(idConvProg);				
						
					conveniosProgramasAnexoFctRepository.save(anexos);				
					
					RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(files.get(i), "MFCT", "CONV_PROG_ANEXO_", anexos.getId(), -1L, idCentro, -1L,"-1",-1L,-1L);
					
					anexos.setIdAnexoRodal(respuesta.getIdDoc());
					anexos.setNombreFichero(files.get(i).getOriginalFilename());
					conveniosProgramasAnexoFctRepository.save(anexos);
				
			  } catch (RodalExceptionService e) {					

					rodalClient.borrarDocumento(conveniosProgs.get(i).getIdAnexoRodal());
					conveniosProgramasAnexoFctRepository.deleteById(conveniosProgs.get(i).getId());
					
              		throw new RodalExceptionService(e);			    
				}     			
			}
			
		}
	public void updateFicherosAnexo(ConveniosProgramasFct covProgUpdate) {
	//	autorizacionDesplazamientoRepository.save(covProgUpdate);
		conveniosProgramasFctRepository.save(covProgUpdate);
	}

	@Override
	public List<ConveniosProgramasAnexos> getAnexoII(Long idConvProg) {
		 return conveniosProgramasAnexoFctRepository.findAllByIdConvProg(idConvProg);
	}

	@Override
	public List<ParteSemanalAnexosPrograma> uploadFicherosParteSemanalAnexosProyecto(Long idConvProgAlu, List<MultipartFile> partesSemanales) throws RodalExceptionService, InsertarDocFault, IOException { 
		 
		RespuestaInsertarDoc respuesta = null;
		ParteSemanalAnexosPrograma anexo = null;
		
		ListadoAnnoCentroProjection annoCentro = parteSemanalAnexosProgramaRepository.getAnnoCentroByIdConvProgAlu(idConvProgAlu);
		
		ListadoAnnoCentro annoCentroE = modelMapper.map(annoCentro, ListadoAnnoCentro.class);
		
		List<ParteSemanalAnexosPrograma> parSemAneProg = parteSemanalAnexosProgramaRepository.findAllByIdConvProgAlu(idConvProgAlu);
		
		//Borrado
		for (ParteSemanalAnexosPrograma partSemOld : parSemAneProg) {
			rodalClient.borrarDocumento(partSemOld.getIdAnexoRodal());
			parteSemanalAnexosProgramaRepository.deleteById(partSemOld.getId());
		}
		//Insertado múltiple 
		for(MultipartFile parteSemanal  : partesSemanales) {
			
				anexo = new ParteSemanalAnexosPrograma();
				anexo.setIdConvProgAlu(idConvProgAlu);
				parteSemanalAnexosProgramaRepository.save(anexo);
				
				respuesta = rodalClient.insertaDoc(parteSemanal, "MFCT", "PARSEM_ANEX_PROG_", anexo.getId(), annoCentroE.getAnno(),annoCentroE.getCentro() , -1L,"-1",-1L,-1L);
				
				anexo.setIdAnexoRodal(respuesta.getIdDoc());
				anexo.setTxAnexoFichero(parteSemanal.getOriginalFilename());
				parteSemanalAnexosProgramaRepository.save(anexo);
		}
		return parteSemanalAnexosProgramaRepository.findAllByIdConvProgAlu(idConvProgAlu);
	}
	
	@Override
	public List<ParteSemanalAnexosPrograma> getParteSemanalAnexosPrograma(Long idConvProgAlu) {	
		
		return parteSemanalAnexosProgramaRepository.findAllByIdConvProgAlu(idConvProgAlu);
		
	}

	@Override
	public Integer checkOverlappingDates(Long idPrograma, Long idConvenio, String fechaInicio, String fechaFin) throws ParseException {

		//Obtengo el listado de Programas que pertenecen a un convenio
		List<ConveniosProgramasFct> conveniosProgramasFct = conveniosProgramasFctRepository.findByConvenioIdAndProgramaId(idConvenio, idPrograma);
		Integer puedeCrear = 1; //0 no puede crear, 1 si puede crear

		//Compruebo si la lista no está vacía
		if(!conveniosProgramasFct.isEmpty()){
			//Recorro la lista de programas
			puedeCrear = checkEachProgram(fechaInicio, fechaFin, conveniosProgramasFct, puedeCrear);
		}

		return puedeCrear;

	}

	private static Integer checkEachProgram(String fechaInicio, String fechaFin, List<ConveniosProgramasFct> conveniosProgramasFct, Integer puedeCrear) throws ParseException {
		for (ConveniosProgramasFct convenioPrograma: conveniosProgramasFct){

			puedeCrear = checkDatesOfEachProgram(fechaInicio, fechaFin, convenioPrograma);
		}
		return puedeCrear;
	}

	private static Integer checkDatesOfEachProgram(String fechaInicio, String fechaFin, ConveniosProgramasFct convenioPrograma) throws ParseException {
		int puedeCrear;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaIni = dateFormat.parse(fechaInicio);
		Date fechaFi = dateFormat.parse(fechaFin);

		//Compruebo si las fechas del nuevo programa se solapan con las fechas de cada programa
		if(fechaIni.before(convenioPrograma.getFechaFin()) && convenioPrograma.getFechaIni().before(fechaFi)){
			puedeCrear = 0; //Las fechas se solapan
		}else{
			puedeCrear = 1; //Las fechas no se solapan
		}
		return puedeCrear;
	}

	@Override
	public void updateProgramDates(Long idConvProg, String fechaInicio, String fechaFin,Integer newHora) throws ParseException {

		ConveniosProgramasFct convenio = conveniosProgramasFctRepository.findById(idConvProg).orElse(null);

		if(convenio != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaIni = dateFormat.parse(fechaInicio);
			Date fechaFi = dateFormat.parse(fechaFin);
			convenio.setFechaIni(fechaIni);
			convenio.setFechaFin(fechaFi);
			convenio.setNuHorasTotales(newHora);
			conveniosProgramasFctRepository.save(convenio);
		}
	}
}
