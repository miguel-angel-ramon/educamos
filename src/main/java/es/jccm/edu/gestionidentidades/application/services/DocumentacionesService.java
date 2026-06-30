package es.jccm.edu.gestionidentidades.application.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.DocumentacionesDto;
import es.jccm.edu.gestionidentidades.adapter.out.repository.DocumentacionesRepository;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.ports.in.IDocumentacionesService;
import es.jccm.edu.gestionidentidades.application.ports.in.ValidaNifServicePortIn;

@Transactional
@Service
public class DocumentacionesService implements IDocumentacionesService{
	
	@Autowired
	private DocumentacionesRepository documentacionesRepository;
	
	@Autowired
	private ValidaNifServicePortIn validadorNif;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public Documentaciones save(Documentaciones documentaciones) {
		return documentacionesRepository.save(documentaciones);
	}
	
	@Override
	public List<Documentaciones> findByOidDocumentacion(Long idDocumentacion) {
		return documentacionesRepository.findByIdDocumentacion(idDocumentacion);
	}

	@Override
	public Documentaciones findByNif(String nif) {
		return documentacionesRepository.findByNif(nif);
	}

	@Override
	public Documentaciones construirDocumentacion(AltaUsuarioGlobalRequest request) {

		Documentaciones documentacion = new Documentaciones();
		documentacion.setIdentificacion(request.getIdentificacion());
		documentacion.setPersona(request.getPersona());
		documentacion.setTipoDocumentacion(1L);
		
		return documentacion;
	}
	
	@Override
	public DocumentacionesDto modificarDocumentacion(DocumentacionesDto documentacionDto) {
		
		Documentaciones documentacion = toDocumentacionesEntity(documentacionDto);
		//TODO fixear
		List<Documentaciones>  doc = findByOidDocumentacion(documentacion.getId());
		
		if(doc.isEmpty()){
			return null;
		}else {		
			
			boolean validado = validaDocumentacion(documentacion);
			
			if(validado == true && (documentacion.getTipoDocumentacion() == 1L || documentacion.getTipoDocumentacion() == 2L || documentacion.getTipoDocumentacion() == 3L)) {
				documentacionesRepository.save(documentacion);
				return toDocumentacionesDto(documentacion);
			}else {
				throw new RuntimeException("El formato del identificador no es válido");
			}			
		}		
	}
	
	@Override
	public boolean validaDocumentacion(Documentaciones documentacion) {
		
		boolean validado = validadorNif.validarSinLimpiar(documentacion.getIdentificacion());
		
		return validado;		
	}
	
	private DocumentacionesDto toDocumentacionesDto(Documentaciones documentacion) {
        return modelMapper.map(documentacion, DocumentacionesDto.class);
    }

    private Documentaciones toDocumentacionesEntity(DocumentacionesDto DocumentacionesDto) {
        return modelMapper.map(DocumentacionesDto, Documentaciones.class);
    }

}
