package es.jccm.edu.buzon.application.services.buzonCentro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.buzon.adapter.out.repository.buzonCentro.BuzonCentroShRepository;
import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.projection.UnidadBuzonCentroProjection;
import es.jccm.edu.buzon.application.ports.in.buzonCentro.IBuzonCentroShService;

@Service
public class BuzonCentroShService implements IBuzonCentroShService{
	
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private BuzonCentroShRepository buzonCentroRepository;
	
	public List<UnidadBuzonCentro> getUnidadesCentroCompClave(Long idEmpleado, String fechaTomaPosesion, Long idCentro, Long anno, Boolean direccion) {
        List<UnidadBuzonCentroProjection> unidadesBuzonCentroProjection = new ArrayList<>();

        if(direccion) {
        	unidadesBuzonCentroProjection = buzonCentroRepository.getUnidadesBuzonCentroCompClaveDirector(idCentro, anno);
        } else {
        	unidadesBuzonCentroProjection = buzonCentroRepository.getUnidadesBuzonCentroCompClave(idEmpleado, fechaTomaPosesion, idCentro, anno);
        }

        List<UnidadBuzonCentro> unidadesValoracion = unidadesBuzonCentroProjection.stream().map(unidad -> modelMapper.map(unidad, UnidadBuzonCentro.class)).collect(Collectors.toList());

        for (UnidadBuzonCentro unidadBuzonCentro: unidadesValoracion) {
            if(unidadBuzonCentro.getEtapa().equals("Bachillerato")) {
            	unidadBuzonCentro.setIdEtapa(unidadBuzonCentro.getIdCiclo());
            }
        }

        return unidadesValoracion;
    }

}
