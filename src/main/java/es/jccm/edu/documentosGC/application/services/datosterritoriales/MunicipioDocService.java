package es.jccm.edu.documentosGC.application.services.datosterritoriales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.documentosGC.adapter.out.repositories.datosterritoriales.MunicipioDocRepository;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import es.jccm.edu.documentosGC.application.ports.in.datosterritoriales.IMunicipioDocService;

@Service
public class MunicipioDocService implements IMunicipioDocService {
	
	@Autowired
	private MunicipioDocRepository municipioRepository;

	@Override
	public List<MunicipioDoc> findMunicipioByProvincia(Long idProvincia) {
		List<MunicipioDoc> res = municipioRepository.findByIdProvincia(idProvincia);
		
		return res;
	}

	@Override
	public List<MunicipioDoc> getMunicipioProvinciaZona(Long idProvincia, Long idPerfil, Long idUsuario) {
		List<MunicipioDoc> res = municipioRepository.getMunicipioProvinciaZona(idProvincia, idPerfil, idUsuario);
		
		return res;
	}

	@Override
	public List<MunicipioDoc> getMunicipioProvinciaCentro(Long idProvincia, Long xEmpleado, String fTomaPos) {
		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date d_fTomapos = formato.parse(fTomaPos);
			
			List<MunicipioDoc> res = municipioRepository.getMunicipioProvinciaCentro(idProvincia,xEmpleado,d_fTomapos);
			
			return res;
			
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		
		return null;		
	}

	@Override
	public List<MunicipioDoc> getMunicipioInspectorProvincial(Long idProvincia, Long idPerfil, Long idUsuario) {
		List<MunicipioDoc> res = municipioRepository.getMunicipioInspectorProvincial(idProvincia, idPerfil, idUsuario);
		
		return res;
	}

	@Override
	public List<MunicipioDoc> getMunicipioConsejeria(Long idProvincia) {
		List<MunicipioDoc> res = municipioRepository.getMunicipioConsejeria(idProvincia);
		
		return res;
	}
	

}
