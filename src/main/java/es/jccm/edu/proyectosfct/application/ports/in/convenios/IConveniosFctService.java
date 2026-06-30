package es.jccm.edu.proyectosfct.application.ports.in.convenios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConvenioListFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.DatosCentroConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import net.sf.jasperreports.engine.JRException;

public interface IConveniosFctService {
	
	// Create
	
	ConveniosFct createConvenio(ConveniosFct conveniosFct);
	
	// Read
	
	List<ConvenioListFct> getAllConvenios();
	
//	List<ConvenioListFct> getAllConveniosCentroAnno(Long idCentro,Long idAnno);
	
	List<ConvenioListFct> getAllConveniosActivos(Long idCentro, Integer lglofp);
	
	ConveniosFct getConvenioById(Long idConvenio);
	
	ConveniosFct getConvenioByIdConfirRodal(String idConfirRodal);
	
	ConveniosFct getConvenioByIdConproRodal(String idConproRodal);
	
	DatosCentroConvenioProjection getDatosCentro(Long idCentro);
	
	// Update
	
	ConveniosFct updateConvenio(ConveniosFct conveniosFct);
	
	// Delete
	
	void deleteConvenio(Long idConvenio);
	
	List<Familia> getAllFamiliasConvenio(Long idConvenio);

	byte[] exportReport(Long id) throws FileNotFoundException, JRException, IOException;
	
	byte[] exportReportRenovar(Long id) throws FileNotFoundException, JRException, IOException;
	
	ConveniosFct firmarConvenio(String fechaFirma, Long idConvenio, String entidad);
	
	String getNumeroConvenio(Long idCentro);

	String getFamiliaConvenioNivelEducativo(Long idOfertamatrig);

	List<ConveniosFct> findConveniosByCentroIdAndAnno(Long idCentro, Integer cAnno);

	List<Familia> getAllFamiliasListadoConvenio(Long idCentro, Integer cAnno, Long idEmpresa, 
			Long idEstado);

	List<ConvenioListFct> getAllConveniosCentroAnno(Long idCentro, Integer cAnno, Long idEmpresa,Long  idEstado, Long idFamilia, Long idTutor, Long idMatricula, Long idTipo);

	List<ElementoSelect> getCentrosDelegacion(Long xUsuarioDelphos, Long idPerfil, Long idCentro, Long idProvincia);

	List<ConveniosFct> findConveniosDelegacionByCentroIdAndAnno(Long xUsuarioDelphos, Long idPerfil,
			Long idCentroProvincia, Long idCentro, Integer cAnno);

	List<ElementoSelect> getEmpresasDelegacion(Long idUsuario, 		
											   Long idPerfil, 
											   Long idCentro,
											   Long idCentroProvincia, 
											   Long cAnno,
											   Long idProvincia,
											   Long idEstado);
	
	List<ElementoSelect> getAllFamiliasDelegacionListadoConvenio(Long xUsuarioDelphos, 
															     Long idPerfil, 
															     Long idCentroProvincia,
															     Long idCentro, 
															     Integer cAnno, 
															     Long idEmpresa,															     
															     Long idEstado,
															     Long idProvincia);
	
	List<ConvenioListFct> getAllCoveniosDelegacionCentroAnno(Long idUsuario, 
															 Long idPerfil, 
															 Long idCentro,
															 Long idCentroProvincia, 
															 Integer cAnno, 
															 Long idEmpresa,
															 Long idEstado, 
															 Long idFamilia,
															 Long idProvincia, 
															 Long idTutor,
															 Long idMatricula,
															 Long idTipo);

	String getEsDirector(Long idCentro,Long idUsuario);

	List<ElementoSelect> getAllTutoresListadoConvenio(Long idCentro, 	
			                                          Integer cAnno, 
			                                          Long idEmpresa,			                                          
			                                          Long idEstado, 
			                                          Long idFamilia);

	List<ElementoSelect> getAllTutoresDelegacionListadoConvenio(Long idUsuario, 
																Long idPerfil, 
																Long idCentro,
																Long idCentroProvincia, 
																Integer cAnno, 
																Long idEmpresa,																
																Long idEstado,
																Long idProvincia, 
																Long idFamilia);

	List<ElementoSelect> getAllAlumnadoListadoConvenio(Long idCentro, 
													   Integer cAnno, 
													   Long idEmpresa,													   
													   Long idEstado, 
													   Long idFamilia, 
													   Long idTutor);

	List<ElementoSelect> getAllAlumnadoDelegacionListadoConvenio(Long idUsuario, 
																 Long idPerfil, 
																 Long idCentro,
																 Long idCentroProvincia, 
																 Integer cAnno, 
																 Long idEmpresa,																
																 Long idEstado,
																 Long idProvincia, 
																 Long idFamilia, 
																 Long idTutor);

    String getConveniosVigentes(Integer tipoConvenio, String cif, Long xCentro, Date fhIni, Date fhFin);

	Integer bajaConvenio(Long id);
}
