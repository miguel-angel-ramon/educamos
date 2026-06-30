package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.io.IOException;
import java.util.Optional;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.SecuenciacionProyectosRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.InformacionProyectosRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.InformacionProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IInformacionProyectosService;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;


@Service
public class InformacionProyectosService implements IInformacionProyectosService {

	@Autowired
	private InformacionProyectosRepository infoProyectosRepository;

	@Autowired
	private SecuenciacionProyectosRepository secuenciacionProyectosRepository;

	@Autowired
	private IRodalClient rodalclient;
	
		
	@Override
	public InformacionProyectos createInfoProyecto(InformacionProyectos infoProyectoIn, MultipartFile file, Long anno) throws Exception {

		InformacionProyectos infoProgramaSave = null;

		InformacionProyectos infoProyecto = infoProyectosRepository.findByProyectoId(infoProyectoIn.getProyecto().getId()).orElse(null);

		try{

			if (infoProyecto != null) {

				infoProyecto.setTx_introduccion(infoProyectoIn.getTx_introduccion());
	//			infoProyecto.get().setTx_objetivos(infoProyectoIn.getTx_objetivos());
				infoProyecto.setLb_objetivos(infoProyectoIn.getLb_objetivos());
				infoProyecto.setTx_metodologia(infoProyectoIn.getTx_metodologia());
	//			infoProyecto.get().setTx_evaluacion(infoProyectoIn.getTx_evaluacion());
				infoProyecto.setLb_evaluacion(infoProyectoIn.getLb_evaluacion());
				infoProyecto.setLb_coordinacion(infoProyectoIn.getLb_coordinacion());
				infoProyecto.setTx_otros(infoProyectoIn.getTx_otros());
				infoProyecto.setSecuenciacionProy(createSecuenciacionProyecto(infoProyectoIn, file, anno));

				infoProgramaSave = infoProyectosRepository.save(infoProyecto);

			} else {
				infoProyecto = infoProyectosRepository.save(infoProyectoIn);
				infoProyecto.setSecuenciacionProy(createSecuenciacionProyecto(infoProyectoIn, file, anno));
				infoProgramaSave = infoProyecto;
			}

			return infoProgramaSave;

		} catch (Exception e) {

			if (infoProyectoIn.getSecuenciacionProy().getId() == null) {

				secuenciacionProyectosRepository.delete(infoProyectoIn.getSecuenciacionProy());
			}

			throw new Exception();
		}
	}

	private SecuenciacionProyectos createSecuenciacionProyecto(InformacionProyectos infoProyectoIn, MultipartFile file, Long anno) throws RodalExceptionService, InsertarDocFault, IOException {
		SecuenciacionProyectos newSecuenciacionProy = null;
		RespuestaInsertarDoc docUp = null;

		if (infoProyectoIn.getSecuenciacionProy().getId() == null) {

			newSecuenciacionProy = secuenciacionProyectosRepository.save(infoProyectoIn.getSecuenciacionProy());

		} else {

			newSecuenciacionProy = secuenciacionProyectosRepository.findById(infoProyectoIn.getSecuenciacionProy().getId()).orElse(null);

		}

		if (file != null && infoProyectoIn.getSecuenciacionProy().getId() != null && newSecuenciacionProy != null) {

			if (newSecuenciacionProy.getIdSecficRodal() == null) {

				docUp = rodalclient.insertaDoc(file, "MFCT", "SP_", newSecuenciacionProy.getId(), anno, infoProyectoIn.getProyecto().getCentro().getId(), -1L,"-1",-1L,-1L);
				newSecuenciacionProy.setIdSecficRodal(docUp.getIdDoc());
				newSecuenciacionProy.setTxSecficFichero(file.getOriginalFilename());


			} else {

				rodalclient.actualizaDoc(file, newSecuenciacionProy.getIdSecficRodal());

			}

			newSecuenciacionProy.setTxProyecto(infoProyectoIn.getSecuenciacionProy().getTxProyecto());
			newSecuenciacionProy.setProyecto(infoProyectoIn.getSecuenciacionProy().getProyecto());
			newSecuenciacionProy.setTxSecficFichero(file.getOriginalFilename());

			infoProyectoIn.setSecuenciacionProy(secuenciacionProyectosRepository.save(newSecuenciacionProy));

		} else {

			if (infoProyectoIn.getSecuenciacionProy().getId() != null && newSecuenciacionProy != null) {

				newSecuenciacionProy.setTxProyecto(infoProyectoIn.getSecuenciacionProy().getTxProyecto());
				newSecuenciacionProy.setProyecto(infoProyectoIn.getSecuenciacionProy().getProyecto());

				if(infoProyectoIn.getSecuenciacionProy().getIdSecficRodal() != null && infoProyectoIn.getSecuenciacionProy().getTxSecficFichero() != null){

					rodalclient.borrarDocumento(infoProyectoIn.getSecuenciacionProy().getIdSecficRodal());

				}

				newSecuenciacionProy.setIdSecficRodal(null);
				newSecuenciacionProy.setTxSecficFichero(null);
				
				secuenciacionProyectosRepository.save(newSecuenciacionProy);

			}

		}
		return newSecuenciacionProy;
	}

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

	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}
	
	
	@Override
	public Optional<InformacionProyectos> getInfoProyectoId(Long idProyecto) {
		
		Optional<InformacionProyectos> infoProyecto = infoProyectosRepository.findByProyectoId(idProyecto);		
		
		return infoProyecto;
		
	}


	@Override
	public void deleteInformacionProyecto(Long id) {
		
		infoProyectosRepository.deleteById(id);
		
	}


	@Override
	public Integer countActividadesProyecto(Long idProyecto) {
		return infoProyectosRepository.countByProyectoId(idProyecto);
	}

}
