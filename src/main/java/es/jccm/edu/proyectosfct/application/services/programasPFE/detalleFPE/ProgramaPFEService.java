package es.jccm.edu.proyectosfct.application.services.programasPFE.detalleFPE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.AnexoVProjection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.BASE64DecodedMultipartFile;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionFlujoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.TipoAutorizacionRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.historialPFERepository.HistorialPFERepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.ProgramasPFERepository;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.TipoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.AutorizacionFlujoSiguienteProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.HistoricoFlujoAutorizacionProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.AnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.DatosVigentePFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.DatosVigentePFEProjection;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.DetallePFE.IProgamaPFE;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.anexosPFE.IAnexosFPE;
import java.util.Calendar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;

@Service
public class ProgramaPFEService implements IProgamaPFE {
	
	private static final Long ANEXO_V = 5L;
	
	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private ProgramasPFERepository programasPFERepository;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private TipoAutorizacionRepository tipoAutorizacionRepository;
	
	@Autowired
	private AutorizacionFlujoRepository autorizacionFlujoRepository;
	
	@Autowired
	private HistorialPFERepository  historialPFERepository;
	
	@Autowired
	private IAnexosFPE iAnexosFPE;
	
	 @Autowired
	 private IRodalClient rodalClient;

	@Override
	public List<ElementoSelect> getCursosCiclos(Long idCentro, Integer cAnno) {
		
		List<ElementoSelectProjection> cursos = programasPFERepository.getCursosCiclos(idCentro,cAnno);

		return cursos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getCursosEspecializacion(Long idCentro, Integer cAnno) {
		List<ElementoSelectProjection> cursos = programasPFERepository.getCursosEspecializacion(idCentro,cAnno);

		return cursos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public ProgramasPFE getProgramaPFE(Long id, Long xPerfil, Long xUsuario, Integer esJefe) {				
		
		ProgramasPFE programa = programasPFERepository.findById(id).orElse(null);
		Integer tipoCurso = -1;
		Integer puedeActualizar = -1;		
		
		if (programa != null) {
		 tipoCurso = programasPFERepository.obtenerTipoPrograma(programa.getIdCurso());		
		programa.setLgCurso(tipoCurso!=null?0:1);	
		
		
		    if (xPerfil != null && xPerfil != -1L) {
				
				puedeActualizar = programasPFERepository.puedeModificar(id,
						 xPerfil, 
						 xUsuario,
						 esJefe);		
				programa.setPuedeModificar(puedeActualizar);
				
			}
		    
		    String dsCurso = programasPFERepository.getNombreCurso(programa.getIdCurso()); 
			
			programa.setDsCurso(dsCurso);
		
		}		
		
		
		
		return programa;
	}

	@Override
	public List<DatosVigentePFE> getExistePFE(Long id, Long idCentro, Long idCurso, Integer nuAnnoDesde, Integer nuAnnoHasta,
			Integer lgAlcance, Integer lgModalidad) {
		
		Integer nuAnnoHastaFor = nuAnnoHasta==-1?9999:nuAnnoHasta;
		
		List<DatosVigentePFEProjection> vigente = programasPFERepository.getExistePFE(id,idCentro,idCurso,nuAnnoDesde,nuAnnoHastaFor,lgModalidad);

		return vigente.stream().map(entity -> modelMapper.map(entity, DatosVigentePFE.class)).collect(Collectors.toList());
		
	}

	@Override
	public ProgramasPFE savePFE(ProgramasPFE pfeRequest, Long xUsuarioDelphos) throws RodalExceptionService, InsertarDocFault, IOException {		
		
		Long id = pfeRequest.getId() == null ? -1L : pfeRequest.getId();
		
		ProgramasPFE pfe = getProgramaPFE(id, -1L, -1L, 0);
		Boolean actualizacion = false;
		
		if (pfe != null)  actualizacion = true;
		
		if (!actualizacion) pfe = new ProgramasPFE(); 

		pfe.setIdCurso(pfeRequest.getIdCurso());
		pfe.setIdCentro(pfeRequest.getIdCentro());
		if (!actualizacion) pfe.setAnno(pfeRequest.getAnno());
		pfe.setNuAnnoDesde(pfeRequest.getNuAnnoDesde());
		pfe.setNuAnnoHasta(pfeRequest.getNuAnnoHasta());
		pfe.setLgActivo(1);	
		
		pfe.setLgAlcance(pfeRequest.getLgAlcance());
		pfe.setNuAlumnado(pfeRequest.getNuAlumnado());
		pfe.setLgModalidad(pfeRequest.getLgModalidad());
		pfe.setLgAmpliacion(pfeRequest.getLgAmpliacion()==null?0:pfeRequest.getLgAmpliacion());
		
		pfe.setNuHorasFpe(pfeRequest.getNuHorasFpe());
		pfe.setNuHorasCur(pfeRequest.getNuHorasCur());
		
		pfe.setLgDiario(pfeRequest.getLgDiario()==null?0:pfeRequest.getLgDiario());
		pfe.setLgSemanal(pfeRequest.getLgSemanal()==null?0:pfeRequest.getLgSemanal());
		pfe.setLgMensual(pfeRequest.getLgMensual()==null?0:pfeRequest.getLgMensual());
		pfe.setLgOtros(pfeRequest.getLgOtros()==null?0:pfeRequest.getLgOtros());		
		
		pfe.setNuHorasComFpe(pfeRequest.getNuHorasComFpe());		
		pfe.setNuHorasComCur(pfeRequest.getNuHorasComCur());		
		
		pfe.setDsCooModProf(pfeRequest.getDsCooModProf());
		pfe.setDsEmpOrgCol(pfeRequest.getDsEmpOrgCol());
		pfe.setDsCarConAlt(pfeRequest.getDsCarConAlt());
		pfe.setDsConBeca(pfeRequest.getDsConBeca());
		
		pfe.setLgAutorizacion(pfeRequest.getLgAutorizacion());
		
		
		if (actualizacion) {
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAut400(),1);
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAutForcom(),2);
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAutCurEsp(),3);
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAut3Cur(),4);
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAutCamReg(),5);
			updateAnexos(pfeRequest.getId(), pfeRequest.getLgAutProEns(),6);
		}
		
		pfe.setLgAut400(pfeRequest.getLgAut400());
		pfe.setLgAutForcom(pfeRequest.getLgAutForcom());
		pfe.setLgAutCurEsp(pfeRequest.getLgAutCurEsp());		
		pfe.setLgAut3Cur(pfeRequest.getLgAut3Cur());
		pfe.setLgAutCamReg(pfeRequest.getLgAutCamReg());
		pfe.setLgAutProEns(pfeRequest.getLgAutProEns());
		
		pfe.setCUsuProg(xUsuarioDelphos);		 
		
		return  programasPFERepository.save(pfe);
   }
	
	
	private void updateAnexos(Long idPrograma, Integer anexo, Integer tipo) throws RodalExceptionService, InsertarDocFault, IOException {
		
		if (anexo == 0) 
		{			
			List<AnexosProgPFE> anexos = iAnexosFPE.getAnexosAutTipo(idPrograma,tipo);
			
			if (anexos != null && anexos.size()>0) 
			{				
				iAnexosFPE.deleteAnexo(anexos.get(0).getId());			
			}			
		}	
	}

	@Override
	public List<ElementoSelect> getAnnosIni() {
		
		List<ElementoSelectProjection> anoIni = programasPFERepository.getAnnosIni();

		return anoIni.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getAnnosFin() {
		
		List<ElementoSelectProjection> anoIni = programasPFERepository.getAnnosFin();

		return anoIni.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	public byte[] getAnexoV(Long idProgPerFor) {
		try {
			byte[] output = null;
			InputStream dbAsStream = null;

			Resource resource = resourceLoader.getResource("classpath:reports/anexoV.jrxml");
			dbAsStream = resource.getInputStream();

			Map<String, Object> parameters = new HashMap<>();
			AnexoVProjection datosAnexoV = programasPFERepository.getDatosAnexoV(idProgPerFor);


			if (datosAnexoV != null) {
				parameters.put("centro", datosAnexoV.getCentro());
				parameters.put("codigo", datosAnexoV.getCodigo());
				parameters.put("nif", datosAnexoV.getNif());
				parameters.put("tipo", datosAnexoV.getTipo());
				parameters.put("tipoCurso", datosAnexoV.getTipoCurso());
				parameters.put("alcance", datosAnexoV.getAlcance());
				parameters.put("nAlumnos", datosAnexoV.getNAlumnos());
				parameters.put("ampliacion", datosAnexoV.getAmpliacion());
				parameters.put("horasPFE", datosAnexoV.getHorasPFE());
				parameters.put("horasCur", datosAnexoV.getHorasCur());
				parameters.put("horasComPFE", datosAnexoV.getHorasComPFE());
				parameters.put("horasConcur", datosAnexoV.getHorasConcur());
				parameters.put("modulos", datosAnexoV.getModulos());
				parameters.put("autorizacion", datosAnexoV.getAutorizacion());
				parameters.put("dsOrg", datosAnexoV.getDsOrg());
				parameters.put("dsCara", datosAnexoV.getDsCara());
				parameters.put("dsBeca", datosAnexoV.getDsBeca());
				parameters.put("dsMod", datosAnexoV.getDsMod());
				parameters.put("lgAut400", datosAnexoV.getLgAut400());
				parameters.put("lgAutForCom", datosAnexoV.getLgAutForCom());
				parameters.put("lgAutCurEsp", datosAnexoV.getLgAutCurEsp());
				parameters.put("lgAut3Cur", datosAnexoV.getLgAut3Cur());
				parameters.put("lgAutCamReg", datosAnexoV.getLgAutCamReg());
				parameters.put("lgAutProens", datosAnexoV.getLgAutProens());
				parameters.put("lgModalidad", datosAnexoV.getLgModalidad());
				parameters.put("lgDiario", datosAnexoV.getLgDiario());
				parameters.put("lgSemanal", datosAnexoV.getLgSemanal());
				parameters.put("lgMensual", datosAnexoV.getLgMensual());
				parameters.put("lgOtros", datosAnexoV.getLgOtros());
				parameters.put("lgTipoCiclo", datosAnexoV.getLgTipoCiclo());
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			output = outputStream.toByteArray();

			return output;

		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el recurso JRXML");
		} catch (JRException e) {
			throw new RuntimeException("Error al compilar o generar el reporte");
		}
	}

	@Override
	public Integer generarAnexoV(Long idPrograma, Long xUsuarioDelphos, Integer modo, String sigEstado, Long idPerfil, Integer esJefe, Integer esDirector ) throws Exception {
		byte[] output = getAnexoV(idPrograma);
		
		ProgramasPFE programa = getProgramaPFE(idPrograma,-1L,-1L, 0); 
		
		Optional<TipoAutorizacion> tipo = tipoAutorizacionRepository.findById(ANEXO_V);
		
		BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(output,"AnexoV.pdf");
		
		if (tipo.isPresent()) {
						
			if (modo != 1) {
				
				HistorialProgramaPFE historial = new HistorialProgramaPFE();			
				
				AutorizacionFlujo flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(2L, ANEXO_V);
				
				historial = new HistorialProgramaPFE();
		    	historial.setFRegistro(new Date());
		    	historial.setIdUsuario(xUsuarioDelphos);
		    	historial.setIdProgramaFPE(idPrograma);
		    	historial.setIdFlujo(flujo.getId());
		    	historial.setTxAneFichero("AnexoV.pdf");
		    	historialPFERepository.save(historial);   
		    	
		    	
		    	
		    	rodalClient.insertaDoc(file, 
		             				   "MFCT", 
		                               "ANEV", 
		                                historial.getId(),
		                                programa.getAnno().longValue(),
		                                programa.getIdCentro(),
		                                -1L,"-1L",-1L,-1L);
		    	
		    	
               if (sigEstado.equals("S")) {
		    		
		    		pasarSiguienteEstado(idPerfil,idPrograma,esJefe,xUsuarioDelphos, esDirector);
		    		
		    	}
				
			} else {
				
				List<HistorialProgramaPFE> historial = historialPFERepository.findByIdProgramaFPEAndIdAneRodalIsNotNull(idPrograma);
			
				rodalClient.actualizaDoc(file, historial.get(0).getIdAneRodal());
			
			}	    	
			
		}
		
		return 0;
	}

	private void pasarSiguienteEstado(Long idPerfil, Long idPrograma, Integer esJefe, Long cUsuario, Integer esDirector) {
		
		List<AutorizacionFlujoSiguiente> flujo = getSiguienteEstadoFlujoPFE(idPerfil,idPrograma,esJefe, cUsuario, esDirector, false);
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        // Sumar 2 segundos
        calendar.add(Calendar.SECOND, 2);
        
        Date horaMasDosSegundos = calendar.getTime();
		
		HistorialProgramaPFE historialNEW = new HistorialProgramaPFE();
		historialNEW.setFRegistro(horaMasDosSegundos);
		historialNEW.setIdUsuario(cUsuario);
		historialNEW.setIdProgramaFPE(idPrograma);
		historialNEW.setIdFlujo(flujo.get(0).getId());    	
    	historialPFERepository.save(historialNEW);		
		
	}
	
	

	@Override
	public List<AutorizacionFlujoSiguiente> getSiguienteEstadoFlujoPFE(Long idPerfil, Long idPrograma, Integer esJefe, Long cUsuario, Integer esDirector, boolean esMasivo) {		
		
		List<AutorizacionFlujoSiguienteProjection> estados = autorizacionFlujoRepository.findSiguienteAutorizacionFlujoPFE(idPerfil, 
				                                                                                                           idPrograma,
				                                                                                                           esJefe,
				                                                                                                           cUsuario,
				                                                                                                           esDirector,
				                                                                                                           esMasivo?1:0);
		
		return estados.stream().map(entity -> modelMapper.map(entity, AutorizacionFlujoSiguiente.class)).collect(Collectors.toList());
	}

	@Override
	public List<HistoricoFlujoAutorizacion> getHistoricoFlujoAutorizacionPFE(Long id) {
		
		List<HistoricoFlujoAutorizacionProjection> listadohistorico = programasPFERepository.getHistoricoFlujoAutorizacionPFE(id);
		
		return listadohistorico.stream().map(entity -> modelMapper.map(entity, HistoricoFlujoAutorizacion.class)).collect(Collectors.toList());
	}

	@Override
	public HistorialProgramaPFE createSiguienteEstadoFlujoPfeAnexo(
			AutorizacionesAnexosHistorialDto historialAnexoDto, Long idPerfil, Long xUsuarioDelphos) {
		
		HistorialProgramaPFE historial = new HistorialProgramaPFE();
		historial.setIdProgramaFPE(historialAnexoDto.getAutorizacionAnexo().getId());
		historial.setIdFlujo(historialAnexoDto.getFlujo().getId());
		historial.setFRegistro(new Date());
		historial.setIdUsuario(xUsuarioDelphos);
		historial.setDObservaciones(historialAnexoDto.getObservaciones());		
		
		historialPFERepository.save(historial);
		
		return historial;	 
	}
	
	@Override
	public HistorialProgramaPFE createSiguienteEstadoFlujoPfePrograma(
			Long idPrograma, Long idFlujo, Long xUsuarioDelphos) {
		
		HistorialProgramaPFE historial = new HistorialProgramaPFE();
		historial.setIdProgramaFPE(idPrograma);
		historial.setIdFlujo(idFlujo);
		historial.setFRegistro(new Date());
		historial.setIdUsuario(xUsuarioDelphos);		
		
		historialPFERepository.save(historial);
		
		return historial;	 
	}
	@Override
	public List<AutorizacionFlujoSiguiente> getSiguienteEstadoFlujoPFEDelegacion() {

		List<AutorizacionFlujoSiguienteProjection> estados = autorizacionFlujoRepository.getSiguienteEstadoFlujoPFEDelegacion();

		return estados.stream().map(entity -> modelMapper.map(entity, AutorizacionFlujoSiguiente.class)).collect(Collectors.toList());
	}

}
