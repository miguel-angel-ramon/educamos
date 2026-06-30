package es.jccm.edu.proyectosfct.application.ports.in.conveniosprogramas;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasHorarioAlumnoFctDto;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasAnexos;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ParteSemanalAnexosPrograma;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface IConveniosProgramasService {
	
	List<ConveniosProgramasFct> convenioAprogramas(List<ConveniosProgramasFct> convprogFct, Long idConvenio);
	
	List<ConveniosProgramasFct> getProgramasConvenio(Long idConvenio, Integer cAnno);
	
	ConveniosProgramasFct getConvenioProgramaFct(Long idConvenio);
	
	Integer countByConvenioId(Long idConvenio);

	List<ConveniosProgramasFct> createConvenioProgramas(List<ConveniosProgramasFct> conveniosProgramasFctListIn);

	void deleteConvenioProgramas(List<ConveniosProgramasFct> convenioProgramasFctListIn);

	List<ConvProgHorPeriodoFctDto> getConvenioProgramaPeriodosHorarios(Long idConvProg);

	List<ConveniosProgramasFct> createConvenioProgramasHorarioAlumno(List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto);

	void deleteConvenioProgramasHorarioAlumno(List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto);

	List<ConvProgHorPeriodoFctDto> createConvenioProgramaPeriodosHorarios(List<ConvProgHorPeriodoFctDto> listConvProgHorPeriodoFctDto);

	List<ConvProgAluHorPeriodoFctDto> getConvenioProgramaPeriodosHorariosAlumno(Long idConvProg, Long idMatricula);

	Integer getAnnoConvenioPrograma(Long idConvProg);
	
	Double getHorasAlumnos(Long id, Long idAlumno);
	
	void uploadFicherosAnexo(Long idConvProg, String tipo,List<MultipartFile> ficheros)throws RodalExceptionService, InsertarDocFault, IOException ;
	
	void updateFicherosAnexo(ConveniosProgramasFct covProgUpdate);

	List<ConveniosProgramasAnexos> getAnexoII(Long idConvProg);

	List<ParteSemanalAnexosPrograma> uploadFicherosParteSemanalAnexosProyecto(Long idConvProgAlu,
			List<MultipartFile> partesSemanales) throws RodalExceptionService, InsertarDocFault, IOException;

	List<ParteSemanalAnexosPrograma> getParteSemanalAnexosPrograma(Long idConvProgAlu);

	Integer checkOverlappingDates(Long idPrograma, Long idConvenio, String fechaInicio, String fechaFin) throws ParseException;

	void updateProgramDates(Long idConvProg, String fechaInicio, String fechaFin, Integer newHora) throws ParseException;
}
