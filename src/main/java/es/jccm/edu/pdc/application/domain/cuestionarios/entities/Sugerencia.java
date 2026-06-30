package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Sugerencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idSugNiv;
	//COM.C_PDCCOMPETENCIA codCompetencia, COM.D_PDCCOMPETENCIA tituloCompetencia, COM.T_PDCCOMPETENCIA desCompetencia, COM.X_COMPETENCIA idCompetencia, COM.X_SUBDIMENSION idSubDimension,
	//NIV.X_SUGERENCIA idSugerencia, NIV.X_SECTOR idSector, NIV.X_PDCSUGNIV idSugNiv, NIV.X_NIVEL idNivel, SUG.D_COMOMEJORAR comoMejorar, SUG.D_SUGERENCIA desSugerencia,

	private String codCompetencia;
	private String tituloCompetencia;
	private String desCompetencia;
	private Long idCompetencia;
	private Long idSubDimension;
	private Long idSugerencia;
	private Long idSector;
	private Long idNivel;
	private String desSugerencia;



}
