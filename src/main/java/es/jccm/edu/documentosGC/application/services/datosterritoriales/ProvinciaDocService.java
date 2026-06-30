package es.jccm.edu.documentosGC.application.services.datosterritoriales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.datosterritoriales.ProvinciaDocRepository;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.ProvinciaDoc;
import es.jccm.edu.documentosGC.application.ports.in.datosterritoriales.IProvinciaDocService;

@Service
public class ProvinciaDocService implements IProvinciaDocService {

	@Autowired
	private ProvinciaDocRepository provinciaRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ProvinciaDoc findProvinciaById(Long id) {
		Optional<ProvinciaDoc> res = provinciaRepository.findById(id);
		
		return res.isPresent() ? modelMapper.map(res.get(), ProvinciaDoc.class) : null;
	}

	@Override
	public List<ProvinciaDoc> getListadoProvincias() {
		return provinciaRepository.findAllByEsManchega("S") ;
	}
		
	@Override
	public List<ProvinciaDoc> getListadoProvinciasZona(Long idPerfil, Long idUsuario) {
		
		return provinciaRepository.getListadoProvinciasZona(idPerfil,idUsuario) ; 		
		
	}

	@Override
	public List<ProvinciaDoc> getListadoProvinciasCentro(Long xEmpleado, String fTomapos) {
		
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date d_fTomapos = formato.parse(fTomapos);
			
			return provinciaRepository.getListadoProvinciasCentro(xEmpleado,d_fTomapos) ;
			
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		
		return null;		
		
	}

	@Override
	public List<ProvinciaDoc> getListadoProvinciasProvincial(Long idPerfil, Long idUsuario, Long idCentroProvincia) {
		return provinciaRepository.getListadoProvinciasProvincial(idPerfil,idUsuario,idCentroProvincia) ; 
	}

}

