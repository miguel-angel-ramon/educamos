package es.jccm.edu.proyectosfct.application.ports.in.programasPFE.anexosPFE;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.AnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.InfoAnexos;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface IAnexosFPE  {

	void createModulosFPE(Long idProgramaFPE, List<Integer> tipos, List<MultipartFile> files, Integer cAnno, Long xCentro) throws RodalExceptionService, InsertarDocFault, IOException ;

	List<InfoAnexos> getInfoAnexosAut(Long idProgramaFPE);
	
	List<AnexosProgPFE> getAnexosAutTipo(Long idProgramaFPE, Integer tipo);

	void deleteAnexo(Long id) throws RodalExceptionService, InsertarDocFault, IOException;

	void actualizaAnexo(Long id, MultipartFile file) throws RodalExceptionService, InsertarDocFault, IOException;

	void insertaAnexo(Long idProgramaFPE, Integer cAnno, Long xCentro, Integer tipo, MultipartFile file) throws RodalExceptionService, InsertarDocFault, IOException;

}
