package es.jccm.edu.proyectosfct.application.domain.extraordinario.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUT_EXTPRO")
public class AutorizacionExtraordinario  extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autext_seq")
    @SequenceGenerator(name = "autext_seq", sequenceName = "SQ_FCT_AUTEXTPRO", allocationSize = 1)
	@Column(name="ID_AUT_EXTPRO")
	private Long id;
	
	@Column(name="LG_FINDE_EXT")
	private Integer lgfindeext;
	
	@Column(name="LG_VACAC_EXT")
	private Integer lgvacacext;
	
//	@Column(name="TX_CALEN_EXT")
//	private String txcalenext;
	
	@Column(name="TX_HORAR_EXT")
	private String txhorarext;
	
//	@Column(name="TX_FINDE_FUE")
//	private String txfindefue;
//	
//	@Column(name="TX_VACAC_FUE")
//	private String txvacacfue;
//	
//	@Column(name="TX_CALEN_FUE")
//	private String txcalenfue;
	
	@Column(name="TX_HORAR_FUE")
	private String txhorarfue;	
	
	@Column(name="FH_INI_CALENEXT")
	private Date fhIniCalenExt;
	
	@Column(name="FH_FIN_CALENEXT")
	private Date fhFinCalenExt;
	
	@Column(name="FH_INI_CALENFUE")
	private Date fhIniCalenFue;
	
	@Column(name="FH_FIN_CALENFUE")
	private Date fhFinCalenFue;

	@Column(name="NU_PETICION")
	private Integer nuPeticion;

	// ---------- Relationships -----------	
	
	@Column(name="ID_TUTORFCTDUAL")
	private Long idTutorFct;
	
	@Column(name="X_MATRICULA")
	private Long idMatricula;
	
	@Column(name="X_CENTRO")
	private Long idCentro;
	
	
	
	

}
