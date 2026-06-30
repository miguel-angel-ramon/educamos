package es.jccm.edu.documentosGC.application.ports.in.plazoentrega;

import es.jccm.edu.documentosGC.application.domain.plazoentrega.entities.PlazosEntrega;

public interface IPlazosEntregaService {

	PlazosEntrega findById(Long idPlazo);

	PlazosEntrega createPlazosEntrega(PlazosEntrega plazosEntrega);

	PlazosEntrega findByCAnnoAndTipoId(Long idTipoDocumento, Integer cAnno);

}
