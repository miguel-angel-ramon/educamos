package es.jccm.edu.documentosGC.application.services.bandejafirmas;

import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConvertUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import es.jccm.edu.documentosGC.adapter.out.repositories.bandejafirmas.BandejaFirmasRepository;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.BandejaFirmasList;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.EstadosBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoAdjuntoBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoDocumentoBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.BandejaFirmasListProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.EstadosBandejaProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.TipoAdjuntoBandejaProjection;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.projection.TipoDocumentoBandejaProjection;
import es.jccm.edu.documentosGC.application.ports.in.bandejafirmas.IBandejaFirmasService;


@Service
public class BandejaFirmasService implements IBandejaFirmasService {
	
	@Autowired
	private BandejaFirmasRepository bandejaFirmasRepository;
	
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public List<TipoDocumentoBandeja> getTiposDocumentoBandeja(Long cAnno) {
		
		List<TipoDocumentoBandejaProjection> tiposP = bandejaFirmasRepository.getTiposDocumentoBandeja(cAnno); 

        return tiposP.stream().map(entity -> modelMapper.map(entity, TipoDocumentoBandeja.class)).collect(Collectors.toList());
		
		
	}

	@Override
	public List<TipoAdjuntoBandeja> getTiposAdjuntosBandeja(Long cAnno, Long idTipoDocumento) {
		
		List<TipoAdjuntoBandejaProjection> tiposP = bandejaFirmasRepository.getTiposAdjuntosBandeja(cAnno,idTipoDocumento); 

        return tiposP.stream().map(entity -> modelMapper.map(entity, TipoAdjuntoBandeja.class)).collect(Collectors.toList());
	}

	@Override
	public List<EstadosBandeja> getEstadosDocumentoBandeja(Long cAnno, Long idTipoDocumento) {
		
		List<EstadosBandejaProjection> estadosP = bandejaFirmasRepository.getEstadosDocumentoBandeja(cAnno,idTipoDocumento); 

        return estadosP.stream().map(entity -> modelMapper.map(entity, EstadosBandeja.class)).collect(Collectors.toList());
	}

	@Override
	public List<BandejaFirmasList> getAllDocumentosBandeja(Long cAnno, 
														   Long idTipoDocumento, 
														   Long idTipoAdjunto,
														   Long idEstado,	
														   String miFirma, 
														   Long idEmpleado,
														   String fechaGeneracion,
														   String fechaFirma,
														   String perfil) {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formato.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		Date fechaInicioGeneracion = null;
		Date fechaFinGeneracion = null;
		Date fechaInicioFirma = null;
		Date fechaFinFirma = null;
		
		String[] fechasGeneracion = fechaGeneracion.split(",");
		String[] fechasFirma = fechaFirma.split(",");
		try {
			if(fechasGeneracion.length == 2 && (!fechasGeneracion[0].equals("-1") && !fechasGeneracion[1].equals("-1"))) {
				fechaInicioGeneracion = formato.parse(fechasGeneracion[0] + " 00:00:00");
				fechaFinGeneracion = formato.parse(fechasGeneracion[1] + " 23:59:59");
			}
			
			if(fechasFirma.length == 2 && (!fechasFirma[0].equals("-1") && !fechasFirma[1].equals("-1"))) {
				fechaInicioFirma = formato.parse(fechasFirma[0] + " 00:00:00");
				fechaFinFirma = formato.parse(fechasFirma[1] + " 23:59:59");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<String> perfiles = Arrays.asList(perfil.split(","));
		
		List<BandejaFirmasListProjection> firmasP = bandejaFirmasRepository.getAllDocumentosBandeja(cAnno, 
																					   			    idTipoDocumento, 
																					   			    idTipoAdjunto, 
																								    idEstado,
																								    miFirma.equals("true")?1:0,
																								    idEmpleado,
																								    fechaInicioGeneracion,
																								    fechaFinGeneracion,
																								    fechaInicioFirma,
																								    fechaFinFirma,
																								    perfiles); 

		return firmasP.stream().map(x -> modelMapper.map(x,BandejaFirmasList.class)).collect(Collectors.toList());
	}
	
	

}
