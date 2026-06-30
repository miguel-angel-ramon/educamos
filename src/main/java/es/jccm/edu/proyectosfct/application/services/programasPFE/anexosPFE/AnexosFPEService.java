package es.jccm.edu.proyectosfct.application.services.programasPFE.anexosPFE;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.anexosPFERepository.AnexosFPERepository;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.AnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.InfoAnexos;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.InfoAnexosProjection;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.anexosPFE.IAnexosFPE;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;


@Service
public class AnexosFPEService implements IAnexosFPE {
	
	private static final String TIPO = "ANE_FPE_";
	private static final String ENTIDAD = "MFCT";
	
    @Autowired
    private AnexosFPERepository anexosFPERepository;
    
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IRodalClient rodalClient;

	@Override
	public void createModulosFPE(Long idProgramaFPE, List<Integer> tipos, List<MultipartFile> files, Integer cAnno, Long xCentro) throws RodalExceptionService, InsertarDocFault, IOException {
		
		int i = 0;
		for (Integer tipo : tipos) {				
			AnexosProgPFE anexo = new AnexosProgPFE();
			anexo.setIdProgramaFPE(idProgramaFPE);
			anexo.setIdTipAutPro(tipo);			
			anexosFPERepository.save(anexo);
				
			rodalClient.insertaDoc(
					files.get(i), ENTIDAD, TIPO, anexo.getId(), Long.valueOf(cAnno),
					xCentro, -1L, "-1", -1L,
					-1L);	
			i++;
		}		
	}

	@Override
	public List<InfoAnexos> getInfoAnexosAut(Long idProgramaFPE) {
		List<InfoAnexosProjection> anexosP = anexosFPERepository.getInfoAnexosAut(idProgramaFPE);
		
		return anexosP.stream().map(entity -> modelMapper.map(entity, InfoAnexos.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<AnexosProgPFE> getAnexosAutTipo(Long idProgramaFPE, Integer tipo) {
		return anexosFPERepository.findByIdProgramaFPEAndIdTipAutPro(idProgramaFPE, tipo);		
		
	}

	@Override
	public void deleteAnexo(Long id) throws RodalExceptionService, InsertarDocFault, IOException {
	
		AnexosProgPFE anexo = anexosFPERepository.findById(id).orElse(null);
		
		if (anexo != null) {			
			
			if (anexo.getIdRodal() != null) {
			   rodalClient.borrarDocumento(anexo.getIdRodal());
			}
			
			anexosFPERepository.delete(anexo);
		}
		
		
	}

	@Override
	public void actualizaAnexo(Long id, MultipartFile file)
			throws RodalExceptionService, InsertarDocFault, IOException {
		
		rodalClient.actualizaDoc(file, 
				                 ENTIDAD, 
	                             TIPO, 
	                             id,"-1",-1L,-1L);
		
	}

	@Override
	public void insertaAnexo(Long idProgramaFPE, Integer cAnno, Long xCentro, Integer tipo, MultipartFile file) throws RodalExceptionService, InsertarDocFault, IOException {
		AnexosProgPFE anexo = new AnexosProgPFE();
		anexo.setIdProgramaFPE(idProgramaFPE);
		anexo.setIdTipAutPro(tipo);
		anexosFPERepository.save(anexo);
			
		rodalClient.insertaDoc(
				file, ENTIDAD, TIPO, anexo.getId(), Long.valueOf(cAnno),
				xCentro, -1L, "-1", -1L,
				-1L);	
		
	}
}
