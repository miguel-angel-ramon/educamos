package es.jccm.edu.documentosGC.application.services.firmantesdigital;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.adjuntosdocumento.AdjuntosDocumentoRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.documentosGC.DocumentoGCRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.estadodoc.EstadoDocumentoGCRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.firmantesdigital.AdjuntosFirmantesRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.historicodoc.HistoricoDocumentoGCRepository;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.AdjuntosDocumento;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.DetalleFirmante;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoFlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.EstadoFlujoDocumentoGCProjection;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.AdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.FirmanteRequest;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.projection.DetalleFirmanteProjection;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;
import es.jccm.edu.documentosGC.application.ports.in.firmantesdigital.IFirmantesDigitalService;
import es.jccm.edu.documentosGC.application.ports.out.DgcEmpleadoRepository;
import es.jccm.edu.documentosGC.application.ports.out.FlujoDocumentoGCRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.EmpleadoRepository;

@Service
public class FirmantesDigitalService implements IFirmantesDigitalService {
	
	@Autowired
	private AdjuntosFirmantesRepository adjuntosFirmantesRepository;
	
	@Autowired
	private DgcEmpleadoRepository empleadoRepository;
	
	@Autowired
	private AdjuntosDocumentoRepository adjuntosDocumentoRepository;
	
	@Autowired
	private EstadoDocumentoGCRepository  estadoDocumentoGCRepository;
	
	@Autowired
	DocumentoGCRepository documentoGCRepository;
	
	@Autowired
	FlujoDocumentoGCRepository flujoDocumentoGCRepository;
	
	@Autowired
	HistoricoDocumentoGCRepository historicoDocumentoGCRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<AdjuntosFirmantes> createAdjuntosFirmantes(List<FirmanteRequest> firmantesActas, Long idAdjunto, String conTutor) {
		
		List<AdjuntosFirmantes> firmantes = new ArrayList<>();
		
		Optional<AdjuntosDocumento> idjuntosDocumento =  adjuntosDocumentoRepository.findById(idAdjunto);	
		
		for (int i = 0; i < firmantesActas.size(); i++) {
			
			AdjuntosFirmantes adjuntoFirmante = new AdjuntosFirmantes();
			Optional<DgcEmpleado> empleado = empleadoRepository.findById(firmantesActas.get(i).getId());
			
			adjuntoFirmante.setAdjunto(idjuntosDocumento.orElse(null));
			
			if (firmantesActas.get(i).getTipo().equals("Director")) {
				
				if (conTutor.equals("S")) adjuntoFirmante.setOrden(3);	
				else adjuntoFirmante.setOrden(2);
				
			} else if (firmantesActas.get(i).getTipo().equals("Tutor")) {
				
				adjuntoFirmante.setOrden(2);
				
			} else {
				
				adjuntoFirmante.setOrden(1);
			}
			
			adjuntoFirmante.setEmpleado(empleado.orElse(null));
			adjuntoFirmante.setTipoFirma("D");
			adjuntoFirmante.setIsFirmado(0);
			adjuntoFirmante.setDescripcion(firmantesActas.get(i).getTipo());
			
			adjuntosFirmantesRepository.save(adjuntoFirmante);
		}	
		
		return firmantes;
	}


	@Override
	public void deleteByIdAdjunto(Long idAdjunto) {		
		adjuntosFirmantesRepository.deleteAllByAdjuntoId(idAdjunto);
	}
	
	@Override
	public void deleteById(Long idFirmante) {
		adjuntosFirmantesRepository.deleteById(idFirmante);		
	}


	@Override
	public Boolean actualizarDocumentoFirmado(Long idAdjunto, Long idPerfil, Long idEmpleado, Long idUsuario, String tipoFirma) {		
		
		Optional<AdjuntosFirmantes> adjuntoO = adjuntosFirmantesRepository.findByAdjuntoIdAndEmpleadoId(idAdjunto,idEmpleado);
		
		if (adjuntoO.isPresent()) 
		{
			Date date = new Date();
			adjuntoO.get().setIsFirmado(1);
			adjuntoO.get().setFechaFirma(date);
			adjuntoO.get().setTipoFirma(tipoFirma);
			AdjuntosFirmantes adjunto = adjuntoO.get();			
			
			// ACTUALIMOS EL FIRMANTE
			adjuntosFirmantesRepository.save(adjunto);
			
			if (adjuntosFirmantesRepository.algunFirmanteSinFirma(idAdjunto) == 0) 
			{				
				Optional<AdjuntosDocumento> adjuntoODoc = adjuntosDocumentoRepository.findById(idAdjunto);
				
				if (adjuntoODoc.isPresent()) 
				{
					AdjuntosDocumento adjuntoDoc = adjuntoODoc.get();
					try {						
						
						adjuntoDoc.setFirmado(1);
						// SI ES EL ULTIMO FIRMATE, ACTUALIZAMOS EL HISTORICO
						adjuntosDocumentoRepository.save(adjuntoDoc);	
						
						if (adjuntoDoc.getTipo().getPrincipal() == 1) {
							
							Optional<DocumentosGC> documento = documentoGCRepository.findById(adjuntoDoc.getHistorial().getDocumento().getId());					
							
							List<EstadoFlujoDocumentoGCProjection> estadoP = estadoDocumentoGCRepository.estadosPosiblesDocumentosGC(idPerfil,
																																	 adjuntoDoc.getHistorial().getDocumento().getIdTipoDocumento().getId(),
																																	 documento.isPresent()?documento.get().getId():-1,
																																	 "S");
							
							List<EstadoFlujoDocumentoGC> estado = estadoP.stream().map(entity -> modelMapper.map(entity, EstadoFlujoDocumentoGC.class)).collect(Collectors.toList());		
							
							Long idFlujo = estado.get(0).getIdFlujo();
							Optional<FlujoDocumentoGC> flujo = flujoDocumentoGCRepository.findById(idFlujo);					
							
							HistoricoDocumentoGC historico = new HistoricoDocumentoGC();
							historico.setDocumento(documento.isPresent()?documento.get():null);
							historico.setFlujo(flujo.isPresent()?flujo.get():null);
							historico.setRegistro(date);
							historico.setUsuario(idUsuario);	
							// CAMBIAMOS EL ESTADO DEL DOCUMENTO
							historicoDocumentoGCRepository.save(historico);
							
						}					  

					  } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							adjuntoDoc.setFirmado(0);
							adjuntosDocumentoRepository.save(adjuntoDoc);
							adjunto.setIsFirmado(0);
							adjunto.setFechaFirma(null);
							
					  }					
				}				
			}			
			
			return true;
			
		} else {
			
			return false;
		}

	}
	
	@Override
	public AdjuntosFirmantes createAdjuntosFirmante(Long idAdjunto, Long idEmpleado) 
	{
			
		Optional<AdjuntosDocumento> adjunto = adjuntosDocumentoRepository.findById(idAdjunto);
		Optional<DgcEmpleado> empleado = empleadoRepository.findById(idEmpleado);
		
		AdjuntosFirmantes firmante = new AdjuntosFirmantes(); 
		if (adjunto.isPresent()) 
		{
			firmante.setAdjunto(adjunto.get());
			firmante.setEmpleado(empleado.get());
			firmante.setOrden(1);
			firmante.setTipoFirma("D");
			firmante.setIsFirmado(0);
			firmante.setDescripcion("Director");
			
			adjuntosFirmantesRepository.save(firmante);	
			
		}
		
		return firmante;
	}
	
	public List<DetalleFirmante> getDetalleFirmantes(Long idAdjunto) {

		List<DetalleFirmanteProjection> detalleFirmanteP = adjuntosFirmantesRepository.getDetalleFirmantes(idAdjunto);

		return detalleFirmanteP.stream().map(entity -> modelMapper.map(entity, DetalleFirmante.class)).collect(Collectors.toList());
	}


	@Override
	public Optional<AdjuntosFirmantes> findByAdjuntoIdAndEmpleadoId(Long idAdjunto, Long idEmpleado) {
		return adjuntosFirmantesRepository.findByAdjuntoIdAndEmpleadoId(idAdjunto, idEmpleado);
	}


	@Override
	public List<String> getEntornoFirma() {
		List<String> entornoFirma = adjuntosFirmantesRepository.getEntornoFirma();
		return entornoFirma;
	}


	@Override
	public List<AdjuntosFirmantes> createAdjuntosFirmantesPartes(List<FirmanteRequest> firmantesPartes,
																 Long idAdjunto) {
		List<AdjuntosFirmantes> firmantes = new ArrayList<>();
		
		Optional<AdjuntosDocumento> idjuntosDocumento =  adjuntosDocumentoRepository.findById(idAdjunto);	
		
		for (int i = 0; i < firmantesPartes.size(); i++) {
			
			AdjuntosFirmantes adjuntoFirmante = new AdjuntosFirmantes();
			Optional<DgcEmpleado> empleado = empleadoRepository.findById(firmantesPartes.get(i).getId());
			
			adjuntoFirmante.setAdjunto(idjuntosDocumento.orElse(null));
			
			if (firmantesPartes.get(i).getTipo().equals("Inspector")) {
				
				 adjuntoFirmante.setOrden(2);
				
			} else {
				
				adjuntoFirmante.setOrden(1);
			}
			
			adjuntoFirmante.setEmpleado(empleado.orElse(null));
			adjuntoFirmante.setTipoFirma("D");
			adjuntoFirmante.setIsFirmado(0);
			adjuntoFirmante.setDescripcion(firmantesPartes.get(i).getTipo());
			
			adjuntosFirmantesRepository.save(adjuntoFirmante);
		}	
		
		return firmantes;
	}
    @Override
    public Integer getNumeroFirmados(Long idAdjunto) {
        return adjuntosFirmantesRepository.getNumeroFirmados(idAdjunto);
    
    }

}
