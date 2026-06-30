package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.SecuenciacionProyectosRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.ISecuenciacionProyectosService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;



@Service
public class SecuenciacionProyectosService implements ISecuenciacionProyectosService {

	@Autowired
	private SecuenciacionProyectosRepository secuenciacionProyectosRepository;
	
	@Autowired
	private IRodalClient rodalclient;
	
	@Override
	public SecuenciacionProyectos createSecuenciacionProyectos(SecuenciacionProyectos secuenciacionProyectosIn) {
		return secuenciacionProyectosRepository.save(secuenciacionProyectosIn);
	}
	
	// Other methods
		private String crearMessage(String nameClass, String id) {
			return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
		}
	
	@Override
	public SecuenciacionProyectos updateSecuenciacionProyecto(SecuenciacionProyectos secProyecto) {
		
		Optional<SecuenciacionProyectos> secProyectoUpdate = secuenciacionProyectosRepository.findById(secProyecto.getId());
		
		if(secProyectoUpdate.isPresent()) {
			secProyectoUpdate.get().setTxProyecto(secProyecto.getTxProyecto());
			secProyectoUpdate.get().setProyecto(secProyecto.getProyecto());
						
			secuenciacionProyectosRepository.save(secProyectoUpdate.get());
		}else {
			String mes = crearMessage(SecuenciacionProyectos.class.getSimpleName(), secProyecto.getId().toString());
			throw new NotFoundException(mes);
		}
		
		return secProyectoUpdate.get();
	}
	
	@Transactional
	public void deleteSecuenciacionProyectos(Long idSecProy) {
		secuenciacionProyectosRepository.deleteById(idSecProy);
	}
	
	@Override
	public Optional<SecuenciacionProyectos> getSecuenciacionProyectoId(Long idProyecto) {
		
		Optional<SecuenciacionProyectos> secProyecto = secuenciacionProyectosRepository.findByProyectoId(idProyecto);		
		
		return secProyecto;
		
	}
	
	@Override
	public SecuenciacionProyectos getSecuenciacionById(Long idSecuenciacion) {
		Optional<SecuenciacionProyectos> res = secuenciacionProyectosRepository.findById(idSecuenciacion);
		
		return res.isPresent() ? res.get() : null;
	}
	
	@Override
	public SecuenciacionProyectos createDocumentoGC(SecuenciacionProyectos documento, MultipartFile file, Long cAnno, Long idCentro) throws RodalExceptionService, Exception  {
		
		SecuenciacionProyectos newdocumento = null;
		RespuestaInsertarDoc docUp = null;
		
		try {			
					
			// guardamos la entidad Documento 
			if (documento.getId() == null) {
				newdocumento = createSecuenciacionProyectos(documento);
			} else {
				newdocumento = this.getSecuenciacionById(documento.getId());
			}						
		
							
			if (file != null) {
//				if (documento.getId() == null) {
					
					
//					docUp = rodalclient.insertaDoc(file,"MFCT", "SP_",documento.getId(), cAnno);
					
//				}else 
				if (documento.getId() != null){
					if(newdocumento.getIdSecficRodal() == null) {
						docUp = rodalclient.insertaDoc(file, "MFCT", "SP_",newdocumento.getId(), cAnno, -1L, -1L,"-1",-1L,-1L);
						newdocumento = updateSecuenciacionProyecto(documento);						
						
					}else {
						rodalclient.actualizaDoc(file, "MFCT", "SP_", newdocumento.getId(),"-1",-1L,-1L);
						newdocumento = updateSecuenciacionProyecto(documento);
						
					}		
					
				}
				
			} else {
				if (documento.getId() != null) {
					newdocumento.setTxProyecto(documento.getTxProyecto());
					newdocumento = updateSecuenciacionProyecto(newdocumento);
				}
				
			}
			
			return newdocumento;			
			
			
			} catch (Exception e) {
				if (documento.getId() == null) {
			
				secuenciacionProyectosRepository.delete(documento);
				}
				
				throw new Exception();		
		    }	
				
	}
	
	@Override
	public SecuenciacionProyectos getSecuenciacionByIdSecficRodal(String idRodal) {
		Optional<SecuenciacionProyectos> res = secuenciacionProyectosRepository.findByIdSecficRodal(idRodal);
		
		return res.isPresent() ? res.get() : null;
	}
	
	
	

}
