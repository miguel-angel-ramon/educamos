package es.jccm.edu.documentosGC.application.services.centrodoc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.centrodoc.CentroDocInspeccionRepository;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocInspeccion;
import es.jccm.edu.documentosGC.application.ports.in.centrodoc.ICentroDocInspeccionService;

@Service
public class CentroDocInspeccionService implements ICentroDocInspeccionService {
	
	@Autowired
	private CentroDocInspeccionRepository centroDocInspeccionRepository;
	
	

	@Override
	public List<CentroDocInspeccion> getCentroMunicipio(Long c_provincia, Long c_municipio) {
		// TODO Auto-generated method stub
		return centroDocInspeccionRepository.getCentroMunicipio(c_provincia, c_municipio);
	}



	@Override
	public List<CentroDocInspeccion> getListadoCentrosMunicipioZona(Long c_provincia, Long c_municipio, Long idPerfil,
			Long idUsuario) {
		return centroDocInspeccionRepository.getListadoCentrosMunicipioZona(c_provincia, c_municipio, idPerfil, idUsuario  );
	}



	@Override
	public List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorCentro(Long c_provincia, Long c_municipio, Long xEmpleado, String fTomaPos ) {
		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date d_fTomapos = formato.parse(fTomaPos);
			
			return centroDocInspeccionRepository.getListadoCentrosMunicipioInspectorCentro(c_provincia,c_municipio,xEmpleado,d_fTomapos);
			
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		
		return null;		
	}

	@Override
	public List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorProvincial(Long c_provincia, Long c_municipio,
			Long idPerfil, Long idUsuario) {
		return centroDocInspeccionRepository.getListadoCentrosMunicipioInspectorProvincial(c_provincia, c_municipio, idPerfil, idUsuario);
	}



	@Override
	public List<CentroDocInspeccion> getListadoCentrosConsejeria(Long c_provincia, Long c_municipio) {
		return centroDocInspeccionRepository.getListadoCentrosConsejeria(c_provincia, c_municipio);
	}

}